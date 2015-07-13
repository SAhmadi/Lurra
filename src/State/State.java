package State;

import Main.GamePanel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

/**
 * Abstrakte Klasse, Menuzustand
 *
 * @author Sirat
 * */
public abstract class State
{

    protected GamePanel gamePanel;
    protected Graphics graphics;
    protected StateManager stateManager;

    /**
     * State        Konstruktor der State Klasse
     *
     * @param gamePanel     Inhaltsflaeche
     * @param graphics      Graphics Objekt
     * @param stateManager  Zustandsmanager
     * */
    public State(GamePanel gamePanel, Graphics graphics, StateManager stateManager)
    {
        this.gamePanel = gamePanel;
        this.graphics = graphics;
        this.stateManager = stateManager;
    }

    public abstract void init();
    public abstract void update();
    public abstract void render(Graphics2D g);

    public abstract void keyPressed(KeyEvent e);
    public abstract void keyReleased(KeyEvent e);
    public abstract void mouseClicked(MouseEvent e);
    public abstract void mousePressed(MouseEvent e);
    public abstract void mouseReleased(MouseEvent e);
    public abstract void mouseEntered(MouseEvent e);
    public abstract void mouseExited(MouseEvent e);
    public abstract void mouseWheelMoved(MouseWheelEvent e);
    public abstract void mouseMoved(MouseEvent e);

}
