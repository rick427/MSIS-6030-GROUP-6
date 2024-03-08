package components;

import Models.UserData;
import services.DbService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.border.EmptyBorder;

public class Register implements ActionListener {
    JFrame frame;
    JButton submitButton;
    String primary_dark = "#141414";
    String textField_border_color = "#ced4da";
    Font font_bold, font_medium, font_regular;

    Register(){
        //@: Initial Setup
        frame = new JFrame();
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setLayout(new BorderLayout());
        frame.setTitle("Register - Serenade");
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
        JTextField username = new JTextField("Your Username");
        JTextField email = new JTextField("Your Email");
        JTextField phoneNumber = new JTextField("Your Phone Number");
        JPasswordField password = new JPasswordField("Your Password");
        submitButton = new JButton("Create Account");

        username.setBounds(125, 40, 350, 50);
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
                if(username.getText().equals("Your Username")){
                    username.setText("");
                    username.setForeground(Color.decode(primary_dark));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(username.getText().isEmpty()){
                    username.setForeground(Color.lightGray);
                    username.setText("Your Username");
                }
            }
        });

        email.setBounds(125, 110, 350, 50);
        email.setFont(font_regular.deriveFont(15f));
        email.setForeground(Color.decode(primary_dark));
        email.setBackground(Color.white);
        email.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.decode(textField_border_color)),
                        BorderFactory.createEmptyBorder(5, 20, 5, 20)
                )
        );
        email.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(email.getText().equals("Your Email")){
                    email.setText("");
                    email.setForeground(Color.decode(primary_dark));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(email.getText().isEmpty()){
                    email.setForeground(Color.lightGray);
                    email.setText("Your Email");
                }
            }
        });

        phoneNumber.setBounds(125, 180, 350, 50);
        phoneNumber.setFont(font_regular.deriveFont(15f));
        phoneNumber.setForeground(Color.decode(primary_dark));
        phoneNumber.setBackground(Color.white);
        phoneNumber.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.decode(textField_border_color)),
                        BorderFactory.createEmptyBorder(5, 20, 5, 20)
                )
        );
        phoneNumber.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(phoneNumber.getText().equals("Your Phone Number")){
                    phoneNumber.setText("");
                    phoneNumber.setForeground(Color.decode(primary_dark));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(phoneNumber.getText().isEmpty()){
                    phoneNumber.setForeground(Color.lightGray);
                    phoneNumber.setText("Your Phone Number");
                }
            }
        });

        password.setBounds(125, 250, 350, 50);
        password.setFont(font_regular.deriveFont(15f));
        password.setForeground(Color.decode(primary_dark));
        password.setBackground(Color.white);
        password.setEchoChar('*');
        password.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.decode(textField_border_color)),
                        BorderFactory.createEmptyBorder(5, 20, 5, 20)
                )
        );

        submitButton.setBounds(125, 350, 350, 50);
        submitButton.setFocusable(false);
        submitButton.setOpaque(true);
        submitButton.setBorderPainted(false);
        submitButton.setForeground(Color.white);
        submitButton.setBackground(Color.decode(primary_dark));
        submitButton.setFont(font_medium.deriveFont(15f));
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserData userData = new UserData();
                userData.setEmail(email.getText().toString());
                userData.setUsername(username.getText().toString());
                userData.setPhone_number(phoneNumber.getText().toString());
                userData.setPassword(password.getText().toString());
                DbService dbService = new DbService();
                boolean answer = dbService.signup(userData);
                if (answer){
                    System.out.println("Worked");
                    frame.dispose();
                }
                else {
                    System.out.println("Failed");
                }
            }
        });

        //@: Add Components to Form
        form.add(username);
        form.add(email);
        form.add(phoneNumber);
        form.add(password);
        form.add(submitButton);
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

        JLabel title = new JLabel("Get Started Today!");
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
        if(e.getSource() == submitButton){
//            frame.dispose();
        }
    }
}
