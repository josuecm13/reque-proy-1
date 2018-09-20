package musicbeans.dataaccess;



import android.net.Uri;
import android.util.Base64;

import musicbeans.entities.Band;
import musicbeans.entities.Client;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Account
{
    /**
     *
     * @param client
     * @return
     */
    public Status registerClient (Uri uri, Client client)
    {
        Connection connection =  Connector.getConnection2();
        PreparedStatement pst=null;
        ResultSet rs = null;
        boolean inserted =false;
        if(connection != null)
        {
            try
            {
                pst= connection.prepareStatement("select username from Account where username = ?");
                pst.setString(1,client.getUsername());
                rs = pst.executeQuery();
                boolean exits = rs.next();
                if(exits) return Status.REPEATED_USER;
                pst = connection.prepareStatement("insert into Account (username,password)values(?,?)");
                pst.setString(1,client.getUsername());
                pst.setString(2,client.getPassword());
                pst.executeUpdate();

                pst = connection.prepareStatement("insert into Client values (?,?)");
                pst.setString(1,client.getUsername());
                pst.setString(2,client.getName());
                pst.executeUpdate();

                inserted=true;
                return ImageManager.uploadImage(uri,"users/"+client.getUsername());
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


    public Status login(Client client)
    {
        Connection connection = Connector.getConnection2();
        PreparedStatement pst=null;
        ResultSet rs = null;
        if(connection!=null)
        {
            try
            {
                pst = connection.prepareStatement("{call login_user @username=?, @password=?}");
                pst.setString(1,client.getUsername());
                pst.setString(2,client.getPassword());
                rs = pst.executeQuery();
                boolean exits = rs.next();
                if(exits)
                {
                    String type = rs.getString("type");
                    if(type.equals("Client"))return Status.CLIENT;
                    if(type.equals("Admin"))return Status.ADMIN;
                    if(type.equals("Band"))return Status.BAND;
                }
                else return Status.WRONG_CREDENTIALS;

            }
            catch (SQLException e)
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

    public Status registerBand (Uri uri,Band band)
    {
        Connection connection =  Connector.getConnection2();
        PreparedStatement pst=null;
        ResultSet rs = null;
        boolean inserted =false;
        if(connection != null)
        {
            try
            {
                pst= connection.prepareStatement("select username from Account where username = ?");
                pst.setString(1,band.getUsername());
                rs = pst.executeQuery();
                boolean exits = rs.next();

                if(exits) return Status.REPEATED_USER;

                pst = connection.prepareStatement("insert into Account (username,password)values(?,?)");
                pst.setString(1,band.getUsername());
                pst.setString(2,band.getPassword());
                pst.executeUpdate();

                pst = connection.prepareStatement("insert into Band (username,description,rating) values (?,?,?)");
                pst.setString(1,band.getUsername());
                pst.setString(2,band.getDescription());
                pst.setByte(3,band.getRate());
                pst.executeUpdate();

                inserted=true;
                return ImageManager.uploadImage(uri,"users/"+band.getUsername());
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
}
