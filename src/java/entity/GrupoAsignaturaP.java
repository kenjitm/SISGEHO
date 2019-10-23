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
@Table(name = "grupo_asignatura_p")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GrupoAsignaturaP.findAll", query = "SELECT g FROM GrupoAsignaturaP g"),
    @NamedQuery(name = "GrupoAsignaturaP.findById", query = "SELECT g FROM GrupoAsignaturaP g WHERE g.id = :id")})
public class GrupoAsignaturaP implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "rowid_Periodo", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Periodo rowidPeriodo;
    @JoinColumn(name = "rowid_Asignatura_Docente", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private AsignaturaDocente rowidAsignaturaDocente;
    @JoinColumn(name = "rowid_grupo", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Grupo rowidGrupo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rowidGrupoAsignatura")
    private Collection<HorarioAsignado> horarioAsignadoCollection;

    public GrupoAsignaturaP() {
    }

    public GrupoAsignaturaP(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Periodo getRowidPeriodo() {
        return rowidPeriodo;
    }

    public void setRowidPeriodo(Periodo rowidPeriodo) {
        this.rowidPeriodo = rowidPeriodo;
    }

    public AsignaturaDocente getRowidAsignaturaDocente() {
        return rowidAsignaturaDocente;
    }

    public void setRowidAsignaturaDocente(AsignaturaDocente rowidAsignaturaDocente) {
        this.rowidAsignaturaDocente = rowidAsignaturaDocente;
    }

    public Grupo getRowidGrupo() {
        return rowidGrupo;
    }

    public void setRowidGrupo(Grupo rowidGrupo) {
        this.rowidGrupo = rowidGrupo;
    }

    @XmlTransient
    public Collection<HorarioAsignado> getHorarioAsignadoCollection() {
        return horarioAsignadoCollection;
    }

    public void setHorarioAsignadoCollection(Collection<HorarioAsignado> horarioAsignadoCollection) {
        this.horarioAsignadoCollection = horarioAsignadoCollection;
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
        if (!(object instanceof GrupoAsignaturaP)) {
            return false;
        }
        GrupoAsignaturaP other = (GrupoAsignaturaP) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.GrupoAsignaturaP[ id=" + id + " ]";
    }
    
}
