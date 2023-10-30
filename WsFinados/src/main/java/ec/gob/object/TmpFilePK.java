package ec.gob.object;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the TMP_FILE database table.
 * 
 */
@Embeddable
public class TmpFilePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ID_SESSION")
	private String idSession;

	@Column(name="ID_REQUISITO")
	private long idRequisito;

	public TmpFilePK() {
	}
	public String getIdSession() {
		return this.idSession;
	}
	public void setIdSession(String idSession) {
		this.idSession = idSession;
	}
	public long getIdRequisito() {
		return this.idRequisito;
	}
	public void setIdRequisito(long idRequisito) {
		this.idRequisito = idRequisito;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TmpFilePK)) {
			return false;
		}
		TmpFilePK castOther = (TmpFilePK)other;
		return 
			this.idSession.equals(castOther.idSession)
			&& (this.idRequisito == castOther.idRequisito);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idSession.hashCode();
		hash = hash * prime + ((int) (this.idRequisito ^ (this.idRequisito >>> 32)));
		
		return hash;
	}
}