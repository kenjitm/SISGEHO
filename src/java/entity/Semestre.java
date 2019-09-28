/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jhoan david ramirez
 */
@Entity
@Table(name = "semestre")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Semestre.findAll", query = "SELECT s FROM Semestre s")
    , @NamedQuery(name = "Semestre.findById", query = "SELECT s FROM Semestre s WHERE s.id = :id")
    , @NamedQuery(name = "Semestre.findByNrosemestre", query = "SELECT s FROM Semestre s WHERE s.nrosemestre = :nrosemestre")
    , @NamedQuery(name = "Semestre.findByFechainicio", query = "SELECT s FROM Semestre s WHERE s.fechainicio = :fechainicio")
    , @NamedQuery(name = "Semestre.findByFechafinal", query = "SELECT s FROM Semestre s WHERE s.fechafinal = :fechafinal")})
public class Semestre implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Column(name = "Nro_semestre")
    private Integer nrosemestre;
    @Column(name = "Fecha_inicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechainicio;
    @Column(name = "Fecha_final")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechafinal;
    @JoinColumn(name = "rowid_pensum", referencedColumnName = "rowid")
    @ManyToOne
    private Pensum rowidPensum;

    public Semestre() {
    }

    public Semestre(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNrosemestre() {
        return nrosemestre;
    }

    public void setNrosemestre(Integer nrosemestre) {
        this.nrosemestre = nrosemestre;
    }

    public Date getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(Date fechainicio) {
        this.fechainicio = fechainicio;
    }

    public Date getFechafinal() {
        return fechafinal;
    }

    public void setFechafinal(Date fechafinal) {
        this.fechafinal = fechafinal;
    }

    public Pensum getRowidPensum() {
        return rowidPensum;
    }

    public void setRowidPensum(Pensum rowidPensum) {
        this.rowidPensum = rowidPensum;
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
        if (!(object instanceof Semestre)) {
            return false;
        }
        Semestre other = (Semestre) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Semestre[ id=" + id + " ]";
    }
    
}
