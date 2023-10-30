package ec.gob.dao;

import javax.persistence.EntityManager;

import ec.gob.object.SpFinaExpositor;
import ec.gob.persist.JPAUtil;

public class SpFinaExpositorDAO {
	EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
	private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(SpFinaExpositorDAO.class);

	public SpFinaExpositor find(String id) {
		SpFinaExpositor obj = new SpFinaExpositor();		
		obj = em.find(SpFinaExpositor.class, id);
		return obj;
	}
	public SpFinaExpositor save(SpFinaExpositor obj) {
		em.getTransaction().begin();
		em.persist(obj);
		em.getTransaction().commit();		
		return obj;
	}
	public void update(SpFinaExpositor obj) {
		em.getTransaction().begin();
		em.merge(obj);
		em.getTransaction().commit();
	}
}
