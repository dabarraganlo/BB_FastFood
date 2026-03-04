package com.dabl.bb_fastfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class ProductDetailActivity extends AppCompatActivity {

    ImageButton btnVolver;
    Button btnCompartir, btnAgregarCarrito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        btnVolver = findViewById(R.id.btnVolver);
        btnCompartir = findViewById(R.id.btnCompartir);
        btnAgregarCarrito = findViewById(R.id.btnAgregarCarrito);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnCompartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCompartir = new Intent(Intent.ACTION_SEND);
                intentCompartir.setType("text/plain");
                intentCompartir.putExtra(Intent.EXTRA_TEXT,
                        "Te recomiendo la Hamburguesa Clasica de B&B FastFood. Pidela ya!");
                startActivity(Intent.createChooser(intentCompartir, "Compartir via"));
            }
        });

        btnAgregarCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDetailActivity.this, CartSummaryActivity.class);
                startActivity(intent);
            }
        });
    }
}