/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Rol;
import entity.Usuario;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import entity.UsuarioRol;
/**
 *
 * @author IngenieroDesarrollo
 */
public class ConexDB {
    String url = "jdbc:mysql://localhost:3306/uniajc";

		String username = "root";
		String password = "";
    public int executeQuery(String query) throws ClassNotFoundException, SQLException
    {
        Connection connect = null;
		try {

			Class.forName("com.mysql.jdbc.Driver");

			connect = DriverManager.getConnection(url, username, password);
			// System.out.println("Connection established"+connect);

		} catch (SQLException ex) {
			System.out.println("in exec");
			System.out.println(ex.getMessage());
		}
                //System.out.println(query);
              PreparedStatement pstmt = connect
				.prepareStatement(query);
		int rs = pstmt.executeUpdate();
                // close resources
		//rs.close();
		pstmt.close();
		connect.close();
          return rs;      
    }
    public UsuarioRol getUserRols(Integer id) throws ClassNotFoundException, SQLException{
        UsuarioRol userRol = new UsuarioRol();
        Connection connect = null;
		try {

			Class.forName("com.mysql.jdbc.Driver");

			connect = DriverManager.getConnection(url, username, password);
			// System.out.println("Connection established"+connect);

		} catch (SQLException ex) {
			System.out.println("in exec");
			System.out.println(ex.getMessage());
		}
                String query = "SELECT * FROM usuario_rol WHERE rowid_usuario = "+id;
                System.out.println("*********Consulta UsuarioRoles: "+query);
              PreparedStatement pstmt = connect
				.prepareStatement(query);
		ResultSet rs = pstmt.executeQuery(query);
                while (rs.next()) {
                        userRol.setId(rs.getInt("id"));
                        userRol.setRowidRol(rs.getObject("rowid_rol", Rol.class));
                        userRol.setRowidUsuario(rs.getObject("rowid_usuario", Usuario.class));
		}
                //close resources
		rs.close();
		pstmt.close();
		connect.close();
          
        return userRol;
    }
}
