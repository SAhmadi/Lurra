package Assets.World;

import java.awt.image.BufferedImage;

/**
 *
 * */
public class Tree extends Tile
{

    /**
     * Erzeugen eines Tiles mit all seinen Eigenschaften
     *
     * @param texture        Texture des Tiles
     * @param x              Position auf der x-Achse
     * @param y              Position auf der y-Achse
     * @param row            Position in Zeile
     * @param column         Position in Spalte
     * @param isCollidable   Tile ist kollidierbar
     * @param hasGravity     Tile wird von der Schwerkraft angezogen
     * @param isDestructible Tile ist zerstoerbar
     */
    public Tree(BufferedImage texture, int x, int y, int row, int column, boolean isCollidable, boolean hasGravity, boolean isDestructible)
    {
        super(texture, x, y, row, column, isCollidable, hasGravity, isDestructible);
    }



}
