package Assets.GameObjects;

import Assets.World.TileMap;
import Main.References;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;

/**
 * Abstrakte Klasse, aller Spielobjekte
 *
 * @author Sirat
 * */
public abstract class GameObject
{

    // Animation
    protected Animation animation;
    protected int activeAnimation;

    // Map
    protected TileMap tileMap;
    protected int currentColumn;
    protected int currentRow;
    protected double xOnMap;
    protected double yOnMap;

    // Spielobjekt
    protected int width;            // Groesse
    protected int height;

    protected double x;             // Position
    protected double y;
    protected double xTmp;
    protected double yTmp;

    public static double xForTileMap;

    protected double directionX;    // Bewegung
    protected double directionY;
    protected double xDestination;
    protected double yDestination;
    protected double velocityX;
    protected double velocityY;
    protected double maxVelocityX;
    protected double maxVelocityY;
    protected boolean movingLeft;
    protected boolean movingRight;
    protected boolean jumping;
    protected boolean falling;
    protected boolean isFacingRight = true;

    protected int widthForCollision;    // Kollision
    protected int heightForCollision;
    protected boolean topLeftTile;
    protected boolean topRightTile;
    protected boolean bottomLeftTile;
    protected boolean bottomRightTile;
    protected boolean isInWater;


    /**
    * GameObject                    Konstruktor der GameObject-Klasse
    *
    * @param width                  Breite des Bildes
    * @param height                 Hoehe des Bildes
    * @param widthForCollision      Breite des Kollisionsrechtecks
    * @param heightForCollision     Hoehe des Kollisionsrechtecks
    * @param velocityX              Geschwindigkeit x-Achse
    * @param velocityY              Geschwindigkeit y-Achse
    * @param maxVelocityX           Maximalgeschwindigkeit x-Achse
    * @param maxVelocityY           Maximalgeschwindigkeit y-Achse
    * @param tileMap                Zugehoerige TileMap
    * */
    public GameObject(int width, int height, int widthForCollision, int heightForCollision,
                      double velocityX, double velocityY, double maxVelocityX, double maxVelocityY,
                      TileMap tileMap)
    {
        this.width = width;
        this.height = height;
        this.widthForCollision = widthForCollision;
        this.heightForCollision = heightForCollision;

        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.maxVelocityX = maxVelocityX;
        this.maxVelocityY = maxVelocityY;

        this.tileMap = tileMap;

        this.isInWater = false;
    }

    /**
    * update        Spieldaten, Spielphysik
    * */
    public abstract void update();

    /**
    * render        Zeichnen des Objekts
    *
    * @param g      Graphics Objekt
    * */
    public abstract void render(Graphics g);

    /**
    * checkFourCorners      Ausgehend vom mittlerem Tile, pruefen der vier Eck-Tiles auf Kollision
    *
    * @param x              x-Koordinate des mittleren Tiles
    * @param y              y-Koordinate des mittleren Tiles
    * */
    private void checkFourCorners(double x, double y)
    {
        // Berechnen der Zeile und Spalten, um Eck-Tiles zufinden
        int rowOfTopTile = (int) (Math.floor((y - height/2) / References.TILE_SIZE)) ;
        int rowOfBottomTile = (int) (Math.floor((y + height/2 - 1) / References.TILE_SIZE));
        int columnOfLeftTile = (int) (Math.floor((x - width/2) / References.TILE_SIZE));
        int columnOfRightTile = (int) (Math.floor((x + width/ 2 - 1) / References.TILE_SIZE));

        // Pruefen, ob Eck-Tiles kollidierbar sind
        if (tileMap.getMap().get(new Point(rowOfTopTile, columnOfLeftTile)) != null)
            topLeftTile = tileMap.getMap().get(new Point(rowOfTopTile, columnOfLeftTile)).getIsCollidable();

        if (tileMap.getMap().get(new Point(rowOfTopTile, columnOfRightTile)) != null)
            topRightTile = tileMap.getMap().get(new Point(rowOfTopTile, columnOfRightTile)).getIsCollidable();

        if (tileMap.getMap().get(new Point(rowOfBottomTile, columnOfLeftTile)) != null)
            bottomLeftTile = tileMap.getMap().get(new Point(rowOfBottomTile, columnOfLeftTile)).getIsCollidable();

        if (tileMap.getMap().get(new Point(rowOfBottomTile, columnOfRightTile)) != null)
            bottomRightTile = tileMap.getMap().get(new Point(rowOfBottomTile, columnOfRightTile)).getIsCollidable();
    }

