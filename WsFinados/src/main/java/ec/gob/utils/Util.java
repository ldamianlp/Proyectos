package ec.gob.utils;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;

import ec.gob.dao.UtilsDOA;
import ec.gob.enumeracion.Enums;
import ec.gob.object.Resultado;
import ec.gob.object.ResultadoSorteo;
import ec.gob.object.SpFinaExpositor;


public class Util {

	private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(Util.class);

	public String getUser(HttpServletRequest tmpRequest) {
		return tmpRequest.getUserPrincipal().getName();
		//		
		//		String[] lv_values=null; 
		//		String authorization = tmpRequest.getHeader("Authorization");
		//		 if (authorization != null && authorization.toLowerCase().startsWith("basic")) {			     
		//		     String base64Credentials = authorization.substring("Basic".length()).trim();
		//		     byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
		//		     String credentials = new String(credDecoded, StandardCharsets.UTF_8);
		//		     lv_values = credentials.split(":");			
		//		 }
		//		return lv_values[0];
	}

	public Resultado validarFormato(SpFinaExpositor objExpo) {
		String lv_aux = "";
		UtilsDOA obj = new UtilsDOA();
		/*VALIDACION IDENTIFICACION*/
		//		SpFinaExpositor objExpo = objExpo;
		if(lv_aux.isEmpty() && !(objExpo.getTipoId().equals("CED") || objExpo.getTipoId().equals("RUC") || objExpo.getTipoId().equals("PAS"))){
			lv_aux = "TIPO DE DOCUMENTO INVALIDO";	
		}
		if((lv_aux.isEmpty() && objExpo.getTipoId().equals("RUC")) ||objExpo.getIdRepLegal() != null ) {			
			if (objExpo.getIdRepLegal()==null) {
				lv_aux = "INGRESE EL ID DEL REPRESENTANTE LEGAL";
			}
			//			else {
			//				CEDULA
			//				if (lv_aux.isEmpty() || (objExpo.getIdRepLegal()!=null && !objExpo.getIdRepLegal().equals("") && !objExpo.getIdRepLegal().matches("^[0-9]{2,10}$") && objExpo.getIdRepLegal().length()!=10)) 
			//					lv_aux = "DIGITOS Y FORMATO IDENTIFICACION CED DEL REPRESENTANTE LEGAL";
			//				if (lv_aux.isEmpty() && (Objects.nonNull(objExpo.getRepLegal()) && !objExpo.getRepLegal().isEmpty() &&
			//						objExpo.getRepLegal().trim().length() > 1 && 
			//						//						!objExpo.getNombre().matches("[a-zA-zñÑáéíóúÁÉÍÓÚ]+([ '-][a-zA-ZñÑáéíóúÁÉÍÓÚ]+)*"))
			//						!objExpo.getRepLegal().matches("^[a-zA-Z0-9ÁÉÍÓÚÜÑáéíóúüñ.,\\-&'() ]+$")
			//						)||objExpo.getNombre().length()<3
			//						)
			//					lv_aux = "NOMBRE CON CARACTERES ESPECIALES REPRESENTANTE LEGAL";
			//			}
			if(lv_aux.isEmpty() &&  objExpo.getIdRepLegal().length()==10) {//cedula
				String resultado = obj.validarCedula(objExpo.getIdRepLegal());
				if(!resultado.equals("1")) {
					lv_aux = "NUMERO DE CEDULA DEL REPRESENTANTE LEGAL INVALIDO";	
				}
			}
		}

		if (lv_aux.isEmpty() && Objects.nonNull(objExpo.getId()) && !objExpo.getId().isEmpty() &&
				objExpo.getTipoId().equals(Enums.TipIdentificacion.CEDULA.getValue())
				) {
			if (!objExpo.getId().matches("^[0-9]{2,10}$") || objExpo.getId().length()!=10) 
				lv_aux = "FORMATO INCORRECTO EN LOS DIGITOS IDENTIFICACION CED";

			if(lv_aux.isEmpty() && objExpo.getTipoId().equals("CED") && objExpo.getId().length()==10) {//cedula
				String resultado = obj.validarCedula(objExpo.getId());
				if(!resultado.equals("1")) {
					lv_aux = "NUMERO DE CEDULA INVALIDO";	
				}
			}
		} else {
			if (objExpo.getTipoId().equals(Enums.TipIdentificacion.RUC.getValue())) {				
				if (!objExpo.getId().matches("^[0-9]{2,13}$")|| objExpo.getId().length()!=13) 
					lv_aux = "FORMATO INCORRECTO EN LOS DIGITOS DIGITOS IDENTIFICACION RUC";			
			}
		}
		/*VALIDACION DEL CORREO*/
		if (lv_aux.isEmpty() && Objects.nonNull(objExpo.getCorreo()) && !objExpo.getCorreo().isEmpty() &&
				//!objExpo.getCorreo().matches("^[\\w-]+(\\.[\\w-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"))
				!objExpo.getCorreo().matches("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$"))
			lv_aux = "CORREO NO CUMPLE FORMATO";

		/*VALIDACION DEL TELEFONO*/ //"^[1-9][0-9]*$"	
		if (lv_aux.isEmpty() && Objects.nonNull(objExpo.getTelefono()) && !objExpo.getTelefono().isEmpty() &&
				!objExpo.getTelefono().matches("^[0-9]{2,10}"))
			lv_aux = "NO CUMPLE CON LOS DIGITOS DEL TELEFONO";

		/*VALIDACION DEL NOMBRE*/
		if (lv_aux.isEmpty() && (Objects.nonNull(objExpo.getNombre()) && !objExpo.getNombre().isEmpty() &&
				objExpo.getNombre().trim().length() > 1 && 
				//				!objExpo.getNombre().matches("[a-zA-zñÑáéíóúÁÉÍÓÚ]+([ '-][a-zA-ZñÑáéíóúÁÉÍÓÚ]+)*"))
				!objExpo.getNombre().matches("^[a-zA-Z0-9ÁÉÍÓÚÜÑáéíóúüñ.,\\-&'() ]+$")
				)||objExpo.getNombre().length()<3
				)
			lv_aux = "INCONSISTENCIA CON EL NOMBRE";
		if(lv_aux.isEmpty() && !(objExpo.getArtesano().equals("S") || objExpo.getArtesano().equals("N") )){
			lv_aux = "VALOR DE ARTESANO NO DEFINIDO";	
		}
		if(lv_aux.isEmpty() && !(objExpo.getSorteo().equals("S") || objExpo.getSorteo().equals("N") )){
			lv_aux = "VALOR DE SORTEO NO DEFINIDO";	
		}
		if(lv_aux.isEmpty() && !(objExpo.getEstado().equals("A") || objExpo.getEstado().equals("I") )){
			lv_aux = "VALOR DE ESTADO NO DEFINIDO";	
		}



		Resultado resultado = new Resultado();
		if (!lv_aux.isEmpty()) {
//			resultado.setCodError(Enums.Errores.e90.getCodError());
			resultado.setSuccess(false);
			resultado.setError("Formato de datos erroneos - " + lv_aux);
			resultado.setMensaje("Formato de datos erroneos - " + lv_aux);
		} else {		
//			resultado.setCodError(Enums.Errores.e00.getCodError());
			resultado.setSuccess(true);
			resultado.setMensaje(Enums.Errores.e00.getMensaje());
		}

		return resultado;
	}

