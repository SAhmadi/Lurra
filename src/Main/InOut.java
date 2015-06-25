package Main;

/**
 * Created by moham_000 on 25.06.2015.
 */
import java.io.*;

public class InOut{
    public static String input;

    public static void writeinfile(int a)throws IOException {
        try {
            BufferedWriter write = new BufferedWriter(new FileWriter("res/Settings",true));
            String output = Integer.toString(a);
            write.write(output);
            write.newLine();
            write.close();
        }
        catch (IOException e){System.err.println("IOException: "+e.getMessage());}
    }
    public static int  readoutoffile(int a)throws IOException{
        BufferedReader read = new BufferedReader(new FileReader("res/Settings"));
        for (int i=0;i<a;i++) {
            input = read.readLine();
        }
        read.close();
        int b = Integer.parseInt(input);
        return b;
    }


}