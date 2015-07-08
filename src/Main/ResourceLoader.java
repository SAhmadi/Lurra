package Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Laden der Ressourcen, die fuer das Spiel benoetigt werden
 *
 * @author Sirat
 * */
public class ResourceLoader
{

    // Font
    public static Font textFieldFont;
    public static Font inventoryItemFont;

    // Menu
    public static BufferedImage menuIlandBackground;
    public static BufferedImage menuTitleImage;
    public static BufferedImage teamCredits;

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
    public static ImageIcon themesButton, themesButtonPressed;

    // Multiplayer
    public static ImageIcon createOnlineGame, createOnlineGamePressed;
    public static ImageIcon joinOnlineGame, joinOnlineGamePressed;
    public static ImageIcon watchGame, watchGamePressed;

    public static ImageIcon maleCharacterButtonUnactive, maleCharacterButtonActive;
    public static ImageIcon femaleCharacterButtonUnactive, femaleCharacterButtonActive;

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

    // Wasser, Lava, Eis
    public static BufferedImage waterTop;
    public static BufferedImage waterTop2;
    public static BufferedImage water;
    public static BufferedImage torch;
    public static BufferedImage lava;
    public static BufferedImage iceTop;
    public static BufferedImage ice;
    public static BufferedImage ice2;
    public static BufferedImage ice3;

    // Kupfer, Silber und Gold
    public static BufferedImage gold;
    public static BufferedImage ion;
    public static BufferedImage silver;
    public static BufferedImage copper;
    public static BufferedImage ruby;
    public static BufferedImage saphire;
    public static BufferedImage smaragd;
    public static BufferedImage diamond;

    public static BufferedImage goldIced;
    public static BufferedImage ionIced;
    public static BufferedImage silverIced;
    public static BufferedImage copperIced;
    public static BufferedImage rubyIced;
    public static BufferedImage saphireIced;
    public static BufferedImage smaragdIced;
    public static BufferedImage diamondIced;

    // Gatter
    private static BufferedImage BROn;
    private static BufferedImage BROff;
    private static BufferedImage NandR;
    private static BufferedImage NandL;
    private static BufferedImage Powerblock;
    private static BufferedImage SwitchesOn;
    private static BufferedImage SwitchesOff;

    // Bluerock
    public static BufferedImage BluerockOn;
    public static BufferedImage BluerockOff;
    public static BufferedImage Batterie;
    public static BufferedImage NANDR;
    public static BufferedImage NANDL;
    public static BufferedImage SwitchOn;
    public static BufferedImage SwitchOff;

    // Lebens-, Hunge- und Durstleiste
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
    public static BufferedImage health0;

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

    // Nahrung
    public static BufferedImage burger;
    public static BufferedImage healthPotion;

    // Wand
    public static BufferedImage dirtWall;
    public static BufferedImage dirtWall2;
    public static BufferedImage dirtWallGras;

    // Sand
    public static BufferedImage sandTop;
    public static BufferedImage sand;
    public static BufferedImage sand2;
    public static BufferedImage sand3;

    // Explosion
    public static BufferedImage tnt;

    // Einrichtung
    public static BufferedImage woodenBoard;

    // Waffen
    public static BufferedImage axe;
    public static BufferedImage hammer;
    public static BufferedImage stonePick;
    public static BufferedImage sword;

    public static BufferedImage gunPurple;
    public static BufferedImage bulletGunPurple;
    public static BufferedImage bulletIronMan;

    // Kaktus
    public static BufferedImage cactusRoot;
    public static BufferedImage cactus1;
    public static BufferedImage cactus2;
    public static BufferedImage cactusTop;

    // Holz und Blatt
    public static BufferedImage treeTrunkRoot;
    public static BufferedImage treeTrunk;
    public static BufferedImage treeTrunkRight;
    public static BufferedImage treeTrunkLeft;
    public static BufferedImage treeTrunkTop;

