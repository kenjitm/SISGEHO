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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author IngenieroDesarrollo
 */
@Entity
@Table(name = "asignacion_grupos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AsignacionGrupos.findAll", query = "SELECT a FROM AsignacionGrupos a"),
    @NamedQuery(name = "AsignacionGrupos.findById", query = "SELECT a FROM AsignacionGrupos a WHERE a.id = :id"),
    @NamedQuery(name = "AsignacionGrupos.findByCantidadEstudiantes", query = "SELECT a FROM AsignacionGrupos a WHERE a.cantidadEstudiantes = :cantidadEstudiantes")})
public class AsignacionGrupos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "cantidadEstudiantes")
    private Integer cantidadEstudiantes;
    @JoinColumn(name = "rowid_grupo", referencedColumnName = "id")
    @ManyToOne
    private Grupo rowidGrupo;
    @JoinColumn(name = "rowid_asignacion", referencedColumnName = "id")
    @ManyToOne
    private Asignacion rowidAsignacion;

    public AsignacionGrupos() {
    }

    public AsignacionGrupos(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCantidadEstudiantes() {
        return cantidadEstudiantes;
    }

    public void setCantidadEstudiantes(Integer cantidadEstudiantes) {
        this.cantidadEstudiantes = cantidadEstudiantes;
    }

    public Grupo getRowidGrupo() {
        return rowidGrupo;
    }

    public void setRowidGrupo(Grupo rowidGrupo) {
        this.rowidGrupo = rowidGrupo;
    }

    public Asignacion getRowidAsignacion() {
        return rowidAsignacion;
    }

    public void setRowidAsignacion(Asignacion rowidAsignacion) {
        this.rowidAsignacion = rowidAsignacion;
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
        if (!(object instanceof AsignacionGrupos)) {
            return false;
        }
        AsignacionGrupos other = (AsignacionGrupos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.AsignacionGrupos[ id=" + id + " ]";
    }
    
}
