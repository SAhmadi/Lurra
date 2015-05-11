package Assets.GameObjects;

import Assets.GameObject;

import java.awt.image.BufferedImage;

/**
 * Created by Vanessa on 10.05.2015.
 */
public abstract class Beast extends GameObject {
    //Gesundheit
    public int health;

    public Beast(BufferedImage image, int width, int height, int x, int y, int vX, int vY) {
        super(image, width, height, x, y, vX, vY);
    }

    public void setHealth (int h){return;};

    public int getHealth() {
        return getHealth();
    }

    // Stärke
    public int power;

    // Agieren
    public void attac (GameObject g){return;};
    public void move (int x1, int x2, int y1, int y2){return;};

}