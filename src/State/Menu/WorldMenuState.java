package State.Menu;

import GameSaves.GameData.GameData;
import Main.GamePanel;
import Main.ResourceLoader;
import Main.ScreenDimensions;
import Main.Sound;
import State.Level.Level1State;
import State.State;
import State.StateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.nio.file.Path;

/**
 * Created by moham_000 on 04.06.2015.
 */

//MenuState Andere Welten
public class WorldMenuState extends State {

    protected GamePanel gamePanel;
    protected Graphics graphics;
    protected StateManager stateManager;

    // Resources
    private BufferedImage menuBackground;
    private BufferedImage menuIlandBackground;
    private BufferedImage menuTitleImage;

    private ImageIcon backButton, backButtonPressed;
    //private ImageIcon desertButton, desertButtonPressed;
    //private ImageIcon jungleButton, jungleButtonPressed;
    //private ImageIcon alaskaButton, alaskaButtonPressed;

    private JButton backBtn;
    public JButton desertBtn;
    public JButton jungleBtn;
    public JButton alaskaBtn;

    public static String backgroundPath = "/img/sky_day.jpg";

    public  WorldMenuState (Graphics graphics, GamePanel gamePanel, StateManager stateManager) {
        this.graphics = graphics;
        this.gamePanel = gamePanel;
        this.stateManager = stateManager;

        this.menuBackground = ResourceLoader.menuBackground;
        this.menuIlandBackground = ResourceLoader.menuIlandBackground;
        this.menuTitleImage = ResourceLoader.menuTitleImage;

        this.backButton = ResourceLoader.backButton;
        this.backButtonPressed = ResourceLoader.backButtonPressed;

        //this.desertButton = ResourceLoader.desertButton;
        //this.desertButtonPressed = ResourceLoader.desertButtonPressed;

        //this.jungleButton = ResourceLoader.jungleButton;
        //this.jungleButtonPressed = ResourceLoader.jungleButtonPressed;

        //this.alaskaButton = ResourceLoader.alaskaButton;
        //this.alaskaButtonPressed = ResourceLoader.alaskaButtonPressed;

        init();

    }

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
                ScreenDimensions.WIDTH/2 - menuTitleImage.getWidth(null)/2,
                ScreenDimensions.HEIGHT/4,
                menuTitleImage.getWidth(), menuTitleImage.getHeight(),
                null
        );

        backBtn = new JButton(backButton);
        desertBtn = new JButton("Wueste");
        jungleBtn = new JButton("Dschungel");
        alaskaBtn = new JButton("Alaska");

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Spiele Sound
                if (GameData.isSoundOn.equals("On"))
                    Sound.diamondSound.play();


                gamePanel.remove(backBtn);
                gamePanel.remove(desertBtn);
                gamePanel.remove(jungleBtn);
                gamePanel.remove(alaskaBtn);


                gamePanel.revalidate();
                gamePanel.repaint();

                // Pushe StartMenu -> Starte NewGameState
                stateManager.getGameStates().pop();
                stateManager.setActiveState(new NewGameState(graphics, gamePanel, stateManager), stateManager.NEWGAMESTATE);
            }
        });



        desertBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Spiele Sound
                if(GameData.isSoundOn.equals("On"))
                    Sound.diamondSound.play();

                //Aendern des Hintergrundblides: Wueste
                backgroundPath = "/img/desert.jpg";


            }
        });

        jungleBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Spiele Sound
                if(GameData.isSoundOn.equals("On"))
                    Sound.diamondSound.play();

                //Aendern des Hintergrundblides: Dschungel
                backgroundPath = "/img/jungle.jpg";

            }
        });

        alaskaBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Spiele Sound
                if(GameData.isSoundOn.equals("On"))
                    Sound.diamondSound.play();

                //Aendern des Hintergrundblides: Schneelandschaft
                backgroundPath = "/img/snowland.jpg";

            }
        });

        backBtn.setBounds(
                ScreenDimensions.WIDTH / 2 - backButton.getIconWidth() / 2,
                ScreenDimensions.HEIGHT / 2 + backButton.getIconHeight() / 2 + backButton.getIconHeight(),
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



        desertBtn.setBounds(
                (ScreenDimensions.WIDTH  /2-350) ,
                ScreenDimensions.HEIGHT/2 , 200, 40
        );
        desertBtn.setBorderPainted(true);
        desertBtn.setFocusPainted(true);
        desertBtn.setBackground(Color.GREEN);
        desertBtn.setForeground(Color.WHITE);
        //desertBtn.setContentAreaFilled(true);
        desertBtn.setOpaque(true);
        //desertBtn.setPressedIcon(desertButtonPressed);
        desertBtn.setFont(new Font("Monospaced",Font.CENTER_BASELINE, 25));
        desertBtn.setVisible(true);
        gamePanel.add(desertBtn);


        jungleBtn.setBounds(
                (ScreenDimensions.WIDTH  /2 - 100) ,
                ScreenDimensions.HEIGHT/2 , 200, 40);
        jungleBtn.setBorderPainted(true);
        jungleBtn.setFocusPainted(true);
        jungleBtn.setBackground(Color.GREEN);
        jungleBtn.setForeground(Color.WHITE);
        //jungleBtn.setContentAreaFilled(true);
        jungleBtn.setOpaque(true);
        //jungleBtn.setPressedIcon(jungleButtonPressed);
        jungleBtn.setFont(new Font("Monospaced",Font.CENTER_BASELINE, 25));
        jungleBtn.setVisible(true);
        gamePanel.add(jungleBtn);

        alaskaBtn.setBounds(
                (ScreenDimensions.WIDTH  /2+ 150) ,
                ScreenDimensions.HEIGHT/2 , 200, 40);
        alaskaBtn.setBorderPainted(true);
        alaskaBtn.setFocusPainted(true);
        alaskaBtn.setBackground(Color.GREEN);
        alaskaBtn.setForeground(Color.WHITE);
        //alaskaBtn.setContentAreaFilled(true);
        alaskaBtn.setOpaque(true);
        //alaskaBtn.setPressedIcon(alaskaButtonPressed);
        alaskaBtn.setFont(new Font("Monospaced",Font.CENTER_BASELINE, 25));
        alaskaBtn.setVisible(true);
        gamePanel.add(alaskaBtn);

    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics g) {

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

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
