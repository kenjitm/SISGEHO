/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
