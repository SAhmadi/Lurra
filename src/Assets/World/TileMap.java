package Assets.World;

import Assets.Crafting.Crafting;
import Assets.GameObjects.Player;
import Assets.GameObjects.Weapon;
import Assets.Inventory.Inventory;
import GameSaves.GameData.GameData;
import GameSaves.PlayerData.PlayerData;
import GameSaves.TilemapData.TilemapDataLoad;
import Main.References;
import Main.ResourceLoader;
import Main.Sound;
import Main.Tutorial;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Erstellen der Spielwelt.
 * Positionieren, Scrollen und weiteres der Tiles.
 *
 * @author Sirat
 * */
public class TileMap
{

    // Farbe
    private final Color DIRT = new Color(83, 63, 72);
    private final Color GRAS = new Color(1, 212, 12);
    private final Color GOLD = new Color(238, 237, 38);
    private final Color RUBY = new Color(247, 54, 80);
    private final Color SAPHIRE = new Color(68, 131, 205);
    private final Color SMARAGD = new Color(50, 165, 85);
    private final Color COPPER = new Color(205, 113, 0);
    private final Color SAND = new Color(255, 222, 0);
    private final Color LEAF = new Color(1, 244, 14);
    private final Color TREE = new Color(137, 80, 71);
    private final Color STONE = new Color(236, 223, 201);
    private final Color STONE_RED = new Color(156, 37, 38);

    // Tiles
    public static BufferedImage[] dirtOnlyTextures = {ResourceLoader.dirt, ResourceLoader.dirtMidDark, ResourceLoader.dirtDark};
    public static BufferedImage[] grasOnlyTextures = {ResourceLoader.gras, ResourceLoader.grasWithFlower};

    public static BufferedImage[] treeOnlyTextures = {ResourceLoader.leafStart, ResourceLoader.leaf, ResourceLoader.leafEnd, ResourceLoader.treeTrunk, ResourceLoader.treeTrunkRight, ResourceLoader.treeTrunkLeft, ResourceLoader.treeTrunkRoot};

    public static BufferedImage[] treeSnowOnlyTextures = {ResourceLoader.treeTrunkSnowEnd, ResourceLoader.treeTrunkSnow, ResourceLoader.treeTrunkAfterRootSnow, ResourceLoader.treeTrunkRootSnow, ResourceLoader.treeTrunkRight, ResourceLoader.treeTrunkLeft, ResourceLoader.treeTrunkRoot};

    public static BufferedImage[] cactusOnlyTextues = {ResourceLoader.cactusRoot, ResourceLoader.cactus1, ResourceLoader.cactus2, ResourceLoader.cactusTop};

    public static BufferedImage[] cactusTextures = {ResourceLoader.cactus1, ResourceLoader.cactus2};

    public static BufferedImage[] treeTrunkTextures = {ResourceLoader.treeTrunk, ResourceLoader.treeTrunkRight, ResourceLoader.treeTrunkLeft};

    public static BufferedImage[] gemsTextures = {ResourceLoader.smaragd, ResourceLoader.diamond, ResourceLoader.gold, ResourceLoader.saphire, ResourceLoader.ruby, ResourceLoader.ion, ResourceLoader.copper, ResourceLoader.silver};

    public static BufferedImage[] waterTextures = {ResourceLoader.water, ResourceLoader.waterTop, ResourceLoader.waterTop2};
    public static BufferedImage[] iceTextures = {ResourceLoader.ice, ResourceLoader.ice2, ResourceLoader.ice3};
    public static BufferedImage[] sandTextures = {ResourceLoader.sand, ResourceLoader.sand2, ResourceLoader.sand3};

    // Truemmer
    private Rectangle[] particles;
    private boolean showParticles;
    private Color tileParticlesColor;

    // Map
    private double x;
    private double y;
    private Map<Point, Tile> map;
    public static Map<Point, Byte> minedTiles;
    private boolean continueLevel;

    private int columnOffset;           // Offsets
    private int rowOffset;

    private int numberOfColumnsToDraw;  // Anzahl der zu zeichnenden Tiles
    private int numberOfRowsToDraw;
    private int puffer;             // Puffer

    // Zufallsvariabeln
    public static int seed = 20;    // Standard Seed, kann ueberschrieben werden
    private int gemsSeed;
    private int randomizeSeed;

    private int[] randomNumbers;
    private int randNumbersIndex;
    private int treeTextureCounter;     // zufaellige Baeume
    private int treeHeight;

    private boolean shouldPlaceWater;   // zufaellige Seen

    // zufaellige Huegel
    private double rowTmp = References.SCREEN_HEIGHT / References.TILE_SIZE / 2;
    private double xForTileMapStart;
    private boolean isMountain = true;

    // zufaelliger Regen
    private Rain[] rain;

    // Player
    private Player player;
    private int mineRadius;
    private Tile lastCollected;

    /**
     * TileMap                  Konstrultor der TileMap-Klasse
     *
     * @param s                 Zufalls Seed
     * @param continueLevel     Wert ob von Spielstand geladen soll
     */
    public TileMap(int s, boolean continueLevel)
    {
        // Neue Spielwelt
        this.map = new HashMap<>();

        this.continueLevel = continueLevel;
        minedTiles = new HashMap<>();

        if (continueLevel)  // Laden der bereits abgebauten Tiles
            TilemapDataLoad.XMLRead(PlayerData.name);

        // Partikel
        this.showParticles = false;

        // Anzahl Spalten und Reihe zum Zeichnen
        this.puffer = 2;
        this.numberOfColumnsToDraw = References.SCREEN_WIDTH / References.TILE_SIZE + this.puffer;
        this.numberOfRowsToDraw = References.SCREEN_HEIGHT / References.TILE_SIZE + this.puffer;

        // Seed festlegen
        TileMap.seed = s;
        this.gemsSeed = 1;
        this.randomizeSeed = 1;

        this.randomNumbers = new int[]{1, 2, 3, 4};
        this.randNumbersIndex = 0;

        this.treeTextureCounter = 0;
        this.treeHeight = seed / randomNumbers[randNumbersIndex];

        this.shouldPlaceWater = false;
        this.rain = new Rain[References.SCREEN_WIDTH/30];

        for (int i = 0; i < rain.length; i++)
            rain[i] = new Rain();

        // Abbauradius
        this.mineRadius = 4;
    }

    /**
     * update           Aktualisieren der Spielwelt
     */
    public void update()
    {
        // Tiles Erstellen und Positionieren
        generateTiles();
    }

