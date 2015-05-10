package Main;

import java.lang.reflect.Method;
import java.util.function.Function;

/**
 * Created by Vanessa on 07.05.2015.
 */
public abstract class GameObject {

    //Name
    public String name;

    //Name Setzen
    public abstract void setName (String name);

   //Name Auslesen    public abstract String getName ();

    //Bild
    public String image;

    //Bild Setzen
    public abstract void setImage (String image);

    //Bild Auslesen
    public abstract String getImage ();

    //Koordinaten
    public int posX;
    public int posY;

    //Koordinaten Setzen
    public abstract void setCol (int posX, int posY);

    //Koordinaten Auslesen
    public abstract int getCol ();

    //Kollision
    public boolean col;
    public int colRecX1;
    public int colRecX2;
    public int colRecY1;
    public int colRecY2;
    public Object colComp;

    //Kollision Setzen
    public abstract void setColRecX1 (int colRecX1);
    public abstract void setColRecX2 (int colRecX2);
    public abstract void setColRecY1 (int colRecY1);
    public abstract void setColRecY2 (int colRecY2);

    //Kollision Auslesen
    public abstract int getColRecX1 ();
    public abstract int getColRecX2 ();
    public abstract int getColRecY1 ();
    public abstract int getColRecY2 ();

    //Kollision
    public abstract void collision (int colRecX1, int colRecX2, int colRecY1, int colRecY2, Object GameObject);


}







