package State.Level;


import Assets.Crafting.Crafting;
import Assets.GameObjects.Player;
import Assets.Inventory.Inventory;
import Assets.World.Tile;
import Assets.World.TileMap;
import GameSaves.GameData.GameData;
import Main.GamePanel;
import Main.References;
import Main.ResourceLoader;
import Main.Sound;
import State.State;
import State.StateManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


/*
* Level1State - Erstes Level
* */
public class Level1State extends State {

    // Inhaltsflaeche, Graphics-Obj und Zustands-Manger
    protected GamePanel gamePanel;
    public static Graphics graphics;
    protected StateManager stateManager;

    private boolean continueLevel;
    public boolean isNight = false;

    // Hintergrundbilder - Pfad
    private BufferedImage backgroundImage;
   // private Image statusbarImage;
    private String level1DayBackgroundPath = "/img/grassbg1.gif";

    public static BufferedImage currentHealth;


    public static BufferedImage currentEnergy;

    public static int h = Player.getMaxPower();
    public static int k = Player.getMaxHealth();
    public static int t = 100;
    static boolean energyDown = false;
    public static boolean wasEaten = false;

    private boolean gotEp = false;



    public static BufferedImage currentThirst;


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
                            if(GameData.isSoundOn.equals("On")) {
                                Sound.heartBeatSound.play();
                                Sound.heartBeatSound.continues();
                            }
                        } else if (energyDown && k == 15) {
                            currentHealth = ResourceLoader.health10;
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
                currentHealth = ResourceLoader.health90;
                isThirsty = true;
                Timer healthTimer = new Timer(1000, new ActionListener() {
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
                        }

                    }
                });
                healthTimer.start();

                if(k == 0) {
                    healthTimer.stop();
                }
            }

        }
    });





