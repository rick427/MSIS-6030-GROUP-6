
package utils;

import java.sql.*;

public class DbConnection {
    private static final String url = "jdbc:mariadb://localhost:3306/jukebox";
    private static final String user = "root";
    private static final String password = "";

    private static Connection connection = null; // Static connection shared by all methods

    // No main method needed here

    // Helper method to get the connection (creates one if necessary)
    private static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(url, user, password);
        }
        return connection;
    }

    public static String listAll() {
        StringBuilder output = new StringBuilder();

        try (Connection conn = getConnection(); // Get connection for this method
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Library")) {

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String trackName = resultSet.getString("TrackName");
                String artist = resultSet.getString("Artist");
                int rating = resultSet.getInt("Rating");

                output.append(id).append(": ").append(trackName).append(" - ").append(artist).append("\n");


//                System.out.println("ID: " + id + ", Track: " + trackName + ", Artist: " + artist + ", Rating: " + rating);

            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }

        return output.toString();
    }

    public static int getTotal() throws SQLException {
        try (Connection conn = getConnection();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) as total FROM Library")) {
            if (resultSet.next()) {
                return resultSet.getInt("total");
            } else {
                return 0;
            }
        }
    }



//    public static String getArtist(Connection connection, String key) throws SQLException {
//        String query = "SELECT Artist FROM Library WHERE ID = ?";
//        try (PreparedStatement statement = connection.prepareStatement(query)) {
//            statement.setString(1, key);
//            try (ResultSet resultSet = statement.executeQuery()) {
//                if (resultSet.next()) {
//                    return resultSet.getString("Artist");
//                } else {
//                    return null;
//                }
//            }
//        }
//    }

    public static String getArtist(String key) throws SQLException {
        String query = "SELECT Artist FROM Library WHERE ID = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, key);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("Artist");
                } else {
                    return null;
                }
            }
        }
    }


//    public static int getRating(Connection connection, String key) throws SQLException {
//        String query = "SELECT Rating FROM Library WHERE ID = ?";
//        try (PreparedStatement statement = connection.prepareStatement(query)) {
//            statement.setString(1, key);
//            try (ResultSet resultSet = statement.executeQuery()) {
//                if (resultSet.next()) {
//                    return resultSet.getInt("Rating");
//                } else {
//                    return -1;
//                }
//            }
//        }
//    }

    public static int getRating(String key) throws SQLException {

        String query = "SELECT Rating FROM Library WHERE ID = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, key);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("Rating");
                } else {
                    return -1;
                }
            }
        }
    }

//    public static String getName(Connection connection, String key) throws SQLException {
//        String query = "SELECT TrackName FROM Library WHERE ID = ?";
//        try (PreparedStatement statement = connection.prepareStatement(query)) {
//            statement.setString(1, key);
//            try (ResultSet resultSet = statement.executeQuery()) {
//                if (resultSet.next()) {
//                    return resultSet.getString("TrackName");
//                } else {
//                    return null;
//                }
//            }
//        }
//    }

    public static String getName(String key) throws SQLException {
        String query = "SELECT TrackName FROM Library WHERE ID = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, key);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("TrackName");
                } else {
                    return null;
                }
            }
        }
    }

}
