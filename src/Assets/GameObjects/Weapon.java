package Assets.GameObjects;

import Main.ScreenDimensions;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class Weapon{
    public final static int WEAPON_TYPE_1 = 0;
    public final static int WEAPON_TYPE_2 = 1;
    public final static int WEAPON_TYPE_3 = 2;
    public final static int WEAPON_TYPE_4 = 3;
    public final static int WEAPON_TYPE_5 = 4;

    private double x;
    private double y;
    private boolean facingRight = true;

    private BufferedImage skin;
    private int renderX = 0;
    private int renderY = 0;

    private double damage;
    private String name;

    private Player player;


    public Weapon(Player player, double damage, int type) {
        this.player = player;
        update();
        //facingRight =
        this.damage = damage;

        try {
            switch (type) {
                case WEAPON_TYPE_1:
                    //Bild 1 setzen
                    name = "Typ 1";
                    skin = ImageIO.read(Weapon.class.getResourceAsStream("/img/Weapons/weapon_ax.png"));
                    break;
                case WEAPON_TYPE_2:
                    //Bild 2 setzen
                    name = "Typ 2";
                    skin = ImageIO.read(Weapon.class.getResourceAsStream("/img/Weapons/weapon_sword.png"));
                    break;
                case WEAPON_TYPE_3:
                    //Bild 3 setzen
                    name = "Typ 3";
                    skin = ImageIO.read(Weapon.class.getResourceAsStream("/img/Weapons/weapon_pickaxe.png"));
                    break;
                case WEAPON_TYPE_4:
                    //Bild 4 setzen
                    name = "Typ 4";
                    skin = ImageIO.read(Weapon.class.getResourceAsStream("/img/Weapons/weapon_rpg.png"));
                    break;
                case WEAPON_TYPE_5:
                    //Bild 5 setzen
                    name = "Typ 5";
                    skin = ImageIO.read(Weapon.class.getResourceAsStream("/img/Weapons/weapon_gold_pickaxe.png"));
                    break;
                default:
                    break;
            }
        } catch (IOException e) {
            System.out.println("[FEHLER] Waffenbild konnte nicht geladen werden");
            e.printStackTrace();
        }
    }

    public void update() {
        x = player.directionX - 8 + ScreenDimensions.WIDTH/2;
        y = player.directionY - 8 + ScreenDimensions.HEIGHT/2;
    }

    public double shoot(boolean activate) {
        if(activate) {
            renderX = 30;
        } else {
            renderX = 0;
        }
        return damage;
    }

    public double getDamage() {
        return damage;
    }



    public void render(Graphics g) {
        if(facingRight)
            g.drawImage(skin.getSubimage(renderX, renderY, 30, 30), (int)x, (int)y , null);
        else //spiegeln
            g.drawImage(skin.getSubimage(renderX, renderY, 30, 30), (int)x, (int)y, null);
    }
}
