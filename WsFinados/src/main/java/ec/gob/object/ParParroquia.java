package ec.gob.object;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the PAR_PARROQUIAS database table.
 * 
 */
@Entity
@Table(name="PAR_PARROQUIAS")
@NamedQuery(name="ParParroquia.findAll", query="SELECT p FROM ParParroquia p")
public class ParParroquia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PAR_CODIGO")
	private long parCodigo;

	@Column(name="CAN_CODIGO")
	private BigDecimal canCodigo;

	@Column(name="PAR_COORDENADA_X")
	private BigDecimal parCoordenadaX;

	@Column(name="PAR_COORDENADA_Y")
	private BigDecimal parCoordenadaY;

	@Column(name="PAR_NOMBRE")
	private String parNombre;

	public ParParroquia() {
	}

	public long getParCodigo() {
		return this.parCodigo;
	}

	public void setParCodigo(long parCodigo) {
		this.parCodigo = parCodigo;
	}

	public BigDecimal getCanCodigo() {
		return this.canCodigo;
	}

	public void setCanCodigo(BigDecimal canCodigo) {
		this.canCodigo = canCodigo;
	}

	public BigDecimal getParCoordenadaX() {
		return this.parCoordenadaX;
	}

	public void setParCoordenadaX(BigDecimal parCoordenadaX) {
		this.parCoordenadaX = parCoordenadaX;
	}

	public BigDecimal getParCoordenadaY() {
		return this.parCoordenadaY;
	}

	public void setParCoordenadaY(BigDecimal parCoordenadaY) {
		this.parCoordenadaY = parCoordenadaY;
	}

	public String getParNombre() {
		return this.parNombre;
	}

	public void setParNombre(String parNombre) {
		this.parNombre = parNombre;
	}

}