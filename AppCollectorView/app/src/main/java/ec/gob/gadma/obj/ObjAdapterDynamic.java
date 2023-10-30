package ec.gob.gadma.obj;

/**
 * Clase de Objeto para crear un Adapter dynamic
 */
public class ObjAdapterDynamic {
    private String id;
    private String value;

    /**
     * Constructor de la clase ObjAdapterDynamic
     * @param id: Id principal
     * @param value: Valor de Visualización
     */
    public ObjAdapterDynamic(String id, String value) {
        this.id = id;
        this.value = value;
    }

    /**
     * Método que obtiene el ID del registro
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Metodo que perite setear el ID del registro
     * @param id: ID de registro
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Método que permite obtener el valor de visualizacion
     * @return value
     */
    public String getValue() {
        return value;
    }

    /**
     * Método que permite setear el valor de visualización.
     * @param value: Valor de visualización.
     */
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ObjAdapterDynamic){
            ObjAdapterDynamic c = (ObjAdapterDynamic )obj;
            if(c.getValue().equals(value) && c.getId()==id ) return true;
        }
        return false;
    }
}
