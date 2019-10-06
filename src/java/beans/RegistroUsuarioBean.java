/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.TipoId;
import entity.TipoRol;
import entity.Usuario;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Raul A. Hernandez
 */
@ManagedBean
@RequestScoped
public class RegistroUsuarioBean {

    private Integer id;
    private TipoId tipoId;
    private String nombre;
    private String apellido;
    private String telefono;
    private boolean estado;
    private TipoRol rol;
    private String email;
    private String usuario;
    private String password;

    public RegistroUsuarioBean() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TipoId getTipoId() {
        return tipoId;
    }

    public void setTipoId(TipoId tipoId) {
        this.tipoId = tipoId;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public TipoRol getRol() {
        return rol;
    }

    public void setRol(TipoRol rol) {
        this.rol = rol;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public TipoId[] getTipoIdes() {
        return TipoId.values();
    }
    
    public TipoRol[] getTipoRoles() {
        return TipoRol.values();
    }

    public String registrarse() {
        Usuario user = new Usuario();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        user.setId(id);
        user.setTipoId(tipoId);
        user.setNombre(nombre);
        user.setApellido(apellido);
        user.setTelefono(telefono);
        user.setEstado(true);
        user.setRol(rol);
        user.setEmail(email);
        user.setUsuario(usuario);
        user.setPassword(password);
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        return "index";
    }

}
