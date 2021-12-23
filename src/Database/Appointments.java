package Database;

import HelperFunctions.AppDateFormatter;
import HelperFunctions.SystemTimestamp;
import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class Appointments {

    /**
     * Method gets all appointments within a specific date range
     * @param days number of days to search
     * @return ObservableList of appointments
     */
    public static ObservableList<Appointment> getAppointments(int days) {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        String today = SystemTimestamp.getTimestamp();
        if (days == 30) {
            try {
                LocalDate currentDate = LocalDate.now();
                Statement statement = DatabaseConnection.getConnection().createStatement();
                // Query appointments from database within last days
                ResultSet resultSet = statement.executeQuery("SELECT * FROM appointments WHERE Start >=" +
                        " (LAST_DAY(NOW()) + INTERVAL 1 DAY - INTERVAL 1 MONTH) AND Start <  (LAST_DAY(NOW()) + " +
                        "INTERVAL 1 DAY);");
                //ResultSet resultSet = statement.executeQuery("SELECT * FROM appointments");
                //Add all appointments to observable list
                createAppointmentObject(appointments, resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (days == 7) {
            try {
                Statement statement = DatabaseConnection.getConnection().createStatement();
                // Query appointments from database within last days
                ResultSet resultSet = statement.executeQuery("SELECT * FROM appointments WHERE WEEK(START) = WEEK(NOW())");
                //ResultSet resultSet = statement.executeQuery("SELECT * FROM appointments");
                //Add all appointments to observable list
                createAppointmentObject(appointments, resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        //Print length of appointments
        System.out.println("Appointments: " + appointments.size());
        return appointments;
    }

    /**
     * Method gets all appointments
     * @return ObservableList of appointments
     */
    public static ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            // Query appointments from database within last days
            ResultSet resultSet = statement.executeQuery("SELECT * FROM appointments");
            //Add all appointments to observable list
            createAppointmentObject(appointments, resultSet);
            // Print appointment ID
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    /**
     * Methos creates appointment object from result set and list of appointments
     * @param appointments list of appointments
     * @param resultSet result set of appointments
     * @throws SQLException exception
     */
    private static void createAppointmentObject(ObservableList<Appointment> appointments, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            Appointment appointment = new Appointment(resultSet.getInt("Appointment_ID"),
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
            appointments.add(appointment);
        }
        appointmentList(appointments, resultSet);
    }

    /**
     * Method adds an appointment to the database
     * @param appointment Appointment object
     */
    public static void addAppointment(Appointment appointment) {
        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            statement.executeUpdate("INSERT INTO appointments (Title, Description, Location, Type, Start, End, " +
                    "Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                    "VALUES ('" + appointment.getTitle() + "', " +
                    "'" + appointment.getDescription() + "'," +
                    " '" + appointment.getLocation() + "', " +
                    "'" + appointment.getType() + "', " +
                    "'" + appointment.getStart() + "', " +
                    "'" + appointment.getEnd() + "', " +
                    "'" + appointment.getCreateDate() + "', " +
                    "'" + appointment.getCreatedBy() + "'," +
                    " '" + appointment.getLastUpdate() + "', " +
                    "'" + appointment.getLastUpdatedBy() + "', " +
                    "'" + appointment.getCustomerID() + "', " +
                    "'" + appointment.getUserID() + "', " +
                    "'" + appointment.getContactID() + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method updates an appointment in the database
     * @param appointment to be updated
     */
    public static void updateAppointment(Appointment appointment) {
        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            statement.executeUpdate("UPDATE appointments SET Title = '" + appointment.getTitle() + "', " +
                    "Description = '" + appointment.getDescription() + "', " +
                    "Location = '" + appointment.getLocation() + "', " +
                    "Type = '" + appointment.getType() + "', " +
                    "Start = '" + appointment.getStart() + "', " +
                    "End = '" + appointment.getEnd() + "', " +
                    "Create_Date = '" + appointment.getCreateDate() + "', " +
                    "Created_By = '" + appointment.getCreatedBy() + "', " +
                    "Last_Update = '" + appointment.getLastUpdate() + "', " +
                    "Last_Updated_By = '" + appointment.getLastUpdatedBy() + "', " +
                    "Customer_ID = '" + appointment.getCustomerID() + "', " +
                    "User_ID = '" + appointment.getUserID() + "', " +
                    "Contact_ID = '" + appointment.getContactID() + "' WHERE Appointment_ID = " + appointment.getID());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method deletes an appointment from the database
     * @param id ID of the appointment to be deleted
     */
    public static void deleteAppointment(int id) {
        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            statement.executeUpdate("DELETE FROM appointments WHERE Appointment_ID = " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method returns a list of appointments for a specific user
     * @param userID - user ID
     * @return list of appointments
     */
    public static ObservableList<Appointment> searchAppointment(int userID) {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM appointments WHERE User_ID = " + userID);
            appointmentList(appointments, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    /**
     * Method returns a list of appointments
     * @param appointments - list of appointments
     * @param resultSet - result set from database
     * @throws SQLException - exception
     */
    private static void appointmentList(ObservableList<Appointment> appointments, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            appointments.add(new Appointment(
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
                    resultSet.getInt("Contact_ID")));
        }
    }

    /**
     * Method returns a list of appointments for a specific customer
     * @param customerID - customer ID
     * @return - list of appointments
     */
    public static boolean customerExists(int customerID) {
        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM appointments WHERE Customer_ID = " + customerID);
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Method returns an appointment based on the appointment ID
     * @param appointmentID the appointment ID
     * @return the appointment
     */
    public static Appointment getAppointment(int appointmentID) {
        Appointment appointment = null;
        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM appointments WHERE Appointment_ID = " + appointmentID);
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointment;
    }

    /**
     * Method ensures that there are no overlapping appointments
     * @param appointmentStartDate - start date of the appointment
     * @param appointmentStartTime - start time of the appointment
     * @param appointmentEndDate - end date of the appointment
     * @param appointmentEndTime - end time of the appointment
     * @param appointmentID - appointment ID
     * @return - true if there are no overlapping appointments
     */
    public static boolean checkForOverlappingAppointments(String appointmentStartDate, String appointmentStartTime,
                                                          String appointmentEndDate, String appointmentEndTime,
                                                          int appointmentID) {
        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM appointments WHERE Appointment_ID != " + appointmentID + " AND " +
                    "((Start BETWEEN '" + appointmentStartDate + " " + appointmentStartTime + "' AND '" + appointmentEndDate + " " + appointmentEndTime + "') OR " +
                    "(End BETWEEN '" + appointmentStartDate + " " + appointmentStartTime + "' AND '" + appointmentEndDate + " " + appointmentEndTime + "'))");
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
