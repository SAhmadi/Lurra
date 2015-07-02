package Assets.World;

import Main.ResourceLoader;

import java.awt.image.BufferedImage;

/**
 * Created by Vanessa on 30.06.2015.
 */
public class Switch {
    private int x;
    private int y;
    private int row;
    private int column;
    private BufferedImage texture;
    public String name;
    private boolean isOn;
    private boolean isCollidable = true;
    private boolean hasGravity = true;
    private boolean isDestructible = true;
    int NeighborUp;
    int NeighborDown;
    int NeighborLeft;
    int NeighborRight;
    private boolean isConductive;
    int CenterC;
    int CenterR;

    public Switch (BufferedImage texture, int x, int y, int row, int column, boolean isOn){
        this.x = x;
        this.y = y;
        setTexture(texture);
        this.row = row;
        this.column = column;
        this.isOn = isOn;
        this.isConductive = isConductive;
    }

    public int getColumn() { return this.column; }
    public int getRow() { return this.row; }
    public void setTexture(BufferedImage texture) {this.texture = texture;}
    public BufferedImage getTexture(int column) { return this.texture; }
    public void setIsOn (Switch switches) { this.isOn = true; this.name = "SwitchOn"; setTexture(ResourceLoader.SwitchOn); }
    public void setIsNotOn (Switch switches) { this.isOn = false; this.name = "SwitchOff"; setTexture(ResourceLoader.SwitchOff);}


    //Schalter betätigen
    public void turnSwitch(Switch switches){

        if (isOn == true) {
            this.isOn = false;
            this.name = "SwitchOff";
            setTexture(ResourceLoader.SwitchOff);

        }
        if (isOn == false) {
            this.isOn = true;
            this.name = "SwitchOn";
            setTexture(ResourceLoader.SwitchOn);
            checkSurroundingOn(switches);
        }

    }
    public int getsColumn() { return this.column; }
    public int getsRow() { return this.row; }
    public void setsTexture(BufferedImage texture) {this.texture = texture;}
    public BufferedImage getsTexture(int column) { return this.texture; }
    public void setsIsConductive(Bluerock blue) { this.isConductive = true; this.name = "BluerockOn"; setTexture(ResourceLoader.BluerockOn); }
    public void setsIsNotConductive(Bluerock blue) { this.isConductive = false; this.name = "BluerockOff"; setTexture(ResourceLoader.BluerockOff);}

    public void checkSurroundingOn(Switch switches ){
        CenterC = switches.getColumn();
        CenterR = switches.getRow();
        NeighborUp = switches.getRow() - 1;
        NeighborDown = switches.getRow() + 1;
        NeighborLeft = switches.getColumn() - 1;
        NeighborRight = switches.getColumn() + 1;

    }
}