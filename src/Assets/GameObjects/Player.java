package Assets.GameObjects;

import Assets.Assets;
import Assets.Inventory.Inventory;
import Assets.World.Tile;
import Assets.World.TileMap;
import GameSaves.GameData.GameData;
import Main.References;
import Main.ResourceLoader;
import Main.Sound;
import State.Level.Level1State;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Spielfigure des Spiels
 * Player -     Spieler-Objekt
 */
public class Player extends GameObject {

    // Assets
    private Assets playerAssets;
    private String playerAssetsResPath = "/img/playerSet.png";
    private int playerAssetsNumberOfRows = 10;

    // Animation
    private ArrayList<BufferedImage[]> frames;
    private final int[] frameNumber = {2, 9, 1, 1, 2, 2, 2, 4, 3, 1};

    // Animation ID
    private static final int STILL = 0;
    private static final int WALK = 1;
    private static final int JUMP = 2;
    private static final int FALL = 3;
    private static final int AXE_NORMAL = 4;
    private static final int PICK_NORMAL = 5;
    private static final int HAMMER_NORMAL = 6;
    private static final int SWORD_NORMAL = 7;
    private static final int BOW_NORMAL = 8;
    private static final int GUN_NORMAL = 9;


    // Sprunggeschwindigkeit
    //private int jumpVelocity = -10;
    //private int maxJumpVelocity = -10;

    // weitere Eigenschaften
    public static boolean isAxeHit;
    public static boolean isPickHit;
    public static boolean isHammerHit;
    public static boolean isGunHit;

    public static int health;
    private static int maxHealth = 100;
    private static int maxPower = 100;
    private int range;
    public static ArrayList<Weapon> weaponList = new ArrayList<Weapon>();
    private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    //private ArrayList<Armor> armorList;

    private static Image healthImage = new ImageIcon("res/img/Health/heart.png").getImage();

    private int currentWeaponID;
    private int armorID;
    private boolean wearsArmor;

    private int level;
    private int ep;


    /**
     * Player                       Konstruktor der Player Klasse
     *
     * @param width              Breite des Bildes
     * @param height             Hoehe des Bildes
     * @param widthForCollision  Breite des Kollisionsrechteckes
     * @param heightForCollision Hoehe des Kollisionsrechteckes
     * @param velocityX          Geschwindigkeit auf der x-Achse
     * @param velocityY          Geschwindigkeit auf der y-Achse
     * @param maxVelocityX       Maximalgeschwindigkeit auf der x-Achse
     * @param maxVelocityY       Maximalgeschwindigkeit auf der y-Achse*
     */
    public Player(int width, int height, int widthForCollision, int heightForCollision,
                  double velocityX, double velocityY, double maxVelocityX, double maxVelocityY, TileMap tileMap) {

        super(width, height, widthForCollision, heightForCollision, velocityX, velocityY, maxVelocityX, maxVelocityY, tileMap);

        // Spielerwerte setzen
        this.health = maxHealth = 40;
        level = 1;
        ep = 0;

        // Initialisieren Assets
        this.playerAssets = new Assets(playerAssetsResPath);

        // Laden des PlayerSet
        loadPlayerSet();

        // Standard Waffen
        weaponList.add(new Weapon(ResourceLoader.stonePick, "Picke", Weapon.PICKE_ID, Weapon.PICKE_DAMAGE, Weapon.PICKE_RANGE));
        weaponList.add(new Weapon(ResourceLoader.axe, "Axt", Weapon.AXE_ID, Weapon.AXE_DAMAGE, Weapon.AXE_RANGE));
        weaponList.add(new Weapon(ResourceLoader.hammer, "Hammer", Weapon.HAMMER_ID, Weapon.HAMMER_DAMAGE, Weapon.HAMMER_RANGE));
        weaponList.add(new Weapon(ResourceLoader.gunPurple, "Schleimpistole", Weapon.PURPLE_GUN_ID, Weapon.PURPLE_GUN_DAMAGE, Weapon.PURPLE_GUN_RANGE));

        // Initialisieren der Animation
        animation = new Animation();
        activeAnimation = Player.STILL;
        animation.init(frames.get(Player.STILL));
        animation.setFrameHoldTime(200);
    }

    public static int getMaxHealth() {
        return maxHealth;
    }

