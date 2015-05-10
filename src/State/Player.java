package State;


import java.awt.*;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

/**
 * Created by Amin und Mohamed on 10.05.2015.
 * Spielfigur hinzugefügt
 * "Kleinigkeiten" in der Main verändert, Avatarbuttom hinzugefügt
 */
public class Player implements KeyListener {

    private Image figure;
    private int spawnPosX;
    private int spawnPosY;
    private int key;



    public Player(Image figure, int spawnPosX, int spawnPosY) {
        this.figure = figure;
        this.spawnPosX = spawnPosX;
        this.spawnPosY = spawnPosY;

    }


    public void update() {
    }

    public void render(Graphics g) {
        g.drawImage(figure, spawnPosX, spawnPosY, null);
    }


    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override//funktionieren noch nicht!!
    public void keyPressed(KeyEvent e) {
        key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            spawnPosX = spawnPosX - 2;

        }

        if (key == KeyEvent.VK_RIGHT) {
            spawnPosX = spawnPosX + 2;
        }
        if(key == KeyEvent.VK_ESCAPE){
            System.exit(0);
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {
        key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
            spawnPosX = 0;
        }

    }
}





