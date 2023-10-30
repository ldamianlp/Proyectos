package ec.gob.object;

import java.io.Serializable;

public class ResultadoSorteo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6453965520389006410L;
	private String bloque;
	private String puestos;
	private String sorteados;
	private String ocupados;
	private String codError;
	private String error;
	private String mensaje;
	
	public String getBloque() {
		return bloque;
	}
	public void setBloque(String bloque) {
		this.bloque = bloque;
	}
	public String getPuestos() {
		return puestos;
	}
	public void setPuestos(String puestos) {
		this.puestos = puestos;
	}
	public String getSorteados() {
		return sorteados;
	}
	public void setSorteados(String sorteados) {
		this.sorteados = sorteados;
	}
	public String getOcupados() {
		return ocupados;
	}
	public void setOcupados(String ocupados) {
		this.ocupados = ocupados;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public String getCodError() {
		return codError;
	}
	public void setCodError(String codError) {
		this.codError = codError;
	}
	

}


