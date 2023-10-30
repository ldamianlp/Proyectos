package gadma.gob.ec.appcollectorview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.StrictMode;

import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import ec.gob.gadma.obj.ObjAdapterDynamic;
import ec.gob.gadma.obj.ObjUser;
import ec.gob.gadma.serv.AsyncCallWS;

public class MainActivity extends AppCompatActivity {
    private EditText txtUsuarioConnectConsul;
    private Spinner spIdPolitico;
    private EditText txtClaveCatastral;
    private EditText txtArea;
    private EditText txtCIU;
    private EditText txtApellidos;
    private EditText txtNombres;
    private EditText txtCedula;
    private EditText txtRUC;
    private TextView lblIdPolitico;
    private Button btnBuscar;
    private Boolean bandera = true;
    private TableLayout table_construcciones;
    private TableRow trConstrucciones;
    private TableLayout table_escrituras;
    private TableRow trEscrituras;
    //private TableLayout table_inquilinato;
    //private TableRow trInquilinato;
    private Button btnCargarImg;
    private Button btnNuevaConsulta;
    private RadioButton radioUrbano;
    private RadioButton radioUrbanoParroquia;
    private RadioButton radioRustico;
    AlertDialog.Builder dlgAlert;
    private View viewCargando;
    private View pbCargando;
    private AsyncCallWS tareaWsdatoPredio;
    private AsyncCallWS tareaWsIDPoli;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //final Object  context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        viewCargando = findViewById(R.id.login_formConsulta);
        pbCargando = findViewById(R.id.pbCargando);

        table_construcciones = (TableLayout) findViewById(R.id.table_construcciones);
        table_escrituras = (TableLayout) findViewById(R.id.table_escrituras);
        //table_inquilinato = (TableLayout) findViewById(R.id.table_inquilinato);
        /* table_escrituras.setColumnStretchable(0, true); //first column*/

        btnCargarImg = (Button) findViewById(R.id.btnCargarImg);
        btnCargarImg.setVisibility(View.INVISIBLE);

        btnNuevaConsulta = (Button) findViewById(R.id.btnNuevaConsulta);
        btnNuevaConsulta.setVisibility(View.INVISIBLE);

        txtClaveCatastral = (EditText) findViewById(R.id.txtClaveCatastral);
        //txtClaveCatastral.setImeOptions(EditorInfo.IME_ACTION_DONE | EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        txtArea = (EditText) findViewById(R.id.txtArea);
        txtArea.setInputType(InputType.TYPE_NULL);
        txtArea.setTextIsSelectable(true);
        txtArea.setKeyListener(null);

        txtCIU = (EditText) findViewById(R.id.txtCIU);
        txtCIU.setInputType(InputType.TYPE_NULL);
        txtCIU.setTextIsSelectable(true);
        txtCIU.setKeyListener(null);

        txtApellidos = (EditText) findViewById(R.id.txtApellidos);
        txtApellidos.setInputType(InputType.TYPE_NULL);
        txtApellidos.setTextIsSelectable(true);
        txtApellidos.setKeyListener(null);

        txtNombres = (EditText) findViewById(R.id.txtNombres);
        txtNombres.setInputType(InputType.TYPE_NULL);
        txtNombres.setTextIsSelectable(true);
        txtNombres.setKeyListener(null);

        txtCedula = (EditText) findViewById(R.id.txtCedula);
        txtCedula.setInputType(InputType.TYPE_NULL);
        txtCedula.setTextIsSelectable(true);
        txtCedula.setKeyListener(null);

        txtRUC = (EditText) findViewById(R.id.txtRUC);
        txtRUC.setInputType(InputType.TYPE_NULL);
        txtRUC.setTextIsSelectable(true);
        txtRUC.setKeyListener(null);

        lblIdPolitico = (TextView) findViewById(R.id.lblIdPolitico);
        spIdPolitico = (Spinner) findViewById(R.id.spIdPolitico);
        spIdPolitico.setVisibility(View.INVISIBLE);

