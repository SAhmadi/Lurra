package Main;

/**
 * Einfuehrungstutorial
 *
 * @author Halit
 * */
public class Tutorial
{

    public static final int TUT_RUN_RIGHT = 0;
    public static final int TUT_RUN_LEFT = 1;
    public static final int TUT_JUMP = 2;
    public static final int TUT_DESTROY_BLOCK = 3;
    public static final int TUT_COLLECT_MORE = 4;
    public static final int TUT_CRAFT = 5;

    private static int currentTut = 0;

    /**
     * Anzeigen des aktuellen Tutorialtextes
     *
     * @return Text als String
     */
    public static String getCurrentTutorial()
    {
        switch (currentTut)
        {
            case TUT_RUN_RIGHT :
                return "Laufe nach rechts, indem du Taste-D gedrueckt haelst.";
            case TUT_RUN_LEFT :
                return "Geschafft. Laufe nun nach links, indem du Taste-A gedrueckt haelst.";
            case TUT_JUMP :
                return "Sehr gut. Mit Taste-W kannst du springen.";
            case TUT_DESTROY_BLOCK :
                return "Richtig, nun zum wichtigen Teil: Zerstoere einen Block, indem du ihn mehrmals anklickst.\nJe wertvoller der Block, desto laenger dauert es und desto mehr XP bekommst du.";
            case TUT_COLLECT_MORE :
                return "Der Block ist nun in deinem Inventar. Mit Taste-C oeffnest du dein Inventar. Sammle nun einen Block einer anderen Sorte.";
            case TUT_CRAFT :
                // TODO: Crafter beschreiben
                return "Nun kannst du mit Taste-F den Craftebereich aufrufen. Stelle nun ...";
            default:
                return "";
        }
    }

    /**
     * Bestimmtes Tutorial abschlieﬂen
     *
     * @param tutId    ID des Tutorials (TUT_RUN_RIGHT, ...)
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
