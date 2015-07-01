package Assets.World;

import Main.ResourceLoader;

import java.awt.image.BufferedImage;

/**
 * Created by Vanessa on 30.06.2015.
 */
public class Bluerock {
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

    public Bluerock (BufferedImage texture, int x, int y, int row, int column, boolean isConductive){
        this.x = x;
        this.y = y;
        setTexture(texture);
        this.row = row;
        this.column = column;
        this.isConductive = isConductive;
    }


    public int getColumn() { return this.column; }
    public int getRow() { return this.row; }
    public void setTexture(BufferedImage texture) {this.texture = texture;}
    public BufferedImage getTexture (int row, int column) { return this.texture; }
    public void setIsConductive(Bluerock blue) { this.isConductive = true; this.name = "BluerockOn"; setTexture(ResourceLoader.BluerockOn); }
    public void setIsNotConductive(Bluerock blue) { this.isConductive = false; this.name = "BluerockOff"; setTexture(ResourceLoader.BluerockOff);}

    //Umgebung prüfen
    public void checkNeighbors(Bluerock blue){
        CenterC = blue.getColumn();
        CenterR = blue.getRow();
        NeighborUp = blue.getRow() - 1;
        NeighborDown = blue.getRow() + 1;
        NeighborLeft = blue.getColumn() - 1;
        NeighborRight = blue.getColumn() + 1;
        if(getTexture(NeighborUp, CenterC) == ResourceLoader.BluerockOn){
            setIsConductive(blue);
        }
        if(getTexture(NeighborDown, CenterC) == ResourceLoader.BluerockOn){
            setIsConductive(blue);
        }
        if(getTexture(CenterR, NeighborLeft) == ResourceLoader.BluerockOn){
            setIsConductive(blue);
        }
        if(getTexture(CenterR, NeighborRight) == ResourceLoader.BluerockOn){
            setIsConductive(blue);
        }


        if(getTexture(NeighborUp, CenterC) == ResourceLoader.Batterie){
            setIsConductive(blue);
        }
        if(getTexture(NeighborDown, CenterC) == ResourceLoader.Batterie){
            setIsConductive(blue);
        }
        if(getTexture(CenterR, NeighborLeft) == ResourceLoader.Batterie){
            setIsConductive(blue);
        }
        if(getTexture(CenterR, NeighborRight) == ResourceLoader.Batterie){
            setIsConductive(blue);
        }


        if(getTexture(NeighborUp, CenterC) == ResourceLoader.SwitchOn){
            setIsConductive(blue);
        }
        if(getTexture(NeighborDown, CenterC) == ResourceLoader.SwitchOn){
            setIsConductive(blue);
        }
        if(getTexture(CenterR, NeighborLeft) == ResourceLoader.SwitchOn){
            setIsConductive(blue);
        }
        if(getTexture(CenterR, NeighborRight) == ResourceLoader.SwitchOn){
            setIsConductive(blue);
        }


        if(getTexture(NeighborUp, CenterC) == ResourceLoader.BluerockOff){
            setIsNotConductive(blue);
        }
        if(getTexture(NeighborDown, CenterC) == ResourceLoader.BluerockOff){
            setIsNotConductive(blue);
        }
        if(getTexture(CenterR, NeighborLeft) == ResourceLoader.BluerockOff){
            setIsNotConductive(blue);
        }
        if(getTexture(CenterR, NeighborRight) == ResourceLoader.BluerockOff){
            setIsNotConductive(blue);
        }


        if(getTexture(NeighborUp, CenterC) == ResourceLoader.SwitchOff){
            setIsNotConductive(blue);
        }
        if(getTexture(NeighborDown, CenterC) == ResourceLoader.SwitchOff){
            setIsNotConductive(blue);
        }
        if(getTexture(CenterR, NeighborLeft) == ResourceLoader.SwitchOff){
            setIsNotConductive(blue);
        }
        if(getTexture(CenterR, NeighborRight) == ResourceLoader.SwitchOff){
            setIsNotConductive(blue);
        }
    }

}

