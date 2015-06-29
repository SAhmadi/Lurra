package Assets.GameObjects;

import Assets.Assets;
import Assets.Inventory.Inventory;
import Assets.World.Tile;
import Assets.World.TileMap;
import Main.ResourceLoader;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Spielfigur des Spiels
 *
 * @author Sirat
 * */
public class Player extends GameObject
{

    // Assets
    private Assets playerAssets;

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

    // weitere Eigenschaften
    private boolean isAxeHit;
    private boolean isPickHit;
    private boolean isHammerHit;
    private boolean isGunHit;

    private ArrayList<Weapon> weaponList = new ArrayList<>();
    private ArrayList<Bullet> bullets = new ArrayList<>();

    /**
     * Player                       Konstruktor der Player-Klasse
     *
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
                  double velocityX, double velocityY, double maxVelocityX, double maxVelocityY, TileMap tileMap)
    {
        super(width, height, widthForCollision, heightForCollision, velocityX, velocityY, maxVelocityX, maxVelocityY, tileMap);

        // Initialisieren Assets
        String playerAssetsResPath = "/img/playerSet.png";
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

    /**
     * update           Aktualisieren der Position und Animationen
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
        if(directionY < 0 || (directionY < 0 && movingLeft) || (directionY < 0 && movingRight)) {
            if(activeAnimation != Player.JUMP)
            {
                width = 22;
                height = 41;

                activeAnimation = Player.JUMP;
                animation.init(frames.get(Player.JUMP));
                animation.setFrameHoldTime(-1);
            }
        }
        else if(movingLeft || movingRight) {
            if(activeAnimation != Player.WALK)
            {
                width = 22;
                height = 41;

                activeAnimation = Player.WALK;
                animation.init(frames.get(Player.WALK));
                animation.setFrameHoldTime(40);
            }
        }
        else if(directionY > 0) {
            if(activeAnimation != Player.FALL)
            {
                width = 22;
                height = 41;

                activeAnimation = Player.FALL;
                animation.init(frames.get(Player.FALL));
                animation.setFrameHoldTime(-1);
            }
        }
        else if(isPickHit) {
            if(activeAnimation != Player.PICK_NORMAL)
            {
                width = 34;
                height = 41;

                activeAnimation = Player.PICK_NORMAL;
                animation.init(frames.get(Player.PICK_NORMAL));
                animation.setFrameHoldTime(90);
            }
        }
        else if(isAxeHit) {
            if(activeAnimation != Player.AXE_NORMAL)
            {
                width = 34;
                height = 41;

                activeAnimation = Player.AXE_NORMAL;
                animation.init(frames.get(Player.AXE_NORMAL));
                animation.setFrameHoldTime(90);
            }
        }
        else if(isHammerHit) {
            if(activeAnimation != Player.HAMMER_NORMAL)
            {
                width = 34;
                height = 41;

                activeAnimation = Player.HAMMER_NORMAL;
                animation.init(frames.get(Player.HAMMER_NORMAL));
                animation.setFrameHoldTime(90);
            }
        }
        else if(isGunHit) {
            if(activeAnimation != Player.GUN_NORMAL)
            {
                width = 34;
                height = 41;

                activeAnimation = Player.GUN_NORMAL;
                animation.init(frames.get(Player.GUN_NORMAL));
                animation.setFrameHoldTime(70);
            }
        }
        else {
            if(activeAnimation != Player.STILL)
            {
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
        for (int i = 0; i < bullets.size(); i++)
        {
            bullets.get(i).update();

            if (bullets.get(i).shouldRemove())
            {
                bullets.remove(i);
                i--;
            }
        }
    }

   /**
    * render        Zeichnen des Spielers und Geschosse
    *
    * @param g      Graphics Objekt
    * */
    @Override
    public void render(Graphics g)
    {
        // Zeichnen auf der TileMap
        super.setOnMap();
        if(!super.isFacingRight)
            g.drawImage(animation.getActiveFrameImage(), (int)(x + xOnMap - width/2 + width), (int)(y + yOnMap - height/2), -width, height, null);
        else
            g.drawImage(animation.getActiveFrameImage(), (int)(x + xOnMap - width/2), (int)(y + yOnMap - height/2), null);

        // Zeichnen der Bullets
        for (Bullet bullet : bullets) { bullet.render(g); }
    }

