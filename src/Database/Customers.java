package Database;

import Model.Customer;
import Model.FirstLevelDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Customers {

    /**
     * Method that gets all customers from the database and returns an observable list of customers
     * @return ObservableList of customers
     */
    public static ObservableList<Customer> getCustomers() {
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM customers");
            while (resultSet.next()) {
                customers.add(new Customer(
                        resultSet.getInt("Customer_ID"),
                        resultSet.getString("Customer_Name"),
                        resultSet.getString("Address"),
                        resultSet.getString("Postal_Code"),
                        resultSet.getString("Phone"),
                        resultSet.getString("Create_Date"),
                        resultSet.getString("Created_By"),
                        resultSet.getString("Last_Update"),
                        resultSet.getString("Last_Updated_By"),
                        resultSet.getInt("Division_ID")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    /**
     * Method that gets a customer name from a customer id
     * @param customerID int
     * @return String
     */
    public static String getCustomerName(int customerID) {
        String customerName = "";
        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT Customer_Name FROM customers WHERE Customer_ID = " + customerID);
            while (resultSet.next()) {
                customerName = resultSet.getString("Customer_Name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerName;
    }

    /**
     * Method that creates a new customer and adds it to the database
     * @param customer Customer
     */
    public static void createCustomer(Customer customer) {
        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            statement.executeUpdate("INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone,"
                    + " Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) "
                    + "VALUES ("
                    + customer.getId()
                    + ", '"
                    + customer.getName()
                    + "', '"
                    + customer.getAddress()
                    + "', "
                    + "'"
                    + customer.getPostalCode()
                    + "', '"
                    + customer.getPhone()
                    + "', '"
                    + customer.getCreateDate()
                    + "', '"
                    + customer.getCreatedBy()
                    + "', '"
                    + customer.getLastUpdate()
                    + "', '"
                    + customer.getLastUpdatedBy()
                    + "', "
                    + customer.getDivisionID()
                    + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that updates a customer and updates it in the database
     * @param customer Customer to be updated
     */
    public static void updateCustomer(Customer customer) {
        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            statement.executeUpdate("UPDATE customers SET Customer_Name = '"
                    + customer.getName()
                    + "', Address = '"
                    + customer.getAddress()
                    + "', Postal_Code = '"
                    + customer.getPostalCode()
                    + "', Phone = '"
                    + customer.getPhone()
                    + "', Create_Date = '"
                    + customer.getCreateDate()
                    + "', Created_By = '"
                    + customer.getCreatedBy()
                    + "', Last_Update = '"
                    + customer.getLastUpdate()
                    + "', Last_Updated_By = '"
                    + customer.getLastUpdatedBy()
                    + "', Division_ID = "
                    + customer.getDivisionID()
                    + " WHERE Customer_ID = "
                    + customer.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that deletes a customer and deletes it from the database
     * @param customer int
     */
    public static void deleteCustomer(Customer customer) {
        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            boolean toDelete = Appointments.customerExists(customer.getId());
            if (!toDelete) {
                statement.executeUpdate("DELETE FROM customers WHERE Customer_ID = " + customer.getId());
            } else {
                // Alert user that customer has appointments and cannot be deleted
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Customer has appointments");
                alert.setContentText("Customer cannot be deleted");
                alert.showAndWait();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
