package Assets.GameObjects;

import Assets.TileMap;
import Assets.Tile;

import java.awt.*;
import java.awt.event.KeyEvent;

/*
* GameObject - Abstrakte Klasse, aller Spielobjekte
* */
public abstract class GameObject {

    // Animation
    protected Animation animation;
    protected int activeAnimation;

    // TileMap
    protected TileMap tileMap;
    protected int currentColumn;
    protected int currentRow;
    protected double xOnMap;
    protected double yOnMap;

    // Größe
    protected int width;
    protected int height;

    // Position
    protected double x;
    protected double y;
    protected double directionX;
    protected double directionY;

    // Bewegung
    protected double velocityX;
    protected double velocityY;
    protected double maxVelocityX;
    protected double maxVelocityY;
    protected boolean movingLeft;
    protected boolean movingRight;
    protected boolean jumping;
    protected boolean falling;

    // Kollision
    protected int widthForCollision;
    protected int heightForCollision;
    protected double xTmp, yTmp;
    protected double xDestination;
    protected double yDestination;
    protected boolean topLeftTile;
    protected boolean topRightTile;
    protected boolean bottomLeftTile;
    protected boolean bottomRightTile;


    /*
    * Konstruktor - Initialisieren
    *
    * @param width                  - Breite des Bildes
    * @param height                 - Höhe des Bildes
    * @param widthForCollision      - Breite des Kollisionsrechtecks
    * @param heightForCollision     - Höhe des Kollisionsrechtecks
    * @param velocityX              - Geschwindigkeit x-Achse
    * @param velocityY              - Geschwindigkeit y-Achse
    * @param maxVelocityX           - Maximalgeschwindigkeit x-Achse
    * @param maxVelocityY           - Maximalgeschwindigkeit y-Achse
    * @param tileMap                - Zugehörige TileMap
    * */
    public GameObject(int width, int height, int widthForCollision, int heightForCollision,
                      double velocityX, double velocityY, double maxVelocityX, double maxVelocityY,
                      TileMap tileMap) {

        this.width = width;
        this.height = height;
        this.widthForCollision = widthForCollision;
        this.heightForCollision = heightForCollision;

        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.maxVelocityX = maxVelocityX;
        this.maxVelocityY = maxVelocityY;

        this.tileMap = tileMap;
    }

    /*
    * update - Spieldaten, Spielphysik
    * */
    public abstract void update();

    /*
    * render - Darstellung der Veränderungen
    *
    * @param g  - Graphics Objekt
    * */
    public abstract void render(Graphics g);

    /*
    * checkFourCorners - Ausgehend vom mittlerem Tile, prüfen der vier Eck-Tiles auf Kollision
    *
    * @param x  - x-Koordinate des mittleren Tiles
    * @param y  - y-Koordinate des mittleren Tiles
    * */
    private void checkFourCorners(double x, double y) {
        // Berechnen der Zeile und Spalten, um Eck-Tiles zufinden
        int rowOfTopTile = (int) ((y - height/2) / Tile.HEIGHT);
        int rowOfBottomTile = (int) ((y + height/2 - 1) / Tile.HEIGHT);
        int columnOfLeftTile = (int) ((x - width/2) / Tile.WIDTH);
        int columnOfRightTile = (int) ((x + width/ 2 - 1) / Tile.WIDTH);

        // Prüfen, ob Eck-Tiles kollidierbar sind
        if (tileMap.getTile(rowOfTopTile, columnOfLeftTile) != null)
            topLeftTile = tileMap.getTile(rowOfTopTile, columnOfLeftTile).isCollidable;

        if (tileMap.getTile(rowOfTopTile, columnOfRightTile) != null)
            topRightTile = tileMap.getTile(rowOfTopTile, columnOfRightTile).isCollidable;

        if (tileMap.getTile(rowOfBottomTile, columnOfLeftTile) != null)
            bottomLeftTile = tileMap.getTile(rowOfBottomTile, columnOfLeftTile).isCollidable;

        if (tileMap.getTile(rowOfBottomTile, columnOfRightTile) != null)
            bottomRightTile = tileMap.getTile(rowOfBottomTile, columnOfRightTile).isCollidable;
    }

    /*
    * collisionWithTileMap - Prüfen, ob Objekt Tiles des TileMaps kollidiert
    * */
    public void collisionWithTileMap() {
        // Zum Verändern der Positions-Koordinaten
        // Ab jetzt werden die temporären Variablen verwendet
        xTmp = x;
        yTmp = y;

        // Aktive Zeile und Spalte
        currentRow = (int) y/Tile.HEIGHT;
        currentColumn = (int) x/Tile.WIDTH;

        // Ziel-Koordinaten
        xDestination = x + directionX;
        yDestination = y + directionY;

        /*
        * Kollisions-Prüfung
        * */
        // Veränderung auf der x-Achse
        checkFourCorners(xDestination, y);

        // Wenn nach links gelaufen wird
        if(directionX < 0) {

            if(topLeftTile || bottomLeftTile) {
                directionX = 0;

                xTmp = currentColumn * Tile.WIDTH + widthForCollision/2;
            }
            else {
                xTmp += directionX;
            }
        }

        // Wenn nach rechts gelaufen wird
        if(directionX > 0) {
            if(topRightTile || bottomRightTile) {
                directionX = 0;
                xTmp = (currentColumn+1) * Tile.WIDTH - widthForCollision/2;
            }
            else {
                xTmp += directionX;
            }
        }

        // Veränderung auf der y-Achse
        checkFourCorners(x, yDestination);

        // Wenn gefallen wird
        if(directionY > 0) {
            if(bottomLeftTile || bottomRightTile) {
                falling = false;
                directionY = 0;
                yTmp = (currentRow+1) * Tile.HEIGHT - heightForCollision/2;
            }
            else {
                yTmp += directionY;
            }
        }

        // Wenn gesprungen wird
        if(directionY < 0) {
            if(topLeftTile || topRightTile) {
                directionY = 0;
                yTmp = currentRow * Tile.HEIGHT - heightForCollision/2;
            }
            else {
                yTmp += directionY;
            }
        }

        // Wenn fallen nicht aktiviert, dann prüfe auf Kontakt mit Boden
        if(falling == false) {
            checkFourCorners(x, yDestination + Tile.HEIGHT);
            if(bottomLeftTile == false && bottomRightTile == false) {
                falling = true;
            }
        }
    }

    /*
    * KeyListener - Tastatureingaben
    *
    * @param e  - KeyEvent Objekt
    * */
    public abstract void keyPressed(KeyEvent e);
    public abstract void keyReleased(KeyEvent e);

    /////////////// Setter und Getter //////////////
    /*
    * setPosition - Setze Position
    *
    * @param x  - x-Koordinate des Objekts
    * @param y  - y-Koordinate des Objekts
    * */
    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /*
    * getX - Rückgabe der x-Koordinate des Objektes
    * */
    public double getX() { return this.x; }

    /*
    * getY - Rückgabe der y-Koordinate des Objekts
    * */
    public double getY() { return this.y; }

    /*
    * getWidth - Rückgabe der Bildbreite
    * */
    public int getWidth() { return this.width; }

    /*
    * getHeight - Rückgabe der Bildhöhe
    * */
    public int getHeight() { return this.height; }

    /*
    * setOnMap - Position auf der TileMap, da Objekte sonst außerhalb des Sichtbereiches liegen
    * */
    public void setOnMap() {
        xOnMap = tileMap.getX();
        yOnMap = tileMap.getY();
    }

}
