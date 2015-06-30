package Main;

import GameSaves.GameData.GameData;
import GameSaves.GameData.GameDataLoad;
import GameSaves.GameData.GameDataSave;
import State.Level.Level1State;
import State.Level.MPLevelState;
import State.StateManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    private int framesPerSecond = 40;
    private int optimalTimeLoop = 1000 / framesPerSecond;

    // Graphics Objekte
    public BufferedImage gameBufferedImage;
    public Graphics2D graphics;


    // Spiel-Zustands-Manager
    public StateManager stateManager;

    // Pause-Menu
    private JFrame pauseMenu;

    // Netzwerk
    public static String USER_NAME = "Unknown";
    public static int PORT = 8080;
    public static String IP_ADDRESS = "localhost";

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

        // Initialisiere Hintergrundmusik und SFX
        Sound.waterSound = new Sound("water.wav");
        Sound.elevatorSound = new Sound("elevator.wav");
        Sound.boomSound = new Sound("boom.wav");
        Sound.walkSound = new Sound("walk.wav");
        Sound.jumpSound = new Sound("jump.wav");
        Sound.metalSound = new Sound("metal.wav");
        Sound.earthSound = new Sound("earth.wav");
        Sound.desertSound = new Sound("desert.wav");
        Sound.jungleSound = new Sound("jungle.wav");
        Sound.alaskaSound = new Sound("alaska.wav");
        Sound.woodSound = new Sound("wood.wav");
        Sound.explosionSound = new Sound ("explosion.wav");
        Sound.startButtonSound = new Sound("startButton.wav");
        Sound.settingButtonSound = new Sound("settingButton.wav");
        Sound.soundButtonSound = new Sound("soundButton.wav");
        Sound.backButtonSound = new Sound("backButton.wav");
        Sound.avatarButtonSound = new Sound ("avatarButton.wav");
        Sound.localButtonSound = new Sound ("localButton.wav");
        Sound.onlineButtonSound = new Sound("onlineButton.wav");
        Sound.continueButtonSound = new Sound("continueButton.wav");
        Sound.newGameButtonSound = new Sound("newGameButton.wav");
        Sound.createButtonSound = new Sound("createButton.wav");
        Sound.joinButtonSound = new Sound("joinButton.wav");
        Sound.spectateButtonSound = new Sound("spectateButton.wav");
        Sound.endStartButtonSound = new Sound("endStartButton.wav");
        Sound.onButtonSound = new Sound("onButton.wav");
        Sound.offButtonSound = new Sound("offButton.wav");
        Sound.gameSound = new Sound("gameSound.wav");
        Sound.eatSound = new Sound ("eat.wav");
        Sound.heartBeatSound = new Sound ("heartBeat.wav");
        Sound.drinkSound = new Sound ("drink.wav");
        Sound.killSound = new Sound ("kill.wav");



        // Initialisiere Pause-Menu
        this.pauseMenu = new PauseMenu(graphics, this, stateManager);

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
            }
            catch (Exception ex) { System.out.println("Error: " + ex.getMessage()); }

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
            if (Level1State.isDead ) {
                isRunning = false;
            } else if (MPLevelState.goldRushDone) {
                isRunning = false;
            }
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
     * takeScreenshot           Aufnehmen eines Screenshots
     * */
    public void takeScreenshot() {
        try
        {
            BufferedImage screenShotImage = new Robot().createScreenCapture(new Rectangle(0, 0, References.SCREEN_WIDTH, References.SCREEN_HEIGHT));
            File screenShot = new File("res/img/Screenshots/" + new SimpleDateFormat("yyyy-MM-dd-hh-mm'.png'").format(new Date()));

            ImageIO.write(screenShotImage, "png", screenShot);
            System.out.println("Screenshot gespeichert.");
        }
        catch (IOException | AWTException ex) { System.out.println("Error: " + ex.getMessage()); }
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
