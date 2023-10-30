package ec.gob.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

import ec.gob.persist.JPAUtil;



public class UtilsDOA {
	EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
	//private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(tmp_finadosDOA.class);

	/*public void update(objTmp_finados objTmp_finados) {
		em.getTransaction().begin();
		em.merge(objTmp_finados);
		em.getTransaction().commit();
	}
	
	public objTmp_finados find(BigDecimal id) {
		objTmp_finados objTmp_finados = new objTmp_finados();		
		objTmp_finados = em.find(objTmp_finados.class, id);
		return objTmp_finados;
	}
	
	public void delete(BigDecimal id) {
		objTmp_finados objTmp_finados = new objTmp_finados();	
		objTmp_finados = em.find(objTmp_finados.class, id);
		em.getTransaction().begin();
		em.remove(objTmp_finados);
		em.getTransaction().commit();
	}*/
	public String validarCedula(String inCedula) {
		
		BigDecimal valido = (BigDecimal) em.createNativeQuery("SELECT cabildo.VALCEDULA@muni(:NUMERO) FROM DUAL")
			    .setParameter("NUMERO", inCedula)
			    .getSingleResult();
		return valido.toString();
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<HashMap<String,Object>> getPuestosLibres(
		BigDecimal inCod_Evento,
		BigDecimal inCod_cat_nego
	) {	
	       Query query = em.createNativeQuery("SELECT COD_BLOQ,BLOQUE,ORD_ASIGNACION,COD_PUESTO,PUESTO_DISP \r\n"
	       		+ "FROM GADMAPPS.VW_FINADO_ASIGNA WHERE COD_EVENTO = "+inCod_Evento+" AND COD_CAT_NEGO = "+inCod_cat_nego+"\r\n"
	       		+ " AND PUESTOS_DISP >0 "
	       		+ "ORDER BY ORD_ASIGNACION");// where t.codigo=1
	      
	       List<ArrayList<Object>> resultList = query.getResultList();	       
	       ArrayList<HashMap<String,Object>> outAl = new ArrayList<HashMap<String,Object>>();
	       for(Object obj : resultList){	       
		       Object[] objects = (Object[]) obj;		       
	    	   HashMap<String,Object> tmpObj = new HashMap<String,Object>();
	    	   tmpObj.put("COD_BLOQ", objects[0]);
	    	   tmpObj.put("BLOQUE", objects[1]);
	    	   tmpObj.put("ORD_ASIGNACION", objects[2]);
	    	   tmpObj.put("COD_PUESTO", objects[3]);
	    	   tmpObj.put("PUESTO_DISP", objects[4]);
	    	   outAl.add(tmpObj);
	       }
	       return outAl;
	}
	@SuppressWarnings("unchecked")
	public ArrayList<HashMap<String,Object>> getPuestosParametrizados(
		BigDecimal inCod_Evento,
		BigDecimal inCod_cat_nego,
		BigDecimal inCod_bloq
	) {	
		       Query query = em.createNativeQuery("SELECT CODIGO,NUM_PUESTO FROM GADMAPPS.SP_FINA_PUESTOS \r\n"
		       		+ "WHERE COD_EVENTO = "+inCod_Evento+" AND COD_CAT_NEGO = "+inCod_cat_nego+" AND COD_BLOQ = "+inCod_bloq+" \r\n"
		       		+ "AND ESTADO IN ('O','P')"
		       		+ "ORDER BY NUM_PUESTO");
		      
		       List<ArrayList<Object>> resultList = query.getResultList();	       
		       ArrayList<HashMap<String,Object>> outAl = new ArrayList<HashMap<String,Object>>();
		       for(Object obj : resultList){	       
			       Object[] objects = (Object[]) obj;		       
		    	   HashMap<String,Object> tmpObj = new HashMap<String,Object>();
		    	   tmpObj.put("COD_PUESTO", objects[0]);
		    	   tmpObj.put("NUM_PUESTO", objects[1]);
		    	   outAl.add(tmpObj);
		       }
		       return outAl;
		}
	@SuppressWarnings("unchecked")
	public ArrayList<HashMap<String,Object>> getPuestosOcupados1(
		BigDecimal inCod_Evento,
		BigDecimal inCod_cat_nego,
		BigDecimal inCod_bloq
	) {	
//		       Query query = em.createNativeQuery("SELECT CODIGO,NUM_PUESTO FROM GADMAPPS.SP_FINA_PUESTOS \r\n"
//		       		+ "WHERE COD_EVENTO = "+inCod_Evento+" AND COD_CAT_NEGO = "+inCod_cat_nego+" AND COD_BLOQ = "+inCod_bloq+" \r\n"
//		       		+ "AND ESTADO IN ('O')"
//		       		+ "ORDER BY NUM_PUESTO");
		       Query query = em.createNativeQuery("SELECT COD_PUESTO_OCUP,PUESTO_OCUP FROM GADMAPPS.VW_FINADO_ASIGNA \r\n"
			       		+ "WHERE COD_EVENTO = "+inCod_Evento+" AND COD_CAT_NEGO = "+inCod_cat_nego+" AND COD_BLOQ = "+inCod_bloq+" \r\n"
			       		+ "AND ESTADO IN ('O')"
			       		+ "ORDER BY NUM_PUESTO");
		      
		       List<ArrayList<Object>> resultList = query.getResultList();	       
		       ArrayList<HashMap<String,Object>> outAl = new ArrayList<HashMap<String,Object>>();
		       for(Object obj : resultList){	       
			       Object[] objects = (Object[]) obj;		       
		    	   HashMap<String,Object> tmpObj = new HashMap<String,Object>();
//		    	   tmpObj.put("CODIGO", objects[0]);
//		    	   tmpObj.put("NUM_PUESTO", objects[1]);
		    	   tmpObj.put("COD_PUESTO_OCUP", objects[0]);
		    	   tmpObj.put("PUESTO_OCUP", objects[1]);
		    	   outAl.add(tmpObj);
		       }
		       return outAl;
		}
	@SuppressWarnings("unchecked")
	public ArrayList<HashMap<String,Object>> getPuestosOcupados(
		BigDecimal inCod_Evento,
		BigDecimal inCod_cat_nego,
		BigDecimal inCod_bloq
	) {	
//		       Query query = em.createNativeQuery("SELECT CODIGO,NUM_PUESTO FROM GADMAPPS.SP_FINA_PUESTOS \r\n"
//		       		+ "WHERE COD_EVENTO = "+inCod_Evento+" AND COD_CAT_NEGO = "+inCod_cat_nego+" AND COD_BLOQ = "+inCod_bloq+" \r\n"
//		       		+ "AND ESTADO IN ('O')"
//		       		+ "ORDER BY NUM_PUESTO");
		       Query query = em.createNativeQuery("SELECT CODIGO,NUM_PUESTO FROM GADMAPPS.SP_FINA_PUESTOS \r\n"
			       		+ "WHERE COD_EVENTO = "+inCod_Evento+" AND COD_CAT_NEGO = "+inCod_cat_nego+" AND COD_BLOQ = "+inCod_bloq+" \r\n"
			       		+ "AND ESTADO IN ('O')"
			       		+ "ORDER BY NUM_PUESTO");
		      
		       List<ArrayList<Object>> resultList = query.getResultList();	       
		       ArrayList<HashMap<String,Object>> outAl = new ArrayList<HashMap<String,Object>>();
		       for(Object obj : resultList){	       
			       Object[] objects = (Object[]) obj;		       
		    	   HashMap<String,Object> tmpObj = new HashMap<String,Object>();
		    	   tmpObj.put("CODIGO", objects[0]);
		    	   tmpObj.put("NUM_PUESTO", objects[1]);
//		    	   tmpObj.put("COD_PUESTO_OCUP", objects[0]);
//		    	   tmpObj.put("PUESTO_OCUP", objects[1]);
		    	   outAl.add(tmpObj);
		       }
		       return outAl;
		}
	
	/*
	 SELECT LISTAGG(NUM_PUESTO, ',') WITHIN GROUP (ORDER BY CODIGO) AS PUESTOS
	INTO LV_CADENA
		FROM GADMAPPS.SP_FINA_PUESTOS
		WHERE COD_EVENTO = IN_EVENTO
		AND COD_CAT_NEGO = IN_CAT_NEGO
		AND COD_BLOQ = IN_BLOQ
		AND ESTADO = IN_ESTADO; 
	 * */
	public String getDatoscedula1(
			String inCedula 
	) {	
       Query query = em.createNativeQuery("SELECT gadmapps.PKG_WS_DINARDAP.FN_GET_REGISTRO_CIVIL(:CEDULA) FROM DUAL")           
               .setParameter("CEDULA", inCedula);			      
       Object resultList = query.getSingleResult();
       return resultList.toString();
	}
	public String getDatoscedula(
			String inCedula 
	) {	
       Query query = em.createNativeQuery("SELECT gadmapps.PKG_WS_DINARDAP.FN_GET_REGISTRO_CIVIL(:CEDULA) FROM DUAL")           
               .setParameter("CEDULA", inCedula);			      
       Object resultList = query.getSingleResult();       
       
       return resultList.toString();
	}
	public String getDatosRUC(
			String inRUC
	) {	
       Query query = em.createNativeQuery("SELECT gadmapps.PKG_WS_DINARDAP.FN_GET_SRI(:RUC) FROM DUAL")           
               .setParameter("RUC", inRUC);			      
       Object resultList = query.getSingleResult();
       return resultList.toString();
	}
	
	public int getCantidadPuestosReservados(
			BigDecimal inCodEvento,
			String inNumDocumento
	) {	
       Query query = em.createNativeQuery("SELECT COUNT(*) FROM GADMAPPS.VW_FINADO_PUESTOS_ASIG WHERE COD_EVENTO = (:COD_EVENTO) AND ID_EXPOSITOR = (:NUM_DOCUMENTO)")           
               .setParameter("COD_EVENTO", inCodEvento)
               .setParameter("NUM_DOCUMENTO", inNumDocumento);			      
       Object resultList = query.getSingleResult();       
       
       return Integer.valueOf(resultList.toString());
	}
//	public int getExisteReservaPuesto(
//			BigDecimal inCodEvento,
//			BigDecimal inCodCategoria,
//			BigDecimal inCodBloque,
//			BigDecimal inCodPuesto
//	) {	
//       Query query = em.createNativeQuery("SELECT COUNT(*) FROM GADMAPPS.VW_FINADO_PUESTOS_ASIG WHERE COD_EVENTO = (:COD_EVENTO) AND COD_CAT_NEGO = (:COD_CATEGORIA) "
//       		+ "AND COD_BLOQ = (:COD_BLOQUE) AND COD_PUESTO = (:COD_PUESTO)")           
//               .setParameter("COD_EVENTO", inCodEvento)
//               .setParameter("COD_CATEGORIA", inCodCategoria)
//	       .setParameter("COD_BLOQUE", inCodBloque)
//	       .setParameter("COD_PUESTO", inCodPuesto);
//       Object resultList = query.getSingleResult();       
//       
//       return Integer.valueOf(resultList.toString());
//	}
	@SuppressWarnings("unchecked")
	public ArrayList<HashMap<String,Object>> getDetalleFactura(
			BigDecimal inCod_Solicitud
		) {	
//			       Query query = em.createNativeQuery("select DISTINCT\r\n"
//			    		+"PAS.SOLIC,\r\n"
//			       		+ "FDE.COD_RUBRO,\r\n"
//			       		+ "RUB.NOMBRE,\r\n"
//			       		+ "FDE.VALOR,\r\n"
//			       		+ "PAS.VAL_FAC\r\n"
//			       		+ "FROM GADMAPPS.VW_FINADO_PUESTOS_ASIG PAS\r\n"
//			       		+ "INNER JOIN GADMAPPS.SP_FINA_FAC_DET FDE ON (FDE.COD_FAC = PAS.FACTURA)\r\n"
//			       		+ "INNER JOIN GADMAPPS.sp_fina_rubro RUB ON (RUB.CODIGO = FDE.COD_RUBRO)\r\n"
//			       		+ "WHERE SOLIC = "+inCod_Solicitud+"\r\n"
//			       		+ "ORDER BY COD_RUBRO");
		 		Query query = em.createNativeQuery("select COD_SOLICITUD,FDE.COD_RUBRO,RUB.NOMBRE,FDE.VALOR,TOTAL\r\n"
		 				+ "from GADMAPPS.VW_FINA_EXPO_PUESTO_FAC VPF\r\n"
		 				+ "INNER JOIN GADMAPPS.sp_fina_fac_det FDE ON (FDE.COD_FAC = VPF.COD_FACTURA)\r\n"
		 				+ "INNER JOIN GADMAPPS.sp_fina_rubro RUB ON (RUB.CODIGO = FDE.COD_RUBRO)\r\n"
		 				+ "WHERE COD_SOLICITUD = "+inCod_Solicitud+" ORDER BY COD_RUBRO");
			      
			       List<ArrayList<Object>> resultList = query.getResultList();	       
			       ArrayList<HashMap<String,Object>> outAl = new ArrayList<HashMap<String,Object>>();
			       for(Object obj : resultList){	       
				       Object[] objects = (Object[]) obj;		       
			    	   HashMap<String,Object> tmpObj = new HashMap<String,Object>();
			    	   tmpObj.put("COD_SOLI", objects[0]);
			    	   tmpObj.put("COD_RUBRO", objects[1]);
			    	   tmpObj.put("NOMBRE", objects[2]);
			    	   tmpObj.put("VALOR", objects[3]);
			    	   tmpObj.put("VALOR_TOTAL", objects[4]);
			    	   outAl.add(tmpObj);
			       }
			       return outAl;
			}
	
	public void sendEmailAprobado1(BigDecimal inCodSolicitud) {
		StoredProcedureQuery query = em.createNamedStoredProcedureQuery("SP_SEND_MAIL_APRO");
		query.setParameter("IN_SOL", inCodSolicitud);
		query.execute();
	}
	public String sendEmailAprobado(BigDecimal inCodSolicitud) {
       Query query = em.createNativeQuery("SELECT GADMAPPS.PKG_FINADOS.FN_SEND_MAIL_APRO(:COD_SOLICITUD) FROM DUAL")           
               .setParameter("COD_SOLICITUD", inCodSolicitud);			      
       Object resultList = query.getSingleResult();
       return resultList.toString();
	}
}
