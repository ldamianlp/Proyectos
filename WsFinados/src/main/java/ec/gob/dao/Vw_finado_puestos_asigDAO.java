package ec.gob.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
//import java.util.function.Predicate;

import javax.persistence.EntityManager;
import javax.persistence.Query;
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import ec.gob.object.*;
import ec.gob.persist.JPAUtil;

public class Vw_finado_puestos_asigDAO {
	EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
	private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(Vw_finado_puestos_asigDAO.class);

	/*public VwFinadoPuestosAsig find(String id) {
		Vw_finado_puestos_asig obj = new Vw_finado_puestos_asig();		
		obj = em.find(Vw_finado_puestos_asig.class, id);
		return obj;
	}
	public Vw_finado_puestos_asig save(Vw_finado_puestos_asig obj) {
		em.getTransaction().begin();
		em.persist(obj);
		em.getTransaction().commit();		
		return obj;
	}
	public void update(Vw_finado_puestos_asig obj) {
		em.getTransaction().begin();
		em.merge(obj);
		em.getTransaction().commit();
	}*/
	
	@SuppressWarnings("rawtypes")
	public List<VwFinadoPuestosAsig> getSolicitudes1(
			String inIdExpositor
	) {
		List<VwFinadoPuestosAsig> listaSolicitud = new ArrayList<>();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			 CriteriaQuery<VwFinadoPuestosAsig> cq = cb.createQuery(VwFinadoPuestosAsig.class);
			 Root<VwFinadoPuestosAsig> rootParCantones = cq.from(VwFinadoPuestosAsig.class);			 
			 
			 cq.select(rootParCantones);
			 Predicate[] predicates = new Predicate[1];	
			 predicates[0] = cb.equal(rootParCantones.get("idExpositor"),inIdExpositor);
			 cq.where(predicates);
			 listaSolicitud = em.createQuery(cq).getResultList();
			 
			 return listaSolicitud;
		}
	
	
	@SuppressWarnings("unchecked")
	public ArrayList<HashMap<String,Object>> getSolicitudes(
			String inIdExpositor
			) {	
	       Query query = em.createNativeQuery("select nombre,\r\n"
	       		+ "         (select  nombre from GADMAPPS.sp_fina_cat_negocio where codigo = cod_cat_nego) nomNego,\r\n"
	       		+ "          bloque nomBloq,\r\n"
	       		+ "          puestos numPuesto,\r\n"
	       		+ "          est_factura estFactura,\r\n"
	       		+ "          total valFac,\r\n"
	       		+ "          est_solicitud,\r\n"
	       		+ "          cod_solicitud\r\n"
	       		+ "          ,(select observacion from gadmapps.sp_fina_solicitud where codigo = cod_solicitud) rechazo "	
	       		+ "			 from GADMAPPS.VW_FINA_EXPO_PUESTO_FAC WHERE EXPOSITOR = '"+inIdExpositor+"'");// where t.codigo=1
	      
	       List<ArrayList<Object>> resultList = query.getResultList();
	       
//	       ArrayList<objRequisitosCategoria> alReq = new ArrayList<objRequisitosCategoria>();
	       ArrayList<HashMap<String,Object>> outAl = new ArrayList<HashMap<String,Object>>();
	       for(Object obj : resultList){	       
		       Object[] objects = (Object[]) obj;
	    	   HashMap<String,Object> tmpObj = new HashMap<String,Object>();
	    	   tmpObj.put("nombre", objects[0]);
	    	   tmpObj.put("nomNego", objects[1]);
	    	   tmpObj.put("nomBloq", objects[2]);
	    	   tmpObj.put("numPuesto", objects[3]);
	    	   tmpObj.put("estFactura", objects[4]);	    	   
	    	   tmpObj.put("valFac", objects[5]);
	    	   tmpObj.put("estSolicitud", objects[6]);
	    	   tmpObj.put("cod_solicitud", objects[7]);
	    	   tmpObj.put("rechazo", objects[8]);
//	    	   tmpObj.put("SOLIC", objects[0]);
//	    	   tmpObj.put("COD_EVENTO", objects[1]);
//	    	   tmpObj.put("NOM_EVENTO", objects[2]);
//	    	   tmpObj.put("COD_CAT_NEGO", objects[3]);	    	   
//	    	   tmpObj.put("nomNego", objects[4]);
//	    	   tmpObj.put("COD_BLOQ", objects[5]);
//	    	   tmpObj.put("nomBloq", objects[6]);
//	    	   tmpObj.put("CAN_PUESTO", objects[7]);
//	    	   tmpObj.put("EST_SOLICITUD", objects[8]);
//	    	   tmpObj.put("ID_EXPOSITOR", objects[9]);
//	    	   tmpObj.put("nombre", objects[10]);
//	    	   tmpObj.put("COD_PUESTO", objects[11]);
//	    	   tmpObj.put("numPuesto", objects[12]);
//	    	   tmpObj.put("EST_PUESTO", objects[13]);
//	    	   tmpObj.put("FACTURA", objects[14]);
//	    	   tmpObj.put("estFactura", objects[15]);
//	    	   tmpObj.put("valFac", objects[16]);
	    	   outAl.add(tmpObj);
	    	   
	       }
	       return outAl;
	}
}
