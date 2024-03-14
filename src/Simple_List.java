import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class Simple_List {
    public static void main(String[] args) {

        // Create a frame
        JFrame frame = new JFrame("Simple List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a list of fruits
        String[] fruits = {"Apple", "Banana", "Orange", "Mango", "Pineapple", "Grapefruit",
                "Watermelon", "Kiwi", "Strawberry", "Blueberry", "Blackberry",
                "Cherry", "Peach", "Nectarine", "Plum", "Apricot", "Fig", "Date"};

        JList<String> fruitList = getStringJList(fruits);

        // Wrap the list in a scroll pane for large datasets
        JScrollPane scrollPane = new JScrollPane(fruitList);

        // Add the scroll pane to the frame
        frame.getContentPane().add(scrollPane);

        // Set the frame size and make it visible
        frame.setSize(300, 150);
        frame.setVisible(true);
    }

    private static JList<String> getStringJList(String[] fruits) {
        JList<String> fruitList = new JList<>(fruits);

        fruitList.setBackground(new Color(204,255,204));
        fruitList.setForeground(new Color(204,0,51));
        fruitList.setSelectionBackground(new Color(255,204,204));
        fruitList.setSelectionForeground(new Color(0,0,255));

        fruitList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) { // Ignore selection changes during adjustment
                    int selectedIndex = fruitList.getSelectedIndex();
                    if (selectedIndex != -1 && fruitList.getModel().getElementAt(selectedIndex).equals(fruits[selectedIndex])) {
                        // User selected "Orange", perform an action
                        System.out.println(fruits[selectedIndex] + " was selected!!");
                    }
                }
            }
        });
        return fruitList;
    }
}