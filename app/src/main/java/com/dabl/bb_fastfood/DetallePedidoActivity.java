package com.dabl.bb_fastfood;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

// Activity que muestra el detalle de un pedido individual.
// Permite editar el estado del pedido y eliminarlo del historial.
// En la Fase 4 estas operaciones se realizaran sobre la base de datos SQLite (CRUD).
public class DetallePedidoActivity extends AppCompatActivity {

    TextView tvId, tvFecha, tvEstado, tvProductos, tvMetodoPago, tvTotal, tvPuntos;
    RadioGroup radioGroupEstado;
    RadioButton radioEnPreparacion, radioEnCamino, radioEntregado, radioCancelado;
    Button btnGuardar, btnEliminar;
    ImageButton btnVolver;

    int pedidoId;
    Pedido pedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pedido);

        // Referencias a las vistas del layout
        tvId = findViewById(R.id.tvDetallePedidoId);
        tvFecha = findViewById(R.id.tvDetalleFecha);
        tvEstado = findViewById(R.id.tvDetalleEstado);
        tvProductos = findViewById(R.id.tvDetalleProductos);
        tvMetodoPago = findViewById(R.id.tvDetalleMetodoPago);
        tvTotal = findViewById(R.id.tvDetalleTotal);
        tvPuntos = findViewById(R.id.tvDetallePuntos);
        radioGroupEstado = findViewById(R.id.radioGroupEstado);
        radioEnPreparacion = findViewById(R.id.radioEnPreparacion);
        radioEnCamino = findViewById(R.id.radioEnCamino);
        radioEntregado = findViewById(R.id.radioEntregado);
        radioCancelado = findViewById(R.id.radioCancelado);
        btnGuardar = findViewById(R.id.btnGuardarCambios);
        btnEliminar = findViewById(R.id.btnEliminarPedido);
        btnVolver = findViewById(R.id.btnVolverDetalle);

        // Obtener el id del pedido enviado desde PedidosActivity
        pedidoId = getIntent().getIntExtra("pedido_id", -1);
        pedido = HistorialPedidos.getInstance(this).getPedidoPorId(pedidoId);

        if (pedido == null) {
            Toast.makeText(this, "Pedido no encontrado", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Mostrar los datos del pedido en las vistas
        mostrarDatosPedido();

        // Boton volver: regresa a la lista de pedidos
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Boton guardar cambios: actualiza el estado del pedido en el historial
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarCambioEstado();
            }
        });

        // Boton eliminar: muestra dialogo de confirmacion antes de eliminar
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmarEliminacion();
            }
        });
    }

    private void mostrarDatosPedido() {
        tvId.setText("Pedido #" + pedido.getId());
        tvFecha.setText(pedido.getFecha());
        tvEstado.setText(pedido.getEstado());
        tvProductos.setText(pedido.getResumenItems());
        tvMetodoPago.setText(pedido.getMetodoPago());
        tvTotal.setText("$" + String.format("%,d", pedido.getTotalPrecio()));
        tvPuntos.setText("+" + pedido.getTotalPuntos() + " puntos");

        // Marcar el RadioButton correspondiente al estado actual del pedido
        switch (pedido.getEstado()) {
            case "En preparacion":
                radioEnPreparacion.setChecked(true);
                break;
            case "En camino":
                radioEnCamino.setChecked(true);
                break;
            case "Entregado":
                radioEntregado.setChecked(true);
                break;
            case "Cancelado":
                radioCancelado.setChecked(true);
                break;
            default:
                radioEnPreparacion.setChecked(true);
        }
    }

    // Lee el RadioButton seleccionado y actualiza el estado del pedido en el historial
    private void guardarCambioEstado() {
        String nuevoEstado = "En preparacion";
        int seleccionado = radioGroupEstado.getCheckedRadioButtonId();

        if (seleccionado == R.id.radioEnPreparacion) {
            nuevoEstado = "En preparacion";
        } else if (seleccionado == R.id.radioEnCamino) {
            nuevoEstado = "En camino";
        } else if (seleccionado == R.id.radioEntregado) {
            nuevoEstado = "Entregado";
        } else if (seleccionado == R.id.radioCancelado) {
            nuevoEstado = "Cancelado";
        }

        pedido.setEstado(nuevoEstado);
        HistorialPedidos.getInstance(this).actualizarEstado(pedidoId, nuevoEstado);
        tvEstado.setText(nuevoEstado);

        Toast.makeText(this, "Estado actualizado: " + nuevoEstado,
                Toast.LENGTH_SHORT).show();
    }

    // Muestra un dialogo de confirmacion antes de eliminar el pedido
    private void confirmarEliminacion() {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar pedido")
                .setMessage("Seguro que deseas eliminar el Pedido #" +
                        pedido.getId() + "? Esta accion no se puede deshacer.")
                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HistorialPedidos.getInstance(DetallePedidoActivity.this).eliminarPedido(pedidoId);
                        Toast.makeText(DetallePedidoActivity.this,
                                "Pedido eliminado", Toast.LENGTH_SHORT).show();
                        finish(); // Regresa a PedidosActivity
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}
