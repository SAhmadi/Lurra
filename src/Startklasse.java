
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.*;
import java.awt.Window.*;
import java.io.*;
import javax.imageio.*;

/**
 *  
 * 
 * @author Amin Oulad; Mo Cobay Hafid 
 * @version  30042015 
 * Damit das Hintergrundbild zu sehen und der Sound zu hören ist, muss im selben Verzeichnis gespeichert werden!
 */
public class Startklasse {
    JFrame frame = new JFrame("Lurra");
    
    JLabel img = new JLabel(new ImageIcon("Terraria.png"));
    JLabel img1 = new JLabel(new ImageIcon("Terraria.png"));
    JLabel img2 = new JLabel(new ImageIcon("Terraria.png"));
    JPanel panelCont = new JPanel();
    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel panel3 = new JPanel();
    JButton startButton = new JButton("Spiel starten");
    JButton settingButton= new JButton("Einstellungen");
    JButton closeButton= new JButton("Beenden");
    JButton avatarButton = new JButton("Avatar");
    JButton soundButton = new JButton("Sound");
    JButton controlButton = new JButton("Steuerung");
    JButton localButton = new JButton("Lokal?");
    JButton onlineButton = new JButton("Online?");
    JButton backButton1 = new JButton("Zurück");
    JButton backButton2 = new JButton("Zurück");
    Sound s = new Sound();
    CardLayout cl = new CardLayout();

    public Startklasse() {
        panelCont.setLayout(cl);
        //Button Farbe
        startButton.setBackground(new Color(67,184,41));
        settingButton.setBackground(new Color(67,184,41));
        closeButton.setBackground(new Color(67,184,41));
        avatarButton.setBackground(new Color(67,184,41));
        soundButton.setBackground(new Color(67,184,41));
        controlButton.setBackground(new Color(67,184,41));
        localButton.setBackground(new Color(67,184,41));
        onlineButton.setBackground(new Color(67,184,41));
        backButton1.setBackground(new Color(67,184,41));
        backButton2.setBackground(new Color(67,184,41));

        //Den Panels werden Buttons und das Bild hinzugefügt
        panel1.add(startButton);
        panel1.add(settingButton);
        panel1.add(closeButton);
        panel1.add(img);

        panel2.add(localButton);
        panel2.add(onlineButton);
        panel2.add(backButton1);
        panel2.add(img1);

        panel3.add(avatarButton);
        panel3.add(soundButton);
        panel3.add(controlButton);
        panel3.add(backButton2);
        panel3.add(img2);

        panel1.setBackground(new Color(22,115,255));
        panel2.setBackground(new Color(22,115,255));
        panel3.setBackground(new Color(22,115,255));

        panelCont.add(panel1, "1");
        panelCont.add(panel2, "2");
        panelCont.add(panel3, "3");
        cl.show(panelCont, "1");

        //Funktionen der Button wenn sie gedrueckt werden
        
        //Spiel starten
        startButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    cl.show(panelCont, "2");
                    s.playDiamondSound();
                }
            });

        //Einstellungen
        settingButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    cl.show(panelCont, "3");
                    s.playDiamondSound();
                }
            });

        //Beenden
        closeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    frame.setVisible( false );
                    System.exit(0);
                }
            });

        //Avatar
        avatarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {

                    s.playDiamondSound();
                }
            });

        //Sound
        soundButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {

                    s.playDiamondSound();
                }
            });

        //Steuerung
        controlButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {

                    s.playDiamondSound();
                }
            });

        //Lokal?
        localButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {

                    s.playDiamondSound();
                }
            });

        //Online?
        onlineButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {

                    s.playDiamondSound();
                }
            });

        //Zurück (nach Spiel starten)
        backButton1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    cl.show(panelCont, "1");
                    s.playDiamondSound();
                }
            });

        //Zurück  (Einstellungen)
        backButton2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    cl.show(panelCont, "1");
                    s.playDiamondSound();
                }
            });

        
        //das Fenster wird erstellt
        frame.add(panelCont);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1000,600));
        frame.setUndecorated(true);
        frame.pack();
        frame.setLocationRelativeTo(null);
        //GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(frame);
        frame.setVisible(true);

    }
    //Die Main
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new Startklasse();
                }
            });
    }

}