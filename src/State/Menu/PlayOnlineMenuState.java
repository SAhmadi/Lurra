package State.Menu;

import GameSaves.GameData.GameData;
import Main.GamePanel;
import Main.References;
import Main.ResourceLoader;
import Main.Sound;
import State.Multiplayer.CreateOnlineGameState;
import State.Multiplayer.JoinOnlineGameState;
import State.State;
import State.StateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

/**
 * Online-Spielen Menu
 *
 * @author Sirat
 * */
public class PlayOnlineMenuState extends State
{

    // Inhaltsflaeche, Graphics Objekt und Zustandsmanager
    protected GamePanel gamePanel;
    protected Graphics graphics;
    protected StateManager stateManager;

    // Resources
    private BufferedImage menuBackground;
    private BufferedImage menuIlandBackground;
    private BufferedImage menuTitleImage;

    private ImageIcon createOnlineGame, createOnlineGamePressed;
    private ImageIcon joinOnlineGame, joinOnlineGamePressed;
    private ImageIcon watchGame, watchGamePressed;
    private ImageIcon backButton, backButtonPressed;

    // Buttons
    private JButton createOnlineGameBtn;
    private JButton joinOnlineGameBtn;
    private JButton watchGameBtn;
    private JButton backBtn;

    /**
     * PlayOnlineMenuState      Konstruktor der PlayOnlineMenuState-Klasse
     *
     * @param graphics          Graphics Objekt
     * @param gamePanel         Spielinhaltsflaeche
     * @param stateManager      Zustandsmanager
     * */
    public PlayOnlineMenuState(Graphics graphics, GamePanel gamePanel, StateManager stateManager)
    {
        this.graphics = graphics;
        this.gamePanel = gamePanel;
        this.stateManager = stateManager;

        // Resource Initialisieren
        this.menuBackground = ResourceLoader.menuBackground;
        this.menuIlandBackground = ResourceLoader.menuIlandBackground;
        this.menuTitleImage = ResourceLoader.menuTitleImage;

        this.createOnlineGame = ResourceLoader.createOnlineGame;
        this.createOnlineGamePressed = ResourceLoader.createOnlineGamePressed;

        this.joinOnlineGame = ResourceLoader.joinOnlineGame;
        this.joinOnlineGamePressed = ResourceLoader.joinOnlineGamePressed;

        this.watchGame = ResourceLoader.watchGame;
        this.watchGamePressed = ResourceLoader.watchGamePressed;

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
        createOnlineGameBtn = new JButton(createOnlineGame);
        joinOnlineGameBtn = new JButton(joinOnlineGame);
        watchGameBtn = new JButton(watchGame);
        backBtn = new JButton(backButton);

        // BUTTONLISTENERS
        createOnlineGameBtn.addActionListener(e ->
        {
            // Spiele Sound
            if (GameData.isSoundOn.equals("On")) Sound.createButtonSound.play();

            // TODO ueberpruefen ob Server ueberhaupt an ist

            gamePanel.remove(watchGameBtn);
            gamePanel.remove(joinOnlineGameBtn);
            gamePanel.remove(backBtn);
            gamePanel.remove(createOnlineGameBtn);

            gamePanel.revalidate();
            gamePanel.repaint();

            stateManager.getGameStates().pop();
            stateManager.setActiveState(new CreateOnlineGameState(graphics, gamePanel, stateManager), StateManager.CREATE_ONLINE_GAMESTATE);
        });

        joinOnlineGameBtn.addActionListener(e ->
        {
            // Spiele Sound
            if(GameData.isSoundOn.equals("On")) Sound.joinButtonSound.play();

            gamePanel.remove(watchGameBtn);
            gamePanel.remove(backBtn);
            gamePanel.remove(createOnlineGameBtn);
            gamePanel.remove(joinOnlineGameBtn);

            gamePanel.revalidate();
            gamePanel.repaint();

            stateManager.getGameStates().pop();
            stateManager.setActiveState(new JoinOnlineGameState(graphics, gamePanel, stateManager), StateManager.JOIN_ONLINE_GAMESTATE);
        });

        watchGameBtn.addActionListener(e ->
        {
            // Spiele Sound
            if(GameData.isSoundOn.equals("On")) Sound.spectateButtonSound.play();

            gamePanel.remove(backBtn);
            gamePanel.remove(createOnlineGameBtn);
            gamePanel.remove(joinOnlineGameBtn);
            gamePanel.remove(watchGameBtn);

            gamePanel.revalidate();
            gamePanel.repaint();

            stateManager.getGameStates().pop();
            stateManager.setActiveState(new JoinOnlineGameState(graphics, gamePanel, stateManager), StateManager.JOIN_ONLINE_GAMESTATE);
        });

        backBtn.addActionListener(e ->
        {
            // Spiele Sound
            if (GameData.isSoundOn.equals("On")) Sound.backButtonSound.play();


            gamePanel.remove(createOnlineGameBtn);
            gamePanel.remove(joinOnlineGameBtn);
            gamePanel.remove(watchGameBtn);
            gamePanel.remove(backBtn);

            gamePanel.revalidate();
            gamePanel.repaint();

            gamePanel.getRootPane().setBorder(null);

            stateManager.getGameStates().pop();
            stateManager.setActiveState(new StartMenuState(graphics, gamePanel, stateManager), StateManager.STARTMENUSTATE);
        });

        // Kein Layout, um Buttons selbst zu positionieren
        gamePanel.setLayout(null);

        // Erstellen Button
        createOnlineGameBtn.setBounds(
                (References.SCREEN_WIDTH - createOnlineGame.getIconWidth() * 4) / 5,
                References.SCREEN_HEIGHT / 2 - createOnlineGame.getIconHeight() / 2,
                createOnlineGame.getIconWidth(),
                createOnlineGame.getIconHeight()
        );
        createOnlineGameBtn.setBorderPainted(false);
        createOnlineGameBtn.setFocusPainted(false);
        createOnlineGameBtn.setContentAreaFilled(false);
        createOnlineGameBtn.setOpaque(false);
        createOnlineGameBtn.setPressedIcon(createOnlineGamePressed);
        createOnlineGameBtn.setVisible(true);
        gamePanel.add(createOnlineGameBtn);

        // Beitreten Button
        joinOnlineGameBtn.setBounds(
                2*((References.SCREEN_WIDTH - joinOnlineGame.getIconWidth() * 4) / 5) + joinOnlineGame.getIconWidth(),
                References.SCREEN_HEIGHT / 2 - joinOnlineGame.getIconHeight() / 2,
                joinOnlineGame.getIconWidth(),
                joinOnlineGame.getIconHeight()
        );
        joinOnlineGameBtn.setBorderPainted(false);
        joinOnlineGameBtn.setFocusPainted(false);
        joinOnlineGameBtn.setContentAreaFilled(false);
        joinOnlineGameBtn.setOpaque(false);
        joinOnlineGameBtn.setPressedIcon(joinOnlineGamePressed);
        joinOnlineGameBtn.setVisible(true);
        gamePanel.add(joinOnlineGameBtn);

        // Zuschauen Button
        watchGameBtn.setBounds(
                2 * ((References.SCREEN_WIDTH - watchGame.getIconWidth() * 4) / 5 + watchGame.getIconWidth()) + ((References.SCREEN_WIDTH - watchGame.getIconWidth() * 4) / 5),
                References.SCREEN_HEIGHT / 2 - watchGame.getIconHeight() / 2,
                watchGame.getIconWidth(),
                watchGame.getIconHeight()
        );
        watchGameBtn.setBorderPainted(false);
        watchGameBtn.setFocusPainted(false);
        watchGameBtn.setContentAreaFilled(false);
        watchGameBtn.setOpaque(false);
        watchGameBtn.setPressedIcon(watchGamePressed);
        watchGameBtn.setVisible(true);
        gamePanel.add(this.watchGameBtn);

        // Zurueck Button
        backBtn.setBounds(
                3 * ((References.SCREEN_WIDTH - backButton.getIconWidth() * 4) / 5 + backButton.getIconWidth()) + ((References.SCREEN_WIDTH - backButton.getIconWidth() * 4) / 5),
                References.SCREEN_HEIGHT / 2 - backButton.getIconHeight() / 2,
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
