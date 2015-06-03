package Assets.World;

import Main.ResourceLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

/*
* Tile - Tile-Bloecke
* */
public class Tile {

    //
    public static final int WATERTILE_TOP = 1;
    public static final int WATERTILE = 2;
    public static final int LAVALTILETOP = 4;
    public static final int LAVATILE = 3;

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

    // BILDER FEHLEN NOCH, FOLGEN SPAETER
    public static final int DIAMOND = 248;
    public static final int ICE = 249;
    public static final int RUBIN = 250;
    public static final int SAPHIRE = 251;
    public static final int SMARAGD = 252;
    public static final int TOPAS = 253;


    protected BufferedImage texture;
    //public int id;  // ID Fehlt noch um die abgebauten Tiles mit zu speichern

    public boolean isCollidable;
    public boolean hasGravity;
    public boolean isDestructible;
    public boolean belongsToTree;

    protected int x;
    protected int y;
    protected int row;
    protected int column;
    public static final int WIDTH = 16;
    public static final int HEIGHT = 16;

    public String name;
    private int resistance;


    public Tile(BufferedImage texture, int x, int y, int row, int column, boolean isCollidable, boolean hasGravity, boolean isDestructible) {
        this.texture = texture;
        //.id = id;

        this.x = x;
        this.y = y;
        this.row = row;
        this.column = column;

        this.isCollidable = isCollidable;
        this.hasGravity = hasGravity;
        this.isDestructible = isDestructible;

        this.belongsToTree = false;
    }

    public void render(Graphics graphics) {
        graphics.drawImage(texture, x, y, null);
    }

    /*
    * delete - Setzt Bild auf null, das explizite remove wird in der TileMap-Klasse vollzogen
    * */
    public void delete() {
        this.texture = null;
        this.isCollidable = false;
        this.hasGravity = false;
        this.isDestructible = false;
        this.resistance = 0;
        this.belongsToTree = false;
        this.name = "";
    }


    /**
     *
     * */
    private boolean checkIfTree() {
        if(this.texture == ResourceLoader.treeTrunkRootLeft ||
                this.texture == ResourceLoader.treeTrunkBottomLeft ||
                this.texture == ResourceLoader.treeTrunkBottomRight ||
                this.texture == ResourceLoader.treeTrunkRootRight ||
                this.texture == ResourceLoader.treeTrunkRoundedCornerTopLeft ||
                this.texture == ResourceLoader.treeTrunkNextToCorner ||
                this.texture == ResourceLoader.treeTrunkHorizontalNormal ||
                this.texture == ResourceLoader.treeTrunkRoundedCornerBottomRight ||
                this.texture == ResourceLoader.treeTrunkVerticalNormal ||
                this.texture == ResourceLoader.treeTrunkTopCenter ||
                this.texture == ResourceLoader.treeTrunkTopLeft ||
                this.texture == ResourceLoader.treeTrunkTopLeftEnd ||
                this.texture == ResourceLoader.treeTrunkTopRightEnd ||

                this.texture == ResourceLoader.leafBottomLeftCorner ||
                this.texture == ResourceLoader.leafBottom ||
                this.texture == ResourceLoader.leafBottomRightCorner ||
                this.texture == ResourceLoader.leafRight ||
                this.texture == ResourceLoader.leafNormal ||
                this.texture == ResourceLoader.leafLeft ||
                this.texture == ResourceLoader.leafTopLeftCorner ||
                this.texture == ResourceLoader.leafTop ||
                this.texture == ResourceLoader.leafTopRightCorner) {

            return true;

        }
        return false;
    }


    /*
    * Getter and Setter
    * */
    public void setIsCollidable(boolean isCollidable) { this.isCollidable = isCollidable; }
    public void setHasGravity(boolean hasGravity) { this.hasGravity = hasGravity; }
    public void setIsDestructible(boolean isDestructible) { this.isDestructible = isDestructible; }

    public void setBelongsToTree(boolean belongsToTree) { this.belongsToTree = belongsToTree; }

    public int getX() { return this.x; }
    public int getY() { return this.y; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }

    public void setRow(int row) { this.row = row; }
    public void setColumn(int column) { this.column = column; }
    public int getRow() { return this.row; }
    public int getColumn() { return this.column; }

    public BufferedImage getTexture() { return this.texture; }

    public void setTexture(BufferedImage texture) {
        this.texture = texture;
        if(texture != null) {
            this.belongsToTree = checkIfTree();
            this.setResistance();
        }
        else {
            this.belongsToTree = false;
            this.resistance = 0;
        }

    }

    public void wasHit() {
        this.resistance--;
    }

