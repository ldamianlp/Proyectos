package ec.gob.ws;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import ec.gob.dao.ParCantonDAO;
import ec.gob.dao.Par_directorioDAO;
import ec.gob.dao.SpFinaCatNegocioDAO;
import ec.gob.dao.SpFinaEventosDAO;
import ec.gob.dao.SpFinaExpositorDAO;
import ec.gob.dao.SpFinaSolicitudDAO;
import ec.gob.dao.TmpFileDAO;
import ec.gob.dao.UtilsDOA;
import ec.gob.dao.Vw_finado_puestos_asigDAO;
import ec.gob.db.ParProvinciaDB;
import ec.gob.enumeracion.Enums;
import ec.gob.object.ParCantones;
import ec.gob.object.ParProvincia;
import ec.gob.object.Resultado;
import ec.gob.object.ResultadoSorteo;
import ec.gob.object.SpFinaCatNegocio;
import ec.gob.object.SpFinaEvento;
import ec.gob.object.SpFinaExpositor;
import ec.gob.object.SpFinaSolReq;
import ec.gob.object.SpFinaSolReqPK;
import ec.gob.object.SpFinaSolicitud;
import ec.gob.object.SpFinaSolicitudDet;
import ec.gob.object.TmpFile;
import ec.gob.object.VwFinadoPuestosAsig;
import ec.gob.object.objProvinciaCanton;
import ec.gob.persist.JPAUtil;
import ec.gob.utils.Util;
import jcifs.CIFSContext;
import jcifs.Configuration;
import jcifs.config.PropertyConfiguration;
import jcifs.context.BaseContext;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileOutputStream;

@Path("metodo")
public class MetodoFinados {

	@Context private javax.servlet.http.HttpServletRequest hsr;	

	@GET
	@Path("cEventosActivos/")
	@Produces(MediaType.APPLICATION_JSON)
	public String cEventosActivos(
			){	
		SpFinaEventosDAO obj = new SpFinaEventosDAO();
		List<SpFinaEvento> retorno1 = obj.getEventosActivos();
		//		Collection<SpFinaEvento> retornoCol = obj.getEventosActivos();
		//		retornoCol.toArray(retorno1);
		Gson gson = new Gson();
		String lv_cadena = gson.toJson(retorno1);
		return lv_cadena;
	}

	@GET
	@Path("cCategorias/{pIdEvento}")
	@Produces(MediaType.APPLICATION_JSON)
	public String cCategorias(
			@PathParam("pIdEvento") long pIdEvento
			){	
		SpFinaCatNegocioDAO obj = new SpFinaCatNegocioDAO();
		List<SpFinaCatNegocio> retorno1 = obj.getCategoriasByEve(pIdEvento);

		Gson gson = new Gson();
		String lv_cadena = gson.toJson(retorno1);
		return lv_cadena;
	}

	@GET
	@Path("cRequisitos/{pIdCategoria}")
	@Produces(MediaType.APPLICATION_JSON)
	public String cRequisitos(
			@PathParam("pIdCategoria") long pIdCategoria
			){	
//		System.out.println("LLEGO");
		SpFinaCatNegocioDAO obj = new SpFinaCatNegocioDAO();
		ArrayList<HashMap<String,Object>> retorno1 = obj.getRequisitosByCategoria(pIdCategoria);

		Gson gson = new Gson();
		String lv_cadena = gson.toJson(retorno1);
		return lv_cadena;
	}

	@GET
	@Path("cProviciaCanton")
	@Produces(MediaType.APPLICATION_JSON)
	public String cProviciaCanton(){		
		ParProvinciaDB obj = new ParProvinciaDB();
		List<ParProvincia> retorno = obj.getAllProvincias();


		ArrayList<objProvinciaCanton> alProvCanton = new ArrayList<objProvinciaCanton>();
		for(ParProvincia objProv :retorno) {
			//Provicnias
			objProvinciaCanton objProvCant = new objProvinciaCanton();
			objProvCant.setPRO_CODIGO(objProv.getProCodigo());
			objProvCant.setPRO_NOMBRE(objProv.getProNombre());

			ParCantonDAO objCanDao = new ParCantonDAO();
			List<ParCantones> lstCan = objCanDao.getCantonesByProv(objProv.getProCodigo());

			objProvCant.setCantones(lstCan);
			alProvCanton.add(objProvCant);
		}
		Gson gson = new Gson();
		String lv_cadena = gson.toJson(alProvCanton);
		return lv_cadena;
	}	

	@GET
	@Path("validaCedula/{pCedula}")
	@Produces(MediaType.TEXT_PLAIN)	
	public String validaCedula(
			@PathParam("pCedula") String pCedula
			){		

		UtilsDOA obj = new UtilsDOA();
		String resultado = obj.validarCedula(pCedula);
		return resultado;
	}

	@GET
	@Path("cProvicias")
	@Produces(MediaType.APPLICATION_JSON)
	public String cProvicias(){		
		ParProvinciaDB obj = new ParProvinciaDB();
		List<ParProvincia> retorno = obj.getAllProvincias();
		Gson gson = new Gson();
		String lv_cadena = gson.toJson(retorno);
		return lv_cadena;
	}

