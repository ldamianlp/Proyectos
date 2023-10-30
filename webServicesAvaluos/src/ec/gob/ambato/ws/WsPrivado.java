package ec.gob.ambato.ws;



import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.DatatypeConverter;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.ws.soap.SOAPFaultException;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import ec.gob.ambato.db.CatastrosDB;
import ec.gob.ambato.db.GadmaConnection;
import ec.gob.ambato.obj.objGis_syncro_imagen_predio;

@Path("/WsSyncro")
public class WsPrivado {
    @POST
    @Path("syncronizar")
    @Produces(MediaType.APPLICATION_JSON)
    public String getSaludoJson(String datosJsonObject,byte[] inImagen) throws SOAPException{    	
    	JsonParser parser = new JsonParser();
    	JsonObject json = (JsonObject) parser.parse(datosJsonObject);    	
    	JsonPrimitive projection = json.getAsJsonPrimitive("IN_SIP_IMAGEN");
    	String iumagen = projection.getAsString();   	
    	
    	GadmaConnection conObj = new GadmaConnection();
        Connection con = null;
        String resultado = "false";
        try{
        	con = conObj.getConnectionSDE();
  	        CatastrosDB objCatastrosDB = new CatastrosDB();	
  	      
	    	byte[] decoded = DatatypeConverter.parseBase64Binary(iumagen);
	    	 //seteo a los object.
	        objGis_syncro_imagen_predio objDatos = new objGis_syncro_imagen_predio();
	        objDatos.setSip_predio(json.get("IN_SIP_PREDIO").getAsString());
	        objDatos.setSip_revision(new BigDecimal(json.get("IN_SIP_REVISION").getAsString()));
	        objDatos.setPti_codigo(new BigDecimal(json.get("IN_PTI_CODIGO").getAsString()));
	        objDatos.setSip_imagen(decoded);
	        objDatos.setSip_nombre_ima(json.get("IN_SIP_NOMBRE_IMA").getAsString());
	        objDatos.setSip_mimetype_ima(json.get("IN_SIP_MIMETYPE_IMA").getAsString());
	        objDatos.setSip_usuario_insert(json.get("IN_SIP_USUARIO_INSERT").getAsString());
	        resultado = objCatastrosDB.insertDatosPredio(con, objDatos);
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
    
    @GET
    @Path("test")
    @Produces(MediaType.TEXT_PLAIN)
    public String test(String inDatos){
    	//sd
    	String retorno =  "Test de prueba";
    	return retorno; //sd
    }
}