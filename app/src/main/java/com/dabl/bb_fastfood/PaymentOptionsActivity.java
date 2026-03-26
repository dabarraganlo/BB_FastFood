package com.dabl.bb_fastfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentOptionsActivity extends AppCompatActivity {

    ImageButton btnVolverDireccion;
    Button btnRealizarPedido;
    TextView tvResumenPedido, tvTotalPagar, tvPuntosDisponibles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_options);

        btnVolverDireccion = findViewById(R.id.btnVolverDireccion);
        btnRealizarPedido = findViewById(R.id.btnRealizarPedido);
        tvResumenPedido = findViewById(R.id.tvResumenPedido);
        tvTotalPagar = findViewById(R.id.tvTotalPagar);
        tvPuntosDisponibles = findViewById(R.id.tvPuntosDisponibles);

        mostrarResumen();

        btnVolverDireccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnRealizarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentOptionsActivity.this,
                        LoyaltyDashboardActivity.class);
                startActivity(intent);
            }
        });
    }

    private void mostrarResumen() {
        Carrito carrito = Carrito.getInstance();
        StringBuilder resumen = new StringBuilder();

        for (Producto p : carrito.getProductos()) {
            resumen.append(p.getCantidad())
                    .append("x ")
                    .append(p.getNombre())
                    .append(" - $")
                    .append(String.format("%,d", p.getTotalPrecio()))
                    .append("\n");
        }

        tvResumenPedido.setText(resumen.toString());
        tvTotalPagar.setText("Total a pagar: $" +
                String.format("%,d", carrito.getTotalPrecio()));
        tvPuntosDisponibles.setText("Puntos a ganar: +" +
                carrito.getTotalPuntos() + " puntos");
    }
}