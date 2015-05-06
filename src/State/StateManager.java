package State;

import Main.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Stack;

/*
*
* */
public class StateManager {

    // Spielzustaende
    private final int MENUSTATE = 0;

    // Alle Spielzustaende gespeichert in einem Stack
    private Stack<State> gameStates;

    // Aktuelle Spielzustand
    private int activeState;

    public StateManager(Graphics graphics, GamePanel gamePanel) {
        this.gameStates = new Stack<State>();
        this.activeState = MENUSTATE;
        this.gameStates.push(new MenuState(graphics, gamePanel, this));
    }

    public void setActiveState(int stateNumber,Graphics graphics, JFrame gameFrame, JPanel gamePanel) {
        this.activeState = stateNumber;
        gameStates.peek().update(graphics, gameFrame, gamePanel);
    }

    public int getActiveState() {
        return this.activeState;
    }

    public void update(Graphics graphics, JFrame gameFrame, JPanel gamePanel) {
        gameStates.peek().update(graphics, gameFrame, gamePanel);
    }

    public void render(Graphics graphics, JFrame gameFrame, JPanel gamePanel) {
        gameStates.peek().render(graphics, gameFrame, gamePanel);
    }

    public void keyPressed(KeyEvent e) {
        gameStates.peek().keyPressed(e);
    }

    public void keyReleased(KeyEvent e) {
        gameStates.peek().keyReleased(e);
    }

    public void mouseClicked(MouseEvent e) {
        gameStates.peek().mouseClicked(e);
    }

    public void mousePressed(MouseEvent e) {
        gameStates.peek().mousePressed(e);
    }

    public void mouseReleased(MouseEvent e) {
        gameStates.peek().mouseReleased(e);
    }

    public void mouseEntered(MouseEvent e) {
        gameStates.peek().mouseEntered(e);
    }

    public void mouseExited(MouseEvent e) {
        gameStates.peek().mouseExited(e);
    }

}
