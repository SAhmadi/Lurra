package Assets.Tiles;

import Assets.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

/*
* StoneTile
* */
public class StoneTile extends Tile {

    public StoneTile(BufferedImage texture, int x, int y, boolean isCollidable, boolean hasGravity, boolean isDestructable, int index) {
        super(texture, x, y, isCollidable, hasGravity, isDestructable, index);
    }

}
