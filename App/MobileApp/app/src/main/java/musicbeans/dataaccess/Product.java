package musicbeans.dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
    public static Status editProduct (musicbeans.entities.Product product)
    {
        Connection connection =  Connector.getConnection2();
        PreparedStatement pst=null;
        ResultSet rs = null;
        if(connection != null)
        {
            try
            {
                pst = connection.prepareStatement("update Product set name=?," +
                                                    "type=?"+
                                                    "stock=?"+
                                                    "price=?"+
                                                    "price=? WHERE id=?");
                pst.setString(1,product.getName());
                pst.setString(2,product.getType());
                pst.setInt(3,product.getStock());
                pst.setDouble(4,product.getPrice());
                pst.setInt(5,product.getID());
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
    public static List<musicbeans.entities.Product> getProducts ()
    {
        Connection connection =  Connector.getConnection2();
        PreparedStatement pst=null;
        ResultSet rs = null;
        List<musicbeans.entities.Product> list = new ArrayList<>();
        if(connection != null)
        {
            try
            {
                pst = connection.prepareStatement("select * from Product where band = ?");
                pst.setString(1,Sesion.getInstance().getUsername());
                rs=pst.executeQuery();
                while(rs.next())
                {
                    musicbeans.entities.Product product = new musicbeans.entities.Product(rs.getInt("id"),
                                                                                        rs.getString("band"),
                            rs.getString("name"),rs.getString("type"),
                            rs.getDouble("price"),
                            rs.getInt("stock"),null);
                    list.add(product);
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

    public static Status deleteProduct (musicbeans.entities.Product product)
    {
        Connection connection = Connector.getConnection2();
        PreparedStatement pst=null;
        ResultSet rs=null;
        if(connection!=null)
        {
            try
            {
                pst = connection.prepareStatement("{call deleteProduct @product=?}");
                pst.setInt(1,product.getID());
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
