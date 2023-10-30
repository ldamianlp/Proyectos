package ec.gob.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;



public class DataBaseObject {

	private PreparedStatement preparedStatement = null;
	private StringBuffer query = new StringBuffer("begin dbms_debug_jdwp.connect_tcp('10.10.0.67',4000);end;");
	private StringBuffer queryClose = new StringBuffer("begin dbms_debug_jdwp.disconnect;end;");
	private boolean debug = false;
	
	private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(DataBaseObject.class);
	
	private Connection connection;
	private DataSource dataSource = null;
	private String ds = null;
	
	public DataBaseObject(){
		super();
	}
		
	public DataBaseObject(int gadma) {			
		Context env = null;
		String source = null;
		try {
			 switch (gadma) {
	            case 1:  	            	
            		env = (Context)new InitialContext().lookup("java:comp/env");
					source = (String)env.lookup("dataSourceFinados");
                    break;	           
	        }

		} catch (NamingException e) {
			e.printStackTrace();
		}		
		this.ds = source;
	}
	
	public DataBaseObject(String dataSource) {
		this.ds = dataSource;		
	}

	public Map<String,Object> select(Connection connection) throws SQLException{ return null;}
	
	public void insert(Connection connection) throws SQLException {}
	
	public void update(Connection connection) throws SQLException {}
	
	public void delete(Connection connection) throws SQLException{}
	
	public Connection getConnection(){
		try {
			this.dataSource = (DataSource) new InitialContext().lookup(this.ds);						    			   			   			   	
			   connection = this.dataSource.getConnection();
			   if(this.debug){					
						preparedStatement = connection.prepareStatement(query.toString());
						preparedStatement.executeQuery();
						preparedStatement.close();					
				}
		} catch (NamingException e) {
			e.printStackTrace();
			logger.error(e);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
		}
		return connection;		
	}
	
	public void stopDebug() throws SQLException{
		if(this.debug){
			if(connection != null && !connection.isClosed()){
				preparedStatement = connection.prepareStatement(queryClose.toString());
				preparedStatement.executeQuery();
				preparedStatement.close();
			}
		}	
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}		
	
}
