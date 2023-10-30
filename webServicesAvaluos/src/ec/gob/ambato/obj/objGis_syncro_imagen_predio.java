package ec.gob.ambato.obj;

import java.math.BigDecimal;
import java.util.Date;

public class objGis_syncro_imagen_predio {
	String sip_predio;
	BigDecimal sip_revision;
	BigDecimal pti_codigo;
	byte[] sip_imagen;
	String sip_nombre_ima;
	String sip_mimetype_ima;
	String sip_usuario_insert;
	Date sip_fecha_insert;
	public String getSip_predio() {
		return sip_predio;
	}
	public void setSip_predio(String sip_predio) {
		this.sip_predio = sip_predio;
	}
	public BigDecimal getSip_revision() {
		return sip_revision;
	}
	public void setSip_revision(BigDecimal sip_revision) {
		this.sip_revision = sip_revision;
	}
	public BigDecimal getPti_codigo() {
		return pti_codigo;
	}
	public void setPti_codigo(BigDecimal pti_codigo) {
		this.pti_codigo = pti_codigo;
	}
	public byte[] getSip_imagen() {
		return sip_imagen;
	}
	public void setSip_imagen(byte[] sip_imagen) {
		this.sip_imagen = sip_imagen;
	}
	public String getSip_nombre_ima() {
		return sip_nombre_ima;
	}
	public void setSip_nombre_ima(String sip_nombre_ima) {
		this.sip_nombre_ima = sip_nombre_ima;
	}
	public String getSip_mimetype_ima() {
		return sip_mimetype_ima;
	}
	public void setSip_mimetype_ima(String sip_mimetype_ima) {
		this.sip_mimetype_ima = sip_mimetype_ima;
	}
	public String getSip_usuario_insert() {
		return sip_usuario_insert;
	}
	public void setSip_usuario_insert(String sip_usuario_insert) {
		this.sip_usuario_insert = sip_usuario_insert;
	}
	public Date getSip_fecha_insert() {
		return sip_fecha_insert;
	}
	public void setSip_fecha_insert(Date sip_fecha_insert) {
		this.sip_fecha_insert = sip_fecha_insert;
	}
	
}
