package HelperFunctions;

import Controller.LoginController;
import Database.Customers;
import Model.Appointment;
import javafx.scene.control.Alert;

import java.util.ResourceBundle;

public class ApplicationAlerts {

    // Get system language
    static ResourceBundle language = DetermineLanguage.getLanguage();

    /**
     * Alert user that appointment is upcoming
     * @param appointment Appointment object
     */
    public static void UpcomingAppointment(Appointment appointment) {
        String appointmentDate = appointment.getStart().split(" ")[0];
        String appointmentTime = AppointmentTimes.convertTime(LoginController.getTimeZone(),
                appointment.getStart().split(" ")[1]);
        String customerName = Customers.getCustomerName(appointment.getCustomerID());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(language.getString("upcomingAppointment"));
        alert.setHeaderText(language.getString("upcomingAppointment"));
        alert.setContentText(language.getString("upcomingAppointmentContext1") + " "  + customerName + " " +
                language.getString("upcomingAppointmentID") + " " + appointment.getID() + " " +
                language.getString("upcomingAppointmentAt") + " " + appointmentDate + " " + appointmentTime + " " +
                language.getString("upcomingAppointmentContext2"));
        alert.showAndWait();
    }
}
