/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Asignatura;
import entity.Grupo;
import entity.Sede;
import entity.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
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
import org.primefaces.context.RequestContext;
import utils.JpaUtil;
import utils.SessionUtils;

/**
 *
 * @author KTANAKA
 */
@ManagedBean
@ViewScoped //INDISPENSABLE PONER ESTA ANOTACIÓN EN VEZ DEL REQUESTSCOPED
public class GrupoBean implements Serializable {

    /**
     * Creates a new instance of LoginBean
     */
    private String descripcion;
    private Asignatura asignatura;
    private int id;
    private boolean activo;
    private String nomenclatura;
    private Grupo grupos;
    private Grupo gruposSearch;
    //INDISPENSABLE ESTA VARIABLE CON EL ALCANCE ESTÁTICO
    private static List<Grupo> gruposList;

    public List<Grupo> getGruposList() {
        return gruposList;
    }

    public Grupo getGrupos() {
        return grupos;
    }

    public void setGrupos(Grupo grupos) {
        this.grupos = grupos;
    }

    public Grupo getGruposSearch() {
        return gruposSearch;
    }

    public void setGruposSearch(Grupo gruposSearch) {
        this.gruposSearch = gruposSearch;
    }
    
    public String getNomenclatura() {
        return nomenclatura;
    }

    public void setNomenclatura(String nomenclatura) {
        this.nomenclatura = nomenclatura;
    }
    private List<Grupo> listaGrupos = new ArrayList<>();
    private List<Asignatura> listaAsignatura = new ArrayList<>();

    public String getDescripcion() {
        return descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Grupo> getListaGrupos() {
        return listaGrupos;
    }

    public void setListaGrupos(List<Grupo> listaGrupos) {
        this.listaGrupos = listaGrupos;
    }

    public List<Asignatura> getListaAsignatura() {
        return listaAsignatura;
    }

    public void setListaAsignatura(List<Asignatura> listaAsignatura) {
        this.listaAsignatura = listaAsignatura;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Asignatura getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
    }

    public GrupoBean() {
        grupos = new Grupo();
        gruposSearch = new Grupo();
        obtenerGrupo();
    }

    public String home() {
        return "index.xhtml";
    }

    public String irGrupo() {
        return "gestionGrupos.xhtml";
    }
//EL MÉTODO DEBE QUEDAR ASÍ MISMO
    private void obtenerGrupo() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<Grupo> q = em.createNamedQuery("Grupo.findAll", Grupo.class);
        gruposList = q.getResultList();
    }
    public void buscarGrupoPorId(Integer id) {
        gruposSearch = buscarById(id);
    }

    public Grupo buscarById(Integer id) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<Grupo> group = em.createNamedQuery("Grupo.findById", Grupo.class);
        group.setParameter("id", id);
        return (group.getResultList().isEmpty())?  null : group.getResultList().get(0);
    }
    //EL MÉTODO DEBE QUEDAR ASÍ MISMO
    public void guardarGrupo() {
        try {
            EntityManagerFactory emf
                    = Persistence.createEntityManagerFactory("SisgehoPU");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(grupos);
            em.getTransaction().commit();
            em.close();
            grupos = new Grupo();
            obtenerGrupo(); //Actualiza lista
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
    public void enableEditarOption(Grupo group, boolean estado) {
        group.setEditable(estado);
    }
    //EL MÉTODO DEBE QUEDAR ASÍ MISMO
    public void editarGrupo(Grupo group) {
        try {
            EntityManagerFactory emf
                    = Persistence.createEntityManagerFactory("SisgehoPU");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.merge(grupos);
            em.getTransaction().commit();
            em.close();
            obtenerGrupo(); //Actualiza lista
            group.setEditable(false);
            grupos = new Grupo();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "REALIZADO CON ÉXITO", "Se actualizó correctamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "NO SE PUDO REALIZAR", "No se pudo editar los datos. Inténtelo más tarde");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    //EL MÉTODO DEBE QUEDAR ASÍ MISMO
    public void eliminarGrupo() {
        try {
            EntityManagerFactory emf
                    = Persistence.createEntityManagerFactory("SisgehoPU");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            if (!em.contains(grupos)) {
                grupos = em.merge(grupos);
            }
            em.remove(grupos);
            em.getTransaction().commit();
            obtenerGrupo(); //Actualiza lista
            grupos = new Grupo();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "REALIZADO CON ÉXITO", "Se eliminó correctamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "NO SE PUDO REALIZAR", "No se pudo eliminar los datos. Inténtelo más tarde");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    public void consultarGrupo() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();

        TypedQuery<Grupo> consultaGrupo = em.createNamedQuery("Grupo.findAll", Grupo.class);
        listaGrupos = consultaGrupo.getResultList();
    }

    public void consultarGrupoById() {
        if (id != 0) {
            EntityManagerFactory emf
                    = Persistence.createEntityManagerFactory("SisgehoPU");
            EntityManager em = emf.createEntityManager();

            TypedQuery<Grupo> consultaGrupo = em.createNamedQuery("Grupo.findByID", Grupo.class);
            consultaGrupo.setParameter("id", id);
            listaGrupos = consultaGrupo.getResultList();
        }
    }

    public void buscarAsginatura() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();

        TypedQuery<Asignatura> consultaAsignatura = em.createNamedQuery("Asignatura.findAll", Asignatura.class);
        listaAsignatura = consultaAsignatura.getResultList();
    }


    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
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
            return ((GrupoBean) context.getApplication().evaluateExpressionGet(context, "#{" + "GrupoBean" + "}", GrupoBean.class)).buscarById(getKey(value));
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
