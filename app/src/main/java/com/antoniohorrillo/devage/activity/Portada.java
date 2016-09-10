package com.antoniohorrillo.devage.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.antoniohorrillo.devage.R;
import com.antoniohorrillo.devage.model.Preferencias;

public class Portada extends AppCompatActivity {

    /**
     * Variables.
     */
    private Button btnentrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portada);

        //TODO: Invocamos al método que gestiona la animación del logo.
        animaciones();

        //TODO: Asginamos la variable del botón y gestionamos su listener.
        btnentrar = (Button)findViewById(R.id.btn_entrar);
        btnentrar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                entrar();
            }
        });
    }

    /**
     * Método entrar(). Comprobamos las preferencias de usuario, Shared Preferences si existe un
     * usuario, de existir dirigimos al activity Principal, de no existir, al activity Login.
     */
    public void entrar() {
        if (Preferencias.getUserName(getApplicationContext()).length() == 0) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            this.finish();
        }
        else {
            Intent intent = new Intent(getApplicationContext(), Principal.class);
            startActivity(intent);
            this.finish();
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
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
