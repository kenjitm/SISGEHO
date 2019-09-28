/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jhoan david ramirez
 */
@Entity
@Table(name = "pensum")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pensum.findAll", query = "SELECT p FROM Pensum p")
    , @NamedQuery(name = "Pensum.findById", query = "SELECT p FROM Pensum p WHERE p.id = :id")
    , @NamedQuery(name = "Pensum.findByFecha", query = "SELECT p FROM Pensum p WHERE p.fecha = :fecha")
    , @NamedQuery(name = "Pensum.findByRowid", query = "SELECT p FROM Pensum p WHERE p.rowid = :rowid")})
public class Pensum implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Column(name = "Fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "rowid")
    private String rowid;
    @JoinColumn(name = "rowid_asignatura", referencedColumnName = "rowid")
    @ManyToOne
    private Asignaturas rowidAsignatura;
    @OneToMany(mappedBy = "rowidPensum")
    private List<Programa> programaList;
    @OneToMany(mappedBy = "rowidPensum")
    private List<Semestre> semestreList;

    public Pensum() {
    }

    public Pensum(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getRowid() {
        return rowid;
    }

    public void setRowid(String rowid) {
        this.rowid = rowid;
    }

    public Asignaturas getRowidAsignatura() {
        return rowidAsignatura;
    }

    public void setRowidAsignatura(Asignaturas rowidAsignatura) {
        this.rowidAsignatura = rowidAsignatura;
    }

    @XmlTransient
    public List<Programa> getProgramaList() {
        return programaList;
    }

    public void setProgramaList(List<Programa> programaList) {
        this.programaList = programaList;
    }

    @XmlTransient
    public List<Semestre> getSemestreList() {
        return semestreList;
    }

    public void setSemestreList(List<Semestre> semestreList) {
        this.semestreList = semestreList;
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
        if (!(object instanceof Pensum)) {
            return false;
        }
        Pensum other = (Pensum) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Pensum[ id=" + id + " ]";
    }
    
}
