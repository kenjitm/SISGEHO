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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author SougiroHylian
 */
@Entity
@Table(name = "asignacion_grupos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AsignacionGrupos.findAll", query = "SELECT a FROM AsignacionGrupos a"),
    @NamedQuery(name = "AsignacionGrupos.findById", query = "SELECT a FROM AsignacionGrupos a WHERE a.id = :id"),
    @NamedQuery(name = "AsignacionGrupos.findByRowidGrupo", query = "SELECT a FROM AsignacionGrupos a WHERE a.rowidGrupo = :rowidGrupo"),
    @NamedQuery(name = "AsignacionGrupos.findByCantidadEstudiantes", query = "SELECT a FROM AsignacionGrupos a WHERE a.cantidadEstudiantes = :cantidadEstudiantes"),
    @NamedQuery(name = "AsignacionGrupos.findByRowidAsignacion", query = "SELECT a FROM AsignacionGrupos a WHERE a.rowidAsignacion = :rowidAsignacion")})
public class AsignacionGrupos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "rowid_grupo")
    private Grupo rowidGrupo;
    @Basic(optional = false)
    @Column(name = "cantidadEstudiantes")
    private int cantidadEstudiantes;
    @Basic(optional = false)
    @Column(name = "rowid_asignacion")
    private Asignacion rowidAsignacion;

    public AsignacionGrupos() {
    }

    public AsignacionGrupos(Integer id) {
        this.id = id;
    }

    public AsignacionGrupos(Integer id, Grupo rowidGrupo, int cantidadEstudiantes, Asignacion rowidAsignacion) {
        this.id = id;
        this.rowidGrupo = rowidGrupo;
        this.cantidadEstudiantes = cantidadEstudiantes;
        this.rowidAsignacion = rowidAsignacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Grupo getRowidGrupo() {
        return rowidGrupo;
    }

    public void setRowidGrupo(Grupo rowidGrupo) {
        this.rowidGrupo = rowidGrupo;
    }

    public int getCantidadEstudiantes() {
        return cantidadEstudiantes;
    }

    public void setCantidadEstudiantes(int cantidadEstudiantes) {
        this.cantidadEstudiantes = cantidadEstudiantes;
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
