package State.Menu;

import Main.CustomFont;
import Main.GamePanel;
import Main.ScreenDimensions;
import Main.Sound;
import State.State;
import State.StateManager;
import State.Level1State;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/*
* MenuState - Spielmenu
* */
public class StartMenu extends State {

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
    private JButton loadGameButton;
    private String loadGameButtonLabel = "Fortsetzen";

    private JButton playLocalButton;
    private String playLocalButtonLabel = "Lokal";

    private JButton playOnlineButton;
    private String playOnlineButtonLabel = "Online";

    private JButton backButton;
    private String backButtonLabel = "Zurück";

    /*
    * Konstruktor - Initialisieren
    * */
    public StartMenu(Graphics graphics, GamePanel gamePanel, StateManager stateManager) {
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

        loadGameButton = new JButton(loadGameButtonLabel);
        playLocalButton = new JButton(playLocalButtonLabel);
        playOnlineButton = new JButton(playOnlineButtonLabel);

        backButton = new JButton(backButtonLabel);

        /*
        * Button Listeners
        * Aendert Sichtbarkeit der Buttons, die im Ober-/Unter-Menu sichbar sein sollen
        * */
        loadGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Spiele Sound
                Sound.getIsSoundOn();
                if(Sound.isSoundOn)
                    Sound.playDiamondSound();

                gamePanel.remove(loadGameButton);
                gamePanel.remove(playOnlineButton);
                gamePanel.remove(backButton);
                gamePanel.remove(playLocalButton);

                gamePanel.revalidate();
                gamePanel.repaint();

                // Push Levle1State -> Starte erstes Level
                stateManager.setActiveState(new Level1State(graphics, gamePanel, stateManager), 1);
            }
        });

        playLocalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Spiele Sound
                Sound.getIsSoundOn();
                if(Sound.isSoundOn)
                    Sound.playDiamondSound();

                gamePanel.remove(loadGameButton);
                gamePanel.remove(playOnlineButton);
                gamePanel.remove(backButton);
                gamePanel.remove(playLocalButton);

                // Push Levle1State -> Starte erstes Level
                stateManager.setActiveState(new Level1State(graphics, gamePanel, stateManager), 1);
            }
        });

        playOnlineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Spiele Sound
                Sound.getIsSoundOn();
                if(Sound.isSoundOn)
                    Sound.playDiamondSound();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Spiele Sound
                Sound.getIsSoundOn();
                if(Sound.isSoundOn)
                    Sound.playDiamondSound();

                gamePanel.remove(loadGameButton);
                gamePanel.remove(playOnlineButton);
                gamePanel.remove(backButton);
                gamePanel.remove(playLocalButton);

                gamePanel.revalidate();
                gamePanel.repaint();

                // Push MenuState -> Zurück zum MenuState
                stateManager.setActiveState(new MenuState(graphics, gamePanel, stateManager), 0);
            }
        });

        /*
        * Hinzufuegen und Positionieren der Buttons
        * */
        // Kein Layout, um Buttons selbst zu positionieren
        gamePanel.setLayout(null);

        // Spielstand Laden Button
        loadGameButton.setBounds( ((ScreenDimensions.WIDTH/4)-200)/2, ScreenDimensions.HEIGHT / 2 - 25, 200, 50 );
        loadGameButton.setBorderPainted(false);
        loadGameButton.setFocusPainted(false);
        loadGameButton.setBackground(StartMenu.DARK_GREY);
        loadGameButton.setForeground(Color.WHITE);
        loadGameButton.setFont(menuFont);
        //closeButton.setOpaque(false);
        loadGameButton.setOpaque(true);
        loadGameButton.setVisible(true);
        gamePanel.add(loadGameButton);

        // Lokal Button
        playLocalButton.setBounds( (ScreenDimensions.WIDTH/4)+((ScreenDimensions.WIDTH/4)-200)/2, ScreenDimensions.HEIGHT / 2 - 25, 200, 50 );
        playLocalButton.setBorderPainted(false);
        playLocalButton.setFocusPainted(false);
        playLocalButton.setBackground(StartMenu.DARK_GREY);
        playLocalButton.setForeground(Color.WHITE);
        playLocalButton.setFont(menuFont);
        playLocalButton.setOpaque(true);
        playLocalButton.setVisible(true);
        gamePanel.add(playLocalButton);

        // Online Button
        playOnlineButton.setBounds( 2*(ScreenDimensions.WIDTH/4)+((ScreenDimensions.WIDTH/4)-200)/2, ScreenDimensions.HEIGHT / 2 - 25, 200, 50 );
        playOnlineButton.setBorderPainted(false);
        playOnlineButton.setFocusPainted(false);
        playOnlineButton.setBackground(StartMenu.DARK_GREY);
        playOnlineButton.setForeground(Color.WHITE);
        playOnlineButton.setFont(menuFont);
        playOnlineButton.setOpaque(true);
        playOnlineButton.setVisible(true);
        gamePanel.add(playOnlineButton);

        // Zurueck Button
        backButton.setBounds( 3*(ScreenDimensions.WIDTH/4)+((ScreenDimensions.WIDTH/4)-200)/2, ScreenDimensions.HEIGHT / 2 - 25, 200, 50 );
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);
        backButton.setBackground(StartMenu.DARK_GREY);
        backButton.setForeground(Color.WHITE);
        backButton.setFont(menuFont);
        backButton.setOpaque(true);
        backButton.setVisible(true);
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
