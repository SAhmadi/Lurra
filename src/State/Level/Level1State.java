package State.Level;

import Assets.Crafting.Crafting;
import Assets.GameObjects.Enemy;
import Assets.GameObjects.GameObject;
import Assets.GameObjects.Player;
import Assets.GameObjects.Weapon;
import Assets.Inventory.Inventory;
import Main.Tutorial;
import Assets.World.Background;
import Assets.World.Tile;
import Assets.World.TileMap;
import GameSaves.GameData.GameData;
import Main.GamePanel;
import Main.References;
import Main.ResourceLoader;
import Main.Sound;
import State.State;
import State.StateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

/**
 * Erstes Level des Spiels
 *
 * @author Sirat, Amin, Mo
 * */
public class Level1State extends State
{
    // Inhaltsflaeche, Graphics-Obj und Zustands-Manger
    protected GamePanel gamePanel;
    public  Graphics graphics;
    protected StateManager stateManager;

    private Graphics2D g2d;

    private boolean continueLevel;

    // Hintergrundbilder - Pfad
    private Background background;
    private Image backgroundImage;
    private String level1DayBackgroundPath = "/img/grassbg1.jpg";

    // Statusbar
    public static BufferedImage currentEnergy = ResourceLoader.energy100;
    public static BufferedImage currentHealth = ResourceLoader.health100;
    public static BufferedImage currentThirst = ResourceLoader.thirst100;

    public static int maxPower = Player.maxPower;
    public static int maxHealth = Player.maxHealth;
    public static int maxThirst = Player.maxThirst;

    public static boolean energyDown = false;
    public static boolean isDead = false;
    public static boolean isThirsty = false;

    private static boolean isHealthCritical = false;
    private static boolean drawHealthCritical = false;
    private static boolean tutorialOpen = false;

    private Timer energyTimer;  // Timer fuer die Energieanzeige und Lebebnsanzeige
    private Timer thirstTimer;  //Timer fuer die Durstanzeige

    // Tilemap
    public static TileMap tileMap;
    private String levelMapPath = "res/xml/playerLevelSaves/";

    // Spieler
    private Player player;
    private boolean LSDMode;

    // Gegner
    private ArrayList<Enemy> enemies;

    // Inventar und Crafting
    public Inventory inventory;
    public Crafting crafting;

    /**
     * Level1State          Konstruktor der Level1State-Klasse
     *
     * @param graphics      Graphics Objekt
     * @param gamePanel     Spielinhaltsflaeche
     * @param stateManager  Zustandsmanager
     * @param continueLevel Wert, ob bereits angespielt
     * */
    public Level1State(Graphics graphics, GamePanel gamePanel, StateManager stateManager, boolean continueLevel)
    {
        this.gamePanel = gamePanel;
        this.graphics = graphics;
        this.stateManager = stateManager;

        this.background = new Background();

        // Inventory und Crafting
        this.inventory = new Inventory();
        this.crafting = new Crafting();

        this.continueLevel = continueLevel;

        this.LSDMode = false;

        // Initialisieren
        init();
        initTimers();
    }

    /**
     * init     Initialisieren des Levels
     * */
    @Override
    public void init() {
        // Hintergrundbild
        g2d = (Graphics2D) graphics;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        //this.backgroundImage = ImageIO.read(getClass().getResourceAsStream(level1DayBackgroundPath));

        // Tilemap
        tileMap = new TileMap(20);
        tileMap.setPosition(0, 0);

        // Spiel Fortsetzen oder Neues Spiel
//        if(continueLevel) {
//            tileMap.levelLoad(PlayerData.name);
//            //InventoryDataLoad.XMLRead(PlayerData.name);
//            //inventory.loadCells();
//        }
//        else
//            tileMap.generateMap(ScreenDimensions.WIDTH / 100);

        // Spieler Positionieren
        player = new Player(22, 41, 16, 16, 0.5, -5.0, 8.0, -20.0, tileMap);
        TileMap.ownPlayerInstance = player;
        player.setPosition(References.SCREEN_WIDTH / 2, References.SCREEN_HEIGHT / 2 - 2 * player.getHeight());
        tileMap.xForTileMapStart = player.getX();

        // Gegner Deklarieren
        enemies = new ArrayList<>();
    }

