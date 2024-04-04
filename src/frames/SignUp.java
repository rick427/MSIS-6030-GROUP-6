package frames;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

public class SignUp {
    private JFrame frame;
    private final JPanel rootPanel;
    private Font FONT_MEDIUM, FONT_REGULAR;
    public static final Color DARK_COLOR = Color.decode("#141414");
    public static final Color FRAME_COLOR = Color.white;
    public static final Color LIGHT_GRAY_COLOR = Color.decode("#f1f1f1");
    public static final Color INPUT_COLOR = Color.decode("#c9c9c9");

    SignUp(){
        //@: Load frame
        loadFrame();

        //@: Create parent panel to hold other panels and their respective components
        rootPanel = new JPanel(new BorderLayout());
        rootPanel.setBackground(Color.white);
        frame.add(rootPanel);

        //@: Load Form Header
        loadHeader();

        //@: Load Form
        loadForm();
    }

    private void loadFrame(){
        frame = new JFrame();
        frame.setSize(600, 650);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setLayout(new BorderLayout());
        frame.setTitle("Serenade - Register");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        ImageIcon frameIcon = getImage();
        frame.setIconImage(frameIcon.getImage());
        frame.getContentPane().setBackground(FRAME_COLOR);

        //@: Load required custom fonts
        getFonts();

        //@: Change the default font of the dialog
        UIManager.put("OptionPane.messageFont", FONT_REGULAR.deriveFont(13f));
        UIManager.put("OptionPane.buttonFont", FONT_REGULAR.deriveFont(14f));
    }

    private void loadHeader(){
        JPanel stack = new JPanel(new BorderLayout());
        stack.setBackground(Color.decode("#f5f5f5"));
        stack.setBorder(new EmptyBorder(40, 20, 40, 20));

        JLabel title = new JLabel("Get an Account.");
        title.setForeground(DARK_COLOR);
        title.setFont(FONT_MEDIUM.deriveFont(28f));
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setVerticalAlignment(JLabel.CENTER);

        //@: Add Components to Panel (i.e stack)
        stack.add(title, BorderLayout.NORTH);
        rootPanel.add(stack, BorderLayout.NORTH);
    }

    private void loadForm(){
        JPanel form = new JPanel(null);

        JTextField fullName = new JTextField("");
        JTextField username = new JTextField("");
        JTextField phoneNumber = new JTextField("");
        JPasswordField password = new JPasswordField("");

        JButton register_btn = getButton("Register", DARK_COLOR, FRAME_COLOR);
        JButton back_btn = getButton("Cancel Registration", LIGHT_GRAY_COLOR, DARK_COLOR);

        fullName.setBounds(125, 40, 350, 50);
        fullName.setFont(FONT_REGULAR.deriveFont(15f));
        fullName.setForeground(DARK_COLOR);
        fullName.setBackground(Color.white);
        fullName.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(INPUT_COLOR),
                        BorderFactory.createEmptyBorder(5, 20, 5, 20)
                )
        );
        fullName.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(fullName.getText().equals("Full Name")){
                    fullName.setText("");
                    fullName.setForeground(INPUT_COLOR);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(fullName.getText().isEmpty()){
                    fullName.setText("Full Name");
                    fullName.setForeground(INPUT_COLOR);
                }
            }
        });

        username.setBounds(125, 110, 350, 50);
        username.setFont(FONT_REGULAR.deriveFont(15f));
        username.setForeground(DARK_COLOR);
        username.setBackground(Color.white);
        username.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(INPUT_COLOR),
                        BorderFactory.createEmptyBorder(5, 20, 5, 20)
                )
        );
        username.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(username.getText().equals("Email Address")){
                    username.setText("");
                    username.setForeground(INPUT_COLOR);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(username.getText().isEmpty()){
                    username.setText("Email Address");
                    username.setForeground(INPUT_COLOR);
                }
            }
        });

        phoneNumber.setBounds(125, 180, 350, 50);
        phoneNumber.setFont(FONT_REGULAR.deriveFont(15f));
        phoneNumber.setForeground(DARK_COLOR);
        phoneNumber.setBackground(Color.white);
        phoneNumber.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(INPUT_COLOR),
                        BorderFactory.createEmptyBorder(5, 20, 5, 20)
                )
        );
        phoneNumber.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(phoneNumber.getText().equals("Phone Number")){
                    phoneNumber.setText("");
                    phoneNumber.setForeground(INPUT_COLOR);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(phoneNumber.getText().isEmpty()){
                    phoneNumber.setForeground(INPUT_COLOR);
                    phoneNumber.setText("Phone Number");
                }
            }
        });

        password.setBounds(125, 250, 350, 50);
        password.setFont(FONT_REGULAR.deriveFont(15f));
        password.setForeground(DARK_COLOR);
        password.setEchoChar('*');
        password.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(INPUT_COLOR),
                        BorderFactory.createEmptyBorder(5, 20, 5, 20)
                )
        );

        register_btn.setBounds(125, 350, 350, 50);
        register_btn.addActionListener(e -> {
            if(username.getText().isEmpty() || fullName.getText().isEmpty() || phoneNumber.getText().isEmpty() || password.getPassword().length == 0){
                JOptionPane.showMessageDialog(
                        frame,
                        "All registration fields are required.\nPlease check your inputs and try again.",
                        "Missing Field(s)!",
                        JOptionPane.ERROR_MESSAGE
                );
            }
            else if(!isValid(username.getText())){
                JOptionPane.showMessageDialog(
                        frame,
                        "Invalid email address detected.\nPlease check your email input and try again.",
                        "Validation Error!",
                        JOptionPane.ERROR_MESSAGE
                );
            }
            else{
                //@: DB Logic Here
                frame.dispose();
                new SignIn();
            }
        });

        back_btn.setBounds(125, 415, 350, 50);
        back_btn.addActionListener(e -> {
            frame.dispose();
            new SignIn();
        });

        form.add(fullName);
        form.add(username);
        form.add(phoneNumber);
        form.add(password);
        form.add(back_btn);
        form.add(register_btn);
        form.setBackground(Color.white);

        //@: Add Components to Frame
        rootPanel.add(form);
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
        button.setFont(FONT_REGULAR.deriveFont(15f));
        button.setPreferredSize(new Dimension(100, 35));
        return button;
    }

    private static boolean isValid (String email){
        String emailFormat = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\." + "[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
        Pattern p = Pattern.compile(emailFormat);
        if (email == null || email.isEmpty()) {
            return false;
        }
        return p.matcher(email).matches();
    }
}