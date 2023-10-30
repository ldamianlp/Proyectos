package ec.gob.ambato.db;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.bind.DatatypeConverter;

import ec.gob.ambato.obj.objGis_syncro_imagen_predio;
import oracle.jdbc.OracleTypes;

public class CatastrosDB implements Serializable {
	private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(CatastrosDB.class);
	private static final long serialVersionUID = 484893357;
	/**
	* Constructor.
	*/
	public CatastrosDB(){}
	
	/**
     * Método que permite los datos del propietario de un determinado predio Urbano o Predio Urbano de Parroquia
     * @param inCLaveCatastral: Clave Catastral.
     * @param inContext: Contexto en el cual se visualizaran los errores en el caso de provocarse
     * @return hmDatosPredio
     */
    public HashMap<String,Object> getDatosPredioByClaveCat(
    		Connection inCon,
            String inCLaveCatastral
    )throws SQLException{
        HashMap<String,Object> hmDatosPredio = new HashMap<String, Object>();        
        CallableStatement stmt = null;
        try{
        	//inCon.setAutoCommit(false);
            stmt = inCon.prepareCall("{ call PKG_COLLECTOR.SP_GET_DATOS_PREDIO_URB(?,?) }");
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.setString(2, inCLaveCatastral);
            stmt.execute();
            ResultSet rs = (ResultSet)stmt.getObject(1);
            /*while (rs.next()) {
                hmDatosPredio.put("AREA_TERRENO",rs.getString("AREA_TERRENO")!=null?rs.getString("AREA_TERRENO"):"");
                hmDatosPredio.put("CODIGO_CIUS",rs.getString("CODIGO_CIUS")!=null?rs.getString("CODIGO_CIUS"):"");
                hmDatosPredio.put("NOMBRES",rs.getString("NOMBRES")!=null?rs.getString("NOMBRES"):"");
                hmDatosPredio.put("APELLIDOS",rs.getString("APELLIDOS")!=null?rs.getString("APELLIDOS"):"");
                hmDatosPredio.put("CEDULA",rs.getString("CEDULA")!=null?rs.getString("CEDULA"):"");
                hmDatosPredio.put("RUC",rs.getString("RUC")!=null?rs.getString("RUC"):"");
            }*/
            int tmpColumnCount = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                for (int i = 1 ; i <= tmpColumnCount; i++)
                    if(rs.getObject(i) != null){
                    	hmDatosPredio.put(rs.getMetaData().getColumnName(i).toUpperCase(), rs.getObject(i));
                    }else
                    	hmDatosPredio.put(rs.getMetaData().getColumnName(i).toUpperCase(), ""/*null*/);
            }
            rs.close();
		}catch(SQLException exception){
			logger.error(exception);
			throw exception;
		}finally{
			if(stmt != null) try { stmt.close(); } catch (SQLException exception) { logger.error(exception); }
		}
		return hmDatosPredio;
	}
    /**
     * Método que permite obtener los datos del predio rustico segun ID Politico y Clave catastral
     * @param inCLaveCatastral: Clave Catastral
     * @param inIDPolitico: ID Politico
     * @param inContext: Contexto en el cual se visualizara los errores en caso de existir
     * @return hmDatosPredio
     */
    public HashMap<String,Object> getDatosPredioRustico(
    		Connection con,
            String inCLaveCatastral,
            String inIDPolitico
    )throws SQLException{
        HashMap<String,Object> hmDatosPredio = new HashMap<String, Object>();
        CallableStatement stmt = null;
        try {
            //con.setAutoCommit(false);
            stmt = con.prepareCall("{ call PKG_COLLECTOR.SP_GET_DATOS_PREDIO_RUST(?,?,?) }");
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.setString(2, inIDPolitico);
            stmt.setString(3, inCLaveCatastral);
            stmt.execute();
            ResultSet rs = (ResultSet)stmt.getObject(1);
            hmDatosPredio = new HashMap<String,Object>();
            int tmpColumnCount = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                for (int i = 1 ; i <= tmpColumnCount; i++)
                    if(rs.getObject(i) != null){
                    	hmDatosPredio.put(rs.getMetaData().getColumnName(i).toUpperCase(), rs.getObject(i));
                    }else
                    	hmDatosPredio.put(rs.getMetaData().getColumnName(i).toUpperCase(), ""/*null*/);
            }
            /*while (rs.next()) {
            	
                hmDatosPredio.put("AREA_TERRENO",rs.getString("AREA_TERRENO"));
                hmDatosPredio.put("CODIGO_CIUS",rs.getString("CODIGO_CIUS"));
                hmDatosPredio.put("NOMBRES",rs.getString("NOMBRES"));
                hmDatosPredio.put("APELLIDOS",rs.getString("APELLIDOS"));
                hmDatosPredio.put("CEDULA",rs.getString("CEDULA"));
                hmDatosPredio.put("RUC",rs.getString("RUC"));
            }
            */
            rs.close();
        }catch(SQLException exception){
			logger.error(exception);
			throw exception;
		}finally{
			if(stmt != null) try { stmt.close(); } catch (SQLException exception) { logger.error(exception); }
		}
		return hmDatosPredio;
    }
    /**
     * Método que permite obtener las construcciones urbanas segun la clave catastral
     * @param inCLaveCatastral: Clave Catastral
     * @param inContext: Contexto en el cual se visualizara los errores en caso de existir
     * @return outAlHmConstrucciones
     */
    public ArrayList<HashMap<String,Object>> getConstruccionesUrbano(
    		Connection con,
            String inCLaveCatastral
    )throws SQLException{
        ArrayList<HashMap<String,Object>> outAlHmConstrucciones = new ArrayList<HashMap<String,Object>>();
        CallableStatement stmt = null;
        try {
            con.setAutoCommit(false);
            stmt = con.prepareCall("{ call PKG_COLLECTOR.SP_GET_CONSTRUC_URBA(?,?) }");
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.setString(2, inCLaveCatastral);
            stmt.execute();
            ResultSet rs = (ResultSet)stmt.getObject(1);
            int tmpColumnCount = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                HashMap<String,Object> tmpObj = new HashMap<String,Object>();
                for (int i = 1 ; i <= tmpColumnCount; i++)
                    if(rs.getObject(i) != null){
                        tmpObj.put(rs.getMetaData().getColumnName(i).toUpperCase(), rs.getObject(i));
                    }else
                        tmpObj.put(rs.getMetaData().getColumnName(i).toUpperCase(), ""/*null*/);
                outAlHmConstrucciones.add(tmpObj);
            }
            rs.close();
        }catch(SQLException exception){
			logger.error(exception);
			throw exception;
		}finally{
			if(stmt != null) try { stmt.close(); } catch (SQLException exception) { logger.error(exception); }
		}
        return outAlHmConstrucciones;
    }
    /**
     * Método que permite obtener las construcciones de un predio rustico
     * @param inCLaveCatastral: Clave catastral
     * @param inIDPolitico: ID Politico
     * @param inContext: Contexto en el cual se visualizara el error en caso de existir
     * @return outAlHmConstrucciones
     */
    public ArrayList<HashMap<String,Object>> getConstruccionesRustico(
    		Connection con,
            String inCLaveCatastral,
            String inIDPolitico
    )throws SQLException{
        ArrayList<HashMap<String,Object>> outAlHmConstrucciones = new ArrayList<HashMap<String,Object>>();
        CallableStatement stmt = null;
        try {
            con.setAutoCommit(false);
            if(isPH(con,inCLaveCatastral,inIDPolitico).equalsIgnoreCase("S")){
                stmt = con.prepareCall("{ call PKG_COLLECTOR.SP_GET_CONSTRUC_RUST_PH(?,?,?) }");
            }else{
                stmt = con.prepareCall("{ call PKG_COLLECTOR.SP_GET_CONSTRUC_RUST_NOPH(?,?,?) }");
            }
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.setString(2, inIDPolitico);
            stmt.setString(3, inCLaveCatastral);
            stmt.execute();
            ResultSet rs = (ResultSet)stmt.getObject(1);
            int tmpColumnCount = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                HashMap<String,Object> tmpObj = new HashMap<String,Object>();
                for (int i = 1 ; i <= tmpColumnCount; i++)
                    if(rs.getObject(i) != null){
                        tmpObj.put(rs.getMetaData().getColumnName(i).toUpperCase(), rs.getObject(i));
                    }else
                        tmpObj.put(rs.getMetaData().getColumnName(i).toUpperCase(), ""/*null*/);
                outAlHmConstrucciones.add(tmpObj);
            }
            rs.close();
        }catch(SQLException exception){
			logger.error(exception);
			throw exception;
		}finally{
			if(stmt != null) try { stmt.close(); } catch (SQLException exception) { logger.error(exception); }
		}
        return outAlHmConstrucciones;
    }
    /**
     * Método que permite obtener las construcciones urbanas segun la clave catastral
     * @param inCLaveCatastral: Clave Catastral
     * @param inContext: Contexto en el cual se visualizara los errores en caso de existir
     * @return outAlHmConstrucciones
     */
    public ArrayList<HashMap<String,Object>> getInquilinatoUrbano(
    		Connection con,
            String inCLaveCatastral
    )throws SQLException{
        ArrayList<HashMap<String,Object>> outAlHmInquilinato = new ArrayList<HashMap<String,Object>>();
        CallableStatement stmt = null;
        try {
            con.setAutoCommit(false);
            stmt = con.prepareCall("{ call PKG_COLLECTOR.SP_GET_INQUILINATO_URBA(?,?) }");
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.setString(2, inCLaveCatastral);
            stmt.execute();
            ResultSet rs = (ResultSet)stmt.getObject(1);
            int tmpColumnCount = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                HashMap<String,Object> tmpObj = new HashMap<String,Object>();
                for (int i = 1 ; i <= tmpColumnCount; i++)
                    if(rs.getObject(i) != null){
                        tmpObj.put(rs.getMetaData().getColumnName(i).toUpperCase(), rs.getObject(i));
                    }else
                        tmpObj.put(rs.getMetaData().getColumnName(i).toUpperCase(), ""/*null*/);
                outAlHmInquilinato.add(tmpObj);
            }
            rs.close();
        }catch(SQLException exception){
			logger.error(exception);
			throw exception;
		}finally{
			if(stmt != null) try { stmt.close(); } catch (SQLException exception) { logger.error(exception); }
		}
        return outAlHmInquilinato;
    }
    /**
     * Método que permite obtener las construcciones de un predio rustico
     * @param inCLaveCatastral: Clave catastral
     * @param inIDPolitico: ID Politico
     * @param inContext: Contexto en el cual se visualizara el error en caso de existir
     * @return outAlHmConstrucciones
     */
    public ArrayList<HashMap<String,Object>> getInquilinatoRustico(
    		Connection con,
            String inCLaveCatastral,
            String inIDPolitico
    )throws SQLException{
        ArrayList<HashMap<String,Object>> outAlHmInquilinato = new ArrayList<HashMap<String,Object>>();
        CallableStatement stmt = null;
        try {
            con.setAutoCommit(false);
            stmt = con.prepareCall("{ call PKG_COLLECTOR.SP_GET_INQUILINATO_RUSTI(?,?,?) }");
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.setString(2, inIDPolitico);
            stmt.setString(3, inCLaveCatastral);
            stmt.execute();
            ResultSet rs = (ResultSet)stmt.getObject(1);
            int tmpColumnCount = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                HashMap<String,Object> tmpObj = new HashMap<String,Object>();
                for (int i = 1 ; i <= tmpColumnCount; i++)
                    if(rs.getObject(i) != null){
                        tmpObj.put(rs.getMetaData().getColumnName(i).toUpperCase(), rs.getObject(i));
                    }else
                        tmpObj.put(rs.getMetaData().getColumnName(i).toUpperCase(), ""/*null*/);
                outAlHmInquilinato.add(tmpObj);
            }
            rs.close();
        }catch(SQLException exception){
			logger.error(exception);
			throw exception;
		}finally{
			if(stmt != null) try { stmt.close(); } catch (SQLException exception) { logger.error(exception); }
		}
        return outAlHmInquilinato;
    }
    /**
     * Método que permite verificar si un predio es PH
     * @param con: Coneccion a la base de datos
     * @param inPredio: Calve catastral del predio
     * @param inIDPolitico: ID Politico.
     * @param inContext: Contexto en el cua se visualizara los errores en caso de provocarse
     * @return outIsPH
     */
    public String isPH(
            java.sql.Connection con,
            String inPredio,
            String inIDPolitico
    )throws SQLException{
        String outIsPH = null;
        CallableStatement stmt = null;
        try{

            stmt = con.prepareCall("{ ? =  call PKG_COLLECTOR.FN_IS_PREDIO_PH(?,?) }");
            stmt.registerOutParameter(1, OracleTypes.VARCHAR);
            stmt.setString(2, inIDPolitico);
            stmt.setString(3, inPredio);
            stmt.execute();
            outIsPH = stmt.getString(1);
            //ResultSet tmpResults = (ResultSet)tmpStatement.getObject(1);
            //outIsPH = tmpResults.getString(1);
            //tmpResults.close();
        }catch(SQLException exception){
			logger.error(exception);
			throw exception;
		}finally{
			if(stmt != null) try { stmt.close(); } catch (SQLException exception) { logger.error(exception); }
		}
        return outIsPH;
    }
    /**
     * Método que permite obtener las escrituras del predio
     * @param inTipo: Especifica si un predio es: 1 - Urbano; 2 - Rustico; 3 - Urbano de Parroquia
     * @param inCLaveCatastral: Clave Catastral
     * @param inIDPolitico: ID Politico
     * @param inContext: Contexto en el cual se visualizaran los errores en caso de provocarse
     * @return outAlHmConstrucciones
     */
    public ArrayList<HashMap<String,Object>> getEscrituras(
    		Connection con,
            String inTipo,
            String inCLaveCatastral,
            String inIDPolitico
    )throws SQLException{
        ArrayList<HashMap<String,Object>> outAlHmConstrucciones = new ArrayList<HashMap<String,Object>>();
        CallableStatement stmt = null;
        try {
            con.setAutoCommit(false);
            if(inTipo.equalsIgnoreCase("1")) {  //URBANO
                stmt = con.prepareCall("{ call PKG_COLLECTOR.SP_GET_ESCRITURA_URBA(?,?) }");
                stmt.registerOutParameter(1, OracleTypes.CURSOR);
                stmt.setString(2, inCLaveCatastral);

            }else{
                if(inTipo.equalsIgnoreCase("3")) { //URBANO DE PARROQUIA
                    stmt = con.prepareCall("{ call PKG_COLLECTOR.SP_GET_ESCRITURA_URBA_PARR(?,?) }");
                    stmt.registerOutParameter(1, OracleTypes.CURSOR);
                    stmt.setString(2, inCLaveCatastral);
                }else{
                    stmt = con.prepareCall("{ call PKG_COLLECTOR.SP_GET_ESCRITURA_RUST(?,?,?) }");
                    stmt.registerOutParameter(1, OracleTypes.CURSOR);
                    stmt.setString(2, inIDPolitico);
                    stmt.setString(3, inCLaveCatastral);
                }
            }
            stmt.execute();
            ResultSet rs = (ResultSet)stmt.getObject(1);
            int tmpColumnCount = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                HashMap<String,Object> tmpObj = new HashMap<String,Object>();
                for (int i = 1 ; i <= tmpColumnCount; i++)
                    if(rs.getObject(i) != null){
                        tmpObj.put(rs.getMetaData().getColumnName(i).toUpperCase(), rs.getObject(i));
                    }else
                        tmpObj.put(rs.getMetaData().getColumnName(i).toUpperCase(), ""/*null*/);
                outAlHmConstrucciones.add(tmpObj);
            }
            rs.close();
        }catch(SQLException exception){
			logger.error(exception);
			throw exception;
		}finally{
			if(stmt != null) try { stmt.close(); } catch (SQLException exception) { logger.error(exception); }
		}
        return outAlHmConstrucciones;
    }
    /**
     * Método que permite obtener todos los ID Politicos del canton Ambato
     * @param inContext: Contexto en el cual se visualizara los errores en caso de existir
     * @return alIdPolitico
     */
    public ArrayList<HashMap<String, Object>> getAllIdPoliticos(
    		Connection con
    )throws SQLException{
        ArrayList<HashMap<String, Object>> alIdPolitico = new ArrayList<>();        
        CallableStatement stmt = null;
        try {
            con.setAutoCommit(false);
            stmt = con.prepareCall("{ call PKG_COLLECTOR.SP_GET_ID_POLITICOS(?) }");
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();
            ResultSet rs = (ResultSet)stmt.getObject(1);
            int tmpColumnCount = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                HashMap<String,Object> tmpObj = new HashMap<String,Object>();
                for (int i = 1 ; i <= tmpColumnCount; i++)
                    if(rs.getObject(i) != null){
                        tmpObj.put(rs.getMetaData().getColumnName(i).toUpperCase(), rs.getObject(i));
                    }else
                        tmpObj.put(rs.getMetaData().getColumnName(i).toUpperCase(), ""/*null*/);
                alIdPolitico.add(tmpObj);
            }
            rs.close();
        }catch(SQLException exception){
			logger.error(exception);
			throw exception;
		}finally{
			if(stmt != null) try { stmt.close(); } catch (SQLException exception) { logger.error(exception); }
		}
        return alIdPolitico;
    }
    /**
     * Método que permite obtener todos los ID Politicos del canton Ambato
     * @param inContext: Contexto en el cual se visualizara los errores en caso de existir
     * @return alIdPolitico
     */
    public String validarUsuario(
    		Connection con,
    		String inUser,
    		String inPassword
    )throws SQLException{
        String retorno= "false";
        CallableStatement stmt = null;
        String tmp_password = 
    		org.jboss.crypto.CryptoUtil.createPasswordHash("SHA1", "rfc2617", "UTF-8", inUser, inPassword);
        try {
        	stmt = con.prepareCall("{ ? =  call PKG_COLLECTOR.FN_VALIDAR_USUARIO(?,?) }");
            stmt.registerOutParameter(1, java.sql.Types.VARCHAR);
            stmt.setString(2, inUser);
            stmt.setString(3, tmp_password);
            stmt.execute();
            retorno = stmt.getString(1);
        }catch(SQLException exception){
			logger.error(exception);
			throw exception;
		}finally{
			if(stmt != null) try { stmt.close(); } catch (SQLException exception) { logger.error(exception); }
		}
        return retorno;
    }
    /**
     * Método que permite los datos del propietario de un determinado predio Urbano o Predio Urbano de Parroquia
     * @param inCLaveCatastral: Clave Catastral.
     * @param inContext: Contexto en el cual se visualizaran los errores en el caso de provocarse
     * @return hmDatosPredio
     */
    public ArrayList<HashMap<String, Object>> getParametersSyncro(
    		Connection inCon,
            String inUser,
            String inPassword
    )throws SQLException{
    	ArrayList<HashMap<String, Object>> alParameters = new ArrayList<>(); 
        CallableStatement stmt = null;
        try{
        	//inCon.setAutoCommit(false);
        	String tmp_password = 
            		org.jboss.crypto.CryptoUtil.createPasswordHash("SHA1", "rfc2617", "UTF-8", inUser, inPassword);
            stmt = inCon.prepareCall("{ call PKG_COLLECTOR.SP_GET_PARAMETERS_SYNCRO(?,?,?) }");
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.setString(2, inUser);
            stmt.setString(3, tmp_password);
            stmt.execute();
            ResultSet rs = (ResultSet)stmt.getObject(1);            
            int tmpColumnCount = rs.getMetaData().getColumnCount();
            while (rs.next()) {
            	HashMap<String,Object> hmDatosParameters = new HashMap<String, Object>(); 
                for (int i = 1 ; i <= tmpColumnCount; i++)
                    if(rs.getObject(i) != null){
                    	System.out.println("campo: "+rs.getMetaData().getColumnName(i).toUpperCase());
                    	if(rs.getMetaData().getColumnName(i).toUpperCase().equalsIgnoreCase("SPA_CODIGO") 
                    			&& rs.getObject(i).toString().equalsIgnoreCase("PASSWORD_USER_SDE")){
                    		hmDatosParameters.put(rs.getMetaData().getColumnName(i).toUpperCase(), rs.getObject(i).toString());
                    		hmDatosParameters.put("SPA_VALOR", cifrarBase64(rs.getObject("SPA_VALOR").toString()));
                    	}else{
                    		if(!hmDatosParameters.containsKey("SPA_VALOR"))
                    			hmDatosParameters.put(rs.getMetaData().getColumnName(i).toUpperCase(), rs.getObject(i));
                    	}
                    }else
                    	hmDatosParameters.put(rs.getMetaData().getColumnName(i).toUpperCase(), ""/*null*/);
                alParameters.add(hmDatosParameters);
            }
            rs.close();
		}catch(SQLException exception){
			logger.error(exception);
			throw exception;
		}finally{
			if(stmt != null) try { stmt.close(); } catch (SQLException exception) { logger.error(exception); }
		}
		return alParameters;
	}
    public static String cifrarBase64(String cadena){

		  byte[] message;
		  String encode="";
		  try {
		   message = cadena.getBytes("UTF-8");
		   encode =  DatatypeConverter.printBase64Binary(message); 
		      return encode;
		  } catch (UnsupportedEncodingException e) {
		   // TODO Auto-generated catch block   
		   e.printStackTrace();
		   return encode;
		  }
    }
    
    public BigDecimal getNumRevision(
    		Connection con,
    		String inClaveCatastral
    )throws SQLException{        
        BigDecimal revision=null;
        CallableStatement stmt = null;        
        try {        	
        	stmt = con.prepareCall("{ ? = call SDE.PKG_SYNCRO_IMAGEN_PREDIO.F_GET_REVISION_BY_PREDIO(?) }");
        	stmt.registerOutParameter(1, Types.NUMERIC);
        	stmt.setString(2, inClaveCatastral);
        	stmt.execute();
    		revision = stmt.getBigDecimal(1);
            
        }catch(SQLException exception){
			logger.error(exception);
			throw exception;
		}finally{
			if(stmt != null) try { stmt.close(); } catch (SQLException exception) { logger.error(exception); }
		}
        return revision;
    }
    
    public String insertDatosPredio(
    		Connection con,
    		objGis_syncro_imagen_predio inObjDatos
    )throws SQLException{
    	 String retornoFrontal;
        CallableStatement tmpStatementFrontal = null;        
        try {        	//AQUI HAY Q VERIFICAR QUE SE CAMBIE A LA FUNCION SDE.PKG_SYNCRO_IMAGEN_PREDIO.F_INSERT_SYNCRO_IMAGEN_PREDIO(?,?,?,?,?,?,?)
        	//tmpStatementFrontal = con.prepareCall("{ ? = call SDE.PKG_SYNCRO_IMAGEN_PREDIO.F_INSERT_TEST(?,?,?,?,?,?,?) }");
        	tmpStatementFrontal = con.prepareCall("{ ? = call SDE.PKG_SYNCRO_IMAGEN_PREDIO.F_INSERT_SYNCRO_IMAGEN_PREDIO(?,?,?,?,?,?,?) }");
            tmpStatementFrontal.registerOutParameter(1, Types.VARCHAR);
            tmpStatementFrontal.setString(2, inObjDatos.getSip_predio());
            tmpStatementFrontal.setBigDecimal(3, inObjDatos.getSip_revision());
            tmpStatementFrontal.setBigDecimal(4, inObjDatos.getPti_codigo());  //tipo imagen frontal del predio
            tmpStatementFrontal.setBytes(5, inObjDatos.getSip_imagen());            
            tmpStatementFrontal.setString(6, inObjDatos.getSip_nombre_ima());
            tmpStatementFrontal.setString(7, inObjDatos.getSip_mimetype_ima());
            tmpStatementFrontal.setString(8, inObjDatos.getSip_usuario_insert());
            tmpStatementFrontal.execute();
            retornoFrontal = tmpStatementFrontal.getString(1);
            tmpStatementFrontal.close();
            
        }catch(SQLException exception){
			logger.error(exception);
			throw exception;
		}finally{
			if(tmpStatementFrontal != null) try { tmpStatementFrontal.close(); } catch (SQLException exception) { logger.error(exception); }
		}
        return retornoFrontal;
    }
    public BigDecimal getNumRevisionTest(
    		Connection con,
    		String inClaveCatastral
    )throws SQLException{        
        BigDecimal revision=null;
        CallableStatement stmt = null;        
        try {        	
        	stmt = con.prepareCall("{ ? = call SDE.PKG_SYNCRO_IMAGEN_PREDIO.F_GET_REVISION_TEST(?) }");
        	stmt.registerOutParameter(1, Types.NUMERIC);
        	stmt.setString(2, inClaveCatastral);
        	stmt.execute();
    		revision = stmt.getBigDecimal(1);
            
        }catch(SQLException exception){
			logger.error(exception);
			throw exception;
		}finally{
			if(stmt != null) try { stmt.close(); } catch (SQLException exception) { logger.error(exception); }
		}
        return revision;
    }
}