    private void setResistance() {
        // Baumstamm
        if(this.texture == ResourceLoader.treeTrunkRootLeft ||
                this.texture == ResourceLoader.treeTrunkBottomLeft ||
                this.texture == ResourceLoader.treeTrunkBottomRight ||
                this.texture == ResourceLoader.treeTrunkRootRight ||
                this.texture == ResourceLoader.treeTrunkRoundedCornerTopLeft ||
                this.texture == ResourceLoader.treeTrunkNextToCorner ||
                this.texture == ResourceLoader.treeTrunkHorizontalNormal ||
                this.texture == ResourceLoader.treeTrunkRoundedCornerBottomRight ||
                this.texture == ResourceLoader.treeTrunkVerticalNormal ||
                this.texture == ResourceLoader.treeTrunkTopCenter ||
                this.texture == ResourceLoader.treeTrunkTopLeft ||
                this.texture == ResourceLoader.treeTrunkTopLeftEnd ||
                this.texture == ResourceLoader.treeTrunkTopRightEnd) {

            this.resistance = 5;
            this.name = "Holz";
        }

        // Baumkrone
        if(this.texture == ResourceLoader.leafBottomLeftCorner ||
                this.texture == ResourceLoader.leafBottom ||
                this.texture == ResourceLoader.leafBottomRightCorner ||
                this.texture == ResourceLoader.leafRight ||
                this.texture == ResourceLoader.leafNormal ||
                this.texture == ResourceLoader.leafLeft ||
                this.texture == ResourceLoader.leafTopLeftCorner ||
                this.texture == ResourceLoader.leafTop ||
                this.texture == ResourceLoader.leafTopRightCorner) {

            this.resistance = 5;
            this.name = "Blatt";
        }

        // Erde
        if(this.texture == ResourceLoader.dirt ||
                this.texture == ResourceLoader.dirtMidDark ||
                this.texture == ResourceLoader.dirtDark) {

            this.resistance = 3;
            this.name = "Erde";
        }

        // Gras
        if(this.texture == ResourceLoader.grasTile) {

            this.resistance = 3;
            this.name = "Gras";
        }

        // Kupfer
        if(this.texture == ResourceLoader.copper) {

            this.resistance = 7;
            this.name = "Kupfer";
        }

        // Silber
        if(this.texture == ResourceLoader.silver) {

            this.resistance = 9;
            this.name = "Silber";
        }

        // Gold
        if(this.texture == ResourceLoader.gold) {
            this.resistance = 12;
            this.name = "Gold";
        }
    }

    public int getResistance() { return this.resistance; }

    public String getTextureAsString() {
        if(this.texture == ResourceLoader.grasTile) return "grasTile";

        else if(this.texture == ResourceLoader.dirt) return "dirt";
        else if(this.texture == ResourceLoader.dirtMidDark) return "dirtMidDark";
        else if(this.texture == ResourceLoader.dirtDark) return "dirtDark";

        else if(this.texture == ResourceLoader.gold) return "gold";
        else if(this.texture == ResourceLoader.silver) return "silver";
        else if(this.texture == ResourceLoader.copper) return "copper";

        else if(this.texture == ResourceLoader.lavaTile) return "lavaTile";
        else if(this.texture == ResourceLoader.lavaTileTop) return "lavaTileTop";

        else if(this.texture == ResourceLoader.waterTile) return "waterTile";
        else if(this.texture == ResourceLoader.waterTileTop) return "waterTileTop";

        else if(this.texture == ResourceLoader.treeTrunkRootLeft) return "treeTrunkRootLeft";
        else if(this.texture == ResourceLoader.treeTrunkBottomLeft) return "treeTrunkBottomLeft";
        else if(this.texture == ResourceLoader.treeTrunkBottomRight) return "treeTrunkBottomRight";
        else if(this.texture == ResourceLoader.treeTrunkRootRight) return "treeTrunkRootRight";
        else if(this.texture == ResourceLoader.treeTrunkRoundedCornerTopLeft) return "treeTrunkRoundedCornerTopLeft";
        else if(this.texture == ResourceLoader.treeTrunkNextToCorner) return "treeTrunkNextToCorner";
        else if(this.texture == ResourceLoader.treeTrunkHorizontalNormal) return "treeTrunkHorizontalNormal";
        else if(this.texture == ResourceLoader.treeTrunkRoundedCornerBottomRight) return "treeTrunkRoundedCornerBottomRight";
        else if(this.texture == ResourceLoader.treeTrunkVerticalNormal) return "treeTrunkVerticalNormal";
        else if(this.texture == ResourceLoader.treeTrunkTopCenter) return "treeTrunkTopCenter";
        else if(this.texture == ResourceLoader.treeTrunkTopLeft) return "treeTrunkTopLeft";
        else if(this.texture == ResourceLoader.treeTrunkTopLeftEnd) return "treeTrunkTopLeftEnd";
        else if(this.texture == ResourceLoader.treeTrunkTopRightEnd) return "treeTrunkTopRightEnd";

        else if(this.texture == ResourceLoader.leafBottomLeftCorner) return "leafBottomLeftCorner";
        else if(this.texture == ResourceLoader.leafBottom) return "leafBottom";
        else if(this.texture == ResourceLoader.leafBottomRightCorner) return "leafBottomRightCorner";
        else if(this.texture == ResourceLoader.leafRight) return "leafRight";
        else if(this.texture == ResourceLoader.leafNormal) return "leafNormal";
        else if(this.texture == ResourceLoader.leafLeft) return "leafLeft";
        else if(this.texture == ResourceLoader.leafTopLeftCorner) return "leafTopLeftCorner";
        else if(this.texture == ResourceLoader.leafTop) return "leafTop";
        else if(this.texture == ResourceLoader.leafTopRightCorner) return "leafTopRightCorner";

        else if(this.texture == null) return "";

        return "";
    }

