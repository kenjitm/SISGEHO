/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Asignacion;
import entity.AsignacionGrupos;
import entity.Grupo;
import entity.RelacionDocenteHorarioMateria;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

/**
 *
 * @author IngenieroDesarrollo
 */
@ManagedBean
@ViewScoped //INDISPENSABLE PONER ESTA ANOTACIÓN EN VEZ DEL REQUESTSCOPED
public class ReportesBean {

    private int id;
    private Asignacion horarios;
    private Grupo grupo;
    private Asignacion asig;
    private AsignacionGrupos asigGrup;
    //INDISPENSABLE ESTA VARIABLE CON EL ALCANCE ESTÁTICO
    private static List<AsignacionGrupos> asigGrupList;
    private RelacionDocenteHorarioMateria relacion;
    //INDISPENSABLE ESTA VARIABLE CON EL ALCANCE ESTÁTICO
    private static List<RelacionDocenteHorarioMateria> relacionesList;
    private int idPeriodo;
    private int idDocente;
    private int idGrupo;
    private int idAsignatura;
    private int idDia;

    private String condicionReporte = "";

    public String irReportes() {
        return "reportes.xhtml";
    }

    public Asignacion getAsig() {
        return asig;
    }

    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public int getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(int idDocente) {
        this.idDocente = idDocente;
    }

    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    public int getIdAsignatura() {
        return idAsignatura;
    }

    public void setIdAsignatura(int idAsignatura) {
        this.idAsignatura = idAsignatura;
    }

    public int getIdDia() {
        return idDia;
    }

    public void setIdDia(int idDia) {
        this.idDia = idDia;
    }

    public void setAsig(Asignacion asig) {
        this.asig = asig;
    }

    public RelacionDocenteHorarioMateria getRelacion() {
        return relacion;
    }

    public void setRelacion(RelacionDocenteHorarioMateria relacion) {
        this.relacion = relacion;
    }

    public AsignacionGrupos getAsigGrup() {
        return asigGrup;
    }

    public void setAsigGrup(AsignacionGrupos asigGrup) {
        this.asigGrup = asigGrup;
    }

    public List<AsignacionGrupos> getAsigGrupList() {
        return asigGrupList;
    }

    public List<RelacionDocenteHorarioMateria> getRelacionesList() {
        return relacionesList;
    }

    public ReportesBean() throws ClassNotFoundException, SQLException {
        generacionReporte();
    }