    /**
     * render           Zeichnen der Spielwelt
     *
     * @param graphics Graphics Objekt
     */
    public void render(Graphics2D graphics)
    {
        graphics.setColor(new Color(255, 255, 255, 180)); // Setzen der Farbe fuer das Auswahlquadrat

        // Zeichnen der Tiles
        for (int row = rowOffset - puffer; row < rowOffset + numberOfRowsToDraw; row++)
        {
            for (int column = columnOffset - puffer; column < columnOffset + numberOfColumnsToDraw; column++)
            {
                try
                {
                    // Abdunkel ab Zeile 4
//                    if (map.get(new Point(row, column)).getId() != 0 && map.get(new Point(row, column)).getId() != References.WOOD && map.get(new Point(row, column)).getId() != References.LEAF
//                            && map.get(new Point(row-1, column)).getId() != 0 && map.get(new Point(row-1, column)).getId() != References.WOOD && map.get(new Point(row-1, column)).getId() != References.LEAF
//                            && map.get(new Point(row-2, column)).getId() != 0 && map.get(new Point(row-2, column)).getId() != References.WOOD && map.get(new Point(row-2, column)).getId() != References.LEAF
//                            && map.get(new Point(row-3, column)).getId() != 0 && map.get(new Point(row-3, column)).getId() != References.WOOD && map.get(new Point(row-3, column)).getId() != References.LEAF)
//                    {
//                        graphics.setColor(Color.BLACK);
//                        graphics.fillRect((int) (map.get(new Point(row, column)).xTmp + this.x), (int) (map.get(new Point(row, column)).yTmp + this.y), References.TILE_SIZE, References.TILE_SIZE);
//                        continue;
//                    }

                    if (continueLevel)
                    {
                        if (minedTiles.containsKey(new Point(row, column)))
                        {
                            if (map.get(new Point(row, column)).getId() != minedTiles.get(new Point(row, column)))
                            {
                                map.get(new Point(row, column)).setTexture(References.getTextureById(minedTiles.get(new Point(row, column))));
                            }
                        }

                    }

                    map.get(new Point(row, column)).render(graphics, this.x, this.y);

                    if (map.get(new Point(row, column)).getId() == 0 && map.get(new Point(row-1, column)).getId() == References.LEAF)
                    {
                        map.get(new Point(row-1, column)).setTexture(null);
                        map.get(new Point(row-1, column)).setIsCollidable(false);
                        map.get(new Point(row-1, column)).setHasGravity(false);
                        map.get(new Point(row-1, column)).setIsDestructible(false);

                        // Veraenderung fuer Speichern festhalten
                        minedTiles.put(new Point(row-1, column), (byte)0);
                    }

                    if (map.get(new Point(row, column)).getId() == References.WATER && map.get(new Point(row, column-1)).getId() == 0)
                    {
                        if (map.get(new Point(row-1, column)).getTexture() != ResourceLoader.waterTop
                                || map.get(new Point(row-1, column)).getTexture() != ResourceLoader.waterTop2
                                || map.get(new Point(row-1, column)).getTexture() != ResourceLoader.water)
                        {
                            map.get(new Point(row, column-1)).setTexture(ResourceLoader.waterTop);
                            map.get(new Point(row, column-1)).setIsCollidable(false);
                            map.get(new Point(row, column-1)).setHasGravity(false);
                            map.get(new Point(row, column-1)).setIsDestructible(false);
                        }
                        else
                        {
                            map.get(new Point(row, column-1)).setTexture(ResourceLoader.water);
                            map.get(new Point(row, column-1)).setIsCollidable(false);
                            map.get(new Point(row, column-1)).setHasGravity(false);
                            map.get(new Point(row, column-1)).setIsDestructible(false);
                        }

                        // Veraenderung fuer Speichern festhalten
                        minedTiles.put(new Point(row-1, column), (byte)0);
                    }

                    // Zeichnen des Auswahlquadrates
                    if (player.getActiveAnimation() == Player.STILL)
                        drawAimRectangle(graphics);
                }
                catch (NullPointerException ignored) {}
            }
        }

        // Abdunkeln der Tiles bei Dunkelheit
        graphics.setColor(new Color(Background.red, Background.green, Background.blue, Background.opacity));
        graphics.fillRect(0, 0, References.SCREEN_WIDTH, References.SCREEN_HEIGHT);

        // Zeichnen der Tile Abbau-Splitter
        graphics.setColor(tileParticlesColor);
        if (particles != null && showParticles)
        {
            for (Rectangle particle : particles)
            {
                if (new Random().nextInt(10) > 4)
                    graphics.fillRect(particle.x + new Random().nextInt(10), particle.y, particle.width, particle.height);
                else
                    graphics.fillRect(particle.x - new Random().nextInt(10), particle.y, particle.width, particle.height);

            }

            showParticles = false;
        }

        // Zeichnen des Regens
        for (Rain drop : rain)
        {
            drop.render(graphics);
        }
    }

    /**
     * checkIfNotInRadius   Pruefen, ob Tile ausserhalb des Abbauradius liegt
     *
     * @param selectedTile  Tile
     * @return              Wert, ob Tile in Abbauradius liegt
     * */
    private boolean checkIfNotInRadius(Tile selectedTile)
    {
        return (selectedTile.getX() > player.getX()+this.x + mineRadius*References.TILE_SIZE || selectedTile.getX() < player.getX()+this.x - mineRadius*References.TILE_SIZE
                || selectedTile.getY() > player.getY()+this.y + mineRadius*References.TILE_SIZE || selectedTile.getY() < player.getY()+this.y - mineRadius*References.TILE_SIZE);
    }

    /**
     * drawAimRectangle     Zeichnen eines Auswahlquadrates ueber dem aktuellen Tile am Mauspunkt
     *
     * @param graphics      Graphics Objekt
     * */
    private void drawAimRectangle(Graphics graphics)
    {
        Tile selectedTile = map.get(new Point((int) ((References.MOUSE_Y - this.y) / References.TILE_SIZE), (int) (Math.floor((References.MOUSE_X - this.x) / References.TILE_SIZE))));
        if (checkIfNotInRadius(selectedTile)) return;

        if (selectedTile.getIsDestructible())
            graphics.fillRect(selectedTile.getX(), selectedTile.getY(), References.TILE_SIZE, References.TILE_SIZE);
    }

    /**
     * generate Tiles
     */
    private void generateTiles()
    {
        for (int column = columnOffset - puffer; column < columnOffset + numberOfColumnsToDraw; column++)
        {
            for (int row = rowOffset - puffer; row < rowOffset + numberOfRowsToDraw; row++)
            {
                // Huegel-Position ermitteln
                calculateHills();

                // Fuellen des Sees
                fillLake(row, column);

                if (row >= rowTmp)
                {
                    if (map.get(new Point(row, column)) == null)
                    {
                        try
                        {
                            // Wueste
                            if ( (this.x <= 0 && this.x <= -7*References.SCREEN_WIDTH) || (this.x > 0 && this.x < 7*References.SCREEN_WIDTH) )
                            {
                                generateDesertBiome(row, column);
                                continue;
                            }

                            // Schnee
                            if ( (this.x <= 0 && this.x <= -4*References.SCREEN_WIDTH) || (this.x > 0 && this.x < 4*References.SCREEN_WIDTH) )
                            {
                                generateIceBiome(row, column);
                                continue;
                            }

                            // Jungle
                            generateJungleBiome(row, column);

                        }
                        catch (NullPointerException ignored)
                        {}
                    }
                } else if (map.get(new Point(row, column)) == null)
                    map.put(new Point(row, column), new Tile(null, column * References.TILE_SIZE, row * References.TILE_SIZE, row, column, false, false, false));
            }
        }
    }

