package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.MatteBorder;

public class CheckLibrary extends JFrame implements ActionListener {
    JTextField trackNumField;
    JButton checkTrackButton, listAllTracksButton;

    public CheckLibrary(){
        //******: INITIAL FRAME SETUP ******
        this.setVisible(true);
        this.setSize(500, 200);
        this.setLocationRelativeTo(null);
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
        actionsRow.setBackground(new Color(255, 255,255, 255));

        //@: Create components
        JLabel label = new JLabel("Enter Track Number:");
        label.setFont(new Font("Consolas", Font.BOLD, 13));

        trackNumField = new JTextField();
        trackNumField.setFocusable(true);
        trackNumField.setPreferredSize(new Dimension(45, 30));

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
        JTextArea textArea = getTextArea();

        JScrollPane scrollableTextArea = new JScrollPane(textArea);
        scrollableTextArea.setBorder(new MatteBorder(1, 0, 0, 0, Color.lightGray));
        scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        this.add(scrollableTextArea);
    }

    //@: Create tracks list
    private static JTextArea getTextArea (){
        JTextArea textArea = new JTextArea(20, 20);

        textArea.setEnabled(false);
        textArea.setFont(new Font("Consolas", Font.PLAIN, 13));
        textArea.setWrapStyleWord(true);
        textArea.setMargin(new Insets(10, 10, 10, 10));

        textArea.append("01 How much is that doggy in the window - Zee-J \n");
        textArea.append("02 Exotic - Maradonna \n");
        textArea.append("03 I'm dreaming of a white Christmas - Ludwig van Beethoven \n");
        textArea.append("04 Pastoral Symphony - Cayley Minnow \n");
        textArea.append("05 Anarchy in the UK - The Kings Singers \n");

        return textArea;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean isTrackEmptyOrBlank = trackNumField.getText().isEmpty() || trackNumField.getText().isBlank();
        if(e.getSource() == checkTrackButton){
            if(isTrackEmptyOrBlank){
                JOptionPane.showMessageDialog(null, "A track number is required to check tracks.\nKindly provide one to perform this operation.", "Invalid Operation", JOptionPane.WARNING_MESSAGE);
            }
            else {
                System.out.println("Check track button was clicked");
            }
        }
        else if (e.getSource() == listAllTracksButton){
            //@: TODO: Implementation goes here
            System.out.println("List all tracks button was clicked");
        }
    }
}
