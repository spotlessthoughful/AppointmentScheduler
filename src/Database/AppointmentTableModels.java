package Database;

import Controller.LoginController;
import HelperFunctions.AppointmentTimes;
import Model.Appointment;
import Model.AppointmentTableModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AppointmentTableModels {

    private static final ObservableList<AppointmentTableModel> appointmentTableModels = FXCollections.observableArrayList();
    static Boolean isLoaded = false;

    /**
     * Method to clear the appointment table models
     */
    public static void clear() {
        appointmentTableModels.clear();
    }

    /**
     * Method to create AppointmentTableModels within specified range
     * @param days Number of days to create AppointmentTableModels
     * @throws SQLException SQLException
     */
    public static void createAppointmentTableModels(int days) throws SQLException {
        // ObservableList of Appointment Objects
        ObservableList<Appointment> appointments = Appointments.getAppointments(days);
        for (Appointment appointment : appointments) {
            // Query contacts table for contact name using appointment contactID
            String contactName = getContactName(appointment);
            System.out.println(contactName);
            // Create new AppointmentTableModel object
            AppointmentTableModel appointmentTableModel = new AppointmentTableModel(
                    appointment.getID(),
                    appointment.getTitle(),
                    appointment.getDescription(),
                    appointment.getLocation(),
                    contactName,
                    appointment.getType(),
                    AppointmentTimes.convertDateTimeFromUTCToLocal(LoginController.getTimeZone(),appointment.getStart()),
                    AppointmentTimes.convertDateTimeFromUTCToLocal(LoginController.getTimeZone(),appointment.getEnd()),
                    appointment.getCustomerID(),
                    appointment.getUserID());
            // Add AppointmentTableModel object to observable list
            appointmentTableModels.add(appointmentTableModel);
        }
        isLoaded = true;
    }

    /**
     * Method to create AppointmentTableModel from Appointment object
     * @param appointment Appointment object
     */
    public static void createAppointmentTableModel(Appointment appointment) {
        // Query contacts table for contact name using appointment contactID
        //Print appointment id
        String contactName = getContactName(appointment);
        // Query appointments table and get Appointment object using appointmentID
        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            String query = "SELECT * FROM appointments WHERE " + "Appointment_ID = " + appointment.getID();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                // Create new AppointmentTableModel object
                AppointmentTableModel appointmentTableModel = new AppointmentTableModel(
                        resultSet.getInt("Appointment_ID"),
                        resultSet.getString("Title"),
                        resultSet.getString("Description"),
                        resultSet.getString("Location"),
                        contactName,
                        resultSet.getString("Type"),
                        AppointmentTimes.convertDateTimeFromUTCToLocal(LoginController.getTimeZone(),resultSet.getString("Start")),
                        AppointmentTimes.convertDateTimeFromUTCToLocal(LoginController.getTimeZone(),resultSet.getString("End")),
                        resultSet.getInt("Customer_ID"),
                        resultSet.getInt("User_ID"));
                // Add AppointmentTableModel object to observable list
                appointmentTableModels.add(appointmentTableModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Method to get contact name from appointment
     * @param appointment Appointment object
     * @return contactName
     */
    private static String getContactName(Appointment appointment) {
        String contactName = null;
        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            String query = "SELECT Contact_Name FROM contacts WHERE " + "Contact_ID = " + appointment.getContactID();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                contactName = resultSet.getString("Contact_Name");
                return contactName;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Method to get appointment table models
     * @return observable list of AppointmentTableModel objects
     */
    public static ObservableList<AppointmentTableModel> getAppointmentTableModels() {
        return appointmentTableModels;
    }

    public static Boolean getIsLoaded() {
        return isLoaded;
    }

}
