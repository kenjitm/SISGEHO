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
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author IngenieroDesarrollo
 */
@Entity
@Table(name = "recurso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Recurso.findAll", query = "SELECT r FROM Recurso r"),
    @NamedQuery(name = "Recurso.findById", query = "SELECT r FROM Recurso r WHERE r.id = :id"),
    @NamedQuery(name = "Recurso.findByDescripcion", query = "SELECT r FROM Recurso r WHERE r.descripcion = :descripcion"),
    @NamedQuery(name = "Recurso.findByNomenclatura", query = "SELECT r FROM Recurso r WHERE r.nomenclatura = :nomenclatura"),
    @NamedQuery(name = "Recurso.findByCapacidad", query = "SELECT r FROM Recurso r WHERE r.capacidad = :capacidad"),
    @NamedQuery(name = "Recurso.findByActivo", query = "SELECT r FROM Recurso r WHERE r.activo = :activo")})
public class Recurso implements Serializable {

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
    @Column(name = "nomenclatura")
    private String nomenclatura;
    @Basic(optional = false)
    @Column(name = "capacidad")
    private int capacidad;
    @Basic(optional = false)
    @Column(name = "activo")
    private boolean activo;
    @JoinColumn(name = "rowid_tipo", referencedColumnName = "id")
    @ManyToOne(optional = true)
    private TipoRecurso rowidTipo;
    @JoinColumn(name = "rowid_sede", referencedColumnName = "id")
    @ManyToOne(optional = true)
    private Sede rowidSede;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rowidRecurso")
    private Collection<HorarioAsignado> horarioAsignadoCollection;

    //Atributo para poder renderizar los campos de editar en la tabla
    //Ponerlo como Transient para que no afecte los querys, ya que es un campo que no existe en la DB
    @Transient
    private boolean editable;
    //Indispensable poner los set y get del atributo "editable"
    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
    public Recurso() {
    }

    public Recurso(Integer id) {
        this.id = id;
    }

    public Recurso(Integer id, String descripcion, String nomenclatura, int capacidad, boolean activo) {
        this.id = id;
        this.descripcion = descripcion;
        this.nomenclatura = nomenclatura;
        this.capacidad = capacidad;
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
        this.descripcion = descripcion;
    }

    public String getNomenclatura() {
        return nomenclatura;
    }

    public void setNomenclatura(String nomenclatura) {
        this.nomenclatura = nomenclatura;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public TipoRecurso getRowidTipo() {
        return rowidTipo;
    }

    public void setRowidTipo(TipoRecurso rowidTipo) {
        this.rowidTipo = rowidTipo;
    }

    public Sede getRowidSede() {
        return rowidSede;
    }

    public void setRowidSede(Sede rowidSede) {
        this.rowidSede = rowidSede;
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
        if (!(object instanceof Recurso)) {
            return false;
        }
        Recurso other = (Recurso) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Recurso[ id=" + id + " ]";
    }
    
}
