package com.dabl.bb_fastfood;

import java.util.ArrayList;

// Singleton que almacena el historial de pedidos del usuario durante la sesion.
// En la Fase 4 este singleton se complementara con persistencia SQLite.
public class HistorialPedidos {

    private static HistorialPedidos instancia;
    private ArrayList<Pedido> pedidos;
    private int contadorId;

    private HistorialPedidos() {
        pedidos = new ArrayList<>();
        contadorId = 1;
    }

    public static HistorialPedidos getInstance() {
        if (instancia == null) {
            instancia = new HistorialPedidos();
        }
        return instancia;
    }

    // Agrega un nuevo pedido y le asigna un ID automatico
    public void agregarPedido(Pedido pedido) {
        pedidos.add(pedido);
        contadorId++;
    }

    // Retorna la lista completa de pedidos
    public ArrayList<Pedido> getPedidos() {
        return pedidos;
    }

    // Retorna un pedido por su posicion en la lista
    public Pedido getPedido(int index) {
        if (index >= 0 && index < pedidos.size()) {
            return pedidos.get(index);
        }
        return null;
    }

    // Elimina un pedido por su posicion en la lista
    public void eliminarPedido(int index) {
        if (index >= 0 && index < pedidos.size()) {
            pedidos.remove(index);
        }
    }

    // Retorna el proximo ID disponible
    public int getProximoId() {
        return contadorId;
    }
}
