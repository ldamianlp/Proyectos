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
@NamedQuery(name="ObjParCantones.findAll", query="SELECT o FROM ObjParCantones o")
public class ObjParCantones implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CAN_CODIGO")
	private long canCodigo;

	@Column(name="CAN_NOMBRE")
	private String canNombre;

	//bi-directional many-to-one association to ObjParProvincia
	@ManyToOne
	@JoinColumn(name="PRO_CODIGO")
	private ObjParProvincia ParProvincia;

	public ObjParCantones() {
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

	public ObjParProvincia getParProvincia() {
		return this.ParProvincia;
	}

	public void setParProvincia(ObjParProvincia ParProvincia) {
		this.ParProvincia = ParProvincia;
	}

}