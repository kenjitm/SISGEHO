/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import entity.Asignacion;
import entity.Grupo;
import java.io.Serializable;
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
/**
 *
 * @author IngenieroDesarrollo
 */
@ManagedBean
@ViewScoped //INDISPENSABLE PONER ESTA ANOTACIÓN EN VEZ DEL REQUESTSCOPED
public class AsignacionBean implements Serializable{
    private Asignacion asignaciones;
   private Asignacion asignacionesSearch;
   private int id;
   private boolean showDialog;
   private boolean showDialogDetalle;
   private String gestionTipo;
   //INDISPENSABLE ESTA VARIABLE CON EL ALCANCE ESTÁTICO
    private static List<Asignacion> asignacionesList;
    public AsignacionBean(){
        asignaciones = new Asignacion();
        asignacionesSearch = new Asignacion();
    }

    public Asignacion getAsignaciones() {
        return asignaciones;
    }

    public boolean isShowDialogDetalle() {
        return showDialogDetalle;
    }

    public void setShowDialogDetalle(boolean showDialogDetalle) {
        this.showDialogDetalle = showDialogDetalle;
    }

    public void setAsignaciones(Asignacion asignaciones) {
        this.asignaciones = asignaciones;
    }

    public Asignacion getAsignacionesSearch() {
        return asignacionesSearch;
    }

    public void setAsignacionesSearch(Asignacion asignacionesSearch) {
        this.asignacionesSearch = asignacionesSearch;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Asignacion> getAsignacionesList() {
        return asignacionesList;
    }
    public boolean isShowDialog() {
        return showDialog;
    }

    public void setShowDialog(boolean showDialog) {
        this.showDialog = showDialog;
    }
     public String getGestionTipo() {
        return gestionTipo;
    }

    public void setGestionTipo(String gestionTipo) {
        this.gestionTipo = gestionTipo;
    }
    public String action_new() {
        //action_clear();
        setShowDialog(true);
        gestionTipo = "Asignar Recurso";
        return "";
    }
    //EL MÉTODO DEBE QUEDAR ASÍ MISMO
    private void obtenerAsginaciones() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<Asignacion> q = em.createNamedQuery("Asignacion.findAll", Asignacion.class);
        asignacionesList = q.getResultList();
    }
    public void buscarAsignacionPorId(Integer id) {
        asignacionesSearch = buscarById(id);
    }

    public Asignacion buscarById(Integer id) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<Asignacion> asig = em.createNamedQuery("Asignacion.findById", Asignacion.class);
        asig.setParameter("id", id);
        return (asig.getResultList().isEmpty())?  null : asig.getResultList().get(0);
    }
    //INDISPENSABLE tener este método
    public void enableEditarOption(Integer id, boolean estado) {
        asignaciones = buscarById(id);
        asignaciones.setEditable(estado);
        setShowDialog(true);
        gestionTipo = "Asignar Recurso";
    }
    //INDISPENSABLE tener este método
    public void ShowDetail(Integer id) {
        asignaciones = buscarById(id);
        setShowDialogDetalle(true);
        gestionTipo = "Detalle Asignación";
    }
     public void CloseDetail() {
        setShowDialogDetalle(false);
         setShowDialog(false);
    }
    //EL MÉTODO DEBE QUEDAR ASÍ MISMO
    public void editarAsignaciones(Asignacion asig) {
        try {
            EntityManagerFactory emf
                    = Persistence.createEntityManagerFactory("SisgehoPU");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.merge(asig);
            em.getTransaction().commit();
            em.close();
            obtenerAsginaciones(); //Actualiza lista
            setShowDialog(false);
            asig.setEditable(false);
            asig = new Asignacion();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "REALIZADO CON ÉXITO", "Se actualizó correctamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            FacesContext facesContext = FacesContext.getCurrentInstance();
        String outcome = "getionAsignacion";// Do your thing?
        facesContext.getApplication().getNavigationHandler().handleNavigation(facesContext, null, outcome);
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "NO SE PUDO REALIZAR", "No se pudo editar los datos. Inténtelo más tarde");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    @FacesConverter(forClass = Asignacion.class)
    public static class AsignacionBeanConverter implements Converter {

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
            return ((AsignacionBean) context.getApplication().evaluateExpressionGet(context, "#{" + "AsignacionBean" + "}", AsignacionBean.class)).buscarById(getKey(value));
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object value) {
            if (value == null) {
                return null;
            } else if (value instanceof Asignacion) {
                return getStringKey(((Asignacion) value).getId());
            } else {
                throw new IllegalArgumentException("object " + value + " is of type " + value.getClass().getName() + "; expected type: " + Asignacion.class.getName());
            }
        }

    }
}