    /**
     * initTimers       Initialisieren der Timer
     * */
    private void initTimers()
    {
        energyTimer = new Timer(5000, e ->
        {
            Player.power--;
            Level1State.setEnergyTimer();   // Ruft automatisch auch Lebenstimer auf
        });

        thirstTimer = new Timer(5000, e ->
        {
            Player.thirst--;
            Level1State.setThirstTimer();
        });
    }

    /**
     * update       Aktualisieren der Levelinhalte
     * */
    @Override
    public void update()
    {
        // Gegner hinzufuegen
        if (!Background.isDay)
            if (new Random().nextInt(100) == 15)
                enemies.add(new Enemy(43, 41, 32, 32, 0.3, -5.0, 5.0, -20.0, tileMap, ResourceLoader.enemyEye, player, 40, 5));

        // Hintergrund
        background.update();

        // Tilemap
        tileMap.setPosition(References.SCREEN_WIDTH / 2 - player.getX(), References.SCREEN_HEIGHT / 2 - player.getY());
        tileMap.update();

        // Spieler
        player.update();

        // Gegner
        try
        {
            for (int i = 0; i < enemies.size(); i++)
            {
                enemies.get(i).setWasHit(false);

                // Falls Gegner kein Leben mehr hat, entfernen
                if (enemies.get(i).shouldRemove())
                {
                    enemies.remove(i);
                    i--;
                    continue;
                }

                // Kollision zwischen Gegner und Spieler
                if (enemies.get(i).collisionWith(player))
                {
                    player.looseHealth(enemies.get(i).getDamage());
                    setHealthTimer(false);  // Updaten der Lebensanzeige
                }

                // Kollision zwischen Gegner und Geschoss
                for (int j = 0; j < player.bullets.size(); j++)
                {
                    if (player.bullets.get(j).shouldRemove())
                    {
                        player.bullets.remove(j);
                        j--;
                        continue;
                    }

                    if (enemies.get(i).collisionWith(player.bullets.get(j)))
                    {
                        if (Inventory.invBar[Inventory.selected].name.equals("Schleimpistole"))
                        {
                            enemies.get(i).looseHealth(Weapon.PURPLE_GUN_DAMAGE);
                            setHealthTimer(false);  // Updaten der Lebensanzeige
                            enemies.get(i).setWasHit(true);

                            player.bullets.get(j).setHit();
                        }
                    }
                }
                enemies.get(i).update();
            }
        } catch (Exception ex) { if (References.SHOW_EXCEPTION) System.out.println("Error: " + ex.getMessage());}

        // Crafting Rezepte
        crafting.checkRecipes();
    }

