package Database;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class DatabaseConnection {

    // Database credentials and connection
    private static final String url = "STRING";
    private static final String user = "USER";
    private static final String password = "PASSWORD";
    private static Connection connection;

    public DatabaseConnection() {
    }

    /**
     * Connects to the database.
     */
    public static void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connection established.");

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Class not found: " + e.getMessage());
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }

    /**
     * Disconnects from the database.
     */
    public static void disconnect() {
        try {
            connection.close();
            System.out.println("Connection closed.");
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }

    /**
     * Returns the connection.
     * @return connection
     */
    public static java.sql.Connection getConnection() {
        return connection;
    }

}
