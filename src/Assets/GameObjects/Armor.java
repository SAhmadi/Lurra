package Assets.GameObjects;

import Main.ScreenDimensions;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Armor {

    private double x;
    private double y;
    private boolean facingRight = true;

    private Image skin;
    private int renderX = 0;
    private int renderY = 0;

    private double resistance;
    private double left = 100;

    private boolean destroyed = false;

    private Player player;


    public Armor(Player player, double resistance) {
        this.player = player;
        update();
        //facingRight =
        this.resistance = resistance;

        try {
            skin = ImageIO.read(Armor.class.getResourceAsStream("/img/armor.png"));
        } catch (IOException e) {
            System.out.println("[FEHLER] Armorbild konnte nicht geladen werden");
            e.printStackTrace();
        }
    }

    public void update() {
        x = player.directionX - 10 + ScreenDimensions.WIDTH/2;
        y = player.directionY - 30 + ScreenDimensions.HEIGHT/2;
    }

    public double getAttacked(double damage) {
        left -= damage;
        if(left <= 0) {
            destroyed = true;
            return -left;
        }
        else return 0;
    }

    public void render(Graphics g) {
        if(facingRight)
            g.drawImage(skin, (int)x, (int)y , null);
        else //spiegeln
            g.drawImage(skin, (int)x, (int)y, null);
    }
}
