package ec.gob.utils;

import javax.ws.rs.core.Response;

import ec.gob.ws.MetodoGis;

public class Test {
//	private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(Test.class);
	public static void main(String[] args) {
		MetodoGis metodo = new MetodoGis();
		Response resultado = metodo.getParroquiaByIdPolitico("180151");
		String obj = (String)resultado.getEntity();
		System.out.println("FINALIZO: "+obj);
//		Response resultado = metodo.getImagenB64("5601041027", "IZAMBA");
//		String obj1 = (String)resultado.getEntity();
//		System.out.println("FINALIZO2: "+obj1);
		/*Gson gson = new Gson();
		HttpURLConnection conne = null;
		String output="";

		try {

            URL url = new URL("https://gis.ambato.gob.ec:444/api/Imagen/NombreParroquia/180150");
            conne = (HttpURLConnection) url.openConnection();
            conne.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conne.setDoInput(true);
            conne.setDoOutput(true);
            conne.setRequestMethod("GET");
            conne.setRequestProperty("Accept", "application/json");
            String userpass = "test" + ":" + "test";
            String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userpass.getBytes()));
            conne.setRequestProperty ("Authorization", basicAuth);
            
//            OutputStream os = conne.getOutputStream();		
//			os.write(lv_cadena.getBytes());		
//			os.close(); 
            if (conne.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP Error code : "
                        + conne.getResponseCode());
            }
            InputStream in = new BufferedInputStream(conne.getInputStream());
            output = IOUtils.toString(in, "UTF-8");
//			System.out.println("ReplicarInfraccion:" + output);            
            in.close();
            conne.disconnect();
            System.out.println("RETORNO: "+output);
            
        } catch (Exception e) {                        
            LOGGER.error(e.getMessage() + "Exception in ReplicarInfraccion", e);     
            
        } finally {
        	conne.disconnect();
        	String retorno = gson.toJson(output);
		}*/
		
	}
}
