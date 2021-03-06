package musicbeans.dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import musicbeans.entities.Event;
import musicbeans.entities.NewsItem;
import musicbeans.entities.Sesion;

public class Band {

    public static List<musicbeans.entities.Band> getBands(){
        Connection connection =  Connector.getConnection2();
        List<musicbeans.entities.Band> result = new ArrayList<>();
        Statement pst=null;
        ResultSet rs=null;
        if (connection != null) {
            try {
                pst = connection.createStatement();
                rs = pst.executeQuery("Select * from Band order by username");
                while (rs.next()) {
                    result.add(new musicbeans.entities.Band(rs.getString("username"),"",
                            null,"",
                            rs.getString("description"),rs.getByte("rating"),
                            rs.getBytes("banner_photo"),"gege"));
                }
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

    public static musicbeans.entities.Band getBand(String band){
        Connection connection =  Connector.getConnection2();

        PreparedStatement pst=null;
        ResultSet rs=null;
        if (connection != null) {
            try {
                pst = connection.prepareStatement("Select top 1 * from Band where username=?");
                pst.setString(1,band);
                rs = pst.executeQuery();
                while (rs.next()) {
                    return (new musicbeans.entities.Band(rs.getString("username"),"",
                            null,"",
                            rs.getString("description"),rs.getByte("rating"),
                            rs.getBytes("banner_photo"),"gege"));
                }
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
        return null;
    }

    public static boolean validateFavBand(String bandname, String username){
        Connection connection =  Connector.getConnection2();
        PreparedStatement pst=null;
        ResultSet rs=null;
        if (connection != null) {
            try {
                pst = connection.prepareStatement("select * from FavoriteBand where band = ? and client = ?");
                pst.setString(1,bandname);
                pst.setString(2,username);
                rs = pst.executeQuery();
                boolean exists = rs.next();
                if(exists)
                    return true;
            }  catch (Exception e)
            {
                System.err.println(e.toString());
            }
            finally
            {
                if (rs != null) try { rs.close(); } catch(Exception ignored) {}
                if (pst != null) try { pst.close(); } catch(Exception ignored) {}
                if (connection != null) try { connection.close(); } catch(Exception ignored) {}
            }
        }
        return false;
    }


    public static Status addFavorite(String bandname, String username){
        Connection connection =  Connector.getConnection2();
        PreparedStatement pst=null;
        int rs= 0;
        if (connection != null) {
            try {
                pst = connection.prepareStatement("insert into favoriteband values(?,?)");
                pst.setString(1,bandname);
                pst.setString(2,username);
                rs = pst.executeUpdate();

            }  catch (Exception e)
            {
                System.err.println(e.toString());
                return Status.NETWORK_ERROR;
            }
            finally
            {
                if (pst != null) try { pst.close(); } catch(Exception ignored) {}
                if (connection != null) try { connection.close(); } catch(Exception ignored) {}
            }
        }
        return Status.OK;
    }

    public static Status removeFavorite(String bandname, String username){
        Connection connection =  Connector.getConnection2();
        PreparedStatement pst=null;
        int rs= 0;
        if (connection != null) {
            try {
                pst = connection.prepareStatement("DELETE from favoriteband where band= ? and client =?");
                pst.setString(1,bandname);
                pst.setString(2,username);
                rs = pst.executeUpdate();
            }  catch (Exception e)
            {
                System.err.println(e.toString());
                return Status.NETWORK_ERROR;
            }
            finally
            {
                if (pst != null) try { pst.close(); } catch(Exception ignored) {}
                if (connection != null) try { connection.close(); } catch(Exception ignored) {}
            }
        }
        return Status.OK;
    }

    public static Status deleteBand(musicbeans.entities.Band band)
    {
        Connection connection = Connector.getConnection2();
        PreparedStatement pst=null;
        ResultSet rs=null;
        if(connection!=null)
        {
            try
            {
                pst = connection.prepareStatement("{call deleteBand @band=?}");
                pst.setString(1,band.getUsername());
                rs = pst.executeQuery();
                boolean exits = rs.next();
                if(exits)
                {
                    int msg = rs.getInt("msg");
                    if(msg==0)return  Status.OK;
                    else return  Status.FAILED;
                }
                else return Status.NETWORK_ERROR;
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
        return Status.NETWORK_ERROR;
    }
    public static  Status rateBand(String band,byte rate)
    {
        Connection connection =  Connector.getConnection2();
        PreparedStatement pst=null;
        int rs= 0;
        ResultSet _rs=null;
        if (connection != null) {
            try {

                pst = connection.prepareStatement("select top 1 * from rating where band=? and client=?");
                pst.setString(1, band);
                pst.setString(2, Sesion.getInstance().getUsername());
                _rs=pst.executeQuery();
                if(_rs.next())
                {
                    pst = connection.prepareStatement("update rating set rating=? where band=? and client=?");
                    pst.setInt(1, rate);
                    pst.setString(2, band);
                    pst.setString(3, Sesion.getInstance().getUsername());
                    rs = pst.executeUpdate();
                }
                else {
                    pst = connection.prepareStatement("insert into rating (band,client,date,rating) values(?,?,getdate(),?)");
                    pst.setString(1, band);
                    pst.setString(2, Sesion.getInstance().getUsername());
                    pst.setInt(3, rate);
                    rs = pst.executeUpdate();
                }

            }  catch (Exception e)
            {
                System.err.println(e.toString());
                return Status.NETWORK_ERROR;
            }
            finally
            {
                if (pst != null) try { pst.close(); } catch(Exception ignored) {}
                if (connection != null) try { connection.close(); } catch(Exception ignored) {}
                if (_rs != null) try { _rs.close(); } catch(Exception ignored) {}
            }
        }
        return Status.OK;
    }
    public static double getBandRating(String band){
        Connection connection =  Connector.getConnection2();

        PreparedStatement pst=null;
        ResultSet rs=null;
        if (connection != null) {
            try {
                pst = connection.prepareStatement("Select top 1 avg(cast(rating as float)) rat from Rating where band=?");
                pst.setString(1,band);
                rs = pst.executeQuery();
                while (rs.next()) {
                    return rs.getDouble("rat");
                }
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
        return -1;
    }

    public static int getBandRatingClient(String band){
        Connection connection =  Connector.getConnection2();

        PreparedStatement pst=null;
        ResultSet rs=null;
        if (connection != null) {
            try {
                pst = connection.prepareStatement("Select top 1 rating from Rating where band=? and client=?");
                pst.setString(1,band);
                pst.setString(2,Sesion.getInstance().getUsername());
                rs = pst.executeQuery();
                while (rs.next()) {
                    return rs.getInt("rating");
                }
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
        return -1;
    }
}