    public static BufferedImage treeTrunkRootSnow;
    public static BufferedImage treeTrunkAfterRootSnow;
    public static BufferedImage treeTrunkSnow;
    public static BufferedImage treeTrunkSnowEnd;

    public static BufferedImage leafStart;
    public static BufferedImage leaf;
    public static BufferedImage leafEnd;

    // Inventar
    public static BufferedImage inventoryBarCellSelected;
    public static BufferedImage inventoryBarCellUnselected;

    // Gegner
    public static BufferedImage enemyEye;


    /**
     * loadResources        Laden der Ressourcen
     * */
    public static void loadResources()
    {
        try
        {
            // Font
            textFieldFont = CustomFont.createCustomFont("robotoLight.ttf", 18f);
            inventoryItemFont = new Font("Arial", Font.BOLD, 14);

            // Hintergrundmusik und SFX
            Sound.waterSound = new Sound("water.wav");
            Sound.elevatorSound = new Sound("elevator.wav");
            Sound.boomSound = new Sound("boom.wav");
            Sound.walkSound = new Sound("walk.wav");
            Sound.jumpSound = new Sound("jump.wav");
            Sound.metalSound = new Sound("metal.wav");
            Sound.earthSound = new Sound("earth.wav");
            Sound.desertSound = new Sound("desert.wav");
            Sound.jungleSound = new Sound("jungle.wav");
            Sound.alaskaSound = new Sound("alaska.wav");
            Sound.woodSound = new Sound("wood.wav");
            Sound.explosionSound = new Sound ("explosion.wav");
            Sound.startButtonSound = new Sound("startButton.wav");
            Sound.settingButtonSound = new Sound("settingButton.wav");
            Sound.soundButtonSound = new Sound("soundButton.wav");
            Sound.backButtonSound = new Sound("backButton.wav");
            Sound.avatarButtonSound = new Sound ("avatarButton.wav");
            Sound.localButtonSound = new Sound ("localButton.wav");
            Sound.onlineButtonSound = new Sound("onlineButton.wav");
            Sound.continueButtonSound = new Sound("continueButton.wav");
            Sound.newGameButtonSound = new Sound("newGameButton.wav");
            Sound.createButtonSound = new Sound("createButton.wav");
            Sound.joinButtonSound = new Sound("joinButton.wav");
            Sound.spectateButtonSound = new Sound("spectateButton.wav");
            Sound.endStartButtonSound = new Sound("endStartButton.wav");
            Sound.onButtonSound = new Sound("onButton.wav");
            Sound.offButtonSound = new Sound("offButton.wav");
            Sound.gameSound = new Sound("gameSound.wav");
            Sound.eatSound = new Sound ("eat.wav");
            Sound.heartBeatSound = new Sound ("heartBeat.wav");
            Sound.drinkSound = new Sound ("drink.wav");
            Sound.killSound = new Sound ("kill.wav");

            // Menu
            menuIlandBackground = ImageIO.read(ResourceLoader.class.getResourceAsStream("/img/Menu/menuIlandBackground.png"));
            menuTitleImage = ImageIO.read(ResourceLoader.class.getResourceAsStream("/img/Menu/lurraTitle.png"));
            teamCredits = ImageIO.read(ResourceLoader.class.getResourceAsStream("/img/Menu/teamCredits.png"));

            // Buttons
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

            themesButton = new ImageIcon("res/img/Menu/MenuButtons/themesButton.png");
            themesButtonPressed = new ImageIcon("res/img/Menu/MenuButtons/themesButtonPressed.png");

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

            // TileSet
            BufferedImage tileSet = ImageIO.read(ResourceLoader.class.getResourceAsStream("/img/tileSet.png"));

            /*
            BROn = ImageIO.read(ResourceLoader.class.getResourceAsStream("/img/BluerockOn.png"));
            BROff = ImageIO.read(ResourceLoader.class.getResourceAsStream("/img/BluerockOff.png"));
            */

            /*
            NandR = ImageIO.read(ResourceLoader.class.getResourceAsStream("/img/NANDright.png"));
            NandL = ImageIO.read(ResourceLoader.class.getResourceAsStream("/img/NANDleft.png"));
            SwitchesOn = ImageIO.read(ResourceLoader.class.getResourceAsStream("/img/SwitchOn.png"));
            SwitchesOff = ImageIO.read(ResourceLoader.class.getResourceAsStream("/img/SwitchOff.png"));
            Powerblock = ImageIO.read(ResourceLoader.class.getResourceAsStream("/img/Powerblock.png"));
            */

            // Erde
            dirt = tileSet.getSubimage(0, 0, References.TILE_SIZE, References.TILE_SIZE);
            dirtMidDark = tileSet.getSubimage(References.TILE_SIZE, 0, References.TILE_SIZE, References.TILE_SIZE);
            dirtDark = tileSet.getSubimage(2 * References.TILE_SIZE, 0, References.TILE_SIZE, References.TILE_SIZE);

            // Gras
            gras = tileSet.getSubimage(3 * References.TILE_SIZE, 0, References.TILE_SIZE, References.TILE_SIZE);
            grasWithFlower = tileSet.getSubimage(4 * References.TILE_SIZE, 0, References.TILE_SIZE, References.TILE_SIZE);

            // Stein Weiss
            stoneWhiteGrass = tileSet.getSubimage(5 * References.TILE_SIZE, 0, References.TILE_SIZE, References.TILE_SIZE);
            stoneWhite1 = tileSet.getSubimage(6 * References.TILE_SIZE, 0, References.TILE_SIZE, References.TILE_SIZE);
            stoneWhite2 = tileSet.getSubimage(7 * References.TILE_SIZE, 0, References.TILE_SIZE, References.TILE_SIZE);
            stoneWhite3 = tileSet.getSubimage(8 * References.TILE_SIZE, 0, References.TILE_SIZE, References.TILE_SIZE);
            stoneWhiteLeafs = tileSet.getSubimage(9 * References.TILE_SIZE, 0, References.TILE_SIZE, References.TILE_SIZE);

            // Wasser, Lava, Eis
            waterTop = tileSet.getSubimage(10 * References.TILE_SIZE, 0, References.TILE_SIZE, References.TILE_SIZE);
            waterTop2 = tileSet.getSubimage(11 * References.TILE_SIZE, 0, References.TILE_SIZE, References.TILE_SIZE);
            water = tileSet.getSubimage(12 * References.TILE_SIZE, 0, References.TILE_SIZE, References.TILE_SIZE);
            torch = tileSet.getSubimage(13 * References.TILE_SIZE, 0, References.TILE_SIZE, References.TILE_SIZE);
            ice = tileSet.getSubimage(14 * References.TILE_SIZE, 0, References.TILE_SIZE, References.TILE_SIZE);
            iceTop = tileSet.getSubimage(15 * References.TILE_SIZE, 0, References.TILE_SIZE, References.TILE_SIZE);
            ice2 = tileSet.getSubimage(16 * References.TILE_SIZE, 0, References.TILE_SIZE, References.TILE_SIZE);
            ice3 = tileSet.getSubimage(17 * References.TILE_SIZE, 0, References.TILE_SIZE, References.TILE_SIZE);

            // Edelsteine
            gold = tileSet.getSubimage(0, 16, References.TILE_SIZE, References.TILE_SIZE);
            ion = tileSet.getSubimage(References.TILE_SIZE, 16, References.TILE_SIZE, References.TILE_SIZE);
            silver = tileSet.getSubimage(2 * References.TILE_SIZE, 16, References.TILE_SIZE, References.TILE_SIZE);
            copper = tileSet.getSubimage(3 * References.TILE_SIZE, 16, References.TILE_SIZE, References.TILE_SIZE);
            ruby = tileSet.getSubimage(4 * References.TILE_SIZE, 16, References.TILE_SIZE, References.TILE_SIZE);
            saphire = tileSet.getSubimage(5 * References.TILE_SIZE, 16, References.TILE_SIZE, References.TILE_SIZE);
            smaragd = tileSet.getSubimage(6 * References.TILE_SIZE, 16, References.TILE_SIZE, References.TILE_SIZE);
            diamond = tileSet.getSubimage(7 * References.TILE_SIZE, 16, References.TILE_SIZE, References.TILE_SIZE);

            //Bluerock
            /*BluerockOn = BROn.getSubimage(0, 0, References.TILE_SIZE, References.TILE_SIZE);
            BluerockOff = BROff.getSubimage(0, 0, References.TILE_SIZE, References.TILE_SIZE);
            Batterie = Powerblock.getSubimage(0, 0, References.TILE_SIZE, References.TILE_SIZE);
            NANDR = NandR.getSubimage(0, 0, 2*References.TILE_SIZE, References.TILE_SIZE);
            NANDL = NandL.getSubimage(0, 0, 2*References.TILE_SIZE, References.TILE_SIZE);
            SwitchOn = SwitchesOn.getSubimage(0, 0, References.TILE_SIZE, References.TILE_SIZE);
            SwitchOff = SwitchesOff.getSubimage(0, 0, References.TILE_SIZE, References.TILE_SIZE);
*/
            // Trank und Nahrung
            healthPotion = tileSet.getSubimage(8 * References.TILE_SIZE, 16, References.TILE_SIZE, References.TILE_SIZE);
            tnt = tileSet.getSubimage(9 * References.TILE_SIZE, 16, References.TILE_SIZE, References.TILE_SIZE);
            burger = tileSet.getSubimage(10 * References.TILE_SIZE, 16, References.TILE_SIZE, References.TILE_SIZE);

            // Wand
            dirtWall = tileSet.getSubimage(11 * References.TILE_SIZE, 16, References.TILE_SIZE, References.TILE_SIZE);
            dirtWall2 = tileSet.getSubimage(12 * References.TILE_SIZE, 16, References.TILE_SIZE, References.TILE_SIZE);
            dirtWallGras = tileSet.getSubimage(13 * References.TILE_SIZE, 16, References.TILE_SIZE, References.TILE_SIZE);

            // Sand
            sand = tileSet.getSubimage(14 * References.TILE_SIZE, 16, References.TILE_SIZE, References.TILE_SIZE);
            sand2 = tileSet.getSubimage(15 * References.TILE_SIZE, 16, References.TILE_SIZE, References.TILE_SIZE);
            sand3 = tileSet.getSubimage(16 * References.TILE_SIZE, 16, References.TILE_SIZE, References.TILE_SIZE);
            sandTop = tileSet.getSubimage(17 * References.TILE_SIZE, 16, References.TILE_SIZE, References.TILE_SIZE);

            // Ziegelstein
            stoneRed1 = tileSet.getSubimage(0, 32, References.TILE_SIZE, References.TILE_SIZE);
            stoneRed2 = tileSet.getSubimage(References.TILE_SIZE, 32, References.TILE_SIZE, References.TILE_SIZE);
            stoneRed3 = tileSet.getSubimage(2 * References.TILE_SIZE, 32, References.TILE_SIZE, References.TILE_SIZE);
            stoneRedLeafs = tileSet.getSubimage(3 * References.TILE_SIZE, 32, References.TILE_SIZE, References.TILE_SIZE);

            // Holzwand
            woodenBoard = tileSet.getSubimage(4 * References.TILE_SIZE, 32, References.TILE_SIZE, References.TILE_SIZE);

            // Waffen und Geschosse
            axe = tileSet.getSubimage(5 * References.TILE_SIZE, 32, References.TILE_SIZE, References.TILE_SIZE);
            hammer = tileSet.getSubimage(6 * References.TILE_SIZE, 32, References.TILE_SIZE, References.TILE_SIZE);
            stonePick = tileSet.getSubimage(7 * References.TILE_SIZE, 32, References.TILE_SIZE, References.TILE_SIZE);
            sword = tileSet.getSubimage(8 * References.TILE_SIZE, 32, References.TILE_SIZE, References.TILE_SIZE);
            bulletGunPurple = tileSet.getSubimage(9 * References.TILE_SIZE, 32, References.TILE_SIZE, References.TILE_SIZE);
            gunPurple = tileSet.getSubimage(10 * References.TILE_SIZE, 32, References.TILE_SIZE, References.TILE_SIZE);
            bulletIronMan = tileSet.getSubimage(11 * References.TILE_SIZE, 32, References.TILE_SIZE, References.TILE_SIZE);

            // Kaktus
            cactusRoot = tileSet.getSubimage(12 * References.TILE_SIZE, 32, References.TILE_SIZE, References.TILE_SIZE);
            cactus1 = tileSet.getSubimage(13 * References.TILE_SIZE, 32, References.TILE_SIZE, References.TILE_SIZE);
            cactus2 = tileSet.getSubimage(14 * References.TILE_SIZE, 32, References.TILE_SIZE, References.TILE_SIZE);
            cactusTop = tileSet.getSubimage(15 * References.TILE_SIZE, 32, References.TILE_SIZE, References.TILE_SIZE);

            // Holz und Blatt
            treeTrunkRoot = tileSet.getSubimage(0, 48, References.TILE_SIZE, References.TILE_SIZE);
            treeTrunk = tileSet.getSubimage(References.TILE_SIZE, 48, References.TILE_SIZE, References.TILE_SIZE);
            treeTrunkRight = tileSet.getSubimage(2 * References.TILE_SIZE, 48, References.TILE_SIZE, References.TILE_SIZE);
            treeTrunkLeft = tileSet.getSubimage(3 * References.TILE_SIZE, 48, References.TILE_SIZE, References.TILE_SIZE);
            treeTrunkTop = tileSet.getSubimage(4 * References.TILE_SIZE, 48, References.TILE_SIZE, References.TILE_SIZE);
            leafStart = tileSet.getSubimage(5 * References.TILE_SIZE, 48, References.TILE_SIZE, References.TILE_SIZE);
            leaf = tileSet.getSubimage(6 * References.TILE_SIZE, 48, References.TILE_SIZE, References.TILE_SIZE);
            leafEnd = tileSet.getSubimage(7 * References.TILE_SIZE, 48, References.TILE_SIZE, References.TILE_SIZE);

            treeTrunkRootSnow = tileSet.getSubimage(8 * References.TILE_SIZE, 48, References.TILE_SIZE, References.TILE_SIZE);
            treeTrunkAfterRootSnow = tileSet.getSubimage(9 * References.TILE_SIZE, 48, References.TILE_SIZE, References.TILE_SIZE);
            treeTrunkSnow = tileSet.getSubimage(10 * References.TILE_SIZE, 48, References.TILE_SIZE, References.TILE_SIZE);
            treeTrunkSnowEnd = tileSet.getSubimage(11 * References.TILE_SIZE, 48, References.TILE_SIZE, References.TILE_SIZE);

            // Edelsteine vereist
            goldIced = tileSet.getSubimage(0, 64, References.TILE_SIZE, References.TILE_SIZE);
            ionIced = tileSet.getSubimage(References.TILE_SIZE, 64, References.TILE_SIZE, References.TILE_SIZE);
            silverIced = tileSet.getSubimage(2 * References.TILE_SIZE, 64, References.TILE_SIZE, References.TILE_SIZE);
            copperIced = tileSet.getSubimage(3 * References.TILE_SIZE, 64, References.TILE_SIZE, References.TILE_SIZE);
            rubyIced = tileSet.getSubimage(4 * References.TILE_SIZE, 64, References.TILE_SIZE, References.TILE_SIZE);
            saphireIced = tileSet.getSubimage(5 * References.TILE_SIZE, 64, References.TILE_SIZE, References.TILE_SIZE);
            smaragdIced = tileSet.getSubimage(6 * References.TILE_SIZE, 64, References.TILE_SIZE, References.TILE_SIZE);
            diamondIced = tileSet.getSubimage(7 * References.TILE_SIZE, 64, References.TILE_SIZE, References.TILE_SIZE);

            // Inventar
            inventoryBarCellSelected = tileSet.getSubimage(0, 80, References.INVENTORY_BAR_CELL_SIZE, References.INVENTORY_BAR_CELL_SIZE);
            inventoryBarCellUnselected = tileSet.getSubimage(References.INVENTORY_BAR_CELL_SIZE, 80, References.INVENTORY_BAR_CELL_SIZE, References.INVENTORY_BAR_CELL_SIZE);

            // Leben
            health100 = tileSet.getSubimage(0, 128, References.HEALTH_CELL_WIDTH, References.HEALTH_CELL_HEIGHT);
            health90 = tileSet.getSubimage(0, 128 + References.HEALTH_CELL_HEIGHT, References.HEALTH_CELL_WIDTH, References.HEALTH_CELL_HEIGHT);
            health80 = tileSet.getSubimage(0, 128 + 2 * References.HEALTH_CELL_HEIGHT, References.HEALTH_CELL_WIDTH, References.HEALTH_CELL_HEIGHT);
            health70 = tileSet.getSubimage(0, 128 + 3 * References.HEALTH_CELL_HEIGHT, References.HEALTH_CELL_WIDTH, References.HEALTH_CELL_HEIGHT);
            health60 = tileSet.getSubimage(0, 128 + 4 * References.HEALTH_CELL_HEIGHT, References.HEALTH_CELL_WIDTH, References.HEALTH_CELL_HEIGHT);
            health50 = tileSet.getSubimage(0, 128 + 5 * References.HEALTH_CELL_HEIGHT, References.HEALTH_CELL_WIDTH, References.HEALTH_CELL_HEIGHT);
            health40 = tileSet.getSubimage(0, 128 + 6 * References.HEALTH_CELL_HEIGHT, References.HEALTH_CELL_WIDTH, References.HEALTH_CELL_HEIGHT);
            health30 = tileSet.getSubimage(0, 128 + 7 * References.HEALTH_CELL_HEIGHT, References.HEALTH_CELL_WIDTH, References.HEALTH_CELL_HEIGHT);
            health20 = tileSet.getSubimage(0, 128 + 8 * References.HEALTH_CELL_HEIGHT, References.HEALTH_CELL_WIDTH, References.HEALTH_CELL_HEIGHT);
            health10 = tileSet.getSubimage(0, 128 + 9 * References.HEALTH_CELL_HEIGHT, References.HEALTH_CELL_WIDTH, References.HEALTH_CELL_HEIGHT);
            health0 = tileSet.getSubimage(0, 128 + 10 * References.HEALTH_CELL_HEIGHT, References.HEALTH_CELL_WIDTH, References.HEALTH_CELL_HEIGHT);

            // Energie
            energy100 = tileSet.getSubimage(0, 370, References.ENERGY_CELL_WIDTH, References.ENERGY_CELL_HEIGHT);
            energy90 = tileSet.getSubimage(0, 370 + References.ENERGY_CELL_HEIGHT, References.ENERGY_CELL_WIDTH, References.ENERGY_CELL_HEIGHT);
            energy80 = tileSet.getSubimage(0, 370 + 2 * References.ENERGY_CELL_HEIGHT, References.ENERGY_CELL_WIDTH, References.ENERGY_CELL_HEIGHT);
            energy70 = tileSet.getSubimage(0, 370 + 3 * References.ENERGY_CELL_HEIGHT, References.ENERGY_CELL_WIDTH, References.ENERGY_CELL_HEIGHT);
            energy60 = tileSet.getSubimage(0, 370 + 4 * References.ENERGY_CELL_HEIGHT, References.ENERGY_CELL_WIDTH, References.ENERGY_CELL_HEIGHT);
            energy50 = tileSet.getSubimage(0, 370 + 5 * References.ENERGY_CELL_HEIGHT, References.ENERGY_CELL_WIDTH, References.ENERGY_CELL_HEIGHT);
            energy40 = tileSet.getSubimage(0, 370 + 6 * References.ENERGY_CELL_HEIGHT, References.ENERGY_CELL_WIDTH, References.ENERGY_CELL_HEIGHT);
            energy30 = tileSet.getSubimage(0, 370 + 7 * References.ENERGY_CELL_HEIGHT, References.ENERGY_CELL_WIDTH, References.ENERGY_CELL_HEIGHT);
            energy20 = tileSet.getSubimage(0, 370 + 8 * References.ENERGY_CELL_HEIGHT, References.ENERGY_CELL_WIDTH, References.ENERGY_CELL_HEIGHT);
            energy10 = tileSet.getSubimage(0, 370 + 9 * References.ENERGY_CELL_HEIGHT, References.ENERGY_CELL_WIDTH, References.ENERGY_CELL_HEIGHT);
            energy0 = tileSet.getSubimage(0, 370 + 10 * References.ENERGY_CELL_HEIGHT, References.ENERGY_CELL_WIDTH, References.ENERGY_CELL_HEIGHT);

            // Durst
            thirst100 = tileSet.getSubimage(0, 670, References.THIRST_CELL_WIDTH, References.THIRST_CELL_HEIGHT);
            thirst90 = tileSet.getSubimage(0, 670 + References.THIRST_CELL_HEIGHT, References.THIRST_CELL_WIDTH, References.THIRST_CELL_HEIGHT);
            thirst80 = tileSet.getSubimage(0, 670 + 2 * References.THIRST_CELL_HEIGHT, References.THIRST_CELL_WIDTH, References.THIRST_CELL_HEIGHT);
            thirst70 = tileSet.getSubimage(0, 670 + 3 * References.THIRST_CELL_HEIGHT, References.THIRST_CELL_WIDTH, References.THIRST_CELL_HEIGHT);
            thirst60 = tileSet.getSubimage(0, 670 + 4 * References.THIRST_CELL_HEIGHT, References.THIRST_CELL_WIDTH, References.THIRST_CELL_HEIGHT);
            thirst50 = tileSet.getSubimage(0, 670 + 5 * References.THIRST_CELL_HEIGHT, References.THIRST_CELL_WIDTH, References.THIRST_CELL_HEIGHT);
            thirst40 = tileSet.getSubimage(0, 670 + 6 * References.THIRST_CELL_HEIGHT, References.THIRST_CELL_WIDTH, References.THIRST_CELL_HEIGHT);
            thirst30 = tileSet.getSubimage(0, 670 + 7 * References.THIRST_CELL_HEIGHT, References.THIRST_CELL_WIDTH, References.THIRST_CELL_HEIGHT);
            thirst20 = tileSet.getSubimage(0, 670 + 8 * References.THIRST_CELL_HEIGHT, References.THIRST_CELL_WIDTH, References.THIRST_CELL_HEIGHT);
            thirst10 = tileSet.getSubimage(0, 670 + 9 * References.THIRST_CELL_HEIGHT, References.THIRST_CELL_WIDTH, References.THIRST_CELL_HEIGHT);
            thirst0 = tileSet.getSubimage(0, 670 + 10 * References.THIRST_CELL_HEIGHT, References.THIRST_CELL_WIDTH, References.THIRST_CELL_HEIGHT);

            // Gegner
            enemyEye = ImageIO.read(ResourceLoader.class.getResourceAsStream("/img/enemyEye.png"));

        } catch (IOException ex) { System.out.println("Error: " + ex.getMessage()); }
    }

}
