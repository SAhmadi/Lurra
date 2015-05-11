package Main;

import Assets.Assets;
import State.StateManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

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

    /*
    * MenuState Hintergrundbild
    * */
    private Image backgroundImage;
    private String menuBackgroundPath = "/img/lurra_background.jpg";

    /*
    * Konstruktor
    * */
    public GamePanel(JFrame gameFrame) {
        this.gameFrame = gameFrame;

        // Setzte Panel Dimensionen
        panelSize = new Dimension(ScreenDimensions.WIDTH, ScreenDimensions.HEIGHT);
        this.setPreferredSize(panelSize);

        // Verlange Fenster Focus
        this.setFocusable(true);
        this.requestFocus();

        // Initialisiere Graphics Objekte
        gameBufferedImage = new BufferedImage(ScreenDimensions.WIDTH, ScreenDimensions.HEIGHT, BufferedImage.TYPE_INT_RGB);
        graphics = gameBufferedImage.createGraphics();

        // Initialisiere Zustands-Manager
        stateManager = new StateManager(graphics, this);

        // Initialisiere Assets
        this.tileAssets = new Assets(this.tileAssetsResPath);

        // Initialisiere MenuState Hintergrundbild
        if(stateManager.getActiveState() == stateManager.MENUSTATE) {
            try {
                this.backgroundImage = ImageIO.read(getClass().getResourceAsStream(menuBackgroundPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Starte Game-Thread
        startThread();
    }

    // Initialisiere Game-Thread
    private void startThread() {
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

            // Berechne wie lang Schleife gedauert hat
            deltaTime = System.nanoTime() - startTime;
            threadSleepTime = optimalTimeLoop - deltaTime/1000000;

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
    // Erlaubt das Zeichnen auf dem Panel. Wird selbst regelmaeﬂig aufgerufen.
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
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            System.exit(0);
        stateManager.keyPressed(e);
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
