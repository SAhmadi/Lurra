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

    public WoodTile(BufferedImage texture, int x, int y, int row, int column, boolean isCollidable, boolean hasGravity, boolean isDestructible) {
        super(texture, x, y, row, column, isCollidable, hasGravity, isDestructible);
    }

    public WoodTile(BufferedImage texture, int x, int y,int row, int column, boolean isCollidable, boolean hasGravity, boolean isDestructible, boolean belongsToTree) {
        super(texture, x, y, row, column, isCollidable, hasGravity, isDestructible);
        this.belongsToTree = belongsToTree;
    }


}
