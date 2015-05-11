package Assets.Tiles;

import Assets.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

/*
* StoneTile
* */
public class GrasTile extends Tile {

    public GrasTile(BufferedImage texture, int x, int y, boolean isCollidable, boolean hasGravity, boolean isDestructable, int index) {
        super(texture, x, y, isCollidable, hasGravity, isDestructable, index);
    }

    public void render(Graphics graphics) {
        super.render(graphics);
    }

}