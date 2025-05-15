package com.roc.cheesetrade.dao;

import com.roc.cheesetrade.db.DBConexion;
import com.roc.cheesetrade.entities.VentaHistorial;
import com.roc.cheesetrade.entities.DetalleHistorial;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistorialDAO {

    /**
     * Obtiene todas las compras del historial, junto con el nombre del cliente.
     */
  public List<VentaHistorial> obtenerHistorialFiltrado(String usuarioFiltro, java.util.Date fechaInicio, java.util.Date fechaFin) {
    List<VentaHistorial> lista = new ArrayList<>();
    Connection con = DBConexion.obtenerConexion();
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
        StringBuilder sql = new StringBuilder(
            "SELECT v.id_venta, v.fecha, v.total, u.usuario, "
            + "p.prod_nombre, d.cantidad, d.precio_unitario, d.subtotal "
            + "FROM tblventas v "
            + "JOIN tblusuarios u ON v.usuario_id = u.id_usuario "
            + "JOIN tbldetalle_venta d ON v.id_venta = d.id_venta "
            + "JOIN tblproductos p ON d.id_producto = p.id_producto "
            + "WHERE 1=1 "
        );
        List<Object> params = new ArrayList<>();

        // Filtro por usuario
        if (usuarioFiltro != null && !usuarioFiltro.trim().isEmpty()) {
            sql.append(" AND u.usuario LIKE ? ");
            params.add("%" + usuarioFiltro.trim() + "%");
        }

        // Filtro por fechas
        if (fechaInicio != null) {
            sql.append(" AND v.fecha >= ? ");
            params.add(new java.sql.Timestamp(fechaInicio.getTime()));
        }
        if (fechaFin != null) {
            sql.append(" AND v.fecha <= ? ");
            params.add(new java.sql.Timestamp(fechaFin.getTime()));
        }

        sql.append("ORDER BY v.fecha DESC");

        // DEBUG: Imprime el SQL generado y la cantidad de parámetros
        System.out.println("SQL FINAL: " + sql.toString());
        System.out.println("PARAMS: " + params.size());

        ps = con.prepareStatement(sql.toString());
        for (int i = 0; i < params.size(); i++) {
            ps.setObject(i + 1, params.get(i));
        }

        rs = ps.executeQuery();

        int lastVenta = -1;
        VentaHistorial venta = null;
        while (rs.next()) {
            int idVenta = rs.getInt("id_venta");
            if (idVenta != lastVenta) {
                if (venta != null) {
                    lista.add(venta);
                }
                venta = new VentaHistorial(
                    idVenta,
                    rs.getTimestamp("fecha"),
                    rs.getDouble("total"),
                    rs.getString("usuario"),
                    null,  // método de pago (agrega si lo necesitas)
                    null,  // estado (agrega si lo necesitas)
                    new ArrayList<>()
                );
                lastVenta = idVenta;
            }
            DetalleHistorial det = new DetalleHistorial(
                rs.getString("prod_nombre"),
                rs.getInt("cantidad"),
                rs.getDouble("precio_unitario"),
                rs.getDouble("subtotal")
            );
            venta.getDetalles().add(det);
        }
        if (venta != null) {
            lista.add(venta);
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try { if (rs != null) rs.close(); } catch (Exception e) {}
        try { if (ps != null) ps.close(); } catch (Exception e) {}
        DBConexion.cerrarConexion();
    }
    return lista;
}


    public List<VentaHistorial> obtenerHistorialCompleto() {
        List<VentaHistorial> lista = new ArrayList<>();
        Connection con = DBConexion.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT v.id_venta, v.fecha, v.total, u.usuario, \n"
                    + "       p.prod_nombre, d.cantidad, d.precio_unitario, d.subtotal\n"
                    + "FROM tblventas v\n"
                    + "JOIN tblusuarios u ON v.usuario_id = u.id_usuario\n"
                    + "JOIN tbldetalle_venta d ON v.id_venta = d.id_venta\n"
                    + "JOIN tblproductos p ON d.id_producto = p.id_producto\n"
                    + "ORDER BY v.fecha DESC;";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            // Vamos a armar el historial agrupando por venta
            int lastVenta = -1;
            VentaHistorial venta = null;
            while (rs.next()) {
                int idVenta = rs.getInt("id_venta");
                if (idVenta != lastVenta) {
                    if (venta != null) {
                        lista.add(venta);
                    }
                    venta = new VentaHistorial();
                    venta.setIdVenta(idVenta);
                    venta.setFecha(rs.getTimestamp("fecha"));
                    venta.setTotal(rs.getDouble("total"));
                    venta.setUsuario(rs.getString("usuario"));
                    venta.setDetalles(new ArrayList<>());
                    lastVenta = idVenta;
                }
                DetalleHistorial det = new DetalleHistorial();
                det.setNombreProducto(rs.getString("prod_nombre"));
                det.setCantidad(rs.getInt("cantidad"));
                det.setPrecioUnitario(rs.getDouble("precio_unitario"));
                det.setSubtotal(rs.getDouble("subtotal"));
                venta.getDetalles().add(det);
            }
            if (venta != null) {
                lista.add(venta);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                DBConexion.cerrarConexion();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return lista;
    }

    /**
     * Si quieres buscar el historial solo de un cliente específico
     */
    public List<Object[]> obtenerHistorialPorCliente(int idCliente) {
        List<Object[]> historial = new ArrayList<>();
        Connection con = DBConexion.obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT v.id_venta, v.fecha, v.total, u.usuario,\n"
                    + "       h.hist_metodo_pago, h.hist_estado, -- aquí traes los nuevos campos\n"
                    + "       p.prod_nombre, d.cantidad, d.precio_unitario, d.subtotal\n"
                    + "FROM tblventas v\n"
                    + "JOIN tblusuarios u ON v.usuario_id = u.id_usuario\n"
                    + "JOIN tbldetalle_venta d ON v.id_venta = d.id_venta\n"
                    + "JOIN tblproductos p ON d.id_producto = p.id_producto\n"
                    + "LEFT JOIN tblhistorial_compras h ON h.id_cliente = u.id_usuario -- Ajusta esto según tu modelo real\n"
                    + "ORDER BY v.fecha DESC";
            ps = con.prepareStatement(sql);
            ps.setInt(1, idCliente);
            rs = ps.executeQuery();

            while (rs.next()) {
                Object[] fila = new Object[]{
                    rs.getInt("id_compra"),
                    rs.getTimestamp("hist_fecha_compra"),
                    rs.getDouble("hist_total"),
                    rs.getString("hist_metodo_pago"),
                    rs.getString("hist_estado")
                };
                historial.add(fila);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
            }
            DBConexion.cerrarConexion();
        }
        return historial;
    }

}
