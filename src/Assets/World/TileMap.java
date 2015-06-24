package Assets.World;

import Assets.GameObjects.Weapon;
import Assets.Inventory.Inventory;
import GameSaves.GameData.GameData;
import Main.References;
import Main.ResourceLoader;
import Main.Sound;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.*;

/**
 * Erstellen der Spielwelt.
 * Positionieren, Scrollen und weiteres der Tiles.
 *
 * @author Sirat
 * */
public class TileMap
{

    /* Farben */
    private final Color BROWN = new Color(83, 63, 72);

    /* Tiles */
    public static BufferedImage[] dirtTextures = { ResourceLoader.gras, ResourceLoader.grasWithFlower, ResourceLoader.dirt, ResourceLoader.dirtMidDark, ResourceLoader.dirtDark };
    public static BufferedImage[] dirtOnlyTextures = { ResourceLoader.dirt, ResourceLoader.dirtMidDark, ResourceLoader.dirtDark };
    public static BufferedImage[] grasOnlyTextures = { ResourceLoader.gras, ResourceLoader.grasWithFlower };

    public static BufferedImage[] treeOnlyTextures = {
            ResourceLoader.leafStart, ResourceLoader.leaf, ResourceLoader.leafEnd,
            ResourceLoader.treeTrunk, ResourceLoader.treeTrunkRight, ResourceLoader.treeTrunkLeft, ResourceLoader.treeTrunkRoot
    };
    public static BufferedImage[] treeTrunkTextures = { ResourceLoader.treeTrunk, ResourceLoader.treeTrunkRight, ResourceLoader.treeTrunkLeft };

    public static BufferedImage[] gemsTexture = {
            ResourceLoader.gold, ResourceLoader.ion, ResourceLoader.copper, ResourceLoader.silver,
            ResourceLoader.ruby, ResourceLoader.saphire, ResourceLoader.smaragd, ResourceLoader.diamond
    };

    private ArrayList<Rectangle> tileParticles = new ArrayList<Rectangle>();
    private Color tileParticlesColor;
    private int particleVelocityY = 4;


    /* Map */
    private double x;
    public static double y;
    public static Map<Point, Tile> map;
    private String mapFilePath;        // Map Speicher-Pfad

    private int columnOffset;           // Offsets
    private int rowOffset;

    private int numberOfColumnsToDraw;  // Anzahl der zuzeichnenden Tiles
    private int numberOfRowsToDraw;
    private int puffer = 2;             // Puffer
    private int seed;
    private static int colCounter = 0;
    private int startRow = References.SCREEN_HEIGHT/References.TILE_SIZE/2 + 3;
    private int rowTmp = startRow;

    /**
     * Konstruktor der Klasse TileMap
     * */
    public TileMap(int seed)
    {
        // Neue Spielwelt
        this.map = new HashMap<>();

        // Anzahl Spalten und Reihe zum Zeichnen
        this.numberOfColumnsToDraw = References.SCREEN_WIDTH / References.TILE_SIZE + this.puffer;
        this.numberOfRowsToDraw = References.SCREEN_HEIGHT / References.TILE_SIZE + this.puffer;

        // Seed festlegen
        this.seed = seed;
    }

