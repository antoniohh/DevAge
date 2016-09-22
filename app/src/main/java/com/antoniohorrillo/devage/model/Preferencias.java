package com.antoniohorrillo.devage.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by antoniohh on 25/08/16.
 */
public class Preferencias {

    /**
     * Atributos.
     */
    static final String PREF_USER_NAME = "username";
    static final String PREF_DB_VERSION = "dbversion";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    /**
     * Establecemos el email de usuario y lo almacenamos en un archivo xml.
     * @param ctx
     * @param userName
     */
    public static void setUserName(Context ctx, String userName) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.commit();
    }

    /**
     * Establecemos la version de la base de datos SQLite y lo almacenamos en un archivo xml.
     * @param ctx
     * @param dbVersion
     */
    public static void setDbVersion(Context ctx, String dbVersion) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_DB_VERSION, dbVersion);
        editor.commit();
    }

    /**
     * Obtenemos el email del usuario almacenado en la preferencias.
     * @param ctx
     * @return
     */
    public static String getUserName(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }

    /**
     * Obtenemos la version de la base de datos SQLite almacenada en la preferencias.
     * @param ctx
     * @return
     */
    public static String getDbVersion(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_DB_VERSION, "");
    }

    /**
     * Eliminamos las preferencias de usuario.
     * @param ctx
     */
    public static void clearUserName(Context ctx) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(PREF_USER_NAME);
        //editor.clear(); //clear all stored data
        editor.commit();
    }
}
