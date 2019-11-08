/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Usuario;
import java.sql.SQLException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.primefaces.context.RequestContext;

/**
 *
 * @author IngenieroDesarrollo
 */
@ManagedBean
@RequestScoped
public class cambiarPasswordBean {
    private String username;
    private String password;
    private String confirPassword;
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirPassword() {
        return confirPassword;
    }

    public void setConfirPassword(String confirPassword) {
        this.confirPassword = confirPassword;
    }
    public void cambiarPassword() throws SQLException, ClassNotFoundException{
        if(password.equals(confirPassword))
        {
         RegistroUsuarioBean process = new RegistroUsuarioBean();
        Usuario user = new Usuario();
        user = process.getUsuarioUser(username);
        /*EntityManagerFactory emf = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        em.createQuery("update usuario set contraseña = \'"+password+"\' where id="+user.getId())
        .executeUpdate();*/
        ConexDB db = new ConexDB();
        int rs = db.executeQuery("update usuario set contraseña = \'"+password+"\' where id="+user.getId());
        System.out.println("********* Resultado: "+rs+"******************");
        if(rs > 0)
        {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "ÈXITO", "Actualización realizada satisfactoriamente");
            // For PrimeFaces < 6.2
            RequestContext.getCurrentInstance().showMessageInDialog(message);   
            this.username = null;
            this.password = null;
            this.confirPassword = null;
        }
        }else
        {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "ERROR", "El password y su confirmación no son iguales");
            // For PrimeFaces < 6.2
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        }
        
    }
    public String irCambio()
    {
        return "cambiarPassword.xhtml";
    }
}
