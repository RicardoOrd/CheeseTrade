package com.roc.cheesetrade.entities;

/**
 *
 * @author nuevo
 */
public class Usuario {
    private int id;
    private String usuario;      
    private String contrasena;
    private String correo;
    private String direccion;
    private String celular;
    private String rol;
    private String icon;

    public Usuario() {
    }
    
    public Usuario(String usuario, String contrasena, String correo, String direccion, String celular, String rol) {
    this.usuario = usuario;
    this.contrasena = contrasena;
    this.correo = correo;
    this.direccion = direccion;
    this.celular = celular;
    this.rol = rol;
}

    public Usuario( int id, String usuario, String contrasena, String correo, String direccion, String celular, String rol) {
        this.id = id;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.correo = correo;
        this.direccion = direccion;
        this.celular = celular;
        this.rol = rol;
    }

    
    
    
    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

}
