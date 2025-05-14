package com.roc.cheesetrade.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nuevo
 */
public class DBConexion {
    
    private static Connection conexion;
    
    private static void abrirConexion (){
        try {
            String url = "jdbc:mysql://127.0.0.1:3306/bd_cheesetrade";
            String usuario= "root";
            String password= "1234";
            
            conexion = DriverManager.getConnection(url, usuario, password);

            System.out.println("Conexion Establecida");
            
        } catch (SQLException ex) {
            Logger.getLogger(DBConexion.class.getName()).log(
                    Level.SEVERE,null, ex);
        }
    }
    
    public static Connection obtenerConexion() {
        try {
            if (conexion == null) {
                abrirConexion();
            } else if (conexion.isClosed()) {
                abrirConexion();
            }

            return conexion;
        } catch (SQLException ex) {
            Logger.getLogger(DBConexion.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static void cerrarConexion() {
        if (conexion != null) {

            try {
                conexion.close();
                System.out.println("Conexión cerrrada");
            } catch (SQLException ex) {
                Logger.getLogger(DBConexion.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
    }
}

        

