package ec.gob.object;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the SP_FINA_EXPOSITOR database table.
 * 
 */
@Entity
@Table(name="SP_FINA_EXPOSITOR")
@NamedQuery(name="SpFinaExpositor.findAll", query="SELECT s FROM SpFinaExpositor s")
public class SpFinaExpositor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String artesano;

	private String ciudad;

	@Column(name="COD_EVENTO")
	private BigDecimal codEvento;

	private String correo;

	private String estado;

	@Column(name="ID_REP_LEGAL")
	private String idRepLegal;

	private String nombre;

	private String provincia;

	@Column(name="REP_LEGAL")
	private String repLegal;

	private String sorteo;

	private String telefono;

	@Column(name="TIPO_ID")
	private String tipoId;

	public SpFinaExpositor() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getArtesano() {
		return this.artesano;
	}

	public void setArtesano(String artesano) {
		this.artesano = artesano;
	}

	public String getCiudad() {
		return this.ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public BigDecimal getCodEvento() {
		return this.codEvento;
	}

	public void setCodEvento(BigDecimal codEvento) {
		this.codEvento = codEvento;
	}

	public String getCorreo() {
		return this.correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getIdRepLegal() {
		return this.idRepLegal;
	}

	public void setIdRepLegal(String idRepLegal) {
		this.idRepLegal = idRepLegal;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getProvincia() {
		return this.provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getRepLegal() {
		return this.repLegal;
	}

	public void setRepLegal(String repLegal) {
		this.repLegal = repLegal;
	}

	public String getSorteo() {
		return this.sorteo;
	}

	public void setSorteo(String sorteo) {
		this.sorteo = sorteo;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getTipoId() {
		return this.tipoId;
	}

	public void setTipoId(String tipoId) {
		this.tipoId = tipoId;
	}

}