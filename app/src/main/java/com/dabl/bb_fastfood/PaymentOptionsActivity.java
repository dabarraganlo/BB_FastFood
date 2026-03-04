package com.dabl.bb_fastfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentOptionsActivity extends AppCompatActivity {

    ImageButton btnVolverDireccion;
    Button btnRealizarPedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_options);

        btnVolverDireccion = findViewById(R.id.btnVolverDireccion);
        btnRealizarPedido = findViewById(R.id.btnRealizarPedido);

        btnVolverDireccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnRealizarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentOptionsActivity.this, LoyaltyDashboardActivity.class);
                startActivity(intent);
            }
        });
    }
}