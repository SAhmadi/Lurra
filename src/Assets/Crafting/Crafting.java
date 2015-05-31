package Assets.Crafting;

import Assets.Inventory.Inventory;
import Main.ScreenDimensions;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Sirat on 31.05.2015.
 */
public class Crafting extends Rectangle implements KeyListener {

    public static boolean isExpanded = false;

    public Crafting() {
        setBounds(ScreenDimensions.WIDTH / 8, ScreenDimensions.HEIGHT - Inventory.inventoryCellSize - Inventory.inventoryBorderSpacing, 200, 48);
    }

    public void render(Graphics g) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_F) {
            if(isExpanded) {
                isExpanded = false;
                setBounds(ScreenDimensions.WIDTH/10, ScreenDimensions.HEIGHT - Inventory.inventoryCellSize - Inventory.inventoryBorderSpacing, 200, 48);
            }
            else {
                isExpanded = true;
                setBounds(0, 0, ScreenDimensions.WIDTH/8, ScreenDimensions.HEIGHT);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
