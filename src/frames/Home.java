package frames;

import models.JukeBox;
import models.Song;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;

public class Home implements ActionListener, ListSelectionListener {
    //@: Global vars
    private JFrame frame;
    private final JPanel rootPanel;
    private JList<String> songList;
    private JPanel controlsPanel;
    private JSlider playbackSlider;
    private Font FONT_MEDIUM, FONT_REGULAR;
    private JButton library_btn, playlist_btn;
    public static final Color DARK_COLOR = Color.decode("#141414");
    public static final Color FRAME_COLOR = Color.white;
    public static final Color LIGHT_GRAY_COLOR = Color.decode("#f1f1f1");
    public static final Color LIGHT_GRAY_COLOR_2 = Color.decode("#f5f5f5");
    private final JukeBox jukeBox;

    //@: Store all song paths in a .txt file (used in creating a default playlist)
    private final ArrayList<String> songPaths = new ArrayList<>();

    public Home(){
        //@: Load frame
        loadFrame();

        jukeBox = new JukeBox(this);

        //@: Create parent panel to hold other panels and their respective components
        rootPanel = new JPanel(new BorderLayout());
        rootPanel.setBackground(Color.white);
        frame.add(rootPanel);

        //@: Create menu components i.e. top section
        loadHeaderComponents();

        //@: Create content components i.e middle section
        loadContentComponents();

        //@: Create footer components i.e bottom section
        loadFooterComponents();
    }

    //@: Component Loaders and Getters
    private void loadFrame(){
        frame = new JFrame();
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setLayout(new BorderLayout());
        frame.setTitle("Serenade - Home");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        ImageIcon frameIcon = getImage("src/assets/images/brand.png");
        frame.setIconImage(frameIcon.getImage());
        frame.getContentPane().setBackground(FRAME_COLOR);

        //@: Load required custom fonts
        getFonts();
    }

    private void loadHeaderComponents(){
        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.setBackground(Color.white);
        menuPanel.setBorder(new EmptyBorder(20, 50, 20, 50));

        //@: Left Column
        JPanel leftColumn = new JPanel(new FlowLayout());
        leftColumn.setBackground(Color.white);
        JButton home_btn = getButton("Home", DARK_COLOR, FRAME_COLOR);
        library_btn = getButton("Library", LIGHT_GRAY_COLOR, DARK_COLOR);
        playlist_btn = getButton("Playlist", LIGHT_GRAY_COLOR, DARK_COLOR);
        leftColumn.add(home_btn);
        leftColumn.add(library_btn);
        leftColumn.add(playlist_btn);
        menuPanel.add(leftColumn, BorderLayout.WEST);

        //@: Right Column
        JPanel rightColumn = new JPanel(new FlowLayout());
        rightColumn.setBackground(Color.white);

        JButton logout_btn = getButton("Logout", LIGHT_GRAY_COLOR, DARK_COLOR);
        logout_btn.addActionListener(e -> {
            frame.dispose();
            new Welcome();
        });

        rightColumn.add(logout_btn);
        menuPanel.add(rightColumn, BorderLayout.EAST);
        rootPanel.add(menuPanel, BorderLayout.NORTH);
    }

    private void loadContentComponents(){
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.white);
        contentPanel.setBorder(new EmptyBorder(20, 50, 20, 50));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(FRAME_COLOR);
        headerPanel.setBorder(new EmptyBorder(0 , 0, 20, 0));

        JLabel title = new JLabel("Welcome back.");
        title.setFont(FONT_MEDIUM.deriveFont(23f));

        JLabel userName = new JLabel("Kurosaki");
        userName.setFont(FONT_REGULAR.deriveFont(15f));
        headerPanel.add(title, BorderLayout.WEST);
        headerPanel.add(userName, BorderLayout.EAST);
        contentPanel.add(headerPanel, BorderLayout.NORTH);

        String[] songs = getSongs();
        songList = new JList<>(songs);
        songList.setFixedCellHeight(32);
        songList.setSelectionBackground(DARK_COLOR);
        songList.setBorder(null);
        songList.setBackground(Color.decode("#f9f9f9"));
        songList.setFont(FONT_REGULAR.deriveFont(13f));
        songList.addListSelectionListener(this);
        songList.setBorder(new EmptyBorder(12, 10, 12, 10));

        JScrollPane songsListScrollPane = new JScrollPane(songList);
        songsListScrollPane.setBorder(null);

