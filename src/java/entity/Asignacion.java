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
@Table(name = "asignacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Asignacion.findAll", query = "SELECT a FROM Asignacion a"),
    @NamedQuery(name = "Asignacion.findById", query = "SELECT a FROM Asignacion a WHERE a.id = :id")})
public class Asignacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "rowid_asignatura", referencedColumnName = "id")
    @ManyToOne
    private Asignatura rowidAsignatura;
    @JoinColumn(name = "rowid_docente", referencedColumnName = "id")
    @ManyToOne
    private Docente rowidDocente;
    @JoinColumn(name = "rowid_periodo", referencedColumnName = "id")
    @ManyToOne
    private Periodo rowidPeriodo;
    @JoinColumn(name = "rowid_dia", referencedColumnName = "id")
    @ManyToOne
    private Dia rowidDia;
    @JoinColumn(name = "rowid_hora", referencedColumnName = "id")
    @ManyToOne
    private Hora rowidHora;
    @JoinColumn(name = "rowid_sede", referencedColumnName = "id")
    @ManyToOne
    private Sede rowidSede;
    @JoinColumn(name = "rowid_recurso", referencedColumnName = "id")
    @ManyToOne
    private Recurso rowidRecurso;
    @JoinColumn(name = "rowid_hora_final", referencedColumnName = "id")
    @ManyToOne
    private Hora rowidHoraFinal;
@Transient
    private boolean editable;
    public Asignacion() {
        editable = false;
    }
 public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
    public Asignacion(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Asignatura getRowidAsignatura() {
        return rowidAsignatura;
    }

    public void setRowidAsignatura(Asignatura rowidAsignatura) {
        this.rowidAsignatura = rowidAsignatura;
    }

    public Docente getRowidDocente() {
        return rowidDocente;
    }

    public void setRowidDocente(Docente rowidDocente) {
        this.rowidDocente = rowidDocente;
    }

    public Periodo getRowidPeriodo() {
        return rowidPeriodo;
    }

    public void setRowidPeriodo(Periodo rowidPeriodo) {
        this.rowidPeriodo = rowidPeriodo;
    }

    public Dia getRowidDia() {
        return rowidDia;
    }

    public void setRowidDia(Dia rowidDia) {
        this.rowidDia = rowidDia;
    }

    public Hora getRowidHora() {
        return rowidHora;
    }

    public void setRowidHora(Hora rowidHora) {
        this.rowidHora = rowidHora;
    }

    public Sede getRowidSede() {
        return rowidSede;
    }

    public void setRowidSede(Sede rowidSede) {
        this.rowidSede = rowidSede;
    }

    public Recurso getRowidRecurso() {
        return rowidRecurso;
    }

    public void setRowidRecurso(Recurso rowidRecurso) {
        this.rowidRecurso = rowidRecurso;
    }

    public Hora getRowidHoraFinal() {
        return rowidHoraFinal;
    }

    public void setRowidHoraFinal(Hora rowidHoraFinal) {
        this.rowidHoraFinal = rowidHoraFinal;
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
        if (!(object instanceof Asignacion)) {
            return false;
        }
        Asignacion other = (Asignacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Asignacion[ id=" + id + " ]";
    }
    
}
