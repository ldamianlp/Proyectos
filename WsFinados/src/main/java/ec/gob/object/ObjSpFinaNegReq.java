package ec.gob.object;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the SP_FINA_NEG_REQ database table.
 * 
 */
@Entity
@Table(name="SP_FINA_NEG_REQ")
@NamedQuery(name="ObjSpFinaNegReq.findAll", query="SELECT o FROM ObjSpFinaNegReq o")
public class ObjSpFinaNegReq implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ObjSpFinaNegReqPK id;

	private String obligatorio;
	private long cod_negocio;
	private long cod_requisito;

	//bi-directional many-to-one association to ObjSpFinaRequisito
	@ManyToOne
	@JoinColumn(name="COD_REQUISITO", insertable=false, updatable=false)
	private ObjSpFinaRequisito spFinaRequisito;

	public ObjSpFinaNegReq() {
	}

	public ObjSpFinaNegReqPK getId() {
		return this.id;
	}

	public void setId(ObjSpFinaNegReqPK id) {
		this.id = id;
	}

	public String getObligatorio() {
		return this.obligatorio;
	}

	public void setObligatorio(String obligatorio) {
		this.obligatorio = obligatorio;
	}

	public ObjSpFinaRequisito getSpFinaRequisito() {
		return this.spFinaRequisito;
	}

	public void setSpFinaRequisito(ObjSpFinaRequisito spFinaRequisito) {
		this.spFinaRequisito = spFinaRequisito;
	}
	
	public long getCod_negocio() {
		return cod_negocio;
	}

	public void setCod_negocio(long cod_negocio) {
		this.cod_negocio = cod_negocio;
	}

	public long getCod_requisito() {
		return cod_requisito;
	}

	public void setCod_requisito(long cod_requisito) {
		this.cod_requisito = cod_requisito;
	}

}