/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Asignatura;
import entity.Rol;
import entity.TipoSemestre;
import entity.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import static javax.management.Query.value;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author Kenji
 */
@ManagedBean
@RequestScoped
public class GlobalBean {

    public GlobalBean() {

    }

    public String getLoggedUser() {
        String usuarioLogueado = (String) getObjectFromSession("user");
        return usuarioLogueado;
    }

    public String getLoggedUserName() {
        String usuarioLogueado = (String) getObjectFromSession("username");
        return usuarioLogueado;
    }

    public List<Rol> getListaRoles() {
        List<Rol> listaRoles = new ArrayList<>();
        listaRoles = (List) getObjectFromSession("listaRoles");
        return listaRoles;
    }
    
    public Rol getUserRol(){
        Rol rol = new Rol();
        rol = (Rol) getObjectFromSession("rol");
        return rol;
    }

    public String getUserRoles() {
        String rolesCadena = (String) getObjectFromSession("roles");
        return rolesCadena;
    }

    public Object getObjectFromSession(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(key);
    }

    public void saveObjectInSession(String value, String key) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().put(key, value);
    }
     public void saveObjectFormatInSession(Object value, String key) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().put(key, value);
    }
    public void saveObjectInSessionBit(Boolean valeBit, String key) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().put(key, valeBit);
    }
}
