package Assets.GameObjects;

import Assets.Assets;
import Assets.TileMap;
import Assets.Tile;
import Main.ScreenDimensions;
import javafx.stage.Screen;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.reflect.Array;

/**
 *
 * Spielfigure des Spiels
 * Player -     Spieler-Objekt
 *
 * */
public class Player extends GameObject {

    // Assets
    private Assets playerAssets;
    private String playerAssetsResPath = "/img/playerSet.gif";
    private int playerAssetsNumberOfRows = 3;

    // Animation
    private ArrayList<BufferedImage[]> frames;
    private final int[] frameNumber = {2, 3, 2};

    // Animation ID
    private static int still = 0;
    private static int walk = 1;
    private static final int jump = 2;

    // Sprunggeschwindigkeit
    private int jumpVelocity = -10;
    private int maxJumpVelocity = -40;

    // Inventar
    int[][] inv  = new int[10][2];
    private ArrayList<InventoryItem> inventory = new ArrayList<InventoryItem>();
    public static Player currentPlayer;
    private int inventoryBoxWidth = 500;
    private int inventoryBoxHeight = 50;

    /**
     *
     * Player -                     Konstruktor der Player Klasse
     * @param width                 Breite des Bildes
     * @param height                Hoehe des Bildes
     * @param widthForCollision     Breite des Kollisionsrechteckes
     * @param heightForCollision    Hoehe des Kollisionsrechteckes
     * @param velocityX             Geschwindigkeit auf der x-Achse
     * @param velocityY             Geschwindigkeit auf der y-Achse
     * @param maxVelocityX          Maximalgeschwindigkeit auf der x-Achse
     * @param maxVelocityY          Maximalgeschwindigkeit auf der y-Achse
     * @param tileMap               TileMap mit dem der Spieler interagiert
     *
     * */
    public Player(int width, int height, int widthForCollision, int heightForCollision,
                  double velocityX, double velocityY, double maxVelocityX, double maxVelocityY,
                  TileMap tileMap) {

        super(width, height, widthForCollision, heightForCollision, velocityX, velocityY, maxVelocityX, maxVelocityY, tileMap);

        // Initilisieren Inventory
        this.currentPlayer = this;

        // Initialisieren Assets
        this.playerAssets = new Assets(playerAssetsResPath);

        // Laden des PlayerSet
        loadPlayerSet();

        // Initialisieren der Animation
        animation = new Animation();
        activeAnimation = Player.still;
        animation.init(frames.get(Player.still));
        animation.setFrameHoldTime(800);
    }

    /*
    * update - Spieldaten, Spielphysik
    * */
    @Override
    public void update() {
        // Update Bewegungen
        move();

        // Update Kollision mit der TileMap
        super.collisionWithTileMap();

        // Update Position relativ zu TileMap
        super.setPosition(xTmp, yTmp);

        // Aktive Animation Initialisieren
        if(movingLeft || movingRight) {
            if(activeAnimation != Player.walk) {
                width = 43;
                activeAnimation = Player.walk;
                animation.init(frames.get(Player.walk));
                animation.setFrameHoldTime(40);

            }
        }
        else if(directionY < 0) {
            if(activeAnimation != Player.jump) {
                width = 43;
                activeAnimation = Player.jump;
                animation.init(frames.get(Player.jump));
                animation.setFrameHoldTime(-1);
            }
        }
        else {
            if(activeAnimation != Player.still) {
                width = 43;
                activeAnimation = Player.still;
                animation.init(frames.get(Player.still));
                animation.setFrameHoldTime(400);
            }
        }

        // Update Animation
        animation.update();
    }

    /*
    * render - Darstellung der Veränderungen
    *
    * @param g  - Graphics Objekt
    * */
    @Override
    public void render(Graphics g) {
        // Zeichnnen des Inventory
        drawInventory(g);

        // Zeichnen auf der TileMap
        super.setOnMap();
        if(super.movingLeft)
            g.drawImage(animation.getActiveFrameImage(), (int)(x + xOnMap - width/2 + width), (int)(y + yOnMap - height/2), -width, height, null);
        else
            g.drawImage(animation.getActiveFrameImage(), (int)(x + xOnMap - width/2), (int)(y + yOnMap - height/2), null);
    }

