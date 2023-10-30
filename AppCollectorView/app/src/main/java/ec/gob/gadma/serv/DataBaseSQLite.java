package ec.gob.gadma.serv;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import ec.gob.gadma.obj.ObjTablaLocal;
import ec.gob.gadma.obj.ObjAdapterDynamic;

/**
 * Clase generada para establecer el proceso de coneccion y operaciones con la base de datos SQLite.
 */
public class DataBaseSQLite extends SQLiteOpenHelper {
    // Version de la base de datos
    private static final int DATABASE_VERSION = 1;
    // Nombre de la Base de Datos
    private static final String DATABASE_NAME = "DEMO_DB";
    //Nombre de la tabla
    private String tabla = "TBL_IMAGENES";
    //Constructor de la clase
    public DataBaseSQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Sentecia sql para crear la tabla
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "+tabla+" (\n" +
                "DIM_CODIGO INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ,\n" +
                "DIM_CLAVE_CATASTRAL VARCHAR NOT NULL ,\n" +
                "DIM_ID_POLITICO VARCHAR,\n" +
                "DIM_IMAGEN_PRED BLOB,\n" +
                "DIM_NOMBRE_IMA_PRED VARCHAR,\n" +
                "DIM_MIMETYPE_IMA_PRED VARCHAR,\n" +
                "DIM_DIBUJO_PRED BLOB,\n" +
                "DIM_NOMBRE_DIB_PRED VARCHAR,\n" +
                "DIM_MIMETYPE_DIB_PRED VARCHAR,\n" +
                "DIM_USUARIO VARCHAR NOT NULL ,\n" +
                "DIM_FECHA_INSERT DATETIME," +
                "DIM_ESTADO VARCHAR(1) NOT NULL)";

        // Ejecuta la sentencia sql
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Elimina la tabla en caso de existir
        db.execSQL("DROP TABLE IF EXISTS "+tabla+"");
        // Ejecuta la sentencia sql
        this.onCreate(db);
    }

    /**
     * Metodo que permite visualizar una alerta de error en caso de que exista un inconveniente
     * al realizar una operacion en la base de datos SQLite
     * @param inContext : Especifica el contexto em el cual se va a visualizar el Mensaje de Error.
     * @param e: Especifica la excepcion por la cual se genero el error.
     */
    private void smsError(Context inContext,Exception e){
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(inContext);
        dlgAlert.setMessage(e.getMessage());
        dlgAlert.setTitle("Mensaje de Error...");
        dlgAlert.setCancelable(false);
        dlgAlert.setPositiveButton(android.R.string.ok, null);
        dlgAlert.create().show();
    }

    /**
     * Metodo que permite registrar las imagenes del predio
     * @param obj: Objeto con la informacion a ingresar
     * @param inTipo: Especifica el tipo si es: 1 - INSERT, 2 - UPDATE
     * @param inContext: Contexto en el cual se va a visualizar el mensaje de error.
     */
    public void addImagen(
            ObjTablaLocal obj,
            int inTipo,
            Context inContext
    ) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            //values.put("DIM_CODIGO",obj.getDim_codigo());
            values.put("DIM_CLAVE_CATASTRAL", obj.getDim_clave_catastral());
            values.put("DIM_ID_POLITICO", obj.getDim_id_politico());
            if(inTipo==1){
                values.put("DIM_IMAGEN_PRED", obj.getDim_imagen_pred());
                values.put("DIM_NOMBRE_IMA_PRED", obj.getDim_nombre_ima_pred());
                values.put("DIM_MIMETYPE_IMA_PRED", obj.getDim_mimetype_ima_pred());
            }else{
                values.put("DIM_DIBUJO_PRED", obj.getDim_dibujo_pred());
                values.put("DIM_NOMBRE_DIB_PRED", obj.getDim_nombre_dib_pred());
                values.put("DIM_MIMETYPE_DIB_PRED", obj.getDim_mimetype_dib_pred());
            }
            values.put("DIM_USUARIO", obj.getDim_usuario());
            values.put("DIM_FECHA_INSERT", (obj.getDim_fecha_insert() != null) ? obj.getDim_fecha_insert().toString() : null);
            values.put("DIM_ESTADO", obj.getDim_estado());

