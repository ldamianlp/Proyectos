package gadma.gob.ec.appcollectorview;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import ec.gob.gadma.obj.Util;

/**
 * Created by titecnico11 on 07/09/2017.
 */

public class AppDialog extends Dialog {
    AlertDialog.Builder dlgAlert;
    View mainView;
    public AppDialog(final  Context context) {
        super(context);
        dlgAlert = new AlertDialog.Builder(context);
        dlgAlert.setTitle("Mensaje de Error...");
        dlgAlert.setCancelable(false);
        dlgAlert.setPositiveButton(android.R.string.ok,null);

        mainView = LayoutInflater.from(context).inflate(R.layout.activity_config,null);

        final EditText txtURL_WS = (EditText) mainView.findViewById(R.id.txtURL_WS);
        final EditText txtURL_WS_SYNCRO = (EditText) mainView.findViewById(R.id.txtURL_WS_SYNCRO);
        final EditText txtCalidadImagen = (EditText) mainView.findViewById(R.id.txtCalidadImagen);
        try {
            String URL = Util.getProperty("URL_WS", context);
            txtURL_WS.setText(URL);
            String URL_SYNCRO = Util.getProperty("URL_WS_SYNCRO", context);
            txtURL_WS_SYNCRO.setText(URL_SYNCRO);
            String calidad = Util.getProperty("CALIDAD_IMG", context);
            txtCalidadImagen.setText(calidad);
        }catch (IOException e){
            dlgAlert.setMessage(e.getMessage());
            dlgAlert.create().show();
        }
        Button btnAplicar = (Button)mainView.findViewById(R.id.btnAplicar);
        btnAplicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Util.setProperty("URL_WS", txtURL_WS.getText().toString(), context);
                    Util.setProperty("URL_WS_SYNCRO", txtURL_WS_SYNCRO.getText().toString(), context);
                    Util.setProperty("CALIDAD_IMG", txtCalidadImagen.getText().toString(), context);
                    AppDialog.this.cancel();
                }catch (IOException e){
                    dlgAlert.setMessage(e.getMessage());
                    dlgAlert.create().show();
                }
            }
        });
        addContentView(mainView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }
}