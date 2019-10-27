/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;


import entity.Periodo;
import entity.Sede;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
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
@ViewScoped //INDISPENSABLE PONER ESTA ANOTACIÓN EN VEZ DEL REQUESTSCOPED
public class beanPeriodo {
    private Integer id;
    private Periodo periodo;
    private Periodo periodoSearch;
    //INDISPENSABLE ESTA VARIABLE CON EL ALCANCE ESTÁTICO
    private static List<Periodo> periodoList;
     public String home() {
        return "index.xhtml";
    }
    public String irPeriodos() {
        return "periodos.xhtml";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public Periodo getPeriodoSearch() {
        return periodoSearch;
    }

    public void setPeriodoSearch(Periodo periodoSearch) {
        this.periodoSearch = periodoSearch;
    }
    //INDISPENSABLE EL MÉTODO GET. SÓLO EL GET
    public List<Periodo> getPeriodoList() {
        return periodoList;
    }
    /**
     * Creates a new instance of SedeBean
     */
    public beanPeriodo() {
        periodo = new Periodo();
        periodoSearch = new Periodo();
        obtenerPeriodos();
    }
    //EL MÉTODO DEBE QUEDAR ASÍ MISMO
    private void obtenerPeriodos() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<Periodo> q = em.createNamedQuery("Periodo.findAll", Periodo.class);
        periodoList = q.getResultList();
    }
    public void buscarPeriodoPorId(Integer id) {
        periodoSearch = buscarById(id);
    }
    public Periodo buscarById(Integer id) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<Periodo> periodo = em.createNamedQuery("Periodo.findById", Periodo.class);
        periodo.setParameter("id", id);
        return (periodo.getResultList().isEmpty())?  null : periodo.getResultList().get(0);
    }
    //EL MÉTODO DEBE QUEDAR ASÍ MISMO
    public void guardarPeriodo() {
        try {
            EntityManagerFactory emf
                    = Persistence.createEntityManagerFactory("SisgehoPU");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(periodo);
            em.getTransaction().commit();
            em.close();
            periodo = new Periodo();
            obtenerPeriodos(); //Actualiza lista
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
    public void editarPeriodo(Periodo p) {
        try {
            EntityManagerFactory emf
                    = Persistence.createEntityManagerFactory("SisgehoPU");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.merge(periodo);
            em.getTransaction().commit();
            em.close();
            obtenerPeriodos(); //Actualiza lista
            p.setEditable(false);
            periodo = new Periodo();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "REALIZADO CON ÉXITO", "Se actualizó correctamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "NO SE PUDO REALIZAR", "No se pudo editar los datos. Inténtelo más tarde");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    //EL MÉTODO DEBE QUEDAR ASÍ MISMO
    public void eliminarPeriodo() {
        try {
            EntityManagerFactory emf
                    = Persistence.createEntityManagerFactory("SisgehoPU");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            if (!em.contains(periodo)) {
                periodo = em.merge(periodo);
            }
            em.remove(periodo);
            em.getTransaction().commit();
            obtenerPeriodos(); //Actualiza lista
            periodo = new Periodo();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "REALIZADO CON ÉXITO", "Se eliminó correctamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "NO SE PUDO REALIZAR", "No se pudo eliminar los datos. Inténtelo más tarde");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    @FacesConverter(forClass = Periodo.class)
    public static class beanPeriodoConverter implements Converter {

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
            return ((beanPeriodo) context.getApplication().evaluateExpressionGet(context, "#{" + "beanPeriodo" + "}", beanPeriodo.class)).buscarById(getKey(value));
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object value) {
            if (value == null) {
                return null;
            } else if (value instanceof Sede) {
                return getStringKey(((Sede) value).getId());
            } else {
                throw new IllegalArgumentException("object " + value + " is of type " + value.getClass().getName() + "; expected type: " + Periodo.class.getName());
            }
        }

    }
}