    /**
     * checkIfWater         Pruefen, ob Objekt sich im Wasser befindet
     *
     * @param x             x-Koordinate des mittleren Tiles
     * @param y             y-Koordinate des mittleren Tiles
     * */
    private void checkIfWater(double x, double y)
    {
        // Berechnen der Zeile und Spalten, um Eck-Tiles zufinden
        int rowOfTopTile = (int) (Math.floor((y - height/2) / References.TILE_SIZE)) ;
        int rowOfBottomTile = (int) (Math.floor((y + height/2 - 1) / References.TILE_SIZE));
        int columnOfLeftTile = (int) (Math.floor((x - width/2) / References.TILE_SIZE));
        int columnOfRightTile = (int) (Math.floor((x + width/ 2 - 1) / References.TILE_SIZE));

        // Pruefen, ob Eck-Tiles kollidierbar sind
        if (tileMap.getMap().get(new Point(rowOfTopTile, columnOfLeftTile)) != null)
            isInWater = Arrays.asList(TileMap.waterTextures).contains(tileMap.getMap().get(new Point(rowOfTopTile, columnOfLeftTile)).getTexture());

        if (tileMap.getMap().get(new Point(rowOfTopTile, columnOfRightTile)) != null)
            isInWater = Arrays.asList(TileMap.waterTextures).contains(tileMap.getMap().get(new Point(rowOfTopTile, columnOfLeftTile)).getTexture());

        if (tileMap.getMap().get(new Point(rowOfBottomTile, columnOfLeftTile)) != null)
            isInWater = Arrays.asList(TileMap.waterTextures).contains(tileMap.getMap().get(new Point(rowOfTopTile, columnOfLeftTile)).getTexture());

        if (tileMap.getMap().get(new Point(rowOfBottomTile, columnOfRightTile)) != null)
            isInWater = Arrays.asList(TileMap.waterTextures).contains(tileMap.getMap().get(new Point(rowOfTopTile, columnOfLeftTile)).getTexture());
    }

    /**
    * collisionWithTileMap      Pruefen, ob Objekt Tiles des TileMaps kollidiert
    * */
    public void collisionWithTileMap()
    {
        // Zum Veraendern der Positions-Koordinaten
        // Ab jetzt werden die temporaeren Variablen verwendet
        xTmp = x;
        yTmp = y;

        // Aktive Zeile und Spalte
        currentRow = (int) (y / References.TILE_SIZE);
        currentColumn = (int) (x / References.TILE_SIZE);

        // Ziel-Koordinaten
        xDestination = x + directionX;
        yDestination = y + directionY;

        /*
        * Kollisions-Pruefung
        * */
        // Veraenderung auf der x-Achse
        checkFourCorners(xDestination, y);
        checkIfWater(xDestination, y);

        // Wenn nach links gelaufen wird
        if(directionX < 0)
        {
            if(topLeftTile || bottomLeftTile)
            {
                directionX = 0;
                xTmp = (currentColumn+1) * References.TILE_SIZE + widthForCollision/2;
            }
            else
                xTmp += directionX;
        }

        // Wenn nach rechts gelaufen wird
        if(directionX > 0)
        {
            if(topRightTile || bottomRightTile)
            {
                directionX = 0;
                xTmp = (currentColumn) * References.TILE_SIZE - widthForCollision/2;
            }
            else
                xTmp += directionX;
        }

        // Veraenderung auf der y-Achse
        checkFourCorners(x, yDestination);
        checkIfWater(x, yDestination);


        // Wenn gefallen wird
        if(directionY > 0)
        {
            if(bottomLeftTile || bottomRightTile)
            {
                falling = false;
                directionY = 0;
                yTmp = (currentRow+1) * References.TILE_SIZE - heightForCollision/2;
            }
            else
                yTmp += directionY;
        }

        // Wenn gesprungen wird
        if(directionY < 0)
        {
            if(topLeftTile || topRightTile)
            {
                directionY = 0;
                yTmp = currentRow * References.TILE_SIZE - heightForCollision/2;
            }
            else
                yTmp += directionY;
        }

        // Wenn fallen nicht aktiviert, dann pruefe auf Kontakt mit Boden
        if(!falling)
        {
            checkFourCorners(x, yDestination + References.TILE_SIZE);
            if(!bottomLeftTile && !bottomRightTile)
                falling = true;
        }
    }