	public ResultadoSorteo sorteoConsecutivos(String libres,String codlibres,long pCantidadPuestos) {
		ResultadoSorteo resultado = null;//new ResultadoSorteo();
		String[] alPuestos = libres.split(",");
		List<String> lstLibres = Arrays.asList(alPuestos);
		String[] alCodPuestos = codlibres.split(",");
		List<String> lstCodLibres = Arrays.asList(alCodPuestos);

		ArrayList<HashMap<String, Object>> alHash = new ArrayList<HashMap<String,Object>>();


		Random rand = new Random();
		int pos = lstLibres.size();
		int randomitem = rand.nextInt(pos);//lstLibres.size());
		String randomElement = lstLibres.get(randomitem);
		Integer vNumRandom = Integer.valueOf(randomElement);//new Integer(randomElement);
//		System.out.println("NUMERO SORTEADO: "+vNumRandom);
		String finS = lstLibres.get(lstLibres.size()-1);
		int fin = Integer.valueOf(finS);//new Integer(finS);

		String sorteados="" ;
//		System.out.println("CANTIDAD PUESTOS: "+pCantidadPuestos);
		if(pCantidadPuestos>=1 && pCantidadPuestos<=4) {
//			System.out.println("ENTRO rango");
			//verifico si existe los puestos siguientes al consecutivo			
			long max = vNumRandom+pCantidadPuestos-1;
			if(max<=fin) {
				int posicion = randomitem;
//				System.out.println("*****POSICION A OBTENER:"+posicion);
//				System.out.println("*****cantidad puestos libres :"+lstCodLibres.size());
//				System.out.println("*****puestos libres :"+lstCodLibres);
				for(int i=vNumRandom ;i<=max;i++) {
					if(lstCodLibres.size()>posicion) {
					if(i<max)
						sorteados = sorteados + String.valueOf(i)+",";
					else
						sorteados = sorteados + String.valueOf(i);

					//					if(posicion<lstCodLibres.size()) {
					HashMap<String, Object> hash = new HashMap<String, Object>();
					//						System.out.println("*****POSICION :"+posicion);
//					try {
//						String codigoPuesto = lstCodLibres.get(posicion);
						hash.put("COD_PUESTO", new BigDecimal(lstCodLibres.get(posicion)));
						hash.put("NUM_PUESTO", i);

						alHash.add(hash);
//					}catch(Exception e) {
//						sorteoConsecutivos(libres,codlibres, pCantidadPuestos);
//					}
										}
					
					posicion++;
				}
//				System.out.println("SORTEADOS: "+sorteados);
				if(libres.contains(sorteados) && alHash.size()==pCantidadPuestos) {
					Gson gson =  new Gson();
					resultado = new ResultadoSorteo();
					resultado.setSorteados(gson.toJson(alHash));
				}else {
					resultado = sorteoConsecutivos(libres,codlibres, pCantidadPuestos);
				}
			}else
			{
				resultado = sorteoConsecutivos(libres,codlibres, pCantidadPuestos);
			}
		}
		return resultado;
	}
	public ResultadoSorteo sorteoRandom(String libres,String codlibres,long pCantidadPuestos) {
		ResultadoSorteo resultado = null;//new ResultadoSorteo();
		String[] alPuestos = libres.split(",");
		List<String> lstLibres = Arrays.asList(alPuestos);
		String[] alCodPuestos = codlibres.split(",");
		List<String> lstCodLibres = Arrays.asList(alCodPuestos);

		ArrayList<HashMap<String, Object>> alHash = new ArrayList<HashMap<String,Object>>();

		if(pCantidadPuestos>=1 && pCantidadPuestos<=4) {

			while (alHash.size()!=pCantidadPuestos) {				
				Random rand = new Random();
				int randomitem = rand.nextInt(lstLibres.size());

				int cont = 0;
				for(HashMap<String, Object> hash:alHash) {
					BigDecimal a = new BigDecimal(lstCodLibres.get(randomitem));
					BigDecimal b = (BigDecimal)hash.get("COD_PUESTO");
					if(a.equals(b)) {
						cont++;
						break;
					}
				}

				if(cont==0) {
					HashMap<String, Object> hash = new HashMap<String, Object>();
					hash.put("COD_PUESTO", new BigDecimal(lstCodLibres.get(randomitem)));
					hash.put("NUM_PUESTO", new BigDecimal(lstLibres.get(randomitem)));					
					alHash.add(hash);
				}
			}
			Gson gson =  new Gson();
			resultado = new ResultadoSorteo();
			resultado.setSorteados(gson.toJson(alHash));
		}

		return resultado;
	}
	/*public SpFinaExpositor saveExpositor(EntityManager em, SpFinaExpositor obj) {
		em.getTransaction().begin();
		em.persist(obj);
		em.getTransaction().commit();		
		return obj;
	}
	public void updateExpositor(EntityManager em, SpFinaExpositor obj) {
		em.getTransaction().begin();
		em.merge(obj);
		em.getTransaction().commit();
	}*/
}
