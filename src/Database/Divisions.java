package Database;

import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Divisions {

    /**
     * Method to get all divisions from first_level_divisions table
     * @return ObservableList of locations
     */
    public static ObservableList<String> getLocations() {
        ObservableList<String> locations = FXCollections.observableArrayList();
        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            // Query Division from first_level_divisions table
            ResultSet resultSet = statement.executeQuery("SELECT * FROM first_level_divisions");
            //Add all Division to observable list
            while (resultSet.next()) {
                locations.add(resultSet.getString("division"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return locations;
    }
}