	@GET
	@Path("cCantones/{pIdProvincia}")
	@Produces(MediaType.APPLICATION_JSON)
	public String cCantones(
			@PathParam("pIdProvincia") long pIdProvincia
			){	
		ParCantonDAO obj = new ParCantonDAO();
		List<ParCantones> retorno = obj.getCantonesByProv(pIdProvincia);

		Gson gson = new Gson();
		String lv_cadena = gson.toJson(retorno);
		return lv_cadena;
	}

	@GET
	@Path("cExpositor/{pIdExpositor}")
	@Produces(MediaType.APPLICATION_JSON)
	public String cExpositor(
			@PathParam("pIdExpositor") String pIdExpositor			
			){	
		SpFinaExpositorDAO obj = new SpFinaExpositorDAO();
		SpFinaExpositor retorno = obj.find(pIdExpositor);

		Gson gson = new Gson();
		String lv_cadena = gson.toJson(retorno);
		return lv_cadena;
	}

	/*
	@POST
	@Path("saveExpositor")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String  saveExpositor(
			//			@PathParam("inputJsonObj") 
			String inputJsonObj
			) {

		//		System.out.println("llego: "+inputJsonObj);
		ResultadoExpositor rExpositor = new ResultadoExpositor();
		Resultado resultado = new Resultado();
		Gson gson =  new Gson();		

		try {
			Util util = new Util();			
			//			String lv_usuario = util.getUser(hsr);
			//verifico si existe el expositor.
			SpFinaExpositorDAO objExpoDao = new SpFinaExpositorDAO();
			//			System.out.println("USUARIO: "+lv_usuario);
			JSONObject obJson = new JSONObject(inputJsonObj);


			Type typeExpositor = new TypeToken<SpFinaExpositor>(){}.getType();

			SpFinaExpositor objExpo = new SpFinaExpositor();
			objExpo= gson.fromJson(obJson.toString(), typeExpositor);


			//verifico si los datos estan correctos o no
			resultado = util.validarFormato(objExpo);
			if(resultado.getSuccess()!= null && resultado.getSuccess().equals("S")) {			

				rExpositor.setId(objExpo.getId());				
				SpFinaExpositor retornoExpo = objExpoDao.find(objExpo.getId());
				if(retornoExpo != null) {
					//					System.out.println("EXISTE");
					//Actualizo
					objExpoDao.update(objExpo);

				}else {
					//					System.out.println("NO EXISTE");
					//inserto
					objExpoDao.save(objExpo);
				}
				resultado.setCodError(Enums.Errores.e00.getCodError());
				resultado.setSuccess(true);;
				resultado.setMensaje(Enums.Errores.e00.getMensaje());
			}
		}catch (Exception e) {
			resultado.setCodError(Enums.Errores.e01.getCodError());
			resultado.setSuccess(false);
			resultado.setError("Excepción: "+e.getLocalizedMessage());
			resultado.setMensaje(Enums.Errores.e01.getMensaje());
		}
		rExpositor.setResultado(resultado);

		return gson.toJson(rExpositor);
	}*/


