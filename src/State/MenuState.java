package State;

import Main.CustomFont;
import Main.GamePanel;
import Main.ScreenDimensions;
import Main.Sound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/*
* MenuState - Spielmenu
* */
public class MenuState extends State {

    // Inhaltsflaeche, Graphics-Obj und Zustands-Manger
    protected GamePanel gamePanel;
    protected Graphics graphics;
    protected StateManager stateManager;

    // Menu Schriftart
    private Font menuFont;

    // Farben
    private final static Color DARK_GREY = new Color(23, 23, 23);

    /*
    * Menu Buttons und ihre Beschriftung
    * */
    private JButton startButton;
    private String startButtonLabel = "Spiel starten";

    private JButton settingsButton;
    private String settingsButtonLabel = "Einstellungen";

    private JButton avatarButton;
    private String avatarButtonLabel = "Avatar";

    private JButton closeButton;
    private String closeButtonLabel = "Beenden";

    private JButton playLocalButton;
    private String playLocalButtonLabel = "Lokal";

    private JButton playOnlineButton;
    private String playOnlineButtonLabel = "Online";

    private JButton controlsButton;
    private String controlsButtonLabel = "Steuerung";

    private JButton backButton;
    private String backButtonLabel = "Zurück";

    private JRadioButton soundButton;
    private String soundButtonLabel = "Sound an";

    /*
    * Konstruktor - Initialisieren
    * */
    public MenuState(Graphics graphics, GamePanel gamePanel, StateManager stateManager) {
        this.graphics = graphics;
        this.gamePanel = gamePanel;
        this.stateManager = stateManager;

        // Erstelle neues Font
        menuFont = CustomFont.createCustomFont("fixedsys.ttf");

        // Initialisieren der Buttons
        init();
    }

