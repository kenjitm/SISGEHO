/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Facultad;
import entity.Recurso;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import org.primefaces.context.RequestContext;

/**
 *
 * @author IngenieroDesarrollo
 */
@ManagedBean
@RequestScoped
public class facultadBean {
    private int id;
    private String descripcion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String irFacultad(){
        return "gestionFacultad.xhtml";
    }
    public List<Facultad> obtenerFacultades() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<Facultad> q = em.createNamedQuery("Facultad.findAll", Facultad.class);
        return q.getResultList();
    }
    public void registrarFacultad(){
        Facultad facultad = new Facultad();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        facultad.setId(id);
        facultad.setDescripcion(descripcion);
        em.getTransaction().begin();
        em.persist(facultad);
        em.getTransaction().commit();
        
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "ÈXITO", "Registro realizado satisfactoriamente");
            // For PrimeFaces < 6.2
            RequestContext.getCurrentInstance().showMessageInDialog(message);  
        //obtenerFacultades();
        this.id = 0;
        this.descripcion = null;
    }
}
