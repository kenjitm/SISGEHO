/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import entity.ModalidadPrograma;
import entity.Sede;
import entity.TipoPrograma;
import java.util.List;
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
 * @author SougiroHylian
 */
@ManagedBean
@ViewScoped //INDISPENSABLE PONER ESTA ANOTACIÓN EN VEZ DEL REQUESTSCOPED
public class modalidadProgramaBean {
    private ModalidadPrograma modal;
    private ModalidadPrograma modalSearch;
    //INDISPENSABLE ESTA VARIABLE CON EL ALCANCE ESTÁTICO
    private static List<ModalidadPrograma> modalList;

    public ModalidadPrograma getModal() {
        return modal;
    }

    public void setModal(ModalidadPrograma modal) {
        this.modal = modal;
    }

    public List<ModalidadPrograma> getModalList() {
        return modalList;
    }
    public modalidadProgramaBean(){
        obtenerModalidad();
    }
    //EL MÉTODO DEBE QUEDAR ASÍ MISMO
    private void obtenerModalidad() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<ModalidadPrograma> q = em.createNamedQuery("ModalidadPrograma.findAll", ModalidadPrograma.class);
        modalList = q.getResultList();
    }
    public void buscarModalidadPorId(Integer id) {
        modalSearch = buscarById(id);
    }

    public ModalidadPrograma buscarById(Integer id) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<ModalidadPrograma> modalidad = em.createNamedQuery("ModalidadPrograma.findById", ModalidadPrograma.class);
        modalidad.setParameter("id", id);
        return (modalidad.getResultList().isEmpty())?  null : modalidad.getResultList().get(0);
    }
    @FacesConverter(forClass = ModalidadPrograma.class)
    public static class tipoProgramaBeanConverter implements Converter {

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
            return ((modalidadProgramaBean) context.getApplication().evaluateExpressionGet(context, "#{" + "modalidadProgramaBean" + "}", modalidadProgramaBean.class)).buscarById(getKey(value));
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object value) {
            if (value == null) {
                return null;
            } else if (value instanceof ModalidadPrograma) {
                return getStringKey(((ModalidadPrograma) value).getId());
            } else {
                throw new IllegalArgumentException("object " + value + " is of type " + value.getClass().getName() + "; expected type: " + Sede.class.getName());
            }
        }

    }
}
