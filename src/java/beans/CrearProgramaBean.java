/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Pensum;
import entity.Programa;
import entity.Usuario;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
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
import utils.JpaUtil;
import utils.SessionUtils;

/**
 *
 * @author KTANAKA
 */
@ManagedBean
@ViewScoped //INDISPENSABLE PONER ESTA ANOTACIÓN EN VEZ DEL REQUESTSCOPED
public class CrearProgramaBean implements Serializable {

    /**
     * Creates a new instance of LoginBean
     */
    
    private SessionUtils session;
    
    private Pensum pensum;
    
    
    private Programa program;
    private Programa programSearch;
    //INDISPENSABLE ESTA VARIABLE CON EL ALCANCE ESTÁTICO
    private static List<Programa> programList;

    public Programa getProgram() {
        return program;
    }

    public void setProgram(Programa program) {
        this.program = program;
    }

    public Programa getProgramSearch() {
        return programSearch;
    }

    public void setProgramSearch(Programa programSearch) {
        this.programSearch = programSearch;
    }

    public List<Programa> getProgramList() {
        return programList;
    }

    public SessionUtils getSession() {
        return session;
    }

    public void setSession(SessionUtils session) {
        this.session = session;
    }


    public Pensum getPensum() {
        return pensum;
    }

    public void setPensum(Pensum pensum) {
        this.pensum = pensum;
    }


    public String irPrograma() {
        return "crearPrograma.xhtml";
    }

    public CrearProgramaBean(){
        program = new Programa();
        programSearch = new Programa();
        obtenerProgramas();
    }
    //EL MÉTODO DEBE QUEDAR ASÍ MISMO
    private void obtenerProgramas() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<Programa> q = em.createNamedQuery("Programa.findAll", Programa.class);
        programList = q.getResultList();
    }
    public void buscarProgramaPorId(Integer id) {
        programSearch = buscarById(id);
    }

    public Programa buscarById(Integer id) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<Programa> Programa = em.createNamedQuery("Programa.findById", Programa.class);
        Programa.setParameter("id", id);
        return (Programa.getResultList().isEmpty())?  null : Programa.getResultList().get(0);
    }
    //EL MÉTODO DEBE QUEDAR ASÍ MISMO
    public void guardarPrograma() {
        try {
            EntityManagerFactory emf
                    = Persistence.createEntityManagerFactory("SisgehoPU");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(program);
            em.getTransaction().commit();
            em.close();
            program = new Programa();
            obtenerProgramas(); //Actualiza lista
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
    public void enableEditarOption(Programa program, boolean estado) {
        program.setEditable(estado);
    }
    //EL MÉTODO DEBE QUEDAR ASÍ MISMO
    public void editarPrograma(Programa p) {
        try {
            EntityManagerFactory emf
                    = Persistence.createEntityManagerFactory("SisgehoPU");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.merge(program);
            em.getTransaction().commit();
            em.close();
            obtenerProgramas(); //Actualiza lista
            p.setEditable(false);
            program = new Programa();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "REALIZADO CON ÉXITO", "Se actualizó correctamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "NO SE PUDO REALIZAR", "No se pudo editar los datos. Inténtelo más tarde");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    //EL MÉTODO DEBE QUEDAR ASÍ MISMO
    public void eliminarPrograma() {
        try {
            EntityManagerFactory emf
                    = Persistence.createEntityManagerFactory("SisgehoPU");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            if (!em.contains(program)) {
                program = em.merge(program);
            }
            em.remove(program);
            em.getTransaction().commit();
            obtenerProgramas(); //Actualiza lista
            program = new Programa();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "REALIZADO CON ÉXITO", "Se eliminó correctamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "NO SE PUDO REALIZAR", "No se pudo eliminar los datos. Inténtelo más tarde");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    public String home() {
        return "index.xhtml";
    }

    @FacesConverter(forClass = Programa.class)
    public static class CrearProgramaBeanConverter implements Converter {

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
            return ((CrearProgramaBean) context.getApplication().evaluateExpressionGet(context, "#{" + "crearProgramaBean" + "}", CrearProgramaBean.class)).buscarById(getKey(value));
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object value) {
            if (value == null) {
                return null;
            } else if (value instanceof Programa) {
                return getStringKey(((Programa) value).getId());
            } else {
                throw new IllegalArgumentException("object " + value + " is of type " + value.getClass().getName() + "; expected type: " + Programa.class.getName());
            }
        }

    }
}
