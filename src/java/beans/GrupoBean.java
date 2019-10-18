/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Asignaturas;
import entity.Grupos;
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
    private Asignaturas asignatura;
    private int id;
    private List<Grupos> listaGrupos = new ArrayList<>();
    private List<Asignaturas> listaAsignaturas = new ArrayList<>();

    public String getDescripcion() {
        return descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Grupos> getListaGrupos() {
        return listaGrupos;
    }

    public void setListaGrupos(List<Grupos> listaGrupos) {
        this.listaGrupos = listaGrupos;
    }

    public List<Asignaturas> getListaAsignaturas() {
        return listaAsignaturas;
    }

    public void setListaAsignaturas(List<Asignaturas> listaAsignaturas) {
        this.listaAsignaturas = listaAsignaturas;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Asignaturas getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(Asignaturas asignatura) {
        this.asignatura = asignatura;
    }

    public GrupoBean() {
        id = 0;
        consultarGrupos();
    }

    public String home() {
        return "index.xhtml";
    }

    public String irGrupo() {
        return "gestionGrupos.xhtml";
    }

    public void consultarGrupos() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();

        TypedQuery<Grupos> consultaGrupos = em.createNamedQuery("Grupos.findAll", Grupos.class);
        listaGrupos = consultaGrupos.getResultList();
    }

    public void consultarGruposById() {
        if (id != 0) {
            EntityManagerFactory emf
                    = Persistence.createEntityManagerFactory("SisgehoPU");
            EntityManager em = emf.createEntityManager();

            TypedQuery<Grupos> consultaGrupos = em.createNamedQuery("Grupos.findByID", Grupos.class);
            consultaGrupos.setParameter("id", id);
            listaGrupos = consultaGrupos.getResultList();
        }
    }

    public void buscarAsginatura() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();

        TypedQuery<Asignaturas> consultaAsignatura = em.createNamedQuery("Asignaturas.findAll", Asignaturas.class);
        listaAsignaturas = consultaAsignatura.getResultList();
    }

    public void guardarGrupo() {
        Grupos grupo = new Grupos();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        grupo.setId(id);
        grupo.setDescripcion(descripcion);
        grupo.setRowidAsignatura(asignatura);
        em.getTransaction().begin();
        em.persist(grupo);
        em.getTransaction().commit();
    }

}
