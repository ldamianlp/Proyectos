package ec.gob.ambato.ws;

import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath ("/interno")
public class claseRestLoad extends Application{
	@Override
	 public Set<Class<?>> getClasses(){
	  Set<Class<?>> resources = new java.util.HashSet<>();
	  addRestResourceClasses(resources);  
	  return resources;
	 }

	 private void addRestResourceClasses(Set<Class<?>> resources) {	  
	  resources.add(WsPrivado.class);  
	  
	 }
}
