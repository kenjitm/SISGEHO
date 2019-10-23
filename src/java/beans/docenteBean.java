/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Docente;
import entity.TipoId;
import entity.TipoRol;
import entity.Usuario;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author SougiroHylian
 */
@ManagedBean
@RequestScoped
public class docenteBean {
    private Docente docentes = new Docente();
    private Integer id;
    private String nombre;
    private String apellido;
    private Integer edad;
    private String email;
    private String tipo_contrato;
    public Docente getDocentes() {
        return docentes;
    }

    public void setDocentes(Docente docentes) {
        this.docentes = docentes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo_contrato() {
        return tipo_contrato;
    }

    public void setTipo_contrato(String tipo_contrato) {
        this.tipo_contrato = tipo_contrato;
    }

    

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void registrarDocente(Integer id, String nombre, String apellido, Integer edad,
            String email, String tipo_contrato) {
        Docente doce = new Docente();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        doce.setId(id);
        doce.setNombre(nombre);
        doce.setApellido(apellido);
        doce.setEdad(edad);
        doce.setEmail(email);
        doce.setTipoContrato(tipo_contrato);
        em.getTransaction().begin();
        em.persist(doce);
        em.getTransaction().commit();
        
    }
    public void registrarDocenteV() {
        Docente doce = new Docente();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        doce.setId(id);
        doce.setNombre(nombre);
        doce.setApellido(apellido);
        doce.setEdad(edad);
        doce.setEmail(email);
        
        em.getTransaction().begin();
        em.persist(doce);
        em.getTransaction().commit();
        
    }
    public List<Docente> obtenerDocentes() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<Docente> q = em.createNamedQuery("Docente.findAll", Docente.class);
        return q.getResultList();
    }
    public String irDocente(){
        return "gestionDocente.xhtml";
    }
}
