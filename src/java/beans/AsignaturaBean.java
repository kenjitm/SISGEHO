/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Asignaturas;
import entity.TipoJornada;
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
public class AsignaturaBean {

    private Asignaturas asignatura;

    public AsignaturaBean() {
        asignatura = new Asignaturas();
    }

    public Asignaturas getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(Asignaturas asignatura) {
        this.asignatura = asignatura;
    }
    
    public TipoJornada[] getTipoJornadas() {
        return TipoJornada.values();
    }
    
    public String irAsignatura(){
        return "gestionAsignaturas.xhtml";
    }

    public List<Asignaturas> obtenerAsignaturas() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<Asignaturas> q = em.createNamedQuery("Asignaturas.findAll", Asignaturas.class);
        return q.getResultList();
    }

    public Asignaturas buscarAsignaturaById(Integer id) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        asignatura = em.find(Asignaturas.class, id);
        return asignatura;
    }

    public void guardarAsignatura() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        asignatura.setRowid(asignatura.getId().toString());
        em.getTransaction().begin();
        em.persist(asignatura);
        em.getTransaction().commit();
        asignatura = new Asignaturas();
    }

    public void editarAsignatura(Asignaturas salon) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(salon);
        em.getTransaction().commit();
    }

    public void eliminarAsignatura(Integer id) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        Asignaturas asignatura = em.find(Asignaturas.class, id);
        em.getTransaction().begin();
        em.remove(asignatura);
        em.getTransaction().commit();
    }

}
