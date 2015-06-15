package Main;

import GameSaves.GameData.GameData;
import GameSaves.GameData.GameDataLoad;
import GameSaves.GameData.GameDataSave;
import State.StateManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.Robot;
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
    private int framesPerSecond = 40;
    private int optimalTimeLoop = 1000 / framesPerSecond;

    // Graphics Objekte
    public BufferedImage gameBufferedImage;
    public static Graphics2D graphics;

    // Spiel-Zustands-Manager
    public StateManager stateManager;

    // Pause-Menu
    private JFrame pauseMenu;

    /*
    * Netzwerk
    * */
    public static String USER_NAME = "Unknown";
    public static int PORT = 8080;
    public static String IP_ADDRESS = "localhost";


    // private Bullet bullet;

    private Robot robot = null;



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
        this.setPreferredSize(new Dimension(ScreenDimensions.WIDTH, ScreenDimensions.HEIGHT));

        // Verlange Fenster Focus
        this.setFocusable(true);
        this.requestFocus();

        // Initialisiere Graphics Objekt
        this.gameBufferedImage = new BufferedImage(ScreenDimensions.WIDTH, ScreenDimensions.HEIGHT, BufferedImage.TYPE_INT_ARGB);
        this.graphics = gameBufferedImage.createGraphics();

        // Initialisiere Zustands-Manager
        this.stateManager = new StateManager(graphics, this);

        // Initialisiere Hintergrundmusik
        Sound.diamondSound = new Sound("bling.wav");
        Sound.elevatorSound = new Sound("elevator.wav");
        Sound.boomSound = new Sound("boom.wav");
        Sound.walkSound = new Sound("walk.wav");
        Sound.jumpSound = new Sound("jump.wav");
        Sound.metalSound = new Sound("metal.wav");
        Sound.earthSound = new Sound("earth.wav");

        if(GameData.isSoundOn.equals("On"))
            Sound.elevatorSound.play();

        // Initialisiere Pause-Menu
        this.pauseMenu = new PauseMenu();

        // Initialisiere Chat-Fenster
        //this.chatWindow = new ChatWindow();

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
                gameFrame.setTitle(Long.toString(threadSleepTime));     // TEST ZWECKEN
                Thread.sleep(threadSleepTime);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }

            update();
            render();

            if (stateManager.getActiveState() <= 0)
            {
                repaint();
            }
            else
            {
                displayGameBufferedImage();
            }

            // Falls auf ESC gedrueckt wurde, pausiere
            if(PauseMenu.paused.get())
            {
                synchronized(gameThread)
                {
                    try
                    {
                        gameThread.wait();
                    }
                    catch (InterruptedException ex)
                    {
                        ex.printStackTrace();
                    }
                }
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

    public void takeScreenshot() {
        try {
            Robot robot = new Robot();
            BufferedImage i = null;
            i = robot.createScreenCapture(new Rectangle(0, 0, ScreenDimensions.WIDTH, ScreenDimensions.HEIGHT));
            File output = new File("res/img/Screenshots/screeni.png");
            ImageIO.write(i, "png", output);
            System.out.println("Screenshot gespeichert! Bitch!!");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (AWTException e) {
            e.printStackTrace();
        }

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
                pauseMenu.setVisible(true);
                pauseMenu.setSize(ScreenDimensions.WIDTH, ScreenDimensions.HEIGHT);
            }

            synchronized(gameThread)
            {
                gameThread.notify();
            }
        }

        //Kugel abfeuern beim drücken der Leertaste
       /* if(e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            bullet = new Bullet();
            bullet.DrawBullet();
            System.out.println("Kugel geschossen.");
        }*/

        if (e.getKeyCode() == KeyEvent.VK_L) {
            takeScreenshot();
        }

        // ActionListener Return-Button
        PauseMenu.returnBtn.addActionListener(ae ->
        {
            if (PauseMenu.paused.get())
            {
                pauseMenu.setVisible(false);
                PauseMenu.paused.set(false);
            }

            synchronized(gameThread)
            {
                gameThread.notify();
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

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) { stateManager.mouseWheelMoved(e); }

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) { stateManager.mouseMoved(e); }

}
