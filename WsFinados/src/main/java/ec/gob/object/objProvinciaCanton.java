package ec.gob.object;

import java.util.List;

public class objProvinciaCanton {

	private long PRO_CODIGO;
	private String PRO_NOMBRE;
	public long getPRO_CODIGO() {
		return PRO_CODIGO;
	}
	public void setPRO_CODIGO(long pRO_CODIGO) {
		PRO_CODIGO = pRO_CODIGO;
	}
	public String getPRO_NOMBRE() {
		return PRO_NOMBRE;
	}
	public void setPRO_NOMBRE(String pRO_NOMBRE) {
		PRO_NOMBRE = pRO_NOMBRE;
	}
	public List<ParCantones> getCantones() {
		return cantones;
	}
	public void setCantones(List<ParCantones> cantones) {
		this.cantones = cantones;
	}
	private List<ParCantones> cantones;
	
}
