package com.mx.mestudillo.dao;

/**
 *
 * @author Marco Estudillo
 */
public class AuthCorreo {

    /**
     * Credenciales para la conexión con GMAIL para el envío de correos
     */
    private static final String USUARIO = "challengesmercadolibre@gmail.com";
    private static final String PASSWORD = "Challenges1234";

    public static String getUsuario() {
        return USUARIO;
    }

    public static String getPassword() {
        return PASSWORD;
    }
}
