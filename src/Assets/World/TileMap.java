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
    private BufferedImage[] dirtTextures = {ResourceLoader.gras, ResourceLoader.grasWithFlower, ResourceLoader.dirt, ResourceLoader.dirtMidDark, ResourceLoader.dirtDark};
    private BufferedImage[] grasOnlyTextures = {ResourceLoader.gras, ResourceLoader.grasWithFlower};
    private BufferedImage[] gemsTexture = {
            ResourceLoader.gold, ResourceLoader.ion, ResourceLoader.silver, ResourceLoader.ruby,
            ResourceLoader.saphire, ResourceLoader.smaragd, ResourceLoader.diamond
    };


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
    private int seed;
    private static int colCounter = 0;
    private int startRow = References.SCREEN_HEIGHT/References.TILE_SIZE/2 + 3;
    private int rowTmp = startRow;

    /**
     * Konstruktor der Klasse TileMap
     * */
    public TileMap(int seed)
    {
        // Neue Spielwelt
        this.map = new HashMap<>();

        // Anzahl Spalten und Reihe zum Zeichnen
        this.numberOfColumnsToDraw = References.SCREEN_WIDTH / References.TILE_SIZE + this.puffer;
        this.numberOfRowsToDraw = References.SCREEN_HEIGHT / References.TILE_SIZE + this.puffer;

        // Seed festlegen
        this.seed = seed;
    }

    /**
     * update           Aktualisieren der Spielwelt
     * */
    public void update()
    {
        for (int column = columnOffset-puffer; column < columnOffset+numberOfColumnsToDraw; column++)
        {
            if (colCounter > seed)
                colCounter = 0;

            for (int row = rowOffset-puffer; row < rowOffset+numberOfRowsToDraw; row++)
            {
                if (map.get(new Point(row, column)) == null)
                {
                    if (colCounter == seed)
                    {
                        if (new Random().nextInt(10) > 5)
                        {
                            if (rowTmp > rowOffset+numberOfRowsToDraw)
                                rowTmp -= 5;
                            else
                                rowTmp += 2;
                        }
                        else
                        {
                            if (rowTmp < rowOffset)
                                rowTmp += 5;
                            else
                                rowTmp -= 2;
                        }
                    }

                    if (row > rowTmp && row > (startRow - rowTmp/2))
                    {
                        if (map.get(new Point(row-1, column)).getTexture() == null) // Gras
                        {
                                map.put(new Point(row, column), new Tile(grasOnlyTextures[new Random().nextInt(grasOnlyTextures.length)], (column)*References.TILE_SIZE, (row)*References.TILE_SIZE, row, column, true, true, true));
                        }
                        else
                        {
                            int rand = new Random().nextInt(800); // Edelsteine
                            if (rand > 797)
                            {
                                map.put(new Point(row, column), new Tile(ResourceLoader.ion, (column)*References.TILE_SIZE, (row)*References.TILE_SIZE, row, column, true, true, true));
                            }
                            else if (rand > 794 && rand < 797)
                            {
                                map.put(new Point(row, column), new Tile(ResourceLoader.copper, (column)*References.TILE_SIZE, (row)*References.TILE_SIZE, row, column, true, true, true));
                            }
                            else if (rand > 791 && rand < 794)
                            {
                                map.put(new Point(row, column), new Tile(ResourceLoader.silver, (column)*References.TILE_SIZE, (row)*References.TILE_SIZE, row, column, true, true, true));
                            }
                            else if (rand == 450)
                            {
                                map.put(new Point(row, column), new Tile(ResourceLoader.gold, (column)*References.TILE_SIZE, (row)*References.TILE_SIZE, row, column, true, true, true));
                            }
                            else if (rand == 449)
                            {
                                map.put(new Point(row, column), new Tile(ResourceLoader.ruby, (column)*References.TILE_SIZE, (row)*References.TILE_SIZE, row, column, true, true, true));
                            }
                            else if (rand == 448)
                            {
                                map.put(new Point(row, column), new Tile(ResourceLoader.saphire, (column)*References.TILE_SIZE, (row)*References.TILE_SIZE, row, column, true, true, true));
                            }
                            else if (rand == 447)
                            {
                                map.put(new Point(row, column), new Tile(ResourceLoader.smaragd, (column)*References.TILE_SIZE, (row)*References.TILE_SIZE, row, column, true, true, true));
                            }
                            else if (rand == 446)
                            {
                                map.put(new Point(row, column), new Tile(ResourceLoader.diamond, (column)*References.TILE_SIZE, (row)*References.TILE_SIZE, row, column, true, true, true));
                            }
                            else // Erde und Gras
                            {
                                map.put(new Point(row, column), new Tile(dirtTextures[new Random().nextInt(dirtTextures.length)], (column)*References.TILE_SIZE, (row)*References.TILE_SIZE, row, column, true, true, true));
                            }
                        }
                    }
                    else
                    {
                        map.put(new Point(row, column), new Tile(null, column*References.TILE_SIZE, row*References.TILE_SIZE, row, column, false, false, false));
                    }
                }
            }

            // Baum
            for (int row = rowOffset-puffer; row < rowOffset+numberOfRowsToDraw; row++)
            {
                if (row > rowTmp && row > (startRow - rowTmp/2))
                {
                    for (int i = 1; i <= 8; i++)
                    {
                        for (int j = 1; j <= 8; j++)
                        {
                            if (map.get(new Point(row-i, column+j)).getTexture() == null)
                            {
                                map.put(new Point(row, column), new Tile(ResourceLoader.saphire, (column)*References.TILE_SIZE, (row)*References.TILE_SIZE, row, column, true, true, true));
                            }
                        }
                    }
                }
            }
            colCounter++;
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

            map.get(new Point((int) ((e.getY()-this.y)/References.TILE_SIZE), (int) ((e.getX()-this.x)/References.TILE_SIZE))).setTexture(null);
            map.get(new Point((int) ((e.getY()-this.y)/References.TILE_SIZE), (int) ((e.getX()-this.x)/References.TILE_SIZE))).setIsCollidable(false);
            map.get(new Point((int) ((e.getY()-this.y)/References.TILE_SIZE), (int) ((e.getX()-this.x)/References.TILE_SIZE))).setHasGravity(false);
            map.get(new Point((int) ((e.getY()-this.y)/References.TILE_SIZE), (int) ((e.getX()-this.x)/References.TILE_SIZE))).setIsDestructible(false);
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
