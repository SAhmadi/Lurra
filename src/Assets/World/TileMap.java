package Assets.World;

import Assets.GameObjects.Player;
import Assets.GameObjects.Weapon;
import Assets.Inventory.Inventory;
import GameSaves.PlayerData.PlayerData;
import Main.ResourceLoader;
import Main.ScreenDimensions;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/*
* TileMap - Spielfeld bestehend aus Tiles
* */
public class TileMap implements Serializable {
    /*
    * TileMap
    * */
    // Position
    private int width = 0;
    private int height = 0;
    private double x = 0;
    private double y = 0;

    // Grenzen
    private int xmin = 0, ymin = 0;
    private int xmax = 0, ymax = 0;

    // Anzahl der Spalten und Reihen
    public static int numberOfColumns = 0, numberOfRows = 0;
    private int puffer = 2;

    // Offset
    private int columnOffset = 0, rowOffset = 0;
    private int numberOfColumnsToDraw = 0, numberOfRowsToDraw = 0;
    private int rowGenerateStartPoint = 0;

    /*
    * Tiles
    * */
    public static ArrayList<ArrayList<Tile>> tiles = null;

    // Map - Pfad
    private String mapFilePath = "";

    // MouseClicked - Variablen
    private Rectangle selectedTileBounds = null;



    // MausPosition
    public static int mouseX = 0, mouseY = 0;

    private double damage = 1;


    /*
    * Konstruktor
    * */
    public TileMap(String mapFilePath, int numberOfColumns, int numberOfRows) {
        this.mapFilePath = mapFilePath;
        this.numberOfColumns = numberOfColumns;
        this.numberOfRows = numberOfRows;
        this.rowGenerateStartPoint = (ScreenDimensions.HEIGHT/Tile.HEIGHT)/2;

        this.tiles = new ArrayList<ArrayList<Tile>>();

        // Anzahl Spalten und Reihe zum Zeichnen
        this.numberOfColumnsToDraw = ScreenDimensions.WIDTH / Tile.WIDTH + this.puffer;
        this.numberOfRowsToDraw = ScreenDimensions.HEIGHT / Tile.HEIGHT + this.puffer;

        width = numberOfColumns * Tile.WIDTH;
        height = numberOfRows * Tile.HEIGHT;

        xmin = ScreenDimensions.WIDTH - width;
        ymin = ScreenDimensions.HEIGHT - height;
        xmax = 0;
        ymax = 0;

    }


    /*
    * update
    * */
    public void update() {}

    /*
    * render
    *
    * @param graphics   - Graphics Objekt
    * */
    public void render(Graphics graphics) {
        // Aufrufen der render-Methode des durchlaufenen Tiles
        for(int row = rowOffset; row < rowOffset+numberOfRowsToDraw; row++) {
            if(row >= tiles.size()) break;

            for(int column = columnOffset; column < columnOffset+numberOfColumnsToDraw; column++) {
                try {
                    if(column >= tiles.get(row).size()) break;

                    tiles.get(row).get(column).setX((int) x + column * Tile.WIDTH);
                    tiles.get(row).get(column).setY((int) y + row * Tile.HEIGHT);
                    tiles.get(row).get(column).setRow((int) y * Tile.HEIGHT);
                    tiles.get(row).get(column).setColumn((int) x * Tile.WIDTH);
                    tiles.get(row).get(column).render(graphics);
//                    graphics.drawImage(tiles.get(row).get(column).getTexture(), (int) x + column * Tile.WIDTH, (int) y + row * Tile.HEIGHT, null);

                    if(tiles.get(row).get(column).isDestructible && !Inventory.isDrawerOpen) {
                        if(mouseX > tiles.get(row).get(column).getX() && mouseX < tiles.get(row).get(column).getX() + Tile.WIDTH &&
                                mouseY > tiles.get(row).get(column).getY() && mouseY < tiles.get(row).get(column).getY() + Tile.HEIGHT) {
                            graphics.setColor(new Color(255, 255, 255, 180));
                            graphics.fillRect(tiles.get(row).get(column).getX(), tiles.get(row).get(column).getY(), Tile.WIDTH, Tile.HEIGHT);
                        }
                    }
                }
                catch(IndexOutOfBoundsException ex) {

                }

            }
        }
    }


