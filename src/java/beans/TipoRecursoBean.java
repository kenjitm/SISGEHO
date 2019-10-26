/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.List;
import entity.TipoRecurso;
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
public class TipoRecursoBean {

    /**
     * Creates a new instance of TipoRecursoBean
     */
    public TipoRecursoBean() {
    }

    public List<TipoRecurso> obtenerTiposRecurso() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<TipoRecurso> q = em.createNamedQuery("TipoRecurso.findAll", TipoRecurso.class);
        return q.getResultList();
    }

    public TipoRecurso buscarPorId(Integer id) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<TipoRecurso> sedes = em.createNamedQuery("TipoRecurso.findById", TipoRecurso.class);
        sedes.setParameter("id", id);
        return sedes.getResultList().get(0);
    }

    @FacesConverter(forClass = TipoRecurso.class)
    public static class SedeBeanConverter implements Converter {

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
            return ((TipoRecursoBean) context.getApplication().evaluateExpressionGet(context, "#{" + "tipoRecursoBean" + "}", TipoRecursoBean.class)).buscarPorId(getKey(value));
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object value) {
            if (value == null) {
                return null;
            } else if (value instanceof TipoRecurso) {
                return getStringKey(((TipoRecurso) value).getId());
            } else {
                throw new IllegalArgumentException("object " + value + " is of type " + value.getClass().getName() + "; expected type: " + TipoRecurso.class.getName());
            }
        }

    }

}
