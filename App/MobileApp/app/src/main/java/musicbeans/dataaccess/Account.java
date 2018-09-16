package musicbeans.dataaccess;



import android.util.Base64;

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
    public Status registerClient (Client client)
    {
        Connection connection =  Connector.getConnection2();
        if(connection != null)
        {
            try
            {
                PreparedStatement pst= connection.prepareStatement("select username from Account where username = ?");
                pst.setString(1,client.getUsername());

                ResultSet rs = pst.executeQuery();
                boolean exits = rs.next();
                pst.close();
                rs.close();
                connection.close();
                if(exits) return Status.REPEATED_USER;
                connection=Connector.getConnection2();
               // pst = connection.prepareStatement("insert into Account (username,password,photo_b64)values(?,?,?)");
                //pst.setString(1,client.getUsername());
                //pst.setString(2,client.getPassword());
                //pst.setBytes(3,client.getProfile_photo());
               // pst.setBinaryStream(3,new ByteArrayInputStream(client.getProfile_photo()),client.getProfile_photo().length);
                //pst.setString(3, Base64.encodeToString(client.getProfile_photo(),Base64.NO_WRAP));
                //pst.executeUpdate();

                pst = connection.prepareStatement("insert into Test values ('"+client.getName()+"','"+Base64.encodeToString(client.getProfile_photo(),Base64.NO_WRAP)+"')");
                pst.setMaxFieldSize(Integer.MAX_VALUE);

                //pst.setString(1,client.getUsername());
               // pst.setString(2, Base64.encodeToString(client.getProfile_photo(),Base64.NO_WRAP));
                pst.executeUpdate();

                pst.close();
                connection.close();
                /*connection=Connector.getInstance().getConnection();
                pst = connection.prepareStatement("insert into Client values (?,?)");
                pst.setString(1,client.getUsername());
                pst.setString(2,client.getName());
                pst.executeUpdate();
                pst.close();
                connection.close();*/
                return Status.REGISTERED;
            } catch (Exception e)
            {
                System.err.println(e.toString());
            }
        }
        return Status.NETWORK_ERROR;

    }


    public Status login(Client client)
    {
        Connection connection = Connector.getInstance().getConnection();
        if(connection!=null)
        {
            try
            {
                PreparedStatement pst = connection.prepareStatement("{call login_user @username=?, @password=?}");
                pst.setString(1,client.getUsername());
                pst.setString(2,client.getPassword());
                ResultSet rs = pst.executeQuery();
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
        }
        return Status.NETWORK_ERROR;
    }
}
