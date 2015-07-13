package State.Menu;

import GameSaves.GameData.GameData;
import GameSaves.GameData.GameDataSave;
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
 * Avatar Auswahlmenu
 *
 * @author Sirat
 * */
public class AvatarMenuState extends State
{

    private ImageIcon maleCharacterButtonUnactive, maleCharacterButtonActive;
    private ImageIcon femaleCharacterButtonUnactive, femaleCharacterButtonActive;
    private ImageIcon backButton, backButtonPressed;

    // Menu Buttons
    private JButton maleCharacterBtn;
    private JButton femaleCharacterBtn;
    private JButton backBtn;

    /**
     * AvatarMenuState      Konstruktor der AvatarMenuState-Klasse
     *
     * @param graphics      Graphics Objekt
     * @param gamePanel     Spielinhaltsflaeche
     * @param stateManager  Zustandsmanager
     * */
    public AvatarMenuState(Graphics graphics, GamePanel gamePanel, StateManager stateManager)
    {
        super(gamePanel, graphics, stateManager);

        this.maleCharacterButtonUnactive = ResourceLoader.maleCharacterButtonUnactive;
        this.maleCharacterButtonActive = ResourceLoader.maleCharacterButtonActive;

        this.femaleCharacterButtonUnactive = ResourceLoader.femaleCharacterButtonUnactive;
        this.femaleCharacterButtonActive = ResourceLoader.femaleCharacterButtonActive;

        this.backButton = ResourceLoader.backButton;
        this.backButtonPressed = ResourceLoader.backButtonPressed;

        // Initialisieren der Buttons
        this.init();
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
                ResourceLoader.menuIlandBackground.getWidth(null), ResourceLoader.menuIlandBackground.getHeight(null),
                null
        );

        // Zeichne Title
        graphics.drawImage(
                ResourceLoader.menuTitleImage,
                (References.SCREEN_WIDTH / 2) - (ResourceLoader.menuTitleImage.getWidth(null) / 2),
                (References.SCREEN_HEIGHT / 4),
                ResourceLoader.menuTitleImage.getWidth(null), ResourceLoader.menuTitleImage.getHeight(null),
                null
        );

        // Initialisieren der Buttons
        maleCharacterBtn = new JButton();
        femaleCharacterBtn = new JButton();
        backBtn = new JButton(backButton);

        // BUTTONLISTENERS
        maleCharacterBtn.addActionListener(e ->
        {
            if(GameData.gender.equals("Male"))
            {
                maleCharacterBtn.setIcon(maleCharacterButtonUnactive);
                femaleCharacterBtn.setIcon(femaleCharacterButtonActive);
                GameData.gender = "Female";
                GameDataSave.XMLSave();
            }
            else if(GameData.gender.equals("Female"))
            {
                maleCharacterBtn.setIcon(maleCharacterButtonActive);
                femaleCharacterBtn.setIcon(femaleCharacterButtonUnactive);
                GameData.gender = "Male";
                GameDataSave.XMLSave();
            }
        });

        femaleCharacterBtn.addActionListener(e ->
        {
            if(GameData.gender.equals("Female"))
            {
                femaleCharacterBtn.setIcon(femaleCharacterButtonUnactive);
                maleCharacterBtn.setIcon(maleCharacterButtonActive);
                GameData.gender = "Male";
                GameDataSave.XMLSave();
            }
            else if(GameData.gender.equals("Male"))
            {
                femaleCharacterBtn.setIcon(femaleCharacterButtonActive);
                maleCharacterBtn.setIcon(maleCharacterButtonUnactive);
                GameData.gender = "Female";
                GameDataSave.XMLSave();
            }
        });

        backBtn.addActionListener(e ->
        {
            // Spiele Sound
            if (GameData.isSoundOn.equals("On"))
                Sound.backButtonSound.play();

            gamePanel.remove(maleCharacterBtn);
            gamePanel.remove(femaleCharacterBtn);
            gamePanel.remove(backBtn);

            gamePanel.revalidate();
            gamePanel.repaint();

            stateManager.getGameStates().pop();
            stateManager.setActiveState(new SettingsMenuState(graphics, gamePanel, stateManager), StateManager.SETTINGSMENUSTATE);
        });

        // BUTTON POSITIONIEREN
        // Kein Layout, um Buttons selbst zu positionieren
        gamePanel.setLayout(null);

        // Male-Character Button
        // In den Grenzen des MenuTitleImage positionieren
        maleCharacterBtn.setBounds(
                References.SCREEN_WIDTH / 2 - ResourceLoader.menuTitleImage.getWidth() / 2,
                References.SCREEN_HEIGHT / 2 - maleCharacterButtonActive.getIconHeight() / 2,
                maleCharacterButtonActive.getIconWidth(),
                maleCharacterButtonActive.getIconHeight()
        );
        maleCharacterBtn.setBorderPainted(false);
        maleCharacterBtn.setFocusPainted(false);
        maleCharacterBtn.setContentAreaFilled(true);
        maleCharacterBtn.setOpaque(true);

        if(GameData.gender.equals("Male"))
            maleCharacterBtn.setIcon(maleCharacterButtonActive);
        else
            maleCharacterBtn.setIcon(maleCharacterButtonUnactive);

        maleCharacterBtn.setVisible(true);
        gamePanel.add(maleCharacterBtn);

        // Female-Character Button
        femaleCharacterBtn.setBounds(
                References.SCREEN_WIDTH/ 2 + ResourceLoader.menuTitleImage.getWidth() / 2 - femaleCharacterButtonActive.getIconWidth(),
                References.SCREEN_HEIGHT / 2 - femaleCharacterButtonActive.getIconHeight() / 2,
                femaleCharacterButtonActive.getIconWidth(),
                femaleCharacterButtonActive.getIconHeight()
        );
        femaleCharacterBtn.setBorderPainted(false);
        femaleCharacterBtn.setFocusPainted(false);
        femaleCharacterBtn.setContentAreaFilled(false);
        femaleCharacterBtn.setOpaque(true);

        if(GameData.gender.equals("Female"))
            femaleCharacterBtn.setIcon(femaleCharacterButtonActive);
        else
            femaleCharacterBtn.setIcon(femaleCharacterButtonUnactive);

        femaleCharacterBtn.setVisible(true);
        gamePanel.add(femaleCharacterBtn);

        // Zurueck Button
        backBtn.setBounds(
                References.SCREEN_WIDTH/2 - backButton.getIconWidth()/2,
                References.SCREEN_HEIGHT/2 + maleCharacterButtonActive.getIconHeight() - backButton.getIconHeight()/2,
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
