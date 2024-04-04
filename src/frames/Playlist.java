package frames;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
//import java.util.ArrayList;
import java.util.Arrays;

public class Playlist implements ActionListener {
    private JFrame frame;
    private final JPanel rootPanel;
    private JPanel songList, controlsPanel;
    private JSlider playbackSlider;
    private JButton home_btn, library_btn;
    private Font FONT_MEDIUM, FONT_REGULAR;
    public static final Color DARK_COLOR = Color.decode("#141414");
    public static final Color FRAME_COLOR = Color.white;
    public static final Color LIGHT_GRAY_COLOR = Color.decode("#f1f1f1");
    public static final Color LIGHT_GRAY_COLOR_2 = Color.decode("#f5f5f5");
    public static final Color PLAYLIST_COLOR = Color.decode("#f9f9f9");
    //private final ArrayList<String> songPaths = new ArrayList<>();

    Playlist(){
        //@: Load frame
        loadFrame();

        //@: Create parent panel to hold other panels and their respective components
        rootPanel = new JPanel(new BorderLayout());
        rootPanel.setBackground(Color.white);
        frame.add(rootPanel);

        //@: Create menu components i.e. top section
        loadHeaderComponents();

        //@: Create content components i.e. middle section
        loadContentComponents();

        //@: Create controls components i.e. footer section
        loadFooterComponents();
    }

    private void loadFrame(){
        frame = new JFrame();
        frame.setSize(800, 700);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setLayout(new BorderLayout());
        frame.setTitle("Serenade - Playlist");
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
        home_btn = getButton("Home", LIGHT_GRAY_COLOR, DARK_COLOR);
        library_btn = getButton("Library", LIGHT_GRAY_COLOR, DARK_COLOR);
        JButton playlist_btn = getButton("Playlist", DARK_COLOR, FRAME_COLOR);
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
        contentPanel.setBackground(FRAME_COLOR);
        contentPanel.setBorder(new EmptyBorder(20, 50, 20, 50));

        JPanel stackPanel = new JPanel(new BorderLayout());
        stackPanel.setBackground(FRAME_COLOR);

        JPanel groupPanel = new JPanel(new BorderLayout());
        JLabel title = new JLabel("My Serenades.");
        title.setFont(FONT_MEDIUM.deriveFont(23f));

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton save_playlist_btn = getButton("Add to Playlist", DARK_COLOR, FRAME_COLOR);
        JButton reset_playlist_btn = getButton("Reset Playlist", LIGHT_GRAY_COLOR, DARK_COLOR);
        save_playlist_btn.setPreferredSize(new Dimension(130, 40));
        reset_playlist_btn.setPreferredSize(new Dimension(130, 40));

        buttonsPanel.setBackground(FRAME_COLOR);
        buttonsPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        buttonsPanel.add(save_playlist_btn);
        buttonsPanel.add(reset_playlist_btn);

        groupPanel.setBackground(FRAME_COLOR);
        groupPanel.add(title, BorderLayout.WEST);
        groupPanel.add(buttonsPanel, BorderLayout.EAST);

        JPanel playlistPanel = new JPanel(new GridLayout(0, 4, 10, 0));
        playlistPanel.setBackground(FRAME_COLOR);
        playlistPanel.setBorder(new EmptyBorder(0, 0, 15, 0));

        for(int i=1; i<= 4; i++){
            JPanel playlistItem = new JPanel(new BorderLayout());
            playlistItem.setBackground(PLAYLIST_COLOR);
            playlistItem.setPreferredSize(new Dimension(100, 60));

            JLabel playlistItemText = new JLabel("Playlist "+ i);
            ImageIcon icon = getImage("src/assets/images/brand.png");
            ImageIcon brandIcon = new ImageIcon(icon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
            playlistItemText.setIcon(brandIcon);
            playlistItemText.setIconTextGap(16);
            playlistItemText.setForeground(DARK_COLOR);
            playlistItemText.setFont(FONT_MEDIUM.deriveFont(13f));
            playlistItemText.setHorizontalAlignment(JLabel.CENTER);
            playlistItemText.setVerticalAlignment(JLabel.CENTER);

            playlistItem.add(playlistItemText, BorderLayout.CENTER);
            playlistPanel.add(playlistItem);
        }

        stackPanel.add(groupPanel, BorderLayout.NORTH);
        stackPanel.add(playlistPanel, BorderLayout.SOUTH);

        songList = new JPanel(new GridLayout(0, 1, 0, 10));
        getSongs();
        songList.setBackground(Color.decode("#f9f9f9"));
        songList.setBorder(new EmptyBorder(5, 10, 5, 10));

        JScrollPane songsListScrollPane = new JScrollPane(songList);
        songsListScrollPane.setBorder(null);

        contentPanel.add(stackPanel, BorderLayout.NORTH);
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
            //togglePauseButton();

            //@: Play the song
            //jukeBox.playCurrentSong();
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
            //togglePlayButton();

            //@: Pause the song
            //jukeBox.pauseSong();
        });

