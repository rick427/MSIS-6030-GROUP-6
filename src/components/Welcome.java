package components;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;

public class Welcome implements ActionListener {
    JFrame frame;
    JButton startButton;
    String primary_dark = "#141414";
    Font font_bold, font_medium, font_regular;
    public Welcome(){
        //@: Initial Setup
        frame = new JFrame();
        frame.setSize(600, 380);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setLayout(new BorderLayout());
        frame.setTitle("Serenade");
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
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font_bold);
            ge.registerFont(font_regular);
            ge.registerFont(font_medium);
        } catch (IOException | FontFormatException e) {
            System.out.println(e.getMessage());
        }

        //@: Landing Screen
        //@: Setup Header Section
        JPanel header = getHeader();

        //@: Content Section
        JPanel content = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weighty = 1;
        content.setBackground(Color.white);

        //@: Get Started Button
        startButton = new JButton("Get Started Now!");
        startButton.setFocusable(false);
        startButton.setOpaque(true);
        startButton.setBorderPainted(false);
        startButton.setForeground(Color.white);
        startButton.setBackground(Color.decode(primary_dark));
        startButton.setFont(font_medium.deriveFont(15f));
        startButton.setPreferredSize(new Dimension(200, 50));
        startButton.addActionListener(this);

        content.add(startButton, gbc);

        //@: Add Components to Frame
        frame.add(header, BorderLayout.NORTH);
        frame.add(content);
    }

    private JPanel getHeader() {
        int stackPadding = 40;
        JPanel stack = new JPanel(new BorderLayout(0, 10));
        stack.setBackground(Color.decode("#fafafa"));
        stack.setBorder(new EmptyBorder(stackPadding, 20, stackPadding, 20));

        JLabel title = new JLabel("Music for Everyone!");
        ImageIcon icon = new ImageIcon("assets/logo-2.png");
        ImageIcon iconImage = new ImageIcon(icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        title.setIcon(iconImage);
        title.setIconTextGap(10);
        title.setForeground(Color.decode(primary_dark));
        title.setFont(font_bold.deriveFont(30f));
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setVerticalAlignment(JLabel.CENTER);
        title.setHorizontalTextPosition(JLabel.CENTER);
        title.setVerticalTextPosition(JLabel.BOTTOM);

        JLabel subtitle = new JLabel("Unleash your inner groove with Serenade.");
        subtitle.setForeground(Color.decode(primary_dark));
        subtitle.setFont(font_regular.deriveFont(15f));
        subtitle.setHorizontalAlignment(JLabel.CENTER);
        subtitle.setVerticalAlignment(JLabel.CENTER);
        subtitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        //@: Add Components to Panel (i.e stack)
        stack.add(title, BorderLayout.NORTH);
        stack.add(subtitle, BorderLayout.SOUTH);

        return stack;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == startButton){
            frame.dispose();
            new Login();
//            new Jukebox();

        }
    }
}