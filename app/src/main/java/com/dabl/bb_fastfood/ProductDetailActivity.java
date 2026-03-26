package com.dabl.bb_fastfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProductDetailActivity extends AppCompatActivity {

    ImageButton btnVolver;
    Button btnCompartir, btnAgregarCarrito, btnMas, btnMenos;
    TextView tvNombreProducto, tvPrecioProducto, tvPuntos, tvCantidad;
    ImageView imgProducto;

    int cantidad = 1;
    int precio, puntos, imagenResId;
    String nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Recibir datos del producto
        nombre = getIntent().getStringExtra("nombre");
        precio = getIntent().getIntExtra("precio", 0);
        puntos = getIntent().getIntExtra("puntos", 0);
        imagenResId = getIntent().getIntExtra("imagenResId", 0);

        // Conectar vistas
        btnVolver = findViewById(R.id.btnVolver);
        btnCompartir = findViewById(R.id.btnCompartir);
        btnAgregarCarrito = findViewById(R.id.btnAgregarCarrito);
        btnMas = findViewById(R.id.btnMas);
        btnMenos = findViewById(R.id.btnMenos);
        tvNombreProducto = findViewById(R.id.tvNombreProducto);
        tvPrecioProducto = findViewById(R.id.tvPrecioProducto);
        tvPuntos = findViewById(R.id.tvPuntos);
        tvCantidad = findViewById(R.id.tvCantidad);
        imgProducto = findViewById(R.id.imgProducto);

        // Mostrar datos del producto
        tvNombreProducto.setText(nombre);
        tvPrecioProducto.setText("$" + String.format("%,d", precio));
        tvPuntos.setText("Puntos de fidelidad: +" + puntos + " puntos");
        imgProducto.setImageResource(imagenResId);
        actualizarCantidad();

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cantidad++;
                actualizarCantidad();
            }
        });

        btnMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cantidad > 1) {
                    cantidad--;
                    actualizarCantidad();
                }
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
                Producto producto = new Producto(nombre, precio, puntos, imagenResId);
                producto.setCantidad(cantidad);
                Carrito.getInstance().agregarProducto(producto);
                Intent intent = new Intent(ProductDetailActivity.this, CartSummaryActivity.class);
                startActivity(intent);
            }
        });
    }

    private void actualizarCantidad() {
        tvCantidad.setText(String.valueOf(cantidad));
        tvPrecioProducto.setText("$" + String.format("%,d", precio * cantidad));
        tvPuntos.setText("Puntos de fidelidad: +" + (puntos * cantidad) + " puntos");
    }
}