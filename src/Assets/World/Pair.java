package Assets.World;

/**
 * Paar(Zeile, Spalte) fuer die Tilemap.
 * Ein Tile wird durch ein Paar(Zeile, Spalte) positioniert.
 *
 * @author Sirat
 * */
public class Pair
{

    private int row;
    private int column;

    /**
     * Konstruktor der Klasse Pair
     * Erstellen eines Punktes von Zeilen und Spalten
     * */
    public Pair(int row, int column)
    {
        this.row = row;
        this.column = column;
    }

    /**
     * GETTER UND SETTER
     * */
    public void setRow(int row) { this.row = row; }
    public int getRow(int row) { return this.row; }

    public void setColumn(int column) { this.column = column; }
    public int getColumn() { return this.column; }

}
