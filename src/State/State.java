package State;

import Main.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/*
*
* */
public abstract class State {

    protected GamePanel gamePanel;
    public StateManager stateManager;

    public abstract void update(Graphics graphics, JFrame gameFrame, JPanel gamePanel);
    public abstract void render(Graphics graphics, JFrame gameFrame, JPanel gamePanel);

    public abstract void keyPressed(KeyEvent e);
    public abstract void keyReleased(KeyEvent e);
    public abstract void mouseClicked(MouseEvent e);
    public abstract void mousePressed(MouseEvent e);
    public abstract void mouseReleased(MouseEvent e);
    public abstract void mouseEntered(MouseEvent e);
    public abstract void mouseExited(MouseEvent e);
}
