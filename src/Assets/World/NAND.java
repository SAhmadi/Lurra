/*package Assets.World;

import Main.ResourceLoader;

import java.awt.image.BufferedImage;

/**
 * Created by Vanessa on 30.06.2015.
 */
/*public class NAND {
    private int x;
    private int y;
    private int row;
    private int column;
    private BufferedImage texture;
    public String name;
    private boolean isConductive = false;
    int InA;
    int InBc;
    int InBr;
    int Out;
    private boolean isCollidable = true;
    private boolean hasGravity = true;
    private boolean isDestructible = true;
    private boolean hasEnergy = true;
    int CenterC;
    int CenterR;

    public int getColumn() { return this.column; }
    public int getRow() { return this.row; }
    public void setTexture(BufferedImage texture) {this.texture = texture;}
    public BufferedImage getTexture (int row, int column) { return this.texture; }
    public void setIsConductive(int row, int column) { this.isConductive = true; this.name = "BluerockOn"; setTexture(ResourceLoader.BluerockOn); }
    public void setIsNotConductive(int row, int column) { this.isConductive = false; this.name = "BluerockOff"; setTexture(ResourceLoader.BluerockOff);}

    public void checkNeighborsNANDR(Bluerock blue) {
        CenterC = blue.getColumn();
        CenterR = blue.getRow();
        InA = blue.getColumn() - 1;
        InBc = blue.getColumn() - 1;
        InBr = blue.getRow() + 1;
        Out = blue.getColumn() + 1;
        if((getTexture(CenterR, InA) == ResourceLoader.BluerockOff)&&(getTexture(InBr, InBc)== ResourceLoader.BluerockOff)){
            setIsConductive(CenterC, Out);
        }
        if((getTexture(CenterR, InA) == ResourceLoader.BluerockOff)&&(getTexture(InBr, InBc)== ResourceLoader.BluerockOn)){
            setIsConductive(CenterC, Out);
        }
        if((getTexture(CenterR, InA) == ResourceLoader.BluerockOn)&&(getTexture(InBr, InBc)== ResourceLoader.BluerockOff)){
            setIsConductive(CenterC, Out);
        }
        if((getTexture(CenterR, InA) == ResourceLoader.BluerockOn)&&(getTexture(InBr, InBc)== ResourceLoader.BluerockOn)){
            setIsNotConductive(CenterC, Out);
        }

    }
    public void checkNeighborsNANDL(Bluerock blue) {
        CenterC = blue.getColumn();
        CenterR = blue.getRow();
        InA = blue.getColumn() + 1;
        InBc = blue.getColumn() + 1;
        InBr = blue.getRow() + 1;
        Out = blue.getColumn() - 1;
        if((getTexture(CenterR, InA) == ResourceLoader.BluerockOff)&&(getTexture(InBr, InBc)== ResourceLoader.BluerockOff)){
            setIsConductive(CenterC,Out);
        }
        if((getTexture(CenterR, InA) == ResourceLoader.BluerockOff)&&(getTexture(InBr, InBc)== ResourceLoader.BluerockOn)){
            setIsConductive(CenterC,Out);
        }
        if((getTexture(CenterR, InA) == ResourceLoader.BluerockOn)&&(getTexture(InBr, InBc)== ResourceLoader.BluerockOff)){
            setIsConductive(CenterC,Out);
        }
        if((getTexture(CenterR, InA) == ResourceLoader.BluerockOn)&&(getTexture(InBr, InBc)== ResourceLoader.BluerockOn)){
            setIsNotConductive(CenterC, Out);
        }

    }
}
*/