package com.antoniohorrillo.devage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Portada extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portada);
        Button btnEntrar = (Button)findViewById(R.id.btn_entrar);
        btnEntrar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                entrar();
            }
        });
    }

    public void entrar() {
        if (SaveSharedPreference.getUserName(getApplicationContext()).length() == 0) {
            Intent intent = new Intent(getApplicationContext(), Principal.class);
            startActivity(intent);
            this.finish();
        }
        else {
            Intent intent = new Intent(getApplicationContext(), Principal.class);
            startActivity(intent);
            this.finish();
        }
    }
}
