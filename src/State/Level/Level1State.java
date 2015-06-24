package State.Level;


import Assets.Crafting.Crafting;
import Assets.GameObjects.Player;
import Assets.Inventory.Inventory;
import Assets.World.Tile;
import Assets.World.TileMap;
import Main.GamePanel;
import Main.References;
import Main.ResourceLoader;
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
public class Level1State extends State  {

    // Inhaltsflaeche, Graphics-Obj und Zustands-Manger
    protected GamePanel gamePanel;
    public static Graphics graphics;
    protected StateManager stateManager;

    private boolean continueLevel;
    public boolean isNight = false;

    // Hintergrundbilder - Pfad
    private BufferedImage backgroundImage;
    private String level1DayBackgroundPath = "/img/grassbg1.gif";

    public static BufferedImage currentHealth;
    public static String currentHealthPath = "/img/Health/Health_100.png";

    public static BufferedImage currentEnergy;
    public static String currentEnergyPath = "/img/Energy/energy_100.png";
    public static int h = Player.getMaxPower();
    public static int k = Player.getMaxHealth();
    static boolean energyDown = false;

    public static Timer timer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            h--;
            if (h == 85) {
                currentEnergyPath = "/img/Energy/energy_80.png";
            } else if (h == 75) {
                currentEnergyPath = "/img/Energy/energy_70.png";
            } else if (h == 65) {
                currentEnergyPath = "/img/Energy/energy_60.png";
            } else if (h == 55) {
                currentEnergyPath = "/img/Energy/energy_50.png";
            } else if (h == 45) {
                currentEnergyPath = "/img/Energy/energy_40.png";
            } else if (h == 35) {
                currentEnergyPath = "/img/Energy/energy_30.png";
            } else if (h == 25) {
                currentEnergyPath = "/img/Energy/energy_20.png";
            } else if (h == 15) {
                currentEnergyPath = "/img/Energy/energy_10.png";
            } else if (h == 0) {
                currentEnergyPath = "/img/Energy/energy_0.png";
                currentHealthPath = "/img/Health/Health_90.png";
                energyDown = true;
                Timer healthTimer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        k--;
                        if (energyDown && k == 85) {
                            currentHealthPath = "/img/Health/Health_80.png";
                        } else if (energyDown && k == 75) {
                            currentHealthPath = "/img/Health/Health_70.png";
                        } else if (energyDown &&k == 65) {
                            currentHealthPath = "/img/Health/Health_60.png";
                        } else if (energyDown && k == 55) {
                            currentHealthPath = "/img/Health/Health_50.png";
                        } else if (energyDown && k == 45) {
                            currentHealthPath = "/img/Health/Health_40.png";
                        } else if (energyDown && k == 35) {
                            currentHealthPath = "/img/Health/Health_30.png";
                        } else if (energyDown && k == 25) {
                            currentHealthPath = "/img/Health/Health_20.png";
                        } else if (energyDown && k == 15) {
                            currentHealthPath = "/img/Health/Health_10.png";

                        }

                    }
                });
                healthTimer.start();

                if(k == 0)
                    healthTimer.stop();
            }






        }
    });


    public static Tile tile;

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
    public static  Inventory inventory;
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

        init();
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

        try {
            currentHealth = ImageIO.read(ResourceLoader.class.getResourceAsStream(currentHealthPath));

        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            currentEnergy = ImageIO.read(ResourceLoader.class.getResourceAsStream(currentEnergyPath));
            timer.start();
            if(h == 0 && k == 0)
                timer.stop();


        } catch (IOException e) {
            e.printStackTrace();
        }




        // Zeichne Tag Hintergrundverlauf
//        g2d = (Graphics2D) g;
//        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
//        gradientPaint = new GradientPaint(0, 0, DAY_COLOR_1, 0, References.SCREEN_HEIGHT, DAY_COLOR_2);
//        g2d.setPaint(gradientPaint);
//        g2d.fillRect(0, 0, References.SCREEN_WIDTH, References.SCREEN_HEIGHT);


        tileMap.render(g);
        graphics.drawImage(currentHealth, 870, -5, null);
        graphics.drawImage(currentEnergy, 925, 50, null);
        player.render(g);

        inventory.render(g);
        crafting.render(g);
    }


    public static void eat() {
        BufferedImage burgerImage = ResourceLoader.burger;
        for (int i = 0; i < inventory.invBar.length; i++) {
            if((inventory.invBar[i].getTileImage() == burgerImage )) {
                currentEnergyPath = "/img/Energy/energy_100.png";
                h = Player.getMaxHealth();
                timer.start();
                System.out.println("Lecker!");
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


