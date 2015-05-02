import javax.swing.*;

public abstract class Tile {

    public JLabel[] tile;
    private boolean isCollectable;

    public abstract boolean getIsCollectable();

}
