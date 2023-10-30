package ec.gob.gadma.serv;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ec.gob.gadma.connection.GadmaConnection;
import ec.gob.gadma.obj.ObjParameters;
import ec.gob.gadma.obj.ObjTablaLocal;
import ec.gob.gadma.obj.ObjAdapterDynamic;
import ec.gob.gadma.obj.ObjUser;

/**
 * Clase que permite realizar diferentes operacion con la base de datos real
 */
public class ServDatosPredio {
    /**
     * Método que permite syncronizar los datos del dispositivo movil a la base de datos real
     * @param inContext: Contexto en el cual se visualizara los errores en caso de provicarse.
     * @return result
     */
    public String getParameterSyncro(
            Context inContext,
            ObjUser inObjUser
    ){
        String result = "false";
        //llamo al ws para obtener los parametros
        HashMap<String,Object> hmParam=new HashMap<String, Object>();
        hmParam.put("inUser", inObjUser.getUserConnect());
        hmParam.put("inPaswword", inObjUser.getUserPassword());
        AsyncCallWS objAsync = new AsyncCallWS(inContext,"getParametersSyncro",hmParam);


        try{
            String param = objAsync.execute().get();
            if(!param.contains("ERROR")) {
                String v_Host = null;
                String v_Port = null;
                String v_SID = null;
                String v_User = null;
                String v_Password = null;

                JSONArray alParameters;
                JSONObject objJson = new JSONObject(param);
                if (objJson.getString("totalCount").equalsIgnoreCase("1")) {
                    //JSONObject hmDatosPredio = obj.getJSONObject("parameters");
                    if (objJson.getString("parameters") != null && !objJson.getString("parameters").equalsIgnoreCase("")) {
                        alParameters = objJson.getJSONArray("parameters");

                        for (int current = 0; current < alParameters.length(); current++) {
                            Object spa_codigo = alParameters.getJSONObject(current).get("SPA_CODIGO");
                            Object spa_valor = alParameters.getJSONObject(current).get("SPA_VALOR");
                            if (spa_codigo != null)
                                switch (spa_codigo.toString()) {
                                    case "SERVER_SDE":
                                        v_Host = spa_valor.toString();
                                        break;
                                    case "PORT_SDE":
                                        v_Port = spa_valor.toString();
                                        break;
                                    case "NAME_SID_SDE":
                                        v_SID = spa_valor.toString();
                                        break;
                                    case "NAME_USER_SDE":
                                        v_User = spa_valor.toString();
                                        break;
                                    case "PASSWORD_USER_SDE":
                                        v_Password = spa_valor.toString();
                                        break;
                                    default:
                                        break;
                                }
                        }
                    }
                } else {
                    return "ERROR: No existe datos de parámetros para poder syncronizar la información";
                }
                ObjParameters objParameters = ObjParameters.getInstance();
                objParameters.setNAME_SID_SDE(v_SID);
                objParameters.setNAME_USER_SDE(v_User);
                objParameters.setPASSWORD_USER_SDE(v_Password);
                objParameters.setPORT_SDE(v_Port);
                objParameters.setSERVER_SDE(v_Host);


                result = "true";
            }else{
                return param;
            }
        }catch (InterruptedException e) {
            smsError(inContext,e);
            result = e.getMessage();
        } catch (ExecutionException e) {
            smsError(inContext,e);
            result = e.getMessage();
        }catch (JSONException e) {
            smsError(inContext,e);
            result = e.getMessage();
        }  finally {

        }
        return result;
    }
    /**
     * Método que permite syncronizar los datos del dispositivo movil a la base de datos real
     * @param datos: Listado de Predios a syncronizar
     * @param inContext: Contexto en el cual se visualizara los errores en caso de provicarse.
     * @return result
     */
    public String syncroDatos(
            ArrayList<ObjAdapterDynamic> datos,
            Context inContext,
            ObjUser inObjUser
    ){
        String tipoImagenFrontalPredio="1";
        String tipoLevantamientoPlanimetrico="2";
        String result = "false";
        //llamo al ws para obtener los parametros
       /* HashMap<String,Object> hmParam=new HashMap<String, Object>();
        hmParam.put("inUser", inObjUser.getUserConnect());
        hmParam.put("inPaswword", inObjUser.getUserPassword());
        AsyncCallWS objAsync = new AsyncCallWS(inContext,"getParametersSyncro",hmParam);
*/


        GadmaConnection conObj = new GadmaConnection();
        Connection con = null;
        CallableStatement tmpStatementRevision = null;
        CallableStatement tmpStatementFrontal = null;
        CallableStatement tmpStatementLevantamiento = null;
        DataBaseSQLite db = new DataBaseSQLite(inContext);
        try{
           // String param = objAsync.execute().get();
            //if(!param.contains("ERROR")) {
            ObjParameters objParam = ObjParameters.getInstance();
            if(objParam!=null && objParam.getNAME_USER_SDE() !=null){
                String v_Host = objParam.getSERVER_SDE();// null;
                String v_Port = objParam.getPORT_SDE();//null;
                String v_SID = objParam.getNAME_SID_SDE();//null;
                String v_User = objParam.getNAME_USER_SDE();//null;
                String v_Password = objParam.getPASSWORD_USER_SDE();//null;

                v_Password = decodeString(v_Password);
                con = conObj.getConnection(v_Host, v_Port, v_SID, v_User, v_Password);
                if (con == null){
                    result = "false";
                    throw new Exception("Driver no compatible. Por favor consulta con el administrador del Sistema");
                }else {
                    con.setAutoCommit(false);
                    for (ObjAdapterDynamic obj : datos) {
                        ObjTablaLocal ObjTablaLocal = db.getDatos(Integer.valueOf(obj.getId()), inContext);
                        List<ObjTablaLocal> shops = db.getAllDatosByClaveAndIdPoli(
                                ObjTablaLocal.getDim_clave_catastral(),
                                ObjTablaLocal.getDim_id_politico(), inContext);
                        for (ObjTablaLocal objLocal : shops) {
                            tmpStatementRevision = con.prepareCall("{ ? = call SDE.PKG_SYNCRO_IMAGEN_PREDIO.F_GET_REVISION_BY_PREDIO(?) }");
                            tmpStatementRevision.registerOutParameter(1, Types.NUMERIC);
                            tmpStatementRevision.setString(2, objLocal.getDim_id_politico() + objLocal.getDim_clave_catastral());
                            tmpStatementRevision.execute();
                            BigDecimal revision = tmpStatementRevision.getBigDecimal(1);
                            tmpStatementRevision.close();


                            tmpStatementFrontal = con.prepareCall("{ ? = call SDE.PKG_SYNCRO_IMAGEN_PREDIO.F_INSERT_SYNCRO_IMAGEN_PREDIO(?,?,?,?,?,?,?) }");
                            tmpStatementFrontal.registerOutParameter(1, Types.VARCHAR);
                            tmpStatementFrontal.setString(2, objLocal.getDim_id_politico() + objLocal.getDim_clave_catastral());
                            tmpStatementFrontal.setBigDecimal(3, revision);
                            tmpStatementFrontal.setInt(4, 1);  //tipo imagen frontal del predio
                            tmpStatementFrontal.setBytes(5, objLocal.getDim_imagen_pred());
                            String nombre_foto = objLocal.getDim_nombre_ima_pred().split("\\.")[0] + "_" + tipoImagenFrontalPredio + "." + objLocal.getDim_nombre_ima_pred().split("\\.")[1];
                            tmpStatementFrontal.setString(6, nombre_foto);
                            tmpStatementFrontal.setString(7, objLocal.getDim_mimetype_ima_pred());
                            tmpStatementFrontal.setString(8, objLocal.getDim_usuario());
                            tmpStatementFrontal.execute();
                            String retornoFrontal = tmpStatementFrontal.getString(1);
                            tmpStatementFrontal.close();

                            tmpStatementLevantamiento = con.prepareCall("{ ? = call SDE.PKG_SYNCRO_IMAGEN_PREDIO.F_INSERT_SYNCRO_IMAGEN_PREDIO(?,?,?,?,?,?,?) }");
                            tmpStatementLevantamiento.registerOutParameter(1, Types.VARCHAR);
                            tmpStatementLevantamiento.setString(2, objLocal.getDim_id_politico() + objLocal.getDim_clave_catastral());
                            tmpStatementLevantamiento.setBigDecimal(3, revision);
                            tmpStatementLevantamiento.setInt(4, 2);  //tipo imagen levatamiento planimetrico
                            tmpStatementLevantamiento.setBytes(5, objLocal.getDim_dibujo_pred());
                            nombre_foto = objLocal.getDim_nombre_dib_pred().split("\\.")[0] + "_" + tipoLevantamientoPlanimetrico + "." + objLocal.getDim_nombre_dib_pred().split("\\.")[1];
                            tmpStatementLevantamiento.setString(6, nombre_foto);
                            tmpStatementLevantamiento.setString(7, objLocal.getDim_mimetype_dib_pred());
                            tmpStatementLevantamiento.setString(8, objLocal.getDim_usuario());
                            tmpStatementLevantamiento.execute();
                            String retornoLevantamiento = tmpStatementLevantamiento.getString(1);
                            tmpStatementLevantamiento.close();

                            objLocal.setDim_estado("M");
                            db.updateEstadoLocal(objLocal, inContext);
                            //result = "true";
                        }
                    }
                    con.commit();
                    for (ObjAdapterDynamic obj : datos) {
                        ObjTablaLocal ObjTablaLocal = db.getDatos(Integer.valueOf(obj.getId()), inContext);
                        db.deleteDato(ObjTablaLocal, inContext);
                    }
                    result = "true";
                }
            }
        }catch (SQLException e ) {
            smsError(inContext,e);
            result = e.getMessage();
            if (tmpStatementRevision != null) {
                try {
                    tmpStatementRevision.close();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
            if (tmpStatementFrontal != null) {
                try {
                    tmpStatementFrontal.close();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
            if (tmpStatementLevantamiento != null) {
                try {
                    tmpStatementLevantamiento.close();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.rollback();
                    con.close();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
            //e1.printStackTrace();
        }
        /*desde aqui añado para verificar el driver*/
        catch (Exception  e){
            smsError(inContext,e);
            result = e.getMessage();
            if (tmpStatementRevision != null) {
                try {
                    tmpStatementRevision.close();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
            if (tmpStatementFrontal != null) {
                try {
                    tmpStatementFrontal.close();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
            if (tmpStatementLevantamiento != null) {
                try {
                    tmpStatementLevantamiento.close();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.rollback();
                    con.close();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
        }/*hasta aqui añado para verificar el driver*/
        finally {
            if (tmpStatementRevision != null) {
                try {
                    tmpStatementRevision.close();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
            if (tmpStatementFrontal != null) {
                try {
                    tmpStatementFrontal.close();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
            if (tmpStatementLevantamiento != null) {
                try {
                    tmpStatementLevantamiento.close();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.rollback();
                    con.close();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * Método que permite syncronizar los datos del dispositivo movil a la base de datos real
     * @param datos: Listado de Predios a syncronizar
     * @param inContext: Contexto en el cual se visualizara los errores en caso de provicarse.
     * @return result
     */
    public String eliminarSeleccionado(
            ArrayList<ObjAdapterDynamic> datos,
            Context inContext,
            ObjUser inObjUser
    ){
        String result = "false";
        DataBaseSQLite db = new DataBaseSQLite(inContext);

        for (ObjAdapterDynamic obj : datos) {
            ObjTablaLocal ObjTablaLocal = db.getDatos(Integer.valueOf(obj.getId()), inContext);
            db.deleteDato(ObjTablaLocal, inContext);
        }
        result = "true";

        return result;
    }
    /**
     * Metodo que permite visualizar el mensaje de error.
     * @param inContext: Contexto en el cual se visualizara el error
     * @param e: Excepcion de tipo SQLException
     */
    private void smsError(Context inContext,Exception e){
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(inContext);
        dlgAlert.setMessage(e.getMessage());
        dlgAlert.setTitle("Mensaje de Error...");
        dlgAlert.setCancelable(false);
        dlgAlert.setPositiveButton(android.R.string.ok, null);
        dlgAlert.create().show();
    }
    public static String qdescifrarBase64(String cadena){
        String source = cadena;
        byte[] byteArray;
        String valor="";
        try {
            byteArray = source.getBytes("UTF-16");
            valor = new String(Base64.decode(Base64.encode(byteArray,
                    Base64.DEFAULT), Base64.DEFAULT));
            return valor;
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return valor;
        }

    }
    private String decodeString(String encoded) {
        byte[] dataDec = Base64.decode(encoded, Base64.DEFAULT);
        String decodedString = "";
        try {

            decodedString = new String(dataDec, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

        }catch (Exception e){
            e.printStackTrace();
        }
        finally {

            return decodedString;
        }
    }

    /**
     * Método que permite syncronizar los datos del dispositivo movil a la base de datos real
     * @param datos: Listado de Predios a syncronizar
     * @param inContext: Contexto en el cual se visualizara los errores en caso de provicarse.
     * @return result
     */
    public String syncroDatosWs(
            ArrayList<ObjAdapterDynamic> datos,
            Context inContext,
            ObjUser inObjUser
    ){
        String tipoImagenFrontalPredio="1";
        String tipoLevantamientoPlanimetrico="2";
        String result = "false";
        DataBaseSQLite db = new DataBaseSQLite(inContext);

        for (ObjAdapterDynamic obj : datos) {

            ObjTablaLocal ObjTablaLocal = db.getDatos(Integer.valueOf(obj.getId()), inContext);
            List<ObjTablaLocal> shops = db.getAllDatosByClaveAndIdPoli(
                    ObjTablaLocal.getDim_clave_catastral(),
                    ObjTablaLocal.getDim_id_politico(), inContext);

            for (ObjTablaLocal objLocal : shops) {

                try {
                    HashMap<String,Object> hmParam=new HashMap<String, Object>();
                    hmParam.put("IN_SIP_PREDIO",objLocal.getDim_id_politico() + objLocal.getDim_clave_catastral());
                    AsyncCallWS objAsync = new AsyncCallWS(inContext,"getNumRevision",hmParam);
                    String resultadoRevision = objAsync.execute().get();
                    Integer numRevision = new Integer(resultadoRevision);


                    hmParam=new HashMap<String, Object>();
                    hmParam.put("IN_SIP_PREDIO", objLocal.getDim_id_politico() + objLocal.getDim_clave_catastral());
                    hmParam.put("IN_SIP_REVISION",numRevision);
                    hmParam.put("IN_PTI_CODIGO",1);//tipo imagen frontal del predio
                    hmParam.put("IN_SIP_IMAGEN",objLocal.getDim_imagen_pred());
                    String nombre_foto = objLocal.getDim_nombre_ima_pred().split("\\.")[0] + "_" + tipoImagenFrontalPredio + "." + objLocal.getDim_nombre_ima_pred().split("\\.")[1];
                    hmParam.put("IN_SIP_NOMBRE_IMA",nombre_foto);
                    hmParam.put("IN_SIP_MIMETYPE_IMA",objLocal.getDim_mimetype_ima_pred());
                    hmParam.put("IN_SIP_USUARIO_INSERT",objLocal.getDim_usuario());
                    objAsync = new AsyncCallWS(inContext,"subirDatosWs",hmParam);

                    String valido = objAsync.execute().get();
                    if (valido != null && valido.equalsIgnoreCase("true")) {
                        hmParam=new HashMap<String, Object>();
                        hmParam.put("IN_SIP_PREDIO", objLocal.getDim_id_politico() + objLocal.getDim_clave_catastral());
                        hmParam.put("IN_SIP_REVISION",numRevision);
                        hmParam.put("IN_PTI_CODIGO",2);//tipo imagen levatamiento planimetrico
                        hmParam.put("IN_SIP_IMAGEN",objLocal.getDim_dibujo_pred());
                        nombre_foto = objLocal.getDim_nombre_dib_pred().split("\\.")[0] + "_" + tipoLevantamientoPlanimetrico + "." + objLocal.getDim_nombre_dib_pred().split("\\.")[1];
                        hmParam.put("IN_SIP_NOMBRE_IMA",nombre_foto);
                        hmParam.put("IN_SIP_MIMETYPE_IMA",objLocal.getDim_mimetype_dib_pred());
                        hmParam.put("IN_SIP_USUARIO_INSERT",objLocal.getDim_usuario());
                        objAsync = new AsyncCallWS(inContext,"subirDatosWs",hmParam);

                        String valido2 = objAsync.execute().get();
                        if (valido2 != null && valido2.equalsIgnoreCase("true")) {

                            //Actualiazo el estado del registro local
                            objLocal.setDim_estado("M");
                            db.updateEstadoLocal(objLocal, inContext);
                            result = "true";
                        }else {
                            result = valido;
                            smsErrorString(inContext,valido);
                        }

                    }else {
                        result = valido;
                        smsErrorString(inContext,valido);
                    }



                } catch (InterruptedException e) {
                    smsError(inContext,e);
                    result = e.getMessage();
                } catch (ExecutionException e) {
                    smsError(inContext,e);
                    result = e.getMessage();
                } catch (Exception e) {
                    smsError(inContext,e);
                    result = e.getMessage();
                }







            }


        }

        return result;
    }
    /*metodo pendiente de revizar*/
    public String syncroDatosWsRest(
            ArrayList<ObjAdapterDynamic> datos,
            Context inContext,
            ObjUser inObjUser
    ){
        String tipoImagenFrontalPredio="1";
        String tipoLevantamientoPlanimetrico="2";
        String result = "false";
        DataBaseSQLite db = new DataBaseSQLite(inContext);

        for (ObjAdapterDynamic obj : datos) {

            ObjTablaLocal ObjTablaLocal = db.getDatos(Integer.valueOf(obj.getId()), inContext);
            List<ObjTablaLocal> shops = db.getAllDatosByClaveAndIdPoli(
                    ObjTablaLocal.getDim_clave_catastral(),
                    ObjTablaLocal.getDim_id_politico(), inContext);

            for (ObjTablaLocal objLocal : shops) {

                try {
                    HashMap<String,Object> hmParam=new HashMap<String, Object>();
                    hmParam.put("IN_SIP_PREDIO",objLocal.getDim_id_politico() + objLocal.getDim_clave_catastral());
                    AsyncCallWS objAsync = new AsyncCallWS(inContext,"getNumRevision",hmParam);
                    String resultadoRevision = objAsync.execute().get();
                    Integer numRevision = new Integer(resultadoRevision);


                    hmParam=new HashMap<String, Object>();
                    hmParam.put("IN_SIP_PREDIO", objLocal.getDim_id_politico() + objLocal.getDim_clave_catastral());
                    hmParam.put("IN_SIP_REVISION",numRevision);
                    hmParam.put("IN_PTI_CODIGO",1);//tipo imagen frontal del predio

                    String imageString = Base64.encodeToString(objLocal.getDim_imagen_pred(), Base64.DEFAULT);

                    hmParam.put("IN_SIP_IMAGEN",imageString);//objLocal.getDim_imagen_pred());
                    String nombre_foto = objLocal.getDim_nombre_ima_pred().split("\\.")[0] + "_" + tipoImagenFrontalPredio + "." + objLocal.getDim_nombre_ima_pred().split("\\.")[1];
                    hmParam.put("IN_SIP_NOMBRE_IMA",nombre_foto);
                    hmParam.put("IN_SIP_MIMETYPE_IMA",objLocal.getDim_mimetype_ima_pred());
                    hmParam.put("IN_SIP_USUARIO_INSERT",objLocal.getDim_usuario());

                    JSONObject jsonObjEnvio = new JSONObject(hmParam);

                    /*JSONObject envio = new JSONObject();
                    envio.put("IN_SIP_IMAGEN",objLocal.getDim_imagen_pred());*/
                    AsyncCallWSRest ResultadoWSREst = new AsyncCallWSRest(inContext,"syncronizar","POST",jsonObjEnvio);

                    String valido = ResultadoWSREst.execute().get();
                    if (valido != null && valido.equalsIgnoreCase("true")) {
                        hmParam=new HashMap<String, Object>();
                        hmParam.put("IN_SIP_PREDIO", objLocal.getDim_id_politico() + objLocal.getDim_clave_catastral());
                        hmParam.put("IN_SIP_REVISION",numRevision);
                        hmParam.put("IN_PTI_CODIGO",2);//tipo imagen levatamiento planimetrico

                        String imageString2 = Base64.encodeToString(objLocal.getDim_dibujo_pred(), Base64.DEFAULT);

                        hmParam.put("IN_SIP_IMAGEN",imageString2);//objLocal.getDim_dibujo_pred());
                        nombre_foto = objLocal.getDim_nombre_dib_pred().split("\\.")[0] + "_" + tipoLevantamientoPlanimetrico + "." + objLocal.getDim_nombre_dib_pred().split("\\.")[1];
                        hmParam.put("IN_SIP_NOMBRE_IMA",nombre_foto);
                        hmParam.put("IN_SIP_MIMETYPE_IMA",objLocal.getDim_mimetype_dib_pred());
                        hmParam.put("IN_SIP_USUARIO_INSERT",objLocal.getDim_usuario());

                        JSONObject jsonObjEnvio2 = new JSONObject(hmParam);
                        AsyncCallWSRest ResultadoWSREst2 = new AsyncCallWSRest(inContext,"syncronizar","POST",jsonObjEnvio2);

                        String valido2 = ResultadoWSREst2.execute().get();
                        if (valido2 != null && valido2.equalsIgnoreCase("true")) {

                            //Actualiazo el estado del registro local
                            objLocal.setDim_estado("M");
                            db.updateEstadoLocal(objLocal, inContext);
                            result = "true";
                        }else {
                            result = valido;
                            smsErrorString(inContext,valido);
                        }

                    }else {
                        result = valido;
                        smsErrorString(inContext,valido);
                    }



                } catch (InterruptedException e) {
                    smsError(inContext,e);
                    result = e.getMessage();
                } catch (ExecutionException e) {
                    smsError(inContext,e);
                    result = e.getMessage();
                } catch (Exception e) {
                    smsError(inContext,e);
                    result = e.getMessage();
                }







            }


        }

        return result;
    }
    private void smsErrorString(Context inContext,String e){
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(inContext);
        dlgAlert.setMessage(e);
        dlgAlert.setTitle("Mensaje de Error...");
        dlgAlert.setCancelable(false);
        dlgAlert.setPositiveButton(android.R.string.ok, null);
        dlgAlert.create().show();
    }
}
