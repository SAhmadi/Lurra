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
    public static Font textFieldFontBold;
    public static Font inventoryItemFont;

    // Menu
    public static BufferedImage menuIlandBackground;
    public static BufferedImage menuTitleImage;
    public static BufferedImage gameIntro;

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
    public static ImageIcon thorButton, thorButtonPressed;
    public static ImageIcon hulkButton, hulkButtonPressed;
    public static ImageIcon ironManButton, ironManButtonPressed;
    public static ImageIcon captainAmericaButton, captainAmericaButtonPressed;
    public static ImageIcon blackWidowButton, blackWidowButtonPressed;
    public static ImageIcon specialButton, specialButtonPressed;
    public static ImageIcon normalButton, normalButtonPressed;

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

    // Bluerock
    public static BufferedImage bluerockOn;
    public static BufferedImage bluerockOff;
    public static BufferedImage battery;
    public static BufferedImage NANDR;
    public static BufferedImage NANDL;
    public static BufferedImage switchOn;
    public static BufferedImage switchOff;

    // Ruestungen
    public static BufferedImage ironMan;
    public static BufferedImage captainAmerica;

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

    // Kaktus
    public static BufferedImage cactusRoot;
    public static BufferedImage cactus1;
    public static BufferedImage cactus2;
    public static BufferedImage cactusTop;

    // Marvel Helden
    public static BufferedImage ironManBullet;
    public static BufferedImage hulkBullet;
    public static BufferedImage captainAmericaShield;
    public static BufferedImage mjoelmir;
    public static BufferedImage blackBullet;

    //Spezialcharakter
    public static BufferedImage specialBullet;

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
            textFieldFont = CustomFont.createCustomFont("robotoLight.ttf");
            if (textFieldFont != null)
                textFieldFont = textFieldFont.deriveFont(18f);

            textFieldFontBold = CustomFont.createCustomFont("robotoRegular.ttf");
            if (textFieldFontBold != null)
                textFieldFontBold = textFieldFontBold.deriveFont(18f);

            inventoryItemFont = new Font("Arial", Font.BOLD, 14);

            // Hintergrundmusik und SFX
            Sound.waterSound = new Sound("water.wav");
            Sound.elevatorSound = new Sound("elevator.wav");
            Sound.boomSound = new Sound("boom.wav");
            Sound.jumpSound = new Sound("jump.wav");
            Sound.metalSound = new Sound("metal.wav");
            Sound.earthSound = new Sound("earth.wav");
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
            Sound.themeButtonSound = new Sound("themeButton.wav");
            Sound.ironManButtonSound = new Sound("ironManButton.wav");
            Sound.ironManJumpSound = new Sound("ironManJump.wav");
            Sound.ironManShootSound = new Sound("ironManShoot.wav");
            Sound.jarvisSound = new Sound ("jarvis.wav");
            Sound.jarvisDeadSound = new Sound("jarvisDead.wav");
            Sound.hulkButtonSound = new Sound("hulkButton.wav");
            Sound.hulkJumpSound = new Sound("hulkJump.wav");
            Sound.hulkClapSound = new Sound("hulkClap.wav");
            Sound.hulkBreathSound = new Sound ("hulkBreath.wav");
            Sound.hulkDeathSound = new Sound("hulkDeath.wav");
            Sound.captainAmericaButtonSound = new Sound("captainAmericaButton.wav");
            Sound.captainAmericaJumpSound = new Sound("captainAmericaJump.wav");
            Sound.captainAmericaThrowSound = new Sound("captainAmericaThrow.wav");
            Sound.captainAmericaDeathSound = new Sound("captainAmericaDeath.wav");
            Sound.captainAmericaEnoughSound = new Sound("captainAmericaEnough.wav");
            Sound.thorButtonSound = new Sound("thorButton.wav");
            Sound.thorJumpSound = new Sound("thorJump.wav");
            Sound.mjoelmirSound = new Sound("mjoelmir.wav");
            Sound.thorDeathSound = new Sound("thorDeath.wav");
            Sound.blackWidowButtonSound = new Sound("blackWidowButton.wav");
            Sound.blackWidowJumpSound = new Sound("blackWidowJump.wav");
            Sound.blackWidowDeathSound = new Sound("blackWidowDeath.wav");
            Sound.gunShot = new Sound("gunShot.wav");
            Sound.specialButtonSound = new Sound("specialButton.wav");
            Sound.specialShootSound = new Sound("specialShoot.wav");
            Sound.specialJumpSound = new Sound("specialJump.wav");
            Sound.specialHeartBeatSound = new Sound("specialHeartBeat.wav");
            Sound.specialDeathSound = new Sound("specialDeath.wav");
            Sound.normalButtonSound = new Sound("normalButton.wav");
            Sound.tickSound = new Sound ("tick.wav");
            Sound.beepSound = new Sound ("beep.wav");
            Sound.tntSound = new Sound ("tnt.wav");

            // Menu
            menuIlandBackground = ImageIO.read(ResourceLoader.class.getResourceAsStream("/img/Menu/menuIlandBackground.png"));
            menuTitleImage = ImageIO.read(ResourceLoader.class.getResourceAsStream("/img/Menu/lurraTitle.png"));
            gameIntro = ImageIO.read(ResourceLoader.class.getResourceAsStream("/img/Menu/lurraIntro.png"));

            // Buttons
            avatarButton = new ImageIcon(ResourceLoader.class.getResource("/img/Menu/MenuButtons/avatarButton.png"));
            avatarButtonPressed = new ImageIcon(ResourceLoader.class.getResource("/img/Menu/MenuButtons/avatarButton_Pressed.png"));

            backButton = new ImageIcon(ResourceLoader.class.getResource("/img/Menu/MenuButtons/backButton.png"));
            backButtonPressed = new ImageIcon(ResourceLoader.class.getResource("/img/Menu/MenuButtons/backButton_Pressed.png"));

            closeButton = new ImageIcon(ResourceLoader.class.getResource("/img/Menu/MenuButtons/closeButton.png"));
            closeButtonPressed = new ImageIcon(ResourceLoader.class.getResource("/img/Menu/MenuButtons/closeButton_Pressed.png"));

            continueGameButton = new ImageIcon(ResourceLoader.class.getResource("/img/Menu/MenuButtons/continueGameButton.png"));
            continueGameButtonPressed = new ImageIcon(ResourceLoader.class.getResource("/img/Menu/MenuButtons/continueGameButton_Pressed.png"));

            mainMenuButton = new ImageIcon(ResourceLoader.class.getResource("/img/Menu/MenuButtons/mainMenuButton.png"));
            mainMenuButtonPressed = new ImageIcon(ResourceLoader.class.getResource("/img/Menu/MenuButtons/mainMenuButton_Pressed.png"));

            newGameButton = new ImageIcon(ResourceLoader.class.getResource("/img/Menu/MenuButtons/newGameButton.png"));
            newGameButtonPressed = new ImageIcon("res/img/Menu/MenuButtons/newGameButton_Pressed.png");

            offButton = new ImageIcon(ResourceLoader.class.getResource("/img/Menu/MenuButtons/offButton.png"));
            offButtonPressed = new ImageIcon(ResourceLoader.class.getResource("/img/Menu/MenuButtons/offButton_Pressed.png"));

            onButton = new ImageIcon(ResourceLoader.class.getResource("/img/Menu/MenuButtons/onButton.png"));
            onButtonPressed = new ImageIcon(ResourceLoader.class.getResource("/img/Menu/MenuButtons/onButton_Pressed.png"));

            playLocalButton = new ImageIcon(ResourceLoader.class.getResource("/img/Menu/MenuButtons/playLocalButton.png"));
            playLocalButtonPressed = new ImageIcon(ResourceLoader.class.getResource("/img/Menu/MenuButtons/playLocalButton_Pressed.png"));

            playOnlineButton = new ImageIcon(ResourceLoader.class.getResource("/img/Menu/MenuButtons/playOnlineButton.png"));
            playOnlineButtonPressed = new ImageIcon(ResourceLoader.class.getResource("/img/Menu/MenuButtons/playOnlineButton_Pressed.png"));

            saveButton = new ImageIcon(ResourceLoader.class.getResource("/img/Menu/MenuButtons/saveButton.png"));
            saveButtonPressed = new ImageIcon(ResourceLoader.class.getResource("/img/Menu/MenuButtons/saveButton_Pressed.png"));

            settingsButton = new ImageIcon(ResourceLoader.class.getResource("/img/Menu/MenuButtons/settingsButton.png"));
            settingsButtonPressed = new ImageIcon(ResourceLoader.class.getResource("/img/Menu/MenuButtons/settingsButton_Pressed.png"));

            soundButton = new ImageIcon(ResourceLoader.class.getResource("/img/Menu/MenuButtons/soundButton.png"));
            soundButtonPressed = new ImageIcon(ResourceLoader.class.getResource("/img/Menu/MenuButtons/soundButton_Pressed.png"));

            startGameButton = new ImageIcon(ResourceLoader.class.getResource("/img/Menu/MenuButtons/startGameButton.png"));
            startGameButtonPressed = new ImageIcon(ResourceLoader.class.getResource("/img/Menu/MenuButtons/startGameButton_Pressed.png"));

            themesButton = new ImageIcon(ResourceLoader.class.getResource("/img/Menu/MenuButtons/themesButton.png"));
            themesButtonPressed = new ImageIcon(ResourceLoader.class.getResource("/img/Menu/MenuButtons/themesButton_Pressed.png"));

            thorButton = new ImageIcon(ResourceLoader.class.getResource("/img/Menu/MenuButtons/thorButton.png"));
            thorButtonPressed = new ImageIcon(ResourceLoader.class.getResource("/img/Menu/MenuButtons/thorButton_Pressed.png"));

            hulkButton = new ImageIcon(ResourceLoader.class.getResource("/img/Menu/MenuButtons/hulkButton.png"));
            hulkButtonPressed = new ImageIcon(ResourceLoader.class.getResource("/img/Menu/MenuButtons/hulkButton_Pressed.png"));

            ironManButton = new ImageIcon(ResourceLoader.class.getResource("/img/Menu/MenuButtons/ironManButton.png"));
            ironManButtonPressed = new ImageIcon(ResourceLoader.class.getResource("/img/Menu/MenuButtons/ironManButton_Pressed.png"));

            captainAmericaButton = new ImageIcon(ResourceLoader.class.getResource("/img/Menu/MenuButtons/captainAmericaButton.png"));
            captainAmericaButtonPressed = new ImageIcon(ResourceLoader.class.getResource("/img/Menu/MenuButtons/captainAmericaButton_Pressed.png"));

            blackWidowButton = new ImageIcon(ResourceLoader.class.getResource("/img/Menu/MenuButtons/blackWidowButton.png"));
            blackWidowButtonPressed = new ImageIcon(ResourceLoader.class.getResource("/img/Menu/MenuButtons/blackWidowButton_Pressed.png"));

            specialButton = new ImageIcon(ResourceLoader.class.getResource("/img/Menu/MenuButtons/specialButton.png"));
            specialButtonPressed = new ImageIcon(ResourceLoader.class.getResource("/img/Menu/MenuButtons/specialButton_Pressed.png"));

            normalButton = new ImageIcon(ResourceLoader.class.getResource("/img/Menu/MenuButtons/normalButton.png"));
            normalButtonPressed = new ImageIcon(ResourceLoader.class.getResource("/img/Menu/MenuButtons/normalButton_Pressed.png"));

            // Avatar
            maleCharacterButtonUnactive = new ImageIcon(ResourceLoader.class.getResource("/img/Menu/MenuButtons/maleCharacter_Unactive.jpg"));
            maleCharacterButtonActive = new ImageIcon(ResourceLoader.class.getResource("/img/Menu/MenuButtons/maleCharacter_Active.jpg"));

            femaleCharacterButtonUnactive = new ImageIcon(ResourceLoader.class.getResource("/img/Menu/MenuButtons/femaleCharacter_Unactive.jpg"));
            femaleCharacterButtonActive = new ImageIcon(ResourceLoader.class.getResource("/img/Menu/MenuButtons/femaleCharacter_Active.jpg"));

            // Multiplayer
            createOnlineGame = new ImageIcon(ResourceLoader.class.getResource("/img/Menu/MenuButtons/createOnlineGame.png"));
            createOnlineGamePressed = new ImageIcon(ResourceLoader.class.getResource("/img/Menu/MenuButtons/createOnlineGame_Pressed.png"));

            joinOnlineGame = new ImageIcon(ResourceLoader.class.getResource("/img/Menu/MenuButtons/joinOnlineGame.png"));
            joinOnlineGamePressed = new ImageIcon(ResourceLoader.class.getResource("/img/Menu/MenuButtons/joinOnlineGame_Pressed.png"));

            watchGame = new ImageIcon(ResourceLoader.class.getResource("/img/Menu/MenuButtons/spectatorGame.png"));
            watchGamePressed = new ImageIcon(ResourceLoader.class.getResource("/img/Menu/MenuButtons/spectatorGame_Pressed.png"));

            // TileSet
            BufferedImage tileSet = ImageIO.read(ResourceLoader.class.getResourceAsStream("/img/tileSet.png"));

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

            ironManBullet = tileSet.getSubimage(11 * References.TILE_SIZE, 32, References.TILE_SIZE, References.TILE_SIZE);
            hulkBullet = tileSet.getSubimage(8*References.TILE_SIZE, 64, References.TILE_SIZE, References.TILE_SIZE);
            captainAmericaShield = tileSet.getSubimage(9* References.TILE_SIZE, 64, References.TILE_SIZE, References.TILE_SIZE);
            mjoelmir = tileSet.getSubimage(10 * References.TILE_SIZE, 64, References.TILE_SIZE, References.TILE_SIZE);
            blackBullet = tileSet.getSubimage(11 * References.TILE_SIZE, 64, References.TILE_SIZE, References.TILE_SIZE);
            specialBullet = tileSet.getSubimage(12*References.TILE_SIZE, 64, References.TILE_SIZE, References.TILE_SIZE);

            // Kaktus
            cactusRoot = tileSet.getSubimage(12 * References.TILE_SIZE, 32, References.TILE_SIZE, References.TILE_SIZE);
            cactus1 = tileSet.getSubimage(13 * References.TILE_SIZE, 32, References.TILE_SIZE, References.TILE_SIZE);
            cactus2 = tileSet.getSubimage(14 * References.TILE_SIZE, 32, References.TILE_SIZE, References.TILE_SIZE);
            cactusTop = tileSet.getSubimage(15 * References.TILE_SIZE, 32, References.TILE_SIZE, References.TILE_SIZE);

            // Schalter
            switchOn = tileSet.getSubimage(16 * References.TILE_SIZE, 32, References.TILE_SIZE, References.TILE_SIZE);
            switchOff = tileSet.getSubimage(17 * References.TILE_SIZE, 32, References.TILE_SIZE, References.TILE_SIZE);

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

            // Bluerock
            bluerockOn = tileSet.getSubimage(12 * References.TILE_SIZE, 48, References.TILE_SIZE, References.TILE_SIZE);
            bluerockOff = tileSet.getSubimage(13 * References.TILE_SIZE, 48, References.TILE_SIZE, References.TILE_SIZE);
            battery = tileSet.getSubimage(14 * References.TILE_SIZE, 48, References.TILE_SIZE, References.TILE_SIZE);
            NANDL = tileSet.getSubimage(15 * References.TILE_SIZE, 48, References.TILE_SIZE, References.TILE_SIZE);
            NANDR = tileSet.getSubimage(16 * References.TILE_SIZE, 48, References.TILE_SIZE, References.TILE_SIZE);

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
            energy0 = energy10;

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
            thirst0 = thirst10;

            // Gegner
            enemyEye = ImageIO.read(ResourceLoader.class.getResourceAsStream("/img/enemyEye.png"));

            // Ruestungen
            ironMan = ImageIO.read(ResourceLoader.class.getResourceAsStream("/img/ironManTile.jpg"));
            captainAmerica = ImageIO.read(ResourceLoader.class.getResourceAsStream("/img/captainAmericaTile.jpg"));

        } catch (IOException ex) { System.out.println("Error: " + ex.getMessage()); }
    }

}
