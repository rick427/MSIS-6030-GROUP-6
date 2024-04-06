package frames;

import models.JukeBox;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

public class Playlist extends Home implements ActionListener, MouseListener {
    private JFrame frame;
    private final JPanel rootPanel;
    private JPanel controlsPanel;
    private JSlider playbackSlider;
    private JCheckBox[] songCheckBoxes;
    private JPanel songList;
    private JButton home_btn, library_btn;
    private final JukeBox jukeBox;
    private Font FONT_MEDIUM, FONT_REGULAR;
    public static final Color DARK_COLOR = Color.decode("#141414");
    public static final Color FRAME_COLOR = Color.white;
    public static final Color LIGHT_GRAY_COLOR = Color.decode("#f1f1f1");
    public static final Color LIGHT_GRAY_COLOR_2 = Color.decode("#f5f5f5");
    public static final Color PLAYLIST_COLOR = Color.decode("#f9f9f9");
    private final ArrayList<String> songPaths = new ArrayList<>();
    String musicDirPath = "src/assets/music/";
    File fileDirectory = new File(musicDirPath);
    String[] files = fileDirectory.list();

    Playlist(){
        //@: Load frame
        loadFrame();

        jukeBox = new JukeBox(this);

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
            new SignIn();
            frame.dispose();
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
        save_playlist_btn.setPreferredSize(new Dimension(130, 40));
        save_playlist_btn.addActionListener(e -> {
            if(songPaths.isEmpty()){
                JOptionPane.showMessageDialog(
                        frame,
                        "Playlists cannot be null or empty.\nPlease select some songs to create a playlist.",
                        "Missing Field(s)!",
                        JOptionPane.ERROR_MESSAGE
                );
            }
            else if(getPlaylists().length == 4){
                JOptionPane.showMessageDialog(
                        frame,
                        "You can only have 4 playlists per account.\nTo get more playlists purchase a Serenade\n subscription at $10.00/month.",
                        "Playlist Limit Exceeded!",
                        JOptionPane.ERROR_MESSAGE
                );
                //@: Clear the checkboxes
                for(int i=0; i<files.length; i++){
                    songCheckBoxes[i].setSelected(false);
                }
            }
            else{
                //@: Create a new playlist based on user choice
                createPlaylist();

                //@: Clear the checkboxes
                for(int i=0; i<files.length; i++){
                    songCheckBoxes[i].setSelected(false);
                }
                //@: Show a success message
                JOptionPane.showMessageDialog(
                        frame,
                        "Congratulations! You just added a new Serenade to your list.\nGet started by taking your Serenade for spin.",
                        "Success!",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });


        JButton reset_playlist_btn = getButton("Delete Playlists", LIGHT_GRAY_COLOR, DARK_COLOR);
        reset_playlist_btn.setPreferredSize(new Dimension(130, 40));
        reset_playlist_btn.addActionListener(e -> {
            deletePlaylists();
        });

        buttonsPanel.setBackground(FRAME_COLOR);
        buttonsPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        buttonsPanel.add(save_playlist_btn);
        buttonsPanel.add(reset_playlist_btn);

        groupPanel.setBackground(FRAME_COLOR);
        groupPanel.setBorder(new EmptyBorder(0, 0, 15, 0));
        groupPanel.add(title, BorderLayout.WEST);
        groupPanel.add(buttonsPanel, BorderLayout.EAST);

        JPanel playlistPanel = new JPanel(new GridLayout(0, 4, 10, 0));
        playlistPanel.setBackground(FRAME_COLOR);
        playlistPanel.setBorder(new EmptyBorder(0, 0, 15, 0));

        for(int i=0; i< getPlaylists().length; i++){
            JPanel playlistItem = new JPanel(new BorderLayout());
            playlistItem.setBackground(PLAYLIST_COLOR);
            playlistItem.setPreferredSize(new Dimension(100, 50));
            playlistItem.addMouseListener(this);

            String name = getPlaylists()[i].getName().toLowerCase().contains("default") ? "Default-" : "Playlist-";
            JLabel playlistItemText = new JLabel(name + (i+1));
            ImageIcon icon = getImage("src/assets/images/brand.png");
            ImageIcon brandIcon = new ImageIcon(icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
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
            File defaultPlaylist = new File("src/playlists/default.txt");
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
        if((files != null ? files.length : 0) == 0){
            System.out.println("The directory is empty");
        }
        else{
            Arrays.sort(files);
            songCheckBoxes = new JCheckBox[files.length];

            for(int i=0; i< songCheckBoxes.length; i++){
                songCheckBoxes[i] = new JCheckBox(files[i]);
                songCheckBoxes[i].setFont(FONT_REGULAR.deriveFont(13f));
                int tempIndex = i;
                songCheckBoxes[i].addActionListener(e -> {
                    if(songCheckBoxes[tempIndex].isSelected()){
                        songPaths.add(musicDirPath + files[tempIndex]);
                    }
                    else {
                        songPaths.remove(musicDirPath + files[tempIndex]);
                    }
                });
                songList.add(songCheckBoxes[i]);
            }
        }
    }

    private void createPlaylist(){
        String id = "playlist-"+ UUID.randomUUID() +".txt";
        File selectedFile = new File("src/playlists/"+id);
        try {
            if(!selectedFile.exists()){
                boolean res = selectedFile.createNewFile();
                System.out.println(res);
            }

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

    private File[] getPlaylists(){
        File playlistFolder = new File("src/playlists");
        return playlistFolder.listFiles();
    }

    private void deletePlaylists(){
        File playlistFolder = new File("src/playlists");
        File[] files = playlistFolder.listFiles();
        if(files == null) return;
        else if(files.length == 1){
            JOptionPane.showMessageDialog(
                    frame,
                    "You cannot delete a system defined playlist.\nConsider getting a subscription to better enjoy the\nSerenade experience.",
                    "Invalid Operation!",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        for(File file : files){
            String fileName = file.getName();
            if(!fileName.contains("default")){
                boolean res = file.delete();
                System.out.println(res);
            }
        }
        JOptionPane.showMessageDialog(
                frame,
                "Your playlists have been deleted successfully.\nConsider getting a subscription to better enjoy the\nSerenade experience.",
                "Playlists Deleted!",
                JOptionPane.INFORMATION_MESSAGE
        );
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

    @Override
    public void mouseClicked(MouseEvent e) {
        //@: Load the clicked playlist
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
