/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author IngenieroDesarrollo
 */
@Entity
@Table(name = "docente_disponibilidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DocenteDisponibilidad.findAll", query = "SELECT d FROM DocenteDisponibilidad d"),
    @NamedQuery(name = "DocenteDisponibilidad.findById", query = "SELECT d FROM DocenteDisponibilidad d WHERE d.id = :id"),
    @NamedQuery(name = "DocenteDisponibilidad.findByJornada", query = "SELECT d FROM DocenteDisponibilidad d WHERE d.jornada = :jornada"),
    @NamedQuery(name = "DocenteDisponibilidad.findByLunes", query = "SELECT d FROM DocenteDisponibilidad d WHERE d.lunes = :lunes"),
    @NamedQuery(name = "DocenteDisponibilidad.findByMartes", query = "SELECT d FROM DocenteDisponibilidad d WHERE d.martes = :martes"),
    @NamedQuery(name = "DocenteDisponibilidad.findByMiercoles", query = "SELECT d FROM DocenteDisponibilidad d WHERE d.miercoles = :miercoles"),
    @NamedQuery(name = "DocenteDisponibilidad.findByJueves", query = "SELECT d FROM DocenteDisponibilidad d WHERE d.jueves = :jueves"),
    @NamedQuery(name = "DocenteDisponibilidad.findByViernes", query = "SELECT d FROM DocenteDisponibilidad d WHERE d.viernes = :viernes"),
    @NamedQuery(name = "DocenteDisponibilidad.findBySabado", query = "SELECT d FROM DocenteDisponibilidad d WHERE d.sabado = :sabado")})
public class DocenteDisponibilidad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "jornada")
    private String jornada;
    @Basic(optional = false)
    @Column(name = "Lunes")
    private boolean lunes;
    @Basic(optional = false)
    @Column(name = "Martes")
    private boolean martes;
    @Basic(optional = false)
    @Column(name = "Miercoles")
    private boolean miercoles;
    @Basic(optional = false)
    @Column(name = "Jueves")
    private boolean jueves;
    @Basic(optional = false)
    @Column(name = "Viernes")
    private boolean viernes;
    @Basic(optional = false)
    @Column(name = "Sabado")
    private boolean sabado;
    @JoinColumn(name = "rowid_docente", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Docente rowidDocente;
@Transient
    private boolean editable;
    public DocenteDisponibilidad() {
    }
 public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
    public DocenteDisponibilidad(Integer id) {
        this.id = id;
    }

    public DocenteDisponibilidad(Integer id, String jornada, boolean lunes, boolean martes, boolean miercoles, boolean jueves, boolean viernes, boolean sabado) {
        this.id = id;
        this.jornada = jornada;
        this.lunes = lunes;
        this.martes = martes;
        this.miercoles = miercoles;
        this.jueves = jueves;
        this.viernes = viernes;
        this.sabado = sabado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJornada() {
        return jornada;
    }

    public void setJornada(String jornada) {
        this.jornada = jornada;
    }

    public boolean getLunes() {
        return lunes;
    }

    public void setLunes(boolean lunes) {
        this.lunes = lunes;
    }

    public boolean getMartes() {
        return martes;
    }

    public void setMartes(boolean martes) {
        this.martes = martes;
    }

    public boolean getMiercoles() {
        return miercoles;
    }

    public void setMiercoles(boolean miercoles) {
        this.miercoles = miercoles;
    }

    public boolean getJueves() {
        return jueves;
    }

    public void setJueves(boolean jueves) {
        this.jueves = jueves;
    }

    public boolean getViernes() {
        return viernes;
    }

    public void setViernes(boolean viernes) {
        this.viernes = viernes;
    }

    public boolean getSabado() {
        return sabado;
    }

    public void setSabado(boolean sabado) {
        this.sabado = sabado;
    }

    public Docente getRowidDocente() {
        return rowidDocente;
    }

    public void setRowidDocente(Docente rowidDocente) {
        this.rowidDocente = rowidDocente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DocenteDisponibilidad)) {
            return false;
        }
        DocenteDisponibilidad other = (DocenteDisponibilidad) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.DocenteDisponibilidad[ id=" + id + " ]";
    }
    
}
