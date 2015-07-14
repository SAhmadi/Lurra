package State.Level;

import Assets.Crafting.Crafting;
import Assets.GameObjects.Enemy;
import Assets.GameObjects.Multiplayer.MPPlayer;
import Assets.GameObjects.Player;
import Assets.GameObjects.Weapon;
import Assets.Inventory.Inventory;
import Assets.World.Background;
import Assets.World.Tile;
import Assets.World.TileMap;
import GameSaves.GameData.GameData;
import GameSaves.TilemapData.TilemapData;
import Main.GamePanel;
import Main.References;
import Main.ResourceLoader;
import Main.Sound;
import State.Multiplayer.LobbyState;
import State.State;
import State.StateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Erstes Level des Spiels
 *
 * @author Sirat, Amin, Mo
 * */
public class MPLevelState extends State
{

    private Graphics2D g2d;

    public static boolean goldRushDone;

    // Hintergrundbilder - Pfad
    private Background background;

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

    private Timer energyTimer;  // Timer fuer die Energieanzeige und Lebebnsanzeige
    private Timer thirstTimer;  //Timer fuer die Durstanzeige

    // Tilemap
    public static TileMap tileMap;

    // Spieler
    private ArrayList<MPPlayer> players;
    private MPPlayer myPlayer;
    private int clientId;

    //private Player player;
    private boolean LSDMode;
    public static boolean enemyDestroyed;

    // Gegner
    private ArrayList<Enemy> enemies;

    // Inventar und Crafting
    public Inventory inventory;
    public Crafting crafting;

    /**
     * MPLevelState         Konstruktor der MPLevelState-Klasse
     *
     * @param graphics      Graphics Objekt
     * @param gamePanel     Spielinhaltsflaeche
     * @param stateManager  Zustandsmanager
     * */
    public MPLevelState(Graphics graphics, GamePanel gamePanel, StateManager stateManager, ArrayList<MPPlayer> players, int clientId, boolean isSpectator)
    {
        super(gamePanel, graphics, stateManager);

        this.players = players;
        this.clientId = clientId;
        for (MPPlayer p : this.players)
        {
            if (p.playerID == clientId-1) this.myPlayer = p;
        }

        System.out.println("myPlayer: " + myPlayer.playerID + " name: " + myPlayer.playerName);

        this.background = new Background();

        // Inventory und Crafting
        this.inventory = new Inventory(false);
        this.crafting = new Crafting();

        this.LSDMode = false;
        enemyDestroyed = false;

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

        // Neue Tilemap erstellen
        tileMap = new TileMap(10, false);

        // Speichern des Seeds
        TilemapData.seed = TileMap.seed;

        // Tilemap positionieren
        tileMap.setPosition(0, 0);

        // Spieler Positionieren
        tileMap.setPlayer(myPlayer);

        for (MPPlayer p : players)
        {
            p.setPosition(References.SCREEN_WIDTH / 2, References.SCREEN_HEIGHT / 2 - 2 * p.getHeight());
            p.setTileMap(tileMap);
        }

        tileMap.setXForTileMapStart();

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
            if (new Random().nextInt(350) == 15)
                enemies.add(new Enemy(43, 41, 32, 32, 0.3, -5.0, 5.0, -20.0, tileMap, ResourceLoader.enemyEye, myPlayer, 40, 5));

        // Hintergrund
        background.update();

        // Tilemap
        tileMap.setPosition(References.SCREEN_WIDTH / 2 - myPlayer.getX(), References.SCREEN_HEIGHT / 2 - myPlayer.getY());
        tileMap.update();

        // Spieler
        myPlayer.update();
        sendMove();

        multiplayerThread();

        // Goldrush
        goldRushWon();

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
                    enemyDestroyed = true;
                    continue;

                }

                // Kollision zwischen Gegner und Spieler
                if (enemies.get(i).collisionWith(myPlayer))
                {
                    if (myPlayer.getActiveAnimation() == Player.SWORD_NORMAL)
                    {
                        enemies.get(i).looseHealth(Weapon.SWORD_DAMAGE);
                        enemies.get(i).setWasHit(true);
                    }
                    else
                    {
                        myPlayer.looseHealth(enemies.get(i).getDamage());
                        setHealthTimer(false);  // Updaten der Lebensanzeige
                    }
                }

