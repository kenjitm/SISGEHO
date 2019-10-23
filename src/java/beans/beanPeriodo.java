/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Horario;
import entity.Periodo;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author SougiroHylian
 */
@ManagedBean
@RequestScoped
public class beanPeriodo {
    private Periodo Periodo;
    private int id;
    private String descripcion;
    private List<Periodo> filteredPeriodo;  
    public Periodo getPeriodo() {
        return Periodo;
    }

    public void setPeriodo(Periodo Periodo) {
        this.Periodo = Periodo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    

  
    public beanPeriodo() {
        Periodo = new Periodo();
    }
    public String irPeriodos() {
        return "periodos.xhtml";
    }
    public List<Periodo> obtenerPeriodo() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<Periodo> q = em.createNamedQuery("Periodo.findAll", Periodo.class);
        return q.getResultList();
    }
    public List<Periodo> getFilteredPeriodo() {  
        return filteredPeriodo;  
    }  
    public void guardarPeriodo() {
        Periodo periodo = new Periodo();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        periodo.setId(id);
        periodo.setDescripcion(this.descripcion);
        
        em.getTransaction().begin();
        em.persist(periodo);
        em.getTransaction().commit();
        
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "ÈXITO", "Registro realizado satisfactoriamente");
        // For PrimeFaces < 6.2
        RequestContext.getCurrentInstance().showMessageInDialog(message);

    }
    
}
