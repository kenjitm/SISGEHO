/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author IngenieroDesarrollo
 */
@Entity
@Table(name = "log_auditoria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LogAuditoria.findAll", query = "SELECT l FROM LogAuditoria l"),
    @NamedQuery(name = "LogAuditoria.findById", query = "SELECT l FROM LogAuditoria l WHERE l.id = :id"),
    @NamedQuery(name = "LogAuditoria.findByAccion", query = "SELECT l FROM LogAuditoria l WHERE l.accion = :accion"),
    @NamedQuery(name = "LogAuditoria.findByTabla", query = "SELECT l FROM LogAuditoria l WHERE l.tabla = :tabla"),
    @NamedQuery(name = "LogAuditoria.findByFecha", query = "SELECT l FROM LogAuditoria l WHERE l.fecha = :fecha")})
public class LogAuditoria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "accion")
    private String accion;
    @Basic(optional = false)
    @Column(name = "tabla")
    private String tabla;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @JoinColumn(name = "rowid_usuario", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario rowidUsuario;

    public LogAuditoria() {
    }

    public LogAuditoria(Integer id) {
        this.id = id;
    }

    public LogAuditoria(Integer id, String accion, String tabla, Date fecha) {
        this.id = id;
        this.accion = accion;
        this.tabla = tabla;
        this.fecha = fecha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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
        if (!(object instanceof LogAuditoria)) {
            return false;
        }
        LogAuditoria other = (LogAuditoria) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.LogAuditoria[ id=" + id + " ]";
    }
    
}