	@POST
	//	@Path("cPuestosLibres/{pIdEvento}/{pIdCategoria}/{pCantidadPuestos}/{pConsecutivo}")
	@Path("cPuestosLibres")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public javax.ws.rs.core.Response cPuestosLibres(
			String inputJsonObj
			//			@PathParam("pIdEvento") BigDecimal pIdEvento,
			//			@PathParam("pIdCategoria") BigDecimal pIdCategoria,
			//			@PathParam("pCantidadPuestos") int pCantidadPuestos,
			//			@PathParam("pConsecutivo") String pConsecutivo
			){		

//		System.out.println("LLEGO");
		Gson gson =  new Gson();
		ResultadoSorteo resultado = null;// = new ResultadoSorteo();
		//		try {
//		System.out.println("llego: "+inputJsonObj);
		JSONObject obJson = new JSONObject(inputJsonObj);
		BigDecimal pIdEvento = new BigDecimal((String)obJson.get("COD_EVENTO"));
		BigDecimal pIdCategoria = new BigDecimal((String)obJson.get("COD_CAT_NEGO"));
		int pCantidadPuestos = Integer.valueOf((String)obJson.get("CANTI_PUESTO"));
		String pConsecutivo = (String)obJson.get("CONSECUTIVO");
		String pNumeroDocumentoExpo = obJson.getString("ID_EXPOSITOR");

		UtilsDOA objUtil = new UtilsDOA();
		int numPuestosOcupados = objUtil.getCantidadPuestosReservados(pIdEvento,pNumeroDocumentoExpo);
		if((pCantidadPuestos+numPuestosOcupados)>4) {
			return javax.ws.rs.core.Response.status(405).entity("No se puede asignar mas puestos al numero de documento "+pNumeroDocumentoExpo).build();
		}

		//llamo a la vista de puestos disponibles.
		UtilsDOA utlDAO = new UtilsDOA();
		ArrayList<HashMap<String,Object>> lstPuestosLibres = utlDAO.getPuestosLibres(pIdEvento, pIdCategoria);

		for(HashMap<String,Object> hash : lstPuestosLibres) {

			String puestoslibres = (String)hash.get("PUESTO_DISP");//"1,2";//,3,4,5,6,7";
			String codlibres = (String)hash.get("COD_PUESTO");//"11,22";//,33,44,55,66,77";

			BigDecimal codBloq = (BigDecimal)hash.get("COD_BLOQ");

			ArrayList<HashMap<String,Object>> lstPuestosParam = utlDAO.getPuestosParametrizados(pIdEvento, pIdCategoria, codBloq);
			ArrayList<HashMap<String,Object>> lstPuestosOcup = utlDAO.getPuestosOcupados(pIdEvento, pIdCategoria, codBloq);


			String[] alPuestos = puestoslibres.split(",");
			List<String> listPuestosLibres = Arrays.asList(alPuestos);		

			Util util = new Util();
			//solo si es puestos consecutivos
			if(pConsecutivo.equals("S")) {
//				int conta = 1;
				String inicioS = listPuestosLibres.get(0);
				int inicio = Integer.valueOf(inicioS);// Integer(inicioS);
				String finS = listPuestosLibres.get(listPuestosLibres.size()-1);
				int fin = Integer.valueOf(finS);//new Integer(finS);
				int atras = inicio;
				int conta = 0;
				if(pCantidadPuestos>1) {
					if(inicio!=fin) {
						for(int i=inicio;i<=fin ;i++) {
							//				int totalLibres = listPuestosLibres.size();
							//				for (int k = 0;k<totalLibres;k++) {
							//					if(Long.valueOf(i)==Long.valueOf((String)listPuestosLibres.get(k))) {


							if(listPuestosLibres.contains(String.valueOf(i))) {
								if(i!=inicio) {
									if(atras==(i-1)) {
										atras = i;
										conta ++;
										if(conta>=pCantidadPuestos) {
											break;
										}

									}
								}else {
									if(atras==(i)) {
										atras = i;
										conta ++;
										if(conta>=pCantidadPuestos) {
											break;
										}
									}
								}

							}else {
								conta = 0;
							}
							atras = i;
						}
					}
				}
				else {
					if(listPuestosLibres.size()>=pCantidadPuestos) {
						conta ++;					
					}
				}
				
				/*if(inicio!=fin) {
					for(int i=inicio;i<=fin ;i++) {
//						int totalLibres = listPuestosLibres.size();
//						for (int k = 0;k<totalLibres;k++) {
//							if(Long.valueOf(i)==Long.valueOf((String)listPuestosLibres.get(k))) {
							if(i!=inicio) {
								if(listPuestosLibres.contains(String.valueOf(i))) {
							if(atras==(i-1)) {
								atras = i;
								conta ++;
								if(conta>=pCantidadPuestos) {
									break;
								}
	
							}else {
								conta = 0;
							}
								}
//						}
//						}
					}
					}
				}*/
				if(conta>=pCantidadPuestos) {
//					System.out.println("SI HAY DISPONIBILIDAD DE PUESTOS CONSECUTIVOS EN EL BLOQUE");

					resultado = util.sorteoConsecutivos(puestoslibres,codlibres, pCantidadPuestos);
					HashMap<String,Object> hashPuestos = new HashMap<String, Object>();
					hashPuestos.put("COD_BLOQ", hash.get("COD_BLOQ"));
					hashPuestos.put("BLOQUE", hash.get("BLOQUE"));
					resultado.setBloque(gson.toJson(hashPuestos));
					resultado.setPuestos(gson.toJson(lstPuestosParam));
					resultado.setOcupados(gson.toJson(lstPuestosOcup));
					break;
				}else {
					//salata al siguiente bloque
//					System.out.println("NO HAY DISPONIBILIDAD DE PUESTOS EN EL BLOQUE - SALTA AL SIGUIENTE BLOQUE - PUESTOS CONTINUOS");
					continue;
				}

			}if(pConsecutivo.equals("N")) {
				//verifico si hay disponibles de acuerdo al numero de puesto solicitados
				if(listPuestosLibres.size()>=pCantidadPuestos) {
					resultado = util.sorteoRandom(puestoslibres,codlibres, pCantidadPuestos);
					HashMap<String,Object> hashPuestos = new HashMap<String, Object>();
					hashPuestos.put("COD_BLOQ", hash.get("COD_BLOQ"));
					hashPuestos.put("BLOQUE", hash.get("BLOQUE"));
					resultado.setBloque(gson.toJson(hashPuestos));
					resultado.setPuestos(gson.toJson(lstPuestosParam));
					resultado.setOcupados(gson.toJson(lstPuestosOcup));
					break;
				}else {
					//salata al siguiente bloque
//					System.out.println("NO HAY DISPONIBILIDAD DE PUESTOS EN EL BLOQUE - SALTA AL SIGUIENTE BLOQUE - PUESTOS DISPERSOS");
					continue;
				}

			}

		}
		/*}catch (Exception e) {
			resultado = new ResultadoSorteo();
			resultado.setCodError(Enums.Errores.e03.getCodError());
			resultado.setError(e.getMessage());
			resultado.setMensaje(Enums.Errores.e03.getMensaje());
			return gson.toJson(resultado);
		}*/
		if(resultado==null) {
			//			return gson.toJson(resultado);
			//		else {
			resultado = new ResultadoSorteo();
			resultado.setError(Enums.Errores.e02.getCodError());
			resultado.setMensaje(Enums.Errores.e02.getMensaje());

			return javax.ws.rs.core.Response.status(406).entity("Puestos no disponibles para la Categoria Seleccionada").build();
			//			return gson.toJson(resultado);
		}
		return javax.ws.rs.core.Response.ok(gson.toJson(resultado), MediaType.APPLICATION_JSON).build();//lv_cadena;
	}


