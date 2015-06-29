package State;

import Main.GamePanel;
import State.Menu.MenuState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.Stack;

/**
 * Verwaltet alle Spielzustaende
 *
 * @author Sirat
 * */
public class StateManager
{

    public Graphics graphics;
    public GamePanel gamePanel;

    // Online
    public static final int MPLEVELSTATE = -24;
    public static final int JOIN_ONLINE_GAMESTATE = -23;
    public static final int LOBBY_STATE = -22;
    public static final int CREATE_ONLINE_GAMESTATE = -21;
    public static final int PLAYONLINEMENUSTATE = -20;

    // LOKAL
    public static final int CONTINUEGAMESTATE = -7;
    public static final int NEWGAMESTATE = -6;
    public static final int PLAYLOCALMENUSTATE = -5;
    public static final int AVATARMENUSTATE = -4;
    public static final int SOUNDMENUSTATE = -3;
    public static final int SETTINGSMENUSTATE = -2;
    public static final int STARTMENUSTATE = -1;
    public static final int MENUSTATE = 0;
    public static final int LEVEL1STATE = 1;

    // Alle Spielzustaende gespeichert in einem Stack
    private Stack<State> gameStates;

    // Aktuelle Spielzustand
    private int activeState;

    /**
     * StateManager         Konstruktor der StateManager-Klasse
     *
     * @param graphics      Graphics Objekt
     * @param gamePanel     Spielinhaltsflaeche
     * */
    public StateManager(Graphics graphics, GamePanel gamePanel)
    {
        this.graphics = graphics;
        this.gamePanel = gamePanel;

        this.gameStates = new Stack<>();

        this.activeState = MENUSTATE;
        this.gameStates.push(new MenuState(graphics, gamePanel, this));
    }

    /**
     * update           Aktualisieren des obersten Zustands
     * */
    public void update() { gameStates.peek().update(); }

    /**
     * render           Zeichnen des obersten Zustands
     * */
    public void render(Graphics graphics) { gameStates.peek().render(graphics); }

    // KEY EVENTS
    public void keyPressed(KeyEvent e) { gameStates.peek().keyPressed(e); }

    public void keyReleased(KeyEvent e) { gameStates.peek().keyReleased(e); }

    // MOUSE EVENTS
    public void mouseClicked(MouseEvent e) { gameStates.peek().mouseClicked(e); }

    public void mousePressed(MouseEvent e) { gameStates.peek().mousePressed(e); }

    public void mouseReleased(MouseEvent e) { gameStates.peek().mouseReleased(e); }

    public void mouseEntered(MouseEvent e) { gameStates.peek().mouseEntered(e); }

    public void mouseExited(MouseEvent e) { gameStates.peek().mouseExited(e); }

    public void mouseWheelMoved(MouseWheelEvent e) { gameStates.peek().mouseWheelMoved(e); }

    public void mouseMoved(MouseEvent e) { gameStates.peek().mouseMoved(e); }

    // GETTER UND SETTER

    /**
     * setActiveState       Setzen des aktuellen Zustands und seiner ID
     * @param state         Aktueller Zustand
     * @param id            Aktuelle Zustands-ID
     * */
    public void setActiveState(State state, int id)
    {
        this.activeState = id;
        this.gameStates.push(state);
    }

    /**
     * getActiveState       Rueckgabe der aktuellen Zustands-ID
     * @return int          Aktuelle Zustands-ID
     * */
    public int getActiveState() { return this.activeState; }

    /**
     * setActiveStateID     Setzen der aktuellen Zustands-ID
     * @param id            Aktuelle Zustands-ID
     * */
    public void setActiveStateID(int id) { this.activeState = id; }

    /**
     * getGameStates        Rueckgabe des gesamten Zustandsstack
     * @return Stack<State> Zustandsstack
     * */
    public Stack<State> getGameStates() { return this.gameStates; }

}
