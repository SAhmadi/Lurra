import javax.swing.*;

public class EarthTile extends Tile {

    private JLabel tiles[];
    private boolean isCollectable;

    public EarthTile(JLabel[] tiles, boolean isCollectable) {
        this.tiles = tiles;
        this.isCollectable = isCollectable;
    }

    public boolean getIsCollectable() {
        return this.isCollectable;
    }

}