        btnBuscar = (Button) findViewById(R.id.btnBuscar);

        radioUrbano = (RadioButton) findViewById(R.id.radio_urbano);
        radioUrbanoParroquia = (RadioButton) findViewById(R.id.radio_urbanoParroquia);
        radioRustico = (RadioButton) findViewById(R.id.radio_rustico);

        dlgAlert = new AlertDialog.Builder(MainActivity.this);
        dlgAlert.setMessage("La clave catastral no corresponde a un predio de Tipo Urbano de Parroquia");
        dlgAlert.setTitle("Mensaje de Error...");
        dlgAlert.setCancelable(false);
        dlgAlert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                bandera = false;
                btnCargarImg.setVisibility(View.INVISIBLE);
            }
        });
        ////********************+
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String datosPredio=null;
                Editable edit_claveCatastral = txtClaveCatastral.getText();
                int total = edit_claveCatastral.length();
                if (total > 0) {
                    limpiarDatos();
                    bandera = true;
                    String claveCatastral = txtClaveCatastral.getText().toString();
                    String primerDigito = claveCatastral.substring(0, 1);
                    if (radioUrbanoParroquia.isChecked()) {
                        if (radioUrbanoParroquia.isChecked() && primerDigito != null && !primerDigito.equalsIgnoreCase("") &&
                                (!((primerDigito.equalsIgnoreCase("5") || primerDigito.equalsIgnoreCase("6"))))
                                ) {
                            bandera = false;
                            dlgAlert.create().show();
                        }else
                            datosPredio = callDatosPredio(3,claveCatastral,null);
                    }
                    if (radioUrbano.isChecked()) {
                        if (primerDigito != null && !primerDigito.equalsIgnoreCase("") &&
                                ((primerDigito.equalsIgnoreCase("5") || primerDigito.equalsIgnoreCase("6")))
                                ) {
                            bandera = false;
                            dlgAlert.setMessage("La clave catastral no corresponde a un predio de Tipo Urbano");
                            dlgAlert.create().show();
                        }else
                            datosPredio = callDatosPredio(1,claveCatastral,null);
                    }
                    if (radioRustico.isChecked()) {
                        datosPredio = callDatosPredio(2,claveCatastral,((ObjAdapterDynamic) spIdPolitico.getSelectedItem()).getId());
                    }
                    if(bandera) {
                        if(datosPredio!=null) {
                            try {
                                JSONObject obj = new JSONObject(datosPredio);
                                if(obj.getString("totalCount").equalsIgnoreCase("1")) {
                                    JSONObject hmDatosPredio = obj.getJSONObject("datosPredio");
                                    setearDatosPredio(hmDatosPredio);
                                    FillPredios(datosPredio);
                                    FillEscrituras(datosPredio);
                                    //FillInquilinato(datosPredio);
                                    DeshabilitarComponentes();
                                }else{
                                    dlgAlert.setMessage("No existe datos para el predio ingresado");
                                    dlgAlert.create().show();
                                }
                            } catch (JSONException e) {
                                dlgAlert.setMessage(e.getMessage());
                                dlgAlert.create().show();
                            }
                        }else{
                            dlgAlert.setMessage("No existe datos para el predio ingresado");
                            dlgAlert.create().show();
                        }
                    }
                } else {
                    View focusView = null;
                    // Check for a valid email address.
                    if (TextUtils.isEmpty(txtClaveCatastral.getText().toString())) {
                        txtClaveCatastral.setError("Es necesario una clave catastral");
                        focusView = txtClaveCatastral;
                    }
                    limpiarDatos();
                }
            }
        });

        tareaWsIDPoli = new AsyncCallWS(MainActivity.this,"getAllIdPoliticos",null);
        String alHMIdPolitico;
        //getDatosPredioByClaveCat
        ArrayList<ObjAdapterDynamic> alIdPolitico = new ArrayList<>();
        try {
            alHMIdPolitico = tareaWsIDPoli.execute().get();
            JSONObject obj = new JSONObject(alHMIdPolitico);
            JSONArray hmDatosIdPoli = obj.getJSONArray("dataSet");

            for (int i=0;i<hmDatosIdPoli.length();i++) {
                alIdPolitico.add(new ObjAdapterDynamic(hmDatosIdPoli.getJSONObject(i).get("ID_POLITICO").toString(), hmDatosIdPoli.getJSONObject(i).get("DISPLAY_FIELD").toString()));
            }

            //Toast.makeText(MainActivity.this, valor, Toast.LENGTH_SHORT).show();
        } catch (InterruptedException e) {
            dlgAlert.setMessage(e.getMessage());
            dlgAlert.create().show();
        } catch (ExecutionException e) {
            dlgAlert.setMessage(e.getMessage());
            dlgAlert.create().show();
        }catch (JSONException e) {
            dlgAlert.setMessage(e.getMessage());
            dlgAlert.create().show();
        }
        //fill data in spinner
        ArrayAdapter<ObjAdapterDynamic> adapter = new ArrayAdapter<ObjAdapterDynamic>(this, android.R.layout.simple_spinner_dropdown_item, alIdPolitico);
        spIdPolitico.setAdapter(adapter);
        spIdPolitico.setSelection(-1);
        /*spIdPolitico.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ObjAdapterDynamic objIdPolitico = (ObjAdapterDynamic) parent.getSelectedItem();
                Toast.makeText(MainActivity.this, "objIdPolitico ID: " + objIdPolitico.getId() + ",  objIdPolitico Name : " + objIdPolitico.getValue(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });*/

        btnNuevaConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HabilitarComponentes();
                limpiarDatos();
                btnNuevaConsulta.setVisibility(View.INVISIBLE);
                btnCargarImg.setVisibility(View.INVISIBLE);
                btnBuscar.setVisibility(View.VISIBLE);
            }
        });


        btnCargarImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Aqui llamo a la otra ventana
                //Creamos el Intent
                Intent intent =
                        new Intent(MainActivity.this, CargarImagenesActivity.class);

                //Creamos la información a pasar entre actividades
                Bundle b = new Bundle();
                b.putString("USER_CONNECT", txtUsuarioConnectConsul.getText().toString());
                b.putString("txtClaveCatastral", txtClaveCatastral.getText().toString());
                if (radioRustico.isChecked())
                    b.putString("txtIDPolitico", ((ObjAdapterDynamic) spIdPolitico.getSelectedItem()).getId());
                else
                    b.putString("txtIDPolitico", null);
                //Añadimos la información al intent
                intent.putExtras(b);

                //Iniciamos la nueva actividad
                startActivity(intent);
            }
        });
        //Localizar los controles
        txtUsuarioConnectConsul = (EditText) findViewById(R.id.txtUsuarioConnectConsul);

        //Recuperamos la información pasada en el intent
        Bundle bundle = this.getIntent().getExtras();

        if(bundle!= null) {
            //Construimos el mensaje a mostrar
            txtUsuarioConnectConsul.setText(bundle.getString("NOMBRE"));
        }else{
            ObjUser obj = ObjUser.getInstance();
            txtUsuarioConnectConsul.setText(obj.getUserConnect());
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setTitle("Consulta de Predios");
        //getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private String callDatosPredio(int inTipo,String claveCatastral, String idPolitico){
        //showProgress(true);
        String datosPredio;
        HashMap<String,Object> hmParam=new HashMap<String, Object>();
        hmParam.put("inCLaveCatastral", claveCatastral);
        hmParam.put("inIDPolitico", idPolitico);
        hmParam.put("inTipo", inTipo);

        tareaWsdatoPredio = new AsyncCallWS(MainActivity.this,"getDatosPredio",hmParam);
        try {
            datosPredio = tareaWsdatoPredio.execute().get();
            return datosPredio;
        } catch (InterruptedException e) {
            dlgAlert.setMessage(e.getMessage());
            dlgAlert.create().show();
            return e.getMessage();
        } catch (ExecutionException e) {
            dlgAlert.setMessage(e.getMessage());
            dlgAlert.create().show();
            return e.getMessage();
        }
    }
    private void setearDatosPredio(JSONObject hmDatosPredio){
        try {
            btnCargarImg.setVisibility(View.VISIBLE);
            Object dato = hmDatosPredio.get("AREA_TERRENO");
            txtArea.setText(dato != null ? dato.toString() : "");
            dato = hmDatosPredio.get("CODIGO_CIUS");
            txtCIU.setText(dato != null ? dato.toString() : "");
            dato = hmDatosPredio.get("NOMBRES");
            txtNombres.setText(dato != null ? dato.toString() : "");
            dato = hmDatosPredio.get("APELLIDOS");
            txtApellidos.setText(dato != null ? dato.toString() : "");
            dato = hmDatosPredio.get("CEDULA");
            txtCedula.setText(dato != null ? dato.toString() : "");
            dato = hmDatosPredio.get("RUC");
            txtRUC.setText(dato != null ? dato.toString() : "");
            DeshabilitarComponentes();
        }catch (JSONException e) {
            dlgAlert.setMessage(e.getMessage());
            dlgAlert.create().show();
        }
    }
    private void HabilitarComponentes() {
        txtClaveCatastral.setEnabled(true);
        radioUrbano.setEnabled(true);
        radioUrbanoParroquia.setEnabled(true);
        radioRustico.setEnabled(true);
        spIdPolitico.setEnabled(true);
    }

    private void DeshabilitarComponentes() {
        txtClaveCatastral.setEnabled(false);
        radioUrbano.setEnabled(false);
        radioUrbanoParroquia.setEnabled(false);
        radioRustico.setEnabled(false);
        spIdPolitico.setEnabled(false);
        btnBuscar.setVisibility(View.INVISIBLE);
        btnNuevaConsulta.setVisibility(View.VISIBLE);
        btnCargarImg.setVisibility(View.VISIBLE);
    }

    public void limpiarDatos() {
        txtArea.setText("");
        txtCIU.setText("");
        txtNombres.setText("");
        txtApellidos.setText("");
        txtCedula.setText("");
        txtRUC.setText("");
        int total = table_construcciones.getChildCount();
        if (total > 1) {
            table_construcciones.removeViews(1, total - 1);
        }
        trConstrucciones = null;
        total = table_escrituras.getChildCount();
        if (total > 1) {
            table_escrituras.removeViews(1, total - 1);
        }
        trEscrituras = null;
        /*total = table_inquilinato.getChildCount();
        if (total > 1) {
            table_inquilinato.removeViews(1, total - 1);
        }
        trInquilinato = null;*/
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            viewCargando.setVisibility(show ? View.GONE : View.VISIBLE);
            viewCargando.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    viewCargando.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            pbCargando.setVisibility(show ? View.VISIBLE : View.GONE);
            pbCargando.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    pbCargando.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            pbCargando.setVisibility(show ? View.VISIBLE : View.GONE);
            viewCargando.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @SuppressLint("WrongConstant")
    private void FillPredios(String inDatosJsonString) {
        showProgress(true);
        TextView t1, t2, t3, t4, t5, t6, t7, t8, t9;
        JSONArray alHmConstrucciones;

        try {
            JSONObject obj = new JSONObject(inDatosJsonString);

            if(obj.getString("construcciones")!=null && !obj.getString("construcciones").equalsIgnoreCase("")) {
                alHmConstrucciones = obj.getJSONArray("construcciones");

                for (int current = 0; current < alHmConstrucciones.length(); current++) {
                    trConstrucciones = new TableRow(this);

                    t1 = new TextView(this);
                    //t1.setTextColor(getResources().getColor(R.color.yellow));
                    t2 = new TextView(this);
                    //t2.setTextColor(getResources().getColor(R.color.dark_red));
                    t3 = new TextView(this);
                    t4 = new TextView(this);
                    t5 = new TextView(this);
                    t6 = new TextView(this);
                    t7 = new TextView(this);
                    t8 = new TextView(this);
                    t9 = new TextView(this);
                    Object dato = alHmConstrucciones.getJSONObject(current).get("NUM_BLOQUE");
                    t1.setText(dato != null ? dato.toString() : "");
                    t1.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

                    dato = alHmConstrucciones.getJSONObject(current).get("NUM_PISO");
                    t2.setText(dato != null ? dato.toString() : "");
                    t2.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

                    dato = alHmConstrucciones.getJSONObject(current).get("DES_TIPOLOGIA");
                    t3.setText(dato != null ? dato.toString() : "");

                    dato = alHmConstrucciones.getJSONObject(current).get("ESTADO");
                    t4.setText(dato != null ? dato.toString() : "");
                    t4.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

                    dato = alHmConstrucciones.getJSONObject(current).get("ANIO_CONST");
                    t5.setText(dato != null ? dato.toString() : "");
                    t5.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

                    dato = alHmConstrucciones.getJSONObject(current).get("AREA_CONS");
                    t6.setText(dato != null ? dato.toString() : "");
                    t6.setGravity(Gravity.END);

                    dato = alHmConstrucciones.getJSONObject(current).get("AREA_COM");
                    t7.setText(dato != null ? dato.toString() : "");
                    t7.setGravity(Gravity.END);

                    dato = alHmConstrucciones.getJSONObject(current).get("CONSTOT");
                    t8.setText(dato != null ? dato.toString() : "");
                    t8.setGravity(Gravity.END);

                    dato = alHmConstrucciones.getJSONObject(current).get("VALOR_BLOQUE");
                    t9.setText(dato != null ? dato.toString() : "");
                    t9.setGravity(Gravity.END);

                    t1.setTypeface(null, 1);
                    t2.setTypeface(null, 1);
                    t3.setTypeface(null, 1);
                    t4.setTypeface(null, 1);
                    t5.setTypeface(null, 1);
                    t6.setTypeface(null, 1);
                    t7.setTypeface(null, 1);
                    t8.setTypeface(null, 1);
                    t9.setTypeface(null, 1);

                    t1.setTextSize(15);
                    t2.setTextSize(15);
                    t3.setTextSize(15);
                    t4.setTextSize(15);
                    t5.setTextSize(15);
                    t6.setTextSize(15);
                    t7.setTextSize(15);
                    t8.setTextSize(15);
                    t9.setTextSize(15);

                    t1.setPadding(1, 0, 0, 3);//t1.setPadding(1*dip, 0, 0, 0);
                    trConstrucciones.addView(t1);
                    trConstrucciones.addView(t2);
                    trConstrucciones.addView(t3);
                    trConstrucciones.addView(t4);
                    trConstrucciones.addView(t5);
                    trConstrucciones.addView(t6);
                    trConstrucciones.addView(t7);
                    trConstrucciones.addView(t8);
                    trConstrucciones.addView(t9);

                    table_construcciones.addView(trConstrucciones);
                }
            }
        }catch (JSONException e) {
            showProgress(false);
            dlgAlert.setMessage(e.getMessage());
            dlgAlert.create().show();
        }
        showProgress(false);
    }

    @SuppressLint("WrongConstant")
    private void FillEscrituras(String inDatosJsonString) {
        showProgress(true);
        TextView t1, t2, t3, t4, t5, t6;//, t7;

        try {
            JSONArray alHmEscrituras;
            JSONObject obj = new JSONObject(inDatosJsonString);
            if(obj.getString("escrituras")!=null && !obj.getString("escrituras").equalsIgnoreCase("")) {
                alHmEscrituras = obj.getJSONArray("escrituras");

                for (int current = 0; current < alHmEscrituras.length(); current++) {
                    trEscrituras = new TableRow(this);

                    t1 = new TextView(this);
                    //t1.setTextColor(getResources().getColor(R.color.yellow));
                    t2 = new TextView(this);
                    //t2.setTextColor(getResources().getColor(R.color.dark_red));
                    t3 = new TextView(this);
                    t4 = new TextView(this);
                    t5 = new TextView(this);
                    t6 = new TextView(this);

                    Object dato = alHmEscrituras.getJSONObject(current).get("PROPIETARIO_ANTERIOR");
                    t1.setText(dato != null ? dato.toString() : "");
                    t1.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

                    dato = alHmEscrituras.getJSONObject(current).get("PROVINCIA_NOTARIA");
                    t2.setText(dato != null ? dato.toString() : "");
                    t2.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

                    dato = alHmEscrituras.getJSONObject(current).get("CANTON_NOTARIA");
                    t3.setText(dato != null ? dato.toString() : "");
                    t3.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

                    dato = alHmEscrituras.getJSONObject(current).get("NUMERO_NOTARIA");
                    t4.setText(dato != null ? dato.toString() : "");
                    t4.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

                    dato = alHmEscrituras.getJSONObject(current).get("FECHA_NOTARIA");
                    t5.setText(dato != null ? dato.toString() : "");
                    t5.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

                    dato = alHmEscrituras.getJSONObject(current).get("NUMERO_REGISTRO");
                    t6.setText(dato != null ? dato.toString() : "");
                    t6.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

                    t1.setTypeface(null, 1);
                    t2.setTypeface(null, 1);
                    t3.setTypeface(null, 1);
                    t4.setTypeface(null, 1);
                    t5.setTypeface(null, 1);
                    t6.setTypeface(null, 1);
                    //t7.setTypeface(null, 1);

                    t1.setTextSize(15);
                    t2.setTextSize(15);
                    t3.setTextSize(15);
                    t4.setTextSize(15);
                    t5.setTextSize(15);
                    t6.setTextSize(15);
                    //t7.setTextSize(15);

                    t1.setPadding(1, 0, 0, 3);//t1.setPadding(1*dip, 0, 0, 0);
                    trEscrituras.addView(t1);
                    trEscrituras.addView(t2);
                    trEscrituras.addView(t3);
                    trEscrituras.addView(t4);
                    trEscrituras.addView(t5);
                    trEscrituras.addView(t6);

                    table_escrituras.addView(trEscrituras);
                }
            }
        }catch (JSONException e) {
            showProgress(false);
            dlgAlert.setMessage(e.getMessage());
            dlgAlert.create().show();
        }
        showProgress(false);
    }

   /* private void FillInquilinato(String inDatosJsonString) {
        showProgress(true);
        TextView t1, t2, t3, t4, t5, t6, t7,t8,t9,t10;

        try {
            JSONArray alHmInquilinato;
            JSONObject obj = new JSONObject(inDatosJsonString);
            if(obj.getString("inquilinato")!=null && !obj.getString("inquilinato").equalsIgnoreCase("")) {
                alHmInquilinato = obj.getJSONArray("inquilinato");

                for (int current = 0; current < alHmInquilinato.length(); current++) {
                    trInquilinato = new TableRow(this);

                    t1 = new TextView(this);
                    //t1.setTextColor(getResources().getColor(R.color.yellow));
                    t2 = new TextView(this);
                    //t2.setTextColor(getResources().getColor(R.color.dark_red));
                    t3 = new TextView(this);
                    t4 = new TextView(this);
                    t5 = new TextView(this);
                    t6 = new TextView(this);
                    t7 = new TextView(this);
                    t8 = new TextView(this);
                    t9 = new TextView(this);
                    t10 = new TextView(this);

                    Object dato = alHmInquilinato.getJSONObject(current).get("CIU");
                    t1.setText(dato != null ? dato.toString() : "");

                    dato = alHmInquilinato.getJSONObject(current).get("ARRENDATARIO");
                    t2.setText(dato != null ? dato.toString() : "");

                    dato = alHmInquilinato.getJSONObject(current).get("NUM_ANIOS");
                    t3.setText(dato != null ? dato.toString() : "");

                    dato = alHmInquilinato.getJSONObject(current).get("METROS_ARRIENDO");
                    t4.setText(dato != null ? dato.toString() : "");

                    dato = alHmInquilinato.getJSONObject(current).get("FECHA_INGRESO");
                    t5.setText(dato != null ? dato.toString() : "");

                    dato = alHmInquilinato.getJSONObject(current).get("FECHA_SALIDA");
                    t6.setText(dato != null ? dato.toString() : "");

                    dato = alHmInquilinato.getJSONObject(current).get("TIPO_INMUEBLE");
                    t7.setText(dato != null ? dato.toString() : "");

                    dato = alHmInquilinato.getJSONObject(current).get("ANIO");
                    t8.setText(dato != null ? dato.toString() : "");

                    dato = alHmInquilinato.getJSONObject(current).get("ARRIENDO");
                    t9.setText(dato != null ? dato.toString() : "");

                    dato = alHmInquilinato.getJSONObject(current).get("IMP");
                    t10.setText(dato != null ? dato.toString() : "");

                    t1.setTypeface(null, 1);
                    t2.setTypeface(null, 1);
                    t3.setTypeface(null, 1);
                    t4.setTypeface(null, 1);
                    t5.setTypeface(null, 1);
                    t6.setTypeface(null, 1);
                    t7.setTypeface(null, 1);
                    t8.setTypeface(null, 1);
                    t9.setTypeface(null, 1);
                    t10.setTypeface(null, 1);

                    t1.setTextSize(15);
                    t2.setTextSize(15);
                    t3.setTextSize(15);
                    t4.setTextSize(15);
                    t5.setTextSize(15);
                    t6.setTextSize(15);
                    t7.setTextSize(15);
                    t8.setTextSize(15);
                    t9.setTextSize(15);
                    t10.setTextSize(15);

                    t1.setPadding(1, 0, 0, 3);//t1.setPadding(1*dip, 0, 0, 0);
                    trInquilinato.addView(t1);
                    trInquilinato.addView(t2);
                    trInquilinato.addView(t3);
                    trInquilinato.addView(t4);
                    trInquilinato.addView(t5);
                    trInquilinato.addView(t6);
                    trInquilinato.addView(t7);
                    trInquilinato.addView(t8);
                    trInquilinato.addView(t9);
                    trInquilinato.addView(t10);

                    table_inquilinato.addView(trInquilinato);
                }
            }
        }catch (JSONException e) {
            showProgress(false);
            dlgAlert.setMessage(e.getMessage());
            dlgAlert.create().show();
        }
        showProgress(false);
    }*/

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_urbano:
                if (checked) {
                    spIdPolitico.setSelection(-1);
                    spIdPolitico.setVisibility(View.INVISIBLE);
                    lblIdPolitico.setVisibility(View.INVISIBLE);
                    limpiarDatos();
                    txtClaveCatastral.requestFocus();
                    break;
                }
            case R.id.radio_urbanoParroquia:
                if (checked) {
                    spIdPolitico.setSelection(-1);
                    spIdPolitico.setVisibility(View.INVISIBLE);
                    lblIdPolitico.setVisibility(View.INVISIBLE);
                    limpiarDatos();
                    txtClaveCatastral.requestFocus();
                    break;
                }
            case R.id.radio_rustico:
                if (checked) {
                    spIdPolitico.setVisibility(View.VISIBLE);
                    lblIdPolitico.setVisibility(View.VISIBLE);
                    limpiarDatos();
                    txtClaveCatastral.requestFocus();
                    break;
                }
        }
    }
}