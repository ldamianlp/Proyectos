package ec.gob.ws;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("pruebas")
public class Pruebas {

	@Context private javax.servlet.http.HttpServletRequest hsr;	

	@GET
	@Path("cPrueba/")
	@Produces(MediaType.TEXT_PLAIN)
	public String cPrueba(
			){		
		return "HOLA MUNDO";
	}

}
