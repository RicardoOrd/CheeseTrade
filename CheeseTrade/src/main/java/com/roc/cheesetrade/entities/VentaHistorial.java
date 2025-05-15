package com.roc.cheesetrade.entities;

import java.sql.Timestamp;
import java.util.List;

public class VentaHistorial {

    private int idVenta;
    private Timestamp fecha;
    private double total;
    private String usuario;
    private String metodoPago;    // Nuevo campo
    private String estado;        // Nuevo campo
    private List<DetalleHistorial> detalles;

    // Constructor vacío
    public VentaHistorial() {}

    // Constructor completo
    public VentaHistorial(int idVenta, Timestamp fecha, double total, String usuario, String metodoPago, String estado, List<DetalleHistorial> detalles) {
        this.idVenta = idVenta;
        this.fecha = fecha;
        this.total = total;
        this.usuario = usuario;
        this.metodoPago = metodoPago;
        this.estado = estado;
        this.detalles = detalles;
    }

    // Getters y Setters
    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<DetalleHistorial> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleHistorial> detalles) {
        this.detalles = detalles;
    }
}
