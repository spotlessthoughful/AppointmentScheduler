package Controller;

import Database.AppointmentTableModels;
import Database.DatabaseConnection;
import HelperFunctions.*;
import Database.UserLogin;
import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class LoginController implements Initializable {

    static String zoneID = ZoneId.systemDefault().toString();

    static String timeZone = TimeZone.getTimeZone(zoneID).getID();

    //Returns the current timezone
    public static String getTimeZone() {
        return timeZone;
    }

    @FXML
    public Label usernameLabel;

    @FXML
    public Label passwordLabel;

    @FXML
    public TextField usernameTextField;

    @FXML
    public TextField passwordTextField;

    @FXML
    public Button loginButton;

    @FXML
    public Button exitButton;

    @FXML
    public Label localeLabel;

    @FXML
    public Label loginLabel;

    /**
     * This method is called when the login button is pushed.
     * @param actionEvent The event that is triggered when the button is pushed.
     * @throws SQLException Throws an exception if the database cannot be accessed.
     */
    @FXML
    public void loginButtonPushed(ActionEvent actionEvent) throws SQLException {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        boolean loginSuccessful = UserLogin.login(username, password);
        if (loginSuccessful) {
            // Log successful login containing username, timestamp(UTC), and success status to login_activity.txt under src/
            LoggingWriter.appendToLog("Login successful for user: " + UserLogin.getCurrentUser() + " at " +
                    AppointmentTimes.convertDateTimeFromLocalToUTC(timeZone,
                            SystemTimestamp.getTimestamp()) + " UTC");
            Window.show(actionEvent, "/View/Welcome.fxml");
        } else {
            ResourceBundle language = DetermineLanguage.getLanguage();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(language.getString("loginErrorTitle"));
            alert.setHeaderText(language.getString("loginErrorHeaderText"));
            alert.setContentText(language.getString("loginErrorContext"));
            alert.showAndWait();
            // Log unsuccessful login containing username, timestamp(UTC), and success status to login_activity.txt under src/
            LoggingWriter.appendToLog("Login failed for user: " + usernameTextField.getText() + " at " +
                    AppointmentTimes.convertDateTimeFromLocalToUTC(timeZone,
                            SystemTimestamp.getTimestamp()) + " UTC");
        }
    }

    /**
     * This method is called when the exit button is pushed.
     * @param actionEvent The event that is triggered when the button is pushed.
     */
    public void exitButtonPushed(ActionEvent actionEvent) {
        // Close the window
        DatabaseConnection.disconnect();
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    /**
     * This method initializes the login controller.
     * @param url The url of the controller.
     * @param resourceBundle The resource bundle of the controller.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Set localeLabel to the current time zone
        localeLabel.setText(zoneID);
        // Set resource bundle to the current language
        ResourceBundle language = DetermineLanguage.getLanguage();
        System.out.println(language.getString("loginLabel"));
        // Set the labels and buttons to the current language
        loginLabel.setText(language.getString("loginLabel"));
        usernameLabel.setText(language.getString("usernameLabel"));
        passwordLabel.setText(language.getString("passwordLabel"));
        loginButton.setText(language.getString("loginButton"));
        exitButton.setText(language.getString("exitButton"));
    }

}