    public static BufferedImage getTextureFromString(String textureName) {
        if(textureName.equals("grasTile")) return ResourceLoader.grasTile;

        else if(textureName.equals("dirt")) return ResourceLoader.dirt;
        else if(textureName.equals("dirtMidDark")) return ResourceLoader.dirtMidDark;
        else if(textureName.equals("dirtDark")) return ResourceLoader.dirtDark;

        else if(textureName.equals("gold")) return ResourceLoader.gold;
        else if(textureName.equals("silver")) return ResourceLoader.silver;
        else if(textureName.equals("copper")) return ResourceLoader.copper;

        else if(textureName.equals("lavaTile")) return ResourceLoader.lavaTile;
        else if(textureName.equals("lavaTileTop")) return ResourceLoader.lavaTileTop;

        else if(textureName.equals("waterTile")) return ResourceLoader.waterTile;
        else if(textureName.equals("waterTileTop")) return ResourceLoader.waterTileTop;

        else if(textureName.equals("treeTrunkRootLeft")) return ResourceLoader.treeTrunkRootLeft;
        else if(textureName.equals("treeTrunkBottomLeft")) return ResourceLoader.treeTrunkBottomLeft;
        else if(textureName.equals("treeTrunkBottomRight")) return ResourceLoader.treeTrunkBottomRight;
        else if(textureName.equals("treeTrunkRootRight")) return ResourceLoader.treeTrunkRootRight;
        else if(textureName.equals("treeTrunkRoundedCornerTopLeft")) return ResourceLoader.treeTrunkRoundedCornerTopLeft;
        else if(textureName.equals("treeTrunkNextToCorner")) return ResourceLoader.treeTrunkNextToCorner;
        else if(textureName.equals("treeTrunkHorizontalNormal")) return ResourceLoader.treeTrunkHorizontalNormal;
        else if(textureName.equals("treeTrunkRoundedCornerBottomRight")) return ResourceLoader.treeTrunkRoundedCornerBottomRight;
        else if(textureName.equals("treeTrunkVerticalNormal")) return ResourceLoader.treeTrunkVerticalNormal;
        else if(textureName.equals("treeTrunkTopCenter")) return ResourceLoader.treeTrunkTopCenter;
        else if(textureName.equals("treeTrunkTopLeft")) return ResourceLoader.treeTrunkTopLeft;
        else if(textureName.equals("treeTrunkTopLeftEnd")) return ResourceLoader.treeTrunkTopLeftEnd;
        else if(textureName.equals("treeTrunkTopRightEnd")) return ResourceLoader.treeTrunkTopRightEnd;

        else if(textureName.equals("leafBottomLeftCorner")) return ResourceLoader.leafBottomLeftCorner;
        else if(textureName.equals("leafBottom")) return ResourceLoader.leafBottom;
        else if(textureName.equals("leafBottomRightCorner")) return ResourceLoader.leafBottomRightCorner;
        else if(textureName.equals("leafRight")) return ResourceLoader.leafRight;
        else if(textureName.equals("leafNormal")) return ResourceLoader.leafNormal;
        else if(textureName.equals("leafLeft")) return ResourceLoader.leafLeft;
        else if(textureName.equals("leafTopLeftCorner")) return ResourceLoader.leafTopLeftCorner;
        else if(textureName.equals("leafTop")) return ResourceLoader.leafTop;
        else if(textureName.equals("leafTopRightCorner")) return ResourceLoader.leafTopRightCorner;

        else if(textureName.equals("")) return null;

        return null;
    }
}
