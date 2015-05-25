package State.Menu;

import GameData.GameData;
import Main.GamePanel;
import Main.ResourceLoader;
import Main.ScreenDimensions;
import Main.Sound;
import State.State;
import State.StateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

/*
* MenuState - Spielmenu
* */
public class SettingsMenuState extends State {

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
    private ImageIcon soundButton, soundButtonPressed;

    /*
    * Menu Buttons
    * */
    private JButton avatarBtn;
    private JButton soundBtn;
    private JButton backBtn;

    /*
    * Konstruktor - Initialisieren
    * */
    public SettingsMenuState(Graphics graphics, GamePanel gamePanel, StateManager stateManager) {
        this.graphics = graphics;
        this.gamePanel = gamePanel;
        this.stateManager = stateManager;

        // Resource Initialisieren
        this.menuBackground = ResourceLoader.menuBackground;
        this.menuIlandBackground = ResourceLoader.menuIlandBackground;
        this.menuTitleImage = ResourceLoader.menuTitleImage;

        this.avatarButton = ResourceLoader.avatarButton;
        this.avatarButtonPressed = ResourceLoader.avatarButtonPressed;

        this.backButton = ResourceLoader.backButton;
        this.backButtonPressed = ResourceLoader.backButtonPressed;

        this.soundButton = ResourceLoader.soundButton;
        this.soundButtonPressed = ResourceLoader.soundButtonPressed;

        // Initialisieren der Buttons
        this.init();
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
                (ScreenDimensions.WIDTH / 2) - (menuTitleImage.getWidth(null) / 2),
                (ScreenDimensions.HEIGHT / 4),
                menuTitleImage.getWidth(null), menuTitleImage.getHeight(null),
                null
        );


        // Initialisieren der Buttons
        avatarBtn = new JButton(avatarButton);
        soundBtn = new JButton(soundButton);
        backBtn = new JButton(backButton);

        /*
        * Button Listeners
        * Aendert Sichtbarkeit der Buttons, die im Ober-/Unter-Menu sichbar sein sollen
        * */
        avatarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Spiele Sound
                if(GameData.isSoundOn.equals("On"))
                    Sound.diamondSound.play();

                gamePanel.remove(soundBtn);
                gamePanel.remove(backBtn);
                gamePanel.remove(avatarBtn);

                gamePanel.revalidate();
                gamePanel.repaint();

                // Pushe StartMenu -> Starte SettingsMenuState
                stateManager.setActiveState(new AvatarMenuState(graphics, gamePanel, stateManager), -4);
            }
        });

        soundBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Spiele Sound
                if(GameData.isSoundOn.equals("On"))
                    Sound.diamondSound.play();

                gamePanel.remove(avatarBtn);
                gamePanel.remove(backBtn);
                gamePanel.remove(soundBtn);

                gamePanel.revalidate();
                gamePanel.repaint();

                // Pushe StartMenu -> Starte SettingsMenuState
                stateManager.setActiveState(new SoundMenuState(graphics, gamePanel, stateManager), -3);
            }
        });

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Spiele Sound
                if(GameData.isSoundOn.equals("On"))
                    Sound.diamondSound.play();

                gamePanel.remove(avatarBtn);
                gamePanel.remove(soundBtn);
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

        // Avatar Button
        avatarBtn.setBounds(
                (ScreenDimensions.WIDTH - avatarButton.getIconWidth() * 3) / 4,
                ScreenDimensions.HEIGHT / 2 - avatarButton.getIconHeight() / 2,
                avatarButton.getIconWidth(),
                avatarButton.getIconHeight()
        );
        avatarBtn.setBorderPainted(false);
        avatarBtn.setFocusPainted(false);
        avatarBtn.setContentAreaFilled(false);
        avatarBtn.setOpaque(false);
        avatarBtn.setPressedIcon(avatarButtonPressed);
        avatarBtn.setVisible(true);
        gamePanel.add(avatarBtn);

        // Einstelllungen Button
        soundBtn.setBounds(
                ((ScreenDimensions.WIDTH - soundButton.getIconWidth() * 3) / 2) + soundButton.getIconWidth(),
                ScreenDimensions.HEIGHT / 2 - soundButton.getIconHeight() / 2,
                soundButton.getIconWidth(),
                soundButton.getIconHeight()
        );
        soundBtn.setBorderPainted(false);
        soundBtn.setFocusPainted(false);
        soundBtn.setContentAreaFilled(false);
        soundBtn.setOpaque(false);
        soundBtn.setPressedIcon(soundButtonPressed);
        soundBtn.setVisible(true);
        gamePanel.add(soundBtn);

        // Beenden Button
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