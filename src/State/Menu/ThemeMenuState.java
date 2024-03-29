package State.Menu;

import Assets.GameObjects.Player;
import GameSaves.GameData.GameData;
import Main.GamePanel;
import Main.References;
import Main.ResourceLoader;
import Main.Sound;
import State.State;
import State.StateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Spielfigur Theme auswaehlen
 *
 * @author Mo, Amin
 */
public class ThemeMenuState extends State
{

    // Resources
    private ImageIcon backButton, backButtonPressed;
    private ImageIcon thorButton, thorButtonPressed;
    private ImageIcon hulkButton, hulkButtonPressed;
    private ImageIcon ironManButton, ironManButtonPressed;
    private ImageIcon captainAmericaButton, captainAmericaButtonPressed;
    private ImageIcon blackWidowButton, blackWidowButtonPressed;
    private ImageIcon specialButton, specialButtonPressed;
    private ImageIcon normalButton, normalButtonPressed;

    private JButton ironManBtn;
    private JButton hulkBtn;
    private JButton captainAmericaBtn;
    private JButton thorBtn;
    private JButton blackWidowBtn;
    private JButton specialBtn;
    private JButton normalBtn;
    private JButton backBtn;


    /**
     * ThemeMenuState       Konstruktor der ThemeMenuState Klasse
     *
     * @param graphics      Graphics Objekt
     * @param gamePanel     Spielinhaltsflaeche
     * @param stateManager  Zustandsmanager
     */
    public ThemeMenuState(Graphics graphics, GamePanel gamePanel, StateManager stateManager)
    {
        super(gamePanel, graphics, stateManager);

        this.backButton = ResourceLoader.backButton;
        this.backButtonPressed = ResourceLoader.backButtonPressed;
        this.thorButton = ResourceLoader.thorButton;
        this.thorButtonPressed = ResourceLoader.thorButtonPressed;
        this.hulkButton = ResourceLoader.hulkButton;
        this.hulkButtonPressed = ResourceLoader.hulkButtonPressed;
        this.ironManButton = ResourceLoader.ironManButton;
        this.ironManButtonPressed = ResourceLoader.ironManButtonPressed;
        this.captainAmericaButton = ResourceLoader.captainAmericaButton;
        this.captainAmericaButtonPressed = ResourceLoader.captainAmericaButtonPressed;
        this.blackWidowButton = ResourceLoader.blackWidowButton;
        this.blackWidowButtonPressed = ResourceLoader.blackWidowButtonPressed;
        this.specialButton = ResourceLoader.specialButton;
        this.specialButtonPressed = ResourceLoader.specialButtonPressed;
        this.normalButton = ResourceLoader.normalButton;
        this.normalButtonPressed = ResourceLoader.normalButtonPressed;



        // Initialisieren der Buttons
        this.init();
    }

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
                (References.SCREEN_WIDTH / 2) - (ResourceLoader.menuTitleImage.getWidth(null) / 2),
                (References.SCREEN_HEIGHT / 4),
                ResourceLoader.menuTitleImage.getWidth(null),
                ResourceLoader.menuTitleImage.getHeight(null),
                null
        );

        //Initialisieren der Buttons
        ironManBtn = new JButton(ironManButton);
        hulkBtn = new JButton(hulkButton);
        captainAmericaBtn = new JButton(captainAmericaButton);
        thorBtn = new JButton(thorButton);
        blackWidowBtn = new JButton(blackWidowButton);
        specialBtn = new JButton(specialButton);
        normalBtn = new JButton(normalButton);
        backBtn = new JButton(backButton);

        //BUTTONLISTENERS
        ironManBtn.addActionListener(e ->
        {
            //Spiele Sound
            if(GameData.isSoundOn.equals("On")) Sound.ironManButtonSound.play();

            GameData.gender = "IronMan";
            Player.isIronManSelected = true;
            Player.isHulkSelected = false;
            Player.isCaptainAmericaSelected = false;
            Player.isThorSelected = false;
            Player.isBlackWidowSelected = false;
            Player.isSpecialSelected = false;

        });

        hulkBtn.addActionListener(e -> {

            //Spiele Sound
            if (GameData.isSoundOn.equals("On")) Sound.hulkButtonSound.play();

                GameData.gender = "Hulk";
                Player.isHulkSelected = true;
                Player.isIronManSelected = false;
                Player.isCaptainAmericaSelected = false;
                Player.isThorSelected = false;
                Player.isBlackWidowSelected = false;
                Player.isSpecialSelected = false;

        });

        captainAmericaBtn.addActionListener(e -> {

            //Spiele Sound
            if(GameData.isSoundOn.equals("On")) Sound.captainAmericaButtonSound.play();

            GameData.gender = "Captain";
            Player.isCaptainAmericaSelected = true;
            Player.isIronManSelected = false;
            Player.isHulkSelected = false;
            Player.isThorSelected = false;
            Player.isBlackWidowSelected = false;
            Player.isSpecialSelected = false;

        });

        thorBtn.addActionListener(e -> {

            //Spiele Sound
            if(GameData.isSoundOn.equals("On")) Sound.thorButtonSound.play();

            GameData.gender = "Thor";
            Player.isThorSelected = true;
            Player.isCaptainAmericaSelected = false;
            Player.isHulkSelected = false;
            Player.isIronManSelected = false;
            Player.isBlackWidowSelected = false;
            Player.isSpecialSelected = false;

        });

        blackWidowBtn.addActionListener(e -> {

            //Spiele Sound
            if(GameData.isSoundOn.equals("On")) Sound.blackWidowButtonSound.play();

            GameData.gender = "Black Widow";
            Player.isBlackWidowSelected = true;
            Player.isThorSelected = false;
            Player.isCaptainAmericaSelected = false;
            Player.isHulkSelected = false;
            Player.isIronManSelected = false;
            Player.isSpecialSelected = false;


        });

        specialBtn.addActionListener(e -> {

            //Spiele Sound
            if (GameData.isSoundOn.equals("On")) Sound.specialButtonSound.play();

            GameData.gender = "Special";
            Player.isSpecialSelected = true;
            Player.isBlackWidowSelected = false;
            Player.isThorSelected = false;
            Player.isCaptainAmericaSelected = false;
            Player.isHulkSelected = false;
            Player.isIronManSelected = false;
        });

        normalBtn.addActionListener(e -> {

            //Spiele Sound
            if (GameData.isSoundOn.equals("On")) Sound.normalButtonSound.play();

            GameData.gender = "Normal";
            Player.isSpecialSelected = false;
            Player.isBlackWidowSelected = false;
            Player.isThorSelected = false;
            Player.isCaptainAmericaSelected = false;
            Player.isHulkSelected = false;
            Player.isIronManSelected = false;
        });

            backBtn.addActionListener(e ->
        {
            // Spiele Sound
            if(GameData.isSoundOn.equals("On")) Sound.backButtonSound.play();

            gamePanel.remove(ironManBtn);
            gamePanel.remove(hulkBtn);
            gamePanel.remove (captainAmericaBtn);
            gamePanel.remove(thorBtn);
            gamePanel.remove(blackWidowBtn);
            gamePanel.remove(specialBtn);
            gamePanel.remove(normalBtn);
            gamePanel.remove(backBtn);

            gamePanel.revalidate();
            gamePanel.repaint();

            stateManager.getGameStates().pop();
            stateManager.setActiveState(new MenuState(graphics, gamePanel, stateManager), StateManager.MENUSTATE);
        });


        gamePanel.setLayout(null);

        ironManBtn.setBounds(References.SCREEN_WIDTH/4-50, References.SCREEN_HEIGHT/2-20,
                ironManButton.getIconWidth(),
                ironManButton.getIconHeight());
        ironManBtn.setBorderPainted(false);
        ironManBtn.setFocusPainted(false);
        ironManBtn.setContentAreaFilled(false);
        ironManBtn.setOpaque(false);
        ironManBtn.setPressedIcon(ironManButtonPressed);
        ironManBtn.setVisible(true);
        gamePanel.add(ironManBtn);

        hulkBtn.setBounds(References.SCREEN_WIDTH/4+165, References.SCREEN_HEIGHT/2-20,
                hulkButton.getIconWidth(),
                hulkButton.getIconHeight());
        hulkBtn.setBorderPainted(false);
        hulkBtn.setFocusPainted(false);
        hulkBtn.setContentAreaFilled(false);
        hulkBtn.setOpaque(false);
        hulkBtn.setPressedIcon(hulkButtonPressed);
        hulkBtn.setVisible(true);
        gamePanel.add(hulkBtn);

        captainAmericaBtn.setBounds(References.SCREEN_WIDTH / 4 - 50, References.SCREEN_HEIGHT / 2 - 70,
                captainAmericaButton.getIconWidth(),
                captainAmericaButton.getIconHeight());
        captainAmericaBtn.setBorderPainted(false);
        captainAmericaBtn.setFocusPainted(false);
        captainAmericaBtn.setContentAreaFilled(false);
        captainAmericaBtn.setOpaque(false);
        captainAmericaBtn.setPressedIcon(captainAmericaButtonPressed);
        captainAmericaBtn.setVisible(true);
        gamePanel.add(captainAmericaBtn);

        thorBtn.setBounds(References.SCREEN_WIDTH/4 + 165, References.SCREEN_HEIGHT/2-70,
                thorButton.getIconWidth(),
                thorButton.getIconHeight());
        thorBtn.setBorderPainted(false);
        thorBtn.setFocusPainted(false);
        thorBtn.setContentAreaFilled(false);
        thorBtn.setOpaque(false);
        thorBtn.setPressedIcon(thorButtonPressed);
        thorBtn.setVisible(true);
        gamePanel.add(thorBtn);

        blackWidowBtn.setBounds(References.SCREEN_WIDTH / 4 - 265 , References.SCREEN_HEIGHT / 2 - 70,
                blackWidowButton.getIconWidth(),
                blackWidowButton.getIconHeight());
        blackWidowBtn.setBorderPainted(false);
        blackWidowBtn.setFocusPainted(false);
        blackWidowBtn.setContentAreaFilled(false);
        blackWidowBtn.setOpaque(false);
        blackWidowBtn.setPressedIcon(blackWidowButtonPressed);
        blackWidowBtn.setVisible(true);
        gamePanel.add(blackWidowBtn);

        specialBtn.setBounds(References.SCREEN_WIDTH / 4 - 265 , References.SCREEN_HEIGHT / 2 - 20,
                specialButton.getIconWidth(),
                specialButton.getIconHeight());
        specialBtn.setBorderPainted(false);
        specialBtn.setFocusPainted(false);
        specialBtn.setContentAreaFilled(false);
        specialBtn.setOpaque(false);
        specialBtn.setPressedIcon(specialButtonPressed);
        specialBtn.setVisible(true);
        gamePanel.add(specialBtn);

        normalBtn.setBounds(
                2 * ((References.SCREEN_WIDTH - normalButton.getIconWidth() * 3) / 4 + normalButton.getIconWidth()) + ((References.SCREEN_WIDTH - normalButton.getIconWidth() * 3) / 4),
                References.SCREEN_HEIGHT / 2 - normalButton.getIconHeight() / 2 - 50, normalButton.getIconWidth(),
                normalButton.getIconHeight());
        normalBtn.setBorderPainted(false);
        normalBtn.setFocusPainted(false);
        normalBtn.setContentAreaFilled(false);
        normalBtn.setOpaque(false);
        normalBtn.setPressedIcon(normalButtonPressed);
        normalBtn.setVisible(true);
        gamePanel.add(normalBtn);

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
