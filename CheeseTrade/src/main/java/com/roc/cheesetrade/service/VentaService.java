package com.roc.cheesetrade.service;

import com.roc.cheesetrade.dao.VentaDAO;
import com.roc.cheesetrade.dao.DetalleVentaDAO;
import com.roc.cheesetrade.dao.InvDAO;
import com.roc.cheesetrade.entities.Inv;
import java.util.List;

public class VentaService {

    public boolean realizarVenta(List<Inv> carrito, int idUsuario) {
        VentaDAO ventaDAO = new VentaDAO();
        DetalleVentaDAO detalleDAO = new DetalleVentaDAO();
        InvDAO invDAO = new InvDAO();

        // Realizar la venta
        return realizarVentaCompleta(carrito, idUsuario, ventaDAO, detalleDAO, invDAO);
    }

    private boolean realizarVentaCompleta(List<Inv> carrito, int idUsuario, VentaDAO ventaDAO, DetalleVentaDAO detalleDAO, InvDAO invDAO) {
        java.sql.Connection con = null;

        try {
            con = com.roc.cheesetrade.db.DBConexion.obtenerConexion();
            con.setAutoCommit(false); // Inicia transacción

            // Calcular el total
            double total = 0;
            for (Inv prod : carrito) {
                total += prod.getPrecio() * prod.getStock(); // stock = cantidad vendida
            }

            // Insertar la venta
            int idVenta = ventaDAO.insertarVenta(new java.sql.Date(System.currentTimeMillis()), total, idUsuario);

            if (idVenta == -1) {
                con.rollback();
                return false;
            }

            // Insertar detalles de la venta y actualizar el stock
            for (Inv prod : carrito) {
                int detalle = detalleDAO.insertarDetalleVenta(idVenta, prod.getIdProducto(), prod.getStock(), prod.getPrecio());
                int actualizado = invDAO.descontarStock(prod.getIdProducto(), prod.getStock());

                if (detalle == 0 || actualizado == 0) {
                    con.rollback();
                    return false;
                }
            }

            con.commit(); // Todo salió bien
            return true;

        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                if (con != null) con.rollback();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        } finally {
            try {
                if (con != null) con.setAutoCommit(true);
                com.roc.cheesetrade.db.DBConexion.cerrarConexion();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}


