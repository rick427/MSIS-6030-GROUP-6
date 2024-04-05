package frames;

import services.DbService;

import java.awt.*;
import java.io.File;
import javax.swing.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;

public class Welcome implements ActionListener {
    //@: Global vars
    JFrame frame;
    JButton getStartedBtn;
    Font FONT_BOLD, FONT_REGULAR;
    public static final Color DARK_COLOR = Color.decode("#141414");
    public static final Color FRAME_COLOR = Color.white;

    public Welcome(){
        //@: Load frame
        loadFrame();

        //@: Create panel
        JPanel container = new JPanel(null);
        frame.add(container);

        //@: Add brand information
        JLabel title = new JLabel("Serenade.");
        ImageIcon icon = getImage();
        ImageIcon brandIcon = new ImageIcon(icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        title.setIcon(brandIcon);
        title.setIconTextGap(15);
        title.setForeground(DARK_COLOR);
        title.setFont(FONT_BOLD.deriveFont(30f));
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setVerticalAlignment(JLabel.CENTER);
        title.setHorizontalTextPosition(JLabel.CENTER);
        title.setVerticalTextPosition(JLabel.BOTTOM);
        title.setBounds(0, 30, frame.getWidth(), 200);
        container.add(title);

        //@: Add Button
        getStartedBtn = new JButton("Get Started Now");
        getStartedBtn.setFocusable(false);
        getStartedBtn.setOpaque(true);
        getStartedBtn.setBorderPainted(false);
        getStartedBtn.setForeground(Color.white);
        getStartedBtn.setBackground(DARK_COLOR);
        getStartedBtn.setFont(FONT_REGULAR.deriveFont(15f));
        //getStartedBtn.setPreferredSize(new Dimension(200, 50));
        getStartedBtn.addActionListener(this);
        getStartedBtn.setBounds(122, 250, frame.getWidth() / 2, 50);
        container.add(getStartedBtn);
    }

    private void loadFrame(){
        frame = new JFrame();
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setLayout(new BorderLayout());
        frame.setTitle("Serenade");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        ImageIcon frameIcon = getImage();
        frame.setIconImage(frameIcon.getImage());
        frame.getContentPane().setBackground(FRAME_COLOR);

        //@: Load required custom fonts
        getFonts();
    }

    private void getFonts(){
        //@: Setup Custom Font
        //@: Reference - https://stackoverflow.com/questions/5652344/how-can-i-use-a-custom-font-in-java
        try {
            FONT_REGULAR = Font.createFont(Font.TRUETYPE_FONT, new File("src/assets/fonts/Rubik-Regular.ttf"));
            FONT_BOLD = Font.createFont(Font.TRUETYPE_FONT, new File("src/assets/fonts/Rubik-Bold.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(FONT_REGULAR);
            ge.registerFont(FONT_BOLD);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == getStartedBtn){
            DbService dbService = new DbService();
            dbService.connect();
            frame.dispose();
            new SignIn();
        }
    }
}
