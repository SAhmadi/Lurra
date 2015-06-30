package State.Menu;

import GameSaves.GameData.GameData;
import GameSaves.GameData.GameDataSave;
import Main.GamePanel;
import Main.References;
import Main.ResourceLoader;
import Main.Sound;
import State.State;
import State.StateManager;
import javafx.geometry.HorizontalDirection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

/**
 * Soundmenu
 *
 * @author Sirat
 * */
public class SoundMenuState extends State
{

    // Inhaltsflaeche, Graphics Objekt und Zustandsmanager
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

    // Buttons
    private JButton offBtn;
    private JButton onBtn;
    private JSlider volumeSlider;
    private JButton backBtn;

    /**
     * SoundMenuState       Konstruktor der SoundMenuState-Klasse
     *
     * @param graphics      Graphics Objekt
     * @param gamePanel     Spielinhaltsflaeche
     * @param stateManager  Zustandsmanager
     * */
    public SoundMenuState(Graphics graphics, GamePanel gamePanel, StateManager stateManager)
    {
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

    /**
     * init         Initialisieren des Menus
     * */
    @Override
    public void init()
    {
        // Zeichne Himmel
        graphics.drawImage(menuBackground, 0, 0, References.SCREEN_WIDTH, References.SCREEN_HEIGHT, null);

        // Zeichne Insel
        graphics.drawImage(
                menuIlandBackground,
                (References.SCREEN_WIDTH / 2) - (menuIlandBackground.getWidth(null) / 2),
                (References.SCREEN_WIDTH / 2) - (menuIlandBackground.getHeight(null) / 2),
                menuIlandBackground.getWidth(null), menuIlandBackground.getHeight(null),
                null
        );

        // Zeichne Title
        graphics.drawImage(
                menuTitleImage,
                (References.SCREEN_WIDTH / 2) - (menuTitleImage.getWidth(null) / 2),
                (References.SCREEN_HEIGHT / 4),
                menuTitleImage.getWidth(null), menuTitleImage.getHeight(null),
                null
        );

        // Initialisieren der Buttons
        offBtn = new JButton(offButton);
        onBtn = new JButton(onButton);
        volumeSlider = new JSlider( JSlider.HORIZONTAL, 0, 100, 50);
        backBtn = new JButton(backButton);

        // BUTTONSLISTENERS
        offBtn.addActionListener(e ->
        {
            // Spiele Sound
            if(GameData.isSoundOn.equals("On")) Sound.offButtonSound.play();

            Sound.elevatorSound.stop();
            GameData.isSoundOn = "Off";
            GameDataSave.XMLSave();
        });

        onBtn.addActionListener(e ->
        {
            // Spiele Sound
            if(GameData.isSoundOn.equals("On")) Sound.onButtonSound.play();

            Sound.elevatorSound.play();
            GameData.isSoundOn = "On";
            GameDataSave.XMLSave();
        });

        backBtn.addActionListener(e ->
        {
            // Spiele Sound
            if(GameData.isSoundOn.equals("On")) Sound.backButtonSound.play();

            gamePanel.remove(offBtn);
            gamePanel.remove(onBtn);
            gamePanel.remove(backBtn);

            gamePanel.revalidate();
            gamePanel.repaint();

            stateManager.getGameStates().pop();
            stateManager.setActiveState(new SettingsMenuState(graphics, gamePanel, stateManager), StateManager.SETTINGSMENUSTATE);
        });


        // Kein Layout, um Buttons selbst zu positionieren
        gamePanel.setLayout(null);

        // Avatar Button
        offBtn.setBounds(
                (References.SCREEN_WIDTH - offButton.getIconWidth() * 3) / 4,
                References.SCREEN_HEIGHT / 2 - offButton.getIconHeight() / 2,
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
                ((References.SCREEN_WIDTH - onButton.getIconWidth() * 3) / 2) + onButton.getIconWidth(),
                References.SCREEN_HEIGHT / 2 - onButton.getIconHeight() / 2,
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

        volumeSlider.setLocation(References.SCREEN_WIDTH/2, References.SCREEN_HEIGHT/2);
        volumeSlider.setVisible(true);
        volumeSlider.setMajorTickSpacing(10);
        volumeSlider.setMinorTickSpacing(1);
        volumeSlider.setPaintLabels(true);
        volumeSlider.setPaintTicks(true);
        gamePanel.add(volumeSlider);

        // Beenden Button
        backBtn.setBounds(
                2*((References.SCREEN_WIDTH - backButton.getIconWidth()*3)/4 + backButton.getIconWidth()) + ((References.SCREEN_WIDTH - backButton.getIconWidth()*3)/4),
                References.SCREEN_HEIGHT/2 - backButton.getIconHeight()/2,
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

    @Override
    public void update() {}

    @Override
    public void render(Graphics g) {}

    // EVENTS
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
    public void mouseWheelMoved(MouseWheelEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {}

}
