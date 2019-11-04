/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Rol;
import entity.Usuario;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpSession;
import utils.SessionUtils;
import entity.UsuarioRol;
import java.util.ArrayList;

/**
 *
 * @author KTANAKA
 */
@ManagedBean
@RequestScoped
public class LoginBean implements Serializable {

    /**
     * Creates a new instance of LoginBean
     */
    private String usuario;
    private String password;
    private SessionUtils session;
    private List<Rol> listaRoles;
    private List<String> nombresRoles;
    private String nombreRol;
    private int idRol;
    public String getUsuario() {
        return usuario;
    }

    public SessionUtils getSession() {
        return session;
    }

    public void setSession(SessionUtils session) {
        this.session = session;
    }

    public List<Rol> getListaRoles() {
        return listaRoles;
    }

    public void setListaRoles(List<Rol> listaRoles) {
        this.listaRoles = listaRoles;
    }

    public List<String> getNombresRoles() {
        return nombresRoles;
    }

    public void setNombresRoles(List<String> nombresRoles) {
        this.nombresRoles = nombresRoles;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
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

    public LoginBean() {
    }

    public String home() {
        return "index.xhtml";
    }

    public String recoverPassword() {
        return "recuperarPassword.xhtml";
    }

    public String newUser() {
        return "newUser";
    }

    public String login() {

        if (usuario != null && !usuario.isEmpty()) {
            EntityManagerFactory emf
                    = Persistence.createEntityManagerFactory("SisgehoPU");
            EntityManager em = emf.createEntityManager();

            TypedQuery<Usuario> consultaUsuarios = em.createNamedQuery("Usuario.findByUsuario", Usuario.class);
            consultaUsuarios.setParameter("usuario", usuario);
            List<Usuario> lista = consultaUsuarios.getResultList();
            Usuario user = consultaUsuarios.getResultList().get(0);
            usuarioRolBean userBean = new usuarioRolBean();
            /*ConexDB db = new ConexDB();
            UsuarioRol userRol = new UsuarioRol();
            try
            {
               userRol =  db.getUserRols(user.getId());
            }catch(Exception ex)
            {
                System.out.println("Error consultando Roles Usuarios: "+ex.getMessage());
            }*/

            lista = lista.stream().filter(lu -> lu != null && lu.getUsuario() != null && lu.getUsuario().equals(usuario)).collect(Collectors.toList());
            if (lista != null) {
                if (!lista.isEmpty() && lista.get(0) != null && lista.get(0).getPassword().equals(password)) {

                    FacesContext context = FacesContext.getCurrentInstance();
                    context.getExternalContext().getSessionMap().put("user", usuario);
                    context.getExternalContext().getSessionMap().put("username", user.getNombre());
                    //context.getExternalContext().getSessionMap().put("roles", userRol.getRowidRol().getNombre());
                    //session = new SessionUtils();
                    //session.add("user", usuario);

                    obtenerRoles(user.getId());
                    obtenerNombresRoles();
                    GlobalBean globalBean = new GlobalBean();
                    globalBean.saveObjectInSession(nombreRol, "roles");
                    //usuario correcto
                    return "Dashboard";
                } else {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Acceso denegado", "Contraseña incorrecta");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                    return null;
                }
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Acceso denegado", "Usuario no existe");
                FacesContext.getCurrentInstance().addMessage(null, message);
                return null;
            }
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Acceso denegado", "Usuario o contraseña vacios");
            FacesContext.getCurrentInstance().addMessage(null, message);
            return "";
        }

    }

    public void obtenerRoles(int id) {
        List<UsuarioRol> listaUsuarioRoles = new ArrayList<>();
        listaRoles = new ArrayList<>();
        listaUsuarioRoles = consultarUsuarioRol(id);
        if (listaUsuarioRoles != null) {
            for (UsuarioRol usuarioRol : listaUsuarioRoles) {
                if (usuarioRol != null && usuarioRol.getRowidRol() != null) {
                    listaRoles.add(usuarioRol.getRowidRol());
                    Rol rol = usuarioRol.getRowidRol();
                    GlobalBean globalBean = new GlobalBean();
                    globalBean.saveObjectInSession(nombreRol, "listaRoles");
                    globalBean.saveObjectInSessionBit(rol.getBitInsert(), "bitInsert");
                    globalBean.saveObjectInSessionBit(rol.getBitUpdate(), "bitUpdate");
                    globalBean.saveObjectInSessionBit(rol.getBitDelete(), "bitDelete");
                }
            }
        }
    }

    public void obtenerNombresRoles() {
        nombresRoles = new ArrayList<>();
        nombreRol = "";
        if (listaRoles != null && !listaRoles.isEmpty()) {
            for (Rol rol : listaRoles) {
                if (rol.getNombre() != null ) {
                    nombresRoles.add(rol.getNombre());
                    if (nombreRol.isEmpty()) {
                        nombreRol += rol.getNombre();
                    } else {
                        nombreRol += ", " + rol.getNombre();
                    }
                }
            }
        }
    }

    public List<UsuarioRol> consultarUsuarioRol(int id) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();

        TypedQuery<UsuarioRol> usuariosRoles = em.createNamedQuery("UsuarioRol.findByUserId", UsuarioRol.class);
        usuariosRoles.setParameter("id", id);
        return (usuariosRoles.getResultList().isEmpty()) ? null : usuariosRoles.getResultList();
    }

}