                // Kollision zwischen Gegner und Geschoss
                for (int j = 0; j < myPlayer.bullets.size(); j++)
                {
                    if (myPlayer.bullets.get(j).shouldRemove())
                    {
                        myPlayer.bullets.remove(j);
                        j--;
                        continue;
                    }

                    if (enemies.get(i).collisionWith(myPlayer.bullets.get(j)))
                    {
                        if (Inventory.invBar[Inventory.selected].getId() == References.PURPLE_GUN)
                        {
                            enemies.get(i).looseHealth(Weapon.PURPLE_GUN_DAMAGE);
                            setHealthTimer(false);  // Updaten der Lebensanzeige
                            enemies.get(i).setWasHit(true);

                            myPlayer.bullets.get(j).setHit();
                        }
                    }
                }
                enemies.get(i).update();
            }
        } catch (Exception ex) { ex.printStackTrace(); }

        // Crafting
        crafting.update();
    }

    /**
     * render       Zeichnen des Levels
     * */
    @Override
    public void render(Graphics2D g)
    {
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        // Zeichne Hintergrund
        if (Background.opacity < 180 && !LSDMode) {
            GradientPaint gp = new GradientPaint(0, 0, References.WHITE_BLUE, 0, References.SCREEN_HEIGHT, References.LIGHT_BLUE);
            g2d.setPaint(gp);

            g2d.fillRect(0, 0, References.SCREEN_WIDTH, References.SCREEN_HEIGHT);
        }

        // Energie
        energyTimer.start();
        if (Player.power <= 0 && Player.health <= 0) energyTimer.stop();

        // Durst
        thirstTimer.start();
        if (Player.thirst <= 0) thirstTimer.stop();
        if (myPlayer.isInWater)
        {
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
        g.drawImage(currentHealth, References.SCREEN_WIDTH - References.HEALTH_CELL_WIDTH - 10, 10, References.HEALTH_CELL_WIDTH, References.HEALTH_CELL_HEIGHT, null);
        g.drawImage(currentEnergy, References.SCREEN_WIDTH - References.ENERGY_CELL_WIDTH - 10, 20 + References.HEALTH_CELL_HEIGHT, null);
        g.drawImage(currentThirst, References.SCREEN_WIDTH - References.THIRST_CELL_WIDTH - 10, 30 + References.HEALTH_CELL_HEIGHT + References.ENERGY_CELL_HEIGHT, null);

        // Zeichne Spieler
        for (MPPlayer p : players)
            p.render(g);

        // Zeichne Questanzeige
        myPlayer.renderQuestbar(g);

        // Zeichne Gegner
        for (Enemy enemy : enemies)
        {
            if (enemy.getWasHit())
            {
                g.setColor(Color.RED);
                g.setFont(ResourceLoader.textFieldFont.deriveFont(30f));
                g.drawString(
                        "HIT!",
                        (int) (enemy.getX() + enemy.getXOnMap() - enemy.getWidth() / 2),
                        (int) (enemy.getY() + enemy.getYOnMap() - enemy.getHeight() / 2)
                );
            }
            enemy.render(g);
        }

        // Zeichne Inventar und Crafting
        inventory.render(g);
        crafting.render(g);

        if(goldRushDone)
        {
            g.setColor(Color.RED);
            g.setFont(ResourceLoader.textFieldFontBold);
            g.drawString("Spiel vorbei", References.SCREEN_WIDTH/2- g.getFontMetrics(g.getFont()).stringWidth("Spiel vorbei")/2, References.SCREEN_HEIGHT/2 - g.getFont().getSize()/2);
        }

        if (isDead) renderDeath(g); // Zeichne Todesanzeige

    }

    /**
     * goldRushWon      Ueberpruft, ob das Spiel Goldrush gewonnen wurde
     * */
    private void goldRushWon()
    {
        if (LobbyState.goldRushSelected && clientId == 1)
        {
            for (int i = 0; i < Inventory.invBar.length; i++)
            {
                if (Inventory.invBar[i].getId() == References.GOLD)
                {
                    //LobbyState.pw.write(LobbyState.playerName +" hat das spiel gewonnen \n ");
                    //LobbyState.pw.write("rmPl " +LobbyState.playerName +"\n");
                    if (Inventory.invBar[i].getCount() == 8)
                    {
                        LobbyState.pw.println(LobbyState.playerName + " hat verloren!");
                        goldRushDone = true;
                    }
                }
            }
        }
    }

    /**
     * multiplayerThread
     * */
    public void multiplayerThread()
    {
        new Thread()
        {
            @Override
            public void run()
            {
                String line;
                try
                {
                    while ((line = LobbyState.br.readLine()) != null)
                    {
                        receiveMove(line);
                    }
                }
                catch (IOException ex) { ex.printStackTrace(); }
            }

        }.start();
    }

    /**
     * sendMove
     * */
    private void sendMove()
    {
        LobbyState.pw.println("plyMove:" + myPlayer.playerID + ":" + myPlayer.getX() + ":" + myPlayer.getY());
    }

    /**
     * receiveMove
     * */
    private void receiveMove(String line)
    {
        if (line.contains("plyMove"))
        {
            int id = Integer.parseInt(line.split(":")[1]);
            double x = Double.parseDouble(line.split(":")[2]);
            double y = Double.parseDouble(line.split(":")[3]);

            for (MPPlayer p : players)
            {
                if (p.playerID == id) p.setPosition(x, y);
            }
        }
    }

    /**
     * renderDeath      Zeichnen des Todesanzeige
     *
     * @param g         Graphics Objekt
     * */
    public void renderDeath(Graphics g)
    {
        if (!isDead) return;

        g.setColor(new Color(255, 0, 0, 70));
        g.fillRect(0, 0, References.SCREEN_WIDTH, References.SCREEN_HEIGHT);

        g.setColor(Color.WHITE);
        g.setFont(ResourceLoader.textFieldFont.deriveFont(40f));

        // Todesanzeigen
        String[] deathNotes = new String[6];
        deathNotes[0] = "Du wurdest viergeteilt!";
        deathNotes[1] = "Du wurdest zerfleischt!";
        deathNotes[2] = "Schwächling!";
        deathNotes[3] = "Er opferte sich fuer nichts..";
        deathNotes[4] = "Seine Gedärme lagen nur noch auf dem Boden..";
        deathNotes[5] = "DU BIST TOT";

        int rand = new Random().nextInt(deathNotes.length);
        if (rand < deathNotes.length)
        {
            g.drawString(deathNotes[rand], References.SCREEN_WIDTH / 2 - g.getFontMetrics().stringWidth(deathNotes[rand]) / 2, References.SCREEN_HEIGHT / 2 - g.getFontMetrics().getHeight() / 2 - g.getFontMetrics().getLeading());
        }

        // Spielschleife wird hier beendet
        References.GAME_OVER = true;
    }

    // KEYLISTENERS
    @Override
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) LSDMode = !LSDMode;

        myPlayer.keyPressed(e);
        inventory.keyPressed(e);
        crafting.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) { myPlayer.keyReleased(e); }

    // MOUSELISTENERS
    @Override
    public void mouseClicked(MouseEvent e)
    {
        Tile selectedTile = tileMap.getMap().get(new Point((int) ((e.getY() - tileMap.getY()) / References.TILE_SIZE), (int) (Math.floor((e.getX() - tileMap.getX()) / References.TILE_SIZE))));
        myPlayer.mouseClicked(e, selectedTile);

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
    public void mouseWheelMoved(MouseWheelEvent e)
    {
        inventory.mouseWheelMoved(e);
        crafting.mouseWheelMoved(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) { tileMap.mouseMoved(e); }

    // GETTER UND SETTER
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
     * setHealthTimer               Setzen und starten des Lebenstimers
     *
     * @param checkForEnergyDown    Wert ob keine Energie mehr vorhanden
     * */
    public static void setHealthTimer(boolean checkForEnergyDown)
    {
        isHealthCritical = false;

        if (checkForEnergyDown)
            if (!energyDown) return;

        if (Player.health == Player.maxHealth)
        {
            currentHealth = ResourceLoader.health100;
            if (GameData.isSoundOn.equals("On") && Player.isIronManSelected) {
                Sound.jarvisSound.stop();
            } else if (GameData.isSoundOn.equals("On") && Player.isSpecialSelected) {
                Sound.specialHeartBeatSound.stop();
            } else if (GameData.isSoundOn.equals("On") && Player.isHulkSelected) {
                Sound.hulkBreathSound.stop();
            } else if (GameData.isSoundOn.equals("On") && Player.isCaptainAmericaSelected) {
                Sound.heartBeatSound.stop();
            } else if (GameData.isSoundOn.equals("On") && Player.isThorSelected) {
                Sound.heartBeatSound.stop();
            } else if (GameData.isSoundOn.equals("On") && Player.isBlackWidowSelected) {
                Sound.heartBeatSound.stop();
            } else if (GameData.isSoundOn.equals("On") && Player.isSpecialSelected) {
                Sound.specialHeartBeatSound.stop();
            } else if (GameData.isSoundOn.equals("On")) {
                Sound.heartBeatSound.stop();
            }

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
                Sound.heartBeatSound.play();
                Sound.heartBeatSound.continues();
            }
            else if(GameData.isSoundOn.equals("On") && Player.isThorSelected) {
                Sound.heartBeatSound.play();
                Sound.heartBeatSound.continues();
            }
            else if (GameData.isSoundOn.equals("On") && Player.isBlackWidowSelected) {
                Sound.heartBeatSound.play();
                Sound.heartBeatSound.continues();
            }
            else if (GameData.isSoundOn.equals("On") && Player.isSpecialSelected) {
                Sound.specialHeartBeatSound.play();
                Sound.specialHeartBeatSound.continues();
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
            else if (GameData.isSoundOn.equals("On") && Player.isSpecialSelected) {
                Sound.specialHeartBeatSound.stop();
                Sound.specialDeathSound.play();
                Sound.gameSound.stop();
                Sound.specialJumpSound.close();
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

        if (Player.thirst == 95) currentThirst = ResourceLoader.thirst90;
        else if (Player.thirst == 85) currentThirst = ResourceLoader.thirst80;
        else if (Player.thirst == 75) currentThirst = ResourceLoader.thirst70;
        else if (Player.thirst == 65) currentThirst = ResourceLoader.thirst60;
        else if (Player.thirst == 55) currentThirst = ResourceLoader.thirst50;
        else if (Player.thirst == 45) currentThirst = ResourceLoader.thirst40;
        else if (Player.thirst == 35) currentThirst = ResourceLoader.thirst30;
        else if (Player.thirst == 25) currentThirst = ResourceLoader.thirst20;
        else if (Player.thirst == 15) currentThirst = ResourceLoader.thirst10;
        else if (Player.thirst == 0) currentThirst = ResourceLoader.thirst0;
        // currentHealth = ResourceLoader.health90;
        //isThirsty = true;
    }

}