	@SuppressWarnings("deprecation")
	@POST
	@Path("saveSolicitud")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveSolicitud(
			String inputJsonObj
			){		

//		System.out.println("LLEGO");
		Gson gson =  new Gson();

		//		try {
//		System.out.println("llego: "+inputJsonObj);
		JSONObject obJson = new JSONObject(inputJsonObj);
		String inSessionId = (String)obJson.get("SESSION_ID");
		BigDecimal inCOD_EVENTO = obJson.getBigDecimal("COD_EVENTO");
		BigDecimal inCOD_CAT_NEGO = obJson.getBigDecimal("COD_CAT_NEGO");
		BigDecimal inCOD_BLOQ = obJson.getBigDecimal("COD_BLOQ");
		BigDecimal inCAN_PUESTO = obJson.getBigDecimal("CAN_PUESTO");

		JSONObject inExpositor = obJson.getJSONObject("expositor");
		JSONArray inPuestos = obJson.getJSONArray("puestos");
//		System.out.println("EXPOSITOR: "+inExpositor);

		SpFinaExpositorDAO objExpoDao = new SpFinaExpositorDAO();		

		Util util = new Util();
		Type typeExpositor = new TypeToken<SpFinaExpositor>(){}.getType();
		SpFinaExpositor objExpo = new SpFinaExpositor();
		objExpo= gson.fromJson(inExpositor.toString(), typeExpositor);
		objExpo.setId(objExpo.getId().toUpperCase());
		objExpo.setNombre(objExpo.getNombre().toUpperCase());
		Resultado resultadoSolicitud = new Resultado();
		//verifico si los datos estan correctos o no
		resultadoSolicitud = util.validarFormato(objExpo);

		SpFinaExpositor retornoExpo = objExpoDao.find(objExpo.getId());


		EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
		em.getTransaction().begin();
		try {
			//datos del expositor
			if(resultadoSolicitud.getSuccess()!= null && resultadoSolicitud.getSuccess().equals(true)) {
				if(retornoExpo != null) {
					//					System.out.println("EXISTE");
					//Actualizo				
					em.merge(objExpo);

				}else {
					//					System.out.println("NO EXISTE");
					//inserto
					em.persist(objExpo);
				}

			}else {
				//				resultadoSolicitud.setError("Excepción: "+Enums.Errores.e10.getMensaje()+ " - "+resultadoSolicitud.getCodError()+" - "+resultadoSolicitud.getMensaje());
				//				resultadoSolicitud.setCodError(Enums.Errores.e10.getCodError());
				resultadoSolicitud.setSuccess(false);
				em.getTransaction().rollback();
				em.close();		
				return javax.ws.rs.core.Response.status(411).entity(resultadoSolicitud.getError()).build();
			}
			//			if(resultadoSolicitud.getSuccess().equals(true)) {
			//verifico que no exista mas reservas con esos puestos.
			String cadenaCodPuestos="";
			int cont = 0;
			for(Object codPuesto:inPuestos) {
				JSONObject b = (JSONObject) codPuesto;
				if(cont != inPuestos.length()-1) {
					cadenaCodPuestos = cadenaCodPuestos+b.getBigDecimal("COD_PUESTO").toString()+",";
//					String puestos = inPuestos.toString();
				}else {
					cadenaCodPuestos = cadenaCodPuestos+b.getBigDecimal("COD_PUESTO").toString();
				}
				cont++;
			}
			
			Query queryCantPuesto = em.createNativeQuery("SELECT COUNT(*) FROM GADMAPPS.VW_FINADO_PUESTOS_ASIG WHERE COD_EVENTO = (:COD_EVENTO) AND ID_EXPOSITOR = (:ID_EXPOSITOR)")           
					.setParameter("COD_EVENTO", inCOD_EVENTO)
					.setParameter("ID_EXPOSITOR", objExpo.getId())
					;
			Object cantiPuestos = queryCantPuesto.getSingleResult();
			int totalPuestos = Integer.valueOf(cantiPuestos.toString());
			
			if(totalPuestos>=4) {
				em.getTransaction().rollback();
				em.close();		
				return javax.ws.rs.core.Response.status(405).entity("Ya a exedido la cantidad de puestos permitidos por persona").build();
			}else {
//			System.out.println("*****************************************************: PUESTOS A SOLICITAR: "+cadenaCodPuestos);
			Query queryExiste = em.createNativeQuery("SELECT COUNT(*) FROM GADMAPPS.VW_FINADO_PUESTOS_ASIG WHERE COD_EVENTO = (:COD_EVENTO) AND COD_CAT_NEGO = (:COD_CATEGORIA) "
					+ "AND COD_BLOQ = (:COD_BLOQUE) AND COD_PUESTO IN ( "+cadenaCodPuestos+" )")           
					.setParameter("COD_EVENTO", inCOD_EVENTO)
					.setParameter("COD_CATEGORIA", inCOD_CAT_NEGO)
					.setParameter("COD_BLOQUE", inCOD_BLOQ)
//					.setParameter("COD_PUESTO", puestos)
					;
			Object existeReservaPuesto = queryExiste.getSingleResult();
			int totalExistePuesto = Integer.valueOf(existeReservaPuesto.toString());
			
			if(totalExistePuesto>0) {
				em.getTransaction().rollback();
				em.close();		
				return javax.ws.rs.core.Response.status(410).entity("Puestos Reservados por otra persona").build();
			}else {
			//datos de solicitud
			SpFinaSolicitud objSolicitud = new SpFinaSolicitud();
//			System.out.println("ID EXPOSITOR: "+objExpo.getId());
			objSolicitud.setIdExpositor(objExpo.getId());
			objSolicitud.setCodEvento(inCOD_EVENTO);
			objSolicitud.setCodCatNego(inCOD_CAT_NEGO);
			objSolicitud.setCodBloq(inCOD_BLOQ);
			objSolicitud.setCanPuesto(inCAN_PUESTO);
			String EstadoSolicitud = "";
			if(objSolicitud.getCodCatNego().equals(new BigDecimal(1)))//SI ES ARTESANIA COLOCO ESTADO TEMPORAL
			{
			objSolicitud.setEstado("Z");
			EstadoSolicitud = "Z";
			}else {
				objSolicitud.setEstado("P");
				EstadoSolicitud = "P";
			}
//			LocalDateTime b = LocalDateTime.now();
//			java.sql.Date now = new java.sql.Date(new Date().getTime());
//			System.out.println("FECHA ACTUAL: "+b);
//			objSolicitud.setFecSol(Date.from(b));
			objSolicitud.setUsuInsert("FINADOS_WEB");
//			objSolicitud.setFecInsert(now);
			//ingreso Solicitud
			em.persist(objSolicitud);
			//datos de Detalle de solicitud

			for(Object codPuesto:inPuestos) {
//				System.out.println("COD PUESTO: "+codPuesto);
				SpFinaSolicitudDet objSolDet = new SpFinaSolicitudDet();
				objSolDet.setCodSol(objSolicitud.getCodigo());
				JSONObject b = (JSONObject) codPuesto;
				objSolDet.setCodPuesto(b.getBigDecimal("COD_PUESTO"));
				//verifico que el codigo del puesto este disponible en la vista del YU LUng Li.
				Query query = em.createNativeQuery("SELECT COUNT(*) FROM GADMAPPS.VW_FINADO_PUESTOS_ASIG WHERE COD_EVENTO = (:COD_EVENTO) AND COD_CAT_NEGO = (:COD_CATEGORIA) "
						+ "AND COD_BLOQ = (:COD_BLOQUE) AND COD_PUESTO = (:COD_PUESTO)")           
						.setParameter("COD_EVENTO", inCOD_EVENTO)
						.setParameter("COD_CATEGORIA", inCOD_CAT_NEGO)
						.setParameter("COD_BLOQUE", inCOD_BLOQ)
						.setParameter("COD_PUESTO", inCAN_PUESTO);
				Object resultList = query.getSingleResult();

				int puestosRegistrados = Integer.valueOf(resultList.toString());
				if(puestosRegistrados==0) {
					//inserto detalle
					em.persist(objSolDet);
				}else {
					em.getTransaction().rollback();
					em.close();					
					return javax.ws.rs.core.Response.status(407).entity("Los puestos seleccionados ya fueron reservados por otro Ciudadano").build();
				}
			}

			//requisitos
			LocalDateTime lv_now   = LocalDateTime.now();  
			Par_directorioDAO directorioDAO =  new Par_directorioDAO();
			String lv_ruta = directorioDAO.find("DIR_FINADOS").getParUrl() + lv_now.getYear() + "/";


			TmpFileDAO tmpFileDao = new TmpFileDAO();
			//			ArrayList<HashMap<String, Object>> datosArchivosTmp = tmpFileDao.getFilesTmpBySession(inSessionId);
			List<TmpFile> datosArchivosTmp = tmpFileDao.getFilesTmpBySession(inSessionId);
			//			for(HashMap<String, Object> hash : datosArchivosTmp) {
			for(TmpFile hash : datosArchivosTmp) {
				/*
			 COD_REQUISITO", objects[0]);
			    	   tmpObj.put("FILENAME", objects[1]);
			    	   tmpObj.put("ARCHIVO
				 */
				SpFinaSolReq objReq = new SpFinaSolReq();
				SpFinaSolReqPK pkReq = new SpFinaSolReqPK();					
				pkReq.setCodSol(Long.valueOf(objSolicitud.getCodigo().toString()));
				pkReq.setCodNegocio(Long.valueOf(inCOD_CAT_NEGO.toString()));			
				//				pkReq.setCodRequisito(Long.valueOf(hash.get("COD_REQUISITO").toString()));
				pkReq.setCodRequisito(Long.valueOf(hash.getId().getIdRequisito()));

				objReq.setId(pkReq);
				objReq.setMimetype(hash.getMymetype());
//				objReq.setFilename(hash.getNombre());



				//				String NomArchivo = (String)hash.get("FILENAME");
				String NomArchivo = hash.getNombre();
				String[] arrNomb = NomArchivo.split("\\.");
				String nombreFile = "REQ_"+pkReq.getCodSol()+"_"+pkReq.getCodNegocio()+"_"+pkReq.getCodRequisito()+"."+arrNomb[arrNomb.length-1];
				String pathFileServerSamba = lv_ruta+nombreFile;//"REQ_"+pkReq.getCodSol()+"_"+pkReq.getCodNegocio()+"_"+pkReq.getCodRequisito()+"."+arrNomb[arrNomb.length-1];
				objReq.setFilename(nombreFile);
				String path = "smb:"+pathFileServerSamba;
				String pathFileServer = pathFileServerSamba.replace("/", "\\");
				objReq.setFilepath(pathFileServer);

				String v_userSMB = directorioDAO.find("USR_DIR_FINA").getParUrl();
				String v_passSMB = directorioDAO.find("PAS_DIR_FINA").getParUrl();
				v_userSMB = new String(Base64.getDecoder().decode(v_userSMB));
				v_passSMB = new String(Base64.getDecoder().decode(v_passSMB));
				Configuration config = new PropertyConfiguration(new Properties());
				CIFSContext context = new BaseContext(config);
				context = context.withCredentials(new NtlmPasswordAuthentication(null, 
						directorioDAO.find("DIR_FINADOS").getParUrl(), 
						v_userSMB, 
						v_passSMB));
//				System.out.println("RUTA SAMBA: "+path);
				//			InputStream IsArchivo = new ByteArrayInputStream((byte[])hash.get("ARCHIVO"));
				SmbFile sFile = new SmbFile(path, context);
				SmbFileOutputStream sfos = new SmbFileOutputStream(sFile);
				//				Object file1 = hash.get("ARCHIVO");
				byte[] file1 = hash.getArchivo();

				sfos.write(file1);
				sfos.close();

				//inserto requisitos
				em.persist(objReq);
			}
			//elimino los temporales
			Query query = em.createNativeQuery("Delete from gadmapps.TMP_FILE where ID_SESSION=?");
			query.setParameter(1, inSessionId);
			query.executeUpdate();

			//REALIZO EL COMIT DE LA TRANSACCION
			em.getTransaction().commit();
			em.close();

			UtilsDOA objUtil = new UtilsDOA();
//			if(objSolicitud.getEstado()=="P") {
			if(EstadoSolicitud.equals("P")) {
				System.out.println("INICIA ENVIO EMAIL SOL: "+objSolicitud.getCodigo());
				String resEmail = objUtil.sendEmailAprobado(objSolicitud.getCodigo());
				System.out.println("ENVIO EMAIL: "+resEmail);
			}
			ArrayList<HashMap<String, Object>> detalleFactura = objUtil.getDetalleFactura(objSolicitud.getCodigo());
			resultadoSolicitud.setSuccess(true);
			//seteo el detalle de la factura
			resultadoSolicitud.setDatos(gson.toJson(detalleFactura));
			resultadoSolicitud.setMensaje(Enums.Errores.e00.getMensaje());
			//			}else {				
			//				resultadoSolicitud.setSuccess(false);
			//				return javax.ws.rs.core.Response.status(javax.ws.rs.core.Response.Status.NOT_FOUND).entity(resultadoSolicitud.getError()).build();
			//			}
		}
			}
		}catch (Exception e) {
			em.getTransaction().rollback();
			em.close();
			e.printStackTrace();
//			resultadoSolicitud.setCodError(Enums.Errores.e04.getCodError());
			resultadoSolicitud.setSuccess(false);
			resultadoSolicitud.setError("Excepción: "+e.getLocalizedMessage());
			resultadoSolicitud.setMensaje(Enums.Errores.e04.getMensaje());
			return javax.ws.rs.core.Response.status(412).entity(resultadoSolicitud.getError()).build();
		}
		return javax.ws.rs.core.Response.ok(gson.toJson(resultadoSolicitud), MediaType.APPLICATION_JSON).build();
		//		return gson.toJson(resultadoSolicitud);

	}

