package Assets.World;

import Main.ResourceLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Erzeugen eines Tile Objekts
 *
 * @author Sirat
 * */
public class Tile
{
    /* Tiles */
    private int x;
    private int y;
    private int xTmp;
    private int yTmp;

    private int row;
    private int column;

    private BufferedImage texture;

    private boolean isCollidable;
    private boolean hasGravity;
    private boolean isDestructible;
    private boolean belongsToTree;

    private String name;
    private int resistance;


    /**
     * Erzeugen eines Tiles mit all seinen Eigenschaften
     *
     * @param texture           Texture des Tiles
     * @param x                 Position auf der x-Achse
     * @param y                 Position auf der y-Achse
     * @param row               Position in Zeile
     * @param column            Position in Spalte
     * @param isCollidable      Tile ist kollidierbar
     * @param hasGravity        Tile wird von der Schwerkraft angezogen
     * @param isDestructible    Tile ist zerstoerbar
     * */
    public Tile(BufferedImage texture, int x, int y, int row, int column, boolean isCollidable, boolean hasGravity, boolean isDestructible)
    {
        this.x = x;
        this.y = y;
        this.xTmp = this.x;
        this.yTmp = this.y;

        this.texture = texture;

        this.row = row;
        this.column = column;

        this.isCollidable = isCollidable;
        this.hasGravity = hasGravity;
        this.isDestructible = isDestructible;

        this.belongsToTree = false;
    }

    /**
     * render           Zeichnen des Tiles
     *
     * @param graphics  Graphics Objekt
     * */
    public void render(Graphics graphics, double xMap, double yMap)
    {
        x = (int) (xTmp + xMap);
        y = (int) (yTmp + yMap);

        if (texture != null)
            graphics.drawImage(texture, x, y, null);
    }

    /**
     * checkIfTree      Pruefe ob Tile zu einem Baum gehört
     * */
    private boolean checkIfTree()
    {
        return this.texture == ResourceLoader.treeTrunkRootLeft ||
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
                this.texture == ResourceLoader.leafTopRightCorner;
    }


    // GETTER UND SETTER
    /**
     * getX             Rueckgabe der x-Position
     * */
    public int getX() { return this.x; }

    /**
     * setX             Setzen der x-Position
     *
     * @param x         x-Position
     * */
    public void setX(int x) { this.x = x; }

    /**
     * getY             Rueckgabe der y-Position
     * */
    public int getY() { return this.y; }

    /**
     * setY             Setzen der y-Position
     *
     * @param y         y-Position
     * */
    public void setY(int y) { this.y = y; }

    /**
     * getRow           Rueckgabe der Zeile
     * */
    public int getRow() { return this.row; }

    /**
     * setRow           Setzen der Zeile
     *
     * @param row       Zeile
     * */
    public void setRow(int row) { this.row = row; }

    /**
     * getColumn        Rueckgabe der Spalte
     * */
    public int getColumn() { return this.column; }

    /**
     * setColumn        Setzen der Spalte
     *
     * @param column    Spalte
     * */
    public void setColumn(int column) { this.column = column; }

    /**
     * getIsCollidable          Setzen ob kollidierbar
     *
     * @return boolean          Wert ob kollidierbar
     * */
    public boolean getIsCollidable() { return this.isCollidable; }

    /**
     * setIsCollidable          Setzen ob Tile kollidierbar ist
     *
     * @param isCollidable      Wert ob Tile kollidierbar ist
     * */
    public void setIsCollidable(boolean isCollidable) { this.isCollidable = isCollidable; }

    /**
     * getHasGravity            Setzen ob von der Schwerkraft angezogen wird
     *
     * @return boolean          Wert ob von der Schwerkraft angezogen wird
     * */
    public boolean getHasGravity() { return this.hasGravity; }

    /**
     * setHasGravity            Setzen ob Tile von der Schwerkraft angezogen wird
     * @param hasGravity        Wert ob Tile von der Schwerkraft angezogen wird
     * */
    public void setHasGravity(boolean hasGravity) { this.hasGravity = hasGravity; }

    /**
     * getIsDestructible        Setzen ob zerstoerbar
     *
     * @return boolean          Wert ob zerstoerbar
     * */
    public boolean getIsDestructible() { return this.isDestructible; }

    /**
     * setIsDestructible        Setzen ob Tile zerstoerbar ist
     *
     * @param isDestructible    Wert ob Tile zerstoerbar ist
     * */
    public void setIsDestructible(boolean isDestructible) { this.isDestructible = isDestructible; }

    /**
     * getTexture       Rueckgabe der Texture
     * */
    public BufferedImage getTexture() { return this.texture; }

    /**
     * setTexture       Setzen der Texture
     *
     * @param texture   Texture des Tiles
     * */
    public void setTexture(BufferedImage texture)
    {
        this.texture = texture;

        if(texture != null)
        {
            this.belongsToTree = checkIfTree();
            this.setResistance();
        }
        else
        {
            this.belongsToTree = false;
            this.resistance = 0;
        }

    }

    /**
     * getResistance    Rueckgabe des Wiederstands
     * */
    public int getResistance() { return this.resistance; }

    /**
     * setResistance    Setzen der Wiederstands
     * */
    private void setResistance()
    {
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
                this.texture == ResourceLoader.treeTrunkTopRightEnd)
        {
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
                this.texture == ResourceLoader.leafTopRightCorner)
        {
            this.resistance = 5;
            this.name = "Blatt";
        }

        // Erde
        if(this.texture == ResourceLoader.dirt ||
                this.texture == ResourceLoader.dirtMidDark ||
                this.texture == ResourceLoader.dirtDark)
        {
            this.resistance = 3;
            this.name = "Erde";
        }

        // Gras
        if(this.texture == ResourceLoader.grasTile)
        {
            this.resistance = 3;
            this.name = "Gras";
        }

        // Kupfer
        if(this.texture == ResourceLoader.copper)
        {
            this.resistance = 7;
            this.name = "Kupfer";
        }

        // Silber
        if(this.texture == ResourceLoader.silver)
        {
            this.resistance = 9;
            this.name = "Silber";
        }

        // Gold
        if(this.texture == ResourceLoader.gold)
        {
            this.resistance = 12;
            this.name = "Gold";
        }
    }

    /**
     * getTextureAsString       Rueckgabe des Texturenamen
     * */
    public String getTextureAsString()
    {
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

    /**
     * getTextureFromString     Rueckgabe der Texture durch einen Namen
     * */
    public static BufferedImage getTextureFromString(String textureName)
    {
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
