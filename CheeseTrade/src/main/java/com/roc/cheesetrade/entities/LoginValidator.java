package com.roc.cheesetrade.entities;

import com.roc.cheesetrade.db.DBConexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginValidator {

    // Método para autenticar al usuario
    public static boolean autenticarUsuario(String usuario, String password) {
        Connection con = DBConexion.obtenerConexion(); // Obtener la conexión
        if (con != null) {
            try {
                // Consulta SQL adaptada para tu tabla "tblusuarios"
                String sql = "SELECT * FROM tblusuarios WHERE usuario = ? AND contraseña = ?";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setString(1, usuario);
                stmt.setString(2, password);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    return true; // Usuario y contraseña válidos
                }
            } catch (SQLException ex) {
                System.err.println("Error de base de datos: " + ex.getMessage());
            } finally {
                DBConexion.cerrarConexion(); // Cerrar la conexión después de usarla
            }
        }
        return false; // Si no se encuentra el usuario o hay algún error
    }
}
