package State.Menu;

import Main.CustomFont;
import Main.GamePanel;
import Main.ScreenDimensions;
import Main.Sound;
import State.State;
import State.StateManager;
import State.Level1State;
import com.sun.xml.internal.ws.api.config.management.policy.ManagementAssertion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/*
* MenuState - Spielmenu
* */
public class SettingsMenu extends State {

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
    private JButton avatarButton;
    private String avatarButtonLabel = "Avatar";

    private JButton controlsButton;
    private String controlsButtonLabel = "Steuerung";

    private JCheckBox soundCheckBox;
    private String soundCheckBoxLabel = "Sound";

    private JButton backButton;
    private String backButtonLabel = "Zurück";

    /*
    * Konstruktor - Initialisieren
    * */
    public SettingsMenu(Graphics graphics, GamePanel gamePanel, StateManager stateManager) {
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
        avatarButton = new JButton(avatarButtonLabel);
        controlsButton = new JButton(controlsButtonLabel);
        soundCheckBox = new JCheckBox(soundCheckBoxLabel, true);

        backButton = new JButton(backButtonLabel);

        /*
        * Button Listeners
        * Aendert Sichtbarkeit der Buttons, die im Ober-/Unter-Menu sichbar sein sollen
        * */
        avatarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Spiele Sound
                Sound.getIsSoundOn();
                if(Sound.isSoundOn)
                    Sound.playDiamondSound();

                gamePanel.remove(avatarButton);
                gamePanel.remove(controlsButton);
                gamePanel.remove(backButton);
                gamePanel.remove(soundCheckBox);

                gamePanel.revalidate();
                gamePanel.repaint();

                // Push Levle1State -> Starte erstes Level
                //stateManager.setActiveState(new Level1State(graphics, gamePanel, stateManager), 1);
            }
        });

        controlsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Spiele Sound
                Sound.getIsSoundOn();
                if(Sound.isSoundOn)
                    Sound.playDiamondSound();

                gamePanel.remove(avatarButton);
                gamePanel.remove(controlsButton);
                gamePanel.remove(backButton);
                gamePanel.remove(soundCheckBox);

                // Push Levle1State -> Starte erstes Level
                //stateManager.setActiveState(new Level1State(graphics, gamePanel, stateManager), 1);
            }
        });

        soundCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Sound.getIsSoundOn();
                if(Sound.isSoundOn)
                    Sound.playDiamondSound();

                if(soundCheckBox.isSelected()) {
                    Sound.isSoundOn = true;
                    Sound.setIsSoundOn();
                }
                else {
                    Sound.isSoundOn = false;
                    System.out.println("isSound: " + Sound.isSoundOn);
                    Sound.setIsSoundOn();
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Spiele Sound
                if(Sound.isSoundOn)
                    Sound.playDiamondSound();

                gamePanel.remove(avatarButton);
                gamePanel.remove(controlsButton);
                gamePanel.remove(backButton);
                gamePanel.remove(soundCheckBox);

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
        avatarButton.setBounds( ((ScreenDimensions.WIDTH/4)-200)/2, ScreenDimensions.HEIGHT / 2 - 25, 200, 50 );
        avatarButton.setBorderPainted(false);
        avatarButton.setFocusPainted(false);
        avatarButton.setBackground(SettingsMenu.DARK_GREY);
        avatarButton.setForeground(Color.WHITE);
        avatarButton.setFont(menuFont);
        //closeButton.setOpaque(false);
        avatarButton.setOpaque(true);
        avatarButton.setVisible(true);
        gamePanel.add(avatarButton);

        // Lokal Button
        controlsButton.setBounds( (ScreenDimensions.WIDTH/4)+((ScreenDimensions.WIDTH/4)-200)/2, ScreenDimensions.HEIGHT / 2 - 25, 200, 50 );
        controlsButton.setBorderPainted(false);
        controlsButton.setFocusPainted(false);
        controlsButton.setBackground(SettingsMenu.DARK_GREY);
        controlsButton.setForeground(Color.WHITE);
        controlsButton.setFont(menuFont);
        controlsButton.setOpaque(true);
        controlsButton.setVisible(true);
        gamePanel.add(controlsButton);

        // Online Button
        //soundCheckBox.setBounds( 2*(ScreenDimensions.WIDTH/4)+((ScreenDimensions.WIDTH/4)-200)/2, ScreenDimensions.HEIGHT / 2 - 25, 200, 50 );
        soundCheckBox.setVisible(true);
        soundCheckBox.setFont(menuFont);
        soundCheckBox.setForeground(SettingsMenu.DARK_GREY);
        soundCheckBox.setBorderPaintedFlat(true);
        soundCheckBox.setBounds( 2*(ScreenDimensions.WIDTH/4)+((ScreenDimensions.WIDTH/4)-100)/2, ScreenDimensions.HEIGHT / 2 - 25, 100, 50 );
        gamePanel.add(soundCheckBox);

        // Zurueck Button
        backButton.setBounds( 3*(ScreenDimensions.WIDTH/4)+((ScreenDimensions.WIDTH/4)-200)/2, ScreenDimensions.HEIGHT / 2 - 25, 200, 50 );
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);
        backButton.setBackground(SettingsMenu.DARK_GREY);
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
