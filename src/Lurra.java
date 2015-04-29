import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;

/**
 *  
 * 
 * @author Amin Oulad; Mo Cobay Hafid 
 * @version  27042015 
 * Damit das Hintergrundbild angezeigt werden kann muss es im selben Verzeichniss gespeichert werden!! 
 */
public class Lurra implements ActionListener {
    JButton  but, but1, but2, but3 ,but4,but5, but6, but7;
    JPanel jp = new JPanel();
    JLabel jl = new JLabel(); 
    JLabel lab,lab1,lab2;
    JFrame frame, frame1, frame2 ;
   

    public void showWindow() {
        // Erstellt ein Fenster  
        frame = new JFrame();
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setTitle("Lurra©");

        frame.setLayout( null );  

        jp = new JPanel();

        try{

            frame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("../res/img/Terraria.png")))));

        }catch(IOException e)
        {
            System.out.println("Kein Bild vorhanden! Bitte das Bild in das Verzeichnis ziehen(LurraBlueJ)");   

        }

       

        // Erstellt einen Button um das Spiel zu starten                                                                                                                                             
        but = new JButton("Spiel starten");
        but.setBounds(50,150,500,50 );
        but.setBackground(new Color(0,153,0));
        // but.setForeground(new Color(0,255,0));
        frame.add( but );
        but.addActionListener(this);

        but1 = new JButton("Einstellungen");
        but1.setBounds(50,250,500,50 );
        but1.setBackground(new Color(0,153,0));
        frame.add( but1 );
        but1.addActionListener(this);

        but2 = new JButton("Beenden");
        but2.setBounds(50,350,500,50 );
        but2.setBackground(new Color(0,153,0));
        frame.add( but2 );
        but2.addActionListener(this);

        //Definiert die Größe des Fensters und sorgt dafür dass das Fenster sichtbar wird
        frame.setPreferredSize(new Dimension(600,600));
        frame.pack();
        frame.setVisible( true );

    }

    public void actionPerformed( ActionEvent e ) {
        Object source = e.getSource();

        // Funktionen beim drücken eines Buttons
        if (source == but) {
            frame1 = new JFrame();
            frame1.setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE ); // X-windowsButton
            frame1.setTitle("");
            frame1.setLayout( null );

            try{

                frame1.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("../res/img/Terraria.png")))));

            }catch(IOException f)
            {
                System.out.println("Kein Bild vorhanden! Bitte das Bild in das Verzeichnis ziehen(LurraBlueJ)");   

            }
            // Das Label zeigt eine Nachricht 
            lab1 = new JLabel("Zurück →↑" );        

            lab1.setBounds(495,-3,500,50);
            lab1.setForeground(Color.WHITE);
            lab1.setFont(new Font("Arial", Font.BOLD,15));
            frame1.add( lab1 );

            but6 = new JButton("Lokal?");
            but6.setBounds(50,150,500,50 );
            but6.setBackground(new Color(0,153,0));
            frame1.add( but6 );
            but6.addActionListener(this);

            but7 = new JButton("Online?");
            but7.setBounds(50,250,500,50 );
            but7.setBackground(new Color(0,153,0));
            frame1.add( but7 );
            but7.addActionListener(this);

            frame1.setPreferredSize(new Dimension(600,600));
            frame1.pack();
            frame1.setVisible( true );
        }

        if (source == but1 ) {
            frame2 = new JFrame();
            frame2.setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
            frame2.setTitle("Einstellungen");
            frame2.setLayout( null );  

            frame2.setPreferredSize(new Dimension(600,600));
            frame2.pack();
            frame2.setVisible( true );

            try{

                frame2.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("../res/img/Terraria.png"))))); //Hintergrundbild wird eingefügt bild ist im Verzeichniss!!!!!!!

            }catch(IOException g)
            {
                System.out.println("Kein Bild vorhanden! Bitte das Bild in das Verzeichnis ziehen(LurraBlueJ)");   

            }

            lab2 = new JLabel("Speichern und zurück →↑" );
            lab2.setBounds(388,-3,500,50);
            lab2.setForeground(Color.WHITE);
            lab2.setFont(new Font("Arial", Font.BOLD,15));
            frame2.add( lab2 );

            but3 = new JButton("Avatar");
            but3.setBounds(50,150,500,50 );
            but3.setBackground(new Color(0,153,0));
            frame2.add( but3 );
            but3.addActionListener(this);

            but4 = new JButton("Steuerung");
            but4.setBounds(50,250,500,50 );
            but4.setBackground(new Color(0,153,0));
            frame2.add( but4 );
            but4.addActionListener(this);

            but5 = new JButton("Sound");
            but5.setBounds(50,350,500,50 );
            but5.setBackground(new Color(0,153,0));
            frame2.add( but5 );
            but5.addActionListener(this);

        
        }

        if (source == but2) {                               // "BEENDEN-BUTTON"
            frame.setVisible( false );
            System.exit(0);
        }

        if (source == but3) {                               //Buttons ohne Funktion (bis jetzt)

        }

        if (source == but4) { 

        }

        if (source == but5) { 

        }

        if (source == but6) { 

        }
        frame.pack();
    }

    public static void main(String[] args) {
        (new Lurra()).showWindow();
    }

}
