package com.antoniohorrillo.devage.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.antoniohorrillo.devage.model.Cifrado;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {

    /**
     * Dirección del servidor Api REST.
     */
    private static final String URL = "http://devage.antoniohorrillo.com";

    /**
     * Variables.
     */
    private static final String TAG = "Signup";
    private Button btnsignup;
    private TextView linklogin;
    private TextInputLayout cmpoemail;
    private TextInputLayout cmpopassword;
    private String txtoemail;
    private String txtopassword;
    private String estado="";
    private String mensaje="";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //TODO: Invocamos al método que gestiona la animación del logo.
        animaciones();

        //TODO: Asginamos las variables del botón y del link y gestionamos los listeners.
        btnsignup = (Button)findViewById(R.id.btn_signup);
        linklogin = (TextView)findViewById(R.id.link_login);
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
        linklogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    public void signup() {

        /**
         * Asignamos las Variables.
         */
        cmpoemail = (TextInputLayout)findViewById(R.id.cmpo_email_wrapper);
        cmpopassword = (TextInputLayout)findViewById(R.id.cmpo_password_wrapper);
        txtoemail = cmpoemail.getEditText().getText().toString();
        txtopassword = cmpopassword.getEditText().getText().toString();

        /**
         * Mensaje de LOG.
         */
        Log.d(TAG, "Signup");

        /**
         * Realizamos la validación de los datos. Si no se validan lanzamos un "onsignupfailed" y
         * volvemos a pedir la introducción de datos. Si es correcta, continuamos.
         */
        if (!validar()) {
            onSignupFailed();
            return;
        }

        ocultarTeclado();
        btnsignup.setEnabled(false);

        /**
         * Definimos la barra de progreso de la conexión.
         */
        final ProgressDialog progressDialog = new ProgressDialog(Signup.this, R.style.DevAgeTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getResources().getString(R.string.txtconect));
        progressDialog.show();

        /**
         * Encriptamos la contraseña.
         */
        Cifrado cifrado = new Cifrado();
        String passwordSha1 = cifrado.getSha1(txtopassword);

        /**
         * Objeto Json con los datos enviados al servidor.
         */
        JSONObject js = new JSONObject();
        try {
            js.put("operacion", "signup");
            js.put("email", txtoemail);
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
                            Toast.makeText(Signup.this,mensaje,Toast.LENGTH_SHORT).show();
                            onSignupSuccess();
                        }else{
                            Toast.makeText(Signup.this,mensaje,Toast.LENGTH_SHORT).show();
                            onSignupFailed();
                        }
                        progressDialog.dismiss();
                    }
                }, 1000);
    }

    public void onSignupSuccess() {
        btnsignup.setEnabled(true);
        Toast.makeText(getBaseContext(), R.string.signupcorrecto, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), Login.class);
        intent.putExtra("email", cmpoemail.getEditText().getText().toString());
        intent.putExtra("password", cmpopassword.getEditText().getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }

    public void onSignupFailed() {
        btnsignup.setEnabled(true);
        Toast.makeText(getBaseContext(), R.string.signupincorrecto, Toast.LENGTH_SHORT).show();
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
        txtoemail = cmpoemail.getEditText().getText().toString();
        txtopassword = cmpopassword.getEditText().getText().toString();
        boolean valid = true;

        if (txtoemail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(txtoemail).matches()) {
            cmpoemail.setError(getResources().getString(R.string.errorloginemail));
            valid = false;
        } else {
            cmpoemail.setError(null);
        }

        if (txtopassword.isEmpty() || txtopassword.length() < 4 || txtopassword.length() > 10) {
            cmpopassword.setError(getResources().getString(R.string.errorloginpass));
            valid = false;
        } else {
            cmpopassword.setError(null);
        }

        return valid;
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

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
