package Assets.GameObjects;

import Assets.Assets;
import Assets.Inventory.Inventory;
import Assets.SpeechBubble;
import Assets.World.Background;
import Assets.World.Tile;
import Assets.World.TileMap;
import GameSaves.GameData.GameData;
import Main.References;
import Main.ResourceLoader;
import Main.Sound;
import Main.Tutorial;
import State.Level.Level1State;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

/**
 * Spielfigur des Spiels
 *
 * @author Sirat
 * */
public class Player extends GameObject
{
    // Assets
    private Assets playerAssets;
    public static String playerAssetsResPath = "/img/playerSet.png";

    // Animation
    private ArrayList<BufferedImage[]> frames;
    private final int[] frameNumber = {2, 9, 1, 1, 2, 2, 2, 4, 1};

    // Animation ID
    public static final int STILL = 0;
    public static final int WALK = 1;
    public static final int JUMP = 2;
    public static final int FALL = 3;
    public static final int AXE_NORMAL = 4;
    public static final int PICK_NORMAL = 5;
    public static final int HAMMER_NORMAL = 6;
    public static final int SWORD_NORMAL = 7;
    public static final int GUN_NORMAL = 8;

    // weitere Eigenschaften
    private boolean isAxeHit;
    private boolean isPickHit;
    private boolean isHammerHit;
    private boolean isSwordHit;
    private boolean isGunHit;

    public static int maxPower = 100;
    public static int maxHealth = 100;
    public static int maxThirst = 100;
    public static int health = maxHealth;
    public static int power = maxPower;
    public static int thirst = maxThirst;

    private int level;
    private int ep;

    //public ArrayList<Weapon> weaponList = new ArrayList<>();
    public ArrayList<Bullet> bullets = new ArrayList<>();

    public static boolean isIronManSelected = false;
    public static boolean isHulkSelected = false;
    public static boolean isCaptainAmericaSelected = false;
    public static boolean isThorSelected = false;
    public static boolean isBlackWidowSelected = false;
    public static boolean isSpecialSelected = false;

    // Quest
    int Quest = 1;
    private static String task = "Quest 1: Baue 8 GOLD Stuecke ab!";
    static  boolean questDone = false;

    // Sprechblase
    public SpeechBubble speechBubble;

    // Explosion
    private byte explosionRadius;
    private int explosionTimer;
    private int tmpExplosionTimer;
    private boolean setExplosion;
    private Tile explosionTile;

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
     * @param maxVelocityY          Maximalgeschwindigkeit auf der y-Achse
     * @param tileMap               Spielmap
     * */
    public Player(int width, int height, int widthForCollision, int heightForCollision,
                  double velocityX, double velocityY, double maxVelocityX, double maxVelocityY, TileMap tileMap)
    {
        super(width, height, widthForCollision, heightForCollision, velocityX, velocityY, maxVelocityX, maxVelocityY, tileMap);

        // Spielerwerte setzen
        this.level = 1;
        this.ep = 0;

        // Initialisieren Assets
        if (GameData.gender.equals("Female"))
            playerAssetsResPath = "/img/womanPlayerSet.png";
        else if (GameData.gender.equals("IronMan"))
            playerAssetsResPath = "/img/ironManPlayerSet.png";
        else if (GameData.gender.equals("Hulk"))
            playerAssetsResPath = "/img/hulkPlayerSet.png";
        else if (GameData.gender.equals("Captain"))
            playerAssetsResPath = "/img/captainAmericaPlayerSet.png";
        else if (GameData.gender.equals("Thor"))
            playerAssetsResPath = "/img/thorPlayerSet.png";
        else if (GameData.gender.equals("Black Widow"))
            playerAssetsResPath = "/img/blackWidowPlayerSet.png";
        else if (GameData.gender.equals("Special"))
            playerAssetsResPath = "/img/specialPlayerSet.png";
        else
            playerAssetsResPath = "/img/playerSet.png";

        this.playerAssets = new Assets(playerAssetsResPath);

        // Laden des PlayerSet
        loadPlayerSet();

        // Initialisieren der Animation
        animation = new Animation();
        activeAnimation = Player.STILL;
        animation.init(frames.get(Player.STILL));
        animation.setFrameHoldTime(200);

        // Sprechblase
        this.speechBubble = new SpeechBubble();

        // Explosion
        this.explosionRadius = 4;
        this.explosionTimer = 60;
        this.tmpExplosionTimer = this.explosionTimer;
        this.setExplosion = false;
    }

