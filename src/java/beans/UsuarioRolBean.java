/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import entity.UsuarioRol;
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
public class UsuarioRolBean {

    private UsuarioRol userRol;
    private UsuarioRol userRolSearch;
    //INDISPENSABLE ESTA VARIABLE CON EL ALCANCE ESTÁTICO
    private static List<UsuarioRol> userRolList;

    public UsuarioRolBean() {
        userRol = new UsuarioRol();
        userRolSearch = new UsuarioRol();
    }
    public String irUserRol(){
        return "gestionUsuarioRol.xhtml";
    }
    
    public UsuarioRol getUserRol() {
        return userRol;
    }

    public void setUserRol(UsuarioRol userRol) {
        this.userRol = userRol;
    }

    public UsuarioRol getUserRolSearch() {
        return userRolSearch;
    }

    public void setUserRolSearch(UsuarioRol userRolSearch) {
        this.userRolSearch = userRolSearch;
    }

    public List<UsuarioRol> getUserRolList() {
        return userRolList;
    }
    //EL MÉTODO DEBE QUEDAR ASÍ MISMO
    private void obtenerUsuariosRoles() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<UsuarioRol> q = em.createNamedQuery("UsuarioRol.findAll", UsuarioRol.class);
        userRolList = q.getResultList();
    }
    public void buscarUserRolPorId(Integer id) {
        userRolSearch = buscarById(id);
    }

    public UsuarioRol buscarById(Integer id) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<UsuarioRol> user = em.createNamedQuery("UsuarioRol.findById", UsuarioRol.class);
        user.setParameter("id", id);
        return (user.getResultList().isEmpty())?  null : user.getResultList().get(0);
    }
    //EL MÉTODO DEBE QUEDAR ASÍ MISMO
    public void guardarUsuarioRol() {
        try {
            EntityManagerFactory emf
                    = Persistence.createEntityManagerFactory("SisgehoPU");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(userRol);
            em.getTransaction().commit();
            em.close();
            userRol = new UsuarioRol();
            obtenerUsuariosRoles(); //Actualiza lista
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
    public void enableEditarOption(UsuarioRol rol, boolean estado) {
        //rol.setEditable(estado);
    }
    //EL MÉTODO DEBE QUEDAR ASÍ MISMO
    public void editarUsuarioRol(UsuarioRol s) {
        try {
            EntityManagerFactory emf
                    = Persistence.createEntityManagerFactory("SisgehoPU");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.merge(userRol);
            em.getTransaction().commit();
            em.close();
            obtenerUsuariosRoles(); //Actualiza lista
            //s.setEditable(false);
            userRol = new UsuarioRol();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "REALIZADO CON ÉXITO", "Se actualizó correctamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "NO SE PUDO REALIZAR", "No se pudo editar los datos. Inténtelo más tarde");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    //EL MÉTODO DEBE QUEDAR ASÍ MISMO
    public void eliminarUsuarioRol() {
        try {
            EntityManagerFactory emf
                    = Persistence.createEntityManagerFactory("SisgehoPU");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            if (!em.contains(userRol)) {
                userRol = em.merge(userRol);
            }
            em.remove(userRol);
            em.getTransaction().commit();
            obtenerUsuariosRoles(); //Actualiza lista
            userRol = new UsuarioRol();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "REALIZADO CON ÉXITO", "Se eliminó correctamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "NO SE PUDO REALIZAR", "No se pudo eliminar los datos. Inténtelo más tarde");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    @FacesConverter(forClass = UsuarioRol.class)
    public static class usuarioRolBeanConverter implements Converter {

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
            return ((UsuarioRolBean) context.getApplication().evaluateExpressionGet(context, "#{" + "usuarioRolBean" + "}", UsuarioRolBean.class)).buscarById(getKey(value));
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object value) {
            if (value == null) {
                return null;
            } else if (value instanceof UsuarioRol) {
                return getStringKey(((UsuarioRol) value).getId());
            } else {
                throw new IllegalArgumentException("object " + value + " is of type " + value.getClass().getName() + "; expected type: " + UsuarioRol.class.getName());
            }
        }

    }
}
