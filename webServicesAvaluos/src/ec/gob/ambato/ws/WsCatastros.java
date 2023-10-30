package ec.gob.ambato.ws;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.soap.SOAPFaultException;

import ec.gob.ambato.db.CatastrosDB;
import ec.gob.ambato.db.GadmaConnection;
import ec.gob.ambato.format.Formatter;
import ec.gob.ambato.obj.objGis_syncro_imagen_predio;

@WebService
public class WsCatastros implements Serializable{
	private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(WsCatastros.class);
	private static final long serialVersionUID = 1116984823;
	@Resource WebServiceContext wctx;
	
	@WebMethod
	public String getDatosPredio(
			@WebParam(name="inCLaveCatastral")String inCLaveCatastral,
			@WebParam(name="inIDPolitico")String inIDPolitico,
			@WebParam(name="inTipo")String inTipo
    )throws SOAPException{
		String tmpRes = "false";
//        HashMap<String,Object> hmDatosPredio = new HashMap<String, Object>();
        StringBuilder outCadena = null;
        StringBuilder outCadenaConstrucciones= null;
        StringBuilder outCadenaEscrituras= null;
        StringBuilder outCadenaInquilinato= null;
        GadmaConnection conObj = new GadmaConnection();
        Connection con = null;
        long tmpCount = 0;
        try{
        	con = conObj.getConnection();
	        CatastrosDB objCatastrosDB = new CatastrosDB();
	        HashMap<String, Object> datos;
	        if(inTipo.equalsIgnoreCase("1") || inTipo.equalsIgnoreCase("3"))
	        	datos = objCatastrosDB.getDatosPredioByClaveCat(con,inCLaveCatastral);
	        else
	        	datos = objCatastrosDB.getDatosPredioRustico(con,inCLaveCatastral,inIDPolitico);
	        
	        if(datos.size()>0){
		        Formatter tmpFormat = new ec.gob.ambato.format.Formatter("JSON", datos);
		        outCadena = tmpFormat.getData();
		        tmpCount = 1;
	        }
	        ArrayList<HashMap<String, Object>> alHmConstrucciones;
	        if (inTipo.equalsIgnoreCase("1") || inTipo.equalsIgnoreCase("3")) {
	            alHmConstrucciones = objCatastrosDB.getConstruccionesUrbano(con,inCLaveCatastral);
	        } else {
	            alHmConstrucciones = objCatastrosDB.getConstruccionesRustico(con,inCLaveCatastral, inIDPolitico);
	        }
	        if(alHmConstrucciones.size()>0){
		        Formatter tmpFormat = new ec.gob.ambato.format.Formatter("JSON", alHmConstrucciones);
		        outCadenaConstrucciones = tmpFormat.getData();
	        }
	        
	        ArrayList<HashMap<String, Object>> alHmEscrituras;
        	alHmEscrituras = objCatastrosDB.getEscrituras(con, inTipo, inCLaveCatastral, inIDPolitico);
	       
	        if(alHmEscrituras.size()>0){
		        Formatter tmpFormat = new ec.gob.ambato.format.Formatter("JSON", alHmEscrituras);
		        outCadenaEscrituras = tmpFormat.getData();
	        }

	        ArrayList<HashMap<String, Object>> alHmInquilinato;
	        if (inTipo.equalsIgnoreCase("1") || inTipo.equalsIgnoreCase("3")) {
	        	alHmInquilinato = objCatastrosDB.getInquilinatoUrbano(con,inCLaveCatastral);
	        } else {
	        	alHmInquilinato = objCatastrosDB.getInquilinatoRustico(con,inCLaveCatastral, inIDPolitico);
	        }
	        if(alHmInquilinato.size()>0){
		        Formatter tmpFormat = new ec.gob.ambato.format.Formatter("JSON", alHmInquilinato);
		        outCadenaInquilinato = tmpFormat.getData();
	        }
	        tmpRes = "true";
		}catch(SQLException exception){
			throw new SOAPFaultException(SOAPFactory.newInstance().createFault(exception.getMessage(), new QName("http://schemas.xmlsoap.org/soap/envelope/", Thread.currentThread().getStackTrace()[1].getClassName()+" "+Thread.currentThread().getStackTrace()[1].getMethodName())));
		}finally{
			try {
				if(con != null) con.close();
			}catch (SQLException e){e.printStackTrace();}
		}
        if(!tmpRes.equalsIgnoreCase("true")) throw new SOAPFaultException(SOAPFactory.newInstance().createFault(tmpRes, new QName("http://schemas.xmlsoap.org/soap/envelope/", Thread.currentThread().getStackTrace()[1].getClassName()+" "+Thread.currentThread().getStackTrace()[1].getMethodName())));
        return "{\"totalCount\":\""+tmpCount+
        		"\",\"datosPredio\":"+((outCadena!=null)?outCadena.toString():"\"\"")+
        		",\"construcciones\":"+((outCadenaConstrucciones!=null)?outCadenaConstrucciones.toString():"\"\"")+
        		",\"escrituras\":"+((outCadenaEscrituras!=null)?outCadenaEscrituras.toString():"\"\"")+
        		",\"inquilinato\":"+((outCadenaInquilinato!=null)?outCadenaInquilinato.toString():"\"\"")
        		+"}";
    }/**
     * Método que permite obtener los datos del predio rustico segun ID Politico y Clave catastral
     * @param inCLaveCatastral: Clave Catastral
     * @param inIDPolitico: ID Politico
     * @param inContext: Contexto en el cual se visualizara los errores en caso de existir
     * @return hmDatosPredio
     */
	@WebMethod
    public String getDatosPredioRustico(
    		@WebParam(name="inCLaveCatastral")String inCLaveCatastral,
    		@WebParam(name="inIDPolitico")String inIDPolitico
    )throws SOAPException{
		String tmpRes = "false";
//      HashMap<String,Object> hmDatosPredio = new HashMap<String, Object>();
      StringBuilder outCadena = null;
      GadmaConnection conObj = new GadmaConnection();
      Connection con = null;
      long tmpCount = 0;
      try{
      	con = conObj.getConnection();
	        CatastrosDB objCatastrosDB = new CatastrosDB();
	        HashMap<String, Object> datos = objCatastrosDB.getDatosPredioRustico(con,inCLaveCatastral,inIDPolitico);
	        if(datos.size()>0){
		        Formatter tmpFormat = new ec.gob.ambato.format.Formatter("JSON", datos);
		        outCadena = tmpFormat.getData();
		        tmpCount = 1;
	        }
	        tmpRes = "true";
	        	
		}catch(SQLException exception){
			throw new SOAPFaultException(SOAPFactory.newInstance().createFault(exception.getMessage(), new QName("http://schemas.xmlsoap.org/soap/envelope/", Thread.currentThread().getStackTrace()[1].getClassName()+" "+Thread.currentThread().getStackTrace()[1].getMethodName())));
		}finally{
			try {
				if(con != null) con.close();
			}catch (SQLException e){e.printStackTrace();}
		}
      if(!tmpRes.equalsIgnoreCase("true")) throw new SOAPFaultException(SOAPFactory.newInstance().createFault(tmpRes, new QName("http://schemas.xmlsoap.org/soap/envelope/", Thread.currentThread().getStackTrace()[1].getClassName()+" "+Thread.currentThread().getStackTrace()[1].getMethodName())));
      return "{\"totalCount\":\""+tmpCount+"\",\"dataSet\":"+outCadena.toString()+"}";
    }
	/**
     * Método que permite obtener los datos del predio rustico segun ID Politico y Clave catastral
     * @param inCLaveCatastral: Clave Catastral
     * @param inIDPolitico: ID Politico
     * @param inContext: Contexto en el cual se visualizara los errores en caso de existir
     * @return hmDatosPredio
     */
	@WebMethod
    public String validarUsuario(
    		@WebParam(name="inUser")String inUser,
    		@WebParam(name="inPassword")String inPassword
    )throws SOAPException{
		//String tmpRes = "false";
      GadmaConnection conObj = new GadmaConnection();
      Connection con = null;
      String resultado = "false";
      try{
      		con = conObj.getConnection();
	        CatastrosDB objCatastrosDB = new CatastrosDB();
	        resultado = objCatastrosDB.validarUsuario(con,inUser,inPassword);
	        //tmpRes = "true";
		}catch(SQLException exception){
//			logger.error(exception.getMessage(), exception);
//			if(System.getProperty("app.debug.flag") != null){
//				StringWriter tmpBuffer = new StringWriter();
//				exception.printStackTrace(new PrintWriter(tmpBuffer));
//				tmpRes = tmpBuffer.toString();
//			}else
//				tmpRes = Messages.getMessageInfo(CodesMessage.ADMIN_ERROR);
			throw new SOAPFaultException(SOAPFactory.newInstance().createFault(exception.getMessage(), new QName("http://schemas.xmlsoap.org/soap/envelope/", Thread.currentThread().getStackTrace()[1].getClassName()+" "+Thread.currentThread().getStackTrace()[1].getMethodName())));
		}finally{
			try {
				if(con != null) con.close();
			}catch (SQLException e){
				e.printStackTrace();
				}
		}
      //if(!tmpRes.equalsIgnoreCase("true")) throw new SOAPFaultException(SOAPFactory.newInstance().createFault(tmpRes, new QName("http://schemas.xmlsoap.org/soap/envelope/", Thread.currentThread().getStackTrace()[1].getClassName()+" "+Thread.currentThread().getStackTrace()[1].getMethodName())));
      return resultado;
    }
	/**
     * Método que permite obtener los datos del predio rustico segun ID Politico y Clave catastral
     * @param inCLaveCatastral: Clave Catastral
     * @param inIDPolitico: ID Politico
     * @param inContext: Contexto en el cual se visualizara los errores en caso de existir
     * @return hmDatosPredio
     */
	@WebMethod
    public String getAllIdPoliticos(
    )throws SOAPException{
		String tmpRes = "false";
      StringBuilder outCadena = null;
      GadmaConnection conObj = new GadmaConnection();
      Connection con = null;
      ArrayList<HashMap<String, Object>> datos = null;
      long tmpCount = 0;
      try{
      	con = conObj.getConnection();
	        CatastrosDB objCatastrosDB = new CatastrosDB();
	        datos = objCatastrosDB.getAllIdPoliticos(con);
	        if(datos.size()>0){
		        Formatter tmpFormat = new ec.gob.ambato.format.Formatter("JSON", datos);
		        outCadena = tmpFormat.getData();
		        tmpCount = datos.size();
	        }
	        tmpRes = "true";
	        	
		}catch(SQLException exception){
			throw new SOAPFaultException(SOAPFactory.newInstance().createFault(exception.getMessage(), new QName("http://schemas.xmlsoap.org/soap/envelope/", Thread.currentThread().getStackTrace()[1].getClassName()+" "+Thread.currentThread().getStackTrace()[1].getMethodName())));
		}finally{
			try {
				if(con != null) con.close();
			}catch (SQLException e){e.printStackTrace();}
		}
      if(!tmpRes.equalsIgnoreCase("true")) throw new SOAPFaultException(SOAPFactory.newInstance().createFault(tmpRes, new QName("http://schemas.xmlsoap.org/soap/envelope/", Thread.currentThread().getStackTrace()[1].getClassName()+" "+Thread.currentThread().getStackTrace()[1].getMethodName())));
      return "{\"totalCount\":\""+tmpCount+"\",\"dataSet\":"+outCadena.toString()+"}";
    }
	@WebMethod
	public String getParametersSyncro(
			@WebParam(name="inUser")String inUser,
			@WebParam(name="inPaswword")String inPaswword
    )throws SOAPException{
		String tmpRes = "false";
        StringBuilder outCadena = null;
        GadmaConnection conObj = new GadmaConnection();
        Connection con = null;
        long tmpCount = 0;
        try{
        	con = conObj.getConnection();
	        CatastrosDB objCatastrosDB = new CatastrosDB();
	        
	         ArrayList<HashMap<String, Object>> datos = objCatastrosDB.getParametersSyncro(con,inUser,inPaswword);
	        
	        if(datos.size()>0){
		        Formatter tmpFormat = new ec.gob.ambato.format.Formatter("JSON", datos);
		        outCadena = tmpFormat.getData();
		        tmpCount = 1;
	        }
	        
	        tmpRes = "true";
		}catch(SQLException exception){
			throw new SOAPFaultException(SOAPFactory.newInstance().createFault(exception.getMessage(), new QName("http://schemas.xmlsoap.org/soap/envelope/", Thread.currentThread().getStackTrace()[1].getClassName()+" "+Thread.currentThread().getStackTrace()[1].getMethodName())));
		}finally{
			try {
				if(con != null) con.close();
			}catch (SQLException e){e.printStackTrace();}
		}
        if(!tmpRes.equalsIgnoreCase("true")) throw new SOAPFaultException(SOAPFactory.newInstance().createFault(tmpRes, new QName("http://schemas.xmlsoap.org/soap/envelope/", Thread.currentThread().getStackTrace()[1].getClassName()+" "+Thread.currentThread().getStackTrace()[1].getMethodName())));
        return "{\"totalCount\":\""+tmpCount+
        		"\",\"parameters\":"+((outCadena!=null)?outCadena.toString():"\"\"")
        		+"}";
    }
	
