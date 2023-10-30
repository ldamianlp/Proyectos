package ec.gob.object;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the SP_FINA_REQUISITO database table.
 * 
 */
@Entity
@Table(name="SP_FINA_REQUISITO")
@NamedQuery(name="ObjSpFinaRequisito.findAll", query="SELECT o FROM ObjSpFinaRequisito o")
public class ObjSpFinaRequisito implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long codigo;

	private String descripcion;

	private String estado;

	//bi-directional many-to-one association to ObjSpFinaNegReq
	@OneToMany(mappedBy="spFinaRequisito")
	private List<ObjSpFinaNegReq> spFinaNegReqs;

	public ObjSpFinaRequisito() {
	}

	public long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public List<ObjSpFinaNegReq> getSpFinaNegReqs() {
		return this.spFinaNegReqs;
	}

	public void setSpFinaNegReqs(List<ObjSpFinaNegReq> spFinaNegReqs) {
		this.spFinaNegReqs = spFinaNegReqs;
	}

	public ObjSpFinaNegReq addSpFinaNegReq(ObjSpFinaNegReq spFinaNegReq) {
		getSpFinaNegReqs().add(spFinaNegReq);
		spFinaNegReq.setSpFinaRequisito(this);

		return spFinaNegReq;
	}

	public ObjSpFinaNegReq removeSpFinaNegReq(ObjSpFinaNegReq spFinaNegReq) {
		getSpFinaNegReqs().remove(spFinaNegReq);
		spFinaNegReq.setSpFinaRequisito(null);

		return spFinaNegReq;
	}

}