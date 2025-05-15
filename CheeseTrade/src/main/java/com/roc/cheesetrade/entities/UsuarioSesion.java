package com.roc.cheesetrade.entities;

public class UsuarioSesion {
    private String usuario;
    private String rol;

    public UsuarioSesion(String usuario, String rol) {
        this.usuario = usuario;
        this.rol = rol;
    }

    public String getUsuario() { return usuario; }
    public String getRol() { return rol; }
}
