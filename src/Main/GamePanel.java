package Main;

import Assets.Assets;
import GameData.GameData;
import GameData.GameDataSave;
import GameData.GameDataLoad;
import State.StateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Image;

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
    private int framesPerSecond = 30;
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
    private Image menuBackgroundImage;
    private String menuBackgroundPath = "/img/Menu/menuBackground.jpg";

    // Pause Menu
    private JFrame pauseMenu;
    private JFrame chatWindow;



//    private Runtime runtime;
//    private Runtime tmp;
//    private int mb = 1024*1024;


    /*
    * Konstruktor - Initialisieren
    *
    * @param gameFrame  - Spielfenter
    * */
    public GamePanel(JFrame gameFrame) {
        this.gameFrame = gameFrame;
        this.setBackground(Color.BLACK);

        // Initialisiere Spielstands-Daten
        //GameDataSave.XMLSave(null);
        GameDataLoad.XMLRead(null);
        GameDataSave.XMLSave(null);

        // Alle Resourcen laden
        ResourceLoader.loadResources();

        // Setzte Panel Dimensionen
        panelSize = new Dimension(ScreenDimensions.WIDTH, ScreenDimensions.HEIGHT);
        this.setPreferredSize(panelSize);

        // Verlange Fenster Focus
        this.setFocusable(true);
        this.requestFocus();

        // Initialisiere Graphics Objekt
        gameBufferedImage = new BufferedImage(ScreenDimensions.WIDTH, ScreenDimensions.HEIGHT, BufferedImage.TYPE_INT_ARGB);
        graphics = gameBufferedImage.createGraphics();

        // Initialisiere Zustands-Manager
        stateManager = new StateManager(graphics, this);

        // Initialisiere Assets
        this.tileAssets = new Assets(this.tileAssetsResPath);

        // Initialisiere Hintergrundmusik
        Sound.diamondSound = new Sound("bling.wav");
        Sound.elevatorSound = new Sound("elevator.wav");

        if(GameData.isSoundOn.equals("On")) {
            Sound.elevatorSound.play();
        }

        this.pauseMenu = new PauseMenu();
        this.chatWindow = new ChatWindow();

        // Starte Game-Thread
        startThread();
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

            //displayGameBufferedImage();
            if (stateManager.getActiveState() <= 0) {
                repaint();
            } else {
                displayGameBufferedImage();
            }

            // Falls auf ESC gedrückt wurde, pausiere
            if(PauseMenu.paused.get()) {
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

//            runtime = Runtime.getRuntime();
//            System.out.println("max memory: " + runtime.maxMemory() / mb);
//
//            runtime = Runtime.getRuntime();
//            System.out.println("allocated memory: " + runtime.totalMemory() / mb);
//            tmp = runtime;
//
//            runtime = Runtime.getRuntime();
//            System.out.println("free memory: " + runtime.freeMemory() / mb);
//
//            System.out.println("used memory: " + (runtime.totalMemory() - tmp.freeMemory())/mb );
//
//            System.out.println("-----------");
//

            // Berechne wie lang Schleife gedauert hat
            deltaTime = System.nanoTime() - startTime;
            threadSleepTime = optimalTimeLoop - deltaTime/1000000;  // Umrechnen in Millisekunden

            //System.out.println(threadSleepTime);

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
    // Erlaubt das Zeichnen auf dem Panel. Wird selbst regelmaessig aufgerufen.
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(gameBufferedImage, 0, 0, null);

        // Zeichne Hintergrundbild des aktiven States
//        if(stateManager.getActiveState() == stateManager.MENUSTATE || stateManager.getActiveState() == stateManager.STARTMENUSTATE || stateManager.getActiveState() == stateManager.SETTINGSSTATE)
//            g.drawImage(menuBackgroundImage,
//                    0, 0,
//                    ScreenDimensions.WIDTH*ScreenDimensions.SCALE, ScreenDimensions.HEIGHT*ScreenDimensions.SCALE,
//                    null);
    }

    /*
    * EventListeners
    * */
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        // KeyPressed des aktiven States
        stateManager.keyPressed(e);

        // Falls ESC-Taste rufe Pause-Menu auf
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (!PauseMenu.paused.get()) {
                PauseMenu.paused.set(true);
                pauseMenu.setVisible(true);
                pauseMenu.setSize(ScreenDimensions.WIDTH, ScreenDimensions.HEIGHT);
            }

            synchronized (gameThread) {
                gameThread.notify();
            }
        }

        if(e.getKeyCode() == KeyEvent.VK_C) {
            if (!PauseMenu.paused.get()) {

                chatWindow.setVisible(true);
                chatWindow.setBounds(950, 600, 400, 150);
            }

            synchronized (gameThread) {
                gameThread.notify();
            }
        }

        PauseMenu.returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (PauseMenu.paused.get()) {
                    pauseMenu.setVisible(false);
                    PauseMenu.paused.set(false);
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
