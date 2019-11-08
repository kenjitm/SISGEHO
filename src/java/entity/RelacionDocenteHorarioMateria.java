/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author SougiroHylian
 */
public class RelacionDocenteHorarioMateria {
    private Integer Id;
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
    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
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

    

    public RelacionDocenteHorarioMateria(Integer Id, Integer IdDocente, Integer IdMateria, Integer IdHorario) {
        this.Id = Id;
        this.IdDocente = IdDocente;
        this.IdMateria = IdMateria;
        this.IdHorario = IdHorario;
    }
    public RelacionDocenteHorarioMateria() {
    }
    
}
