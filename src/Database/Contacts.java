package Database;

import Model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class Contacts {

    /**
     * Function that queries the contacts table and returns an ObservableList of Contact Objects
     * @return ObservableList of Contact Objects
     */
    public static ObservableList<String> getContactsNames() {
        ObservableList<String> contacts = FXCollections.observableArrayList();
        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT Contact_Name FROM Contacts");
            while (resultSet.next()) {
                contacts.add(resultSet.getString("Contact_Name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contacts;
    }

    /**
     * Function that queries the contacts table with given Contact_ID and returns a Contact Object
     * @param contactID Contact_ID of the Contact
     * @return Contact Object
     */
    public static Contact getContact(int contactID) {
        Contact contact = null;
        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Contacts WHERE Contact_ID = " + contactID);
            while (resultSet.next()) {
                contact = new Contact(contactID,
                        resultSet.getString("Contact_Name"),
                        resultSet.getString("Email"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contact;
    }

    /**
     * Function that queries the contacts table with given Contact_Name and returns a Contact Object
     * @param contactName Contact_Name of the Contact
     * @return Contact Object
     */
    public static Contact getContactName(String contactName) {
        Contact contact = null;
        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Contacts WHERE Contact_Name = '" + contactName + "'");
            while (resultSet.next()) {
                contact = new Contact(resultSet.getInt("Contact_ID"),
                        resultSet.getString("Contact_Name"),
                        resultSet.getString("Email"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contact;
    }

}
