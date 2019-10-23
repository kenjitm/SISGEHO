/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Horario;
import entity.TipoDia;
import entity.TipoFrecuencia;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author Raul A. Hernandez
 */
@ManagedBean
@RequestScoped
public class HorarioBean {

    private Horario horario; //Falta llenar

    public TipoDia[] getTipoDias() {
        return TipoDia.values();
    }

    public TipoFrecuencia[] getTipoFrecuencias() {
        return TipoFrecuencia.values();
    }
    
    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    /**
     * Creates a new instance of HorarioBean
     */
    public HorarioBean() {
        horario = new Horario();
    }
    public String irHorario() {
        return "horarios.xhtml";
    }
    public List<Horario> obtenerHorarios() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<Horario> q = em.createNamedQuery("Horario.findAll", Horario.class);
        return q.getResultList();
    }

    public Horario buscarHorario(Long id) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<Horario> q = em.createNamedQuery("Horario.findById", Horario.class);
        q.setParameter("id", id);
        return q.getResultList().get(0);
    }
    
    public Horario buscarPorId(Integer id) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<Horario> sedes = em.createNamedQuery("Horario.findById", Horario.class);
        sedes.setParameter("id", id);
        return sedes.getResultList().get(0);
    }

    public void guardarHorario() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        //horario.setRowid(horario.getId().toString());
        em.getTransaction().begin();
        em.persist(horario);
        em.getTransaction().commit();
        horario = new Horario();
    }
 
    @FacesConverter(forClass = Horario.class)
    public static class HorarioBeanConverter implements Converter {

        Integer getKey(String value) {
            return Integer.valueOf(value);
        }

        String getStringKey(Integer value) {
            return new StringBuilder().append(value).toString();
        }

        @Override
        public Object getAsObject(FacesContext context, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            return ((HorarioBean) context.getApplication().evaluateExpressionGet(context, "#{" + "horarioBean" + "}", HorarioBean.class)).buscarPorId(getKey(value));
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object value) {
            if (value == null) {
                return null;
            } else if (value instanceof Horario) {
                return getStringKey(((Horario) value).getId());
            } else {
                throw new IllegalArgumentException("object " + value + " is of type " + value.getClass().getName() + "; expected type: " + Horario.class.getName());
            }
        }

    }
    
}
