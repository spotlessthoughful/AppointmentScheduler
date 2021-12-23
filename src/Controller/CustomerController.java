package Controller;

import Database.Customers;
import HelperFunctions.DetermineLanguage;
import HelperFunctions.SetTables;
import HelperFunctions.Window;
import Model.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    private static Customer selectedCustomer;

    public static Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    @FXML
    public TableView<Customer> customerTable;

    @FXML
    public TableColumn<Customer, Integer> customerIDColumn;

    @FXML
    public TableColumn<Customer, String> customerNameColumn;

    @FXML
    public TableColumn<Customer, String> customerAddressColumn;

    @FXML
    public TableColumn<Customer, String>  customerPostalCodeColumn;

    @FXML
    public TableColumn<Customer, String>  customerPhoneNumberColumn;

    @FXML
    public Button addCustomerButton;

    @FXML
    public Button modifyCustomerButton;

    @FXML
    public Button deleteCustomerButton;

    @FXML
    public Button backButton;

    @FXML
    public Label customerLabel;

    /**
     * Open add customer window
     * @param actionEvent action event
     */
    @FXML
    public void addCustomerPushed(ActionEvent actionEvent) {
        Window.show(actionEvent, "/View/AddCustomer.fxml");
    }

    /**
     * Open modify customer window
     * @param actionEvent action event
     */
    @FXML
    public void modifyCustomerPushed(ActionEvent actionEvent) {
        // Set selected customer
        selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        // Open modify customer window
        Window.show(actionEvent, "/View/ModifyCustomer.fxml");
    }

    /**
     * Delete selected customer
     * @param actionEvent action event
     * Lambda expression
     */
    @FXML
    public void deleteCustomerButtonPushed(ActionEvent actionEvent) {
        // Alert user to confirm deletion of customer
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Are you sure you want to delete this customer?");
        alert.setContentText("This action cannot be undone.");
        // Lambda expression for handling user response
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Delete customer from database
                Customer customer = customerTable.getSelectionModel().getSelectedItem();
                Customers.deleteCustomer(customer);
            }
        });
        SetTables.customers(customerTable, customerIDColumn, customerNameColumn, customerAddressColumn, customerPostalCodeColumn, customerPhoneNumberColumn);
    }

    /**
     * Back to previous window
     * @param actionEvent action event
     */
    @FXML
    public void backButtonPushed(ActionEvent actionEvent) {
        Window.show(actionEvent, "/View/Welcome.fxml");
    }

    /**
     * Initialize customer controller
     * @param url url
     * @param resourceBundle resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SetTables.customers(customerTable, customerIDColumn, customerNameColumn, customerAddressColumn, customerPostalCodeColumn, customerPhoneNumberColumn);
        // Get system language
        ResourceBundle language = DetermineLanguage.getLanguage();
        // Set labels, columns, buttons
        customerLabel.setText(language.getString("customerLabel"));
        customerIDColumn.setText(language.getString("appointmentIDColumn"));
        customerNameColumn.setText(language.getString("nameLabel"));
        customerAddressColumn.setText(language.getString("customerAddressColumn"));
        customerPostalCodeColumn.setText(language.getString("customerPostalCodeColumn"));
        customerPhoneNumberColumn.setText(language.getString("phoneNumberLabel"));
        addCustomerButton.setText(language.getString("addButton"));
        modifyCustomerButton.setText(language.getString("modifyButton"));
        deleteCustomerButton.setText(language.getString("deleteButton"));
        backButton.setText(language.getString("backButton"));
    }

}
