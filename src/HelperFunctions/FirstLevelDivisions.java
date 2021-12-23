package HelperFunctions;

import Database.DatabaseConnection;
import Model.FirstLevelDivision;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FirstLevelDivisions {

    /**
     * Method that queries the first_level_divisions with given Division_ID and returns a FirstLevelDivision object
     * @param divisionID int
     * @return FirstLevelDivision object
     */
    public static FirstLevelDivision getFirstLevelDivision(int divisionID) {
        FirstLevelDivision firstLevelDivision = null;
        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM first_level_divisions WHERE Division_ID = " + divisionID);
            while (resultSet.next()) {
                firstLevelDivision = new FirstLevelDivision(resultSet.getInt("Division_ID"),
                        resultSet.getString("Division"),
                        resultSet.getString("Create_Date"),
                        resultSet.getString("Created_By"),
                        resultSet.getString("Last_Update"),
                        resultSet.getString("Last_Updated_By"),
                        resultSet.getInt("Country_ID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return firstLevelDivision;
    }

    /**
     * Method that queries the countries table and returns the Country Name for the given Country_ID
     * @param countryID int
     * @return String Country Name
     */
    public static String getCountryName(int countryID) {
        String countryName = "";
        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM countries WHERE Country_ID = " + countryID);
            while (resultSet.next()) {
                countryName = resultSet.getString("Country");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countryName;
    }

}
