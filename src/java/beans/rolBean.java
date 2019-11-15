/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import entity.Rol;
import entity.Sede;
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
 * @author SougiroHylian
 */
@ManagedBean
@ViewScoped //INDISPENSABLE PONER ESTA ANOTACIÓN EN VEZ DEL REQUESTSCOPED
public class rolBean {
    private Rol rol;
    private Rol rolSearch;
    //INDISPENSABLE ESTA VARIABLE CON EL ALCANCE ESTÁTICO
    private static List<Rol> rolList;
    public rolBean()
    {
        rol = new Rol();
        rolSearch = new Rol();
        obtenerRol();
    }
    public String irRol() {
        return "gestionRol.xhtml";
    }
    public List<Rol> getRolList() {
        return rolList;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Rol getRolSearch() {
        return rolSearch;
    }

    public void setRolSearch(Rol rolSearch) {
        this.rolSearch = rolSearch;
    }
    //EL MÉTODO DEBE QUEDAR ASÍ MISMO
    private void obtenerRol() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<Rol> q = em.createNamedQuery("Rol.findAll", Rol.class);
        rolList = q.getResultList();
    }
    public void buscarRolPorId(Integer id) {
        rolSearch = buscarById(id);
    }

    public Rol buscarById(Integer id) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<Rol> rol = em.createNamedQuery("Rol.findById", Rol.class);
        rol.setParameter("id", id);
        return (rol.getResultList().isEmpty())?  null : rol.getResultList().get(0);
    }
    //EL MÉTODO DEBE QUEDAR ASÍ MISMO
    public void guardarRol() {
        try {
            EntityManagerFactory emf
                    = Persistence.createEntityManagerFactory("SisgehoPU");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(rol);
            em.getTransaction().commit();
            em.close();
            rol = new Rol();
            obtenerRol(); //Actualiza lista
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
    //Agregar este método para campos booleanos, como "habilitado"
    public String transformHabilitado(Boolean bitBool) {
        return (bitBool) ? "HABILITADO" : "INHABILITADO";
    }
    //INDISPENSABLE tener este método
    public void enableEditarOption(Rol rol, boolean estado) {
        rol.setEditable(estado);
    }
    //EL MÉTODO DEBE QUEDAR ASÍ MISMO
    public void editarRol(Rol r) {
        try {
            EntityManagerFactory emf
                    = Persistence.createEntityManagerFactory("SisgehoPU");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.merge(rol);
            em.getTransaction().commit();
            em.close();
            obtenerRol(); //Actualiza lista
            r.setEditable(false);
            rol = new Rol();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "REALIZADO CON ÉXITO", "Se actualizó correctamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "NO SE PUDO REALIZAR", "No se pudo editar los datos. Inténtelo más tarde");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    //EL MÉTODO DEBE QUEDAR ASÍ MISMO
    public void eliminarRol() {
        try {
            EntityManagerFactory emf
                    = Persistence.createEntityManagerFactory("SisgehoPU");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            if (!em.contains(rol)) {
                rol = em.merge(rol);
            }
            em.remove(rol);
            em.getTransaction().commit();
            obtenerRol(); //Actualiza lista
            rol = new Rol();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "REALIZADO CON ÉXITO", "Se eliminó correctamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "NO SE PUDO REALIZAR", "No se pudo eliminar los datos. Inténtelo más tarde");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    @FacesConverter(forClass = Rol.class)
    public static class rolBeanConverter implements Converter {

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
            return ((rolBean) context.getApplication().evaluateExpressionGet(context, "#{" + "rolBean" + "}", rolBean.class)).buscarById(getKey(value));
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object value) {
            if (value == null) {
                return null;
            } else if (value instanceof Rol) {
                return getStringKey(((Rol) value).getId());
            } else {
                throw new IllegalArgumentException("object " + value + " is of type " + value.getClass().getName() + "; expected type: " + Rol.class.getName());
            }
        }

    }
}
