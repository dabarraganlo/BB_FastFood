package com.dabl.bb_fastfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LoyaltyDashboardActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loyalty_dashboard);

        bottomNav = findViewById(R.id.bottomNav);

        bottomNav.setSelectedItemId(R.id.nav_puntos);

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
                } else if (id == R.id.nav_perfil) {
                    startActivity(new Intent(LoyaltyDashboardActivity.this, ProfileActivity.class));
                    return true;
                }
                return false;
            }
        });
    }
}