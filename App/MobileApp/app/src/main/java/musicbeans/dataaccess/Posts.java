package musicbeans.dataaccess;

import android.net.Uri;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import musicbeans.entities.Band;
import musicbeans.entities.Event;
import musicbeans.entities.NewsItem;
import musicbeans.entities.Sesion;

public class Posts {

    public static List<musicbeans.entities.Posts> getPosts(String sesion){
        Connection connection =  Connector.getConnection2();
        List<musicbeans.entities.Posts> result = new ArrayList<>();
        if(connection != null){
            try{
                PreparedStatement pst = connection.prepareStatement
                        ("select * " +
                                "from News n inner join favoriteband fb on (n.author = fb.band) " +
                                "inner join Client c on (c.username = fb.client)" +
                                "where c.username = ?");
                pst.setString(1,sesion);
                ResultSet rs = pst.executeQuery();
                while (rs.next()){
                    result.add(new NewsItem(rs.getString("title"),rs.getString("body"),rs.getBytes("photo"),rs.getString("author"),rs.getDate("date")));
                }
                pst = connection.prepareStatement
                        ("select n.* " +
                                "from event n inner join favoriteband fb on (n.band = fb.band) " +
                                "inner join Client c on (c.username = fb.client)" +
                                "where c.username = ?");
                pst.setString(1,sesion);
                rs = pst.executeQuery();
                while (rs.next()){
                    result.add(new Event(rs.getDate("date"),rs.getString("location"),rs.getString("Title"),rs.getString("description"),rs.getString("band")));
                }
                Collections.sort(result);
                Collections.reverse(result);
            } catch (SQLException e)
            {
                System.err.println(e.toString());
            }
        }
        return result;
    }

    public static List<musicbeans.entities.Posts> getPosts() {
        Connection connection = Connector.getConnection2();
        Statement pst=null;
        ResultSet rs=null;
        List<musicbeans.entities.Posts> result = new ArrayList<>();
        if (connection != null) {
            try {
                pst = connection.createStatement();
                rs = pst.executeQuery("select * from News order by date desc");
                while (rs.next()) {
                    result.add(new NewsItem(rs.getString("title"), rs.getString("body"), null, rs.getString("author"), rs.getDate("date")));
                }
                pst = connection.createStatement();
                rs = pst.executeQuery("select * from Event order by date desc");
                while (rs.next()){
                    result.add(new Event(rs.getDate("date"),rs.getString("location"),rs.getString("Title"),rs.getString("description"),rs.getString("band")));
                }
                Collections.sort(result);
                Collections.reverse(result);
            }  catch (Exception e)
            {
                System.err.println(e.toString());
            }
            finally
            {
                if (rs != null) try { rs.close(); } catch(Exception e) {}
                if (pst != null) try { pst.close(); } catch(Exception e) {}
                if (connection != null) try { connection.close(); } catch(Exception e) {}
            }
        }
        return result;
    }
    public static List<musicbeans.entities.Posts> getPostsAdmin() {
        Connection connection = Connector.getConnection2();
        PreparedStatement pst=null;
        ResultSet rs=null;
        List<musicbeans.entities.Posts> result = new ArrayList<>();
        if (connection != null) {
            try {
                pst = connection.prepareStatement("select * from News where author=? order by date desc ");
                pst.setString(1, Sesion.getInstance().getUsername());
                rs = pst.executeQuery();
                while (rs.next()) {
                    result.add(new NewsItem(rs.getString("title"), rs.getString("body"), null, rs.getString("author"), rs.getDate("date")));
                }
                Collections.sort(result);
                Collections.reverse(result);
            }  catch (Exception e)
            {
                System.err.println(e.toString());
            }
            finally
            {
                if (rs != null) try { rs.close(); } catch(Exception e) {}
                if (pst != null) try { pst.close(); } catch(Exception e) {}
                if (connection != null) try { connection.close(); } catch(Exception e) {}
            }
        }
        return result;
    }
    public static Status publishNews(Uri uri, NewsItem news)
    {
        Connection connection =  Connector.getConnection2();
        PreparedStatement pst=null;
        ResultSet rs = null;
        boolean inserted = false;
        if(connection != null)
        {
            try
            {
                pst = connection.prepareStatement("insert into News (author,date,title,body) values (?,getdate(),?,?)");
                pst.setString(1,Sesion.getInstance().getUsername());
                pst.setString(2,news.getTitle());
                pst.setString(3,news.getBody());
                pst.executeUpdate();
                inserted=true;
                pst = connection.prepareStatement("select top 1 date,author from News where author=? order by date desc");
                pst.setString(1,Sesion.getInstance().getUsername());
                rs=pst.executeQuery();
                if(rs.next())
                {
                    java.sql.Date date = rs.getDate("date");
                    String author = rs.getString("author");
                    NewsItem _news = new NewsItem(date,author);
                    return ImageManager.uploadImage(uri,"news/"+_news.getImageID());
                }
                return Status.REGISTERED;
            } catch (Exception e)
            {
                if(inserted)return Status.IMG_FAILED;
                System.err.println(e.toString());
            }
            finally
            {
                if (rs != null) try { rs.close(); } catch(Exception e) {}
                if (pst != null) try { pst.close(); } catch(Exception e) {}
                if (connection != null) try { connection.close(); } catch(Exception e) {}
            }
        }
        return Status.NETWORK_ERROR;
    }

