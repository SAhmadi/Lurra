package Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


/**
 * Laden der Resourcen, die fuer das Spiel benoetigt werden
 *
 * @author Sirat
 * */
public class ResourceLoader
{

    /*
    * FONT
    * */
    public static Font textFieldFont;
    public static Font inventoryItemFont;

    /*
    * MENU
    * */
    public static BufferedImage menuBackground;
    public static BufferedImage menuIlandBackground;
    public static BufferedImage menuTitleImage;

    public static ImageIcon avatarButton, avatarButtonPressed;
    public static ImageIcon backButton, backButtonPressed;
    public static ImageIcon closeButton, closeButtonPressed;
    public static ImageIcon continueGameButton, continueGameButtonPressed;
    public static ImageIcon mainMenuButton, mainMenuButtonPressed;
    public static ImageIcon newGameButton, newGameButtonPressed;
    public static ImageIcon offButton, offButtonPressed;
    public static ImageIcon onButton, onButtonPressed;
    public static ImageIcon playLocalButton, playLocalButtonPressed;
    public static ImageIcon playOnlineButton, playOnlineButtonPressed;
    public static ImageIcon saveButton, saveButtonPressed;
    public static ImageIcon settingsButton, settingsButtonPressed;
    public static ImageIcon soundButton, soundButtonPressed;
    public static ImageIcon startGameButton, startGameButtonPressed;

    // Multiplayer
    public static ImageIcon createOnlineGame, createOnlineGamePressed;
    public static ImageIcon joinOnlineGame, joinOnlineGamePressed;
    public static ImageIcon watchGame, watchGamePressed;

    public static ImageIcon maleCharacterButtonUnactive, maleCharacterButtonActive;
    public static ImageIcon femaleCharacterButtonUnactive, femaleCharacterButtonActive;

    /*
    * Tiles
    * und andere Sets
    * */
    private static BufferedImage tileSet;
    private static BufferedImage heartSet;
    private static BufferedImage energySet;
    private static BufferedImage thirstSet;
    // GrasTile
    public static BufferedImage gras;
    public static BufferedImage grasWithFlower;

    // DirtTile
    public static BufferedImage dirt;
    public static BufferedImage dirtMidDark;
    public static BufferedImage dirtDark;

    // StoneTile
    public static BufferedImage stoneWhiteGrass;
    public static BufferedImage stoneWhite1;
    public static BufferedImage stoneWhite2;
    public static BufferedImage stoneWhite3;
    public static BufferedImage stoneWhiteLeafs;

    public static BufferedImage stoneRed1;
    public static BufferedImage stoneRed2;
    public static BufferedImage stoneRed3;
    public static BufferedImage stoneRedLeafs;
    public static BufferedImage stoneRed_Destroyed;

    // Waasser, Lava, Eis
    public static BufferedImage waterTop;
    public static BufferedImage waterTop2;
    public static BufferedImage water;
    public static BufferedImage lavaTop;
    public static BufferedImage lava;
    public static BufferedImage iceTop;
    public static BufferedImage ice;

    // Kupfer, Silber und Gold
    public static BufferedImage gold;
    public static BufferedImage ion;
    public static BufferedImage silver;
    public static BufferedImage copper;
    public static BufferedImage ruby;
    public static BufferedImage saphire;
    public static BufferedImage smaragd;
    public static BufferedImage diamond;

    //Lebens-, Hunge- und Durstleiste
    public static BufferedImage health100;
    public static BufferedImage health90;
    public static BufferedImage health80;
    public static BufferedImage health70;
    public static BufferedImage health60;
    public static BufferedImage health50;
    public static BufferedImage health40;
    public static BufferedImage health30;
    public static BufferedImage health20;
    public static BufferedImage health10;

    public static BufferedImage energy100;
    public static BufferedImage energy90;
    public static BufferedImage energy80;
    public static BufferedImage energy70;
    public static BufferedImage energy60;
    public static BufferedImage energy50;
    public static BufferedImage energy40;
    public static BufferedImage energy30;
    public static BufferedImage energy20;
    public static BufferedImage energy10;
    public static BufferedImage energy0;

    public static BufferedImage thirst100;
    public static BufferedImage thirst90;
    public static BufferedImage thirst80;
    public static BufferedImage thirst70;
    public static BufferedImage thirst60;
    public static BufferedImage thirst50;
    public static BufferedImage thirst40;
    public static BufferedImage thirst30;
    public static BufferedImage thirst20;
    public static BufferedImage thirst10;
    public static BufferedImage thirst0;




    //Essen
    public static BufferedImage burger;

    //Tr�nke
    public static BufferedImage healthPotion;

    // Explosion
    public static BufferedImage blackPowder;
    public static BufferedImage tnt;

    // Einrichtung
    public static BufferedImage woodenBoard;
    public static BufferedImage woodenBench;
    public static BufferedImage stoneBench;
    public static BufferedImage stoneOven;

