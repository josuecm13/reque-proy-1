package musicbeans.entities;

import java.util.Date;

public class Event extends Posts {

    private String location;
    private String title;
    private String description;



    private String banda;

    public Event(Date date, String location, String title, String description, String banda) {
        this.date = date;
        this.location = location;
        this.title = title;
        this.description = description;
        this.banda=banda;

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

    public String getBanda() {
        return banda;
    }

    public void setBanda(String banda) {
        this.banda = banda;
    }

    public java.sql.Date getDateSQL(){return new java.sql.Date(date.getTime());}
}