	@POST
	@Path("cDatoSeguro")		
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public javax.ws.rs.core.Response cDatoSeguro(
			String inputJsonObj
			//				@PathParam("pTipoDocumento") String pTipoDocumento,
			//				@PathParam("pNumeroDocumento") String pNumeroDocumento
			){	
		
		Gson gson =  new Gson();
//		System.out.println("llego: "+inputJsonObj);
		JSONObject obJson = new JSONObject(inputJsonObj);
		String pTipoDocumento = obJson.getString("TIPO_DOC");
		String pNumeroDocumento = obJson.getString("NUM_DOC");
		BigDecimal pCodEvento = obJson.getBigDecimal("COD_EVENTO");

		Resultado resultado = new Resultado();
		String CedulaValida;
		//			HttpURLConnection conne = null;
		try {
			if(pTipoDocumento.equalsIgnoreCase("CED")) {
				if(pNumeroDocumento.length()==10) {
				UtilsDOA objUtil = new UtilsDOA();
				String resultado1;
				try {
					resultado1 = objUtil.getDatoscedula(pNumeroDocumento);
				}catch (Exception e) {
					return javax.ws.rs.core.Response.status(401).entity("No existe conexión con el Servicio de Dato Seguro").build();
				}

				JSONObject obJsonCED = new JSONObject(resultado1);
				try {
					String cedulaWS = obJsonCED.getString("cedula");
					CedulaValida = "{\"VALIDA\":true}";
					resultado.setDatos(CedulaValida);
				}catch(Exception e) {
					CedulaValida = "{\"VALIDA\":false}";
					resultado.setDatos(CedulaValida);
					return javax.ws.rs.core.Response.status(402).entity(gson.toJson(resultado)).build();
				}
				int numPuestosOcupados = objUtil.getCantidadPuestosReservados(pCodEvento,pNumeroDocumento);
				if(numPuestosOcupados>=4) {
					return javax.ws.rs.core.Response.status(405).entity("No se puede asignar mas puestos al numero de documento "+pNumeroDocumento).build();
				}

				//				if(cedulaWS!= null && obJsonCED.getString("cedula").equals(pNumeroDocumento)){	

				//					return javax.ws.rs.core.Response.ok(lv_cadena, MediaType.APPLICATION_JSON).build();//lv_cadena;
				//				}
				//				
				//				
				//				{"actaMatrimonio" : "54","anioInscripcionNacimiento" : "1988","callesDomicilio" : "SN SN","cedula" : "1804161949","cedulaConyuge" : "1804880076","cedulaMadre" : "1802179646","cedulaPadre" : "1801878594","condicionCiudadano" : "CIUDADANO","conyuge" : "PINTO PAUCAR MARIA DE LOS ANGELES","domicilio" : "TUNGURAHUA/AMBATO/IZAMBA","estadoCivil" : "CASADO","fechaMatrimonio" : "19/04/2011","fechaNacimiento" : "19/01/1988","lugarNacimiento" : "TUNGURAHUA/AMBATO/LA MERCED","nacionalidad" : "ECUATORIANA","nombre" : "LAURA PAUCAR LUIS DAMIAN","nombreMadre" : "PAUCAR GUANGASHI ROSA ELVIRA","nombrePadre" : "LAURA CAGUANA LUIS OLMEDO","numeroCasa" : "SN"}
				//				String inSessionId = (String)obJson.get("SESSION_ID");



				//				resultado.setDatos(resultado1);
				}else {
					return javax.ws.rs.core.Response.status(409).entity("El número de CEDULA no cumple con la cantidad de Digitos").build();
				}
			}
			else {
				if(pTipoDocumento.equalsIgnoreCase("RUC")) {
					if(pNumeroDocumento.length()==13) {
					String resultado1;
					UtilsDOA objUtil = new UtilsDOA();
					try {						
						resultado1 = objUtil.getDatosRUC(pNumeroDocumento);
					}catch (Exception e) {
						return javax.ws.rs.core.Response.status(401).entity("No existe conexión con el Servicio de Dato Seguro").build();
					}

					JSONObject obJsonRUC = new JSONObject(resultado1);
					String RUCValida;
					try {
						JSONObject rucWS = obJsonRUC.getJSONObject("datosPrincipales");
						String nuRUC = rucWS.getString("numeroRuc");
						if(!nuRUC.equalsIgnoreCase("")) {
							RUCValida = "{\"VALIDA\":true}";
						}else {							
							RUCValida = "{\"VALIDA\":false}";
							return javax.ws.rs.core.Response.status(403).entity("RUC Invalido").build();
						}						
						resultado.setDatos(RUCValida);
					}catch(Exception e) {
						CedulaValida = "{\"VALIDA\":false}";
						resultado.setDatos(CedulaValida);
						return javax.ws.rs.core.Response.status(403).entity(gson.toJson(resultado)).build();
					}
					int numPuestosOcupados = objUtil.getCantidadPuestosReservados(pCodEvento,pNumeroDocumento);
					if(numPuestosOcupados>=4) {
						return javax.ws.rs.core.Response.status(405).entity("No se puede asignar mas puestos al número de documento "+pNumeroDocumento).build();
					}
					//					{"datosPrincipales": {"numeroRuc" : "1804161949001","personaSociedad" : "PNL","razonSocial" : "LAURA PAUCAR LUIS DAMIAN","nombreFantasiaComercial" : "","obligado" : "N","fechaInicioActividades" : "2010-12-13 00:00:00","fechaReInicioActividades" : "2016-05-26 00:00:00","fechaActualizacion" : "2016-05-26 00:00:00","fechaCancelacion" : "","fechaSuspencionDefinitiva" : "2017-01-24 11:25:49","actividadEconomicaPrincipal" : "PRESTACION DE SERVICIOS PROFESIONALES.","estadoPersonaNatural" : "SUSPENDIDO","estadoSociedad" : "","tipoContribuyente" : "PERSONAS NATURALES","claseContribuyente" : "OTROS"}, "detalle": {"items":[{"numeroRuc" : "1804161949001","numeroEstableciminiento" : "1","estadoEstablecimiento" : "CERRADO","calle" : "PRINCIPAL","interseccion" : "","numero" : "S/N","tipoEstablecimiento" : "MATRIZ"}]}}


					//					String resultado1 = objUtil.getDatosRUC(pNumeroDocumento);
					//					resultado.setDatos(resultado1);		
					}else {
						return javax.ws.rs.core.Response.status(408).entity("El número de RUC no cumple con la cantidad de Dígitos").build();
					}
				}else {
					//validar el numero de puestos.

					return javax.ws.rs.core.Response.status(404).entity("Tipo de Documento no Válido").build();
				}
			}

		}catch (Exception e) {
			e.printStackTrace();
//			resultado.setCodError(Enums.Errores.e51.getCodError());
			resultado.setSuccess(false);
			resultado.setError("Excepción: "+e.getLocalizedMessage());
			resultado.setMensaje(Enums.Errores.e51.getMensaje());
			return javax.ws.rs.core.Response.status(javax.ws.rs.core.Response.Status.NOT_FOUND).entity(gson.toJson(resultado)).build();
		} 

		String lv_cadena = gson.toJson(resultado);
		return javax.ws.rs.core.Response.ok(lv_cadena, MediaType.APPLICATION_JSON).build();//lv_cadena;
		
	}
	
