package Assets.World;

import Main.ResourceLoader;
import Main.ScreenDimensions;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 *
 * */
public class TileMap
{

    /*
    * TileMap
    * */
    private double x = 0;
    private double y = 0;

    private BufferedImage[] textures = {ResourceLoader.grasTile, ResourceLoader.dirt};

    // Anzahl der Spalten und Reihen

    private int puffer = 2;

    // Offset
    private int columnOffset = 0, rowOffset = 0;
    private int numberOfColumnsToDraw = 0, numberOfRowsToDraw = 0;

    /*
    * Tiles
    * */
    public Map<Pair, Tile> map;


    // Map - Pfad
    private String mapFilePath = "";

    // MouseClicked - Variablen
    private Rectangle selectedTileBounds = null;



    // MausPosition
    public static int mouseX = 0, mouseY = 0;


    /**
     *
     * */
    public TileMap()
    {
        this.map = new HashMap<>();

        // Anzahl Spalten und Reihe zum Zeichnen
        this.numberOfColumnsToDraw = ScreenDimensions.WIDTH / Tile.WIDTH + this.puffer;
        this.numberOfRowsToDraw = ScreenDimensions.HEIGHT / Tile.HEIGHT + this.puffer;

    }


    /**
     *
     * */
    public void update()
    {

        for(int row = rowOffset; row < rowOffset+numberOfRowsToDraw; row++)
        {
            for(int column = columnOffset; column < columnOffset+numberOfColumnsToDraw; column++)
            {
                try
                {
                    if (row > 30 && map.get(new Pair(row, column)) == null)
                    {
                        map.put(new Pair(row, column), new Tile(textures[new Random().nextInt(textures.length)], column * Tile.WIDTH, row * Tile.HEIGHT, row, column, true, true, true));
                    }
                }
                catch (NullPointerException ex)
                {
                    //ex.printStackTrace();
                }
            }
        }

    }

    /**
     *
     * */
    public void render(Graphics graphics)
    {

        for(int row = rowOffset; row < rowOffset+numberOfRowsToDraw; row++)
        {
            for(int column = columnOffset; column < columnOffset+numberOfColumnsToDraw; column++)
            {
                try
                {
                    map.get(new Pair(row, column)).render(graphics, getX(), getY());
                }
                catch (NullPointerException ex)
                {
                    //ex.printStackTrace();
                }
            }
        }

        System.out.println(rowOffset + " | " + columnOffset);

    }


    /**
     *
     * */


    /*
    * Getter and Setter
    * */
    public double getX() { return this.x; }
    public double getY() { return this.y; }

    public void setPosition(double x, double y)
    {
        this.x = x;
        this.y = y;

        this.columnOffset = (int)-this.x / Tile.WIDTH;
        this.rowOffset = (int)-this.y / Tile.HEIGHT;
    }

    /*
    * EventListener
    * */
    public void mouseClicked(MouseEvent e)
    {}

    public void mouseMoved(MouseEvent e)
    {
        mouseX = e.getX();
        mouseY = e.getY();
    }

}
