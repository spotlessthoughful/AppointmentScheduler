package Controller;

import Database.Customers;
import Database.QueryIds;
import Database.UserLogin;
import HelperFunctions.*;
import Model.Customer;
import Model.FirstLevelDivision;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.ResourceBundle;

public class ModifyCustomerController implements Initializable {

    Customer customer = CustomerController.getSelectedCustomer();

    @FXML
    public TextField name;

    @FXML
    public TextField phoneNumber;

    @FXML
    public TextField street;

    @FXML
    public TextField zip;

    @FXML
    public ComboBox<String> country;

    @FXML
    public ComboBox<String> region;

    @FXML
    public Label customerIDEntryLabel;

    @FXML
    public Button saveButton;

    @FXML
    public Button backButton;

    @FXML
    public Label customerIDLabel;

    @FXML
    public Label modifyCustomerLabel;

    @FXML
    public Label nameLabel;

    @FXML
    public Label phoneNumberLabel;

    @FXML
    public Label streetAddressLabel;

    @FXML
    public Label countryLabel;

    @FXML
    public Label stateProvinceRegionLabel;

    @FXML
    public Label zipCodeLabel;


    Hashtable<String, Integer> countries = new Hashtable<>();
    Hashtable<String, Integer> regions = new Hashtable<>();
    int divisionId;

    /**
     * Method to add countries to the combo box
     * @throws SQLException - SQLException
     */
    public void addCountries() throws SQLException {
        // QueryIds for the country name and ID
        try {
            countries = QueryIds.country("Country_ID", "Country", "countries");
            // Add countries to the ComboBox
            country.setItems(FXCollections.observableArrayList(countries.keySet()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to add regions to the combo box
     * @param id - int
     */
    public void setRegion(Integer id) {
        switch (id) {
            case 1:
                try {
                    // QueryIds regions for the state name and ID
                    regions = QueryIds.region("Division_ID", "Division", "1", "first_level_divisions");
                    // Add states to the ComboBox
                    region.setItems(FXCollections.observableArrayList(regions.keySet()));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                // QueryIds regions for the state name and ID
                try {
                    regions = QueryIds.region("Division_ID", "Division", "2", "first_level_divisions");
                    // Add regions to the ComboBox
                    region.setItems(FXCollections.observableArrayList(regions.keySet()));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                // QueryIds provinces for the state name and ID
                try {
                    regions = QueryIds.region("Division_ID", "Division", "3", "first_level_divisions");
                    // Add provinces to the ComboBox
                    region.setItems(FXCollections.observableArrayList(regions.keySet()));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     * Method to set the countryID
     * @param actionEvent - ActionEvent
     */
    @FXML
    public void countrySelected(ActionEvent actionEvent) {
        // Get value from combo box
        Integer countryId = countries.get(country.getValue());
        // Call setRegion method passing in countryId
        setRegion(countryId);
    }

    /**
     * Method to save the  modified customer
     * @param actionEvent - ActionEvent
     */
    @FXML
    public void saveButtonClicked(ActionEvent actionEvent) {
        // Get values from text fields and combo boxes
        int customerId = customer.getId();
        String customerName = name.getText();
        String customerAddress = street.getText();
        String customerZip;
        String customerPhoneNumber;
        // Get customer zip code and validate it
        if (ValidateInput.isZipCode(zip.getText())) {
            customerZip = zip.getText();
        } else {
            // Show error message
            ApplicationErrors.zipCodeError();
            return;
        }

        // Get customer phone number and validate it
        if (ValidateInput.isPhoneNumber(phoneNumber.getText())) {
            customerPhoneNumber = phoneNumber.getText();
        } else {
            // Show error message
            ApplicationErrors.phoneNumberError();
            return;
        }

        int customerDivisionID = divisionId;
        // Create new customer object
        Customer customer = new Customer(customerId, customerName, customerAddress, customerZip, customerPhoneNumber, SystemTimestamp.getTimestamp(), UserLogin.getCurrentUser(), SystemTimestamp.getTimestamp(), UserLogin.getCurrentUser(), customerDivisionID);
        // Add customer to database
        Customers.updateCustomer(customer);
        // Open Customers.fxml
        Window.show(actionEvent, "/View/Customers.fxml");
    }

    /**
     * Method to cancel the modification of the customer
     * @param actionEvent - ActionEvent
     */
    @FXML
    public void backButtonClicked(ActionEvent actionEvent) {
        // Show Customers.fxml
        Window.show(actionEvent, "/View/Customers.fxml");
    }

    /**
     * Method to set the divisionID to the selected region
     * @param actionEvent event
     */
    @FXML
    public void regionSelected(ActionEvent actionEvent) {
        // Get value from combo box
        divisionId = regions.get(region.getValue());
    }

    /**
     * Method to initialize the Modify Customer window
     * @param url URL
     * @param resourceBundle  ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            addCountries();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Get the division and country name
        FirstLevelDivision division = FirstLevelDivisions.getFirstLevelDivision(customer.getDivisionID());
        String divisionName = division.getDivision();
        String countryName = FirstLevelDivisions.getCountryName(division.getCountryID());
        // Set values for customer
        customerIDEntryLabel.setText(String.valueOf(customer.getId()));
        customerIDEntryLabel.setText(String.valueOf(customer.getId()));
        name.setText(customer.getName());
        street.setText(customer.getAddress());
        zip.setText(customer.getPostalCode());
        phoneNumber.setText(customer.getPhone());
        // Set values for combo boxes
        country.setValue(countryName);
        region.setValue(divisionName);
        // Get system language
        ResourceBundle language = DetermineLanguage.getLanguage();
        // Set labels and button text
        saveButton.setText(language.getString("saveButton"));
        backButton.setText(language.getString("backButton"));
        customerIDLabel.setText(language.getString("customerIDLabel"));
        modifyCustomerLabel.setText(language.getString("modifyCustomerLabel"));
        nameLabel.setText(language.getString("nameLabel"));
        phoneNumberLabel.setText(language.getString("phoneNumberLabel"));
        streetAddressLabel.setText(language.getString("streetAddressLabel"));
        countryLabel.setText(language.getString("countryLabel"));
        stateProvinceRegionLabel.setText(language.getString("stateProvinceRegionLabel"));
        zipCodeLabel.setText(language.getString("zipCodeLabel"));
        setRegion(countries.get(country.getValue()));
    }


}