	@POST
	@Path("cDatos")		
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public javax.ws.rs.core.Response cDatos(
			String inputJsonObj
			){	
		
		Gson gson =  new Gson();
//		System.out.println("llego: "+inputJsonObj);
		JSONObject obJson = new JSONObject(inputJsonObj);
		String pTipoDocumento = obJson.getString("TIPO_DOC");
		String pNumeroDocumento = obJson.getString("NUM_DOC");

		Resultado resultado = new Resultado();
//		String CedulaValida;
		String resultado1;
		//			HttpURLConnection conne = null;
		try {
			if(pTipoDocumento.equalsIgnoreCase("CED")) {
				if(pNumeroDocumento.length()==10) {
				UtilsDOA objUtil = new UtilsDOA();				
				try {
					resultado1 = objUtil.getDatoscedula(pNumeroDocumento);
					resultado.setSuccess(true);
					resultado.setDatos(resultado1);
				}catch (Exception e) {
					return javax.ws.rs.core.Response.status(401).entity("No existe conexión con el Servicio de Dato Seguro").build();
				}

			}
			else {
				if(pTipoDocumento.equalsIgnoreCase("RUC")) {
					if(pNumeroDocumento.length()==13) {
					
					UtilsDOA objUtil = new UtilsDOA();
					try {						
						resultado1 = objUtil.getDatosRUC(pNumeroDocumento);
						resultado.setSuccess(true);
						resultado.setDatos(resultado1);
					}catch (Exception e) {
						return javax.ws.rs.core.Response.status(401).entity("No existe conexión con el Servicio de Dato Seguro").build();
					}

					
				}else {
					//validar el numero de puestos.

					return javax.ws.rs.core.Response.status(404).entity("Tipo de Documento no Válido").build();
				}
			}

		}
			}
		}catch (Exception e) {
			e.printStackTrace();
//			resultado.setCodError(Enums.Errores.e51.getCodError());
			resultado.setSuccess(false);
			resultado.setError("Excepción: "+e.getLocalizedMessage());
			resultado.setMensaje(Enums.Errores.e51.getMensaje());
			return javax.ws.rs.core.Response.status(javax.ws.rs.core.Response.Status.NOT_FOUND).entity(gson.toJson(resultado)).build();
		} 

		String lv_cadena = gson.toJson(resultado);
		return javax.ws.rs.core.Response.ok(lv_cadena, MediaType.APPLICATION_JSON).build();//lv_cadena;
		
	}
	
