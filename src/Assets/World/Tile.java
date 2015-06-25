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
    public static boolean isEatable;


    public String name;
    public int resistance;
    private int ep_bonus;


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

        setTexture(texture);    // Setzen der Texture, Wiederstand sowie pruefen ob Baum

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
        return this.texture == ResourceLoader.treeTrunkRoot ||
                this.texture == ResourceLoader.treeTrunk ||
                this.texture == ResourceLoader.treeTrunkRight ||
                this.texture == ResourceLoader.treeTrunkLeft ||
                this.texture == ResourceLoader.treeTrunkTop ||

                this.texture == ResourceLoader.leafStart ||
                this.texture == ResourceLoader.leaf ||
                this.texture == ResourceLoader.leafEnd;
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

    public void wasHit(double damage) {
        System.out.println("Hit: " + damage + " | Life left: " + resistance);
        this.resistance -= Math.min(resistance, damage);
    }

    /**
     * getResistance    Rueckgabe des Wiederstands
     * */
    public int getResistance() { return this.resistance; }

    /**
     * setResistance        Setzen des Wiederstands
     *
     * @param resistance    Wiederstand
     * */
    public void setResistance(int resistance) { this.resistance = resistance; }

    /**
     * setResistance    Setzen der Wiederstands
     * */
    private void setResistance()
    {
        // Baumstamm
        if(this.texture == ResourceLoader.treeTrunkRoot ||
                this.texture == ResourceLoader.treeTrunk ||
                this.texture == ResourceLoader.treeTrunkRight ||
                this.texture == ResourceLoader.treeTrunkLeft ||
                this.texture == ResourceLoader.treeTrunkTop)
        {
            this.resistance = 10;
            this.name = "Holz";
        }

        // Baumkrone
        else if(this.texture == ResourceLoader.leafStart ||
                this.texture == ResourceLoader.leaf ||
                this.texture == ResourceLoader.leafEnd)
        {
            this.resistance = 10;
            this.name = "Blatt";
        }

        // Erde
        else if(this.texture == ResourceLoader.dirt ||
                this.texture == ResourceLoader.dirtMidDark ||
                this.texture == ResourceLoader.dirtDark)
        {
            this.resistance = 7;
            this.name = "Erde";
        }

        // Gras
        else if(this.texture == ResourceLoader.gras ||
                this.texture == ResourceLoader.grasWithFlower)
        {
            this.resistance = 7;
            this.name = "Gras";
        }

        // Kupfer
        else if(this.texture == ResourceLoader.copper)
        {
            this.resistance = 10;
            this.name = "Kupfer";
        }

        // Silber
        else if(this.texture == ResourceLoader.silver)
        {
            this.resistance = 14;
            this.name = "Silber";
        }

        // Gold
        else if(this.texture == ResourceLoader.gold)
        {
            this.resistance = 18;
            this.name = "Gold";
        }

        // Burger
        else if (this.texture == ResourceLoader.burger)
        {
            this.resistance = 15;
            this.name = "Burger";
            this.isEatable = true;

        }

        //Tränke
        else if (this.texture == ResourceLoader.healthPotion)
        {
            this.resistance = 20;
            this.name = "Zaubertrank";
            this.isEatable = true;

        }

        // Eisen
        else if(this.texture == ResourceLoader.ion)
        {
            this.resistance = 8;
            this.name = "Eisen";
        }

        // Rubin
        else if(this.texture == ResourceLoader.ruby)
        {
            this.resistance = 12;
            this.name = "Rubin";
        }

        // Saphire
        else if(this.texture == ResourceLoader.saphire)
        {
            this.resistance = 12;
            this.name = "Saphire";
        }

        // Smaragd
        else if(this.texture == ResourceLoader.smaragd)
        {
            this.resistance = 15;
            this.name = "Smaragd";
        }

        // Diamant
        else if(this.texture == ResourceLoader.diamond)
        {
            this.resistance = 20;
            this.name = "Diamant";
        }

        ep_bonus = resistance * 10;
    }


    /**
     * getTextureAsString       Rueckgabe des Texturenamen
     * */
    public String getTextureAsString()
    {
        if(this.texture == ResourceLoader.gras) return "gras";
        else if(this.texture == ResourceLoader.grasWithFlower) return "grasWithFlower";

        else if(this.texture == ResourceLoader.dirt) return "dirt";
        else if(this.texture == ResourceLoader.dirtMidDark) return "dirtMidDark";
        else if(this.texture == ResourceLoader.dirtDark) return "dirtDark";

        else if(this.texture == ResourceLoader.gold) return "gold";
        else if(this.texture == ResourceLoader.silver) return "silver";
        else if(this.texture == ResourceLoader.copper) return "copper";
        else if(this.texture == ResourceLoader.ion) return "ion";
        else if(this.texture == ResourceLoader.ruby) return "ruby";
        else if(this.texture == ResourceLoader.saphire) return "saphire";
        else if(this.texture == ResourceLoader.smaragd) return "smaragd";
        else if(this.texture == ResourceLoader.diamond) return "diamond";


        else if(this.texture == ResourceLoader.lava) return "lava";
        else if(this.texture == ResourceLoader.lavaTop) return "lavaTop";

        else if(this.texture == ResourceLoader.water) return "water";
        else if(this.texture == ResourceLoader.waterTop) return "waterTop";
        else if(this.texture == ResourceLoader.waterTop2) return "waterTop2";

        else if(this.texture == ResourceLoader.treeTrunkRoot) return "treeTrunkRoot";
        else if(this.texture == ResourceLoader.treeTrunk) return "treeTrunk";
        else if(this.texture == ResourceLoader.treeTrunkRight) return "treeTrunkRight";
        else if(this.texture == ResourceLoader.treeTrunkLeft) return "treeTrunkLeft";
        else if(this.texture == ResourceLoader.treeTrunkTop) return "treeTrunkTop";

        else if(this.texture == ResourceLoader.leafStart) return "leafStart";
        else if(this.texture == ResourceLoader.leaf) return "leaf";
        else if(this.texture == ResourceLoader.leafEnd) return "leafEnd";
        else if(this.texture == ResourceLoader.burger) return  "burger";
        else if(this.texture == ResourceLoader.healthPotion) return  "healthPotion";

        else if(this.texture == null) return "";

        return "";
    }

    /**
     * getTextureFromString     Rueckgabe der Texture durch einen Namen
     * */
    public static BufferedImage getTextureFromString(String textureName)
    {
        if(textureName.equals("grasTile")) return ResourceLoader.gras;

        else if(textureName.equals("dirt")) return ResourceLoader.dirt;
        else if(textureName.equals("dirtMidDark")) return ResourceLoader.dirtMidDark;
        else if(textureName.equals("dirtDark")) return ResourceLoader.dirtDark;

        else if(textureName.equals("goldDirt")) return ResourceLoader.gold;
        else if(textureName.equals("silverDirt")) return ResourceLoader.silver;
        else if(textureName.equals("copperDirt")) return ResourceLoader.copper;

        else if(textureName.equals("lava")) return ResourceLoader.lava;
        else if(textureName.equals("lavaTop")) return ResourceLoader.lavaTop;
        else if(textureName.equals("water")) return ResourceLoader.water;
        else if(textureName.equals("waterTop")) return ResourceLoader.waterTop;

        else if(textureName.equals("treeTrunkRoot")) return ResourceLoader.treeTrunkRoot;
        else if(textureName.equals("treeTrunk")) return ResourceLoader.treeTrunk;
        else if(textureName.equals("treeTrunkRight")) return ResourceLoader.treeTrunkRight;
        else if(textureName.equals("treeTrunkLeft")) return ResourceLoader.treeTrunkLeft;
        else if(textureName.equals("treeTrunkTop")) return ResourceLoader.treeTrunkTop;

        else if(textureName.equals("leafStart")) return ResourceLoader.leafStart;
        else if(textureName.equals("leaf")) return ResourceLoader.leaf;
        else if(textureName.equals("leafEnd")) return ResourceLoader.leafEnd;
        else if (textureName.equals("burger")) return  ResourceLoader.burger;
        else if (textureName.equals("healthPotion")) return ResourceLoader.healthPotion;

        else if(textureName.equals("")) return null;

        return null;
    }


    public int getEpBonus() {
        return ep_bonus;
    }
}
