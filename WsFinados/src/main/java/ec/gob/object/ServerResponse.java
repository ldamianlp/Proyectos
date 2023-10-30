package ec.gob.object;

/**
 * Clase para el envio de respuestas formateadas hacia el lado del cliente. Generalmente utilizadas para un correcto formateo de servlet hacia json.
 *@author BESIXPLUS CIA. LTDA.
 */
public class ServerResponse {
	private boolean success=false;
	private String msg = "";
	private Object objData = null;
	
	/**
	 * PARAMETROS PARA CONTENER LA RESPUESTA DEL SERVIDOR PARA EL CLIENTE.
	 * @param success SE ENVIA TRUE O FALSE SI A SIDO SATISFACTORIO LA OPERACION
	 * @param msg MENSAJE QUE SE ENVIA
	 * @param objData ENVIA INFORMACION ADICIONAL
	 */
	public ServerResponse(boolean success, String msg, Object objData) {
		super();
		this.success = success;
		this.msg = msg;
		this.objData = objData;
	}
	
	/**
	 * CONSTRUCTOR
	 */
	public ServerResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * OBTIENE TRUE O FALSE SI A SIDO SATISFACTORIO LA OPERACION
	 * @return success ENVIA TRUE O FALSE SI A SIDO SATISFACTORIO LA OPERACION
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * ESTABLECE TRUE O FALSE SI A SIDO SATISFACTORIO LA OPERACION
	 * @param success
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * OBTIENE EL MENSAJE QUE SE ENVIA
	 * @return msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * ESTABLECE EL MENSAJE QUE SE ENVIA
	 * @param msg MENSAJE QUE SE ENVIA
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * OBTIENE INFORMACION ADICIONAL
	 * @return objData
	 */
	public Object getObjData() {
		return objData;
	}

	/**
	 * ESTABLECE INFORMACION ADICIONAL
	 * @param objData ENVIA INFORMACION ADICIONAL
	 */
	public void setObjData(Object objData) {
		this.objData = objData;
	}	

}
