package ec.gob.object;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the SP_FINA_SOLICITUD_DET database table.
 * 
 */
@Entity
@Table(name="SP_FINA_SOLICITUD_DET")
@NamedQuery(name="SpFinaSolicitudDet.findAll", query="SELECT s FROM SpFinaSolicitudDet s")
public class SpFinaSolicitudDet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUST_SEQ_SOLT")
    @SequenceGenerator(sequenceName = "SEQ_T_SP_FINA_SOL_DET", allocationSize = 1, name = "CUST_SEQ_SOLT")
	private long codigo;

	@Column(name="COD_PUESTO")
	private java.math.BigDecimal codPuesto;

	@Column(name="COD_SOL")
	private java.math.BigDecimal codSol;

	public SpFinaSolicitudDet() {
	}

	public long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}

	public java.math.BigDecimal getCodPuesto() {
		return this.codPuesto;
	}

	public void setCodPuesto(java.math.BigDecimal codPuesto) {
		this.codPuesto = codPuesto;
	}

	public java.math.BigDecimal getCodSol() {
		return this.codSol;
	}

	public void setCodSol(java.math.BigDecimal codSol) {
		this.codSol = codSol;
	}

}