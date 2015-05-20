package Assets;

import java.util.Random;

/*
* RandomLevel - Zufällige Auswahl der Tiles
* */
public class RandomLevel {
    // Alle Tile-ID
    public static int[] numbers= {0, 196, 209, 157, 158, 105, 3, 4, 131, 118, 144, 160};

    // Aufteilen der Tiles-Typen
    public static int[] earth = {131, 118, 144};
    public static int[] world = {0, 196, 209, 157, 158, 131, 118, 144, 160};

    /*
    * generateEarth   - Zufälliges Wählen von Erdtiles
    * */
    public static int[] generateEarth () {
        Random rand = new Random();
        int randomNum;

        int[] earthInRow = new int[2304 * 4];

        for (int i = 0; i < 256*4; i++)
            earthInRow[i] = world[0];

        for (int j = 256*4; j < 1664*4; j++) {
            randomNum = rand.nextInt(9);
            earthInRow[j] = world[randomNum];
        }
        for (int k = 1664*4; k < 1728*4; k++) {
            //randomNum = rand.nextInt(9);
            earthInRow [k] =  numbers[5];
        }


        for (int l = 1728*4; l < 2304*4; l++) {
            randomNum = rand.nextInt(3);
            earthInRow[l] = earth[randomNum];
        }

        return (earthInRow);
    }

}


