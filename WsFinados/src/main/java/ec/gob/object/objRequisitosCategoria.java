package ec.gob.object;

public class objRequisitosCategoria {
	private String descripcion;
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String nombre) {
		this.descripcion = nombre;
	}
	public long getCod_requisito() {
		return cod_requisito;
	}
	public void setCod_requisito(long cod_requisito) {
		this.cod_requisito = cod_requisito;
	}
	public long getCod_negocio() {
		return cod_negocio;
	}
	public void setCod_negocio(long cod_negocio) {
		this.cod_negocio = cod_negocio;
	}
	public String getObligatorio() {
		return obligatorio;
	}
	public void setObligatorio(String obligatorio) {
		this.obligatorio = obligatorio;
	}
	private long cod_requisito;
	private long cod_negocio;
	private String obligatorio;
}
