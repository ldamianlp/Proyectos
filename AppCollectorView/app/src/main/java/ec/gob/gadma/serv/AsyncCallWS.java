package ec.gob.gadma.serv;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ec.gob.gadma.obj.ObjUser;
import ec.gob.gadma.obj.Util;

public class AsyncCallWS extends AsyncTask<Void, Void, String> {
    private String v_METHOD_NAME;
    private HashMap<String,Object> v_parameters;
    private Context v_Context;
    private String v_excepcion;

    public AsyncCallWS(Context inContext, String inMETHOD_NAME,HashMap<String,Object> inParameters){
        this.v_Context = inContext;
        this.v_METHOD_NAME = inMETHOD_NAME;
        this.v_parameters = inParameters;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected String doInBackground(Void... params) {
        ObjUser objUser = ObjUser.getInstance();

        String response = null;

        String SOAP_ACTION ="";// "http://ws.ambato.gob.ec/getDatosPredioByClaveCat";//"http://ws.ambato.gob.ec/getDatosPredioByClaveCat";
        String METHOD_NAME = this.v_METHOD_NAME;//"getDatosPredioByClaveCat";
        String NAMESPACE = "http://ws.ambato.gob.ec/";


        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        if(v_parameters!=null) {
            Iterator it = v_parameters.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                request.addProperty(pair.getKey().toString(), pair.getValue());
                //it.remove(); // avoids a ConcurrentModificationException
            }
        }

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        //ESTO AUMENTO PARA PODER SERIALIZAR
        new MarshalBase64().register(envelope); // serialization


        //envelope.dotNet = true:
        envelope.setOutputSoapObject(request);
        try {
            List<HeaderProperty> headerList = new ArrayList<>();
            headerList.add(new HeaderProperty("Authorization", "Basic " +
                    org.kobjects.base64.Base64.encode((objUser.getUserConnect()+":"+objUser.getUserPassword()).getBytes())));
                    //org.kobjects.base64.Base64.encode("LDLP20:Ambato20.".getBytes())));

            String URL = null;

            URL = Util.getProperty("URL_WS",v_Context);//v_Context.getString(R.string.URL_WS_INTERNA);
            //HttpTransportBasicAuth httpTransport = new HttpTransportBasicAuth(URL,"LDLP2017","Ambato2017.");
            HttpTransportSE httpTransport = new HttpTransportSE (URL,900000000);
           // httpTransport.debug = true;


            httpTransport.call(SOAP_ACTION, envelope,headerList);
            SoapPrimitive  resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();
            response =  resultsRequestSOAP.toString();

            return response;

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
        } catch (XmlPullParserException e) {
            v_excepcion = e.getMessage();
            //e.printStackTrace();
            response = e.getMessage();
            return "ERROR: "+response;
        } catch(ClassCastException e){
            //e.printStackTrace();
            response = e.getMessage();
            return "ERROR: "+response;
        } catch(Exception e){
            v_excepcion = e.getMessage();
           // e.printStackTrace();
            response = e.getMessage();
            return "ERROR: "+response;
        }
        //return response;
    }
    @Override
    protected void onPostExecute(String result) {
        if(v_excepcion!=null) {
            if (v_excepcion.contains("IOException: java.net.ConnectException:")) {
                Toast.makeText(v_Context, "YOUR TEXT HERE", Toast.LENGTH_SHORT).show();
                //mProgressDialog.dismiss();
            } else if (v_excepcion.contains("IOException: java.net.SocketTimeoutException:")) {
                Toast.makeText(v_Context, "YOUR TEXT HERE", Toast.LENGTH_SHORT).show();
                //mProgressDialog.dismiss();
            } else if (v_excepcion.contains("IOException: java.net.SocketException:")) {
                Toast.makeText(v_Context, "YOUR TEXT HERE", Toast.LENGTH_SHORT).show();
                //mProgressDialog.dismiss();
            }
        }
    }
}