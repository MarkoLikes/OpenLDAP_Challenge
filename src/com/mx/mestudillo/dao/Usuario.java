package com.mx.mestudillo.dao;

/**
 *
 * @author Marco Estudillo
 */
public class Usuario {

    /**
     * Variables que permiten registrar la información de los usuarios en la BD
     */
    private String correo;
    private String nombre;
    private String apellido;
    private String password;

    public Usuario(String correo, String nombre, String apellido, String password) {
        this.correo = correo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.password = password;
    }

    public Usuario() {
    }

    public String getCorreo() {
        return correo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getPassword() {
        return password;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
