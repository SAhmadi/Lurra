import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.*;

/**
 * Nur eine Version für Meilenstein 02
 * Projektaufbau fast komplett aendern. Aufbau mittels GameStates
 * Es muss noch einiges geändert werden, z.B. einfuegen der GameLoop um ueberhaupt spielen zu koennen :D
 */
public class Startklasse {
    JFrame frame = new JFrame("Lurra");
    JLabel[] earthTilesElements;
    EarthTile earthTile;

    JLabel[] grasTilesElements;
    GrasTile grasTile;


    JLabel img = new JLabel(new ImageIcon("res/img/Terraria.png"));
    JLabel img1 = new JLabel(new ImageIcon("res/img/Terraria.png"));
    JLabel img2 = new JLabel(new ImageIcon("res/img/Terraria.png"));
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
                    startGame();
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
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1024,576));
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

    public void startGame() {
        JFrame gameFrame = new JFrame();
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setResizable(false);
        gameFrame.setUndecorated(false);
        gameFrame.pack();
        gameFrame.setSize(new Dimension(1024, 576));
        gameFrame.setVisible(true);
        frame.setVisible(false);
        gameFrame.setLocationRelativeTo(null);

        // Hintergrund
        ImageIcon backgroundImageIcon = new ImageIcon("res/img/sky.jpg");
        Image background = backgroundImageIcon.getImage();
        // Erstelle neues Panel und setzte Himmel-Hintergrund
        JPanel gamePanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                g.drawImage(background, 0, 0, null);
            }
        };
        gameFrame.setContentPane(gamePanel);

        // Aktualisiere/Rekalkuliere das Layout
        gamePanel.revalidate();

        gameFrame.getContentPane().setLayout(null);

        // Positioniere Erde-Tile
        setEarthTiles(gamePanel);
        // Positioniere Gras-Tile
        setGrasTiles(gamePanel);
    }

    private void setEarthTiles(JPanel gamePanel) {}

    private void setGrasTiles(JPanel gamePanel) {
        grasTilesElements = new JLabel[1024/16];
        for(int i = 0; i < 1024/16; i++) {
            if(i == 6) {
                grasTilesElements[i] = new JLabel(new ImageIcon("res/img/spawn_spot.jpg"));
            }
            else {
                grasTilesElements[i] = new JLabel(new ImageIcon("res/img/gras_top.jpg"));
            }
            grasTilesElements[i].setOpaque(true);
            grasTilesElements[i].setSize(new Dimension(16, 16));
            grasTilesElements[i].setLocation(i * 16, 576 - 80);
            gamePanel.add(grasTilesElements[i]);
        }

        grasTile = new GrasTile(grasTilesElements, true);
    }

}