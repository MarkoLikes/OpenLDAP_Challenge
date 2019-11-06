package com.mx.mestudillo.modelo;

import com.mx.mestudillo.dao.AuthCorreo;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Marco Estudillo
 */
public class EnvCorreo {

    /**
     * Variables donde se arman el asunto y cuerpo del correo
     */
    private final String asunto;
    private String cuerpo;

    /**
     * Se inicializa el asunto que llevarán todos los correos
     */
    public EnvCorreo() {
        this.asunto = "Crenciales de acceso - Challenge OpenLDAP";
    }

    /**
     * Método encargado de enviar correo a los destinatarios correspondientes
     *
     * @param destinatario
     * @param password
     */
    public void enviarCorreo(String destinatario, String password) {
        Properties prop = new Properties();

        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

        prop.put("mail.smtp.user", AuthCorreo.getUsuario());
        prop.put("mail.smtp.clave", AuthCorreo.getPassword());

        Session session = Session.getDefaultInstance(prop);
        MimeMessage message = new MimeMessage(session);

        this.cuerpo = "A continuación se envían tus credenciales de acceso.\n\n"
                + "Usuario: " + destinatario + "\n"
                + "Password: " + password + "\n\n"
                + "El presente correo es únicamente con fines didácticos.";

        try {
            message.setFrom(new InternetAddress(AuthCorreo.getUsuario()));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
            message.setSubject(this.asunto);
            message.setText(this.cuerpo);

            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", AuthCorreo.getUsuario(), AuthCorreo.getPassword());
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

        } catch (Exception ex) {
        }
    }
}
