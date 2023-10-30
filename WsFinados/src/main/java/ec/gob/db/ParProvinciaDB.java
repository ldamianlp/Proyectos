package ec.gob.db;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import ec.gob.dao.ParProvinciaDAO;
import ec.gob.object.ObjParProvincia;
import ec.gob.object.ParProvincia;
import ec.gob.persist.JPAUtil;

public class ParProvinciaDB {
	EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
	private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(ParProvinciaDAO.class);
	
	@SuppressWarnings("unchecked")
	public List<ParProvincia> getAllProvincias() {
	  List<ParProvincia> listaParProvincia = new ArrayList<>();
	  Query q = em.createQuery("SELECT p FROM ParProvincia p");
	  listaParProvincia = q.getResultList();
	  return listaParProvincia;
	}
	/*public String getAllProvicias() {
		ParProvinciaDAO obj = new ParProvinciaDAO();
		objTmp_finados obj1 = obj.find(new BigDecimal(1));
		Gson gson = new Gson();
		String retorno = gson.toJson(obj1);
		return retorno;
	}*/
	@SuppressWarnings("unchecked")
	public List<ObjParProvincia> getProvinciaCanton() {
//		SELECT d FROM Employee e INNER JOIN e.department d
//		Query q = em.createNativeQuery("SELECT o FROM ParCantones o INNER JOIN d.ParProvincia e ",ObjParCantones.class);
		List<ObjParProvincia> listaParProvincia = new ArrayList<>();
//		  Query q = em.createQuery("SELECT p FROM ParProvincia p");
//		TypedQuery<ObjParCantones> q = em.createQuery("SELECT o FROM ObjParCantones o INNER JOIN o.ParProvincia e ",ObjParCantones.class);
		TypedQuery<ObjParProvincia> q = em.createQuery("SELECT o FROM ObjParProvincia o INNER JOIN o.ParCantones e ",ObjParProvincia.class);
		  listaParProvincia = q.getResultList();
		  return listaParProvincia;
		}
	
}
