package com.dabl.bb_fastfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CartSummaryActivity extends AppCompatActivity {

    android.widget.ImageButton btnVolverMenu;
    android.widget.Button btnContinuar;
    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_summary);

        btnVolverMenu = findViewById(R.id.btnVolverMenu);
        btnContinuar = findViewById(R.id.btnContinuar);
        bottomNav = findViewById(R.id.bottomNav);

        bottomNav.setSelectedItemId(R.id.nav_carrito);

        btnVolverMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartSummaryActivity.this, CheckoutAddressActivity.class);
                startActivity(intent);
            }
        });

        bottomNav.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_menu) {
                    startActivity(new Intent(CartSummaryActivity.this, HomeMenuActivity.class));
                    return true;
                } else if (id == R.id.nav_carrito) {
                    return true;
                } else if (id == R.id.nav_puntos) {
                    startActivity(new Intent(CartSummaryActivity.this, LoyaltyDashboardActivity.class));
                    return true;
                } else if (id == R.id.nav_perfil) {
                    startActivity(new Intent(CartSummaryActivity.this, ProfileActivity.class));
                    return true;
                }
                return false;
            }
        });
    }
}