package ec.gob.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import ec.gob.object.ParCantones;
import ec.gob.persist.JPAUtil;

public class ParCantonDAO {
	EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
	private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(ParCantonDAO.class);	
	
	public ParCantones find(long id) {
		ParCantones objParCantones = new ParCantones();		
		objParCantones = em.find(ParCantones.class, id);
		return objParCantones;
	}
	
	public List<ParCantones> getCantonesByProv(long inProCodigo) {
		List<ParCantones> listaCantones = new ArrayList<>();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			 CriteriaQuery<ParCantones> cq = cb.createQuery(ParCantones.class);
			 Root<ParCantones> rootParCantones = cq.from(ParCantones.class);			 
			 try 
			 {
				 cq.select(rootParCantones);
				 Predicate[] predicates = new Predicate[1];	//numero de condiciones
//				 predicates[0] = cb.or(cb.equal(rootParCantones.get("proCodigo"),inProCodigo),cb.equal(rootParCantones.get("proCodigo"),17));
				 predicates[0] = cb.equal(rootParCantones.get("proCodigo"),inProCodigo);

//				 cq.where(cb.equal(rootParCantones.get("proCodigo"),inProCodigo));
				 cq.where(predicates);
				 listaCantones = em.createQuery(cq).getResultList();
			 }catch (NoResultException e){
				 e.printStackTrace();
				 LOGGER.error("ERROR: getCantonesByProv: "+ e.getMessage());
			 }
			 return listaCantones;
		}	

}
