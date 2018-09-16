package musicbeans.dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import musicbeans.entities.Event;
import musicbeans.entities.NewsItem;

public class Posts {

    public static List<musicbeans.entities.Posts> getPosts(String sesion){
        Connection connection =  Connector.getInstance().getConnection();
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
                        ("select * " +
                                "from event n inner join favoriteband fb on (n.band = fb.band) " +
                                "inner join Client c on (c.username = fb.client)" +
                                "where c.username = ?");
                pst.setString(1,sesion);
                rs = pst.executeQuery();
                while (rs.next()){
                    result.add(new Event(rs.getDate("date"),rs.getString("location"),rs.getString("title"),rs.getString("description")));
                }
                Collections.sort(result);
            } catch (SQLException e)
            {
                System.err.println(e.toString());
            }
        }
        return result;
    }

    public static List<musicbeans.entities.Posts> getPosts() {
        Connection connection = Connector.getInstance().getConnection();
        List<musicbeans.entities.Posts> result = new ArrayList<>();
        if (connection != null) {
            try {
                Statement pst = connection.createStatement();
                ResultSet rs = pst.executeQuery("select * from News order by date desc");
                while (rs.next()) {
                    result.add(new NewsItem(rs.getString("title"), rs.getString("body"), rs.getBytes("photo"), rs.getString("author"), rs.getDate("date")));
                }
                pst = connection.createStatement();
                rs = pst.executeQuery("select * from Event order by date desc");
                while (rs.next()){
                    result.add(new Event(rs.getDate("date"),rs.getString("location"),rs.getString("title"),rs.getString("description")));
                }
                Collections.sort(result);
            }  catch (SQLException e)
            {
                System.err.println(e.toString());
            }
        }
        return result;
    }

}
