package musicbeans.entities;

import java.sql.Date;

public class Event {

    private Date date;
    private String location;
    private String title;
    private String description;

    public Event(Date date, String location, String title, String description) {
        this.date = date;
        this.location = location;
        this.title = title;
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
