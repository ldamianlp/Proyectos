package ec.gob.utils;


import java.util.Base64;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;


public class Util {

	private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(Util.class);

	public String getUser(HttpServletRequest tmpRequest) {
		return tmpRequest.getUserPrincipal().getName();
		//		
		//		String[] lv_values=null; 
		//		String authorization = tmpRequest.getHeader("Authorization");
		//		 if (authorization != null && authorization.toLowerCase().startsWith("basic")) {			     
		//		     String base64Credentials = authorization.substring("Basic".length()).trim();
		//		     byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
		//		     String credentials = new String(credDecoded, StandardCharsets.UTF_8);
		//		     lv_values = credentials.split(":");			
		//		 }
		//		return lv_values[0];
	}
	public String getAutenticationWsGis() {
		Context env;
		String basicAuth="";
		try {
			env = (Context)new InitialContext().lookup("java:comp/env");
			String UserWS = (String)env.lookup("UserWsGis");
			String PasswordWS = (String)env.lookup("PasswordWsGis");
			String userpass = UserWS + ":" + PasswordWS;
			basicAuth = "Basic " + new String(Base64.getEncoder().encode(userpass.getBytes()));
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return basicAuth;
	}
	
}
