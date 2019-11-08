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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author IngenieroDesarrollo
 */
@Entity
@Table(name = "asignatura_docente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AsignaturaDocente.findAll", query = "SELECT a FROM AsignaturaDocente a"),
    @NamedQuery(name = "AsignaturaDocente.findById", query = "SELECT a FROM AsignaturaDocente a WHERE a.id = :id")})
public class AsignaturaDocente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rowidAsignaturaDocente")
    private Collection<GrupoAsignaturaP> grupoAsignaturaPCollection;
    @JoinColumn(name = "rowid_docente", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Docente rowidDocente;
    @JoinColumn(name = "rowid_asignatura", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Asignatura rowidAsignatura;

    public AsignaturaDocente() {
    }

    public AsignaturaDocente(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlTransient
    public Collection<GrupoAsignaturaP> getGrupoAsignaturaPCollection() {
        return grupoAsignaturaPCollection;
    }

    public void setGrupoAsignaturaPCollection(Collection<GrupoAsignaturaP> grupoAsignaturaPCollection) {
        this.grupoAsignaturaPCollection = grupoAsignaturaPCollection;
    }

    public Docente getRowidDocente() {
        return rowidDocente;
    }

    public void setRowidDocente(Docente rowidDocente) {
        this.rowidDocente = rowidDocente;
    }

    public Asignatura getRowidAsignatura() {
        return rowidAsignatura;
    }

    public void setRowidAsignatura(Asignatura rowidAsignatura) {
        this.rowidAsignatura = rowidAsignatura;
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
        if (!(object instanceof AsignaturaDocente)) {
            return false;
        }
        AsignaturaDocente other = (AsignaturaDocente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.AsignaturaDocente[ id=" + id + " ]";
    }
    
}
