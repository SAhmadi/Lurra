package State.Level;


import Assets.Crafting.Crafting;
import Assets.GameObjects.Player;
import Assets.Inventory.Inventory;
import Assets.World.Tile;
import Assets.World.TileMap;
import GameSaves.PlayerData.PlayerData;
import Main.GamePanel;
import Main.ScreenDimensions;
import State.Menu.WorldMenuState;
import State.State;
import State.StateManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
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
    private String level1DayBackgroundPath = WorldMenuState.backgroundPath;

    /*
    * TileMap
    * */
    public static TileMap tileMap;
    private String levelMapPath = "res/xml/playerLevelSaves/";

    /*
    * Tile
    * */
    private Rectangle tileRectangle;

    /*
    * Player Objekt
    * */
    public static Player player;
    public BufferedImage playerImage;
    private Rectangle playerRectangle;

    /*
    * Inventory and Crafting
    * */
    public Inventory inventory;
    public Crafting crafting;

    /*
    * Konstruktor - Initialisieren
    * */
    public Level1State(Graphics graphics, GamePanel gamePanel, StateManager stateManager, boolean continueLevel) {
        this.gamePanel = gamePanel;
        this.graphics = graphics;
        this.stateManager = stateManager;

        // Inventory
        this.inventory = new Inventory();

        // Crafting
        this.crafting = new Crafting();


        // Crafting-Button;
        //this.crafting = new Crafting();

        this.continueLevel = continueLevel;

        init();
    }

    /*
    * Eigentliches Initialisieren
    * */
    @Override
    public void init() {
        tileMap = new TileMap(levelMapPath+PlayerData.name+".txt", ScreenDimensions.WIDTH/Tile.WIDTH*10, ScreenDimensions.HEIGHT/Tile.HEIGHT*2);
        tileMap.setPosition(0, 0);

        // Spiel Fortsetzen oder Neues Spiel
        if(continueLevel) {
            tileMap.levelLoad(PlayerData.name);
            //InventoryDataLoad.XMLRead(PlayerData.name);
            //inventory.loadCells();
        }
        else
            tileMap.generateMap(ScreenDimensions.WIDTH / 100);

        // Positioniere Spieler
        player = new Player(43, 43, 20, 25, 0.5, 5, 8.0, 20.0, tileMap);
        player.setPosition(
                tileMap.numberOfColumns*Tile.WIDTH / 2,
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

        // Crafting Rezepte
        crafting.checkRecipes();
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


        g.setColor(Color.BLACK);
        g.fillRect((int) crafting.getX(), (int) crafting.getY(), (int) crafting.getWidth(), (int) crafting.getHeight());

        inventory.render(g);
        crafting.render(g);
    }

    /*
    * EventListener
    * */
    @Override
    public void keyPressed(KeyEvent e) {
        player.keyPressed(e);
        inventory.keyPressed(e);
        crafting.keyPressed(e);
    }
    @Override
    public void keyReleased(KeyEvent e) {
        player.keyReleased(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        tileMap.mouseClicked(e);
        inventory.mouseClicked(e);
        crafting.mouseClicked(e);
    }
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        inventory.mouseWheelMoved(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        tileMap.mouseMoved(e);
    }

    /**
     *
     * Getter und Setter Methoden
     *
     * */
    public Player getPlayer() { return this.player; }

}
