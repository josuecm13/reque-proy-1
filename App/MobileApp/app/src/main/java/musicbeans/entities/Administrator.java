package musicbeans.entities;

public class Administrator extends Account
{
    public Administrator() {
    }

    public Administrator(String username, String password) {
        super(username, password);
    }

    public Administrator(String username, String password, byte[] profile_photo) {
        super(username, password, profile_photo);
    }
}
