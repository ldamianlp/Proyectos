package ec.gob.object;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;


/**
 * The persistent class for the VW_FINADO_PUESTOS_ASIG database table.
 * 
 */
@Entity
@Immutable
@Table(name="VW_FINADO_PUESTOS_ASIG")
@NamedQuery(name="VwFinadoPuestosAsig.findAll", query="SELECT v FROM VwFinadoPuestosAsig v")
public class VwFinadoPuestosAsig implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="CAN_PUESTO")
	private BigDecimal canPuesto;

	@Column(name="COD_BLOQ")
	private BigDecimal codBloq;

	@Column(name="COD_CAT_NEGO")
	private BigDecimal codCatNego;

	@Column(name="COD_EVENTO")
	private BigDecimal codEvento;

	@Column(name="COD_PUESTO")
	private BigDecimal codPuesto;

	@Column(name="EST_FACTURA")
	private String estFactura;

	@Column(name="EST_PUESTO")
	private String estPuesto;

	@Column(name="EST_SOLICITUD")
	private String estSolicitud;

	private BigDecimal factura;
@Id
	@Column(name="ID_EXPOSITOR")
	private String idExpositor;

	@Column(name="NOM_BLOQ")
	private String nomBloq;

	@Column(name="NOM_EVENTO")
	private String nomEvento;

	@Column(name="NOM_NEGO")
	private String nomNego;

	private String nombre;

	@Column(name="NUM_PUESTO")
	private BigDecimal numPuesto;

	private BigDecimal solic;

	@Column(name="VAL_FAC")
	private BigDecimal valFac;

	public VwFinadoPuestosAsig() {
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

	public BigDecimal getCodPuesto() {
		return this.codPuesto;
	}

	public void setCodPuesto(BigDecimal codPuesto) {
		this.codPuesto = codPuesto;
	}

	public String getEstFactura() {
		return this.estFactura;
	}

	public void setEstFactura(String estFactura) {
		this.estFactura = estFactura;
	}

	public String getEstPuesto() {
		return this.estPuesto;
	}

	public void setEstPuesto(String estPuesto) {
		this.estPuesto = estPuesto;
	}

	public String getEstSolicitud() {
		return this.estSolicitud;
	}

	public void setEstSolicitud(String estSolicitud) {
		this.estSolicitud = estSolicitud;
	}

	public BigDecimal getFactura() {
		return this.factura;
	}

	public void setFactura(BigDecimal factura) {
		this.factura = factura;
	}

	public String getIdExpositor() {
		return this.idExpositor;
	}

	public void setIdExpositor(String idExpositor) {
		this.idExpositor = idExpositor;
	}

	public String getNomBloq() {
		return this.nomBloq;
	}

	public void setNomBloq(String nomBloq) {
		this.nomBloq = nomBloq;
	}

	public String getNomEvento() {
		return this.nomEvento;
	}

	public void setNomEvento(String nomEvento) {
		this.nomEvento = nomEvento;
	}

	public String getNomNego() {
		return this.nomNego;
	}

	public void setNomNego(String nomNego) {
		this.nomNego = nomNego;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public BigDecimal getNumPuesto() {
		return this.numPuesto;
	}

	public void setNumPuesto(BigDecimal numPuesto) {
		this.numPuesto = numPuesto;
	}

	public BigDecimal getSolic() {
		return this.solic;
	}

	public void setSolic(BigDecimal solic) {
		this.solic = solic;
	}

	public BigDecimal getValFac() {
		return this.valFac;
	}

	public void setValFac(BigDecimal valFac) {
		this.valFac = valFac;
	}

}