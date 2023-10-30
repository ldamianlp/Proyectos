package ec.gob.object;

import java.math.BigDecimal;



public class objTmpFile {
	private BigDecimal id_requisito;
	private String nombre;
	private byte[] archivo;
	public BigDecimal getId_requisito() {
		return id_requisito;
	}
	public void setId_requisito(BigDecimal id_requisito) {
		this.id_requisito = id_requisito;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public byte[] getArchivo() {
		return archivo;
	}
	public void setArchivo(byte[] archivo) {
		this.archivo = archivo;
	}
}
