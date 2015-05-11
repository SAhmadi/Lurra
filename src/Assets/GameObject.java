package Assets;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

/*
* GameObject - Abstrakte Klasse, alle SPielobjekte
* */
public abstract class GameObject {

    // Bild
    protected BufferedImage image;

    // Groesse
    protected int width;
    protected int height;

    // Position
    protected int x;
    protected int y;

    // Geschwindigkeit
    protected int velocityX;
    protected int velocityY;

    public GameObject(BufferedImage image, int width, int height, int x, int y, int vX, int vY) {
        this.image = image;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.velocityX = vX;
        this.velocityY = vY;
    }

    // Bewegen
    public abstract void move();

    // Kollisions - Rechteck
    public abstract Rectangle getCollisionRectangle();

    // KeyListeners
    public abstract void keyPressed(KeyEvent e);
    public abstract void keyReleased(KeyEvent e);

    // Update und Render
    public abstract void update();
    public abstract void render(Graphics g);

    /*
    * Getter and Setter
    * */
    public abstract BufferedImage getImage();
    public abstract int getX();
    public abstract int getY();
    public abstract int getWidth();
    public abstract int getHeight();
    public abstract int getVelocityX();
    public abstract int getVelocityY();
}
