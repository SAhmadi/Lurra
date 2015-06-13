package Assets.World;

import Main.References;
import Main.ResourceLoader;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Erstellen der Spielwelt.
 * Positionieren, Scrollen und weiteres der Tiles.
 *
 * @author Sirat
 * */
public class TileMap
{
    private BufferedImage[] textures = {ResourceLoader.grasTile, ResourceLoader.dirt};

    /* Map */
    private double x;
    private double y;
    private Map<Point, Tile> map;
    private String mapFilePath;        // Map Speicher-Pfad

    private int columnOffset;           // Offsets
    private int rowOffset;

    private int numberOfColumnsToDraw;  // Anzahl der zuzeichnenden Tiles
    private int numberOfRowsToDraw;
    private int puffer = 2;             // Puffer



    /**
     * Konstruktor der Klasse TileMap
     * */
    public TileMap()
    {
        // Neue Spielwelt
        this.map = new HashMap<>();

        // Anzahl Spalten und Reihe zum Zeichnen
        this.numberOfColumnsToDraw = References.SCREEN_WIDTH / References.TILE_SIZE + this.puffer;
        this.numberOfRowsToDraw = References.SCREEN_HEIGHT / References.TILE_SIZE + this.puffer;
    }

    /**
     * update           Aktualisieren der Spielwelt
     * */
    public void update()
    {
        for (int row = rowOffset-puffer; row < rowOffset+numberOfRowsToDraw; row++)
        {
            for (int column = columnOffset-puffer; column < columnOffset+numberOfColumnsToDraw; column++)
            {
                try
                {
                    if (row > 30 && map.get(new Point(row, column)) == null)
                    {
                        map.put(new Point(row, column), new Tile(textures[new Random().nextInt(textures.length)], column*References.TILE_SIZE, row*References.TILE_SIZE, row, column, true, true, true));
                    }
                    else if(map.get(new Point(row, column)) == null)
                    {
                        map.put(new Point(row, column), new Tile(null, column * References.TILE_SIZE, row * References.TILE_SIZE, row, column, false, false, false));
                    }
                }
                catch (NullPointerException ignored) {}
            }
        }
    }

    /**
     * render           Zeichnen der Spielwelt
     *
     * @param graphics  Graphics Objekt
     * */
    public void render(Graphics graphics)
    {
        for (int row = rowOffset-puffer; row < rowOffset+numberOfRowsToDraw; row++)
        {
            for (int column = columnOffset-puffer; column < columnOffset+numberOfColumnsToDraw; column++)
            {
                try
                {
                    map.get(new Point(row, column)).render(graphics, getX(), getY());
                }
                catch (NullPointerException ignored) {}
            }
        }
    }

    /**
     *
     * */
    public String toString()
    {
        String ret = "x = " + x + "; y = " + y
                + "; rowOffset = " + rowOffset+ "; columnOffset = " + columnOffset
                + "; numberOfRowsToDraw = " + numberOfRowsToDraw + "; numberOfColumnsToDraw = " + numberOfColumnsToDraw
                + "; puffer = " + puffer
                + "\n map = " + map.toString();

        return ret;
    }

    // GETTER UND SETTER
    /**
     * setPosition              Setzen der Map Position, sowie des Offsets
     *
     * @param x                 x-Koordinate
     * @param y                 y-Koordinate
     * */
    public void setPosition(double x, double y)
    {
        this.x = x;
        this.y = y;

        this.columnOffset = (int) (-this.x / References.TILE_SIZE);
        this.rowOffset = (int) (-this.y / References.TILE_SIZE);
    }

    /**
     * getX                     Rueckgabe der Map x-Koordinate
     *
     * @return int              x-Koordinate
     * */
    public double getX() { return this.x; }

    /**
     * getY                     Rueckgabe der Map y-Koordinate
     *
     * @return int              y-Koordinate
     * */
    public double getY() { return this.y; }

    /**
     * getMap                   Rueckgabe der Map
     *
     * @return Map<>            Spielwelt HashMap<Point/Tile>
     * */
    public Map<Point, Tile> getMap() { return this.map; }

    /**
     * getRowOffset             Rueckgabe des Zeilen-Offsets
     *
     * @return int              Zeilen Offset
     * */
    public int getRowOffset() { return this.rowOffset; }

    /**
     * getColumnOffset          Rueckgabe des Spalten-Offsets
     *
     * @return int              Spalten Offset
     * */
    public int getColumnOffset() { return this.columnOffset; }

    /**
     * getNumberOfRowsToDraw        Rueckgabe der Anzahl zuzeichnender Zeilen-Tiles
     *
     * @return int                  Anzahl der Zeilen Tiles
     * */
    public int getNumberOfRowsToDraw() { return this.numberOfRowsToDraw; }

    /**
     * getNumberOfColumnsToDraw     Rueckgabe der Anzahl zuzeichnender Spalten-Tiles
     *
     * @return int                  Anzahl der Spalten Tiles
     * */
    public int getNumberOfColumnsToDraw() { return this.numberOfColumnsToDraw; }

    /**
     * getPuffer                Rueckgabe des Puffers
     *
     * @return int              Puffer
     * */
    public int getPuffer() { return this.puffer; }


    // LISTENER
    /**
     * mouseClicked             Maus-Klick Event
     *
     * @param e                 MouseEvent Objekt
     * */
    public void mouseClicked(MouseEvent e)
    {
        if (map.get(new Point((int) ((e.getY()-this.y)/References.TILE_SIZE), (int) ((e.getX()-this.x)/References.TILE_SIZE))).getTexture() != null
                && map.get(new Point((int) ((e.getY()-this.y)/References.TILE_SIZE), (int) ((e.getX()-this.x)/References.TILE_SIZE))).getIsDestructible()) {

            map.get(new Point((int) ((e.getY()-this.y)/References.TILE_SIZE), (int) ((e.getX()-this.x)/References.TILE_SIZE))).setTexture(ResourceLoader.gold);
        }
    }

    /**
     * mouseMoved               Speichern der aktuellen Mausposition
     *
     * @param e                 MouseEvent Objekt
     * */
    public void mouseMoved(MouseEvent e)
    {
        References.MOUSE_X = e.getX();
        References.MOUSE_Y = e.getY();
    }

}
