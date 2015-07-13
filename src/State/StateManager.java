package State;

import Main.GamePanel;
import State.Menu.GameIntro;

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
    public static final byte MPLEVELSTATE = -24;
    public static final byte JOIN_ONLINE_GAMESTATE = -23;
    public static final byte LOBBY_STATE = -22;
    public static final byte CREATE_ONLINE_GAMESTATE = -21;
    public static final byte PLAYONLINEMENUSTATE = -20;

    // LOKAL
    public static final byte THEMEMENUSTATE = -9;
    public static final byte CONTINUEGAMESTATE = -8;
    public static final byte NEWGAMESTATE = -7;
    public static final byte PLAYLOCALMENUSTATE = -6;
    public static final byte AVATARMENUSTATE = -5;
    public static final byte SOUNDMENUSTATE = -4;
    public static final byte SETTINGSMENUSTATE = -3;
    public static final byte STARTMENUSTATE = -2;
    public static final byte MENUSTATE = -1;
    public static final byte INTRO = 0;
    public static final byte LEVEL1STATE = 1;

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

        this.activeState = INTRO;
        this.gameStates.push(new GameIntro(graphics, gamePanel, this));
    }

    /**
     * update           Aktualisieren des obersten Zustands
     * */
    public void update() { gameStates.peek().update(); }

    /**
     * render           Zeichnen des obersten Zustands
     *
     * @param graphics  Graphics Objekt
     * */
    public void render(Graphics2D graphics) { gameStates.peek().render(graphics); }

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
     * @return              Aktuelle Zustands-ID
     * */
    public int getActiveState() { return this.activeState; }

    /**
     * setActiveStateID     Setzen der aktuellen Zustands-ID
     * @param id            Aktuelle Zustands-ID
     * */
    public void setActiveStateID(int id) { this.activeState = id; }

    /**
     * getGameStates        Rueckgabe des gesamten Zustandsstack
     * @return              Gesamter Zustandsstack
     * */
    public Stack<State> getGameStates() { return this.gameStates; }

}
