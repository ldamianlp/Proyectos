package gadma.gob.ec.appcollectorview;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by titecnico11 on 16/08/2017.
 */

public class tester {
    public static void main(String[] args) {

        Connection connection = null;
        try {

            // Load the Oracle JDBC driver

            String driverName = "oracle.jdbc.driver.OracleDriver";

            Class.forName(driverName);


            // Create a connection to the database

            String serverName = "10.10.0.111";

            String serverPort = "1521";

            String sid = "munidev";

            String url = "jdbc:oracle:thin:@" + serverName + ":" + serverPort + ":" + sid;

            String username = "cabildo";

            String password = "cabildo";

            connection = DriverManager.getConnection(url, username, password);



            System.out.println("Se conecto a la base de datos Oracle munidev correctamente!");


        } catch (ClassNotFoundException e) {

            System.out.println("Could not find the database driver " + e.getMessage());
        } catch (SQLException e) {

            System.out.println("Could not connect to the database " + e.getMessage());
        }

    }

}
