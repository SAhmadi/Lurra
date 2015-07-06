package Main;

/**
 * Created by Rua on 6.7.2015.
 */

// Tutorial ID
public class Tutorial {
    public static final int TUT_RUN_RIGHT = 0;
    public static final int TUT_RUN_LEFT = 1;
    public static final int TUT_JUMP = 2;
    public static final int TUT_DESTROY_BLOCK = 3;
    public static final int TUT_COLLECT_MORE = 4;
    public static final int TUT_CRAFT = 5;

    private static int currentTut = 0;

    /**
     * Aktuellen Tutorialtext anzeigen
     * @return Text als String
     */
    public static String getCurrentTutorial() {
        switch (currentTut) {
            case TUT_RUN_RIGHT :
                return "Laufe nach rechts, indem du D gedrueckt haelst.";
            case TUT_RUN_LEFT :
                return "Geschafft. Laufe nun nach links, indem du A gedrueckt haelst.";
            case TUT_JUMP :
                return "Sehr gut. Mit W kannst du springen.";
            case TUT_DESTROY_BLOCK :
                return "Richtig, nun zum wichtigen Teil: Zerstoere einen Block, indem du ihn mehrmals anklickst.\nJe wertvoller der Block, desto laenger dauert es und desto mehr XP bekommst du.";
            case TUT_COLLECT_MORE :
                return "Der Block ist nun in deinem Inventar. Sammle nun einen Block anderer Sorte.";
            case TUT_CRAFT :
                // TODO: Crafter beschreiben
                return "Nun kannst du mit F den Crafter aufrufen. Stelle nun ...";
            default:
                return "";
        }
    }

    /**
     * Bestimmtes Tutorial abschlieﬂen
     * @param tut_id ID des Tutorials (TUT_RUN_RIGHT, ...)
     */
    public static void solveTut(int tut_id) {
        if(currentTut == tut_id)
            currentTut++;
    }


    /**
     * Tutorial neustarten
     */
    public static void restart() {
        currentTut = 0;
    }

}