    /**
     * update           Aktualisieren der Position und Animationen
     * */
    @Override
    public void update()
    {
        // Update Bewegungen
        move();

        // Update Kollision mit der TileMap
        super.collisionWithTileMap(true);

        // Update Position relativ zu TileMap
        super.setPosition(xTmp, yTmp);

        // Animationen stoppen, falls bereits abgespielt
        if (activeAnimation == Player.PICK_NORMAL)
            if (animation.getWasPlayed()) isPickHit = false;

        if (activeAnimation == Player.AXE_NORMAL)
            if (animation.getWasPlayed()) isAxeHit = false;

        if (activeAnimation == Player.HAMMER_NORMAL)
            if (animation.getWasPlayed()) isHammerHit = false;

        if (activeAnimation == Player.SWORD_NORMAL)
            if (animation.getWasPlayed()) isSwordHit = false;

        if (activeAnimation == Player.GUN_NORMAL)
            if (animation.getWasPlayed()) isGunHit = false;

        // Aktive Animation Initialisieren
        if(directionY < 0 || (directionY < 0 && movingLeft) || (directionY < 0 && movingRight))
        {
            if(activeAnimation != Player.JUMP)
            {
                width = 22;
                height = 41;

                activeAnimation = Player.JUMP;
                animation.init(frames.get(Player.JUMP));
                animation.setFrameHoldTime(-1);
            }
        }
        else if(movingLeft || movingRight)
        {
            if(activeAnimation != Player.WALK)
            {
                width = 22;
                height = 41;

                activeAnimation = Player.WALK;
                animation.init(frames.get(Player.WALK));
                animation.setFrameHoldTime(40);
            }
        }
        else if(directionY > 0)
        {
            if(activeAnimation != Player.FALL)
            {
                width = 22;
                height = 41;

                activeAnimation = Player.FALL;
                animation.init(frames.get(Player.FALL));
                animation.setFrameHoldTime(-1);
            }
        }
        else if(isPickHit)
        {
            if(activeAnimation != Player.PICK_NORMAL)
            {
                width = 31;
                height = 41;

                activeAnimation = Player.PICK_NORMAL;
                animation.init(frames.get(Player.PICK_NORMAL));
                animation.setFrameHoldTime(90);
            }
        }
        else if(isAxeHit)
        {
            if(activeAnimation != Player.AXE_NORMAL)
            {
                width = 31;
                height = 41;

                activeAnimation = Player.AXE_NORMAL;
                animation.init(frames.get(Player.AXE_NORMAL));
                animation.setFrameHoldTime(90);
            }

        }
        else if(isHammerHit)
        {
            if(activeAnimation != Player.HAMMER_NORMAL)
            {
                width = 31;
                height = 41;

                activeAnimation = Player.HAMMER_NORMAL;
                animation.init(frames.get(Player.HAMMER_NORMAL));
                animation.setFrameHoldTime(90);
            }
        }
        else if(isSwordHit)
        {
            if(activeAnimation != Player.SWORD_NORMAL)
            {
                width = 31;
                height = 41;

                activeAnimation = Player.SWORD_NORMAL;
                animation.init(frames.get(Player.SWORD_NORMAL));
                animation.setFrameHoldTime(90);
            }
        }
        else if(isGunHit)
        {
            if(activeAnimation != Player.GUN_NORMAL)
            {
                width = 31;
                height = 41;

                activeAnimation = Player.GUN_NORMAL;
                animation.init(frames.get(Player.GUN_NORMAL));
                animation.setFrameHoldTime(-1);
            }
        }
        else
        {
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
        try
        {
            for (int i = 0; i < bullets.size(); i++)
            {
                bullets.get(i).update();

                if (bullets.get(i).shouldRemove())
                {
                    bullets.remove(i);
                    i--;
                }
            }
        } catch (ConcurrentModificationException ex) { ex.printStackTrace(); }
        if (Level1State.enemyDestroyed) {
            ep = ep + 50;
            Level1State.enemyDestroyed = false;
        }
    }

    /**
     * render        Zeichnen des Spielers und Geschosse
     *
     * @param g      Graphics Objekt
     * */
    @Override
    public void render(Graphics2D g)
    {
        // Zeichnen auf der TileMap
        super.setOnMap();
        if (!super.isFacingRight)
            g.drawImage(animation.getActiveFrameImage(), (int) (x + xOnMap - width / 2 + width), (int) (y + yOnMap - height / 2), -width, height, null);
        else
            g.drawImage(animation.getActiveFrameImage(), (int) (x + xOnMap - width / 2), (int) (y + yOnMap - height / 2), null);

        // Zeichnen der Bullets
        try
        {
            for (Bullet bullet : bullets) { bullet.render(g); }
        } catch (ConcurrentModificationException ex) { ex.printStackTrace(); }

        // Zeichnen der Sprechblase
        speechBubble.render(g);

        // Zeichne Explosions Timer
        if (setExplosion)
        {
            g.setFont(ResourceLoader.textFieldFontBold.deriveFont(20f));
            if (tmpExplosionTimer >= 0)
            {
                if (GameData.isSoundOn.equals("On")) {
                Sound.tickSound.play();
            }
                if (tmpExplosionTimer < 11) {
                    if (GameData.isSoundOn.equals("On")) {
                        Sound.beepSound.play();
                    }

                    g.setColor(Color.RED);
                } else
                    g.setColor(Color.WHITE);

                g.drawString(
                        Integer.toString(tmpExplosionTimer),
                        References.SCREEN_WIDTH/2 - g.getFontMetrics(ResourceLoader.textFieldFontBold.deriveFont(20f)).stringWidth(Integer.toString(explosionTimer)),
                        References.SCREEN_HEIGHT/4
                );
                tmpExplosionTimer--;
            }
            else
            {
                if (GameData.isSoundOn.equals("On")) {
                Sound.tntSound.play();
                }
                destroyTilesAfterExplosion(explosionTile);
                setExplosion = false;
                tmpExplosionTimer = explosionTimer;
            }
        }
    }

    /**
     * renderQuestbar   Zeichnen der Questanzeige
     *
     * @param g         Graphics Objekt
     * */
    public void renderQuestbar(Graphics2D g)
    {
        // Level & EP
        if (Background.opacity > 100)
            g.setColor(Color.WHITE);
        else
            g.setColor(Color.BLACK);

        g.setFont(ResourceLoader.textFieldFont.deriveFont(14f));

        String lvlAndEpText = "LEVEL: " + level + " | EP: " + ep;
        g.drawString(lvlAndEpText, References.SCREEN_WIDTH - 10 - g.getFontMetrics(ResourceLoader.textFieldFont.deriveFont(14f)).stringWidth(lvlAndEpText), 30 + References.HEALTH_CELL_HEIGHT + References.ENERGY_CELL_HEIGHT + 4 * g.getFont().getSize());

        //Quest-Anzeige
        g.drawString(task, 10, 25);

        //Algorithmus fuer die Quests
        for (int i = 0; i < Inventory.invBar.length; i++)
        {
            if (Inventory.invBar[i].getId() == References.GOLD && Inventory.invBar[i].getCount() == 8 && Quest == 1)
            {
                Inventory.invBar[i].setCount((byte) 0);
                Inventory.invBar[i].setTileImage(null);

                questDone = true;

                ep += 100;
                Quest ++;
                task = "100 EXP verdient!\nQuest " + Quest + ": Stelle einen Burger her und packe ihn ins Inventar.";

                questDone = false;
            }
            else if (Inventory.invBar[i].getId() == References.BURGER && Quest == 2)
            {
                Inventory.invBar[i].setId((byte) 0);
                Inventory.invBar[i].setTileImage(null);
                questDone = true;

                ep += 160;
                Quest++;
                task="160 EXP verdient! Quest " + Quest + ": Sammle passende Zutaten, um einen Genesungstrank herzustellen und pack ihn ins Inventar!";

                questDone = false;
            }
            else if(Inventory.invBar[i].getId() == References.POTION && Quest == 3)
            {
                Inventory.invBar[i].setId((byte) 0);
                Inventory.invBar[i].setTileImage(null);

                questDone = true;

                ep += 300;
                Quest++;
                task = "300 EXP verdient! Quest " + Quest + ": Versuch dein Leben auf 50% zu bringen!";

                questDone = false;
            }
        }
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
                    for(int column = 0; column < frameNumber[row]; column++) { frame[column] = playerAssets.getSubimage(column*31, row*41, 31, 41); }
                else
                    for(int column = 0; column < frameNumber[row]; column++) { frame[column] = playerAssets.getSubimage(column*width, row*height, width, height); }

                frames.add(frame);
            }
        } catch(Exception ex) { System.out.println("Error: " + ex.getMessage()); }
    }

    /**
     * looseHealth          Leben verlieren
     *
     * @param damage        Schaden
     * */
    public void looseHealth(int damage) { health = Math.max(health - damage, 0); }

    /**
     * incExperience        Erfahrungspunkte erhoehen
     *
     * @param ep            Erfahrungspunkte
     * */
    public void incExperience(int ep)
    {
        this.ep += ep;

        while(this.ep >= level*250)
        {
            this.ep -= level * 250;
            level++;
        }
        maxHealth = health = 30 + level * 10;
    }

    /**
     * consume          Zunahme von Essen oder Zaubertrank
     * */
    private void consume()
    {
        if (Inventory.invBar[Inventory.selected].getId() == References.BURGER)
        {
            if (GameData.isSoundOn.equals("On"))
            {
                Sound.eatSound.play();
                Sound.heartBeatSound.stop();
            }

            Level1State.energyDown = false;
            Level1State.isThirsty = false;

            Player.power = Player.maxPower;
            Level1State.setEnergyTimer();

            Player.health = Player.maxHealth;
            Level1State.setHealthTimer(false);

            Player.thirst = Player.maxThirst;
            Level1State.setThirstTimer();

            speechBubble.createSpeechBubble("Lecker!");

            Inventory.removeFromInventory(Inventory.invBar[Inventory.selected].getId());
        }
        else if (Inventory.invBar[Inventory.selected].getId() == References.POTION)
        {
            if (GameData.isSoundOn.equals("On"))
            {
                Sound.drinkSound.play();
                Sound.heartBeatSound.stop();
            }

            Player.power = Player.maxPower;
            Level1State.setEnergyTimer();

            Player.health = Player.maxHealth;
            Level1State.setHealthTimer(false);

            Player.thirst = Player.maxThirst;
            Level1State.setThirstTimer();

            speechBubble.createSpeechBubble("Geheilt!");
            Inventory.removeFromInventory(Inventory.invBar[Inventory.selected].getId());
        }
    }

    /**
     * checkIfNotInRadius   Pruefen, ob Tile ausserhalb des Abbauradius liegt
     *
     * @param selectedTile  Tile
     * @return              Wert, ob Tile in Abbauradius liegt
     * */
    private boolean checkIfNotInRadius(Tile selectedTile)
    {
        return (selectedTile.getX() > x+tileMap.getX() + explosionRadius*References.TILE_SIZE || selectedTile.getX() < x+tileMap.getX() - explosionRadius*References.TILE_SIZE
                || selectedTile.getY() > y+tileMap.getY()+ explosionRadius*References.TILE_SIZE || selectedTile.getY() < y+tileMap.getY() - explosionRadius*References.TILE_SIZE);
    }

    /**
     * explode          Anwenden von TNT, falls ausgewaehlt
     *
     * @param e         Mausevent
     * */
    private void explode(MouseEvent e)
    {
        if (setExplosion) return;

        explosionTile = tileMap.getMap().get(new Point((int) ((e.getY() - tileMap.getY()) / References.TILE_SIZE), (int) (Math.floor((e.getX() - tileMap.getX()) / References.TILE_SIZE))));

        if (checkIfNotInRadius(explosionTile)) return;

        if (tileMap.getMap().get(new Point(explosionTile.getRow()+1, explosionTile.getColumn())).getId() != 0
                &&  tileMap.getMap().get(new Point(explosionTile.getRow()+1, explosionTile.getColumn())).getId() != References.WATER
                &&  tileMap.getMap().get(new Point(explosionTile.getRow()-1, explosionTile.getColumn())).getId() == 0)
        {
            setExplosion = true;
            tileMap.getMap().get(new Point(explosionTile.getRow(), explosionTile.getColumn())).setTexture(ResourceLoader.tnt);
            tileMap.getMap().get(new Point(explosionTile.getRow(), explosionTile.getColumn())).setIsCollidable(true);
            tileMap.getMap().get(new Point(explosionTile.getRow(), explosionTile.getColumn())).setHasGravity(true);
            tileMap.getMap().get(new Point(explosionTile.getRow(), explosionTile.getColumn())).setIsDestructible(false);
            Inventory.removeFromInventory(Inventory.invBar[Inventory.selected].getId());
        }
    }

    /**
     * destroyTilesAfterExplosion       Zerstoeren der Tiles die im Explosionsradius liegen
     *
     * @param explosionTile             TNT Tile
     * */
    private void destroyTilesAfterExplosion(Tile explosionTile)
    {
        int tmpExplosionRadius = explosionRadius;
        int tmpExplosionRadius2 = 0;

        // Zeile entferene
        while (tmpExplosionRadius > 0)
        {
            // linke Seite
            while (tmpExplosionRadius2 < explosionRadius)
            {
                tileMap.getMap().get(new Point(explosionTile.getRow()-tmpExplosionRadius, explosionTile.getColumn()-tmpExplosionRadius2)).setTexture(null);
                tileMap.getMap().get(new Point(explosionTile.getRow()-tmpExplosionRadius, explosionTile.getColumn()-tmpExplosionRadius2)).setIsCollidable(false);
                tileMap.getMap().get(new Point(explosionTile.getRow()-tmpExplosionRadius, explosionTile.getColumn()-tmpExplosionRadius2)).setHasGravity(false);
                tileMap.getMap().get(new Point(explosionTile.getRow()-tmpExplosionRadius, explosionTile.getColumn()-tmpExplosionRadius2)).setIsDestructible(false);

                tmpExplosionRadius2++;
            }

            // rechte Seite
            tmpExplosionRadius2 = 0;
            while (tmpExplosionRadius2 < explosionRadius)
            {
                tileMap.getMap().get(new Point(explosionTile.getRow()-tmpExplosionRadius, explosionTile.getColumn()+tmpExplosionRadius2)).setTexture(null);
                tileMap.getMap().get(new Point(explosionTile.getRow()-tmpExplosionRadius, explosionTile.getColumn()+tmpExplosionRadius2)).setIsCollidable(false);
                tileMap.getMap().get(new Point(explosionTile.getRow()-tmpExplosionRadius, explosionTile.getColumn()+tmpExplosionRadius2)).setHasGravity(false);
                tileMap.getMap().get(new Point(explosionTile.getRow()-tmpExplosionRadius, explosionTile.getColumn()+tmpExplosionRadius2)).setIsDestructible(false);

                tmpExplosionRadius2++;
            }

            tmpExplosionRadius2 = 0;
            tmpExplosionRadius--;
        }

        tmpExplosionRadius = 0;
        tmpExplosionRadius2 = 0;
        while (tmpExplosionRadius < explosionRadius)
        {
            // linke Seite
            while (tmpExplosionRadius2 < explosionRadius)
            {
                tileMap.getMap().get(new Point(explosionTile.getRow()+tmpExplosionRadius, explosionTile.getColumn()-tmpExplosionRadius2)).setTexture(null);
                tileMap.getMap().get(new Point(explosionTile.getRow()+tmpExplosionRadius, explosionTile.getColumn()-tmpExplosionRadius2)).setIsCollidable(false);
                tileMap.getMap().get(new Point(explosionTile.getRow()+tmpExplosionRadius, explosionTile.getColumn()-tmpExplosionRadius2)).setHasGravity(false);
                tileMap.getMap().get(new Point(explosionTile.getRow()+tmpExplosionRadius, explosionTile.getColumn()-tmpExplosionRadius2)).setIsDestructible(false);

                tmpExplosionRadius2++;
            }

            // rechte Seite
            tmpExplosionRadius2 = 0;
            while (tmpExplosionRadius2 < explosionRadius)
            {
                tileMap.getMap().get(new Point(explosionTile.getRow()+tmpExplosionRadius, explosionTile.getColumn()+tmpExplosionRadius2)).setTexture(null);
                tileMap.getMap().get(new Point(explosionTile.getRow()+tmpExplosionRadius, explosionTile.getColumn()+tmpExplosionRadius2)).setIsCollidable(false);
                tileMap.getMap().get(new Point(explosionTile.getRow()+tmpExplosionRadius, explosionTile.getColumn()+tmpExplosionRadius2)).setHasGravity(false);
                tileMap.getMap().get(new Point(explosionTile.getRow()+tmpExplosionRadius, explosionTile.getColumn()+tmpExplosionRadius2)).setIsDestructible(false);

                tileMap.generateParticles(new Point((int) (explosionTile.getX()+tileMap.getX() - tmpExplosionRadius), (int) (explosionTile.getY()+tileMap.getY() - tmpExplosionRadius2)), Color.RED, 20, 5);

                tmpExplosionRadius2++;
            }

            tmpExplosionRadius2 = 0;
            tmpExplosionRadius++;
        }
    }

    /**
     * move         Berechnen der Bewegung
     * */
    private void move()
    {
        if (!Inventory.isDrawerOpen)
        {
            if(super.movingLeft && !super.isInWater)
            {

                super.isFacingRight = false;

                if (directionX < -velocityX)
                    directionX = -maxVelocityX;
                else
                    directionX -= velocityX;
            }
            else if(super.movingRight && !super.isInWater)
            {
                super.isFacingRight = true;

                if (directionX > velocityX)
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
        if ( Inventory.invBar[Inventory.selected].getId() == References.PICK
                && (tile.getId() == References.DIRT || tile.getId() == References.GRAS || tile.getId() == References.COPPER || tile.getId() == References.ION || tile.getId() == References.SILVER
                || tile.getId() == References.GOLD || tile.getId() == References.RUBY || tile.getId() == References.SAPHIRE || tile.getId() == References.SMARAGD
                || tile.getId() == References.DIAMOND || tile.getId() == References.ICE || tile.getId() == References.SAND) )
        {
            isPickHit = true;
            isAxeHit = false;
            isHammerHit = false;
            isSwordHit = false;
            isGunHit = false;
        }
        else if (Inventory.invBar[Inventory.selected].getId() == References.AXE && (tile.getId() == References.WOOD || tile.getId() == References.LEAF) )
        {
            isAxeHit = true;
            isPickHit = false;
            isHammerHit = false;
            isSwordHit = false;
            isGunHit = false;
        }
        else if (Inventory.invBar[Inventory.selected].getId() == References.HAMMER
                && (tile.getId() == References.COPPER || tile.getId() == References.ION || tile.getId() == References.SILVER || tile.getId() == References.GOLD
                || tile.getId() == References.RUBY || tile.getId() == References.SAPHIRE || tile.getId() == References.SMARAGD || tile.getId() == References.DIAMOND) )
        {
            isHammerHit = true;
            isAxeHit = false;
            isPickHit = false;
            isSwordHit = false;
            isGunHit = false;
        }
        else if (Inventory.invBar[Inventory.selected].getId() == References.SWORD)
        {
            isSwordHit = true;
            isHammerHit = false;
            isAxeHit = false;
            isPickHit = false;
            isGunHit = false;
        }
        else if (Inventory.invBar[Inventory.selected].getId() == References.PURPLE_GUN)
        {
            if(GameData.isSoundOn.equals("On") && isIronManSelected)
                Sound.ironManShootSound.play();
            else if(GameData.isSoundOn.equals("On")&& isHulkSelected)
                Sound.hulkClapSound.play();
            else if (GameData.isSoundOn.equals("On")&& isCaptainAmericaSelected)
                Sound.captainAmericaThrowSound.play();
            else if (GameData.isSoundOn.equals("On") && isThorSelected)
              Sound.mjoelmirSound.play();
            else if (GameData.isSoundOn.equals("On") && isBlackWidowSelected)
                Sound.gunShot.play();
            else if (GameData.isSoundOn.equals("On") && isSpecialSelected)
              Sound.specialShootSound.play();
            else if(GameData.isSoundOn.equals("On"))
                Sound.boomSound.play();

            isGunHit = true;
            Tutorial.solveTut(Tutorial.TUT_SHOOT);

            if(isIronManSelected)
            {
                Bullet ironManBullet = new Bullet(
                        ResourceLoader.ironManBullet.getWidth(),
                        ResourceLoader.ironManBullet.getHeight(),
                        ResourceLoader.ironManBullet.getWidth(),
                        ResourceLoader.ironManBullet.getHeight(),
                        9,
                        1.4,
                        15,
                        2.5,
                        super.getTileMap(),
                        super.isFacingRight,
                        ResourceLoader.ironManBullet
                );
                ironManBullet.setPosition(this.x, this.y);
                ironManBullet.setStartX();
                bullets.add(ironManBullet);
            }
            else if (isBlackWidowSelected)
            {
                Bullet blackBullet = new Bullet(
                        ResourceLoader.blackBullet.getWidth(),
                        ResourceLoader.blackBullet.getHeight(),
                        ResourceLoader.blackBullet.getWidth(),
                        ResourceLoader.blackBullet.getHeight(),
                        9,
                        1.4,
                        15,
                        2.5,
                        super.getTileMap(),
                        super.isFacingRight,
                        ResourceLoader.blackBullet
                );

                blackBullet.setPosition(this.x, this.y);
                blackBullet.setStartX();
                bullets.add(blackBullet);
            }
            else if (isSpecialSelected)
            {
                Bullet specialBullet = new Bullet(
                        ResourceLoader.specialBullet.getWidth(),
                        ResourceLoader.specialBullet.getHeight(),
                        ResourceLoader.specialBullet.getWidth(),
                        ResourceLoader.specialBullet.getHeight(),
                        9,
                        1.4,
                        15,
                        2.5,
                        super.getTileMap(),
                        super.isFacingRight,
                        ResourceLoader.specialBullet
                );

                specialBullet.setPosition(this.x, this.y);
                specialBullet.setStartX();
                bullets.add(specialBullet);
            }
            else if (isHulkSelected)
            {
                Bullet hulkBullet = new Bullet(
                        ResourceLoader.hulkBullet.getWidth(),
                        ResourceLoader.hulkBullet.getHeight(),
                        ResourceLoader.hulkBullet.getWidth(),
                        ResourceLoader.hulkBullet.getHeight(),
                        9,
                        1.4,
                        15,
                        2.5,
                        super.getTileMap(),
                        super.isFacingRight,
                        ResourceLoader.hulkBullet
                );

                hulkBullet.setPosition(this.x, this.y);
                hulkBullet.setStartX();
                bullets.add(hulkBullet);
            }
            else if (isCaptainAmericaSelected)
            {
                Bullet captainAmericaShield = new Bullet (
                        ResourceLoader.captainAmericaShield.getWidth(),
                        ResourceLoader.captainAmericaShield.getHeight(),
                        ResourceLoader.captainAmericaShield.getWidth(),
                        ResourceLoader.captainAmericaShield.getHeight(),
                        9,
                        1.4,
                        15,
                        2.5,
                        super.getTileMap(),
                        super.isFacingRight,
                        ResourceLoader.captainAmericaShield
                );

                captainAmericaShield.setPosition(this.x, this.y);
                captainAmericaShield.setStartX();
                bullets.add(captainAmericaShield);
            }
            else if (isThorSelected)
            {
                Bullet mjoelmir = new Bullet (
                        ResourceLoader.mjoelmir.getWidth(),
                        ResourceLoader.mjoelmir.getHeight(),
                        ResourceLoader.mjoelmir.getWidth(),
                        ResourceLoader.mjoelmir.getHeight(),
                        9,
                        1.4,
                        15,
                        2.5,
                        super.getTileMap(),
                        super.isFacingRight,
                        ResourceLoader.mjoelmir
                );

                mjoelmir.setPosition(this.x, this.y);
                mjoelmir.setStartX();
                bullets.add(mjoelmir);
            }
            else
            {
                Bullet bullet = new Bullet(
                        ResourceLoader.bulletGunPurple.getWidth(),
                        ResourceLoader.bulletGunPurple.getHeight(),
                        ResourceLoader.bulletGunPurple.getWidth(),
                        ResourceLoader.bulletGunPurple.getHeight(),
                        20,
                        1.5,
                        40,
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
        else if (Inventory.invBar[Inventory.selected].getId() == References.TNT)
        {
            explode(e);
        }
    }

    // KEYLISTENERS
    @Override
    public void keyPressed(KeyEvent e)
    {
        // Rechts laufen
        if (e.getKeyCode() == KeyEvent.VK_D)
        {
            Tutorial.solveTut(Tutorial.TUT_RUN_RIGHT);
            super.movingRight = true;
        }

        // Links laufen
        if (e.getKeyCode() == KeyEvent.VK_A)
        {
            Tutorial.solveTut(Tutorial.TUT_RUN_LEFT);
            super.movingLeft = true;
        }

        // Springen
        if (e.getKeyCode() == KeyEvent.VK_W)
        {
            Tutorial.solveTut(Tutorial.TUT_JUMP);

            if(GameData.isSoundOn.equals("On") && isIronManSelected)
                Sound.ironManJumpSound.play();
            else if (GameData.isSoundOn.equals("On")&& isHulkSelected)
                Sound.hulkJumpSound.play();
            else if (GameData.isSoundOn.equals("On")&& isCaptainAmericaSelected)
                Sound.captainAmericaJumpSound.play();
            else if (GameData.isSoundOn.equals("On")&& isThorSelected)
                Sound.thorJumpSound.play();
            else if (GameData.isSoundOn.equals("On")&& isBlackWidowSelected)
                Sound.blackWidowJumpSound.play();
            else if (GameData.isSoundOn.equals("On")&& isSpecialSelected)
              Sound.specialJumpSound.play();
            else if (GameData.isSoundOn.equals("On"))
                Sound.jumpSound.play();

            if (GameData.isSoundOn.equals("On") && isIronManSelected && isInWater)
            {
                Sound.ironManJumpSound.stop();
                Sound.waterSound.play();
            }
            else if (GameData.isSoundOn.equals("On")&& isHulkSelected && isInWater)
            {
                Sound.hulkJumpSound.stop();
                Sound.waterSound.play();
            }
            else if (GameData.isSoundOn.equals("On")&& isCaptainAmericaSelected && isInWater)
            {
                Sound.captainAmericaJumpSound.stop();
                Sound.waterSound.play();
            }
            else if (GameData.isSoundOn.equals("On")&& isThorSelected && isInWater)
            {
                Sound.thorJumpSound.stop();
                Sound.waterSound.play();
            }
            else if (GameData.isSoundOn.equals("On")&& isBlackWidowSelected && isInWater)
            {
                Sound.blackWidowJumpSound.stop();
                Sound.waterSound.play();
            }
            else if (GameData.isSoundOn.equals("On")&& isSpecialSelected && isInWater)
            {
                Sound.specialJumpSound.stop();
                Sound.waterSound.play();
            }
            else if(GameData.isSoundOn.equals("On") && isInWater)
            {
                Sound.jumpSound.stop();
                Sound.waterSound.play();
            }

            super.jumping = true;
        }

        // Konsumiere Essen oder Zaubertrank
        if (e.getKeyCode() == KeyEvent.VK_E)
            consume();

        // Sprechblase ziegen oder verdecken
        if (e.getKeyCode() == KeyEvent.VK_ENTER) speechBubble.keyPressed(e);

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

        if(e.getKeyCode() == KeyEvent.VK_W) super.jumping = false;
    }

    // GETTER UND SETTER
    /**
     * getHealth        Rueckgabe des Lebens
     * @return          Leben
     * */
    public int getHealth() { return health; }

}
