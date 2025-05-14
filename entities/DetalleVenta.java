package com.roc.cheesetrade.entities;

/**
 *
 * @author nuevo
 */
public class DetalleVenta {
    
    Producto producto;
    int cantidad;

    public double getSubtotal() {
        return producto.getPrecio() * cantidad;
    }

    public DetalleVenta(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    
}
