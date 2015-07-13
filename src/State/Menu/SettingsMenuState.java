package State.Menu;

import GameSaves.GameData.GameData;
import Main.GamePanel;
import Main.References;
import Main.ResourceLoader;
import Main.Sound;
import State.State;
import State.StateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

/**
 * Einstellungsmenu
 *
 * @author Sirat
 * */
public class SettingsMenuState extends State
{

    // Resources
    private ImageIcon avatarButton, avatarButtonPressed;
    private ImageIcon backButton, backButtonPressed;
    private ImageIcon soundButton, soundButtonPressed;
    private ImageIcon themesButton, themesButtonPressed;

    // Buttons
    private JButton avatarBtn;
    private JButton soundBtn;
    private JButton backBtn;
    //private JButton screenBtn;
    //private JButton screenBtn_1;

    /**
     * SettingsMenuState        Konstruktor der SettingsMenuState-Klasse
     *
     * @param graphics      Graphics Objekt
     * @param gamePanel     Spielinhaltsflaeche
     * @param stateManager  Zustandsmanager
     * */
    public SettingsMenuState(Graphics graphics, GamePanel gamePanel, StateManager stateManager)
    {
        super(gamePanel, graphics, stateManager);

        // Resource Initialisieren
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
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        GradientPaint gp = new GradientPaint(0, 0, References.NEON_GREEN, 0, References.SCREEN_HEIGHT, References.LIGHT_BLUE);
        g2d.setPaint(gp);

        g2d.fillRect(0, 0, References.SCREEN_WIDTH, References.SCREEN_HEIGHT);
        //graphics.drawImage(menuBackground, 0, 0, References.SCREEN_WIDTH, References.SCREEN_HEIGHT, null);

        // Zeichne Insel
        graphics.drawImage(
                ResourceLoader.menuIlandBackground,
                (References.SCREEN_WIDTH / 2) - (ResourceLoader.menuIlandBackground.getWidth(null) / 2),
                (References.SCREEN_HEIGHT / 2) - (ResourceLoader.menuIlandBackground.getHeight(null) / 2),
                ResourceLoader.menuIlandBackground.getWidth(null),
                ResourceLoader.menuIlandBackground.getHeight(null),
                null
        );

        // Zeichne Title
        graphics.drawImage(
                ResourceLoader.menuTitleImage,
                (References.SCREEN_WIDTH / 2) - (ResourceLoader.menuTitleImage.getWidth(null) / 2),
                (References.SCREEN_HEIGHT / 4),
                ResourceLoader.menuTitleImage.getWidth(null),
                ResourceLoader.menuTitleImage.getHeight(null),
                null
        );

        // Initialisieren der Buttons
        //screenBtn = new JButton("800x500");
        //screenBtn_1 = new JButton("1024x576");
        avatarBtn = new JButton(avatarButton);
        soundBtn = new JButton(soundButton);
        backBtn = new JButton(backButton);

        // BUTTONSLISTENERS
        avatarBtn.addActionListener(e ->
        {
            // Spiele Sound
            if(GameData.isSoundOn.equals("On"))
                Sound.avatarButtonSound.play();

            gamePanel.remove(soundBtn);
            gamePanel.remove(backBtn);
            gamePanel.remove(avatarBtn);
            //gamePanel.remove(screenBtn);
            //gamePanel.remove(screenBtn_1);

            gamePanel.revalidate();
            gamePanel.repaint();

            stateManager.getGameStates().pop();
            stateManager.setActiveState(new AvatarMenuState(graphics, gamePanel, stateManager), StateManager.AVATARMENUSTATE);
        });

        soundBtn.addActionListener(e ->
        {
            // Spiele Sound
            if(GameData.isSoundOn.equals("On"))
                Sound.soundButtonSound.play();

            gamePanel.remove(avatarBtn);
            gamePanel.remove(backBtn);
            gamePanel.remove(soundBtn);
            //gamePanel.remove(screenBtn);
            //gamePanel.remove(screenBtn_1);

            gamePanel.revalidate();
            gamePanel.repaint();

            stateManager.getGameStates().pop();
            stateManager.setActiveState(new SoundMenuState(graphics, gamePanel, stateManager), StateManager.SOUNDMENUSTATE);
        });

        backBtn.addActionListener(e ->
        {
            // Spiele Sound
            if(GameData.isSoundOn.equals("On"))
                Sound.backButtonSound.play();

            gamePanel.remove(avatarBtn);
            gamePanel.remove(soundBtn);
            gamePanel.remove(backBtn);
            //gamePanel.remove(screenBtn);
            //gamePanel.remove(screenBtn_1);


            gamePanel.revalidate();
            gamePanel.repaint();

            stateManager.getGameStates().pop();
            stateManager.setActiveState(new MenuState(graphics, gamePanel, stateManager), StateManager.MENUSTATE);
        });

        // BUTTON POSITIONIERUNG
        // Kein Layout, um Buttons selbst zu positionieren
        gamePanel.setLayout(null);

        /*screenBtn.setBounds(0,0,200,45);
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
        gamePanel.add(screenBtn_1);*/

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
    public void render(Graphics2D g) {}

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
