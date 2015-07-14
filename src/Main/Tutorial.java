package Main;

/**
 * Einfuehrungstutorial
 *
 * @author Halit
 * */
public class Tutorial
{

    public static final byte TUT_RUN_RIGHT = 0;
    public static final byte TUT_RUN_LEFT = 1;
    public static final byte TUT_JUMP = 2;
    public static final byte TUT_DESTROY_BLOCK = 3;
    public static final byte TUT_COLLECT_MORE = 4;
    public static final byte TUT_CRAFT = 5;
    public static final byte TUT_SCROLL = 6;
    public static final byte TUT_SHOOT = 7;
    public static final byte TUT_CLOSE_TUT = 8;

    public static int currentTut = 0;

    /**
     * getCurrentTutorial   Anzeigen des aktuellen Tutorialtextes
     *
     * @return              Text als String
     */
    public static String getCurrentTutorial()
    {
        switch (currentTut)
        {
            case TUT_RUN_RIGHT :
                return "Laufe nach rechts! Halte Taste D gedrueckt.";
            case TUT_RUN_LEFT :
                return "Geschafft. Laufe nun nach links! Halte Taste A gedrueckt.";
            case TUT_JUMP :
                return "Sehr gut. Mit Taste W kannst du springen.";
            case TUT_DESTROY_BLOCK :
                return "Nun zerstoere einen Block, indem du ihn mehrmals anklickst.";
            case TUT_COLLECT_MORE :
                return "Sammle nun einen Block einer anderen Sorte.";
            case TUT_CRAFT :
                return "Mit Taste F oeffnest/schliesst du die Craftingkarten.";
            case TUT_SCROLL :
                return "Benutze dein Mausrad. Damit scrollst du durch die Inventarleiste.";
            case TUT_SHOOT :
                return "Wechsel zur Schleimpistole. Feuere nun mit der linken Maustaste.";
            case TUT_CLOSE_TUT :
                return "Druecke Taste T um das Tutorial zu schliessen.";
            default:
                return "";
        }
    }

    /**
     * Bestimmtes Tutorial abschlieﬂen
     *
     * @param tutId    ID des Tutorials
     */
    public static void solveTut(int tutId)
    {
        if(currentTut == tutId) currentTut++;
    }

    /**
     * Tutorial neustarten
     */
    public static void restart() { currentTut = 0; }

}
