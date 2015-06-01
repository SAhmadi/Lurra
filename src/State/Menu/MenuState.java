package State.Menu;

import GameSaves.GameData.GameData;
import Main.*;
import State.State;
import State.StateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

/*
* MenuState - Spielmenu
* */
public class MenuState extends State {

    // Inhaltsflaeche, Graphics-Obj und Zustands-Manger
    protected GamePanel gamePanel;
    protected Graphics graphics;
    protected StateManager stateManager;

    // Resources
    private BufferedImage menuBackground;
    private BufferedImage menuIlandBackground;
    private BufferedImage menuTitleImage;

    private ImageIcon avatarButton, avatarButtonPressed;
    private ImageIcon backButton, backButtonPressed;
    private ImageIcon closeButton, closeButtonPressed;
    private ImageIcon settingsButton, settingsButtonPressed;
    private ImageIcon soundButton, soundButtonPressed;
    private ImageIcon startGameButton, startGameButtonPressed;

    // Spieltitel
    private String gameTitleText = "LURRA";

    // Menu Schriftart
    private FontMetrics fontMetricsForTitle;
    private FontMetrics fontMetricsForMenu;
    private Font titleFont;
    private float titleFontDevSize = 80;
    private Font menuFont;

    // Farben
    private final static Color DIRT_BROWN = new Color(83, 63, 72);
    private final static Color LIME_GREEN = new Color(111, 229, 2);

    /*
    * Menu Buttons
    * */
    private JButton startBtn;
    private JButton settingsBtn;
    private JButton closeBtn;

    /*
    * Konstruktor - Initialisieren
    * */
    public MenuState(Graphics graphics, GamePanel gamePanel, StateManager stateManager) {
        this.graphics = graphics;
        this.gamePanel = gamePanel;
        this.stateManager = stateManager;

        // Erstelle neues Font
        // Fuer die Groesse: width*scale*devSize / devWidth
        //titleFont = CustomFont.createCustomFont("Munro.ttf", ScreenDimensions.WIDTH*ScreenDimensions.SCALE*titleFontDevSize / ScreenDimensions.DEVELOPMENT_WIDTH);
        //menuFont = CustomFont.createCustomFont("fixedsys.ttf", 20f);


        // Resource Initialisieren
        this.menuBackground = ResourceLoader.menuBackground;
        this.menuIlandBackground = ResourceLoader.menuIlandBackground;
        this.menuTitleImage = ResourceLoader.menuTitleImage;

        this.avatarButton = ResourceLoader.avatarButton;
        this.avatarButtonPressed = ResourceLoader.avatarButtonPressed;
        this.backButton = ResourceLoader.backButton;
        this.backButtonPressed = ResourceLoader.backButtonPressed;
        this.closeButton = ResourceLoader.closeButton;
        this.closeButtonPressed = ResourceLoader.closeButtonPressed;
        this.settingsButton = ResourceLoader.settingsButton;
        this.settingsButtonPressed = ResourceLoader.settingsButtonPressed;
        this.soundButton = ResourceLoader.soundButton;
        this.soundButtonPressed = ResourceLoader.soundButtonPressed;
        this.startGameButton = ResourceLoader.startGameButton;
        this.startGameButtonPressed = ResourceLoader.startGameButtonPressed;

        // Initialisieren der Buttons
        init();
    }

