package State.Menu;

import GameSaves.GameData.GameData;
import Main.*;
import State.State;
import State.StateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Einstellungsmenu
 *
 * @author Sirat
 * */
public class SettingsMenuState extends State
{

    // Inhaltsflaeche, Graphics Objekt und Zustandsmanager
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

    // Buttons
    private JButton avatarBtn;
    private JButton soundBtn;
    private JButton backBtn;
    private JButton screenBtn;
    private JButton screenBtn_1;

    /**
     * SettingsMenuState        Konstruktor der SettingsMenuState-Klasse
     *
     * @param graphics      Graphics Objekt
     * @param gamePanel     Spielinhaltsflaeche
     * @param stateManager  Zustandsmanager
     * */
    public SettingsMenuState(Graphics graphics, GamePanel gamePanel, StateManager stateManager)
    {
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
                (References.SCREEN_HEIGHT / 2) - (menuIlandBackground.getHeight(null) / 2),
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
        screenBtn = new JButton("800x500");
        screenBtn_1 = new JButton("1024x576");
        avatarBtn = new JButton(avatarButton);
        soundBtn = new JButton(soundButton);
        backBtn = new JButton(backButton);

        // BUTTONSLISTENERS
        avatarBtn.addActionListener(e ->
        {
            // Spiele Sound
            if(GameData.isSoundOn.equals("On"))
                Sound.diamondSound.play();

            gamePanel.remove(soundBtn);
            gamePanel.remove(backBtn);
            gamePanel.remove(avatarBtn);

            gamePanel.revalidate();
            gamePanel.repaint();

            stateManager.getGameStates().pop();
            stateManager.setActiveState(new AvatarMenuState(graphics, gamePanel, stateManager), StateManager.AVATARMENUSTATE);
        });

        soundBtn.addActionListener(e ->
        {
            // Spiele Sound
            if(GameData.isSoundOn.equals("On"))
                Sound.diamondSound.play();

            gamePanel.remove(avatarBtn);
            gamePanel.remove(backBtn);
            gamePanel.remove(soundBtn);

            gamePanel.revalidate();
            gamePanel.repaint();

            stateManager.getGameStates().pop();
            stateManager.setActiveState(new SoundMenuState(graphics, gamePanel, stateManager), StateManager.SOUNDMENUSTATE);
        });

        backBtn.addActionListener(e ->
        {
            // Spiele Sound
            if(GameData.isSoundOn.equals("On"))
                Sound.diamondSound.play();

            gamePanel.remove(avatarBtn);
            gamePanel.remove(soundBtn);
            gamePanel.remove(backBtn);
            gamePanel.remove(screenBtn);
            gamePanel.remove(screenBtn_1);


            gamePanel.revalidate();
            gamePanel.repaint();

            stateManager.getGameStates().pop();
            stateManager.setActiveState(new MenuState(graphics, gamePanel, stateManager), StateManager.MENUSTATE);
        });

        screenBtn.addActionListener(e ->
        {
            try { Main.gameFrame.setSize(InOut.readoutoffile(1), InOut.readoutoffile(2)); }
            catch (IOException ex) { System.out.println("Error: " + ex.getMessage()); }
        });

        screenBtn_1.addActionListener(e ->
        {
            try { Main.gameFrame.setSize(InOut.readoutoffile(3), InOut.readoutoffile(4)); }
            catch (IOException ex) { System.out.println("Error: " + ex.getMessage()); }
        });

        // BUTTON POSITIONIERUNG
        // Kein Layout, um Buttons selbst zu positionieren
        gamePanel.setLayout(null);

        screenBtn.setBounds(0,0,200,45);
        screenBtn.setBackground(Color.green);
        screenBtn.setForeground(Color.white);
        screenBtn.setFont(ResourceLoader.textFieldFont);
        screenBtn.setVisible(true);
        gamePanel.add(screenBtn);

        screenBtn_1.setBounds(205,0,200,45);
        screenBtn_1.setBackground(Color.green);
        screenBtn_1.setForeground(Color.white);
        screenBtn_1.setFont(ResourceLoader.textFieldFont);
        screenBtn_1.setVisible(true);
        gamePanel.add(screenBtn_1);

        // Avatar Button
        avatarBtn.setBounds(
                (References.SCREEN_WIDTH - avatarButton.getIconWidth() * 3) / 4,
                References.SCREEN_HEIGHT / 2 - avatarButton.getIconHeight() / 2,
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

        // Sound Button
        soundBtn.setBounds(
                ((References.SCREEN_WIDTH - soundButton.getIconWidth() * 3) / 2) + soundButton.getIconWidth(),
                References.SCREEN_HEIGHT / 2 - soundButton.getIconHeight() / 2,
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

        // Zurueck Button
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
