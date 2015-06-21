package State.Level;


import Assets.Crafting.Crafting;
import Assets.GameObjects.Multiplayer.MPPlayer;
import Assets.GameObjects.Player;
import Assets.Inventory.Inventory;
import Assets.World.Tile;
import Assets.World.TileMap;
import Main.GamePanel;
import Main.References;
import State.Multiplayer.LobbyState;
import State.State;
import State.StateManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.IOException;
import java.util.ArrayList;

/*
* Level1State - Erstes Level
* */
public class MPLevelState extends State {

    // Inhaltsflaeche, Graphics-Obj und Zustands-Manger
    protected GamePanel gamePanel;
    protected Graphics graphics;
    protected StateManager stateManager;

    private boolean continueLevel;

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
    public TileMap tileMap;
    private String levelMapPath = "res/xml/playerLevelSaves/";

    /*
    * Tile
    * */
    private Rectangle tileRectangle;

    /*
    * Player Objekt
    * */
    private ArrayList<MPPlayer> players;
    private MPPlayer myPlayer;
    private int clientId;

    /*
    * Inventory and Crafting
    * */
    public Inventory inventory;
    public Crafting crafting;

    /*
    * Konstruktor - Initialisieren
    * */
    public MPLevelState(Graphics graphics, GamePanel gamePanel, StateManager stateManager, ArrayList<MPPlayer> mpPlayers, int clientId, boolean isSpectator) {
        this.gamePanel = gamePanel;
        this.graphics = graphics;
        this.stateManager = stateManager;
        this.players = mpPlayers;
        this.clientId = clientId;

        for (MPPlayer p : players) {
            if (p.playerID == clientId-1) {
                this.myPlayer = p;
            }
        }

        // Inventory
        this.inventory = new Inventory();

        // Crafting
        this.crafting = new Crafting();

        init();
    }

    /*
    * Eigentliches Initialisieren
    * */
    @Override
    public void init() {
        tileMap = myPlayer.getTileMap();
        tileMap.setPosition(0, 0);

        // Positioniere Spieler
        for (MPPlayer p : players) {
            p.setPosition(
                    References.SCREEN_WIDTH / 2,
                    References.SCREEN_HEIGHT / 2 - 2*p.getHeight()
            );
        }
    }

    /*
    * update
    * */
    @Override
    public void update() {

        myPlayer.update();
        sendMove();

        tileMap.setPosition(References.SCREEN_WIDTH / 2 - myPlayer.getX(), References.SCREEN_HEIGHT / 2 - myPlayer.getY());
        tileMap.update();

        multiplayerThread();

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

        for (MPPlayer p : players) {
            p.render(graphics);
        }

        inventory.render(g);
        crafting.render(g);
    }

    /**
     *
     * */
    public void multiplayerThread() {
        new Thread() {

            @Override
            public void run() {
                String line;
                try {
                    while ((line = LobbyState.br.readLine()) != null) {
                        System.out.println("** Line from Server was: " + line);

                        receiveMove(line);
                    }
                }
                catch (IOException ioe) {
                }
            }

        }.start();
    }

    /**
     *
     * */
    private void sendMove() {
        LobbyState.pw.println("plyMove:" + myPlayer.playerID + ":" + myPlayer.getX() + ":" + myPlayer.getY());

    }

    /**
     *
     * */
    private void receiveMove(String line) {
        if (line.contains("plyMove")) {
            int id = Integer.parseInt(line.split(":")[1]);
            double x = Double.parseDouble(line.split(":")[2]);
            double y = Double.parseDouble(line.split(":")[3]);

            for (MPPlayer p : players) {
                if (p.playerID == id) {
                    p.setPosition(x, y);
                }
            }
        }
    }

    /*
    * EventListener
    * */
    @Override
    public void keyPressed(KeyEvent e) {
        myPlayer.keyPressed(e);
        inventory.keyPressed(e);
        crafting.keyPressed(e);
    }
    @Override
    public void keyReleased(KeyEvent e) {
        myPlayer.keyReleased(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Tile selectedTile = tileMap.getMap().get(new Point((int) ((e.getY() - tileMap.getY()) / References.TILE_SIZE), (int) (Math.floor((e.getX() - tileMap.getX()) / References.TILE_SIZE))));
        myPlayer.mouseClicked(e, selectedTile);

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

    // GETTER UND SETTER
    public Player getPlayer() { return this.myPlayer; }

}
