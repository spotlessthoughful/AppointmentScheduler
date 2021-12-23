package HelperFunctions;

import Database.AppointmentTableModels;
import Database.Customers;
import Model.AppointmentTableModel;
import Model.Customer;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


public class SetTables {

    /**
     * Set the table columns for the customer table
     * @param table the table to set the columns for
     * @param customerIDColumn the column to set the customer ID for
     * @param customerNameColumn the column to set the customer name for
     * @param customerAddressColumn the column to set the customer address for
     * @param customerPostalCodeColumn the column to set the customer postal code for
     * @param customerPhoneColumn the column to set the customer phone for
     */
    public static void customers(TableView<Customer> table,
                                 TableColumn<Customer, Integer> customerIDColumn,
                                 TableColumn<Customer, String> customerNameColumn,
                                 TableColumn<Customer, String>  customerAddressColumn,
                                 TableColumn<Customer, String>  customerPostalCodeColumn,
                                 TableColumn<Customer, String>  customerPhoneColumn) {
        table.setItems(Customers.getCustomers());
        // Set the table columns
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPostalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
    }

    /**
     * Set the table columns for the appointment table
     * @param table the table to set the columns for
     * @param appointmentIDColumn the column to set the appointment ID for
     * @param appointmentTitleColumn the column to set the appointment title for
     * @param appointmentDescriptionColumn the column to set the appointment description for
     * @param appointmentLocationColumn the column to set the appointment location for
     * @param appointmentContactColumn the column to set the appointment contact for
     * @param appointmentTypeColumn the column to set the appointment type for
     * @param appointmentStartColumn the column to set the appointment start for
     * @param appointmentEndColumn the column to set the appointment end for
     * @param appointmentCustomerIDColumn the column to set the appointment customer ID for
     * @param appointmentUserIDColumn the column to set the appointment user ID for
     * @param days the days to set the appointment start and end for
     */
    public static void appointments(TableView<AppointmentTableModel> table,
                                    TableColumn<AppointmentTableModel, Integer> appointmentIDColumn,
                                    TableColumn<AppointmentTableModel, String> appointmentTitleColumn,
                                    TableColumn<AppointmentTableModel, String> appointmentDescriptionColumn,
                                    TableColumn<AppointmentTableModel, String> appointmentLocationColumn,
                                    TableColumn<AppointmentTableModel, String> appointmentContactColumn,
                                    TableColumn<AppointmentTableModel, String> appointmentTypeColumn,
                                    TableColumn<AppointmentTableModel, String> appointmentStartColumn,
                                    TableColumn<AppointmentTableModel, String> appointmentEndColumn,
                                    TableColumn<AppointmentTableModel, Integer> appointmentCustomerIDColumn,
                                    TableColumn<AppointmentTableModel, Integer> appointmentUserIDColumn,
                                    int days) {
        table.setItems(AppointmentTableModels.getAppointmentTableModels());
        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        appointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointmentLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        appointmentContactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
        appointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        appointmentStartColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        appointmentEndColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        appointmentCustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        appointmentUserIDColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));
    }

}