    // Waffen
    public static BufferedImage axe;
    public static BufferedImage hammer;
    public static BufferedImage stonePick;
    public static BufferedImage sword;
    public static BufferedImage arrow;
    public static BufferedImage bow;


    public static BufferedImage gunPurple;
    public static BufferedImage bulletGunPurple;



    // Holz und Blatt
    public static BufferedImage treeTrunkRoot;
    public static BufferedImage treeTrunk;
    public static BufferedImage treeTrunkRight;
    public static BufferedImage treeTrunkLeft;
    public static BufferedImage treeTrunkTop;

    public static BufferedImage leafStart;
    public static BufferedImage leaf;
    public static BufferedImage leafEnd;

    /*
    * InventoryBar
    * */
    public static BufferedImage inventoryBarCellSelected;
    public static BufferedImage inventoryBarCellUnselected;


    /**
     * loadResources        Laden der Resourcen
     * */
    public static void loadResources()
    {
        try
        {
            /*
            * FONT
            * */
            textFieldFont = CustomFont.createCustomFont("Munro.ttf", 18f);
            inventoryItemFont = new Font("Munro", Font.BOLD, 14);

            /*
            * MENU
            * */
            menuBackground = ImageIO.read(ResourceLoader.class.getResourceAsStream("/img/Menu/menuBackground.jpg"));
            menuIlandBackground = ImageIO.read(ResourceLoader.class.getResourceAsStream("/img/Menu/menuIlandBackground.png"));
            menuTitleImage = ImageIO.read(ResourceLoader.class.getResourceAsStream("/img/Menu/lurraTitle.png"));

            avatarButton = new ImageIcon("res/img/Menu/MenuButtons/avatarButton.png");
            avatarButtonPressed = new ImageIcon("res/img/Menu/MenuButtons/avatarButton_Pressed.png");

            backButton = new ImageIcon("res/img/Menu/MenuButtons/backButton.png");
            backButtonPressed = new ImageIcon("res/img/Menu/MenuButtons/backButton_Pressed.png");

            closeButton = new ImageIcon("res/img/Menu/MenuButtons/closeButton.png");
            closeButtonPressed = new ImageIcon("res/img/Menu/MenuButtons/closeButton_Pressed.png");

            continueGameButton = new ImageIcon("res/img/Menu/MenuButtons/continueGameButton.png");
            continueGameButtonPressed = new ImageIcon("res/img/Menu/MenuButtons/continueGameButton_Pressed.png");

            mainMenuButton = new ImageIcon("res/img/Menu/MenuButtons/mainMenuButton.png");
            mainMenuButtonPressed = new ImageIcon("res/img/Menu/MenuButtons/mainMenuButton_Pressed.png");

            newGameButton = new ImageIcon("res/img/Menu/MenuButtons/newGameButton.png");
            newGameButtonPressed = new ImageIcon("res/img/Menu/MenuButtons/newGameButton_Pressed.png");

            offButton = new ImageIcon("res/img/Menu/MenuButtons/offButton.png");
            offButtonPressed = new ImageIcon("res/img/Menu/MenuButtons/offButton_Pressed.png");

            onButton = new ImageIcon("res/img/Menu/MenuButtons/onButton.png");
            onButtonPressed = new ImageIcon("res/img/Menu/MenuButtons/onButton_Pressed.png");

            playLocalButton = new ImageIcon("res/img/Menu/MenuButtons/playLocalButton.png");
            playLocalButtonPressed = new ImageIcon("res/img/Menu/MenuButtons/playLocalButton_Pressed.png");

            playOnlineButton = new ImageIcon("res/img/Menu/MenuButtons/playOnlineButton.png");
            playOnlineButtonPressed = new ImageIcon("res/img/Menu/MenuButtons/playOnlineButton_Pressed.png");

            saveButton = new ImageIcon("res/img/Menu/MenuButtons/saveButton.png");
            saveButtonPressed = new ImageIcon("res/img/Menu/MenuButtons/saveButton_Pressed.png");

            settingsButton = new ImageIcon("res/img/Menu/MenuButtons/settingsButton.png");
            settingsButtonPressed = new ImageIcon("res/img/Menu/MenuButtons/settingsButton_Pressed.png");

            soundButton = new ImageIcon("res/img/Menu/MenuButtons/soundButton.png");
            soundButtonPressed = new ImageIcon("res/img/Menu/MenuButtons/soundButton_Pressed.png");

            startGameButton = new ImageIcon("res/img/Menu/MenuButtons/startGameButton.png");
            startGameButtonPressed = new ImageIcon("res/img/Menu/MenuButtons/startGameButton_Pressed.png");

            // Avatar
            maleCharacterButtonUnactive = new ImageIcon("res/img/Menu/MenuButtons/maleCharacter_Unactive.jpg");
            maleCharacterButtonActive = new ImageIcon("res/img/Menu/MenuButtons/maleCharacter_Active.jpg");

            femaleCharacterButtonUnactive = new ImageIcon("res/img/Menu/MenuButtons/femaleCharacter_Unactive.jpg");
            femaleCharacterButtonActive = new ImageIcon("res/img/Menu/MenuButtons/femaleCharacter_Active.jpg");


            // Multiplayer
            createOnlineGame = new ImageIcon("res/img/Menu/MenuButtons/createOnlineGame.png");
            createOnlineGamePressed = new ImageIcon("res/img/Menu/MenuButtons/createOnlineGame_Pressed.png");

            joinOnlineGame = new ImageIcon("res/img/Menu/MenuButtons/joinOnlineGame.png");
            joinOnlineGamePressed = new ImageIcon("res/img/Menu/MenuButtons/joinOnlineGame_Pressed.png");

            watchGame = new ImageIcon("res/img/Menu/MenuButtons/spectatorGame.png");
            watchGamePressed = new ImageIcon("res/img/Menu/MenuButtons/spectatorGame_Pressed.png");


            /*
            * TILES
            * */
            // TileSet
            tileSet = ImageIO.read(ResourceLoader.class.getResourceAsStream("/img/tileSet.png"));
            heartSet = ImageIO.read(ResourceLoader.class.getResourceAsStream("/img/healthBarSet.png"));
            energySet = ImageIO.read(ResourceLoader.class.getResourceAsStream("/img/energyBarSet.png"));
            thirstSet = ImageIO.read(ResourceLoader.class.getResourceAsStream("/img/thirstBarSet.png"));

            // Erde
            dirt = tileSet.getSubimage(0, 0, References.TILE_SIZE, References.TILE_SIZE);
            dirtMidDark = tileSet.getSubimage(16, 0, References.TILE_SIZE, References.TILE_SIZE);
            dirtDark = tileSet.getSubimage(32, 0, References.TILE_SIZE, References.TILE_SIZE);

            // Gras
            gras = tileSet.getSubimage(48, 0, References.TILE_SIZE, References.TILE_SIZE);
            grasWithFlower = tileSet.getSubimage(64, 0, References.TILE_SIZE, References.TILE_SIZE);

            // Stein Weiss
            stoneWhiteGrass = tileSet.getSubimage(80, 0, References.TILE_SIZE, References.TILE_SIZE);
            stoneWhite1 = tileSet.getSubimage(96, 0, References.TILE_SIZE, References.TILE_SIZE);
            stoneWhite2 = tileSet.getSubimage(112, 0, References.TILE_SIZE, References.TILE_SIZE);
            stoneWhite3 = tileSet.getSubimage(128, 0, References.TILE_SIZE, References.TILE_SIZE);
            stoneWhiteLeafs = tileSet.getSubimage(144, 0, References.TILE_SIZE, References.TILE_SIZE);

            // Wasser, Lava, Eis
            waterTop = tileSet.getSubimage(160, 0, References.TILE_SIZE, References.TILE_SIZE);
            waterTop2 = tileSet.getSubimage(176, 0, References.TILE_SIZE, References.TILE_SIZE);
            water = tileSet.getSubimage(192, 0, References.TILE_SIZE, References.TILE_SIZE);
            lavaTop = tileSet.getSubimage(208, 0, References.TILE_SIZE, References.TILE_SIZE);
            lava = tileSet.getSubimage(224, 0, References.TILE_SIZE, References.TILE_SIZE);
            iceTop = tileSet.getSubimage(240, 0, References.TILE_SIZE, References.TILE_SIZE);
            ice = tileSet.getSubimage(256, 0, References.TILE_SIZE, References.TILE_SIZE);

            // Edelsteine
            gold = tileSet.getSubimage(0, 16, References.TILE_SIZE, References.TILE_SIZE);
            ion = tileSet.getSubimage(16, 16, References.TILE_SIZE, References.TILE_SIZE);
            silver = tileSet.getSubimage(32, 16, References.TILE_SIZE, References.TILE_SIZE);
            copper = tileSet.getSubimage(48, 16, References.TILE_SIZE, References.TILE_SIZE);
            ruby = tileSet.getSubimage(64, 16, References.TILE_SIZE, References.TILE_SIZE);
            saphire = tileSet.getSubimage(80, 16, References.TILE_SIZE, References.TILE_SIZE);
            smaragd = tileSet.getSubimage(96, 16, References.TILE_SIZE, References.TILE_SIZE);
            diamond = tileSet.getSubimage(112, 16, References.TILE_SIZE, References.TILE_SIZE);

            //Essen
            burger = tileSet.getSubimage(160,16,References.TILE_SIZE,References.TILE_SIZE);

            //Tr�nke
            healthPotion = tileSet.getSubimage(176, 16, References.TILE_SIZE, References.TILE_SIZE);

            //Leben
            health100 = heartSet.getSubimage(0,0,150,28);
            health90 = heartSet.getSubimage(0,28,150,28);
            health80 = heartSet.getSubimage(0,28*2,150,28);
            health70 = heartSet.getSubimage(0,28*3,150,28);
            health60 = heartSet.getSubimage(0,28*4,150,28);
            health50 = heartSet.getSubimage(0,28*5,150,28);
            health40 = heartSet.getSubimage(0,28*6,150,28);
            health30 = heartSet.getSubimage(0,28*7,150,28);
            health20 = heartSet.getSubimage(0,28*8,150,28);
            health10 = heartSet.getSubimage(0,28*9,150,28);

            //Energie
            energy100 = energySet.getSubimage(0,0,74,30);
            energy80 = energySet.getSubimage(0,30,74,30);
            energy70 = energySet.getSubimage(0,60,74,30);
            energy60 = energySet.getSubimage(0,90,74,30);
            energy50 = energySet.getSubimage(0,120,74,30);
            energy40 = energySet.getSubimage(0,150,74,30);
            energy30 = energySet.getSubimage(0,180,74,30);
            energy20 = energySet.getSubimage(0,210,74,30);
            energy10 = energySet.getSubimage(0,240,74,30);
            energy0 = energySet.getSubimage(0,270,74,30);

            //Durst
            thirst100 = thirstSet.getSubimage(0,0,112,40);
            thirst90 = thirstSet.getSubimage(0,40,112,40);
            thirst80 = thirstSet.getSubimage(0,80,112,40);
            thirst70 = thirstSet.getSubimage(0,120,112,40);
            thirst60 = thirstSet.getSubimage(0,160,112,40);
            thirst50 = thirstSet.getSubimage(0,200,112,40);
            thirst40 = thirstSet.getSubimage(0,240,112,40);
            thirst30 = thirstSet.getSubimage(0,280,112,40);
            thirst20 = thirstSet.getSubimage(0,320,112,40);
            thirst10 = thirstSet.getSubimage(0,360,112,40);
            thirst0 = thirstSet.getSubimage(0,400,112,40);

            // Explosion
            blackPowder = tileSet.getSubimage(128, 16, References.TILE_SIZE, References.TILE_SIZE);
            tnt = tileSet.getSubimage(144, 16, References.TILE_SIZE, References.TILE_SIZE);

            // TODO Zeile 32
            arrow = tileSet.getSubimage(144, 32, References.TILE_SIZE*2, References.TILE_SIZE);

            axe = tileSet.getSubimage(176, 32, References.TILE_SIZE, References.TILE_SIZE);
            hammer = tileSet.getSubimage(192, 32, References.TILE_SIZE, References.TILE_SIZE);
            stonePick = tileSet.getSubimage(208, 32, References.TILE_SIZE, References.TILE_SIZE);
            sword = tileSet.getSubimage(224, 32, References.TILE_SIZE, References.TILE_SIZE);
            bow = tileSet.getSubimage(240, 32, References.TILE_SIZE, References.TILE_SIZE);

            bulletGunPurple = tileSet.getSubimage(256, 32, References.TILE_SIZE, References.TILE_SIZE);
            gunPurple = tileSet.getSubimage(272, 32, References.TILE_SIZE, References.TILE_SIZE);


            // Holz und Blatt
            treeTrunkRoot = tileSet.getSubimage(0, 48, References.TILE_SIZE, References.TILE_SIZE);
            treeTrunk = tileSet.getSubimage(16, 48, References.TILE_SIZE, References.TILE_SIZE);
            treeTrunkRight = tileSet.getSubimage(32, 48, References.TILE_SIZE, References.TILE_SIZE);
            treeTrunkLeft = tileSet.getSubimage(48, 48, References.TILE_SIZE, References.TILE_SIZE);
            treeTrunkTop = tileSet.getSubimage(64, 48, References.TILE_SIZE, References.TILE_SIZE);
            leafStart = tileSet.getSubimage(80, 48, References.TILE_SIZE, References.TILE_SIZE);
            leaf = tileSet.getSubimage(96, 48, References.TILE_SIZE, References.TILE_SIZE);
            leafEnd = tileSet.getSubimage(112, 48, References.TILE_SIZE, References.TILE_SIZE);

            // TODO Zeile 80

            /*
            * InventoryBar
            * */
            inventoryBarCellSelected = ImageIO.read(ResourceLoader.class.getResourceAsStream("/img/Inventory/inventoryBar_Selected.jpg"));
            inventoryBarCellUnselected = ImageIO.read(ResourceLoader.class.getResourceAsStream("/img/Inventory/inventoryBar_Unselected.jpg"));

        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

}
