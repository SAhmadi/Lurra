package Assets.Tiles;

import Assets.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

/*
* DirtTile
* */
public class LeafTile extends Tile {

    public boolean belongsToTree;

    public LeafTile(BufferedImage texture, int x, int y, boolean isCollidable, boolean hasGravity, boolean isDestructable, int index) {
        super(texture, x, y, isCollidable, hasGravity, isDestructable, index);
    }

    public LeafTile(BufferedImage texture, int x, int y, boolean isCollidable, boolean hasGravity, boolean isDestructable, int index, boolean belongsToTree) {
        super(texture, x, y, isCollidable, hasGravity, isDestructable, index);
        this.belongsToTree = belongsToTree;
    }

    public void render(Graphics graphics) {
        super.render(graphics);
    }

}
