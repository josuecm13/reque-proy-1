package musicbeans.entities;

public abstract class Account
{
    private String username;
    private String password;
    private byte[] profile_photo;
    private String photo_b64;

    public Account() {}

    public Account(String username, String password)
    {
        this.username = username;
        this.password = password;
        this.profile_photo = null;
        this.photo_b64 ="";
    }

    public Account(String username, String password, byte[] profile_photo)
    {
        this.username = username;
        this.password = password;
        this.profile_photo = profile_photo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getProfile_photo() {
        return profile_photo;
    }

    public void setProfile_photo(byte[] profile_photo) {
        this.profile_photo = profile_photo;
    }

    public String getPhoto_b64() {
        return photo_b64;
    }

    public void setPhoto_b64(String photo_b64) {
        this.photo_b64 = photo_b64;
    }
}
