package State.Menu;

import GameSaves.GameData.GameData;
import Main.*;
import State.State;
import State.StateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

/*
* MenuState - Spielmenu
* */
public class PlayLocalMenuState extends State {

    // Inhaltsflaeche, Graphics-Obj und Zustands-Manger
    protected GamePanel gamePanel;
    protected Graphics graphics;
    protected StateManager stateManager;

    // Resources
    private BufferedImage menuBackground;
    private BufferedImage menuIlandBackground;
    private BufferedImage menuTitleImage;

    private ImageIcon continueGameButton, continueGameButtonPressed;
    private ImageIcon newGameButton, newGameButtonPressed;
    private ImageIcon backButton, backButtonPressed;

    /*
    * Menu Buttons
    * */
    private JButton continueGameBtn;
    private JButton newGameBtn;
    private JButton backBtn;

    /*
    * Konstruktor - Initialisieren
    * */
    public PlayLocalMenuState(Graphics graphics, GamePanel gamePanel, StateManager stateManager) {
        this.graphics = graphics;
        this.gamePanel = gamePanel;
        this.stateManager = stateManager;

        // Resource Initialisieren
        this.menuBackground = ResourceLoader.menuBackground;
        this.menuIlandBackground = ResourceLoader.menuIlandBackground;
        this.menuTitleImage = ResourceLoader.menuTitleImage;

        this.continueGameButton = ResourceLoader.continueGameButton;
        this.continueGameButtonPressed = ResourceLoader.continueGameButtonPressed;

        this.newGameButton = ResourceLoader.newGameButton;
        this.newGameButtonPressed = ResourceLoader.newGameButtonPressed;

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
        continueGameBtn = new JButton(continueGameButton);
        newGameBtn = new JButton(newGameButton);
        backBtn = new JButton(backButton);

        /*
        * Button Listeners
        * Aendert Sichtbarkeit der Buttons, die im Ober-/Unter-Menu sichbar sein sollen
        * */
        continueGameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Spiele Sound
                if(GameData.isSoundOn.equals("On"))
                    Sound.diamondSound.play();

                gamePanel.remove(newGameBtn);
                gamePanel.remove(backBtn);
                gamePanel.remove(continueGameBtn);

                gamePanel.revalidate();
                gamePanel.repaint();

                // Pushe StartMenu -> Starte StartMenu
                stateManager.getGameStates().pop();
                stateManager.setActiveState(new GameContinueState(graphics, gamePanel, stateManager), -7);
            }
        });

        newGameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Spiele Sound
                if(GameData.isSoundOn.equals("On"))
                    Sound.diamondSound.play();

                gamePanel.remove(continueGameBtn);
                gamePanel.remove(backBtn);
                gamePanel.remove(newGameBtn);

                gamePanel.revalidate();
                gamePanel.repaint();

                // Pushe StartMenu -> Starte SettingsMenuState
                stateManager.getGameStates().pop();
                stateManager.setActiveState(new NewGameState(graphics, gamePanel, stateManager), -6);
            }
        });

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Spiele Sound
                if(GameData.isSoundOn.equals("On"))
                    Sound.diamondSound.play();

                gamePanel.remove(continueGameBtn);
                gamePanel.remove(newGameBtn);
                gamePanel.remove(backBtn);

                gamePanel.revalidate();
                gamePanel.repaint();

                // Pushe StartMenu -> Starte SettingsMenuState
                stateManager.getGameStates().pop();
                stateManager.setActiveState(new StartMenuState(graphics, gamePanel, stateManager), -1);
            }
        });

        /*
        * Hinzufuegen und Positionieren der Buttons
        * */

        // Kein Layout, um Buttons selbst zu positionieren
        gamePanel.setLayout(null);

        // Lokal-Spielen Button
        continueGameBtn.setBounds(
                (ScreenDimensions.WIDTH - continueGameButton.getIconWidth() * 3) / 4,
                ScreenDimensions.HEIGHT / 2 - continueGameButton.getIconHeight() / 2,
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

        // Online-Spielen Button
        newGameBtn.setBounds(
                ((ScreenDimensions.WIDTH - newGameButton.getIconWidth() * 3) / 2) + newGameButton.getIconWidth(),
                ScreenDimensions.HEIGHT / 2 - newGameButton.getIconHeight() / 2,
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
