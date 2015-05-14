package Assets;

import Assets.GameObjects.Player;
import Assets.Tiles.*;
import Main.ScreenDimensions;
import com.sun.glass.ui.Screen;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

/*
* TileMap - Spielfeld bestehend aus Tiles
* */
public class TileMap {

    private Assets assets;

    /*
    * Tiles
    * */
    // Alle Tiles in einer ArrayList
    private ArrayList<Tile> tiles;

    // WaterTile and LavaTile
    private BufferedImage waterTileTop;
    private BufferedImage waterTile;
    private BufferedImage lavaTileTop;
    private BufferedImage lavaTile;

    // GrasTile
    private BufferedImage grasTileRight;
    private BufferedImage grasTileRight_Destroyed;
    private BufferedImage grasTileLeft;
    private BufferedImage grasTileLeft_Destroyed;
    private BufferedImage grasTileBottom;
    private BufferedImage grasTileBottom_Destroyed;

    private BufferedImage grasTileBottomRightCorner;
    private BufferedImage grasTileBottomRightCorner_Destroyed;
    private BufferedImage grasTileBottomLeftCorner;
    private BufferedImage grasTileBottomLeftCorner_Destroyed;
    private BufferedImage grasTileTopRightCorner;
    private BufferedImage grasTileTopRightCorner_Destroyed;
    private BufferedImage grasTileTopLeftCorner;
    private BufferedImage grasTileTopLeftCorner_Destroyed;

    private BufferedImage grasTile;
    private BufferedImage grasTile_Destroyed;

    // DirtTile
    private BufferedImage dirtDark;
    private BufferedImage dirtDark_Destroyed;
    private BufferedImage dirtMidDark;
    private BufferedImage dirtMidDark_Destroyed;
    private BufferedImage dirt;
    private BufferedImage dirt_Destroyed;

    // StoneTile
    private BufferedImage stoneWhite1;
    private BufferedImage stoneWhite2;
    private BufferedImage stoneWhite3;
    private BufferedImage stoneWhiteLeafs;
    private BufferedImage stoneWhite_Destroyed;

    private BufferedImage stoneRed1;
    private BufferedImage stoneRed2;
    private BufferedImage stoneRed3;
    private BufferedImage stoneRedLeafs;
    private BufferedImage stoneRed_Destroyed;

    // Kupfer, Silber und Gold
    private BufferedImage copper;
    private BufferedImage copper_Destroyed;

    private BufferedImage silver;
    private BufferedImage silver_Destroyed;

    private BufferedImage gold;
    private BufferedImage gold_Destroyed;

    // Holz und Blatt
    private BufferedImage leafBottomLeftCorner;
    private BufferedImage leafBottom;
    private BufferedImage leafBottomRightCorner;
    private BufferedImage leafRight;
    private BufferedImage leafNormal;
    private BufferedImage leafLeft;
    private BufferedImage leafTopLeftCorner;
    private BufferedImage leafTop;
    private BufferedImage leafTopRightCorner;

    private BufferedImage treeTrunkRootLeft;
    private BufferedImage treeTrunkBottomLeft;
    private BufferedImage treeTrunkBottomRight;
    private BufferedImage treeTrunkRootRight;
    private BufferedImage treeTrunkRoundedCornerTopLeft;
    private BufferedImage treeTrunkNextToCorner;
    private BufferedImage treeTrunkHorizontalNormal;
    private BufferedImage treeTrunkRoundedCornerBottomRight;
    private BufferedImage treeTrunkVerticalNormal;
    private BufferedImage treeTrunkTopCenter;
    private BufferedImage treeTrunkTopLeft;
    private BufferedImage treeTrunkTopLeftEnd;
    private BufferedImage treeTrunkTopRightEnd;

    // Map - Pfad
    private String mapFilePath;

    // Anzahl der Spalten in der Map-Datei
    public int numberOfColumns;

