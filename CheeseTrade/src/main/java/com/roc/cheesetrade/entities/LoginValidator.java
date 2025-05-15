package com.roc.cheesetrade.entities;

import com.roc.cheesetrade.db.DBConexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginValidator {

    // Método para autenticar al usuario


    public static UsuarioSesion autenticarUsuario(String usuario, String password) {
        Connection con = DBConexion.obtenerConexion();
        if (con != null) {
            try {
                String sql = "SELECT usuario, rol FROM tblusuarios WHERE usuario = ? AND contraseña = ?";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setString(1, usuario);
                stmt.setString(2, password);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    return new UsuarioSesion(rs.getString("usuario"), rs.getString("rol"));
                }
            } catch (SQLException ex) {
                System.err.println("Error de base de datos: " + ex.getMessage());
            } finally {
                DBConexion.cerrarConexion();
            }
        }
        return null;
    }
}
