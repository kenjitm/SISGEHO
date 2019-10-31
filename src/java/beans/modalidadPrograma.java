/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import entity.ModalidadPrograma;
import entity.TipoPrograma;
import java.util.List;
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
public class modalidadPrograma {
    private ModalidadPrograma modal;
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
    public modalidadPrograma(){
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
}
