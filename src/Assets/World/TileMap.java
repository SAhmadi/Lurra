package Assets.World;

import Assets.GameObjects.Player;
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

    // Farbe
    private final Color BROWN = new Color(83, 63, 72);

    // Tiles
    public static BufferedImage[] dirtTextures = { ResourceLoader.gras, ResourceLoader.grasWithFlower, ResourceLoader.dirt, ResourceLoader.dirtMidDark, ResourceLoader.dirtDark };
    public static BufferedImage[] dirtOnlyTextures = { ResourceLoader.dirt, ResourceLoader.dirtMidDark, ResourceLoader.dirtDark };
    public static BufferedImage[] grasOnlyTextures = { ResourceLoader.gras, ResourceLoader.grasWithFlower };

    public static BufferedImage[] treeOnlyTextures = {
            ResourceLoader.leafStart, ResourceLoader.leaf, ResourceLoader.leafEnd,
            ResourceLoader.treeTrunk, ResourceLoader.treeTrunkRight, ResourceLoader.treeTrunkLeft, ResourceLoader.treeTrunkRoot
    };

    public static BufferedImage[] treeSnowOnlyTextures = {
            ResourceLoader.treeTrunkSnowEnd, ResourceLoader.treeTrunkSnow, ResourceLoader.treeTrunkAfterRootSnow, ResourceLoader.treeTrunkRootSnow,
            ResourceLoader.treeTrunkRight, ResourceLoader.treeTrunkLeft, ResourceLoader.treeTrunkRoot
    };

    public static BufferedImage[] cactusOnlyTextues = { ResourceLoader.cactusRoot, ResourceLoader.cactus1, ResourceLoader.cactus2, ResourceLoader.cactusTop };

    public static BufferedImage[] iceOnlyTextures = { ResourceLoader.iceTop, ResourceLoader.ice, ResourceLoader.ice2, ResourceLoader.ice3 };

    public static BufferedImage[] sandOnlyTextures = { ResourceLoader.sandTop, ResourceLoader.sand, ResourceLoader.sand2, ResourceLoader.sand3 };

    public static BufferedImage[] cactusTextures = { ResourceLoader.cactus1, ResourceLoader.cactus2 };

    public static BufferedImage[] treeTrunkTextures = { ResourceLoader.treeTrunk, ResourceLoader.treeTrunkRight, ResourceLoader.treeTrunkLeft };

    public static BufferedImage[] gemsTextures = {
            ResourceLoader.smaragd, ResourceLoader.diamond, ResourceLoader.gold, ResourceLoader.saphire,
            ResourceLoader.ruby, ResourceLoader.ion, ResourceLoader.copper, ResourceLoader.silver
    };

    public static BufferedImage[] waterTextures = { ResourceLoader.water, ResourceLoader.waterTop, ResourceLoader.waterTop2 };
    public static BufferedImage[] iceTextures = { ResourceLoader.ice, ResourceLoader.ice2, ResourceLoader.ice3 };
    public static BufferedImage[] sandTextures = { ResourceLoader.sand, ResourceLoader.sand2, ResourceLoader.sand3 };

    // Truemmer
    private ArrayList<Rectangle> tileParticles = new ArrayList<>();
    private Color tileParticlesColor;

    // Map
    private double x;
    private double y;
    private Map<Point, Tile> map;
    private String mapFilePath;        // Map Speicher-Pfad

    private int columnOffset;           // Offsets
    private int rowOffset;

    private int numberOfColumnsToDraw;  // Anzahl der zu zeichnenden Tiles
    private int numberOfRowsToDraw;
    private int puffer;             // Puffer

    // Zufallsvariabeln
    private int seed;
    private int gemsSeed;
    private int randomizeSeed;

    private int[] randomNumbers;
    private int randNumbersIndex;
    private int treeTextureCounter;     // zufaellige Baeume
    private int treeHeight;

    private boolean shouldPlaceWater;   // zufaellige Seen

    // zufaellige Huegel
    private static double rowTmp = References.SCREEN_HEIGHT/References.TILE_SIZE/2;
    public double xForTileMapStart;
    private boolean isMountain = true;

    // zufaelliger Regen
    private Rain[] rain;

    public static Player ownPlayerInstance;

    /**
     * TileMap      Konstrultor der TileMap-Klasse
     *
     * @param seed  Zufallsvariable
     * */
    public TileMap(int seed)
    {
        // Neue Spielwelt
        this.map = new HashMap<>();

        // Anzahl Spalten und Reihe zum Zeichnen
        this.puffer = 2;
        this.numberOfColumnsToDraw = References.SCREEN_WIDTH / References.TILE_SIZE + this.puffer;
        this.numberOfRowsToDraw = References.SCREEN_HEIGHT / References.TILE_SIZE + this.puffer;

        // Seed festlegen
        this.seed = seed;
        this.gemsSeed = 1;
        this.randomizeSeed = 1;

        this.randomNumbers = new int[]{1, 2, 3, 4};
        this.randNumbersIndex = 0;

        this.treeTextureCounter = 0;
        this.treeHeight = seed / randomNumbers[randNumbersIndex];

        this.shouldPlaceWater = false;
        this.rain = new Rain[20];

        for (int i = 0; i < rain.length; i++)
            rain[i] = new Rain();
    }

    /**
     * update           Aktualisieren der Spielwelt
     * */
    public void update()
    {
        // Partikel entfernen
        try { removeParticles(); }
        catch (ConcurrentModificationException ignored) {}

        // Tiles Erstellen und Positionieren
        generateTiles();
    }

    /**
     * render           Zeichnen der Spielwelt
     *
     * @param graphics  Graphics Objekt
     * */
    public void render(Graphics graphics)
    {
        // Zeichnen der Tiles
        for (int row = rowOffset-puffer; row < rowOffset+numberOfRowsToDraw; row++)
        {
            for (int column = columnOffset-puffer; column < columnOffset+numberOfColumnsToDraw; column++)
            {
                try { map.get(new Point(row, column)).render(graphics, this.x, this.y); }
                catch (NullPointerException ignored) {}
            }
        }

        // Abdunkeln der Tiles bei Dunkelheit
        graphics.setColor(new Color(Background.red, Background.green, Background.blue, Background.opacity));
        graphics.fillRect(0, 0, References.SCREEN_WIDTH, References.SCREEN_HEIGHT);

        // Zeichnen der Tile Abbau-Splitter
        graphics.setColor(tileParticlesColor);
        if (tileParticles != null)
        {
            try
            {
                for (Rectangle rec : tileParticles)
                {
                    rec.y += 8;
                    graphics.fillRect((int) rec.getX() + (new Random().nextInt(10)-new Random().nextInt(15)), (int) rec.getY(), (int) rec.getWidth(), (int) rec.getHeight());
                }
            } catch (ConcurrentModificationException ignored) {}
        }

        // Zeichnen des Regens
        for (Rain drop : rain)
        {
            drop.render(graphics, randomizeSeed);
        }
    }

    /**
     * generate Tiles
     * */
    private void generateTiles()
    {
        for (int column = columnOffset-puffer; column < columnOffset+numberOfColumnsToDraw; column++)
        {
            for (int row = rowOffset-puffer; row < rowOffset+numberOfRowsToDraw; row++)
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
                            if (this.x < -2000)
                            {
                                generateDesertBiome(row, column);
                                continue;
                            }

                            // Schnee
                            if (this.x < -1000)
                            {
                                generateIceBiome(row, column);
                                continue;
                            }

                            // Jungle
                            generateJungleBiome(row, column);

                        } catch (NullPointerException ignored) {}
                    }
                }
                else
                    if (map.get(new Point(row, column)) == null)
                        map.put(new Point(row, column), new Tile(null, column*References.TILE_SIZE, row*References.TILE_SIZE, row, column, false, false, false));
            }
        }
    }

    /**
     * generateDesertBiome      Generieren des Wuesten Bioms
     *
     * @param row               Aktuelle Zeile
     * @param column            Aktuelle Spalte
     * */
    private void generateDesertBiome(int row, int column)
    {
        // Kaktus
        if (map.get(new Point(row - 1, column)).getTexture() == null)
        {
            if (randNumbersIndex >= randomNumbers.length)
                randNumbersIndex = 0;

            if (Math.abs(column % seed) < randomNumbers[randNumbersIndex]+1
                    && ((this.x <= 0 && map.get(new Point(row-1, column-1)).getTexture() == null) || (this.x > 0 && map.get(new Point(row-1, column+1)).getTexture() == null)))
            {
                map.put(new Point(row-1, column), new Tile(ResourceLoader.cactusRoot, column*References.TILE_SIZE, (row-1)*References.TILE_SIZE, row-1, column, false, false, true));

                // Baumhoehe
                if (treeHeight > 14) treeHeight = 14;
                else if (treeHeight < 8) treeHeight = 12;

                // Baumstamm
                for (int i = 2; i < treeHeight; i++)
                {
                    if (treeTextureCounter >= cactusTextures.length)
                        treeTextureCounter = 0;

                    map.put(new Point(row-i, column), new Tile(cactusTextures[treeTextureCounter], column*References.TILE_SIZE, (row-i)*References.TILE_SIZE, row-i, column, false, false, true));
                    treeTextureCounter++;
                }
                map.put(new Point(row-treeHeight, column), new Tile(ResourceLoader.cactusTop, column*References.TILE_SIZE, (row-treeHeight)*References.TILE_SIZE, row-treeHeight, column, false, false, true));
            }
            else
                map.put(new Point(row-1, column), new Tile(null, column*References.TILE_SIZE, (row-1)*References.TILE_SIZE, row-1, column, false, false, false));
        }

        // Wueste oder Wasser
        if (map.get(new Point(row - 1, column)).getTexture() == null)
        {
            if (!shouldPlaceWater)
                if (new Random(randomizeSeed).nextInt(1000) == 10)
                    shouldPlaceWater = true;

            if (shouldPlaceWater && !Arrays.asList(cactusOnlyTextues).contains(map.get(new Point(row-1, column)).getTexture()))
                map.put(new Point(row, column), new Tile(ResourceLoader.waterTop, column * References.TILE_SIZE, row * References.TILE_SIZE, row, column, false, false, false));
            else
                map.put(new Point(row, column), new Tile(ResourceLoader.sandTop, column*References.TILE_SIZE, row*References.TILE_SIZE, row, column, true, true, true));
        }
        // Sand Oberflaeche
        else if (Arrays.asList(waterTextures).contains(map.get(new Point(row - 1, column)).getTexture()) || Arrays.asList(treeOnlyTextures).contains(map.get(new Point(row-1, column)).getTexture()))
            map.put(new Point(row, column), new Tile(ResourceLoader.sandTop, column*References.TILE_SIZE, row*References.TILE_SIZE, row, column, true, true, true));
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
            map.put(new Point(row, column), new Tile(sandTextures[new Random().nextInt(sandTextures.length)], column*References.TILE_SIZE, row*References.TILE_SIZE, row, column, true, true, true));
    }

    /**
     * generateIceBiome      Generieren des Schnee Bioms
     *
     * @param row               Aktuelle Zeile
     * @param column            Aktuelle Spalte
     * */
    private void generateIceBiome(int row, int column)
    {
        // Baum schneebedeckt
        if (map.get(new Point(row - 1, column)).getTexture() == null)
        {
            if (randNumbersIndex >= randomNumbers.length)
                randNumbersIndex = 0;

            if (Math.abs(column % seed) < randomNumbers[randNumbersIndex]+1
                    && ((this.x <= 0 && map.get(new Point(row-1, column-1)).getTexture() == null) || (this.x > 0 && map.get(new Point(row-1, column+1)).getTexture() == null)))
            {
                map.put(new Point(row-1, column), new Tile(ResourceLoader.treeTrunkRoot, column*References.TILE_SIZE, (row-1)*References.TILE_SIZE, row-1, column, false, false, true));

                // Baumhoehe
                if (treeHeight > 14) treeHeight = 14;
                else if (treeHeight < 8) treeHeight = 12;

                // Baumstamm
                for (int i = 2; i < treeHeight; i++)
                {
                    if (treeTextureCounter >= treeTrunkTextures.length)
                        treeTextureCounter = 0;

                    map.put(new Point(row-i, column), new Tile(treeTrunkTextures[treeTextureCounter], column*References.TILE_SIZE, (row-i)*References.TILE_SIZE, row-i, column, false, false, true));
                    treeTextureCounter++;
                }
                map.put(new Point(row-treeHeight, column), new Tile(ResourceLoader.treeTrunkRootSnow, column*References.TILE_SIZE, (row-treeHeight)*References.TILE_SIZE, row-treeHeight, column, false, false, true));

                // Baumkrone
                map.put(new Point(row-treeHeight-1, column), new Tile(ResourceLoader.treeTrunkAfterRootSnow, column*References.TILE_SIZE, (row-treeHeight-1)*References.TILE_SIZE, row-treeHeight-1, column, false, false, true));
                for (int i = 2; i < treeHeight; i++)
                {
                    if (treeTextureCounter >= treeTrunkTextures.length)
                        treeTextureCounter = 0;

                    map.put(new Point(row-treeHeight-i, column), new Tile(ResourceLoader.treeTrunkSnow, column*References.TILE_SIZE, (row-treeHeight-i)*References.TILE_SIZE, row-treeHeight-i, column, false, false, true));
                    treeTextureCounter++;
                }
                map.put(new Point(row-2*treeHeight, column), new Tile(ResourceLoader.treeTrunkSnowEnd, column*References.TILE_SIZE, (row-2*treeHeight)*References.TILE_SIZE, row-2*treeHeight, column, false, false, true));
            }
            else
                map.put(new Point(row-1, column), new Tile(null, column*References.TILE_SIZE, (row-1)*References.TILE_SIZE, row-1, column, false, false, false));
        }

        // Schnee Oberflaeche
        if (map.get(new Point(row - 1, column)).getTexture() == null)
        {
            if (!shouldPlaceWater)
                if (new Random(randomizeSeed).nextInt(1000) < 10)
                    shouldPlaceWater = true;

            if (shouldPlaceWater && !Arrays.asList(treeSnowOnlyTextures).contains(map.get(new Point(row-1, column)).getTexture()))
                map.put(new Point(row, column), new Tile(ResourceLoader.waterTop, column * References.TILE_SIZE, row * References.TILE_SIZE, row, column, false, false, false));
            else
                map.put(new Point(row, column), new Tile(ResourceLoader.iceTop, column*References.TILE_SIZE, row*References.TILE_SIZE, row, column, true, true, true));
        }
        // Schnee Oberflaeche
        else if (Arrays.asList(waterTextures).contains(map.get(new Point(row - 1, column)).getTexture()) || Arrays.asList(treeSnowOnlyTextures).contains(map.get(new Point(row-1, column)).getTexture()))
            map.put(new Point(row, column), new Tile(ResourceLoader.iceTop, column*References.TILE_SIZE, row*References.TILE_SIZE, row, column, true, true, true));
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
            map.put(new Point(row, column), new Tile(iceTextures[new Random().nextInt(iceTextures.length)], column*References.TILE_SIZE, row*References.TILE_SIZE, row, column, true, true, true));
    }

    /**
     * generateJungleBiome      Generieren des Jungle Bioms
     *
     * @param row               Aktuelle Zeile
     * @param column            Aktuelle Spalte
     * */
    private void generateJungleBiome(int row, int column)
    {
        // Baum
        if (map.get(new Point(row - 1, column)).getTexture() == null)
        {
            if (randNumbersIndex >= randomNumbers.length)
                randNumbersIndex = 0;

            if (Math.abs(column % seed) < randomNumbers[randNumbersIndex]+1
                    && ((this.x <= 0 && map.get(new Point(row-1, column-1)).getTexture() == null) || (this.x > 0 && map.get(new Point(row-1, column+1)).getTexture() == null)))
            {
                map.put(new Point(row-1, column), new Tile(ResourceLoader.treeTrunkRoot, column*References.TILE_SIZE, (row-1)*References.TILE_SIZE, row-1, column, false, false, true));

                // Baumhoehe
                if (treeHeight > 14) treeHeight = 14;
                else if (treeHeight < 8) treeHeight = 12;

                // Baumstamm
                for (int i = 2; i < treeHeight; i++)
                {
                    if (treeTextureCounter >= treeTrunkTextures.length)
                        treeTextureCounter = 0;

                    map.put(new Point(row-i, column), new Tile(treeTrunkTextures[treeTextureCounter], column*References.TILE_SIZE, (row-i)*References.TILE_SIZE, row-i, column, false, false, true));
                    treeTextureCounter++;
                }
                map.put(new Point(row-treeHeight, column), new Tile(ResourceLoader.treeTrunkTop, column*References.TILE_SIZE, (row-treeHeight)*References.TILE_SIZE, row-treeHeight, column, false, false, true));

                // Baumkrone
                map.put(new Point(row-treeHeight-1, column), new Tile(ResourceLoader.leafStart, column*References.TILE_SIZE, (row-treeHeight-1)*References.TILE_SIZE, row-treeHeight-1, column, false, false, true));
                for (int i = 2; i < treeHeight; i++)
                {
                    if (treeTextureCounter >= treeTrunkTextures.length)
                        treeTextureCounter = 0;

                    map.put(new Point(row-treeHeight-i, column), new Tile(ResourceLoader.leaf, column*References.TILE_SIZE, (row-treeHeight-i)*References.TILE_SIZE, row-treeHeight-i, column, false, false, true));
                    treeTextureCounter++;
                }
                map.put(new Point(row-2*treeHeight, column), new Tile(ResourceLoader.leaf, column*References.TILE_SIZE, (row-2*treeHeight)*References.TILE_SIZE, row-2*treeHeight, column, false, false, true));
                map.put(new Point(row-2*treeHeight-1, column), new Tile(ResourceLoader.leafEnd, column*References.TILE_SIZE, (row-2*treeHeight-1)*References.TILE_SIZE, row-2*treeHeight-1, column, false, false, true));
            }
            else
                map.put(new Point(row-1, column), new Tile(null, column*References.TILE_SIZE, (row-1)*References.TILE_SIZE, row-1, column, false, false, false));
        }

        // Gras und Wasser
        if (map.get(new Point(row - 1, column)).getTexture() == null)
        {
            if (!shouldPlaceWater)
                if (new Random(randomizeSeed).nextInt(1000) == 10)
                    shouldPlaceWater = true;

            if (shouldPlaceWater && !Arrays.asList(treeOnlyTextures).contains(map.get(new Point(row-1, column)).getTexture()))
                map.put(new Point(row, column), new Tile(ResourceLoader.waterTop, column * References.TILE_SIZE, row * References.TILE_SIZE, row, column, false, false, false));
            else
                map.put(new Point(row, column), new Tile(grasOnlyTextures[new Random().nextInt(grasOnlyTextures.length)], column*References.TILE_SIZE, row*References.TILE_SIZE, row, column, true, true, true));
        }
        // Gras
        else if (Arrays.asList(waterTextures).contains(map.get(new Point(row-1, column)).getTexture()) || Arrays.asList(treeOnlyTextures).contains(map.get(new Point(row-1, column)).getTexture()))
            map.put(new Point(row, column), new Tile(grasOnlyTextures[new Random().nextInt(grasOnlyTextures.length)], column*References.TILE_SIZE, row*References.TILE_SIZE, row, column, true, true, true));
            // Edelsteine
        else if (Arrays.asList(dirtOnlyTextures).contains(map.get(new Point(row-1, column)).getTexture()))
        {
            if (new Random(randomizeSeed).nextInt(100) < 3)
                map.put(new Point(row, column), new Tile(gemsTextures[new Random(gemsSeed).nextInt(gemsTextures.length)], column * References.TILE_SIZE, row * References.TILE_SIZE, row, column, true, true, true));
            else
                map.put(new Point(row, column), new Tile(dirtOnlyTextures[new Random().nextInt(dirtOnlyTextures.length)], column*References.TILE_SIZE, row*References.TILE_SIZE, row, column, true, true, true));

            // Updaten der Seedvariablen
            randomizeSeed++;
            //if (randomizeSeed >= Integer.MAX_VALUE-1) randomizeSeed = 1;
            gemsSeed++;
            //if (gemsSeed >= Integer.MAX_VALUE-1) gemsSeed = 1;
        }
        // Erde
        else
            map.put(new Point(row, column), new Tile(dirtOnlyTextures[new Random().nextInt(dirtOnlyTextures.length)], column*References.TILE_SIZE, row*References.TILE_SIZE, row, column, true, true, true));

    }

    /**
     * calculateHills       Ermitteln der Heugel Position
     * */
    private void calculateHills()
    {
        if ((int) Math.abs(Player.xForTileMap) > xForTileMapStart + (References.SCREEN_WIDTH/4))
        {
            if (randNumbersIndex >= randomNumbers.length)
                randNumbersIndex = 0;

            xForTileMapStart += 50 * randomNumbers[randNumbersIndex];

            if (isMountain)
            {
                rowTmp += randomNumbers[randNumbersIndex];
                shouldPlaceWater = false;
            }
            else
            {
                rowTmp -= randomNumbers[randNumbersIndex];
                shouldPlaceWater = false;
            }

            if (rowTmp > (References.SCREEN_HEIGHT/References.TILE_SIZE/2) + 10)
            {
                rowTmp = (References.SCREEN_HEIGHT/References.TILE_SIZE/2) + 10;
                isMountain = false;
            }
            else if (rowTmp < (References.SCREEN_HEIGHT/References.TILE_SIZE/2) - 5)
            {
                rowTmp = (References.SCREEN_HEIGHT/References.TILE_SIZE/2) - 5;
                isMountain = true;
            }

            randNumbersIndex++;
        }
    }

    /**
     * fillLake         Fuellen des Sees
     *
     * @param row       Aktuelle Zeile
     * @param column    Aktuelle Spalte
     * */
    private void fillLake(int row, int column)
    {
        try
        {
            if ( row < rowTmp && map.get(new Point(row, column)) == null
                    && (Arrays.asList(waterTextures).contains(map.get(new Point(row, column-1)).getTexture())
                    || Arrays.asList(waterTextures).contains(map.get(new Point(row, column-2)).getTexture())
                    || Arrays.asList(waterTextures).contains(map.get(new Point(row-1, column)).getTexture())) )
            {
                if (map.get(new Point(row-1, column)).getTexture() == null)
                    map.put(new Point(row, column), new Tile(ResourceLoader.waterTop, column * References.TILE_SIZE, row * References.TILE_SIZE, row, column, false, false, false));
                else
                    map.put(new Point(row, column), new Tile(ResourceLoader.water, column * References.TILE_SIZE, row * References.TILE_SIZE, row, column, false, false, false));
            }
        } catch (NullPointerException ignored) {}
    }

    /**
     * generateParticles        Erstellen von kleinen Truemmer-Teilen
     *
     * @param point             Generierungspunkt
     * @param color             Farbe der Truemmer
     * @param numberOfParticles Anzahl der Truemmer
     * @param size               Groesse der Truemmer
     * */
    private void generateParticles(Point point, Color color, int numberOfParticles, int size)
    {
        tileParticlesColor = color;

        for (int i = 0; i < numberOfParticles; i++)
        {
            tileParticles.add(new Rectangle((int) point.getX()+(new Random().nextInt(10) - new Random().nextInt(20)), (int) point.getY()+(new Random().nextInt(10) - new Random().nextInt(20)), size, size));
        }
    }

    /**
     * removeParticles          Entfernen der Partikel, die nicht mehr im Radius liegen
     *
     * @throws ConcurrentModificationException
     * */
    private void removeParticles() throws ConcurrentModificationException
    {
        for (int i = 0; i < tileParticles.size(); i++)
        {
            if (tileParticles.get(i).getY() > tileParticles.get(i).getY()+100
                    || tileParticles.get(i).getX() < tileParticles.get(i).getX()-100
                    || tileParticles.get(i).getX() > tileParticles.get(i).getX()+100)
            {
                tileParticles.remove(tileParticles.get(i));
                i--;
            }
        }
    }

    // GETTER UND SETTER
    /**
     * setPosition              Setzen der Map Position, sowie des Offsets
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
     * @return int              x-Koordinate
     * */
    public double getX() { return this.x; }

    /**
     * getY                     Rueckgabe der Map y-Koordinate
     * @return int              y-Koordinate
     * */
    public double getY() { return this.y; }

    /**
     * getMap                   Rueckgabe der Map
     * @return Map<>            Spielwelt HashMap<Point/Tile>
     * */
    public Map<Point, Tile> getMap() { return this.map; }

    /**
     * getRowOffset             Rueckgabe des Zeilen-Offsets
     * @return int              Zeilen Offset
     * */
    public int getRowOffset() { return this.rowOffset; }

    /**
     * getColumnOffset          Rueckgabe des Spalten-Offsets
     * @return int              Spalten Offset
     * */
    public int getColumnOffset() { return this.columnOffset; }

    /**
     * getNumberOfRowsToDraw        Rueckgabe der Anzahl zuzeichnender Zeilen-Tiles
     * @return int                  Anzahl der Zeilen Tiles
     * */
    public int getNumberOfRowsToDraw() { return this.numberOfRowsToDraw; }

    /**
     * getNumberOfColumnsToDraw     Rueckgabe der Anzahl zuzeichnender Spalten-Tiles
     * @return int                  Anzahl der Spalten Tiles
     * */
    public int getNumberOfColumnsToDraw() { return this.numberOfColumnsToDraw; }

    /**
     * getPuffer                Rueckgabe des Puffers
     * @return int              Puffer
     * */
    public int getPuffer() { return this.puffer; }

    // LISTENER
    public void mouseClicked(MouseEvent e)
    {
        if (!Inventory.isDrawerOpen)
        {
            Tile selectedTile = map.get(new Point((int) ((e.getY() - this.y) / References.TILE_SIZE), (int) (Math.floor((e.getX() - this.x) / References.TILE_SIZE))));

            // Abbauen der Rohstoffe
            if (selectedTile.getTexture() != null && selectedTile.getIsDestructible())
            {
                // Picke ausgewaehlt
                if ( Inventory.invBar[Inventory.selected].name.equals("Picke")
                        && (Arrays.asList(dirtTextures).contains(selectedTile.getTexture()) || Arrays.asList(gemsTextures).contains(selectedTile.getTexture())
                        || Arrays.asList(iceOnlyTextures).contains(selectedTile.getTexture()) || Arrays.asList(sandOnlyTextures).contains(selectedTile.getTexture())) )
                {
                    int tileResistance = selectedTile.getResistance();

                    if (tileResistance >= 0 )
                    {
                        if(GameData.isSoundOn.equals("On")) Sound.earthSound.play();

                        generateParticles(e.getPoint(), BROWN, 30, 3);
                        tileResistance -= Weapon.PICKE_DAMAGE;
                        selectedTile.setResistance(tileResistance);
                    }
                    // Wasser fuellt die Luecken
                    else if ( Arrays.asList(waterTextures).contains(map.get(new Point(selectedTile.getRow()-1, selectedTile.getColumn())).getTexture())
                            || Arrays.asList(waterTextures).contains(map.get(new Point(selectedTile.getRow()+1, selectedTile.getColumn())).getTexture())
                            || Arrays.asList(waterTextures).contains(map.get(new Point(selectedTile.getRow(), selectedTile.getColumn()-1)).getTexture())
                            || Arrays.asList(waterTextures).contains(map.get(new Point(selectedTile.getRow(), selectedTile.getColumn()+1)).getTexture()) )
                    {
                        try
                        {
                            if (Arrays.asList(waterTextures).contains(map.get(new Point(selectedTile.getRow() - 1, selectedTile.getColumn())).getTexture()))
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
                                    Inventory.addToInventory(selectedTile);
                                    selectedTile.setTexture(ResourceLoader.waterTop);
                                    selectedTile.setIsCollidable(false);
                                    selectedTile.setHasGravity(false);
                                    selectedTile.setIsDestructible(false);
                                }
                                else
                                {
                                    Inventory.addToInventory(selectedTile);
                                    selectedTile.setTexture(ResourceLoader.water);
                                    selectedTile.setIsCollidable(false);
                                    selectedTile.setHasGravity(false);
                                    selectedTile.setIsDestructible(false);
                                }

                                i = 0;
                                int j = 0;
                                while (map.get(new Point(selectedTile.getRow()-rowOfTopWater, selectedTile.getColumn()+i)).getTexture() == ResourceLoader.waterTop)
                                {
                                    while (map.get(new Point(selectedTile.getRow() - rowOfTopWater, selectedTile.getColumn() - j)).getTexture() == ResourceLoader.waterTop)
                                    {
                                        map.get(new Point(selectedTile.getRow() - rowOfTopWater, selectedTile.getColumn() - j)).setTexture(null);
                                        map.get(new Point(selectedTile.getRow() - rowOfTopWater, selectedTile.getColumn() - j)).setIsCollidable(false);
                                        map.get(new Point(selectedTile.getRow() - rowOfTopWater, selectedTile.getColumn() - j)).setHasGravity(false);
                                        map.get(new Point(selectedTile.getRow() - rowOfTopWater, selectedTile.getColumn() - j)).setIsDestructible(false);

                                        if (map.get(new Point(selectedTile.getRow() - rowOfTopWater + 1, selectedTile.getColumn() - j)).getTexture() == ResourceLoader.water)
                                            map.get(new Point(selectedTile.getRow() - rowOfTopWater + 1, selectedTile.getColumn() - j)).setTexture(ResourceLoader.waterTop);
                                        j++;
                                    }

                                    map.get(new Point(selectedTile.getRow() - rowOfTopWater, selectedTile.getColumn() + i)).setTexture(null);
                                    map.get(new Point(selectedTile.getRow() - rowOfTopWater, selectedTile.getColumn() + i)).setIsCollidable(false);
                                    map.get(new Point(selectedTile.getRow() - rowOfTopWater, selectedTile.getColumn() + i)).setHasGravity(false);
                                    map.get(new Point(selectedTile.getRow() - rowOfTopWater, selectedTile.getColumn() + i)).setIsDestructible(false);

                                    if (map.get(new Point(selectedTile.getRow() - rowOfTopWater + 1, selectedTile.getColumn() + i)).getTexture() == ResourceLoader.water)
                                        map.get(new Point(selectedTile.getRow() - rowOfTopWater + 1, selectedTile.getColumn() + i)).setTexture(ResourceLoader.waterTop);
                                    i++;
                                }
                            }
                            else
                            {
                                Inventory.addToInventory(selectedTile);

                                if (map.get(new Point(selectedTile.getRow()-1, selectedTile.getColumn())).getTexture() == null)
                                    selectedTile.setTexture(ResourceLoader.waterTop);
                                else
                                    selectedTile.setTexture(ResourceLoader.water);
                                selectedTile.setIsCollidable(false);
                                selectedTile.setHasGravity(false);
                                selectedTile.setIsDestructible(false);
                            }

                        }
                        catch (Exception ex) { ex.printStackTrace(); }
                    }
                    else
                    {
                        Inventory.addToInventory(selectedTile);

                        if(ownPlayerInstance != null) ownPlayerInstance.incExperience(selectedTile.getEpBonus());

                        selectedTile.setTexture(null);
                        selectedTile.setIsCollidable(false);
                        selectedTile.setHasGravity(false);
                        selectedTile.setIsDestructible(false);
                    }
                }
                // Axt ausgewaehlt
                else if (Inventory.invBar[Inventory.selected].name.equals("Axt") &&
                        (Arrays.asList(treeOnlyTextures).contains(selectedTile.getTexture()) || Arrays.asList(treeSnowOnlyTextures).contains(selectedTile.getTexture())
                        || Arrays.asList(cactusOnlyTextues).contains(selectedTile.getTexture())))
                {
                    int tileResistance = selectedTile.getResistance();
                    if (tileResistance >= 0) {
                        if (GameData.isSoundOn.equals("On"))
                            Sound.woodSound.play();

                        generateParticles(e.getPoint(), BROWN, 30, 3);  // TODO particles spray to left or right
                        tileResistance -= Weapon.AXE_DAMAGE;
                        selectedTile.setResistance(tileResistance);
                    }
                    else
                    {
                        // Zum Inventar hinzufuegen
                        Inventory.addToInventory(selectedTile);

                        selectedTile.setTexture(null);
                        selectedTile.setIsCollidable(false);
                        selectedTile.setHasGravity(false);
                        selectedTile.setIsDestructible(false);
                    }
                }
                // Hammer ausgewaehlt
                else if (Inventory.invBar[Inventory.selected].name.equals("Hammer") && Arrays.asList(gemsTextures).contains(selectedTile.getTexture()))
                {
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
                    }
                    else
                    {
                        // Zum Inventar hinzufuegen
                        Inventory.addToInventory(selectedTile);

                        selectedTile.setTexture(null);
                        selectedTile.setIsCollidable(false);
                        selectedTile.setHasGravity(false);
                        selectedTile.setIsDestructible(false);
                    }
                }
            }
            else if (selectedTile.getTexture() == null)
            {
                if (!Inventory.invBar[Inventory.selected].name.equals("Picke") && !Inventory.invBar[Inventory.selected].name.equals("Axt") && !Inventory.invBar[Inventory.selected].name.equals("Hammer") && !Inventory.invBar[Inventory.selected].name.equals("Schleimpistole"))
                {
                    Inventory.removeFromInventory(selectedTile);
                    selectedTile.setTexture(Inventory.invBar[Inventory.selected].tileImage);
                    selectedTile.setIsCollidable(true);
                    selectedTile.setHasGravity(false);
                    selectedTile.setIsDestructible(true);
                }
            }
        }
    }

    public void mouseMoved(MouseEvent e)
    {
        References.MOUSE_X = e.getX();
        References.MOUSE_Y = e.getY();
    }

}