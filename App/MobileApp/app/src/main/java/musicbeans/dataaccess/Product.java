package musicbeans.dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import musicbeans.entities.Event;
import musicbeans.entities.Sesion;

public class Product
{
    public static Status addProduct (musicbeans.entities.Product product)
    {
        Connection connection =  Connector.getConnection2();
        PreparedStatement pst=null;
        ResultSet rs = null;
        if(connection != null)
        {
            try
            {
                pst = connection.prepareStatement("insert into Product (band,name,type,stock,price)values(?,?,?,?,?)");
                pst.setString(1,Sesion.getInstance().getUsername());
                pst.setString(2,product.getName());
                pst.setString(3,product.getType());
                pst.setInt(4,product.getStock());
                pst.setDouble(5,product.getPrice());
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
}
