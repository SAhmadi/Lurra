package Assets.GameObjects;

import Assets.Assets;
import Assets.Inventory.Inventory;
import Assets.World.TileMap;
import GameSaves.GameData.GameData;
import Main.Sound;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

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

    // weitere Eigenschaften
    private int health = 100;
    private int range;
    private double damage = 0.5;
    private ArrayList<Weapon> weaponList;
    public static int currentWeapon;
    private Armor armor;

    double renderX;
    double renderY;

    double bulletPosX= 40;
    double bulletPosY =0;
    private BufferedImage skin;
    public static boolean rpgActivated;


    /**
     *
     * Player                       Konstruktor der Player Klasse
     * @param width                 Breite des Bildes
     * @param height                Hoehe des Bildes
     * @param widthForCollision     Breite des Kollisionsrechteckes
     * @param heightForCollision    Hoehe des Kollisionsrechteckes
     * @param velocityX             Geschwindigkeit auf der x-Achse
     * @param velocityY             Geschwindigkeit auf der y-Achse
     * @param maxVelocityX          Maximalgeschwindigkeit auf der x-Achse
     * @param maxVelocityY          Maximalgeschwindigkeit auf der y-Achse*
     * */
    public Player(int width, int height, int widthForCollision, int heightForCollision,
                  double velocityX, double velocityY, double maxVelocityX, double maxVelocityY, TileMap tileMap) {

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

        // Waffen
        weaponList = new ArrayList<>();
        currentWeapon = -1;
        weaponList.add(new Weapon(this, 2, Weapon.WEAPON_TYPE_1));
        weaponList.add(new Weapon(this, 5, Weapon.WEAPON_TYPE_2));
        weaponList.add(new Weapon(this, 3, Weapon.WEAPON_TYPE_3));
        weaponList.add(new Weapon(this, 15, Weapon.WEAPON_TYPE_4));
        weaponList.add(new Weapon(this, 5, Weapon.WEAPON_TYPE_5));
        weaponList.add(new Weapon(this, 7, Weapon.WEAPON_TYPE_6));

        tileMap.setDamage(damage);

        armor = new Armor(this, 100);


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
        if(currentWeapon > -1)
            weaponList.get(currentWeapon).update();
        if(armor != null)
            armor.update();
    }

    /*
    * render - Darstellung der Ver�nderungen
    *
    * @param g  - Graphics Objekt
    * */
    @Override
    public void render(Graphics g) {
        // Zeichnnen des Inventory
        //drawInventory(g);

        // Zeichnen auf der TileMap
        super.setOnMap();
        if(super.movingLeft)
            g.drawImage(animation.getActiveFrameImage(), (int)(x + xOnMap - width/2 + width), (int)(y + yOnMap - height/2), -width, height, null);
        else
            g.drawImage(animation.getActiveFrameImage(), (int) (x + xOnMap - width / 2), (int) (y + yOnMap - height / 2), null);
        if(currentWeapon > -1)
            weaponList.get(currentWeapon).render(g);
        if(armor != null)
            armor.render(g);

        if(currentWeapon == 3 && rpgActivated) {
            g.drawImage(skin, (int)(bulletPosX + x + xOnMap - width/2 + width), (int)( bulletPosY + y + yOnMap - height/2), -width, height, null);
        }


    }

    /*
    * KeyListener - Tastatureingaben
    * */
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_D) {
            super.movingRight = true;

        }
        if(e.getKeyCode() == KeyEvent.VK_A)
            super.movingLeft = true;

        if(e.getKeyCode() == KeyEvent.VK_W)
            super.jumping = true;

        if(e.getKeyCode() == KeyEvent.VK_B) {
            rpgActivated = true;
            shoot(true);

        }

        if(e.getKeyCode() == KeyEvent.VK_E) {
            currentWeapon++;
            if(currentWeapon >= weaponList.size()) {
                currentWeapon = -1;
            }
            if(currentWeapon > -1)
                tileMap.setDamage(weaponList.get(currentWeapon).getDamage() + damage);
            else
                tileMap.setDamage(damage);
            //Schaden auf Block anwenden

        }
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

        if(e.getKeyCode()==KeyEvent.VK_B){
            rpgActivated = false;
            bulletPosX = 40;
            bulletPosY = 0;
            update();
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

    public double shoot(boolean activate) {
        if(activate) {
            renderX = 1;
            renderY = 1;
            if (rpgActivated && currentWeapon == 3) {
                if(GameData.isSoundOn.equals("On")) {
                    Sound.boomSound.play();
                }
                try {
                    skin = ImageIO.read(Player.class.getResourceAsStream("/img/Weapons/weapon_bullet.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < 20; i++) {
                    bulletPosX += 0.2;
                    super.collisionWithTileMap();
                    if (bulletPosX <= 149.40000000000003){
                        bulletPosY -= 0.2;
                    } else {
                        bulletPosY += 0.2;
                    }
                }
            }


        } else {
            renderX = 0;
            renderY = 0;
        }
        return damage;
    }

    /*
    * move - Bewegen des Objekts
    * */
    private void move() {
        if(!Inventory.isDrawerOpen) {
            if(super.movingLeft) {
                if(directionX < -velocityX)
                {
                    if(GameData.isSoundOn.equals("On")) {
                    Sound.walkSound.play();
                }

                    directionX = -2*maxVelocityX;
                }
                else
                {
                    directionX -= 2*velocityX;
                }
            }
            else if(super.movingRight) {
                if(directionX > velocityX) {
                    if(GameData.isSoundOn.equals("On")) {
                        Sound.walkSound.play();
                    }

                    directionX = 2*maxVelocityX;
                }
                else
                    directionX += 2*velocityX;
            }

            if(super.jumping) {
                falling = true;

                if(directionY > maxJumpVelocity) {

                    if (GameData.isSoundOn.equals("On")) {
                        Sound.jumpSound.play();
                    }
                    directionY = maxJumpVelocity;
                } else
                    directionY = jumpVelocity;
            }

        }

        if(super.falling) {
            jumping = false;

            if(directionY > maxVelocityY)
                directionY = maxVelocityY;
            else
                directionY += velocityY;
        }
    }

    public void wasHit(int damage) {
        double dmg = armor.getAttacked(damage);
        if(dmg > 0){
            armor = null;
            health -= dmg;
            if((health -= dmg) <= 0) {
                //Spieler tot
                System.out.println("Spieler tot");
            }
        }

    }
}
