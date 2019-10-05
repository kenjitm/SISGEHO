/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Pensum;
import entity.Programa;
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
public class CrearProgramaBean implements Serializable {

    /**
     * Creates a new instance of LoginBean
     */
    String usuario;
    String password;
    private SessionUtils session;
    private String descripcionPrograma;
    private Pensum pensum;
    private String descripcionTipo;
    private String modalidad;
    private String duracion;
    private String titulo;
    private String registroCalificado;
    private String snies;
    private String director;
    private String email;

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

    public CrearProgramaBean() {
    }

    public void crear() {
        Programa programa = new Programa();
        if (!descripcionPrograma.isEmpty()) {
            programa.setDescripcionprograma(descripcionPrograma);
        }
        if (!descripcionTipo.isEmpty()) {
            programa.setDescripciontipo(descripcionTipo);
        }
        if (!modalidad.isEmpty()) {
            programa.setModalidad(modalidad);
        }
        if (!duracion.isEmpty()) {
            programa.setDuracion(duracion);
        }
        if (!titulo.isEmpty()) {
            programa.setTitulo(titulo);
        }
        if (!registroCalificado.isEmpty()) {
            programa.setRegistrocalificado(registroCalificado);
        }
        if (!snies.isEmpty()) {
            programa.setRegistrocalificado(snies);
        }
        if (!director.isEmpty()) {
            programa.setRegistrocalificado(director);
        }
        if (!email.isEmpty()) {
            programa.setRegistrocalificado(email);
        }

    }

    public void getAllPrograms() {

        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();

        TypedQuery<Programa> consultaPrograma = em.createNamedQuery("Programa.findAll", Programa.class);
        //consultaPrograma.setParameter("programa", programa);
        List<Programa> lista = consultaPrograma.getResultList();
        if (lista != null) {
            if (!lista.isEmpty() && lista.get(0) != null) {

                //
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No existen programas creados", "No existen programas");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No existen programas creados", "No existen programas");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

    }

    public String home() {
        return "index.xhtml";
    }
}
