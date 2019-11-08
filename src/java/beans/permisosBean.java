/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

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

    public void setBitDelete(Boolean bitDelete) {
        this.bitDelete = bitDelete;
    }
    public permisosBean(){
        GlobalBean globalBean = new GlobalBean();
        bitInsert = (Boolean)globalBean.getObjectFromSession("bitInsert"); 
        System.out.println("********beans.permisosBean: Permiso Insert: --"+bitInsert.toString());
        bitUpdate = (Boolean)globalBean.getObjectFromSession("bitUpdate"); 
        bitDelete = (Boolean)globalBean.getObjectFromSession("bitDelete"); 
    }
}
