package frames;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Library implements ActionListener {
    private JFrame frame;
    private final JPanel rootPanel;
    private JPanel songList;
    private JButton home_btn, playlist_btn, search_btn,  update_btn;
    private Font FONT_MEDIUM, FONT_REGULAR;
    public static final Color DARK_COLOR = Color.decode("#141414");
    public static final Color FRAME_COLOR = Color.white;
    public static final Color LIGHT_GRAY_COLOR = Color.decode("#f1f1f1");
    public static final Color INPUT_COLOR = Color.decode("#c9c9c9");

    private ArrayList<String> selectedSongs = new ArrayList<>();

    Library(){
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
    }

    private void loadFrame(){
        frame = new JFrame();
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setLayout(new BorderLayout());
        frame.setTitle("Serenade - Library");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        ImageIcon frameIcon = getImage();
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
        JButton library_btn = getButton("Library", DARK_COLOR, FRAME_COLOR);
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
        contentPanel.setBackground(FRAME_COLOR);
        contentPanel.setBorder(new EmptyBorder(20, 50, 20, 50));

        JPanel groupPanel = new JPanel(new BorderLayout());

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(FRAME_COLOR);
        headerPanel.setBorder(new EmptyBorder(0 , 0, 10, 0));

        JLabel title = new JLabel("Liked Songs.");
        title.setFont(FONT_MEDIUM.deriveFont(23f));

        JLabel userName = new JLabel("Kurosaki");
        userName.setFont(FONT_REGULAR.deriveFont(15f));
        headerPanel.add(title, BorderLayout.WEST);
        headerPanel.add(userName, BorderLayout.EAST);

        JPanel actionsPanel = new JPanel(null);
        actionsPanel.setBackground(FRAME_COLOR);
        actionsPanel.setPreferredSize(new Dimension(frame.getWidth(), 80));

        JTextField search_input = new JTextField("");
        search_input.setBounds(0, 10, frame.getWidth() - 385, 40);
        search_input.setFont(FONT_REGULAR.deriveFont(15f));
        search_input.setForeground(DARK_COLOR);
        search_input.setBackground(FRAME_COLOR);
        search_input.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(INPUT_COLOR),
                        BorderFactory.createEmptyBorder(5, 10, 5, 10)
                )
        );

        search_btn = getButton("Search", LIGHT_GRAY_COLOR, DARK_COLOR);
        search_btn.setBounds(425, 10, 130, 40);

        update_btn = getButton("Reset Search", DARK_COLOR, FRAME_COLOR);
        update_btn.setBounds(565, 10, 130, 40);

        actionsPanel.add(search_input);
        actionsPanel.add(search_btn);
        actionsPanel.add(update_btn);

        songList = new JPanel(new GridLayout(0, 1, 0, 0));
        getSongs();
        songList.setBackground(Color.decode("#f9f9f9"));
        songList.setBorder(new EmptyBorder(5, 10, 5, 10));

        JScrollPane songsListScrollPane = new JScrollPane(songList);
        songsListScrollPane.setBorder(null);

        groupPanel.add(headerPanel, BorderLayout.NORTH);
        groupPanel.add(actionsPanel, BorderLayout.SOUTH);
        contentPanel.add(groupPanel, BorderLayout.NORTH);
        contentPanel.add(songsListScrollPane, BorderLayout.CENTER);
        rootPanel.add(contentPanel, BorderLayout.CENTER);
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

    private ImageIcon getImage(){
        try{
            //@: Read the image file from the given path
            BufferedImage image = ImageIO.read(new File("src/assets/images/brand.png"));
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
                JLabel song = new JLabel(file);
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
        else if(e.getSource() == playlist_btn){
            new Playlist();
            frame.dispose();
        }
        else if(e.getSource() == search_btn){
            System.out.println("Filter songs based on search text");
        }
        else if(e.getSource() == update_btn){
            //@: get all selected songs and save to DB
            System.out.println("Save library to the database!");
        }
    }
}
