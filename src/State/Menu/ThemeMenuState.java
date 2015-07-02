package State.Menu;

import Assets.GameObjects.Player;
import GameSaves.GameData.GameData;
import Main.*;
import State.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;

/**
 * Created by moham_000 and Amin on 02.07.2015.
 *
 * @author Mo and Amin
 */
public class ThemeMenuState extends State
{


    protected Graphics graphics;
    protected GamePanel gamePanel;
    protected StateManager stateManager;


    // Resources
    private BufferedImage menuBackground;
    private BufferedImage menuIlandBackground;
    private BufferedImage menuTitleImage;

    private ImageIcon backButton, backButtonPressed;

    private JButton ironManBtn;
    private JButton backBtn;

    /**
     *
     * Konstruktor det ThemeMenuState-Klasse
     *
     * @param graphics       Graphics Objekt
     * @param gamePanel     Spielinhaltsflaeche
     * @param stateManager  Zustandsmanager
     */
    public ThemeMenuState(Graphics graphics, GamePanel gamePanel, StateManager stateManager) {
        this.graphics = graphics;
        this.gamePanel = gamePanel;
        this.stateManager = stateManager;

        this.menuBackground = ResourceLoader.menuBackground;
        this.menuIlandBackground = ResourceLoader.menuIlandBackground;
        this.menuTitleImage = ResourceLoader.menuTitleImage;

        this.backButton = ResourceLoader.backButton;
        this.backButtonPressed = ResourceLoader.backButtonPressed;



        // Initialisieren der Buttons
        this.init();

    }

    @Override
    public void init() {

        // Zeichne Himmel
        graphics.drawImage(menuBackground, 0, 0, References.SCREEN_WIDTH, References.SCREEN_HEIGHT, null);

        // Zeichne Insel
        graphics.drawImage(
                menuIlandBackground,
                (References.SCREEN_WIDTH / 2) - (menuIlandBackground.getWidth(null) / 2),
                (References.SCREEN_WIDTH / 2) - (menuIlandBackground.getHeight(null) / 2),
                menuIlandBackground.getWidth(null), menuIlandBackground.getHeight(null),
                null
        );

        // Zeichne Title
        graphics.drawImage(
                menuTitleImage,
                (References.SCREEN_WIDTH / 2) - (menuTitleImage.getWidth(null) / 2),
                (References.SCREEN_HEIGHT / 4),
                menuTitleImage.getWidth(null), menuTitleImage.getHeight(null),
                null
        );

        //Initialisieren der Buttons
        ironManBtn = new JButton("Iron Man");
        backBtn = new JButton(backButton);

        //BUTTONLISTENERS
        ironManBtn.addActionListener(e ->
        {
            if(GameData.isSoundOn.equals("On")) {
                Sound.ironManButtonSound.play();
            }

            Player.playerAssetsResPath = "/img/ironManPlayerSet.png";
            Player.isIronManSelected = true;
        });

        backBtn.addActionListener(e ->
        {
            // Spiele Sound
            if(GameData.isSoundOn.equals("On")) Sound.backButtonSound.play();


            gamePanel.remove(ironManBtn);
            gamePanel.remove(backBtn);

            gamePanel.revalidate();
            gamePanel.repaint();

            stateManager.getGameStates().pop();
            stateManager.setActiveState(new MenuState(graphics, gamePanel, stateManager), StateManager.MENUSTATE);
        });


        gamePanel.setLayout(null);

        ironManBtn.setBounds(References.SCREEN_WIDTH/4-50, References.SCREEN_HEIGHT/2-20, 200, 40);
        ironManBtn.setBackground(Color.green);
        ironManBtn.setForeground(Color.white);
        ironManBtn.setFont(CustomFont.createCustomFont("Munro.ttf", 18f));
        ironManBtn.setVisible(true);
        gamePanel.add(ironManBtn);

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
