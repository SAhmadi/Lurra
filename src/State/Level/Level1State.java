package State.Level;


import Assets.Crafting.Crafting;
import Assets.GameObjects.Player;
import Assets.Inventory.Inventory;
import Assets.World.Tile;
import Assets.World.TileMap;
import Main.GamePanel;
import Main.References;
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
    public boolean isNight = false;

    // Hintergrundbilder - Pfad
    private Image backgroundImage;
    private String level1DayBackgroundPath = "/img/grassbg1.gif";

//    private Graphics2D g2d;
//    private GradientPaint gradientPaint;
//    private final Color DAY_COLOR_1 = new Color(150, 255, 249);
//    private final Color DAY_COLOR_2 = new Color(250, 255, 255);

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
        tileMap = new TileMap(20);
        tileMap.setPosition(0, 0);

        // Spiel Fortsetzen oder Neues Spiel
/*
        if(continueLevel) {
            tileMap.levelLoad(PlayerData.name);
            //InventoryDataLoad.XMLRead(PlayerData.name);
            //inventory.loadCells();
        }
        else
            tileMap.generateMap(ScreenDimensions.WIDTH / 100);
*/

        // Positioniere Spieler
        player = new Player(22, 41, 16, 16, 0.5, -5.0, 8.0, -20.0, tileMap);
        player.setPosition(
                References.SCREEN_WIDTH/2,
                References.SCREEN_HEIGHT/2 - 2*player.getHeight()
        );
    }

    /*
    * update
    * */
    @Override
    public void update() {
        tileMap.setPosition(References.SCREEN_WIDTH / 2 - player.getX(), References.SCREEN_HEIGHT / 2 - player.getY());
        tileMap.update();

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
            graphics.drawImage(backgroundImage, 0, 0, References.SCREEN_WIDTH, References.SCREEN_HEIGHT, null);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        // Zeichne Tag Hintergrundverlauf
//        g2d = (Graphics2D) g;
//        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
//        gradientPaint = new GradientPaint(0, 0, DAY_COLOR_1, 0, References.SCREEN_HEIGHT, DAY_COLOR_2);
//        g2d.setPaint(gradientPaint);
//        g2d.fillRect(0, 0, References.SCREEN_WIDTH, References.SCREEN_HEIGHT);

        tileMap.render(g);
        player.render(g);

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
        Tile selectedTile = tileMap.getMap().get(new Point((int) ((e.getY() - tileMap.getY()) / References.TILE_SIZE), (int) (Math.floor((e.getX() - tileMap.getX()) / References.TILE_SIZE))));
        player.mouseClicked(e, selectedTile);


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
