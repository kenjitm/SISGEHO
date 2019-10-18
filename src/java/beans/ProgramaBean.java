/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Asignaturas;
import entity.Grupos;
import entity.Programa;
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
import utils.JpaUtil;
import utils.SessionUtils;

/**
 *
 * @author KTANAKA
 */
@ManagedBean
@RequestScoped
public class ProgramaBean implements Serializable {

    /**
     * Creates a new instance of LoginBean
     */
    private String descripcion;
    private int tipo;
    private String descripcionTipo;
    private String modalidad;
    private String duracion;
    private String titulo;
    private String registroCalificado;
    private String snies;
    private String director;
    private String email;
    private String pensum;
    private int id;
    private List<Programa> listaProgramas = new ArrayList<>();

    public String getDescripcion() {
        return descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getDescripcionTipo() {
        return descripcionTipo;
    }

    public void setDescripcionTipo(String descripcionTipo) {
        this.descripcionTipo = descripcionTipo;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getRegistroCalificado() {
        return registroCalificado;
    }

    public void setRegistroCalificado(String registroCalificado) {
        this.registroCalificado = registroCalificado;
    }

    public String getSnies() {
        return snies;
    }

    public void setSnies(String snies) {
        this.snies = snies;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPensum() {
        return pensum;
    }

    public void setPensum(String pensum) {
        this.pensum = pensum;
    }

    public List<Programa> getListaProgramas() {
        return listaProgramas;
    }

    public void setListaProgramas(List<Programa> listaProgramas) {
        this.listaProgramas = listaProgramas;
    }




    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ProgramaBean() {
        id = 0;
        consultarProgramas();
    }

    public String home() {
        return "index.xhtml";
    }

    public String irGrupo() {
        return "gestionGrupos.xhtml";
    }

    public void consultarProgramas() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();

        TypedQuery<Programa> consultaProgramas = em.createNamedQuery("ProgramasfindAll", Programa.class);
        listaProgramas = consultaProgramas.getResultList();
    }

    public void consultarGruposById() {
        if (id != 0) {
            EntityManagerFactory emf
                    = Persistence.createEntityManagerFactory("SisgehoPU");
            EntityManager em = emf.createEntityManager();

            TypedQuery<Programa> consultaProgramas = em.createNamedQuery("Programa.findByID", Programa.class);
            consultaProgramas.setParameter("id", id);
            listaProgramas = consultaProgramas.getResultList();
        }
    }

    public void guardarPrograma() {
        Programa programa = new Programa();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        programa.setDescripcionprograma(descripcion);
        programa.setTipo(id);
        programa.setDescripciontipo(descripcionTipo);
        programa.setModalidad(modalidad);
        programa.setDuracion(duracion);
        programa.setTitulo(titulo);
        programa.setRegistrocalificado(registroCalificado);
        programa.setSnies(snies);
        programa.setDirector(director);
        programa.setEmail(email);

        em.getTransaction().begin();
        em.persist(programa);
        em.getTransaction().commit();
    }

}
