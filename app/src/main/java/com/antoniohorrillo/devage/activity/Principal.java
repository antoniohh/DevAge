package com.antoniohorrillo.devage.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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
import com.antoniohorrillo.devage.R;
import com.antoniohorrillo.devage.model.Preferencias;
import com.antoniohorrillo.devage.model.ApiRestful;

public class Principal extends AppCompatActivity implements
        Principal_Fragment_Lista.OnFragmentInteractionListener,
        Principal_Fragment_Add.OnFragmentInteractionListener,
        Principal_Fragment_Detalle.OnFragmentInteractionListener,
        Principal_Fragment_Buscar.OnFragmentInteractionListener,
        Principal_Fragment_Acerca.OnFragmentInteractionListener {

    /**
     * Atributos.
     */
    private static final String RESPUESTA = "OK";
    private Toolbar mToolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private Fragment fragment = null;
    private boolean fragmentTransaction = false;
    private TextView txtemailmenu;
    private String[] emailmenulateral;
    private int oldDbVersion;
    private int newDbVersion;
    private ApiRestful api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        //TODO: Sincronizamos Persistencia local en SQLite y conexión a Servicio Web RESTful PHP MySql.
        sincronizacion();

        //TODO: Instanciamos la barra de título, toolbar.
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //TODO: Instanciamos el menú lateral.
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        navView = (NavigationView)findViewById(R.id.navview);
        navView.setNavigationItemSelectedListener(
            new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_seccion_1:
                        Log.i("Menú Lateral", "Lista");
                        fragment = Principal_Fragment_Lista.newInstance(api.listaDev(),RESPUESTA);
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

        //TODO: Inicio en Fragment Acerca De.
        navView.getMenu().performIdentifierAction(R.id.menu_seccion_4, 0);

        //TODO: Email de Usuario en Menú Lateral. Lo obtenemos de la clase Preferencias.
        View header=navView.getHeaderView(0);
        txtemailmenu = (TextView)header.findViewById(R.id.txtemailmenu);
        emailmenulateral = Preferencias.getUserName(this).split("@");
        txtemailmenu.setText(emailmenulateral[0]);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_search:
                navView.getMenu().performIdentifierAction(R.id.menu_seccion_3, 0);
                return true;
            case R.id.action_sinc:
                sincronizacion();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        // moveTaskToBack(true);
        //TODO: Cuadro de diálogo de advertencia al salir de la aplicación.
        //TODO: Constructor del cuadro de diálogo.
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        //TODO: Título del cuadro de diálogo.
        alertDialog.setTitle(getResources().getString(R.string.diagsalirtitulo));
        //TODO: Mensaje del cuadro de diálogo.
        alertDialog.setMessage(getResources().getString(R.string.diagsalirmensaje));
        //TODO: Icono del cuadro de diálogo.
        alertDialog.setIcon(R.drawable.ic_action_agenda);
        //TODO: Botón Si.
        alertDialog.setPositiveButton("Si",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        //TODO: Botón No.
        alertDialog.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        //TODO: Mostramos el cuadro de diálogo.
        alertDialog.show();
    }

    /**
     * Buscamos el developer indicado en el fragmentlist a traves de su posición. La posición en el
     * fragmentlist es equivalente a su id en la base de datos local SQLite con una diferencia de
     * una unidad. Realizamos la consulta y enviamos los datos al fragmen detalle.
     * @param posicion
     */
    public void onFragmentInteractionLista (int posicion) {
        // TODO: you can leave it empty
        int id = posicion+1;
        String where = "id="+id;
        Cursor cursor = api.detalleDev(where);
        try {
            if (cursor.moveToNext()) {
                fragment = Principal_Fragment_Detalle.newInstance(cursor.getString(0),
                        cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4));
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
                return;
            }
            Toast.makeText(Principal.this, "Error al obtener los detalles del Developer.", Toast.LENGTH_SHORT).show();
        }
        catch (NullPointerException ex) {
            System.err.println(ex);
        }
    }

    /**
     * Buscamos el developer por su dni, indicado en el campo de texto.
     * Realizamos la consulta y enviamos los datos al fragmen detalle.
     * @param dni
     */
    public void onFragmentInteractionBuscar (String dni) {
        // TODO: you can leave it empty
        String where = "dni="+"'"+dni+"'";
        Cursor cursor = api.detalleDev(where);
        try {
            if (cursor.moveToNext()) {
                fragment = Principal_Fragment_Detalle.newInstance(cursor.getString(0),
                        cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4));
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
                return;
            }
            Toast.makeText(Principal.this, "DNI Incorrecto o Inexistente: "+dni, Toast.LENGTH_SHORT).show();
        }
        catch (NullPointerException ex) {
            System.err.println(ex);
        }
    }

    /**
     * Realizamos la inserción de un nuevo developer en la base de datos del Servicio Web, utilizando
     * la Api RESTful y la libreria Volley. Incrementamos la version de la base de datos local SQLite,
     * provocando el proceso de update y sincronización.
     * @param dni
     * @param nombre
     * @param apellidos
     * @param email
     * @param especialidad
     */
    public void onFragmentInteractionAdd(String dni, String nombre, String apellidos, String email, String especialidad) {
        // TODO: you can leave it empty
        api.setDev(dni, nombre, apellidos, email, especialidad);
        //TODO: Nueva versión de base de datos SQLite.
        newDbVersion = Integer.parseInt(Preferencias.getDbVersion(this)) + 1;
        Preferencias.setDbVersion(this,Integer.toString(newDbVersion));
    }

    public void onFragmentInteractionDetalle () {
        // TODO: you can leave it empty
    }

    public void onFragmentInteractionAcerca () {
        // TODO: you can leave it empty
    }

    /**
     * Método sincronización.
     */
    public void sincronizacion() {
        if (Preferencias.getDbVersion(getApplicationContext()).length() == 0) {
            //TODO: Persistencia local en SQLite y conexion a Servicio Web RESTful PHP MySql.
            oldDbVersion = 1;
            Preferencias.setDbVersion(this,Integer.toString(oldDbVersion));
            api = new ApiRestful(this, oldDbVersion);
            if (!api.contenidoDb()) {
                api.getLista();
                Toast.makeText(Principal.this, R.string.txtsincok, Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(Principal.this, R.string.txtsincnonecesario, Toast.LENGTH_SHORT).show();
            }
            Log.d("Versión DB SQLite", Integer.toString(api.getVersion()));
            Log.d("Versión DB Preferencias", Preferencias.getDbVersion(this));
        }
        else {
            //TODO: Actualización SQLite y conexión a Servicio Web RESTful PHP MySql.
            oldDbVersion = Integer.parseInt(Preferencias.getDbVersion(this));
            api = new ApiRestful(this, oldDbVersion);
            if (!api.contenidoDb()) {
                api.getLista();
                Toast.makeText(Principal.this, R.string.txtsincok, Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(Principal.this, R.string.txtsincnonecesario, Toast.LENGTH_SHORT).show();
            }
            Log.d("Versión DB SQLite", Integer.toString(api.getVersion()));
            Log.d("Versión DB Preferencias", Preferencias.getDbVersion(this));
        }
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
}