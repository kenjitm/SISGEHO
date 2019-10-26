/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Programa;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author KTANAKA
 */
@ManagedBean
@RequestScoped
public class ProgramaBean implements Serializable {

    /**
     * Creates a new instance of LoginBean
     */
    private Programa programa;

    public ProgramaBean() {
        this.consultarProgramas();
    }

    public Programa getPrograma() {
        return programa;
    }

    public void setPrograma(Programa programa) {
        this.programa = programa;
    }

    public String home() {
        return "index.xhtml";
    }

    public String irGrupo() {
        return "gestionGrupos.xhtml";
    }

    public List<Programa> consultarProgramas() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();

        TypedQuery<Programa> consultaProgramas = em.createNamedQuery("Programa.findAll", Programa.class);
        return consultaProgramas.getResultList();
    }

    public Programa consultarProgramaById(Integer id) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();

        return em.find(Programa.class, id);
    }

    public void guardarPrograma() {
        Programa programa = new Programa();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(programa);
        em.getTransaction().commit();
    }
}