    // LISTENER
    public abstract void keyPressed(KeyEvent e);
    public abstract void keyReleased(KeyEvent e);

    // GETTER UND SETTER
    /**
     * getX              Rueckgabe der x-Koordinate des Objekts
     * */
    public double getX() { return this.x; }

    /**
     * getY              Rueckgabe der y-Koordinate des Objekts
     * */
    public double getY() { return this.y; }

    /**
    * setPosition       Setzen der Position
    * @param x          x-Koordinate des Objekts
    * @param y          y-Koordinate des Objekts
    * */
    public void setPosition(double x, double y)
    {
        this.x = x;
        this.y = y;

        xForTileMap = this.x;
    }

    /**
    * getWidth          Rueckgabe der Bildbreite
    * @return int       Bildbreite
    * */
    public int getWidth() { return this.width; }

    /**
     * setWidth         Setzen der Bildbreite
     * @param width     Bildbreite
     * */
    public void setWidth(int width) { this.width = width; }

    /**
     * getHeight        Rueckgabe der Bildhoehe
     * @return int      Bildhoehe
     * */
    public int getHeight() { return this.height; }

    /**
     * setHeight        Setzen der Bildhoehe
     * @param height    Bildhoehe
     * */
    public void setHeight(int height) { this.height = height; }

    /**
     * getCurrentColumn         Rueckgabe der Spalte
     * @return int              Spalte
     * */
    public int getCurrentColumn() { return this.currentColumn; }

    /**
     * setCurrentColumn         Setzen der Spalte
     * @param currentColumn     Spalte
     * */
    public void setCurrentColumn(int currentColumn) { this.currentColumn = currentColumn; }

    /**
     * getCurrentRow            Rueckgabe der Zeile
     * @return int              Zeile
     * */
    public int getCurrentRow() { return this.currentRow; }

    /**
     * setCurrentRow            Setzen der Zeile
     * @param currentRow        Zeile
     * */
    public void setCurrentRow(int currentRow) { this.currentRow = currentRow; }

    /**
     * setOnMap                 Setzen des Spielers auf der TileMap
     * */
    public void setOnMap()
    {
        xOnMap = tileMap.getX();
        yOnMap = tileMap.getY();
    }

    /**
     * getActiveAnimation       Rueckgabe der aktiven Animation
     * @return int              Aktive Animation ID
     * */
    public int getActiveAnimation() { return activeAnimation; }

    /**
     * setActiveAnimation       Setzen der aktiven Animation
     * @param activeAnimation   Aktive Animation ID
     * */
    public void setActiveAnimation(int activeAnimation) { this.activeAnimation = activeAnimation; }

    /**
     * getTileMap               Rueckgabe der TileMap
     * @return TileMap          TileMap
     * */
    public TileMap getTileMap() { return tileMap; }

    /**
     * setTileMap               Setzen der TileMap
     * @param tileMap           TileMap
     * */
    public void setTileMap(TileMap tileMap) { this.tileMap = tileMap; }

    /**
     * getDirectionX            Rueckgabe der x Bewegungsrichtung
     * @return double           Bewegung auf der x-Achse
     * */
    public double getDirectionX() { return directionX; }

    /**
     * setDirectionX            Setzen der x Bewegungsrichtung
     * @param directionX        Bewegung auf der x-Achse
     * */
    public void setDirectionX(double directionX) { this.directionX = directionX; }

    /**
     * getDirectionY            Setzen der y Bewegungsrichtung
     * @return double           Bewegung auf der y-Achse
     * */
    public double getDirectionY() { return directionY; }

    /**
     * setDirectionY            Setzen der y Bewegungsrichtung
     * @param directionY        Bewegung auf der y-Achse
     * */
    public void setDirectionY(double directionY) { this.directionY = directionY; }

    /**
     * getXDestination          Rueckgabe der x Zielrichtung
     * @return double           Ziel auf der x-Achse
     * */
    public double getXDestination() { return xDestination; }

    /**
     * setXDestination          Setzen der x Zielrichtung
     * @param xDestination      Ziel auf der x-Achse
     * */
    public void setXDestination(double xDestination) { this.xDestination = xDestination; }

    /**
     * getYDestination          Rueckgabe der y Zielrichtung
     * @return double           Ziel auf der y-Achse
     * */
    public double getYDestination() { return yDestination; }

