//package ec.gob.ambato.format;
//
//import java.text.MessageFormat;
//import java.util.Locale;
//import java.util.ResourceBundle;
//
//public class Messages {
//	/**
//	 * Localizacion del sistema.
//	 */
//	private static Locale locale = Locale.getDefault();
//	/**
//	 * Manejador de recursos incrustados.
//	 */
//	private static ResourceBundle messages;
//	/**
//	 * Formateador de texto.
//	 */
//	private static MessageFormat msgFormatter = new MessageFormat("");
//	/**
//	 * Ruta del archivos de propiedades.
//	 */
//	private static String properties = null;
//	
//	/**
//	 * Obtiene un mensaje de texto de acuero a la localidad.
//	 * @param tipoMensaje Categoria del mensaje.
//	 * @param mensaje Identificador del mensaje.
//	 * @param argumentos Parametros para formatear el mensaje.
//	 * @return Mensaje de texto.
//	 */
//	public static String getMessage(TypeMessage tipoMensaje, CodesMessage mensaje, Object[] argumentos){
//		Messages.properties = Messages.class.getPackage().getName()+"."+tipoMensaje.toString();
//		Messages.messages = ResourceBundle.getBundle(Messages.properties, locale);
//		Messages.msgFormatter.applyPattern(Messages.messages.getString(mensaje.toString()));
//		return Messages.msgFormatter.format(argumentos);
//	}
//	
//	public static String getMessageInfo(CodesMessage mensaje, Object[] argumentos){
//		return getMessage(TypeMessage.Info, mensaje, argumentos);
//	}
//	
//	public static String getMessageError(CodesMessage mensaje, Object[] argumentos){
//		return getMessage(TypeMessage.Error, mensaje, argumentos);
//	}
//	
//	public static String getMessageInfo(CodesMessage mensaje){
//		return getMessage(TypeMessage.Info, mensaje, null);
//	}
//	
//	public static String getMessageError(CodesMessage mensaje){
//		return getMessage(TypeMessage.Error, mensaje, null);
//	}
//	
//	/**
//	 * Establece la localizada utilizada para obtener los mensajes de texto.
//	 * @param locale
//	 */
//	public static void setLocale(Locale locale){
//		Messages.locale = locale;
//	}
//}