	@GET
	@Path("cFecha")		
	@Produces(MediaType.TEXT_PLAIN)
	public javax.ws.rs.core.Response cFecha(
			String inputJsonObj
			//				@PathParam("pTipoDocumento") String pTipoDocumento,
			//				@PathParam("pNumeroDocumento") String pNumeroDocumento
			){
		SpFinaSolicitud obj = new SpFinaSolicitud();
		Date fecha1 = new Date();
		obj.setFecSol(new Timestamp(fecha1.getTime()));
		java.sql.Date fecha2 = new java.sql.Date(new Date().getTime());
		obj.setFecInsert(new Timestamp(fecha2.getTime()));
		
		SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String a = formato.format(new Date());
		HashMap<String, Object> hash = new HashMap<String, Object>();
		
		
		hash.put("FECHA1: ", obj.getFecSol());
		hash.put("FECHA2: ", obj.getFecInsert());
		hash.put("FECHA3: ", a);
		Gson gson =  new Gson();
		return javax.ws.rs.core.Response.ok(gson.toJson(hash), MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Path("cPuestosExpositor")	
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public javax.ws.rs.core.Response cPuestosExpositor(
			String inputJsonObj
//			@PathParam("pTipoDocumento") String pTipoDocumento,			
//			@PathParam("pIdExpositor") String pIdExpositor
	){	
		JSONObject obJson = new JSONObject(inputJsonObj);
		String inTipoDocumento = (String)obJson.get("type");
		String inIdExpositor = (String)obJson.get("id_expositor");
		
		
		Vw_finado_puestos_asigDAO obj = new Vw_finado_puestos_asigDAO();
		ArrayList<HashMap<String,Object>> retorno = obj.getSolicitudes(inIdExpositor);

		Gson gson = new Gson();
		String lv_cadena = gson.toJson(retorno);
//		HashMap<String, Object> hash = new HashMap<String, Object>();
//		
//		
//		hash.put("FECHA1: ", "1");
//		hash.put("FECHA2: ", "2");
//		hash.put("FECHA3: ", "3");
//		Gson gson =  new Gson();
		return javax.ws.rs.core.Response.ok(gson.toJson(retorno), MediaType.APPLICATION_JSON).build();
	}
}
