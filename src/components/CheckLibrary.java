package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CheckLibrary extends JFrame implements ActionListener {
    JButton checkTrackButton, listAllTracksButton;

    public CheckLibrary(){
        //******: INITIAL FRAME SETUP ******
        this.setVisible(true);
        this.setSize(500, 200);
        this.setResizable(false);
        this.setTitle("Check Library");
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        ImageIcon frameIcon = new ImageIcon("");
        this.setIconImage(frameIcon.getImage());

        this.getContentPane().setBackground(new Color(255, 255,255, 255));

        //******: ACTIONS MENU ******
        //@: Create a border layout panel to contain a flow layout
        JPanel actionsGroup = new JPanel();
        actionsGroup.setLayout(new BorderLayout());
        actionsGroup.setPreferredSize(new Dimension(400, 50));

        //@: Create a flow layout panel to contain other components
        JPanel actionsRow = new JPanel();
        actionsRow.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        //@: Create components
        JLabel label = new JLabel("Enter Track Number:");
        label.setFont(new Font("Consolas", Font.BOLD, 13));

        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(45, 30));

        checkTrackButton = new JButton("Check Track");
        checkTrackButton.addActionListener(this);
        checkTrackButton.setFocusable(false);

        listAllTracksButton = new JButton("List All Tracks");
        listAllTracksButton.addActionListener(this);
        listAllTracksButton.setFocusable(false);

        actionsRow.add(label);
        actionsRow.add(textField);
        actionsRow.add(checkTrackButton);
        actionsRow.add(listAllTracksButton);
        actionsGroup.add(actionsRow);
        this.add(actionsGroup, BorderLayout.NORTH);

        //******: TRACKS LIST ******
        JPanel tracksListPanel = new JPanel();
        tracksListPanel.setLayout(new BorderLayout());
        tracksListPanel.setPreferredSize(new Dimension(300, 200));

        JTextArea tracksList = new JTextArea();
        tracksList.setSize(200, 200);
        tracksList.setBackground(Color.red);

        tracksListPanel.add(tracksList);
        tracksListPanel.setBackground(Color.lightGray);
        this.add(tracksListPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == checkTrackButton){
            //@: TODO: Implementation goes here
            System.out.println("Check track button was clicked");
        }
        else if (e.getSource() == listAllTracksButton){
            //@: TODO: Implementation goes here
            System.out.println("List all tracks button was clicked");
        }
    }
}
