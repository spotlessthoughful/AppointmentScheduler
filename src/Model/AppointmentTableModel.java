package Model;

public class AppointmentTableModel {
    private int id;
    private String title;
    private String description;
    private String location;
    private String contact;
    private String type;
    private String start;
    private String end;
    private int customerID;
    private int userID;

    public AppointmentTableModel(
            int id,
            String title,
            String description,
            String location,
            String contact,
            String type,
            String start,
            String end,
            int customerID,
            int userID){

        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
        this.userID = userID;

    }

    public int getID() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getContact() {
        return contact;
    }

    public String getType() {
        return type;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public int getCustomerID() {
        return customerID;
    }

    public int getUserID() {
        return userID;
    }

    public void setID(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setCustomerID(int customerId) {
        this.customerID = customerId;
    }

    public void setUserID(int userId) {
        this.userID = userId;
    }


}
