package Controller;

import Database.Customers;
import Database.QueryIds;
import Database.UserLogin;
import HelperFunctions.*;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {

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
    public Label customerIDLabel;

    @FXML
    public Button saveButton;

    @FXML
    public Button backButton;

    @FXML
    public Label addCustomerLabel;

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
     * This method populates the country combo box with the countries from the database
     * @throws SQLException - if there is an error with the database
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
     * This method populates the region combo box with the regions from the database
     * @param id - the country id
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
     * This method sets the country id once the user selects a country from the combo box
     * @param actionEvent - the event that triggers the method
     */
    @FXML
    public void countrySelected(ActionEvent actionEvent) {
        // Get value from combo box
        Integer countryId = countries.get(country.getValue());
        // Call setRegion method passing in countryId
        setRegion(countryId);
    }

    /**
     * This method saves the customer to the database
     * @param actionEvent - the event that triggers the method
     */
    @FXML
    public void saveButtonClicked(ActionEvent actionEvent) {
        // Get values from text fields and combo boxes
        int customerId = Sequence.nextCustomerID();
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
        Customers.createCustomer(customer);
        // Open Customers.fxml
        Window.show(actionEvent, "/View/Customers.fxml");
    }

    /**
     * This method cancels the customer creation and returns to the customers screen
     * @param actionEvent - the event that triggers the method
     */
    @FXML
    public void backButtonClicked(ActionEvent actionEvent) {
        // Open Customers.fxml
        Window.show(actionEvent, "/View/Customers.fxml");
    }

    /**
     * This method sets the region id once the user selects a region from the combo box
     * @param actionEvent - the event that triggers the method
     */
    @FXML
    public void regionSelected(ActionEvent actionEvent) {
        // Get value from combo box
        divisionId = regions.get(region.getValue());
    }

    /**
     * This method sets the values for the text fields and combo boxes when the screen is loaded
     * @param url - the url of the screen
     * @param resourceBundle - the resource bundle of the screen
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            addCountries();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        customerIDEntryLabel.setText(String.valueOf(Sequence.nextCustomerID()));
        // Get system language
        ResourceBundle language = DetermineLanguage.getLanguage();
        // Set labels and button text
        saveButton.setText(language.getString("saveButton"));
        backButton.setText(language.getString("backButton"));
        customerIDLabel.setText(language.getString("customerIDLabel"));
        addCustomerLabel.setText(language.getString("addCustomerLabel"));
        nameLabel.setText(language.getString("nameLabel"));
        phoneNumberLabel.setText(language.getString("phoneNumberLabel"));
        streetAddressLabel.setText(language.getString("streetAddressLabel"));
        countryLabel.setText(language.getString("countryLabel"));
        stateProvinceRegionLabel.setText(language.getString("stateProvinceRegionLabel"));
        zipCodeLabel.setText(language.getString("zipCodeLabel"));
    }


}
