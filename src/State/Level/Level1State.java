package State.Level;

import Assets.Assets;
import Assets.Inventory.Inventory;
import Assets.Inventory.InventoryItem;
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
import java.util.ArrayList;

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
    * Inventory
    * */
    public Inventory inventory;

    /*
    * Konstruktor - Initialisieren
    * */
    public Level1State(Graphics graphics, GamePanel gamePanel, StateManager stateManager, boolean continueLevel) {
        this.gamePanel = gamePanel;
        this.graphics = graphics;
        this.stateManager = stateManager;

        // Inventory
        this.inventory = new Inventory();

        this.continueLevel = continueLevel;
        init();
    }

    /*
    * Eigentliches Initialisieren
    * */
    @Override
    public void init() {
        tileMap = new TileMap(levelMapPath+PlayerData.name+".txt", ScreenDimensions.WIDTH/Tile.WIDTH*2, ScreenDimensions.HEIGHT/Tile.HEIGHT*2);
        tileMap.setPosition(0, 0);



            tileMap.generateMap(ScreenDimensions.WIDTH / 100);




        player = new Player(43, 43, 20, 25, 0.5, 5, 8.0, 20.0, tileMap);
        player.setPosition(
                tileMap.numberOfRows*Tile.WIDTH,
                0
        );
    }

    /*
    * update
    * */
    @Override
    public void update() {
//        int xOld = (int) tileMap.getX();
//        int yOld = (int) tileMap.getY();

        tileMap.setPosition(ScreenDimensions.WIDTH / 2 - player.getX(), ScreenDimensions.HEIGHT / 2 - player.getY());

//        if(xOld != tileMap.getX() || yOld != tileMap.getY()) {
//            tileMap.create();
//        }

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
        inventory.render(g);
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

    /**
     *
     * Getter und Setter Methoden
     *
     * */
    public Player getPlayer() { return this.player; }

}
