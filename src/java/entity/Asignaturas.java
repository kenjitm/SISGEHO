/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author KTANAKA
 */
@Entity
@Table(name = "asignaturas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Asignaturas.findAll", query = "SELECT a FROM Asignaturas a")
    , @NamedQuery(name = "Asignaturas.findById", query = "SELECT a FROM Asignaturas a WHERE a.id = :id")
    , @NamedQuery(name = "Asignaturas.findByRowid", query = "SELECT a FROM Asignaturas a WHERE a.rowid = :rowid")})
public class Asignaturas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Lob
    @Column(name = "Descripcion")
    private String descripcion;
    @Lob
    @Column(name = "Jornada")
    @Enumerated(EnumType.STRING)
    private TipoJornada jornada;
    @Column(name = "rowid")
    private String rowid;
    @OneToMany(mappedBy = "rowidAsignatura")
    private List<Pensum> pensumList;
    @OneToMany(mappedBy = "rowidAsignatura")
    private List<Horario> horarioList;
    @OneToMany(mappedBy = "rowidAsignatura")
    private List<Grupos> gruposList;
    @JoinColumn(name = "rowid_docente", referencedColumnName = "rowid")
    @ManyToOne
    private Docente rowidDocente;

    public Asignaturas() {
    }

    public Asignaturas(Integer id) {
        this.id = id;
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

    public TipoJornada getJornada() {
        return jornada;
    }

    public void setJornada(TipoJornada jornada) {
        this.jornada = jornada;
    }

    public String getRowid() {
        return rowid;
    }

    public void setRowid(String rowid) {
        this.rowid = rowid;
    }

    @XmlTransient
    public List<Pensum> getPensumList() {
        return pensumList;
    }

    public void setPensumList(List<Pensum> pensumList) {
        this.pensumList = pensumList;
    }

    @XmlTransient
    public List<Horario> getHorarioList() {
        return horarioList;
    }

    public void setHorarioList(List<Horario> horarioList) {
        this.horarioList = horarioList;
    }

    @XmlTransient
    public List<Grupos> getGruposList() {
        return gruposList;
    }

    public void setGruposList(List<Grupos> gruposList) {
        this.gruposList = gruposList;
    }

    public Docente getRowidDocente() {
        return rowidDocente;
    }

    public void setRowidDocente(Docente rowidDocente) {
        this.rowidDocente = rowidDocente;
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
        if (!(object instanceof Asignaturas)) {
            return false;
        }
        Asignaturas other = (Asignaturas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Asignaturas[ id=" + id + " ]";
    }
    
}