//    private Graphics2D g2d;
//    private GradientPaint gradientPaint;
//    private final Color DAY_COLOR_1 = new Color(150, 255, 249);
//    private final Color DAY_COLOR_2 = new Color(250, 255, 255);

    /*
    * TileMap
    * */
    public static TileMap tileMap;
    private String levelMapPath = "res/xml/playerLevelSaves/";

    /*
    * Tile
    * */
    private Rectangle tileRectangle;

    /*
    * Player Objekt
    * */
    public static Player player;
    public BufferedImage playerImage;
    private Rectangle playerRectangle;

    /*
    * Inventory and Crafting
    * */
    public static Inventory inventory;
    public Crafting crafting;

    /*
    * Konstruktor - Initialisieren
    * */
    public Level1State(Graphics graphics, GamePanel gamePanel, StateManager stateManager, boolean continueLevel) {
        this.gamePanel = gamePanel;
        this.graphics = graphics;
        this.stateManager = stateManager;

        // Inventory
        this.inventory = new Inventory();

        // Crafting
        this.crafting = new Crafting();


        // Crafting-Button;
        //this.crafting = new Crafting();

        this.continueLevel = continueLevel;

        //statusbarImage = new ImageIcon("res/img/Menu/statusbar.png").getImage();
        init();
        currentHealth = ResourceLoader.health100;
        currentEnergy = ResourceLoader.energy100;
        currentThirst = ResourceLoader.thirst100;

    }

    /*
    * Eigentliches Initialisieren
    * */
    @Override
    public void init() {
        tileMap = new TileMap(20);
        tileMap.setPosition(0, 0);

        // Spiel Fortsetzen oder Neues Spiel
/*
        if(continueLevel) {
            tileMap.levelLoad(PlayerData.name);
            //InventoryDataLoad.XMLRead(PlayerData.name);
            //inventory.loadCells();
        }
        else
            tileMap.generateMap(ScreenDimensions.WIDTH / 100);
*/

        // Positioniere Spieler
        player = new Player(22, 41, 16, 16, 0.5, -5.0, 8.0, -20.0, tileMap);
        TileMap.ownPlayerInstance = player;
        player.setPosition(
                References.SCREEN_WIDTH / 2,
                References.SCREEN_HEIGHT / 2 - 2 * player.getHeight()
        );
    }

    /*
    * update
    * */
    @Override
    public void update() {
        tileMap.setPosition(References.SCREEN_WIDTH / 2 - player.getX(), References.SCREEN_HEIGHT / 2 - player.getY());
        tileMap.update();

        player.update();

        // Crafting Rezepte
        crafting.checkRecipes();
    }

    /*
    * render
    * */
    @Override
    public void render(Graphics g) {
        // Zeichne Hintergrund
        try {
            this.backgroundImage = ImageIO.read(getClass().getResourceAsStream(level1DayBackgroundPath));
            graphics.drawImage(backgroundImage, 0, 0, References.SCREEN_WIDTH, References.SCREEN_HEIGHT, null);
        } catch (IOException ex) {
            ex.printStackTrace();
        }









            energyTimer.start();
            if(h == 0 && k == 0)
                energyTimer.stop();



            thirstTimer.start();
            if (t == 0 )
                thirstTimer.stop();








        // Zeichne Tag Hintergrundverlauf
//        g2d = (Graphics2D) g;
//        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
//        gradientPaint = new GradientPaint(0, 0, DAY_COLOR_1, 0, References.SCREEN_HEIGHT, DAY_COLOR_2);
//        g2d.setPaint(gradientPaint);
//        g2d.fillRect(0, 0, References.SCREEN_WIDTH, References.SCREEN_HEIGHT);


        tileMap.render(g);
        graphics.drawImage(currentHealth, References.SCREEN_WIDTH-currentHealth.getWidth()-10, 5, null);
        graphics.drawImage(currentEnergy, References.SCREEN_WIDTH-currentEnergy.getWidth()-10, 40, null);
        graphics.drawImage(currentThirst, References.SCREEN_WIDTH-currentThirst.getWidth()-10, 80, null);


        player.render(g);
        //g.drawImage(statusbarImage, 0, 0, null);
        player.renderStatusbar(g);

        inventory.render(g);
        crafting.render(g);
    }




    public static void consume() {
        BufferedImage burgerImage = ResourceLoader.burger;
        BufferedImage healthPotionImage = ResourceLoader.healthPotion;
        for (int i = 0; i < inventory.invBar.length; i++) {
            if((inventory.invBar[i].getTileImage() == burgerImage )) {
                if (GameData.isSoundOn.equals("On")) {
                    Sound.eatSound.play();
                }
                currentEnergy = ResourceLoader.energy100;
                h = Player.getMaxPower();
                energyDown = false;
                isThirsty = false;
                energyTimer.start();
                thirstTimer.start();
                System.out.println("Lecker!");
                inventory.invBar[i].name = "null";
                inventory.invBar[i].setTileImage();
                inventory.invBar[i].wasEaten = true;


            } else if (inventory.invBar[i].getTileImage() == healthPotionImage ) {
                if(GameData.isSoundOn.equals("On")){
                    Sound.drinkSound.play();
                    Sound.heartBeatSound.stop();
                }
                currentHealth = ResourceLoader.health100;
                k = Player.getMaxHealth();
                energyDown = false;
                isThirsty = false;
                energyTimer.start();
                thirstTimer.start();
                inventory.invBar[i].name = "null";
                inventory.invBar[i].setTileImage();
                inventory.invBar[i].wasEaten = true;
                System.out.println("Leben geheilt!");

            }
        }

    }





    /*
        * EventListener
        * */
    @Override
    public void keyPressed(KeyEvent e) {
        player.keyPressed(e);
        inventory.keyPressed(e);
        crafting.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        player.keyReleased(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Tile selectedTile = tileMap.getMap().get(new Point((int) ((e.getY() - tileMap.getY()) / References.TILE_SIZE), (int) (Math.floor((e.getX() - tileMap.getX()) / References.TILE_SIZE))));
        player.mouseClicked(e, selectedTile);


        tileMap.mouseClicked(e);
        inventory.mouseClicked(e);
        crafting.mouseClicked(e);


    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        inventory.mouseWheelMoved(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        tileMap.mouseMoved(e);
    }

    /**
     * Getter und Setter Methoden
     */
    public Player getPlayer() {
        return this.player;
    }




}