    /*
    * init - Eigentliches Initialisieren
    * Hinzufuegen und Positionieren der Buttons
    * */
    @Override
    public void init() {
        // Zeichne Himmel
        graphics.drawImage(menuBackground, 0, 0, ScreenDimensions.WIDTH, ScreenDimensions.HEIGHT, null);

        // Zeichne Insel
        graphics.drawImage(
                menuIlandBackground,
                (ScreenDimensions.WIDTH / 2) - (menuIlandBackground.getWidth(null) / 2),
                (ScreenDimensions.HEIGHT / 2) - (menuIlandBackground.getHeight(null) / 2),
                menuIlandBackground.getWidth(null), menuIlandBackground.getHeight(null),
                null
        );

        // Zeichne Title
        graphics.drawImage(
                menuTitleImage,
                (ScreenDimensions.WIDTH/2) - (menuTitleImage.getWidth(null)/2),
                (ScreenDimensions.HEIGHT/4),
                menuTitleImage.getWidth(null), menuTitleImage.getHeight(null),
                null
        );


        // Initialisieren der Buttons
        startBtn = new JButton(startGameButton);
        settingsBtn = new JButton(settingsButton);
        closeBtn = new JButton(closeButton);

        /*
        * Button Listeners
        * Aendert Sichtbarkeit der Buttons, die im Ober-/Unter-Menu sichbar sein sollen
        * */
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Spiele Sound
                if(GameData.isSoundOn.equals("On"))
                    Sound.diamondSound.play();

                gamePanel.remove(startBtn);
                gamePanel.remove(settingsBtn);
                gamePanel.remove(closeBtn);

                gamePanel.revalidate();
                gamePanel.repaint();

                // Pushe StartMenu -> Starte StartMenu
                stateManager.setActiveState(new StartMenuState(graphics, gamePanel, stateManager), -1);
            }
        });

        settingsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Spiele Sound
                if(GameData.isSoundOn.equals("On"))
                    Sound.diamondSound.play();

                gamePanel.remove(startBtn);
                gamePanel.remove(settingsBtn);
                gamePanel.remove(closeBtn);

                gamePanel.revalidate();
                gamePanel.repaint();

                // Pushe StartMenu -> Starte SettingsMenuState
                stateManager.setActiveState(new SettingsMenuState(graphics, gamePanel, stateManager), -2);
            }
        });

        closeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Spiele Sound
                if(GameData.isSoundOn.equals("On"))
                    Sound.diamondSound.play();

                // Schliessen des Programms
                System.exit(0);
            }
        });

        /*
        * Hinzufuegen und Positionieren der Buttons
        * */

        // Kein Layout, um Buttons selbst zu positionieren
        gamePanel.setLayout(null);

        // Start Button
        startBtn.setBounds(
                (ScreenDimensions.WIDTH - startGameButton.getIconWidth() * 3) / 4,
                ScreenDimensions.HEIGHT / 2 - startGameButton.getIconHeight() / 2,
                startGameButton.getIconWidth(),
                startGameButton.getIconHeight()
        );
        startBtn.setBorderPainted(false);
        startBtn.setFocusPainted(false);
        startBtn.setContentAreaFilled(false);
        startBtn.setOpaque(false);
        startBtn.setPressedIcon(startGameButtonPressed);
        startBtn.setVisible(true);
        gamePanel.add(startBtn);

        // Einstelllungen Button
        settingsBtn.setBounds(
                ((ScreenDimensions.WIDTH - settingsButton.getIconWidth() * 3) / 2) + settingsButton.getIconWidth(),
                ScreenDimensions.HEIGHT / 2 - settingsButton.getIconHeight() / 2,
                settingsButton.getIconWidth(),
                settingsButton.getIconHeight()
        );
        settingsBtn.setBorderPainted(false);
        settingsBtn.setFocusPainted(false);
        settingsBtn.setContentAreaFilled(false);
        settingsBtn.setOpaque(false);
        settingsBtn.setPressedIcon(settingsButtonPressed);
        settingsBtn.setVisible(true);
        gamePanel.add(settingsBtn);

        // Beenden Button
        closeBtn.setBounds(
                2*((ScreenDimensions.WIDTH - closeButton.getIconWidth()*3)/4 + closeButton.getIconWidth()) + ((ScreenDimensions.WIDTH - closeButton.getIconWidth()*3)/4),
                ScreenDimensions.HEIGHT/2 - settingsButton.getIconHeight()/2,
                settingsButton.getIconWidth(),
                settingsButton.getIconHeight()
        );
        closeBtn.setBorderPainted(false);
        closeBtn.setFocusPainted(false);
        closeBtn.setContentAreaFilled(false);
        closeBtn.setOpaque(false);
        closeBtn.setPressedIcon(closeButtonPressed);
        closeBtn.setVisible(true);
        gamePanel.add(closeBtn);

    }

    /*
    * update
    * */
    @Override
    public void update() {}

    /*
    * render
    * */
    @Override
    public void render(Graphics g) {}

    /*
    * EventListeners
    * */
    @Override
    public void keyPressed(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

}