    /**
     * update           Aktualisieren der Spielwelt
     * */
    public void update()
    {
        try
        {
            for (int i = 0; i < tileParticles.size(); i++)
            {
                if (tileParticles.get(i).getY() > tileParticles.get(i).getY()+100
                        || tileParticles.get(i).getX() < tileParticles.get(i).getX()-100
                        || tileParticles.get(i).getX() > tileParticles.get(i).getX()+100)
                {
                    tileParticles.remove(tileParticles.get(i));
                }
            }
        }
        catch (ConcurrentModificationException ex) { ex.printStackTrace(); }


        for (int column = columnOffset-puffer; column < columnOffset+numberOfColumnsToDraw; column++)
        {
            if (colCounter > seed)
                colCounter = 0;

            for (int row = rowOffset-puffer; row < rowOffset+numberOfRowsToDraw; row++)
            {
                if (map.get(new Point(row, column)) == null)
                {
                    if (colCounter == seed)
                    {
                        if (new Random().nextInt(10) > 5)
                        {
                            if (rowTmp > rowOffset+numberOfRowsToDraw)
                                rowTmp -= 5;
                            else
                                rowTmp += 2;
                        }
                        else
                        {
                            if (rowTmp < rowOffset)
                                rowTmp += 5;
                            else
                                rowTmp -= 2;
                        }
                    }

                    if (row > rowTmp && row > (startRow - rowTmp/2))
                    {
                        if (map.get(new Point(row-1, column)).getTexture() == null)
                        {
                            try
                            {
                                if (new Random().nextInt(100) > 70)
                                {
                                    int randNumTrunk = new Random().nextInt(20);
                                    int randNumLeaf = new Random().nextInt(3);

                                    // Baumstamm
                                    map.get(new Point(row-1, column)).setTexture(ResourceLoader.treeTrunkRoot);
                                    map.get(new Point(row-1, column)).setIsCollidable(false);
                                    map.get(new Point(row-1, column)).setHasGravity(false);
                                    map.get(new Point(row-1, column)).setIsDestructible(true);
                                    //map.get(new Point(row-1, column)).setBelongsToTree(true);

                                    for (int i = 2; i < randNumTrunk+8; i++)
                                    {
                                        map.get(new Point(row-i, column)).setTexture(treeTrunkTextures[new Random().nextInt(treeTrunkTextures.length)]);
                                        map.get(new Point(row-i, column)).setIsCollidable(false);
                                        map.get(new Point(row-i, column)).setHasGravity(false);
                                        map.get(new Point(row-i, column)).setIsDestructible(true);
                                        //map.get(new Point(row-i, column)).setBelongsToTree(true);
                                    }

                                    map.get(new Point(row-1-(randNumTrunk+7), column)).setTexture(ResourceLoader.treeTrunkTop);
                                    map.get(new Point(row-1-(randNumTrunk+7), column)).setIsCollidable(false);
                                    map.get(new Point(row-1-(randNumTrunk+7), column)).setHasGravity(false);
                                    map.get(new Point(row-1-(randNumTrunk+7), column)).setIsDestructible(true);
                                    //map.get(new Point(row-1-(randNumTrunk+7), column)).setBelongsToTree(true);

                                    // Baumkrone
                                    map.get(new Point(row-2-(randNumTrunk+7), column)).setTexture(ResourceLoader.leafStart);
                                    map.get(new Point(row-2-(randNumTrunk+7), column)).setIsCollidable(false);
                                    map.get(new Point(row-2-(randNumTrunk+7), column)).setHasGravity(false);
                                    map.get(new Point(row-2-(randNumTrunk+7), column)).setIsDestructible(true);
                                    //map.get(new Point(row-2-(randNumTrunk+7), column)).setBelongsToTree(true);

                                    for (int i = 1; i < randNumLeaf+4; i++)
                                    {
                                        map.get(new Point(row-2-(randNumTrunk+7)-i, column)).setTexture(ResourceLoader.leaf);
                                        map.get(new Point(row-2-(randNumTrunk+7)-i, column)).setIsCollidable(false);
                                        map.get(new Point(row-2-(randNumTrunk+7)-i, column)).setHasGravity(false);
                                        map.get(new Point(row-2-(randNumTrunk+7)-i, column)).setIsDestructible(true);
                                        //map.get(new Point(row-2-(randNumTrunk+7)-i, column)).setBelongsToTree(true);
                                    }

                                    map.get(new Point(row-2-(randNumTrunk+7)-(randNumLeaf+3), column)).setTexture(ResourceLoader.leafEnd);
                                    map.get(new Point(row-2-(randNumTrunk+7)-(randNumLeaf+3), column)).setIsCollidable(false);
                                    map.get(new Point(row-2-(randNumTrunk+7)-(randNumLeaf+3), column)).setHasGravity(false);
                                    map.get(new Point(row-2-(randNumTrunk+7)-(randNumLeaf+3), column)).setIsDestructible(true);
                                    //map.get(new Point(row-2-(randNumTrunk+7)-(randNumLeaf+3), column)).setBelongsToTree(true);
                                }
                                else
                                {
                                    map.put(new Point(row, column), new Tile(grasOnlyTextures[new Random().nextInt(grasOnlyTextures.length)], (column)*References.TILE_SIZE, (row)*References.TILE_SIZE, row, column, true, true, true));
                                }
                            }
                            catch (NullPointerException ignored) {}

                        }

                        if (map.get(new Point(row-1, column)).getTexture() == null || map.get(new Point(row-1, column)).getTexture() == ResourceLoader.treeTrunkRoot) // Gras
                        {
                            map.put(new Point(row, column), new Tile(grasOnlyTextures[new Random().nextInt(grasOnlyTextures.length)], (column)*References.TILE_SIZE, (row)*References.TILE_SIZE, row, column, true, true, true));
                        }
                        else
                        {
                            int rand = new Random().nextInt(800); // Edelsteine
                            if (rand > 797)
                            {
                                map.put(new Point(row, column), new Tile(ResourceLoader.ion, (column)*References.TILE_SIZE, (row)*References.TILE_SIZE, row, column, true, true, true));
                            }
                            else if (rand > 794 && rand < 797)
                            {
                                map.put(new Point(row, column), new Tile(ResourceLoader.copper, (column)*References.TILE_SIZE, (row)*References.TILE_SIZE, row, column, true, true, true));
                            }
                            else if (rand > 791 && rand < 794)
                            {
                                map.put(new Point(row, column), new Tile(ResourceLoader.silver, (column)*References.TILE_SIZE, (row)*References.TILE_SIZE, row, column, true, true, true));
                            }
                            else if (rand == 450)
                            {
                                map.put(new Point(row, column), new Tile(ResourceLoader.gold, (column)*References.TILE_SIZE, (row)*References.TILE_SIZE, row, column, true, true, true));
                            }
                            else if (rand == 449)
                            {
                                map.put(new Point(row, column), new Tile(ResourceLoader.ruby, (column)*References.TILE_SIZE, (row)*References.TILE_SIZE, row, column, true, true, true));
                            }
                            else if (rand == 448)
                            {
                                map.put(new Point(row, column), new Tile(ResourceLoader.saphire, (column)*References.TILE_SIZE, (row)*References.TILE_SIZE, row, column, true, true, true));
                            }
                            else if (rand == 447)
                            {
                                map.put(new Point(row, column), new Tile(ResourceLoader.smaragd, (column)*References.TILE_SIZE, (row)*References.TILE_SIZE, row, column, true, true, true));
                            }
                            else if (rand == 446)
                            {
                                map.put(new Point(row, column), new Tile(ResourceLoader.diamond, (column)*References.TILE_SIZE, (row)*References.TILE_SIZE, row, column, true, true, true));
                            }
                            else // Erde und Gras
                            {
                                map.put(new Point(row, column), new Tile(dirtOnlyTextures[new Random().nextInt(dirtOnlyTextures.length)], column*References.TILE_SIZE, row*References.TILE_SIZE, row, column, true, true, true));
                            }
                        }
                    }
                    else
                    {
                        map.put(new Point(row, column), new Tile(null, column*References.TILE_SIZE, row*References.TILE_SIZE, row, column, false, false, false));
                    }
                }
            }

            colCounter++;
        }

    }

