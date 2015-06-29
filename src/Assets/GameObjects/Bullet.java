package Assets.GameObjects;

import Assets.World.Tile;
import Assets.World.TileMap;
import GameSaves.GameData.GameData;
import Main.References;
import Main.Sound;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 * Alle Geschosse, welche von Waffen abgefeuert werden koennen
 *
 * @author Sirat
 * */
public class Bullet extends GameObject
{

    // Geschoss
    private BufferedImage texture;
    private double startX;
    private double startY;

    private boolean hit;
    private boolean remove;

    /**
     * Bullet                   Konstrultor der Bullet-Klasse
     *
     * @param width              Breite des Bildes
     * @param height             Hoehe des Bildes
     * @param widthForCollision  Breite des Kollisionsrechtecks
     * @param heightForCollision Hoehe des Kollisionsrechtecks
     * @param velocityX          Geschwindigkeit x-Achse
     * @param velocityY          Geschwindigkeit y-Achse
     * @param maxVelocityX       Maximalgeschwindigkeit x-Achse
     * @param maxVelocityY       Maximalgeschwindigkeit y-Achse
     * @param tileMap            Zugehoerige TileMap
     */
    public Bullet(int width, int height, int widthForCollision, int heightForCollision,
                  double velocityX, double velocityY, double maxVelocityX, double maxVelocityY,
                  TileMap tileMap, boolean isFacingRight, BufferedImage texture)
    {
        super(width, height, widthForCollision, heightForCollision, velocityX, velocityY, maxVelocityX, maxVelocityY, tileMap);

        super.isFacingRight = isFacingRight;
        this.texture = texture;

        if (isFacingRight)
            directionX = velocityX;
        else
            directionX = -velocityX;
    }

    /**
     * setStartX        Initialisieren der Startposition
     * */
    public void setStartX()
    {
        this.startX = super.x;
        this.startY = super.y;
    }

    /**
     * setHit           Setzen, ob Geschoss mit Objekt kollidiert
     * */
    public void setHit()
    {
        if (hit) return;
        this.hit = true;
    }

    /**
     * shouldRemove     Pruefen, ob Geschoss geloescht werden kann
     * */
    public boolean shouldRemove() { return remove; }

    /**
     * update           Aktualisieren der Position
     * */
    @Override
    public void update() {
        super.collisionWithTileMap();
        super.setPosition(xTmp, yTmp);

        if (super.x > 0 && (super.x > startX + Weapon.PURPLE_GUN_RANGE || super.y > startY + Weapon.PURPLE_GUN_RANGE))
            y++;
        else if (super.x < 0 && super.x < startX - Weapon.PURPLE_GUN_RANGE)
            y++;

        if (directionX == 0 && !hit)
            setHit();

        if (hit)
        {
            if (GameData.isSoundOn.equals("On")) Sound.explosionSound.play();
            remove = true;
        }
    }

    /**
     * render       Zeichnen des Objekts
     * */
    @Override
    public void render(Graphics g)
    {
        super.setOnMap();

        if(!super.isFacingRight)
            g.drawImage(texture, (int)(x + xOnMap - width/2 + width), (int)(y + yOnMap - height/2), -width, height, null);
        else
            g.drawImage(texture, (int)(x + xOnMap - width/2), (int)(y + yOnMap - height/2), null);
    }

    // EVENTS
    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

}
