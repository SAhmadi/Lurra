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
    * */
    private static BufferedImage tileSet;

    // GrasTile
    public static BufferedImage gras;
    public static BufferedImage grasWithFlower;
    public static BufferedImage grasStripesBright;
    public static BufferedImage grasStripes;
    public static BufferedImage grasStripesYellowFlower;
    public static BufferedImage grasStripesPurpleFlower;

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

    // WaterTile and LavaTile
    public static BufferedImage waterTop;
    public static BufferedImage water;
    public static BufferedImage lavaTop;
    public static BufferedImage lava;

    // Kupfer, Silber und Gold
    public static BufferedImage goldCrystal;
    public static BufferedImage gold;

    public static BufferedImage ionCrystal;
    public static BufferedImage ion;

    public static BufferedImage silverCrystal;
    public static BufferedImage silver;

    public static BufferedImage copperCrystal;
    public static BufferedImage copper;

    public static BufferedImage rubyCrystal;
    public static BufferedImage ruby;

    public static BufferedImage saphireCrystal;
    public static BufferedImage saphire;

    public static BufferedImage smaragdCrystal;
    public static BufferedImage smaragd;

    public static BufferedImage diamondCrystal;
    public static BufferedImage diamond;

    // Eis
    public static BufferedImage iceTop;
    public static BufferedImage ice;

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
    public static BufferedImage swordBlue;

    // Holz und Blatt
    public static BufferedImage greenLeafBottomLeftCorner;
    public static BufferedImage greenLeafBottom;
    public static BufferedImage greenLeafBottomRightCorner;
    public static BufferedImage greenLeafRight;
    public static BufferedImage greenLeafNormal;
    public static BufferedImage greenLeafLeft;
    public static BufferedImage greenLeafTopLeftCorner;
    public static BufferedImage greenLeafTop;
    public static BufferedImage greenLeafTopRightCorner;

    public static BufferedImage purpleLeafBottomLeftCorner;
    public static BufferedImage purpleLeafBottom;
    public static BufferedImage purpleLeafBottomRightCorner;
    public static BufferedImage purpleLeafRight;
    public static BufferedImage purpleLeafNormal;
    public static BufferedImage purpleLeafLeft;
    public static BufferedImage purpleLeafTopLeftCorner;
    public static BufferedImage purpleLeafTop;
    public static BufferedImage purpleLeafTopRightCorner;

    public static BufferedImage treeTrunkRootLeft;
    public static BufferedImage treeTrunkBottomLeft;
    public static BufferedImage treeTrunkBottomRight;
    public static BufferedImage treeTrunkRootRight;
    public static BufferedImage treeTrunkRoundedCornerTopLeft;
    public static BufferedImage treeTrunkNextToCorner;
    public static BufferedImage treeTrunkHorizontalNormal;
    public static BufferedImage treeTrunkRoundedCornerBottomRight;
    public static BufferedImage treeTrunkVerticalNormal;
    public static BufferedImage treeTrunkTopCenter;
    public static BufferedImage treeTrunkTopLeft;
    public static BufferedImage treeTrunkTopLeftEnd;
    public static BufferedImage treeTrunkTopRightEnd;

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

            inventoryItemFont = new Font("Arial", Font.PLAIN, 12);

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

             // Erde
            dirt = tileSet.getSubimage(0, 0, References.TILE_SIZE, References.TILE_SIZE);
            dirtMidDark = tileSet.getSubimage(16, 0, References.TILE_SIZE, References.TILE_SIZE);
            dirtDark = tileSet.getSubimage(32, 0, References.TILE_SIZE, References.TILE_SIZE);

            // Gras
            gras = tileSet.getSubimage(48, 0, References.TILE_SIZE, References.TILE_SIZE);
            grasWithFlower = tileSet.getSubimage(64, 0, References.TILE_SIZE, References.TILE_SIZE);
            grasStripesBright = tileSet.getSubimage(80, 0, References.TILE_SIZE, References.TILE_SIZE);
            grasStripes = tileSet.getSubimage(96, 0, References.TILE_SIZE, References.TILE_SIZE);
            grasStripesYellowFlower = tileSet.getSubimage(112, 0, References.TILE_SIZE, References.TILE_SIZE);
            grasStripesPurpleFlower = tileSet.getSubimage(128, 0, References.TILE_SIZE, References.TILE_SIZE);

            // Stein Weiss
            stoneWhiteGrass = tileSet.getSubimage(144, 0, References.TILE_SIZE, References.TILE_SIZE);
            stoneWhite1 = tileSet.getSubimage(160, 0, References.TILE_SIZE, References.TILE_SIZE);
            stoneWhite2 = tileSet.getSubimage(176, 0, References.TILE_SIZE, References.TILE_SIZE);
            stoneWhite3 = tileSet.getSubimage(192, 0, References.TILE_SIZE, References.TILE_SIZE);
            stoneWhiteLeafs = tileSet.getSubimage(208, 0, References.TILE_SIZE, References.TILE_SIZE);

            // Wasser und Lava
            waterTop = tileSet.getSubimage(224, 0, References.TILE_SIZE, References.TILE_SIZE);
            water = tileSet.getSubimage(240, 0, References.TILE_SIZE, References.TILE_SIZE);
            lavaTop = tileSet.getSubimage(256, 0, References.TILE_SIZE, References.TILE_SIZE);
            lava = tileSet.getSubimage(272, 0, References.TILE_SIZE, References.TILE_SIZE);

            // Edelsteine
            goldCrystal = tileSet.getSubimage(0, 16, References.TILE_SIZE, References.TILE_SIZE);
            gold = tileSet.getSubimage(16, 16, References.TILE_SIZE, References.TILE_SIZE);

            ionCrystal = tileSet.getSubimage(32, 16, References.TILE_SIZE, References.TILE_SIZE);
            ion = tileSet.getSubimage(48, 16, References.TILE_SIZE, References.TILE_SIZE);

            silverCrystal = tileSet.getSubimage(64, 16, References.TILE_SIZE, References.TILE_SIZE);
            silver = tileSet.getSubimage(80, 16, References.TILE_SIZE, References.TILE_SIZE);

            copperCrystal = tileSet.getSubimage(96, 16, References.TILE_SIZE, References.TILE_SIZE);
            copper = tileSet.getSubimage(112, 16, References.TILE_SIZE, References.TILE_SIZE);

            rubyCrystal = tileSet.getSubimage(128, 16, References.TILE_SIZE, References.TILE_SIZE);
            ruby = tileSet.getSubimage(144, 16, References.TILE_SIZE, References.TILE_SIZE);

            saphireCrystal = tileSet.getSubimage(160, 16, References.TILE_SIZE, References.TILE_SIZE);
            saphire = tileSet.getSubimage(176, 16, References.TILE_SIZE, References.TILE_SIZE);

            smaragdCrystal = tileSet.getSubimage(192, 16, References.TILE_SIZE, References.TILE_SIZE);
            smaragd = tileSet.getSubimage(208, 16, References.TILE_SIZE, References.TILE_SIZE);

            diamondCrystal = tileSet.getSubimage(224, 16, References.TILE_SIZE, References.TILE_SIZE);
            diamond = tileSet.getSubimage(240, 16, References.TILE_SIZE, References.TILE_SIZE);

            // Explosion
            blackPowder = tileSet.getSubimage(256, 16, References.TILE_SIZE, References.TILE_SIZE);
            tnt = tileSet.getSubimage(272, 16, References.TILE_SIZE, References.TILE_SIZE);

            // TODO Zeile 32

            // Holz und Blatt
            greenLeafBottomLeftCorner = tileSet.getSubimage(0, 48, References.TILE_SIZE, References.TILE_SIZE);
            greenLeafBottom = tileSet.getSubimage(16, 48, References.TILE_SIZE, References.TILE_SIZE);
            greenLeafBottomRightCorner = tileSet.getSubimage(32, 48, References.TILE_SIZE, References.TILE_SIZE);
            greenLeafLeft = tileSet.getSubimage(48, 48, References.TILE_SIZE, References.TILE_SIZE);
            greenLeafNormal = tileSet.getSubimage(64, 48, References.TILE_SIZE, References.TILE_SIZE);
            greenLeafRight = tileSet.getSubimage(80, 48, References.TILE_SIZE, References.TILE_SIZE);
            greenLeafTopLeftCorner = tileSet.getSubimage(96, 48, References.TILE_SIZE, References.TILE_SIZE);
            greenLeafTop = tileSet.getSubimage(112, 48, References.TILE_SIZE, References.TILE_SIZE);
            greenLeafTopRightCorner = tileSet.getSubimage(128, 48, References.TILE_SIZE, References.TILE_SIZE);

            purpleLeafBottomLeftCorner = tileSet.getSubimage(144, 48, References.TILE_SIZE, References.TILE_SIZE);
            purpleLeafBottom = tileSet.getSubimage(160, 48, References.TILE_SIZE, References.TILE_SIZE);
            purpleLeafBottomRightCorner = tileSet.getSubimage(176, 48, References.TILE_SIZE, References.TILE_SIZE);
            purpleLeafLeft = tileSet.getSubimage(192, 48, References.TILE_SIZE, References.TILE_SIZE);
            purpleLeafNormal = tileSet.getSubimage(208, 48, References.TILE_SIZE, References.TILE_SIZE);
            purpleLeafRight = tileSet.getSubimage(224, 48, References.TILE_SIZE, References.TILE_SIZE);
            purpleLeafTopLeftCorner = tileSet.getSubimage(240, 48, References.TILE_SIZE, References.TILE_SIZE);
            purpleLeafTop = tileSet.getSubimage(256, 48, References.TILE_SIZE, References.TILE_SIZE);
            purpleLeafTopRightCorner = tileSet.getSubimage(272, 48, References.TILE_SIZE, References.TILE_SIZE);

            treeTrunkRootLeft = tileSet.getSubimage(0, 64, References.TILE_SIZE, References.TILE_SIZE);
            treeTrunkBottomLeft = tileSet.getSubimage(16, 64, References.TILE_SIZE, References.TILE_SIZE);
            treeTrunkBottomRight = tileSet.getSubimage(32, 64, References.TILE_SIZE, References.TILE_SIZE);
            treeTrunkRootRight = tileSet.getSubimage(48, 64, References.TILE_SIZE, References.TILE_SIZE);
            treeTrunkRoundedCornerTopLeft = tileSet.getSubimage(64, 64, References.TILE_SIZE, References.TILE_SIZE);
            treeTrunkNextToCorner = tileSet.getSubimage(80, 64, References.TILE_SIZE, References.TILE_SIZE);
            treeTrunkHorizontalNormal = tileSet.getSubimage(96, 64, References.TILE_SIZE, References.TILE_SIZE);
            treeTrunkRoundedCornerBottomRight = tileSet.getSubimage(112, 64, References.TILE_SIZE, References.TILE_SIZE);
            treeTrunkVerticalNormal = tileSet.getSubimage(128, 64, References.TILE_SIZE, References.TILE_SIZE);
            treeTrunkTopCenter = tileSet.getSubimage(144, 64, References.TILE_SIZE, References.TILE_SIZE);
            treeTrunkTopLeft = tileSet.getSubimage(160, 64, References.TILE_SIZE, References.TILE_SIZE);
            treeTrunkTopLeftEnd = tileSet.getSubimage(176, 64, References.TILE_SIZE, References.TILE_SIZE);
            treeTrunkTopRightEnd = tileSet.getSubimage(192, 64, References.TILE_SIZE, References.TILE_SIZE);

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
