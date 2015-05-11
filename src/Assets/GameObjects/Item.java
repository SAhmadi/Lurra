package Assets.GameObjects;

import Assets.GameObject;

import java.awt.image.BufferedImage;

/**
 * Created by Vanessa on 10.05.2015.
 */

public abstract class Item extends GameObject {

    public Item(BufferedImage image, int width, int height, int x, int y, int vX, int vY) {
        super(image, width, height, x, y, vX, vY);
    }
}