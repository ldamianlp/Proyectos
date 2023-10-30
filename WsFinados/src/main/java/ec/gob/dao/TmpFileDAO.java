package ec.gob.dao;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.sql.rowset.serial.SerialBlob;

import org.hibernate.engine.jdbc.SerializableBlobProxy;

import ec.gob.object.ParCantones;
import ec.gob.object.TmpFile;
import ec.gob.object.TmpFilePK;
import ec.gob.object.objTmpFile;
import ec.gob.persist.JPAUtil;

public class TmpFileDAO {
	EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
	private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(TmpFileDAO.class);

	public TmpFile find(TmpFilePK objPk) {
		TmpFile obj = new TmpFile();		
		obj = em.find(TmpFile.class, objPk);
		return obj;
	}
	public TmpFile save(TmpFile obj) {
		em.getTransaction().begin();
		em.persist(obj);
		em.getTransaction().commit();
		em.close();
		return obj;
	}
	public void update(TmpFile obj) {
		em.getTransaction().begin();
		em.merge(obj);
		em.getTransaction().commit();
		em.close();
	}
	public int delete(String inSessionID) {
		/*Session session = (Session) em.getDelegate();
		session=session.getSessionFactory().openSession();
		session.getTransaction().begin();
		Query query=session.createNativeQuery("Delete from gadmapps.TMP_FILE where ID_SESSION=?");
		query.setParameter(1, inSessionID);
		query.executeUpdate();
		session.getTransaction().commit();*/
		int retorno;
		
		em.getTransaction().begin();
		Query query = em.createNativeQuery("Delete from gadmapps.TMP_FILE where ID_SESSION=?");
	    query.setParameter(1, inSessionID);
	    retorno = query.executeUpdate();
	    em.getTransaction().commit();
	    return retorno;
	}
	
	public Boolean deletePK(String inSessionID) {
		Boolean retorno = false;
//		List<SpFinaCatNegocio> listaCate = new ArrayList<>();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			 CriteriaDelete<TmpFile> cq = cb.createCriteriaDelete(TmpFile.class);
			 Root<TmpFile> root = cq.from(TmpFile.class);			 
			 try 
			 {
//				 cq.select(root);
				 Predicate[] predicates = new Predicate[1];	//numero de condiciones
				 predicates[0] = cb.equal(root.get("id").get("idSession"),inSessionID);
				 

//				 cq.where(cb.equal(rootParCantones.get("proCodigo"),inProCodigo));
				 cq.where(predicates);
				 em.createQuery(cq).executeUpdate();
				 retorno = true;
			 }catch (NoResultException e){
				 e.printStackTrace();
				 LOGGER.error("ERROR: getEventosActivos: "+ e.getMessage());
				 retorno = false;
			 }
			 return retorno;
		}
	
	public List<TmpFile> getFilesTmpBySession(String inSessionId) {
		List<TmpFile> listaCantones = new ArrayList<>();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			 CriteriaQuery<TmpFile> cq = cb.createQuery(TmpFile.class);
			 Root<TmpFile> rootParCantones = cq.from(TmpFile.class);
			 Path<TmpFilePK> a = rootParCantones.get("id");
			 try 
			 {
				 cq.select(rootParCantones);
				 Predicate[] predicates = new Predicate[1];	//numero de condiciones
//				 predicates[0] = cb.or(cb.equal(rootParCantones.get("proCodigo"),inProCodigo),cb.equal(rootParCantones.get("proCodigo"),17));
				 predicates[0] = cb.equal(a.get("idSession"),inSessionId);

//				 cq.where(cb.equal(rootParCantones.get("proCodigo"),inProCodigo));
				 cq.where(predicates);
				 listaCantones = em.createQuery(cq).getResultList();
			 }catch (NoResultException e){
				 e.printStackTrace();
				 LOGGER.error("ERROR: getCantonesByProv: "+ e.getMessage());
			 }
			 return listaCantones;
		}	
//	@SuppressWarnings("unchecked")
//	public ArrayList<HashMap<String,Object>> getFilesTmpBySession1(
//			String inSessionId
//		) {	
//			       Query query = em.createNativeQuery("SELECT ID_REQUISITO,NOMBRE,ARCHIVO FROM GADMAPPS.TMP_FILE \r\n"
//			       		+ "WHERE ID_SESSION = '"+inSessionId +"' ORDER BY ID_REQUISITO");
//			      
//			       List<ArrayList<Object>> resultList = query.getResultList();	       
//			       ArrayList<HashMap<String,Object>> outAl = new ArrayList<HashMap<String,Object>>();
//			       for(Object obj : resultList){	       
//				       Object[] objects = (Object[]) obj;		       
//			    	   HashMap<String,Object> tmpObj = new HashMap<String,Object>();
//			    	   tmpObj.put("COD_REQUISITO", objects[0]);
//			    	   tmpObj.put("FILENAME", objects[1]);
//			    	   Object archi = objects[2];
//			    	   org.hibernate.engine.jdbc.SerializableBlobProxy file = (SerializableBlobProxy) archi;
//			    	   tmpObj.put("ARCHIVO",archi);
//			    	   outAl.add(tmpObj);
//			       }
//			       return outAl;
//			}
}
