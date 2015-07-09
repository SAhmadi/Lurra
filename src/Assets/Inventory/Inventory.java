package Assets.Inventory;

import Assets.Crafting.Crafting;
import Assets.World.Tile;
import Main.References;
import Main.Tutorial;

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
    public static String holdingName = "null";
    public BufferedImage holdingTileImage;
    public int holdingCount = 0;

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
            if (tile.name.equals(cell.name)) { cell.count++; return; }
        }

        for (Cell cell : invDrawer)
        {
            if (tile.name.equals(cell.name)) { cell.count++; return;}
        }

        // Falls nicht im Inventarleiste oder Drawer vorhanden
        for (Cell cell : invBar)
        {
            // Suche erste leere Zelle
            if (cell.name.equals("null") && cell.tileImage == null && cell.count == 0)
            {
                cell.tileImage = tile.getTexture();
                cell.name = tile.name;
                cell.count = 1;
                return;
            }
        }

        for (Cell cell : invDrawer)
        {
            // Suche erste leere Zelle
            if (cell.name.equals("null") && cell.tileImage == null && cell.count == 0)
            {
                cell.tileImage = tile.getTexture();
                cell.name = tile.name;
                cell.count = 1;
                return;
            }
        }
    }

    /**
     * removeFromInventory      Tile entfernen
     *
     * @param tile              Tile
     * */
    public static void removeFromInventory(Tile tile)
    {
        for (Cell cell : invBar)
        {
            if (tile.name.equals(cell.name))
            {
                if (cell.count > 1) cell.count--;
                else
                {
                    cell.count = 0;
                    cell.name = "null";
                    cell.tileImage = null;
                }
                break;
            }
        }
    }

    /**
     * checkInvBarMouseClick    Pruefe ob mit Inventarleiste interagiert wird
     *
     * @param e                 Mausevent mit BUTTON1 = linke Maustaste und BUTTON3 = rechte Maustaste
     * */
    public void checkInvBarMouseClick(MouseEvent e)
    {
        for (Cell cell : invBar)
        {
            if (cell.contains(References.MOUSE_X, References.MOUSE_Y))
            {
                if (!cell.name.equals("null") && !isHolding)    // Falls eine Zelle aufgehoben wird
                {
                    if (e.getButton() == MouseEvent.BUTTON1)  // Bewege nur ein einziges Tile
                    {
                        holdingName = cell.name;
                        holdingTileImage = cell.tileImage;
                        holdingCount = 1;

                        if (cell.count == 1)    // Falls nur ein Tile vorhanden
                        {
                            cell.name = "null";
                            cell.setTileImage();
                            cell.count = 0;
                        }
                        else if (cell.count > 1)    // Falls mehr als ein Tile vorhanden, hebe nur eins auf
                            cell.count--;
                    }

                    if (e.getButton() == MouseEvent.BUTTON3) // Bewege gesamte Anzahl an Tiles
                    {
                        holdingName = cell.name;
                        holdingTileImage = cell.tileImage;
                        holdingCount = cell.count;

                        cell.name = "null";
                        cell.setTileImage();
                        cell.count = 0;
                    }

                    isHolding = true;
                }
                else if (isHolding && cell.name.equals("null")) // Falls eine aufgehobene Zelle in einer leeren abgelegt wird
                {
                    if (e.getButton() == MouseEvent.BUTTON3)
                    {
                        boolean canPlace = true;
                        for (Cell invBarCell : invBar)  // Pruefe ob bereits in Inventarleiste vorhanden
                        {
                            if (invBarCell.name.equals(holdingName)) { canPlace = false; break; }
                        }

                        if (canPlace)   // Falls in Inventarleiste nicht gefunden wurde, pruefe ob bereits in Inventardrawer vorhanden
                            for (Cell invDrawerCell : invDrawer)
                            {
                                if (invDrawerCell.name.equals(holdingName)) { canPlace = false; break; }
                            }

                        if (canPlace)
                        {
                            cell.name = holdingName;
                            cell.tileImage = holdingTileImage;
                            cell.count = holdingCount;
                            isHolding = false;
                        }
                    }
                }
                else if (isHolding && !cell.name.equals("null"))    // Falls eine aufgehobene Zelle in einer bereits benuzten abgelegt wird
                {
                    if (e.getButton() == MouseEvent.BUTTON1 && holdingName.equals(cell.name))   // Falls ein Tile aufgehoben wurde und wieder in seiner eigenen Zelle gelegt wird
                    {
                        cell.count++;
                        isHolding = false;
                    }

                    if (e.getButton() == MouseEvent.BUTTON3)    // Vertauschen
                    {
                        if (holdingCount == 1)
                        {
                            boolean canPlace = true;
                            for (Cell invBarCell : invBar)  // Pruefe ob bereits in Inventarleiste vorhanden
                            {
                                if (invBarCell.name.equals(holdingName)) { canPlace = false; break; }
                            }

                            if (canPlace)   // Falls in Inventarleiste nicht gefunden wurde, pruefe ob bereits in Inventardrawer vorhanden
                                for (Cell invDrawerCell : invDrawer)
                                {
                                    if (invDrawerCell.name.equals(holdingName)) { canPlace = false; break; }
                                }

                            if (canPlace)
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
                        else
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

    /**
     * checkInvDrawerMouseClick     Pruefe ob mit Inventardrawer interagiert wird
     *
     * @param e                     Mausevent mit BUTTON1 = linke Maustaste und BUTTON3 = rechte Maustaste
     * */
    public void checkInvDrawerMouseClick(MouseEvent e)
    {
        for (Cell cell : invDrawer)
        {
            if (cell.contains(References.MOUSE_X, References.MOUSE_Y))
            {
                if (!cell.name.equals("null") && !isHolding)    // Falls eine Zelle aufgehoben wird
                {
                    if (e.getButton() == MouseEvent.BUTTON1)  // Bewege nur ein einziges Tile
                    {
                        holdingName = cell.name;
                        holdingTileImage = cell.tileImage;
                        holdingCount = 1;

                        if (cell.count == 1)    // Falls nur ein Tile vorhanden
                        {
                            cell.name = "null";
                            cell.setTileImage();
                            cell.count = 0;
                        }
                        else if (cell.count > 1)    // Falls mehr als ein Tile vorhanden, hebe nur eins auf
                            cell.count--;
                    }

                    if (e.getButton() == MouseEvent.BUTTON3) // Bewege gesamte Anzahl an Tiles
                    {
                        holdingName = cell.name;
                        holdingTileImage = cell.tileImage;
                        holdingCount = cell.count;

                        cell.name = "null";
                        cell.setTileImage();
                        cell.count = 0;
                    }

                    isHolding = true;
                }
                else if (isHolding && cell.name.equals("null")) // Falls eine aufgehobene Zelle in einer leeren abgelegt wird
                {
                    if (e.getButton() == MouseEvent.BUTTON3)
                    {
                        boolean canPlace = true;
                        for (Cell invBarCell : invBar)  // Pruefe ob bereits in Inventarleiste vorhanden
                        {
                            if (invBarCell.name.equals(holdingName)) { canPlace = false; break; }
                        }

                        if (canPlace)   // Falls in Inventarleiste nicht gefunden wurde, pruefe ob bereits in Inventardrawer vorhanden
                            for (Cell invDrawerCell : invDrawer)
                            {
                                if (invDrawerCell.name.equals(holdingName)) { canPlace = false; break; }
                            }

                        if (canPlace)
                        {
                            cell.name = holdingName;
                            cell.tileImage = holdingTileImage;
                            cell.count = holdingCount;
                            isHolding = false;
                        }
                    }
                }
                else if (isHolding && !cell.name.equals("null"))    // Falls eine aufgehobene Zelle in einer bereits benuzten abgelegt wird
                {
                    if (e.getButton() == MouseEvent.BUTTON1 && holdingName.equals(cell.name))   // Falls ein Tile aufgehoben wurde und wieder in seiner eigenen Zelle gelegt wird
                    {
                        cell.count++;
                        isHolding = false;
                    }

                    if (e.getButton() == MouseEvent.BUTTON3)    // Vertauschen
                    {
                        if (holdingCount == 1)
                        {
                            boolean canPlace = true;
                            for (Cell invBarCell : invBar)  // Pruefe ob bereits in Inventarleiste vorhanden
                            {
                                if (invBarCell.name.equals(holdingName)) { canPlace = false; break; }
                            }

                            if (canPlace)   // Falls in Inventarleiste nicht gefunden wurde, pruefe ob bereits in Inventardrawer vorhanden
                                for (Cell invDrawerCell : invDrawer)
                                {
                                    if (invDrawerCell.name.equals(holdingName)) { canPlace = false; break; }
                                }

                            if (canPlace)
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
                        else
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

    // KEYLISTENERS
    public void keyPressed(KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_C)
            isDrawerOpen = !isDrawerOpen;
    }

    public void mouseClicked(MouseEvent e)
    {
        if(e.getButton() == MouseEvent.BUTTON1 || e.getButton() == MouseEvent.BUTTON3) // Linke oder Rechte Maustaste
        {
            if(isDrawerOpen && !Crafting.isCraftBenchOpen)
            {
                checkInvBarMouseClick(e);   // Leiste
                checkInvDrawerMouseClick(e);    // Drawer
            }
        }
    }

    public void mouseWheelMoved(MouseWheelEvent e)
    {
        // Runter
        if(e.getWheelRotation() < 0)
        {
            Tutorial.solveTut(Tutorial.TUT_SCROLL);

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
