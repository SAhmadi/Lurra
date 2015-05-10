package Main;

/**
 * Created by Vanessa on 10.05.2015.
 */
public abstract class Player extends GameObject {

    // Gesundheit
    public int health;
    public void setHealth (int h){return;};
    public int getHealth(){return getHealth();};

    // Waffen
    public Weapon usedWeapon;
    public Weapon weapon[];

    // Agieren
    public void attac (GameObject g){return;};
    public void move (int x1, int x2, int y1, int y2){return;};
    public void interact (GameObject g){return;};


}