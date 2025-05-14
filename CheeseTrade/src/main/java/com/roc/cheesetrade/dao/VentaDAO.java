package com.roc.cheesetrade.dao;

import com.roc.cheesetrade.db.DBConexion;
import java.sql.*;

public class VentaDAO {

    public int insertarVenta(Date fecha, double total, int idUsuario) {
        Connection con = DBConexion.obtenerConexion();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int idVentaGenerada = -1;

        try {
            String sql = "INSERT INTO tblventas (fecha_venta, total, id_usuario) VALUES (?, ?, ?)";
            pstm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstm.setDate(1, fecha);
            pstm.setDouble(2, total);
            pstm.setInt(3, idUsuario);
            pstm.executeUpdate();

            rs = pstm.getGeneratedKeys();
            if (rs.next()) {
                idVentaGenerada = rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Cambia a Logger si deseas
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstm != null) pstm.close();
                DBConexion.cerrarConexion();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return idVentaGenerada;
    }
}
