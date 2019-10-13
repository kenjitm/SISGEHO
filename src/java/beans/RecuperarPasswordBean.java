/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import com.sun.mail.smtp.SMTPTransport;
import entity.Usuario;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.MailcapCommandMap;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import utils.HTMLDataSource;
import utils.SMTPConfiguration;

/**
 *
 * @author Raul A. Hernandez
 */
@ManagedBean
@RequestScoped
public class RecuperarPasswordBean {

    private String emailTo;
    private Usuario usuario;

    /**
     * Creates a new instance of RecuperarPassword
     */
    public RecuperarPasswordBean() {
    }

    public String getEmailTo() {
        return emailTo;
    }

    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }

    public String home() {
        return "index.xhtml";
    }

    private Usuario buscarUsuarioPorEmail() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<Usuario> users = em.createNamedQuery("Usuario.findByEmail", Usuario.class);
        users.setParameter("email", emailTo);
        return (users.getResultList().isEmpty()) ? null : users.getResultList().get(0);
    }

    private String construirMensaje() throws SQLException {
        StringBuilder mensaje = new StringBuilder();
        usuario = buscarUsuarioPorEmail();
        if (usuario == null) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "NO REGISTRADO", "No se encuentra registrado el email ingresado.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            throw new SQLException("No se encontró el usuario");
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(SMTPConfiguration.REAL_PATH + "resources/plantillas/plantillaCorreo.html")))) {
            Object[] lineas = reader.lines().toArray();
            for (int i = 0; i < lineas.length; i++) {
                if (((String) lineas[i]).contains("{{nombre}}")) {
                    lineas[i] = ((String) lineas[i]).replace("{{nombre}}", usuario.getNombre());
                }
                if (((String) lineas[i]).contains("{{apellido}}")) {
                    lineas[i] = ((String) lineas[i]).replace("{{apellido}}", usuario.getApellido());
                }
                if (((String) lineas[i]).contains("{{username}}")) {
                    lineas[i] = ((String) lineas[i]).replace("{{username}}", usuario.getUsuario());
                }
                if (((String) lineas[i]).contains("{{password}}")) {
                    lineas[i] = ((String) lineas[i]).replace("{{password}}", usuario.getPassword());
                }
                mensaje.append(lineas[i]);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RecuperarPasswordBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RecuperarPasswordBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mensaje.toString();
    }

    public void sendMail() {
        Properties prop = new Properties();
        prop.put("mail.smtp.host", SMTPConfiguration.SMTP_SERVER);
        prop.setProperty("mail.smtp.starttls.enable", "true");
        prop.setProperty("mail.smtp.port", "587");
        prop.setProperty("mail.smtp.user", SMTPConfiguration.EMAIL_FROM);
        prop.setProperty("mail.smtp.auth", "true");
        Session session = Session.getDefaultInstance(prop, null);
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(SMTPConfiguration.EMAIL_FROM));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
            message.setSubject(SMTPConfiguration.EMAIL_SUBJECT);
            message.setHeader("Content-Type", "text/html");
            message.setDataHandler(new DataHandler(new HTMLDataSource(construirMensaje())));
            try (SMTPTransport t = (SMTPTransport) session.getTransport("smtp")) {
                t.connect(SMTPConfiguration.EMAIL_FROM, SMTPConfiguration.PASSWORD);
                t.sendMessage(message, message.getAllRecipients());
            }
            FacesMessage notify = new FacesMessage(FacesMessage.SEVERITY_INFO, "REALIZADO CON ÉXITO", "Revisa tu email. Se te ha enviado un correo con tu contraseña");
            FacesContext.getCurrentInstance().addMessage(null, notify);
        } catch (SQLException | MessagingException ex) {
            Logger.getLogger(RecuperarPasswordBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
