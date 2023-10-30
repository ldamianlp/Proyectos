package ec.gob.object;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the AOC_ADJUNTO database table.
 * 
 */
@Entity
@Table(name="TMP_FINADOS", schema="GADMAPPS")
@NamedQuery(name="objTmp_finados.findAll", query="SELECT a FROM objTmp_finados a")
public class objTmp_finados {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="ID")
	private BigDecimal id;
	@Column(name="ANIO")
    private Integer anio;
	@Column(name="DESCRIPCION")
    private String descripcion;
	@Column(name="USUARIO")
    private String usuario;
	
	public objTmp_finados() {
	}
	public BigDecimal getId() {
		return id;
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}
	public Integer getAnio() {
		return anio;
	}
	public void setNumber(Integer anio) {
		this.anio = anio;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
