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
import entity.RelacionDocenteHorarioMateria;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private boolean showDialogReq;
    private boolean showButton;
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

    private void obtenerDisponibilidadByDocente(int id) throws ClassNotFoundException {
        /*EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<DocenteDisponibilidad> q = em.createNamedQuery("DocenteDisponibilidad.findByDocenteId", DocenteDisponibilidad.class);
        q.setParameter("id", id);
        disponibilidadList = q.getResultList();*/
        Connection connect = null;

        String url = "jdbc:mysql://localhost:3306/uniajc";

        String username = "root";
        String password = "";

        try {

            Class.forName("com.mysql.jdbc.Driver");

            connect = DriverManager.getConnection(url, username, password);
            // System.out.println("Connection established"+connect);

        } catch (SQLException ex) {
            System.out.println("in exec");
            System.out.println(ex.getMessage());
        }
        try {
            String Query = "SELECT * FROM docente_disponibilidad WHERE rowid_docente = "+id;
            PreparedStatement pstmt = connect
                    .prepareStatement(Query);
            System.out.println("***********Consulta: " + Query);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("***********Inicia recorrido");
            disponibilidadList = new ArrayList();
            while (rs.next()) {
                DocenteDisponibilidad docDis = new DocenteDisponibilidad();
                docDis.setId(rs.getInt("id"));
                //docDis.setRowidDocente(rs.getObject("rowid_docente", Docente.class));
                docDis.setJornada(rs.getString("jornada"));
                docDis.setLunes(rs.getBoolean("Lunes"));
                docDis.setMartes(rs.getBoolean("Martes"));
                docDis.setMiercoles(rs.getBoolean("Miercoles"));
                docDis.setJueves(rs.getBoolean("Jueves"));
                docDis.setViernes(rs.getBoolean("Viernes"));
                docDis.setSabado(rs.getBoolean("Sabado"));
                disponibilidadList.add(docDis);
                
            }
            
            // close resources
            rs.close();
            pstmt.close();
            connect.close();
        } catch (Exception ex) {
            System.out.println("in Sql consult");
            System.out.println(ex.getMessage());
        }
    }
public List<DocenteDisponibilidad> disponibilidadByDocente(int id) throws ClassNotFoundException {
        /*EntityManobteneragerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<DocenteDisponibilidad> q = em.createNamedQuery("DocenteDisponibilidad.findByDocenteId", DocenteDisponibilidad.class);
        q.setParameter("id", id);
        disponibilidadList = q.getResultList();*/
        Connection connect = null;

        String url = "jdbc:mysql://localhost:3306/uniajc";

        String username = "root";
        String password = "";

        try {

            Class.forName("com.mysql.jdbc.Driver");

            connect = DriverManager.getConnection(url, username, password);
            // System.out.println("Connection established"+connect);

        } catch (SQLException ex) {
            System.out.println("in exec");
            System.out.println(ex.getMessage());
        }
        List<DocenteDisponibilidad> disponibilidadListReturn = new ArrayList();
        try {
            String Query = "SELECT * FROM docente_disponibilidad WHERE rowid_docente = "+id;
            PreparedStatement pstmt = connect
                    .prepareStatement(Query);
            System.out.println("***********Consulta: " + Query);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("***********Inicia recorrido");
            
            while (rs.next()) {
                DocenteDisponibilidad docDis = new DocenteDisponibilidad();
                docDis.setId(rs.getInt("id"));
                //docDis.setRowidDocente(rs.getObject("rowid_docente", Docente.class));
                docDis.setJornada(rs.getString("jornada"));
                docDis.setLunes(rs.getBoolean("Lunes"));
                docDis.setMartes(rs.getBoolean("Martes"));
                docDis.setMiercoles(rs.getBoolean("Miercoles"));
                docDis.setJueves(rs.getBoolean("Jueves"));
                docDis.setViernes(rs.getBoolean("Viernes"));
                docDis.setSabado(rs.getBoolean("Sabado"));
                disponibilidadListReturn.add(docDis);
                
            }
            
            // close resources
            rs.close();
            pstmt.close();
            connect.close();
            
        } catch (Exception ex) {
            System.out.println("in Sql consult");
            System.out.println(ex.getMessage());
        }
        return disponibilidadListReturn;
    }

    public boolean isShowDialogReq() {
        return showDialogReq;
    }

    public void setShowDialogReq(boolean showDialogReq) {
        this.showDialogReq = showDialogReq;
    }

    public boolean isShowButton() {
        return showButton;
    }

    public void setShowButton(boolean showButton) {
        this.showButton = showButton;
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
        
        gestionTipo = "Disponibilidad Horaria";
        disponibilidadList = new ArrayList<>();
        try {
            obtenerDisponibilidadByDocente(docentes.getId());
            if (disponibilidadList.isEmpty()) {
                
                for (int i = 0; i < jornadaList.size(); i++) {
                    jornada = jornadaList.get(i);
                    DocenteDisponibilidad dispon = new DocenteDisponibilidad();
                    dispon.setJornada(jornada.getDescripcion());
                    disponibilidadList.add(dispon);
                }
                setShowButton(true);
                setShowDialogReq(true);
                setShowDialog(false);
            }else
            {
                setShowButton(false);
                setShowDialog(true);
                setShowDialogReq(false);
            }

            docente = docentes;
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public void closeDetail() {
        setShowDialog(false);
        setShowDialogReq(false);
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
            setShowDialogReq(false);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "REALIZADO CON ÉXITO", "Se guardó correctamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "NO SE PUDO REALIZAR", "No se pudo guardar los datos. Inténtelo más tarde");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    public void disponibilidadIdentificacion()
    {
        GlobalBean global = new GlobalBean();
        int identificacion = global.getIdentificacion();
        System.out.println("*****beans.DocenteDisponibilidadBean.disponibilidadIdentificacion: Identificacion-"+identificacion);
        EntityManagerFactory emf
                    = Persistence.createEntityManagerFactory("SisgehoPU");
            EntityManager em = emf.createEntityManager();
        Docente docenteId = new Docente();
            TypedQuery<Docente> consultaDocente = em.createNamedQuery("Docente.findByIdentificacion", Docente.class);
            consultaDocente.setParameter("identificacion", identificacion);
            docenteId = consultaDocente.getResultList().get(0);
            tablaDisponibilidad(docenteId);
            //setShowDialog(true);
            
    }
    
    public String hacerVisible(boolean valor){
        if(valor){
        return "imagenes/chulo.png";
        }
        else{
        return "imagenes/x.png";
        }
    }
}
