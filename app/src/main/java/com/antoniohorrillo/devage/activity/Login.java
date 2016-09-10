package com.antoniohorrillo.devage.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.antoniohorrillo.devage.R;
import com.antoniohorrillo.devage.model.Preferencias;
import com.antoniohorrillo.devage.model.Cifrado;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    /**
     * Dirección del servidor Api REST.
     */
    private static final String URL = "http://devage.antoniohorrillo.com";

    /**
     * Variables.
     */
    private static final String TAG = "Login";
    private Button btnlogin;
    private TextView linksignup;
    private TextInputLayout cmpemail;
    private TextInputLayout cmppassword;
    private String estado="";
    private String mensaje="";
    private String txtemail;
    private String txtpassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //TODO: Invocamos al método que gestiona la animación del logo.
        animaciones();

        //TODO: Asginamos las variables del botón y del link y gestionamos los listeners.
        btnlogin = (Button)findViewById(R.id.btn_login);
        linksignup = (TextView)findViewById(R.id.link_signup);
        btnlogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });
        linksignup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), Signup.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    public void login() {

        /**
         * Asignamos las Variables.
         */
        cmpemail = (TextInputLayout)findViewById(R.id.cmp_email_wrapper);
        cmppassword = (TextInputLayout)findViewById(R.id.cmp_password_wrapper);
        txtemail = cmpemail.getEditText().getText().toString();
        txtpassword = cmppassword.getEditText().getText().toString();

        /**
         * Mensaje de LOG.
         */
        Log.d(TAG, "Realizando un Inicio de Sesion Login");

        /**
         * Realizamos la validación de los datos. Si no se validan lanzamos un "onloginfailed" y
         * volvemos a pedir la introducción de datos. Si es correcta, continuamos.
         */
        if (!validar()) {
            onLoginFailed();
            return;
        }

        ocultarTeclado();
        btnlogin.setEnabled(false);

        /**
         * Definimos la barra de progreso de la conexión.
         */
        final ProgressDialog progressDialog = new ProgressDialog(Login.this, R.style.DevAgeTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getResources().getString(R.string.txtconect));
        progressDialog.show();

        /**
         * Encriptamos la contraseña.
         */
        Cifrado cifrado = new Cifrado();
        String passwordSha1 = cifrado.getSha1(txtpassword);

        /**
         * Objeto Json con los datos enviados al servidor.
         */
        JSONObject js = new JSONObject();
        try {
            js.put("operacion", "login");
            js.put("email", txtemail);
            js.put("password", passwordSha1);
        }catch (JSONException e) {
            e.printStackTrace();
        }

        /**
         * Establecemos la conexión con el servicio REST.
         */
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,URL, js, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                try {
                    response = response.getJSONObject("resultado");
                    estado = response.getString("estado");
                    mensaje = response.getString("mensaje");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d(TAG, "Error: " + volleyError.getMessage());
                if (volleyError instanceof TimeoutError) {
                }
            }
        }) {

            /**
             * Parseamos el header de la petición POST especificando JSON.
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

            /**
             * Establecemos la prioridad de la petición en la cola de peticiones.
             * @return
             */
            @Override
            public Priority getPriority() {
                return Priority.IMMEDIATE;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjReq);

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        if(estado.equals("Correcto")){
                            Toast.makeText(Login.this,mensaje,Toast.LENGTH_SHORT).show();
                            onLoginSuccess();
                        }else{
                            Toast.makeText(Login.this,mensaje,Toast.LENGTH_SHORT).show();
                            onLoginFailed();
                        }
                        progressDialog.dismiss();
                    }
                }, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {

            // TODO: Registro correcto, datos de login correctos pasados desde el formulario de
            // TODO: registro al formulario de login.
            cmpemail = (TextInputLayout)findViewById(R.id.cmp_email_wrapper);
            cmppassword = (TextInputLayout)findViewById(R.id.cmp_password_wrapper);
            cmpemail.getEditText().setText(data.getStringExtra("email").toString());
            cmppassword.getEditText().setText(data.getStringExtra("password").toString());
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        btnlogin.setEnabled(true);
        Toast.makeText(getBaseContext(), R.string.logincorrecto, Toast.LENGTH_SHORT).show();

        // TODO:  Guardamos el email de usuario en las preferencias de usuario, Shared Preferences.
        // TODO:  Podremos establecer el usuario, consultarlo y eliminarlo.
        Preferencias.setUserName(this,cmpemail.getEditText().getText().toString());

        // TODO:  Procedemos al cambio de Activity.
        Intent intent = new Intent(getApplicationContext(), Principal.class);
        //intent.putExtra("email",cmpemail.getEditText().getText().toString());
        startActivity(intent);
        this.finish();
    }

    public void onLoginFailed() {
        btnlogin.setEnabled(true);
        Toast.makeText(getBaseContext(), R.string.loginincorrecto, Toast.LENGTH_SHORT).show();
    }

    /**
     * Método de validación de los campos de texto introducidos por el usuario.
     *
     * @return
     */
    public boolean validar() {

        /**
         * Asignamos las Variables.
         */
        txtemail = cmpemail.getEditText().getText().toString();
        txtpassword = cmppassword.getEditText().getText().toString();
        boolean valid = true;

        if (txtemail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(txtemail).matches()) {
            cmpemail.setError(getResources().getString(R.string.errorloginemail));
            valid = false;
        } else {
            cmpemail.setError(null);
        }

        if (txtpassword.isEmpty() || txtpassword.length() < 4 || txtpassword.length() > 10) {
            cmppassword.setError(getResources().getString(R.string.errorloginpass));
            valid = false;
        } else {
            cmppassword.setError(null);
        }

        return valid;
    }

    /**
     * Método para ocultar el teclado.
     */
    private void ocultarTeclado() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * Animación del logo de la aplicación. Modificación de escala de 0f a 1.0f.
     */
    public void animaciones() {
        //TODO: Variable que contiene el logo.
        ImageView img = (ImageView) findViewById(R.id.logo);

        //TODO: Establecemos la escala a 0f.
        img.setScaleX(0f);
        img.setScaleY(0f);

        //TODO: Definimos el tipo de animación escala y el valor final de la misma sobre el logo.
        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(img, "scaleX", 1.0f);
        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(img, "scaleY", 1.0f);

        //TODO: Establecemos la duración de la animación.
        scaleDownX.setDuration(2000);
        scaleDownY.setDuration(2000);

        //TODO: Creamos un set con las dos animaciones.
        AnimatorSet scaleDown = new AnimatorSet();

        //TODO: Establecemos la sincronización de las dos animaciones y las iniciamos.
        scaleDown.play(scaleDownX).with(scaleDownY);
        scaleDown.start();
    }
}