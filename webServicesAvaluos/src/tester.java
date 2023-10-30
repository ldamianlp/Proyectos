import java.io.UnsupportedEncodingException;

import javax.xml.bind.DatatypeConverter;
import javax.xml.soap.SOAPException;

import ec.gob.ambato.ws.WsCatastros;

public class tester {

	public static void main(String[] args) throws SOAPException {
		
		// TODO Auto-generated method stub
		WsCatastros obj = new WsCatastros();
//		String datos = obj.getDatosPredio("0108005010000","","1");
//		System.out.println(datos);
//		String retorno = obj.validarUsuario("LDLP2017", "benjamin2016");
//		System.out.println(retorno);
//		String retor = obj.validarUsuario("LDLP2017", "benjamin2016");
		String retor = obj.validarUsuario("JEPA2018", "123456");
//		String resp = obj.getParametersSyncro("LDLP2017", "benjamin2016");
		String resp = obj.getParametersSyncro("JEPA2018", "123456");
		System.out.println(resp);
		String encrip = cifrarBase64("damian");
		
		System.out.println(encrip);
			String dencrip = descifrarBase64(encrip);
		
		System.out.println(dencrip);
		
		
	}
	public static String cifrarBase64(String cadena){

		  byte[] message;
		  String encode="";
		  try {
		   message = cadena.getBytes("UTF-8");
		   encode =  DatatypeConverter.printBase64Binary(message); 
		      return encode;
		  } catch (UnsupportedEncodingException e) {
		   // TODO Auto-generated catch block   
		   e.printStackTrace();
		   return encode;
		  }
		       
		    }
		 
		    public static String descifrarBase64(String cadena){

		     byte[] decode;
		     String valor="";
		  try {
		   decode = DatatypeConverter.parseBase64Binary(cadena);
		   valor = new String(decode, "UTF-8");
		   return valor;
		  } catch (UnsupportedEncodingException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		   return valor;
		  }        
		       
		    }
}
