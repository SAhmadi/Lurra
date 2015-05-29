package State.Menu;

import GameData.GameData;
import Main.*;
import State.State;
import State.StateManager;
import sun.security.jgss.krb5.ServiceCreds;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.swing.*;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/*
* MenuState - Spielmenu
* */
public class StartMenuState extends State {

    // Inhaltsflaeche, Graphics-Obj und Zustands-Manger
    protected GamePanel gamePanel;
    protected Graphics graphics;
    protected StateManager stateManager;

    // Resources
    private BufferedImage menuBackground;
    private BufferedImage menuIlandBackground;
    private BufferedImage menuTitleImage;

    private ImageIcon playLocalButton, playLocalButtonPressed;
    private ImageIcon playOnlineButton, playOnlineButtonPressed;
    private ImageIcon backButton, backButtonPressed;

    /*
    * Menu Buttons
    * */
    private JButton playLocalBtn;
    private JButton playOnlineBtn;
    private JButton backBtn;


    /*
    * Konstruktor - Initialisieren
    * */
    public StartMenuState(Graphics graphics, GamePanel gamePanel, StateManager stateManager) {
        this.graphics = graphics;
        this.gamePanel = gamePanel;
        this.stateManager = stateManager;

        // Resource Initialisieren
        this.menuBackground = ResourceLoader.menuBackground;
        this.menuIlandBackground = ResourceLoader.menuIlandBackground;
        this.menuTitleImage = ResourceLoader.menuTitleImage;

        this.playLocalButton = ResourceLoader.playLocalButton;
        this.playLocalButtonPressed = ResourceLoader.playLocalButtonPressed;

        this.playOnlineButton = ResourceLoader.playOnlineButton;
        this.playOnlineButtonPressed = ResourceLoader.playOnlineButtonPressed;

        this.backButton = ResourceLoader.backButton;
        this.backButtonPressed = ResourceLoader.backButtonPressed;

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
        playLocalBtn = new JButton(playLocalButton);
        playOnlineBtn = new JButton(playOnlineButton);
        backBtn = new JButton(backButton);

        /*
        * Button Listeners
        * Aendert Sichtbarkeit der Buttons, die im Ober-/Unter-Menu sichbar sein sollen
        * */
        playLocalBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Spiele Sound
                if(GameData.isSoundOn.equals("On"))
                    Sound.diamondSound.play();

                gamePanel.remove(playOnlineBtn);
                gamePanel.remove(backBtn);
                gamePanel.remove(playLocalBtn);

                gamePanel.revalidate();
                gamePanel.repaint();

                // Pushe StartMenu -> Starte StartMenu
                stateManager.setActiveState(new PlayLocalMenuState(graphics, gamePanel, stateManager), -5);
            }
        });

        playOnlineBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Spiele Sound
                if(GameData.isSoundOn.equals("On"))
                    Sound.diamondSound.play();

//                gamePanel.remove(playLocalBtn);
//                gamePanel.remove(backBtn);
//                gamePanel.remove(playOnlineBtn);
//
//                gamePanel.revalidate();
//                gamePanel.repaint();

                // Pushe StartMenu -> Starte SettingsMenuState
                //stateManager.setActiveState(new SettingsMenuState(graphics, gamePanel, stateManager), -2);
            }
        });

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Spiele Sound
                if(GameData.isSoundOn.equals("On"))
                    Sound.diamondSound.play();

                gamePanel.remove(playOnlineBtn);
                gamePanel.remove(playLocalBtn);
                gamePanel.remove(backBtn);

                gamePanel.revalidate();
                gamePanel.repaint();

                // Pushe StartMenu -> Starte SettingsMenuState
                stateManager.getGameStates().pop();
                stateManager.setActiveState(new MenuState(graphics, gamePanel, stateManager), 0);
            }
        });

        /*
        * Hinzufuegen und Positionieren der Buttons
        * */

        // Kein Layout, um Buttons selbst zu positionieren
        gamePanel.setLayout(null);

        // Lokal-Spielen Button
        playLocalBtn.setBounds(
                (ScreenDimensions.WIDTH - playLocalButton.getIconWidth() * 3) / 4,
                ScreenDimensions.HEIGHT / 2 - playLocalButton.getIconHeight() / 2,
                playLocalButton.getIconWidth(),
                playLocalButton.getIconHeight()
        );
        playLocalBtn.setBorderPainted(false);
        playLocalBtn.setFocusPainted(false);
        playLocalBtn.setContentAreaFilled(false);
        playLocalBtn.setOpaque(false);
        playLocalBtn.setPressedIcon(playLocalButtonPressed);
        playLocalBtn.setVisible(true);
        gamePanel.add(playLocalBtn);

        // Online-Spielen Button
        playOnlineBtn.setBounds(
                ((ScreenDimensions.WIDTH - playOnlineButton.getIconWidth() * 3) / 2) + playOnlineButton.getIconWidth(),
                ScreenDimensions.HEIGHT / 2 - playOnlineButton.getIconHeight() / 2,
                playOnlineButton.getIconWidth(),
                playOnlineButton.getIconHeight()
        );
        playOnlineBtn.setBorderPainted(false);
        playOnlineBtn.setFocusPainted(false);
        playOnlineBtn.setContentAreaFilled(false);
        playOnlineBtn.setOpaque(false);
        playOnlineBtn.setPressedIcon(playOnlineButtonPressed);
        playOnlineBtn.setVisible(true);
        gamePanel.add(playOnlineBtn);

        // Zurueck Button
        backBtn.setBounds(
                2*((ScreenDimensions.WIDTH - backButton.getIconWidth()*3)/4 + backButton.getIconWidth()) + ((ScreenDimensions.WIDTH - backButton.getIconWidth()*3)/4),
                ScreenDimensions.HEIGHT/2 - backButton.getIconHeight()/2,
                backButton.getIconWidth(),
                backButton.getIconHeight()
        );
        backBtn.setBorderPainted(false);
        backBtn.setFocusPainted(false);
        backBtn.setContentAreaFilled(false);
        backBtn.setOpaque(false);
        backBtn.setPressedIcon(backButtonPressed);
        backBtn.setVisible(true);
        gamePanel.add(backBtn);

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
