/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Sede;
import java.util.List;
import java.util.logging.Level;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
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
@ViewScoped //INDISPENSABLE PONER ESTA ANOTACIÓN EN VEZ DEL REQUESTSCOPED
public class SedeBean {

    private Integer id;
    private Sede sede;
    private Sede sedeBusqueda;

    //INDISPENSABLE ESTA VARIABLE CON EL ALCANCE ESTÁTICO
    private static List<Sede> sedesList;

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

    public Sede getSedeBusqueda() {
        return sedeBusqueda;
    }

    public void setSedeBusqueda(Sede sedeBusqueda) {
        this.sedeBusqueda = sedeBusqueda;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    //INDISPENSABLE EL MÉTODO GET. SÓLO EL GET
    public List<Sede> getSedesList() {
        return sedesList;
    }

    /**
     * Creates a new instance of SedeBean
     */
    public SedeBean() {
        sede = new Sede();
        sedeBusqueda = new Sede();
        obtenerSedes();
    }

    //EL MÉTODO DEBE QUEDAR ASÍ MISMO
    private void obtenerSedes() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<Sede> q = em.createNamedQuery("Sede.findAll", Sede.class);
        sedesList = q.getResultList();
    }

    public void buscarSedePorId(Integer id) {
        sedeBusqueda = buscarById(id);
    }

    public Sede buscarById(Integer id) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<Sede> sedes = em.createNamedQuery("Sede.findById", Sede.class);
        sedes.setParameter("id", id);
        return (sedes.getResultList().isEmpty())?  null : sedes.getResultList().get(0);
    }

    //EL MÉTODO DEBE QUEDAR ASÍ MISMO
    public void guardarSede() {
        try {
            EntityManagerFactory emf
                    = Persistence.createEntityManagerFactory("SisgehoPU");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(sede);
            em.getTransaction().commit();
            em.close();
            sede = new Sede();
            obtenerSedes(); //Actualiza lista
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "REALIZADO CON ÉXITO", "Se guardó correctamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "NO SE PUDO REALIZAR", "No se pudo guardar los datos. Inténtelo más tarde");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    //Agregar este método para campos booleanos, como "activo"
    public String transformActivo(Boolean activo) {
        return (activo) ? "ACTIVA" : "INACTIVA";
    }

    //INDISPENSABLE tener este método
    public void enableEditarOption(Sede sede, boolean estado) {
        sede.setEditable(estado);
    }

    //EL MÉTODO DEBE QUEDAR ASÍ MISMO
    public void editarSede(Sede s) {
        try {
            EntityManagerFactory emf
                    = Persistence.createEntityManagerFactory("SisgehoPU");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.merge(sede);
            em.getTransaction().commit();
            em.close();
            obtenerSedes(); //Actualiza lista
            s.setEditable(false);
            sede = new Sede();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "REALIZADO CON ÉXITO", "Se actualizó correctamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "NO SE PUDO REALIZAR", "No se pudo editar los datos. Inténtelo más tarde");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    //EL MÉTODO DEBE QUEDAR ASÍ MISMO
    public void eliminarSede() {
        try {
            EntityManagerFactory emf
                    = Persistence.createEntityManagerFactory("SisgehoPU");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            if (!em.contains(sede)) {
                sede = em.merge(sede);
            }
            em.remove(sede);
            em.getTransaction().commit();
            obtenerSedes(); //Actualiza lista
            sede = new Sede();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "REALIZADO CON ÉXITO", "Se eliminó correctamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "NO SE PUDO REALIZAR", "No se pudo eliminar los datos. Inténtelo más tarde");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
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
            return ((SedeBean) context.getApplication().evaluateExpressionGet(context, "#{" + "sedeBean" + "}", SedeBean.class)).buscarById(getKey(value));
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
