package State.Level;


import Assets.Crafting.Crafting;
import Assets.GameObjects.Multiplayer.MPPlayer;
import Assets.GameObjects.Player;
import Assets.Inventory.Inventory;
import Assets.World.Tile;
import Assets.World.TileMap;
import Main.GamePanel;
import Main.ScreenDimensions;
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
    private ArrayList<MPPlayer> players;
    private MPPlayer myPlayer;

    /*
    *
    * */
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

        System.out.println("MYPlaeryers Size" + players.size());
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
        tileMap = players.get(0).getTileMap();
        tileMap.setPosition(0, 0);

        // Level starten
        tileMap.generateMap(ScreenDimensions.HEIGHT / 100);

        // Positioniere Spieler
        for (MPPlayer p : players) {
            p.setTileMap(tileMap);
            p.setPosition(
                    tileMap.numberOfColumns*Tile.WIDTH / 2,
                    0
            );
        }

    }

    /*
    * update
    * */
    @Override
    public void update() {
        tileMap.setPosition(ScreenDimensions.WIDTH / 2 - myPlayer.getX(), ScreenDimensions.HEIGHT / 2 - myPlayer.getY());

        myPlayer.update();
        sendMove();

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
            graphics.drawImage(backgroundImage, 0, 0, ScreenDimensions.WIDTH, ScreenDimensions.HEIGHT, null);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

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
                        //System.out.println("** Line from Server was: " + line);

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
    public Player getPlayer() { return this.myPlayer; }

}