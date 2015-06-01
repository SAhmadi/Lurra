package State.Menu;

import GameSaves.GameData.GameData;
import GameSaves.GameData.GameDataSave;
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
public class SoundMenuState extends State {

    // Inhaltsflaeche, Graphics-Obj und Zustands-Manger
    protected GamePanel gamePanel;
    protected Graphics graphics;
    protected StateManager stateManager;

    // Resources
    private BufferedImage menuBackground;
    private BufferedImage menuIlandBackground;
    private BufferedImage menuTitleImage;

    private ImageIcon backButton, backButtonPressed;
    private ImageIcon offButton, offButtonPressed;
    private ImageIcon onButton, onButtonPressed;

    /*
    * Menu Buttons
    * */
    private JButton offBtn;
    private JButton onBtn;
    private JButton backBtn;

    /*
    * Konstruktor - Initialisieren
    * */
    public SoundMenuState(Graphics graphics, GamePanel gamePanel, StateManager stateManager) {
        this.graphics = graphics;
        this.gamePanel = gamePanel;
        this.stateManager = stateManager;

        // Resource Initialisieren
        this.menuBackground = ResourceLoader.menuBackground;
        this.menuIlandBackground = ResourceLoader.menuIlandBackground;
        this.menuTitleImage = ResourceLoader.menuTitleImage;

        this.backButton = ResourceLoader.backButton;
        this.backButtonPressed = ResourceLoader.backButtonPressed;

        this.offButton = ResourceLoader.offButton;
        this.offButtonPressed = ResourceLoader.offButtonPressed;

        this.onButton = ResourceLoader.onButton;
        this.onButtonPressed = ResourceLoader.onButtonPressed;

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
        offBtn = new JButton(offButton);
        onBtn = new JButton(onButton);
        backBtn = new JButton(backButton);

        /*
        * Button Listeners
        * Aendert Sichtbarkeit der Buttons, die im Ober-/Unter-Menu sichbar sein sollen
        * */
        offBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Spiele Sound
                if(GameData.isSoundOn.equals("On"))
                    Sound.diamondSound.play();

                Sound.elevatorSound.stop();
                GameData.isSoundOn = "Off";
                GameDataSave.XMLSave();
            }
        });

        onBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Spiele Sound
                if(GameData.isSoundOn.equals("On"))
                    Sound.diamondSound.play();

                Sound.elevatorSound.play();
                GameData.isSoundOn = "On";
                GameDataSave.XMLSave();
            }
        });

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Spiele Sound
                if(GameData.isSoundOn.equals("On"))
                    Sound.diamondSound.play();

                gamePanel.remove(offBtn);
                gamePanel.remove(onBtn);
                gamePanel.remove(backBtn);

                gamePanel.revalidate();
                gamePanel.repaint();

                // Pushe StartMenu -> Starte SettingsMenuState
                stateManager.getGameStates().pop();
                stateManager.setActiveState(new SettingsMenuState(graphics, gamePanel, stateManager), -2);
            }
        });

        /*
        * Hinzufuegen und Positionieren der Buttons
        * */

        // Kein Layout, um Buttons selbst zu positionieren
        gamePanel.setLayout(null);

        // Avatar Button
        offBtn.setBounds(
                (ScreenDimensions.WIDTH - offButton.getIconWidth() * 3) / 4,
                ScreenDimensions.HEIGHT / 2 - offButton.getIconHeight() / 2,
                offButton.getIconWidth(),
                offButton.getIconHeight()
        );
        offBtn.setBorderPainted(false);
        offBtn.setFocusPainted(false);
        offBtn.setContentAreaFilled(false);
        offBtn.setOpaque(false);
        offBtn.setPressedIcon(offButtonPressed);
        offBtn.setVisible(true);
        gamePanel.add(offBtn);

        // Einstelllungen Button
        onBtn.setBounds(
                ((ScreenDimensions.WIDTH - onButton.getIconWidth() * 3) / 2) + onButton.getIconWidth(),
                ScreenDimensions.HEIGHT / 2 - onButton.getIconHeight() / 2,
                onButton.getIconWidth(),
                onButton.getIconHeight()
        );
        onBtn.setBorderPainted(false);
        onBtn.setFocusPainted(false);
        onBtn.setContentAreaFilled(false);
        onBtn.setOpaque(false);
        onBtn.setPressedIcon(onButtonPressed);
        onBtn.setVisible(true);
        gamePanel.add(onBtn);

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

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

}
