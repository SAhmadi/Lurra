package Assets.GameObjects;

import Assets.GameObject;

import java.awt.image.BufferedImage;

/**
 * Created by Vanessa on 10.05.2015.
 */
public abstract class Weapon extends GameObject {
    // Stärke
    public int power;

    public Weapon(BufferedImage image, int width, int height, int x, int y, int vX, int vY) {
        super(image, width, height, x, y, vX, vY);
    }

    public void setPower (int p){return;};
    public int getPower() {return getPower();};

    //Reichweite
    public int range;

}