    public void generacionReporte() throws ClassNotFoundException, SQLException {

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

            String Query = "SELECT CONCAT(horaIn.descripcion, \" a \", horaFi.descripcion) AS jornada,\n"
                    + "(SELECT CONCAT(CONCAT(CONCAT(CONCAT(mat.codigo, \"-\", mat.descripcion),\"-\",doc.nombre),\" \",doc.apellido), \"-\",per.descripcion) as asignacion \n"
                    + "FROM asignacion as asig\n"
                    + "LEFT OUTER JOIN asignatura as mat on mat.id = asig.rowid_asignatura\n"
                    + "LEFT OUTER JOIN docente as doc on doc.id = asig.rowid_docente\n"
                    + "LEFT OUTER JOIN periodo as per on per.id = asig.rowid_periodo\n"
                    + "LEFT OUTER JOIN hora as h on h.id = asig.rowid_dia\n"
                    + "LEFT OUTER JOIN dia as d on d.id = asig.rowid_dia\n"
                    + "LEFT OUTER JOIN sede as se on se.id = asig.rowid_sede\n"
                    + "LEFT OUTER JOIN recurso as rec on rec.id = asig.rowid_recurso\n"
                    + "WHERE d.descripcion = \"Lunes\" and asig.id = asignaciones.id) as Lunes,\n"
                    + "(SELECT CONCAT(CONCAT(CONCAT(CONCAT(mat.codigo, \"-\", mat.descripcion),\"-\",doc.nombre),\" \",doc.apellido), \"-\",per.descripcion) as asignacion \n"
                    + "FROM asignacion as asig\n"
                    + "LEFT OUTER JOIN asignatura as mat on mat.id = asig.rowid_asignatura\n"
                    + "LEFT OUTER JOIN docente as doc on doc.id = asig.rowid_docente\n"
                    + "LEFT OUTER JOIN periodo as per on per.id = asig.rowid_periodo\n"
                    + "LEFT OUTER JOIN hora as h on h.id = asig.rowid_dia\n"
                    + "LEFT OUTER JOIN dia as d on d.id = asig.rowid_dia\n"
                    + "LEFT OUTER JOIN sede as se on se.id = asig.rowid_sede\n"
                    + "LEFT OUTER JOIN recurso as rec on rec.id = asig.rowid_recurso\n"
                    + "WHERE d.descripcion = \"Martes\" and asig.id = asignaciones.id) as Martes,\n"
                    + "(SELECT CONCAT(CONCAT(CONCAT(CONCAT(mat.codigo, \"-\", mat.descripcion),\"-\",doc.nombre),\" \",doc.apellido), \"-\",per.descripcion) as asignacion \n"
                    + "FROM asignacion as asig\n"
                    + "LEFT OUTER JOIN asignatura as mat on mat.id = asig.rowid_asignatura\n"
                    + "LEFT OUTER JOIN docente as doc on doc.id = asig.rowid_docente\n"
                    + "LEFT OUTER JOIN periodo as per on per.id = asig.rowid_periodo\n"
                    + "LEFT OUTER JOIN hora as h on h.id = asig.rowid_dia\n"
                    + "LEFT OUTER JOIN dia as d on d.id = asig.rowid_dia\n"
                    + "LEFT OUTER JOIN sede as se on se.id = asig.rowid_sede\n"
                    + "LEFT OUTER JOIN recurso as rec on rec.id = asig.rowid_recurso\n"
                    + "WHERE d.descripcion = \"Miercoles\" and asig.id = asignaciones.id) as Miercoles,\n"
                    + "(SELECT CONCAT(CONCAT(CONCAT(CONCAT(mat.codigo, \"-\", mat.descripcion),\"-\",doc.nombre),\" \",doc.apellido), \"-\",per.descripcion) as asignacion \n"
                    + "FROM asignacion as asig\n"
                    + "LEFT OUTER JOIN asignatura as mat on mat.id = asig.rowid_asignatura\n"
                    + "LEFT OUTER JOIN docente as doc on doc.id = asig.rowid_docente\n"
                    + "LEFT OUTER JOIN periodo as per on per.id = asig.rowid_periodo\n"
                    + "LEFT OUTER JOIN hora as h on h.id = asig.rowid_dia\n"
                    + "LEFT OUTER JOIN dia as d on d.id = asig.rowid_dia\n"
                    + "LEFT OUTER JOIN sede as se on se.id = asig.rowid_sede\n"
                    + "LEFT OUTER JOIN recurso as rec on rec.id = asig.rowid_recurso\n"
                    + "WHERE d.descripcion = \"Jueves\" and asig.id = asignaciones.id) as Jueves,\n"
                    + "(SELECT CONCAT(CONCAT(CONCAT(CONCAT(mat.codigo, \"-\", mat.descripcion),\"-\",doc.nombre),\" \",doc.apellido), \"-\",per.descripcion) as asignacion \n"
                    + "FROM asignacion as asig\n"
                    + "LEFT OUTER JOIN asignatura as mat on mat.id = asig.rowid_asignatura\n"
                    + "LEFT OUTER JOIN docente as doc on doc.id = asig.rowid_docente\n"
                    + "LEFT OUTER JOIN periodo as per on per.id = asig.rowid_periodo\n"
                    + "LEFT OUTER JOIN hora as h on h.id = asig.rowid_dia\n"
                    + "LEFT OUTER JOIN dia as d on d.id = asig.rowid_dia\n"
                    + "LEFT OUTER JOIN sede as se on se.id = asig.rowid_sede\n"
                    + "LEFT OUTER JOIN recurso as rec on rec.id = asig.rowid_recurso\n"
                    + "WHERE d.descripcion = \"Viernes\" and asig.id = asignaciones.id) as Viernes,\n"
                    + "(SELECT CONCAT(CONCAT(CONCAT(CONCAT(mat.codigo, \"-\", mat.descripcion),\"-\",doc.nombre),\" \",doc.apellido), \"-\",per.descripcion) as asignacion \n"
                    + "FROM asignacion as asig\n"
                    + "LEFT OUTER JOIN asignatura as mat on mat.id = asig.rowid_asignatura\n"
                    + "LEFT OUTER JOIN docente as doc on doc.id = asig.rowid_docente\n"
                    + "LEFT OUTER JOIN periodo as per on per.id = asig.rowid_periodo\n"
                    + "LEFT OUTER JOIN hora as h on h.id = asig.rowid_dia\n"
                    + "LEFT OUTER JOIN dia as d on d.id = asig.rowid_dia\n"
                    + "LEFT OUTER JOIN sede as se on se.id = asig.rowid_sede\n"
                    + "LEFT OUTER JOIN recurso as rec on rec.id = asig.rowid_recurso\n"
                    + "WHERE d.descripcion = \"Sabado\" and asig.id = asignaciones.id) as Sabado\n"
                    + ", asignaciones.id "
                    + "FROM asignacion as asignaciones\n"
                    + "INNER JOIN hora as horaIn on horaIn.id = asignaciones.rowid_hora\n"
                    + "INNER JOIN hora as horaFi  on horaFi.id = asignaciones.rowid_hora_final "
                    + "WHERE COALESCE(asignaciones.rowid_recurso, 0)  > 0";
            PreparedStatement pstmt = connect
                    .prepareStatement(Query);
            System.out.println("***********Consulta: " + Query);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("***********Inicia recorrido");
            relacionesList = new ArrayList<RelacionDocenteHorarioMateria>();
            while (rs.next()) {

                relacion = new RelacionDocenteHorarioMateria();

                relacion.setIdDocente(0);
                relacion.setIdHorario(0);
                relacion.setIdMateria(0);
                relacion.setId(rs.getInt("id"));
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
                    relacion.setSabado(rs.getString("Sabado"));
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

    public void filtroReporte() throws ClassNotFoundException {

        String condicion = "";
        if (idPeriodo != 0) {
            condicion = " and asignaciones.rowid_periodo = " + idPeriodo;
            condicionReporte = condicion;
        }
        if (idAsignatura != 0) {
            if ("".equals(condicion)) {
                condicion += " and asignaciones.rowid_asignatura = " + idAsignatura;
            } else {
                condicion = " and asignaciones.rowid_asignatura = " + idAsignatura;
            }
            condicionReporte = condicion;
        }
        if (idDia != 0) {
            if ("".equals(condicion)) {
                condicion += " and asignaciones.rowid_dia = " + idDia;
            } else {
                condicion = " and asignaciones.rowid_dia = " + idDia;
            }
            condicionReporte = condicion;
        }
        if (idDocente != 0) {
            if ("".equals(condicion)) {
                condicion += " and asignaciones.rowid_docente = " + idDocente;
            } else {
                condicion = " and asignaciones.rowid_docente = " + idDocente;
            }
            condicionReporte = condicion;
        }
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

            String Query = "SELECT CONCAT(horaIn.descripcion, \" a \", horaFi.descripcion) AS jornada,\n"
                    + "(SELECT CONCAT(CONCAT(CONCAT(CONCAT(mat.codigo, \"-\", mat.descripcion),\"-\",doc.nombre),\" \",doc.apellido), \"-\",per.descripcion) as asignacion \n"
                    + "FROM asignacion as asig\n"
                    + "LEFT OUTER JOIN asignatura as mat on mat.id = asig.rowid_asignatura\n"
                    + "LEFT OUTER JOIN docente as doc on doc.id = asig.rowid_docente\n"
                    + "LEFT OUTER JOIN periodo as per on per.id = asig.rowid_periodo\n"
                    + "LEFT OUTER JOIN hora as h on h.id = asig.rowid_dia\n"
                    + "LEFT OUTER JOIN dia as d on d.id = asig.rowid_dia\n"
                    + "LEFT OUTER JOIN sede as se on se.id = asig.rowid_sede\n"
                    + "LEFT OUTER JOIN recurso as rec on rec.id = asig.rowid_recurso\n"
                    + "WHERE d.descripcion = \"Lunes\" and asig.id = asignaciones.id) as Lunes,\n"
                    + "(SELECT CONCAT(CONCAT(CONCAT(CONCAT(mat.codigo, \"-\", mat.descripcion),\"-\",doc.nombre),\" \",doc.apellido), \"-\",per.descripcion) as asignacion \n"
                    + "FROM asignacion as asig\n"
                    + "LEFT OUTER JOIN asignatura as mat on mat.id = asig.rowid_asignatura\n"
                    + "LEFT OUTER JOIN docente as doc on doc.id = asig.rowid_docente\n"
                    + "LEFT OUTER JOIN periodo as per on per.id = asig.rowid_periodo\n"
                    + "LEFT OUTER JOIN hora as h on h.id = asig.rowid_dia\n"
                    + "LEFT OUTER JOIN dia as d on d.id = asig.rowid_dia\n"
                    + "LEFT OUTER JOIN sede as se on se.id = asig.rowid_sede\n"
                    + "LEFT OUTER JOIN recurso as rec on rec.id = asig.rowid_recurso\n"
                    + "WHERE d.descripcion = \"Martes\" and asig.id = asignaciones.id) as Martes,\n"
                    + "(SELECT CONCAT(CONCAT(CONCAT(CONCAT(mat.codigo, \"-\", mat.descripcion),\"-\",doc.nombre),\" \",doc.apellido), \"-\",per.descripcion) as asignacion \n"
                    + "FROM asignacion as asig\n"
                    + "LEFT OUTER JOIN asignatura as mat on mat.id = asig.rowid_asignatura\n"
                    + "LEFT OUTER JOIN docente as doc on doc.id = asig.rowid_docente\n"
                    + "LEFT OUTER JOIN periodo as per on per.id = asig.rowid_periodo\n"
                    + "LEFT OUTER JOIN hora as h on h.id = asig.rowid_dia\n"
                    + "LEFT OUTER JOIN dia as d on d.id = asig.rowid_dia\n"
                    + "LEFT OUTER JOIN sede as se on se.id = asig.rowid_sede\n"
                    + "LEFT OUTER JOIN recurso as rec on rec.id = asig.rowid_recurso\n"
                    + "WHERE d.descripcion = \"Miercoles\" and asig.id = asignaciones.id) as Miercoles,\n"
                    + "(SELECT CONCAT(CONCAT(CONCAT(CONCAT(mat.codigo, \"-\", mat.descripcion),\"-\",doc.nombre),\" \",doc.apellido), \"-\",per.descripcion) as asignacion \n"
                    + "FROM asignacion as asig\n"
                    + "LEFT OUTER JOIN asignatura as mat on mat.id = asig.rowid_asignatura\n"
                    + "LEFT OUTER JOIN docente as doc on doc.id = asig.rowid_docente\n"
                    + "LEFT OUTER JOIN periodo as per on per.id = asig.rowid_periodo\n"
                    + "LEFT OUTER JOIN hora as h on h.id = asig.rowid_dia\n"
                    + "LEFT OUTER JOIN dia as d on d.id = asig.rowid_dia\n"
                    + "LEFT OUTER JOIN sede as se on se.id = asig.rowid_sede\n"
                    + "LEFT OUTER JOIN recurso as rec on rec.id = asig.rowid_recurso\n"
                    + "WHERE d.descripcion = \"Jueves\" and asig.id = asignaciones.id) as Jueves,\n"
                    + "(SELECT CONCAT(CONCAT(CONCAT(CONCAT(mat.codigo, \"-\", mat.descripcion),\"-\",doc.nombre),\" \",doc.apellido), \"-\",per.descripcion) as asignacion \n"
                    + "FROM asignacion as asig\n"
                    + "LEFT OUTER JOIN asignatura as mat on mat.id = asig.rowid_asignatura\n"
                    + "LEFT OUTER JOIN docente as doc on doc.id = asig.rowid_docente\n"
                    + "LEFT OUTER JOIN periodo as per on per.id = asig.rowid_periodo\n"
                    + "LEFT OUTER JOIN hora as h on h.id = asig.rowid_dia\n"
                    + "LEFT OUTER JOIN dia as d on d.id = asig.rowid_dia\n"
                    + "LEFT OUTER JOIN sede as se on se.id = asig.rowid_sede\n"
                    + "LEFT OUTER JOIN recurso as rec on rec.id = asig.rowid_recurso\n"
                    + "WHERE d.descripcion = \"Viernes\" and asig.id = asignaciones.id) as Viernes,\n"
                    + "(SELECT CONCAT(CONCAT(CONCAT(CONCAT(mat.codigo, \"-\", mat.descripcion),\"-\",doc.nombre),\" \",doc.apellido), \"-\",per.descripcion) as asignacion \n"
                    + "FROM asignacion as asig\n"
                    + "LEFT OUTER JOIN asignatura as mat on mat.id = asig.rowid_asignatura\n"
                    + "LEFT OUTER JOIN docente as doc on doc.id = asig.rowid_docente\n"
                    + "LEFT OUTER JOIN periodo as per on per.id = asig.rowid_periodo\n"
                    + "LEFT OUTER JOIN hora as h on h.id = asig.rowid_dia\n"
                    + "LEFT OUTER JOIN dia as d on d.id = asig.rowid_dia\n"
                    + "LEFT OUTER JOIN sede as se on se.id = asig.rowid_sede\n"
                    + "LEFT OUTER JOIN recurso as rec on rec.id = asig.rowid_recurso\n"
                    + "WHERE d.descripcion = \"Sabado\" and asig.id = asignaciones.id) as Sabado\n"
                    + ", asignaciones.id "
                    + "FROM asignacion as asignaciones\n"
                    + "INNER JOIN hora as horaIn on horaIn.id = asignaciones.rowid_hora\n"
                    + "INNER JOIN hora as horaFi  on horaFi.id = asignaciones.rowid_hora_final "
                    + "WHERE COALESCE(asignaciones.rowid_recurso, 0)  > 0 "
                    + condicion;
            PreparedStatement pstmt = connect
                    .prepareStatement(Query);
            System.out.println("***********Consulta: " + Query);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("***********Inicia recorrido");
            relacionesList = new ArrayList<RelacionDocenteHorarioMateria>();
            while (rs.next()) {

                relacion = new RelacionDocenteHorarioMateria();

                relacion.setIdDocente(0);
                relacion.setIdHorario(0);
                relacion.setIdMateria(0);
                relacion.setId(rs.getInt("id"));
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
                    relacion.setSabado(rs.getString("Sabado"));
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
    }

    public void generarPDF() {
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
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReportesBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/TemplatePDF/reporte.jrxml");
            String imgPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/TemplatePDF/LOGO UNIAJC.png");
            File img = new File(imgPath);
            JasperReport archivo = JasperCompileManager.compileReport(reportPath);
            Map<String, Object> params = new HashMap<>();
            //params.put("filtro", getConsulta());
            params.put("filtro", "WHERE TRUE " + condicionReporte);
            Logger.getLogger(ReportesBean.class.getName()).log(Level.INFO, condicionReporte);
            params.put("img", img.getAbsolutePath());
            JasperPrint print = JasperFillManager.fillReport(archivo, params, connect);
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline;filename=" + "reporte.pdf");
            response.getOutputStream().write(JasperExportManager.exportReportToPdf(print));
            response.flushBuffer();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "REALIZADO CON ÉXITO", "Se generó correctamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (JRException | IOException ex) {
            Logger.getLogger(ReportesBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "NO SE PUDO REALIZAR", "No se pudo generar el documento. Inténtelo más tarde");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void generarExcel() {
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
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReportesBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/TemplatePDF/reporte.jrxml");
            String imgPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/TemplatePDF/LOGO UNIAJC.png");
            File img = new File(imgPath);
            JasperReport archivo = JasperCompileManager.compileReport(reportPath);
            Map<String, Object> params = new HashMap<>();
            //params.put("filtro", getConsulta());
            params.put("filtro", "WHERE TRUE " + condicionReporte);
            Logger.getLogger(ReportesBean.class.getName()).log(Level.INFO, condicionReporte);
            params.put("img", img.getAbsolutePath());
            JasperPrint print = JasperFillManager.fillReport(archivo, params, connect);

// Mostrando el documento
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            ServletOutputStream servletOutputStream = response.getOutputStream();
            response.setHeader("Content-disposition", "attachment; filename=reporte.xls");
            response.setContentType("application/vnd.ms-excel");
//httpServletResponse.setContentLength(arrayOutputStream.toByteArray().length);

            JRXlsExporter exporterXLS = new JRXlsExporter();

            exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, print);
            exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, servletOutputStream);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            exporterXLS.exportReport();

            FacesContext.getCurrentInstance().responseComplete();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "REALIZADO CON ÉXITO", "Se generó correctamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (JRException | IOException ex) {
            Logger.getLogger(ReportesBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "NO SE PUDO REALIZAR", "No se pudo generar el documento. Inténtelo más tarde");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void buscarRelacionPorId(Integer id) {
        relacion = buscarById(id);
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
    public static class ReportesBeanConverter implements Converter {

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
            return ((ReportesBean) context.getApplication().evaluateExpressionGet(context, "#{" + "ReportesBean" + "}", ReportesBean.class)).buscarById(getKey(value));
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