    /**
     * render           Zeichnen der Spielwelt
     *
     * @param graphics  Graphics Objekt
     * */
    public void render(Graphics graphics)
    {
        for (int row = rowOffset-puffer; row < rowOffset+numberOfRowsToDraw; row++)
        {
            for (int column = columnOffset-puffer; column < columnOffset+numberOfColumnsToDraw; column++)
            {
                try
                {
                    map.get(new Point(row, column)).render(graphics, getX(), getY());
                }
                catch (NullPointerException ignored) {}
            }
        }

        /* Zeichnen der Tile Abbau-Splitter */
        graphics.setColor(tileParticlesColor);
        if (tileParticles != null)
        {
            try
            {
                for (Rectangle rec : tileParticles)
                {
                    rec.y += 8;
                    graphics.fillRect((int) rec.getX()+(new Random().nextInt(10)-new Random().nextInt(15)), (int) rec.getY(), (int) rec.getWidth(), (int) rec.getHeight());
                }
            }
            catch (ConcurrentModificationException ex) { ex.printStackTrace(); }
        }


    }

    /**
     *
     * */
    private void generateParticles(Point point, Color color, int numberOfParticles, int size)
    {
        tileParticlesColor = color;

        for (int i = 0; i < numberOfParticles; i++)
        {
            tileParticles.add(new Rectangle((int) point.getX()+(new Random().nextInt(10) - new Random().nextInt(20)), (int) point.getY()+(new Random().nextInt(10) - new Random().nextInt(20)), size, size));
        }

    }


