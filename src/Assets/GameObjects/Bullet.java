package Assets.GameObjects;

import Assets.World.TileMap;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 * Created by Sirat on 21.06.2015.
 */
public class Bullet extends GameObject
{

    private BufferedImage texture;
    private Point clickPoint;

    private boolean hit;
    private boolean remove;

    private double startX;
    private double startY;

    /**
     * Konstruktor - Initialisieren
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
                  TileMap tileMap, boolean isFacingRight, BufferedImage texture, Point clickPoint)
    {
        super(width, height, widthForCollision, heightForCollision, velocityX, velocityY, maxVelocityX, maxVelocityY, tileMap);

        super.isFacingRight = isFacingRight;
        this.texture = texture;
        this.clickPoint = clickPoint;

        if (isFacingRight)
            directionX = velocityX;
        else
            directionX = -velocityX;
    }

    /**
     *
     * */
    public void setStartX()
    {
        this.startX = super.x;
        this.startY = super.y;
    }


    /**
     *
     * */
    public void setHit()
    {
        // TODO
        if (hit)
            return;
        this.hit = true;
    }

    /**
     *
     * */
    public boolean shouldRemove() { return remove; }


    @Override
    public void update()
    {
        super.collisionWithTileMap();
        super.setPosition(xTmp, yTmp);

        if (super.x > 0 && (super.x > startX + Weapon.PURPLE_GUN_RANGE || super.y > startY + Weapon.PURPLE_GUN_RANGE))
        {
            y++;
        }
        else if (super.x < 0 && super.x < startX - Weapon.PURPLE_GUN_RANGE)
        {
            y++;
        }

        if (directionX == 0 && !hit)
            setHit();

        if (hit)
            remove = true;
    }

    @Override
    public void render(Graphics g)
    {
        super.setOnMap();

        if(!super.isFacingRight)
            g.drawImage(texture, (int)(x + xOnMap - width/2 + width), (int)(y + yOnMap - height/2), -width, height, null);
        else
            g.drawImage(texture, (int)(x + xOnMap - width/2), (int)(y + yOnMap - height/2), null);

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
