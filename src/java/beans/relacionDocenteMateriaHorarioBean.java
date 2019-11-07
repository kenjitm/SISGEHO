/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.RelacionDocenteHorarioMateria;
import entity.Usuario;
import entity.Sede;
import entity.Asignacion;
import entity.AsignacionGrupos;
import entity.Grupo;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.AjaxBehaviorEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 *
 * @author SougiroHylian
 */
@ManagedBean
@ViewScoped //INDISPENSABLE PONER ESTA ANOTACIÓN EN VEZ DEL REQUESTSCOPED
public class relacionDocenteMateriaHorarioBean implements Serializable {

    private RelacionDocenteHorarioMateria relacion;
    private RelacionDocenteHorarioMateria relacionSearch;
    //INDISPENSABLE ESTA VARIABLE CON EL ALCANCE ESTÁTICO
    private static List<RelacionDocenteHorarioMateria> relacionesList;
    private int id;
    private Asignacion horarios;
    private Grupo grupo;
    private Asignacion asig;
    private AsignacionGrupos asigGrup;
    //INDISPENSABLE ESTA VARIABLE CON EL ALCANCE ESTÁTICO
    private static List<AsignacionGrupos> asigGrupList;

    public RelacionDocenteHorarioMateria getRelacion() {
        return relacion;
    }

    public List<AsignacionGrupos> getAsigGrupList() {
        return asigGrupList;
    }

    public AsignacionGrupos getAsigGrup() {
        return asigGrup;
    }

    public void setAsigGrup(AsignacionGrupos asigGrup) {
        this.asigGrup = asigGrup;
    }

    public Asignacion getAsig() {
        return asig;
    }