            int total = getPrediosCountByClaveAndIDpoli(obj.getDim_clave_catastral(),obj.getDim_id_politico(),inContext);
            if(total==0){
                db.insert(tabla, null, values);
            }else
                db.update(tabla, values, "DIM_CLAVE_CATASTRAL" + " = ? and DIM_ID_POLITICO = ?" ,new String[]{obj.getDim_clave_catastral(),obj.getDim_id_politico()});
            db.close();
        }catch (Exception e){
            smsError(inContext,e);
        }
    }
    /**
     * Metodo que permite contar el numero de registros que existe con respecto al predio.
     * @param inClaveCatastral: Calave catastral.
     * @param inIDpolitico: ID Politico en caso de ser un predio rustico.
     * @param inContext: Contexto en el cual se visualizara el error en caso de existir.
     * @return out_total: total de registros.
     */
    private int getPrediosCountByClaveAndIDpoli(
            String inClaveCatastral,
            String inIDpolitico,
            Context inContext
    ){
        int out_total=0;
        try {
            String countQuery = "SELECT * FROM " + tabla + " where DIM_CLAVE_CATASTRAL = '" + inClaveCatastral +
                    "' and DIM_ID_POLITICO='" + inIDpolitico + "'"; //AND DIM_ESTADO = 'C'";

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(countQuery, null);
            out_total = cursor.getCount();
            cursor.close();
        }catch (Exception e){
            smsError(inContext,e);
        }
        return out_total;
    }

    /**
     * Metodo que devuelve los datos segun su clave primaria.
     * @param inDim_codigo: Especifica el codigo del registro. Clave Primaria
     * @param inContext: Contexto en el cual se visualizara el error en caso de existir
     * @return ObjTablaLocal: Objeto de datos locales.
     */
    public ObjTablaLocal getDatos(
            int inDim_codigo,
            Context inContext
    ) {
        ObjTablaLocal objDatos = null;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(tabla, new String[]{"DIM_CODIGO",
                            "DIM_CLAVE_CATASTRAL",
                            "DIM_ID_POLITICO",
                            "DIM_IMAGEN_PRED",
                            "DIM_NOMBRE_IMA_PRED",
                            "DIM_MIMETYPE_IMA_PRED",
                            "DIM_DIBUJO_PRED",
                            "DIM_NOMBRE_DIB_PRED",
                            "DIM_MIMETYPE_DIB_PRED",
                            "DIM_USUARIO",
                            "DIM_FECHA_INSERT",
                            "DIM_ESTADO"}, "DIM_CODIGO" + "=?",
                    new String[]{String.valueOf(inDim_codigo)}, null, null, null, null);
            if (cursor != null)
                cursor.moveToFirst();
            objDatos = new ObjTablaLocal();
            objDatos.setDim_codigo(cursor.getInt(0));
            objDatos.setDim_clave_catastral(cursor.getString(1));
            objDatos.setDim_id_politico(cursor.getString(2));
            objDatos.setDim_imagen_pred(cursor.getBlob(3));
            objDatos.setDim_nombre_ima_pred(cursor.getString(4));
            objDatos.setDim_mimetype_ima_pred(cursor.getString(5));
            objDatos.setDim_dibujo_pred(cursor.getBlob(6));
            objDatos.setDim_nombre_dib_pred(cursor.getString(7));
            objDatos.setDim_mimetype_dib_pred(cursor.getString(8));
            objDatos.setDim_usuario(cursor.getString(9));
            //objDatos.setDim_fecha_insert(cursor.getString(10));
            objDatos.setDim_estado(cursor.getString(11));
            // return objDatos

        }catch (Exception e){
            smsError(inContext,e);
        }
        return objDatos;
    }

    /**
     * Método que permite eliminar todos los datos de la tabla
     * @param inContext: Contexto en el cual se visualizara los errores en el caso de existir
     */
    public void deleteAllDatos(
            Context inContext
    ) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(tabla, "",
                    new String[]{});
            db.close();
        }catch (Exception e){
            smsError(inContext,e);
        }
    }

    /**
     * Método que permite eliminar un registro en especifico
     * @param obj: Objeto ObjTablaLocal
     * @param inContext: Contexto en el cual se visualizara el error en caso de existir
     */
    public void deleteDato(
            ObjTablaLocal obj,
            Context inContext
    ) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(tabla, "DIM_CODIGO = ? ",
                    new String[]{obj.getDim_codigo().toString()});
            db.close();
        }catch (Exception e){
            smsError(inContext,e);
        }
    }

    /**
     * Método que permite Obtener todos los registros que estan almacenado en la tabla local.
     * @param inClaveCatastral: Clave Catastral
     * @param inIDpolitico: ID Politico
     * @param inContext: Contexto en el cual se visualizara el error en caso de existir
     * @return List<ObjTablaLocal>
     */
    @SuppressLint("Range")
    public List<ObjTablaLocal> getAllDatosByClaveAndIdPoli(
            String inClaveCatastral,
            String inIDpolitico,
            Context inContext
    ) {
        List<ObjTablaLocal> lstObjDatos = null;
        Cursor cursorDatos=null;
        try {
            // Select All Query
            String selectQuery = "SELECT * FROM " + tabla + " where DIM_CLAVE_CATASTRAL = '" + inClaveCatastral +
                    "' and DIM_ID_POLITICO='" + inIDpolitico + "'";

            SQLiteDatabase db = this.getReadableDatabase();
            cursorDatos = db.rawQuery(selectQuery, null);
            if(cursorDatos!=null && cursorDatos.getCount() > 0) {
                // looping through all rows and adding to list
                if (cursorDatos.moveToFirst()) {
                    lstObjDatos = new ArrayList<>();
//                    cursorDatos.moveToPosition(0);
//                    String a = cursorDatos.getColumnName(0);
                    do {
                        ObjTablaLocal objDatos = new ObjTablaLocal();
//                        String a1 = cursorDatos.getColumnName(0);
//                        String b = cursorDatos.getString(cursorDatos.getColumnIndex("DIM_CLAVE_CATASTRAL"));

                        Integer  valor = cursorDatos.getInt(cursorDatos.getColumnIndex("DIM_CODIGO"));
                        objDatos.setDim_codigo(cursorDatos.getInt(cursorDatos.getColumnIndex("DIM_CODIGO")));//1));
                        objDatos.setDim_clave_catastral(cursorDatos.getString(cursorDatos.getColumnIndex("DIM_CLAVE_CATASTRAL")));//1));
                        objDatos.setDim_id_politico(cursorDatos.getString(cursorDatos.getColumnIndex("DIM_ID_POLITICO")));//2));
                        objDatos.setDim_imagen_pred(cursorDatos.getBlob(cursorDatos.getColumnIndex("DIM_IMAGEN_PRED")));//3));
                        objDatos.setDim_nombre_ima_pred(cursorDatos.getString(cursorDatos.getColumnIndex("DIM_NOMBRE_IMA_PRED")));//4));
                        objDatos.setDim_mimetype_ima_pred(cursorDatos.getString(cursorDatos.getColumnIndex("DIM_MIMETYPE_IMA_PRED")));//5));
                        objDatos.setDim_dibujo_pred(cursorDatos.getBlob(cursorDatos.getColumnIndex("DIM_DIBUJO_PRED")));//6));
                        objDatos.setDim_nombre_dib_pred(cursorDatos.getString(cursorDatos.getColumnIndex("DIM_NOMBRE_DIB_PRED")));//7));
                        objDatos.setDim_mimetype_dib_pred(cursorDatos.getString(cursorDatos.getColumnIndex("DIM_MIMETYPE_DIB_PRED")));//8));
                        objDatos.setDim_usuario(cursorDatos.getString(cursorDatos.getColumnIndex("DIM_USUARIO")));//9));
                        // Adding objDatos to list
                        lstObjDatos.add(objDatos);
                    } while (cursorDatos.moveToNext());
                    if(cursorDatos!=null && !cursorDatos.isClosed())//****************
                        cursorDatos.close();//****************
                }
            }
            // return objDatos list
        }catch (Exception e){
            if(cursorDatos!=null && !cursorDatos.isClosed())
                cursorDatos.close();
            smsError(inContext,e);
        }
        return lstObjDatos;
    }

    /**
     * Método que permite obtener todos los registros que tengo en la taba local
     * @param inContext
     * @return
     */
    public ArrayList<ObjAdapterDynamic> getAllDatosLocales(
            Context inContext
    ) {
        ArrayList<ObjAdapterDynamic> alIdPolitico=null;
        try {
            alIdPolitico = new ArrayList<>();
            // Select All Query
            String selectQuery = "SELECT DIM_CODIGO,'ID POL: '||DIM_ID_POLITICO||' CLAVE: '||DIM_CLAVE_CATASTRAL DISPLAY_FIELD FROM " + tabla +
                    "\n where DIM_ESTADO = 'C'";

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    alIdPolitico.add(new ObjAdapterDynamic(cursor.getString(0), cursor.getString(1)));
                } while (cursor.moveToNext());
            }

        }catch (Exception e){
            smsError(inContext,e);
        }
        // return objDatos list
        return alIdPolitico;
    }

    /**
     * Método que permite actualizar el estado de los registros con la condicion de que clave principal sea igual al parametro del Objeto enviado
     * @param obj: Objeto ObjTablaLocal
     * @param inContext: Contexto en el cual se visualizara los errores en caso de provocarse
     * @return Integer
     */
    public Integer updateEstadoLocal(
            ObjTablaLocal obj,
            Context inContext
    ) {
        Integer filas = null;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("DIM_ESTADO", obj.getDim_estado());
            filas = db.update(tabla, values, "DIM_CODIGO" + " = ?",
                    new String[]{String.valueOf(obj.getDim_codigo())});

        }catch (Exception e){
            smsError(inContext,e);
        }
        return filas;
    }
}
