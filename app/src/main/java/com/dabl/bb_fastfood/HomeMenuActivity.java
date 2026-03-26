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

        btnHamburguesaClasica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irADetalle("Hamburguesa Clasica", 15000, 50, R.drawable.burguersencilla);
            }
        });

        btnHamburguesaEspecial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irADetalle("Hamburguesa Especial", 22000, 80, R.drawable.burguerespcial);
            }
        });

        btnPerroSencillo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irADetalle("Perro Sencillo", 8000, 30, R.drawable.perrosencillo);
            }
        });

        btnPerroEspecial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irADetalle("Perro Especial", 14000, 60, R.drawable.perroespecial);
            }
        });

        btnCombo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irADetalle("Combo Hamburguesa + Bebida", 20000, 70, R.drawable.comboburguer);
            }
        });

        btnCombo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irADetalle("Combo Perro + Papas + Bebida", 18000, 65, R.drawable.comboperro);
            }
        });

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

    private void irADetalle(String nombre, int precio, int puntos, int imagenResId) {
        Intent intent = new Intent(HomeMenuActivity.this, ProductDetailActivity.class);
        intent.putExtra("nombre", nombre);
        intent.putExtra("precio", precio);
        intent.putExtra("puntos", puntos);
        intent.putExtra("imagenResId", imagenResId);
        startActivity(intent);
    }
}