package com.dabl.bb_fastfood;

import java.util.ArrayList;

public class Carrito {

    private static Carrito instancia;
    private ArrayList<Producto> productos;

    private Carrito() {
        productos = new ArrayList<>();
    }

    public static Carrito getInstance() {
        if (instancia == null) {
            instancia = new Carrito();
        }
        return instancia;
    }

    public void agregarProducto(Producto producto) {
        for (Producto p : productos) {
            if (p.getNombre().equals(producto.getNombre())) {
                p.setCantidad(p.getCantidad() + producto.getCantidad());
                return;
            }
        }
        productos.add(producto);
    }

    public ArrayList<Producto> getProductos() { return productos; }

    public int getTotalPrecio() {
        int total = 0;
        for (Producto p : productos) {
            total += p.getTotalPrecio();
        }
        return total;
    }

    public int getTotalPuntos() {
        int total = 0;
        for (Producto p : productos) {
            total += p.getTotalPuntos();
        }
        return total;
    }

    public void limpiar() {
        productos.clear();
    }
}