package frames;

import services.DbService;

import javax.swing.*;

public class App {
    public static void main(String[] args){
        //@: We wrap our app in a "invokeLater" method to ensure our GUI
        //@: is executed on the Event Dispatch Thread which helps deal with potential threading issues
        //@: whenever the GUI is updated
        SwingUtilities.invokeLater(Welcome::new);
    }
}
