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

    private JButton ironManBtn;
    private JButton hulkBtn;
    private JButton captainAmericaBtn;
    private JButton thorBtn;
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
        ironManBtn = new JButton("IRON MAN");
        hulkBtn = new JButton("HULK");
        captainAmericaBtn = new JButton("CAPTAIN AMERICA");
        thorBtn = new JButton("THOR");
        backBtn = new JButton(backButton);

        //BUTTONLISTENERS
        ironManBtn.addActionListener(e ->
        {
            if(GameData.isSoundOn.equals("On")) Sound.ironManButtonSound.play();

            GameData.gender = "IronMan";
            Player.isIronManSelected = true;
            Player.isHulkSelected = false;
            Player.isCaptainAmericaSelected = false;
            Player.isThorSelected = false;
        });

        hulkBtn.addActionListener(e -> {

            if(GameData.isSoundOn.equals("On")) Sound.hulkButtonSound.play();

            GameData.gender = "Hulk";
            Player.isHulkSelected = true;
            Player.isIronManSelected = false;
            Player.isCaptainAmericaSelected = false;
            Player.isThorSelected = false;
        });

        captainAmericaBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(GameData.isSoundOn.equals("On")) Sound.captainAmericaButtonSound.play();

                GameData.gender = "Captain";
                Player.isCaptainAmericaSelected = true;
                Player.isIronManSelected = false;
                Player.isHulkSelected = false;
                Player.isThorSelected = false;
            }
        });

        thorBtn.addActionListener(e -> {
            if(GameData.isSoundOn.equals("On")) Sound.thorButtonSound.play();

            GameData.gender = "Thor";
            Player.isThorSelected = true;
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
        ironManBtn.setFont(ResourceLoader.textFieldFont);
        ironManBtn.setVisible(true);
        gamePanel.add(ironManBtn);

        hulkBtn.setBounds(References.SCREEN_WIDTH/4+155, References.SCREEN_HEIGHT/2-20, 200, 40);
        hulkBtn.setBackground(Color.green);
        hulkBtn.setForeground(Color.white);
        hulkBtn.setFont(ResourceLoader.textFieldFont);
        hulkBtn.setVisible(true);
        gamePanel.add(hulkBtn);

        captainAmericaBtn.setBounds(References.SCREEN_WIDTH / 4 - 50, References.SCREEN_HEIGHT / 2 - 70, 200, 40);
        captainAmericaBtn.setBackground(Color.green);
        captainAmericaBtn.setForeground(Color.white);
        captainAmericaBtn.setFont(ResourceLoader.textFieldFont);
        captainAmericaBtn.setVisible(true);
        gamePanel.add(captainAmericaBtn);

        thorBtn.setBounds(References.SCREEN_WIDTH/4 + 155, References.SCREEN_HEIGHT/2-70, 200, 40);
        thorBtn.setBackground(Color.green);
        thorBtn.setForeground(Color.white);
        thorBtn.setFont(ResourceLoader.textFieldFont);
        thorBtn.setVisible(true);
        gamePanel.add(thorBtn);

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
