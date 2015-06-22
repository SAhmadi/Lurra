package NurFuerSirat;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.RescaleOp;
import java.io.IOException;

public class TestFuerSirat extends JFrame {
    private static BufferedImage img;
    private static boolean isNight = false;

    public static void main(String[] args)   {
        JFrame f = new TestFuerSirat();


        img = new BufferedImage(400,600,BufferedImage.TYPE_3BYTE_BGR);
        try {
            img = ImageIO.read(TestFuerSirat.class.getResourceAsStream("/img/desert_cartoon.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        f.setSize(img.getWidth(null),img.getHeight(null));
        f.setLocation(200, 200);
        //f.setUndecorated(true);
        f.setVisible(true);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        //Endlosschleife
        while (true) {
            float i = 0;
            //Diese Schleife sorgt für den Wechsel von Tag zu Nacht
            while (i < 1.0) {

                BufferedImageOp op = new RescaleOp(1.0f + i, 64f, null);
                g2.drawImage(img, op, 0, 0);
                i -= 0.01;
                System.out.println(i);

                if (i <= -1.5)
                    break;
                isNight = true;
            }
            //Die hier lässt die Nacht wieder zum Tag werden
            while (isNight == true) {
                BufferedImageOp op = new RescaleOp(1.0f + i, 64f, null);
                g2.drawImage(img, op, 0, 0);
                i += 0.01;
                System.out.println(i);
                if (i >= 0.009)
                    break;
                System.out.println("BOOM");
            }
        }
    }


            }