    public static int getMaxPower() {
        return maxPower;
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

        if (activeAnimation == Player.PICK_NORMAL)
            if (animation.getWasPlayed()) isPickHit = false;

        if (activeAnimation == Player.AXE_NORMAL)
            if (animation.getWasPlayed()) isAxeHit = false;

        if (activeAnimation == Player.HAMMER_NORMAL)
            if (animation.getWasPlayed()) isHammerHit = false;

        if (activeAnimation == Player.GUN_NORMAL)
            if (animation.getWasPlayed()) isGunHit = false;


        // Aktive Animation Initialisieren
        if (movingLeft || movingRight) {
            if (activeAnimation != Player.WALK) {
                width = 22;
                height = 41;

                activeAnimation = Player.WALK;
                animation.init(frames.get(Player.WALK));
                animation.setFrameHoldTime(40);
            }
        } else if (directionY < 0) {
            if (activeAnimation != Player.JUMP) {
                width = 22;
                height = 41;

                activeAnimation = Player.JUMP;
                animation.init(frames.get(Player.JUMP));
                animation.setFrameHoldTime(-1);
            }
        } else if (directionY > 0) {
            if (activeAnimation != Player.FALL) {
                width = 22;
                height = 41;

                activeAnimation = Player.FALL;
                animation.init(frames.get(Player.FALL));
                animation.setFrameHoldTime(-1);
            }
        } else if (isPickHit) {
            if (activeAnimation != Player.PICK_NORMAL) {
                width = 34;
                height = 41;

                activeAnimation = Player.PICK_NORMAL;
                animation.init(frames.get(Player.PICK_NORMAL));
                animation.setFrameHoldTime(90);
            }
        } else if (isAxeHit) {
            if (activeAnimation != Player.AXE_NORMAL) {
                width = 34;
                height = 41;

                activeAnimation = Player.AXE_NORMAL;
                animation.init(frames.get(Player.AXE_NORMAL));
                animation.setFrameHoldTime(90);
            }
        } else if (isHammerHit) {
            if (activeAnimation != Player.HAMMER_NORMAL) {
                width = 34;
                height = 41;

                activeAnimation = Player.HAMMER_NORMAL;
                animation.init(frames.get(Player.HAMMER_NORMAL));
                animation.setFrameHoldTime(90);
            }
        } else if (isGunHit) {
            if (activeAnimation != Player.GUN_NORMAL) {
                width = 34;
                height = 41;

                activeAnimation = Player.GUN_NORMAL;
                animation.init(frames.get(Player.GUN_NORMAL));
                animation.setFrameHoldTime(70);
            }
        } else {
            if (activeAnimation != Player.STILL) {
                width = 22;
                height = 41;

                activeAnimation = Player.STILL;
                animation.init(frames.get(Player.STILL));
                animation.setFrameHoldTime(400);
            }
        }

        // Update Animation
        animation.update();

        // Update Bullets
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).update();

