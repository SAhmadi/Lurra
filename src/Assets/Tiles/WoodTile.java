package Assets.Tiles;

import Assets.Tile;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/*
* DirtTile
* */
public class WoodTile extends Tile {

    public boolean belongsToTree;

    public WoodTile(BufferedImage texture, int x, int y, boolean isCollidable, boolean hasGravity, boolean isDestructable, int index) {
        super(texture, x, y, isCollidable, hasGravity, isDestructable, index);
    }

    public WoodTile(BufferedImage texture, int x, int y, boolean isCollidable, boolean hasGravity, boolean isDestructable, int index, boolean belongsToTree) {
        super(texture, x, y, isCollidable, hasGravity, isDestructable, index);
        this.belongsToTree = belongsToTree;
    }


}
