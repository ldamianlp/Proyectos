package ec.gob.enumeracion;

public class Enums {

	/**
	 * URL DE LOS METODOS DEL WEB SERVICE
	 *
	 */	

	/**
	 *TIPO DE IDENTIFICACION
	 *
	 */
	public enum TipIdentificacion{

		RUC("RUC"),CEDULA("CED"),PASAPORTE("PAS");

		private String val;

		private TipIdentificacion(String c) {
			this.val = c;
		} 	

		public String getValue() {
			return this.val;
		}

		public String getString() {
			return this.val;
		}
	}

	public enum Errores{
		e00("00"),e01("01"),e02("02"),e03("03"),e04("04"),
		
		//expositor
		e10("10"),
		//DATO SEGURO
		e50("50"),e51("51"),
		//otros
		e90("90");

		private String val;
		private Errores(String c) {
			this.val = c;
		}
		public String getCodError() {
			return this.val;
		}

		public String getString() {
			return this.val;
		}
		
		public String getMensaje() {
			switch(this) {
			case e00:
				return "Transaccion exitosa";
			case e01:
				return "Ha ocurrido un error al momento de Ingresar los datos del expositor. Intente Nuevamente mas tarde";
			case e02:
				return "No hay disponibilidad de Puestos para la categoria seleccionada";
			case e03:
				return "Ha ocurrido un error al momento de sortear los puestos";
			case e04:
				return "Ha ocurrido un error al momento de Registrar la Solicitud";
			case e10:
				return "Problemas con la creacion del Expositor";
			case e50:
				return "Tipo de Documento Inválido";
			case e51:
				return "Ha ocurrido un error al momento de validar la informaicon con Dato Seguro. Intente mas tarde";
			case e90:
				return "Ha ocurrido un error al momento de Ingresar los datos del expositor. Intente Nuevamente mas tarde";	

			default:
				return "No definido";
			}
		}

	}	
}
