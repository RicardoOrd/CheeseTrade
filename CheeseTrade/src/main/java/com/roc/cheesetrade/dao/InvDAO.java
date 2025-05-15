package com.roc.cheesetrade.dao;

import com.roc.cheesetrade.dao.InvDAO;
import com.roc.cheesetrade.db.DBConexion;
import com.roc.cheesetrade.entities.Inv;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author carl-
 */
public class InvDAO {

    public List<Inv> listar() {
        List<Inv> productos = new ArrayList<>();
        Connection conMySQL = DBConexion.obtenerConexion();
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            String consulta = "select id_producto as idProducto, "
                    + "prod_nombre as nombreProd,"
                    + "prod_descripcion as descripcion, "
                    + "prod_precio as precio, "
                    + "prod_stock as stock, "
                    + "prod_peso_unitario as peso_unit, "
                    + "prod_tipo as tipo "
                    + "from tblproductos";

            pstm = conMySQL.prepareStatement(consulta);
            rs = pstm.executeQuery();

            while (rs.next()) {
                Inv prod = new Inv();
                prod.setIdProducto(rs.getInt("idProducto"));
                prod.setNombreProd(rs.getString("nombreProd"));
                prod.setDescripcion(rs.getString("descripcion"));
                prod.setPrecio(rs.getDouble("precio"));
                prod.setStock(rs.getInt("stock"));
                prod.setPeso_unit(rs.getDouble("peso_unit"));
                prod.setTipo(rs.getString("tipo"));
                productos.add(prod);
            }

        } catch (SQLException ex) {
            Logger.getLogger(InvDAO.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstm != null) {
                    pstm.close();
                }
                DBConexion.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(InvDAO.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
        return productos;
    }

    public void obtenerListadoProd(DefaultTableModel modeloTabla) {
        Connection conMySQL = DBConexion.obtenerConexion();
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {

            String consulta = "select id_producto as idProducto, "
                    + "prod_nombre as nombreProd,"
                    + "prod_descripcion as descripcion, \n"
                    + "prod_precio as precio, "
                    + "prod_stock as stock,"
                    + "prod_peso_unitario as peso_unit, "
                    + "prod_tipo as tipo \n"
                    + "from tblproductos";

            pstm = conMySQL.prepareStatement(consulta);

            rs = pstm.executeQuery();

            int idProducto;
            String nombreProd;
            String descripcion;
            double precio;
            int stock;
            double peso_unit;
            String tipo;

            while (rs.next()) {
                idProducto = rs.getInt("idProducto");
                nombreProd = rs.getString("nombreProd");
                descripcion = rs.getString("descripcion");
                precio = rs.getDouble("precio");
                stock = rs.getInt("stock");
                peso_unit = rs.getDouble("peso_unit");
                tipo = rs.getString("tipo");

                Object[] fila = {
                    idProducto,
                    nombreProd,
                    descripcion,
                    precio,
                    stock,
                    peso_unit,
                    tipo,
                    "",
                    ""
                };

                modeloTabla.addRow(fila);
            }

        } catch (SQLException ex) {
            Logger.getLogger(InvDAO.class.getName()).log(
                    Level.SEVERE, ex.getMessage(), ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

                if (pstm != null) {
                    pstm.close();
                }

                DBConexion.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(InvDAO.class.getName()).log(
                        Level.SEVERE, ex.getMessage(), ex);
            }
        }

    }

    public Inv obtenerProducto(int idProducto) {
        Connection conMySQL = DBConexion.obtenerConexion();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Inv productoDevuelto = null;

        try {

            String consulta = "select id_producto as idProducto, "
                    + "prod_nombre as nombreProd,"
                    + "prod_descripcion as descripcion, \n"
                    + "prod_precio as precio, "
                    + "prod_stock as stock,"
                    + "prod_peso_unitario as peso_unit, "
                    + "prod_tipo as tipo \n"
                    + "from tblproductos "
                    + "where id_producto = ?;";

            pstm = conMySQL.prepareStatement(consulta);
            pstm.setInt(1, idProducto);

            rs = pstm.executeQuery();

            if (rs.next()) {
                productoDevuelto = new Inv();

                productoDevuelto.setIdProducto(rs.getInt("idProducto"));
                productoDevuelto.setNombreProd(rs.getString("nombreProd"));
                productoDevuelto.setDescripcion(rs.getString("descripcion"));
                productoDevuelto.setPrecio(rs.getDouble("precio"));
                productoDevuelto.setStock(rs.getInt("stock"));
                productoDevuelto.setPeso_unit(rs.getDouble("peso_unit"));
                productoDevuelto.setTipo(rs.getString("tipo"));

            }

            return productoDevuelto;

        } catch (SQLException ex) {
            Logger.getLogger(InvDAO.class.getName()).log(
                    Level.SEVERE, ex.getMessage(), ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

                if (pstm != null) {
                    pstm.close();
                }

                DBConexion.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(InvDAO.class.getName()).log(
                        Level.SEVERE, ex.getMessage(), ex);
            }
        }
        return productoDevuelto;
    }

    public int actualizarInv(Inv prodActualizar) {
        Connection conMySQL = DBConexion.obtenerConexion();
        PreparedStatement pstm = null;

        int registrosAfectados = 0;

        try {

            String update = "UPDATE tblproductos\n"
                    + "SET\n"
                    + "    prod_nombre = ?,\n"
                    + "    prod_descripcion = ?,\n"
                    + "    prod_precio = ?,\n"
                    + "    prod_stock = ?,\n"
                    + "    prod_peso_unitario = ?,\n"
                    + "    prod_tipo = ? \n"
                    + "WHERE\n"
                    + "    id_producto = ?;";

            pstm = conMySQL.prepareCall(update);
            pstm.setString(1, prodActualizar.getNombreProd());
            pstm.setString(2, prodActualizar.getDescripcion());
            pstm.setDouble(3, prodActualizar.getPrecio());
            pstm.setInt(4, prodActualizar.getStock());
            pstm.setDouble(5, prodActualizar.getPeso_unit());
            pstm.setString(6, prodActualizar.getTipo());
            pstm.setInt(7, prodActualizar.getIdProducto());

            registrosAfectados = pstm.executeUpdate();

            return registrosAfectados;

        } catch (SQLException ex) {
            Logger.getLogger(InvDAO.class.getName()).log(
                    Level.SEVERE, ex.getMessage(), ex);
        } finally {
            try {

                if (pstm != null) {
                    pstm.close();
                }

                DBConexion.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(InvDAO.class.getName()).log(
                        Level.SEVERE, ex.getMessage(), ex);
            }
        }

        return registrosAfectados;
    }

    public int insertarInv(Inv prodNuevo) {
        Connection conMySQL = DBConexion.obtenerConexion();
        PreparedStatement pstm = null;

        int registrosAfectados = 0;

        try {

            String update = "INSERT INTO bd_cheesetrade.tblproductos (\n"
                    + "    prod_nombre,\n"
                    + "    prod_descripcion,\n"
                    + "    prod_precio,\n"
                    + "    prod_stock,\n"
                    + "    prod_peso_unitario,\n"
                    + "    prod_tipo\n"
                    + ")\n"
                    + "VALUES (\n"
                    + "    ?,\n"
                    + "    ?,\n"
                    + "    ?,\n"
                    + "    ?,\n"
                    + "    ?,\n"
                    + "    ?\n"
                    + ")";

            pstm = conMySQL.prepareCall(update);
            pstm.setString(1, prodNuevo.getNombreProd());
            pstm.setString(2, prodNuevo.getDescripcion());
            pstm.setDouble(3, prodNuevo.getPrecio());
            pstm.setInt(4, prodNuevo.getStock());
            pstm.setDouble(5, prodNuevo.getPeso_unit());
            pstm.setString(6, prodNuevo.getTipo());

            registrosAfectados = pstm.executeUpdate();

            return registrosAfectados;

        } catch (SQLException ex) {
            Logger.getLogger(InvDAO.class.getName()).log(
                    Level.SEVERE, ex.getMessage(), ex);
        } finally {
            try {

                if (pstm != null) {
                    pstm.close();
                }

                DBConexion.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(InvDAO.class.getName()).log(
                        Level.SEVERE, ex.getMessage(), ex);
            }
        }

        return registrosAfectados;
    }

    public int eliminarProd(int idProd) {
        Connection conMySQL = DBConexion.obtenerConexion();
        PreparedStatement pstm = null;

        int registrosAfectados = 0;

        try {

            String update = "DELETE FROM tblproductos where id_producto = ?;";

            pstm = conMySQL.prepareCall(update);
            pstm.setInt(1, idProd);

            registrosAfectados = pstm.executeUpdate();

            return registrosAfectados;

        } catch (SQLException ex) {
            Logger.getLogger(InvDAO.class.getName()).log(
                    Level.SEVERE, ex.getMessage(), ex);
        } finally {
            try {

                if (pstm != null) {
                    pstm.close();
                }

                DBConexion.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(InvDAO.class.getName()).log(
                        Level.SEVERE, ex.getMessage(), ex);
            }
        }

        return registrosAfectados;
    }

    public int descontarStock(Connection con, int idProducto, int cantidadVendida) {
    PreparedStatement pstm = null;
    int registros = 0;

    try {
        String sql = "UPDATE tblproductos SET prod_stock = prod_stock - ? WHERE id_producto = ?";
        pstm = con.prepareStatement(sql);
        pstm.setInt(1, cantidadVendida);
        pstm.setInt(2, idProducto);
        registros = pstm.executeUpdate();
    } catch (SQLException ex) {
        ex.printStackTrace();
    } finally {
        try {
            if (pstm != null) pstm.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    return registros;
}

}
