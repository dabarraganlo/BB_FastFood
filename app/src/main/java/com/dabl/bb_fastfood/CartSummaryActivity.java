package com.dabl.bb_fastfood;

import android.content.Intent;
import android.graphics.Color;
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
                if (Carrito.getInstance().getProductos().isEmpty()) {
                    return;
                }
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

        for (final Producto p : carrito.getProductos()) {

            LinearLayout filaProducto = new LinearLayout(this);
            filaProducto.setOrientation(LinearLayout.HORIZONTAL);
            filaProducto.setPadding(0, 8, 0, 8);

            // Nombre y precio del producto
            TextView tvProducto = new TextView(this);
            tvProducto.setText(p.getNombre() + "\n$" +
                    String.format("%,d", p.getTotalPrecio()) +
                    " (+" + p.getTotalPuntos() + " pts)");
            tvProducto.setTextSize(13);
            tvProducto.setTextColor(Color.parseColor("#212121"));
            LinearLayout.LayoutParams paramsTv = new LinearLayout.LayoutParams(
                    0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
            tvProducto.setLayoutParams(paramsTv);
            filaProducto.addView(tvProducto);

            // Botón menos
            Button btnMenos = new Button(this);
            btnMenos.setText("-");
            btnMenos.setTextSize(16);
            btnMenos.setTextColor(Color.WHITE);
            btnMenos.getBackground().setTint(Color.parseColor("#D32F2F"));
            LinearLayout.LayoutParams paramsBtnMenos = new LinearLayout.LayoutParams(80, 80);
            paramsBtnMenos.setMargins(4, 0, 4, 0);
            btnMenos.setLayoutParams(paramsBtnMenos);
            btnMenos.setPadding(0, 0, 0, 0);
            filaProducto.addView(btnMenos);

            // Cantidad
            final TextView tvCantidad = new TextView(this);
            tvCantidad.setText(String.valueOf(p.getCantidad()));
            tvCantidad.setTextSize(16);
            tvCantidad.setTextColor(Color.parseColor("#212121"));
            tvCantidad.setGravity(android.view.Gravity.CENTER);
            LinearLayout.LayoutParams paramsCantidad = new LinearLayout.LayoutParams(60,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            tvCantidad.setLayoutParams(paramsCantidad);
            filaProducto.addView(tvCantidad);

            // Botón más
            Button btnMas = new Button(this);
            btnMas.setText("+");
            btnMas.setTextSize(16);
            btnMas.setTextColor(Color.WHITE);
            btnMas.getBackground().setTint(Color.parseColor("#D32F2F"));
            LinearLayout.LayoutParams paramsBtnMas = new LinearLayout.LayoutParams(80, 80);
            paramsBtnMas.setMargins(4, 0, 4, 0);
            btnMas.setLayoutParams(paramsBtnMas);
            btnMas.setPadding(0, 0, 0, 0);
            filaProducto.addView(btnMas);

            // Botón eliminar
            Button btnEliminar = new Button(this);
            btnEliminar.setText("X");
            btnEliminar.setTextSize(14);
            btnEliminar.setTextColor(Color.WHITE);
            btnEliminar.getBackground().setTint(Color.parseColor("#757575"));
            LinearLayout.LayoutParams paramsBtnEliminar = new LinearLayout.LayoutParams(80, 80);
            paramsBtnEliminar.setMargins(4, 0, 0, 0);
            btnEliminar.setLayoutParams(paramsBtnEliminar);
            btnEliminar.setPadding(0, 0, 0, 0);
            filaProducto.addView(btnEliminar);

            layoutProductos.addView(filaProducto);

            // Listeners
            btnMas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    p.setCantidad(p.getCantidad() + 1);
                    tvCantidad.setText(String.valueOf(p.getCantidad()));
                    tvProducto.setText(p.getNombre() + "\n$" +
                            String.format("%,d", p.getTotalPrecio()) +
                            " (+" + p.getTotalPuntos() + " pts)");
                    actualizarTotales();
                }
            });

            btnMenos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (p.getCantidad() > 1) {
                        p.setCantidad(p.getCantidad() - 1);
                        tvCantidad.setText(String.valueOf(p.getCantidad()));
                        tvProducto.setText(p.getNombre() + "\n$" +
                                String.format("%,d", p.getTotalPrecio()) +
                                " (+" + p.getTotalPuntos() + " pts)");
                        actualizarTotales();
                    }
                }
            });

            btnEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Carrito.getInstance().getProductos().remove(p);
                    mostrarProductos();
                }
            });
        }

        actualizarTotales();
    }

    private void actualizarTotales() {
        Carrito carrito = Carrito.getInstance();
        tvTotal.setText("$" + String.format("%,d", carrito.getTotalPrecio()));
        tvTotalPuntos.setText("Puntos acumulados en este pedido: +" +
                carrito.getTotalPuntos() + " puntos");
    }
}