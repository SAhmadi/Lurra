package State.Menu;

import GameSaves.GameData.GameData;
import Main.*;
import State.State;
import State.StateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

/**
 * Hauptmenu-Zustand des Spiels
 *
 * @author Sirat
 * */
public class MenuState extends State
{
    // Inhaltsflaeche, Graphics Objekt und Zustandsmanager
    protected GamePanel gamePanel;
    protected Graphics graphics;
    protected StateManager stateManager;

    // Resources
    private BufferedImage menuBackground;
    private BufferedImage menuIlandBackground;
    private BufferedImage menuTitleImage;

    private ImageIcon closeButton, closeButtonPressed;
    private ImageIcon settingsButton, settingsButtonPressed;
    private ImageIcon startGameButton, startGameButtonPressed;

    // Buttons
    private JButton startBtn;
    private JButton settingsBtn;
    private JButton closeBtn;

    /**
     * MenuState            Konstruktor der MenuState-Klasse
     *
     * @param graphics      Graphics Objekt
     * @param gamePanel     Spielinhaltsflaeche
     * @param stateManager  Zustandsmanager
     * */
    public MenuState(Graphics graphics, GamePanel gamePanel, StateManager stateManager)
    {
        this.graphics = graphics;
        this.gamePanel = gamePanel;
        this.stateManager = stateManager;

        // Resource Initialisieren
        this.menuBackground = ResourceLoader.menuBackground;
        this.menuIlandBackground = ResourceLoader.menuIlandBackground;
        this.menuTitleImage = ResourceLoader.menuTitleImage;

        this.closeButton = ResourceLoader.closeButton;
        this.closeButtonPressed = ResourceLoader.closeButtonPressed;
        this.settingsButton = ResourceLoader.settingsButton;
        this.settingsButtonPressed = ResourceLoader.settingsButtonPressed;
        this.startGameButton = ResourceLoader.startGameButton;
        this.startGameButtonPressed = ResourceLoader.startGameButtonPressed;

        init(); // Initialisieren des Hauptmenus
    }

    /**
     * init         Initialisieren des Hauptmenus
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

        // Zeichne Titel
        graphics.drawImage(
                menuTitleImage,
                (References.SCREEN_WIDTH/2) - (menuTitleImage.getWidth(null)/2),
                (References.SCREEN_HEIGHT/4),
                menuTitleImage.getWidth(null), menuTitleImage.getHeight(null),
                null
        );

        // Initialisieren der Buttons
        startBtn = new JButton(startGameButton);
        settingsBtn = new JButton(settingsButton);
        closeBtn = new JButton(closeButton);

        // ACTIONLISTENERS
        startBtn.addActionListener(e ->
        {
            // Spiele Sound
            if(GameData.isSoundOn.equals("On"))
                Sound.diamondSound.play();

            gamePanel.remove(startBtn);
            gamePanel.remove(settingsBtn);
            gamePanel.remove(closeBtn);

            gamePanel.revalidate();
            gamePanel.repaint();

            stateManager.setActiveState(new StartMenuState(graphics, gamePanel, stateManager), StateManager.STARTMENUSTATE);
        });

        settingsBtn.addActionListener(e ->
        {
            // Spiele Sound
            if(GameData.isSoundOn.equals("On"))
                Sound.diamondSound.play();

            gamePanel.remove(startBtn);
            gamePanel.remove(settingsBtn);
            gamePanel.remove(closeBtn);

            gamePanel.revalidate();
            gamePanel.repaint();

            stateManager.setActiveState(new SettingsMenuState(graphics, gamePanel, stateManager), StateManager.SETTINGSMENUSTATE);
        });

        closeBtn.addActionListener(e ->
        {
            // Spiele Sound
            if(GameData.isSoundOn.equals("On")) Sound.diamondSound.play();
            System.exit(0); // Schliessen des Programms
        });

        // BUTTON POSITIONIERUNG
        gamePanel.setLayout(null);  // Kein Layout festlegen, um Buttons absolut zu positionieren

        // Start Button
        startBtn.setBounds(
                (References.SCREEN_WIDTH - startGameButton.getIconWidth() * 3) / 4,
                References.SCREEN_HEIGHT / 2 - startGameButton.getIconHeight() / 2,
                startGameButton.getIconWidth(),
                startGameButton.getIconHeight()
        );
        startBtn.setBorderPainted(false);
        startBtn.setFocusPainted(false);
        startBtn.setContentAreaFilled(false);
        startBtn.setOpaque(false);
        startBtn.setPressedIcon(startGameButtonPressed);
        startBtn.setVisible(true);
        gamePanel.add(startBtn);

        // Einstelllungen Button
        settingsBtn.setBounds(
                ((References.SCREEN_WIDTH - settingsButton.getIconWidth() * 3) / 2) + settingsButton.getIconWidth(),
                References.SCREEN_HEIGHT / 2 - settingsButton.getIconHeight() / 2,
                settingsButton.getIconWidth(),
                settingsButton.getIconHeight()
        );
        settingsBtn.setBorderPainted(false);
        settingsBtn.setFocusPainted(false);
        settingsBtn.setContentAreaFilled(false);
        settingsBtn.setOpaque(false);
        settingsBtn.setPressedIcon(settingsButtonPressed);
        settingsBtn.setVisible(true);
        gamePanel.add(settingsBtn);

        // Beenden Button
        closeBtn.setBounds(
                2*((References.SCREEN_WIDTH - closeButton.getIconWidth()*3)/4 + closeButton.getIconWidth()) + ((References.SCREEN_WIDTH - closeButton.getIconWidth()*3)/4),
                References.SCREEN_HEIGHT/2 - settingsButton.getIconHeight()/2,
                settingsButton.getIconWidth(),
                settingsButton.getIconHeight()
        );
        closeBtn.setBorderPainted(false);
        closeBtn.setFocusPainted(false);
        closeBtn.setContentAreaFilled(false);
        closeBtn.setOpaque(false);
        closeBtn.setPressedIcon(closeButtonPressed);
        closeBtn.setVisible(true);
        gamePanel.add(closeBtn);
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
