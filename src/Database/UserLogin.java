package Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import Model.User;

public class UserLogin {
    private static String loggedInUser;

    private static int userID;

    /**
     * @return the loggedInUser (username)
     */
    public static String getCurrentUser() {
        return loggedInUser;
    }

    /**
     * @return the userID
     */
    public static int getUserID() {
        return userID;
    }

    /**
     * Logs in the user
     * @param username the username of the user
     * @param password the password of the user
     * @return true if the user is logged in, false if not
     * @throws SQLException if the query fails
     */
    public static Boolean login(String username, String password) throws SQLException {
        try{
            Statement statement = DatabaseConnection.getConnection().createStatement();
            String query = "SELECT * FROM users WHERE User_Name = '" + username + "' AND Password = '" + password + "'";
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet.next()){
                User currentUser = new User(resultSet.getInt("User_ID"),
                        resultSet.getString("User_Name"),
                        resultSet.getString("Password"),
                        resultSet.getString("Create_Date"),
                        resultSet.getString("Created_By"),
                        resultSet.getString("Last_Update"),
                        resultSet.getString("Last_Updated_By"));

                loggedInUser = currentUser.getUsername();
                userID = currentUser.getId();
                return true;
            } else {
                return false;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }

}
