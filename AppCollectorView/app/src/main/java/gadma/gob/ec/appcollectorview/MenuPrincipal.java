package gadma.gob.ec.appcollectorview;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import ec.gob.gadma.obj.ObjUser;

public class MenuPrincipal extends AppCompatActivity {
    private EditText txtUsuarioConnect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        //Localizar los controles
        txtUsuarioConnect = (EditText)findViewById(R.id.txtUsuarioConnect);
        //Recuperamos la información pasada en el intent
        Bundle bundle = this.getIntent().getExtras();
        if(bundle!= null) {
            //Construimos el mensaje a mostrar
            txtUsuarioConnect.setText(bundle.getString("NOMBRE"));
        }else{
            ObjUser obj = ObjUser.getInstance();
            txtUsuarioConnect.setText(obj.getUserConnect());
        }

        LinearLayout btnConsultas = (LinearLayout)findViewById(R.id.btnConsultas);
        btnConsultas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Aqui llamo a la otra ventana

                //Creamos el Intent
                Intent intent =
                        new Intent(MenuPrincipal.this, MainActivity.class);

                //Creamos la información a pasar entre actividades
                Bundle b = new Bundle();
                b.putString("NOMBRE", txtUsuarioConnect.getText().toString());

                //Añadimos la información al intent
                intent.putExtras(b);

                //Iniciamos la nueva actividad
                startActivity(intent);
            }
        });
        LinearLayout btnMigrar = (LinearLayout)findViewById(R.id.btnMigrar);
        btnMigrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =
                        //new Intent(MenuPrincipal.this, MigrarActivity.class);
                        new Intent(MenuPrincipal.this, SyncronizarActivity.class);
                //Iniciamos la nueva actividad
                startActivity(intent);
            }
        });

        LinearLayout btnSalirApp = (LinearLayout)findViewById(R.id.btnSalirApp);
        btnSalirApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(MenuPrincipal.this, LoginActivity.class);
                loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(loginIntent);
                finish();
            }
        });
    }
}
