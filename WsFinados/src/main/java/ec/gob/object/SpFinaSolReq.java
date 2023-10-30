package ec.gob.object;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the SP_FINA_SOL_REQ database table.
 * 
 */
@Entity
@Table(name="SP_FINA_SOL_REQ")
@NamedQuery(name="SpFinaSolReq.findAll", query="SELECT s FROM SpFinaSolReq s")
public class SpFinaSolReq implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SpFinaSolReqPK id;

	private String filename;

	private String filepath;
	private String mimetype;

	public SpFinaSolReq() {
	}

	public SpFinaSolReqPK getId() {
		return this.id;
	}

	public void setId(SpFinaSolReqPK id) {
		this.id = id;
	}

	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilepath() {
		return this.filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getMimetype() {
		return mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

}