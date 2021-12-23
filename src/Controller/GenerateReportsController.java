package Controller;

import Database.DatabaseConnection;
import HelperFunctions.AppointmentTimes;
import HelperFunctions.DetermineLanguage;
import HelperFunctions.Window;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.ResourceBundle;

public class GenerateReportsController implements Initializable {

    @FXML
    public Button generateCustomersReportButton;

    @FXML
    public TextArea customersReportTextArea;

    @FXML
    public Button contactSchedulesReportButton;

    @FXML
    public TextArea contactScheduleTextArea;

    @FXML
    public Button totalAppointmentsReportButton;

    @FXML
    public TextArea totalAppointmentsTextArea;

    @FXML
    public Button backButton;

    @FXML
    public Label generateReportsLabel;

    @FXML
    public Label appointmentTypeMonthLabel;

    @FXML
    public Label contactScheduleLabel;

    @FXML
    public Label appointmentByYearLabel;

    /**
     * Generates a report of appointments by month
     * @param actionEvent ActionEvent
     */
    @FXML
    public void generateCustomersReportButtonPressed(ActionEvent actionEvent) {
        // Query appointments table for description, monthname, and count, and group by description, monthname
        // and display in text area
        try {

            Statement statement = DatabaseConnection.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT Type, MONTHNAME(Start) as 'Month', COUNT(*) as 'Total' FROM appointments GROUP BY Type, MONTHNAME(Start)");

            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append(String.format("%1$-60s %2$-60s %3$-60s \n", "Month", "Appointment Type", "Total"));
            stringBuilder.append(String.join("", Collections.nCopies(stringBuilder.length(), "-")));
            stringBuilder.append("\n");

            while(resultSet.next()) {
                stringBuilder.append(String.format("%1$-60s %2$-60s %3$-60s \n", resultSet.getString("Month"), resultSet.getString("Type"), resultSet.getInt("Total")));
            }

            customersReportTextArea.setText(stringBuilder.toString());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates a report of contact schedules
     * @param actionEvent ActionEvent
     */
    @FXML
    public void contactSchedulesReportButtonPressed(ActionEvent actionEvent) {
        try {

            Statement statement = DatabaseConnection.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT contacts.Contact_Name, appointments.Appointment_ID, appointments.Title, appointments.Type, appointments.Description, appointments.Start, appointments.End, appointments.Customer_ID FROM contacts, appointments WHERE contacts.Contact_ID = appointments.Contact_ID ORDER BY contacts.Contact_ID");

            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append(String.format("%1$-60s %2$-60s %3$-60s %4$-60s %5$-60s %6$-60s %7$-60s %8$-60s \n", "Contact Name", "Appointment ID", "Title", "Type", "Description", "Start", "End", "Customer ID"));
            stringBuilder.append(String.join("", Collections.nCopies(stringBuilder.length(), "-")));
            stringBuilder.append("\n");

            while(resultSet.next()) {
                stringBuilder.append(String.format("%1$-60s %2$-60s %3$-60s %4$-60s %5$-60s %6$-60s %7$-60s %8$-60s \n", resultSet.getString("Contact_Name"), resultSet.getInt("Appointment_ID"), resultSet.getString("Title"), resultSet.getString("Type"), resultSet.getString("Description"), AppointmentTimes.convertDateTimeFromUTCToLocal(LoginController.getTimeZone(), resultSet.getString("Start")), AppointmentTimes.convertDateTimeFromUTCToLocal(LoginController.getTimeZone(), resultSet.getString("End")), resultSet.getInt("Customer_ID")));
            }

            contactScheduleTextArea.setText(stringBuilder.toString());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates a report of total appointments by year
     * @param actionEvent ActionEvent
     */
    @FXML
    public void totalAppointmentsReportButtonPressed(ActionEvent actionEvent) {
        // Query Appointments table for count of each appointment by year
        try {

            Statement statement = DatabaseConnection.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT YEAR(Start) as 'Year', COUNT(*) as 'Total' FROM appointments GROUP BY YEAR(Start)");

            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append(String.format("%1$-50s %2$-50s \n", "Year", "Total"));
            stringBuilder.append(String.join("", Collections.nCopies(stringBuilder.length(), "-")));
            stringBuilder.append("\n");

            while(resultSet.next()) {
                stringBuilder.append(String.format("%1$-50s %2$-50s \n", resultSet.getString("Year"), resultSet.getInt("Total")));
            }

            totalAppointmentsTextArea.setText(stringBuilder.toString());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns to the welcome screen
     * @param actionEvent ActionEvent
     */
    @FXML
    public void backButtonPressed(ActionEvent actionEvent) {
        Window.show(actionEvent, "/View/Welcome.fxml");
    }

    /**
     * Initializes the generate reports screen
     * @param url URL
     * @param resourceBundle ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Get System Language
        ResourceBundle language = DetermineLanguage.getLanguage();
        // Set Labels and Buttons
        generateReportsLabel.setText(language.getString("generateReportsLabel"));
        appointmentTypeMonthLabel.setText(language.getString("appointmentTypeMonthLabel"));
        generateCustomersReportButton.setText(language.getString("generateReportButton"));
        contactScheduleLabel.setText(language.getString("contactScheduleLabel"));
        contactSchedulesReportButton.setText(language.getString("generateReportButton"));
        appointmentByYearLabel.setText(language.getString("appointmentByYearLabel"));
        totalAppointmentsReportButton.setText(language.getString("generateReportButton"));
        backButton.setText(language.getString("backButton"));
    }
}
