package com.antoniohorrillo.devage.model;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
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
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by antoniohh on 18/09/16.
 */
public class ApiRestful extends Dao {

    /**
     * Dirección del servidor Api REST.
     */
    private static final String URL = "http://devage.antoniohorrillo.com";

    /**
     * Atributos.
     */
    private static final String DB = "developers";
    private static final String TAGL = "getLista";
    private static final String TAGA = "setDev";
    private Context context;
    private boolean respuesta;
    private String estado;
    private String mensaje;
    private Developer dev;

    /**
     *
     * @param context
     * @param version
     */
    public ApiRestful(Context context, int version) {
        super(context, DB, version);
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    /**
     *
     */
    public void getLista() {

        /**
         * Definimos la barra de progreso de la conexión.
         */
        final ProgressDialog progressDialog = new ProgressDialog(context, R.style.DevAgeTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(context.getString(R.string.txtconect));
        progressDialog.show();

        /**
         * Objeto Json con los datos enviados al servidor.
         */
        JSONObject js = new JSONObject();
        try {
            js.put("operacion", "listado");
        }catch (JSONException e) {
            e.printStackTrace();
        }

        /**
         * Establecemos la conexión con el servicio REST.
         */
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, URL, js, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAGL, response.toString());
                for (int i = 0; i < response.length(); i++) {
                    try {
                        String format = "developer"+Integer.toString(i);
                        dev = new Developer(response.getJSONObject(format).getString("dni"),
                                response.getJSONObject(format).getString("nombre"),
                                response.getJSONObject(format).getString("apellidos"),
                                response.getJSONObject(format).getString("email"),
                                response.getJSONObject(format).getString("especialidad")
                        );

                        addDev(dev.getDni(), dev.getNombre(), dev.getApellidos(), dev.getEmail(), dev.getEspecialidad());

                        respuesta = true;

                    } catch (JSONException e) {
                        e.printStackTrace();
                        respuesta = false;
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d(TAGL, "Error: " + volleyError.getMessage());
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
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjReq);
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        if(respuesta){
                            Toast.makeText(context,R.string.txtlistadosi,Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context,R.string.txtlistadono,Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                }, 6000);
    }

    /**
     *
     * @param dni
     * @param nombre
     * @param apellidos
     * @param email
     * @param especialidad
     */
    public void setDev(String dni, String nombre, String apellidos, String email, String especialidad) {

        /**
         * Creamos el objeto de la clase Developer con los nuevos datos.
         */
        dev = new Developer(dni, nombre, apellidos, email, especialidad);

        /**
         * Definimos la barra de progreso de la conexión.
         */
        final ProgressDialog progressDialog = new ProgressDialog(context, R.style.DevAgeTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(context.getString(R.string.txtconect));
        progressDialog.show();

        /**
         * Objeto Json con los datos enviados al servidor.
         */
        JSONObject js = new JSONObject();
        try {
            js.put("operacion", "add");
            js.put("dni", dev.getDni());
            js.put("nombre", dev.getNombre());
            js.put("apellidos", dev.getApellidos());
            js.put("email", dev.getEmail());
            js.put("especialidad", dev.getEspecialidad());
        }catch (JSONException e) {
            e.printStackTrace();
        }

        /**
         * Establecemos la conexión con el servicio REST.
         */
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,URL, js, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAGA, response.toString());
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
                Log.d(TAGA, "Error: " + volleyError.getMessage());
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
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjReq);

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        if(estado.equals("Correcto")){
                            Toast.makeText(context,mensaje,Toast.LENGTH_SHORT).show();
                            Toast.makeText(context,R.string.txtreinicio,Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(context,mensaje,Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                }, 1000);
    }
}
