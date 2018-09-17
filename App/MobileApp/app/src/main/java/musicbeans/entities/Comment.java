package musicbeans.entities;

import java.sql.Date;

public class Comment {

    private String username;
    private String comment;
    private Date date;


    public Comment(String username, String comment, Date date){
        this.username = username;
        this.comment =comment;
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
