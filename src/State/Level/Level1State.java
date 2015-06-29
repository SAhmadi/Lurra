package State.Level;

import Assets.Crafting.Crafting;
import Assets.GameObjects.Player;
import Assets.Inventory.Inventory;
import Assets.World.Background;
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
import java.io.IOException;

/**
 * Erstes Level des Spiels
 *
 * @author Sirat
 * */
public class Level1State extends State
{

    // Inhaltsflaeche, Graphics-Obj und Zustands-Manger
    protected GamePanel gamePanel;
    protected Graphics graphics;
    protected StateManager stateManager;

    private boolean continueLevel;
    public boolean isNight = false;

    // Hintergrundbilder - Pfad
    private Background background;

    private Image backgroundImage;
    private String level1DayBackgroundPath = "/img/grassbg1.gif";

//    private Graphics2D g2d;
//    private GradientPaint gradientPaint;
//    private final Color DAY_COLOR_1 = new Color(150, 255, 249);
//    private final Color DAY_COLOR_2 = new Color(250, 255, 255);

    // Tilemap
    public static TileMap tileMap;
    private String levelMapPath = "res/xml/playerLevelSaves/";

    // Spieler
    private Player player;

    // Inventar und Crafting
    public Inventory inventory;
    public Crafting crafting;

    /**
     * Level1State          Konstruktor der Level1State-Klasse
     *
     * @param graphics      Graphics Objekt
     * @param gamePanel     Spielinhaltsflaeche
     * @param stateManager  Zustandsmanager
     * @param continueLevel Wert, ob bereits angespielt
     * */
    public Level1State(Graphics graphics, GamePanel gamePanel, StateManager stateManager, boolean continueLevel)
    {
        this.gamePanel = gamePanel;
        this.graphics = graphics;
        this.stateManager = stateManager;

        this.background = new Background();

        // Inventory und Crafting
        this.inventory = new Inventory();
        this.crafting = new Crafting();

        this.continueLevel = continueLevel;

        init();
    }

    /**
     * init     Initialisieren des Levels
     * */
    @Override
    public void init() {
        // Hintergrundbild
        try { this.backgroundImage = ImageIO.read(getClass().getResourceAsStream(level1DayBackgroundPath)); }
        catch (IOException ignored) {}

        // Tilemap
        tileMap = new TileMap(20);
        tileMap.setPosition(0, 0);

        // Spiel Fortsetzen oder Neues Spiel
//        if(continueLevel) {
//            tileMap.levelLoad(PlayerData.name);
//            //InventoryDataLoad.XMLRead(PlayerData.name);
//            //inventory.loadCells();
//        }
//        else
//            tileMap.generateMap(ScreenDimensions.WIDTH / 100);

        // Spieler Positionieren
        player = new Player(22, 41, 16, 16, 0.5, -5.0, 8.0, -20.0, tileMap);
        player.setPosition(
                References.SCREEN_WIDTH/2,
                References.SCREEN_HEIGHT/2 - 2*player.getHeight()
        );
        tileMap.xForTileMapStart = player.getX();
    }

    /**
     * update       Aktualisieren der Levelinhalte
     * */
    @Override
    public void update()
    {
        // Hintergrund
        background.update();

        // Tilemap
        tileMap.setPosition(References.SCREEN_WIDTH / 2 - player.getX(), References.SCREEN_HEIGHT / 2 - player.getY());
        tileMap.update();

        // Spieler
        player.update();

        // Crafting Rezepte
        crafting.checkRecipes();
    }

    /**
     * render       Zeichnen des Levels
     * */
    @Override
    public void render(Graphics g)
    {
        // Zeichne Hintergrund
        if (Background.opacity < 180)
            graphics.drawImage(backgroundImage, 0, 0, References.SCREEN_WIDTH, References.SCREEN_HEIGHT, null);

        // Zeichne Tag Hintergrundverlauf
//        g2d = (Graphics2D) g;
//        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
//        gradientPaint = new GradientPaint(0, 0, DAY_COLOR_1, 0, References.SCREEN_HEIGHT, DAY_COLOR_2);
//        g2d.setPaint(gradientPaint);
//        g2d.fillRect(0, 0, References.SCREEN_WIDTH, References.SCREEN_HEIGHT);

        // Verdunkle Hintergrund
        background.render(g);

        // Zeichne Tilemap
        tileMap.render(g);

        // Zeichne Spieler
        player.render(g);

        // Zeichne Inventar und Crafting
        inventory.render(g);
        crafting.render(g);
    }

    // KEYLISTENERS
    @Override
    public void keyPressed(KeyEvent e)
    {
        player.keyPressed(e);
        inventory.keyPressed(e);
        crafting.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) { player.keyReleased(e); }

    // MOUSELISTENERS
    @Override
    public void mouseClicked(MouseEvent e)
    {
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
    public void mouseWheelMoved(MouseWheelEvent e) { inventory.mouseWheelMoved(e); }

    @Override
    public void mouseMoved(MouseEvent e) { tileMap.mouseMoved(e); }

    // GETTER UND SETTER
    /**
     * getPlayer        Rueckgabe des Spielers
     * @return Player   Spieler
     * */
    public Player getPlayer() { return this.player; }

}
