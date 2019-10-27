/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Asignatura;
import entity.TipoSemestre;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author Raul A. Hernandez
 */
@ManagedBean
@ViewScoped
public class AsignaturaBean {

    private Integer id;
    private Asignatura asignatura;
    private Asignatura asignaturaBusqueda;
    private TipoSemestre semestre;

    private static List<Asignatura> asignaturasList;

    public AsignaturaBean() {
        asignatura = new Asignatura();
        obtenerAsignaturas();
    }

    public Asignatura getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
    }

    public TipoSemestre getSemestre() {
        return semestre;
    }

    public void setSemestre(TipoSemestre semestre) {
        this.semestre = semestre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Asignatura getAsignaturaBusqueda() {
        return asignaturaBusqueda;
    }

    public void setAsignaturaBusqueda(Asignatura asignaturaBusqueda) {
        this.asignaturaBusqueda = asignaturaBusqueda;
    }

    public List<Asignatura> getAsignaturasList() {
        return asignaturasList;
    }

    public TipoSemestre[] getTipoSemestres() {
        return TipoSemestre.values();
    }

    public String irAsignatura() {
        return "gestionAsignaturas.xhtml";
    }

    private void obtenerAsignaturas() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<Asignatura> q = em.createNamedQuery("Asignatura.findAll", Asignatura.class);
        asignaturasList = q.getResultList();
    }

    public void buscarAsignaturaPorId(Integer id) {
        asignaturaBusqueda = buscarById(id);
    }

    public Asignatura buscarById(Integer id) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<Asignatura> asignaturas = em.createNamedQuery("Asignatura.findById", Asignatura.class);
        asignaturas.setParameter("id", id);
        return (asignaturas.getResultList().isEmpty()) ? null : asignaturas.getResultList().get(0);
    }

    public void guardarAsignatura() {
        try {
            EntityManagerFactory emf
                    = Persistence.createEntityManagerFactory("SisgehoPU");
            EntityManager em = emf.createEntityManager();
            asignatura.setSemestre(semestre.getLabel());
            em.getTransaction().begin();
            em.persist(asignatura);
            em.getTransaction().commit();
            em.close();
            asignatura = new Asignatura();
            semestre = null;
            obtenerAsignaturas(); //Actualiza lista
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "REALIZADO CON ÉXITO", "Se guardó correctamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "NO SE PUDO REALIZAR", "No se pudo guardar los datos. Inténtelo más tarde");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public String transformActivo(Boolean activo) {
        return (activo) ? "ACTIVA" : "INACTIVA";
    }

    public void enableEditarOption(Asignatura asignatura, boolean estado) {
        semestre = (estado) ? getTipoSemestres()[asignatura.getSemestre() - 1] : null;
        asignatura.setEditable(estado);
    }

    public void editarAsignatura(Asignatura s) {
        try {
            EntityManagerFactory emf
                    = Persistence.createEntityManagerFactory("SisgehoPU");
            EntityManager em = emf.createEntityManager();
            asignatura.setSemestre(semestre.getLabel());
            em.getTransaction().begin();
            em.merge(asignatura);
            em.getTransaction().commit();
            em.close();
            obtenerAsignaturas(); //Actualiza lista
            s.setEditable(false);
            asignatura = new Asignatura();
            semestre = null;
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "REALIZADO CON ÉXITO", "Se actualizó correctamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "NO SE PUDO REALIZAR", "No se pudo editar los datos. Inténtelo más tarde");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    //EL MÉTODO DEBE QUEDAR ASÍ MISMO
    public void eliminarAsignatura() {
        try {
            EntityManagerFactory emf
                    = Persistence.createEntityManagerFactory("SisgehoPU");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            if (!em.contains(asignatura)) {
                asignatura = em.merge(asignatura);
            }
            em.remove(asignatura);
            em.getTransaction().commit();
            obtenerAsignaturas(); //Actualiza lista
            asignatura = new Asignatura();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "REALIZADO CON ÉXITO", "Se eliminó correctamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "NO SE PUDO REALIZAR", "No se pudo eliminar los datos. Inténtelo más tarde");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
}