        JButton next_btn = new JButton(getImage("src/assets/images/next.png"));
        next_btn.setFocusable(false);
        next_btn.setOpaque(true);
        next_btn.setBorderPainted(false);
        next_btn.setBackground(LIGHT_GRAY_COLOR_2);
        next_btn.setBounds(460, 18, 100, 45);
        next_btn.addActionListener(e -> {
            //@: Go to the next song
            //jukeBox.nextSong();
        });

        JButton loadPlaylist_btn = new JButton(getImage("src/assets/images/play.png"));
        loadPlaylist_btn.setFocusable(false);
        loadPlaylist_btn.setOpaque(true);
        loadPlaylist_btn.setBorderPainted(false);
        loadPlaylist_btn.setBackground(LIGHT_GRAY_COLOR_2);
        loadPlaylist_btn.setBounds(350, 18, 100, 45);
        loadPlaylist_btn.addActionListener(e -> {
            //File defaultPlaylist = new File("src/playlist.txt");
            //jukeBox.loadPlaylist(defaultPlaylist);
            loadPlaylist_btn.setVisible(false);
            loadPlaylist_btn.setEnabled(false);
        });

        playbackSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        playbackSlider.setBorder(new EmptyBorder(0, 50, 20, 50));
        playbackSlider.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //@: Pause the song when the user is holding the tick
                //jukeBox.pauseSong();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //@: When the user releases the tick
                //JSlider source = (JSlider) e.getSource();

                //@: Get the frame value from where the user wants to playback to
                //int frame = source.getValue();

                //@: Update the current frame in the jukebox to this frame
                //jukeBox.setCurrentFrame(frame);

                //@: Update the current time in ms
                //jukeBox.setCurrentTimeInMs((int) (frame / (2.08 * jukeBox.getCurrentSong().getFrameRatePerMs())));

                //@: Resume the song
                //jukeBox.playCurrentSong();

                //@: Toggle the pause and play buttons
                //togglePauseButton();
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

    private void getSongs(){
        String musicDirectory = "src/assets/music/";
        File fileDirectory = new File(musicDirectory);
        String[] files = fileDirectory.list();

        if((files != null ? files.length : 0) == 0){
            System.out.println("The directory is empty");
        }
        else{
            Arrays.sort(files);
            for (String file : files){
                JCheckBox song = new JCheckBox(file);
                song.addItemListener(e -> {
                    if(song.isSelected()){
                        //@: Add song to selected array
                        System.out.println(song.getText());
                    }
                });
                song.setFont(FONT_REGULAR.deriveFont(13f));
                songList.add(song);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == home_btn) {
            new Home();
            frame.dispose();
        }
        else if(e.getSource() == library_btn){
            new Library();
            frame.dispose();
        }
    }
}
