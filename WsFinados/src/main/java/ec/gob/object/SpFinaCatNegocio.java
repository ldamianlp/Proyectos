package ec.gob.object;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the SP_FINA_CAT_NEGOCIO database table.
 * 
 */
@Entity
@Table(name="SP_FINA_CAT_NEGOCIO")
@NamedQuery(name="SpFinaCatNegocio.findAll", query="SELECT s FROM SpFinaCatNegocio s")
public class SpFinaCatNegocio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long codigo;

	@Column(name="COD_EVENTO")
	private java.math.BigDecimal codEvento;

	private String estado;

	private String nombre;

	public SpFinaCatNegocio() {
	}

	public long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}

	public java.math.BigDecimal getCodEvento() {
		return this.codEvento;
	}

	public void setCodEvento(java.math.BigDecimal codEvento) {
		this.codEvento = codEvento;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}