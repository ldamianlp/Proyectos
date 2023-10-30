package ec.gob.object;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the PAR_PROVINCIAS database table.
 * 
 */
@Entity
@Table(name="PAR_PROVINCIAS")
@NamedQuery(name="ObjParProvincia.findAll", query="SELECT o FROM ObjParProvincia o")
public class ObjParProvincia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PRO_CODIGO")
	private long proCodigo;

	@Column(name="CODIGO_ANT")
	private String codigoAnt;

	@Column(name="PRO_NOMBRE")
	private String proNombre;

	//bi-directional many-to-one association to ObjParCantones
	@OneToMany(mappedBy="ParProvincia")
	private List<ObjParCantones> ParCantones;

	public ObjParProvincia() {
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

	public List<ObjParCantones> getParCantones() {
		return this.ParCantones;
	}

	public void setParCantones(List<ObjParCantones> ParCantones) {
		this.ParCantones = ParCantones;
	}

	public ObjParCantones addParCantone(ObjParCantones ParCantone) {
		getParCantones().add(ParCantone);
		ParCantone.setParProvincia(this);

		return ParCantone;
	}

	public ObjParCantones removeParCantone(ObjParCantones ParCantone) {
		getParCantones().remove(ParCantone);
		ParCantone.setParProvincia(null);

		return ParCantone;
	}

}