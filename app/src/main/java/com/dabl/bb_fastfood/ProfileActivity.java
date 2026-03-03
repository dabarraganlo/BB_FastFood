package com.dabl.bb_fastfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    Button btnVolverPerfil, btnActualizarPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btnVolverPerfil = findViewById(R.id.btnVolverPerfil);
        btnActualizarPerfil = findViewById(R.id.btnActualizarPerfil);

        btnVolverPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnActualizarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Simula actualizacion del perfil y regresa al menu
                Intent intent = new Intent(ProfileActivity.this, HomeMenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}