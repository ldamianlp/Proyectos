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
@NamedQuery(name="ParProvinciaParCantones.findAll", query="SELECT p FROM ParProvinciaParCantones p")
public class ParProvinciaParCantones implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CAN_CODIGO")
	private long canCodigo;

	@Column(name="CAN_NOMBRE")
	private String canNombre;

	//bi-directional many-to-one association to ParProvincia
	@ManyToOne
	@JoinColumn(name="PRO_CODIGO")
	private ParProvincia ObjParProvincia;

	public ParProvinciaParCantones() {
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

	public ParProvincia getObjParProvincia() {
		return this.ObjParProvincia;
	}

	public void setObjParProvincia(ParProvincia ObjParProvincia) {
		this.ObjParProvincia = ObjParProvincia;
	}

}