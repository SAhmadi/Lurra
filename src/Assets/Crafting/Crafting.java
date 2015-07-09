package Assets.Crafting;

import Assets.Inventory.Cell;
import Assets.Inventory.Inventory;
import Main.References;
import Main.Tutorial;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

/**
 * Mixen der Abbauobjekten zu neuen Objekten
 *
 * @author Sirat
 * */
public class Crafting extends Rectangle implements KeyListener, MouseListener
{

    // Crafting
    public static int craftBenchLength = 3;
    public static int craftBenchHeight = 3;
    public static int productBenchLength = 1;
    public static int productBenchHeight = 1;

    public static int craftCellSize = 48;
    public static int craftCellSpacing = 5;
    public static int craftBorderSpacing = 5;

    public static Cell[] craftBench = new Cell[craftBenchLength * craftBenchHeight];
    public static Cell[] productBench = new Cell[productBenchLength * productBenchHeight];

    public static boolean isHolding = false;
    public static boolean isCraftBenchOpen = false;

    public static String holdingName = "null";
    public BufferedImage holdingTileImage;
    public int holdingCount = 0;

    /**
     * Crafting         Konstruktor der Crafting-Klasse
     * */
    public Crafting()
    {
        // Crafting Cells
        int x = 0;
        int y = 0;
        int yOffset = 150;

        for(int i = 0; i < craftBench.length; i++)
        {
            craftBench[i] = new Cell(new Rectangle(
                    (References.SCREEN_WIDTH/2) - ((craftBenchLength * (craftCellSize + craftCellSpacing))/2) + (x * (craftCellSize + craftCellSpacing)),
                    References.SCREEN_HEIGHT - (craftCellSize + craftBorderSpacing) - (craftBenchHeight * (craftCellSize + craftCellSpacing)) + (y * (craftCellSize + craftCellSpacing)) - yOffset,
                    craftCellSize,
                    craftCellSize
            ));

            x++;
            if(x == craftBenchLength)
            {
                x = 0;
                y++;
            }
        }

        // Produkt Cell
        for(int i = 0; i < productBench.length; i++)
        {
            productBench[i] = new Cell(new Rectangle(
                    (References.SCREEN_WIDTH/2) - ((productBenchLength * (craftCellSize + craftCellSpacing))/2) + (x * (craftCellSize + craftCellSpacing)) + (craftCellSize*2+craftCellSpacing+craftBorderSpacing),
                    References.SCREEN_HEIGHT - (craftCellSize + craftBorderSpacing) - (productBenchHeight * (craftCellSize + craftCellSpacing)) + (y * (craftCellSize + craftCellSpacing)) - ((yOffset-2) *2+craftCellSize + craftBorderSpacing +craftCellSpacing),
                    craftCellSize,
                    craftCellSize
            ));

            x++;

            if(x == productBenchLength)
            {
                x = 0;
                y++;
            }
        }
    }

