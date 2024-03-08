package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class Jukebox implements MouseListener, ActionListener {
    JFrame frame;
    Clip clip;
    JButton homeButton, checkLibButton, playlistButton, logoutButton;
    JPanel mainListItem;
    String primary_dark = "#141414";
    Font font_bold, font_medium, font_light, font_regular;

    Jukebox(){
        //@: Initial Setup
        frame = new JFrame();
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

        homeButton = new JButton("Home");
        homeButton.setBounds(32, 25, 135, 35);
        homeButton.setFocusable(false);
        homeButton.setOpaque(true);
        homeButton.setBorderPainted(false);
        homeButton.setForeground(Color.white);
        homeButton.setBackground(Color.decode(primary_dark));
        homeButton.setFont(font_medium.deriveFont(13f));
        homeButton.setPreferredSize(new Dimension(200, 50));
        homeButton.addActionListener(e -> {});

        checkLibButton = new JButton("My Library");
        checkLibButton.setBounds(32, 75, 135, 35);
        checkLibButton.setFocusable(false);
        checkLibButton.setOpaque(true);
        checkLibButton.setBorderPainted(false);
        checkLibButton.setForeground(Color.decode(primary_dark));
        checkLibButton.setBackground(Color.white);
        checkLibButton.setFont(font_regular.deriveFont(13f));
        checkLibButton.setPreferredSize(new Dimension(200, 50));
        checkLibButton.addActionListener(this);

        playlistButton = new JButton("My Playlist");
        playlistButton.setBounds(32, 125, 135, 35);
        playlistButton.setFocusable(false);
        playlistButton.setOpaque(true);
        playlistButton.setBorderPainted(false);
        playlistButton.setForeground(Color.decode(primary_dark));
        playlistButton.setBackground(Color.white);
        playlistButton.setFont(font_regular.deriveFont(13f));
        playlistButton.setPreferredSize(new Dimension(200, 50));
        playlistButton.addActionListener(this);

        logoutButton = new JButton("Logout");
        logoutButton.setBounds(32, 560, 135, 35);
        logoutButton.setFocusable(false);
        logoutButton.setOpaque(true);
        logoutButton.setBorderPainted(false);
        logoutButton.setForeground(Color.decode("#de2f40"));
        logoutButton.setBackground(Color.white);
        logoutButton.setFont(font_medium.deriveFont(13f));
        logoutButton.setPreferredSize(new Dimension(200, 50));
        logoutButton.addActionListener(this);

        sideBar.add(homeButton);
        sideBar.add(checkLibButton);
        sideBar.add(playlistButton);
        sideBar.add(logoutButton);

        //@: Top Main Area Section
        JPanel topMainArea = new JPanel(new BorderLayout());
        JPanel topUserArea = new JPanel();

        JLabel userName = new JLabel("Kirigaya404");
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
        mainListItem = new JPanel(new BorderLayout());

        JLabel label1 = new JLabel("<html><p style='text-align:center;'>Number One ( Bleach ) - Shiro Sagisu (Topic).</p></html>");
        ImageIcon labelIcon = new ImageIcon("assets/music.png");
        ImageIcon labelIconImage = new ImageIcon(labelIcon.getImage().getScaledInstance(80, 50, Image.SCALE_SMOOTH));
        label1.setIcon(labelIconImage);
        label1.setFont(font_light.deriveFont(12f));
        label1.setHorizontalAlignment(JLabel.CENTER);
        label1.setVerticalAlignment(JLabel.CENTER);
        label1.setHorizontalTextPosition(JLabel.CENTER);
        label1.setVerticalTextPosition(JLabel.BOTTOM);

        mainListArea.setBackground(Color.white);
        mainListArea.add(mainListItem);

        mainListItem.add(label1, BorderLayout.CENTER);
        mainListItem.setBackground(Color.decode("#fafafa"));
        mainListItem.addMouseListener(this);
        mainListItem.setPreferredSize(new Dimension(mainListItem.getWidth(), 150));
        mainListItem.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

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

        //@: Footer Area
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel playButtonLabel = new JLabel("PLAY CONTROLS HERE!");
        playButtonLabel.setForeground(Color.decode(primary_dark));
        playButtonLabel.setFont(font_medium.deriveFont(15f));
        playButtonLabel.setHorizontalAlignment(JLabel.CENTER);
        playButtonLabel.setVerticalAlignment(JLabel.CENTER);
        playButtonLabel.setHorizontalTextPosition(JLabel.CENTER);
        playButtonLabel.setVerticalTextPosition(JLabel.BOTTOM);

        footer.setBackground(Color.decode("#f9f9f9"));
        footer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));


        footer.add(playButtonLabel);

        //@: Add Components to Main Area
        mainArea.setBackground(Color.white);
        mainArea.add(topMainArea, BorderLayout.NORTH);
        mainArea.add(mainContentArea);
        mainArea.add(footer, BorderLayout.SOUTH);

        //@: Add Components to Frame
        frame.add(sideBar, BorderLayout.WEST);
        frame.add(mainArea, BorderLayout.CENTER);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == mainListItem){
            File file = new File("assets/songs/1.wav");
            try {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                clip = AudioSystem.getClip();
                clip.open(audioStream);
                //@: Volume Control
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-12.0f);

                //@: Change UI to show that a song is playing
                mainListItem.setBackground(Color.decode("#e1fce2"));

                //@: Start playing the audio
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == checkLibButton){
            frame.dispose();
            new Library();
        }
        else if(e.getSource() == playlistButton){
            frame.dispose();
            new Playlist();
        }
        else if(e.getSource() == logoutButton){
            frame.dispose();
            new Login();
        }
    }
}