    /**
     * generateDesertBiome      Generieren des Wuesten Bioms
     *
     * @param row    Aktuelle Zeile
     * @param column Aktuelle Spalte
     */
    private void generateDesertBiome(int row, int column)
    {
        // Kaktus
        if (map.get(new Point(row - 1, column)).getTexture() == null)
        {
            if (randNumbersIndex >= randomNumbers.length) randNumbersIndex = 0;

            if (Math.abs(column % seed) < randomNumbers[randNumbersIndex] + 1 && ((this.x <= 0 && map.get(new Point(row - 1, column - 1)).getTexture() == null) || (this.x > 0 && map.get(new Point(row - 1, column + 1)).getTexture() == null)))
            {
                map.put(new Point(row - 1, column), new Tile(ResourceLoader.cactusRoot, column * References.TILE_SIZE, (row - 1) * References.TILE_SIZE, row - 1, column, false, false, true));

                // Baumhoehe
                if (treeHeight > 14) treeHeight = 14;
                else if (treeHeight < 8) treeHeight = 12;

                // Baumstamm
                for (int i = 2; i < treeHeight; i++)
                {
                    if (treeTextureCounter >= cactusTextures.length) treeTextureCounter = 0;

                    map.put(new Point(row - i, column), new Tile(cactusTextures[treeTextureCounter], column * References.TILE_SIZE, (row - i) * References.TILE_SIZE, row - i, column, false, false, true));
                    treeTextureCounter++;
                }
                map.put(new Point(row - treeHeight, column), new Tile(ResourceLoader.cactusTop, column * References.TILE_SIZE, (row - treeHeight) * References.TILE_SIZE, row - treeHeight, column, false, false, true));
            } else
                map.put(new Point(row - 1, column), new Tile(null, column * References.TILE_SIZE, (row - 1) * References.TILE_SIZE, row - 1, column, false, false, false));
        }

        // Wueste oder Wasser
        if (map.get(new Point(row - 1, column)).getTexture() == null)
        {
            if (!shouldPlaceWater) if (new Random(randomizeSeed).nextInt(1000) == 10) shouldPlaceWater = true;

            if (shouldPlaceWater && !Arrays.asList(cactusOnlyTextues).contains(map.get(new Point(row - 1, column)).getTexture()))
                map.put(new Point(row, column), new Tile(ResourceLoader.waterTop, column * References.TILE_SIZE, row * References.TILE_SIZE, row, column, false, false, false));
            else
                map.put(new Point(row, column), new Tile(ResourceLoader.sandTop, column * References.TILE_SIZE, row * References.TILE_SIZE, row, column, true, true, true));
        }
        // Sand Oberflaeche
        else if (Arrays.asList(waterTextures).contains(map.get(new Point(row - 1, column)).getTexture()) || Arrays.asList(treeOnlyTextures).contains(map.get(new Point(row - 1, column)).getTexture()))
            map.put(new Point(row, column), new Tile(ResourceLoader.sandTop, column * References.TILE_SIZE, row * References.TILE_SIZE, row, column, true, true, true));
            // Edelsteine
        else if (Arrays.asList(sandTextures).contains(map.get(new Point(row - 1, column)).getTexture()))
        {
            if (new Random(randomizeSeed).nextInt(100) < 3)
                map.put(new Point(row, column), new Tile(gemsTextures[new Random(gemsSeed).nextInt(gemsTextures.length)], column * References.TILE_SIZE, row * References.TILE_SIZE, row, column, true, true, true));
            else
                map.put(new Point(row, column), new Tile(sandTextures[new Random().nextInt(sandTextures.length)], column * References.TILE_SIZE, row * References.TILE_SIZE, row, column, true, true, true));

            // Updaten der Seedvariablen
            randomizeSeed++;
            //if (randomizeSeed >= Integer.MAX_VALUE - 1) randomizeSeed = 1;
            gemsSeed++;
            //if (gemsSeed >= Integer.MAX_VALUE - 1) gemsSeed = 1;
        }
        // Sand Boden
        else
            map.put(new Point(row, column), new Tile(sandTextures[new Random().nextInt(sandTextures.length)], column * References.TILE_SIZE, row * References.TILE_SIZE, row, column, true, true, true));
    }

