package ec.gob.object;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the PA_MA_PARAMETROS database table.
 * 
 */
@Entity
@Table(name="PA_MA_PARAMETROS")
@NamedQuery(name="PaMaParametro.findAll", query="SELECT p FROM PaMaParametro p")
public class PaMaParametro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PAR_CODIGO")
	private long parCodigo;

	@Column(name="PAR_DESCRIPCION")
	private String parDescripcion;

	@Column(name="PAR_VALOR_CADENA")
	private String parValorCadena;

	@Column(name="PAR_VALOR_DECIMAL")
	private BigDecimal parValorDecimal;

	@Column(name="PAR_VALOR_ENTERO")
	private BigDecimal parValorEntero;

	public PaMaParametro() {
	}

	public long getParCodigo() {
		return this.parCodigo;
	}

	public void setParCodigo(long parCodigo) {
		this.parCodigo = parCodigo;
	}

	public String getParDescripcion() {
		return this.parDescripcion;
	}

	public void setParDescripcion(String parDescripcion) {
		this.parDescripcion = parDescripcion;
	}

	public String getParValorCadena() {
		return this.parValorCadena;
	}

	public void setParValorCadena(String parValorCadena) {
		this.parValorCadena = parValorCadena;
	}

	public BigDecimal getParValorDecimal() {
		return this.parValorDecimal;
	}

	public void setParValorDecimal(BigDecimal parValorDecimal) {
		this.parValorDecimal = parValorDecimal;
	}

	public BigDecimal getParValorEntero() {
		return this.parValorEntero;
	}

	public void setParValorEntero(BigDecimal parValorEntero) {
		this.parValorEntero = parValorEntero;
	}

}