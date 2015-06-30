package State.Level;


import Assets.Crafting.Crafting;
import Assets.GameObjects.Multiplayer.MPPlayer;
import Assets.GameObjects.Player;
import Assets.Inventory.Inventory;
import Assets.World.Tile;
import Assets.World.TileMap;
import Main.CustomFont;
import Main.GamePanel;
import Main.References;
import Main.ResourceLoader;
import State.Multiplayer.LobbyState;
import State.State;
import State.StateManager;

import javax.imageio.ImageIO;
import javax.swing.*;
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

    public static boolean goldRushDone = false;

    private boolean continueLevel;

    // Hintergrundbilder - Pfad
    private Image backgroundImage;
    private Image statusbarImage;
    private Image healthImage;
    private String level1DayBackgroundPath = "/img/grassbg1.jpg";



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
        TileMap.ownPlayerInstance = myPlayer;

        // Inventory
        this.inventory = new Inventory();

        // Crafting
        this.crafting = new Crafting();

        statusbarImage = new ImageIcon("res/img/Menu/statusbar.png").getImage();
        healthImage = new ImageIcon("res/img/Health/heart.png").getImage();

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


        tileMap.render(g);

        for (MPPlayer p : players) {
            p.render(graphics);
        }

        // Anzeigeleiste zeichnen
       // g.drawImage(statusbarImage, 0, 0, null);
        //myPlayer.renderStatusbar(g);

        if(goldRushDone){

            g.setColor(Color.RED);
            g.setFont(CustomFont.createCustomFont("Munro.ttf",18f));
            g.drawString("Spiel vorbei", References.SCREEN_WIDTH/2-50, References.SCREEN_HEIGHT/2 -50);
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

    /**
     * goldRushWon      Überpruft, ob das Spiel "Goldrush" gewonnen wurde
     * */

    private void goldRushWon() {
        if(LobbyState.goldRushSelected == true && clientId == 1) {
            for (int i = 0; i < inventory.invBar.length; i++) {
                if (inventory.invBar[i].name.equals("Gold")) {
                    //LobbyState.pw.write(LobbyState.playerName +" hat das spiel gewonnen \n ");
                    //LobbyState.pw.write("rmPl " +LobbyState.playerName +"\n");
                    if(inventory.invBar[i].count == 5) {
                        LobbyState.pw.println(LobbyState.playerName + " hat verloren!");
                        goldRushDone = true;
                    }
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
        goldRushWon();
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
