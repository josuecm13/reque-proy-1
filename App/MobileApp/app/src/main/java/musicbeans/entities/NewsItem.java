package musicbeans.entities;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class NewsItem extends Posts {
    
    private String title;
    private String body;
    private byte[] img;
    private String author;

    public NewsItem(String title, String body, byte[] img, String author, Date date) {
        this.title = title;
        this.body = body;
        this.img = img;
        this.author = author;
        this.date = date;
    }
    public NewsItem(String title,String body,byte[] img)
    {
        this.title=title;
        this.body=body;
        this.img=img;
    }
    public NewsItem(Date date,String author)
    {
        this.date=date;
        this.author=author;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getImageID()
    {
        java.util.Date date = getDate();
        SimpleDateFormat mask = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
        String _date = mask.format(date);
        return _date + "_author_"+getAuthor();
    }
}
