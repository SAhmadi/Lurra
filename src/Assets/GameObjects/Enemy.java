package Assets.GameObjects;

import Assets.World.TileMap;
import Main.References;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Erstellen von beliebigen Gegnern
 *
 * @author Sirat
 * @version 0.8
 * */
public class Enemy extends GameObject
{
    // Gegner
    private BufferedImage texture;
    private Player player;

    private int health;
    private int damage;

    private boolean wasHit;

    /**
     * GameObject                   Konstruktor der GameObject-Klasse
     *
     * @param width                 Breite des Bildes
     * @param height                Hoehe des Bildes
     * @param widthForCollision     Breite des Kollisionsrechtecks
     * @param heightForCollision    Hoehe des Kollisionsrechtecks
     * @param velocityX             Geschwindigkeit x-Achse
     * @param velocityY             Geschwindigkeit y-Achse
     * @param maxVelocityX          Maximalgeschwindigkeit x-Achse
     * @param maxVelocityY          Maximalgeschwindigkeit y-Achse
     * @param tileMap               Zugehoerige TileMap
     * @param texture               Texture
     * @param damage                Schaden
     * @param health                Leben
     * @param player                Spieler
     * */
    public Enemy(int width, int height, int widthForCollision, int heightForCollision, double velocityX, double velocityY, double maxVelocityX, double maxVelocityY, TileMap tileMap, BufferedImage texture, Player player, int health, int damage)
    {
        super(width, height, widthForCollision, heightForCollision, velocityX, velocityY, maxVelocityX, maxVelocityY, tileMap);

        this.texture = texture;
        this.player = player;
        this.health = health;
        this.damage = damage;
        this.wasHit = false;

        boolean positionNegative = new Random().nextInt(10) > 4;
        if (positionNegative)
            super.setPosition(
                    new Random().nextInt(References.SCREEN_WIDTH/2 - this.getWidth()) - player.getX(),
                    new Random().nextInt(References.SCREEN_HEIGHT/2 - this.getHeight()) - player.getY()
            );
        else
            super.setPosition(
                    new Random().nextInt(References.SCREEN_WIDTH/2 - this.getWidth()) + player.getX(),
                    new Random().nextInt(References.SCREEN_HEIGHT/2 - this.getHeight()) + player.getY()
            );
    }

    @Override
    public void update()
    {
        // Update Bewegungen
        move();

        // Update Position relativ zu TileMap
        super.setPosition(xTmp, yTmp);
    }

    @Override
    public void render(Graphics2D g)
    {
        // Zeichnen auf der TileMap
        super.setOnMap();
        if (!super.isFacingRight)
            g.drawImage(texture, (int) (x + xOnMap - width / 2 + width), (int) (y + yOnMap - height / 2), -width, height, null);
        else
            g.drawImage(texture, (int) (x + xOnMap - width / 2), (int) (y + yOnMap - height / 2), null);
    }

    /**
     * looseHealth          Leben verlieren
     *
     * @param damage        Schaden
     * */
    public void looseHealth(int damage) { this.health = Math.max(this.health - damage, 0); }

    /**
     * shouldRemove         Rueckgabe ob Objekt zu entfernen ist
     * @return              Wert, ob zu entfernen
     * */
    public boolean shouldRemove() { return health <= 0; }

    /**
     * move     Berechnen der Bewegung
     * */
    private void move()
    {
        xTmp = x;
        yTmp = y;

        xDestination = x + directionX;
        yDestination = y + directionY;

        xTmp += directionX;
        yTmp += directionY;

        // x Koordinate
        if(this.x > player.getX())
        {
            super.isFacingRight = false;
            if (directionX < -velocityX)
                directionX = -maxVelocityX;
            else
                directionX -= velocityX;
        }
        else if (this.x <= player.getX())
        {
            super.isFacingRight = true;
            if (directionX > velocityX)
                directionX = maxVelocityX;
            else
                directionX += velocityX;
        }

        // y Koordinate
        if(this.y <= player.getY())
        {
            if (directionY < -maxVelocityY/2)
                directionY = -maxVelocityY/2;
            else
                directionY -= velocityY/2;
        }
        else if (this.y > player.getY())
        {
            if (directionY > maxVelocityY/2)
                directionY = maxVelocityY/2;
            else
                directionY += velocityY/2;
        }
    }

    // KEYLISTENERS
    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    // GETTER UND SETTER
    /**
     * getTexture       Rueckgabe des Bildes
     * @return          Gegner Bild
     * */
    public BufferedImage getTexture() { return this.texture; }

    /**
     * setTexture       Setzen des Bildes
     * @param texture   Gegner Bild
     * */
    public void setTexture(BufferedImage texture) { this.texture = texture; }

    /**
     * getHealth        Rueckgabe des Lebens
     * @return          Leben
     * */
    public int getHealth() { return this.health; }

    /**
     * setHealth        Setzen des Lebens
     * @param health    Leben
     * */
    public void setHealth(int health) { this.health = health; }

    /**
     * getDamage        Rueckgabe des Schadens
     * @return          Schaden
     * */
    public int getDamage() { return this.damage; }

    /**
     * setDamage        Setzen des Schadens
     * @param damage    Schaden
     * */
    public void setDamage(int damage) { this.damage = damage; }

    /**
     * getWasHit        Rueckgabe, ob Objekt getroffen wurde
     * @return          Wert, ob getroffen wurde
     * */
    public boolean getWasHit() { return this.wasHit; }

    /**
     * setWasHit        Setzen, ob Objekt getroffen wurde
     * @param wasHit    Wert, ob getroffen wurde
     * */
    public void setWasHit(boolean wasHit) { this.wasHit = wasHit; }

}


