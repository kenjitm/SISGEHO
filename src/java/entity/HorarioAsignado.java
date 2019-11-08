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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author IngenieroDesarrollo
 */
@Entity
@Table(name = "horario_asignado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HorarioAsignado.findAll", query = "SELECT h FROM HorarioAsignado h"),
    @NamedQuery(name = "HorarioAsignado.findById", query = "SELECT h FROM HorarioAsignado h WHERE h.id = :id")})
public class HorarioAsignado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "rowid_horario", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Horario rowidHorario;
    @JoinColumn(name = "rowid_recurso", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Recurso rowidRecurso;
    @JoinColumn(name = "rowid_grupo_asignatura", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private GrupoAsignaturaP rowidGrupoAsignatura;

    public HorarioAsignado() {
    }

    public HorarioAsignado(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Horario getRowidHorario() {
        return rowidHorario;
    }

    public void setRowidHorario(Horario rowidHorario) {
        this.rowidHorario = rowidHorario;
    }

    public Recurso getRowidRecurso() {
        return rowidRecurso;
    }

    public void setRowidRecurso(Recurso rowidRecurso) {
        this.rowidRecurso = rowidRecurso;
    }

    public GrupoAsignaturaP getRowidGrupoAsignatura() {
        return rowidGrupoAsignatura;
    }

    public void setRowidGrupoAsignatura(GrupoAsignaturaP rowidGrupoAsignatura) {
        this.rowidGrupoAsignatura = rowidGrupoAsignatura;
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
        if (!(object instanceof HorarioAsignado)) {
            return false;
        }
        HorarioAsignado other = (HorarioAsignado) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.HorarioAsignado[ id=" + id + " ]";
    }
    
}
