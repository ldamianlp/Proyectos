package ec.gob.object;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the PAR_DIRECTORIO database table.
 * 
 */
@Entity
@Table(name="PAR_DIRECTORIO")
@NamedQuery(name="ParDirectorio.findAll", query="SELECT p FROM ParDirectorio p")
public class ParDirectorio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PAR_CODIGO")
	private String parCodigo;

	@Column(name="PAR_DESC")
	private String parDesc;

	@Column(name="PAR_ESTADO")
	private String parEstado;

	@Column(name="PAR_URL")
	private String parUrl;

	public ParDirectorio() {
	}

	public String getParCodigo() {
		return this.parCodigo;
	}

	public void setParCodigo(String parCodigo) {
		this.parCodigo = parCodigo;
	}

	public String getParDesc() {
		return this.parDesc;
	}

	public void setParDesc(String parDesc) {
		this.parDesc = parDesc;
	}

	public String getParEstado() {
		return this.parEstado;
	}

	public void setParEstado(String parEstado) {
		this.parEstado = parEstado;
	}

	public String getParUrl() {
		return this.parUrl;
	}

	public void setParUrl(String parUrl) {
		this.parUrl = parUrl;
	}

}