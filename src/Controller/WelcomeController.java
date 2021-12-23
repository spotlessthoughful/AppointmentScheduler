package Controller;

import Database.UserLogin;
import Database.Users;
import HelperFunctions.AppointmentTimes;
import HelperFunctions.DetermineLanguage;
import HelperFunctions.SystemTimestamp;
import HelperFunctions.Window;
import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class WelcomeController implements Initializable {

    @FXML
    public Label welcomeLabel;

    @FXML
    public Button viewCustomersButton;

    @FXML
    public Button viewAppointmentsButton;

    @FXML
    public Button viewReportsButton;

    @FXML
    public Button exitButton;

    /**
     * This method will be called when the user clicks the view customers button
     * @param actionEvent the event that is triggered when the user clicks the view customers button
     */
    @FXML
    public void viewCustomers(ActionEvent actionEvent) {
        Window.show(actionEvent, "/View/Customers.fxml");
    }

    /**
     * This method will be called when the user clicks the view appointments button
     * @param actionEvent the event that is triggered when the user clicks the view appointments button
     */
    @FXML
    public void viewAppointments(ActionEvent actionEvent) {
        Window.show(actionEvent, "/View/Appointments.fxml");
    }

    /**
     * This method will be called when the user clicks the view reports button
     * @param actionEvent the event that is triggered when the user clicks the view reports button
     */
    @FXML
    public void viewReports(ActionEvent actionEvent) {
        Window.show(actionEvent, "/View/GenerateReports.fxml");
    }

    /**
     * This method will be called when the user clicks the exit button and will close the application
     * @param actionEvent the event that is triggered when the user clicks the exit button
     */
    @FXML
    public void exitHandler(ActionEvent actionEvent) {
        // Exit the program
        System.exit(0);
    }

    /**
     * This method will initialize the welcome screen
     * @param url the url
     * @param resourceBundle the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set the language
        ResourceBundle language = DetermineLanguage.getLanguage();
        // Set Values Of Labels And Buttons
        exitButton.setText(language.getString("exitButton"));
        welcomeLabel.setText(language.getString("welcomeLabel"));
        viewCustomersButton.setText(language.getString("viewCustomersButton"));
        viewAppointmentsButton.setText(language.getString("viewAppointmentsButton"));
        viewReportsButton.setText(language.getString("viewReportsButton"));
        // Get current UTC timestamp
        String currentTime = AppointmentTimes.convertDateTimeFromLocalToUTC(LoginController.getTimeZone(), SystemTimestamp.getTimestamp());
        String timeFrame = AppointmentTimes.convertDateTimeFromLocalToUTC(LoginController.getTimeZone(), SystemTimestamp.getTimestampPlus15());
        // Check to see if there are any appointments within 15 minutes for signed in user
        // Query the database for appointments within 15 minutes
        // If there are, show the user a notification
        // If there are not, do nothing
        int signedInUser = UserLogin.getUserID();
        Users.getAppointments(signedInUser, currentTime, timeFrame);
    }
}
