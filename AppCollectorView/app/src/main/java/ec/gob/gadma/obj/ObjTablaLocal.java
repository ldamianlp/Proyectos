package ec.gob.gadma.obj;

import java.util.Date;

/**
 * Clase Objeto de la tabla de predios (local SLite)
 */
public class ObjTablaLocal {
    private Integer dim_codigo;
    private String dim_clave_catastral;
    private String dim_id_politico;
    private byte[] dim_imagen_pred;
    private String dim_nombre_ima_pred;
    private String dim_mimetype_ima_pred;
    private byte[] dim_dibujo_pred;
    private String dim_nombre_dib_pred;
    private String dim_mimetype_dib_pred;
    private String dim_usuario;
    private Date dim_fecha_insert;
    private String dim_estado;

    /**
     * Metodo que retorna el estado del registro
     * @return dim_estado: Estado
     */
    public String getDim_estado() {
        return dim_estado;
    }

    /**
     * Método que setea el estdo del registro
     * @param dim_estado: Estado del registro
     */
    public void setDim_estado(String dim_estado) {
        this.dim_estado = dim_estado;
    }

    /**
     * Método que obtiene el usuario que inserto el registro
     * @return dim_usuario: Usuario que registro
     */
    public String getDim_usuario() {
        return dim_usuario;
    }

    /**
     * Método que setea el usuario que registro la información
     * @param dim_usuario: Usuario que registro
     */
    public void setDim_usuario(String dim_usuario) {
        this.dim_usuario = dim_usuario;
    }

    /**
     * Método que obtiene la fecha de inserccion.
     * @return dim_fecha_insert: fecha que se inserto
     */
    public Date getDim_fecha_insert() {
        return dim_fecha_insert;
    }

    /**
     * Método que permite setear la fecha que se ingreso el registro
     * @param dim_fecha_insert: Fecha que se inserto
     */
    public void setDim_fecha_insert(Date dim_fecha_insert) {
        this.dim_fecha_insert = dim_fecha_insert;
    }

    /**
     * Método que permite obtener el codigo primario del registro
     * @return dim_codigo: Codigo primario
     */
    public Integer getDim_codigo() {
        return dim_codigo;
    }

    /**
     * Método que permite setear el codigo primario
     * @param dim_codigo: Codigo primario
     */
    public void setDim_codigo(Integer dim_codigo) {
        this.dim_codigo = dim_codigo;
    }

    /**
     * Método que permite obtener la clave catastral
     * @return dim_clave_catastral: Clave Catastral
     */
    public String getDim_clave_catastral() {
        return dim_clave_catastral;
    }

    /**
     * Método que permite setear la clave catastral
     * @param dim_clave_catastral: Clave Catastral
     */
    public void setDim_clave_catastral(String dim_clave_catastral) {
        this.dim_clave_catastral = dim_clave_catastral;
    }

    /**
     * Método que obtiene el ID Politico del registro seleccionado
     * @return dim_id_politico
     */
    public String getDim_id_politico() {
        return dim_id_politico;
    }

    /**
     * Método que permite setear el ID Politico.
     * @param dim_id_politico: ID Politico
     */
    public void setDim_id_politico(String dim_id_politico) {
        this.dim_id_politico = dim_id_politico;
    }

    /**
     * Método que permite obtener la imagen del predio
     * @return dim_imagen_pred: Imagen predial
     */
    public byte[] getDim_imagen_pred() {
        return dim_imagen_pred;
    }

    /**
     * Método que permite setear la magen predial
     * @param dim_imagen_pred: Imagen predial
     */
    public void setDim_imagen_pred(byte[] dim_imagen_pred) {
        this.dim_imagen_pred = dim_imagen_pred;
    }

    /**
     * Método que permite obtener el nombre de la imagen predial
     * @return dim_nombre_ima_pred: Nombre de la imagen predial
     */
    public String getDim_nombre_ima_pred() {
        return dim_nombre_ima_pred;
    }
    /**
     * Método que permite setear el nombre de la imagen predial
     * @param dim_nombre_ima_pred: Nombre de la imagen predial
     */
    public void setDim_nombre_ima_pred(String dim_nombre_ima_pred) {
        this.dim_nombre_ima_pred = dim_nombre_ima_pred;
    }
    /**
     * Método que permite obtener el mimetype de la imagen predial
     * @return dim_nombre_ima_pred: Mimetype de la imagen predial
     */
    public String getDim_mimetype_ima_pred() {
        return dim_mimetype_ima_pred;
    }
    /**
     * Método que permite setear el mimetype de la imagen predial
     * @param dim_mimetype_ima_pred: Mimetype de la imagen predial
     */
    public void setDim_mimetype_ima_pred(String dim_mimetype_ima_pred) {
        this.dim_mimetype_ima_pred = dim_mimetype_ima_pred;
    }
    /**
     * Método que permite obtener la imagen del levantamiento planimetrico del predio
     * @return dim_mimetype_ima_pred: Nombre de la imagen predial
     */
    public byte[] getDim_dibujo_pred() {
        return dim_dibujo_pred;
    }
    /**
     * Método que permite setera el la imagen del levantamiento planimetrico del predio
     * @param dim_dibujo_pred: Nombre de la imagen predial
     */
    public void setDim_dibujo_pred(byte[] dim_dibujo_pred) {
        this.dim_dibujo_pred = dim_dibujo_pred;
    }
    /**
     * Método que permite obtener el nombre de la imagen del levantamiento planimétrico predial
     * @return dim_nombre_ima_pred: Nombre de la imagen del levantamiento planimétrico predial
     */
    public String getDim_nombre_dib_pred() {
        return dim_nombre_dib_pred;
    }
    /**
     * Método que permite setera el nombre de la imagen del levantamiento planimétrico predial
     * @param dim_nombre_dib_pred: Nombre de la imagen del levantamiento planimétrico predial
     */
    public void setDim_nombre_dib_pred(String dim_nombre_dib_pred) {
        this.dim_nombre_dib_pred = dim_nombre_dib_pred;
    }
    /**
     * Método que permite obtener el mimetype de la imagen del levantamiento planimétrico predial
     * @return dim_nombre_ima_pred: Mimetype de la imagen del levantamiento planimétrico predial
     */
    public String getDim_mimetype_dib_pred() {
        return dim_mimetype_dib_pred;
    }

    /**
     * Método que permite setear el Mimetype de la imagen del levantamiento planimétrico predial
     * @param dim_mimetype_dib_pred: Mimetype de la imagen del levantamiento planimétrico predial
     */
    public void setDim_mimetype_dib_pred(String dim_mimetype_dib_pred) {
        this.dim_mimetype_dib_pred = dim_mimetype_dib_pred;
    }
}
