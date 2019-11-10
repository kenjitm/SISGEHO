/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Sede;
import java.util.List;
import entity.TipoRecurso;
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

/**
 *
 * @author Raul A. Hernandez
 */
@ManagedBean
@ViewScoped //INDISPENSABLE PONER ESTA ANOTACIÓN EN VEZ DEL REQUESTSCOPED
public class TipoRecursoBean {
    private TipoRecurso tipeRec;
    private TipoRecurso tipeRecSearch;
        //INDISPENSABLE ESTA VARIABLE CON EL ALCANCE ESTÁTICO
    private static List<TipoRecurso> tipeRecList;
    /**
     * Creates a new instance of TipoRecursoBean
     */
    public TipoRecursoBean() {
        tipeRec = new TipoRecurso();
        tipeRecSearch = new TipoRecurso();
        obtenerTiposRecurso();
    }
public String irTipoRecurso() {
        return "gestionTipoRecurso.xhtml";
    }

    public TipoRecurso getTipeRec() {
        return tipeRec;
    }

    public void setTipeRec(TipoRecurso tipeRec) {
        this.tipeRec = tipeRec;
    }

    public TipoRecurso getTipeRecSearch() {
        return tipeRecSearch;
    }

    public void setTipeRecSearch(TipoRecurso tipeRecSearch) {
        this.tipeRecSearch = tipeRecSearch;
    }

    public  List<TipoRecurso> getTipeRecList() {
        return tipeRecList;
    }

    public List<TipoRecurso> obtenerTiposRecurso() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<TipoRecurso> q = em.createNamedQuery("TipoRecurso.findAll", TipoRecurso.class);
        tipeRecList = q.getResultList();
        return q.getResultList();
    }

    public void buscarTipoRecursoPorId(Integer id) {
        tipeRecSearch = buscarById(id);
    }

    public TipoRecurso buscarById(Integer id) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<TipoRecurso> t = em.createNamedQuery("TipoRecurso.findById", TipoRecurso.class);
        t.setParameter("id", id);
        return (t.getResultList().isEmpty())?  null : t.getResultList().get(0);
    }
//EL MÉTODO DEBE QUEDAR ASÍ MISMO
    public void guardarTipoRecurso() {
        try {
            EntityManagerFactory emf
                    = Persistence.createEntityManagerFactory("SisgehoPU");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(tipeRec);
            em.getTransaction().commit();
            em.close();
            tipeRec = new TipoRecurso();
            obtenerTiposRecurso(); //Actualiza lista
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
    public void enableEditarOption(TipoRecurso tipo, boolean estado) {
        tipo.setEditable(estado);
    }
    //EL MÉTODO DEBE QUEDAR ASÍ MISMO
    public void editarTipo(TipoRecurso s) {
        try {
            EntityManagerFactory emf
                    = Persistence.createEntityManagerFactory("SisgehoPU");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.merge(tipeRec);
            em.getTransaction().commit();
            em.close();
            obtenerTiposRecurso(); //Actualiza lista
            s.setEditable(false);
            tipeRec = new TipoRecurso();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "REALIZADO CON ÉXITO", "Se actualizó correctamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "NO SE PUDO REALIZAR", "No se pudo editar los datos. Inténtelo más tarde");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
     //EL MÉTODO DEBE QUEDAR ASÍ MISMO
    public void eliminarTipo() {
        try {
            EntityManagerFactory emf
                    = Persistence.createEntityManagerFactory("SisgehoPU");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            if (!em.contains(tipeRec)) {
                tipeRec = em.merge(tipeRec);
            }
            em.remove(tipeRec);
            em.getTransaction().commit();
            obtenerTiposRecurso(); //Actualiza lista
            tipeRec = new TipoRecurso();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "REALIZADO CON ÉXITO", "Se eliminó correctamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "NO SE PUDO REALIZAR", "No se pudo eliminar los datos. Inténtelo más tarde");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    @FacesConverter(forClass = TipoRecurso.class)
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
            return ((TipoRecursoBean) context.getApplication().evaluateExpressionGet(context, "#{" + "tipoRecursoBean" + "}", TipoRecursoBean.class)).buscarById(getKey(value));
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object value) {
            if (value == null) {
                return null;
            } else if (value instanceof TipoRecurso) {
                return getStringKey(((TipoRecurso) value).getId());
            } else {
                throw new IllegalArgumentException("object " + value + " is of type " + value.getClass().getName() + "; expected type: " + TipoRecurso.class.getName());
            }
        }

    }

}
