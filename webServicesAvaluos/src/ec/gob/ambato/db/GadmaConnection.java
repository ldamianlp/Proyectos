package ec.gob.ambato.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class GadmaConnection{

	private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(GadmaConnection.class);

	public Connection getConnection(){
		DataSource DATA_SOURCE = null;
		Boolean IS_DEPLOYED = false;
		try {
			if(DATA_SOURCE == null){
				try{
					Context env = null;
					String source = null;
					env = (Context)new InitialContext().lookup("java:comp/env");
					source = (String)env.lookup("DATA-SOURCES");
					DATA_SOURCE = (DataSource)new InitialContext().lookup(source);
					IS_DEPLOYED= true;
				} catch (NamingException e) {
					IS_DEPLOYED= false;
				}
			}
			/*if(!IS_DEPLOYED){
				System.out.println("CON LOCAL");
				return getConnectionLocal();				
			}else{*/
				//System.out.println("CON JOSS");
				return DATA_SOURCE.getConnection();
			//}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public Connection getConnectionSDE(){
		DataSource DATA_SOURCE = null;
		Boolean IS_DEPLOYED = false;
		try {
			if(DATA_SOURCE == null){
				
					Context env = null;
					String source = null;
					env = (Context)new InitialContext().lookup("java:comp/env");
					source = (String)env.lookup("DATA-SOURCES_SDE");
					DATA_SOURCE = (DataSource)new InitialContext().lookup(source);
					IS_DEPLOYED= true;
				
			}
			if(IS_DEPLOYED){
				//System.out.println("CON JOSS SDE");
				return DATA_SOURCE.getConnection();
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/*public Connection getConnectionLocal(){
		Connection connection = null;
		try {
			// Load the Oracle JDBC driver
			String driverName = "oracle.jdbc.driver.OracleDriver";
			Class.forName(driverName);
			// Create a connection to the database
			String serverName = "10.10.0.3";
			String serverPort = "1525";//CAMBIAR AL PUERTO DE PRD
			String sid = "PRD";//CAMBIAR AL SID DE PRD
			String url = "jdbc:oracle:thin:@" + serverName + ":" + serverPort + ":" + sid;
			String username = "GADMAPPS";
			String password = "GADMAPPS";
			connection = DriverManager.getConnection(url, username, password);			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}*/

}