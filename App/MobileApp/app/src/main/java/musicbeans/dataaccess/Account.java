package musicbeans.dataaccess;



import musicbeans.entities.Client;
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
        Connection connection =  Connector.getInstance().getConnection();
        if(connection != null)
        {
            try
            {
                PreparedStatement pst = connection.prepareStatement("select username from Account where username = ?");
                pst.setString(1,client.getUsername());
                ResultSet rs = pst.executeQuery();
                boolean exits = rs.next();
                if(exits) return Status.REPEATED_USER;
                pst = connection.prepareStatement("insert into Account values(?,?,?)");
                pst.setString(1,client.getUsername());
                pst.setString(2,client.getPassword());
                pst.setBytes(3,client.getProfile_photo());
                pst.executeUpdate();

                pst = connection.prepareStatement("insert into Client values(?,?)");
                pst.setString(1,client.getUsername());
                pst.setString(2,client.getName());
                pst.executeUpdate();
                return Status.REGISTERED;
            } catch (SQLException e)
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
                PreparedStatement pst = connection.prepareStatement("exec login_user ? ?");
                pst.setString(1,client.getUsername());
                pst.setString(2,client.getPassword());
                ResultSet rs = pst.executeQuery();
                boolean exits = rs.next();
                if(exits)
                {

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
