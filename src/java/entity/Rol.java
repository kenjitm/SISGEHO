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
@Table(name = "rol")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rol.findAll", query = "SELECT r FROM Rol r"),
    @NamedQuery(name = "Rol.findById", query = "SELECT r FROM Rol r WHERE r.id = :id"),
    @NamedQuery(name = "Rol.findByNombre", query = "SELECT r FROM Rol r WHERE r.nombre = :nombre"),
    @NamedQuery(name = "Rol.findByDescripcion", query = "SELECT r FROM Rol r WHERE r.descripcion = :descripcion"),
    @NamedQuery(name = "Rol.findByActivo", query = "SELECT r FROM Rol r WHERE r.activo = :activo"),
    @NamedQuery(name = "Rol.findByBitInsert", query = "SELECT r FROM Rol r WHERE r.bitInsert = :bitInsert"),
    @NamedQuery(name = "Rol.findByBitUpdate", query = "SELECT r FROM Rol r WHERE r.bitUpdate = :bitUpdate"),
    @NamedQuery(name = "Rol.findByBitDelete", query = "SELECT r FROM Rol r WHERE r.bitDelete = :bitDelete")})
public class Rol implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "activo")
    private boolean activo;
    @Basic(optional = false)
    @Column(name = "bitInsert")
    private boolean bitInsert;
    @Basic(optional = false)
    @Column(name = "bitUpdate")
    private boolean bitUpdate;
    @Basic(optional = false)
    @Column(name = "bitDelete")
    private boolean bitDelete;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rowidRol")
    private Collection<UsuarioRol> usuarioRolCollection;
@Transient
    private boolean editable;
    public Rol() {
    }
 public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
    public Rol(Integer id) {
        this.id = id;
    }

    public Rol(Integer id, String nombre, String descripcion, boolean activo, boolean bitInsert, boolean bitUpdate, boolean bitDelete) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.activo = activo;
        this.bitInsert = bitInsert;
        this.bitUpdate = bitUpdate;
        this.bitDelete = bitDelete;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public boolean getBitInsert() {
        return bitInsert;
    }

    public void setBitInsert(boolean bitInsert) {
        this.bitInsert = bitInsert;
    }

    public boolean getBitUpdate() {
        return bitUpdate;
    }

    public void setBitUpdate(boolean bitUpdate) {
        this.bitUpdate = bitUpdate;
    }

    public boolean getBitDelete() {
        return bitDelete;
    }

    public void setBitDelete(boolean bitDelete) {
        this.bitDelete = bitDelete;
    }

    @XmlTransient
    public Collection<UsuarioRol> getUsuarioRolCollection() {
        return usuarioRolCollection;
    }

    public void setUsuarioRolCollection(Collection<UsuarioRol> usuarioRolCollection) {
        this.usuarioRolCollection = usuarioRolCollection;
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
        if (!(object instanceof Rol)) {
            return false;
        }
        Rol other = (Rol) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
}