    /**
     * loadPlayerSet        Laden der Animationsbilder
     * */
    private void loadPlayerSet()
    {
        try
        {
            // Bildsequenz Liste
            frames = new ArrayList<>();

            // Durchlaufe Zeilen
            for(int row = 0; row < frameNumber.length; row++)
            {
                // Speichere eine ganze Zeile
                BufferedImage[] frame = new BufferedImage[frameNumber[row]];

                // Durchlaufe einzelne Bilder einer Zeile
                // Angriffs-Bilder sind groesser als die normalen
                if (row >= 4)
                    for(int column = 0; column < frameNumber[row]; column++) { frame[column] = playerAssets.getSubimage(column*34, row*41, 34, 41); }
                else
                    for(int column = 0; column < frameNumber[row]; column++) { frame[column] = playerAssets.getSubimage(column*width, row*height, width, height); }

                frames.add(frame);
            }
        } catch(Exception ex) { System.out.println("Error: " + ex.getMessage()); }
    }

    /**
     * move     Berechnen der Bewegung
     * */
    private void move()
    {
        if (!Inventory.isDrawerOpen)
        {
            if(super.movingLeft && !super.isInWater)
            {
                super.isFacingRight = false;

                if(directionX < -velocityX)
                    directionX = -maxVelocityX;
                else
                    directionX -= velocityX;
            }
            else if(super.movingRight && !super.isInWater)
            {
                super.isFacingRight = true;

                if(directionX > velocityX)
                    directionX = maxVelocityX;
                else
                    directionX += velocityX;
            }

            if(super.jumping && !super.falling && !super.isInWater)
            {
                directionY = 2*maxVelocityY;
                super.falling = true;
            }

            // Falls Spieler im Wasser ist, verlangsame seine Bewegungen
            if (super.isInWater && super.movingLeft)
            {
                super.isFacingRight = false;

                if(directionX < -velocityX)
                    directionX = -maxVelocityX/2;
                else
                    directionX -= velocityX/2;
            }
            else if(super.isInWater && super.movingRight)
            {
                super.isFacingRight = true;

                if(directionX > velocityX)
                    directionX = maxVelocityX/2;
                else
                    directionX += velocityX/2;
            }

            if(super.isInWater && super.jumping)
            {
                directionY = maxVelocityY - maxVelocityY/3;
                super.falling = true;
            }
        }

        if(super.falling && !super.isInWater)
        {
            if (directionY > -maxVelocityY)
                directionY = -maxVelocityY;
            else
                directionY -= velocityY;

            if (directionY > 0)
                super.jumping = false;
        }
        else if(super.isInWater && super.falling)
        {
            if (directionY > -maxVelocityY/2)
                directionY = -maxVelocityY/2;
            else
                directionY -= velocityY/2;

            if (directionY > 0)
                super.jumping = false;
        }
    }

    /**
     * mouseClicked     Klicken der linken Maustaste
     *
     * @param e         MausEvent Objekt
     * @param tile      Angeklicktes Tileobjekt
     * */
    public void mouseClicked(MouseEvent e, Tile tile)
    {
        if ((Arrays.asList(TileMap.dirtTextures).contains(tile.getTexture()) || Arrays.asList(TileMap.gemsTextures).contains(tile.getTexture())) && Inventory.invBar[Inventory.selected].name.equals("Picke"))
        {
            isPickHit = true;
            isAxeHit = false;
            isHammerHit = false;
        }
        else if (Arrays.asList(TileMap.treeOnlyTextures).contains(tile.getTexture()) && Inventory.invBar[Inventory.selected].name.equals("Axt"))
        {
            isAxeHit = true;
            isPickHit = false;
            isHammerHit = false;
        }
        else if (Arrays.asList(TileMap.gemsTextures).contains(tile.getTexture()) && Inventory.invBar[Inventory.selected].name.equals("Hammer"))
        {
            isHammerHit = true;
            isAxeHit = false;
            isPickHit = false;
        }
        else if (Inventory.invBar[Inventory.selected].name.equals("Schleimpistole"))
        {
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
                    ResourceLoader.bulletGunPurple
            );

            bullet.setPosition(this.x, this.y);
            bullet.setStartX();
            bullets.add(bullet);
        }
    }

    // KEYLISTENERS
    @Override
    public void keyPressed(KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_D)
            super.movingRight = true;

        if(e.getKeyCode() == KeyEvent.VK_A)
            super.movingLeft = true;

        if(e.getKeyCode() == KeyEvent.VK_W)
            super.jumping = true;
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_D)
        {
            super.movingRight = false;
            directionX = 0;
        }

        if(e.getKeyCode() == KeyEvent.VK_A)
        {
            super.movingLeft = false;
            directionX = 0;
        }

        if(e.getKeyCode() == KeyEvent.VK_W)
            super.jumping = false;
    }

}
