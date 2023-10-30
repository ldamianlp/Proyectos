package gadma.gob.ec.appcollectorview;

import android.app.AlertDialog;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import ec.gob.gadma.obj.Util;

public class ConfigActivity extends AppCompatActivity {
    private EditText txtURL_WS;
    private EditText txtURL_WS_SYNCRO;
    private EditText txtCalidadImagen;
    AlertDialog.Builder dlgAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        dlgAlert = new AlertDialog.Builder(this);
        dlgAlert.setTitle("Mensaje de Error...");
        dlgAlert.setCancelable(false);
        dlgAlert.setPositiveButton(android.R.string.ok,null);

        txtURL_WS = (EditText) findViewById(R.id.txtURL_WS);
        txtURL_WS_SYNCRO = (EditText) findViewById(R.id.txtURL_WS_SYNCRO);
        txtCalidadImagen = (EditText) findViewById(R.id.txtCalidadImagen);

        try {
            String URL = Util.getProperty("URL_WS", getApplicationContext());
            txtURL_WS.setText(URL);
            txtURL_WS_SYNCRO.setText(Util.getProperty("URL_WS_SYNCRO", getApplicationContext()));
            txtCalidadImagen.setText(Util.getProperty("CALIDAD_IMG", getApplicationContext()));
        }catch (IOException e){
            dlgAlert.setMessage(e.getMessage());
            dlgAlert.create().show();
        }

        Button btnAplicar = (Button)findViewById(R.id.btnAplicar);
        btnAplicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Util.setProperty("URL_WS", txtURL_WS.getText().toString(), getApplicationContext());
                    Util.setProperty("URL_WS_SYNCRO", txtURL_WS_SYNCRO.getText().toString(), getApplicationContext());
                    Util.setProperty("CALIDAD_IMG", txtCalidadImagen.getText().toString(), getApplicationContext());
                }catch (IOException e){
                    dlgAlert.setMessage(e.getMessage());
                    dlgAlert.create().show();
                }
            }
        });
    }
}