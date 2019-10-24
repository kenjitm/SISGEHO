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
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.context.RequestContext;

/**
 *
 * @author SougiroHylian
 */
@ManagedBean
@SessionScoped
public class relacionDocenteMateriaHorarioBean implements Serializable {
    public List<RelacionDocenteHorarioMateria> relacionDMH() throws ClassNotFoundException, SQLException {

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

		List<RelacionDocenteHorarioMateria> relaciones = new ArrayList<RelacionDocenteHorarioMateria>();
		/*PreparedStatement pstmt = connect
				.prepareStatement("SELECT CONCAT( CONCAT(Dia, \" \", hora_inicio), \" a \", hora_fin) AS jornada"+
                                        ", (SELECT 'MATERIA' FROM horario WHERE Dia = 'Lunes') AS Lunes FROM horario");*/
                PreparedStatement pstmt = connect
				.prepareStatement("SELECT CONCAT(hora_inicio, \" a \", hora_fin) AS jornada"+
                                        ", (SELECT CONCAT(CONCAT(a.descripcion, \"-\",g.Nomenclatura),\"-\",r.nomenclatura) FROM horario as h\n" +
"INNER JOIN horario_asignado as hasig on hasig.rowid_horario = h.id\n" +
"INNER JOIN grupo as g on g.id = hasig.rowid_grupo_asignatura\n" +
"INNER JOIN grupo_asignatura_p gasig on gasig.rowid_grupo = g.id\n" +
"INNER JOIN asignatura_docente dasig on dasig.rowid_docente = gasig.id\n" +
"INNER JOIN asignatura a on a.id = dasig.rowid_asignatura\n" +
"INNER JOIN recurso r on r.id = hasig.rowid_recurso\n" +
"WHERE Dia = 'Lunes') AS Lunes FROM horario");
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			RelacionDocenteHorarioMateria objRelacion = new RelacionDocenteHorarioMateria();
			//objRelacion.setId(rs.getInt("id"));
			objRelacion.setJornada(rs.getString("jornada"));
			objRelacion.setLunes(rs.getString("Lunes"));
                        
			relaciones.add(objRelacion);

		}

		// close resources
		rs.close();
		pstmt.close();
		connect.close();

		return relaciones;

	}
    
 
}
