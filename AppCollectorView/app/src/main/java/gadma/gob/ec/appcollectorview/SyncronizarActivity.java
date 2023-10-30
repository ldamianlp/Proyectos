package gadma.gob.ec.appcollectorview;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.StrictMode;
import android.provider.Settings;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import java.util.ArrayList;
import ec.gob.gadma.obj.ObjTablaLocal;
import ec.gob.gadma.obj.ObjAdapterDynamic;
import ec.gob.gadma.obj.ObjUser;
import ec.gob.gadma.serv.ServDatosPredio;
import ec.gob.gadma.serv.DataBaseSQLite;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SyncronizarActivity extends AppCompatActivity {
    ListView lvPredios;
    AlertDialog.Builder dlgAlert;
    ArrayAdapter<ObjAdapterDynamic> adapter;
    ObjUser objUser;
    ServDatosPredio objServDatosPredio;
    AlertDialog.Builder dlgConfirEliminar;
    AlertDialog.Builder dlgConfirSyncro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syncronizar);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        objUser = ObjUser.getInstance();
        objServDatosPredio = new ServDatosPredio();
        cargarLista();

        dlgAlert  = new AlertDialog.Builder(SyncronizarActivity.this);
        dlgAlert.setCancelable(false);
        dlgAlert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cargarLista();
            }
        });

        dlgConfirEliminar  = new AlertDialog.Builder(SyncronizarActivity.this);
        dlgConfirEliminar.setCancelable(true);
        dlgConfirEliminar.setNegativeButton("Cancelar", null);
        dlgConfirEliminar.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SparseBooleanArray checked = lvPredios.getCheckedItemPositions();
                ArrayList<ObjAdapterDynamic> selectedItems = new ArrayList<ObjAdapterDynamic>();
                for (int i = 0; i < checked.size(); i++) {
                    // Item position in adapter
                    int position = checked.keyAt(i);
                    // Add sport if it is checked i.e.) == TRUE!
                    if (checked.valueAt(i))
                        selectedItems.add(adapter.getItem(position));
                }
                if(selectedItems.size()>0) {
                    String resultado = objServDatosPredio.eliminarSeleccionado(selectedItems, SyncronizarActivity.this, objUser);
                    if(resultado.equalsIgnoreCase("true")){
                        dlgAlert.setMessage("Los datos seleccionados fueron eliminados correctamente");
                        dlgAlert.setTitle("Mensaje Correcto.");
                        dlgAlert.create().show();
                    }else{
                        dlgAlert.setMessage("Existió un error al momento de eliminar los registros seleccionados");
                        dlgAlert.setTitle("Alerta.");
                        dlgAlert.create().show();
                    }
                }
            }
        });

        dlgConfirSyncro  = new AlertDialog.Builder(SyncronizarActivity.this);
        dlgConfirSyncro.setCancelable(true);
        dlgConfirSyncro.setNegativeButton("Cancelar", null);
        dlgConfirSyncro.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //WifiManager wifi = (WifiManager)getSystemService(Context.WIFI_SERVICE);
                //if (wifi.isWifiEnabled()){//wifi is enabled
                if(wifiState()){
                    if(!isConnectedMobile(SyncronizarActivity.this)){//verificarRedDeDatos()) {
                        SparseBooleanArray checked = lvPredios.getCheckedItemPositions();
                        ArrayList<ObjAdapterDynamic> selectedItems = new ArrayList<ObjAdapterDynamic>();
                        for (int i = 0; i < checked.size(); i++) {
                            // Item position in adapter
                            int position = checked.keyAt(i);
                            // Add sport if it is checked i.e.) == TRUE!
                            if (checked.valueAt(i))
                                selectedItems.add(adapter.getItem(position));
                        }
                        if(selectedItems.size()>0) {
                            //ServDatosPredio obj = new ServDatosPredio();
                            //String resultado = objServDatosPredio.syncroDatos(selectedItems, SyncronizarActivity.this,objUser);
                            //String resultado = objServDatosPredio.syncroDatosWs(selectedItems, SyncronizarActivity.this,objUser);




                            String resultado = objServDatosPredio.syncroDatosWsRest(selectedItems, SyncronizarActivity.this,objUser);



                            if(resultado.equalsIgnoreCase("true")) {
                                dlgAlert.setMessage("Los datos seleccionados fueron syncronizados correctamente");
                                dlgAlert.setTitle("Migración exitosa...");
                                dlgAlert.create().show();
                            }else{
                                if(resultado.toUpperCase().contains("ERROR")) {
                                    dlgAlert.setMessage("Existe un problema al momento de migrar la información: \n"+resultado);
                                    dlgAlert.setTitle("Error...");
                                    dlgAlert.create().show();
                                }else{
                                    dlgAlert.setMessage("Por favor informa este error al administrador del sistema");
                                    dlgAlert.setTitle("Error desconocido...");
                                    dlgAlert.create().show();
                                }
                            }
                        }else{
                            dlgAlert.setMessage("No existe datos para migrar");
                            dlgAlert.setTitle("Alerta...");
                            dlgAlert.create().show();
                        }
                    }else{
                        dlgAlert.setMessage("El servicio de Red de Datos esta habilitado. Por favor apague el Servicio de Datos Móviles para no consumir sus megas del plan de Datos");
                        dlgAlert.setTitle("Alerta...");
                        dlgAlert.create().show();
                    }
                }else{
                    dlgAlert.setMessage("El servicio de Wifi no esta habilitado. Por favor encienda el Servicio WIFI");
                    dlgAlert.setTitle("Alerta...");
                    dlgAlert.create().show();
                }
            }
        });
        LinearLayout btnEliminar = (LinearLayout)findViewById(R.id.btnEliminar) ;
        btnEliminar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dlgConfirEliminar.setMessage("Esta seguro de eliminar los registros seleccionados?");
                dlgConfirEliminar.setTitle("Confirmación.");
                dlgConfirEliminar.create().show();
            }
        });

