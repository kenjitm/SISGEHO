/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Asignacion;
import entity.AsignacionGrupos;
import entity.Docente;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import entity.DocenteDisponibilidad;
import entity.Jornada;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author IngenieroDesarrollo
 */
@ManagedBean
@ViewScoped //INDISPENSABLE PONER ESTA ANOTACIÓN EN VEZ DEL REQUESTSCOPED
public class DocenteDisponibilidadBean {

    private DocenteDisponibilidad disponibilidad;
    private Jornada jornada;
    //INDISPENSABLE ESTA VARIABLE CON EL ALCANCE ESTÁTICO
    private static List<Jornada> jornadaList;
    //INDISPENSABLE ESTA VARIABLE CON EL ALCANCE ESTÁTICO
    private static List<DocenteDisponibilidad> disponibilidadList;
    private boolean showDialog;
    private String gestionTipo;
    private Docente docente;

    //EL MÉTODO DEBE QUEDAR ASÍ MISMO
    private void obtenerJornadas() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<Jornada> q = em.createNamedQuery("Jornada.findAll", Jornada.class);
        jornadaList = q.getResultList();
    }

    private void obtenerDisponibilidadByDocente(int id) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<DocenteDisponibilidad> q = em.createNamedQuery("DocenteDisponibilidad.findById", DocenteDisponibilidad.class);
        q.setParameter("id", id);
        disponibilidadList = q.getResultList();
    }

    public DocenteDisponibilidadBean() {
        disponibilidad = new DocenteDisponibilidad();
        jornada = new Jornada();
        obtenerJornadas();
        docente = new Docente();
    }

    public boolean isShowDialog() {
        return showDialog;
    }

    public void setShowDialog(boolean showDialog) {
        this.showDialog = showDialog;
    }

    public String getGestionTipo() {
        return gestionTipo;
    }

    public void setGestionTipo(String gestionTipo) {
        this.gestionTipo = gestionTipo;
    }

    public List<DocenteDisponibilidad> getDisponibilidadList() {
        return disponibilidadList;
    }

    public void tablaDisponibilidad(Docente docentes) {
        setShowDialog(true);
        gestionTipo = "Disponibilidad Horaria";
        try {
            obtenerDisponibilidadByDocente(docentes.getId());
            if (disponibilidadList == null) {
                disponibilidadList = new ArrayList<>();
                for (int i = 0; i < jornadaList.size(); i++) {
                    jornada = jornadaList.get(i);
                    DocenteDisponibilidad dispon = new DocenteDisponibilidad();
                    dispon.setJornada(jornada.getDescripcion());
                    disponibilidadList.add(dispon);
                }
            }

            docente = docentes;
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public void closeDetail() {
        setShowDialog(false);
    }

    public void guardarDisponibilidad() {
        try {
            EntityManagerFactory emf
                    = Persistence.createEntityManagerFactory("SisgehoPU");
            EntityManager em = emf.createEntityManager();

            em = emf.createEntityManager();
            for (int i = 0; i < disponibilidadList.size(); i++) {
                DocenteDisponibilidad dispo = disponibilidadList.get(i);
                System.out.println("Jornada: " + String.valueOf(dispo.getJornada()));

                dispo.setRowidDocente(docente);

                em.getTransaction().begin();
                em.persist(dispo);
                em.getTransaction().commit();

                //Logger.getLogger(RelacionDocenteMateriaHorarioBean.class).log(Level.INFO, "CANTIDAD ESTUDIANTES: "+ String.valueOf(a.getCantidadEstudiantes()));
            }
            em.close();
            setShowDialog(false);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "REALIZADO CON ÉXITO", "Se guardó correctamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "NO SE PUDO REALIZAR", "No se pudo guardar los datos. Inténtelo más tarde");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
}
