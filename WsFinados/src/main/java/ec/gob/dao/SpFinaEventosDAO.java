package ec.gob.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import ec.gob.object.SpFinaEvento;
import ec.gob.persist.JPAUtil;

public class SpFinaEventosDAO {
	EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
	private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(SpFinaEventosDAO.class);

	public void update(SpFinaEvento objSpFinaEvento) {
		em.getTransaction().begin();
		em.merge(objSpFinaEvento);
		em.getTransaction().commit();
	}
	
	public SpFinaEvento find(BigDecimal id) {
		SpFinaEvento objSpFinaEvento = new SpFinaEvento();		
		objSpFinaEvento = em.find(SpFinaEvento.class, id);
		return objSpFinaEvento;
	}
	
	
	public List<SpFinaEvento> getEventosActivos() {
		List<SpFinaEvento> listaEventos = new ArrayList<>();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			 CriteriaQuery<SpFinaEvento> cq = cb.createQuery(SpFinaEvento.class);
			 Root<SpFinaEvento> rootParCantones = cq.from(SpFinaEvento.class);			 
			 try 
			 {
				 cq.select(rootParCantones);
				 Predicate[] predicates = new Predicate[1];	//numero de condiciones
				 predicates[0] = cb.equal(rootParCantones.get("estado"),"A");

//				 cq.where(cb.equal(rootParCantones.get("proCodigo"),inProCodigo));
				 cq.where(predicates);
				 listaEventos = em.createQuery(cq).getResultList();
			 }catch (NoResultException e){
				 e.printStackTrace();
				 LOGGER.error("ERROR: getEventosActivos: "+ e.getMessage());
			 }
			 return listaEventos;
		}	
	
	@SuppressWarnings("unchecked")
	public Collection<SpFinaEvento> getEventosActivos1() {
//		List<SpFinaEvento> listaEventos = new ArrayList<>();
		Collection<SpFinaEvento> listaEventos = em.createQuery("SELECT e.codigo,e.nombre,e.fechainicio,e.fechafin " +
	                   "FROM SpFinaEvento e " +
	                   "WHERE estado = :estado")
	      .setParameter("estado","A")
	      .getResultList();
		return listaEventos;
		}	

}
