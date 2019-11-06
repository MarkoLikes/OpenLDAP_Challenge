package com.mx.mestudillo.modelo;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;

/**
 *
 * @author Marco Estudillo
 */
public class SesionLDAP {

    /**
     * Variables que permitirán inicializar el contexto y envío de parámetros al
     * servidor LDAP
     */
    private static DirContext contexto;
    private Hashtable<String, String> parametros;

    /**
     * Función encargada de establecer la conexión con sel servidor LDAP,
     * dependiendo las credenciales del usuario
     *
     * @param usuario
     * @param password
     * @param tipo : administrador (1) ó usuario (0)
     * @return String : Estatus de conexión
     */
    public String conectar(String usuario, String password, int tipo) {
        parametros = new Hashtable<String, String>();

        try {
            parametros.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            parametros.put(Context.SECURITY_AUTHENTICATION, "simple");
            parametros.put(Context.PROVIDER_URL, "ldap://18.188.43.162:389");

            // parámetros de grupo dependiendo el tipo de usuario que corresponda
            if (tipo == 1) {
                parametros.put(Context.SECURITY_PRINCIPAL, "cn=" + usuario + ",dc=example,dc=com");
            } else {
                parametros.put(Context.SECURITY_PRINCIPAL, "cn=" + usuario + ",ou=People,dc=example,dc=com");
            }

            parametros.put(Context.SECURITY_CREDENTIALS, password);

            contexto = new InitialDirContext(parametros);
            return "OK";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * Método para registrar nuevos usuarios en el servidor LDAP
     *
     * @param nombre
     * @param email
     * @param password
     */
    public void ingresar(String nombre, String email, String password) {
        Attributes matchAttrs = new BasicAttributes(true);
        matchAttrs.put(new BasicAttribute("cn", email));
        matchAttrs.put(new BasicAttribute("sn", nombre));
        matchAttrs.put(new BasicAttribute("userpassword", password));

        matchAttrs.put(new BasicAttribute("objectclass", "person"));

        String name = "cn=" + email + ",ou=People,dc=example,dc=com";
        InitialDirContext iniDirContext = (InitialDirContext) contexto;

        try {
            iniDirContext.bind(name, contexto, matchAttrs);
        } catch (Exception ex) {
        }
    }

    /**
     * Función encargada de actualizar el password del usuario correspondiente
     * en el servidor LDAP
     *
     * @param email
     * @param password
     * @return String : Estatus de operación
     */
    public String actualizar(String email, String password) {
        ModificationItem[] modificaciones = new ModificationItem[1];
        Attribute modificacion = new BasicAttribute("userpassword", password);
        modificaciones[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, modificacion);

        try {
            contexto.modifyAttributes("cn=" + email + ",ou=People,dc=example,dc=com", modificaciones);
            return "OK";
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }
}
