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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author IngenieroDesarrollo
 */
@Entity
@Table(name = "tipo_recurso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoRecurso_1.findAll", query = "SELECT t FROM TipoRecurso_1 t"),
    @NamedQuery(name = "TipoRecurso_1.findById", query = "SELECT t FROM TipoRecurso_1 t WHERE t.id = :id"),
    @NamedQuery(name = "TipoRecurso_1.findByCodigo", query = "SELECT t FROM TipoRecurso_1 t WHERE t.codigo = :codigo"),
    @NamedQuery(name = "TipoRecurso_1.findByDescripcion", query = "SELECT t FROM TipoRecurso_1 t WHERE t.descripcion = :descripcion"),
    @NamedQuery(name = "TipoRecurso_1.findByActivo", query = "SELECT t FROM TipoRecurso_1 t WHERE t.activo = :activo")})
public class TipoRecurso_1 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "codigo")
    private int codigo;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "activo")
    private boolean activo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rowidTipo")
    private Collection<Recurso> recursoCollection;

    public TipoRecurso_1() {
    }

    public TipoRecurso_1(Integer id) {
        this.id = id;
    }

    public TipoRecurso_1(Integer id, int codigo, String descripcion, boolean activo) {
        this.id = id;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.activo = activo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
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

    @XmlTransient
    public Collection<Recurso> getRecursoCollection() {
        return recursoCollection;
    }

    public void setRecursoCollection(Collection<Recurso> recursoCollection) {
        this.recursoCollection = recursoCollection;
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
        if (!(object instanceof TipoRecurso_1)) {
            return false;
        }
        TipoRecurso_1 other = (TipoRecurso_1) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.TipoRecurso_1[ id=" + id + " ]";
    }
    
}