    /**
     * render       Zeichnen des Levels
     * */
    @Override
    public void render(Graphics g)
    {
        // Zeichne Hintergrund
        if (Background.opacity < 180 && !LSDMode)
        {
            GradientPaint gp = new GradientPaint(0, 0, References.WHITE_BLUE, 0, References.SCREEN_HEIGHT, References.LIGHT_BLUE);
            g2d.setPaint(gp);

            g2d.fillRect(0, 0, References.SCREEN_WIDTH, References.SCREEN_HEIGHT);
        }

        // Energie
        energyTimer.start();
        if(Player.power <= 0 && Player.health <= 0) energyTimer.stop();

        // Durst
        thirstTimer.start();
        if (Player.thirst <= 0) thirstTimer.stop();
        if(Player.isInWater) {
            Player.thirst = Player.maxThirst;
            currentThirst = ResourceLoader.thirst100;
        }

        // Verdunkle Hintergrund
        if (!isHealthCritical)
            background.render(g);

        // Zeichne Tilemap
        tileMap.render(g);

        if (isHealthCritical)
        {
            drawHealthCritical = !drawHealthCritical;

            if (drawHealthCritical)
            {
                g.setColor(new Color(255, 0, 0, 142));
                g.fillRect(0, 0, References.SCREEN_WIDTH, References.SCREEN_HEIGHT);
            }
            else
                background.render(g);
        }

        // Zeichne Statusbar
        graphics.drawImage(currentHealth, References.SCREEN_WIDTH - References.HEALTH_CELL_WIDTH - 10, 10, References.HEALTH_CELL_WIDTH, References.HEALTH_CELL_HEIGHT, null);
        graphics.drawImage(currentEnergy, References.SCREEN_WIDTH - References.ENERGY_CELL_WIDTH - 10, 20+References.HEALTH_CELL_HEIGHT, null);
        graphics.drawImage(currentThirst, References.SCREEN_WIDTH - References.THIRST_CELL_WIDTH - 10, 30+References.HEALTH_CELL_HEIGHT+References.ENERGY_CELL_HEIGHT, null);

        // Zeichne Spieler
        player.render(g);

        // Zeichne Questanzeige
        player.renderQuestbar(g);

        // Zeichne Gegner
        for (Enemy enemy : enemies)
        {
            if (enemy.getWasHit())
            {
                g.setColor(Color.RED);
                g.setFont(ResourceLoader.textFieldFont.deriveFont(30f));
                g.drawString(
                        "HIT!",
                        (int) (enemy.getX() + enemy.getXOnMap() - enemy.getWidth()/ 2),
                        (int) (enemy.getY() + enemy.getYOnMap() - enemy.getHeight()/ 2)
                );
            }
            enemy.render(g);
        }

        // Zeichne Inventar und Crafting
        inventory.render(g);
        crafting.render(g);

        if (isDead) renderDeath(g); // Zeichne Todesanzeige
    }

    /**
     * renderDeath      Zeichnen des Todesanzeige
     *
     * @param g         Graphics Objekt
     * */
    public static void renderDeath(Graphics g)
    {
        if (!isDead) return;

        g.setColor(new Color(255, 0, 0, 70));
        g.fillRect(0, 0, References.SCREEN_WIDTH, References.SCREEN_HEIGHT);

        g.setColor(Color.WHITE);
        g.setFont(ResourceLoader.textFieldFont.deriveFont(40f));
        g.drawString("DU BIST TOT!", References.SCREEN_WIDTH / 2 - g.getFontMetrics().stringWidth("DU BIST TOT!") / 2, References.SCREEN_HEIGHT / 2 - g.getFontMetrics().getHeight() / 2 - g.getFontMetrics().getLeading());

        if(tutorialOpen) {
            g.setColor(Color.GREEN);
            drawString(g, "Tutorial: " + Tutorial.getCurrentTutorial(), 10, 50);
        }

        References.GAME_OVER = true;    // Spielschleife wird hier beendet
    }

