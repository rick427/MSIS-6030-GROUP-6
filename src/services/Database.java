package services;

import models.Users;
import constants.UserTable;

import java.sql.*;

public class Database {
    public Database() {
    }
    UserTable userTable = new UserTable();

    public void connect() {
        Connection connection = null;
        try{
            connection = DriverManager.getConnection("jdbc:sqlite:jukebox.db");
            System.out.println("Connected to the database");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.


            String createUserTableSQL = "CREATE TABLE IF NOT EXISTS " + userTable.getTableName() +  " (\n"
                    + userTable.getEMAIL() + " TEXT PRIMARY KEY, \n"
                    + userTable.getUSERNAME() + " TEXT, \n"
                    + userTable.getPhoneNo() + " TEXT, \n"
                    + userTable.getPASSWORD() + " TEXT \n"
                    + ");";

            statement.execute(createUserTableSQL);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            if(connection != null){
                try{
                    connection.close();
                }catch (SQLException e){
                    System.out.println(e.getMessage());
                }
            }
        }
    }

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

    public boolean signup(Users users) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:jukebox.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // Hash the password before storing
//            String hashedPassword = hashPassword(password);

            PreparedStatement myStmt = connection.prepareStatement(
                    "INSERT INTO USERS (EMAIL, USERNAME, PHONE_NUMBER, PASSWORD) VALUES (?, ?, ?, ?)");
            myStmt.setString(1, users.getEmail());
//            myStmt.setString(2, hashedPassword);
            myStmt.setString(2, users.getUsername());
            myStmt.setString(3, users.getPhone_number());
            myStmt.setString(4, users.getPassword());

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
