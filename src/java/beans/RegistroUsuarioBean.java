/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Asignatura;
import entity.Docente;
import entity.TipoId;
import entity.TipoRol;
import entity.Usuario;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Raul A. Hernandez
 */
@ManagedBean
@RequestScoped
public class RegistroUsuarioBean {
    private Usuario usuarios;
    private Integer id;
    private TipoId tipoId;
    private String nombre;
    private String apellido;
    private String tipo_contrato;
    private boolean estado;
    private TipoRol rol;
    private String email;
    private String usuario;
    private String password;
    private Integer edad;
    public RegistroUsuarioBean() {
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TipoId getTipoId() {
        return tipoId;
    }

    public void setTipoId(TipoId tipoId) {
        this.tipoId = tipoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTipo_contrato() {
        return tipo_contrato;
    }

    public void setTipo_contrato(String tipo_contrato) {
        this.tipo_contrato = tipo_contrato;
    }

    

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public TipoRol getRol() {
        return rol;
    }

    public void setRol(TipoRol rol) {
        this.rol = rol;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public TipoId[] getTipoIdes() {
        return TipoId.values();
    }
    
    public TipoRol[] getTipoRoles() {
        return TipoRol.values();
    }

    public String registrarse() {
        Usuario user = new Usuario();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        user.setId(id);
        //user.setTipo(tipoId);
        user.setNombre(nombre);
        user.setApellido(apellido);
        //user.setTelefono(telefono);
        //user.setEstado(true);
        //user.set(rol);
        user.setEmail(email);
        user.setUsuario(usuario);
        user.setContraseña(password);
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        /************* Registro de Docentes **************/
        docenteBean blDocente = new docenteBean();
        blDocente.registrarDocente(id, nombre, apellido, edad, email,tipo_contrato);
        return "index";
    }
public List<Usuario> getUsuarios() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<Usuario> q = em.createNamedQuery("Usuario.findAll", Usuario.class);
        return q.getResultList();
    }
public Usuario getUsuarioUser(String userName) throws SQLException, ClassNotFoundException
{
    EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        
        //Query q = em.createQuery("SELECT * FROM usuario where usuario=\'"+userName+"\'");
        //user = (Usuario)q.getSingleResult();
        
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

		PreparedStatement pstmt = connect
				.prepareStatement("SELECT * FROM usuario where usuario=\'"+userName+"\'");
		ResultSet rs = pstmt.executeQuery();
                Usuario user = new Usuario();
		while (rs.next()) {

			user.setId(rs.getInt("id"));
			user.setNombre(rs.getNString("nombre"));
                        user.setApellido(rs.getNString("apellido"));
                        user.setUsuario(rs.getNString("usuario"));
                        user.setEmail(rs.getNString("email"));
                        user.setContraseña(rs.getNString("contraseña"));
                        user.setActivo(rs.getBoolean("activo"));
		}

		// close resources
		rs.close();
		pstmt.close();
		connect.close();
        
        return user;
}
public String irUsuarios(){
    return "gestionUsuario.xhtml";
}
}
