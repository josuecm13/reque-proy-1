package musicbeans.dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class Client {
    public static List<musicbeans.entities.Client> getClients(){
        Connection connection =  Connector.getConnection2();
        List<musicbeans.entities.Client> result = new ArrayList<>();
        Statement pst=null;
        ResultSet rs=null;
        if (connection != null) {
            try {
                pst = connection.createStatement();
                rs = pst.executeQuery("Select * from Band order by username");
                while (rs.next()) {
                    result.add(new musicbeans.entities.Client(rs.getString("username"),"",
                            null,rs.getString("name")));
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

    public static Status deleteClient(musicbeans.entities.Client client)
    {
        Connection connection = Connector.getConnection2();
        PreparedStatement pst=null;
        ResultSet rs=null;
        if(connection!=null)
        {
            try
            {
                pst = connection.prepareStatement("{call deleteBand @band=?}");
                pst.setString(1,client.getUsername());
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
}
