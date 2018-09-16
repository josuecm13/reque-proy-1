package musicbeans.entities;

public class Band {

    private String name;
    private String description;
    private byte rate;
    private byte[] banner_photo;
    private String photo_b64;

    public Band(String name, String description, byte rate,
                byte[] banner_photo, String photo_b64) {
        this.name = name;
        this.description = description;
        this.rate = rate;
        this.banner_photo = banner_photo;
        this.photo_b64 = photo_b64;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte getRate() {
        return rate;
    }

    public void setRate(byte rate) {
        this.rate = rate;
    }

    public byte[] getBanner_photo() {
        return banner_photo;
    }

    public void setBanner_photo(byte[] banner_photo) {
        this.banner_photo = banner_photo;
    }

    public String getPhoto_b64() {
        return photo_b64;
    }

    public void setPhoto_b64(String photo_b64) {
        this.photo_b64 = photo_b64;
    }
}