    public static Status addEvent (Event event)
    {
        Connection connection =  Connector.getConnection2();
        PreparedStatement pst=null;
        ResultSet rs = null;
        if(connection != null)
        {
            try
            {
                pst= connection.prepareStatement("select * from Event where date = ? and band=?");
                pst.setDate(1,event.getDateSQL());
                pst.setString(2,Sesion.getInstance().getUsername());
                rs = pst.executeQuery();
                boolean exits = rs.next();

                if(exits) return Status.REPEATED_USER;

                pst = connection.prepareStatement("insert into Event (date,band,location,description,Title)values(?,?,?,?,?)");
                pst.setDate(1,event.getDateSQL());
                pst.setString(2,Sesion.getInstance().getUsername());
                pst.setString(3,event.getLocation());
                pst.setString(4,event.getDescription());
                pst.setString(5,event.getTitle());
                pst.executeUpdate();

                return Status.REGISTERED;
            } catch (Exception e)
            {
                System.err.println(e.toString());
            }
            finally
            {
                if (rs != null) try { rs.close(); } catch(Exception e) {}
                if (pst != null) try { pst.close(); } catch(Exception e) {}
                if (connection != null) try { connection.close(); } catch(Exception e) {}
            }
        }
        return Status.NETWORK_ERROR;

    }

    public static List<musicbeans.entities.Event> getEvents ()
    {
        Connection connection =  Connector.getConnection2();
        PreparedStatement pst=null;
        ResultSet rs = null;
        List<musicbeans.entities.Event> list = new ArrayList<>();
        if(connection != null)
        {
            try
            {
                pst = connection.prepareStatement("select * from Event where band = ?");
                pst.setString(1,Sesion.getInstance().getUsername());
                rs=pst.executeQuery();
                while(rs.next())
                {
                    Timestamp timestamp = rs.getTimestamp("date");
                    Date d = new Date(timestamp.getTime());
                    musicbeans.entities.Event event = new musicbeans.entities.Event(d,
                            rs.getString("location"),
                            rs.getString("Title"),
                            rs.getString("description"),
                            rs.getString("band"));
                    list.add(event);
                }

            } catch (Exception e)
            {
                System.err.println(e.toString());
            }
            finally
            {
                if (rs != null) try { rs.close(); } catch(Exception e) {}
                if (pst != null) try { pst.close(); } catch(Exception e) {}
                if (connection != null) try { connection.close(); } catch(Exception e) {}
            }
        }
        return list;
    }
    public static List<musicbeans.entities.Event> getEvents (String band)
    {
        Connection connection =  Connector.getConnection2();
        PreparedStatement pst=null;
        ResultSet rs = null;
        List<musicbeans.entities.Event> list = new ArrayList<>();
        if(connection != null)
        {
            try
            {
                pst = connection.prepareStatement("select * from Event where band = ?");
                pst.setString(1,band);
                rs=pst.executeQuery();
                while(rs.next())
                {
                    musicbeans.entities.Event event = new musicbeans.entities.Event(rs.getDate("date"),
                            rs.getString("location"),
                            rs.getString("Title"),
                            rs.getString("description"),
                            rs.getString("band"));
                    list.add(event);
                }

            } catch (Exception e)
            {
                System.err.println(e.toString());
            }
            finally
            {
                if (rs != null) try { rs.close(); } catch(Exception e) {}
                if (pst != null) try { pst.close(); } catch(Exception e) {}
                if (connection != null) try { connection.close(); } catch(Exception e) {}
            }
        }
        return list;
    }
    public static Status deleteEvent (musicbeans.entities.Event event)
    {
        Connection connection = Connector.getConnection2();
        PreparedStatement pst=null;
        ResultSet rs=null;
        if(connection!=null)
        {
            try
            {
                pst = connection.prepareStatement("select * from event where date=? and band=?");
                pst.setTimestamp(1,new Timestamp(event.getDate().getTime()));
                pst.setString(2,event.getBanda());
                rs = pst.executeQuery();
                boolean exits = rs.next();
                if(exits)
                {
                    pst = connection.prepareStatement("delete event where date=? and band=?");
                    pst.setTimestamp(1,new Timestamp(event.getDate().getTime()));
                    pst.setString(2,event.getBanda());
                    pst.executeUpdate();
                    return Status.OK;
                }
                else return Status.FAILED;
            }
            catch (Exception e)
            {
                System.err.println(e.toString());
            }
            finally
            {
                if (pst != null) try { pst.close(); } catch(Exception ignored) {}
                if (rs != null) try { rs.close(); } catch(Exception ignored) {}
                if (connection != null) try { connection.close(); } catch(Exception ignored) {}
            }
        }
        return Status.FAILED;
    }
}
