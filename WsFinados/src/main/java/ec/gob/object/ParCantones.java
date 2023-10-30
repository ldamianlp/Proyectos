package ec.gob.object;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the PAR_CANTONES database table.
 * 
 */
@Entity
@Table(name="PAR_CANTONES")
@NamedQuery(name="ParCantones.findAll", query="SELECT p FROM ParCantones p")
public class ParCantones implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CAN_CODIGO")
	private long canCodigo;

	@Column(name="CAN_NOMBRE")
	private String canNombre;

	@Column(name="PRO_CODIGO")
	private BigDecimal proCodigo;

	public ParCantones() {
	}

	public long getCanCodigo() {
		return this.canCodigo;
	}

	public void setCanCodigo(long canCodigo) {
		this.canCodigo = canCodigo;
	}

	public String getCanNombre() {
		return this.canNombre;
	}

	public void setCanNombre(String canNombre) {
		this.canNombre = canNombre;
	}

	public BigDecimal getProCodigo() {
		return this.proCodigo;
	}

	public void setProCodigo(BigDecimal proCodigo) {
		this.proCodigo = proCodigo;
	}

}