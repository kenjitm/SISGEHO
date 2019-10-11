/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Recursos;
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

    private Recursos recurso;
    private Integer id;

    public TipoRecurso[] getTipoRecursos() {
        return TipoRecurso.values();
    }

    public Recursos getRecurso() {
        return recurso;
    }

    public void setRecurso(Recursos recurso) {
        this.recurso = recurso;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RecursoBean() {
        recurso = new Recursos();
    }

    public String home() {
        return "index.xhtml";
    }

    public String salones(){
        return "gestionSalones.xhtml";
    }

    public List<Recursos> obtenerRecursos() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<Recursos> q = em.createNamedQuery("Recursos.findAll", Recursos.class);
        return q.getResultList();
    }

    public Recursos buscar(Integer id) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        recurso = em.find(Recursos.class, id);
        return recurso;
    }

    public void guardarRecurso() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        recurso.setDescripciontipo(recurso.getTipo());
        em.getTransaction().begin();
        em.persist(recurso);
        em.getTransaction().commit();
        recurso = new Recursos();
    }

    public void editarRecurso(Recursos salon) {
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
        Recursos salon = em.find(Recursos.class, id);
        em.getTransaction().begin();
        em.remove(salon);
        em.getTransaction().commit();
    }

}
