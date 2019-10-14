/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Horario;
import entity.Periodos;
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
    private Periodos Periodos;
    private int id;
    private String periodo;
    private List<Periodos> filteredPeriodos;  
    public Periodos getPeriodos() {
        return Periodos;
    }

    public void setPeriodos(Periodos Periodos) {
        this.Periodos = Periodos;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    

  
    public beanPeriodo() {
        Periodos = new Periodos();
    }
    public String irPeriodos() {
        return "periodos.xhtml";
    }
    public List<Periodos> obtenerPeriodos() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<Periodos> q = em.createNamedQuery("Periodos.findAll", Periodos.class);
        return q.getResultList();
    }
    public List<Periodos> getFilteredPeriodos() {  
        return filteredPeriodos;  
    }  
    public void guardarPeriodos() {
        Periodos periodo = new Periodos();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        periodo.setId(id);
        periodo.setPeriodo(this.periodo);
        
        em.getTransaction().begin();
        em.persist(periodo);
        em.getTransaction().commit();
        
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "ÈXITO", "Registro realizado satisfactoriamente");
        // For PrimeFaces < 6.2
        RequestContext.getCurrentInstance().showMessageInDialog(message);

    }
    
}
