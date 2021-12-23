package Model;

public class Contact {

    private int id;
    private String name;
    private String email;

    public Contact(
            int id,
            String name,
            String email) {

        this.id = id;
        this.name = name;
        this.email = email;

    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