	/**
     * Método que permite obtener los datos del predio rustico segun ID Politico y Clave catastral
     
     * @return resultado
     */
	@WebMethod
    public String subirDatosWs(
    		@WebParam(name="IN_SIP_PREDIO")String inPredio,
    		@WebParam(name="IN_SIP_REVISION")int inRevision,
    		@WebParam(name="IN_PTI_CODIGO")String inTipo,
    		@WebParam(name="IN_SIP_IMAGEN")byte[] inImagen,
    		@WebParam(name="IN_SIP_NOMBRE_IMA")String inNombreImagen,
    		@WebParam(name="IN_SIP_MIMETYPE_IMA")String inMimetype,
    		@WebParam(name="IN_SIP_USUARIO_INSERT")String inUsuario
    )throws SOAPException{		
      GadmaConnection conObj = new GadmaConnection();
      Connection con = null;
      String resultado = "false";
      try{
      		con = conObj.getConnectionSDE();
	        CatastrosDB objCatastrosDB = new CatastrosDB();
	        //aqui verificar que la consulta se realize en la base de datos gis gadmagis sde
	        //BigDecimal numRevision = objCatastrosDB.getNumRevision(con,inPredio);
	        
	        //seteo a los object.
	        objGis_syncro_imagen_predio objDatos = new objGis_syncro_imagen_predio();
	        objDatos.setSip_predio(inPredio);
	        objDatos.setSip_revision(new BigDecimal(inRevision));
	        objDatos.setPti_codigo(new BigDecimal(inTipo));
	        objDatos.setSip_imagen(inImagen);
	        objDatos.setSip_nombre_ima(inNombreImagen);
	        objDatos.setSip_mimetype_ima(inMimetype);
	        objDatos.setSip_usuario_insert(inUsuario);
	        //objDatos.setSip_fecha_insert(null);
	        String  retorno = objCatastrosDB.insertDatosPredio(con, objDatos);
	       
	        
	        
	        resultado = "true";
		}catch(SQLException exception){
			throw new SOAPFaultException(SOAPFactory.newInstance().createFault(exception.getMessage(), new QName("http://schemas.xmlsoap.org/soap/envelope/", Thread.currentThread().getStackTrace()[1].getClassName()+" "+Thread.currentThread().getStackTrace()[1].getMethodName())));
		}finally{
			try {
				if(con != null) con.close();
			}catch (SQLException e){
				e.printStackTrace();
				}
		}
      //if(!tmpRes.equalsIgnoreCase("true")) throw new SOAPFaultException(SOAPFactory.newInstance().createFault(tmpRes, new QName("http://schemas.xmlsoap.org/soap/envelope/", Thread.currentThread().getStackTrace()[1].getClassName()+" "+Thread.currentThread().getStackTrace()[1].getMethodName())));
      return resultado;
    }
	/**
     * Método que permite obtener los datos del predio rustico segun ID Politico y Clave catastral
     
     * @return resultado
     */
	@WebMethod
    public BigDecimal getNumRevision(
    		@WebParam(name="IN_SIP_PREDIO")String inPredio
    )throws SOAPException{
      GadmaConnection conObj = new GadmaConnection();
      Connection con = null;
      BigDecimal numRevision= null;
      try{
      		con = conObj.getConnectionSDE();
	        CatastrosDB objCatastrosDB = new CatastrosDB();
	        //aqui verificar que la consulta se realize en la base de datos gis gadmagis sde y colocar el metodo 
	        //getNumRevision
	        numRevision = objCatastrosDB.getNumRevision(con,inPredio);
	        //numRevision = objCatastrosDB.getNumRevisionTest(con,inPredio);
		}catch(SQLException exception){
			throw new SOAPFaultException(SOAPFactory.newInstance().createFault(exception.getMessage(), new QName("http://schemas.xmlsoap.org/soap/envelope/", Thread.currentThread().getStackTrace()[1].getClassName()+" "+Thread.currentThread().getStackTrace()[1].getMethodName())));
		}finally{
			try {
				if(con != null) con.close();
			}catch (SQLException e){
				e.printStackTrace();
				}
		}      
      return numRevision;
    }
}
