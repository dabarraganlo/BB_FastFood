package com.dabl.bb_fastfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CartSummaryActivity extends AppCompatActivity {

    android.widget.ImageButton btnVolverMenu;
    Button btnContinuar;
    BottomNavigationView bottomNav;
    LinearLayout layoutProductos;
    TextView tvTotal, tvTotalPuntos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_summary);

        btnVolverMenu = findViewById(R.id.btnVolverMenu);
        btnContinuar = findViewById(R.id.btnContinuar);
        bottomNav = findViewById(R.id.bottomNav);
        layoutProductos = findViewById(R.id.layoutProductos);
        tvTotal = findViewById(R.id.tvTotal);
        tvTotalPuntos = findViewById(R.id.tvTotalPuntos);

        bottomNav.setSelectedItemId(R.id.nav_carrito);

        mostrarProductos();

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

    private void mostrarProductos() {
        layoutProductos.removeAllViews();
        Carrito carrito = Carrito.getInstance();

        for (Producto p : carrito.getProductos()) {
            TextView tvProducto = new TextView(this);
            tvProducto.setText(p.getCantidad() + "x " + p.getNombre() +
                    " - $" + String.format("%,d", p.getTotalPrecio()) +
                    " (+" + p.getTotalPuntos() + " pts)");
            tvProducto.setTextSize(14);
            tvProducto.setPadding(0, 8, 0, 8);
            tvProducto.setTextColor(getResources().getColor(android.R.color.black));
            layoutProductos.addView(tvProducto);
        }

        tvTotal.setText("$" + String.format("%,d", carrito.getTotalPrecio()));
        tvTotalPuntos.setText("Puntos acumulados en este pedido: +" +
                carrito.getTotalPuntos() + " puntos");
    }
}