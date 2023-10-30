package ec.gob.dao;

import java.math.BigDecimal;

import javax.persistence.EntityManager;

import ec.gob.object.ParProvincia;
import ec.gob.persist.JPAUtil;

public class ParProvinciaDAO {
	EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
	//private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(tmp_finadosDOA.class);

	public void update(ParProvincia objParProvincia) {
		em.getTransaction().begin();
		em.merge(objParProvincia);
		em.getTransaction().commit();
	}
	
	public ParProvincia find(BigDecimal id) {
		ParProvincia objParProvincia = new ParProvincia();		
		objParProvincia = em.find(ParProvincia.class, id);
		return objParProvincia;
	}
	
	/*public void delete(BigDecimal id) {
		ParProvincia objParProvincia = new ParProvincia();	
		objParProvincia = em.find(ParProvincia.class, id);
		em.getTransaction().begin();
		em.remove(objParProvincia);
		em.getTransaction().commit();
	}*/

}
