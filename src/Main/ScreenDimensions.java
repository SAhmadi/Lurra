package Main;

/*
* ScreenDimensions - Breite und Hoehe des Spielfelds
* */
public class ScreenDimensions {

    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;

    // Fenster wird verkleinert, damit alles fluessiger laeuft
    // Alle Daten muessen mit dem SCALE multipliziert werden
    public static int SCALE;

    // Display Breite und Hoehe -> Spielfenster Dimensionen
    // Werden dementsprechen überschrieben
    public static int WIDTH = 1024;
    public static int HEIGHT = 576;

    // Entwicklungsgroesse fuer Schriftgroessen-Anpassung
    public static final int DEVELOPMENT_WIDTH = 1920;
    public static final int DEVELOPMENT_HEIGHT = 1200;

}
