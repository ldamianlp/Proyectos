package ec.gob.object;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Resultado implements Serializable{

/**
	 * 
	 */
	private static final long serialVersionUID = 6453965520389006410L;
	
		
//	@SerializedName("codError")
//	@Expose
//	private String codError;
//	@SerializedName("error")
	@Expose
	private Object error;
	@SerializedName("success")
	@Expose
	private Object success;
	@SerializedName("mensaje")
	@Expose
	private String mensaje;
	
	private String datos;
	
	
	public Resultado() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Resultado(/*String codError, */Object error, Object success, String mensaje) {
		super();
//		this.codError = codError;
		this.error = error;
		this.success = success;
		this.mensaje = mensaje;
	}

//	public String getCodError() {
//	return codError;
//	}
	
//	public void setCodError(String codError) {
//	this.codError = codError;
//	}
	
	public Object getError() {
	return error;
	}
	
	public void setError(Object error) {
	this.error = error;
	}
	
	public Object getSuccess() {
	return success;
	}
	
	public void setSuccess(Object success) {
	this.success = success;
	}
	
	public String getMensaje() {
	return mensaje;
	}
	
	public void setMensaje(String mensaje) {
	this.mensaje = mensaje;
	}

	public String getDatos() {
		return datos;
	}

	public void setDatos(String datos) {
		this.datos = datos;
	}

}
