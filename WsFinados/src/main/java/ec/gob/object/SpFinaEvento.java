package ec.gob.object;

import java.io.Serializable;
import javax.persistence.*;

import org.json.JSONPropertyName;

import java.util.Date;


/**
 * The persistent class for the SP_FINA_EVENTOS database table.
 * 
 */
@Entity
@Table(name="SP_FINA_EVENTOS")
@NamedQuery(name="SpFinaEvento.findAll", query="SELECT s FROM SpFinaEvento s")
public class SpFinaEvento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long codigo;

	private String direccion;

	private String estado;
	
	@Temporal(TemporalType.TIMESTAMP)
//	@Temporal(TemporalType.DATE)
//	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date fechafin;

//	@Temporal(TemporalType.DATE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechainicio;

	private String nombre;

	@Column(name="TIP_EVE_COD")
	private String tipEveCod;

	public SpFinaEvento() {
	}

	public long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechafin() {
		return this.fechafin;
	}

	public void setFechafin(Date fechafin) {
		this.fechafin = fechafin;
	}

	public Date getFechainicio() {
		return this.fechainicio;
	}

	public void setFechainicio(Date fechainicio) {
		this.fechainicio = fechainicio;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipEveCod() {
		return this.tipEveCod;
	}

	public void setTipEveCod(String tipEveCod) {
		this.tipEveCod = tipEveCod;
	}

}