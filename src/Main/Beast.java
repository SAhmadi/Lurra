package Main;

/**
 * Created by Vanessa on 10.05.2015.
 */
public abstract class Beast extends GameObject {
    //Gesundheit
    public int health;
    public void setHealth (int h){return;};

    public int getHealth() {
        return getHealth();
    }

    // Stärke
    public int power;

    // Agieren
    public void attac (GameObject g){return;};
    public void move (int x1, int x2, int y1, int y2){return;};

}