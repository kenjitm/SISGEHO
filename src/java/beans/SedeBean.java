/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Sede;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author Stiven Castro Arias
 */
@ManagedBean
@RequestScoped
public class SedeBean {

    private Integer id;
    private Sede sede;

    public String home() {
        return "index.xhtml";
    }
    
    public String irSedes() {
        return "gestionSedes.xhtml";
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
        
    /**
     * Creates a new instance of SedeBean
     */
    public SedeBean() {
        sede = new Sede();
    }

    public List<Sede> obtenerSedes() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<Sede> q = em.createNamedQuery("Sede.findAll", Sede.class);
        return q.getResultList();
    }

    public Sede buscarPorId(Integer id) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<Sede> sedes = em.createNamedQuery("Sede.findById", Sede.class);
        sedes.setParameter("id", id);
        sede = sedes.getResultList().get(0);
        return sede;
    }

    public void guardar() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        
        sede.setRowid(sede.getId().toString());
        em.getTransaction().begin();
        em.persist(sede);
        em.getTransaction().commit();
        sede = new Sede();
    }

    public void editar() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(sede);
        em.getTransaction().commit();
        sede = new Sede();
    }

    public void eliminar(Integer id) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        Sede sede = em.find(Sede.class, id);
        em.getTransaction().begin();
        em.remove(sede);
        em.getTransaction().commit();
    }

    @FacesConverter(forClass = Sede.class)
    public static class SedeBeanConverter implements Converter {

        Integer getKey(String value) {
            return Integer.valueOf(value);
        }

        String getStringKey(Integer value) {
            return new StringBuilder().append(value).toString();
        }

        @Override
        public Object getAsObject(FacesContext context, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            return ((SedeBean) context.getApplication().evaluateExpressionGet(context, "#{" + "sedeBean" + "}", SedeBean.class)).buscarPorId(getKey(value));
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object value) {
            if (value == null) {
                return null;
            } else if (value instanceof Sede) {
                return getStringKey(((Sede) value).getId());
            } else {
                throw new IllegalArgumentException("object " + value + " is of type " + value.getClass().getName() + "; expected type: " + Sede.class.getName());
            }
        }

    }

}