    /**
     * drawString
     * */
    public static void drawString(Graphics g, String text, int x, int y)
    {
        for (String line : text.split("\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }

    // KEYLISTENERS
    @Override
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) LSDMode = !LSDMode;
        if(e.getKeyCode() == KeyEvent.VK_T) tutorialOpen = !tutorialOpen;

        player.keyPressed(e);
        inventory.keyPressed(e);
        crafting.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) { player.keyReleased(e); }

    // MOUSELISTENERS
    @Override
    public void mouseClicked(MouseEvent e)
    {
        Tile selectedTile = tileMap.getMap().get(new Point((int) ((e.getY() - tileMap.getY()) / References.TILE_SIZE), (int) (Math.floor((e.getX() - tileMap.getX()) / References.TILE_SIZE))));
        player.mouseClicked(e, selectedTile);

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
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) { inventory.mouseWheelMoved(e); }

    @Override
    public void mouseMoved(MouseEvent e) { tileMap.mouseMoved(e); }

    // GETTER UND SETTER
    /**
     * getPlayer        Rueckgabe des Spielers
     * @return Player   Spieler
     * */
    public Player getPlayer() { return this.player; }

    /**
     * setEnergyTimer       Setzen und starten des Energietimers
     * */
    public static void setEnergyTimer()
    {
        if (Player.power > 85)
        {
            currentEnergy = ResourceLoader.energy100;
            return;
        }

        if (Player.power == 85)
            currentEnergy = ResourceLoader.energy80;
        else if (Player.power == 75)
            currentEnergy = ResourceLoader.energy70;
        else if (Player.power == 65)
            currentEnergy = ResourceLoader.energy60;
        else if (Player.power == 55)
            currentEnergy = ResourceLoader.energy50;
        else if (Player.power == 45)
            currentEnergy = ResourceLoader.energy40;
        else if (Player.power == 35)
            currentEnergy = ResourceLoader.energy30;
        else if (Player.power == 25)
            currentEnergy = ResourceLoader.energy20;
        else if (Player.power == 15)
            currentEnergy = ResourceLoader.energy10;
        else if (Player.power == 0)
        {
            currentEnergy = ResourceLoader.energy0;
            energyDown = true;

            Timer healthTimer = new Timer(1000, e ->
            {
                Player.health--;
                setHealthTimer(true);
            });

            healthTimer.start();
            if(maxHealth == 0) healthTimer.stop();
        }
    }

    /**
     * setHealthTimer       Setzen und starten des Lebenstimers
     * */
    public static void setHealthTimer(boolean checkForEnergyDown)
    {
        isHealthCritical = false;

        if (checkForEnergyDown)
            if (!energyDown) return;

        if (Player.health == Player.maxHealth)
        {
            currentHealth = ResourceLoader.health100;
            return;
        }

        if (Player.health < Player.maxHealth && Player.health >= Player.maxHealth - Player.maxHealth/5)
            currentHealth = ResourceLoader.health80;
        else if (Player.health < Player.maxHealth - Player.maxHealth/5 && Player.health >= Player.maxHealth - Player.maxHealth/4)
            currentHealth = ResourceLoader.health70;
        else if (Player.health < Player.maxHealth - Player.maxHealth/4 && Player.health >= Player.maxHealth - Player.maxHealth/3)
            currentHealth = ResourceLoader.health60;
        else if (Player.health < Player.maxHealth - Player.maxHealth/3 && Player.health >= Player.maxHealth/2)
            currentHealth = ResourceLoader.health50;
        else if (Player.health < Player.maxHealth/2 && Player.health >= Player.maxHealth - Player.maxHealth/1.7)
            currentHealth = ResourceLoader.health40;
        else if (Player.health < Player.maxHealth - Player.maxHealth/1.7 && Player.health >= Player.maxHealth - Player.maxHealth/1.5)
            currentHealth = ResourceLoader.health30;
        else if (Player.health < Player.maxHealth - Player.maxHealth/1.5 && Player.health >= Player.maxHealth - Player.maxHealth/1.3)
        {
            if(GameData.isSoundOn.equals("On") && Player.isIronManSelected) {
                Sound.jarvisSound.play();
                Sound.jarvisSound.continues();
            }
            else if (GameData.isSoundOn.equals("On")&& Player.isHulkSelected) {
                Sound.hulkBreathSound.play();
                Sound.hulkBreathSound.continues();
            }
            else if (GameData.isSoundOn.equals("On") && Player.isCaptainAmericaSelected) {
                Sound.captainAmericaEnoughSound.play();
                Sound.captainAmericaEnoughSound.continues();
            }
            else if(GameData.isSoundOn.equals("On") && Player.isThorSelected) {
                Sound.heartBeatSound.play();
                Sound.heartBeatSound.continues();
            }
            else if (GameData.isSoundOn.equals("On") && Player.isBlackWidowSelected) {
                Sound.heartBeatSound.play();
                Sound.heartBeatSound.continues();
            }
            else if(GameData.isSoundOn.equals("On")) {
                Sound.heartBeatSound.play();
                Sound.heartBeatSound.continues();
            }
            isHealthCritical = true;
            currentHealth = ResourceLoader.health20;
        }
        else if (Player.health < Player.maxHealth - Player.maxHealth/1.3 && Player.health >= Player.maxHealth - Player.maxHealth/1.1)
        {
            isHealthCritical = true;
            currentHealth = ResourceLoader.health10;
        }
        else if (Player.health <= 0)
        {
            isDead = true;
            currentHealth = ResourceLoader.health0;

            if(GameData.isSoundOn.equals("On") && Player.isIronManSelected)
            {
                Sound.jarvisSound.stop();
                Sound.jarvisDeadSound.play();
                Sound.gameSound.stop();
                Sound.ironManJumpSound.close();
            }
            else if (GameData.isSoundOn.equals("On")&& Player.isHulkSelected)
            {
                Sound.hulkBreathSound.stop();
                Sound.hulkDeathSound.play();
                Sound.gameSound.stop();
                Sound.hulkJumpSound.close();

            }
            else if (GameData.isSoundOn.equals("On") && Player.isCaptainAmericaSelected)
            {
                Sound.captainAmericaEnoughSound.stop();
                Sound.captainAmericaDeathSound.play();
                Sound.gameSound.stop();
                Sound.captainAmericaJumpSound.close();
            }
            else if (GameData.isSoundOn.equals("On") && Player.isThorSelected)
            {
                Sound.heartBeatSound.stop();
                Sound.thorDeathSound.play();
                Sound.gameSound.stop();
                Sound.thorJumpSound.close();
            }
            else if (GameData.isSoundOn.equals("On")&& Player.isBlackWidowSelected) {
                Sound.heartBeatSound.stop();
                Sound.blackWidowDeathSound.play();
                Sound.gameSound.stop();
                Sound.blackWidowJumpSound.close();
            }
            else if(GameData.isSoundOn.equals("On"))
            {
                Sound.heartBeatSound.stop();
                Sound.killSound.play();
                Sound.gameSound.stop();
                Sound.jumpSound.close();
            }
        }
    }

    /**
     * setThirstTimer       Setzen und starten des Dursttimers
     * */
    public static void setThirstTimer()
    {
        if (Player.thirst > 95)
        {
            currentThirst = ResourceLoader.thirst100;
            return;
        }

        if (Player.thirst == 95)
            currentThirst = ResourceLoader.thirst90;
        else if (Player.thirst == 85)
            currentThirst = ResourceLoader.thirst80;
        else if (Player.thirst == 75)
            currentThirst = ResourceLoader.thirst70;
        else if (Player.thirst == 65)
            currentThirst = ResourceLoader.thirst60;
        else if (Player.thirst == 55)
            currentThirst = ResourceLoader.thirst50;
        else if (Player.thirst == 45)
            currentThirst = ResourceLoader.thirst40;
        else if (Player.thirst == 35)
            currentThirst = ResourceLoader.thirst30;
        else if (Player.thirst == 25)
            currentThirst = ResourceLoader.thirst20;
        else if (Player.thirst == 15)
            currentThirst = ResourceLoader.thirst10;
        else if (Player.thirst == 0)
            currentThirst = ResourceLoader.thirst0;
        // currentHealth = ResourceLoader.health90;
        //isThirsty = true;
                /*Timer healthTimer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        k--;
                        if (isThirsty && k == 85) {
                            currentHealth = ResourceLoader.health80;
                        } else if (isThirsty && k == 75) {
                            currentHealth = ResourceLoader.health70;
                        } else if (isThirsty &&k == 65) {
                            currentHealth = ResourceLoader.health60;
                        } else if (isThirsty && k == 55) {
                            currentHealth = ResourceLoader.health50;
                        } else if (isThirsty && k == 45) {
                            currentHealth = ResourceLoader.health40;
                        } else if (isThirsty && k == 35) {
                            currentHealth = ResourceLoader.health30;
                        } else if (isThirsty && k == 25) {
                            currentHealth = ResourceLoader.health20;
                                if (GameData.isSoundOn.equals("On")){
                                    Sound.heartBeatSound.play();
                                    Sound.heartBeatSound.continues();
                                }
                        } else if (isThirsty && k == 15) {
                            currentHealth = ResourceLoader.health10;
                        } else  if (isThirsty && k == 0) {
                            currentHealth = ResourceLoader.health0;
                            if (GameData.isSoundOn.equals("On")) {
                                Sound.heartBeatSound.stop();
                                Sound.killSound.play();
                            }
                                System.out.println("Du bist tot!");
                                isDead = true;
                                renderDeath(graphics);
                        }

                    }
                });
                healthTimer.start();

                if(k == 0) {
                    healthTimer.stop();
                }*/
    }

}