    // Ausgewaehltes-Tile dazugehoerendes Rechteck
    private Rectangle tileSelectedBounds;
    // Ausgewaehltes Tile in der Schleife
    private Tile selectedTile;
    private Tile selectedTileForGravity;
    private Tile tilesToDelete;


    /*
    * Konstruktor
    * */
    public TileMap(Assets tileAssets, String mapFilePath) {
        this.assets = tileAssets;
        this.mapFilePath = mapFilePath;

        this.tiles = new ArrayList<Tile>();

        /*
        * Tiles aus dem TileSet ausschneiden
        * */
        // Wasser und Lava
        this.waterTileTop = tileAssets.getSubimage(0, 0, Tile.WIDTH, Tile.HEIGHT);
        this.waterTile = tileAssets.getSubimage(16, 0, Tile.WIDTH, Tile.HEIGHT);
        this.lavaTile = tileAssets.getSubimage(32, 0, Tile.WIDTH, Tile.HEIGHT);
        this.lavaTileTop = tileAssets.getSubimage(48, 0, Tile.WIDTH, Tile.HEIGHT);

        // Gras
        this.grasTileRight = tileAssets.getSubimage(0, 16, Tile.WIDTH, Tile.HEIGHT);
        this.grasTileRight_Destroyed = tileAssets.getSubimage(16, 16, Tile.WIDTH, Tile.HEIGHT);

        this.grasTileLeft = tileAssets.getSubimage(0, 32, Tile.WIDTH, Tile.HEIGHT);
        this.grasTileLeft_Destroyed = tileAssets.getSubimage(16, 32, Tile.WIDTH, Tile.HEIGHT);

        this.grasTileBottom = tileAssets.getSubimage(0, 48, Tile.WIDTH, Tile.HEIGHT);
        this.grasTileBottom_Destroyed = tileAssets.getSubimage(16, 48, Tile.WIDTH, Tile.HEIGHT);

        this.grasTileBottomRightCorner = tileAssets.getSubimage(0, 64, Tile.WIDTH, Tile.HEIGHT);
        this.grasTileBottomRightCorner_Destroyed = tileAssets.getSubimage(16, 64, Tile.WIDTH, Tile.HEIGHT);

        this.grasTileBottomLeftCorner = tileAssets.getSubimage(0, 80, Tile.WIDTH, Tile.HEIGHT);
        this.grasTileBottomLeftCorner_Destroyed = tileAssets.getSubimage(16, 80, Tile.WIDTH, Tile.HEIGHT);

        this.grasTileTopRightCorner = tileAssets.getSubimage(0, 96, Tile.WIDTH, Tile.HEIGHT);
        this.grasTileTopRightCorner_Destroyed = tileAssets.getSubimage(16, 96, Tile.WIDTH, Tile.HEIGHT);

        this.grasTileTopLeftCorner = tileAssets.getSubimage(0, 112, Tile.WIDTH, Tile.HEIGHT);
        this.grasTileTopLeftCorner_Destroyed = tileAssets.getSubimage(16, 112, Tile.WIDTH, Tile.HEIGHT);

        this.grasTile = tileAssets.getSubimage(0, 128, Tile.WIDTH, Tile.HEIGHT);
        this.grasTile_Destroyed = tileAssets.getSubimage(16, 128, Tile.WIDTH, Tile.HEIGHT);

        // Erde
        this.dirtDark = tileAssets.getSubimage(0, 144, Tile.WIDTH, Tile.HEIGHT);
        this.dirtDark_Destroyed = tileAssets.getSubimage(16, 144, Tile.WIDTH, Tile.HEIGHT);
        this.dirtMidDark = tileAssets.getSubimage(0, 160, Tile.WIDTH, Tile.HEIGHT);
        this.dirtMidDark_Destroyed = tileAssets.getSubimage(16, 160, Tile.WIDTH, Tile.HEIGHT);
        this.dirt = tileAssets.getSubimage(0, 176, Tile.WIDTH, Tile.HEIGHT);
        this.dirt_Destroyed = tileAssets.getSubimage(16, 176, Tile.WIDTH, Tile.HEIGHT);

        // Stein
        this.stoneWhite1 = tileAssets.getSubimage(0, 192, Tile.WIDTH, Tile.HEIGHT);
        this.stoneWhite2 = tileAssets.getSubimage(16, 192, Tile.WIDTH, Tile.HEIGHT);
        this.stoneWhite3 = tileAssets.getSubimage(32, 192, Tile.WIDTH, Tile.HEIGHT);
        this.stoneWhiteLeafs = tileAssets.getSubimage(48, 192, Tile.WIDTH, Tile.HEIGHT);
        this.stoneWhite_Destroyed = tileAssets.getSubimage(64, 192, Tile.WIDTH, Tile.HEIGHT);

        this.stoneRed1 = tileAssets.getSubimage(0, 208, Tile.WIDTH, Tile.HEIGHT);
        this.stoneRed2 = tileAssets.getSubimage(16, 208, Tile.WIDTH, Tile.HEIGHT);
        this.stoneRed3 = tileAssets.getSubimage(32, 208, Tile.WIDTH, Tile.HEIGHT);
        this.stoneRedLeafs = tileAssets.getSubimage(48, 208, Tile.WIDTH, Tile.HEIGHT);
        this.stoneRed_Destroyed = tileAssets.getSubimage(64, 208, Tile.WIDTH, Tile.HEIGHT);

        // Kupfer, Silber und Gold
        copper = tileAssets.getSubimage(0, 224, Tile.WIDTH, Tile.HEIGHT);
        copper_Destroyed = tileAssets.getSubimage(16, 224, Tile.WIDTH, Tile.HEIGHT);

        silver = tileAssets.getSubimage(0, 240, Tile.WIDTH, Tile.HEIGHT);
        silver_Destroyed = tileAssets.getSubimage(16, 240, Tile.WIDTH, Tile.HEIGHT);

        gold = tileAssets.getSubimage(0, 256, Tile.WIDTH, Tile.HEIGHT);
        gold_Destroyed = tileAssets.getSubimage(16, 256, Tile.WIDTH, Tile.HEIGHT);

        // Holz und Blatt
        leafBottomLeftCorner = tileAssets.getSubimage(0, 272, Tile.WIDTH, Tile.HEIGHT);
        leafBottom = tileAssets.getSubimage(16, 272, Tile.WIDTH, Tile.HEIGHT);
        leafBottomRightCorner = tileAssets.getSubimage(32, 272, Tile.WIDTH, Tile.HEIGHT);
        leafRight = tileAssets.getSubimage(48, 272, Tile.WIDTH, Tile.HEIGHT);
        leafNormal = tileAssets.getSubimage(64, 272, Tile.WIDTH, Tile.HEIGHT);
        leafLeft = tileAssets.getSubimage(80, 272, Tile.WIDTH, Tile.HEIGHT);
        leafTopLeftCorner = tileAssets.getSubimage(96, 272, Tile.WIDTH, Tile.HEIGHT);
        leafTop = tileAssets.getSubimage(112, 272, Tile.WIDTH, Tile.HEIGHT);
        leafTopRightCorner = tileAssets.getSubimage(128, 272, Tile.WIDTH, Tile.HEIGHT);

        treeTrunkRootLeft = tileAssets.getSubimage(0, 288, Tile.WIDTH, Tile.HEIGHT);
        treeTrunkBottomLeft = tileAssets.getSubimage(16, 288, Tile.WIDTH, Tile.HEIGHT);
        treeTrunkBottomRight = tileAssets.getSubimage(32, 288, Tile.WIDTH, Tile.HEIGHT);
        treeTrunkRootRight = tileAssets.getSubimage(48, 288, Tile.WIDTH, Tile.HEIGHT);
        treeTrunkRoundedCornerTopLeft = tileAssets.getSubimage(64, 288, Tile.WIDTH, Tile.HEIGHT);
        treeTrunkNextToCorner = tileAssets.getSubimage(80, 288, Tile.WIDTH, Tile.HEIGHT);
        treeTrunkHorizontalNormal = tileAssets.getSubimage(96, 288, Tile.WIDTH, Tile.HEIGHT);
        treeTrunkRoundedCornerBottomRight = tileAssets.getSubimage(112, 288, Tile.WIDTH, Tile.HEIGHT);
        treeTrunkVerticalNormal = tileAssets.getSubimage(128, 288, Tile.WIDTH, Tile.HEIGHT);
        treeTrunkTopCenter = tileAssets.getSubimage(144, 288, Tile.WIDTH, Tile.HEIGHT);
        treeTrunkTopLeft = tileAssets.getSubimage(160, 288, Tile.WIDTH, Tile.HEIGHT);
        treeTrunkTopLeftEnd = tileAssets.getSubimage(176, 288, Tile.WIDTH, Tile.HEIGHT);
        treeTrunkTopRightEnd = tileAssets.getSubimage(192, 288, Tile.WIDTH, Tile.HEIGHT);

        // Anzahl der Spalten in der Map-Datei
        try {
            Scanner scanner = new Scanner(new FileReader(mapFilePath));
            String line = scanner.nextLine();
            numberOfColumns = line.length();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

    }

    /*
    * update
    * */
    public void update() {
        // Schaue ob Tile-Texture existiert, wenn nicht loesche Tile aus der Liste
        for(int i = 0; i < getTiles().size(); i++) {
            tilesToDelete = getTiles().get(i);
            if(tilesToDelete.getTexture() == null) {
                getTiles().remove(i);
            }
        }
    }

    /*
    * render
    * */
    public void render(Graphics graphics, int playerX) {
        System.out.println(playerX);
        // Aufrufen der render-Methode des durchlaufenen Tiles
        for (Tile t : tiles) {

            t.render(graphics, playerX);
        }

    }

    /*
    * loadMap - Laden der Map-Dateien
    * */
    public void loadMap() {
        int counterX = 0;
        int counterY = 0;
        int indexCounter = 0;
        String[] numbersInRow;
        String line;

        try {
            Scanner scanner = new Scanner(new FileReader(mapFilePath));
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();

                numbersInRow = line.split("[,]");
                //tileType = Integer.parseInt(scanner.next());
                //tileXPos = Integer.parseInt(scanner.next());
                //tileYPos = Integer.parseInt(scanner.next());

                for(String number : numbersInRow) {
                    try {
                        switch (Integer.parseInt(number)) {
                            case 0:
                                break;
                            case Tile.WATERTILE_TOP:
                                tiles.add(new WaterTile(waterTileTop, counterX, counterY, true, false, false, indexCounter));
                                indexCounter++;
                                break;
                            case Tile.WATERTILE:
                                tiles.add(new WaterTile(waterTile, counterX, counterY, true, false, false, indexCounter));
                                indexCounter++;
                                break;
                            case Tile.LAVALTILETOP:
                                tiles.add(new LavaTile(lavaTileTop, counterX, counterY, true, false, false, indexCounter));
                                indexCounter++;
                                break;
                            case Tile.LAVATILE:
                                tiles.add(new LavaTile(lavaTile, counterX, counterY, true, false, false, indexCounter));
                                indexCounter++;
                                break;
                            case Tile.GRASTILE_RIGHT:
                                tiles.add(new GrasTile(grasTileRight, counterX, counterY, true, true, true, indexCounter));
                                indexCounter++;
                                break;
                            case Tile.GRASTILE_LEFT:
                                tiles.add(new GrasTile(grasTileLeft, counterX, counterY, true, true, true, indexCounter));
                                indexCounter++;
                                break;
                            case Tile.GRASTILE_BOTTOM:
                                tiles.add(new GrasTile(grasTileBottom, counterX, counterY, true, true, true, indexCounter));
                                indexCounter++;
                                break;
                            case Tile.GRASTILE_BOTTOMRIGHTCORNER:
                                tiles.add(new GrasTile(grasTileBottomRightCorner, counterX, counterY, true, true, true, indexCounter));
                                indexCounter++;
                                break;
                            case Tile.GRASTILE_BOTTOMLEFTCORNER:
                                tiles.add(new GrasTile(grasTileBottomLeftCorner, counterX, counterY, true, true, true, indexCounter));
                                indexCounter++;
                                break;
                            case Tile.GRASTILE_TOPRIGHTCORNER:
                                tiles.add(new GrasTile(grasTileTopRightCorner, counterX, counterY, true, true, true, indexCounter));
                                indexCounter++;
                                break;
                            case Tile.GRASTILE_TOPLEFTCORNER:
                                tiles.add(new GrasTile(grasTileTopLeftCorner, counterX, counterY, true, true, true, indexCounter));
                                indexCounter++;
                                break;
                            case Tile.GRASTILE:
                                tiles.add(new GrasTile(grasTile, counterX, counterY, true, true, true, indexCounter));
                                indexCounter++;
                                break;
                            case Tile.DIRTDARK:
                                tiles.add(new DirtTile(dirtDark, counterX, counterY, true, true, true, indexCounter));
                                indexCounter++;
                                break;
                            case Tile.DIRTMIDDARK:
                                tiles.add(new DirtTile(dirtMidDark, counterX, counterY, true, true, true, indexCounter));
                                indexCounter++;
                                break;
                            case Tile.DIRT:
                                tiles.add(new DirtTile(dirt, counterX, counterY, true, true, true, indexCounter));
                                indexCounter++;
                                break;
                            case Tile.STONEWHITE1:
                                tiles.add(new StoneTile(stoneWhite1, counterX, counterY, true, true, false, indexCounter));
                                indexCounter++;
                                break;
                            case Tile.STONEWHITE2:
                                tiles.add(new StoneTile(stoneWhite2, counterX, counterY, true, true, false, indexCounter));
                                indexCounter++;
                                break;
                            case Tile.STONEWHITE3:
                                tiles.add(new StoneTile(stoneWhite3, counterX, counterY, true, true, false, indexCounter));
                                indexCounter++;
                                break;
                            case Tile.STONEWHITELEAF:
                                tiles.add(new StoneTile(stoneWhiteLeafs, counterX, counterY, true, true, false, indexCounter));
                                indexCounter++;
                                break;
                            case Tile.STONERED1:
                                tiles.add(new StoneTile(stoneRed1, counterX, counterY, true, true, false, indexCounter));
                                indexCounter++;
                                break;
                            case Tile.STONERED2:
                                tiles.add(new StoneTile(stoneRed2, counterX, counterY, true, true, false, indexCounter));
                                indexCounter++;
                                break;
                            case Tile.STONERED3:
                                tiles.add(new StoneTile(stoneRed3, counterX, counterY, true, true, false, indexCounter));
                                indexCounter++;
                                break;
                            case Tile.STONEREDLEAF:
                                tiles.add(new StoneTile(stoneRedLeafs, counterX, counterY, true, true, false, indexCounter));
                                indexCounter++;
                                break;
                            case Tile.COPPER:
                                tiles.add(new CopperTile(copper, counterX, counterY, true, true, true, indexCounter));
                                indexCounter++;
                                break;
                            case Tile.SILVER:
                                tiles.add(new SilverTile(silver, counterX, counterY, true, true, true, indexCounter));
                                indexCounter++;
                                break;
                            case Tile.GOLD:
                                tiles.add(new GoldTile(gold, counterX, counterY, true, true, true, indexCounter));
                                indexCounter++;
                                break;
                            case Tile.LEAFBOTTOM_LEFTCORNER:
                                tiles.add(new LeafTile(leafBottomLeftCorner, counterX, counterY, true, false, true, indexCounter, true));
                                indexCounter++;
                                break;
                            case Tile.LEAFBOTTOM:
                                tiles.add(new LeafTile(leafBottom, counterX, counterY, true, false, true, indexCounter, true));
                                indexCounter++;
                                break;
                            case Tile.LEAFBOTTOM_RIGHTCORNER:
                                tiles.add(new LeafTile(leafBottomRightCorner, counterX, counterY, true, false, true, indexCounter, true));
                                indexCounter++;
                                break;
                            case Tile.LEAFRIGHT:
                                tiles.add(new LeafTile(leafRight, counterX, counterY, true, false, true, indexCounter, true));
                                indexCounter++;
                                break;
                            case Tile.LEAFNORMAL:
                                tiles.add(new LeafTile(leafNormal, counterX, counterY, true, false, true, indexCounter, true));
                                indexCounter++;
                                break;
                            case Tile.LEAFLEFT:
                                tiles.add(new LeafTile(leafLeft, counterX, counterY, true, false, true, indexCounter, true));
                                indexCounter++;
                                break;
                            case Tile.LEAFTOP_LEFTCORNER:
                                tiles.add(new LeafTile(leafTopLeftCorner, counterX, counterY, true, false, true, indexCounter, true));
                                indexCounter++;
                                break;
                            case Tile.LEAFTOP:
                                tiles.add(new LeafTile(leafTop, counterX, counterY, true, false, true, indexCounter, true));
                                indexCounter++;
                                break;
                            case Tile.LEAFTOP_RIGHTCORNER:
                                tiles.add(new LeafTile(leafTopRightCorner, counterX, counterY, true, false, true, indexCounter, true));
                                indexCounter++;
                                break;
                            case Tile.TREETRUNK_ROOTLEFT:
                                tiles.add(new WoodTile(treeTrunkRootLeft, counterX, counterY, true, true, true, indexCounter, true));
                                indexCounter++;
                                break;
                            case Tile.TREETRUNK_BOTTOMLEFT:
                                tiles.add(new WoodTile(treeTrunkBottomLeft, counterX, counterY, true, true, true, indexCounter, true));
                                indexCounter++;
                                break;
                            case Tile.TREETRUNK_BOTTOMRIGHT:
                                tiles.add(new WoodTile(treeTrunkBottomRight, counterX, counterY, true, true, true, indexCounter, true));
                                indexCounter++;
                                break;
                            case Tile.TREETRUNK_ROOTRIGHT:
                                tiles.add(new WoodTile(treeTrunkRootRight, counterX, counterY, true, true, true, indexCounter, true));
                                indexCounter++;
                                break;
                            case Tile.TREETRUNK_ROUNDEDCORNER_TOPLEFT:
                                tiles.add(new WoodTile(treeTrunkRoundedCornerTopLeft, counterX, counterY, true, true, true, indexCounter, true));
                                indexCounter++;
                                break;
                            case Tile.TREETRUNK_NEXTTOCORNER:
                                tiles.add(new WoodTile(treeTrunkNextToCorner, counterX, counterY, true, true, true, indexCounter, true));
                                indexCounter++;
                                break;
                            case Tile.TREETRUNK_HORIZONTALNORMAL:
                                tiles.add(new WoodTile(treeTrunkHorizontalNormal, counterX, counterY, true, true, true, indexCounter, true));
                                indexCounter++;
                                break;
                            case Tile.TREETRUNK_ROUNDEDCORNER_BOTTOMRIGHT:
                                tiles.add(new WoodTile(treeTrunkRoundedCornerBottomRight, counterX, counterY, true, true, true, indexCounter, true));
                                indexCounter++;
                                break;
                            case Tile.TREETRUNK_VERTICALNORMAL:
                                tiles.add(new WoodTile(treeTrunkVerticalNormal, counterX, counterY, true, true, true, indexCounter, true));
                                indexCounter++;
                                break;
                            case Tile.TREETRUNK_TOPCENTER:
                                tiles.add(new WoodTile(treeTrunkTopCenter, counterX, counterY, true, true, true, indexCounter, true));
                                indexCounter++;
                                break;
                            case Tile.TREETRUNK_TOPLEFT:
                                tiles.add(new WoodTile(treeTrunkTopLeft, counterX, counterY, true, true, true, indexCounter, true));
                                indexCounter++;
                                break;
                            case Tile.TREETRUNK_TOPLEFTEND:
                                tiles.add(new WoodTile(treeTrunkTopLeftEnd, counterX, counterY, true, true, true, indexCounter, true));
                                indexCounter++;
                                break;
                            case Tile.TREETRUNK_TOPRIGHTEND:
                                tiles.add(new WoodTile(treeTrunkTopRightEnd, counterX, counterY, true, true, true, indexCounter, true));
                                indexCounter++;
                                break;
                            default:
                                tiles.add(new DirtTile(dirt, counterX, counterY, true, true, true, indexCounter));
                                indexCounter++;
                                break;
                        }
                        counterX += Tile.WIDTH;
                    }
                    catch (NumberFormatException ex) {
                        continue;
                    }

                }
                counterX = 0;
                counterY += Tile.HEIGHT;
            }
            scanner.close();
        }
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    /*
    * Getter and Setter
    * */
    public ArrayList<Tile> getTiles() {
        return this.tiles;
    }

    public Tile getTile(int index) {
        return tiles.get(index);
    }

    /*
    * EventListener
    * */
    public void mouseClicked(MouseEvent e) {
        // Mauszeiger Klickpunkt
        Point point = e.getPoint();

        // Anzahl der Tiles in der TileMap
        int tileMapSize = tiles.size();

        for(int i = 0; i < tileMapSize; i++) {
            selectedTile = tiles.get(i);

            // Setze Rechteck mit den selben Maßen und Koordinaten wie ausgewaehlter Tile
            tileSelectedBounds = new Rectangle(selectedTile.getX(), selectedTile.getY(), Tile.WIDTH, Tile.HEIGHT);

            // Wenn Maus-Klickpunkt im Rechteck liegt
            if(tileSelectedBounds.contains(point)) {
                /*
                * Ist Tile zerstoerbar
                * */
                if(selectedTile.isDestructable) {
                    tiles.get(i).delete();   // Setze Texture == null, dass Loeschen wird in der Update-Methode vollzogen
                }

                /*
                * Wird Tile von der Schwerkraft angezogen
                * */
                if(selectedTile.hasGravity) {
                    try {
                        int maxFallDistanc;
                        // Durchlauf alles nochmal und Pruefe welche Tile ueber angeklickten liegt
                        // Noch ineffizient, muss geaendert werden!!
                        for(int j = 0; j < tileMapSize; j++) {
                            selectedTileForGravity = tiles.get(j);
                            if(selectedTileForGravity.getX() == selectedTile.getX() && (selectedTile.getY()-selectedTileForGravity.getY())==Tile.HEIGHT) {
                                selectedTileForGravity.setY(selectedTile.getY());
                            }
                        }
                    }
                    catch (IndexOutOfBoundsException ex) {
                        ex.printStackTrace();
                    }
                }

                /*
                * Wenn Tile zu einem Baum gehört, lösche gesamten Baum, egal inwelchem Baumbereich geklickt wurde
                * */
                if( (selectedTile instanceof WoodTile && ((WoodTile) selectedTile).belongsToTree) || (selectedTile instanceof LeafTile && ((LeafTile) selectedTile).belongsToTree) ) {

                    for (int k = 0; k < tileMapSize; k++) {

                        if (tiles.get(k) instanceof WoodTile || tiles.get(k) instanceof LeafTile) {
                            if (selectedTile.getX() - tiles.get(k).getX() < Math.abs(4*Tile.WIDTH)) {
                                tiles.get(k).delete();
                            }
                            if (selectedTile.getY() - tiles.get(k).getY() < Math.abs(11*Tile.HEIGHT)) {
                                tiles.get(k).delete();
                            }
                        }

                    }

                }

            }
            // Aktualisieren der ListGroesse , da Elemente geloescht werden
            tileMapSize = getTiles().size();
        }
    }

}
