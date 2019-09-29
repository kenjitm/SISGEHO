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
@Table(name = "programa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Programa.findAll", query = "SELECT p FROM Programa p")
    , @NamedQuery(name = "Programa.findById", query = "SELECT p FROM Programa p WHERE p.id = :id")
    , @NamedQuery(name = "Programa.findByTipo", query = "SELECT p FROM Programa p WHERE p.tipo = :tipo")
    , @NamedQuery(name = "Programa.findByDuracion", query = "SELECT p FROM Programa p WHERE p.duracion = :duracion")
    , @NamedQuery(name = "Programa.findByRegistrocalificado", query = "SELECT p FROM Programa p WHERE p.registrocalificado = :registrocalificado")
    , @NamedQuery(name = "Programa.findBySnies", query = "SELECT p FROM Programa p WHERE p.snies = :snies")
    , @NamedQuery(name = "Programa.findByRowid", query = "SELECT p FROM Programa p WHERE p.rowid = :rowid")})
public class Programa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Lob
    @Column(name = "Descripcion_programa")
    private String descripcionprograma;
    @Column(name = "Tipo")
    private Integer tipo;
    @Lob
    @Column(name = "Descripcion_tipo")
    private String descripciontipo;
    @Lob
    @Column(name = "Modalidad")
    private String modalidad;
    @Column(name = "Duracion")
    private String duracion;
    @Lob
    @Column(name = "Titulo")
    private String titulo;
    @Column(name = "Registro_calificado")
    private String registrocalificado;
    @Column(name = "SNIES")
    private String snies;
    @Lob
    @Column(name = "Director")
    private String director;
    @Lob
    @Column(name = "email")
    private String email;
    @Column(name = "rowid")
    private String rowid;
    @JoinColumn(name = "rowid_pensum", referencedColumnName = "rowid")
    @ManyToOne
    private Pensum rowidPensum;
    @OneToMany(mappedBy = "rowidPrograma")
    private List<Facultad> facultadList;

    public Programa() {
    }

    public Programa(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcionprograma() {
        return descripcionprograma;
    }

    public void setDescripcionprograma(String descripcionprograma) {
        this.descripcionprograma = descripcionprograma;
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

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getRegistrocalificado() {
        return registrocalificado;
    }

    public void setRegistrocalificado(String registrocalificado) {
        this.registrocalificado = registrocalificado;
    }

    public String getSnies() {
        return snies;
    }

    public void setSnies(String snies) {
        this.snies = snies;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRowid() {
        return rowid;
    }

    public void setRowid(String rowid) {
        this.rowid = rowid;
    }

    public Pensum getRowidPensum() {
        return rowidPensum;
    }

    public void setRowidPensum(Pensum rowidPensum) {
        this.rowidPensum = rowidPensum;
    }

    @XmlTransient
    public List<Facultad> getFacultadList() {
        return facultadList;
    }

    public void setFacultadList(List<Facultad> facultadList) {
        this.facultadList = facultadList;
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
        if (!(object instanceof Programa)) {
            return false;
        }
        Programa other = (Programa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Programa[ id=" + id + " ]";
    }
    
}
