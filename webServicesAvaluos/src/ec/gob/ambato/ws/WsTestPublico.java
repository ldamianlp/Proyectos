/**
 * WsCatastros_parameters.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ec.gob.ambato.ws;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.soap.SOAPException;
import javax.xml.ws.WebServiceContext;

@WebService
public class WsTestPublico {
	private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(WsCatastros.class);
	private static final long serialVersionUID = 1116984823;
	@Resource WebServiceContext wctx;
	
	@WebMethod
	public String holaMundo(
			@WebParam(name="inUser")String inUser,
			@WebParam(name="inPaswword")String inPaswword
    )throws SOAPException{		
        return "HOLA MUNDO";
    }
}