/*
        Button btnObtenerParametros = (Button)findViewById(R.id.btnObtenerParametros);
        btnObtenerParametros.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String retorno = objServDatosPredio.getParameterSyncro(SyncronizarActivity.this,objUser);
                if(retorno.equalsIgnoreCase("true")){
                    dlgAlert.setMessage("Parámetros obtenidos correctamente");
                    dlgAlert.setTitle("Correcto.");
                    dlgAlert.create().show();
                }else{
                    dlgAlert.setMessage("Existió un error al momento de obtener los Parámetros");
                    dlgAlert.setTitle("Alerta.");
                    dlgAlert.create().show();
                }
            }
        });
        */
        LinearLayout btnMigrar = (LinearLayout)findViewById(R.id.btnMigrar);
        btnMigrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlgConfirSyncro.setMessage("Esta seguro de syncronizar los registros seleccionados?");
                dlgConfirSyncro.setTitle("Confirmación.");
                dlgConfirSyncro.create().show();
            }
        });

        /** Defining checkbox click event listener **/
        OnClickListener clickListener = new OnClickListener() {

            @Override
            public void onClick(View v) {
                CheckBox chk = (CheckBox) v;
                int itemCount = lvPredios.getAdapter().getCount();
                for(int i=0 ; i < itemCount ; i++){
                    lvPredios.setItemChecked(i, chk.isChecked());
                }
            }
        };

        /** Defining click event listener for the listitem checkbox */
        OnItemClickListener itemClickListener = new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                CheckBox chk = (CheckBox) findViewById(R.id.chkAll);
                int checkedItemCount = getCheckedItemCount();

                if(lvPredios.getCount()==checkedItemCount)
                    chk.setChecked(true);
                else
                    chk.setChecked(false);
            }
        };

        /** Getting reference to checkbox available in the main.xml layout */
        CheckBox chkAll =  ( CheckBox ) findViewById(R.id.chkAll);

        /** Setting a click listener for the checkbox **/
        chkAll.setOnClickListener(clickListener);

        /** Setting a click listener for the listitem checkbox **/
        lvPredios.setOnItemClickListener(itemClickListener);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public boolean wifiState(){
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (mWifi.isConnected()) {
            return true;
        }
        return false;
    }
    /*public Boolean getNetworkClass(Context context) {
        TelephonyManager mTelephonyManager = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        int networkType = mTelephonyManager.getNetworkType();
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_GPRS: return true;
            case TelephonyManager.NETWORK_TYPE_EDGE: return true;
            case TelephonyManager.NETWORK_TYPE_CDMA: return true;
            case TelephonyManager.NETWORK_TYPE_1xRTT: return true;
            case TelephonyManager.NETWORK_TYPE_IDEN: return true;
                //return "2G";
            case TelephonyManager.NETWORK_TYPE_UMTS: return true;
            case TelephonyManager.NETWORK_TYPE_EVDO_0: return true;
            case TelephonyManager.NETWORK_TYPE_EVDO_A: return true;
            case TelephonyManager.NETWORK_TYPE_HSDPA: return true;
            case TelephonyManager.NETWORK_TYPE_HSUPA: return true;
            case TelephonyManager.NETWORK_TYPE_HSPA: return true;
            case TelephonyManager.NETWORK_TYPE_EVDO_B: return true;
            case TelephonyManager.NETWORK_TYPE_EHRPD: return true;
            case TelephonyManager.NETWORK_TYPE_HSPAP: return true;
               // return "3G";
            case TelephonyManager.NETWORK_TYPE_LTE: return true;
                //return "4G";
            default:  return true;
               // return "Unknown";
        }
    }*/
    public boolean isConnectedMobile(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        boolean resultado = (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE);
        return resultado;
    }
    public boolean verificarRedDeDatos()
    {
        ConnectivityManager connManager = (ConnectivityManager) SyncronizarActivity.this.getSystemService(CONNECTIVITY_SERVICE);
        boolean is3gEnabled = false;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Network[] networks = connManager.getAllNetworks();
            for(Network network: networks)
            {
                NetworkInfo info = connManager.getNetworkInfo(network);
                if(info!=null) {
                    if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                        is3gEnabled = true;
                        break;
                    }
                }
            }
        }
        else
        {
            NetworkInfo mMobile = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if(mMobile!=null)
                is3gEnabled = true;
        }
        return is3gEnabled;
    }
    /**
     *
     * Returns the number of checked items
     */
    private int getCheckedItemCount(){
        int cnt = 0;
        SparseBooleanArray positions = lvPredios.getCheckedItemPositions();
        int itemCount = lvPredios.getCount();

        for(int i=0;i<itemCount;i++){
            if(positions.get(i))
                cnt++;
        }
        return cnt;
    }
    private void cargarLista() {
        ArrayList<ObjAdapterDynamic>  datos = getDatosLocales();

        adapter = new ArrayAdapter<ObjAdapterDynamic>(this, android.R.layout.simple_list_item_multiple_choice, datos);
        lvPredios = (ListView)findViewById(R.id.lvPredios);
        lvPredios.setAdapter(adapter);
        lvPredios.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);adapter = new ArrayAdapter<ObjAdapterDynamic>(this, android.R.layout.simple_list_item_multiple_choice, datos);
        lvPredios = (ListView)findViewById(R.id.lvPredios);
        lvPredios.setAdapter(adapter);
        lvPredios.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }

    private ArrayList<ObjAdapterDynamic> getDatosLocales() {
        DataBaseSQLite db = new DataBaseSQLite(SyncronizarActivity.this);
        ArrayList<ObjAdapterDynamic> datos = db.getAllDatosLocales(SyncronizarActivity.this);
        return datos;
    }
}