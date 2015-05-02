import javax.swing.*;

public class GrasTile {

    private JLabel tiles[];
    private boolean isCollectable;

    public GrasTile(JLabel[] tiles, boolean isCollectable) {
        this.tiles = tiles;
        this.isCollectable = isCollectable;
    }

    public boolean getIsCollectable() {
        return this.isCollectable;
    }

}
