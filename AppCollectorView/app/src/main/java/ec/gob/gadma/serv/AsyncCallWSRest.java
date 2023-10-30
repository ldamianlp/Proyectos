package ec.gob.gadma.serv;

import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONObject;
import org.ksoap2.SoapFault;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;

import ec.gob.gadma.obj.ObjUser;
import ec.gob.gadma.obj.Util;

public class AsyncCallWSRest extends AsyncTask<String, Void, String>  {
    private String v_METHOD_NAME;
    private String v_TYPE;
    private JSONObject v_parameters;
    private Context v_Context;
    private String v_excepcion;

    public AsyncCallWSRest(Context inContext, String inMETHOD_NAME, String inTipo,JSONObject inParameters){
        this.v_Context = inContext;
        this.v_METHOD_NAME = inMETHOD_NAME;
        this.v_parameters = inParameters;
        this.v_TYPE = inTipo;
    }

    @Override
    protected String doInBackground(String... params) {
        String response = null;
        ObjUser objUser = ObjUser.getInstance();
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
       /* String param1 = params[0];
        String param2 = params[1];*/
        try{
            String URL = Util.getProperty("URL_WS_SYNCRO",v_Context);
            String urlWs = URL+v_METHOD_NAME;                    //publico POST
            //String urlWs = "http://10.10.4.105:8080/proyectoRest/WsPrivado/json/Dam,ian";     //con autentificacion GET
            //String urlWs = "http://10.10.4.105:8080/proyectoRest/WsPublico/html/Luis/Damian"; //sin autentificacion GET
            String metodo = v_TYPE;

            String usuario = objUser.getUserConnect();
            String pwd = objUser.getUserPassword();
            // Basic Authentication header value

            String baHeader = usuario + ":" + pwd;

            baHeader = org.kobjects.base64.Base64.encode(baHeader.getBytes());   //Base64.encodeBase64String(baHeader.getBytes());

            if(metodo.equalsIgnoreCase("GET")) {
                HttpGet httpGet = new HttpGet(urlWs);
                String text = null;

                    httpGet.setHeader("Authorization", "Basic " + baHeader);
                    HttpResponse responsehttp = httpClient.execute(httpGet, localContext);
                    HttpEntity entity = responsehttp.getEntity();
                    text = getASCIIContentFromEntity(entity);

                return text;
            }else{
                HttpPost httpPost = new HttpPost(urlWs);
                String text = null;

                    httpPost.setHeader("Authorization", "Basic " + baHeader);

                    httpPost.setHeader("Accept", "application/json");
                    httpPost.setHeader("Content-type", "application/json");

                    String envio = v_parameters.toString();
                    StringEntity seJsonEnvio = new StringEntity(envio);
                    httpPost.setEntity(seJsonEnvio);

                    HttpResponse responsehttp = httpClient.execute(httpPost, localContext);
                    HttpEntity entity = responsehttp.getEntity();
                    text = getASCIIContentFromEntity(entity);
                return text;
            }
        } catch (SoapFault e) {
            v_excepcion = e.getMessage();
            //e.printStackTrace();
            response = e.getMessage();
            return "ERROR: "+response;
        } catch (SocketTimeoutException e){
            v_excepcion = e.getMessage();
            //e.printStackTrace();
            response = e.getMessage();
            return "ERROR: "+response;
        }catch (IOException e) {
            v_excepcion = e.getMessage();
            //e.printStackTrace();
            response = e.getMessage();
            return "ERROR: "+response;
        }  catch(ClassCastException e){
            //e.printStackTrace();
            response = e.getMessage();
            return "ERROR: "+response;
        } catch(Exception e){
            v_excepcion = e.getMessage();
            // e.printStackTrace();
            response = e.getMessage();
            return "ERROR: "+response;
        }
    }

    protected void onPostExecute(String results) {

    }

    protected String getASCIIContentFromEntity(HttpEntity entity) throws IllegalStateException, IOException {
        InputStream in = entity.getContent();
        StringBuffer out = new StringBuffer();
        int n = 1;
        while (n>0) {
            byte[] b = new byte[4096];
            n =  in.read(b);
            if (n>0) out.append(new String(b, 0, n));
        }
        return out.toString();
    }
}

