package State;

import Assets.Assets;
import Assets.Tile;
import Assets.TileMap;
import Main.GamePanel;
import Main.ScreenDimensions;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

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
    private String level1MapFilePath = "res/maps/level1Map.txt";
    private Player player;
    public Image playerImage;

    /*
    * Tile
    * */
    // Ausgewaehltes-Tile dazugehoerendes Rechteck
    private Rectangle tileSelectedBounds;
    // Ausgewaehltes Tile in der Schleife
    private Tile selectedTile;


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
        try {
            playerImage = ImageIO.read(getClass().getResourceAsStream("/img/player_still_right.png"));
        } catch ( IOException e) {
            e.printStackTrace();
        }
        player = new Player(playerImage, ScreenDimensions.WIDTH/2, ScreenDimensions.HEIGHT/2);
        level1Assets = GamePanel.tileAssets;
        tileMap = new TileMap(level1Assets, level1MapFilePath);
        tileMap.loadMap();

    }

    /*
    * update
    * */
    @Override
    public void update() {
        player.update();
    }

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
        // Mauszeiger Klickpunkt
        Point point = e.getPoint();

        // Anzahl der Tiles in der TileMap
        int tileMapSize = tileMap.getTiles().size();


        for(int i = 0; i < tileMapSize; i++) {
            selectedTile = tileMap.getTiles().get(i);

            // Setze Rechteck mit den selben Maßen und Koordinaten wie ausgewaehlter Tile
            tileSelectedBounds = new Rectangle(selectedTile.getX(), selectedTile.getY(), Tile.WIDTH, Tile.HEIGHT);

            // Wenn Maus-Klickpunkt im Rechteck liegt
            if(tileSelectedBounds.contains(point)) {
                if(selectedTile.isDestructable) {
                    tileMap.getTiles().remove(selectedTile);    // Entfernen aus der Liste
                }
            }
            // Aktualisieren der ListGroesse , da Elemente geloescht werden
            tileMapSize = tileMap.getTiles().size();
        }
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
