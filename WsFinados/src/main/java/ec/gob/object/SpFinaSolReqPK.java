package ec.gob.object;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the SP_FINA_SOL_REQ database table.
 * 
 */
@Embeddable
public class SpFinaSolReqPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="COD_SOL", insertable=false, updatable=false)
	private long codSol;

	@Column(name="COD_NEGOCIO", insertable=false, updatable=false)
	private long codNegocio;

	@Column(name="COD_REQUISITO", insertable=false, updatable=false)
	private long codRequisito;

	public SpFinaSolReqPK() {
	}
	public long getCodSol() {
		return this.codSol;
	}
	public void setCodSol(long codSol) {
		this.codSol = codSol;
	}
	public long getCodNegocio() {
		return this.codNegocio;
	}
	public void setCodNegocio(long codNegocio) {
		this.codNegocio = codNegocio;
	}
	public long getCodRequisito() {
		return this.codRequisito;
	}
	public void setCodRequisito(long codRequisito) {
		this.codRequisito = codRequisito;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SpFinaSolReqPK)) {
			return false;
		}
		SpFinaSolReqPK castOther = (SpFinaSolReqPK)other;
		return 
			(this.codSol == castOther.codSol)
			&& (this.codNegocio == castOther.codNegocio)
			&& (this.codRequisito == castOther.codRequisito);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.codSol ^ (this.codSol >>> 32)));
		hash = hash * prime + ((int) (this.codNegocio ^ (this.codNegocio >>> 32)));
		hash = hash * prime + ((int) (this.codRequisito ^ (this.codRequisito >>> 32)));
		
		return hash;
	}
}