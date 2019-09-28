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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author KTANAKA
 */
@Entity
@Table(name = "recursos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Recursos.findAll", query = "SELECT r FROM Recursos r")
    , @NamedQuery(name = "Recursos.findById", query = "SELECT r FROM Recursos r WHERE r.id = :id")
    , @NamedQuery(name = "Recursos.findByTipo", query = "SELECT r FROM Recursos r WHERE r.tipo = :tipo")
    , @NamedQuery(name = "Recursos.findByNro", query = "SELECT r FROM Recursos r WHERE r.nro = :nro")
    , @NamedQuery(name = "Recursos.findByDisponibilidad", query = "SELECT r FROM Recursos r WHERE r.disponibilidad = :disponibilidad")})
public class Recursos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Column(name = "Tipo")
    private Integer tipo;
    @Lob
    @Column(name = "Descripcion_tipo")
    private String descripciontipo;
    @Column(name = "Nro")
    private String nro;
    @Column(name = "Disponibilidad")
    private Integer disponibilidad;
    @JoinColumn(name = "rowid_horarios", referencedColumnName = "rowid")
    @ManyToOne
    private Horario rowidHorarios;
    @JoinColumn(name = "rowid_sede", referencedColumnName = "rowid")
    @ManyToOne
    private Sede rowidSede;

    public Recursos() {
    }

    public Recursos(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public String getDescripciontipo() {
        return descripciontipo;
    }

    public void setDescripciontipo(String descripciontipo) {
        this.descripciontipo = descripciontipo;
    }

    public String getNro() {
        return nro;
    }

    public void setNro(String nro) {
        this.nro = nro;
    }

    public Integer getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(Integer disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public Horario getRowidHorarios() {
        return rowidHorarios;
    }

    public void setRowidHorarios(Horario rowidHorarios) {
        this.rowidHorarios = rowidHorarios;
    }

    public Sede getRowidSede() {
        return rowidSede;
    }

    public void setRowidSede(Sede rowidSede) {
        this.rowidSede = rowidSede;
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
        if (!(object instanceof Recursos)) {
            return false;
        }
        Recursos other = (Recursos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Recursos[ id=" + id + " ]";
    }
    
}
