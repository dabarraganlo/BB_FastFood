package com.dabl.bb_fastfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeMenuActivity extends AppCompatActivity {

    Button btnHamburguesaClasica, btnHamburguesaEspecial;
    Button btnPerroSencillo, btnPerroEspecial;
    Button btnCombo1, btnCombo2;
    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_menu);

        btnHamburguesaClasica = findViewById(R.id.btnHamburguesaClasica);
        btnHamburguesaEspecial = findViewById(R.id.btnHamburguesaEspecial);
        btnPerroSencillo = findViewById(R.id.btnPerroSencillo);
        btnPerroEspecial = findViewById(R.id.btnPerroEspecial);
        btnCombo1 = findViewById(R.id.btnCombo1);
        btnCombo2 = findViewById(R.id.btnCombo2);
        bottomNav = findViewById(R.id.bottomNav);

        bottomNav.setSelectedItemId(R.id.nav_menu);

        View.OnClickListener irADetalle = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeMenuActivity.this, ProductDetailActivity.class);
                startActivity(intent);
            }
        };

        btnHamburguesaClasica.setOnClickListener(irADetalle);
        btnHamburguesaEspecial.setOnClickListener(irADetalle);
        btnPerroSencillo.setOnClickListener(irADetalle);
        btnPerroEspecial.setOnClickListener(irADetalle);
        btnCombo1.setOnClickListener(irADetalle);
        btnCombo2.setOnClickListener(irADetalle);

        bottomNav.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_menu) {
                    return true;
                } else if (id == R.id.nav_carrito) {
                    startActivity(new Intent(HomeMenuActivity.this, CartSummaryActivity.class));
                    return true;
                } else if (id == R.id.nav_puntos) {
                    startActivity(new Intent(HomeMenuActivity.this, LoyaltyDashboardActivity.class));
                    return true;
                } else if (id == R.id.nav_perfil) {
                    startActivity(new Intent(HomeMenuActivity.this, ProfileActivity.class));
                    return true;
                }
                return false;
            }
        });
    }
}