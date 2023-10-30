package ec.gob.dao;

import javax.persistence.EntityManager;

import ec.gob.object.PaMaParametro;
import ec.gob.persist.JPAUtil;

public class PaMaParametrosDAO {
	EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
//	public void save(PaMaParametro PaMaParametro ) {
//		em.getTransaction().begin();
//		em.persist(PaMaParametro);
//		em.getTransaction().commit();
//	}
//	
//	public void update(PaMaParametro PaMaParametro) {
//		em.getTransaction().begin();
//		em.merge(PaMaParametro);
//		em.getTransaction().commit();
//	}
	
	public PaMaParametro find(Long parCodigo) {
		PaMaParametro PaMaParametro = new PaMaParametro();		
		PaMaParametro = em.find(PaMaParametro.class, parCodigo);
		return PaMaParametro;
	}
	
//	public void delete(Long id) {
//		PaMaParametro PaMaParametro = new PaMaParametro();	
//		PaMaParametro = em.find(PaMaParametro.class, id);
//		em.getTransaction().begin();
//		em.remove(PaMaParametro);
//		em.getTransaction().commit();
//	}
}
