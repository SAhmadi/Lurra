package Assets.Tiles;

import Assets.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

/*
* DirtTile
* */
public class CopperTile extends Tile {

    public CopperTile(BufferedImage texture, int x, int y, int row, int column, boolean isCollidable, boolean hasGravity, boolean isDestructible) {
        super(texture, x, y, row, column, isCollidable, hasGravity, isDestructible);
    }

}