    /*
    * init - Eigentliches Initialisieren
    * Hinzufuegen und Positionieren der Buttons
    * */
    @Override
    public void init() {
        // Initialisieren der Buttons
        startButton = new JButton(startButtonLabel);
        settingsButton = new JButton(settingsButtonLabel);
        closeButton = new JButton(closeButtonLabel);

        playLocalButton = new JButton(playLocalButtonLabel);
        playOnlineButton = new JButton(playOnlineButtonLabel);
        controlsButton = new JButton(controlsButtonLabel);
        avatarButton = new JButton(avatarButtonLabel);
        soundButton = new JRadioButton(soundButtonLabel, true);

        backButton = new JButton(backButtonLabel);

        /*
        * Button Listeners
        * Aendert Sichtbarkeit der Buttons, die im Ober-/Unter-Menu sichbar sein sollen
        * */
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Spiele Sound
                Sound.playDiamondSound();

                startButton.setVisible(false);
                settingsButton.setVisible(false);
                avatarButton.setVisible(false);
                closeButton.setVisible(false);
                soundButton.setVisible(false);

                playLocalButton.setVisible(true);
                playOnlineButton.setVisible(true);
                backButton.setVisible(true);

                gamePanel.revalidate();
                gamePanel.repaint();
            }
        });

        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Spiele Sound
                Sound.playDiamondSound();

                startButton.setVisible(false);
                settingsButton.setVisible(false);
                closeButton.setVisible(false);

                controlsButton.setVisible(true);
                avatarButton.setVisible(true);
                backButton.setVisible(true);

                gamePanel.revalidate();
                gamePanel.repaint();
            }
        });

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Spiele Sound
                Sound.playDiamondSound();

                // Schliessen des Programms
                System.exit(0);
            }
        });

        playLocalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Spiele Sound
                Sound.playDiamondSound();

                gamePanel.remove(startButton);
                gamePanel.remove(settingsButton);
                gamePanel.remove(avatarButton);
                gamePanel.remove(closeButton);
                gamePanel.remove(playOnlineButton);
                gamePanel.remove(controlsButton);
                gamePanel.remove(backButton);
                gamePanel.remove(playLocalButton);
                gamePanel.remove(soundButton);

                // Push Levle1State -> Starte erstes Level
                stateManager.setActiveState(new Level1State(graphics, gamePanel, stateManager), 1);
            }
        });

        playOnlineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Spiele Sound
                Sound.playDiamondSound();
            }
        });

        controlsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Spiele Sound
                Sound.playDiamondSound();
            }
        });

        soundButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Spiele Sound
                Sound.playDiamondSound();
                //Sound.stopElevatorSound();

            }
        });

        avatarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Spiele Sound
                Sound.playDiamondSound();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Spiele Sound
                Sound.playDiamondSound();

                playLocalButton.setVisible(false);
                playOnlineButton.setVisible(false);
                controlsButton.setVisible(false);
                backButton.setVisible(false);
                avatarButton.setVisible(false);

                startButton.setVisible(true);
                settingsButton.setVisible(true);
                closeButton.setVisible(true);
                soundButton.setVisible(false);

                gamePanel.revalidate();
                gamePanel.repaint();
            }
        });

        /*
        * Hinzufuegen und Positionieren der Buttons
        * */

        // Kein Layout, um Buttons selbst zu positionieren
        gamePanel.setLayout(null);

        // Start Button
        startButton.setBounds((ScreenDimensions.WIDTH - 250 * 3) / 4, ScreenDimensions.HEIGHT / 2 - 30, 250, 60);
        startButton.setBorderPainted(false);
        startButton.setFocusPainted(false);
        startButton.setBackground(MenuState.DARK_GREY);
        startButton.setForeground(Color.WHITE);
        startButton.setFont(menuFont);
        //startButton.setOpaque(false);
        startButton.setOpaque(true);
        startButton.setVisible(true);
        gamePanel.add(startButton);

        // Einstelllungs Button
        settingsButton.setBounds(((ScreenDimensions.WIDTH - 250 * 3) / 2) + 250, ScreenDimensions.HEIGHT / 2 - 30, 250, 60);
        settingsButton.setBorderPainted(false);
        settingsButton.setFocusPainted(false);
        settingsButton.setBackground(MenuState.DARK_GREY);
        settingsButton.setForeground(Color.WHITE);
        settingsButton.setFont(menuFont);
        //settingsButton.setOpaque(false);
        settingsButton.setOpaque(true);
        settingsButton.setVisible(true);
        gamePanel.add(settingsButton);

        // Beenden Button
        closeButton.setBounds(2 * ((ScreenDimensions.WIDTH - 250 * 3) / 4 + 250) + ((ScreenDimensions.WIDTH - 250 * 3) / 4), ScreenDimensions.HEIGHT / 2 - 30, 250, 60);
        closeButton.setBorderPainted(false);
        closeButton.setFocusPainted(false);
        closeButton.setBackground(MenuState.DARK_GREY);
        closeButton.setForeground(Color.WHITE);
        closeButton.setFont(menuFont);
        //closeButton.setOpaque(false);
        closeButton.setOpaque(true);
        closeButton.setVisible(true);
        gamePanel.add(closeButton);

        // Lokal Button
        playLocalButton.setBounds((ScreenDimensions.WIDTH - 250 * 3) / 4, ScreenDimensions.HEIGHT / 2 - 30, 250, 60);
        playLocalButton.setBorderPainted(false);
        playLocalButton.setFocusPainted(false);
        playLocalButton.setBackground(MenuState.DARK_GREY);
        playLocalButton.setForeground(Color.WHITE);
        playLocalButton.setFont(menuFont);
        playLocalButton.setOpaque(true);
        playLocalButton.setVisible(false);
        gamePanel.add(playLocalButton);

        // Online Button
        playOnlineButton.setBounds(((ScreenDimensions.WIDTH - 250 * 3) / 2) + 250, ScreenDimensions.HEIGHT / 2 - 30, 250, 60);
        playOnlineButton.setBorderPainted(false);
        playOnlineButton.setFocusPainted(false);
        playOnlineButton.setBackground(MenuState.DARK_GREY);
        playOnlineButton.setForeground(Color.WHITE);
        playOnlineButton.setFont(menuFont);
        playOnlineButton.setOpaque(true);
        playOnlineButton.setVisible(false);
        gamePanel.add(playOnlineButton);

        // Steuerung Button
        controlsButton.setBounds(((ScreenDimensions.WIDTH - 250 * 3) / 2) + 250, ScreenDimensions.HEIGHT / 2 - 30, 250, 60);
        controlsButton.setBorderPainted(false);
        controlsButton.setFocusPainted(false);
        controlsButton.setBackground(MenuState.DARK_GREY);
        controlsButton.setForeground(Color.WHITE);
        controlsButton.setFont(menuFont);
        controlsButton.setOpaque(true);
        controlsButton.setVisible(false);
        gamePanel.add(controlsButton);

        // Avatar
        avatarButton.setBounds((ScreenDimensions.WIDTH - 250 * 3) / 4, ScreenDimensions.HEIGHT / 2 - 30, 250, 60);
        avatarButton.setBorderPainted(false);
        avatarButton.setFocusPainted(false);
        avatarButton.setBackground(MenuState.DARK_GREY);
        avatarButton.setForeground(Color.WHITE);
        avatarButton.setFont(menuFont);
        avatarButton.setOpaque(true);
        avatarButton.setVisible(false);
        gamePanel.add(avatarButton);

        // Sound
        soundButton.setBounds(((ScreenDimensions.WIDTH - 250 * 3) / 2) + 250, ScreenDimensions.HEIGHT / 2 + 90, 250, 60);
        soundButton.setBorderPainted(false);
        soundButton.setFocusPainted(false);
        soundButton.setBackground(MenuState.DARK_GREY);
        soundButton.setForeground(Color.WHITE);
        soundButton.setFont(menuFont);
        soundButton.setOpaque(true);
        soundButton.setVisible(false);
        gamePanel.add(soundButton);


        // Zurueck Button
        backButton.setBounds( 2*((ScreenDimensions.WIDTH-250*3)/4+250) + ((ScreenDimensions.WIDTH-250*3)/4), ScreenDimensions.HEIGHT/2-30, 250, 60 );
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);
        backButton.setBackground(MenuState.DARK_GREY);
        backButton.setForeground(Color.WHITE);
        backButton.setFont(menuFont);
        backButton.setOpaque(true);
        backButton.setVisible(false);
        gamePanel.add(backButton);
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

}
