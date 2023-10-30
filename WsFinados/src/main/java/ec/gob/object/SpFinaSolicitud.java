package ec.gob.object;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.SequenceGenerator;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedStoredProcedureQuery(
		name = "SP_SEND_MAIL_APRO", 
		procedureName = "GADMAPPS.PKG_FINADOS.SP_SEND_MAIL_APRO",
		parameters = {
			    @StoredProcedureParameter(name="IN_SOL", type=BigDecimal.class, mode=ParameterMode.IN)
		  }
		)

/*@NamedStoredProcedureQueries(value = { @javax.persistence.NamedStoredProcedureQuery(
		name = "SP_SEND_MAIL_APRO", procedureName = "GADMAPPS.PKG_FINADOS.SP_SEND_MAIL_APRO",
		parameters = {
			    @StoredProcedureParameter(name="IN_SOL", type=BigDecimal.class, mode=ParameterMode.IN)
		  }
		) })*/
/**
 * The persistent class for the SP_FINA_SOLICITUD database table.
 * 
 */

@Table(name="SP_FINA_SOLICITUD")
@NamedQuery(name="SpFinaSolicitud.findAll", query="SELECT s FROM SpFinaSolicitud s")
public class SpFinaSolicitud implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUST_SEQ")
    @SequenceGenerator(sequenceName = "SEQ_T_SP_FINA_SOLICITUD", allocationSize = 1, name = "CUST_SEQ")
	private BigDecimal codigo;

	@Column(name="CAN_PUESTO")
	private BigDecimal canPuesto;

	@Column(name="COD_BLOQ")
	private BigDecimal codBloq;

	@Column(name="COD_CAT_NEGO")
	private BigDecimal codCatNego;

	@Column(name="COD_EVENTO")
	private BigDecimal codEvento;

	private String estado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FEC_INSERT")
	private Date fecInsert;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FEC_SOL")
	private Date fecSol;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FEC_UPDATE")
	private Date fecUpdate;

	@Column(name="ID_EXPOSITOR")
	private String idExpositor;

	@Column(name="USU_INSERT")
	private String usuInsert;

	@Column(name="USU_UPDATE")
	private String usuUpdate;

	public SpFinaSolicitud() {
	}

	public BigDecimal getCodigo() {
		return this.codigo;
	}

	public void setCodigo(BigDecimal codigo) {
		this.codigo = codigo;
	}

	public BigDecimal getCanPuesto() {
		return this.canPuesto;
	}

	public void setCanPuesto(BigDecimal canPuesto) {
		this.canPuesto = canPuesto;
	}

	public BigDecimal getCodBloq() {
		return this.codBloq;
	}

	public void setCodBloq(BigDecimal codBloq) {
		this.codBloq = codBloq;
	}

	public BigDecimal getCodCatNego() {
		return this.codCatNego;
	}

	public void setCodCatNego(BigDecimal codCatNego) {
		this.codCatNego = codCatNego;
	}

	public BigDecimal getCodEvento() {
		return this.codEvento;
	}

	public void setCodEvento(BigDecimal codEvento) {
		this.codEvento = codEvento;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFecInsert() {
		return this.fecInsert;
	}

	public void setFecInsert(Date fecInsert) {
		this.fecInsert = fecInsert;
	}

	public Date getFecSol() {
		return this.fecSol;
	}

	public void setFecSol(Date fecSol) {
		this.fecSol = fecSol;
	}

	public Date getFecUpdate() {
		return this.fecUpdate;
	}

	public void setFecUpdate(Date fecUpdate) {
		this.fecUpdate = fecUpdate;
	}

	public String getIdExpositor() {
		return this.idExpositor;
	}

	public void setIdExpositor(String idExpositor) {
		this.idExpositor = idExpositor;
	}

	public String getUsuInsert() {
		return this.usuInsert;
	}

	public void setUsuInsert(String usuInsert) {
		this.usuInsert = usuInsert;
	}

	public String getUsuUpdate() {
		return this.usuUpdate;
	}

	public void setUsuUpdate(String usuUpdate) {
		this.usuUpdate = usuUpdate;
	}

}