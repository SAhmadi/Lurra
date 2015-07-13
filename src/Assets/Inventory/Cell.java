package Assets.Inventory;

import Main.References;
import Main.ResourceLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Eine Zelle im Inventar oder Crafting
 *
 * @author Sirat
 * @version 0.9
 * */
public class Cell extends Rectangle
{

    //public String name = "null";

    // Zelle
    private byte id;
    private BufferedImage tileImage;
    private int count;
    private boolean wasEaten;

    /**
     * Cell         Konstruktor der Cell-Klasse
     *
     * @param size  Zellgroesse
     * */
    public Cell(Rectangle size)
    {
        this.id = 0;
        this.count = 0;

        this.setBounds(size);
    }

    /**
     * render               Zeichnen der Zelle
     *
     * @param g             Graphics Objekt
     * @param isSelected    Wert, ob Zelle gerade ausgewaehlt ist
     * */
    public void render(Graphics g, boolean isSelected)
    {
        // Zeichnen der leeren Zelle
        g.drawImage(ResourceLoader.inventoryBarCellUnselected, super.x, super.y, super.width, super.height, null);

        // Zeichne Zelle unter Mauszeiger schwarz
        if(Inventory.isDrawerOpen && this.contains(References.MOUSE_X, References.MOUSE_Y))
        {
            g.setColor(Color.BLACK);
            g.fillRect(super.x, super.y, super.width, super.height);
        }

        // Zeichnen der ausgewaehlten Zelle
        if(isSelected)
            g.drawImage(ResourceLoader.inventoryBarCellSelected, super.x, super.y, super.width, super.height, null);

        // Zeichnen des Zellinhalts
        if(id != 0 && tileImage != null && count > 0)
        {
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
                    super.y + 2*References.TILE_SIZE + g.getFontMetrics(ResourceLoader.inventoryItemFont).getHeight()/2
            );
        }
    }

    // GETTER UND SETTER
    /**
     * getId            Rueckgabe der ID
     * @return          ID
     * */
    public byte getId() { return this.id; }

    /**
     * setId            Setzen der ID
     * @param id        ID
     * */
    public void setId(byte id) { this.id = id; }

    /**
     * getTileImage     Rueckgabe der Texture
     * @return          Texture
     * */
    public BufferedImage getTileImage() { return tileImage; }

    /**
     * setTileImage     Setzen der Texture
     * @param tileImage Texture
     * */
    public void setTileImage(BufferedImage tileImage) { this.tileImage = tileImage; }

    /**
     * getCount         Rueckgabe der Anzahl
     * @return          Anzahl
     * */
    public int getCount() { return count; }

    /**
     * setCount         Setzen der Anzahl
     * @param count     Anzahl
     * */
    public void setCount(int count) { this.count = count; }

    /**
     * getWasEaten      Rueckgabe ob bereits eingenommen
     * @return          Wert ob bereits eingenommen
     * */
    public boolean getWasEaten() { return wasEaten; }

    /**
     * setWasEaten      Setzen ob bereits eingenommen
     * @param wasEaten  Wert ob bereits eingenommen
     * */
    public void setWasEaten(boolean wasEaten) { this.wasEaten = wasEaten; }

}
