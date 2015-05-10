package Assets.Tiles;

import Assets.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

/*
* StoneTile
* */
public class WaterTile extends Tile {

    public WaterTile(BufferedImage texture, int x, int y, boolean isCollidable, boolean hasGravity, boolean isDestructable) {
        super(texture, x, y, isCollidable, hasGravity, isDestructable);
    }

    public void render(Graphics graphics) {
        super.render(graphics);
    }

}
