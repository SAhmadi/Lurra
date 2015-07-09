package Assets.GameObjects;

import Assets.Assets;
import Assets.Inventory.Cell;
import Assets.Inventory.Inventory;
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
import java.util.Arrays;
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
    private static final int STILL = 0;
    private static final int WALK = 1;
    private static final int JUMP = 2;
    private static final int FALL = 3;
    private static final int AXE_NORMAL = 4;
    private static final int PICK_NORMAL = 5;
    private static final int HAMMER_NORMAL = 6;
    private static final int SWORD_NORMAL = 7;
    private static final int GUN_NORMAL = 8;

    // weitere Eigenschaften
    private boolean isAxeHit;
    private boolean isPickHit;
    private boolean isHammerHit;
    private boolean isGunHit;

    public static int maxPower = 100;
    public static int maxHealth = 100;
    public static int maxThirst = 100;
    public static int health = maxHealth;
    public static int power = maxPower;
    public static int thirst = maxThirst;

    private int level;
    private int ep;

    public ArrayList<Weapon> weaponList = new ArrayList<>();
    public ArrayList<Bullet> bullets = new ArrayList<>();

    public static boolean isIronManSelected = false;
    public static boolean isHulkSelected = false;
    public static boolean isCaptainAmericaSelected = false;
    public static boolean isThorSelected = false;
    public static boolean isBlackWidowSelected = false;

    // Quest
    int Quest = 1;
    private static String task = "Quest 1: Baue 5 GOLD Stuecke ab!";
    static  boolean questDone = false;

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
        else
            playerAssetsResPath = "/img/playerSet.png";

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
        else if(isGunHit)
        {
            if(activeAnimation != Player.GUN_NORMAL)
            {
                width = 31;
                height = 41;

                activeAnimation = Player.GUN_NORMAL;
                animation.init(frames.get(Player.GUN_NORMAL));
                animation.setFrameHoldTime(70);
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
        } catch (ConcurrentModificationException ex) { if (References.SHOW_EXCEPTION) System.out.println("Error: " + ex.getMessage()); }
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
        if (!super.isFacingRight)
            g.drawImage(animation.getActiveFrameImage(), (int) (x + xOnMap - width / 2 + width), (int) (y + yOnMap - height / 2), -width, height, null);
        else
            g.drawImage(animation.getActiveFrameImage(), (int) (x + xOnMap - width / 2), (int) (y + yOnMap - height / 2), null);

        // Zeichnen der Bullets
        try
        {
            for (int i = 0; i < bullets.size(); i++) { bullets.get(i).render(g); }
        } catch (ConcurrentModificationException ex) { if (References.SHOW_EXCEPTION) System.out.println("Error: " + ex.getMessage()); }
    }

    /**
     * renderQuestbar   Zeichnen der Questanzeige
     *
     * @param g         Graphics Objekt
     * */
    public void renderQuestbar(Graphics g)
    {
        // Level & EP
        g.setColor(Color.BLUE);
        g.setFont(ResourceLoader.textFieldFont);
        g.drawString("Level: " + level + " | EP: " + ep, References.SCREEN_WIDTH - 120, 140);

        //Quest-Anzeige
        g.drawString(task, 10, 25);

        //Algorithmus f�r die Quests
        for (int i = 0; i < Inventory.invBar.length; i++)
        {
            if (Inventory.invBar[i].tileImage == ResourceLoader.gold &&
                    Inventory.invBar[i].count == 5 && Quest == 1)
            {
                Inventory.invBar[i].name = "null";
                Inventory.invBar[i].setTileImage();
                questDone = true;

                if (questDone) {
                    ep += 50;
                    Quest ++;
                    task = "50 EXP verdient! Quest " + Quest + ": Stelle mir einen Burger her und pack ihn ins Inventar!";
                    questDone = false;
                }
            }
            else if (Inventory.invBar[i].tileImage == ResourceLoader.burger && Quest == 2)
            {
                Inventory.invBar[i].name = "null";
                Inventory.invBar[i].setTileImage();
                questDone = true;

                if (questDone) {
                    ep += 80;
                    Quest++;
                    task="80 EXP verdient! Quest "+Quest+": Sammle Erde, Saphir und Silber um einen Genesungstrank herzustellen und pack ihn ins Inventar!";
                    questDone = false;
                }
            }
            else if(Inventory.invBar[i].tileImage == ResourceLoader.healthPotion && Quest == 3)
            {
                Inventory.invBar[i].name = "null";
                Inventory.invBar[i].setTileImage();
                questDone = true;

                if (questDone) {
                    ep += 150;
                    Quest++;
                    task = "150 EXP verdient! Quest "+Quest+": Versuch dein Leben auf 50% zu bringen!";
                }
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
                    for(int column = 0; column < frameNumber[row]; column++) { frame[column] = playerAssets.getSubimage(column*31, row*41, 34, 41); }
                else
                    for(int column = 0; column < frameNumber[row]; column++) { frame[column] = playerAssets.getSubimage(column*width, row*height, width, height); }

                frames.add(frame);
            }
        } catch(Exception ex) { System.out.println("Error: " + ex.getMessage()); }
    }

    /**
     * restoreValues        Leben aus Spielstand laden
     * */
    public void restoreValues(int h, int level, int ep)    // Beim fortsetzen eines Spiels
    {
        health = h;
        this.level = level;
        this.ep = ep;
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

        while(this.ep >= level*500)
        {
            this.ep -= level * 500;
            level++;
        }
        maxHealth = health = 30 + level * 10;
    }

    /**
     * consume          Zunahme von Essen oder Zaubertrank
     * */
    private void consume()
    {
        for (Cell cell : Inventory.invBar)
        {
            if ((cell.tileImage == ResourceLoader.burger))
            {
                if (GameData.isSoundOn.equals("On")) Sound.eatSound.play();

                Level1State.energyDown = false;
                Level1State.isThirsty = false;

                Player.power = Player.maxPower;
                Level1State.setEnergyTimer();
                Player.health = Player.maxHealth;
                Level1State.setHealthTimer(false);
                Player.thirst = Player.maxThirst;
                Level1State.setThirstTimer();

                System.out.println("Lecker!");

                cell.name = "null";
                cell.setTileImage();
                cell.count = 0;
                cell.wasEaten = true;
            }
            else if (cell.tileImage == ResourceLoader.healthPotion)
            {
                if (GameData.isSoundOn.equals("On"))
                {
                    Sound.drinkSound.play();
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

                System.out.println("Leben geheilt!");

                cell.name = "null";
                cell.setTileImage();
                cell.count = 0;
                cell.wasEaten = true;
            }
        }
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
        if ( Inventory.invBar[Inventory.selected].name.equals("Picke")
                && (Arrays.asList(TileMap.dirtTextures).contains(tile.getTexture()) || Arrays.asList(TileMap.gemsTextures).contains(tile.getTexture())
                || Arrays.asList(TileMap.iceOnlyTextures).contains(tile.getTexture()) || Arrays.asList(TileMap.sandOnlyTextures).contains(tile.getTexture())) )
        {
            isPickHit = true;
            isAxeHit = false;
            isHammerHit = false;
        }
        else if (Inventory.invBar[Inventory.selected].name.equals("Axt") &&
                (Arrays.asList(TileMap.treeOnlyTextures).contains(tile.getTexture()) || Arrays.asList(TileMap.treeSnowOnlyTextures).contains(tile.getTexture())
                        || Arrays.asList(TileMap.cactusOnlyTextues).contains(tile.getTexture())) )
        {
            isAxeHit = true;
            isPickHit = false;
            isHammerHit = false;
        }
        else if (Inventory.invBar[Inventory.selected].name.equals("Hammer") && Arrays.asList(TileMap.gemsTextures).contains(tile.getTexture()))
        {
            isHammerHit = true;
            isAxeHit = false;
            isPickHit = false;
        }
        else if (Inventory.invBar[Inventory.selected].name.equals("Schleimpistole"))
        {
            if(GameData.isSoundOn.equals("On") && isIronManSelected) {
                Sound.ironManShootSound.play();
            } else if(GameData.isSoundOn.equals("On")&& isHulkSelected) {
                Sound.hulkClapSound.play();
            } else if (GameData.isSoundOn.equals("On")&& isCaptainAmericaSelected) {
                Sound.captainAmericaThrowSound.play();
            } else if (GameData.isSoundOn.equals("On") && isThorSelected) {
              Sound.mjoelmirSound.play();
            } else if (GameData.isSoundOn.equals("On") && isBlackWidowSelected) {
                Sound.gunShot.play();
            } else if(GameData.isSoundOn.equals("On")) {
                Sound.boomSound.play();
            }
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

            Bullet blackBullet = new Bullet (
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


            if(isIronManSelected) {
                ironManBullet.setPosition(this.x, this.y);
                ironManBullet.setStartX();
                bullets.add(ironManBullet);
            } else if (isHulkSelected) {
                hulkBullet.setPosition(this.x, this.y);
                hulkBullet.setStartX();
                bullets.add(hulkBullet);
            } else if (isCaptainAmericaSelected) {
                captainAmericaShield.setPosition(this.x, this.y);
                captainAmericaShield.setStartX();
                bullets.add(captainAmericaShield);
            } else if (isThorSelected) {
                mjoelmir.setPosition(this.x, this.y);
                mjoelmir.setStartX();
                bullets.add(mjoelmir);
            } else if (isBlackWidowSelected) {
                blackBullet.setPosition(this.x, this.y);
                blackBullet.setStartX();
                bullets.add(blackBullet);
            } else {
                bullet.setPosition(this.x, this.y);
                bullet.setStartX();
                bullets.add(bullet);
            }
        }
    }

    // KEYLISTENERS
    @Override
    public void keyPressed(KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_D) {
            Tutorial.solveTut(Tutorial.TUT_RUN_RIGHT);
            super.movingRight = true;
        }

        if(e.getKeyCode() == KeyEvent.VK_A) {
            Tutorial.solveTut(Tutorial.TUT_RUN_LEFT);
            super.movingLeft = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_W) {
            Tutorial.solveTut(Tutorial.TUT_JUMP);
            if(GameData.isSoundOn.equals("On") && isIronManSelected) {
                Sound.ironManJumpSound.play();
            } else if (GameData.isSoundOn.equals("On")&& isHulkSelected) {
                Sound.hulkJumpSound.play();
            } else if (GameData.isSoundOn.equals("On")&& isCaptainAmericaSelected) {
                Sound.captainAmericaJumpSound.play();
            } else if (GameData.isSoundOn.equals("On")&& isThorSelected) {
                Sound.thorJumpSound.play();
            } else if (GameData.isSoundOn.equals("On")&& isBlackWidowSelected) {
                Sound.blackWidowJumpSound.play();
            } else if (GameData.isSoundOn.equals("On")) {
                Sound.jumpSound.play();
            }

            if(GameData.isSoundOn.equals("On") && isIronManSelected && isInWater) {
                Sound.ironManJumpSound.stop();
                Sound.waterSound.play();
            } else if(GameData.isSoundOn.equals("On")&& isHulkSelected && isInWater) {
                Sound.hulkJumpSound.stop();
                Sound.waterSound.play();
            } else if (GameData.isSoundOn.equals("On")&& isCaptainAmericaSelected && isInWater) {
                Sound.captainAmericaJumpSound.stop();
                Sound.waterSound.play();
            } else if (GameData.isSoundOn.equals("On")&& isThorSelected && isInWater) {
                Sound.thorJumpSound.stop();
                Sound.waterSound.play();
            } else if (GameData.isSoundOn.equals("On")&& isBlackWidowSelected && isInWater) {
                Sound.blackWidowJumpSound.stop();
                Sound.waterSound.play();
            } else if(GameData.isSoundOn.equals("On") && isInWater) {
                Sound.jumpSound.stop();
                Sound.waterSound.play();
            }

            super.jumping = true;
        }

        // Konsumiere Essen oder Zaubertrank
        if(e.getKeyCode() == KeyEvent.VK_E)
            consume();
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

    // GETTER UND SETTER
    /**
     * getHealth        Rueckgabe des Lebens
     * @return          Leben
     * */
    public int getHealth() { return health; }

}
