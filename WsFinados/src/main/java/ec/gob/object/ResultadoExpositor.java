package ec.gob.object;

import java.io.Serializable;

public class ResultadoExpositor implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3471109969615437667L;
	
	private String id;
	private Resultado resultado;
	
	public ResultadoExpositor() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ResultadoExpositor(String id,Resultado resultado) {
		super();
		this.id = id;
		this.resultado = resultado;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public Resultado getResultado() {
		return resultado;
	}
	public void setResultado(Resultado resultado) {
		this.resultado = resultado;
	}
	
	

}
