package Assets.Inventory;

import Assets.Tile;
import Assets.TileMap;
import InventoryData.InventoryData;
import Main.Main;
import Main.ResourceLoader;
import Main.ScreenDimensions;
import PlayerData.PlayerData;
import com.sun.org.apache.regexp.internal.RESyntaxException;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.RectangularShape;
import java.util.ArrayList;

/**
 *
 * Created by Sirat on 30.05.2015.
 *
 */
public class Inventory {

    /*
    * Inventory
    * */
    public static int inventoryLength = 10;
    public static int inventoryHeight = 3;  // tatsaechliche Hoehe = 4
    public static int inventoryCellSize = 48;
    public static int inventoryCellSpacing = 5;
    public static int inventoryBorderSpacing = 5;

    public boolean isSelected = false;
    public static int selected = 0;

    // Drawer
    public static boolean isDrawerOpen = false;
    public static boolean isHolding = false;
    public static Cell isHoldingCell;

    public static Cell[] inventoryCells = new Cell[inventoryLength];
    public static Cell[] inventoryDrawer = new Cell[inventoryLength * inventoryHeight];

//    private static ArrayList<InventoryItem> inventoryItems = new ArrayList<InventoryItem>();
//    private int inventoryBoxWidth = 500;
//    private int inventoryBoxHeight = 50;

    public Inventory() {
        for(int cell = 0; cell < inventoryCells.length; cell++) {
            inventoryCells[cell] = new Cell(
                    new Rectangle(
                            (ScreenDimensions.WIDTH/2) - ((inventoryLength * (inventoryCellSize + inventoryCellSpacing))/2) + (cell * (inventoryCellSize + inventoryCellSpacing)),
                            ScreenDimensions.HEIGHT - (inventoryCellSize + inventoryBorderSpacing),
                            inventoryCellSize,
                            inventoryCellSize
                    ),
                    null,
                    ""
            );
        }

        // Drawer
        int counterX = 0, counterY = 0;
        for(int cell = 0; cell < inventoryDrawer.length; cell++) {
            inventoryDrawer[cell] = new Cell(
                    new Rectangle(
                            (ScreenDimensions.WIDTH/2) - ((inventoryLength * (inventoryCellSize + inventoryCellSpacing))/2) + (counterX * (inventoryCellSize + inventoryCellSpacing)),
                            ScreenDimensions.HEIGHT - (inventoryCellSize + inventoryBorderSpacing) - (inventoryHeight* (inventoryCellSize + inventoryCellSpacing)) + (counterY* (inventoryCellSize + inventoryCellSpacing)),
                            inventoryCellSize,
                            inventoryCellSize
                    ),
                    null,
                    ""
            );

            counterX++;
            if(counterX == inventoryLength) {
                counterX = 0;
                counterY++;
            }
        }

    }

    public void render(Graphics g) {
        for(int cell = 0; cell < inventoryCells.length; cell++) {
            isSelected = false;
            if(cell == selected) {
                isSelected = true;
            }
            inventoryCells[cell].render(g, isSelected);
        }

        if(isDrawerOpen) {
            for(int cell = 0; cell < inventoryDrawer.length; cell++) {
                inventoryDrawer[cell].render(g, false);
            }
        }

        if(isHolding) {
            // Zeichne Hintergrund
            g.drawImage(
                    isHoldingCell.image,
                    (int) (TileMap.mouseX) - isHoldingCell.image.getWidth() / 2,
                    (int) (TileMap.mouseY) - isHoldingCell.image.getHeight() / 2,
                    (int) (isHoldingCell.image.getWidth() * 2),
                    (int) (isHoldingCell.image.getHeight() * 2),
                    null
            );
        }
    }


    public void loadCells() {
        for(int cell = 0; cell < InventoryData.inUse.length; cell++) {

            try {
                inventoryCells[cell].name = InventoryData.names[cell];
                inventoryCells[cell].count = (InventoryData.counts.equals("0")) ? 0 : Integer.parseInt(InventoryData.counts[cell]);
                inventoryCells[cell].inUse = Boolean.parseBoolean(InventoryData.inUse[cell]);
            }
            catch(NumberFormatException ex) {
                ex.printStackTrace();
            }

        }
    }


    /**
     * addToInventory -     Hinzufuegen eines Tiles zum Inventar
     *
     * @param tile Tile, welches zum Inventar hinzugefuegt werden soll
     */
    public static void addToInventory(Tile tile) {
        for (int i = 0; i < inventoryCells.length; i++) {
            if (inventoryCells[i].name == tile.name) {
                inventoryCells[i].count++;
                break;
            }
            if (!inventoryCells[i].inUse) {
                inventoryCells[i].name = tile.name;
                inventoryCells[i].image = tile.getTexture();
                inventoryCells[i].count++;
                inventoryCells[i].inUse = true;
                break;
            }
//            else if(i == inventoryCells.length -1) {
//                inventoryCells[0].name = tile.name;
//                inventoryCells[0].image = tile.getTexture();
//                inventoryCells[i].count = 1;
//                inventoryCells[0].inUse = true;
//            }
        }
    }


    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_C) {
            if(isDrawerOpen) {
                isDrawerOpen = false;
            }
            else {
                isDrawerOpen = true;
            }
        }
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        // Hoch
        if(e.getWheelRotation() < 0) {
            if(selected > 0) {
                selected--;
            }
            else {
                selected = inventoryLength-1;
            }
        }
        // Runter
        else {
            if(selected < inventoryLength-1) {
                selected++;
            }
            else {
                selected = 0;
            }
        }
    }
}
