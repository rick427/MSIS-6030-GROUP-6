package components;

import utils.LibraryData;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class CreatePlaylist extends JFrame implements ActionListener {
    JButton addTrack, playPlaylist, resetPlaylist;
    JTextField trackNumField;
    JTextArea trackListArea;
    String allTracks = LibraryData.listPlaylist();

    public CreatePlaylist(){
        this.setVisible(true);
        this.setSize(600, 200);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("Create Playlist");
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.getContentPane().setBackground(new Color(255, 255,255, 255));


        //******: ACTIONS MENU ******
        //@: Create a border layout panel to contain a flow layout
        JPanel actionsGroup = new JPanel();
        actionsGroup.setLayout(new BorderLayout());
        actionsGroup.setPreferredSize(new Dimension(600, 50));

        //@: Create a flow layout panel to contain other components
        JPanel actionsRow = new JPanel();
        actionsRow.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
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

        addTrack = new JButton("Add Track");
        addTrack.addActionListener(this);
        addTrack.setFocusable(false);

        playPlaylist = new JButton("Play playlist");
        playPlaylist.addActionListener(this);
        playPlaylist.setFocusable(false);

        resetPlaylist = new JButton("Reset playlist");
        resetPlaylist.addActionListener(this);
        resetPlaylist.setFocusable(false);

        actionsRow.add(label);
        actionsRow.add(trackNumField);
        actionsRow.add(addTrack);
        actionsRow.add(playPlaylist);
        actionsRow.add(resetPlaylist);
        actionsGroup.add(actionsRow, BorderLayout.NORTH);
        this.add(actionsGroup);

        //******: TRACKS LIST ******
        JTextArea tracksList = getTracksList();

        JScrollPane scrollableTextArea = new JScrollPane(tracksList);
        scrollableTextArea.setBorder(new MatteBorder(1, 0, 0, 0, Color.lightGray));
        scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        this.add(scrollableTextArea);
    }

    private JTextArea getTracksList (){
        trackListArea = new JTextArea();

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
        boolean isTrackEmptyOrBlank = trackNumField.getText().isEmpty() || trackNumField.getText().isBlank();
        if(e.getSource() == addTrack){
            System.out.println(trackNumField.getText());
            if(isTrackEmptyOrBlank){
                JOptionPane.showMessageDialog(null, "A track number is required in order to add tracks to the playlist.\nKindly provide one to perform this operation.", "Invalid Operation", JOptionPane.WARNING_MESSAGE);
            }
            else{
                int sanitizedIndex = Integer.parseInt(trackNumField.getText());
                String searchIndex = sanitizedIndex <= 9 ? "0"+sanitizedIndex : Integer.toString(sanitizedIndex);
                boolean success = LibraryData.addTrack(searchIndex);
                if (success){
                    JOptionPane.showMessageDialog(null, "Track added to playlist successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
                else{
                    JOptionPane.showMessageDialog(null, "Error adding track to playlist", "Error Adding",JOptionPane.WARNING_MESSAGE);
                }
            }
        }

        else if (e.getSource() == playPlaylist) {
            System.out.println("Play playlist button was clicked");
            String playedInformation = LibraryData.playPlaylist();
            JOptionPane.showMessageDialog(null, playedInformation, "Songs Played", JOptionPane.INFORMATION_MESSAGE);
        }

        else if (e.getSource() == resetPlaylist) {
//            System.out.println("Reset playlist button was clicked");
//            LibraryData.listPlaylist();
            LibraryData.playPlaylist();
        }

    }
}
