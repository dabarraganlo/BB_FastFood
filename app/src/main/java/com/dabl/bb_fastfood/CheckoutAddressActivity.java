package com.dabl.bb_fastfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CheckoutAddressActivity extends AppCompatActivity {

    Button btnVolverCarrito, btnConfirmarDireccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_address);

        btnVolverCarrito = findViewById(R.id.btnVolverCarrito);
        btnConfirmarDireccion = findViewById(R.id.btnConfirmarDireccion);

        btnVolverCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnConfirmarDireccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent explícito - navegación a opciones de pago
                Intent intent = new Intent(CheckoutAddressActivity.this, PaymentOptionsActivity.class);
                startActivity(intent);
            }
        });
    }
}