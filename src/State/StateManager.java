package State;

import Main.GamePanel;
import State.Menu.MenuState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.Stack;

/*
* StateManager - Managed alle Game-States
* Prueft aktuellen State und fuehrt entsprechende Update-, Render- und EventListener-Methoden aus
* */
public class StateManager {

    public Graphics graphics;
    public GamePanel gamePanel;

    /*
    * Spielzustaende
    * */
    // Pause
    public static final int PAUSESTATE = -99;

    // Online
    public static final int JOIN_ONLINE_GAMESTATE = -23;
    public static final int LOBBY_STATE = -22;
    public static final int CREATE_ONLINE_GAMESTATE = -21;
    public static final int PLAYONLINEMENUSTATE = -20;

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

    /*
    * Konstruktor - Initialisieren
    * */
    public StateManager(Graphics graphics, GamePanel gamePanel) {
        this.graphics = graphics;
        this.gamePanel = gamePanel;
        this.gameStates = new Stack<State>();
        this.activeState = this.MENUSTATE;
        this.gameStates.push(new MenuState(graphics, gamePanel, this));
    }

    /*
    * update - Fuehre Update-Methode des aktuellen States aus
    * */
    public void update() { gameStates.peek().update(); }

    /*
    * render - Fuehre Render-Methode des aktuellen States aus
    * */
    public void render(Graphics graphics) { gameStates.peek().render(graphics); }

    /*
    * EventListener - Fuehre Key- und Mouse-Events des aktuellen States aus
    * */
    public void keyPressed(KeyEvent e) { gameStates.peek().keyPressed(e); }
    public void keyReleased(KeyEvent e) { gameStates.peek().keyReleased(e); }

    public void mouseClicked(MouseEvent e) { gameStates.peek().mouseClicked(e); }
    public void mousePressed(MouseEvent e) { gameStates.peek().mousePressed(e); }
    public void mouseReleased(MouseEvent e) { gameStates.peek().mouseReleased(e); }
    public void mouseEntered(MouseEvent e) { gameStates.peek().mouseEntered(e); }
    public void mouseExited(MouseEvent e) { gameStates.peek().mouseExited(e); }
    public void mouseWheelMoved(MouseWheelEvent e) { gameStates.peek().mouseWheelMoved(e); }
    public void mouseMoved(MouseEvent e) { gameStates.peek().mouseMoved(e); }

    /*
    * Setter und Getter
    * */
    // Aktueller State
    public void setActiveState(State state, int id) {
        this.activeState = id;
        this.gameStates.push(state);
    }

    public void setActiveStateID(int id) {
        this.activeState = id;
    }

    public int getActiveState() { return this.activeState; }

    // Gesamter State-Stack
    public Stack<State> getGameStates() { return this.gameStates; }

}