    /**
     * generateIceBiome      Generieren des Schnee Bioms
     *
     * @param row    Aktuelle Zeile
     * @param column Aktuelle Spalte
     */
    private void generateIceBiome(int row, int column)
    {
        // Baum schneebedeckt
        if (map.get(new Point(row - 1, column)).getTexture() == null)
        {
            if (randNumbersIndex >= randomNumbers.length) randNumbersIndex = 0;

            if (Math.abs(column % seed) < randomNumbers[randNumbersIndex] + 1 && ((this.x <= 0 && map.get(new Point(row - 1, column - 1)).getTexture() == null) || (this.x > 0 && map.get(new Point(row - 1, column + 1)).getTexture() == null)))
            {
                map.put(new Point(row - 1, column), new Tile(ResourceLoader.treeTrunkRoot, column * References.TILE_SIZE, (row - 1) * References.TILE_SIZE, row - 1, column, false, false, true));

                // Baumhoehe
                if (treeHeight > 14) treeHeight = 14;
                else if (treeHeight < 8) treeHeight = 12;

                // Baumstamm
                for (int i = 2; i < treeHeight; i++)
                {
                    if (treeTextureCounter >= treeTrunkTextures.length) treeTextureCounter = 0;


                    map.put(new Point(row - i, column), new Tile(treeTrunkTextures[treeTextureCounter], column * References.TILE_SIZE, (row - i) * References.TILE_SIZE, row - i, column, false, false, true));
                    treeTextureCounter++;
                }
                map.put(new Point(row - treeHeight, column), new Tile(ResourceLoader.treeTrunkRootSnow, column * References.TILE_SIZE, (row - treeHeight) * References.TILE_SIZE, row - treeHeight, column, false, false, true));

                // Baumkrone
                map.put(new Point(row - treeHeight - 1, column), new Tile(ResourceLoader.treeTrunkAfterRootSnow, column * References.TILE_SIZE, (row - treeHeight - 1) * References.TILE_SIZE, row - treeHeight - 1, column, false, false, true));
                for (int i = 2; i < treeHeight; i++)
                {
                    if (treeTextureCounter >= treeTrunkTextures.length) treeTextureCounter = 0;

                    map.put(new Point(row - treeHeight - i, column), new Tile(ResourceLoader.treeTrunkSnow, column * References.TILE_SIZE, (row - treeHeight - i) * References.TILE_SIZE, row - treeHeight - i, column, false, false, true));
                    treeTextureCounter++;
                }
                map.put(new Point(row - 2 * treeHeight, column), new Tile(ResourceLoader.treeTrunkSnowEnd, column * References.TILE_SIZE, (row - 2 * treeHeight) * References.TILE_SIZE, row - 2 * treeHeight, column, false, false, true));
            } else
                map.put(new Point(row - 1, column), new Tile(null, column * References.TILE_SIZE, (row - 1) * References.TILE_SIZE, row - 1, column, false, false, false));
        }

        // Schnee Oberflaeche
        if (map.get(new Point(row - 1, column)).getTexture() == null)
        {
            if (!shouldPlaceWater) if (new Random(randomizeSeed).nextInt(1000) < 10) shouldPlaceWater = true;

            if (shouldPlaceWater && !Arrays.asList(treeSnowOnlyTextures).contains(map.get(new Point(row - 1, column)).getTexture()))
                map.put(new Point(row, column), new Tile(ResourceLoader.waterTop, column * References.TILE_SIZE, row * References.TILE_SIZE, row, column, false, false, false));
            else
                map.put(new Point(row, column), new Tile(ResourceLoader.iceTop, column * References.TILE_SIZE, row * References.TILE_SIZE, row, column, true, true, true));
        }
        // Schnee Oberflaeche
        else if (Arrays.asList(waterTextures).contains(map.get(new Point(row - 1, column)).getTexture()) || Arrays.asList(treeSnowOnlyTextures).contains(map.get(new Point(row - 1, column)).getTexture()))
            map.put(new Point(row, column), new Tile(ResourceLoader.iceTop, column * References.TILE_SIZE, row * References.TILE_SIZE, row, column, true, true, true));
            // Edelsteine
        else if (Arrays.asList(iceTextures).contains(map.get(new Point(row - 1, column)).getTexture()))
        {
            if (new Random(randomizeSeed).nextInt(100) < 3)
                map.put(new Point(row, column), new Tile(gemsTextures[new Random(gemsSeed).nextInt(gemsTextures.length)], column * References.TILE_SIZE, row * References.TILE_SIZE, row, column, true, true, true));
            else
                map.put(new Point(row, column), new Tile(iceTextures[new Random().nextInt(iceTextures.length)], column * References.TILE_SIZE, row * References.TILE_SIZE, row, column, true, true, true));

            // Updaten der Seedvariablen
            randomizeSeed++;
            //if (randomizeSeed >= Integer.MAX_VALUE - 1) randomizeSeed = 1;
            gemsSeed++;
            //if (gemsSeed >= Integer.MAX_VALUE - 1) gemsSeed = 1;
        }
        // Schnee Boden
        else
            map.put(new Point(row, column), new Tile(iceTextures[new Random().nextInt(iceTextures.length)], column * References.TILE_SIZE, row * References.TILE_SIZE, row, column, true, true, true));
    }

    /**
     * generateJungleBiome      Generieren des Jungle Bioms
     *
     * @param row    Aktuelle Zeile
     * @param column Aktuelle Spalte
     */
    private void generateJungleBiome(int row, int column)
    {
        // Baum
        if (map.get(new Point(row - 1, column)).getTexture() == null)
        {
            if (randNumbersIndex >= randomNumbers.length) randNumbersIndex = 0;

            if (Math.abs(column % seed) < randomNumbers[randNumbersIndex] + 1 && ((this.x <= 0 && map.get(new Point(row - 1, column - 1)).getTexture() == null) || (this.x > 0 && map.get(new Point(row - 1, column + 1)).getTexture() == null)))
            {
                map.put(new Point(row - 1, column), new Tile(ResourceLoader.treeTrunkRoot, column * References.TILE_SIZE, (row - 1) * References.TILE_SIZE, row - 1, column, false, false, true));

                // Baumhoehe
                if (treeHeight > 14) treeHeight = 14;
                else if (treeHeight < 8) treeHeight = 12;

                // Baumstamm
                for (int i = 2; i < treeHeight; i++)
                {
                    if (treeTextureCounter >= treeTrunkTextures.length) treeTextureCounter = 0;

                    map.put(new Point(row - i, column), new Tile(treeTrunkTextures[treeTextureCounter], column * References.TILE_SIZE, (row - i) * References.TILE_SIZE, row - i, column, false, false, true));
                    treeTextureCounter++;
                }
                map.put(new Point(row - treeHeight, column), new Tile(ResourceLoader.treeTrunkTop, column * References.TILE_SIZE, (row - treeHeight) * References.TILE_SIZE, row - treeHeight, column, false, false, true));

                // Baumkrone
                map.put(new Point(row - treeHeight - 1, column), new Tile(ResourceLoader.leafStart, column * References.TILE_SIZE, (row - treeHeight - 1) * References.TILE_SIZE, row - treeHeight - 1, column, false, false, true));
                for (int i = 2; i < treeHeight; i++)
                {
                    if (treeTextureCounter >= treeTrunkTextures.length) treeTextureCounter = 0;

                    map.put(new Point(row - treeHeight - i, column), new Tile(ResourceLoader.leaf, column * References.TILE_SIZE, (row - treeHeight - i) * References.TILE_SIZE, row - treeHeight - i, column, false, false, true));
                    treeTextureCounter++;
                }
                map.put(new Point(row - 2 * treeHeight, column), new Tile(ResourceLoader.leaf, column * References.TILE_SIZE, (row - 2 * treeHeight) * References.TILE_SIZE, row - 2 * treeHeight, column, false, false, true));
                map.put(new Point(row - 2 * treeHeight - 1, column), new Tile(ResourceLoader.leafEnd, column * References.TILE_SIZE, (row - 2 * treeHeight - 1) * References.TILE_SIZE, row - 2 * treeHeight - 1, column, false, false, true));
            } else
                map.put(new Point(row - 1, column), new Tile(null, column * References.TILE_SIZE, (row - 1) * References.TILE_SIZE, row - 1, column, false, false, false));
        }

        // Gras und Wasser
        if (map.get(new Point(row - 1, column)).getTexture() == null)
        {
            if (!shouldPlaceWater) if (new Random(randomizeSeed).nextInt(500) < 5) shouldPlaceWater = true;

            if (shouldPlaceWater && !Arrays.asList(treeOnlyTextures).contains(map.get(new Point(row - 1, column)).getTexture()))
                map.put(new Point(row, column), new Tile(ResourceLoader.waterTop, column * References.TILE_SIZE, row * References.TILE_SIZE, row, column, false, false, false));
            else
                map.put(new Point(row, column), new Tile(grasOnlyTextures[new Random().nextInt(grasOnlyTextures.length)], column * References.TILE_SIZE, row * References.TILE_SIZE, row, column, true, true, true));
        }
        // Gras
        else if (Arrays.asList(waterTextures).contains(map.get(new Point(row - 1, column)).getTexture()) || Arrays.asList(treeOnlyTextures).contains(map.get(new Point(row - 1, column)).getTexture()))
            map.put(new Point(row, column), new Tile(grasOnlyTextures[new Random().nextInt(grasOnlyTextures.length)], column * References.TILE_SIZE, row * References.TILE_SIZE, row, column, true, true, true));
            // Edelsteine
        else if (Arrays.asList(dirtOnlyTextures).contains(map.get(new Point(row - 1, column)).getTexture()))
        {
            if (new Random(randomizeSeed).nextInt(100) < 3)
                map.put(new Point(row, column), new Tile(gemsTextures[new Random(gemsSeed).nextInt(gemsTextures.length)], column * References.TILE_SIZE, row * References.TILE_SIZE, row, column, true, true, true));
            else
                map.put(new Point(row, column), new Tile(dirtOnlyTextures[new Random().nextInt(dirtOnlyTextures.length)], column * References.TILE_SIZE, row * References.TILE_SIZE, row, column, true, true, true));

            // Updaten der Seedvariablen
            randomizeSeed++;
            //if (randomizeSeed >= Integer.MAX_VALUE-1) randomizeSeed = 1;
            gemsSeed++;
            //if (gemsSeed >= Integer.MAX_VALUE-1) gemsSeed = 1;
        }
        // Erde
        else
            map.put(new Point(row, column), new Tile(dirtOnlyTextures[new Random().nextInt(dirtOnlyTextures.length)], column * References.TILE_SIZE, row * References.TILE_SIZE, row, column, true, true, true));

    }

