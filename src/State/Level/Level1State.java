package State.Level;

import Assets.Crafting.Crafting;
import Assets.GameObjects.Player;
import Assets.Inventory.Inventory;
import Assets.World.Background;
import Assets.World.Tile;
import Assets.World.TileMap;
import GameSaves.GameData.GameData;
import Main.*;
import State.State;
import State.StateManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Erstes Level des Spiels
 *
 * @author Sirat
 * */
public class Level1State extends State
{
    // Inhaltsflaeche, Graphics-Obj und Zustands-Manger
    protected GamePanel gamePanel;
    public static Graphics graphics;
    protected StateManager stateManager;

    private boolean continueLevel;
    public boolean isNight = false;

    // Hintergrundbilder - Pfad
    private Background background;
    private Image backgroundImage;
    private String level1DayBackgroundPath = "/img/grassbg1.jpg";

    // Statusbar
    public static BufferedImage currentHealth;
    public static BufferedImage currentEnergy;

    public static int h = Player.getMaxPower();
    public static int k = Player.getMaxHealth();
    public static int t = 100;
    static boolean energyDown = false;
    public static BufferedImage currentThirst;

    public static boolean isDead = false;


    /**
     * Timer         Der Timer für die Energieanzeige
     */
    public static Timer energyTimer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            h--;
            if (h == 85) {
                currentEnergy = ResourceLoader.energy80;
            } else if (h == 75) {
                currentEnergy = ResourceLoader.energy70;
            } else if (h == 65) {
                currentEnergy = ResourceLoader.energy60;
            } else if (h == 55) {
                currentEnergy = ResourceLoader.energy50;
            } else if (h == 45) {
                currentEnergy = ResourceLoader.energy40;
            } else if (h == 35) {
                currentEnergy = ResourceLoader.energy30;
            } else if (h == 25) {
                currentEnergy = ResourceLoader.energy20;
            } else if (h == 15) {
                currentEnergy = ResourceLoader.energy10;
            } else if (h == 0) {
                currentEnergy = ResourceLoader.energy0;
                currentHealth = ResourceLoader.health90;
                energyDown = true;

                Timer healthTimer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        k--;
                        if (energyDown && k == 85) {
                            currentHealth = ResourceLoader.health80;
                        } else if (energyDown && k == 75) {
                            currentHealth = ResourceLoader.health70;
                        } else if (energyDown &&k == 65) {
                            currentHealth = ResourceLoader.health60;
                        } else if (energyDown && k == 55) {
                            currentHealth = ResourceLoader.health50;
                        } else if (energyDown && k == 45) {
                            currentHealth = ResourceLoader.health40;
                        } else if (energyDown && k == 35) {
                            currentHealth = ResourceLoader.health30;
                        } else if (energyDown && k == 25) {
                            currentHealth = ResourceLoader.health20;
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
                            else if(GameData.isSoundOn.equals("On")) {
                                Sound.heartBeatSound.play();
                                Sound.heartBeatSound.continues();
                            }
                        } else if (energyDown && k == 15) {
                            currentHealth = ResourceLoader.health10;
                        } else if (energyDown && k == 0) {

                            currentHealth = ResourceLoader.health0;
                            if(GameData.isSoundOn.equals("On") && Player.isIronManSelected) {
                                Sound.jarvisSound.stop();
                                Sound.jarvisDeadSound.play();
                                Sound.gameSound.stop();
                            }
                            else if (GameData.isSoundOn.equals("On")&& Player.isHulkSelected) {
                                Sound.hulkBreathSound.stop();
                                Sound.hulkDeathSound.play();
                                Sound.gameSound.stop();
                            } else if (GameData.isSoundOn.equals("On") && Player.isCaptainAmericaSelected) {
                                Sound.captainAmericaEnoughSound.stop();
                                Sound.captainAmericaDeathSound.play();
                                Sound.gameSound.stop();
                            }
                            else if(GameData.isSoundOn.equals("On")) {
                                Sound.heartBeatSound.stop();
                                Sound.killSound.play();
                                Sound.gameSound.stop();
                            }
                                System.out.println("Du bist tot, Bitch!");
                                isDead = true;
                                renderDeath(graphics);

                        }
                    }
                });
                healthTimer.start();

                if(k == 0)
                    healthTimer.stop();
            }
        }
    });

    public static boolean isThirsty = false;

    //Timer, der die Durst-Leiste nach und nach leert und bei leerer Durst-Leiste das Leben reduziert
    public static Timer thirstTimer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            t--;
            if (t == 95) {
                currentThirst = ResourceLoader.thirst90;
            } else if (t == 85) {
                currentThirst = ResourceLoader.thirst80;
            } else if (t == 75) {
                currentThirst = ResourceLoader.thirst70;
            } else if (t == 65) {
                currentThirst = ResourceLoader.thirst60;
            } else if (t == 55) {
                currentThirst = ResourceLoader.thirst50;
            } else if (t == 45) {
                currentThirst = ResourceLoader.thirst40;
            } else if (t == 35) {
                currentThirst = ResourceLoader.thirst30;
            } else if (t == 25) {
                currentThirst = ResourceLoader.thirst20;
            } else if (t == 15) {
                currentThirst = ResourceLoader.thirst10;
            } else if (t== 0) {
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
                                System.out.println("Du bist tot, Bitch");
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
    });

    // Tilemap
    public static TileMap tileMap;
    private String levelMapPath = "res/xml/playerLevelSaves/";

    // Spieler
    private Player player;

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

        //statusbarImage = new ImageIcon("res/img/Menu/statusbar.png").getImage();
        init();
        currentHealth = ResourceLoader.health100;
        currentEnergy = ResourceLoader.energy100;
        currentThirst = ResourceLoader.thirst100;

    }

    /**
     * init     Initialisieren des Levels
     * */
    @Override
    public void init() {
        // Hintergrundbild
        try { this.backgroundImage = ImageIO.read(getClass().getResourceAsStream(level1DayBackgroundPath)); }
        catch (IOException ignored) {}

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
        player.setPosition(
                References.SCREEN_WIDTH / 2,
                References.SCREEN_HEIGHT / 2 - 2 * player.getHeight()
        );
        tileMap.xForTileMapStart = player.getX();
    }

    /**
     * update       Aktualisieren der Levelinhalte
     * */
    @Override
    public void update()
    {
        // Hintergrund
        background.update();

        // Tilemap
        tileMap.setPosition(References.SCREEN_WIDTH / 2 - player.getX(), References.SCREEN_HEIGHT / 2 - player.getY());
        tileMap.update();

        // Spieler
        player.update();

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
        if (Background.opacity < 180)
            graphics.drawImage(backgroundImage, 0, 0, References.SCREEN_WIDTH, References.SCREEN_HEIGHT, null);

        // Energie
        energyTimer.start();
        if(h == 0 && k == 0)
                energyTimer.stop();

        // Durst
        thirstTimer.start();
        if (t == 0 )
            thirstTimer.stop();

        // Verdunkle Hintergrund
        background.render(g);

        // Zeichne Tilemap
        tileMap.render(g);

        // Zeichne Statusbar
        graphics.drawImage(currentHealth, References.SCREEN_WIDTH - currentHealth.getWidth() - 10, 5, null);
        graphics.drawImage(currentEnergy, References.SCREEN_WIDTH - currentEnergy.getWidth() - 10, 40, null);
        graphics.drawImage(currentThirst, References.SCREEN_WIDTH - currentThirst.getWidth() - 10, 80, null);

        // Zeichne Spieler
        player.render(g);
        //g.drawImage(statusbarImage, 0, 0, null);
        player.renderStatusbar(g);

        // Zeichne Inventar und Crafting
        inventory.render(g);
        crafting.render(g);
    }

    /**
     *renderDeath       Zeichnen der Todesanzeige
     */
    public static void renderDeath(Graphics gr) {
        if(isDead) {
            gr.setColor(Color.RED);
            gr.setFont(CustomFont.createCustomFont("Munro.ttf", 18f));
            gr.drawString("DU BIST TOT, BITCH", References.SCREEN_WIDTH/2-50, References.SCREEN_HEIGHT/2-50);
        }
    }

    /**
     * consume          Essen
     * */
    public static void consume()
    {
        for (int i = 0; i < Inventory.invBar.length; i++)
        {
            if ((Inventory.invBar[i].tileImage == ResourceLoader.burger))
            {
                if (GameData.isSoundOn.equals("On")) Sound.eatSound.play();

                currentEnergy = ResourceLoader.energy100;
                h = Player.getMaxPower();
                energyDown = false;
                isThirsty = false;
                energyTimer.start();
                thirstTimer.start();
                System.out.println("Lecker!");
                Inventory.invBar[i].name = "null";
                Inventory.invBar[i].setTileImage();
                Inventory.invBar[i].wasEaten = true;
            }
            else if (Inventory.invBar[i].tileImage == ResourceLoader.healthPotion)
            {
                if (GameData.isSoundOn.equals("On")) {
                    Sound.drinkSound.play();
                    Sound.heartBeatSound.stop();
                }

                currentHealth = ResourceLoader.health100;
                k = Player.getMaxHealth();
                energyDown = false;
                isThirsty = false;
                energyTimer.start();
                thirstTimer.start();
                Inventory.invBar[i].name = "null";
                Inventory.invBar[i].setTileImage();
                Inventory.invBar[i].wasEaten = true;
                System.out.println("Leben geheilt!");
            }
        }

    }

    // KEYLISTENERS
    @Override
    public void keyPressed(KeyEvent e)
    {
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

}


