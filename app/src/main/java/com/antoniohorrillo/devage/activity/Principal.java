package com.antoniohorrillo.devage.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
import com.antoniohorrillo.devage.model.Developer;
import com.antoniohorrillo.devage.model.Preferencias;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Principal extends AppCompatActivity implements
        Principal_Fragment_Lista.OnFragmentInteractionListener,
        Principal_Fragment_Add.OnFragmentInteractionListener,
        Principal_Fragment_Detalle.OnFragmentInteractionListener,
        Principal_Fragment_Buscar.OnFragmentInteractionListener,
        Principal_Fragment_Acerca.OnFragmentInteractionListener {

    /**
     * Dirección del servidor Api REST.
     */
    private static final String URL = "http://devage.antoniohorrillo.com";

    /**
     * Variables.
     */
    private static final String TAGL = "Lista";
    private static final String TAGA = "Add";
    private static final String RESPUESTA = "OK";
    private Toolbar mToolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private Fragment fragment = null;
    private boolean fragmentTransaction = false;
    private ArrayList<Developer> listado = new ArrayList<Developer>();
    private ArrayList<Developer> developers = new ArrayList<Developer>();
    private Developer dev;
    private String estado;
    private String mensaje;
    private TextView txtemailmenu;
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        developers = developers();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        /** Código comentado. No realiza función.
        //Eventos del Drawer Layout
        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
            }
            @Override
            public void onDrawerOpened(View drawerView) {
            }
            @Override
            public void onDrawerClosed(View drawerView) {
            }
            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
        */

        navView = (NavigationView)findViewById(R.id.navview);
        navView.setNavigationItemSelectedListener(
            new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_seccion_1:
                        Log.i("Menú Lateral", "Lista");
                        /** Código comentado. No realiza función.
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("developers", (Serializable) developers);
                        fragment = new Principal_Fragment_Listado();
                        fragment.setArguments(bundle);
                        */
                        fragment = Principal_Fragment_Lista.newInstance(developers,RESPUESTA);
                        fragmentTransaction = true;
                        break;
                    case R.id.menu_seccion_2:
                        Log.i("Menú Lateral", "Añadir");
                        fragment = Principal_Fragment_Add.newInstance(RESPUESTA);
                        fragmentTransaction = true;
                        break;
                    case R.id.menu_seccion_3:
                        Log.i("Menú Lateral", "Buscar");
                        fragment = Principal_Fragment_Buscar.newInstance(RESPUESTA);
                        fragmentTransaction = true;
                        break;
                    case R.id.menu_seccion_4:
                        Log.i("Menú Lateral", "Acerca de");
                        fragment = Principal_Fragment_Acerca.newInstance(RESPUESTA);
                        fragmentTransaction = true;
                        break;
                    case R.id.menu_opcion_1:
                        Log.i("Menú Lateral", "LogOut");
                        logOut();
                        break;
                    case R.id.menu_opcion_2:
                        Log.i("Menú Lateral", "Salir");
                        salir();
                        break;
                }
                if(fragmentTransaction) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_frame, fragment)
                            .commit();
                    menuItem.setChecked(true);
                    getSupportActionBar().setTitle(menuItem.getTitle());
                }
                drawerLayout.closeDrawers();
                return true;
                }
            });

        /** Código comentado. No realiza función.
        fragment = Principal_Fragment_Acerca.newInstance(RESPUESTA);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
        getSupportActionBar().setTitle(getResources().getString(R.string.seccion_4));
        navView.setCheckedItem(R.id.menu_seccion_4);
         */

        /**
         * Inicio en Fragment Acerca De.
         */
        navView.getMenu().performIdentifierAction(R.id.menu_seccion_4, 0);

        /**
         * Email de Usuario en Menú Lateral. Lo obtenemos de la clase Preferencias.
         */
        View header=navView.getHeaderView(0);
        txtemailmenu = (TextView)header.findViewById(R.id.txtemailmenu);
        String[] emailmenulateral;
        emailmenulateral = Preferencias.getUserName(this).split("@");
        txtemailmenu.setText(emailmenulateral[0]);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /** Código comentado. No realiza función.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menuadd) {
            //insertarAlumno(findViewById(R.id.btnactivityinsertar));
            //return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.menubuscar) {
            //BuscarAlumno(findViewById(R.id.btnactivitybuscar));
            //return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.menusalir) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
    */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Log Out y Redirección a la Portada.
     */
    public void logOut() {
        Preferencias.clearUserName(this);
        Intent intent = new Intent(getApplicationContext(), Portada.class);
        startActivity(intent);
        this.finish();
    }

    /**
     * Salir de la aplicación Sin Realizar Log Out.
     */
    public void salir() {
        this.finish();
    }

    /**
     *
     */
    public ArrayList developers() {

        /**
         * Definimos la barra de progreso de la conexión.
         */
        final ProgressDialog progressDialog = new ProgressDialog(Principal.this, R.style.DevAgeTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getResources().getString(R.string.txtconect));
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
                listado.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        String format = "developer"+Integer.toString(i);
                        dev = new Developer(response.getJSONObject(format).getString("dni"),
                                response.getJSONObject(format).getString("nombre"),
                                response.getJSONObject(format).getString("apellidos"),
                                response.getJSONObject(format).getString("email"),
                                response.getJSONObject(format).getString("especialidad"));
                        listado.add(dev);
                    } catch (JSONException e) {
                        e.printStackTrace();
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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjReq);
        new android.os.Handler().postDelayed(
            new Runnable() {
                public void run() {
                    if(!listado.isEmpty()){
                        Toast.makeText(Principal.this,R.string.txtlistadosi,Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(Principal.this,R.string.txtlistadono,Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
            }, 1000);
        return listado;
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onFragmentInteractionLista (int posicion) {
        // TODO: you can leave it empty
        fragment = Principal_Fragment_Detalle.newInstance(developers.get(posicion).getDni(),
            developers.get(posicion).getNombre(), developers.get(posicion).getApellidos(),
            developers.get(posicion).getEmail(), developers.get(posicion).getEspecialidad());
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();

        /** Código comentado. No realiza función.
        ArticleFragment newFragment = new ArticleFragment();
        Bundle args = new Bundle();
        args.putInt(ArticleFragment.ARG_POSITION, position);
        newFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);
        */
    }

    public void onFragmentInteractionBuscar (String dni) {
        // TODO: you can leave it empty
        for (int i = 0; i < developers.size(); i++) {
            if (developers.get(i).getDni().equals(dni)) {
                fragment = Principal_Fragment_Detalle.newInstance(developers.get(i).getDni(),
                        developers.get(i).getNombre(), developers.get(i).getApellidos(),
                        developers.get(i).getEmail(), developers.get(i).getEspecialidad());
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
                return;
            }
        }
        Toast.makeText(Principal.this, "DNI Incorrecto o Inexistente: "+dni, Toast.LENGTH_SHORT).show();
    }

    public void onFragmentInteractionAdd(String txtodni, String txtonombre, String txtoapellidos,
                                         String txtoemail, String txtoespecialidad) {
        // TODO: you can leave it empty

        /**
         * Definimos la barra de progreso de la conexión.
         */
        final ProgressDialog progressDialog = new ProgressDialog(Principal.this, R.style.DevAgeTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getResources().getString(R.string.txtconect));
        progressDialog.show();

        /**
         * Objeto Json con los datos enviados al servidor.
         */
        JSONObject js = new JSONObject();
        try {
            js.put("operacion", "add");
            js.put("dni", txtodni);
            js.put("nombre", txtonombre);
            js.put("apellidos", txtoapellidos);
            js.put("email", txtoemail);
            js.put("especialidad", txtoespecialidad);
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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjReq);

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        if(estado.equals("Correcto")){
                            Toast.makeText(Principal.this,mensaje,Toast.LENGTH_LONG).show();
                            developers = developers();
                        }else{
                            Toast.makeText(Principal.this,mensaje,Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                }, 1000);
    }

    public void onFragmentInteractionDetalle () {
        // TODO: you can leave it empty
    }

    public void onFragmentInteractionAcerca () {
        // TODO: you can leave it empty
    }
}