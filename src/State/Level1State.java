package State;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

/*
* Level1State - Erstes Level
* */
public class Level1State extends State {

    // Inhaltsflaeche, Graphics-Obj und Zustands-Manger
    protected GamePanel gamePanel;
    protected Graphics graphics;
    protected StateManager stateManager;

    // Hintergrundbilder - Pfad
    private Image backgroundImage;
    private String level1DayBackgroundPath = "/img/sky_day.jpg";

    /*
    * Konstruktor - Initialisieren
    * */
    public Level1State(Graphics graphics, GamePanel gamePanel, StateManager stateManager) {
        this.gamePanel = gamePanel;
        this.graphics = graphics;
        this.stateManager = stateManager;
        init();
    }

    /*
    * Eigentliches Initialisieren
    * */
    @Override
    public void init() {}

    /*
    * update
    * */
    @Override
    public void update() {}

    /*
    * render
    * */
    @Override
    public void render(Graphics g) {
        try {
            this.backgroundImage = ImageIO.read(getClass().getResourceAsStream(level1DayBackgroundPath));
            graphics.drawImage(backgroundImage, 0, 0, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    * EventListener
    * */
    @Override
    public void keyPressed(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}

}
