/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Usuario;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import utils.JpaUtil;
import utils.SessionUtils;

/**
 *
 * @author KTANAKA
 */
@ManagedBean
@RequestScoped
public class LoginBean implements Serializable {

    /**
     * Creates a new instance of LoginBean
     */
    String usuario;
    String password;
    private SessionUtils session;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LoginBean() {
    }

    public String login() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();

        TypedQuery<Usuario> consultaUsuarios = em.createNamedQuery("Usuario.findByUsuario", Usuario.class);
        consultaUsuarios.setParameter("Usuario", usuario);
        List<Usuario> lista = consultaUsuarios.getResultList();
        lista = lista.stream().filter(lu -> lu != null && lu.getUsuario() != null && lu.getUsuario().equals(usuario)).collect(Collectors.toList());
        if (lista == null || lista.size() > 0) {
            session.add("user", usuario);
            return "prueba1";
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Acceso denegado", "Usuario o contraseña incorrecta");
            FacesContext.getCurrentInstance().addMessage(null, message);
            return null;
        }

    }

}
