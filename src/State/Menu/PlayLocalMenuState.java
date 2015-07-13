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
 * Lokal-Spielen Menu
 *
 * @author Sirat
 * */
public class PlayLocalMenuState extends State
{

    // Resources
    private ImageIcon continueGameButton, continueGameButtonPressed;
    private ImageIcon newGameButton, newGameButtonPressed;
    private ImageIcon backButton, backButtonPressed;

    // Buttons
    private JButton continueGameBtn;
    private JButton newGameBtn;
    private JButton backBtn;

    /**
     * PlayLocalMenuState       Konstruktor der PlayLocalMenuState-Klasse
     *
     * @param graphics      Graphics Objekt
     * @param gamePanel     Spielinhaltsflaeche
     * @param stateManager  Zustandsmanager
     * */
    public PlayLocalMenuState(Graphics graphics, GamePanel gamePanel, StateManager stateManager)
    {
        super(gamePanel, graphics, stateManager);

        this.continueGameButton = ResourceLoader.continueGameButton;
        this.continueGameButtonPressed = ResourceLoader.continueGameButtonPressed;

        this.newGameButton = ResourceLoader.newGameButton;
        this.newGameButtonPressed = ResourceLoader.newGameButtonPressed;

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
        continueGameBtn = new JButton(continueGameButton);
        newGameBtn = new JButton(newGameButton);
        backBtn = new JButton(backButton);

        // BUTTONSLISTENERS
        continueGameBtn.addActionListener(e ->
        {
            // Spiele Sound
            if(GameData.isSoundOn.equals("On")) Sound.continueButtonSound.play();

            gamePanel.remove(newGameBtn);
            gamePanel.remove(backBtn);
            gamePanel.remove(continueGameBtn);

            gamePanel.revalidate();
            gamePanel.repaint();

            stateManager.getGameStates().pop();
            stateManager.setActiveState(new GameContinueState(graphics, gamePanel, stateManager), StateManager.CONTINUEGAMESTATE);
        });

        newGameBtn.addActionListener(e ->
        {
            // Spiele Sound
            if(GameData.isSoundOn.equals("On")) Sound.newGameButtonSound.play();

            gamePanel.remove(continueGameBtn);
            gamePanel.remove(backBtn);
            gamePanel.remove(newGameBtn);

            gamePanel.revalidate();
            gamePanel.repaint();

            stateManager.getGameStates().pop();
            stateManager.setActiveState(new NewGameState(graphics, gamePanel, stateManager), StateManager.NEWGAMESTATE);
        });

        backBtn.addActionListener(e ->
        {
            // Spiele Sound
            if(GameData.isSoundOn.equals("On")) Sound.backButtonSound.play();

            gamePanel.remove(continueGameBtn);
            gamePanel.remove(newGameBtn);
            gamePanel.remove(backBtn);

            gamePanel.revalidate();
            gamePanel.repaint();

            stateManager.getGameStates().pop();
            stateManager.setActiveState(new StartMenuState(graphics, gamePanel, stateManager), StateManager.STARTMENUSTATE);
        });

        // Kein Layout, um Buttons selbst zu positionieren
        gamePanel.setLayout(null);

        // Spielfortsetzen Button
        continueGameBtn.setBounds(
                (References.SCREEN_WIDTH - continueGameButton.getIconWidth() * 3) / 4,
                References.SCREEN_HEIGHT / 2 - continueGameButton.getIconHeight() / 2,
                continueGameButton.getIconWidth(),
                continueGameButton.getIconHeight()
        );
        continueGameBtn.setBorderPainted(false);
        continueGameBtn.setFocusPainted(false);
        continueGameBtn.setContentAreaFilled(false);
        continueGameBtn.setOpaque(false);
        continueGameBtn.setPressedIcon(continueGameButtonPressed);
        continueGameBtn.setVisible(true);
        gamePanel.add(continueGameBtn);

        // Neues Spiel Button
        newGameBtn.setBounds(
                ((References.SCREEN_WIDTH - newGameButton.getIconWidth() * 3) / 2) + newGameButton.getIconWidth(),
                References.SCREEN_HEIGHT / 2 - newGameButton.getIconHeight() / 2,
                newGameButton.getIconWidth(),
                newGameButton.getIconHeight()
        );
        newGameBtn.setBorderPainted(false);
        newGameBtn.setFocusPainted(false);
        newGameBtn.setContentAreaFilled(false);
        newGameBtn.setOpaque(false);
        newGameBtn.setPressedIcon(newGameButtonPressed);
        newGameBtn.setVisible(true);
        gamePanel.add(newGameBtn);

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
