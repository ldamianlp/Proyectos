package ec.gob.ws;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;

import ec.gob.utils.Util;

@Path("metodo")
public class MetodoGis {

	@Context private javax.servlet.http.HttpServletRequest hsr;	

	@GET
	@Path("getParroquiaByIdPolitico/{pIdPolitico}")
	@Produces(MediaType.APPLICATION_JSON)
	public javax.ws.rs.core.Response getParroquiaByIdPolitico(
		@PathParam("pIdPolitico") String pIdPolitico	
	){	

		Gson gson = new Gson();
		HttpURLConnection conne = null;
		String output="";
		//String lv_token="";
		try {
			//			lv_token = getToken(getAuthorization(tmpRequest));			
			URL url = new URL("https://gis.ambato.gob.ec:444/api/Imagen/NombreParroquia/"+pIdPolitico);
			conne = (HttpURLConnection) url.openConnection();
			conne.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			conne.setDoInput(true);
			conne.setDoOutput(true);
			conne.setRequestMethod("GET");
			conne.setRequestProperty("Accept", "application/json");
			
//			String userpass = "test" + ":" + "test";
//			String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userpass.getBytes()));
//			conne.setRequestProperty ("Authorization", basicAuth);
			conne.setRequestProperty ("Authorization", new Util().getAutenticationWsGis());

			//            OutputStream os = conne.getOutputStream();		
			//			os.write(lv_cadena.getBytes());		
			//			os.close(); 
			if (conne.getResponseCode() != 200) {
				//throw new RuntimeException("Failed : HTTP Error code : "+ conne.getResponseCode());
				return javax.ws.rs.core.Response.status(conne.getResponseCode()).entity("Ha ocurrido un error al momnento de llamar al WS GIS").build();
			}else {
				InputStream in = new BufferedInputStream(conne.getInputStream());
				output = IOUtils.toString(in, "UTF-8");
				//			System.out.println("ReplicarInfraccion:" + output);            
				in.close();
//				conne.disconnect();
//				System.out.println("RETORNO: "+output);
			}

		} catch (Exception e) {                        
			return javax.ws.rs.core.Response.status(401).entity("Ha ocurrido un error al momento de llamar al WS GIS. Error en método").build();            
		} finally {
			conne.disconnect();
//			String retorno = gson.toJson(output);
		}
		return javax.ws.rs.core.Response.ok(gson.toJson(output), MediaType.APPLICATION_JSON).build();
	}

	@GET
	@Path("getImagenB64/{pClaveCat}/{pIdParroquia}")
	@Produces(MediaType.TEXT_PLAIN)
	public javax.ws.rs.core.Response getImagenB64(
		@PathParam("pClaveCat") String pClaveCat,
		@PathParam("pIdParroquia") String pIdParroquia	
	){	

//		Gson gson = new Gson();
		HttpURLConnection conne = null;
		String output="";
		//String lv_token="";
		try {
			//			lv_token = getToken(getAuthorization(tmpRequest));			
			URL url = new URL("https://gis.ambato.gob.ec:444/api/Imagen/MapaBase/"+pClaveCat+"/"+pIdParroquia+"/base64");
			conne = (HttpURLConnection) url.openConnection();
			conne.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			conne.setDoInput(true);
			conne.setDoOutput(true);
			conne.setRequestMethod("GET");
			conne.setRequestProperty("Accept", "application/json");
//			String userpass = "test" + ":" + "test";
//			String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userpass.getBytes()));
//			conne.setRequestProperty ("Authorization", basicAuth);
			conne.setRequestProperty ("Authorization", new Util().getAutenticationWsGis());

			//            OutputStream os = conne.getOutputStream();		
			//			os.write(lv_cadena.getBytes());		
			//			os.close(); 
			if (conne.getResponseCode() != 200) {
				//throw new RuntimeException("Failed : HTTP Error code : "+ conne.getResponseCode());
				return javax.ws.rs.core.Response.status(conne.getResponseCode()).entity("Ha ocurrido un error al momnento de llamar al WS GIS").build();
			}else {
				InputStream in = new BufferedInputStream(conne.getInputStream());
				output = IOUtils.toString(in, "UTF-8");
				//			System.out.println("ReplicarInfraccion:" + output);            
				in.close();
//				conne.disconnect();
//				System.out.println("RETORNO: "+output);
			}

		} catch (Exception e) {                        
			return javax.ws.rs.core.Response.status(401).entity("Ha ocurrido un error al momento de llamar al WS GIS. Error en método").build();            
		} finally {
			conne.disconnect();
//			String retorno = gson.toJson(output);
		}
		return javax.ws.rs.core.Response.ok(output, MediaType.APPLICATION_JSON).build();
	}
}
