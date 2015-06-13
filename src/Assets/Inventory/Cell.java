package Assets.Inventory;

import Assets.World.Tile;
import Main.References;
import Main.ResourceLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Sirat on 31.05.2015.
 */
public class Cell extends Rectangle {

    public String name = "null";
    public BufferedImage tileImage;

    public Cell(Rectangle size) {
        this.setBounds(size);
    }

    public void setTileImage() {
        if(name.equals("Erde")) {
            tileImage = ResourceLoader.dirt;
        }
        else if(name.equals("Gras")) {
            tileImage = ResourceLoader.grasTile;
        }
        else if(name.equals("Gold")) {
            tileImage = ResourceLoader.gold;
        }
        else if(name.equals("null")) {
            tileImage = null;
        }
    }

    public void render(Graphics g, boolean isSelected) {
        g.drawImage(ResourceLoader.inventoryBarCellUnselected, super.x, super.y, super.width, super.height, null);

        if(Inventory.isDrawerOpen && this.contains(References.MOUSE_X, References.MOUSE_Y)) {
            g.setColor(Color.BLACK);
            g.fillRect(super.x, super.y, super.width, super.height);
        }

        if(isSelected) {
            g.drawImage(ResourceLoader.inventoryBarCellSelected, super.x, super.y, super.width, super.height, null);
        }

        if(!name.equals("null")) {
            g.drawImage(
                    tileImage,
                    super.x + Tile.WIDTH / 2,
                    super.y + Tile.HEIGHT / 2,
                    Tile.WIDTH * 2,
                    Tile.HEIGHT * 2,
                    null
            );
        }


    }

}
