package com.dabl.bb_fastfood;

import java.util.ArrayList;

// Modelo que representa un pedido completado en B&B FastFood
public class Pedido {

    private int id;
    private String fecha;
    private ArrayList<Producto> items;
    private String metodoPago;
    private int totalPrecio;
    private int totalPuntos;
    private String estado;

    public Pedido(int id, String fecha, ArrayList<Producto> items,
                  String metodoPago, int totalPrecio, int totalPuntos) {
        this.id = id;
        this.fecha = fecha;
        // Copia defensiva de los items para no depender del singleton Carrito
        this.items = new ArrayList<>(items);
        this.metodoPago = metodoPago;
        this.totalPrecio = totalPrecio;
        this.totalPuntos = totalPuntos;
        this.estado = "En preparacion";
    }

    // Getters
    public int getId() { return id; }
    public String getFecha() { return fecha; }
    public ArrayList<Producto> getItems() { return items; }
    public String getMetodoPago() { return metodoPago; }
    public int getTotalPrecio() { return totalPrecio; }
    public int getTotalPuntos() { return totalPuntos; }
    public String getEstado() { return estado; }

    // Setters
    public void setEstado(String estado) { this.estado = estado; }
    public void setId(int id) { this.id = id; }

    // Retorna un resumen legible de los productos
    public String getResumenItems() {
        StringBuilder sb = new StringBuilder();
        for (Producto p : items) {
            sb.append(p.getCantidad())
              .append("x ")
              .append(p.getNombre())
              .append("\n");
        }
        return sb.toString().trim();
    }
}
