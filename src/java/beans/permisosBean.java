/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Rol;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import entity.UsuarioRol;
/**
 *
 * @author SougiroHylian
 */
@ManagedBean
@ViewScoped //INDISPENSABLE PONER ESTA ANOTACIÓN EN VEZ DEL REQUESTSCOPED
public class permisosBean {
    private Boolean bitInsert;
    private Boolean bitUpdate;
    private Boolean bitDelete;
    private Boolean bitCoordinacion;

    public Boolean getBitInsert() {
        return bitInsert;
    }

    public void setBitInsert(Boolean bitInsert) {
        this.bitInsert = bitInsert;
    }

    public Boolean getBitUpdate() {
        return bitUpdate;
    }

    public void setBitUpdate(Boolean bitUpdate) {
        this.bitUpdate = bitUpdate;
    }

    public Boolean getBitDelete() {
        return bitDelete;
    }

    public Boolean getBitCoordinacion() {
        return bitCoordinacion;
    }

    public void setBitCoordinacion(Boolean bitCoordinacion) {
        this.bitCoordinacion = bitCoordinacion;
    }

    public void setBitDelete(Boolean bitDelete) {
        this.bitDelete = bitDelete;
    }
    public permisosBean(){
        GlobalBean globalBean = new GlobalBean();
        Rol rol = new Rol();
        rol = globalBean.getUserRol();
        bitInsert = rol.getBitInsert();//(Boolean)globalBean.getObjectFromSession("bitInsert"); 
        System.out.println("********beans.permisosBean: Permiso Insert: --"+bitInsert.toString());
        bitUpdate = rol.getBitUpdate();//(Boolean)globalBean.getObjectFromSession("bitUpdate"); 
        bitDelete = rol.getBitDelete();//(Boolean)globalBean.getObjectFromSession("bitDelete"); 
        String rolDes = globalBean.getObjectFromSession("roles").toString();
        if ("DOCENTE".equals(rolDes)) {
            setBitCoordinacion(true);
        }else
            setBitCoordinacion(false);
    }
}
