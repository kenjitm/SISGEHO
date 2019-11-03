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
@Table(name = "programa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Programa.findAll", query = "SELECT p FROM Programa p"),
    @NamedQuery(name = "Programa.findById", query = "SELECT p FROM Programa p WHERE p.id = :id"),
    @NamedQuery(name = "Programa.findByNombre", query = "SELECT p FROM Programa p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Programa.findByDescripcion", query = "SELECT p FROM Programa p WHERE p.descripcion = :descripcion"),
    @NamedQuery(name = "Programa.findByDuracion", query = "SELECT p FROM Programa p WHERE p.duracion = :duracion"),
    @NamedQuery(name = "Programa.findByRegistroCalificado", query = "SELECT p FROM Programa p WHERE p.registroCalificado = :registroCalificado"),
    @NamedQuery(name = "Programa.findBySnies", query = "SELECT p FROM Programa p WHERE p.snies = :snies"),
    @NamedQuery(name = "Programa.findByDirector", query = "SELECT p FROM Programa p WHERE p.director = :director"),
    @NamedQuery(name = "Programa.findByEmail", query = "SELECT p FROM Programa p WHERE p.email = :email"),
    @NamedQuery(name = "Programa.findByJornada", query = "SELECT p FROM Programa p WHERE p.jornada = :jornada")})
public class Programa implements Serializable {

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
    @Column(name = "duracion")
    private String duracion;
    @Column(name = "registro_calificado")
    private String registroCalificado;
    @Column(name = "SNIES")
    private String snies;
    @Basic(optional = false)
    @Column(name = "director")
    private String director;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Column(name = "jornada")
    private String jornada;
    @JoinColumn(name = "rowid_facultad", referencedColumnName = "id")
    @ManyToOne(optional = true)
    private Facultad rowidFacultad;
    @JoinColumn(name = "rowid_tipo", referencedColumnName = "id")
    @ManyToOne(optional = true)
    private TipoPrograma rowidTipo;
    @JoinColumn(name = "rowid_modalidad", referencedColumnName = "id")
    @ManyToOne(optional = true)
    private ModalidadPrograma rowidModalidad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rowidPrograma")
    private Collection<Asignatura> asignaturaCollection;
    
    //Atributo para poder renderizar los campos de editar en la tabla
    //Ponerlo como Transient para que no afecte los querys, ya que es un campo que no existe en la DB
    @Transient
    private boolean editable;

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public Programa() {
        editable = false;
    }

    public Programa(Integer id) {
        this.id = id;
    }

    public Programa(Integer id, String nombre, String descripcion, String duracion, String director, String email, String jornada) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.duracion = duracion;
        this.director = director;
        this.email = email;
        this.jornada = jornada;
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

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getRegistroCalificado() {
        return registroCalificado;
    }

    public void setRegistroCalificado(String registroCalificado) {
        this.registroCalificado = registroCalificado;
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

    public String getJornada() {
        return jornada;
    }

    public void setJornada(String jornada) {
        this.jornada = jornada;
    }

    @XmlTransient
    public Collection<Asignatura> getAsignaturaCollection() {
        return asignaturaCollection;
    }

    public void setAsignaturaCollection(Collection<Asignatura> asignaturaCollection) {
        this.asignaturaCollection = asignaturaCollection;
    }

    public Facultad getRowidFacultad() {
        return rowidFacultad;
    }

    public void setRowidFacultad(Facultad rowidFacultad) {
        this.rowidFacultad = rowidFacultad;
    }

    public TipoPrograma getRowidTipo() {
        return rowidTipo;
    }

    public void setRowidTipo(TipoPrograma rowidTipo) {
        this.rowidTipo = rowidTipo;
    }

    public ModalidadPrograma getRowidModalidad() {
        return rowidModalidad;
    }

    public void setRowidModalidad(ModalidadPrograma rowidModalidad) {
        this.rowidModalidad = rowidModalidad;
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
        return nombre;
    }
    
}
