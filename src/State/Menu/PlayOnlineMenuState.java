package State.Menu;

import GameSaves.GameData.GameData;
import Main.*;
//import State.Multiplayer.CreateOnlineGameState;
import State.State;
import State.StateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

/*
* MenuState - Spielmenu
* */
public class PlayOnlineMenuState extends State {

    // Inhaltsflaeche, Graphics-Obj und Zustands-Manger
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

    /*
    * Menu Buttons
    * */
    private JButton createOnlineGameBtn;
    private JButton joinOnlineGameBtn;
    private JButton watchGameBtn;
    private JButton backBtn;

    /*
    * Konstruktor - Initialisieren
    * */
    public PlayOnlineMenuState(Graphics graphics, GamePanel gamePanel, StateManager stateManager) {
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
                (ScreenDimensions.WIDTH/2) - (menuTitleImage.getWidth(null)/2),
                (ScreenDimensions.HEIGHT/4),
                menuTitleImage.getWidth(null), menuTitleImage.getHeight(null),
                null
        );

        // Initialisieren der Buttons
        createOnlineGameBtn = new JButton(createOnlineGame);
        joinOnlineGameBtn = new JButton(joinOnlineGame);
        watchGameBtn = new JButton(watchGame);
        backBtn = new JButton(backButton);

        /*
        * Button Listeners
        * Aendert Sichtbarkeit der Buttons, die im Ober-/Unter-Menu sichbar sein sollen
        * */
        createOnlineGameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Spiele Sound
                if (GameData.isSoundOn.equals("On"))
                    Sound.diamondSound.play();

                gamePanel.remove(watchGameBtn);
                gamePanel.remove(joinOnlineGameBtn);
                gamePanel.remove(backBtn);
                gamePanel.remove(createOnlineGameBtn);

                gamePanel.revalidate();
                gamePanel.repaint();

                stateManager.getGameStates().pop();
                //stateManager.setActiveState(new CreateOnlineGameState(graphics, gamePanel, stateManager), stateManager.CREATE_ONLINE_GAMESTATE);

            }
        });

        joinOnlineGameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Spiele Sound
                if(GameData.isSoundOn.equals("On"))
                    Sound.diamondSound.play();

                gamePanel.remove(watchGameBtn);
                gamePanel.remove(backBtn);
                gamePanel.remove(createOnlineGameBtn);
                gamePanel.remove(joinOnlineGameBtn);

                gamePanel.revalidate();
                gamePanel.repaint();


            }
        });

        watchGameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Spiele Sound
                if(GameData.isSoundOn.equals("On"))
                    Sound.diamondSound.play();

                gamePanel.remove(backBtn);
                gamePanel.remove(createOnlineGameBtn);
                gamePanel.remove(joinOnlineGameBtn);
                gamePanel.remove(watchGameBtn);

                gamePanel.revalidate();
                gamePanel.repaint();

            }
        });

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Spiele Sound
                if (GameData.isSoundOn.equals("On"))
                    Sound.diamondSound.play();

                gamePanel.remove(createOnlineGameBtn);
                gamePanel.remove(joinOnlineGameBtn);
                gamePanel.remove(watchGameBtn);
                gamePanel.remove(backBtn);

                gamePanel.revalidate();
                gamePanel.repaint();

                // Pushe StartMenu -> Starte SettingsMenuState
                stateManager.getGameStates().pop();
                stateManager.setActiveState(new StartMenuState(graphics, gamePanel, stateManager), stateManager.STARTMENUSTATE);
            }
        });

        /*
        * Hinzufuegen und Positionieren der Buttons
        * */

        // Kein Layout, um Buttons selbst zu positionieren
        gamePanel.setLayout(null);

        // Erstellen-Button
        createOnlineGameBtn.setBounds(
                (ScreenDimensions.WIDTH - createOnlineGame.getIconWidth() * 4) / 5,
                ScreenDimensions.HEIGHT / 2 - createOnlineGame.getIconHeight() / 2,
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

        // Beitreten-Button
        joinOnlineGameBtn.setBounds(
                2*((ScreenDimensions.WIDTH - joinOnlineGame.getIconWidth() * 4) / 5) + joinOnlineGame.getIconWidth(),
                ScreenDimensions.HEIGHT / 2 - joinOnlineGame.getIconHeight() / 2,
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

        // Zuschauen-Button
        watchGameBtn.setBounds(
                2 * ((ScreenDimensions.WIDTH - watchGame.getIconWidth() * 4) / 5 + watchGame.getIconWidth()) + ((ScreenDimensions.WIDTH - watchGame.getIconWidth() * 4) / 5),
                ScreenDimensions.HEIGHT / 2 - watchGame.getIconHeight() / 2,
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
                3 * ((ScreenDimensions.WIDTH - backButton.getIconWidth() * 4) / 5 + backButton.getIconWidth()) + ((ScreenDimensions.WIDTH - backButton.getIconWidth() * 4) / 5),
                ScreenDimensions.HEIGHT / 2 - backButton.getIconHeight() / 2,
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
