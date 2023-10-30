package ec.gob.dao;

import javax.persistence.EntityManager;

import ec.gob.object.ParDirectorio;
import ec.gob.persist.JPAUtil;

public class Par_directorioDAO {
	EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
	public void save(ParDirectorio parDirectorio ) {
		em.getTransaction().begin();
		em.persist(parDirectorio);
		em.getTransaction().commit();
	}
	
	public void update(ParDirectorio parDirectorio) {
		em.getTransaction().begin();
		em.merge(parDirectorio);
		em.getTransaction().commit();
	}
	
	public ParDirectorio find(String id) {
		ParDirectorio parDirectorio = new ParDirectorio();		
		parDirectorio = em.find(ParDirectorio.class, id);
		return parDirectorio;
	}
	
	public void delete(Long id) {
		ParDirectorio parDirectorio = new ParDirectorio();	
		parDirectorio = em.find(ParDirectorio.class, id);
		em.getTransaction().begin();
		em.remove(parDirectorio);
		em.getTransaction().commit();
	}
}
