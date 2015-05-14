package Assets.GameObjects;

import Assets.GameObject;
import Main.ScreenDimensions;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

/**
 * Amin und Mo
 */
public class Player extends GameObject implements KeyListener {

    public Player(BufferedImage image, int width, int height, int x, int y, int vX, int vY) {
        super(image, width, height, x, y, vX, vY);
    }

    @Override
    public void update() {
        if(x <= 0)
            x = 0;
        if(x == ScreenDimensions.WIDTH)
            x = 0;
        if(x == 2*ScreenDimensions.WIDTH)
            x = ScreenDimensions.WIDTH;
        if(x >= 3*ScreenDimensions.WIDTH-43)
            x = 3*ScreenDimensions.WIDTH-43;
        if(y >= Math.abs(ScreenDimensions.HEIGHT))
            y = 0;
    }

    @Override
    public void render(Graphics g) {
        if(x <= ScreenDimensions.WIDTH) {
            g.drawImage(getImage(), x, y, null);
        }
        else if(x > ScreenDimensions.WIDTH && x <= 2*ScreenDimensions.WIDTH) {
            g.drawImage(getImage(), x-(ScreenDimensions.WIDTH+1), y, null);
        }
        else {
            g.drawImage(getImage(), x-(2*ScreenDimensions.WIDTH+1), y, null);
        }
    }

    @Override
    public BufferedImage getImage() {
        return super.image;
    }

    @Override
    public int getX() {
        return super.x;
    }

    @Override
    public int getY() {
        return super.y;
    }

    @Override
    public int getWidth() {
        return super.width;
    }

    @Override
    public int getHeight() {
        return super.height;
    }

    @Override
    public int getVelocityX() {
        return super.velocityX;
    }

    @Override
    public int getVelocityY() {
        return super.velocityY;
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void move() {

    }

    public void collosion() {
        velocityX = 0;
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_D) {
            velocityX = 5;
            x += velocityX;
        }
        if(e.getKeyCode() == KeyEvent.VK_A) {
            velocityX = -5;
            x += velocityX;
        }
        if(e.getKeyCode() == KeyEvent.VK_W) {
            velocityY = -2;
            y += velocityY;
        }
        if(e.getKeyCode() == KeyEvent.VK_S) {
            velocityY = 2;
            y += velocityY;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
