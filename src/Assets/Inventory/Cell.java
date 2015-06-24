package Assets.Inventory;

import Assets.World.TileMap;
import Main.References;
import Main.ResourceLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * Created by Sirat on 31.05.2015.
 */
public class Cell extends Rectangle {

    public String name = "null";
    public BufferedImage tileImage;
    public int count = 0;

    public Cell(Rectangle size) {
        this.setBounds(size);
        this.setTileImage();
    }

    public void setTileImage()
    {
        switch (name) {
            case "Erde":
                tileImage = ResourceLoader.dirt;
                break;
            case "Gras":
                tileImage = ResourceLoader.gras;
                break;
            case "Gold":
                tileImage = ResourceLoader.gold;
                break;
            case "Picke":
                tileImage = ResourceLoader.stonePick;
                break;
            case "Axt":
                tileImage = ResourceLoader.axe;
                break;
            case "Hammer":
                tileImage = ResourceLoader.hammer;
                break;
            case "Schleimpistole":
                tileImage = ResourceLoader.gunPurple;
                break;
            case "Burger":
                tileImage = ResourceLoader.burger;
                break;
            case "null":
                tileImage = null;
                break;
        }
    }

    public BufferedImage getTileImage() {
        return tileImage;
    }

    public void setName()
    {
        if(Arrays.asList(TileMap.dirtOnlyTextures).contains(tileImage))
            name = "Erde";
        else if(Arrays.asList(TileMap.grasOnlyTextures).contains(tileImage))
            name = "Gras";
        else if(tileImage == ResourceLoader.gold)
            name = "Gold";
        else if(tileImage == ResourceLoader.silver)
            name = "Silber";
        else if(tileImage == ResourceLoader.copper)
            name = "Kupfer";
        else if(tileImage == ResourceLoader.ion)
            name = "Eisen";
        else if(tileImage == ResourceLoader.ruby)
            name = "Rubin";
        else if(tileImage == ResourceLoader.saphire)
            name = "Saphire";
        else if(tileImage == ResourceLoader.smaragd)
            name = "Smaragd";
        else if(tileImage == ResourceLoader.diamond)
            name = "Diamant";
        else if(tileImage == ResourceLoader.stonePick)
            name = "Picke";
        else if(tileImage == ResourceLoader.axe)
            name = "Axt";
        else if(tileImage == ResourceLoader.hammer)
            name = "Hammer";
        else if(tileImage == ResourceLoader.gunPurple)
            name = "Schleimpistole";
        else if (tileImage == ResourceLoader.burger)
            name = "Burger";
        else
            name = "null";
    }

    public void render(Graphics g, boolean isSelected)
    {
        g.drawImage(ResourceLoader.inventoryBarCellUnselected, super.x, super.y, super.width, super.height, null);

        if(Inventory.isDrawerOpen && this.contains(References.MOUSE_X, References.MOUSE_Y))
        {
            g.setColor(Color.BLACK);
            g.fillRect(super.x, super.y, super.width, super.height);
        }

        if(isSelected)
            g.drawImage(ResourceLoader.inventoryBarCellSelected, super.x, super.y, super.width, super.height, null);

        if(!name.equals("null")) {
            g.drawImage(
                    tileImage,
                    super.x + References.TILE_SIZE / 2,
                    super.y + References.TILE_SIZE / 2,
                    References.TILE_SIZE * 2,
                    References.TILE_SIZE * 2,
                    null
            );

            g.setColor(Color.BLACK);
            g.setFont(ResourceLoader.inventoryItemFont);
            g.drawString(
                    Integer.toString(count),
                    super.x + 2*References.TILE_SIZE,
                    super.y + 2*References.TILE_SIZE
            );
        }


    }

}
