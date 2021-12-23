package HelperFunctions;

import Database.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ValidateInput {

    /**
     * Validate that the input is 5 characters long
     * @param input The input to be validated
     * @return True if the input is 5 characters long, false otherwise
     */
    public static boolean isZipCode(String input) {
        return input.length() == 5;
    }

    /**
     * Validate that the input is a valid phone number
     * @param input The input to be validated
     * @return True if the input is a valid phone number, false otherwise
     */
    public static boolean isPhoneNumber(String input) {
        return input.matches("[0-9]{3}-[0-9]{3}-[0-9]{4}");
    }

    /**
     * Validate that the input is a valid user ID and that the user ID exists in the database
     * @param input The input to be validated
     * @return True if the input is a valid user ID and that the user ID exists in the database, false otherwise
     */
    public static boolean validateUserID(String input) {
        int userID = Integer.parseInt(input);
        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            String query = "SELECT * FROM users WHERE User_ID = '" + userID + "'";
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Validate that the customer ID exists in the database
     * @param input The input to be validated
     * @return True if the customer ID exists in the database, false otherwise
     */
    public static boolean validateCustomerID(String input) {
        int customerID = Integer.parseInt(input);
        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            String query = "SELECT * FROM customers WHERE Customer_ID = '" + customerID + "'";
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
