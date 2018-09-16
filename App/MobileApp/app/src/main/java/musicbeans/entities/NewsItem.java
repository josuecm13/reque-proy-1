package musicbeans.entities;

import java.sql.Date;

public class NewsItem {
    
    private String title;
    private String body;
    private byte[] img;
    private Account author;
    private Date date;

    public NewsItem(String title, String body, byte[] img, Account author, Date date) {
        this.title = title;
        this.body = body;
        this.img = img;
        this.author = author;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public Account getAuthor() {
        return author;
    }

    public void setAuthor(Account author) {
        this.author = author;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
