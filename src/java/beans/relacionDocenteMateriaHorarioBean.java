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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

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
		PreparedStatement pstmt = connect
				.prepareStatement("SELECT CONCAT(Dia, \" \", Hora) AS jornada, (SELECT 'MATERIA' FROM horario WHERE Dia = 'Lunes') AS Lunes FROM horario");
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
