package musicbeans.entities;

public class Client extends Account
{
    private String name;

    public Client(String name) {
        this.name = name;
    }

    public Client(String username, String password, String name) {
        super(username, password);
        this.name = name;
    }

    public Client(String username, String password, byte[] profile_photo, String name) {
        super(username, password, profile_photo);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