    /**
     * calculateHills       Ermitteln der Heugel Position
     */
    private void calculateHills()
    {
        if ((int) Math.abs(Player.xForTileMap) > xForTileMapStart + (References.SCREEN_WIDTH / 4))
        {
            if (randNumbersIndex >= randomNumbers.length) randNumbersIndex = 0;

            xForTileMapStart += 50 * randomNumbers[randNumbersIndex];

            if (isMountain)
            {
                rowTmp += randomNumbers[randNumbersIndex];
                shouldPlaceWater = false;
            } else
            {
                rowTmp -= randomNumbers[randNumbersIndex];
                shouldPlaceWater = false;
            }

            if (rowTmp > (References.SCREEN_HEIGHT / References.TILE_SIZE / 2) + 10)
            {
                rowTmp = (References.SCREEN_HEIGHT / References.TILE_SIZE / 2) + 10;
                isMountain = false;
            } else if (rowTmp < (References.SCREEN_HEIGHT / References.TILE_SIZE / 2) - 5)
            {
                rowTmp = (References.SCREEN_HEIGHT / References.TILE_SIZE / 2) - 5;
                isMountain = true;
            }

            randNumbersIndex++;
        }
    }

    /**
     * fillLake         Fuellen des Sees
     *
     * @param row    Aktuelle Zeile
     * @param column Aktuelle Spalte
     */
    private void fillLake(int row, int column)
    {
        try
        {
            if (row < rowTmp && map.get(new Point(row, column)) == null && (Arrays.asList(waterTextures).contains(map.get(new Point(row, column - 1)).getTexture()) || Arrays.asList(waterTextures).contains(map.get(new Point(row, column - 2)).getTexture()) || Arrays.asList(waterTextures).contains(map.get(new Point(row - 1, column)).getTexture())))
            {
                if (map.get(new Point(row - 1, column)).getTexture() == null)
                    map.put(new Point(row, column), new Tile(ResourceLoader.waterTop, column * References.TILE_SIZE, row * References.TILE_SIZE, row, column, false, false, false));
                else
                    map.put(new Point(row, column), new Tile(ResourceLoader.water, column * References.TILE_SIZE, row * References.TILE_SIZE, row, column, false, false, false));
            }
        }
        catch (NullPointerException ignored)
        {
        }
    }

    /**
     * generateParticles        Erstellen von kleinen Truemmer-Teilen
     *
     * @param point             Generierungspunkt
     * @param color             Farbe der Truemmer
     * @param numberOfParticles Anzahl der Truemmer
     * @param size              Groesse der Truemmer
     */
    public void generateParticles(Point point, Color color, int numberOfParticles, int size)
    {
        tileParticlesColor = color;

        if (particles == null || particles.length < numberOfParticles)
        {
            particles = new Rectangle[numberOfParticles];

            for (int i = 0; i < particles.length; i++)
            {
                particles[i] = new Rectangle(
                        (int) Math.abs(point.getX() + (new Random().nextInt(11) - new Random().nextInt(20))),
                        (int) Math.abs(point.getY() + (new Random().nextInt(11) - new Random().nextInt(30))),
                        size,
                        size
                );
            }
        }
        else
        {
            for (Rectangle particle : particles)
            {
                particle.setBounds(
                        (int) Math.abs(point.getX() + (new Random().nextInt(11) - new Random().nextInt(20))),
                        (int) Math.abs(point.getY() + (new Random().nextInt(11) - new Random().nextInt(30))),
                        size,
                        size
                );
            }
        }

        showParticles = true;
    }

    // GETTER UND SETTER
    /**
     * setPosition              Setzen der Map Position, sowie des Offsets
     *
     * @param x x-Koordinate
     * @param y y-Koordinate
     */
    public void setPosition(double x, double y)
    {
        this.x = x;
        this.y = y;

        this.columnOffset = (int) (-this.x / References.TILE_SIZE);
        this.rowOffset = (int) (-this.y / References.TILE_SIZE);
    }

    /**
     * getX                     Rueckgabe der Map x-Koordinate
     * @return int              x-Koordinate
     */
    public double getX() { return this.x; }

    /**
     * getY                     Rueckgabe der Map y-Koordinate
     * @return int              y-Koordinate
     */
    public double getY() { return this.y; }

    /**
     * getMap                   Rueckgabe der Map
     * @return Spielwelt
     */
    public Map<Point, Tile> getMap() { return map; }

    /**
     * setPlayer                Setzen des Spielers
     * @param player            Spieler
     * */
    public void setPlayer(Player player) { this.player = player; }

    /**
     * setXForTileMapStart      Setzen der Tilemap Position fuer die Zufallgenerierung
     * */
    public void setXForTileMapStart() { this.xForTileMapStart = this.player.getX(); }

