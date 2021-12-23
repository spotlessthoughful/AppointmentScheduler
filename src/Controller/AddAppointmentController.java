package Controller;

import Database.*;
import HelperFunctions.*;
import Model.Appointment;
import Model.Contact;
import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class AddAppointmentController implements Initializable {

    @FXML
    public TextField appointmentIDTextField;

    @FXML
    public TextField appointmentTitleTextField;
    @FXML
    public TextField appointmentDescriptionTextField;

    @FXML
    public ComboBox<String> appointmentLocationComboBox;

    @FXML
    public ComboBox<String> appointmentContactComboBox;

    @FXML
    public TextField appointmentTypeTextField;

    @FXML
    public DatePicker appointmentStartDatePicker;

    @FXML
    public DatePicker appointmentEndDatePicker;

    @FXML
    public TextField customerIDTextField;

    @FXML
    public TextField userIDTextField;

    @FXML
    public Button saveButton;

    @FXML
    public Button backButton;

    @FXML
    public ComboBox<String> appointmentStartTimeComboBox;

    @FXML
    public ComboBox<String> appointmentEndTimeComboBox;

    @FXML
    public Label appointmentIDLabel;

    @FXML
    public Label titleLabel;

    @FXML
    public Label descriptionLabel;

    @FXML
    public Label locationLabel;

    @FXML
    public Label contactLabel;

    @FXML
    public Label typeLabel;

    @FXML
    public Label startLabel;

    @FXML
    public Label endLabel;

    @FXML
    public Label customerIDLabel;

    @FXML
    public Label userIDLabel;

    @FXML
    public Label addAppointmentLabel;

    /**
     *
     * @param actionEvent when save button is pushed
     */
    @FXML
    public void saveButtonPushed(ActionEvent actionEvent) {
        int appointmentID = Integer.parseInt(appointmentIDTextField.getText());
        String userID = null;
        String customerID = null;
        Contact contact = Contacts.getContactName(appointmentContactComboBox.getValue());
        // Get contact ID from contact variable
        int contactID = contact.getID();
        // Get Appointment Title From TextField and set it to appointmentTitle
        String appointmentTitle = appointmentTitleTextField.getText();
        // Get Appointment Description From TextField and set it to appointmentDescription
        String appointmentDescription = appointmentDescriptionTextField.getText();
        // Get Appointment Location From ComboBox and set it to appointmentLocation
        String appointmentLocation = appointmentLocationComboBox.getValue();
        // Get Appointment Type From TextField and set it to appointmentType
        String appointmentType = appointmentTypeTextField.getText();
        // Get Appointment Start Date From DatePicker and set it to appointmentStartDate
        String appointmentStartDate = appointmentStartDatePicker.getValue().toString();
        // Get Appointment Start Time From ComboBox and set it to appointmentStartTime
        String appointmentStartTime = AppointmentTimes.convertTimeLocalToUTC(LoginController.getTimeZone(),
                appointmentStartTimeComboBox.getValue());
        // Get Appointment End Date From DatePicker and set it to appointmentEndDate
        String appointmentEndDate = appointmentEndDatePicker.getValue().toString();
        // Get Appointment End Time From ComboBox and set it to appointmentEndTime
        String appointmentEndTime = AppointmentTimes.convertTimeLocalToUTC(LoginController.getTimeZone(),
                appointmentEndTimeComboBox.getValue());
        // Get customer ID from text field and query database to validate customer ID
        if (ValidateInput.validateCustomerID(customerIDTextField.getText())) {
            customerID = customerIDTextField.getText();
        } else {
            ApplicationErrors.customerIDDoesNotExistError();
            return;
        }
        // Get user ID from userIDTextField and query users database to validate userID
        if (ValidateInput.validateUserID(userIDTextField.getText())) {
            userID = userIDTextField.getText();
        } else {
            ApplicationErrors.userIDDoesNotExistError();
            return;
        }

        // Determine if appointmentEndTime is at or before appointmentStartTime
        if (appointmentEndTime.compareTo(appointmentStartTime) <= 0) {
            ApplicationErrors.appointmentEndTimeBeforeStartTimeError();
            return;
        }

        // Ensure there are no overlapping appointments
        if (Appointments.checkForOverlappingAppointments(appointmentStartDate, appointmentStartTime, appointmentEndDate,
                appointmentEndTime, appointmentID)) {
            ApplicationErrors.appointmentOverlapsError();
            return;
        }

        // Get current timestamp and set it to appointmentTimestamp
        String timestamp = AppointmentTimes.convertDateTimeFromLocalToUTC(LoginController.getTimeZone(), SystemTimestamp.getTimestamp());
        // Get current user and set it to user
        String user = UserLogin.getCurrentUser();

        String start = appointmentStartDate + " " + appointmentStartTime;
        String end = appointmentEndDate + " " + appointmentEndTime;

        // Create new appointment object from the variables
        assert customerID != null;
        assert userID != null;
        Appointment appointment = new Appointment(appointmentID,
                appointmentTitle,
                appointmentDescription,
                appointmentLocation,
                appointmentType,
                start,
                end,
                timestamp,
                user,
                timestamp,
                user,
                Integer.parseInt(customerID),
                Integer.parseInt(userID), contactID);
        Appointments.addAppointment(appointment);
        // Add appointment to appointmentTableModels
        AppointmentTableModels.createAppointmentTableModel(appointment);
        // Close window
        Window.show(actionEvent, "/View/Appointments.fxml");
    }

    /**
     * Method to handle the cancel button and return to the appointments window
     *
     * @param actionEvent ActionEvent
     */
    @FXML
    public void backButtonPushed(ActionEvent actionEvent) {
        Window.show(actionEvent, "/View/Appointments.fxml");
    }

    /**
     * Method to handle the appointment date selected.
     *
     * @param actionEvent ActionEvent
     */
    @FXML
    public void appointmentDateSelected(ActionEvent actionEvent) {
        // Get selected date from datePicker
        LocalDate selectedDate = appointmentStartDatePicker.getValue();
        // Set comboBox values
        appointmentEndDatePicker.setValue(selectedDate);
    }

    /**
     * Method to handle the initialization of the controller.
     *
     * @param url          URL
     * @param resourceBundle ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentLocationComboBox.setItems(Divisions.getLocations());
        appointmentContactComboBox.setItems(Contacts.getContactsNames());
        appointmentStartTimeComboBox.setItems(AppointmentTimes.convertTimesEasternToLocal(LoginController.getTimeZone(), AppointmentTimes.getAppointmentTimes()));
        appointmentEndTimeComboBox.setItems(AppointmentTimes.convertTimesEasternToLocal(LoginController.getTimeZone(), AppointmentTimes.getAppointmentTimes()));
        //Set appointmentIDTextField to next available appointment ID
        appointmentIDTextField.setText(Integer.toString(Sequence.getNextAppointmentID()));
        // Get system language
        ResourceBundle language = DetermineLanguage.getLanguage();
        // Set label and button text to language
        addAppointmentLabel.setText(language.getString("addAppointmentLabel"));
        backButton.setText(language.getString("backButton"));
        saveButton.setText(language.getString("saveButton"));
        appointmentIDLabel.setText(language.getString("appointmentIDLabel"));
        titleLabel.setText(language.getString("titleLabel"));
        descriptionLabel.setText(language.getString("descriptionLabel"));
        locationLabel.setText(language.getString("locationLabel"));
        contactLabel.setText(language.getString("contactLabel"));
        typeLabel.setText(language.getString("typeLabel"));
        startLabel.setText(language.getString("startLabel"));
        endLabel.setText(language.getString("endLabel"));
        customerIDLabel.setText(language.getString("customerIDLabel"));
        userIDLabel.setText(language.getString("userIDLabel"));
    }
}