    // GETTER UND SETTER
    /**
     * setPosition              Setzen der Map Position, sowie des Offsets
     *
     * @param x                 x-Koordinate
     * @param y                 y-Koordinate
     * */
    public void setPosition(double x, double y)
    {
        this.x = x;
        this.y = y;

        this.columnOffset = (int) (-this.x / References.TILE_SIZE);
        this.rowOffset = (int) (-this.y / References.TILE_SIZE);
    }

    /**
     * getX                     Rueckgabe der Map x-Koordinate
     *
     * @return int              x-Koordinate
     * */
    public double getX() { return this.x; }

    /**
     * getY                     Rueckgabe der Map y-Koordinate
     *
     * @return int              y-Koordinate
     * */
    public double getY() { return this.y; }

    /**
     * getMap                   Rueckgabe der Map
     *
     * @return Map<>            Spielwelt HashMap<Point/Tile>
     * */
    public Map<Point, Tile> getMap() { return this.map; }

    /**
     * getRowOffset             Rueckgabe des Zeilen-Offsets
     *
     * @return int              Zeilen Offset
     * */
    public int getRowOffset() { return this.rowOffset; }

    /**
     * getColumnOffset          Rueckgabe des Spalten-Offsets
     *
     * @return int              Spalten Offset
     * */
    public int getColumnOffset() { return this.columnOffset; }

    /**
     * getNumberOfRowsToDraw        Rueckgabe der Anzahl zuzeichnender Zeilen-Tiles
     *
     * @return int                  Anzahl der Zeilen Tiles
     * */
    public int getNumberOfRowsToDraw() { return this.numberOfRowsToDraw; }

    /**
     * getNumberOfColumnsToDraw     Rueckgabe der Anzahl zuzeichnender Spalten-Tiles
     *
     * @return int                  Anzahl der Spalten Tiles
     * */
    public int getNumberOfColumnsToDraw() { return this.numberOfColumnsToDraw; }

    /**
     * getPuffer                Rueckgabe des Puffers
     *
     * @return int              Puffer
     * */
    public int getPuffer() { return this.puffer; }

