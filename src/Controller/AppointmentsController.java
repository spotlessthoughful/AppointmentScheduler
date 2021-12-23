package Controller;

import Database.AppointmentTableModels;
import Database.Appointments;
import HelperFunctions.DetermineLanguage;
import HelperFunctions.SetTables;
import HelperFunctions.Window;
import Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AppointmentsController implements Initializable {

    private static Integer selectedAppointmentID;

    private static Integer days;

    // Return selected appointment ID
    public static Integer getSelectedAppointmentID() {
        return selectedAppointmentID;
    }

    // Return days
    public static Integer getDays() {
        return days;
    }

    @FXML
    public RadioButton monthRadioButton;

    @FXML
    public ToggleGroup frequencyRadioButtonGroup;

    @FXML
    public RadioButton weekRadioButton;

    @FXML
    public TableView<AppointmentTableModel> appointmentTable;

    @FXML
    public TableColumn<AppointmentTableModel, Integer> appointmentIDColumn;

    @FXML
    public TableColumn<AppointmentTableModel, String> appointmentTitleColumn;

    @FXML
    public TableColumn<AppointmentTableModel, String>  appointmentDescriptionColumn;

    @FXML
    public TableColumn<AppointmentTableModel, String>  appointmentLocationColumn;

    @FXML
    public TableColumn<AppointmentTableModel, String>  appointmentContactColumn;

    @FXML
    public TableColumn<AppointmentTableModel, String>  appointmentTypeColumn;

    @FXML
    public TableColumn<AppointmentTableModel, String>  appointmentStartColumn;

    @FXML
    public TableColumn<AppointmentTableModel, String>  appointmentEndColumn;

    @FXML
    public TableColumn<AppointmentTableModel, Integer> appointmentCustomerIDColumn;

    @FXML
    public TableColumn<AppointmentTableModel, Integer> appointmentUserIDColumn;

    @FXML
    public Button backButton;

    @FXML
    public Button modifyAppointmentButton;

    @FXML
    public Button deleteAppointmentButton;

    @FXML
    public Button addAppointmentButton;

    @FXML
    public Label appointmentLabel;

    @FXML
    public Label appointmentRangeLabel;

    /**
     * Sets the days to the number of days selected by the user (30)
     * @param actionEvent ActionEvent
     * @throws SQLException SQLException
     */
    @FXML
    public void monthRadioButtonPushed(ActionEvent actionEvent) throws SQLException {
        // Set days to 30
        days = 30;
        AppointmentTableModels.clear();
        AppointmentTableModels.createAppointmentTableModels(days);
        SetTables.appointments(appointmentTable, appointmentIDColumn, appointmentTitleColumn, appointmentDescriptionColumn, appointmentLocationColumn, appointmentContactColumn, appointmentTypeColumn, appointmentStartColumn, appointmentEndColumn, appointmentCustomerIDColumn, appointmentUserIDColumn, days);
    }

    /**
     * Sets the days to the number of days selected by the user (7)
     * @param actionEvent ActionEvent
     * @throws SQLException SQLException
     */
    @FXML
    public void weekRadioButton(ActionEvent actionEvent) throws SQLException {
        // Set days to 7
        days = 7;
        AppointmentTableModels.clear();
        AppointmentTableModels.createAppointmentTableModels(days);
        SetTables.appointments(appointmentTable, appointmentIDColumn, appointmentTitleColumn, appointmentDescriptionColumn, appointmentLocationColumn, appointmentContactColumn, appointmentTypeColumn, appointmentStartColumn, appointmentEndColumn, appointmentCustomerIDColumn, appointmentUserIDColumn, days);
    }

    /**
     * Cancels the appointments view and returns to the previous screen
     * @param actionEvent ActionEvent
     */
    @FXML
    public void backButtonPushed(ActionEvent actionEvent) {
        Window.show(actionEvent, "/View/Welcome.fxml");
    }

    /**
     * Opens modify appointment screen
     * @param actionEvent ActionEvent
     */
    @FXML
    public void modifyAppointmentButtonPushed(ActionEvent actionEvent) {
        // Get selected appointment ID
        selectedAppointmentID = appointmentTable.getSelectionModel().getSelectedItem().getID();
        // Pass appointment ID to modify appointment window
        Window.show(actionEvent, "/View/ModifyAppointment.fxml");
    }

    /**
     * Deletes the selected appointment
     * @param actionEvent ActionEvent
     * Lambda expression used to delete appointment
     */
    @FXML
    public void deleteAppointmentButtonPushed(ActionEvent actionEvent) {
        // Alert user to confirm deletion of appointment
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Are you sure you want to delete this appointment?");
        alert.setContentText("This action cannot be undone.");
        // Lambda expression to handle user response
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Delete Appointment from database
                //Get selected appointment ID
                int appointment = appointmentTable.getSelectionModel().getSelectedItem().getID();
                Appointments.deleteAppointment(appointment);
            }
        });
        SetTables.appointments(appointmentTable, appointmentIDColumn, appointmentTitleColumn, appointmentDescriptionColumn, appointmentLocationColumn, appointmentContactColumn, appointmentTypeColumn, appointmentStartColumn, appointmentEndColumn, appointmentCustomerIDColumn, appointmentUserIDColumn, days);
    }

    /**
     * Opens add appointment screen
     * @param actionEvent ActionEvent
     */
    @FXML
    public void addAppointmentButtonPushed(ActionEvent actionEvent) {
        Window.show(actionEvent, "/View/AddAppointment.fxml");
    }

    /**
     * Initializes the appointments view
     * @param url URL
     * @param resourceBundle  ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set week radio button to default
        weekRadioButton.setSelected(true);
        // Create models for past 7 days
        AppointmentTableModels.clear();
        try {
            AppointmentTableModels.createAppointmentTableModels(7);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SetTables.appointments(appointmentTable, appointmentIDColumn, appointmentTitleColumn, appointmentDescriptionColumn, appointmentLocationColumn, appointmentContactColumn, appointmentTypeColumn, appointmentStartColumn, appointmentEndColumn, appointmentCustomerIDColumn, appointmentUserIDColumn, 7);

        //Get system language
        ResourceBundle language = DetermineLanguage.getLanguage();
        //Set language value for all labels and buttons and table columns
        appointmentLabel.setText(language.getString("appointmentLabel"));
        appointmentRangeLabel.setText(language.getString("appointmentRangeLabel"));
        monthRadioButton.setText(language.getString("monthRadioButton"));
        weekRadioButton.setText(language.getString("weekRadioButton"));
        appointmentIDColumn.setText(language.getString("appointmentIDColumn"));
        appointmentTitleColumn.setText(language.getString("titleLabel"));
        appointmentDescriptionColumn.setText(language.getString("descriptionLabel"));
        appointmentLocationColumn.setText(language.getString("locationLabel"));
        appointmentContactColumn.setText(language.getString("contactLabel"));
        appointmentTypeColumn.setText(language.getString("typeLabel"));
        appointmentStartColumn.setText(language.getString("appointmentStartColumn"));
        appointmentEndColumn.setText(language.getString("appointmentEndColumn"));
        appointmentCustomerIDColumn.setText(language.getString("customerIDLabel"));
        appointmentUserIDColumn.setText(language.getString("userIDLabel"));
        backButton.setText(language.getString("backButton"));
        addAppointmentButton.setText(language.getString("addButton"));
        modifyAppointmentButton.setText(language.getString("modifyButton"));
        deleteAppointmentButton.setText(language.getString("deleteButton"));
    }


}
