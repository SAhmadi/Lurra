package Assets.Inventory;

import Assets.TileMap;
import Main.ResourceLoader;
import org.w3c.dom.css.Rect;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Sirat on 31.05.2015.
 */
public class Cell extends Rectangle {

    private final Color LIGHT_GREY = new Color(220, 220, 220);

    public BufferedImage image;
    public String name = "";
    public int count = 0;

    public boolean inUse = false;
    public boolean dontDisplay = false;
    public boolean selectable = false;

    public Cell(Rectangle size, BufferedImage image, String name) {
        this.setBounds(size);
        this.image = image;
        this.name = name;
    }

    public void render(Graphics g, boolean isSelected) {
        if(isSelected && !Inventory.isDrawerOpen) {
            g.setColor(LIGHT_GREY);
            g.fillRect(x, y, width, height);
        }
        else {
            g.setColor(Color.WHITE);
            g.fillRect(x, y, width, height);
        }

        // Zeichne Drawer
        if(Inventory.isDrawerOpen &&
                TileMap.mouseX > super.x &&
                TileMap.mouseX < super.x + super.width &&
                TileMap.mouseY > super.y &&
                TileMap.mouseY < super.y + super.height) {

            g.setColor(LIGHT_GREY);
            g.fillRect(x, y, width, height);
        }

        // Zeichne abgebauten Tile
        if(inUse) {
            if (isSelected)
                g.setColor(LIGHT_GREY);
            if(image != null) {
                // Zeichne Tile Bild
                g.drawImage(
                        image,
                        super.x + image.getWidth() / 2,
                        super.y + image.getHeight() / 2,
                        image.getWidth() * 2,
                        image.getHeight() * 2,
                        null
                );
                g.setFont(ResourceLoader.inventoryItemFont);
                g.setColor(Color.WHITE);
                g.drawString(
                        Integer.toString(count),
                        super.x + image.getWidth() + g.getFontMetrics(ResourceLoader.inventoryItemFont).stringWidth(Integer.toString(count)) / 2 + 2,
                        super.y + image.getHeight() + 12
                );
            }

        }


    }

}
