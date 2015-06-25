package Assets.Inventory;

import Assets.World.Tile;
import Main.References;
import State.Level.Level1State;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;

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

    public static Cell[] invBar = new Cell[inventoryLength];
    public static Cell[] invDrawer = new Cell[inventoryLength * inventoryHeight];

    public static boolean isDrawerOpen = false;
    public static boolean isHolding = false;

    public static int selected = 0;
    public String holdingName = "null";
    public BufferedImage holdingTileImage;
    public int holdingCount;




    public Inventory() {
        // Inventar Leiste
        for(int i = 0; i < invBar.length; i++) {
            invBar[i] = new Cell(new Rectangle(
                    (References.SCREEN_WIDTH/2) - ((inventoryLength * (inventoryCellSize + inventoryCellSpacing))/2) + (i * (inventoryCellSize + inventoryCellSpacing)),
                    References.SCREEN_HEIGHT - (inventoryCellSize + inventoryBorderSpacing),
                    inventoryCellSize,
                    inventoryCellSize
            ));
        }

        // Inventar Drawer
        int x = 0, y = 0;
        for(int i = 0; i < invDrawer.length; i++) {
            invDrawer[i] = new Cell(new Rectangle(
                    (References.SCREEN_WIDTH/2) - ((inventoryLength * (inventoryCellSize + inventoryCellSpacing))/2) + (x * (inventoryCellSize + inventoryCellSpacing)),
                    References.SCREEN_HEIGHT - (inventoryCellSize + inventoryBorderSpacing) - (inventoryHeight * (inventoryCellSize + inventoryCellSpacing)) + (y * (inventoryCellSize + inventoryCellSpacing)),
                    inventoryCellSize,
                    inventoryCellSize
            ));

            x++;
            if(x == inventoryLength) {
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

    public void render(Graphics g) {
        g.setColor(Color.BLACK);

        for(int i = 0; i < invBar.length; i++) {
            boolean isSelected = false;
            if(i == selected) {
                isSelected = true;
            }
            invBar[i].render(g, isSelected);
        }

        if(isDrawerOpen) {
            for(int i = 0; i < invDrawer.length; i++) {
                invDrawer[i].render(g, false);
            }
        }

        if(isHolding) {
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

//    public void loadCells() {
//        for(int cell = 0; cell < InventoryData.inUse.length; cell++) {
//
//            try {
//                inventoryCells[cell].name = InventoryData.names[cell];
//                inventoryCells[cell].count = (InventoryData.counts.equals("0")) ? 0 : Integer.parseInt(InventoryData.counts[cell]);
//                inventoryCells[cell].inUse = Boolean.parseBoolean(InventoryData.inUse[cell]);
//            } catch(NumberFormatException ex) {
//                ex.printStackTrace();
//            }
//
//        }
//    }



    /**
     *
     * */
    public static void addToInventory(Tile tile)
    {
        for (int i = 0; i < invBar.length; i++)
        {
            if (tile.name.equals(invBar[i].name))
            {
                invBar[i].count++;
                break;
            }
            else if (invBar[i].name.equals("null") && invBar[i].tileImage == null && invBar[i].count == 0)
            {
                invBar[i].tileImage = tile.getTexture();
                invBar[i].name = tile.name;
                invBar[i].count = 1;
                break;
            }
        }
    }


    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_C) {
            if(isDrawerOpen)
                isDrawerOpen = false;
            else
                isDrawerOpen = true;
        }
    }

    public void mouseClicked(MouseEvent e)
    {
        if(e.getButton() == 1) // Linke Maustaste
        {
            if(isDrawerOpen)
            {
                for(int i = 0; i < invBar.length; i++) {
                    if(invBar[i].contains(References.MOUSE_X, References.MOUSE_Y)) {
                        if(invBar[i].name != "null" && !isHolding) {
                            holdingName = invBar[i].name;
                            holdingTileImage = invBar[i].tileImage;
                            holdingCount = invBar[i].count;

                            invBar[i].name = "null";
                            invBar[i].setTileImage();
                            invBar[i].count = 0;

                            isHolding = true;
                        }
                        else if(isHolding && invBar[i].name.equals("null")) {
                            invBar[i].name = holdingName;
                            invBar[i].tileImage = holdingTileImage;
                            invBar[i].count = holdingCount;

                            isHolding = false;
                        }
                        else if(isHolding && !invBar[i].name.equals("null")) {
                            String tmpName = invBar[i].name;
                            BufferedImage tmpImage = invBar[i].tileImage;
                            int tmpCount = invBar[i].count;

                            invBar[i].name = holdingName;
                            invBar[i].tileImage = holdingTileImage;
                            invBar[i].count = holdingCount;

                            holdingName = tmpName;
                            holdingTileImage = tmpImage;
                            holdingCount = tmpCount;
                        }
                    }
                }

                for(int i = 0; i < invDrawer.length; i++) {
                    if(invDrawer[i].contains(References.MOUSE_X, References.MOUSE_Y)) {
                        if(invDrawer[i].name != "null" && !isHolding) {
                            holdingName = invDrawer[i].name;
                            holdingTileImage = invDrawer[i].tileImage;
                            holdingCount = invDrawer[i].count;

                            invDrawer[i].name = "null";
                            invDrawer[i].setTileImage();
                            invDrawer[i].count = 0;

                            isHolding = true;
                        }
                        else if(isHolding && invDrawer[i].name.equals("null")) {
                            invDrawer[i].name = holdingName;
                            invDrawer[i].setTileImage();
                            invDrawer[i].count = 0;

                            isHolding = false;
                        }
                        else if(isHolding && !invDrawer[i].name.equals("null")) {
                            String tmpName = invDrawer[i].name;
                            BufferedImage tmpImage = invDrawer[i].tileImage;
                            int tmpCount = invBar[i].count;

                            invDrawer[i].name = holdingName;
                            invDrawer[i].setTileImage();
                            invDrawer[i].count = holdingCount;


                            holdingName = tmpName;
                            holdingTileImage = tmpImage;
                            holdingCount = tmpCount;
                        }
                    }
                }
            }
        }




    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        if(e.getWheelRotation() < 0) {  // Runter
            if(selected > 0) {
                selected--;
            }
            else {
                selected = inventoryLength - 1;
            }
        }
        else if(e.getWheelRotation() > 0) { // Hoch
            if(selected < inventoryLength - 1) {
                selected++; // nach Links
            }
            else {
                selected = 0;
            }
        }
    }
}
