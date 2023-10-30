package ec.gob.gadma.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by titecnico11 on 16/08/2017.
 */
public class GadmaConnection {
    public Connection getConnection(
            String inHost,
            String inPort,
            String inSID,
            String inUser,
            String inPassword
    ){
        Connection connection = null;
        try {
            // Load the Oracle JDBC driver
            //String driverName = "oracle.jdbc.driver.OracleDriver";//"oracle.jdbc.driver.OracleDriver";
//            Class.forName("oracle.jdbc.OracleDriver");
            // Create a connection to the database
            /*String serverName = "10.10.0.148";
            String serverPort = "1521";
            String sid = "des";*/
//            String url = "jdbc:oracle:thin:@" + inHost + ":" + inPort + ":" + inSID;
           /* String username = "GADMAPPS";
            String password = "GADMAPPS";*/
            //connection = DriverManager.getConnection(url, inUser, inPassword);

            //step1 load the driver class
            Class.forName("oracle.jdbc.driver.OracleDriver");

//step2 create  the connection object
            //Class.forName ("oracle.jdbc.driver.OracleDriver");
            Connection con=DriverManager.getConnection("jdbc:oracle:thin:@"+inHost+":" + inPort + ":" + inSID,inUser,inPassword);

            /*connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@"+inHost+":" + inPort + "/" + inSID, inUser, inPassword);*/
            //connection.close();
            //System.out.println("Se conecto a la base de datos Oracle munidev correctamente!");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        /*catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/
        catch (Exception e) {
            e.printStackTrace();
        }finally {
            return connection;
        }

    }
}