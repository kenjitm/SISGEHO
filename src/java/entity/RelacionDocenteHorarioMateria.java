/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import javax.faces.context.FacesContext;
import javax.persistence.Transient;
import javax.servlet.http.HttpSession;

/**
 *
 * @author SougiroHylian
 */
public class RelacionDocenteHorarioMateria {
    private Integer IdDocente;
    private Integer IdMateria;
    private Integer IdHorario;
    private String Jornada;
    private String Lunes;
    private String Martes;
    private String Miercoles;
    private String Jueves;
    private String Viernes;
    private String Sabado;
    //Atributo para poder renderizar los campos de editar en la tabla
    //Ponerlo como Transient para que no afecte los querys, ya que es un campo que no existe en la DB
    @Transient
    private boolean show;

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public Integer getIdDocente() {
        return IdDocente;
    }

    public void setIdDocente(Integer IdDocente) {
        this.IdDocente = IdDocente;
    }

    public Integer getIdMateria() {
        return IdMateria;
    }

    public void setIdMateria(Integer IdMateria) {
        this.IdMateria = IdMateria;
    }

    public Integer getIdHorario() {
        return IdHorario;
    }

    public void setIdHorario(Integer IdHorario) {
        this.IdHorario = IdHorario;
    }

    public String getJornada() {
        return Jornada;
    }

    public void setJornada(String Jornada) {
        this.Jornada = Jornada;
    }

    public String getLunes() {
        return Lunes;
    }

    public void setLunes(String Lunes) {
        this.Lunes = Lunes;
    }

    public String getMartes() {
        return Martes;
    }

    public void setMartes(String Martes) {
        this.Martes = Martes;
    }

    public String getMiercoles() {
        return Miercoles;
    }

    public void setMiercoles(String Miercoles) {
        this.Miercoles = Miercoles;
    }

    public String getJueves() {
        return Jueves;
    }

    public void setJueves(String Jueves) {
        this.Jueves = Jueves;
    }

    public String getViernes() {
        return Viernes;
    }

    public void setViernes(String Viernes) {
        this.Viernes = Viernes;
    }

    public String getSabado() {
        return Sabado;
    }

    public void setSabado(String Sabado) {
        this.Sabado = Sabado;
    }

    

    public RelacionDocenteHorarioMateria(Integer IdDocente, Integer IdMateria, Integer IdHorario) {
        this.IdDocente = IdDocente;
        this.IdMateria = IdMateria;
        this.IdHorario = IdHorario;
    }
    public RelacionDocenteHorarioMateria() {
    }
    
}
