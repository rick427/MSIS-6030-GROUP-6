package components;

import Models.SongData;
import services.DbService;
import services.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
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

        JLabel userName = new JLabel(User.username);
//        JLabel userName = new JLabel("Kirigaya404");
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


        /*THIS IS FOR THE HOME*/

        //@: Main Content Area Section
        JPanel mainContentAreaHome = getjPanelHome();

        /*THIS IS THE END OF THE HOME*/

        /*THIS IS THE LIBRARY*/

        //@: Main Content Area Section
        JPanel mainContentAreaLibrary = getjPanelLIbrary();

        /*THIS IS THE END OF THE LIBRARY*/
        
        
        /*THE BEGINNING OF PLAYLIST*/

        //@: Main Content Area Section
        JPanel mainContentAreaPlaylist = getjPanelPlaylist();

        /*THE END OF THE PLAYLIST*/

        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                mainContentAreaHome.setVisible(true);
//                mainContentAreaLibrary.setVisible(false);
//                mainContentAreaPlaylist.setVisible(false);

                mainArea.add(mainContentAreaHome);
                mainArea.remove(mainContentAreaPlaylist);
                mainArea.remove(mainContentAreaLibrary);

                mainArea.revalidate();
                mainArea.repaint();
            }
        });

        checkLibButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                frame.dispose();
//                new Library();
                System.out.println("HERE");
                try{
                    mainArea.remove(mainContentAreaHome);
                    mainArea.remove(mainContentAreaPlaylist);
                    mainArea.add(mainContentAreaLibrary);

                    System.out.println("Removing");

                    frame.revalidate();
                    frame.repaint();
                }
                catch (Exception exception){
                    System.out.print(exception);
//                    throw exception;
                }
            }
        });

        playlistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

//                mainContentAreaHome.setVisible(false);
//                mainContentAreaLibrary.setVisible(false);
//                mainContentAreaPlaylist.setVisible(true);

                mainArea.remove(mainContentAreaHome);
                mainArea.add(mainContentAreaPlaylist);
                mainArea.remove(mainContentAreaLibrary);

                mainArea.revalidate();
                mainArea.repaint();
            }
        });

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
//        mainArea.add(mainContentAreaPlaylist);
//        mainArea.add(mainContentAreaLibrary);
        mainArea.add(mainContentAreaHome);
        mainContentAreaHome.setVisible(true);;
//        mainContentAreaLibrary.setVisible(false);
//        mainContentAreaPlaylist.setVisible(false);
        mainArea.revalidate();
        mainArea.repaint();
//        mainArea.layout();
        mainArea.add(footer, BorderLayout.SOUTH);

        //@: Add Components to Frame
        frame.add(sideBar, BorderLayout.WEST);
        frame.add(mainArea, BorderLayout.CENTER);
