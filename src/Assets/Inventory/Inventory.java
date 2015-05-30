package Assets.Inventory;

import Assets.Tile;
import Main.ResourceLoader;
import Main.ScreenDimensions;

import java.awt.*;
import java.awt.geom.RectangularShape;
import java.util.ArrayList;

/**
 * Created by Sirat on 30.05.2015.
 */
public class Inventory {

    /*
    * Inventory
    * */
    // Inventar
    int[][] inv  = new int[10][2];
    private static ArrayList<InventoryItem> inventory = new ArrayList<InventoryItem>();
    private int inventoryBoxWidth = 500;
    private int inventoryBoxHeight = 50;


    public void render(Graphics g) {
        drawInventory(g);
    }

    /**
     *
     * drawInventar -       Zeichnen des Inventar
     * @param g             Graphics-Objekt
     *
     * */
    private void drawInventory(Graphics g) {
        final int displayable_count = 10;
        int x = 0;


        g.setColor(Color.WHITE);
        g.fillRect(
                ScreenDimensions.WIDTH / 2 - inventoryBoxWidth / 2,
                ScreenDimensions.HEIGHT - 2 * inventoryBoxHeight,
                inventoryBoxWidth,
                inventoryBoxHeight
        );

        g.setColor(Color.BLACK);
        g.setFont(ResourceLoader.inventoryItemFont);
        // g.drawRect(o * 50 + 400, 500, 50, 50);

        for (InventoryItem item : inventory) {
            try {
                g.drawImage(
                        item.texture,
                        ScreenDimensions.WIDTH / 2 - inventoryBoxWidth / 2 + 20 + x,
                        ScreenDimensions.HEIGHT - 2 * inventoryBoxHeight + 10,
                        item.texture.getWidth() * 2,
                        item.texture.getHeight() * 2,
                        null
                );

                g.drawString(
                        item.name,
                        ScreenDimensions.WIDTH / 2 - inventoryBoxWidth / 2 + 20 + x,
                        ScreenDimensions.HEIGHT - 2 * inventoryBoxHeight + 10
                );

                g.drawString(
                        Integer.toString(item.count),
                        ScreenDimensions.WIDTH / 2 - inventoryBoxWidth / 2 + 30 + x,
                        ScreenDimensions.HEIGHT - 2 * inventoryBoxHeight + 30
                );

                x += 50;
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }

        }

    }

    /**
     *
     * addToInventory -     Hinzufuegen eines Tiles zum Inventar
     * @param tile          Tile, welches zum Inventar hinzugefuegt werden soll
     *
     * */
    public static void addToInventory(Tile tile) {
        boolean found = false;
        for(InventoryItem item : inventory) {
            if(item.name.equals(tile.name)) {
                item.count++;
                found = true;
                break;
            }
        }
        if(!found)
            inventory.add(new InventoryItem(tile.getTexture(), tile.name ));
    }

}
