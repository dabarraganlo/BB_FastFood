package com.dabl.bb_fastfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// Activity que muestra el listado de todos los pedidos realizados por el usuario.
// Los pedidos provienen de HistorialPedidos (singleton en memoria).
// En la Fase 4 se conectara con SQLite para persistencia permanente.
public class PedidosActivity extends AppCompatActivity {

    RecyclerView recyclerPedidos;
    TextView tvSinPedidos;
    ImageButton btnVolverPedidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);

        recyclerPedidos = findViewById(R.id.recyclerPedidos);
        tvSinPedidos = findViewById(R.id.tvSinPedidos);
        btnVolverPedidos = findViewById(R.id.btnVolverPedidos);

        btnVolverPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cargarPedidos();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Recargar la lista cuando el usuario regresa desde DetallePedidoActivity
        cargarPedidos();
    }

    private void cargarPedidos() {
        ArrayList<Pedido> pedidos = HistorialPedidos.getInstance().getPedidos();

        if (pedidos.isEmpty()) {
            tvSinPedidos.setVisibility(View.VISIBLE);
            recyclerPedidos.setVisibility(View.GONE);
        } else {
            tvSinPedidos.setVisibility(View.GONE);
            recyclerPedidos.setVisibility(View.VISIBLE);

            PedidosAdapter adapter = new PedidosAdapter(pedidos);
            recyclerPedidos.setLayoutManager(new LinearLayoutManager(this));
            recyclerPedidos.setAdapter(adapter);
        }
    }

    // Adaptador interno para el RecyclerView de pedidos
    class PedidosAdapter extends RecyclerView.Adapter<PedidosAdapter.PedidoViewHolder> {

        ArrayList<Pedido> listaPedidos;

        PedidosAdapter(ArrayList<Pedido> lista) {
            this.listaPedidos = lista;
        }

        @NonNull
        @Override
        public PedidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_pedido, parent, false);
            return new PedidoViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PedidoViewHolder holder, int position) {
            Pedido pedido = listaPedidos.get(position);

            holder.tvId.setText("Pedido #" + pedido.getId());
            holder.tvFecha.setText(pedido.getFecha());
            holder.tvProductos.setText(pedido.getResumenItems());
            holder.tvTotal.setText("Total: $" +
                    String.format("%,d", pedido.getTotalPrecio()));
            holder.tvMetodoPago.setText(pedido.getMetodoPago());
            holder.tvEstado.setText(pedido.getEstado());

            // Al hacer clic en el pedido, abre el detalle
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = holder.getAdapterPosition();
                    Intent intent = new Intent(PedidosActivity.this,
                            DetallePedidoActivity.class);
                    intent.putExtra("pedido_index", index);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return listaPedidos.size();
        }

        // ViewHolder que referencia las vistas del item_pedido.xml
        class PedidoViewHolder extends RecyclerView.ViewHolder {
            TextView tvId, tvFecha, tvProductos, tvTotal, tvMetodoPago, tvEstado;

            PedidoViewHolder(@NonNull View itemView) {
                super(itemView);
                tvId = itemView.findViewById(R.id.tvItemPedidoId);
                tvFecha = itemView.findViewById(R.id.tvItemFecha);
                tvProductos = itemView.findViewById(R.id.tvItemProductos);
                tvTotal = itemView.findViewById(R.id.tvItemTotal);
                tvMetodoPago = itemView.findViewById(R.id.tvItemMetodoPago);
                tvEstado = itemView.findViewById(R.id.tvItemEstado);
            }
        }
    }
}
