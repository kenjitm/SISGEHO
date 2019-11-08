/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Asignatura;
import entity.Grupo;
import entity.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
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
@RequestScoped
public class GrupoBean implements Serializable {

    /**
     * Creates a new instance of LoginBean
     */
    private String descripcion;
    private Asignatura asignatura;
    private int id;
    private boolean activo;
    private String nomenclatura;

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
        id = 0;
        consultarGrupo();
    }

    public String home() {
        return "index.xhtml";
    }

    public String irGrupo() {
        return "gestionGrupos.xhtml";
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

    public void guardarGrupo() {
        Grupo grupo = new Grupo();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        grupo.setId(id);
        grupo.setDescripcion(descripcion);
        grupo.setNomenclatura(nomenclatura);
        grupo.setActivo(true);
        //grupo.setRowidAsignatura(asignatura);
        em.getTransaction().begin();
        em.persist(grupo);
        em.getTransaction().commit();
        
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "ÈXITO", "Registro realizado satisfactoriamente");
            // For PrimeFaces < 6.2
            RequestContext.getCurrentInstance().showMessageInDialog(message);  
           this.consultarGrupo();
           this.id = 0;
           this.descripcion = null;
           this.nomenclatura = null;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

}
