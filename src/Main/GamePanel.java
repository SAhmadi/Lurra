package Main;

import GameSaves.GameData.GameDataLoad;
import GameSaves.GameData.GameDataSave;
import State.Level.Level1State;
import State.Level.MPLevelState;
import State.StateManager;
import com.restfb.BinaryAttachment;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Inhaltsflaeche des Spiels. Starten der Spielschleife
 *
 * @author Sirat, Amin, Mo, Halit
 * */
public class GamePanel extends JPanel implements Runnable, KeyListener, MouseListener, MouseWheelListener, MouseMotionListener
{

    // Spielfenster
    public JFrame gameFrame;

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

    // Screenshot
    private int zScreenshotNumber = 0;

    // Pause-Menu
    public JFrame pauseMenu;

    // Netzwerk
    public static String USER_NAME = "Unknown";
    public static int PORT = 8080;
    public static String IP_ADDRESS = "localhost";

    // Facebook-Token (Diese Wertmarke muss jede eine Stunde geändert werden)
    private FacebookClient hFacebookClient = new DefaultFacebookClient
            ("CAACEdEose0cBAOF8ZCbnYKbG1swEu3IS7ZBuGAPCrZCPzZBbim5UjUcZAnSDOMduPTnQ4XgQOBbZCHmpZCZCAZAuhLFbZAPGa4KQ23Vv4UrPXlwNoRb6ziv65hddtMMRVPlr5v0ZCds0JPKAt1wlxJGP70PUbew2xQrsXxJRw5fkSLJiKNZAbLZBLMOJbnBBLrg3665j6Utc1fVCeP5E84tLj24mB");

    /**
     * GamePanel        Konstruktor der GamePanel-Klasse
     *
     * @param gameFrame Spielfenster
     * */
    public GamePanel(JFrame gameFrame)
    {
        this.gameFrame = gameFrame;
        this.setBackground(Color.BLACK);

        // Initialisiere Spielstands-Daten
        GameDataLoad.XMLRead();
        GameDataSave.XMLSave();

        // Alle Resourcen laden
        ResourceLoader.loadResources();

        // Setzte Panel Dimensionen
        this.setPreferredSize(new Dimension(References.SCREEN_WIDTH, References.SCREEN_HEIGHT));

        // Verlange Fenster Focus
        this.setFocusable(true);
        this.requestFocus();

        // Initialisiere Graphics Objekt
        this.gameBufferedImage = new BufferedImage(References.SCREEN_WIDTH, References.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        this.graphics = gameBufferedImage.createGraphics();


        // Initialisiere Zustands-Manager
        this.stateManager = new StateManager(graphics, this);

        // Initialisiere Pause-Menu
        pauseMenu = new PauseMenu(graphics, this, stateManager);

        // Starte Game-Thread
        startThread();
    }

    /**
     * startThread      Starten der Spielschleife
     * */
    private synchronized void startThread()
    {
        if(isRunning)
            return;
        isRunning = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * run      Spielschleife
     * */
    public void run()
    {
        // Initialisiere Listeners
        addKeyListener(this);
        addMouseListener(this);
        addMouseWheelListener(this);
        addMouseMotionListener(this);

        // Setze Timer zur Berechnung der Frames-Per-Second
        long startTime, currentTime, threadSleepTime;

        // Scpielschleife
        while(isRunning)
        {
            startTime = System.currentTimeMillis();

            // Thread-Sleep
            try
            {
                currentTime = System.currentTimeMillis();
                threadSleepTime = optimalTimeLoop - (currentTime - startTime);
                Thread.sleep(threadSleepTime);
            } catch (Exception ex) { if (References.SHOW_EXCEPTION) System.out.println("Error: " + ex.getMessage()); }

            //Wenn Spieler tot ist oder Goldrush gewonnen wurde, dann ist Spiel vorbei
            if (References.GAME_OVER || MPLevelState.goldRushDone) { isRunning = false; }

            // Spiel-Update
            update();
            render();

            if (stateManager.getActiveState() <= 0) repaint();
            else displayGameBufferedImage();

            // Falls auf ESC gedrueckt wurde, pausiere
            if(PauseMenu.paused.get())
            {
                synchronized(gameThread)
                {
                    try { gameThread.wait(); }
                    catch (InterruptedException ex) { System.out.println("Error " + ex.getMessage()); }
                }
            }

            //Wenn Spieler tot ist oder Goldrush gewonnen wurde, Spiel vorbei
            if (Level1State.isDead || MPLevelState.goldRushDone) isRunning = false;
        }
    }

    /**
     * update       Ruft Update-Methode des Game-State-Mangers
     * */
    public void update() { stateManager.update(); }


    /**
     * render       Ruft Render-Methode des Game-State-Mangers
     * */
    public void render() { stateManager.render(graphics); }

    // zurueckgeben den Rueckgabewert für das Bild von Screenshot
    public BufferedImage getScreenShot(){

        BufferedImage image = new BufferedImage(this.getWidth(),this.getHeight(),BufferedImage.TYPE_INT_RGB);

        this.paint(image.getGraphics());
        return image;
    }

    // Screenshot in Facebook teilen
    public void takeScreenshot() {

        BufferedImage img = getScreenShot();
        try {
            String filename = "screenshot" + zScreenshotNumber + ".png";
            String path = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
            ImageIO.write(img, "png", new File(path + filename));
            FacebookType publishPhotoResponse = hFacebookClient.publish("me/photos", FacebookType.class, BinaryAttachment.with(filename, getClass().getResourceAsStream("/" + filename)), Parameter.with("message", "Bestes Spiel, probiert es aus!" ));
            //new BinaryAttachment (filename, )

            System.out.println("Foto hochgeladen");

            zScreenshotNumber++;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * displayGameBufferedImage     Zeichnen des Game-Image
     * */
    public void displayGameBufferedImage()
    {
        Graphics gameBufferedImageGraphics = this.getRootPane().getGraphics();
        gameBufferedImageGraphics.drawImage(gameBufferedImage, 0, 0, null);
        gameBufferedImageGraphics.dispose();
    }

    /**
     * OVERRIDES
     * */
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(gameBufferedImage, 0, 0, null);
    }

    /**
     * EVENTLISTENERS
     * */
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e)
    {
        // KeyPressed des aktiven States
        stateManager.keyPressed(e);

        // Falls ESC-Taste, rufe Pause-Menu auf
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
            if(!PauseMenu.paused.get())
            {
                PauseMenu.paused.set(true);
                pauseMenu.setSize(References.SCREEN_WIDTH, References.SCREEN_HEIGHT);
                pauseMenu.setVisible(true);
            }

            synchronized(gameThread) { gameThread.notify(); }
        }

        // Aufnehmen eines Screenshots
        if (e.getKeyCode() == KeyEvent.VK_L) { takeScreenshot(); }

        // ActionListener Return-Button
        PauseMenu.returnBtn.addActionListener(ae ->
        {
            if (PauseMenu.paused.get())
            {
                pauseMenu.setVisible(false);
                PauseMenu.paused.set(false);
            }

            synchronized(gameThread) { gameThread.notify(); }
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

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) { stateManager.mouseWheelMoved(e); }

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) { stateManager.mouseMoved(e); }

}
