/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import entity.Pensum;
import entity.Sede;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author Raul A. Hernandez
 */
@ManagedBean
@RequestScoped
public class PensumBean {

    private Pensum pensum;
    private Pensum pensumSearch;
    //INDISPENSABLE ESTA VARIABLE CON EL ALCANCE ESTÁTICO
    private static List<Pensum> pensumList;
    public PensumBean() {
        pensum = new Pensum();
        pensumSearch = new Pensum();
        consultarPensums();
    }

    public Pensum getPensum() {
        return pensum;
    }

    public void setPensum(Pensum pensum) {
        this.pensum = pensum;
    }

    public Pensum getPensumSearch() {
        return pensumSearch;
    }

    public void setPensumSearch(Pensum pensumSearch) {
        this.pensumSearch = pensumSearch;
    }

    public  List<Pensum> getPensumList() {
        return pensumList;
    }
    public String irPendum() {
        return "gestionPensum.xhtml";
    }
    public List<Pensum> consultarPensums() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();

        TypedQuery<Pensum> consultaPensums = em.createNamedQuery("Pensum.findAll", Pensum.class);
        pensumList = consultaPensums.getResultList();
        return consultaPensums.getResultList();
    }
    
   /* public Pensum buscarPesumById(Integer id) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        pensum = em.find(Pensum.class, id);
        return pensum;
    }*/
    public void buscarPensumPorId(Integer id) {
        pensumSearch = buscarById(id);
    }

    public Pensum buscarById(Integer id) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<Pensum> pen = em.createNamedQuery("Pensum.findById", Pensum.class);
        pen.setParameter("id", id);
        return (pen.getResultList().isEmpty())?  null : pen.getResultList().get(0);
    }
     //EL MÉTODO DEBE QUEDAR ASÍ MISMO
    public void guardarPensum() {
        try {
            EntityManagerFactory emf
                    = Persistence.createEntityManagerFactory("SisgehoPU");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(pensum);
            em.getTransaction().commit();
            em.close();
            pensum = new Pensum();
            consultarPensums(); //Actualiza lista
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "REALIZADO CON ÉXITO", "Se guardó correctamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "NO SE PUDO REALIZAR", "No se pudo guardar los datos. Inténtelo más tarde");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    //Agregar este método para campos booleanos, como "activo"
    public String transformActivo(Boolean activo) {
        return (activo) ? "ACTIVA" : "INACTIVA";
    }

    //INDISPENSABLE tener este método
    public void enableEditarOption(Pensum pen, boolean estado) {
        pen.setEditable(estado);
    }
    //EL MÉTODO DEBE QUEDAR ASÍ MISMO
    public void editarPensum(Pensum p) {
        try {
            EntityManagerFactory emf
                    = Persistence.createEntityManagerFactory("SisgehoPU");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.merge(pensum);
            em.getTransaction().commit();
            em.close();
            consultarPensums(); //Actualiza lista
            p.setEditable(false);
            pensum = new Pensum();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "REALIZADO CON ÉXITO", "Se actualizó correctamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "NO SE PUDO REALIZAR", "No se pudo editar los datos. Inténtelo más tarde");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    //EL MÉTODO DEBE QUEDAR ASÍ MISMO
    public void eliminarPensum() {
        try {
            EntityManagerFactory emf
                    = Persistence.createEntityManagerFactory("SisgehoPU");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            if (!em.contains(pensum)) {
                pensum = em.merge(pensum);
            }
            em.remove(pensum);
            em.getTransaction().commit();
            consultarPensums(); //Actualiza lista
            pensum = new Pensum();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "REALIZADO CON ÉXITO", "Se eliminó correctamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "NO SE PUDO REALIZAR", "No se pudo eliminar los datos. Inténtelo más tarde");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    @FacesConverter(forClass = Pensum.class)
    public static class PensumBeanConverter implements Converter {

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
            return ((PensumBean) context.getApplication().evaluateExpressionGet(context, "#{" + "pensumBean" + "}", PensumBean.class)).buscarById(getKey(value));
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object value) {
            if (value == null) {
                return null;
            } else if (value instanceof Pensum) {
                return getStringKey(((Pensum) value).getId());
            } else {
                throw new IllegalArgumentException("object " + value + " is of type " + value.getClass().getName() + "; expected type: " + Pensum.class.getName());
            }
        }

    }
    
}
