package State.Menu;

import Main.GamePanel;
import Main.References;
import Main.ResourceLoader;
import State.State;
import State.StateManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

/**
 * Hauptmenu-Zustand des Spiels
 *
 * @author Sirat
 * @version 1.0
 * */
public class GameIntro extends State
{

    /**
     * GameIntro            Konstruktor der GameIntro-Klasse
     *
     * @param graphics      Graphics Objekt
     * @param gamePanel     Spielinhaltsflaeche
     * @param stateManager  Zustandsmanager
     * */
    public GameIntro(Graphics graphics, GamePanel gamePanel, StateManager stateManager)
    {
        super(gamePanel, graphics, stateManager);
        init(); // Initialisieren des Hauptmenus
    }

    /**
     * init         Initialisieren des Hauptmenus
     * */
    @Override
    public void init()
    {
        // Zeichne Himmel
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, References.SCREEN_WIDTH, References.SCREEN_HEIGHT);

        // Zeichne Intro
        graphics.drawImage(
                ResourceLoader.gameIntro,
                References.SCREEN_WIDTH/2 - ResourceLoader.gameIntro.getWidth()/2,
                References.SCREEN_HEIGHT/2 - ResourceLoader.gameIntro.getHeight()/2,
                ResourceLoader.gameIntro.getWidth(),
                ResourceLoader.gameIntro.getHeight(),
                null
        );
    }

    @Override
    public void update()
    {
        try { Thread.sleep(5000); }
        catch (InterruptedException ex) { if (References.SHOW_EXCEPTION) System.out.println("Error: " + ex.getMessage()); }

        stateManager.setActiveState(new MenuState(graphics, gamePanel, stateManager), StateManager.MENUSTATE);
    }

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
