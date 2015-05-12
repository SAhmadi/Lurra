package Assets;


import java.awt.*;
import java.awt.image.BufferedImage;

/*
* Tile - Tile-Bloecke
* */
public class Tile {

    //
    public static final int WATERTILE_TOP = 1;
    public static final int WATERTILE = 2;
    public static final int LAVALTILETOP = 3;
    public static final int LAVATILE = 4;

    //
    public static final int GRASTILE_RIGHT = 14;
    public static final int GRASTILE_RIGHT_DESTROYED = 15;
    public static final int GRASTILE_LEFT = 27;
    public static final int GRASTILE_LEFT_DESTROYED = 28;
    public static final int GRASTILE_BOTTOM = 40;
    public static final int GRASTILE_BOTTOM_DESTROYED = 41;

    public static final int GRASTILE_BOTTOMRIGHTCORNER = 53;
    public static final int GRASTILE_BOTTOMRIGHTCORNER_DESTROYED = 54;
    public static final int GRASTILE_BOTTOMLEFTCORNER = 66;
    public static final int GRASTILE_BOTTOMLEFTCORNER_DESTROYED = 67;
    public static final int GRASTILE_TOPRIGHTCORNER = 79;
    public static final int GRASTILE_TOPRIGHTCORNER_DESTROYED = 80;
    public static final int GRASTILE_TOPLEFTCORNER = 92;
    public static final int GRASTILE_TOPLEFTCORNER_DESTROYED = 93;

    public static final int GRASTILE = 105;
    public static final int GRASTILE_DESTROYED = 106;

    //
    public static final int DIRTDARK = 118;
    public static final int DIRTDARK_DESTROYED = 119;
    public static final int DIRTMIDDARK = 131;
    public static final int DIRTMIDDARK_DESTROYED = 132;
    public static final int DIRT = 144;
    public static final int DIRT_DESTROYED = 145;

    //
    public static final int STONEWHITE1 = 157;
    public static final int STONEWHITE2 = 158;
    public static final int STONEWHITE3 = 159;
    public static final int STONEWHITELEAF = 160;
    public static final int STONEWHITE_DESTROYED = 161;

    public static final int STONERED1 = 170;
    public static final int STONERED2 = 171;
    public static final int STONERED3 = 172;
    public static final int STONEREDLEAF = 173;
    public static final int STONERED_DESTROYED = 174;

    //
    public static final int COPPER = 183;
    public static final int COPPER_DESTROYED = 184;

    public static final int SILVER = 196;
    public static final int SILVER_DESTROYED = 197;

    public static final int GOLD = 209;
    public static final int GOLD_DESTROYED = 210;

    //
    public static final int LEAFBOTTOM_LEFTCORNER = 222;
    public static final int LEAFBOTTOM = 223;
    public static final int LEAFBOTTOM_RIGHTCORNER = 224;
    public static final int LEAFRIGHT = 225;
    public static final int LEAFNORMAL = 226;
    public static final int LEAFLEFT = 227;
    public static final int LEAFTOP_LEFTCORNER = 228;
    public static final int LEAFTOP = 229;
    public static final int LEAFTOP_RIGHTCORNER = 230;

    public static final int TREETRUNK_ROOTLEFT = 235;
    public static final int TREETRUNK_BOTTOMLEFT = 236;
    public static final int TREETRUNK_BOTTOMRIGHT = 237;
    public static final int TREETRUNK_ROOTRIGHT = 238;
    public static final int TREETRUNK_ROUNDEDCORNER_TOPLEFT = 239;
    public static final int TREETRUNK_NEXTTOCORNER = 240;
    public static final int TREETRUNK_HORIZONTALNORMAL = 241;
    public static final int TREETRUNK_ROUNDEDCORNER_BOTTOMRIGHT = 242;
    public static final int TREETRUNK_VERTICALNORMAL = 243;
    public static final int TREETRUNK_TOPCENTER = 244;
    public static final int TREETRUNK_TOPLEFT = 245;
    public static final int TREETRUNK_TOPLEFTEND = 246;
    public static final int TREETRUNK_TOPRIGHTEND = 247;

    protected BufferedImage texture;
    public boolean isCollidable;
    public boolean hasGravity;
    public boolean isDestructible;

    protected int x;
    protected int y;
    protected int row;
    protected int column;
    public static final int WIDTH = 16;
    public static final int HEIGHT = 16;

    public Tile(BufferedImage texture, int x, int y, int row, int  column, boolean isCollidable, boolean hasGravity, boolean isDestructible) {
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.row = row;
        this.column = column;

        this.isCollidable = isCollidable;
        this.hasGravity = hasGravity;
        this.isDestructible = isDestructible;
    }

    public void render(Graphics graphics) {
        graphics.drawImage(texture, x, y, Tile.WIDTH, Tile.HEIGHT, null);
    }

    /*
    * delete - Setzt Bild auf null, das explizite remove wird in der TileMap-Klasse vollzogen
    * */
    public void delete() {
        this.texture = null;
    }


    /*
    * Getter and Setter
    * */
    public void setCollidable(boolean isCollidable) { this.isCollidable = isCollidable; }

    public int getX() { return this.x; }
    public int getY() { return this.y; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }

    public void setRow(int row) { this.row = row; }
    public void setColumn(int column) { this.column = column; }
    public int getRow() { return this.row; }
    public int getColumn() { return this.column; }

    public Image getTexture() { return this.texture; }
}
