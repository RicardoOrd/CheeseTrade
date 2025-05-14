package com.roc.cheesetrade.dao;

import com.roc.cheesetrade.db.DBConexion;
import java.sql.*;

public class DetalleVentaDAO {

    public int insertarDetalleVenta(int idVenta, int idProducto, int cantidad, double precioUnitario) {
        Connection con = DBConexion.obtenerConexion();
        PreparedStatement pstm = null;
        int registros = 0;

        try {
            String sql = "INSERT INTO tbldetalle_venta (id_venta, id_producto, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";
            pstm = con.prepareStatement(sql);
            pstm.setInt(1, idVenta);
            pstm.setInt(2, idProducto);
            pstm.setInt(3, cantidad);
            pstm.setDouble(4, precioUnitario);

            registros = pstm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (pstm != null) pstm.close();
                DBConexion.cerrarConexion();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return registros;
    }
}