        contentPanel.add(songsListScrollPane, BorderLayout.CENTER);
        rootPanel.add(contentPanel, BorderLayout.CENTER);
    }

    private void loadFooterComponents(){
        JPanel playbackBtns = new JPanel(new BorderLayout());
        playbackBtns.setBackground(FRAME_COLOR);

        controlsPanel = new JPanel(null);
        controlsPanel.setBackground(FRAME_COLOR);
        controlsPanel.setPreferredSize(new Dimension(frame.getWidth(), 80));

        JButton prev_btn = new JButton(getImage("src/assets/images/prev.png"));
        prev_btn.setFocusable(false);
        prev_btn.setOpaque(true);
        prev_btn.setBorderPainted(false);
        prev_btn.setBackground(LIGHT_GRAY_COLOR_2);
        prev_btn.setBounds(240, 18, 100, 45);
        prev_btn.addActionListener(e -> {
            //@: Go to the previous song
            jukeBox.previousSong();
        });

        JButton play_btn = new JButton(getImage("src/assets/images/play.png"));
        play_btn.setVisible(false);
        play_btn.setEnabled(false);
        play_btn.setFocusable(false);
        play_btn.setOpaque(true);
        play_btn.setBorderPainted(false);
        play_btn.setBackground(LIGHT_GRAY_COLOR_2);
        play_btn.setBounds(350, 18, 100, 45);
        play_btn.addActionListener(e -> {
            //@: Toggle play and pause buttons
            togglePauseButton();

            //@: Play the song
            jukeBox.playCurrentSong();
        });

        JButton pause_btn = new JButton(getImage("src/assets/images/pause.png"));
        pause_btn.setFocusable(false);
        pause_btn.setOpaque(true);
        pause_btn.setVisible(false);
        pause_btn.setEnabled(false);
        pause_btn.setBorderPainted(false);
        pause_btn.setBackground(LIGHT_GRAY_COLOR_2);
        pause_btn.setBounds(350, 18, 100, 45);
        pause_btn.addActionListener(e -> {
            //@: Toggle pause and play buttons
            togglePlayButton();

            //@: Pause the song
            jukeBox.pauseSong();
        });

        JButton next_btn = new JButton(getImage("src/assets/images/next.png"));
        next_btn.setFocusable(false);
        next_btn.setOpaque(true);
        next_btn.setBorderPainted(false);
        next_btn.setBackground(LIGHT_GRAY_COLOR_2);
        next_btn.setBounds(460, 18, 100, 45);
        next_btn.addActionListener(e -> {
            //@: Go to the next song
            jukeBox.nextSong();
        });

        JButton loadPlaylist_btn = new JButton(getImage("src/assets/images/play.png"));
        loadPlaylist_btn.setFocusable(false);
        loadPlaylist_btn.setOpaque(true);
        loadPlaylist_btn.setBorderPainted(false);
        loadPlaylist_btn.setBackground(LIGHT_GRAY_COLOR_2);
        loadPlaylist_btn.setBounds(350, 18, 100, 45);
        loadPlaylist_btn.addActionListener(e -> {
            File defaultPlaylist = new File("src/playlist.txt");
            jukeBox.loadPlaylist(defaultPlaylist);
            loadPlaylist_btn.setVisible(false);
            loadPlaylist_btn.setEnabled(false);
        });

        playbackSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        playbackSlider.setBorder(new EmptyBorder(0, 50, 20, 50));
        playbackSlider.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
               //@: Pause the song when the user is holding the tick
                jukeBox.pauseSong();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //@: When the user releases the tick
                JSlider source = (JSlider) e.getSource();

                //@: Get the frame value from where the user wants to playback to
                int frame = source.getValue();

                //@: Update the current frame in the jukebox to this frame
                jukeBox.setCurrentFrame(frame);

                //@: Update the current time in ms
                jukeBox.setCurrentTimeInMs((int) (frame / (2.08 * jukeBox.getCurrentSong().getFrameRatePerMs())));

                //@: Resume the song
                jukeBox.playCurrentSong();

                //@: Toggle the pause and play buttons
                togglePauseButton();
            }
        });
        playbackSlider.setBackground(null);

        controlsPanel.add(play_btn);
        controlsPanel.add(pause_btn);
        controlsPanel.add(prev_btn);
        controlsPanel.add(next_btn);
        controlsPanel.add(loadPlaylist_btn);

        playbackBtns.add(controlsPanel, BorderLayout.NORTH);
        playbackBtns.add(playbackSlider, BorderLayout.SOUTH);
        rootPanel.add(playbackBtns, BorderLayout.SOUTH);
    }

    private void getFonts(){
        //@: Setup Custom Font
        //@: Reference - https://stackoverflow.com/questions/5652344/how-can-i-use-a-custom-font-in-java
        try {
            FONT_REGULAR = Font.createFont(Font.TRUETYPE_FONT, new File("src/assets/fonts/Rubik-Regular.ttf"));
            FONT_MEDIUM = Font.createFont(Font.TRUETYPE_FONT, new File("src/assets/fonts/Rubik-Medium.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(FONT_REGULAR);
            ge.registerFont(FONT_MEDIUM);
        } catch (IOException | FontFormatException e) {
            System.out.println(e.getMessage());
        }
    }

    private String[] getSongs(){
        String musicDirectory = "src/assets/music/";
        File fileDirectory = new File(musicDirectory);
        String[] files = fileDirectory.list();

        if((files != null ? files.length : 0) == 0){
            System.out.println("The directory is empty");
            return null;
        }
        else{
            for (String file : files){
                //@: Store the song paths in an array list
                songPaths.add(musicDirectory + file);
                createDefaultPlaylist();
            }
            //@: Sort the files in ascending order
            Arrays.sort(files);

            return files;
        }
    }

    private ImageIcon getImage(String imagePath){
        try{
            //@: Read the image file from the given path
            BufferedImage image = ImageIO.read(new File(imagePath));
            return new ImageIcon(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private JButton getButton (String buttonText, Color bgColor, Color textColor){
        JButton button = new JButton(buttonText);
        button.setFocusable(false);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setForeground(textColor);
        button.setBackground(bgColor);
        button.setFont(FONT_REGULAR.deriveFont(12f));
        button.setPreferredSize(new Dimension(100, 35));
        button.addActionListener(this);

        return button;
    }

    //@: Event Listeners
    //@: JButton Event Listener
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == library_btn) {
            new Library();
            frame.dispose();
        }
        else if(e.getSource() == playlist_btn){
            new Playlist();
            frame.dispose();
        }
    }

    //@: JList Event Listener
    @Override
    public void valueChanged(ListSelectionEvent e) {
        /*if(!e.getValueIsAdjusting()){
            String selection = songList.getSelectedValue();
            if(selection != null){
                Song song = new Song("src/assets/music/" + selection);

                //@: Stop current song if any is playing
                Song currentSong = jukeBox.getCurrentSong();
                if(currentSong != null) {
                    jukeBox.stopSong();
                    jukeBox.loadSong(song);
                    updatePlaybackSlider(song);
                    togglePauseButton();
                }
                else{
                    //@: Load song in music player
                    jukeBox.loadSong(song);

                    //@: Update playback slider
                    updatePlaybackSlider(song);

                    //@: Toggle the play and pause buttons
                    togglePauseButton();
                }
            }
        }*/
    }

    //@: Helper Methods
    private void createDefaultPlaylist(){
        File selectedFile = new File("src/playlist.txt");
        try {
            //@: Write all song paths into the file
            FileWriter fileWriter = new FileWriter(selectedFile);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            //@: Sort the list in ascending order
            Collections.sort(songPaths);

            //@: Loop through the songs path list and write each song to the file
            for(String songPath : songPaths){
                bufferedWriter.write(songPath + "\n");
            }
            bufferedWriter.close();

            //@: Display alert dialog if necessary
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void togglePauseButton(){
        //@: get a ref to play and pause buttons
        JButton playButton = (JButton) controlsPanel.getComponent(0);
        JButton pauseButton = (JButton) controlsPanel.getComponent(1);

        //@: disable play button
        playButton.setVisible(false);
        playButton.setEnabled(false);

        //@: enable pause button
        pauseButton.setVisible(true);
        pauseButton.setEnabled(true);
    }

    public void togglePlayButton(){
        //@: get a ref to play and pause buttons
        JButton playButton = (JButton) controlsPanel.getComponent(0);
        JButton pauseButton = (JButton) controlsPanel.getComponent(1);

        //@: enable play button
        playButton.setVisible(true);
        playButton.setEnabled(true);

        //@: disable pause button
        pauseButton.setVisible(false);
        pauseButton.setEnabled(false);
    }

    //@: This is used to update the slider from the jukebox class
    public void setPlaybackSliderValue(int frame){
        playbackSlider.setValue(frame);
    }

    public void setSelectedSong(String path){
        int currentIndex = -1;
        String[] paths = songPaths.toArray(new String[0]);
        for(int i=0; i<paths.length; i++){
            if(paths[i].equals(path)){
                currentIndex = i;
                break;
            }
        }
        System.out.println(currentIndex);
        songList.setSelectedIndex(currentIndex);
    }

    public void updatePlaybackSlider (Song song){
        //@: Update the max count for slider
        playbackSlider.setMaximum(song.getMp3File().getFrameCount());

        //@: Create the song length label
        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();

        //@: Starting the song will begin at 00:00
        JLabel labelStart = new JLabel("00:00");
        labelStart.setFont(FONT_REGULAR.deriveFont(11f));
        labelStart.setForeground(DARK_COLOR);

        //@: End
        JLabel labelEnd = new JLabel(song.getSongLength());
        labelEnd.setFont(FONT_REGULAR.deriveFont(11f));
        labelEnd.setForeground(DARK_COLOR);

        labelTable.put(0, labelStart);
        labelTable.put(song.getMp3File().getFrameCount(), labelEnd);

        playbackSlider.setLabelTable(labelTable);
        playbackSlider.setPaintLabels(true);
    }
}
