package Assets.Inventory;

import Assets.Crafting.Crafting;
import GameSaves.InventoryData.InventoryData;
import GameSaves.InventoryData.InventoryDataLoad;
import GameSaves.PlayerData.PlayerData;
import Main.References;
import Main.ResourceLoader;
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
 * @version 0.9
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

    private byte holdingID;
    private BufferedImage holdingTileImage;
    private int holdingCount;

    /**
     * Inventory                Konstruktor der Inventory-Klasse
     *
     * @param continueLevel     Wert ob aus Spielstand geladen werden soll
     * */
    public Inventory(boolean continueLevel)
    {
        // Inventar Leiste
        for (int i = 0; i < invBar.length; i++)
        {
            invBar[i] = new Cell(new Rectangle(
                    (References.SCREEN_WIDTH / 2) - ((inventoryLength * (inventoryCellSize + inventoryCellSpacing)) / 2) + (i * (inventoryCellSize + inventoryCellSpacing)),
                    References.SCREEN_HEIGHT - (inventoryCellSize + inventoryBorderSpacing),
                    inventoryCellSize,
                    inventoryCellSize
            ));
        }

        // Inventar Drawer
        int x = 0, y = 0;
        for (int i = 0; i < invDrawer.length; i++)
        {
            invDrawer[i] = new Cell(new Rectangle(
                    (References.SCREEN_WIDTH / 2) - ((inventoryLength * (inventoryCellSize + inventoryCellSpacing)) / 2) + (x * (inventoryCellSize + inventoryCellSpacing)),
                    References.SCREEN_HEIGHT - (inventoryCellSize + inventoryBorderSpacing) - (inventoryHeight * (inventoryCellSize + inventoryCellSpacing)) + (y * (inventoryCellSize + inventoryCellSpacing)),
                    inventoryCellSize,
                    inventoryCellSize
            ));

            x++;
            if (x == inventoryLength)
            {
                x = 0;
                y++;
            }
        }

        if (continueLevel)
        {
            InventoryDataLoad.XMLRead(PlayerData.name);
            invBar = InventoryData.invBar;
            invDrawer = InventoryData.invDrawer;
        }
        else
        {
            // Setzen der Standard Items
            initStandardInventory();
        }

    }

    /**
     * initStandardInventory        Initialisieren der Standard Items im Inventar
     * */
    private void initStandardInventory()
    {
        // Picke
        invBar[0].setId(References.PICK);
        invBar[0].setTileImage(ResourceLoader.stonePick);
        invBar[0].setCount(1);
    }

    /**
     * render       Zeichnen der Inventarleiste und Drawer
     *
     * @param g     Graphics Objekt
     * */
    public void render(Graphics2D g)
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
     * addToInventory       Craftingobjekt zum Inventar hinzufuegen
     *
     * @param id            ID
     * */
    public static void addToInventory(byte id)
    {
        for (Cell cell : invBar)
        {
            if (id == cell.getId())
            {
                cell.setCount(cell.getCount() + 1);
                return;
            }
        }

        for (Cell cell : invDrawer)
        {
            if (id == cell.getId())
            {
                cell.setCount(cell.getCount() + 1);
                return;
            }
        }

        // Falls nicht im Inventarleiste oder Drawer vorhanden
        for (Cell cell : invBar)
        {
            // Suche erste leere Zelle
            if (cell.getId() == 0 && cell.getTileImage() == null && cell.getCount() < 1)
            {
                cell.setId(id);
                cell.setTileImage(References.getTextureById(id));
                cell.setCount(1);
                return;
            }
        }

        for (Cell cell : invDrawer)
        {
            // Suche erste leere Zelle
            if (cell.getId() == 0 && cell.getTileImage() == null && cell.getCount() < 1)
            {
                cell.setId(id);
                cell.setTileImage(References.getTextureById(id));
                cell.setCount(1);
                return;
            }
        }
    }

    /**
     * removeFromInventory      Tile entfernen
     *
     * @param id                ID
     * */
    public static void removeFromInventory(byte id) {
        for (Cell cell : invBar) {
            if (cell.getId() == id) {
                if (cell.getCount() > 1) {
                    cell.setCount(cell.getCount() - 1);
                }
                else {
                    cell.setId((byte) 0);
                    cell.setTileImage(null);
                    cell.setCount(0);
                }
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
                if (cell.getId() != 0 && !isHolding)    // Falls eine Zelle aufgehoben wird
                {
                    holdingID = cell.getId();
                    holdingTileImage = cell.getTileImage();
                    holdingCount = cell.getCount();

                    cell.setId((byte) 0);
                    cell.setTileImage(null);
                    cell.setCount(0);

                    isHolding = true;
                }
                else if (isHolding && cell.getId() == 0) // Falls eine aufgehobene Zelle in einer leeren abgelegt wird
                {
                    boolean canPlace = true;
                    for (Cell invBarCell : invBar)  // Pruefe ob bereits in Inventarleiste vorhanden
                    {
                        if (invBarCell.getId() == holdingID) { canPlace = false; break; }
                    }

                    if (canPlace)   // Falls in Inventarleiste nicht gefunden wurde, pruefe ob bereits in Inventardrawer vorhanden
                        for (Cell invDrawerCell : invDrawer)
                        {
                            if (invDrawerCell.getId() == holdingID) { canPlace = false; break; }
                        }

                    if (canPlace)
                    {
                        cell.setId(holdingID);
                        cell.setTileImage(holdingTileImage);
                        cell.setCount(holdingCount);
                        isHolding = false;
                    }
                }
                else if (isHolding && cell.getId() != 0)    // Falls eine aufgehobene Zelle in einer bereits benuzten abgelegt wird
                {
                    if (holdingCount == 1)
                    {
                        boolean canPlace = true;
                        for (Cell invBarCell : invBar)  // Pruefe ob bereits in Inventarleiste vorhanden
                        {
                            if (invBarCell.getId() == holdingID) { canPlace = false; break; }
                        }

                        if (canPlace)   // Falls in Inventarleiste nicht gefunden wurde, pruefe ob bereits in Inventardrawer vorhanden
                            for (Cell invDrawerCell : invDrawer)
                            {
                                if (invDrawerCell.getId() == holdingID) { canPlace = false; break; }
                            }

                        if (canPlace)
                        {
                            byte tmpId = cell.getId();
                            BufferedImage tmpImage = cell.getTileImage();
                            int tmpCount = cell.getCount();

                            cell.setId(holdingID);
                            cell.setTileImage(holdingTileImage);
                            cell.setCount(holdingCount);

                            holdingID = tmpId;
                            holdingTileImage = tmpImage;
                            holdingCount = tmpCount;
                        }
                    }
                    else
                    {
                        byte tmpId = cell.getId();
                        BufferedImage tmpImage = cell.getTileImage();
                        int tmpCount = cell.getCount();

                        cell.setId(holdingID);
                        cell.setTileImage(holdingTileImage);
                        cell.setCount(holdingCount);

                        holdingID = tmpId;
                        holdingTileImage = tmpImage;
                        holdingCount = tmpCount;
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
                if (cell.getId() != 0 && !isHolding)    // Falls eine Zelle aufgehoben wird
                {
                    holdingID = cell.getId();
                    holdingTileImage = cell.getTileImage();
                    holdingCount = cell.getCount();

                    cell.setId((byte) 0);
                    cell.setTileImage(null);
                    cell.setCount(0);

                    isHolding = true;
                }
                else if (isHolding && cell.getId() == 0) // Falls eine aufgehobene Zelle in einer leeren abgelegt wird
                {
                    boolean canPlace = true;
                    for (Cell invBarCell : invBar)  // Pruefe ob bereits in Inventarleiste vorhanden
                    {
                        if (invBarCell.getId() == holdingID) { canPlace = false; break; }
                    }

                    if (canPlace)   // Falls in Inventarleiste nicht gefunden wurde, pruefe ob bereits in Inventardrawer vorhanden
                        for (Cell invDrawerCell : invDrawer)
                        {
                            if (invDrawerCell.getId() == holdingID) { canPlace = false; break; }
                        }

                    if (canPlace)
                    {
                        cell.setId(holdingID);
                        cell.setTileImage(holdingTileImage);
                        cell.setCount(holdingCount);
                        isHolding = false;
                    }
                }
                else if (isHolding && cell.getId() != 0)    // Falls eine aufgehobene Zelle in einer bereits benuzten abgelegt wird
                {
                    if (holdingCount == 1)
                    {
                        boolean canPlace = true;
                        for (Cell invBarCell : invBar)  // Pruefe ob bereits in Inventarleiste vorhanden
                        {
                            if (invBarCell.getId() == holdingID) { canPlace = false; break; }
                        }

                        if (canPlace)   // Falls in Inventarleiste nicht gefunden wurde, pruefe ob bereits in Inventardrawer vorhanden
                            for (Cell invDrawerCell : invDrawer)
                            {
                                if (invDrawerCell.getId() == holdingID) { canPlace = false; break; }
                            }

                        if (canPlace)
                        {
                            byte tmpId = cell.getId();
                            BufferedImage tmpImage = cell.getTileImage();
                            int tmpCount = cell.getCount();

                            cell.setId(holdingID);
                            cell.setTileImage(holdingTileImage);
                            cell.setCount(holdingCount);

                            holdingID = tmpId;
                            holdingTileImage = tmpImage;
                            holdingCount = tmpCount;
                        }
                    }
                    else
                    {
                        byte tmpId = cell.getId();
                        BufferedImage tmpImage = cell.getTileImage();
                        int tmpCount = cell.getCount();

                        cell.setId(holdingID);
                        cell.setTileImage(holdingTileImage);
                        cell.setCount(holdingCount);

                        holdingID = tmpId;
                        holdingTileImage = tmpImage;
                        holdingCount = tmpCount;
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
        if(isDrawerOpen && !Crafting.isCraftBenchOpen)
        {
            if(e.getButton() == MouseEvent.BUTTON1) // Linke oder Rechte Maustaste
            {
                checkInvBarMouseClick(e);   // Leiste
                checkInvDrawerMouseClick(e);    // Drawer
            }
        }
    }

    public void mouseWheelMoved(MouseWheelEvent e)
    {
        if (Crafting.isCraftBenchOpen) return;

        // Runter
        if(e.getWheelRotation() < 0)
        {
            Tutorial.solveTut(Tutorial.TUT_SCROLL);

            if(selected > 0) selected--;
            else selected = inventoryLength - 1;
        }
        // Hoch
        else if(e.getWheelRotation() > 0)
        {
            if(selected < inventoryLength - 1) selected++; // nach Links
            else selected = 0;
        }
    }

}
