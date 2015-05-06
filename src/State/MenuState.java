package State;

import Main.CustomFont;
import Main.GamePanel;
import Main.ScreenDimensions;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

/*
*
* MenuState - Spielmenu
*
* */
public class MenuState extends State {

    // Bildschirm Dimensionen
    private ScreenDimensions screenDimensions = new ScreenDimensions();

    // Menu Schriftart
    private Font menuFont;

    // Farben
    private final static Color DARK_GREY = new Color(23, 23, 23);

    // Game-Panel
    protected GamePanel gamePanel;

    // Referenz auf Zustands-Manager
    private StateManager stateManager;

    /*
    * Menu Buttons und ihre Beschriftung
    * */
    private JButton startButton;
    private String startButtonLabel = "Spiel starten";

    private JButton settingsButton;
    private String settingsButtonLabel = "Einstellungen";

    private JButton closeButton;
    private String closeButtonLabel = "Beenden";

    private JButton playLocalButton;
    private String playLocalButtonLabel = "Lokal";

    private JButton playOnlineButton;
    private String playOnlineButtonLabel = "Online";

    private JSlider soundSlider;
    private JLabel soundLabel;
    private String soundSliderLabel = "Sound";

    private JButton controlsButton;
    private String controlsButtonLabel = "Steuerung";

    private JButton backButton;
    private String backButtonLabel = "Zurück";

    public MenuState(Graphics graphics, GamePanel gamePanel, StateManager stateManager) {
        this.gamePanel = gamePanel;
        this.stateManager = stateManager;

        // Erstelle neues Font
        menuFont = CustomFont.createCustomFont("fixedsys.ttf");

        // Initialisieren der Buttons
        startButton = new JButton(startButtonLabel);
        settingsButton = new JButton(settingsButtonLabel);
        closeButton = new JButton(closeButtonLabel);
        playLocalButton = new JButton(playLocalButtonLabel);
        playOnlineButton = new JButton(playOnlineButtonLabel);
        controlsButton = new JButton(controlsButtonLabel);

        backButton = new JButton(backButtonLabel);

        /*
        * Button Listeners
        * */
         startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startButton.setVisible(false);
                settingsButton.setVisible(false);
                closeButton.setVisible(false);

                playLocalButton.setVisible(true);
                playOnlineButton.setVisible(true);
                backButton.setVisible(true);

                gamePanel.revalidate();
                gamePanel.repaint();
            }
        });

        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startButton.setVisible(false);
                settingsButton.setVisible(false);
                closeButton.setVisible(false);

                controlsButton.setVisible(true);
                //soundSlider.setVisible(true);
                backButton.setVisible(true);

                gamePanel.revalidate();
                gamePanel.repaint();
            }
        });

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playLocalButton.setVisible(false);
                playOnlineButton.setVisible(false);
                //soundSlider.setVisible(false);
                controlsButton.setVisible(false);
                backButton.setVisible(false);

                startButton.setVisible(true);
                settingsButton.setVisible(true);
                closeButton.setVisible(true);

                gamePanel.revalidate();
                gamePanel.repaint();
            }
        });

        /*
        * Hinzufuegen und Positionieren der Buttons
        * */
        gamePanel.setLayout(null);

        // Start Button
        startButton.setBounds( (screenDimensions.getWidth()-250*3)/4, screenDimensions.getHeight()/2-30, 250, 60 );
        startButton.setBorderPainted(false);
        startButton.setFocusPainted(false);
        startButton.setBackground(MenuState.DARK_GREY);
        startButton.setForeground(Color.WHITE);
        startButton.setFont(menuFont);
        startButton.setVisible(true);
        gamePanel.add(startButton);

        // Einstelllungs Button
        settingsButton.setBounds( ((screenDimensions.getWidth() - 250 * 3) / 2) + 250, screenDimensions.getHeight() / 2 - 30, 250, 60 );
        settingsButton.setBorderPainted(false);
        settingsButton.setFocusPainted(false);
        settingsButton.setBackground(MenuState.DARK_GREY);
        settingsButton.setForeground(Color.WHITE);
        settingsButton.setFont(menuFont);
        settingsButton.setVisible(true);
        gamePanel.add(settingsButton);

        // Beenden Button
        closeButton.setBounds(2 * ((screenDimensions.getWidth() - 250 * 3) / 4 + 250) + ((screenDimensions.getWidth() - 250 * 3) / 4), screenDimensions.getHeight() / 2 - 30, 250, 60);
        closeButton.setBorderPainted(false);
        closeButton.setFocusPainted(false);
        closeButton.setBackground(MenuState.DARK_GREY);
        closeButton.setForeground(Color.WHITE);
        closeButton.setFont(menuFont);
        closeButton.setVisible(true);
        gamePanel.add(closeButton);

        // Lokal Button
        playLocalButton.setBounds((screenDimensions.getWidth() - 250 * 3) / 4, screenDimensions.getHeight() / 2 - 30, 250, 60);
        playLocalButton.setBorderPainted(false);
        playLocalButton.setFocusPainted(false);
        playLocalButton.setBackground(MenuState.DARK_GREY);
        playLocalButton.setForeground(Color.WHITE);
        playLocalButton.setFont(menuFont);
        playLocalButton.setVisible(false);
        gamePanel.add(playLocalButton);

        // Online Button
        playOnlineButton.setBounds(((screenDimensions.getWidth() - 250 * 3) / 2) + 250, screenDimensions.getHeight() / 2 - 30, 250, 60);
        playOnlineButton.setBorderPainted(false);
        playOnlineButton.setFocusPainted(false);
        playOnlineButton.setBackground(MenuState.DARK_GREY);
        playOnlineButton.setForeground(Color.WHITE);
        playOnlineButton.setFont(menuFont);
        playOnlineButton.setVisible(false);
        gamePanel.add(playOnlineButton);

        // Steuerung Button
        controlsButton.setBounds(((screenDimensions.getWidth() - 250 * 3) / 2) + 250, screenDimensions.getHeight() / 2 - 30, 250, 60);
        controlsButton.setBorderPainted(false);
        controlsButton.setFocusPainted(false);
        controlsButton.setBackground(MenuState.DARK_GREY);
        controlsButton.setForeground(Color.WHITE);
        controlsButton.setFont(menuFont);
        controlsButton.setVisible(false);
        gamePanel.add(controlsButton);

        // Zurueck Button
        backButton.setBounds( 2*((screenDimensions.getWidth()-250*3)/4+250) + ((screenDimensions.getWidth()-250*3)/4), screenDimensions.getHeight()/2-30, 250, 60 );
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);
        backButton.setBackground(MenuState.DARK_GREY);
        backButton.setForeground(Color.WHITE);
        backButton.setFont(menuFont);
        backButton.setVisible(false);
        gamePanel.add(backButton);
    }

    @Override
    public void update(Graphics graphics, JFrame gameFrame, JPanel gamePanel) {}

    @Override
    public void render(Graphics graphics, JFrame gameFrame, JPanel gamePanel) {}

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
