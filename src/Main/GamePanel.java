package Main;

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
import java.io.File;
import java.io.IOException;

/*
* Spielflaeche
* */
public class GamePanel extends JPanel implements Runnable, KeyListener, MouseListener {

    // Spielfenster
    JFrame gameFrame;

    // Fensterdimension
    private ScreenDimensions screenDimensions = new ScreenDimensions();
    private Dimension panelSize;

    // Game Thread
    private Thread gameThread;
    private boolean isRunning = false;

    // Frames per Second
    private int framesPerSecond = 60;
    private int optimalTimeLoop = 1000 / framesPerSecond;

    // Graphics Objekte
    public BufferedImage bufferedImage;
    public Graphics2D graphics;
    private Graphics2D graphicsForBufferdImage;

    // Zustands-Manager
    StateManager stateManager;

    /*
    * MENUSTATE Hintergrundbild
    * */
    Image backgroundImage;
    private String backgroundPath = "/img/lurra_background.jpg";

    /*
    * Konstruktor
    * */
    public GamePanel(JFrame gameFrame) {
        this.gameFrame = gameFrame;

        // Setzte Panel Dimensionen
        panelSize = new Dimension(screenDimensions.getWidth(), screenDimensions.getHeight());
        this.setPreferredSize(panelSize);

        // Initialisiere Graphics Objekte
        bufferedImage = new BufferedImage(screenDimensions.getWidth(), screenDimensions.getHeight(), BufferedImage.TYPE_INT_RGB);
        graphics = (Graphics2D)bufferedImage.getGraphics();

        // Initialisiere Zustands-Manager
        stateManager = new StateManager(graphics, this);

        // Initialisiere MenuState Hintergrundbild
        try {
            this.backgroundImage = ImageIO.read(getClass().getResourceAsStream(backgroundPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        // Setze Timer zur Berechnung der Frames-Per-Second
        long startTime;
        long deltaTime;
        long threadSleepTime;

        while(isRunning) {
            // Speichere Startzeit
            startTime = System.nanoTime();

            update();
            render();

            // Berechne wie lang Schleife gedauert hat
            deltaTime = System.nanoTime() - startTime;
            threadSleepTime = deltaTime / 1000000;

            if(threadSleepTime > 0) {
                try {
                    Thread.sleep(threadSleepTime);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void update() {
        stateManager.update(graphics, gameFrame, this);
    }

    public void render() {
        stateManager.render(graphics, gameFrame, this);
    }

    // Überschreiben der paintComponent-Methode
    // Erlaubt das Zeichnen auf dem Panel. Wird selbst aufgerufen, wenn es um.
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Wenn MenuState aktiv, dann zeichne Lurra - Hintergrundbild
        if(stateManager.getActiveState() == 0)
            g.drawImage(backgroundImage, 0, 0, null);

        Graphics drawBufferedImage = this.getRootPane().getGraphics();
        drawBufferedImage.drawImage(bufferedImage, 0, 0, null);
        drawBufferedImage.dispose();
    }

    // Starte Game-Thread
    @Override
    public void addNotify() {
        super.addNotify();
        // Starte Thread
        if(gameThread == null) {
            gameThread = new Thread(this);
            gameThread.start();
            isRunning = true;
        }
    }

    /*
    * EventListeners
    * */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
