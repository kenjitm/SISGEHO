/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Recurso;
import entity.TipoRecurso;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author Raul A. Hernandez
 */
@ManagedBean
@RequestScoped
public class RecursoBean {

    private Recurso recurso;
    private Integer id;

    public TipoRecurso[] getTipoRecurso() {
        return TipoRecurso.values();
    }

    public Recurso getRecurso() {
        return recurso;
    }

    public void setRecurso(Recurso recurso) {
        this.recurso = recurso;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RecursoBean() {
        recurso = new Recurso();
    }

    public String home() {
        return "index.xhtml";
    }

    public String irSalones(){
        return "gestionSalones.xhtml";
    }

    public List<Recurso> obtenerRecurso() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<Recurso> q = em.createNamedQuery("Recurso.findAll", Recurso.class);
        return q.getResultList();
    }

    public Recurso buscarRecursoById(Integer id) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        recurso = em.find(Recurso.class, id);
        return recurso;
    }

    public void guardarRecurso() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        //recurso.setDescripciontipo(recurso.getTipo());
        em.getTransaction().begin();
        em.persist(recurso);
        em.getTransaction().commit();
        recurso = new Recurso();
    }

    public void editarRecurso(Recurso salon) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(salon);
        em.getTransaction().commit();
    }

    public void eliminarRecurso(Integer id) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        Recurso salon = em.find(Recurso.class, id);
        em.getTransaction().begin();
        em.remove(salon);
        em.getTransaction().commit();
    }

}
