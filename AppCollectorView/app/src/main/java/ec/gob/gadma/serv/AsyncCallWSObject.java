package ec.gob.gadma.serv;

import android.os.AsyncTask;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AsyncCallWSObject extends AsyncTask<Void, Void, ArrayList<HashMap<String, Object>>> {
    private String v_METHOD_NAME;
    private HashMap<String,Object> v_parameters;
    public AsyncCallWSObject(String inMETHOD_NAME, HashMap<String,Object> inParameters){
        this.v_METHOD_NAME = inMETHOD_NAME;
        this.v_parameters = inParameters;
    }
    @Override
    protected void onPreExecute() {

    }
    @Override
    protected ArrayList<HashMap<String, Object>> doInBackground(Void... params) {

        ArrayList<HashMap<String, Object>> response = null;

        String SOAP_ACTION ="";// "http://ws.ambato.gob.ec/getDatosPredioByClaveCat";//"http://ws.ambato.gob.ec/getDatosPredioByClaveCat";
        String METHOD_NAME = this.v_METHOD_NAME;//"getDatosPredioByClaveCat";
        String NAMESPACE = "http://ws.ambato.gob.ec/";
        String URL = "http://10.10.4.103:8080/catastros/publico/WsCatastros?wsdl";//http://10.10.4.103:8080/catastros/publico/WsCatastrosPublic?wsdl";
        //"http://LDLP2017:Ambato2017.@10.10.4.103:8080/catastros/WsCatastros?wsdl"

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        if(v_parameters!=null) {
            Iterator it = v_parameters.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                request.addProperty(pair.getKey().toString(), pair.getValue());
                //it.remove(); // avoids a ConcurrentModificationException
            }
        }

        // request.addProperty("inCLaveCatastral","0101082002000");

        //request.addProperty("user", "LDLP2017");
        //request.addProperty("password", "Ambato2017.");

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        //envelope.dotNet = true:
        envelope.setOutputSoapObject(request);


        HttpTransportSE  httpTransport = new HttpTransportSE (URL);
        httpTransport.debug = true;
        try {
            httpTransport.call(SOAP_ACTION, envelope);
            Object resp = envelope.getResponse();
            SoapPrimitive  resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();
            response =  null;
        } catch (SoapFault e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch(ClassCastException e)//send request
        {
            e.printStackTrace();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        //IOException, XmlPullParserException
        return response;
    }
    @Override
    protected void onPostExecute(ArrayList<HashMap<String, Object>> result) {
    }
}