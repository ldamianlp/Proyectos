package ec.gob.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import ec.gob.object.ObjSpFinaNegReq;
import ec.gob.object.ObjSpFinaRequisito;
//import ec.gob.object.ObjSpFinaRequisito;
import ec.gob.object.SpFinaCatNegocio;
import ec.gob.object.objRequisitosCategoria;
import ec.gob.persist.JPAUtil;

public class SpFinaCatNegocioDAO {
	EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
	private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(SpFinaCatNegocioDAO.class);

	
	
	public List<SpFinaCatNegocio> getCategoriasByEve(long inCodigo) {
		List<SpFinaCatNegocio> listaCate = new ArrayList<>();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			 CriteriaQuery<SpFinaCatNegocio> cq = cb.createQuery(SpFinaCatNegocio.class);
			 Root<SpFinaCatNegocio> root = cq.from(SpFinaCatNegocio.class);			 
			 try 
			 {
				 cq.select(root);
				 Predicate[] predicates = new Predicate[2];	//numero de condiciones
				 predicates[0] = cb.equal(root.get("codEvento"),inCodigo);
				 predicates[1] = cb.equal(root.get("estado"),"A");

//				 cq.where(cb.equal(rootParCantones.get("proCodigo"),inProCodigo));
				 cq.where(predicates);
				 listaCate = em.createQuery(cq).getResultList();
			 }catch (NoResultException e){
				 e.printStackTrace();
				 LOGGER.error("ERROR: getEventosActivos: "+ e.getMessage());
			 }
			 return listaCate;
		}
	
	@SuppressWarnings("unchecked")
	public List<ObjSpFinaRequisito> getRequisitosByCat(long inCodigo) {		
	       Query query = em.createQuery("SELECT DISTINCT e FROM ObjSpFinaRequisito e INNER JOIN e.spFinaNegReqs t where e.codigo = t.cod_requisito and t.cod_negocio="+inCodigo+"");// where t.codigo=1
	       List<ObjSpFinaRequisito> resultList = query.getResultList();
	       for(ObjSpFinaRequisito obj : resultList){
	    	   System.out.println("CODIGO: "+obj.getCodigo());
	    	   System.out.println("DESCRIPCION: "+obj.getDescripcion());
	    	   
	    	   for(ObjSpFinaNegReq obj1 : obj.getSpFinaNegReqs()) {
	    		   System.out.println("Obligatorio: "+obj1.getObligatorio());
	    	   }
	    		   
	    	  
	       }
	       return resultList;
	}
	@SuppressWarnings("unchecked")
	public ArrayList<HashMap<String,Object>> getRequisitosByCategoria(long inCodigo) {	
	       Query query = em.createNativeQuery("select RCA.COD_NEGOCIO,RCA.COD_REQUISITO,REQ.DESCRIPCION,RCA.OBLIGATORIO from gadmapps.sp_fina_neg_req RCA INNER JOIN GADMAPPS.sp_fina_requisito REQ ON (req.codigo = RCA.COD_REQUISITO) WHERE RCA.cod_negocio = "+inCodigo);// where t.codigo=1
	      
	       List<ArrayList<Object>> resultList = query.getResultList();
	       
//	       ArrayList<objRequisitosCategoria> alReq = new ArrayList<objRequisitosCategoria>();
	       ArrayList<HashMap<String,Object>> outAl = new ArrayList<HashMap<String,Object>>();
	       for(Object obj : resultList){	       
		       Object[] objects = (Object[]) obj;
//	    	   System.out.println("COD_NEGOCIO: "+objects[0].toString());
//	    	   System.out.println("COD_REQUISITO: "+objects[1].toString());
//	    	   System.out.println("OBLIGATORIO: "+objects[2].toString());		       
		       
	    	   HashMap<String,Object> tmpObj = new HashMap<String,Object>();
	    	   tmpObj.put("COD_NEGOCIO", objects[0]);
	    	   tmpObj.put("COD_REQUISITO", objects[1]);
	    	   tmpObj.put("DESCRIPCION", objects[2]);
	    	   tmpObj.put("OBLIGATORIO", objects[3]);
	    	   outAl.add(tmpObj);
	    	   /*objRequisitosCategoria objReq = new objRequisitosCategoria();
	    	   objReq.setCod_negocio((long)objects[0]);
	    	   objReq.setCod_requisito((long)objects[1]);
	    	   objReq.setObligatorio((String)objects[2]);
	    	   alReq.add(objReq)*/
	       }
	       return outAl;
	}
	
}
