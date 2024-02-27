package services;

import java.sql.*;

public class DbService {

    public DbService() {

    }

    public  void createData() {
        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:jukebox.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate("drop table if exists users");
            statement.executeUpdate("drop table if exists songs");
            statement.executeUpdate("drop table if exists playlists");

            statement.executeUpdate("create table users (id integer, username string, email string, phone_number string, password string)");
            statement.executeUpdate("create table songs (id integer, name string, artist string, rating integer, play_count integer)");
            statement.executeUpdate("create table playlists (id integer, song_id integer, name string)");

            statement.executeUpdate("insert into users values(1, 'jamal', 'jamal@pesawise.com', '0724106569', 'supersecretpassword')");
            statement.executeUpdate("insert into users values(2, 'richard', 'richard@google.com', '0723111222', 'supersecretpassword')");

            statement.executeUpdate("insert into songs values(1, 'Number One (Bleach)', 'Shiro Sagisu (Topic)', 5,  0)");
            statement.executeUpdate("insert into songs values(2, 'I Got Love', 'Don Diablo, Nate Dogg', 5,  0)");
            statement.executeUpdate("insert into songs values(3, 'To The Wire (Album X)', 'Julian Jordan', 3,  0)");
            statement.executeUpdate("insert into songs values(4, 'Things I Said (To The Wire)', 'Nu Aspect', 2,  0)");
            statement.executeUpdate("insert into songs values(5, 'Clearing The Mind', 'Bear McCreary, Sparks & Shadow', 3,  0)");
            statement.executeUpdate("insert into songs values(6, 'Face The Past, Face The Future', 'Bear McCreary, Sparks & Shadow', 5,  0)");
            statement.executeUpdate("insert into songs values(7, 'Master Thyself', 'Bear McCreary, Sparks & Shadow', 4,  0)");
            statement.executeUpdate("insert into songs values(8, 'Forgive Our Trespasses', 'Nandipha808, Ceeka RSA, DEMOLA', 1,  0)");
            statement.executeUpdate("insert into songs values(9, 'Ohema', 'Victony, Crayon, Bella Shmurda', 2,  0)");
            statement.executeUpdate("insert into songs values(10, 'Ayo Girl (Fayahh Beat)', 'Robinson, Jason Derulo, Rema', 4,  0)");
            statement.executeUpdate("insert into songs values(11, 'Unbreakable (with Sam Grey)', 'TELYKAST, Sam Gray', 5,  0)");
            statement.executeUpdate("insert into songs values(12, 'Happiness (feat. Asake & Gunna)', 'Sarz, Asake, Gunna.', 5,  0)");
            statement.executeUpdate("insert into songs values(13, 'Father Stretch My Hands Pt. 1', 'Kanye West', 5,  0)");

            ResultSet rs = statement.executeQuery("select * from users");
            while(rs.next()) {
                // read the result set
                System.out.println("username = " + rs.getString("username"));
                System.out.println("password = " + rs.getString("password"));
                //System.out.println("id = " + rs.getInt("id"));
            }
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

    public void getAllSongs() {

    }
}
