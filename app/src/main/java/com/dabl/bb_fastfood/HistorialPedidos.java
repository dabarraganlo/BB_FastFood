package com.dabl.bb_fastfood;

import android.content.Context;

import java.util.ArrayList;

public class HistorialPedidos {

    private static HistorialPedidos instancia;
    private PedidoDAO dao;

    private HistorialPedidos(Context context) {
        dao = new PedidoDAO(context.getApplicationContext());
    }

    public static HistorialPedidos getInstance(Context context) {
        if (instancia == null) {
            instancia = new HistorialPedidos(context);
        }
        return instancia;
    }

    public void agregarPedido(Pedido pedido) {
        long newId = dao.insertarPedido(pedido);
        pedido.setId((int) newId);
    }

    public ArrayList<Pedido> getPedidos() {
        return dao.obtenerTodosLosPedidos();
    }

    public Pedido getPedidoPorId(int id) {
        return dao.obtenerPedidoPorId(id);
    }

    public void eliminarPedido(int id) {
        dao.eliminarPedido(id);
    }

    public void actualizarEstado(int id, String estado) {
        dao.actualizarEstado(id, estado);
    }
}
