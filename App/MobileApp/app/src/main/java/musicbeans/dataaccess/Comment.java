package musicbeans.dataaccess;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import musicbeans.entities.NewsItem;

public class Comment {

    public static List<musicbeans.entities.Comment> getComments(NewsItem item){
        List<musicbeans.entities.Comment> result = new ArrayList<>();
        Connection connection = Connector.getConnection2();
        PreparedStatement pst=null;
        ResultSet rs=null;
        if (connection != null){
            try {
                // TODO: corregir el tiempo de las clases, no esta guardando las horas, solo la fecha
                pst = connection.prepareStatement
                        ("Select c.* " +
                                "from Comment c inner join News n on (c.news_date = n.date)" +
                        " where n.title = ? and n.author = ? and n.body = ?" );
                pst.setString(1,item.getTitle());
                pst.setString(2,item.getAuthor());
                pst.setString(3,item.getBody());
                rs = pst.executeQuery();
                while (rs.next()){
                    result.add(new musicbeans.entities.Comment(rs.getString("author"),rs.getString("comment"),rs.getDate("date")));
                }
            }catch (SQLException ex){

            }finally{
                if (rs != null) try { rs.close(); } catch(Exception e) {}
                if (pst != null) try { pst.close(); } catch(Exception e) {}
                if (connection != null) try { connection.close(); } catch(Exception e) {}
            }
        }
        return result;
    }


}
