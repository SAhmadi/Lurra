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
    public int xTmp;
    public int yTmp;

    private int row;
    private int column;

    private BufferedImage texture;

    private boolean isCollidable;
    private boolean hasGravity;
    private boolean isDestructible;
    private boolean isEatable;
    private boolean hasEnergy;
    private boolean isConductive;

    private byte id;
    private byte resistance;
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

        setTexture(texture);    // Setzen der Texture, Wiederstand, ID

        this.row = row;
        this.column = column;

        this.isCollidable = isCollidable;
        this.hasGravity = hasGravity;
        this.isDestructible = isDestructible;
    }

    /**
     * render           Zeichnen des Tiles
     *
     * @param graphics  Graphics Objekt
     * @param xMap      x Position der Tilemap
     * @param yMap      y Position der Tilemap
     * */
    public void render(Graphics graphics, double xMap, double yMap)
    {
        x = (int) (xTmp + xMap);
        y = (int) (yTmp + yMap);

        if (texture != null) graphics.drawImage(texture, x, y, null);
    }

    // GETTER UND SETTER
    /**
     * getId            Rueckgabe der Tile-ID
     * @return          Tile ID
     * */
    public byte getId() { return this.id; }

    /**
     * setId            Setzen der Tile-ID
     * @param id        Tile ID
     * */
    public void setId(byte id) { this.id = id; }

    /**
     * getX             Rueckgabe der x-Position
     * @return          x Koordinate
     * */
    public int getX() { return this.x; }

    /**
     * setX             Setzen der x-Position
     * @param x         x-Position
     * */
    public void setX(int x) { this.x = x; }

    /**
     * getY             Rueckgabe der y-Position
     * @return          y Koordinate
     * */
    public int getY() { return this.y; }

    /**
     * setY             Setzen der y-Position
     * @param y         y-Position
     * */
    public void setY(int y) { this.y = y; }

    /**
     * getRow           Rueckgabe der Zeile
     * @return          Aktuelle Zeile
     * */
    public int getRow() { return this.row; }

    /**
     * setRow           Setzen der Zeile
     * @param row       Zeile
     * */
    public void setRow(int row) { this.row = row; }

    /**
     * getColumn        Rueckgabe der Spalte
     * @return          Aktuelle Spalte
     * */
    public int getColumn() { return this.column; }

    /**
     * setColumn        Setzen der Spalte
     * @param column    Spalte
     * */
    public void setColumn(int column) { this.column = column; }

    /**
     * getIsCollidable          Rueckgabe ob kollidierbar
     * @return                  Wert ob kollidierbar
     * */
    public boolean getIsCollidable() { return this.isCollidable; }

    /**
     * setIsCollidable          Setzen ob Tile kollidierbar ist
     * @param isCollidable      Wert ob Tile kollidierbar ist
     * */
    public void setIsCollidable(boolean isCollidable) { this.isCollidable = isCollidable; }

    /**
     * getHasGravity            Rueckgabe ob von der Schwerkraft angezogen wird
     * @return                  Wert ob von der Schwerkraft angezogen wird
     * */
    public boolean getHasGravity() { return this.hasGravity; }

    /**
     * setHasGravity            Setzen ob Tile von der Schwerkraft angezogen wird
     * @param hasGravity        Wert ob Tile von der Schwerkraft angezogen wird
     * */
    public void setHasGravity(boolean hasGravity) { this.hasGravity = hasGravity; }

    /**
     * getIsDestructible        Rueckgabe ob zerstoerbar
     * @return                  Wert ob zerstoerbar
     * */
    public boolean getIsDestructible() { return this.isDestructible; }

    /**
     * setIsDestructible        Setzen ob Tile zerstoerbar ist
     * @param isDestructible    Wert ob Tile zerstoerbar ist
     * */
    public void setIsDestructible(boolean isDestructible) { this.isDestructible = isDestructible; }

    /**
     * getHasEnergy             Rueckgabe ob Energietr�ger
     * @return boolean          Wert ob Energietr�ger
     * */
    public boolean getHasEnergy() { return this.hasEnergy; }

    /**
     * setHasEnergy             Setzen ob Tile Energietr�ger ist
     * @param hasEnergy         Wert ob Tile Energietr�ger ist
     * */
    public void setHasEnergy(boolean hasEnergy) { this.hasEnergy = hasEnergy; }

    /**
     * setIsConductive          Rueckgabe ob Tile leitet
     * @return                  Wert ob Tile leitet
     * */
    public boolean getIsConductive() { return this.isConductive; }

    /**
     * setIsConductive          Setzen ob Tile leitet
     * @param isConductive      Wert ob Tile leitet
     * */
    public void setIsConductive(boolean isConductive) { this.isConductive = isConductive; }

    /**
     * getIsEatable             Rueckgabe ob Tile essbar ist
     * @return                  Wert ob Tile essbar ist
     * */
    public boolean getIsEatable() { return this.isEatable; }

    /**
     * setIsEatable             Setzen ob Tile essbar ist
     * @param isEatable         Wert ob Tile essbar ist
     * */
    public void setIsEatable(boolean isEatable) { this.isEatable = isEatable; }

    /**
     * getNeighbors             Benachbarte Tiles finden
     * @param tile              Aktueller Tile
     * @param map               Tilemap
     * @return                  Benachbarten Tiles
     * */
    public static Tile[] getNeighbors(Tile tile, TileMap map)
    {
        Tile[] neighbors = new Tile[4];
        neighbors[0] = map.getMap().get(new Point(tile.getRow() - 1, tile.getColumn()));
        neighbors[1] = map.getMap().get(new Point(tile.getRow() + 1, tile.getColumn()));
        neighbors[2] = map.getMap().get(new Point(tile.getRow(), tile.getColumn() - 1));
        neighbors[3] = map.getMap().get(new Point(tile.getRow(), tile.getColumn() + 1));
        return neighbors;
    }

    /**
     * setNeighbors             Benachbarte Tiles festlegen
     * @param tile              Aktueller Tile
     * @param switchOn          Wert, ob geswitched werden soll
     * @param map               Tilemap
     * */
    public static void setNeighbors(Tile tile, boolean switchOn, TileMap map)
    {
        if (tile.getTexture() != ResourceLoader.bluerockOff && tile.getTexture() != ResourceLoader.bluerockOn) return;

        Tile[] neighbors = getNeighbors(tile, map);
        if (switchOn)
        {
            tile.setTexture(ResourceLoader.bluerockOn);
            for (int i = 0; i < 4; i++) {
                if (neighbors[i].getTexture() == ResourceLoader.bluerockOff) {
                    setNeighbors(neighbors[i], true, map);
                }
            }
        }
        else
        {
            tile.setTexture(ResourceLoader.bluerockOff);
            for (int i = 0; i < 4; i++) {
                if (neighbors[i].getTexture() == ResourceLoader.bluerockOn) {
                    setNeighbors(neighbors[i], false, map);
                }
            }
        }
    }

    /**
     * setNANDR                 Nach rechts gewandtes NAND anwenden
     * @param tile              Aktueller Tile
     * @param map               Tilemap
     * */
    public static void setNANDR(Tile tile, TileMap map)
    {
        Tile[] neighbors = getNeighbors(tile, map);
        if (neighbors[0].getTexture() == ResourceLoader.bluerockOn && neighbors[1].getTexture() == ResourceLoader.bluerockOn)
            setNeighbors(neighbors[2], false, map);
        else if (neighbors[0].getTexture() == ResourceLoader.bluerockOff && neighbors[1].getTexture() == ResourceLoader.bluerockOff)
            setNeighbors(neighbors[2], true, map);
        else if (neighbors[0].getTexture() == ResourceLoader.bluerockOn && neighbors[1].getTexture() == ResourceLoader.bluerockOff)
            setNeighbors(neighbors[2], true, map);
        else if (neighbors[0].getTexture() == ResourceLoader.bluerockOff && neighbors[1].getTexture() == ResourceLoader.bluerockOn)
            setNeighbors(neighbors[2], true, map);
    }
    /**
     * setNANDL                 Nach links gewandtes NAND anwenden
     * @param tile              Aktueller Tile
     * @param map               Tilemap
     * */
    public static void setNANDL(Tile tile, TileMap map)
    {
        Tile[] neighbors = getNeighbors(tile, map);
        if (neighbors[0].getTexture() == ResourceLoader.bluerockOn && neighbors[1].getTexture() == ResourceLoader.bluerockOn)
            setNeighbors(neighbors[3], false, map);
        else if (neighbors[0].getTexture() == ResourceLoader.bluerockOff && neighbors[1].getTexture() == ResourceLoader.bluerockOff)
            setNeighbors(neighbors[3], true, map);
        else if (neighbors[0].getTexture() == ResourceLoader.bluerockOn && neighbors[1].getTexture() == ResourceLoader.bluerockOff)
            setNeighbors(neighbors[3], true, map);
        else if (neighbors[0].getTexture() == ResourceLoader.bluerockOff && neighbors[1].getTexture() == ResourceLoader.bluerockOn)
            setNeighbors(neighbors[3], true, map);
    }

    /**
     * getTexture       Rueckgabe der Texture
     * @return          Texture
     * */
    public BufferedImage getTexture() { return this.texture; }

    /**
     * setTexture       Setzen der Texture
     * @param texture   Texture des Tiles
     * */
    public void setTexture(BufferedImage texture)
    {
        this.texture = texture;

        if(texture != null) this.initTiles();   // ID und Wiederstand setzen
        else
        {
            this.id = 0;
            this.resistance = 0;
            this.isDestructible = false;
            this.isCollidable = false;
            this.hasGravity = false;
        }
    }

    /**
     * getResistance    Rueckgabe des Wiederstands
     * @return          Wiederstand
     * */
    public int getResistance() { return this.resistance; }

    /**
     * setResistance        Setzen des Wiederstands
     * @param resistance    Wiederstand
     * */
    public void setResistance(byte resistance) { this.resistance = resistance; }

    /**
     * initTiles        Initialisieren der ID und des Wiederstandes
     * */
    private void initTiles()
    {
        // Baumstamm
        if (this.texture == ResourceLoader.treeTrunkRoot ||
                this.texture == ResourceLoader.treeTrunk ||
                this.texture == ResourceLoader.treeTrunkRight ||
                this.texture == ResourceLoader.treeTrunkLeft ||
                this.texture == ResourceLoader.treeTrunkTop ||
                this.texture == ResourceLoader.treeTrunkRootSnow ||
                this.texture == ResourceLoader.treeTrunkAfterRootSnow ||
                this.texture == ResourceLoader.treeTrunkSnow ||
                this.texture == ResourceLoader.treeTrunkSnowEnd)
        {
            this.id = References.WOOD;
            this.isDestructible = true;
            this.isCollidable = false;
            this.hasGravity = false;
            this.resistance = 10;
        }
        // Baumkrone
        else if (this.texture == ResourceLoader.leafStart ||
                this.texture == ResourceLoader.leaf ||
                this.texture == ResourceLoader.leafEnd ||
                this.texture == ResourceLoader.cactusRoot ||
                this.texture == ResourceLoader.cactus1 ||
                this.texture == ResourceLoader.cactus2 ||
                this.texture == ResourceLoader.cactusTop)
        {
            this.id = References.LEAF;
            this.isDestructible = true;
            this.isCollidable = false;
            this.hasGravity = false;
            this.resistance = 10;
        }
        else if (this.texture == ResourceLoader.woodenBoard)
        {
            this.id = References.WOODEN_BOARD;
            this.isDestructible = true;
            this.isCollidable = false;
            this.hasGravity = false;
        }
        // Wasser
        else if (this.texture == ResourceLoader.water ||
                this.texture == ResourceLoader.waterTop||
                this.texture == ResourceLoader.waterTop2)
        {
            this.id = References.WATER;
            this.isDestructible = false;
            this.isCollidable = false;
            this.hasGravity = false;
        }
        // Erde
        else if (this.texture == ResourceLoader.dirt ||
                this.texture == ResourceLoader.dirtMidDark ||
                this.texture == ResourceLoader.dirtDark)
        {
            this.id = References.DIRT;
            this.resistance = 7;
            this.isDestructible = true;
            this.isCollidable = true;
            this.hasGravity = true;
        }
        // Gras
        else if(this.texture == ResourceLoader.gras ||
                this.texture == ResourceLoader.grasWithFlower)
        {
            this.id = References.GRAS;
            this.resistance = 7;
            this.isDestructible = true;
            this.isCollidable = true;
            this.hasGravity = true;
        }
        // Sand
        else if (this.texture == ResourceLoader.sandTop ||
                this.texture == ResourceLoader.sand ||
                this.texture == ResourceLoader.sand2 ||
                this.texture == ResourceLoader.sand3)
        {
            this.id = References.SAND;
            this.resistance = 7;
            this.isDestructible = true;
            this.isCollidable = true;
            this.hasGravity = true;
        }
        // Stein Weiss
        else if (this.texture == ResourceLoader.stoneWhite1 ||
                this.texture == ResourceLoader.stoneWhite2 ||
                this.texture == ResourceLoader.stoneWhite3 ||
                this.texture == ResourceLoader.stoneWhiteLeafs ||
                this.texture == ResourceLoader.stoneWhiteGrass)
        {
            this.id = References.STONE;
            this.resistance = 15;
            this.isDestructible = true;
            this.isCollidable = true;
            this.hasGravity = true;
        }
        // Ziegelstein
        else if (this.texture == ResourceLoader.stoneRed1 ||
                this.texture == ResourceLoader.stoneRed2 ||
                this.texture == ResourceLoader.stoneRed3 ||
                this.texture == ResourceLoader.stoneRedLeafs)
        {
            this.id = References.STONE_RED;
            this.isDestructible = true;
            this.isCollidable = true;
            this.hasGravity = true;
        }
        // Kupfer
        else if (this.texture == ResourceLoader.copper || this.texture == ResourceLoader.copperIced)
        {
            this.id = References.COPPER;
            this.resistance = 10;
            this.isDestructible = true;
            this.isCollidable = true;
            this.hasGravity = true;
        }
        // Silber
        else if (this.texture == ResourceLoader.silver || this.texture == ResourceLoader.silverIced)
        {
            this.id = References.SILVER;
            this.resistance = 14;
            this.isDestructible = true;
            this.isCollidable = true;
            this.hasGravity = true;
        }
        // Gold
        else if (this.texture == ResourceLoader.gold || this.texture == ResourceLoader.goldIced)
        {
            this.id = References.GOLD;
            this.resistance = 18;
            this.isDestructible = true;
            this.isCollidable = true;
            this.hasGravity = true;
        }
        // Burger
        else if (this.texture == ResourceLoader.burger)
        {
            this.id = References.BURGER;
            this.resistance = 15;
            isEatable = true;
            this.isDestructible = true;
            this.isCollidable = true;
            this.hasGravity = true;
        }
        //Traenke
        else if (this.texture == ResourceLoader.healthPotion)
        {
            this.id = References.POTION;
            this.resistance = 20;
            isEatable = true;
            this.isDestructible = true;
            this.isCollidable = true;
            this.hasGravity = true;
        }
        // Eisen
        else if (this.texture == ResourceLoader.ion || this.texture == ResourceLoader.ionIced)
        {
            this.id = References.ION;
            this.resistance = 8;
            this.isDestructible = true;
            this.isCollidable = true;
            this.hasGravity = true;
        }
        // Rubin
        else if (this.texture == ResourceLoader.ruby || this.texture == ResourceLoader.rubyIced)
        {
            this.id = References.RUBY;
            this.resistance = 12;
            this.isDestructible = true;
            this.isCollidable = true;
            this.hasGravity = true;
        }
        // Saphire
        else if (this.texture == ResourceLoader.saphire || this.texture == ResourceLoader.saphireIced)
        {
            this.id = References.SAPHIRE;
            this.resistance = 12;
            this.isDestructible = true;
            this.isCollidable = true;
            this.hasGravity = true;
        }
        // Smaragd
        else if (this.texture == ResourceLoader.smaragd || this.texture == ResourceLoader.smaragdIced)
        {
            this.id = References.SMARAGD;
            this.resistance = 15;
            this.isDestructible = true;
            this.isCollidable = true;
            this.hasGravity = true;
        }
        // Diamant
        else if (this.texture == ResourceLoader.diamond || this.texture == ResourceLoader.diamondIced)
        {
            this.id = References.DIAMOND;
            this.resistance = 20;
            this.isDestructible = true;
            this.isCollidable = true;
            this.hasGravity = true;
        }
        // Eis
        else if (this.texture == ResourceLoader.ice || this.texture == ResourceLoader.ice2 || this.texture == ResourceLoader.ice3 || this.texture == ResourceLoader.iceTop)
        {
            this.id = References.ICE;
            this.resistance = 10;
            this.isDestructible = true;
            this.isCollidable = true;
            this.hasGravity = true;
        }
        // Schalter
        else if (this.texture == ResourceLoader.switchOff)
        {
            this.id = References.SWITCH_OFF;
            this.isDestructible = false;
            this.isCollidable = true;
            this.hasGravity = true;
        }
        else if (this.texture == ResourceLoader.switchOn)
        {
            this.id = References.SWITCH_ON;
            this.isDestructible = false;
            this.isCollidable = true;
            this.hasGravity = true;
        }
        // Gatter
        else if (this.texture == ResourceLoader.NANDL)
        {
            this.id = References.NANDL;
            this.isDestructible = false;
            this.isCollidable = true;
            this.hasGravity = true;
        }
        else if (this.texture == ResourceLoader.NANDR)
        {
            this.id = References.NANDR;
            this.isDestructible = false;
            this.isCollidable = true;
            this.hasGravity = true;
        }
        // Bluerock
        else if (this.texture == ResourceLoader.bluerockOn)
        {
            this.id = References.BLUEROCK_ON;
            this.isConductive = true;
            this.isDestructible = false;
            this.isCollidable = true;
            this.hasGravity = true;
        }
        else if (this.texture == ResourceLoader.bluerockOff)
        {
            this.id = References.BLUEROCK_OFF;
            this.isConductive = false;
            this.isDestructible = false;
            this.isCollidable = true;
            this.hasGravity = true;
        }
        // Batterie
        else if (this.texture == ResourceLoader.battery)
        {
            this.id = References.BATTERY;
            this.isDestructible = false;
            this.isCollidable = true;
            this.hasGravity = true;
        }

        // Erfahrungspunkte
        // ep_bonus = resistance * 10;
    }

    /**
     * getEpBonus       Rueckgabe der Erfahrungspunkte
     * @return          EP Bonus
     * */
    public int getEpBonus() { return ep_bonus; }

}
