/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;


import entity.Periodo;
import java.util.Date;
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
    private Date Fecha_inicio;
    private Date Fecha_fin;

    public Date getFecha_inicio() {
        return Fecha_inicio;
    }

    public void setFecha_inicio(Date Fecha_inicio) {
        this.Fecha_inicio = Fecha_inicio;
    }

    public Date getFecha_fin() {
        return Fecha_fin;
    }

    public void setFecha_fin(Date Fecha_fin) {
        this.Fecha_fin = Fecha_fin;
    }

   
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
    public List<Periodo> obtenerPeriodos() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<Periodo> q = em.createNamedQuery("Periodo.findAll", Periodo.class);
        return q.getResultList();
    }
    public List<Periodo> getFilteredPeriodos() {  
        return filteredPeriodo;  
    }  
    public void guardarPeriodos() {
        Periodo periodo = new Periodo();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        periodo.setId(id);
        periodo.setDescripcion(this.descripcion);
        periodo.setFechainicio(Fecha_inicio);
        periodo.setFechafin(Fecha_fin);
        em.getTransaction().begin();
        em.persist(periodo);
        em.getTransaction().commit();
        
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "ÈXITO", "Registro realizado satisfactoriamente");
        // For PrimeFaces < 6.2
        RequestContext.getCurrentInstance().showMessageInDialog(message);

    }

    
    
}
