package com.roc.cheesetrade.dao;

import com.roc.cheesetrade.db.DBConexion;
import java.sql.*;

public class DetalleVentaDAO {

public int insertarDetalleVenta(Connection con, int idVenta, int idProducto, int cantidad, double precioUnitario) {
    PreparedStatement ps = null;
    int registros = 0;

    try {
        String sql = "INSERT INTO tbldetalle_venta (id_venta, id_producto, cantidad, precio_unitario, subtotal) VALUES (?, ?, ?, ?, ?)";
        ps = con.prepareStatement(sql);
        ps.setInt(1, idVenta);
        ps.setInt(2, idProducto);
        ps.setInt(3, cantidad);
        ps.setDouble(4, precioUnitario);
        ps.setDouble(5, cantidad * precioUnitario);
        registros = ps.executeUpdate();
    } catch (SQLException ex) {
        ex.printStackTrace();
    } finally {
        try {
            if (ps != null) ps.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    return registros;
}


}
