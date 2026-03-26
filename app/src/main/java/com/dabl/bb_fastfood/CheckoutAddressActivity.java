package com.dabl.bb_fastfood;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class CheckoutAddressActivity extends AppCompatActivity {

    ImageButton btnVolverCarrito;
    Button btnConfirmarDireccion;
    TextView tvCoordenadas;
    WebView webViewMapa;
    LocationManager locationManager;
    LocationListener locationListener;
    static final int PERMISO_UBICACION = 100;

    double latitudActual = 3.5209;  // Palmira por defecto
    double longitudActual = -76.2627;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_address);

        btnVolverCarrito = findViewById(R.id.btnVolverCarrito);
        btnConfirmarDireccion = findViewById(R.id.btnConfirmarDireccion);
        tvCoordenadas = findViewById(R.id.tvCoordenadas);
        webViewMapa = findViewById(R.id.webViewMapa);

        configurarWebView();
        cargarMapa(latitudActual, longitudActual);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                latitudActual = location.getLatitude();
                longitudActual = location.getLongitude();
                tvCoordenadas.setText("Ubicacion detectada:\nLat: " +
                        String.format("%.6f", latitudActual) +
                        "\nLon: " + String.format("%.6f", longitudActual));
                cargarMapa(latitudActual, longitudActual);
            }
        };

        solicitarUbicacion();

        btnVolverCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnConfirmarDireccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckoutAddressActivity.this,
                        PaymentOptionsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void configurarWebView() {
        WebSettings settings = webViewMapa.getSettings();
        settings.setJavaScriptEnabled(true);
        webViewMapa.setWebViewClient(new WebViewClient());
    }

    private void cargarMapa(double lat, double lon) {
        String url = "https://maps.google.com/maps?q=" + lat + "," + lon +
                "&z=15&output=embed";
        webViewMapa.loadUrl(url);
    }

    private void solicitarUbicacion() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISO_UBICACION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISO_UBICACION) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                solicitarUbicacion();
            } else {
                Toast.makeText(this, "Permiso de ubicacion denegado",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationManager != null && locationListener != null) {
            locationManager.removeUpdates(locationListener);
        }
    }
}