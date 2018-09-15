package musicbeans.dataaccess;


import android.os.StrictMode;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Singleton class Connector
 */
public class Connector
{
    private static Connector connector = null;
    private static Connection connection = null;
    private static final String url = "jdbc:jtds:sqlserver://smq-basedatos.database.windows.net:1433/MusicBeansDB;user=stmoya;password=Basedatos1;";
    private Connector () {}

    /**
     * Gets a the instance of Connector. If the connection property is null, creates a new connection to the database
     * @return The instance of Connector
     */
    public static synchronized Connector getInstance()
    {
        if(connector == null)
        {
            connector = new Connector();
            try
            {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
                connection = DriverManager.getConnection(url);
            }
            catch (Exception e)
            {
                System.err.println(e.toString());
                connector = null;
            }
        }
        return connector;
    }
    public synchronized Connection getConnection()
    {
        return connection;
    }
}