    // LISTENER
    /**
     * mouseClicked             Maus-Klick Event
     *
     * @param e                 MouseEvent Objekt
     * */
    public void mouseClicked(MouseEvent e) {
        if (!Inventory.isDrawerOpen) {
            Tile selectedTile = map.get(new Point((int) ((e.getY() - this.y) / References.TILE_SIZE), (int) (Math.floor((e.getX() - this.x) / References.TILE_SIZE))));

            if (selectedTile.getTexture() != null && selectedTile.getIsDestructible()) {
                if (Inventory.invBar[Inventory.selected].name.equals("Picke")
                        && (Arrays.asList(dirtTextures).contains(selectedTile.getTexture()) || Arrays.asList(gemsTexture).contains(selectedTile.getTexture()))) {
                    int tileResistance = selectedTile.getResistance();
                    if (tileResistance >= 0 ) {
                        if(GameData.isSoundOn.equals("On"))
                            Sound.earthSound.play();

                        generateParticles(e.getPoint(), BROWN, 30, 3);
                        tileResistance -= Weapon.PICKE_DAMAGE;
                        selectedTile.setResistance(tileResistance);
                    } else {
                        //System.out.println("Selected Tile: " + selectedTile.name + "; ");
                        // Zum Inventar hinzufuegen
                        Inventory.addToInventory(selectedTile);

                        selectedTile.setTexture(null);
                        selectedTile.setIsCollidable(false);
                        selectedTile.setHasGravity(false);
                        selectedTile.setIsDestructible(false);
                    }
                } else if (Inventory.invBar[Inventory.selected].name.equals("Axt") && Arrays.asList(treeOnlyTextures).contains(selectedTile.getTexture())) {
                    int tileResistance = selectedTile.getResistance();
                    if (tileResistance >= 0) {
                        if (GameData.isSoundOn.equals("On"))
                                Sound.woodSound.play();

                        generateParticles(e.getPoint(), BROWN, 30, 3);  // TODO particles spray to left or right
                        tileResistance -= Weapon.AXE_DAMAGE;
                        selectedTile.setResistance(tileResistance);
                    } else {
                        // Zum Inventar hinzufuegen
                        Inventory.addToInventory(selectedTile);

                        selectedTile.setTexture(null);
                        selectedTile.setIsCollidable(false);
                        selectedTile.setHasGravity(false);
                        selectedTile.setIsDestructible(false);
                    }
                } else if (Inventory.invBar[Inventory.selected].name.equals("Hammer") && Arrays.asList(gemsTexture).contains(selectedTile.getTexture())) {
                    int tileResistance = selectedTile.getResistance();
                    if (tileResistance >= 0 ) {
                        if(GameData.isSoundOn.equals("On"))
                            Sound.metalSound.play();

                        if (selectedTile.getTexture() == ResourceLoader.gold)
                            generateParticles(e.getPoint(), Color.YELLOW, 20, 3);
                        else if (selectedTile.getTexture() == ResourceLoader.silver)
                            generateParticles(e.getPoint(), Color.WHITE, 20, 3);
                        else if (selectedTile.getTexture() == ResourceLoader.ion)
                            generateParticles(e.getPoint(), Color.BLACK, 20, 3);
                        else if (selectedTile.getTexture() == ResourceLoader.copper)
                            generateParticles(e.getPoint(), BROWN, 20, 3);
                        else if (selectedTile.getTexture() == ResourceLoader.ruby)
                            generateParticles(e.getPoint(), Color.RED, 20, 3);
                        else if (selectedTile.getTexture() == ResourceLoader.saphire)
                            generateParticles(e.getPoint(), Color.BLUE, 20, 3);
                        else if (selectedTile.getTexture() == ResourceLoader.smaragd)
                            generateParticles(e.getPoint(), Color.GREEN, 20, 3);
                        else if (selectedTile.getTexture() == ResourceLoader.diamond)
                            generateParticles(e.getPoint(), Color.WHITE, 20, 3);
                        else
                            generateParticles(e.getPoint(), Color.BLACK, 20, 3);

                        tileResistance--;
                        selectedTile.setResistance(tileResistance);
                    } else {
                        // Zum Inventar hinzufuegen
                        Inventory.addToInventory(selectedTile);

                        selectedTile.setTexture(null);
                        selectedTile.setIsCollidable(false);
                        selectedTile.setHasGravity(false);
                        selectedTile.setIsDestructible(false);
                    }
                } else if (Inventory.invBar[Inventory.selected].name.equals("Schleimpistole") && Arrays.asList(gemsTexture).contains(selectedTile.getTexture())) {
                    int tileResistance = selectedTile.getResistance();
                    if (tileResistance >= 0 ) {

                        selectedTile.setTexture(null);
                        selectedTile.setIsCollidable(false);
                        selectedTile.setHasGravity(false);
                        selectedTile.setIsDestructible(false);

                    }
                }

            }
        }
    }


    /**
     * mouseMoved               Speichern der aktuellen Mausposition
     *
     * @param e                 MouseEvent Objekt
     * */
    public void mouseMoved(MouseEvent e)
    {
        References.MOUSE_X = e.getX();
        References.MOUSE_Y = e.getY();
    }

}