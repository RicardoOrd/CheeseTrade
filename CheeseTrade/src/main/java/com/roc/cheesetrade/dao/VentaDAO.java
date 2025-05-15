package com.roc.cheesetrade.dao;

import com.roc.cheesetrade.db.DBConexion;
import java.sql.*;

public class VentaDAO {

    public int insertarVenta(Connection con, Date fecha, double total, int idUsuario) {
        // Ya NO abras la conexión aquí, usa la que te pasan
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int idVentaGenerada = -1;

        try {
            String sql = "INSERT INTO tblventas (fecha, total, usuario_id) VALUES (?, ?, ?)";
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
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstm != null) {
                    pstm.close();
                }
                // NO cierres la conexión aquí
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return idVentaGenerada;
    }

    public int registrarHistorialCompra(Connection con, int idVenta, double total, String metodoPago, String estado) {
        PreparedStatement pstm = null;
        int registros = 0;
        try {
            String sql = "INSERT INTO tblhistorial_compras (id_venta, hist_total, hist_metodo_pago, hist_estado) VALUES (?, ?, ?, ?)";
            pstm = con.prepareStatement(sql);
            pstm.setInt(1, idVenta);
            pstm.setDouble(2, total);
            pstm.setString(3, metodoPago); // ejemplo: "efectivo"
            pstm.setString(4, estado);     // ejemplo: "pagado"
            registros = pstm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (pstm != null) {
                    pstm.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return registros;
    }

}
