package State.Level;

import Assets.Assets;
import Assets.Tile;
import Assets.TileMap;
import Assets.GameObjects.Player;
import Main.GamePanel;
import Main.ScreenDimensions;
import PlayerData.PlayerData;
import State.State;
import State.StateManager;

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

    private boolean continueLevel;

    // Hintergrundbilder - Pfad
    private Image backgroundImage;
    private String level1DayBackgroundPath = "/img/sky_day.jpg";

    /*
    * TileMap
    * */
    private Assets level1Assets;
    public TileMap tileMap;
    private String levelMapPath = "res/xml/playerLevelSaves/";

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
    public Level1State(Graphics graphics, GamePanel gamePanel, StateManager stateManager, boolean continueLevel) {
        this.gamePanel = gamePanel;
        this.graphics = graphics;
        this.stateManager = stateManager;

        this.continueLevel = continueLevel;
        init();
    }

    /*
    * Eigentliches Initialisieren
    * */
    @Override
    public void init() {
        level1Assets = GamePanel.tileAssets;
        tileMap = new TileMap(level1Assets, levelMapPath+PlayerData.name+".txt", ScreenDimensions.WIDTH/Tile.WIDTH*4, ScreenDimensions.HEIGHT/Tile.HEIGHT*2);
        tileMap.setPosition(0, 0);

        if(continueLevel == false)
            tileMap.createLevel(30);
        tileMap.loadMap();

        player = new Player(43, 43, 20, 25, 0.5, 5, 8.0, 20.0, tileMap);
        player.setPosition(
                (tileMap.numberOfColumns*Tile.WIDTH)/2,
                0
        );
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
            graphics.drawImage(backgroundImage, 0, 0, ScreenDimensions.WIDTH, ScreenDimensions.HEIGHT, null);
        }
        catch (IOException ex) {
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
