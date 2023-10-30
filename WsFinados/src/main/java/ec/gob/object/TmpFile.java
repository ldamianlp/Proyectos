package ec.gob.object;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


/**
 * The persistent class for the TMP_FILE database table.
 * 
 */
@Entity
@Table(name="TMP_FILE")
@NamedQuery(name="TmpFile.findAll", query="SELECT t FROM TmpFile t")
public class TmpFile implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TmpFilePK id;

	@Lob
	private byte[] archivo;

	private String mymetype;

	private String nombre;
//	@Column	(name="FECHA")
	private Date fecha;

	public TmpFile() {
	}

	public TmpFilePK getId() {
		return this.id;
	}

	public void setId(TmpFilePK id) {
		this.id = id;
	}

	public byte[] getArchivo() {
		return this.archivo;
	}

	public void setArchivo(byte[] archivo) {
		this.archivo = archivo;
	}

	public String getMymetype() {
		return this.mymetype;
	}

	public void setMymetype(String mymetype) {
		this.mymetype = mymetype;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

}