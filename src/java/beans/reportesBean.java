/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author IngenieroDesarrollo
 */
@ManagedBean
@RequestScoped
public class reportesBean {
    public String irReportes(){
        return "reportes.xhtml";
    }
}
