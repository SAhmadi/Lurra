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
 * Spielauswahlmenu
 *
 * @author Sirat
 * */
public class StartMenuState extends State
{

    // Resources
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
        super(gamePanel, graphics, stateManager);

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
                (References.SCREEN_WIDTH/2) - (ResourceLoader.menuTitleImage.getWidth(null)/2),
                (References.SCREEN_HEIGHT/4),
                ResourceLoader.menuTitleImage.getWidth(null),
                ResourceLoader.menuTitleImage.getHeight(null),
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
