/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import beans.DocenteDisponibilidadBean;
import entity.DocenteDisponibilidad;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author SougiroHylian
 */
@ManagedBean
@SessionScoped
public class DocenteValueListener implements Serializable {
//INDISPENSABLE ESTA VARIABLE CON EL ALCANCE ESTÁTICO
    private static List<DocenteDisponibilidad> disponibilidadList;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    

   
}
