package services;

import Models.UserData;
import TableStructure.SongTable;
import TableStructure.UserTable;

import java.sql.*;

public class DbService {

    public DbService() {}

    public void createData() {
        Connection connection = null;
        UserTable userTable = new UserTable();
        SongTable songTable = new SongTable();
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:jukebox.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

//            statement.executeUpdate("drop table if exists users");
//            statement.executeUpdate("drop table if exists songs");
//            statement.executeUpdate("drop table if exists playlists");

//            statement.execute("create table users (email string PRIMARY KEY, username string, phone_number string, password string)");
//            statement.executeUpdate("create table songs (id integer, name string, artist string, rating integer, play_count integer)");
//            statement.executeUpdate("create table playlists (id integer, song_id integer, name string)");
            String createUserTableSQL = "CREATE TABLE IF NOT EXISTS " + userTable.getTableName() +  " (\n"
                    + userTable.getEMAIL() + " TEXT PRIMARY kEY, \n"
                    + userTable.getUSERNAME() + " TEXT, \n"
                    + userTable.getPhoneNo() + " TEXT, \n"
                    + userTable.getPASSWORD() + " TEXT \n"
                    + ");";

            String createSongTableSQL = "CREATE TABLE IF NOT EXISTS " + songTable.getTABLE_NAME() +  " (\n"
                    + songTable.getSONG_ID() + " INTEGER PRIMARY KEY AUTOINCREMENT, \n"
                    + songTable.getSONG_NAME() + " TEXT, \n"
                    + songTable.getSONG_ARTIST() + " TEXT, \n"
                    + songTable.getSONG_RATING() + " INT, \n"
                    + songTable.getSONG_PLAY_COUNT() + " INT \n"
                    + ");";

            statement.execute(createUserTableSQL);
            statement.execute(createSongTableSQL);

//            statement.executeUpdate("insert into songs values(1, 'Number One (Bleach)', 'Shiro Sagisu (Topic)', 5,  0)");
//            statement.executeUpdate("insert into songs values(2, 'I Got Love', 'Don Diablo, Nate Dogg', 5,  0)");
//            statement.executeUpdate("insert into songs values(3, 'To The Wire (Album X)', 'Julian Jordan', 3,  0)");
//            statement.executeUpdate("insert into songs values(4, 'Things I Said (To The Wire)', 'Nu Aspect', 2,  0)");
//            statement.executeUpdate("insert into songs values(5, 'Clearing The Mind', 'Bear McCreary, Sparks & Shadow', 3,  0)");
//            statement.executeUpdate("insert into songs values(6, 'Face The Past, Face The Future', 'Bear McCreary, Sparks & Shadow', 5,  0)");
//            statement.executeUpdate("insert into songs values(7, 'Master Thyself', 'Bear McCreary, Sparks & Shadow', 4,  0)");
//            statement.executeUpdate("insert into songs values(8, 'Forgive Our Trespasses', 'Nandipha808, Ceeka RSA, DEMOLA', 1,  0)");
//            statement.executeUpdate("insert into songs values(9, 'Ohema', 'Victony, Crayon, Bella Shmurda', 2,  0)");
//            statement.executeUpdate("insert into songs values(10, 'Ayo Girl (Fayahh Beat)', 'Robinson, Jason Derulo, Rema', 4,  0)");
//            statement.executeUpdate("insert into songs values(11, 'Unbreakable (with Sam Grey)', 'TELYKAST, Sam Gray', 5,  0)");
//            statement.executeUpdate("insert into songs values(12, 'Happiness (feat. Asake & Gunna)', 'Sarz, Asake, Gunna.', 5,  0)");
//            statement.executeUpdate("insert into songs values(13, 'Father Stretch My Hands Pt. 1', 'Kanye West', 5,  0)");

//            ResultSet rs = statement.executeQuery("select * from users");
//            while(rs.next()) {
//                // read the result set
//                System.out.println("username = " + rs.getString("username"));
//                System.out.println("password = " + rs.getString("password"));
//                //System.out.println("id = " + rs.getInt("id"));
//            }
            System.out.println("Query worked!!");
        }

        catch(SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }

        finally {
            try {
                if(connection != null) connection.close();
            }
            catch(SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
    }

    //Comment

    public void getAllSongs() {
        Connection connection = null;
        try{
            connection = DriverManager.getConnection("jdbc:sqlite:jukebox.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            ResultSet rs = statement.executeQuery("select * from songs");
            while(rs.next()){
                System.out.println(rs.getString("name"));
            }

        } catch(SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
    }

//    public void createUser(UserData userData){
//        Connection connection = null;
//        try{
//            connection = DriverManager.getConnection("jdbc:sqlite:jukebox.db");
//            Statement statement = connection.createStatement();
//            statement.setQueryTimeout(30);
//
//            ResultSet rs = statement.executeQuery("insert");
//            while(rs.next()){
//                System.out.println(rs.getString("name"));
//            }
//
//        } catch(SQLException e) {
//            // if the error message is "out of memory",
//            // it probably means no database file is found
//            System.err.println(e.getMessage());
//        }
//    }

    public boolean login(String email, String password){
        Connection connection = null;

        int rowCount = 0;
        try{
            connection = DriverManager.getConnection("jdbc:sqlite:jukebox.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            PreparedStatement myStmt = connection.prepareStatement("SELECT * FROM USERS WHERE EMAIL = ? AND PASSWORD = ?");
            myStmt.setString(1,email);
            myStmt.setString(2,password);

            ResultSet resultSet = myStmt.executeQuery();

            while (resultSet.next()) {
                rowCount ++;
            }


        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return rowCount != 0;
    }

    public boolean signup(UserData userData) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:jukebox.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // Hash the password before storing
//            String hashedPassword = hashPassword(password);

            PreparedStatement myStmt = connection.prepareStatement(
                    "INSERT INTO USERS (EMAIL, USERNAME, PHONE_NUMBER, PASSWORD) VALUES (?, ?, ?, ?)");
            myStmt.setString(1, userData.getEmail());
//            myStmt.setString(2, hashedPassword);
            myStmt.setString(2, userData.getUsername());
            myStmt.setString(3, userData.getPhone_number());
            myStmt.setString(4, userData.getPassword());

            int rowsAffected = myStmt.executeUpdate();

            return rowsAffected == 1; // Indicates successful insertion

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false; // Indicate signup failure
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    // Handle closing errors
                }
            }
        }
    }
}