    // LISTENER
    /**
     * mouseClicked         Mausklick
     * @param e             Mausevent
     * */
    public void mouseClicked(MouseEvent e)
    {
        if (!Inventory.isDrawerOpen && !Crafting.isCraftBenchOpen)
        {
            Tile selectedTile = map.get(new Point((int) ((e.getY() - this.y) / References.TILE_SIZE), (int) (Math.floor((e.getX() - this.x) / References.TILE_SIZE))));

            // Nur Abbauen wenn Tile im Abbauradius liegt
            if (checkIfNotInRadius(selectedTile)) return;

            //Schalter dr�cken
            if (selectedTile.getId() == References.SWITCH_OFF)
            {
                Tile.setNeighbors(selectedTile, true, this);
                selectedTile.setTexture(ResourceLoader.switchOn);
            }
            else if (selectedTile.getId() == References.SWITCH_ON)
            {
                Tile.setNeighbors(selectedTile, false, this);
                selectedTile.setTexture(ResourceLoader.switchOff);
            }
            // Abbauen der Rohstoffe
            else if (selectedTile.getId() > 0 && selectedTile.getIsDestructible())
            {
                // Picke ausgewaehlt
                if (Inventory.invBar[Inventory.selected].getId() == References.PICK && map.get(new Point(selectedTile.getRow() - 1, selectedTile.getColumn())).getId() != References.WOOD
                        && (
                            selectedTile.getId() == References.DIRT || selectedTile.getId() == References.GRAS || selectedTile.getId() == References.ION || selectedTile.getId() == References.COPPER
                            || selectedTile.getId() == References.SILVER || selectedTile.getId() == References.GOLD || selectedTile.getId() == References.RUBY
                            || selectedTile.getId() == References.SAPHIRE || selectedTile.getId() == References.SMARAGD || selectedTile.getId() == References.DIAMOND
                            || selectedTile.getId() == References.ICE || selectedTile.getId() == References.SAND || selectedTile.getId() == References.STONE || selectedTile.getId() == References.STONE_RED
                        ))
                {
                    if (selectedTile.getResistance() > 0)
                    {
                        if (GameData.isSoundOn.equals("On")) Sound.earthSound.play();

                        if (selectedTile.getId() == References.DIRT) generateParticles(e.getPoint(), DIRT, 20, 2);
                        else if (selectedTile.getId() == References.GRAS) generateParticles(e.getPoint(), GRAS, 20, 2);
                        else if (selectedTile.getId() == References.ICE) generateParticles(e.getPoint(), Color.WHITE, 20, 2);
                        else if (selectedTile.getId() == References.SAND) generateParticles(e.getPoint(), SAND, 20, 2);
                        else if (selectedTile.getId() == References.STONE) generateParticles(e.getPoint(), STONE, 20, 2);
                        else if (selectedTile.getId() == References.STONE_RED) generateParticles(e.getPoint(), STONE_RED, 20, 2);
                        else if (selectedTile.getId() == References.GOLD) generateParticles(e.getPoint(), GOLD, 20, 2);
                        else if (selectedTile.getId() == References.SILVER) generateParticles(e.getPoint(), Color.LIGHT_GRAY, 20, 2);
                        else if (selectedTile.getId() == References.ION) generateParticles(e.getPoint(), Color.BLACK, 20, 2);
                        else if (selectedTile.getId() == References.COPPER) generateParticles(e.getPoint(), COPPER, 20, 2);
                        else if (selectedTile.getId() == References.RUBY) generateParticles(e.getPoint(), RUBY, 20, 2);
                        else if (selectedTile.getId() == References.SAPHIRE) generateParticles(e.getPoint(), SAPHIRE, 20, 2);
                        else if (selectedTile.getId() == References.SMARAGD) generateParticles(e.getPoint(), SMARAGD, 20, 2);
                        else if (selectedTile.getId() == References.DIAMOND) generateParticles(e.getPoint(), Color.WHITE, 20, 2);
                        else generateParticles(e.getPoint(), Color.WHITE, 20, 2);

                        selectedTile.setResistance((byte) (selectedTile.getResistance() - Weapon.PICK_DAMAGE));
                    }
                    // Wasser fuellt die Luecken
                    else if (map.get(new Point(selectedTile.getRow() - 1, selectedTile.getColumn())).getId() == References.WATER
                            || map.get(new Point(selectedTile.getRow() + 1, selectedTile.getColumn())).getId() == References.WATER
                            || map.get(new Point(selectedTile.getRow(), selectedTile.getColumn() - 1)).getId() == References.WATER
                            || map.get(new Point(selectedTile.getRow(), selectedTile.getColumn() + 1)).getId() == References.WATER)
                    {
                        try
                        {
                            if (map.get(new Point(selectedTile.getRow() - 1, selectedTile.getColumn())).getId() == References.WATER)
                            {
                                int rowOfTopWater;
                                int i = 1;
                                while (map.get(new Point(selectedTile.getRow() - i, selectedTile.getColumn())).getTexture() != ResourceLoader.waterTop)
                                {
                                    i++;
                                }
                                rowOfTopWater = i;

                                if (i == 1)
                                {
                                    Inventory.addToInventory(selectedTile.getId());
                                    selectedTile.setTexture(ResourceLoader.waterTop);
                                    selectedTile.setIsCollidable(false);
                                    selectedTile.setHasGravity(false);
                                    selectedTile.setIsDestructible(false);

                                    // Veraenderung fuer Speichern festhalten
                                    minedTiles.put(new Point(selectedTile.getRow(), selectedTile.getColumn()), References.WATER);
                                }
                                else
                                {
                                    Inventory.addToInventory(selectedTile.getId());
                                    selectedTile.setTexture(ResourceLoader.water);
                                    selectedTile.setIsCollidable(false);
                                    selectedTile.setHasGravity(false);
                                    selectedTile.setIsDestructible(false);

                                    // Veraenderung fuer Speichern festhalten
                                    minedTiles.put(new Point(selectedTile.getRow(), selectedTile.getColumn()), References.WATER);
                                }

                                i = 0;
                                int j = 0;
                                while (map.get(new Point(selectedTile.getRow() - rowOfTopWater, selectedTile.getColumn() + i)).getTexture() == ResourceLoader.waterTop
                                        || map.get(new Point(selectedTile.getRow() - rowOfTopWater, selectedTile.getColumn() + i)).getTexture() == ResourceLoader.waterTop2)
                                {
                                    while (map.get(new Point(selectedTile.getRow() - rowOfTopWater, selectedTile.getColumn() - j)).getTexture() == ResourceLoader.waterTop
                                            || map.get(new Point(selectedTile.getRow() - rowOfTopWater, selectedTile.getColumn() - j)).getTexture() == ResourceLoader.waterTop2)
                                    {
                                        map.get(new Point(selectedTile.getRow() - rowOfTopWater, selectedTile.getColumn() - j)).setTexture(null);
                                        map.get(new Point(selectedTile.getRow() - rowOfTopWater, selectedTile.getColumn() - j)).setIsCollidable(false);
                                        map.get(new Point(selectedTile.getRow() - rowOfTopWater, selectedTile.getColumn() - j)).setHasGravity(false);
                                        map.get(new Point(selectedTile.getRow() - rowOfTopWater, selectedTile.getColumn() - j)).setIsDestructible(false);

                                        // Veraenderung fuer Speichern festhalten
                                        minedTiles.put(new Point(selectedTile.getRow() - rowOfTopWater, selectedTile.getColumn() - j), (byte) 0);

                                        if (map.get(new Point(selectedTile.getRow() - rowOfTopWater + 1, selectedTile.getColumn() - j)).getTexture() == ResourceLoader.water)
                                        {
                                            map.get(new Point(selectedTile.getRow() - rowOfTopWater + 1, selectedTile.getColumn() - j)).setTexture(ResourceLoader.waterTop);

                                            // Veraenderung fuer Speichern festhalten
                                            minedTiles.put(new Point(selectedTile.getRow() - rowOfTopWater + 1, selectedTile.getColumn() - j), References.WATER);
                                        }

                                        j++;
                                    }

                                    map.get(new Point(selectedTile.getRow() - rowOfTopWater, selectedTile.getColumn() + i)).setTexture(null);
                                    map.get(new Point(selectedTile.getRow() - rowOfTopWater, selectedTile.getColumn() + i)).setIsCollidable(false);
                                    map.get(new Point(selectedTile.getRow() - rowOfTopWater, selectedTile.getColumn() + i)).setHasGravity(false);
                                    map.get(new Point(selectedTile.getRow() - rowOfTopWater, selectedTile.getColumn() + i)).setIsDestructible(false);

                                    // Veraenderung fuer Speichern festhalten
                                    minedTiles.put(new Point(selectedTile.getRow() - rowOfTopWater, selectedTile.getColumn() + i), (byte) 0);

                                    if (map.get(new Point(selectedTile.getRow() - rowOfTopWater + 1, selectedTile.getColumn() + i)).getTexture() == ResourceLoader.water)
                                    {
                                        map.get(new Point(selectedTile.getRow() - rowOfTopWater + 1, selectedTile.getColumn() + i)).setTexture(ResourceLoader.waterTop);

                                        // Veraenderung fuer Speichern festhalten
                                        minedTiles.put(new Point(selectedTile.getRow() - rowOfTopWater + 1, selectedTile.getColumn() + i), (byte) 0);
                                    }

                                    i++;
                                }
                            }
                            else
                            {
                                Inventory.addToInventory(selectedTile.getId());

                                if (map.get(new Point(selectedTile.getRow() - 1, selectedTile.getColumn())).getTexture() == null)
                                    selectedTile.setTexture(ResourceLoader.waterTop);
                                else selectedTile.setTexture(ResourceLoader.water);

                                selectedTile.setIsCollidable(false);
                                selectedTile.setHasGravity(false);
                                selectedTile.setIsDestructible(false);

                                // Veraenderung fuer Speichern festhalten
                                minedTiles.put(new Point(selectedTile.getRow(), selectedTile.getColumn()), References.WATER);
                            }
                        } catch (Exception ex) { ex.printStackTrace(); }
                    }
                    else
                    {
                        Inventory.addToInventory(selectedTile.getId());

                        if (player != null) player.incExperience(selectedTile.getEpBonus());

                        Tutorial.solveTut(Tutorial.TUT_DESTROY_BLOCK);
                        if (lastCollected != null && selectedTile.getId() != lastCollected.getId())
                            Tutorial.solveTut(Tutorial.TUT_COLLECT_MORE);
                        lastCollected = selectedTile;

                        selectedTile.setTexture(null);
                        selectedTile.setIsCollidable(false);
                        selectedTile.setHasGravity(false);
                        selectedTile.setIsDestructible(false);

                        // Veraenderung fuer Speichern festhalten
                        minedTiles.put(new Point(selectedTile.getRow(), selectedTile.getColumn()), (byte) 0);
                    }
                }
                // Axt ausgewaehlt
                else if (Inventory.invBar[Inventory.selected].getId() == References.AXE && (selectedTile.getId() == References.WOOD || selectedTile.getId() == References.LEAF))
                {
                    if (selectedTile.getResistance() > 0)
                    {
                        if (GameData.isSoundOn.equals("On")) Sound.woodSound.play();

                        if (selectedTile.getId() == References.WOOD) generateParticles(e.getPoint(), TREE, 20, 2);
                        else if (selectedTile.getId() == References.LEAF) generateParticles(e.getPoint(), LEAF, 20, 2); // TODO particles spray to left or right

                        selectedTile.setResistance((byte) (selectedTile.getResistance() - Weapon.AXE_DAMAGE));
                    }
                    else
                    {
                        byte treeHeight = 0;

                        try
                        {
                            while (map.get(new Point(selectedTile.getRow() - treeHeight, selectedTile.getColumn())).getId() == References.WOOD
                                    || map.get(new Point(selectedTile.getRow() - treeHeight, selectedTile.getColumn())).getId() == References.LEAF)
                            {
                                map.get(new Point(selectedTile.getRow() - treeHeight, selectedTile.getColumn())).setTexture(null);
                                map.get(new Point(selectedTile.getRow() - treeHeight, selectedTile.getColumn())).setIsCollidable(false);
                                map.get(new Point(selectedTile.getRow() - treeHeight, selectedTile.getColumn())).setHasGravity(false);
                                map.get(new Point(selectedTile.getRow() - treeHeight, selectedTile.getColumn())).setIsDestructible(false);

                                // Veraenderung fuer Speichern festhalten
                                minedTiles.put(new Point(selectedTile.getRow() - treeHeight, selectedTile.getColumn()), (byte) 0);

                                treeHeight++;
                            }

                            treeHeight = 0;
                            while (map.get(new Point(selectedTile.getRow() + treeHeight, selectedTile.getColumn())).getId() == References.WOOD
                                    || map.get(new Point(selectedTile.getRow() + treeHeight, selectedTile.getColumn())).getId() == References.LEAF)
                            {
                                map.get(new Point(selectedTile.getRow() + treeHeight, selectedTile.getColumn())).setTexture(null);
                                map.get(new Point(selectedTile.getRow() + treeHeight, selectedTile.getColumn())).setIsCollidable(false);
                                map.get(new Point(selectedTile.getRow() + treeHeight, selectedTile.getColumn())).setHasGravity(false);
                                map.get(new Point(selectedTile.getRow() + treeHeight, selectedTile.getColumn())).setIsDestructible(false);

                                // Veraenderung fuer Speichern festhalten
                                minedTiles.put(new Point(selectedTile.getRow() + treeHeight, selectedTile.getColumn()), (byte) 0);

                                treeHeight++;
                            }
                        } catch (NullPointerException ignored) {}

                        // Holz und Blatt zum Inventar hinzufuegen
                        Inventory.addToInventory(References.WOOD);
                        Inventory.addToInventory(References.WOOD);
                        Inventory.addToInventory(References.WOOD);
                        Inventory.addToInventory(References.WOOD);
                        Inventory.addToInventory(References.WOOD);
                        Inventory.addToInventory(References.WOOD);

                        Inventory.addToInventory(References.LEAF);
                        Inventory.addToInventory(References.LEAF);
                        Inventory.addToInventory(References.LEAF);
                        Inventory.addToInventory(References.LEAF);
                    }
                }
                // Hammer ausgewaehlt
                else if (Inventory.invBar[Inventory.selected].getId() == References.HAMMER
                        && (
                        selectedTile.getId() == References.ION || selectedTile.getId() == References.COPPER
                        || selectedTile.getId() == References.SILVER || selectedTile.getId() == References.GOLD || selectedTile.getId() == References.RUBY
                        || selectedTile.getId() == References.SAPHIRE || selectedTile.getId() == References.SMARAGD || selectedTile.getId() == References.DIAMOND
                        ))
                {
                    if (selectedTile.getResistance() > 0)
                    {
                        if (GameData.isSoundOn.equals("On")) Sound.metalSound.play();

                        if (selectedTile.getId() == References.GOLD) generateParticles(e.getPoint(), GOLD, 20, 2);
                        else if (selectedTile.getId() == References.SILVER) generateParticles(e.getPoint(), Color.LIGHT_GRAY, 20, 2);
                        else if (selectedTile.getId() == References.ION) generateParticles(e.getPoint(), Color.BLACK, 20, 2);
                        else if (selectedTile.getId() == References.COPPER) generateParticles(e.getPoint(), COPPER, 20, 2);
                        else if (selectedTile.getId() == References.RUBY) generateParticles(e.getPoint(), RUBY, 20, 2);
                        else if (selectedTile.getId() == References.SAPHIRE) generateParticles(e.getPoint(), SAPHIRE, 20, 2);
                        else if (selectedTile.getId() == References.SMARAGD) generateParticles(e.getPoint(), SMARAGD, 20, 2);
                        else if (selectedTile.getId() == References.DIAMOND) generateParticles(e.getPoint(), Color.WHITE, 20, 2);
                        else generateParticles(e.getPoint(), Color.WHITE, 20, 2);

                        selectedTile.setResistance((byte) (selectedTile.getResistance() - Weapon.HAMMER_DAMAGE));
                    }
                    else
                    {
                        // Zum Inventar hinzufuegen
                        Inventory.addToInventory(selectedTile.getId());

                        selectedTile.setTexture(null);
                        selectedTile.setIsCollidable(false);
                        selectedTile.setHasGravity(false);
                        selectedTile.setIsDestructible(false);

                        // Veraenderung fuer Speichern festhalten
                        minedTiles.put(new Point(selectedTile.getRow(), selectedTile.getColumn()), (byte) 0);
                    }
                }
            }
            // Bauen
            else if (selectedTile.getId() == (byte) 0 && map.get(new Point(selectedTile.getRow() + 1, selectedTile.getColumn())).getId() != References.WATER)
            {
                boolean sheetingableTileIsSelected = false;
                for (Byte id : References.SHEETINGABLE_TILES)
                    if (id == Inventory.invBar[Inventory.selected].getId())
                    {
                        sheetingableTileIsSelected = true;
                        break;
                    }

                if (sheetingableTileIsSelected)
                {
                    if (Inventory.invBar[Inventory.selected].getId() == References.BATTERY)
                    {
                        selectedTile.setTexture(ResourceLoader.battery);
                        selectedTile.setIsCollidable(true);
                        selectedTile.setHasGravity(false);
                        selectedTile.setIsDestructible(false);
                        Tile[] neighbors = Tile.getNeighbors(selectedTile, this);
                        for (int i = 0; i < 4; i++) {
                            Tile.setNeighbors(neighbors[i], true, this);
                        }

                        // Veraenderung fuer Speichern festhalten
                        minedTiles.put(new Point(selectedTile.getRow(), selectedTile.getColumn()), References.BATTERY);
                    }
                    else if (Inventory.invBar[Inventory.selected].getId() == References.NANDR)
                    {
                        Tile.setNANDR(selectedTile, this);
                        selectedTile.setTexture(ResourceLoader.NANDR);
                        selectedTile.setIsCollidable(true);
                        selectedTile.setHasGravity(false);
                        selectedTile.setIsDestructible(true);

                        // Veraenderung fuer Speichern festhalten
                        minedTiles.put(new Point(selectedTile.getRow(), selectedTile.getColumn()), References.NANDR);
                    }
                    else if (Inventory.invBar[Inventory.selected].getId() == References.NANDL)
                    {
                        Tile.setNANDL(selectedTile, this);
                        selectedTile.setTexture(ResourceLoader.NANDL);
                        selectedTile.setIsCollidable(true);
                        selectedTile.setHasGravity(false);
                        selectedTile.setIsDestructible(true);

                        // Veraenderung fuer Speichern festhalten
                        minedTiles.put(new Point(selectedTile.getRow(), selectedTile.getColumn()), References.NANDL);
                    }
                    else if (Inventory.invBar[Inventory.selected].getId() == References.SWITCH_OFF)
                    {
                        Tile.setNeighbors(selectedTile, false, this);
                        selectedTile.setTexture(ResourceLoader.switchOff);
                        selectedTile.setIsCollidable(true);
                        selectedTile.setHasGravity(false);
                        selectedTile.setIsDestructible(true);

                        // Veraenderung fuer Speichern festhalten
                        minedTiles.put(new Point(selectedTile.getRow(), selectedTile.getColumn()), References.SWITCH_OFF);
                    }
                    else if (Inventory.invBar[Inventory.selected].getId() == References.BLUEROCK_OFF)
                    {
                        Tile[] neighbors = Tile.getNeighbors(selectedTile, this);
                        boolean on = false;
                        for (int i = 0; i < 4; i++) {
                            if (neighbors[i].getTexture() == ResourceLoader.bluerockOn || neighbors[i].getTexture() == ResourceLoader.battery) {
                                on = true;
                            }
                        }
                        if (on)
                        {
                            selectedTile.setTexture(ResourceLoader.bluerockOn);
                            selectedTile.setIsCollidable(true);
                            selectedTile.setHasGravity(false);
                            selectedTile.setIsDestructible(false);
                            for (int i = 0; i < 4; i++) {
                                Tile.setNeighbors(neighbors[i], true, this);
                            }

                            // Veraenderung fuer Speichern festhalten
                            minedTiles.put(new Point(selectedTile.getRow(), selectedTile.getColumn()), References.BLUEROCK_ON);
                        }
                        else
                        {
                            selectedTile.setTexture(ResourceLoader.bluerockOff);
                            selectedTile.setIsCollidable(true);
                            selectedTile.setHasGravity(false);
                            selectedTile.setIsDestructible(false);

                            // Veraenderung fuer Speichern festhalten
                            minedTiles.put(new Point(selectedTile.getRow(), selectedTile.getColumn()), References.BLUEROCK_OFF);
                        }
                    }
                    else {
                        selectedTile.setTexture(References.getTextureById(Inventory.invBar[Inventory.selected].getId()));
                        selectedTile.setIsCollidable(true);
                        selectedTile.setHasGravity(false);
                        selectedTile.setIsDestructible(true);

                        // Veraenderung fuer Speichern festhalten
                        minedTiles.put(new Point(selectedTile.getRow(), selectedTile.getColumn()), Inventory.invBar[Inventory.selected].getId());
                    }
                    Inventory.removeFromInventory(selectedTile.getId());
                }
            }
        }
    }

    /**
     * mouseMoved       Bewegung der Maus
     * @param e         Mausevent
     * */
    public void mouseMoved(MouseEvent e)
    {
        References.MOUSE_X = e.getX();
        References.MOUSE_Y = e.getY();
    }

}