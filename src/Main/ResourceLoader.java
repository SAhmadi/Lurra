package Main;

import Assets.World.Tile;

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

    // WaterTile and LavaTile
    public static BufferedImage waterTileTop;
    public static BufferedImage waterTile;
    public static BufferedImage lavaTileTop;
    public static BufferedImage lavaTile;

    // GrasTile
    public static BufferedImage grasTileRight;
    public static BufferedImage grasTileRight_Destroyed;
    public static BufferedImage grasTileLeft;
    public static BufferedImage grasTileLeft_Destroyed;
    public static BufferedImage grasTileBottom;
    public static BufferedImage grasTileBottom_Destroyed;

    public static BufferedImage grasTileBottomRightCorner;
    public static BufferedImage grasTileBottomRightCorner_Destroyed;
    public static BufferedImage grasTileBottomLeftCorner;
    public static BufferedImage grasTileBottomLeftCorner_Destroyed;
    public static BufferedImage grasTileTopRightCorner;
    public static BufferedImage grasTileTopRightCorner_Destroyed;
    public static BufferedImage grasTileTopLeftCorner;
    public static BufferedImage grasTileTopLeftCorner_Destroyed;

    public static BufferedImage grasTile;
    public static BufferedImage grasTile_Destroyed;

    // DirtTile
    public static BufferedImage dirtDark;
    public static BufferedImage dirtDark_Destroyed;
    public static BufferedImage dirtMidDark;
    public static BufferedImage dirtMidDark_Destroyed;
    public static BufferedImage dirt;
    public static BufferedImage dirt_Destroyed;

    // StoneTile
    public static BufferedImage stoneWhite1;
    public static BufferedImage stoneWhite2;
    public static BufferedImage stoneWhite3;
    public static BufferedImage stoneWhiteLeafs;
    public static BufferedImage stoneWhite_Destroyed;

    public static BufferedImage stoneRed1;
    public static BufferedImage stoneRed2;
    public static BufferedImage stoneRed3;
    public static BufferedImage stoneRedLeafs;
    public static BufferedImage stoneRed_Destroyed;

    // Kupfer, Silber und Gold
    public static BufferedImage copper;
    public static BufferedImage copper_Destroyed;

    public static BufferedImage silver;
    public static BufferedImage silver_Destroyed;

    public static BufferedImage gold;
    public static BufferedImage gold_Destroyed;

    // Holz und Blatt
    public static BufferedImage leafBottomLeftCorner;
    public static BufferedImage leafBottom;
    public static BufferedImage leafBottomRightCorner;
    public static BufferedImage leafRight;
    public static BufferedImage leafNormal;
    public static BufferedImage leafLeft;
    public static BufferedImage leafTopLeftCorner;
    public static BufferedImage leafTop;
    public static BufferedImage leafTopRightCorner;

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

             // Wasser und Lava
            waterTileTop = tileSet.getSubimage(0, 0, Tile.WIDTH, Tile.HEIGHT);
            waterTile = tileSet.getSubimage(16, 0, Tile.WIDTH, Tile.HEIGHT);
            lavaTile = tileSet.getSubimage(32, 0, Tile.WIDTH, Tile.HEIGHT);
            lavaTileTop = tileSet.getSubimage(48, 0, Tile.WIDTH, Tile.HEIGHT);

            // Gras
            grasTileRight = tileSet.getSubimage(0, 16, Tile.WIDTH, Tile.HEIGHT);
            grasTileRight_Destroyed = tileSet.getSubimage(16, 16, Tile.WIDTH, Tile.HEIGHT);

            grasTileLeft = tileSet.getSubimage(0, 32, Tile.WIDTH, Tile.HEIGHT);
            grasTileLeft_Destroyed = tileSet.getSubimage(16, 32, Tile.WIDTH, Tile.HEIGHT);

            grasTileBottom = tileSet.getSubimage(0, 48, Tile.WIDTH, Tile.HEIGHT);
            grasTileBottom_Destroyed = tileSet.getSubimage(16, 48, Tile.WIDTH, Tile.HEIGHT);

            grasTileBottomRightCorner = tileSet.getSubimage(0, 64, Tile.WIDTH, Tile.HEIGHT);
            grasTileBottomRightCorner_Destroyed = tileSet.getSubimage(16, 64, Tile.WIDTH, Tile.HEIGHT);

            grasTileBottomLeftCorner = tileSet.getSubimage(0, 80, Tile.WIDTH, Tile.HEIGHT);
            grasTileBottomLeftCorner_Destroyed = tileSet.getSubimage(16, 80, Tile.WIDTH, Tile.HEIGHT);

            grasTileTopRightCorner = tileSet.getSubimage(0, 96, Tile.WIDTH, Tile.HEIGHT);
            grasTileTopRightCorner_Destroyed = tileSet.getSubimage(16, 96, Tile.WIDTH, Tile.HEIGHT);

            grasTileTopLeftCorner = tileSet.getSubimage(0, 112, Tile.WIDTH, Tile.HEIGHT);
            grasTileTopLeftCorner_Destroyed = tileSet.getSubimage(16, 112, Tile.WIDTH, Tile.HEIGHT);

            grasTile = tileSet.getSubimage(0, 128, Tile.WIDTH, Tile.HEIGHT);
            grasTile_Destroyed = tileSet.getSubimage(16, 128, Tile.WIDTH, Tile.HEIGHT);

            // Erde
            dirtDark = tileSet.getSubimage(0, 144, Tile.WIDTH, Tile.HEIGHT);
            dirtDark_Destroyed = tileSet.getSubimage(16, 144, Tile.WIDTH, Tile.HEIGHT);
            dirtMidDark = tileSet.getSubimage(0, 160, Tile.WIDTH, Tile.HEIGHT);
            dirtMidDark_Destroyed = tileSet.getSubimage(16, 160, Tile.WIDTH, Tile.HEIGHT);
            dirt = tileSet.getSubimage(0, 176, Tile.WIDTH, Tile.HEIGHT);
            dirt_Destroyed = tileSet.getSubimage(16, 176, Tile.WIDTH, Tile.HEIGHT);

            // Stein
            stoneWhite1 = tileSet.getSubimage(0, 192, Tile.WIDTH, Tile.HEIGHT);
            stoneWhite2 = tileSet.getSubimage(16, 192, Tile.WIDTH, Tile.HEIGHT);
            stoneWhite3 = tileSet.getSubimage(32, 192, Tile.WIDTH, Tile.HEIGHT);
            stoneWhiteLeafs = tileSet.getSubimage(48, 192, Tile.WIDTH, Tile.HEIGHT);
            stoneWhite_Destroyed = tileSet.getSubimage(64, 192, Tile.WIDTH, Tile.HEIGHT);

            stoneRed1 = tileSet.getSubimage(0, 208, Tile.WIDTH, Tile.HEIGHT);
            stoneRed2 = tileSet.getSubimage(16, 208, Tile.WIDTH, Tile.HEIGHT);
            stoneRed3 = tileSet.getSubimage(32, 208, Tile.WIDTH, Tile.HEIGHT);
            stoneRedLeafs = tileSet.getSubimage(48, 208, Tile.WIDTH, Tile.HEIGHT);
            stoneRed_Destroyed = tileSet.getSubimage(64, 208, Tile.WIDTH, Tile.HEIGHT);

            // Kupfer, Silber und Gold
            copper = tileSet.getSubimage(0, 224, Tile.WIDTH, Tile.HEIGHT);
            copper_Destroyed = tileSet.getSubimage(16, 224, Tile.WIDTH, Tile.HEIGHT);

            silver = tileSet.getSubimage(0, 240, Tile.WIDTH, Tile.HEIGHT);
            silver_Destroyed = tileSet.getSubimage(16, 240, Tile.WIDTH, Tile.HEIGHT);

            gold = tileSet.getSubimage(0, 256, Tile.WIDTH, Tile.HEIGHT);
            gold_Destroyed = tileSet.getSubimage(16, 256, Tile.WIDTH, Tile.HEIGHT);

            // Holz und Blatt
            leafBottomLeftCorner = tileSet.getSubimage(0, 272, Tile.WIDTH, Tile.HEIGHT);
            leafBottom = tileSet.getSubimage(16, 272, Tile.WIDTH, Tile.HEIGHT);
            leafBottomRightCorner = tileSet.getSubimage(32, 272, Tile.WIDTH, Tile.HEIGHT);
            leafLeft = tileSet.getSubimage(48, 272, Tile.WIDTH, Tile.HEIGHT);
            leafNormal = tileSet.getSubimage(64, 272, Tile.WIDTH, Tile.HEIGHT);
            leafRight = tileSet.getSubimage(80, 272, Tile.WIDTH, Tile.HEIGHT);
            leafTopLeftCorner = tileSet.getSubimage(96, 272, Tile.WIDTH, Tile.HEIGHT);
            leafTop = tileSet.getSubimage(112, 272, Tile.WIDTH, Tile.HEIGHT);
            leafTopRightCorner = tileSet.getSubimage(128, 272, Tile.WIDTH, Tile.HEIGHT);

            treeTrunkRootLeft = tileSet.getSubimage(0, 288, Tile.WIDTH, Tile.HEIGHT);
            treeTrunkBottomLeft = tileSet.getSubimage(16, 288, Tile.WIDTH, Tile.HEIGHT);
            treeTrunkBottomRight = tileSet.getSubimage(32, 288, Tile.WIDTH, Tile.HEIGHT);
            treeTrunkRootRight = tileSet.getSubimage(48, 288, Tile.WIDTH, Tile.HEIGHT);
            treeTrunkRoundedCornerTopLeft = tileSet.getSubimage(64, 288, Tile.WIDTH, Tile.HEIGHT);
            treeTrunkNextToCorner = tileSet.getSubimage(80, 288, Tile.WIDTH, Tile.HEIGHT);
            treeTrunkHorizontalNormal = tileSet.getSubimage(96, 288, Tile.WIDTH, Tile.HEIGHT);
            treeTrunkRoundedCornerBottomRight = tileSet.getSubimage(112, 288, Tile.WIDTH, Tile.HEIGHT);
            treeTrunkVerticalNormal = tileSet.getSubimage(128, 288, Tile.WIDTH, Tile.HEIGHT);
            treeTrunkTopCenter = tileSet.getSubimage(144, 288, Tile.WIDTH, Tile.HEIGHT);
            treeTrunkTopLeft = tileSet.getSubimage(160, 288, Tile.WIDTH, Tile.HEIGHT);
            treeTrunkTopLeftEnd = tileSet.getSubimage(176, 288, Tile.WIDTH, Tile.HEIGHT);
            treeTrunkTopRightEnd = tileSet.getSubimage(192, 288, Tile.WIDTH, Tile.HEIGHT);


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
