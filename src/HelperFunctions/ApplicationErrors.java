package HelperFunctions;

import javafx.scene.control.Alert;

import java.util.ResourceBundle;

public class ApplicationErrors {

    // Get system language
    static ResourceBundle language = DetermineLanguage.getLanguage();

    /**
     * Zip code error
     */
    public static void zipCodeError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(language.getString("errorTitle"));
        alert.setHeaderText(language.getString("zipCodeError"));
        alert.setContentText(language.getString("zipCodeContext"));
        alert.showAndWait();
    }

    /**
     * Phone number error
     */
    public static void phoneNumberError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(language.getString("errorTitle"));
        alert.setHeaderText(language.getString("phoneNumberError"));
        alert.setContentText(language.getString("phoneNumberContext"));
        alert.showAndWait();
    }

    /**
     * User ID does not exist error
     */
    public static void userIDDoesNotExistError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(language.getString("errorTitle"));
        alert.setHeaderText(language.getString("userIDError"));
        alert.setContentText(language.getString("userIDContext"));
        alert.showAndWait();
    }

    /**
     * Customer ID does not exist error
     */
    public static void customerIDDoesNotExistError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(language.getString("errorTitle"));
        alert.setHeaderText(language.getString("customerIDError"));
        alert.setContentText(language.getString("customerIDContext"));
        alert.showAndWait();
    }

    /**
     * Appointment End Time Is Before Start Time Error
     */
    public static void appointmentEndTimeBeforeStartTimeError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(language.getString("errorTitle"));
        alert.setHeaderText(language.getString("appointmentEndTimeBeforeStartTimeError"));
        alert.setContentText(language.getString("appointmentEndTimeBeforeStartTimeContext"));
        alert.showAndWait();
    }

    /**
     * Conflicting Appointment Error
     */
    public static void appointmentOverlapsError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(language.getString("errorTitle"));
        alert.setHeaderText(language.getString("appointmentOverlapError"));
        alert.setContentText(language.getString("appointmentOverlapContext"));
        alert.showAndWait();
    }
}
