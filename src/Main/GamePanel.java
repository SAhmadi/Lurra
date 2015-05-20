package Main;

import Assets.Assets;
import State.StateManager;

import javax.imageio.*;
import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.concurrent.atomic.AtomicBoolean;

/*
* GamePanel - Spiel Inhaltsflaeche
* */
public class GamePanel extends JPanel implements Runnable, KeyListener, MouseListener {

    // Spielfenster
    public JFrame gameFrame;

    // Fensterdimension
    private Dimension panelSize;

    // Game Thread
    private Thread gameThread;
    private boolean isRunning = false;

    // Frames per Second
    private int framesPerSecond = 60;
    private int optimalTimeLoop = 1000 / framesPerSecond;

    // Graphics Objekte
    public BufferedImage gameBufferedImage;
    public Graphics2D graphics;

    // Spiel-Zustands-Manager
    public StateManager stateManager;

     // Assets
    public static Assets tileAssets;
    private String tileAssetsResPath = "/img/tileSet.png";

    // Menu Hintergrundbild
    private Image backgroundImage;
    private String menuBackgroundPath = "/img/lurra_background.jpg";

    // Pause Fenster
    private JButton returnButton;
    private JButton saveButton;
    private JButton exitButton;
    private JButton MainMenueButton;
    private JFrame frameForESC;
    private AtomicBoolean paused;


    /*
    * Konstruktor - Initialisieren
    *
    * @param gameFrame  - Spielfenter
    * */
    public GamePanel(JFrame gameFrame) {
        this.gameFrame = gameFrame;

        // Setzte Panel Dimensionen
        panelSize = new Dimension(ScreenDimensions.WIDTH, ScreenDimensions.HEIGHT);
        this.setPreferredSize(panelSize);

        // Verlange Fenster Focus
        this.setFocusable(true);
        this.requestFocus();

        // Initialisiere Graphics Objekt
        gameBufferedImage = new BufferedImage(ScreenDimensions.WIDTH, ScreenDimensions.HEIGHT, BufferedImage.TYPE_INT_RGB);
        graphics = gameBufferedImage.createGraphics();

        // Initialisiere Zustands-Manager
        stateManager = new StateManager(graphics, this);

        // Initialisiere Assets
        this.tileAssets = new Assets(this.tileAssetsResPath);

        // Initialisiere MenuState Hintergrundbild
        if (stateManager.getActiveState() == stateManager.MENUSTATE) {
            try {
                this.backgroundImage = ImageIO.read(getClass().getResourceAsStream(menuBackgroundPath));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Initialisiere ESC Fenster
        this.returnButton = new JButton("Return");
        this.saveButton = new JButton("Save");
        this.exitButton = new JButton("Exit");
        this.MainMenueButton = new JButton("MainMenue")
        ;
        this.returnButton.setForeground(Color.WHITE);
        this.saveButton.setForeground(Color.WHITE);
        this.exitButton.setForeground(Color.WHITE);
        this.MainMenueButton.setForeground(Color.WHITE);

        this.returnButton.setBounds(0, 110, 80, 25);
        this.saveButton.setBounds(109, 110, 80, 25);
        this.exitButton.setBounds(218, 110, 80, 25);
        this.MainMenueButton.setBounds(100, 80, 100, 25);

        this.returnButton.setBackground(Color.BLACK);
        this.saveButton.setBackground(Color.BLACK);
        this.exitButton.setBackground(Color.BLACK);
        this.MainMenueButton.setBackground(Color.BLACK);

        // Event für Exit-Button
        this.exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);}});

        this.MainMenueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               // JFrame escapeFrame = new JFrame("Lurra");



                }});






        this.frameForESC = new JFrame();
        this.paused = new AtomicBoolean(false);

        try {
            frameForESC.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("res/img/sky_sunset.jpg")))));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        this.frameForESC.add(this.returnButton);
        this.frameForESC.add(this.saveButton);
        this.frameForESC.add(this.exitButton);
        this.frameForESC.add(this.MainMenueButton);
        startThread();
    }


    public void actionPerformed( ActionEvent e ) {
        Object source = e.getSource();





    }



    // Initialisiere Game-Thread
    private synchronized void startThread() {
        if(isRunning)
            return;

        isRunning = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    /*
    * run - Spielschleife
    * */
    public void run() {
        addKeyListener(this);
        addMouseListener(this);

        // Setze Timer zur Berechnung der Frames-Per-Second
        long startTime;
        long deltaTime;
        long threadSleepTime;
        while(isRunning) {
            // Speichere Startzeit
            startTime = System.nanoTime();

            update();
            render();

            if(stateManager.getActiveState() == stateManager.MENUSTATE) {
                repaint();
            }
            else {
                displayGameBufferedImage();
            }

            // Falls auf ESC gedrückt wurde, pausiere
            if(paused.get()) {
                synchronized(gameThread) {
                    // Pause
                    try {
                        gameThread.wait();
                    }
                    catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }

            // Berechne wie lang Schleife gedauert hat
            deltaTime = System.nanoTime() - startTime;
            threadSleepTime = optimalTimeLoop - deltaTime/1000000;  // Umrechnen in Millisekunden

            // Abfangen
            if(threadSleepTime < 0)
                threadSleepTime = 5;

            // Falls Schleife schneller ausgefuehrt wurde, warte
            if(threadSleepTime > 0) {
                try {
                    Thread.sleep(threadSleepTime);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /*
    * update - Ruft Update-Methode des Game-State-Mangers
    * Updated alle Veraenderungen bezueglich Spieler-Position, etc.
    * */
    public void update() {
        stateManager.update();
    }

    /*
    * render - Ruft Render-Methode des Game-State-Mangers
    * Alle Updates werden gezeichnet
    * */
    public void render() {
        stateManager.render(graphics);
    }

    /*
    * displayGameBufferedImage - Zeichnen des Game-Image
    * */
    public void displayGameBufferedImage() {
        Graphics gameBufferedImageGraphics = this.getRootPane().getGraphics();
        gameBufferedImageGraphics.drawImage(gameBufferedImage, 0, 0, null);
        gameBufferedImageGraphics.dispose();
    }

   // Ueberschreiben der paintComponent-Methode
    // Erlaubt das Zeichnen auf dem Panel. Wird selbst regelmaeßig aufgerufen.
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Zeichne Hintergrundbild des aktiven States
        if(stateManager.getActiveState() == stateManager.MENUSTATE)
            g.drawImage(backgroundImage, 0, 0, null);
    }

    /*
    * EventListeners
    * */
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        stateManager.keyPressed(e);

        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (!paused.get()) {
                paused.set(true);

                frameForESC.setVisible(true);
                frameForESC.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frameForESC.setSize(315, 315);
            }

            synchronized (gameThread) {
                gameThread.notify();
            }
        }

        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (paused.get()) {
                    frameForESC.setVisible(false);
                    paused.set(false);
                }
                synchronized (gameThread) {
                    gameThread.notify();
                }
            }
        });

    }

    @Override
    public void keyReleased(KeyEvent e) { stateManager.keyReleased(e); }

    @Override
    public void mouseClicked(MouseEvent e) { stateManager.mouseClicked(e); }

    @Override
    public void mousePressed(MouseEvent e) { stateManager.mousePressed(e); }

    @Override
    public void mouseReleased(MouseEvent e) { stateManager.mouseReleased(e); }

    @Override
    public void mouseEntered(MouseEvent e) { stateManager.mouseEntered(e); }

    @Override
    public void mouseExited(MouseEvent e) { stateManager.mouseExited(e); }

}
