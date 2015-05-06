package State;

import Main.GamePanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/*
*
* */
public class MenuState extends State {

    // Game-Panel
    protected GamePanel gamePanel;

    // Referenz auf Zustands-Manager
    private StateManager stateManager;

    // Bild Resource Pfad
    private String resourcePath = "res/img/";

    // Menu Hintergrundbild - Dateiname
    private String menuBackgroundFilename = "sky.jpg";

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
    private String soundSliderLabel = "Sound";

    private JButton controlsButton;
    private String controlsButtonLabel = "Steuerung";

    private JButton backButton;
    private String backButtonLabel = "Zurück";

    public MenuState(Graphics graphics, GamePanel gamePanel, StateManager stateManager) {
        this.gamePanel = gamePanel;
        this.stateManager = stateManager;

       /* try {
            Image backgroundImage = ImageIO.read(new File("res/img/sky.jpg"));
            graphics.drawImage(backgroundImage, 0, 0, null);
        } catch (IOException e) {
            e.printStackTrace();
        }*/




    }

    @Override
    public void update(Graphics graphics, JFrame gameFrame, JPanel gamePanel) {}

    @Override
    public void render(Graphics graphics, JFrame gameFrame, JPanel gamePanel) {

        startButton = new JButton(startButtonLabel);
        settingsButton = new JButton(settingsButtonLabel);
        closeButton = new JButton(closeButtonLabel);

        // Setze Menu Hintergrundbild

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        gamePanel.add(startButton);
        gamePanel.add(settingsButton);
        gamePanel.add(closeButton);
        startButton.setVisible(true);
        settingsButton.setVisible(true);
        closeButton.setVisible(true);


    }

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
