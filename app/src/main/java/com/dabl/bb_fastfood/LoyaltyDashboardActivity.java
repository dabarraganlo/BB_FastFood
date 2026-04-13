package com.dabl.bb_fastfood;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LoyaltyDashboardActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;
    TextView tvPuntosAcumulados, tvEstadoPedido;
    Button btnVerPedidos;

    static final String CHANNEL_ID = "bb_fastfood_channel";
    static final int NOTIFICACION_ID = 1;
    static final int PERMISO_NOTIFICACION = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loyalty_dashboard);

        bottomNav = findViewById(R.id.bottomNav);
        tvPuntosAcumulados = findViewById(R.id.tvPuntosAcumulados);
        tvEstadoPedido = findViewById(R.id.tvEstadoPedido);
        btnVerPedidos = findViewById(R.id.btnVerPedidos);

        bottomNav.setSelectedItemId(R.id.nav_puntos);

        // Recuperar datos del carrito antes de limpiarlo
        Carrito carrito = Carrito.getInstance();
        int puntosDelPedido = carrito.getTotalPuntos();
        int totalPrecio = carrito.getTotalPrecio();

        tvPuntosAcumulados.setText(puntosDelPedido + " puntos");
        tvEstadoPedido.setText("Tu pedido esta en preparacion - +" +
                puntosDelPedido + " puntos ganados");

        // Obtener el metodo de pago enviado desde PaymentOptionsActivity
        String metodoPago = getIntent().getStringExtra("metodoPago");
        if (metodoPago == null) metodoPago = "No especificado";

        // Guardar el pedido en el historial antes de limpiar el carrito
        String fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                .format(new Date());
        Pedido nuevoPedido = new Pedido(
                HistorialPedidos.getInstance().getProximoId(),
                fecha,
                carrito.getProductos(),
                metodoPago,
                totalPrecio,
                puntosDelPedido
        );
        HistorialPedidos.getInstance().agregarPedido(nuevoPedido);

        // Limpiar el carrito despues de guardar el pedido
        carrito.limpiar();

        // Boton para navegar al historial de pedidos
        btnVerPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoyaltyDashboardActivity.this,
                        PedidosActivity.class));
            }
        });

        crearCanalNotificacion();
        solicitarPermisoYNotificar(puntosDelPedido);

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

    private void crearCanalNotificacion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel canal = new NotificationChannel(
                    CHANNEL_ID,
                    "B&B FastFood Pedidos",
                    NotificationManager.IMPORTANCE_HIGH);
            canal.setDescription("Notificaciones del estado de tu pedido");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(canal);
        }
    }

    private void solicitarPermisoYNotificar(int puntos) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                        PERMISO_NOTIFICACION);
                return;
            }
        }
        enviarNotificacion(puntos);
    }

    private void enviarNotificacion(int puntos) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_puntos)
                .setContentTitle("B&B FastFood - Pedido recibido")
                .setContentText("Tu pedido esta en preparacion. Ganaste " + puntos + " puntos!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManager manager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(NOTIFICACION_ID, builder.build());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISO_NOTIFICACION) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enviarNotificacion(Carrito.getInstance().getTotalPuntos());
            }
        }
    }
}