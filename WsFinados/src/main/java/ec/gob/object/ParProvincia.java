package ec.gob.object;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the PAR_PROVINCIAS database table.
 * 
 */
@Entity
@Table(name="PAR_PROVINCIAS")
@NamedQuery(name="ParProvincia.findAll", query="SELECT p FROM ParProvincia p")
public class ParProvincia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PRO_CODIGO")
	private long proCodigo;

	@Column(name="CODIGO_ANT")
	private String codigoAnt;

	@Column(name="PRO_NOMBRE")
	private String proNombre;

	public ParProvincia() {
	}

	public long getProCodigo() {
		return this.proCodigo;
	}

	public void setProCodigo(long proCodigo) {
		this.proCodigo = proCodigo;
	}

	public String getCodigoAnt() {
		return this.codigoAnt;
	}

	public void setCodigoAnt(String codigoAnt) {
		this.codigoAnt = codigoAnt;
	}

	public String getProNombre() {
		return this.proNombre;
	}

	public void setProNombre(String proNombre) {
		this.proNombre = proNombre;
	}

}