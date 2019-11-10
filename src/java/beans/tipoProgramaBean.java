/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Sede;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import entity.TipoPrograma;
/**
 *
 * @author SougiroHylian
 */
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
@ManagedBean
@ViewScoped //INDISPENSABLE PONER ESTA ANOTACIÓN EN VEZ DEL REQUESTSCOPED
public class tipoProgramaBean {
    private TipoPrograma tipoProgram;
    private TipoPrograma tipoProgramSearch;
    //INDISPENSABLE ESTA VARIABLE CON EL ALCANCE ESTÁTICO
    private static List<TipoPrograma> tipoProgramList;
    public tipoProgramaBean()
    {
        tipoProgram = new TipoPrograma();
        tipoProgramSearch = new TipoPrograma();
        obtenerTipoPrograma();
    }
    public String irTipoPrograma() {
        return "gestionTipoPrograma.xhtml";
    }

    public TipoPrograma getTipoProgram() {
        return tipoProgram;
    }

    public void setTipoProgram(TipoPrograma tipoProgram) {
        this.tipoProgram = tipoProgram;
    }

    public TipoPrograma getTipoProgramSearch() {
        return tipoProgramSearch;
    }

    public void setTipoProgramSearch(TipoPrograma tipoProgramSearch) {
        this.tipoProgramSearch = tipoProgramSearch;
    }

    public List<TipoPrograma> getTipoProgramList() {
        return tipoProgramList;
    }
    //EL MÉTODO DEBE QUEDAR ASÍ MISMO
    private void obtenerTipoPrograma() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<TipoPrograma> q = em.createNamedQuery("TipoPrograma.findAll", TipoPrograma.class);
        tipoProgramList = q.getResultList();
    }
    public void buscarTipoPorId(Integer id) {
        tipoProgramSearch = buscarById(id);
    }

    public TipoPrograma buscarById(Integer id) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<TipoPrograma> tipo = em.createNamedQuery("TipoPrograma.findById", TipoPrograma.class);
        tipo.setParameter("id", id);
        return (tipo.getResultList().isEmpty())?  null : tipo.getResultList().get(0);
    }
    //EL MÉTODO DEBE QUEDAR ASÍ MISMO
    public void guardarTipo() {
        try {
            EntityManagerFactory emf
                    = Persistence.createEntityManagerFactory("SisgehoPU");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(tipoProgram);
            em.getTransaction().commit();
            em.close();
            tipoProgram = new TipoPrograma();
            obtenerTipoPrograma(); //Actualiza lista
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
    public void enableEditarOption(TipoPrograma tipo, boolean estado) {
        tipo.setEditable(estado);
    }
    //EL MÉTODO DEBE QUEDAR ASÍ MISMO
    public void editarTipo(TipoPrograma s) {
        try {
            EntityManagerFactory emf
                    = Persistence.createEntityManagerFactory("SisgehoPU");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.merge(tipoProgram);
            em.getTransaction().commit();
            em.close();
            obtenerTipoPrograma(); //Actualiza lista
            s.setEditable(false);
            tipoProgram = new TipoPrograma();
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
            if (!em.contains(tipoProgram)) {
                tipoProgram = em.merge(tipoProgram);
            }
            em.remove(tipoProgram);
            em.getTransaction().commit();
            obtenerTipoPrograma(); //Actualiza lista
            tipoProgram = new TipoPrograma();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "REALIZADO CON ÉXITO", "Se eliminó correctamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "NO SE PUDO REALIZAR", "No se pudo eliminar los datos. Inténtelo más tarde");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    @FacesConverter(forClass = TipoPrograma.class)
    public static class tipoProgramaBeanConverter implements Converter {

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
            return ((tipoProgramaBean) context.getApplication().evaluateExpressionGet(context, "#{" + "tipoProgramaBean" + "}", tipoProgramaBean.class)).buscarById(getKey(value));
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object value) {
            if (value == null) {
                return null;
            } else if (value instanceof TipoPrograma) {
                return getStringKey(((TipoPrograma) value).getId());
            } else {
                throw new IllegalArgumentException("object " + value + " is of type " + value.getClass().getName() + "; expected type: " + Sede.class.getName());
            }
        }

    }

}
