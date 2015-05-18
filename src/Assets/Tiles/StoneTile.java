package Assets.Tiles;

import Assets.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

/*
* StoneTile
* */
public class StoneTile extends Tile {

    public StoneTile(BufferedImage texture, int x, int y, int row, int column, boolean isCollidable, boolean hasGravity, boolean isDestructible) {
        super(texture, x, y, row, column, isCollidable, hasGravity, isDestructible);
    }

}
