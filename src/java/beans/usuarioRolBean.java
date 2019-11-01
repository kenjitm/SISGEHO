/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Sede;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import entity.UsuarioRol;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
/**
 *
 * @author IngenieroDesarrollo
 */
@ManagedBean
@ViewScoped //INDISPENSABLE PONER ESTA ANOTACIÓN EN VEZ DEL REQUESTSCOPED
public class usuarioRolBean {
    private UsuarioRol userRol;
    private UsuarioRol userRolSearch;
    //INDISPENSABLE ESTA VARIABLE CON EL ALCANCE ESTÁTICO
    private static List<UsuarioRol> userRolList;
    public usuarioRolBean(){
        userRol = new UsuarioRol();
        userRolSearch = new UsuarioRol();
    }

    public UsuarioRol getUserRol() {
        return userRol;
    }

    public void setUserRol(UsuarioRol userRol) {
        this.userRol = userRol;
    }

    public UsuarioRol getUserRolSearch() {
        return userRolSearch;
    }

    public void setUserRolSearch(UsuarioRol userRolSearch) {
        this.userRolSearch = userRolSearch;
    }

    public List<UsuarioRol> getUserRolList() {
        return userRolList;
    }
}
