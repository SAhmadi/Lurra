package Assets.World;

import Main.ResourceLoader;

import java.awt.image.BufferedImage;

/**
 * Created by Vanessa on 30.06.2015.
 */
public class Batterie {
    private int x;
    private int y;
    private int row;
    private int column;
    private BufferedImage texture;
    public String name;
    private boolean isConductive = false;
    int NeighborUp;
    int NeighborDown;
    int NeighborLeft;
    int NeighborRight;
    private boolean isCollidable = true;
    private boolean hasGravity = true;
    private boolean isDestructible = true;
    int CenterC;
    int CenterR;

    public int getColumn() { return this.column; }
    public int getRow() { return this.row; }
    public void setTexture(BufferedImage texture) {this.texture = texture;}
    public BufferedImage getTexture (int row, int column) { return this.texture; }
    public void setIsConductive(Bluerock blue) { this.isConductive = true; this.name = "BluerockOn"; setTexture(ResourceLoader.BluerockOn); }
    public void setIsNotConductive(Bluerock blue) { this.isConductive = false; this.name = "BluerockOff"; setTexture(ResourceLoader.BluerockOff);}

}
