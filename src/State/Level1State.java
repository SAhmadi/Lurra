package State;

import Assets.Assets;
import Assets.TileMap;
import Assets.GameObjects.Player;
import Main.GamePanel;
import Main.ScreenDimensions;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
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
    * TileMap
    * */
    private Assets level1Assets;
    public TileMap tileMap;
    private String level1MapFilePath = "res/maps/earthMap.txt";

    /*
    * Tile
    * */
    private Rectangle tileRectangle;

    /*
    * Player Objekt
    * */
    private Player player;
    public BufferedImage playerImage;
    private Rectangle playerRectangle;

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
    public void init() {
        level1Assets = GamePanel.tileAssets;

        tileMap = new TileMap(level1Assets, level1MapFilePath);
        tileMap.setPosition(0,0);
        tileMap.createLevel();
        tileMap.loadMap();

        player = new Player(43, 43, 20, 30, 0.5, 5, 8.0, 20.0, tileMap);
        player.setPosition(400, 100);
    }

    /*
    * update
    * */
    @Override
    public void update() {
        tileMap.setPosition(ScreenDimensions.WIDTH / 2 - player.getX(), ScreenDimensions.HEIGHT / 2 - player.getY());
        player.update();
    }

    /*
    * render
    * */
    @Override
    public void render(Graphics g) {
        // Zeichne Hintergrund
        try {
            this.backgroundImage = ImageIO.read(getClass().getResourceAsStream(level1DayBackgroundPath));
            graphics.drawImage(backgroundImage, 0, 0, null);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        tileMap.render(g);
        player.render(g);
    }

    /*
    * EventListener
    * */
    @Override
    public void keyPressed(KeyEvent e) {
        player.keyPressed(e);
    }
    @Override
    public void keyReleased(KeyEvent e) {
        player.keyReleased(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        tileMap.mouseClicked(e);
    }
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}

}