    /**
     * render       Zeichnen der Craftzellen
     * */
    public void render(Graphics g)
    {
        g.setColor(Color.BLACK);

        if(isCraftBenchOpen)
        {
            for (Cell cell : craftBench) { cell.render(g, false); }
            for (Cell cell : productBench) { cell.render(g, false); }
        }

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
        }
    }

    /**
     * checkRecipes     Pruefe ob Mixture existiert
     * */
    public void checkRecipes() { Recipe.checkRecipes(craftBench, productBench, holdingName); }

    /**
     * checkInvBarMouseClick    Pruefe ob mit Inventarleiste interagiert wird
     *
     * @param e                 Mausevent mit BUTTON1 = linke Maustaste und BUTTON3 = rechte Maustaste
     * */
    public void checkInvBarMouseClick(MouseEvent e)
    {
        for (Cell cell : Inventory.invBar)
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
                        for (Cell invBarCell : Inventory.invBar)  // Pruefe ob bereits in Inventarleiste vorhanden
                        {
                            if (invBarCell.name.equals(holdingName)) { canPlace = false; break; }
                        }

                        if (canPlace)   // Falls in Inventarleiste nicht gefunden wurde, pruefe ob bereits in Inventardrawer vorhanden
                            for (Cell invDrawerCell : Inventory.invDrawer)
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
                            for (Cell invBarCell : Inventory.invBar)  // Pruefe ob bereits in Inventarleiste vorhanden
                            {
                                if (invBarCell.name.equals(holdingName)) { canPlace = false; break; }
                            }

                            if (canPlace)   // Falls in Inventarleiste nicht gefunden wurde, pruefe ob bereits in Inventardrawer vorhanden
                                for (Cell invDrawerCell : Inventory.invDrawer)
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
        for (Cell cell : Inventory.invDrawer)
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
                        for (Cell invBarCell : Inventory.invBar)  // Pruefe ob bereits in Inventarleiste vorhanden
                        {
                            if (invBarCell.name.equals(holdingName)) { canPlace = false; break; }
                        }

                        if (canPlace)   // Falls in Inventarleiste nicht gefunden wurde, pruefe ob bereits in Inventardrawer vorhanden
                            for (Cell invDrawerCell : Inventory.invDrawer)
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
                            for (Cell invBarCell : Inventory.invBar)  // Pruefe ob bereits in Inventarleiste vorhanden
                            {
                                if (invBarCell.name.equals(holdingName)) { canPlace = false; break; }
                            }

                            if (canPlace)   // Falls in Inventarleiste nicht gefunden wurde, pruefe ob bereits in Inventardrawer vorhanden
                                for (Cell invDrawerCell : Inventory.invDrawer)
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
     * checkCraftBenchMouseClick    Pruefe ob mit Crafting interagiert wird
     *
     * @param e                     Mausevent mit BUTTON1 = linke Maustaste und BUTTON3 = rechte Maustaste
     * */
    public void checkCraftBenchMouseClick(MouseEvent e)
    {

        for (Cell cell : craftBench)
        {
            if (cell.contains(References.MOUSE_X, References.MOUSE_Y))
            {
                if (!cell.name.equals("null") && !isHolding)    // Falls eine Zelle aufgehoben wird
                {
                    if (!productBench[0].name.equals("null")) return;

                    if (e.getButton() == MouseEvent.BUTTON1)  // Bewege nur ein einziges Tile
                    {
                        holdingName = cell.name;
                        holdingTileImage = cell.tileImage;
                        holdingCount = 1;

                        cell.name = "null";
                        cell.setTileImage();
                        cell.count = 0;

                        isHolding = true;
                    }
                }
                else if (isHolding && cell.name.equals("null") && holdingCount == 1) // Falls eine aufgehobene Zelle in einer leeren abgelegt wird
                {
                    if (e.getButton() == MouseEvent.BUTTON1)
                    {
                        cell.name = holdingName;
                        cell.tileImage = holdingTileImage;
                        cell.count = 1;
                        isHolding = false;
                    }
                }
                else if (isHolding && !cell.name.equals("null") && holdingCount == 1)    // Falls eine aufgehobene Zelle in einer bereits benuzten abgelegt wird
                {
                    if (e.getButton() == MouseEvent.BUTTON1)
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

    /**
     * checkCraftBenchMouseClick    Pruefe ob mit Crafting interagiert wird
     *
     * @param e                     Mausevent mit BUTTON1 = linke Maustaste und BUTTON3 = rechte Maustaste
     * */
    public void checkProductCellMouseClick(MouseEvent e)
    {
        if (productBench[0].contains(References.MOUSE_X, References.MOUSE_Y))
        {
            if (!productBench[0].name.equals("null") && !isHolding)
            {
                if (e.getButton() == MouseEvent.BUTTON1)  // Bewege nur ein einziges Tile
                {
                    holdingName = productBench[0].name;
                    holdingTileImage = productBench[0].tileImage;
                    holdingCount = 1;

                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                    productBench[0].count = 0;

                    isHolding = true;
                }
            }
        }
    }

    // EVENTS
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_F) {
            isCraftBenchOpen = !isCraftBenchOpen;
            Tutorial.solveTut(Tutorial.TUT_CRAFT);

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e)
    {
        if (e.getButton() == MouseEvent.BUTTON1 || e.getButton() == MouseEvent.BUTTON3) // Linke oder Rechte Maustaste
        {
            if(isCraftBenchOpen)
            {
                checkInvBarMouseClick(e);   // Inventarleiste
                checkInvDrawerMouseClick(e); // InventarDrawer
                checkCraftBenchMouseClick(e);   // Craftbank
                checkProductCellMouseClick(e);  // Produktzelle
            }
        }


    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

}
