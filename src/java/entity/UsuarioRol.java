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
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author IngenieroDesarrollo
 */
@Entity
@Table(name = "usuario_rol")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsuarioRol.findAll", query = "SELECT u FROM UsuarioRol u"),
    @NamedQuery(name = "UsuarioRol.findByUserId", query = "SELECT u FROM UsuarioRol u WHERE u.id = :id"),
    @NamedQuery(name = "UsuarioRol.findByActivo", query = "SELECT u FROM UsuarioRol u WHERE u.activo = :activo")})
public class UsuarioRol implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "activo")
    private boolean activo;
    @JoinColumn(name = "rowid_rol", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Rol rowidRol;
    @JoinColumn(name = "rowid_usuario", referencedColumnName = "id")
    @ManyToOne(optional = false)
    //Atributo para poder renderizar los campos de editar en la tabla
    //Ponerlo como Transient para que no afecte los querys, ya que es un campo que no existe en la DB
    /*@Transient
    private boolean editable;*/
    private Usuario rowidUsuario;

    public UsuarioRol() {
        //editable = false;
    }

    /*public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }*/

    public UsuarioRol(Integer id) {
        this.id = id;
    }

    public UsuarioRol(Integer id, boolean activo) {
        this.id = id;
        this.activo = activo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Rol getRowidRol() {
        return rowidRol;
    }

    public void setRowidRol(Rol rowidRol) {
        this.rowidRol = rowidRol;
    }

    public Usuario getRowidUsuario() {
        return rowidUsuario;
    }

    public void setRowidUsuario(Usuario rowidUsuario) {
        this.rowidUsuario = rowidUsuario;
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
        if (!(object instanceof UsuarioRol)) {
            return false;
        }
        UsuarioRol other = (UsuarioRol) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.UsuarioRol[ id=" + id + " ]";
    }
    
}
