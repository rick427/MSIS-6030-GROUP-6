package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class Jukebox implements MouseListener {
    Clip clip;
    JPanel mainListItem1, mainListItem2, mainListItem3;
    String primary_dark = "#141414";
    Font font_bold, font_medium, font_light, font_regular;

    Jukebox(){
        //@: Initial Setup
        JFrame frame = new JFrame();
        frame.setSize(1000, 650);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setLayout(new BorderLayout());
        frame.setTitle("Home - Serenade");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        ImageIcon frameIcon = new ImageIcon("assets/logo-2.png");
        frame.setIconImage(frameIcon.getImage());
        frame.getContentPane().setBackground(new Color(255, 255,255, 255));

        //@: Setup Custom Font
        //@: Reference - https://stackoverflow.com/questions/5652344/how-can-i-use-a-custom-font-in-java
        try {
            font_bold = Font.createFont(Font.TRUETYPE_FONT, new File("assets/Rubik-Bold.ttf"));
            font_regular = Font.createFont(Font.TRUETYPE_FONT, new File("assets/Rubik-Regular.ttf"));
            font_medium = Font.createFont(Font.TRUETYPE_FONT, new File("assets/Rubik-Medium.ttf"));
            font_light = Font.createFont(Font.TRUETYPE_FONT, new File("assets/Rubik-Light.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font_bold);
            ge.registerFont(font_regular);
            ge.registerFont(font_medium);
            ge.registerFont(font_light);
        } catch (IOException | FontFormatException e) {
            System.out.println(e.getMessage());
        }

        //@: Layout Setup
        JPanel sideBar = new JPanel(null);
        sideBar.setBackground(Color.decode("#f5f5f5"));
        sideBar.setPreferredSize(new Dimension(200, 200));

        JPanel mainArea = new JPanel(new BorderLayout());

        JButton homeButton = new JButton("Home");
        homeButton.setBounds(32, 25, 135, 35);
        homeButton.setFocusable(false);
        homeButton.setOpaque(true);
        homeButton.setBorderPainted(false);
        homeButton.setForeground(Color.white);
        homeButton.setBackground(Color.decode(primary_dark));
        homeButton.setFont(font_medium.deriveFont(13f));
        homeButton.setPreferredSize(new Dimension(200, 50));
        homeButton.addActionListener(e -> System.out.println("Home Clicked"));

        JButton checkLibButton = new JButton("My Library");
        checkLibButton.setBounds(32, 75, 135, 35);
        checkLibButton.setFocusable(false);
        checkLibButton.setOpaque(true);
        checkLibButton.setBorderPainted(false);
        checkLibButton.setForeground(Color.decode(primary_dark));
        checkLibButton.setBackground(Color.white);
        checkLibButton.setFont(font_regular.deriveFont(13f));
        checkLibButton.setPreferredSize(new Dimension(200, 50));
        checkLibButton.addActionListener(e -> System.out.println("Check Library Clicked"));

        JButton playlistButton = new JButton("My Playlist");
        playlistButton.setBounds(32, 125, 135, 35);
        playlistButton.setFocusable(false);
        playlistButton.setOpaque(true);
        playlistButton.setBorderPainted(false);
        playlistButton.setForeground(Color.decode(primary_dark));
        playlistButton.setBackground(Color.white);
        playlistButton.setFont(font_regular.deriveFont(13f));
        playlistButton.setPreferredSize(new Dimension(200, 50));
        playlistButton.addActionListener(e -> System.out.println("Playlist Clicked"));

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(32, 560, 135, 35);
        logoutButton.setFocusable(false);
        logoutButton.setOpaque(true);
        logoutButton.setBorderPainted(false);
        logoutButton.setForeground(Color.decode("#de2f40"));
        logoutButton.setBackground(Color.white);
        logoutButton.setFont(font_medium.deriveFont(13f));
        logoutButton.setPreferredSize(new Dimension(200, 50));
        logoutButton.addActionListener(e -> {
            frame.dispose();
            new Login();
        });

        sideBar.add(homeButton);
        sideBar.add(checkLibButton);
        sideBar.add(playlistButton);
        sideBar.add(logoutButton);

        //@: Top Main Area Section
        JPanel topMainArea = new JPanel(new BorderLayout());
        JPanel topUserArea = new JPanel();

        JLabel userName = new JLabel("Richard404");
        ImageIcon icon = new ImageIcon("assets/avatar-2.png");
        ImageIcon avatarIcon = new ImageIcon(icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        userName.setFont(font_regular.deriveFont(13f));
        userName.setIconTextGap(10);
        userName.setIcon(avatarIcon);

        JLabel logoName = new JLabel("Serenade");
        logoName.setFont(font_bold.deriveFont(22f));
        logoName.setForeground(Color.decode(primary_dark));

        topMainArea.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        topMainArea.setBackground(Color.white);
        topMainArea.add(topUserArea, BorderLayout.EAST);
        topMainArea.add(logoName, BorderLayout.WEST);

        topUserArea.add(userName);
        topUserArea.setBackground(Color.white);

        //@: Main Content Area Section
        JPanel mainContentArea = new JPanel(new BorderLayout(0, 20));

        JPanel mainHeader = new JPanel(new BorderLayout(0, 3));
        JLabel mainHeaderTitle = new JLabel("Trending Today.");
        JLabel mainHeaderSubtitle = new JLabel("Get better recommendations the more you listen.");

        JPanel mainListArea = new JPanel(new GridLayout(0, 4, 10, 10));
        mainListItem1 = new JPanel(new BorderLayout());

        JLabel label1 = new JLabel("<html><p style='text-align:center;'>Number One ( Bleach ) - Shiro Sagisu (Topic).</p></html>");
        ImageIcon labelIcon = new ImageIcon("assets/music.png");
        ImageIcon labelIconImage = new ImageIcon(labelIcon.getImage().getScaledInstance(80, 50, Image.SCALE_SMOOTH));
        label1.setIcon(labelIconImage);
        label1.setFont(font_light.deriveFont(12f));
        label1.setHorizontalAlignment(JLabel.CENTER);
        label1.setVerticalAlignment(JLabel.CENTER);
        label1.setHorizontalTextPosition(JLabel.CENTER);
        label1.setVerticalTextPosition(JLabel.BOTTOM);

        mainListItem2 = new JPanel(new BorderLayout());

        JLabel label2 = new JLabel("<html><p style='text-align:center;'>I Got Love - Don Diablo, Nate Dogg.</p></html>");
        label2.setIcon(labelIconImage);
        label2.setFont(font_light.deriveFont(12f));
        label2.setHorizontalAlignment(JLabel.CENTER);
        label2.setVerticalAlignment(JLabel.CENTER);
        label2.setHorizontalTextPosition(JLabel.CENTER);
        label2.setVerticalTextPosition(JLabel.BOTTOM);

        mainListItem3 = new JPanel(new BorderLayout());

        JLabel label3 = new JLabel("<html><p style='text-align:center;'>To The Wire (Album X) - Julian Jordan.</p></html>");
        label3.setIcon(labelIconImage);
        label3.setFont(font_light.deriveFont(12f));
        label3.setHorizontalAlignment(JLabel.CENTER);
        label3.setVerticalAlignment(JLabel.CENTER);
        label3.setHorizontalTextPosition(JLabel.CENTER);
        label3.setVerticalTextPosition(JLabel.BOTTOM);

        JPanel mainListItem4 = new JPanel(new BorderLayout());

        JLabel label4 = new JLabel("<html><p style='text-align:center;'>Things I Said (To The Wire) - Nu Aspect.</p></html>");
        label4.setIcon(labelIconImage);
        label4.setFont(font_light.deriveFont(12f));
        label4.setHorizontalAlignment(JLabel.CENTER);
        label4.setVerticalAlignment(JLabel.CENTER);
        label4.setHorizontalTextPosition(JLabel.CENTER);
        label4.setVerticalTextPosition(JLabel.BOTTOM);

        JPanel mainListItem5 = new JPanel(new BorderLayout());

        JLabel label5 = new JLabel("<html><p style='text-align:center;'>Clearing The Mind - Bear McCreary, Sparks & Shadow.</p></html>");
        label5.setIcon(labelIconImage);
        label5.setFont(font_light.deriveFont(12f));
        label5.setHorizontalAlignment(JLabel.CENTER);
        label5.setVerticalAlignment(JLabel.CENTER);
        label5.setHorizontalTextPosition(JLabel.CENTER);
        label5.setVerticalTextPosition(JLabel.BOTTOM);

        JPanel mainListItem6 = new JPanel(new BorderLayout());

        JLabel label6 = new JLabel("<html><p style='text-align:center;'>Face The Past, Face The Future - Bear McCreary, Sparks & Shadow.</p></html>");
        label6.setIcon(labelIconImage);
        label6.setFont(font_light.deriveFont(12f));
        label6.setHorizontalAlignment(JLabel.CENTER);
        label6.setVerticalAlignment(JLabel.CENTER);
        label6.setHorizontalTextPosition(JLabel.CENTER);
        label6.setVerticalTextPosition(JLabel.BOTTOM);

        JPanel mainListItem7 = new JPanel(new BorderLayout());

        JLabel label7 = new JLabel("<html><p style='text-align:center;'>Master Thyself - Bear McCreary, Sparks & Shadow.</p></html>");
        label7.setIcon(labelIconImage);
        label7.setFont(font_light.deriveFont(12f));
        label7.setHorizontalAlignment(JLabel.CENTER);
        label7.setVerticalAlignment(JLabel.CENTER);
        label7.setHorizontalTextPosition(JLabel.CENTER);
        label7.setVerticalTextPosition(JLabel.BOTTOM);

        JPanel mainListItem8 = new JPanel(new BorderLayout());

        JLabel label8 = new JLabel("<html><p style='text-align:center;'>Forgive Our Trespasses - Nandipha808, Ceeka RSA, DEMOLA.</p></html>");
        label8.setIcon(labelIconImage);
        label8.setFont(font_light.deriveFont(12f));
        label8.setHorizontalAlignment(JLabel.CENTER);
        label8.setVerticalAlignment(JLabel.CENTER);
        label8.setHorizontalTextPosition(JLabel.CENTER);
        label8.setVerticalTextPosition(JLabel.BOTTOM);

        JPanel mainListItem9 = new JPanel(new BorderLayout());

        JLabel label9 = new JLabel("<html><p style='text-align:center;'>Ohema - Victony, Crayon, Bella Shmurda.</p></html>");
        label9.setIcon(labelIconImage);
        label9.setFont(font_light.deriveFont(12f));
        label9.setHorizontalAlignment(JLabel.CENTER);
        label9.setVerticalAlignment(JLabel.CENTER);
        label9.setHorizontalTextPosition(JLabel.CENTER);
        label9.setVerticalTextPosition(JLabel.BOTTOM);

        JPanel mainListItem10 = new JPanel(new BorderLayout());

        JLabel label10 = new JLabel("<html><p style='text-align:center;'>Ayo Girl (Fayahh Beat) - Robinson, Jason Derulo, Rema.</p></html>");
        label10.setIcon(labelIconImage);
        label10.setFont(font_light.deriveFont(12f));
        label10.setHorizontalAlignment(JLabel.CENTER);
        label10.setVerticalAlignment(JLabel.CENTER);
        label10.setHorizontalTextPosition(JLabel.CENTER);
        label10.setVerticalTextPosition(JLabel.BOTTOM);

        JPanel mainListItem11 = new JPanel(new BorderLayout());

        JLabel label11 = new JLabel("<html><p style='text-align:center;'>Unbreakable (with Sam Grey) - TELYKAST, Sam Gray.</p></html>");
        label11.setIcon(labelIconImage);
        label11.setFont(font_light.deriveFont(12f));
        label11.setHorizontalAlignment(JLabel.CENTER);
        label11.setVerticalAlignment(JLabel.CENTER);
        label11.setHorizontalTextPosition(JLabel.CENTER);
        label11.setVerticalTextPosition(JLabel.BOTTOM);

        JPanel mainListItem12 = new JPanel(new BorderLayout());

        JLabel label12 = new JLabel("<html><p style='text-align:center;'>Happiness (feat. Asake & Gunna) - Sarz, Asake, Gunna.</p></html>");
        label12.setIcon(labelIconImage);
        label12.setFont(font_light.deriveFont(12f));
        label12.setHorizontalAlignment(JLabel.CENTER);
        label12.setVerticalAlignment(JLabel.CENTER);
        label12.setHorizontalTextPosition(JLabel.CENTER);
        label12.setVerticalTextPosition(JLabel.BOTTOM);

        JPanel mainListItem13 = new JPanel(new BorderLayout());

        JLabel label13 = new JLabel("<html><p style='text-align:center;'>Father Stretch My Hands Pt. 1 - Kanye West.</p></html>");
        label13.setIcon(labelIconImage);
        label13.setFont(font_light.deriveFont(12f));
        label13.setHorizontalAlignment(JLabel.CENTER);
        label13.setVerticalAlignment(JLabel.CENTER);
        label13.setHorizontalTextPosition(JLabel.CENTER);
        label13.setVerticalTextPosition(JLabel.BOTTOM);

        mainListArea.setBackground(Color.white);
        mainListArea.add(mainListItem1);
        mainListArea.add(mainListItem2);
        mainListArea.add(mainListItem3);
        mainListArea.add(mainListItem4);
        mainListArea.add(mainListItem5);
        mainListArea.add(mainListItem6);
        mainListArea.add(mainListItem7);
        mainListArea.add(mainListItem8);
        mainListArea.add(mainListItem9);
        mainListArea.add(mainListItem10);
        mainListArea.add(mainListItem11);
        mainListArea.add(mainListItem12);
        mainListArea.add(mainListItem13);

        mainListItem1.add(label1, BorderLayout.CENTER);
        mainListItem1.setBackground(Color.decode("#fafafa"));
        mainListItem1.addMouseListener(this);
        mainListItem1.setPreferredSize(new Dimension(mainListItem1.getWidth(), 150));
        mainListItem1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mainListItem2.add(label2, BorderLayout.CENTER);
        mainListItem2.setBackground(Color.decode("#fafafa"));
        mainListItem2.addMouseListener(this);
        mainListItem2.setPreferredSize(new Dimension(mainListItem2.getWidth(), 150));
        mainListItem2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mainListItem3.add(label3, BorderLayout.CENTER);
        mainListItem3.setBackground(Color.decode("#fafafa"));
        mainListItem3.addMouseListener(this);
        mainListItem3.setPreferredSize(new Dimension(mainListItem3.getWidth(), 150));
        mainListItem3.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mainListItem4.add(label4, BorderLayout.CENTER);
        mainListItem4.setBackground(Color.decode("#fafafa"));
        mainListItem4.setPreferredSize(new Dimension(mainListItem4.getWidth(), 150));
        mainListItem4.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mainListItem5.add(label5, BorderLayout.CENTER);
        mainListItem5.setBackground(Color.decode("#fafafa"));
        mainListItem5.setPreferredSize(new Dimension(mainListItem5.getWidth(), 150));
        mainListItem5.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mainListItem6.add(label6, BorderLayout.CENTER);
        mainListItem6.setBackground(Color.decode("#fafafa"));
        mainListItem6.setPreferredSize(new Dimension(mainListItem6.getWidth(), 150));
        mainListItem6.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mainListItem7.add(label7, BorderLayout.CENTER);
        mainListItem7.setBackground(Color.decode("#fafafa"));
        mainListItem7.setPreferredSize(new Dimension(mainListItem7.getWidth(), 150));
        mainListItem7.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mainListItem8.add(label8, BorderLayout.CENTER);
        mainListItem8.setBackground(Color.decode("#fafafa"));
        mainListItem8.setPreferredSize(new Dimension(mainListItem8.getWidth(), 150));
        mainListItem8.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mainListItem9.add(label9, BorderLayout.CENTER);
        mainListItem9.setBackground(Color.decode("#fafafa"));
        mainListItem9.setPreferredSize(new Dimension(mainListItem9.getWidth(), 150));
        mainListItem9.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mainListItem10.add(label10, BorderLayout.CENTER);
        mainListItem10.setBackground(Color.decode("#fafafa"));
        mainListItem10.setPreferredSize(new Dimension(mainListItem10.getWidth(), 150));
        mainListItem10.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mainListItem11.add(label11, BorderLayout.CENTER);
        mainListItem11.setBackground(Color.decode("#fafafa"));
        mainListItem11.setPreferredSize(new Dimension(mainListItem11.getWidth(), 150));
        mainListItem11.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mainListItem12.add(label12, BorderLayout.CENTER);
        mainListItem12.setBackground(Color.decode("#fafafa"));
        mainListItem12.setPreferredSize(new Dimension(mainListItem12.getWidth(), 150));
        mainListItem12.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mainListItem13.add(label13, BorderLayout.CENTER);
        mainListItem13.setBackground(Color.decode("#fafafa"));
        mainListItem13.setPreferredSize(new Dimension(mainListItem13.getWidth(), 150));
        mainListItem13.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane mainListScrollArea = new JScrollPane(mainListArea);
        mainListScrollArea.setBorder(null);
        //scrollArea.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        mainHeader.setBackground(Color.white);
        mainHeader.add(mainHeaderTitle, BorderLayout.NORTH);
        mainHeader.add(mainHeaderSubtitle, BorderLayout.SOUTH);

        mainHeaderTitle.setFont(font_medium.deriveFont(20f));
        mainHeaderSubtitle.setFont(font_regular.deriveFont(14f));

        mainContentArea.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainContentArea.setBackground(Color.white);
        mainContentArea.add(mainHeader, BorderLayout.NORTH);
        mainContentArea.add(mainListScrollArea);

        //@: Add Components to Main Area
        mainArea.setBackground(Color.white);
        mainArea.add(topMainArea, BorderLayout.NORTH);
        mainArea.add(mainContentArea);

        //@: Add Components to Frame
        frame.add(sideBar, BorderLayout.WEST);
        frame.add(mainArea, BorderLayout.CENTER);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == mainListItem1){
            File file = new File("assets/songs/1.wav");
            try {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                clip = AudioSystem.getClip();
                clip.open(audioStream);
                //@: Volume Control
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-12.0f);

                //@: Change UI to show that a song is playing
                mainListItem1.setBackground(Color.decode("#e1fce2"));

                //@: Start playing the audio
                clip.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                throw new RuntimeException(ex);
            }
        }

        else if(e.getSource() == mainListItem2){
            File file = new File("assets/songs/2.wav");
            try {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                //@: Volume Control
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-7.0f);

                mainListItem2.setBackground(Color.decode("#e1fce2"));

                clip.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                throw new RuntimeException(ex);
            }
        }

        else if(e.getSource() == mainListItem3){
            File file = new File("assets/songs/3.wav");
            try {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                //@: Volume Control
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-7.0f);

                mainListItem3.setBackground(Color.decode("#e1fce2"));

                clip.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e){}

    @Override
    public void mouseReleased(MouseEvent e){}

    @Override
    public void mouseEntered(MouseEvent e){}

    @Override
    public void mouseExited(MouseEvent e){}
}
