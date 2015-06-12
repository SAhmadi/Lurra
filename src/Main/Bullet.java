package Main;

import Assets.World.Tile;
import State.Level.Level1State;

/**
 * Created by Vanessa on 12.06.2015.
 */
public class Bullet {
    //Startpunkte
    private double startpointX;
    private double startpointY;
    //Flugbahn
    private double x;
    private double y;
    //Winkel
    private double a=45;
    //Anfangsgeschwindigkeit
    private double v0=150;
    //Erdbeschleunigung
    private static final double g = 9.81;
    //Zeit
    public double t = 0;


    public Bullet() {

        //startpoint = Level1State.tileMap.numberOfColumns* Tile.WIDTH / 2;
        startpointX = ScreenDimensions.WIDTH / 2 - Level1State.player.getX();
        startpointY = ScreenDimensions.HEIGHT / 2 - Level1State.player.getY();
    }

    public double getX(double t) {

        return (v0 * Math.cos(a) * t)+startpointX;                                              //v(0)*cos(a)*t

    }
    public double getY(double t) {
        return (v0 * Math.sin(a) * t - (g / 2) * t * t)+ startpointY;                               //-1/2gt^2+v0*sin(a)*t
    }

    public void DrawBullet(){
        while (t<100) {
            GamePanel.graphics.fillOval((int)getX(t), (int)getY(t),20,20);
            t=t+0.5;
        }
    }

}
