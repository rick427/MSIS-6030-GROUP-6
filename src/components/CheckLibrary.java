package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.border.MatteBorder;

//@: Utils
import utils.LibraryData;

public class CheckLibrary extends JFrame implements ActionListener {
    JTextField trackNumField;
    JTextArea trackListArea;
    JButton checkTrackButton, listAllTracksButton;
    String allTracks = LibraryData.listAll();

    public CheckLibrary(){
        //******: INITIAL FRAME SETUP ******
        this.setVisible(true);
        this.setSize(500, 250);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("Check Library");
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        ImageIcon frameIcon = new ImageIcon("logo.jpeg");
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
        actionsRow.setBackground(new Color(255, 255,255, 255));

        //@: Create components
        JLabel label = new JLabel("Enter Track Number:");
        label.setFont(new Font("Consolas", Font.BOLD, 13));

        trackNumField = new JTextField();
        trackNumField.setFocusable(true);
        trackNumField.setPreferredSize(new Dimension(45, 30));
        //@: Make sure the track number field only accepts numbers
        trackNumField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char currChar = e.getKeyChar();
               if(!(Character.isDigit(currChar)) || (currChar == KeyEvent.VK_BACK_SPACE) || (currChar == KeyEvent.VK_DELETE)){
                   e.consume();
               }
            }
        });

        checkTrackButton = new JButton("Check Track");
        checkTrackButton.addActionListener(this);
        checkTrackButton.setFocusable(false);

        listAllTracksButton = new JButton("List All Tracks");
        listAllTracksButton.addActionListener(this);
        listAllTracksButton.setFocusable(false);

        actionsRow.add(label);
        actionsRow.add(trackNumField);
        actionsRow.add(checkTrackButton);
        actionsRow.add(listAllTracksButton);
        actionsGroup.add(actionsRow);
        this.add(actionsGroup, BorderLayout.NORTH);

        //******: TRACKS LIST ******
        JTextArea tracksList = getTracksList();

        JScrollPane scrollableTextArea = new JScrollPane(tracksList);
        scrollableTextArea.setBorder(new MatteBorder(1, 0, 0, 0, Color.lightGray));
        scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        this.add(scrollableTextArea);
    }

    //@: Create track list
    private JTextArea getTracksList (){
        trackListArea = new JTextArea(20, 20);

        trackListArea.setEnabled(false);
        trackListArea.setFont(new Font("Consolas", Font.PLAIN, 13));
        trackListArea.setWrapStyleWord(true);
        trackListArea.setMargin(new Insets(10, 10, 10, 10));

        trackListArea.selectAll();
        trackListArea.replaceSelection(allTracks);

        return trackListArea;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String trackIndex = trackNumField.getText();
        boolean isTrackEmptyOrBlank = trackIndex.isEmpty() || trackIndex.isBlank();
        if(e.getSource() == checkTrackButton){
            if(isTrackEmptyOrBlank){
                JOptionPane.showMessageDialog(null, "A track number is required to check tracks.\nKindly provide one to perform this operation.", "Track Number Required", JOptionPane.WARNING_MESSAGE);
            }
            else {
                int sanitizedIndex = Integer.parseInt(trackIndex);
                String searchIndex = sanitizedIndex <= 9 ? "0"+sanitizedIndex : Integer.toString(sanitizedIndex);
                System.out.println(searchIndex);
                String trackDetail = LibraryData.getName(searchIndex) +" by "+ LibraryData.getArtist(searchIndex) +" - "+ LibraryData.getRating(searchIndex)+" stars.";
                trackListArea.setEnabled(true);
                trackListArea.selectAll();
                trackListArea.replaceSelection(trackDetail);
                trackListArea.setEnabled(false);
            }
        }
        else if (e.getSource() == listAllTracksButton){
            //@: Reset tracks list with the original list
            trackListArea.setEnabled(true);
            trackListArea.selectAll();
            trackListArea.replaceSelection(allTracks);
            trackListArea.setEnabled(false);
            //@: Reset track number field
            trackNumField.setText("");
        }
    }
}
