import components.Welcome;
import services.DbService;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        //@: Load the Database
        DbService dbService = new DbService();
        dbService.createData();

        EventQueue.invokeLater(Welcome::new);
    }


}