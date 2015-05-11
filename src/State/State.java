package State;

import Main.GamePanel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/*
* State - Zustands-Klasse
* */
public abstract class State {

    protected GamePanel gamePanel;
    protected Graphics graphics;
    protected StateManager stateManager;

    public abstract void init();
    public abstract void update();
    public abstract void render(Graphics g);

    public abstract void keyPressed(KeyEvent e);
    public abstract void keyReleased(KeyEvent e);
    public abstract void mouseClicked(MouseEvent e);
    public abstract void mousePressed(MouseEvent e);
    public abstract void mouseReleased(MouseEvent e);
    public abstract void mouseEntered(MouseEvent e);
    public abstract void mouseExited(MouseEvent e);
}
