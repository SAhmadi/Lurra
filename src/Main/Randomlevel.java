package Main;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Amin,Sirat on 15.05.2015.
 */
public class Randomlevel {
    public static int[] numbers= {0, 196, 209};


    public Randomlevel() {


        //numbers.add(0,0 );

    }

    public static int[] generateNumbers() {

        int[] numberInRow = new int[64];

        for (int i = 0; i < 64; i++) {

            numberInRow[i] = numbers[(int) Math.random() * 2];

        }

        return (numberInRow);
    }
}