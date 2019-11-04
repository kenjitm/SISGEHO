/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Asignatura;
import entity.Docente;
import entity.Rol;
import entity.Sede;
import entity.TipoId;
import entity.TipoRol;
import entity.Usuario;
import entity.UsuarioRol;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
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
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Raul A. Hernandez
 */
@ManagedBean
@ViewScoped //INDISPENSABLE PONER ESTA ANOTACIÓN EN VEZ DEL REQUESTSCOPED
public class RegistroUsuarioBean {
    private Usuario user;
    private Usuario userSearch;
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
    private Rol rowRol;
    private UsuarioRol userRol;
    //INDISPENSABLE ESTA VARIABLE CON EL ALCANCE ESTÁTICO
    private static List<Usuario> usersList;
     //INDISPENSABLE EL MÉTODO GET. SÓLO EL GET
    public List<Usuario> getUsersList() {
        return usersList;
    }

    public Usuario getUser() {
        return user;
    }

    public Rol getRowRol() {
        return rowRol;
    }

    public UsuarioRol getUserRol() {
        return userRol;
    }

    public void setUserRol(UsuarioRol userRol) {
        this.userRol = userRol;
    }

    public void setRowRol(Rol rowRol) {
        this.rowRol = rowRol;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public Usuario getUserSearch() {
        return userSearch;
    }

    public void setUserSearch(Usuario userSearch) {
        this.userSearch = userSearch;
    }
    
    public RegistroUsuarioBean() {
        user = new Usuario();
        userSearch = new Usuario();
        userRol = new UsuarioRol();
        obtenerUsuarios();
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
    
     //EL MÉTODO DEBE QUEDAR ASÍ MISMO
    private void obtenerUsuarios() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<Usuario> q = em.createNamedQuery("Usuario.findAll", Usuario.class);
        usersList = q.getResultList();
    }
    public void buscarUsuarioPorId(Integer id) {
        userSearch = buscarById(id);
    }

    public Usuario buscarById(Integer id) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<Usuario> users = em.createNamedQuery("Usuario.findById", Usuario.class);
        users.setParameter("id", id);
        return (users.getResultList().isEmpty())?  null : users.getResultList().get(0);
    }
//EL MÉTODO DEBE QUEDAR ASÍ MISMO
    public void guardarUsuarios() {
        try {
            EntityManagerFactory emf
                    = Persistence.createEntityManagerFactory("SisgehoPU");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            em.close();
            //userRol.setRowidRol(rowRol);
            userRol.setRowidUsuario(user);
            userRol.setActivo(true);
            em.getTransaction().begin();
            em.persist(userRol);
            em.getTransaction().commit();
            em.close();
            user = new Usuario();
            obtenerUsuarios(); //Actualiza lista
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "REALIZADO CON ÉXITO", "Se guardó correctamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "NO SE PUDO REALIZAR", "No se pudo guardar los datos. Inténtelo más tarde");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    //Agregar este método para campos booleanos, como "activo"
    public String transformActivo(Boolean activo) {
        return (activo) ? "ACTIVA" : "INACTIVA";
    }

    //INDISPENSABLE tener este método
    public void enableEditarOption(Usuario users, boolean estado) {
        users.setEditable(estado);
    }
    //EL MÉTODO DEBE QUEDAR ASÍ MISMO
    public void editarUsuario(Usuario s) {
        try {
            EntityManagerFactory emf
                    = Persistence.createEntityManagerFactory("SisgehoPU");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
            em.close();
            obtenerUsuarios(); //Actualiza lista
            s.setEditable(false);
            user = new Usuario();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "REALIZADO CON ÉXITO", "Se actualizó correctamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "NO SE PUDO REALIZAR", "No se pudo editar los datos. Inténtelo más tarde");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    //EL MÉTODO DEBE QUEDAR ASÍ MISMO
    public void eliminarUsuario() {
        try {
            EntityManagerFactory emf
                    = Persistence.createEntityManagerFactory("SisgehoPU");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            if (!em.contains(user)) {
                user = em.merge(user);
            }
            em.remove(user);
            em.getTransaction().commit();
            obtenerUsuarios(); //Actualiza lista
            user = new Usuario();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "REALIZADO CON ÉXITO", "Se eliminó correctamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "NO SE PUDO REALIZAR", "No se pudo eliminar los datos. Inténtelo más tarde");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
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
        user.setPassword(password);
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
                        user.setPassword(rs.getNString("contraseña"));
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
@FacesConverter(forClass = Usuario.class)
    public static class RegistroUsuarioBeanConverter implements Converter {

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
            return ((RegistroUsuarioBean) context.getApplication().evaluateExpressionGet(context, "#{" + "RegistroUsuarioBean" + "}", RegistroUsuarioBean.class)).buscarById(getKey(value));
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object value) {
            if (value == null) {
                return null;
            } else if (value instanceof Sede) {
                return getStringKey(((Sede) value).getId());
            } else {
                throw new IllegalArgumentException("object " + value + " is of type " + value.getClass().getName() + "; expected type: " + Sede.class.getName());
            }
        }

    }
}