    /**
     * setYDestination          Setzen der y Zielrichtung
     * @param yDestination      Ziel auf der y-Achse
     * */
    public void setYDestination(double yDestination) { this.yDestination = yDestination; }

    /**
     * getVelocityX             Rueckgabe der x Geschwindigkeit
     * @return double           x-Geschwindigkeit
     * */
    public double getVelocityX() { return velocityX; }

    /**
     * setVelocityX             Setzen der x Geschwindigkeit
     * @param velocityX         x-Geschwindigkeit
     * */
    public void setVelocityX(double velocityX) { this.velocityX = velocityX; }

    /**
     * getVelocityY             Rueckgabe der y Geschwindigkeit
     * @return double           y-Geschwindigkeit
     * */
    public double getVelocityY() { return velocityY; }

    /**
     * setVelocityY             Setzen der y Geschwindigkeit
     * @param velocityY         y-Geschwindigkeit
     * */
    public void setVelocityY(double velocityY) { this.velocityY = velocityY; }

    /**
     * getMaxVelocityX          Rueckgabe der x Maximalgeschwindigkeit
     * @return double           x-Maximalgeschwindigkeit
     * */
    public double getMaxVelocityX() { return maxVelocityX; }

    /**
     * setMaxVelocityX          Setzen der x Maximalgeschwindigkeit
     * @param maxVelocityX      x-Maximalgeschwindigkeit
     * */
    public void setMaxVelocityX(double maxVelocityX) { this.maxVelocityX = maxVelocityX; }

    /**
     * getMaxVelocityY          Rueckgabe der y Maximalgeschwindigkeit
     * @return double           y-Maximalgeschwindigkeit
     * */
    public double getMaxVelocityY() { return maxVelocityY; }

    /**
     * setMaxVelocityY          Setzen der y Maximalgeschwindigkeit
     * @param maxVelocityY      y-Maximalgeschwindigkeit
     * */
    public void setMaxVelocityY(double maxVelocityY) { this.maxVelocityY = maxVelocityY; }

    /**
     * isMovingLeft             Rueckgabe ob nach links gelaufen wird
     * @return boolean          Wert ob nach links gelaufen wird
     * */
    public boolean isMovingLeft() { return movingLeft; }

    /**
     * setMovingLeft            Setzen ob nach links gelaufen wird
     * @param movingLeft        Wert ob nach links gelaufen wird
     * */
    public void setMovingLeft(boolean movingLeft) { this.movingLeft = movingLeft; }

    /**
     * isMovingRight            Rueckgabe ob nach rechts gelaufen wird
     * @return boolean          Wert ob nach rechts gelaufen wird
     * */
    public boolean isMovingRight() { return movingRight; }

    /**
     * setMovingRight           Setzen ob nach rechts gelaufen wird
     * @param movingRight       Wert ob nach rechts gelaufen wird
     * */
    public void setMovingRight(boolean movingRight) { this.movingRight = movingRight; }

    /**
     * isJumping                Rueckgabe ob nach gesprungen wird
     * @return boolean          Wert ob gesprungen wird
     * */
    public boolean isJumping() { return jumping; }

    /**
     * setJumping               Setzen ob nach gesprungen wird
     * @param jumping           Wert ob gesprungen wird
     * */
    public void setJumping(boolean jumping) { this.jumping = jumping; }

    /**
     * isFalling                Rueckgabe ob im Fall
     * @return boolean          Wert ob im Fall
     * */
    public boolean isFalling() { return falling; }

    /**
     * setFalling               Setzen ob im Fall
     * @param falling           Wert ob im Fall
     * */
    public void setFalling(boolean falling) { this.falling = falling; }

    /**
     * getWidthForCollision     Rueckgabe Kollisionsbreite
     * @return double           Kollisionsbreite
     * */
    public int getWidthForCollision() { return widthForCollision; }

    /**
     * setWidthForCollision     Setzen der Kollisionsbreite
     * @param widthForCollision Kollisionsbreite
     * */
    public void setWidthForCollision(int widthForCollision) { this.widthForCollision = widthForCollision; }

    /**
     * getHeightForCollision    Rueckgabe Kollisionshoehe
     * @return double           Kollisionshoehe
     * */
    public int getHeightForCollision() { return heightForCollision; }

    /**
     * setHeightForCollision    Setzen der Kollisionshoehe
     * @param heightForCollision Kollisionshoehe
     * */
    public void setHeightForCollision(int heightForCollision) { this.heightForCollision = heightForCollision; }

}
