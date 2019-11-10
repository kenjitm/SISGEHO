/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import entity.Jornada;
import entity.Periodo;
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
public class JornadaBeans {
    private Jornada jornada;
    private Jornada jornadaSearch;
    //INDISPENSABLE ESTA VARIABLE CON EL ALCANCE ESTÁTICO
    private static List<Jornada> jornadaList;
    public String irJornada() {
        return "gestionJornada.xhtml";
    }
    public JornadaBeans(){
        jornada = new Jornada();
        jornadaSearch = new Jornada();
        obtenerJornadas();
    }

    public Jornada getJornada() {
        return jornada;
    }

    public void setJornada(Jornada jornada) {
        this.jornada = jornada;
    }

    public Jornada getJornadaSearch() {
        return jornadaSearch;
    }

    public void setJornadaSearch(Jornada jornadaSearch) {
        this.jornadaSearch = jornadaSearch;
    }

    public  List<Jornada> getJornadaList() {
        return jornadaList;
    }
    //EL MÉTODO DEBE QUEDAR ASÍ MISMO
    private void obtenerJornadas() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<Jornada> q = em.createNamedQuery("Jornada.findAll", Jornada.class);
        jornadaList = q.getResultList();
    }
    public void buscarJornadaPorId(Integer id) {
        jornadaSearch  = buscarById(id);
    }
    public Jornada buscarById(Integer id) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<Jornada> jor = em.createNamedQuery("Jornada.findById", Jornada.class);
        jor.setParameter("id", id);
        return (jor.getResultList().isEmpty())?  null : jor.getResultList().get(0);
    }
    //EL MÉTODO DEBE QUEDAR ASÍ MISMO
    public void guardarJornada() {
        try {
            EntityManagerFactory emf
                    = Persistence.createEntityManagerFactory("SisgehoPU");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(jornada);
            em.getTransaction().commit();
            em.close();
            jornada = new Jornada();
            obtenerJornadas(); //Actualiza lista
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
    public void enableEditarOption(Jornada jornada, boolean estado) {
        jornada.setEditable(estado);
    }
     //EL MÉTODO DEBE QUEDAR ASÍ MISMO
    public void editarJornada(Jornada j) {
        try {
            EntityManagerFactory emf
                    = Persistence.createEntityManagerFactory("SisgehoPU");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.merge(jornada);
            em.getTransaction().commit();
            em.close();
            obtenerJornadas(); //Actualiza lista
            j.setEditable(false);
            jornada = new Jornada();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "REALIZADO CON ÉXITO", "Se actualizó correctamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "NO SE PUDO REALIZAR", "No se pudo editar los datos. Inténtelo más tarde");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    //EL MÉTODO DEBE QUEDAR ASÍ MISMO
    public void eliminarJornada() {
        try {
            EntityManagerFactory emf
                    = Persistence.createEntityManagerFactory("SisgehoPU");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            if (!em.contains(jornada)) {
                jornada = em.merge(jornada);
            }
            em.remove(jornada);
            em.getTransaction().commit();
            obtenerJornadas(); //Actualiza lista
            jornada = new Jornada();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "REALIZADO CON ÉXITO", "Se eliminó correctamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "NO SE PUDO REALIZAR", "No se pudo eliminar los datos. Inténtelo más tarde");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    @FacesConverter(forClass = Jornada.class)
    public static class JornadaBeansConverter implements Converter {

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
            return ((JornadaBeans) context.getApplication().evaluateExpressionGet(context, "#{" + "JornadaBeans" + "}", JornadaBeans.class)).buscarById(getKey(value));
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object value) {
            if (value == null) {
                return null;
            } else if (value instanceof Jornada) {
                return getStringKey(((Jornada) value).getId());
            } else {
                throw new IllegalArgumentException("object " + value + " is of type " + value.getClass().getName() + "; expected type: " + Jornada.class.getName());
            }
        }

    }
}