            if (bullets.get(i).shouldRemove()) {
                bullets.remove(i);
                i--;
            }
        }
    }

    /*
    * render - Darstellung der Veraenderungen
    *
    * @param g  - Graphics Objekt
    * */
    @Override
    public void render(Graphics g) {
        // Zeichnnen des Inventory
        //drawInventory(g);

        // Zeichnen auf der TileMap
        super.setOnMap();
        if (!super.isFacingRight)
            g.drawImage(animation.getActiveFrameImage(), (int) (x + xOnMap - width / 2 + width), (int) (y + yOnMap - height / 2), -width, height, null);
        else
            g.drawImage(animation.getActiveFrameImage(), (int) (x + xOnMap - width / 2), (int) (y + yOnMap - height / 2), null);


        // Zeichnen der Bullets
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).render(g);
        }
    }

    public void renderStatusbar(Graphics g) {
        // Leben
        for(int i = 1; i <= 10; i++) {
            if(i*10 <= getHealth()) {
                g.drawImage(healthImage, (i-1) * 35 + 10, 5, null);
            } else
                break;
        }

        // Level & EP
        g.drawString("Level: " + level + " | EP: " + ep, References.SCREEN_WIDTH - 250, 25);


    }




    /*
    * KeyListener - Tastatureingaben
    * */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_D) {
            super.movingRight = true;

        }
        if (e.getKeyCode() == KeyEvent.VK_A)
            super.movingLeft = true;

        if (e.getKeyCode() == KeyEvent.VK_W) {
            if (GameData.isSoundOn.equals("On"))
                Sound.jumpSound.play();

            super.jumping = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_E) {
            Level1State.consume();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_D) {
            super.movingRight = false;
            directionX = 0;
        }

        if (e.getKeyCode() == KeyEvent.VK_A) {
            super.movingLeft = false;
            directionX = 0;
        }

        if (e.getKeyCode() == KeyEvent.VK_W) {
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
            for (int row = 0; row < playerAssetsNumberOfRows; row++) {
                // Speichere eine ganze Zeile
                BufferedImage[] frame = new BufferedImage[frameNumber[row]];

                // Durchlaufe einzelne Bilder einer Zeile
                if (row >= 4)   // Angriffs-Bilder sind groesser als die normalen
                {
                    for (int column = 0; column < frameNumber[row]; column++) {
                        frame[column] = playerAssets.getSubimage(column * 34, row * 41, 34, 41);
                    }
                } else {
                    for (int column = 0; column < frameNumber[row]; column++) {
                        frame[column] = playerAssets.getSubimage(column * width, row * height, width, height);
                    }
                }

                frames.add(frame);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /*
    * move - Bewegen des Objekts
    * */
    private void move() {
        if (!Inventory.isDrawerOpen) {
            if (super.movingLeft) {
                super.isFacingRight = false;

                if (directionX < -velocityX)
                    directionX = -maxVelocityX;
                else
                    directionX -= velocityX;
            } else if (super.movingRight) {
                super.isFacingRight = true;

                if (directionX > velocityX)
                    directionX = maxVelocityX;
                else
                    directionX += velocityX;
            }

            if (super.jumping && !super.falling) {
                directionY = 2 * maxVelocityY;
                falling = true;
            }

        }

        if (super.falling) {
            if (directionY > -maxVelocityY)
                directionY = -maxVelocityY;
            else
                directionY -= velocityY;

            if (directionY > 0)
                super.jumping = false;
        }
    }

    /**
     *
     * */
    public void mouseClicked(MouseEvent e, Tile tile) {
        if (Arrays.asList(TileMap.dirtTextures).contains(tile.getTexture()) && Inventory.invBar[Inventory.selected].name.equals("Picke")) {
            isAxeHit = false;
            isHammerHit = false;
            isGunHit = false;
            isPickHit = true;
        } else if (Arrays.asList(TileMap.treeOnlyTextures).contains(tile.getTexture()) && Inventory.invBar[Inventory.selected].name.equals("Axt")) {
            isPickHit = false;
            isHammerHit = false;
            isGunHit = false;
            isAxeHit = true;
        } else if (Arrays.asList(TileMap.gemsTexture).contains(tile.getTexture()) && Inventory.invBar[Inventory.selected].name.equals("Hammer")) {
            isPickHit = false;
            isAxeHit = false;
            isGunHit = false;
            isHammerHit = true;
        } else if (Inventory.invBar[Inventory.selected].name.equals("Schleimpistole")) {

            if (GameData.isSoundOn.equals("On"))
                Sound.boomSound.play();

            isGunHit = true;

            Bullet bullet = new Bullet(
                    ResourceLoader.bulletGunPurple.getWidth(),
                    ResourceLoader.bulletGunPurple.getHeight(),
                    ResourceLoader.bulletGunPurple.getWidth(),
                    ResourceLoader.bulletGunPurple.getHeight(),
                    9,
                    1.4,
                    15,
                    2.5,
                    super.getTileMap(),
                    super.isFacingRight,
                    ResourceLoader.bulletGunPurple,
                    e.getPoint()
            );

            bullet.setPosition(this.x, this.y);
            bullet.setStartX();
            bullets.add(bullet);
        }

    }

    public int getHealth() {
        return health;
    }

    // Beim fortsetzen eines Spiels
    public void restoreValues(int health, int level, int ep) {
        this.health = height;
        this.level = level;
        this.ep = ep;
    }

    public void looseHealth(int health) {
        this.health = Math.max(this.health-health, 0);
        if(health == 0) {
            //tot
        }
    }

    public void incExperience(int ep) {
        this.ep += ep;
        while(this.ep >= level*500) {
            this.ep -= level * 500;
            level++;
        }
        maxHealth = health = 30 + level * 10;
    }
}