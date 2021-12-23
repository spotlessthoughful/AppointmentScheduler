package Database;

import HelperFunctions.ApplicationAlerts;
import Model.Appointment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Users {

    /**
     * Query the database for appointments for the given userID within the next 15 minutes
     * @param userID The userID to query for
     * @param time The current time
     * @param timeFrame The time frame to query for
     */
    public static void getAppointments(int userID, String time, String timeFrame) {
        Appointment appointment = null;
        try{
            // Connect to the database
            Statement statement = DatabaseConnection.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM appointments WHERE User_ID = " + userID +
                    " AND Start BETWEEN '" + time + "' AND '" + timeFrame + "'");
            if (resultSet.next()) {
                appointment = new Appointment(
                        resultSet.getInt("Appointment_ID"),
                        resultSet.getString("Title"),
                        resultSet.getString("Description"),
                        resultSet.getString("Location"),
                        resultSet.getString("Type"),
                        resultSet.getString("Start"),
                        resultSet.getString("End"),
                        resultSet.getString("Create_Date"),
                        resultSet.getString("Created_By"),
                        resultSet.getString("Last_Update"),
                        resultSet.getString("Last_Updated_By"),
                        resultSet.getInt("Customer_ID"),
                        resultSet.getInt("User_ID"),
                        resultSet.getInt("Contact_ID"));
                System.out.println(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Check if the appointment is null
        if (appointment != null) {
            ApplicationAlerts.UpcomingAppointment(appointment);
        }

    }
}
