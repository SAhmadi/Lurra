package Assets.World;

import Main.References;
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
    // Tiles
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
    private boolean hasEnergy;
    private boolean isConductive;

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

        setTexture(texture);    // Setzen der Texture, Wiederstand, Namen sowie pruefen ob Baum

        this.row = row;
        this.column = column;

        this.isCollidable = isCollidable;
        this.hasGravity = hasGravity;
        this.isDestructible = isDestructible;
        this.belongsToTree = false;
        this.hasEnergy = hasEnergy;
        this.isConductive = isConductive;
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
     * getHasEnergy        Setzen ob Energieträger
     *
     * @return boolean          Wert ob Energieträger
     * */
    public boolean getHasEnergy() { return this.hasEnergy; }

    /**
     * setHasEnergy        Setzen ob Tile Energieträger ist
     *
     * @param hasEnergy    Wert ob Tile Energieträger ist
     * */
    public void setHasEnergy(boolean hasEnergy) { this.hasEnergy = hasEnergy; }

    /**
     * setIsConductive        Setzen das  Tile unter Strom steht
     *
     * */

    public void setIsConductive() {
        this.isConductive = true;
        this.name = "BluerockOn";
        setTexture(ResourceLoader.BluerockOn); }
    /**
     * setIsConductive        Setzen das nicht Tile unter Strom steht
     *
     * */

    public void setIsNotConductive() {
        this.isConductive = false;
        this.name = "BluerockOff";
        setTexture(ResourceLoader.BluerockOff);}
    /**
     * setNeighbors        Benachbarte Tiles festlegen
     *
     * @param tile, switchOn
     * */
    public static void setNeighbors(Tile tile, boolean switchOn) {
        Tile[] neighbors = getNeighbors(tile);
        if (tile.getHasEnergy() == false) {
            return;
        }
        else if (switchOn == true) {
            tile.setIsConductive();
            setNeighbors(neighbors[0], true);
            setNeighbors(neighbors[1], true);
            setNeighbors(neighbors[2], true);
            setNeighbors(neighbors[3], true);
        }
        else {
            tile.setIsNotConductive();
            setNeighbors(neighbors[0], false);
            setNeighbors(neighbors[1], false);
            setNeighbors(neighbors[2], false);
            setNeighbors(neighbors[3], false);
        }
    }
    /**
     * setNeighbors        Benachbarte Tiles finden
     *
     * @param tile
     * */

    public static Tile[] getNeighbors(Tile tile) {
        Tile[] neighbors = new Tile[4];
        neighbors[0] = TileMap.map.get(new Point((int) ((tile.getY() - tile.y) / References.TILE_SIZE)+ References.TILE_SIZE, (int) (Math.floor((tile.getX() - tile.x) / References.TILE_SIZE))));
        neighbors[1] = TileMap.map.get(new Point((int) ((tile.getY() - tile.y) / References.TILE_SIZE)- References.TILE_SIZE, (int) (Math.floor((tile.getX() - tile.x) / References.TILE_SIZE))));
        neighbors[2] = TileMap.map.get(new Point((int) ((tile.getY() - tile.y) / References.TILE_SIZE), (int) (Math.floor((tile.getX() - tile.x) / References.TILE_SIZE)+ References.TILE_SIZE)));
        neighbors[3] = TileMap.map.get(new Point((int) ((tile.getY() - tile.y) / References.TILE_SIZE), (int) (Math.floor((tile.getX() - tile.x) / References.TILE_SIZE) - References.TILE_SIZE)));
        return neighbors;
    }

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
            this.setResistance();   // Setzt auch Namen
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
            isEatable = true;
        }
        //Traenke
        else if (this.texture == ResourceLoader.healthPotion)
        {
            this.resistance = 20;
            this.name = "Rottrank";
            isEatable = true;
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
        // Eis
        else if(this.texture == ResourceLoader.ice || this.texture == ResourceLoader.iceTop)
        {
            this.resistance = 10;
            this.name = "Eis";
        }

        // Erfahrungspunkte
        ep_bonus = resistance * 10;
    }

    /**
     * getEpBonus       Rueckgabe der Erfahrungspunkte
     * */
    public int getEpBonus() { return ep_bonus; }


}
