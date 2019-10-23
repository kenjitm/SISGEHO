/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Asignatura;
import entity.Grupo;
import entity.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
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
public class GrupoBean implements Serializable {

    /**
     * Creates a new instance of LoginBean
     */
    private String descripcion;
    private Asignatura asignatura;
    private int id;
    private List<Grupo> listaGrupo = new ArrayList<>();
    private List<Asignatura> listaAsignatura = new ArrayList<>();

    public String getDescripcion() {
        return descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Grupo> getListaGrupo() {
        return listaGrupo;
    }

    public void setListaGrupo(List<Grupo> listaGrupo) {
        this.listaGrupo = listaGrupo;
    }

    public List<Asignatura> getListaAsignatura() {
        return listaAsignatura;
    }

    public void setListaAsignatura(List<Asignatura> listaAsignatura) {
        this.listaAsignatura = listaAsignatura;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Asignatura getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
    }

    public GrupoBean() {
        id = 0;
        consultarGrupo();
    }

    public String home() {
        return "index.xhtml";
    }

    public String irGrupo() {
        return "gestionGrupo.xhtml";
    }

    public void consultarGrupo() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();

        TypedQuery<Grupo> consultaGrupo = em.createNamedQuery("Grupo.findAll", Grupo.class);
        listaGrupo = consultaGrupo.getResultList();
    }

    public void consultarGrupoById() {
        if (id != 0) {
            EntityManagerFactory emf
                    = Persistence.createEntityManagerFactory("SisgehoPU");
            EntityManager em = emf.createEntityManager();

            TypedQuery<Grupo> consultaGrupo = em.createNamedQuery("Grupo.findByID", Grupo.class);
            consultaGrupo.setParameter("id", id);
            listaGrupo = consultaGrupo.getResultList();
        }
    }

    public void buscarAsginatura() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();

        TypedQuery<Asignatura> consultaAsignatura = em.createNamedQuery("Asignatura.findAll", Asignatura.class);
        listaAsignatura = consultaAsignatura.getResultList();
    }

    public void guardarGrupo() {
        Grupo grupo = new Grupo();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        grupo.setId(id);
        grupo.setDescripcion(descripcion);
        //grupo.setRowidAsignatura(asignatura);
        em.getTransaction().begin();
        em.persist(grupo);
        em.getTransaction().commit();
    }

}