    public void setAsig(Asignacion asig) {
        this.asig = asig;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Asignacion getHorarios() {
        return horarios;
    }

    public void setHorarios(Asignacion horarios) {
        this.horarios = horarios;
    }

    public void setRelacion(RelacionDocenteHorarioMateria relacion) {
        this.relacion = relacion;
    }

    public List<RelacionDocenteHorarioMateria> getRelacionesList() {
        return relacionesList;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public relacionDocenteMateriaHorarioBean() throws ClassNotFoundException, SQLException {
        relacion = new RelacionDocenteHorarioMateria();
        horarios = new Asignacion();
        relacionDMH();
        GlobalBean globalBean = new GlobalBean();
        String rol = globalBean.getObjectFromSession("roles").toString();
        if ("BINESTAR".equals(rol)) {
            relacion.setShow(true);
        }
        try
        {
           asigGrupList = new ArrayList<AsignacionGrupos>();
        asigGrup = new AsignacionGrupos();
        grupo = new Grupo();
        asig = new Asignacion();
        asigGrup.setRowidGrupo(grupo);
        asigGrup.setRowidAsignacion(asig);
        asigGrup.setCantidadEstudiantes(0);
        asigGrupList.add(asigGrup); 
        }catch(Exception ex)
        {
            System.out.println("Error: "+ex.getMessage());
        }
        
    }

    public void addGrupo() {
        AsignacionGrupos asigGrupAdd = new AsignacionGrupos();
        grupo = new Grupo();
        asig = new Asignacion();
        asigGrupAdd.setRowidGrupo(grupo);
        asigGrupAdd.setRowidAsignacion(asig);
        asigGrupAdd.setCantidadEstudiantes(0);
        asigGrupList.add(asigGrupAdd);
    }

    public void relacionDMH() throws ClassNotFoundException, SQLException {

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

        //List<RelacionDocenteHorarioMateria> relaciones = new ArrayList<RelacionDocenteHorarioMateria>();
        /*PreparedStatement pstmt = connect
				.prepareStatement("SELECT CONCAT( CONCAT(Dia, \" \", hora_inicio), \" a \", hora_fin) AS jornada"+
                                        ", (SELECT 'MATERIA' FROM horario WHERE Dia = 'Lunes') AS Lunes FROM horario");*/
        try {
            String Query = "SELECT CONCAT(hora_inicio, \" a \", hora_fin) AS jornada"
                    + ", (SELECT CONCAT(CONCAT(a.descripcion, \"-\",g.Nomenclatura),\"-\",r.nomenclatura) FROM horario as h "
                    + "INNER JOIN horario_asignado as hasig on hasig.rowid_horario = h.id "
                    + "INNER JOIN grupo as g on g.id = hasig.rowid_grupo_asignatura "
                    + "INNER JOIN grupo_asignatura_p gasig on gasig.rowid_grupo = g.id "
                    + "INNER JOIN asignatura_docente dasig on dasig.rowid_docente = gasig.id "
                    + "INNER JOIN asignatura a on a.id = dasig.rowid_asignatura "
                    + "INNER JOIN recurso r on r.id = hasig.rowid_recurso "
                    + "WHERE Dia = 'LUNES') AS Lunes "
                    + ", (SELECT CONCAT(CONCAT(a.descripcion, \"-\",g.Nomenclatura),\"-\",r.nomenclatura) FROM horario as h "
                    + "INNER JOIN horario_asignado as hasig on hasig.rowid_horario = h.id "
                    + "INNER JOIN grupo as g on g.id = hasig.rowid_grupo_asignatura "
                    + "INNER JOIN grupo_asignatura_p gasig on gasig.rowid_grupo = g.id "
                    + "INNER JOIN asignatura_docente dasig on dasig.rowid_docente = gasig.id "
                    + "INNER JOIN asignatura a on a.id = dasig.rowid_asignatura "
                    + "INNER JOIN recurso r on r.id = hasig.rowid_recurso "
                    + "WHERE Dia = 'MARTES') AS Martes" + ", (SELECT CONCAT(CONCAT(a.descripcion, \"-\",g.Nomenclatura),\"-\",r.nomenclatura) FROM horario as h "
                    + "INNER JOIN horario_asignado as hasig on hasig.rowid_horario = h.id "
                    + "INNER JOIN grupo as g on g.id = hasig.rowid_grupo_asignatura "
                    + "INNER JOIN grupo_asignatura_p gasig on gasig.rowid_grupo = g.id "
                    + "INNER JOIN asignatura_docente dasig on dasig.rowid_docente = gasig.id "
                    + "INNER JOIN asignatura a on a.id = dasig.rowid_asignatura "
                    + "INNER JOIN recurso r on r.id = hasig.rowid_recurso "
                    + "WHERE Dia = 'MIERCOLES') AS Miercoles" + ", (SELECT CONCAT(CONCAT(a.descripcion, \"-\",g.Nomenclatura),\"-\",r.nomenclatura) FROM horario as h "
                    + "INNER JOIN horario_asignado as hasig on hasig.rowid_horario = h.id "
                    + "INNER JOIN grupo as g on g.id = hasig.rowid_grupo_asignatura "
                    + "INNER JOIN grupo_asignatura_p gasig on gasig.rowid_grupo = g.id "
                    + "INNER JOIN asignatura_docente dasig on dasig.rowid_docente = gasig.id "
                    + "INNER JOIN asignatura a on a.id = dasig.rowid_asignatura "
                    + "INNER JOIN recurso r on r.id = hasig.rowid_recurso "
                    + "WHERE Dia = 'JUEVES') AS Jueves" + ", (SELECT CONCAT(CONCAT(a.descripcion, \"-\",g.Nomenclatura),\"-\",r.nomenclatura) FROM horario as h "
                    + "INNER JOIN horario_asignado as hasig on hasig.rowid_horario = h.id "
                    + "INNER JOIN grupo as g on g.id = hasig.rowid_grupo_asignatura "
                    + "INNER JOIN grupo_asignatura_p gasig on gasig.rowid_grupo = g.id "
                    + "INNER JOIN asignatura_docente dasig on dasig.rowid_docente = gasig.id "
                    + "INNER JOIN asignatura a on a.id = dasig.rowid_asignatura "
                    + "INNER JOIN recurso r on r.id = hasig.rowid_recurso "
                    + "WHERE Dia = 'VIERNES') AS Viernes" + ", (SELECT CONCAT(CONCAT(a.descripcion, \"-\",g.Nomenclatura),\"-\",r.nomenclatura) FROM horario as h "
                    + "INNER JOIN horario_asignado as hasig on hasig.rowid_horario = h.id "
                    + "INNER JOIN grupo as g on g.id = hasig.rowid_grupo_asignatura "
                    + "INNER JOIN grupo_asignatura_p gasig on gasig.rowid_grupo = g.id "
                    + "INNER JOIN asignatura_docente dasig on dasig.rowid_docente = gasig.id "
                    + "INNER JOIN asignatura a on a.id = dasig.rowid_asignatura "
                    + "INNER JOIN recurso r on r.id = hasig.rowid_recurso "
                    + "WHERE Dia = 'SABADO') AS Sabado"
                    + " FROM horario";
            PreparedStatement pstmt = connect
                    .prepareStatement(Query);
            System.out.println("***********Consulta: " + Query);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("***********Inicia recorrido");
            relacionesList = new ArrayList<RelacionDocenteHorarioMateria>();
            while (rs.next()) {

                relacion = new RelacionDocenteHorarioMateria();
                //objRelacion.setId(rs.getInt("id"));
                relacion.setIdDocente(0);
                relacion.setIdHorario(0);
                relacion.setIdMateria(0);
                relacion.setJornada(rs.getString("jornada"));
                if (rs.getString("Lunes") != null) {
                    relacion.setLunes(rs.getString("Lunes"));
                } else {
                    relacion.setLunes("");
                }
                if (rs.getString("Martes") != null) {
                    relacion.setMartes(rs.getString("Martes"));
                } else {
                    relacion.setMartes("");
                }
                if (rs.getString("Miercoles") != null) {
                    relacion.setMiercoles(rs.getString("Miercoles"));
                } else {
                    relacion.setMiercoles("");
                }
                if (rs.getString("Jueves") != null) {
                    relacion.setJueves(rs.getString("Jueves"));
                } else {
                    relacion.setJueves("");
                }
                if (rs.getString("Viernes") != null) {
                    relacion.setViernes(rs.getString("Viernes"));
                } else {
                    relacion.setViernes("");
                }
                if (rs.getString("Sabado") != null) {
                    relacion.setViernes(rs.getString("Sabado"));
                } else {
                    relacion.setSabado("");
                }
                //objRelacion.setSabado(rs.getString("Sabado"));
                relacionesList.add(relacion);

            }

            // close resources
            rs.close();
            pstmt.close();
            connect.close();
        } catch (Exception ex) {
            System.out.println("in Sql consult");
            System.out.println(ex.getMessage());
        }
        //return relaciones;

    }

    public void guardarAsignacion() {
        try {
            EntityManagerFactory emf
                    = Persistence.createEntityManagerFactory("SisgehoPU");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(horarios);
            em.getTransaction().commit();
            em.close();
            em = emf.createEntityManager();
            for (AsignacionGrupos grups : asigGrupList) {
                grups.setRowidAsignacion(horarios);
                em.getTransaction().begin();
                em.persist(grups);
                em.getTransaction().commit();
                em.close();
            }
            horarios = new Asignacion();
            relacionDMH();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "REALIZADO CON ÉXITO", "Se guardó correctamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "NO SE PUDO REALIZAR", "No se pudo guardar los datos. Inténtelo más tarde");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void buscarRelacionPorId(Integer id) {
        relacionSearch = buscarById(id);
    }

    public RelacionDocenteHorarioMateria buscarById(Integer id) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<RelacionDocenteHorarioMateria> relaciones = em.createNamedQuery("RelacionDocenteHorarioMateria.findById", RelacionDocenteHorarioMateria.class);
        relaciones.setParameter("id", id);
        return (relaciones.getResultList().isEmpty()) ? null : relaciones.getResultList().get(0);
    }

    @FacesConverter(forClass = RelacionDocenteHorarioMateria.class)
    public static class relacionDocenteMateriaHorarioBeanConverter implements Converter {

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
            return ((relacionDocenteMateriaHorarioBean) context.getApplication().evaluateExpressionGet(context, "#{" + "relacionDocenteMateriaHorarioBean" + "}", relacionDocenteMateriaHorarioBean.class)).buscarById(getKey(value));
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object value) {
            if (value == null) {
                return null;
            } else if (value instanceof RelacionDocenteHorarioMateria) {
                return getStringKey(((RelacionDocenteHorarioMateria) value).getId());
            } else {
                throw new IllegalArgumentException("object " + value + " is of type " + value.getClass().getName() + "; expected type: " + RelacionDocenteHorarioMateria.class.getName());
            }
        }

    }
}
