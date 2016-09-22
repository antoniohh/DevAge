package com.antoniohorrillo.devage.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;

/**
 * Created by antoniohh on 15/09/16.
 */
public class Dao extends SQLiteOpenHelper {

    /**
     * Atributos.
     */
    private static final String TAG = "Dao";
    private static final String TABLA = "dev_age_dev";
    private static final String[] COLSID = {"id"};
    private static final String[] COLSNOMAPE = {"nombre", "apellidos"};
    private static final String[] COLSTODO = {"dni", "nombre", "apellidos", "email", "especialidad"};
    private ArrayList<String> lista = new ArrayList<String>();
    private int version;

    /**
     * Constructor de la clase Dao.
     * @param context
     * @param db
     * @param version
     */
    public Dao (Context context, String db, int version) {
        super(context, db, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE dev_age_dev (" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " dni TEXT NOT NULL," +
                " nombre TEXT NOT NULL," +
                " apellidos TEXT NOT NULL," +
                " email TEXT NOT NULL," +
                " especialidad TEXT NOT NULL)");
        Log.d(TAG, "Tabla "+TABLA+" creada.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLA);
            Log.d(TAG, "Tabla "+TABLA+" eliminada.");
            onCreate(db);
        }

    }

    public int getVersion() {
        SQLiteDatabase db = this.getReadableDatabase();
        version = db.getVersion();
        return version;
    }

    public void setVersion(int version) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.setVersion(version);
    }

    /**
     *
     * @param dni
     * @param nombre
     * @param apellidos
     * @param email
     * @param especialidad
     */
    public void addDev(String dni, String nombre, String apellidos, String email, String especialidad) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (db != null) {
            ContentValues datos = new ContentValues();
            datos.put("dni", dni);
            datos.put("nombre", nombre);
            datos.put("apellidos", apellidos);
            datos.put("email", email);
            datos.put("especialidad", especialidad);
            long respuesta = db.insert(TABLA, null, datos);
            if (respuesta != 0) {
                Log.d(TAG, "Developer Insertado");
            } else {
                Log.d(TAG, "Developer No Insertado");
            }
            db.close();
        } else {
            Log.d(TAG, "Error en la conexión con la base de datos.");
        }
    }

    /**
     *
     * @return
     */
    public ArrayList<String> listaDev() {
        SQLiteDatabase db = this.getWritableDatabase();
        lista.clear();
        Cursor cursor = db.query(TABLA, COLSNOMAPE, null, null, null, null, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    lista.add(cursor.getString(0)+" "+cursor.getString(1));
                } while (cursor.moveToNext());
                Log.d(TAG, "Listado Generado desde SQLite al FragmentList.");
            }
            else {
                Log.d(TAG, "Error Obteniendo el Listado.");
            }
        }
        catch (NullPointerException ex) {
            System.err.println(ex);
        }
        return lista;
    }

    /**
     *
     * @param where
     * @return
     */
    public Cursor detalleDev(String where) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLA, COLSTODO, where, null, null, null, null);
        return cursor;
    }

    /**
     *
     * @return
     */
    public boolean contenidoDb() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLA, COLSID, null, null, null, null, null);
        Boolean existe = false;
        try {
            if(cursor.moveToFirst()) {
                existe = true;
                Log.d(TAG, "Existen datos en la tabla: "+TABLA);
            }
            else {
                Log.d(TAG, "No existen datos en la tabla: "+TABLA);
            }
        }
        catch (NullPointerException ex) {
            System.err.println(ex);
        }
        return existe;
    }
}
