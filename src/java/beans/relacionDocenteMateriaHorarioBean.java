/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.RelacionDocenteHorarioMateria;
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
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.context.RequestContext;

/**
 *
 * @author SougiroHylian
 */
@ManagedBean
@ViewScoped //INDISPENSABLE PONER ESTA ANOTACIÓN EN VEZ DEL REQUESTSCOPED
public class relacionDocenteMateriaHorarioBean implements Serializable {
    private RelacionDocenteHorarioMateria relacion;
    //INDISPENSABLE ESTA VARIABLE CON EL ALCANCE ESTÁTICO
    private static List<RelacionDocenteHorarioMateria> relacionesList;

    public RelacionDocenteHorarioMateria getRelacion() {
        return relacion;
    }

    public void setRelacion(RelacionDocenteHorarioMateria relacion) {
        this.relacion = relacion;
    }

    public static List<RelacionDocenteHorarioMateria> getRelacionesList() {
        return relacionesList;
    }
    
    public relacionDocenteMateriaHorarioBean() throws ClassNotFoundException, SQLException{
        relacion = new RelacionDocenteHorarioMateria();
        relacionDMH();
        
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
                    String Query = "SELECT CONCAT(hora_inicio, \" a \", hora_fin) AS jornada"+
                                        ", (SELECT CONCAT(CONCAT(a.descripcion, \"-\",g.Nomenclatura),\"-\",r.nomenclatura) FROM horario as h " +
"INNER JOIN horario_asignado as hasig on hasig.rowid_horario = h.id " +
"INNER JOIN grupo as g on g.id = hasig.rowid_grupo_asignatura " +
"INNER JOIN grupo_asignatura_p gasig on gasig.rowid_grupo = g.id " +
"INNER JOIN asignatura_docente dasig on dasig.rowid_docente = gasig.id " +
"INNER JOIN asignatura a on a.id = dasig.rowid_asignatura " +
"INNER JOIN recurso r on r.id = hasig.rowid_recurso " +
"WHERE Dia = 'Lunes') AS Lunes "+
                                        ", (SELECT CONCAT(CONCAT(a.descripcion, \"-\",g.Nomenclatura),\"-\",r.nomenclatura) FROM horario as h " +
"INNER JOIN horario_asignado as hasig on hasig.rowid_horario = h.id " +
"INNER JOIN grupo as g on g.id = hasig.rowid_grupo_asignatura " +
"INNER JOIN grupo_asignatura_p gasig on gasig.rowid_grupo = g.id " +
"INNER JOIN asignatura_docente dasig on dasig.rowid_docente = gasig.id " +
"INNER JOIN asignatura a on a.id = dasig.rowid_asignatura " +
"INNER JOIN recurso r on r.id = hasig.rowid_recurso " +
"WHERE Dia = 'Martes') AS Martes"+", (SELECT CONCAT(CONCAT(a.descripcion, \"-\",g.Nomenclatura),\"-\",r.nomenclatura) FROM horario as h " +
"INNER JOIN horario_asignado as hasig on hasig.rowid_horario = h.id " +
"INNER JOIN grupo as g on g.id = hasig.rowid_grupo_asignatura " +
"INNER JOIN grupo_asignatura_p gasig on gasig.rowid_grupo = g.id " +
"INNER JOIN asignatura_docente dasig on dasig.rowid_docente = gasig.id " +
"INNER JOIN asignatura a on a.id = dasig.rowid_asignatura " +
"INNER JOIN recurso r on r.id = hasig.rowid_recurso " +
"WHERE Dia = 'Miercoles') AS Miercoles"+", (SELECT CONCAT(CONCAT(a.descripcion, \"-\",g.Nomenclatura),\"-\",r.nomenclatura) FROM horario as h " +
"INNER JOIN horario_asignado as hasig on hasig.rowid_horario = h.id " +
"INNER JOIN grupo as g on g.id = hasig.rowid_grupo_asignatura " +
"INNER JOIN grupo_asignatura_p gasig on gasig.rowid_grupo = g.id " +
"INNER JOIN asignatura_docente dasig on dasig.rowid_docente = gasig.id " +
"INNER JOIN asignatura a on a.id = dasig.rowid_asignatura " +
"INNER JOIN recurso r on r.id = hasig.rowid_recurso " +
"WHERE Dia = 'Jueves') AS Jueves"+", (SELECT CONCAT(CONCAT(a.descripcion, \"-\",g.Nomenclatura),\"-\",r.nomenclatura) FROM horario as h " +
"INNER JOIN horario_asignado as hasig on hasig.rowid_horario = h.id " +
"INNER JOIN grupo as g on g.id = hasig.rowid_grupo_asignatura " +
"INNER JOIN grupo_asignatura_p gasig on gasig.rowid_grupo = g.id " +
"INNER JOIN asignatura_docente dasig on dasig.rowid_docente = gasig.id " +
"INNER JOIN asignatura a on a.id = dasig.rowid_asignatura " +
"INNER JOIN recurso r on r.id = hasig.rowid_recurso " +
"WHERE Dia = 'Viernes') AS Viernes"+", (SELECT CONCAT(CONCAT(a.descripcion, \"-\",g.Nomenclatura),\"-\",r.nomenclatura) FROM horario as h " +
"INNER JOIN horario_asignado as hasig on hasig.rowid_horario = h.id " +
"INNER JOIN grupo as g on g.id = hasig.rowid_grupo_asignatura " +
"INNER JOIN grupo_asignatura_p gasig on gasig.rowid_grupo = g.id " +
"INNER JOIN asignatura_docente dasig on dasig.rowid_docente = gasig.id " +
"INNER JOIN asignatura a on a.id = dasig.rowid_asignatura " +
"INNER JOIN recurso r on r.id = hasig.rowid_recurso " +
"WHERE Dia = 'Sabado') AS Sabado"
                                        + " FROM horario";
                PreparedStatement pstmt = connect
				.prepareStatement(Query);
                System.out.println("***********Consulta: "+Query);
		ResultSet rs = pstmt.executeQuery();
                System.out.println("***********Inicia recorrido");
		while (rs.next()) {
                        
			relacion = new RelacionDocenteHorarioMateria();
			//objRelacion.setId(rs.getInt("id"));
			relacion.setJornada(rs.getString("jornada"));
			relacion.setLunes(rs.getString("Lunes"));
                        relacion.setMartes(rs.getString("Martes"));
                        relacion.setMiercoles(rs.getString("Miercoles"));
                        relacion.setJueves(rs.getString("Jueves"));
                        relacion.setViernes(rs.getString("Viernes"));
                        //objRelacion.setSabado(rs.getString("Sabado"));
			relacionesList.add(relacion);

		}

		// close resources
		rs.close();
		pstmt.close();
		connect.close();
}               catch (Exception ex) {
			System.out.println("in Sql consult");
			System.out.println(ex.getMessage());
		}
		//return relaciones;

	}
    
 
}
