package com.dabl.bb_fastfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LoyaltyDashboardActivity extends AppCompatActivity {

    Button btnInicio;
    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loyalty_dashboard);

        btnInicio = findViewById(R.id.btnInicio);
        bottomNav = findViewById(R.id.bottomNav);

        bottomNav.setSelectedItemId(R.id.nav_puntos);

        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoyaltyDashboardActivity.this, HomeMenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        bottomNav.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_menu) {
                    startActivity(new Intent(LoyaltyDashboardActivity.this, HomeMenuActivity.class));
                    return true;
                } else if (id == R.id.nav_carrito) {
                    startActivity(new Intent(LoyaltyDashboardActivity.this, CartSummaryActivity.class));
                    return true;
                } else if (id == R.id.nav_puntos) {
                    return true;
                }
                return false;
            }
        });
    }
}