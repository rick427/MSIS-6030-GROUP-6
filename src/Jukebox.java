import components.CheckLibrary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Jukebox extends JFrame implements ActionListener {
    //@: Create the 4 buttons globally
    JButton checkLibButton, updateLibButton, createPlaylistButton, exitButton;

    Jukebox(){
        //******: INITIAL FRAME SETUP ******
        //@: Make the frame visible
        this.setVisible(true);

        //@: Define layout manager
        this.setLayout(new BorderLayout());

        //@: Adjust size of the frame on both x and y dimensions
        this.setSize(500, 130);

        //@: Add a title to the frame
        this.setTitle("Jukebox");

        //@: Adjust frame from being resized
        this.setResizable(false);

        //@: Dismiss frame from the program i.e exit application
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //@: Create and set a new image icon for the frame
        ImageIcon frameIcon = new ImageIcon("");
        this.setIconImage(frameIcon.getImage());

        //@: Change frame's background color
        this.getContentPane().setBackground(new Color(255, 255,255, 255));

        //******: DESCRIPTION MARKUP ******
        //@: Create a new panel for the description
        JPanel descriptionPanel = new JPanel();
        descriptionPanel.setLayout(new BorderLayout());
        descriptionPanel.setPreferredSize(new Dimension(100, 60));

        //@: Add the panel to the frame
        this.add(descriptionPanel, BorderLayout.NORTH);

        //@: Create a label text
        JLabel description = getDescription();

        //@: Add the label text to the panel
        descriptionPanel.add(description);

        //******: BUTTONS MARKUP ******
        //@: Create a panel to hold all buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        buttonPanel.setPreferredSize(new Dimension(100, 100));

        checkLibButton = new JButton("Check Library");
        checkLibButton.addActionListener(this);
        checkLibButton.setFocusable(false);

        updateLibButton = new JButton("Update Library");
        updateLibButton.addActionListener(this);
        updateLibButton.setFocusable(false);

        createPlaylistButton = new JButton("Create Playlist");
        createPlaylistButton.addActionListener(this);
        createPlaylistButton.setFocusable(false);

        exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> this.dispose());
        exitButton.setFocusable(false);

        buttonPanel.add(checkLibButton);
        buttonPanel.add(updateLibButton);
        buttonPanel.add(createPlaylistButton);
        buttonPanel.add(exitButton);
        this.add(buttonPanel);
    }

    //@: Encapsulate label text setup and styling into a static method for ease of use
    private static JLabel getDescription() {
        JLabel title = new JLabel("Select an option by clicking one of the buttons below");

        //@: Align title
        title.setHorizontalTextPosition(JLabel.CENTER);
        title.setVerticalTextPosition(JLabel.CENTER);

        title.setVerticalAlignment(JLabel.CENTER);
        title.setHorizontalAlignment(JLabel.CENTER);

        //@: Change font color / style
        title.setFont(new Font("Consolas", Font.BOLD, 13));
        return title;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == checkLibButton){
            //@: TODO: Implementation goes here
            new CheckLibrary();
        }
        else if (e.getSource() == updateLibButton){
            //@: TODO: Implementation goes here
            System.out.println("Update library button was clicked");
        }
        else if (e.getSource() == createPlaylistButton){
            //@: TODO: Implementation goes here
            System.out.println("Create playlist button was clicked");
        }
    }
}
