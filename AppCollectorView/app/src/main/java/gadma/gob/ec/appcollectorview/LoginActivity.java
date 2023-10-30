package gadma.gob.ec.appcollectorview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Intent;


import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import ec.gob.gadma.obj.ObjUser;
import ec.gob.gadma.serv.AsyncCallWS;
import ec.gob.gadma.serv.AsyncCallWSRest;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity /*implements LoaderCallbacks<Cursor>*/
{

    //private UserLoginTask mAuthTask = null;
    AlertDialog.Builder dlgAlert;
    // UI references.
    private AutoCompleteTextView txtUsuarioLogin;
    private EditText txtPassword;
    private View mProgressView;
    private View mLoginFormView;

    protected boolean shouldAskPermissions() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }
    protected void askPermissions() {
        String[] permissions = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE"
        };
        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (shouldAskPermissions()) {
            askPermissions();
        }
        dlgAlert = new AlertDialog.Builder(this);
        //dlgAlert.setMessage("La clave catastral no corresponde a un predio de Tipo Urbano de Parroquia");
        dlgAlert.setTitle("Mensaje de Error...");
        dlgAlert.setCancelable(false);
        dlgAlert.setPositiveButton(android.R.string.ok,null);
        // Set up the login form.
        txtUsuarioLogin = (AutoCompleteTextView) findViewById(R.id.txtUsuario);
        txtUsuarioLogin.setText(null);
        //populateAutoComplete();

        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtPassword.setText(null);
        txtPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        LinearLayout btnAcceder = (LinearLayout) findViewById(R.id.btnAcceder);
        btnAcceder.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        //if(isOnlineNet()) {
        // Reset errors.
        txtUsuarioLogin.setError(null);
        txtPassword.setError(null);

        // Store values at the time of the login attempt.
        String usuario = txtUsuarioLogin.getText().toString();
        String password = txtPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid email address.
        if (TextUtils.isEmpty(usuario)) {
            txtUsuarioLogin.setError("Debe registrar el usuario");
            focusView = txtUsuarioLogin;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            //mAuthTask = new UserLoginTask(usuario, password);

            //llamo al ws para obtener los parametros
            ObjUser objUser = ObjUser.getInstance();
            objUser.setUserConnect(usuario);
            objUser.setUserPassword(password);
            HashMap<String,Object> hmParam=new HashMap<String, Object>();
            hmParam.put("inUser", usuario);
            hmParam.put("inPassword",password);
            AsyncCallWS objAsync = new AsyncCallWS(this,"validarUsuario",hmParam);

            //mAuthTask.execute((Void) null);
            try {
                String valido = objAsync.execute().get();//mAuthTask.execute().get();
                //String valido ="true";
                showProgress(false);
                //Aqui llamo a la otra ventana
                if (valido != null && valido.equalsIgnoreCase("true")) {
                    //Creamos el Intent
                    Intent intent =
                            //new Intent(LoginActivity.this, MainActivity.class);
                            new Intent(LoginActivity.this, MenuPrincipal.class);
                    ObjUser obj = ObjUser.getInstance();
                    obj.setUserConnect(usuario);
                    obj.setUserPassword(password);
                    //Creamos la información a pasar entre actividades
                    Bundle b = new Bundle();
                    b.putString("NOMBRE", txtUsuarioLogin.getText().toString());

                    //Añadimos la información al intent
                    intent.putExtras(b);

                    //Iniciamos la nueva actividad
                    startActivity(intent);
                } else {
                    if (valido != null) {
                        if (valido.contains("NO EXISTE EL USUARIO")) {
                            txtUsuarioLogin.setError("El usuario no existe");
                            txtUsuarioLogin.requestFocus();
                        }
                        if (valido.contains("INGRESADA NO ES CORRECTA")) {
                            txtPassword.setError("La clave registrada no es la correcta");
                            txtPassword.requestFocus();
                        }
                        if(valido.contains("ERROR: expected: START_TAG {http://schemas.xmlsoap.org/soap/envelope/}Envelope (position:START_TAG <html>@1:7 in java.io.InputStreamReader@")){
                            dlgAlert.setTitle("Error de validación");
                            dlgAlert.setMessage("Datos erroneos. Por favor ingresa un usuario y una contraseña correcta");
                            dlgAlert.create().show();
                        }else {
                            dlgAlert.setMessage(valido);
                            dlgAlert.create().show();
                        }
                    } else {
                        dlgAlert.setMessage("Existe problemas con los parametros de conexión con el Servicio Web. Por favor verifique los parametros de conexión");
                        dlgAlert.create().show();
                    }
                }
            } catch (InterruptedException e) {
                dlgAlert.setMessage(e.getMessage());
                dlgAlert.create().show();
            } catch (ExecutionException e) {
                dlgAlert.setMessage(e.getMessage());
                dlgAlert.create().show();
            } catch (Exception e) {
                dlgAlert.setMessage(e.getMessage());
                dlgAlert.create().show();
            }
        }
        /*}else{
            dlgAlert.setTitle("Error.");
            dlgAlert.setMessage("No existe conexión. Por favor verifique que tenga activo un plan de datos y verifique que tenga conexión a internet");
            dlgAlert.create().show();
        }*/
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

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
    public Boolean isOnlineNet() {
        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com");

            int val           = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;

        } catch (Exception e) {
            dlgAlert.setMessage(e.getMessage());
            dlgAlert.create().show();
            return false;
        }
        //return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.paramUrlWebService:
                AppDialog dialog = new AppDialog(LoginActivity.this);
                dialog.show();
                /*DialogFragment newFragment = new CustomDialogFragment();
                newFragment.show(getSupportFragmentManager(), "CustomDialogFragment");*/
                Toast.makeText(this, "reset", Toast.LENGTH_LONG).show();
                return(true);
        }
        return(super.onOptionsItemSelected(item));
    }
}