    /*
    * KeyListener - Tastatureingaben
    * */
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_D)
            super.movingRight = true;

        if(e.getKeyCode() == KeyEvent.VK_A)
            super.movingLeft = true;

        if(e.getKeyCode() == KeyEvent.VK_W)
            super.jumping = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_D) {
            super.movingRight = false;
            directionX = 0;
        }

        if(e.getKeyCode() == KeyEvent.VK_A) {
            super.movingLeft = false;
            directionX = 0;
        }

        if(e.getKeyCode() == KeyEvent.VK_W) {
            super.jumping = false;
        }
    }

    /*
    * loadPlayerSet - Laden des PlayerSets
    * */
    private void loadPlayerSet() {
        try {
            // Bildsequenz Liste
            frames = new ArrayList<BufferedImage[]>();

            // Durchlaufe Zeilen
            for(int row = 0; row < playerAssetsNumberOfRows; row++) {
                // Speichere eine ganze Zeile
                BufferedImage[] frame = new BufferedImage[ frameNumber[row] ];

                // Durchlaufe einzelne Bilder einer Zeile
                for(int column = 0; column < frameNumber[row]; column++)
                    frame[column] = playerAssets.getSubimage(column*width, row*height, width, height);

                frames.add(frame);
            }
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    /*
    * move - Bewegen des Objekts
    * */
    private void move() {
        if(super.movingLeft) {
            directionX -= velocityX;
            if(directionX < -velocityX) {
                directionX = -maxVelocityX;
            }
        }
        else if(super.movingRight) {
            if(directionX > velocityX) {
                directionX = maxVelocityX;
            }
            else
                directionX += velocityX;
        }

        if(super.jumping) {
            falling = true;

            if(directionY > maxJumpVelocity)
                directionY = maxJumpVelocity;
            else
                directionY += jumpVelocity;
        }

        if(super.falling) {
            jumping = false;

            if(directionY > maxVelocityY)
                directionY = maxVelocityY;
            else
                directionY += velocityY;
        }
    }

    /**
     *
     * addToInventory -     Hinzufuegen eines Tiles zum Inventar
     * @param tile          Tile, welches zum Inventar hinzugefuegt werden soll
     *
     * */
    public void addToInventory(Tile tile) {
        boolean found = false;
        for(InventoryItem item : inventory) {
            if(item.name.equals(tile.getClass().getSimpleName())) {
                item.count++;
                found = true;
                break;
            }
        }
        if(!found)
            inventory.add(new InventoryItem(tile.getClass().getSimpleName()));
    }

    /**
     *
     * drawInventar -       Zeichnen des Inventar
     * @param g             Graphics-Objekt
     *
     * */
    private void drawInventory(Graphics g){
        final int displayable_count = 10;
        int x = 0;


        g.setColor(Color.WHITE);
        g.fillRect(
                ScreenDimensions.WIDTH/2 - inventoryBoxWidth/2,
                ScreenDimensions.HEIGHT - 2*inventoryBoxHeight,
                inventoryBoxWidth,
                inventoryBoxHeight
        );

        g.setColor(Color.black);
        // g.drawRect(o * 50 + 400, 500, 50, 50);

        for(InventoryItem item : inventory) {
            g.drawString(
                    item.name,
                    ScreenDimensions.WIDTH/2 - inventoryBoxWidth/2 +20 + x,
                    ScreenDimensions.HEIGHT - 2*inventoryBoxHeight +10
            );

            g.drawString(
                    Integer.toString(item.count),
                    ScreenDimensions.WIDTH/2 - inventoryBoxWidth/2 +20 + x,
                    ScreenDimensions.HEIGHT - 2*inventoryBoxHeight +20
            );

            x+=50;
        }

//        inv[2][0] = 1;
//        inv[2][1] = 1;
//        // g.drawRect(10, 10,10,10);
//        int i = inv.length;
//        int o = 0;
//
//
//        while(o < i){
//            g.setColor(Color.white);
//            g.fillRect(o*50+400,500,50,50);
//            g.setColor(Color.black);
//            g.drawRect(o * 50 + 400, 500, 50, 50);
//
//
//
//            if (inv[o][0] == 0) {
//                g.setColor(Color.black);
//                g.drawString(Integer.toString(inv[o][1]),o * 50 + 422, 537);
//            }
//
//            if (inv[o][0] == 1) {
//                g.setColor(Color.yellow);
//                g.fillRect(o * 50 + 402, 502, 47, 47);
//                g.setColor(Color.black);
//                g.drawString(Integer.toString(inv[o][1]),o * 50 + 422, 537);
//            }
//            g.setColor(Color.black);
//            g.drawString(Integer.toString(o+1) ,o * 50 + 412, 517);
//            o++;
//        }
    }

    /**
     *
     * Definition eines Inventar-Elements
     * InventoryItem -      Inventar-Elemente
     *
     * */
    private class InventoryItem {
        String name;
        int count;
        //BufferedImage texture;

        /**
         *
         *  InventoryItem -         Konstruktor der Klasse InventoryItem
         *  @param name             Name des Inventories
         *
         * */
        public InventoryItem(String name) {
            this.name = name;
            this.count = 1;
        }
    }

}
