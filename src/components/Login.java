package components;

import services.DbService;
import services.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.border.EmptyBorder;
import java.util.Arrays;
import java.util.List;

public class Login implements ActionListener {
    JFrame frame;
    JButton submitButton, registerButton;
    String primary_dark = "#141414";
    String textField_border_color = "#ced4da";
    Font font_bold, font_medium, font_regular;

    Login(){
        //@: Initial Setup
        frame = new JFrame();
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setLayout(new BorderLayout());
        frame.setTitle("Login - Serenade");
        frame.setResizable(false);
        frame.requestFocusInWindow();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

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

        //@: Header Section
        JPanel header = getHeader();

        //@: Form Section
        JPanel form = new JPanel(null);
        JTextField username = new JTextField("Email or Username");
        JPasswordField password = new JPasswordField("Password");
        submitButton = new JButton("Login");
        registerButton = new JButton("Register");

        username.setBounds(125, 30, 350, 50);
        username.setFont(font_regular.deriveFont(15f));
        username.setForeground(Color.decode(primary_dark));
        username.setBackground(Color.white);
        username.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.decode(textField_border_color)),
                        BorderFactory.createEmptyBorder(5, 20, 5, 20)
                )
        );
        username.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(username.getText().equals("Email or Username")){
                    username.setText("");
                    username.setForeground(Color.decode(primary_dark));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(username.getText().isEmpty()){
                    username.setForeground(Color.lightGray);
                    username.setText("Email or Username");
                }
            }
        });

        password.setBounds(125, 100, 350, 50);
        password.setFont(font_regular.deriveFont(15f));
        password.setForeground(Color.decode(primary_dark));
        password.setEchoChar('*');
        password.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.decode(textField_border_color)),
                        BorderFactory.createEmptyBorder(5, 20, 5, 20)
                )
        );

        submitButton.setBounds(125, 210, 350, 50);
        submitButton.setFocusable(false);
        submitButton.setOpaque(true);
        submitButton.setBorderPainted(false);
        submitButton.setForeground(Color.white);
        submitButton.setBackground(Color.decode(primary_dark));
        submitButton.setFont(font_medium.deriveFont(15f));
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DbService dbService = new DbService();
                boolean answer = dbService.login(username.getText().toString(), password.getText().toString());
                if (answer){
                    System.out.println("Worked");
                    User.username = dbService.getUsername(username.getText());
                    User.email = username.getText();
//                    String[] fruits = {"Apple", "Banana", "Orange", "Mango", "Pineapple", "Grapefruit",
//                            "Watermelon", "Kiwi", "Strawberry", "Blueberry", "Blackberry",
//                            "Cherry", "Peach", "Nectarine", "Plum", "Apricot", "Fig", "Date"};
//                    boolean tester = dbService.createPlaylist("Test",User.email, fruits);
//                    String[] tester = dbService.getPlaylist("ID1", User.email);
//                    System.out.println(Arrays.toString(tester));
                    frame.dispose();
                    new Jukebox();
                }
                else {
                    System.out.println("Failed");
                }
            }
        });

        registerButton.setBounds(125, 275, 350, 50);
        registerButton.setFocusable(false);
        registerButton.setOpaque(true);
        registerButton.setBorderPainted(false);
        registerButton.setForeground(Color.decode(primary_dark));
        registerButton.setBackground(Color.decode("#eeeeee"));
        registerButton.setFont(font_medium.deriveFont(15f));
        registerButton.addActionListener(this);

        form.add(username);
        form.add(password);
        form.add(submitButton);
        form.add(registerButton);
        form.setBackground(Color.white);

        //@: Add Components to Frame
        frame.add(header, BorderLayout.NORTH);
        frame.add(form);
    }

    private JPanel getHeader() {
        int stackPadding = 40;
        JPanel stack = new JPanel(new BorderLayout());
        stack.setBackground(Color.decode("#f5f5f5"));
        stack.setBorder(new EmptyBorder(stackPadding, 20, stackPadding, 20));

        JLabel title = new JLabel("Login to Continue.");
        title.setForeground(Color.decode(primary_dark));
        title.setFont(font_bold.deriveFont(28f));
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setVerticalAlignment(JLabel.CENTER);

        //@: Add Components to Panel (i.e stack)
        stack.add(title, BorderLayout.NORTH);

        return stack;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        if(e.getSource() == submitButton){
//            frame.dispose();
//            new Jukebox();
//
//        }
        if(e.getSource() == registerButton){
            new Register();
        }
    }
}