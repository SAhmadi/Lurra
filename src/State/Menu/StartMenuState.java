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
 * Spielauswahlmenu
 *
 * @author Sirat
 * */
public class StartMenuState extends State
{

    // Inhaltsflaeche, Graphics Objekt und Zustandsmanager
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

    // Buttons
    private JButton playLocalBtn;
    private JButton playOnlineBtn;
    private JButton backBtn;

    /**
     * StartMenuState       Konstruktor der StartMenuState-Klasse
     *
     * @param graphics      Graphics Objekt
     * @param gamePanel     Spielinhaltsflaeche
     * @param stateManager  Zustandsmanager
     * */
    public StartMenuState(Graphics graphics, GamePanel gamePanel, StateManager stateManager)
    {
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
                (References.SCREEN_WIDTH/2) - (menuTitleImage.getWidth(null)/2),
                (References.SCREEN_HEIGHT/4),
                menuTitleImage.getWidth(null), menuTitleImage.getHeight(null),
                null
        );

        // Initialisieren der Buttons
        playLocalBtn = new JButton(playLocalButton);
        playOnlineBtn = new JButton(playOnlineButton);
        backBtn = new JButton(backButton);

        // BUTTONLISTENERS
        playLocalBtn.addActionListener(e ->
        {
            // Spiele Sound
            if (GameData.isSoundOn.equals("On")) Sound.localButtonSound.play();

            gamePanel.remove(playOnlineBtn);
            gamePanel.remove(backBtn);
            gamePanel.remove(playLocalBtn);

            gamePanel.revalidate();
            gamePanel.repaint();

            stateManager.getGameStates().pop();
            stateManager.setActiveState(new PlayLocalMenuState(graphics, gamePanel, stateManager), StateManager.PLAYLOCALMENUSTATE);
        });

        playOnlineBtn.addActionListener(e ->
        {
            // Spiele Sound
            if(GameData.isSoundOn.equals("On")) Sound.onlineButtonSound.play();

            gamePanel.remove(playOnlineBtn);
            gamePanel.remove(backBtn);
            gamePanel.remove(playLocalBtn);

            gamePanel.revalidate();
            gamePanel.repaint();

            stateManager.getGameStates().pop();
            stateManager.setActiveState(new PlayOnlineMenuState(graphics, gamePanel, stateManager), StateManager.PLAYONLINEMENUSTATE);
        });

        backBtn.addActionListener(e ->
        {
            // Spiele Sound
            if(GameData.isSoundOn.equals("On")) Sound.backButtonSound.play();

            gamePanel.remove(playOnlineBtn);
            gamePanel.remove(playLocalBtn);
            gamePanel.remove(backBtn);

            gamePanel.revalidate();
            gamePanel.repaint();

            stateManager.getGameStates().pop();
            stateManager.setActiveState(new MenuState(graphics, gamePanel, stateManager), StateManager.MENUSTATE);
        });


        // BUTTONS POSITIONIERUNG
        // Kein Layout, um Buttons selbst zu positionieren
        gamePanel.setLayout(null);

        // Lokal-Spielen Button
        playLocalBtn.setBounds(
                (References.SCREEN_WIDTH - playLocalButton.getIconWidth() * 3) / 4,
                References.SCREEN_HEIGHT / 2 - playLocalButton.getIconHeight() / 2,
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
                ((References.SCREEN_WIDTH - playOnlineButton.getIconWidth() * 3) / 2) + playOnlineButton.getIconWidth(),
                References.SCREEN_HEIGHT / 2 - playOnlineButton.getIconHeight() / 2,
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
