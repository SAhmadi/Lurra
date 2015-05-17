package Assets.GameObjects;

import Assets.Assets;
import Assets.TileMap;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/*
* Player - Spieler
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

    public Player(int width, int height, int widthForCollision, int heightForCollision,
                  double velocityX, double velocityY, double maxVelocityX, double maxVelocityY,
                  TileMap tileMap) {

        super(width, height, widthForCollision, heightForCollision, velocityX, velocityY, maxVelocityX, maxVelocityY, tileMap);

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

}