//        frame.add(mainArea);
//        frame.getContentPane().add(mainContentAreaHome);

    }

    private JPanel getjPanelPlaylist() {
        System.out.println("Creating playlist panel");
        JPanel mainContentAreaPlaylist = new JPanel(new BorderLayout(0, 20));

        JPanel mainHeaderPlaylist = new JPanel(new BorderLayout(0, 3));
        JLabel mainHeaderTitlePlaylist = new JLabel("My Playlists.");
        JLabel mainHeaderSubtitlePlaylist = new JLabel("Lorem ipsum dolor shiat kongo smith");

        JPanel mainListArea = new JPanel(new GridLayout(0, 4, 10, 10));

        JScrollPane mainListScrollArea = new JScrollPane(mainListArea);
        mainListScrollArea.setBorder(null);
        //scrollArea.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        mainHeaderPlaylist.setBackground(Color.white);
        mainHeaderPlaylist.add(mainHeaderTitlePlaylist, BorderLayout.NORTH);
        mainHeaderPlaylist.add(mainHeaderSubtitlePlaylist, BorderLayout.SOUTH);

        mainHeaderTitlePlaylist.setFont(font_medium.deriveFont(20f));
        mainHeaderSubtitlePlaylist.setFont(font_regular.deriveFont(14f));

        mainContentAreaPlaylist.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainContentAreaPlaylist.setBackground(Color.white);
        mainContentAreaPlaylist.add(mainHeaderPlaylist, BorderLayout.NORTH);
        mainContentAreaPlaylist.add(mainListScrollArea);
        return mainContentAreaPlaylist;
    }

    private JPanel getjPanelLIbrary() {
        System.out.println("Creating Library panel");
        JPanel mainContentAreaLibrary = new JPanel(new BorderLayout(0, 20));

        JPanel mainHeaderLibrary = new JPanel(new BorderLayout(0, 3));
        JLabel mainHeaderTitleLibrary = new JLabel("My Library.");
        JLabel mainHeaderSubtitleLibrary = new JLabel("Explore your library for a more personalized experience.");

        JPanel mainListArea = new JPanel(new GridLayout(0, 4, 10, 10));

        JScrollPane mainListScrollArea = new JScrollPane(mainListArea);
        mainListScrollArea.setBorder(null);
        //scrollArea.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        mainHeaderLibrary.setBackground(Color.white);
        mainHeaderLibrary.add(mainHeaderTitleLibrary, BorderLayout.NORTH);
        mainHeaderLibrary.add(mainHeaderSubtitleLibrary, BorderLayout.SOUTH);

        mainHeaderTitleLibrary.setFont(font_medium.deriveFont(20f));
        mainHeaderSubtitleLibrary.setFont(font_regular.deriveFont(14f));

        mainContentAreaLibrary.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainContentAreaLibrary.setBackground(Color.white);
        mainContentAreaLibrary.add(mainHeaderLibrary, BorderLayout.NORTH);
        mainContentAreaLibrary.add(mainListScrollArea);
        return mainContentAreaLibrary;
    }

    private JPanel getjPanelHome() {
        DbService dbService = new DbService();
        List<SongData> songDataList = dbService.getAllSongs();
        System.out.println(songDataList);
        System.out.println("Creating Home panel");
        JPanel mainContentAreaHome = new JPanel(new BorderLayout(0, 20));

        JPanel mainHeaderHome = new JPanel(new BorderLayout(0, 3));
        JLabel mainHeaderTitleHome = new JLabel("Trending Today.");
        JLabel mainHeaderSubtitleHome = new JLabel("Get better recommendations the more you listen.");

        JPanel mainListAreaHome = new JPanel(new GridLayout(0, 3, 10, 10));
        mainListItem = new JPanel(new BorderLayout());
//        mainListItem =  new JPanel(new GridLayout(1,3));

        JLabel label1 = new JLabel("<html><p style='text-align:center;'>Number One ( Bleach )  <p> Shiro Sagisu (Topic) </p>.</p></html>");
        JLabel label2 = new JLabel("<html><p style='text-align:center;'>Go and Die (Topic) </p></html>");
        JLabel label3 = new JLabel("<html><p style='text-align:center;'>Hasn't death called you? <p> Mishary Rasheed Al Afasy. </p></p></html>");
        JLabel label4 = new JLabel("<html><p style='text-align:center;'>Hasn't death called you again? <p> Mishary Rasheed Al Afasy. </p></p></html>");
        JLabel label5 = new JLabel("<html><p style='text-align:center;'>Hasn't death called you once again? <p> Mishary Rasheed Al Afasy. </p></p></html>");
        ImageIcon labelIcon = new ImageIcon("assets/music.png");
        ImageIcon labelIconImage = new ImageIcon(labelIcon.getImage().getScaledInstance(80, 50, Image.SCALE_SMOOTH));
        label1.setIcon(labelIconImage);
        label1.setFont(font_light.deriveFont(12f));
        label1.setHorizontalAlignment(JLabel.CENTER);
        label1.setVerticalAlignment(JLabel.CENTER);
        label1.setHorizontalTextPosition(JLabel.CENTER);
        label1.setVerticalTextPosition(JLabel.BOTTOM);
        label1.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                playMusic("BurundiSound");
                label1.setBackground(Color.decode("#e1fce2"));
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        label2.setIcon(labelIconImage);
        label2.setFont(font_light.deriveFont(12f));
        label2.setHorizontalAlignment(JLabel.CENTER);
        label2.setVerticalAlignment(JLabel.CENTER);
        label2.setHorizontalTextPosition(JLabel.CENTER);
        label2.setVerticalTextPosition(JLabel.BOTTOM);

        label2.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                playMusic("1");

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        label3.setIcon(labelIconImage);
        label3.setFont(font_light.deriveFont(12f));
        label3.setHorizontalAlignment(JLabel.CENTER);
        label3.setVerticalAlignment(JLabel.CENTER);
        label3.setHorizontalTextPosition(JLabel.CENTER);
        label3.setVerticalTextPosition(JLabel.BOTTOM);

        label4.setIcon(labelIconImage);
        label4.setFont(font_light.deriveFont(12f));
        label4.setHorizontalAlignment(JLabel.CENTER);
        label4.setVerticalAlignment(JLabel.CENTER);
        label4.setHorizontalTextPosition(JLabel.CENTER);
        label4.setVerticalTextPosition(JLabel.BOTTOM);

        label5.setIcon(labelIconImage);
        label5.setFont(font_light.deriveFont(12f));
        label5.setHorizontalAlignment(JLabel.CENTER);
        label5.setVerticalAlignment(JLabel.CENTER);
        label5.setHorizontalTextPosition(JLabel.CENTER);
        label5.setVerticalTextPosition(JLabel.BOTTOM);


        mainListAreaHome.setBackground(Color.white);
        // mainListAreaHome.add(mainListItem);

        mainListAreaHome.add(label1, BorderLayout.CENTER);
//        mainListAreaHome.setBackground(Color.decode("#fafafa"));
//        mainListAreaHome.addMouseListener(this);
//        mainListAreaHome.setPreferredSize(new Dimension("auto", 150));
//        mainListAreaHome.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
////
////
        mainListAreaHome.add(label2, BorderLayout.CENTER);
        mainListAreaHome.add(label3, BorderLayout.CENTER);
        mainListAreaHome.add(label4, BorderLayout.CENTER);
        mainListAreaHome.add(label5, BorderLayout.CENTER);
//        mainListAreaHome.setBackground(Color.decode("#fafafa"));
//        mainListAreaHome.addMouseListener(this);
//        mainListAreaHome.setPreferredSize(new Dimension(mainListAreaHome.getWidth(), 150));
//        mainListAreaHome.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        JScrollPane mainListScrollAreaHome = new JScrollPane(mainListAreaHome);
        mainListScrollAreaHome.setBorder(null);
        //scrollArea.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        mainHeaderHome.setBackground(Color.white);
        mainHeaderHome.add(mainHeaderTitleHome, BorderLayout.NORTH);
        mainHeaderHome.add(mainHeaderSubtitleHome, BorderLayout.SOUTH);

        mainHeaderTitleHome.setFont(font_medium.deriveFont(20f));
        mainHeaderSubtitleHome.setFont(font_regular.deriveFont(14f));

        mainContentAreaHome.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainContentAreaHome.setBackground(Color.white);
        mainContentAreaHome.add(mainHeaderHome, BorderLayout.NORTH);
        mainContentAreaHome.add(mainListScrollAreaHome);
        return mainContentAreaHome;
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

    public void playMusic(String musicTitle){
        System.out.print("assets/songs/"+musicTitle+".wav");
        File file = new File("assets/songs/"+musicTitle+".wav");
        try {
//            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
//            clip = AudioSystem.getClip();
//            clip.open(audioStream);
//            //@: Volume Control
//            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
//            gainControl.setValue(-12.0f);
//
//            //@: Change UI to show that a song is playing
//            mainListItem.setBackground(Color.decode("#e1fce2"));
//
//            //@: Start playing the audio
//            clip.start();
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            //@: Volume Control
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-12.0f);

            // Check if the clip is already running
            if (clip.isRunning()) {
                clip.stop();  // Stop the current playback
//                clip.setFramePosition(0); // Reset the clip to the beginning
//                clip.start(); // Start playing from the beginning
            } else {
                //@: Change UI to show that a song is playing
//                mainListItem.setBackground(Color.decode("#e1fce2"));

                //@: Start playing the audio (only if it wasn't already running)
                clip.start();
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            throw new RuntimeException(ex);
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
//            frame.dispose();
//            new Library();

        }
        else if(e.getSource() == playlistButton){
//            frame.dispose();
//            new Playlist();
        }
        else if(e.getSource() == logoutButton){
            frame.dispose();
            new Login();
        }
    }
}
