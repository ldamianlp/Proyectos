package ec.gob.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import ec.gob.object.SpFinaSolicitud;
import ec.gob.persist.JPAUtil;

public class SpFinaSolicitudDAO {
	EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
	//private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(SpFinaSolicitudDAO.class);
	public List<SpFinaSolicitud> getSolicitudes(
			String inIdExpositor
	) {
		List<SpFinaSolicitud> listaSolicitud = new ArrayList<>();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			 CriteriaQuery<SpFinaSolicitud> cq = cb.createQuery(SpFinaSolicitud.class);
			 Root<SpFinaSolicitud> rootParCantones = cq.from(SpFinaSolicitud.class);			 
			 
			 cq.select(rootParCantones);
			 Predicate[] predicates = new Predicate[1];	
			 predicates[0] = cb.equal(rootParCantones.get("idExpositor"),inIdExpositor);
			 cq.where(predicates);
			 listaSolicitud = em.createQuery(cq).getResultList();
			 
			 return listaSolicitud;
		}	
//	public SpFinaSolicitud find(String id) {
//		SpFinaSolicitud obj = new SpFinaSolicitud();		
//		obj = em.find(SpFinaSolicitud.class, id);
//		return obj;
//	}
//	public SpFinaSolicitud save(SpFinaSolicitud obj) {
//		em.getTransaction().begin();
//		em.persist(obj);
//		em.getTransaction().commit();		
//		return obj;
//	}
//	public void update(SpFinaSolicitud obj) {
//		em.getTransaction().begin();
//		em.merge(obj);
//		em.getTransaction().commit();
//	}
}
