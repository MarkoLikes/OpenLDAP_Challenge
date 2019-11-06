package com.mx.mestudillo.modelo;

import com.mx.mestudillo.dao.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Marco Estudillo
 */
public class SQLUsuario {

    /**
     * Variables que permitirán realizar la conexión, consultas y
     * actualizaciones en la Base de Datos
     */
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet result;
    private String sql;
    private int tuples;

    private final ConexionBD conexion;

    /**
     * Se inicializan las credenciales de conexión
     */
    public SQLUsuario() {
        this.conexion = new ConexionBD();
    }

    /**
     * Método para registrar nuevos usuarios en la Base de Datos
     *
     * @param usuario
     */
    public void guardarUsuario(Usuario usuario) {

        // Sentencia SQL para el registro de un nuevo usuario
        sql = "INSERT INTO users(correo, nombre, apellido, password) "
                + "VALUES(?, ?, ?, SHA2(?, 256))";

        connection = this.conexion.conectar();

        // Envío de la sentencia y los datos al manejador de MySQL
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, usuario.getCorreo());
            statement.setString(2, usuario.getNombre());
            statement.setString(3, usuario.getApellido());
            statement.setString(4, usuario.getPassword());

            tuples = statement.executeUpdate();

            if (tuples == 1) {
                System.out.println("\nNuevo usuario agregado con éxito a la Base de Datos...");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        this.conexion.desconectar(connection);
    }

    /**
     * Función que devuelve la información del Usuario
     *
     * @param correo
     * @return Usuario
     */
    public Usuario getUsuario(String correo) {

        // Sentencia SQL para la consulta del nombre y apellido del usuario correspondiente
        sql = "SELECT nombre, apellido FROM users WHERE correo = ?";
        connection = this.conexion.conectar();
        Usuario usuario = null;

        // Envío de la sentencia y los datos al manejador de MySQL
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, correo);
            result = statement.executeQuery();

            if (result.next()) {
                usuario = new Usuario();
                usuario.setNombre(result.getString("nombre"));
                usuario.setApellido(result.getString("apellido"));
                usuario.setCorreo(correo);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        this.conexion.desconectar(connection);
        return usuario;
    }

    /**
     * Método encargado de actualizar el password de los Usuarios
     *
     * @param correo
     * @param password
     */
    public void actualizarPassword(String correo, String password) {

        // Sentencia SQL para la actualización del password del usuario correspondiente
        sql = "UPDATE users SET PASSWORD = SHA2(?, 256) WHERE correo = ?";
        connection = this.conexion.conectar();

        // Envío de la sentencia y los datos al manejador de MySQL
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, password);
            statement.setString(2, correo);

            tuples = statement.executeUpdate();

            if (tuples == 1) {
                System.out.println("\nUsuario actualizado éxitosamente...");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        this.conexion.desconectar(connection);
    }
}
