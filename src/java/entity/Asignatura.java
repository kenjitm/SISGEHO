/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author IngenieroDesarrollo
 */
@Entity
@Table(name = "asignatura")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Asignatura.findAll", query = "SELECT a FROM Asignatura a")
    ,
    @NamedQuery(name = "Asignatura.findById", query = "SELECT a FROM Asignatura a WHERE a.id = :id")
    ,
    @NamedQuery(name = "Asignatura.findByDescripcion", query = "SELECT a FROM Asignatura a WHERE a.descripcion = :descripcion")
    ,
    @NamedQuery(name = "Asignatura.findBySemestre", query = "SELECT a FROM Asignatura a WHERE a.semestre = :semestre")
    ,
    @NamedQuery(name = "Asignatura.findByActivo", query = "SELECT a FROM Asignatura a WHERE a.activo = :activo")})
public class Asignatura implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "semestre")
    private Integer semestre;
    @Basic(optional = false)
    @Column(name = "activo")
    private boolean activo;
    @JoinColumn(name = "rowid_programa", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Programa rowidPrograma;
    @JoinColumn(name = "rowid_pensum", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Pensum rowidPensum;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rowidAsignatura")
    private Collection<AsignaturaDocente> asignaturaDocenteCollection;

    @Transient
    private boolean editable;

    public Asignatura() {
        editable = false;
    }

    public Asignatura(Integer id) {
        this.id = id;
    }

    public Asignatura(Integer id, String descripcion, Integer semestre, boolean activo) {
        this.id = id;
        this.descripcion = descripcion.toUpperCase();
        this.semestre = semestre;
        this.activo = activo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion.toUpperCase();
    }

    public Integer getSemestre() {
        return semestre;
    }

    public void setSemestre(Integer semestre) {
        this.semestre = semestre;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Programa getRowidPrograma() {
        return rowidPrograma;
    }

    public void setRowidPrograma(Programa rowidPrograma) {
        this.rowidPrograma = rowidPrograma;
    }

    public Pensum getRowidPensum() {
        return rowidPensum;
    }

    public void setRowidPensum(Pensum rowidPensum) {
        this.rowidPensum = rowidPensum;
    }

    @XmlTransient
    public Collection<AsignaturaDocente> getAsignaturaDocenteCollection() {
        return asignaturaDocenteCollection;
    }

    public void setAsignaturaDocenteCollection(Collection<AsignaturaDocente> asignaturaDocenteCollection) {
        this.asignaturaDocenteCollection = asignaturaDocenteCollection;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
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
        if (!(object instanceof Asignatura)) {
            return false;
        }
        Asignatura other = (Asignatura) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Asignatura[ id=" + id + " ]";
    }

}