    /**
     *
     *
     *
     * */
    public void generateMap(int flatness) {
        BufferedImage[] earthTextures = {ResourceLoader.dirt,ResourceLoader.dirtMidDark, ResourceLoader.dirtDark};
        BufferedImage[] metalTextures = {ResourceLoader.gold, ResourceLoader.copper, ResourceLoader.silver};

        // Initialisiere Map
        for(int row = 0; row < numberOfRows; row++) {
            tiles.add(new ArrayList<Tile>());

            for (int column = 0; column < numberOfColumns; column++) {

                tiles.get(row).add(new Tile(null, column*Tile.WIDTH, row*Tile.HEIGHT, row, column, false, false, false));
            }
        }

        // Erd-Layer
        int col = flatness;
        while(col < numberOfColumns - flatness) {
            try {
                for(int i = 0; i < flatness; i++) {
                    if(tiles.get(rowGenerateStartPoint).get(col + i).getTexture() == null) {
                        tiles.get(rowGenerateStartPoint).get(col + i).setTexture(earthTextures[new Random().nextInt(3)]);
                        tiles.get(rowGenerateStartPoint).get(col + i).setIsCollidable(true);
                        tiles.get(rowGenerateStartPoint).get(col + i).setHasGravity(true);
                        tiles.get(rowGenerateStartPoint).get(col + i).setIsDestructible(true);

                        for (int j = 1; j < numberOfRows - rowGenerateStartPoint; j++) {
                            try {
                                if(tiles.get(rowGenerateStartPoint + j).get(col + i).getTexture() == null) {
                                    tiles.get(rowGenerateStartPoint + j).get(col + i).setTexture(earthTextures[new Random().nextInt(3)]);
                                    tiles.get(rowGenerateStartPoint + j).get(col + i).setIsCollidable(true);
                                    tiles.get(rowGenerateStartPoint + j).get(col + i).setHasGravity(true);
                                    tiles.get(rowGenerateStartPoint + j).get(col + i).setIsDestructible(true);
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            if (new Random().nextInt(10) < 5) {
                if (rowGenerateStartPoint++ >= numberOfRows - numberOfRows/5)
                    rowGenerateStartPoint -= numberOfRows/10;
                else
                    rowGenerateStartPoint++;
            } else {
                if (rowGenerateStartPoint-- <= numberOfRows/8)
                    rowGenerateStartPoint += numberOfRows/10;
                else
                    rowGenerateStartPoint--;
            }

            col++;
        }

        // Gras-Layer
        for(int row = 0; row < numberOfRows; row++) {
            for(int column = 0; column < numberOfColumns; column++) {
                try {
                    // Positioniere Grastile auf Erdtile-Oberste-Schicht
                    if((tiles.get(row + 1).get(column).getTexture() == ResourceLoader.dirt
                            || tiles.get(row + 1).get(column).getTexture() == ResourceLoader.dirtMidDark
                            || tiles.get(row + 1).get(column).getTexture() == ResourceLoader.dirtDark)
                            && tiles.get(row - 1).get(column).getTexture() == null) {

                        tiles.get(row).get(column).setTexture(ResourceLoader.grasTile);
                        tiles.get(row).get(column).setIsCollidable(true);
                        tiles.get(row).get(column).setHasGravity(true);
                        tiles.get(row).get(column).setIsDestructible(true);

                    }
                }
                catch(Exception ex) {}
            }
        }


        // Lava-Layer
        int lavaStartRowLeft = 0;
        int lavaStartRowRight = 0;
        outerloop:
        for(int row = 0; row < numberOfRows; row++) {
            for(int columnLeft = 0; columnLeft <= flatness; columnLeft++) {
                try
                {
                    if(tiles.get(row).get(columnLeft + 1).getTexture() == ResourceLoader.dirt
                            || tiles.get(row).get(columnLeft + 1).getTexture() == ResourceLoader.dirtMidDark
                            || tiles.get(row).get(columnLeft + 1).getTexture() == ResourceLoader.dirtDark
                            || tiles.get(row).get(columnLeft + 1).getTexture() == ResourceLoader.grasTile) {

                        lavaStartRowLeft = row;
                        break;

                    }
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }

            }

            for(int columnRight = numberOfColumns - flatness; columnRight < numberOfColumns; columnRight++) {
                try
                {
                    if(tiles.get(row + 1).get(columnRight).getTexture() == ResourceLoader.dirt
                            || tiles.get(row + 1).get(columnRight).getTexture() == ResourceLoader.dirtMidDark
                            || tiles.get(row + 1).get(columnRight).getTexture() == ResourceLoader.dirtDark
                            || tiles.get(row).get(columnRight).getTexture() == ResourceLoader.grasTile) {

                        lavaStartRowRight = row;
                        break outerloop;

                    }
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }

        for(int row = 0; row < numberOfRows; row++) {
            for(int column = 0; column < numberOfColumns; column++) {
                try
                {
                    if(column < flatness || column > numberOfColumns - flatness) {
                        if(row > lavaStartRowLeft && column < flatness && tiles.get(row).get(column).getTexture() == null) {
                            if(row == lavaStartRowLeft + 1) {
                                try {
                                    // Positioniere Grastile auf Erdtile-Oberste-Schicht
                                    tiles.get(row).get(column).setTexture(ResourceLoader.lavaTileTop);
                                    tiles.get(row).get(column).setIsCollidable(false);
                                    tiles.get(row).get(column).setHasGravity(false);
                                    tiles.get(row).get(column).setIsDestructible(false);
                                }
                                catch(Exception ex) {}
                            }
                            else if(tiles.get(row).get(column).getTexture() == null) {
                                try {
                                    // Positioniere Grastile auf Erdtile-Oberste-Schicht
                                    tiles.get(row).get(column).setTexture(ResourceLoader.lavaTile);
                                    tiles.get(row).get(column).setIsCollidable(false);
                                    tiles.get(row).get(column).setHasGravity(false);
                                    tiles.get(row).get(column).setIsDestructible(false);
                                }
                                catch(Exception ex) {}

                            }
                        }
                        else if(row > lavaStartRowRight && column > numberOfColumns - flatness) {
                            if(row == lavaStartRowRight + 1 && tiles.get(row).get(column).getTexture() == null) {
                                try {
                                    // Positioniere Grastile auf Erdtile-Oberste-Schicht
                                    tiles.get(row).get(column).setTexture(ResourceLoader.lavaTileTop);
                                    tiles.get(row).get(column).setIsCollidable(false);
                                    tiles.get(row).get(column).setHasGravity(false);
                                    tiles.get(row).get(column).setIsDestructible(false);
                                }
                                catch(Exception ex) {}
                            }
                            else if(tiles.get(row).get(column).getTexture() == null) {
                                try {
                                    // Positioniere Grastile auf Erdtile-Oberste-Schicht
                                    tiles.get(row).get(column).setTexture(ResourceLoader.lavaTile);
                                    tiles.get(row).get(column).setIsCollidable(false);
                                    tiles.get(row).get(column).setHasGravity(false);
                                    tiles.get(row).get(column).setIsDestructible(false);
                                }
                                catch(Exception ex) {}

                            }
                        }
                    }
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }


        // Baum
        boolean setTree = false;
        int treeStartPoint;
        for(int row = 0; row < numberOfRows; row++) {
            for(int column = 0; column < numberOfColumns; column++) {
                try
                {
                    for(int i = 0; i < 9; i++) {
                        if(tiles.get(row).get(column + i).getTexture() == ResourceLoader.grasTile) {
                            setTree = true;
                        }
                        else {
                            setTree = false;
                            break;
                        }
                    }

                    if(setTree) {
                        // Baumstamm
                        try {
                            treeStartPoint = new Random().nextInt(5);
                            tiles.get(row - 1).get(column + treeStartPoint).setTexture(ResourceLoader.treeTrunkRootLeft);
                            tiles.get(row - 1).get(column + treeStartPoint + 1).setTexture(ResourceLoader.treeTrunkBottomLeft);
                            tiles.get(row - 1).get(column + treeStartPoint + 2).setTexture(ResourceLoader.treeTrunkBottomRight);
                            tiles.get(row - 1).get(column + treeStartPoint + 3).setTexture(ResourceLoader.treeTrunkRootRight);

                            tiles.get(row - 2).get(column + treeStartPoint + 1).setTexture(ResourceLoader.treeTrunkRoundedCornerTopLeft);
                            tiles.get(row - 2).get(column + treeStartPoint + 2).setTexture(ResourceLoader.treeTrunkNextToCorner);
                            tiles.get(row - 2).get(column + treeStartPoint + 3).setTexture(ResourceLoader.treeTrunkHorizontalNormal);
                            tiles.get(row - 2).get(column + treeStartPoint + 4).setTexture(ResourceLoader.treeTrunkRoundedCornerBottomRight);

                            tiles.get(row - 3).get(column + treeStartPoint + 4).setTexture(ResourceLoader.treeTrunkVerticalNormal);

                            tiles.get(row - 4).get(column + treeStartPoint + 4).setTexture(ResourceLoader.treeTrunkTopCenter);
                            tiles.get(row - 4).get(column + treeStartPoint + 3).setTexture(ResourceLoader.treeTrunkTopLeft);
                            tiles.get(row - 4).get(column + treeStartPoint + 2).setTexture(ResourceLoader.treeTrunkTopLeftEnd);
                            tiles.get(row - 4).get(column + treeStartPoint + 5).setTexture(ResourceLoader.treeTrunkTopRightEnd);

                            // Baumkrone
                            tiles.get(row - 5).get(column + treeStartPoint + 7).setTexture(ResourceLoader.leafBottomRightCorner);
                            tiles.get(row - 5).get(column + treeStartPoint + 6).setTexture(ResourceLoader.leafBottom);
                            tiles.get(row - 5).get(column + treeStartPoint + 5).setTexture(ResourceLoader.leafBottom);
                            tiles.get(row - 5).get(column + treeStartPoint + 4).setTexture(ResourceLoader.leafBottom);
                            tiles.get(row - 5).get(column + treeStartPoint + 3).setTexture(ResourceLoader.leafBottom);
                            tiles.get(row - 5).get(column + treeStartPoint + 2).setTexture(ResourceLoader.leafBottom);
                            tiles.get(row - 5).get(column + treeStartPoint + 1).setTexture(ResourceLoader.leafBottomLeftCorner);

                            tiles.get(row - 6).get(column + treeStartPoint + 7).setTexture(ResourceLoader.leafRight);
                            tiles.get(row - 6).get(column + treeStartPoint + 6).setTexture(ResourceLoader.leafNormal);
                            tiles.get(row - 6).get(column + treeStartPoint + 5).setTexture(ResourceLoader.leafNormal);
                            tiles.get(row - 6).get(column + treeStartPoint + 4).setTexture(ResourceLoader.leafNormal);
                            tiles.get(row - 6).get(column + treeStartPoint + 3).setTexture(ResourceLoader.leafNormal);
                            tiles.get(row - 6).get(column + treeStartPoint + 2).setTexture(ResourceLoader.leafNormal);
                            tiles.get(row - 6).get(column + treeStartPoint + 1).setTexture(ResourceLoader.leafLeft);

                            tiles.get(row - 7).get(column + treeStartPoint + 7).setTexture(ResourceLoader.leafTopRightCorner);
                            tiles.get(row - 7).get(column + treeStartPoint + 6).setTexture(ResourceLoader.leafTop);
                            tiles.get(row - 7).get(column + treeStartPoint + 5).setTexture(ResourceLoader.leafTop);
                            tiles.get(row - 7).get(column + treeStartPoint + 4).setTexture(ResourceLoader.leafTop);
                            tiles.get(row - 7).get(column + treeStartPoint + 3).setTexture(ResourceLoader.leafTop);
                            tiles.get(row - 7).get(column + treeStartPoint + 2).setTexture(ResourceLoader.leafTop);
                            tiles.get(row - 7).get(column + treeStartPoint + 1).setTexture(ResourceLoader.leafTopLeftCorner);
                        }
                        catch(Exception ex) {
                            ex.printStackTrace();
                        }

                        break;
                    }
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }

            }
        }

        // Gold-Kupfer-Silber
        for(int row = numberOfRows/10; row < numberOfRows; row++) {
            for (int column = 0; column < numberOfColumns; column++) {

                try
                {
                    if ((tiles.get(row).get(column).getTexture() == ResourceLoader.dirt
                            || tiles.get(row).get(column).getTexture() == ResourceLoader.dirtMidDark
                            || tiles.get(row).get(column).getTexture() == ResourceLoader.dirtDark)
                            && tiles.get(row - 1).get(column).getTexture() != ResourceLoader.grasTile) {

                        if (new Random().nextInt(100) < 5) {
                            tiles.get(row).get(column).setTexture(metalTextures[new Random().nextInt(metalTextures.length)]);
                        }

                    }
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }

        /*
        * Speichern der erzeugten Welt
        * */
        if(mapFilePath != null) {
            levelSave(tiles, PlayerData.name);
        }

    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public static void levelSave(ArrayList<ArrayList<Tile>> tiles, String filename) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            Element rootElement = doc.createElement("tiles");
            doc.appendChild(rootElement);

            /* TILES */
            for(int row = 0; row < numberOfRows; row++) {
                for(int column = 0; column < numberOfColumns; column++) {
                    Element tileElement = doc.createElement("tile");
                    tileElement.setAttribute("texture", tiles.get(row).get(column).getTextureAsString());
                    tileElement.setAttribute("x", Integer.toString(tiles.get(row).get(column).getX()));
                    tileElement.setAttribute("y", Integer.toString(tiles.get(row).get(column).getY()));
                    tileElement.setAttribute("row", Integer.toString(tiles.get(row).get(column).getRow()));
                    tileElement.setAttribute("column", Integer.toString(tiles.get(row).get(column).getColumn()));
                    tileElement.setAttribute("collidable", Boolean.toString(tiles.get(row).get(column).isCollidable));
                    tileElement.setAttribute("gravity", Boolean.toString(tiles.get(row).get(column).hasGravity));
                    tileElement.setAttribute("destructable", Boolean.toString(tiles.get(row).get(column).isDestructible));

                    rootElement.appendChild(tileElement);
                }
            }


            // Als XML schreiben
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("res/xml/playerLevelSaves/" + filename + ".xml"));
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(source, result);
        }
        catch(ParserConfigurationException ex) {
            ex.printStackTrace();
        }
        catch(TransformerException ex) {
            ex.printStackTrace();
        }
    }

    public void levelLoad(String filename) {
        try {
            System.out.println("Laden anfange");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(new File("res/xml/playerLevelSaves/" + filename + ".xml"));

            //Einstellungen
            NodeList tilesList = document.getElementsByTagName("*");

            boolean collidable, destructable, gravity;
            int row, column, x, y;
            BufferedImage texture;

            for(int i = 0; i < numberOfRows; i++) {
                tiles.add(new ArrayList<Tile>());
            }

            for(int n = 0; n < tilesList.getLength(); n++) {
                Node tilesNode = tilesList.item(n);
                Element tilesNodeAsElement = (Element) tilesNode;

                try {
                    collidable = Boolean.parseBoolean(tilesNodeAsElement.getAttribute("collidable"));
                    destructable = Boolean.parseBoolean(tilesNodeAsElement.getAttribute("destructable"));
                    gravity = Boolean.parseBoolean(tilesNodeAsElement.getAttribute("gravity"));

                    row = (tilesNodeAsElement.getAttribute("row").equals("")) ? 0 : Integer.parseInt(tilesNodeAsElement.getAttribute("row"));
                    column = (tilesNodeAsElement.getAttribute("column").equals("")) ? 0 : Integer.parseInt(tilesNodeAsElement.getAttribute("column"));
                    x = (tilesNodeAsElement.getAttribute("x").equals("")) ? 0 : Integer.parseInt(tilesNodeAsElement.getAttribute("x"));
                    y = (tilesNodeAsElement.getAttribute("y").equals("")) ? 0 : Integer.parseInt(tilesNodeAsElement.getAttribute("y"));

                    texture = Tile.getTextureFromString(tilesNodeAsElement.getAttribute("texture"));

                    // Erzeugen der Tiles
                    tiles.get(row).add(new Tile(null, x, y, row, column, collidable, gravity, destructable));
                    tiles.get(row).get(column).setTexture(texture); // name, resistance und  belongsToTree werden atuomatisch gesetzt! siehe Tile-Klasse
                }
                catch(Exception ex) {
                    ex.printStackTrace();
                }

            }
            System.out.println("Geladen");

        }
        catch(ParserConfigurationException ex) {
            ex.printStackTrace();
        }
        catch (SAXException ex) {
            ex.printStackTrace();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

    }

//    public void create() {
//        BufferedImage[] earthTextures = {ResourceLoader.grasTile, ResourceLoader.dirtMidDark, ResourceLoader.dirtDark};
//
//            for(int row = rowOffset; row < rowOffset + numberOfRowsToDraw; row++) {
//                tiles.add(new ArrayList<Tile>());
//
//                for(int column = columnOffset; column < columnOffset + numberOfColumnsToDraw; column++) {
//
//                    if(row > rowGenerateStartPoint) {
//
//                        tiles.get(row).add(new Tile(earthTextures[new Random().nextInt(earthTextures.length)], column * Tile.WIDTH, (row ) * Tile.HEIGHT, row, column, true, true, true));
//
//                    }
//                }
//            }
//
//        // Update Grenzen
//        //xmax = ScreenDimensions.WIDTH - tiles.size()*Tile.WIDTH;
////        ymax = ScreenDimensions.HEIGHT - tiles.get(0).size()*Tile.HEIGHT;
//
//    }


    /*
    * Getter and Setter
    * */
    public int getWidth() { return this.width; }
    public int getHeight() { return this.height; }
    public double getX() { return this.x; }
    public double getY() { return this.y; }

    public ArrayList<ArrayList<Tile>> getTiles() { return this.tiles; }

    public Tile getTile(int row, int column) {
        try {
            return tiles.get(row).get(column);
        }
        catch(IndexOutOfBoundsException ex) {
            return null;
        }
    }

    public int getNumberOfColumns() { return this.numberOfColumns; }
    public int getNumberOfRows() { return this.numberOfRows; }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;

        // Auf Grenzen prüfen
        if(this.x < this.xmin)
            this.x = this.xmin;

        if(this.y < this.ymin)
            this.y = this.ymin;

        if(this.x > this.xmax)
            this.x = this.xmax;

        if(this.y > this.ymax)
            this.y = this.ymax;

        this.columnOffset = (int)-this.x / Tile.WIDTH;
        this.rowOffset = (int)-this.y / Tile.HEIGHT;
    }

    /*
    * EventListener
    * */
    public void mouseClicked(MouseEvent e) {
        boolean wasTreeHit = false;

        // Mauszeiger Klickpunkt
        Point point = e.getPoint();

        // Durchlaufe Liste und pruefe ob auf ein Tile geklickt wurde
        outerloop:
        for(int row = rowOffset; row < rowOffset + numberOfRowsToDraw; row++) {
            for(int column = columnOffset; column < columnOffset + numberOfColumnsToDraw; column++) {
                if(tiles.get(row).get(column) != null && tiles.get(row).get(column).getTexture() != null) {
                    // Erstelle Rechteck mit der Groesse des ausgewaehlten Tile
                    selectedTileBounds = new Rectangle(tiles.get(row).get(column).getX(), tiles.get(row).get(column).getY(), Tile.WIDTH, Tile.HEIGHT);

                    // Pruefe ob Klickpunkt im Bereich des Rechtecks liegt
                    if(selectedTileBounds.contains(point)) {

                        if(tiles.get(row).get(column).isCollidable) {
                            // Falls Tile zerstoerbar ist
                            if(tiles.get(row).get(column).isDestructible) {
                                // Falls Tile zerstoerbar ist loesche es
                                if (tiles.get(row).get(column).getResistance() == 0 && Player.currentWeapon == 2 || Player.currentWeapon == 4) {
                                    Inventory.addToInventory(tiles.get(row).get(column));
                                    System.out.print("Row " + row + " and Column " + column + "\n");
                                    System.out.println("X: " + tiles.get(row).get(column).getX());
                                    tiles.get(row).get(column).delete();
                                    if (tiles.get(row).get(column - 1).getTextureAsString() == "lavaTile"
                                            || tiles.get(row).get(column - 1).getTextureAsString() == "lavaTileTop") {
                                        tiles.get(row).get(column).setTexture(ResourceLoader.lavaTile);
                                    }
                                    if (tiles.get(row).get(column + 1).getTextureAsString() == "lavaTile") {
                                        tiles.get(row).get(column).setTexture(ResourceLoader.lavaTile);
                                    }
                                    if (tiles.get(row).get(column - 1).getTextureAsString() == "lavaTileTop") {
                                        tiles.get(row).get(column).setTexture(ResourceLoader.lavaTileTop);
                                    }
                                    if (tiles.get(row).get(column + 1).getTextureAsString() == "lavaTileTop") {
                                        tiles.get(row).get(column).setTexture(ResourceLoader.lavaTileTop);
                                    }

                                    if (tiles.get(row - 1).get(column).getTextureAsString() == "lavaTile"
                                            || tiles.get(row - 1).get(column).getTextureAsString() == "lavaTileTop") {
                                        tiles.get(row).get(column).setTexture(ResourceLoader.lavaTile);
                                    }

                                }

                                    break;
                                }
                                else {
                                    tiles.get(row).get(column).wasHit(damage);
                                }
                            }
                        }

                        if(tiles.get(row).get(column).belongsToTree) {
                            if(tiles.get(row).get(column).getResistance() == 0 && Player.currentWeapon == 0) {
                                Inventory.addToInventory(tiles.get(row).get(column));
                                tiles.get(row).get(column).delete();

                            }
                            else {
                                tiles.get(row).get(column).wasHit(damage);
                            }
                        }

                    }

                }


            }
        }


    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }
}