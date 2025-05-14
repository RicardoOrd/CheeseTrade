package com.roc.cheesetrade.dao;

import com.roc.cheesetrade.db.DBConexion;
import com.roc.cheesetrade.entities.Usuario;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author nuevo
 */
public class UsuarioDAO {

    public boolean insertar(Usuario usuario) {
        String sql = "INSERT INTO tblusuarios (usuario, contraseña, correo, direccion, celular, rol, icon) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConexion.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario.getUsuario());
            ps.setString(2, usuario.getContrasena());
            ps.setString(3, usuario.getCorreo());
            ps.setString(4, usuario.getDireccion());
            ps.setString(5, usuario.getCelular());
            ps.setString(6, usuario.getRol());
            ps.setString(7, usuario.getIcon());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM tblusuarios";
        try (Connection conn = DBConexion.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id_usuario"));
                usuario.setUsuario(rs.getString("usuario"));
                usuario.setContrasena(rs.getString("contraseña"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setDireccion(rs.getString("direccion"));
                usuario.setCelular(rs.getString("celular"));
                usuario.setRol(rs.getString("rol"));
                usuario.setIcon(rs.getString("icon"));
                lista.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM tblusuarios WHERE id = ?";
        try (Connection conn = DBConexion.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> obtenerRoles() {
        return Arrays.asList("Administrador", "Empleado");
    }

    public List<String> obtenerIconos() {
        return Arrays.asList("icono1.png", "icono2.png", "icono3.png");
    }


}

