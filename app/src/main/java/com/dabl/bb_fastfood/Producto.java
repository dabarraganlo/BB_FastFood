package com.dabl.bb_fastfood;

public class Producto {

    private String nombre;
    private int precio;
    private int puntos;
    private int imagenResId;
    private int cantidad;

    public Producto(String nombre, int precio, int puntos, int imagenResId) {
        this.nombre = nombre;
        this.precio = precio;
        this.puntos = puntos;
        this.imagenResId = imagenResId;
        this.cantidad = 1;
    }

    public String getNombre() { return nombre; }
    public int getPrecio() { return precio; }
    public int getPuntos() { return puntos; }
    public int getImagenResId() { return imagenResId; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public int getTotalPrecio() { return precio * cantidad; }
    public int getTotalPuntos() { return puntos * cantidad; }
}