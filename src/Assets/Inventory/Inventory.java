package Assets.Inventory;

import Assets.World.Tile;
import Main.References;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;

/**
 * Inventar des Spielers
 *
 * @author Sirat
 * */
public class Inventory
{

    // Inventar
    public static int inventoryLength = 10;
    public static int inventoryHeight = 3;  // tatsaechliche Hoehe = 4
    public static int inventoryCellSize = 48;
    public static int inventoryCellSpacing = 5;
    public static int inventoryBorderSpacing = 5;

    public static Cell[] invBar = new Cell[inventoryLength];
    public static Cell[] invDrawer = new Cell[inventoryLength * inventoryHeight];

    public static boolean isDrawerOpen = false;
    public static boolean isHolding = false;

    public static int selected = 0;
    public String holdingName = "null";
    public BufferedImage holdingTileImage;
    public int holdingCount;

    /**
     * Inventory        Konstruktor der Inventory-Klasse
     * */
    public Inventory()
    {
        // Inventar Leiste
        for(int i = 0; i < invBar.length; i++)
        {
            invBar[i] = new Cell(new Rectangle(
                    (References.SCREEN_WIDTH/2) - ((inventoryLength * (inventoryCellSize + inventoryCellSpacing))/2) + (i * (inventoryCellSize + inventoryCellSpacing)),
                    References.SCREEN_HEIGHT - (inventoryCellSize + inventoryBorderSpacing),
                    inventoryCellSize,
                    inventoryCellSize
            ));
        }

        // Inventar Drawer
        int x = 0, y = 0;
        for(int i = 0; i < invDrawer.length; i++)
        {
            invDrawer[i] = new Cell(new Rectangle(
                    (References.SCREEN_WIDTH/2) - ((inventoryLength * (inventoryCellSize + inventoryCellSpacing))/2) + (x * (inventoryCellSize + inventoryCellSpacing)),
                    References.SCREEN_HEIGHT - (inventoryCellSize + inventoryBorderSpacing) - (inventoryHeight * (inventoryCellSize + inventoryCellSpacing)) + (y * (inventoryCellSize + inventoryCellSpacing)),
                    inventoryCellSize,
                    inventoryCellSize
            ));

            x++;
            if(x == inventoryLength)
            {
                x = 0;
                y++;
            }
        }

        // Setzen der Standard Items
        invBar[0].name = "Picke";
        invBar[0].setTileImage();
        invBar[0].count = 1;

        invBar[1].name = "Axt";
        invBar[1].setTileImage();
        invBar[1].count = 1;

        invBar[2].name = "Hammer";
        invBar[2].setTileImage();
        invBar[2].count = 1;

        invBar[3].name = "Schleimpistole";
        invBar[3].setTileImage();
        invBar[3].count = 1;
    }

    /**
     * render       Zeichnen der Inventarleiste und Drawer
     * */
    public void render(Graphics g)
    {
        g.setColor(Color.BLACK);

        for(int i = 0; i < invBar.length; i++)
        {
            boolean isSelected = false;
            if(i == selected) isSelected = true;
            invBar[i].render(g, isSelected);
        }

        if(isDrawerOpen)
            for (Cell cell : invDrawer) { cell.render(g, false); }

        if(isHolding)
        {
            g.drawImage(
                    holdingTileImage,
                    References.MOUSE_X - References.TILE_SIZE / 2,
                    References.MOUSE_Y - References.TILE_SIZE / 2,
                    References.TILE_SIZE * 2,
                    References.TILE_SIZE * 2,
                    null
            );

            g.drawString(
                    Integer.toString(holdingCount),
                    References.MOUSE_X - References.TILE_SIZE / 2,
                    References.MOUSE_Y - References.TILE_SIZE / 2
            );
        }
    }

    /**
     * addToInventory       Tile zum Inventar hinzufuegen
     *
     * @param tile          Tile
     * */
    public static void addToInventory(Tile tile)
    {
        for (Cell cell : invBar)
        {
            if (tile.name.equals(cell.name))
            {
                cell.count++;
                return;
            }
            else if (cell.name.equals("null") && cell.tileImage == null && cell.count == 0)
            {
                cell.tileImage = tile.getTexture();
                cell.name = tile.name;
                cell.count = 1;
                return;
            }
        }
    }

    // KEYLISTENERS
    public void keyPressed(KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_C)
            isDrawerOpen = !isDrawerOpen;
    }

    public void mouseClicked(MouseEvent e)
    {
        if(e.getButton() == 1) // Linke Maustaste
        {
            if(isDrawerOpen)
            {
                // Leiste
                for (Cell cell : invBar)
                {
                    if (cell.contains(References.MOUSE_X, References.MOUSE_Y))
                    {
                        if (!cell.name.equals("null") && !isHolding)
                        {
                            holdingName = cell.name;
                            holdingTileImage = cell.tileImage;
                            holdingCount = cell.count;

                            cell.name = "null";
                            cell.setTileImage();
                            cell.count = 0;

                            isHolding = true;
                        }
                        else if (isHolding && cell.name.equals("null"))
                        {
                            cell.name = holdingName;
                            cell.tileImage = holdingTileImage;
                            cell.count = holdingCount;

                            isHolding = false;
                        }
                        else if (isHolding && !cell.name.equals("null"))
                        {
                            String tmpName = cell.name;
                            BufferedImage tmpImage = cell.tileImage;
                            int tmpCount = cell.count;

                            cell.name = holdingName;
                            cell.tileImage = holdingTileImage;
                            cell.count = holdingCount;

                            holdingName = tmpName;
                            holdingTileImage = tmpImage;
                            holdingCount = tmpCount;
                        }
                    }
                }

                // Drawer
                for (Cell cell : invDrawer)
                {
                    if (cell.contains(References.MOUSE_X, References.MOUSE_Y))
                    {
                        if (!cell.name.equals("null") && !isHolding)
                        {
                            holdingName = cell.name;
                            holdingTileImage = cell.tileImage;
                            holdingCount = cell.count;

                            cell.name = "null";
                            cell.setTileImage();
                            cell.count = 0;

                            isHolding = true;
                        }
                        else if (isHolding && cell.name.equals("null"))
                        {
                            cell.name = holdingName;
                            cell.tileImage = holdingTileImage;
                            cell.count = holdingCount;

                            isHolding = false;
                        }
                        else if (isHolding && !cell.name.equals("null"))
                        {
                            String tmpName = cell.name;
                            BufferedImage tmpImage = cell.tileImage;
                            int tmpCount = cell.count;

                            cell.name = holdingName;
                            cell.tileImage = holdingTileImage;
                            cell.count = holdingCount;

                            holdingName = tmpName;
                            holdingTileImage = tmpImage;
                            holdingCount = tmpCount;
                        }
                    }
                }
            }
        }
    }

    public void mouseWheelMoved(MouseWheelEvent e)
    {
        // Runter
        if(e.getWheelRotation() < 0)
        {
            if(selected > 0)
                selected--;
            else
                selected = inventoryLength - 1;
        }
        // Hoch
        else if(e.getWheelRotation() > 0)
        {
            if(selected < inventoryLength - 1)
                selected++; // nach Links
            else
                selected = 0;
        }
    }

}
