/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Horario;
import entity.Sede;
import entity.TipoDia;
import entity.TipoFrecuencia;
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

/**
 *
 * @author Raul A. Hernandez
 */
@ManagedBean
@ViewScoped //INDISPENSABLE PONER ESTA ANOTACIÓN EN VEZ DEL REQUESTSCOPED
public class HorarioBean {

    private Horario horario; //Falta llenar
    private Horario horarioSearch;
    //INDISPENSABLE ESTA VARIABLE CON EL ALCANCE ESTÁTICO
    private static List<Horario> horarioList;
    //INDISPENSABLE EL MÉTODO GET. SÓLO EL GET
    public List<Horario> getHorarioList() {
        return horarioList;
    }
    public Horario getHorarioSearch() {
        return horarioSearch;
    }

    public void setHorarioSearch(Horario horarioSearch) {
        this.horarioSearch = horarioSearch;
    }
    public TipoDia[] getTipoDias() {
        return TipoDia.values();
    }

    public TipoFrecuencia[] getTipoFrecuencias() {
        return TipoFrecuencia.values();
    }
    
    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    /**
     * Creates a new instance of HorarioBean
     */
    public HorarioBean() {
        horario = new Horario();
        horarioSearch = new Horario();
        obtenerHorariosList();
    }
    //Agregar este método para campos booleanos, como "activo"
    public String transformActivo(Boolean activo) {
        return (activo) ? "ACTIVA" : "INACTIVA";
    }
    //INDISPENSABLE tener este método
    public void enableEditarOption(Horario horario, boolean estado) {
        horario.setEditable(estado);
    }
    public String irHorario() {
        return "horarios.xhtml";
    }
    public java.sql.Date convertir(java.util.Date fechaUtilDate){
        return new java.sql.Date(fechaUtilDate.getTime());
    }
    public List<Horario> obtenerHorarios() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<Horario> q = em.createNamedQuery("Horario.findAll", Horario.class);
        return q.getResultList();
    }
    //EL MÉTODO DEBE QUEDAR ASÍ MISMO
    private void obtenerHorariosList() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<Horario> q = em.createNamedQuery("Horario.findAll", Horario.class);
        horarioList = q.getResultList();
    }
    public void buscarHorarioPorId(Integer id) {
        horarioSearch = buscarById(id);
    }
    public Horario buscarById(Integer id) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<Horario> hora = em.createNamedQuery("Horario.findById", Horario.class);
        hora.setParameter("id", id);
        return (hora.getResultList().isEmpty())?  null : hora.getResultList().get(0);
    }
    
    public Horario buscarPorId(Integer id) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<Horario> sedes = em.createNamedQuery("Horario.findById", Horario.class);
        sedes.setParameter("id", id);
        return sedes.getResultList().get(0);
    }

    public void guardarHorario() {
        try {
            EntityManagerFactory emf
                    = Persistence.createEntityManagerFactory("SisgehoPU");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(horario);
            em.getTransaction().commit();
            em.close();
            horario = new Horario();
            obtenerHorarios(); //Actualiza lista
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "REALIZADO CON ÉXITO", "Se guardó correctamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "NO SE PUDO REALIZAR", "No se pudo guardar los datos. Inténtelo más tarde");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
 //EL MÉTODO DEBE QUEDAR ASÍ MISMO
    public void editarHorario(Horario h) {
        try {
            EntityManagerFactory emf
                    = Persistence.createEntityManagerFactory("SisgehoPU");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.merge(horario);
            em.getTransaction().commit();
            em.close();
            obtenerHorarios(); //Actualiza lista
            h.setEditable(false);
            horario = new Horario();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "REALIZADO CON ÉXITO", "Se actualizó correctamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "NO SE PUDO REALIZAR", "No se pudo editar los datos. Inténtelo más tarde");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    //EL MÉTODO DEBE QUEDAR ASÍ MISMO
    public void eliminarHorario() {
        try {
            EntityManagerFactory emf
                    = Persistence.createEntityManagerFactory("SisgehoPU");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            if (!em.contains(horario)) {
                horario = em.merge(horario);
            }
            em.remove(horario);
            em.getTransaction().commit();
            obtenerHorarios(); //Actualiza lista
            horario = new Horario();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "REALIZADO CON ÉXITO", "Se eliminó correctamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "NO SE PUDO REALIZAR", "No se pudo eliminar los datos. Inténtelo más tarde");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    @FacesConverter(forClass = Horario.class)
    public static class HorarioBeanConverter implements Converter {

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
            return ((HorarioBean) context.getApplication().evaluateExpressionGet(context, "#{" + "horarioBean" + "}", HorarioBean.class)).buscarPorId(getKey(value));
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object value) {
            if (value == null) {
                return null;
            } else if (value instanceof Horario) {
                return getStringKey(((Horario) value).getId());
            } else {
                throw new IllegalArgumentException("object " + value + " is of type " + value.getClass().getName() + "; expected type: " + Horario.class.getName());
            }
        }

    }
    
}
