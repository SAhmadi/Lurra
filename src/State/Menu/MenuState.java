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

    private JButton closeButton;
    private String closeButtonLabel = "Beenden";

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

        /*
        * Button Listeners
        * Aendert Sichtbarkeit der Buttons, die im Ober-/Unter-Menu sichbar sein sollen
        * */
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Spiele Sound
                Sound.getIsSoundOn();
                if(Sound.isSoundOn)
                    Sound.playDiamondSound();

                gamePanel.remove(startButton);
                gamePanel.remove(settingsButton);
                gamePanel.remove(closeButton);

                gamePanel.revalidate();
                gamePanel.repaint();

                // Pushe StartMenu -> Starte StartMenu
                stateManager.setActiveState(new StartMenu(graphics, gamePanel, stateManager), -1);
            }
        });

        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Spiele Sound
                Sound.getIsSoundOn();
                if(Sound.isSoundOn)
                    Sound.playDiamondSound();

                gamePanel.remove(startButton);
                gamePanel.remove(settingsButton);
                gamePanel.remove(closeButton);

                gamePanel.revalidate();
                gamePanel.repaint();

                // Pushe StartMenu -> Starte StartMenu
                stateManager.setActiveState(new SettingsMenu(graphics, gamePanel, stateManager), -2);
            }
        });

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Spiele Sound
                Sound.getIsSoundOn();
                if(Sound.isSoundOn)
                    Sound.playDiamondSound();

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
        startButton.setBounds( (ScreenDimensions.WIDTH - 250 * 3) / 4, ScreenDimensions.HEIGHT / 2 - 30, 250, 60 );
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
