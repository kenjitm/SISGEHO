/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Asignatura;
import entity.Docente;
import entity.RelacionDocenteHorarioMateria;
import entity.TipoId;
import entity.TipoRol;
import entity.Usuario;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ValueChangeEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import org.primefaces.context.RequestContext;

/**
 *
 * @author SougiroHylian
 */
@ManagedBean
@RequestScoped
public class docenteBean {
    private Docente docentes = new Docente();
    private Integer id;
    private String nombre;
    private String apellido;
    private Integer edad;
    private String email;
    private String tipo_contrato;
    private Asignatura asignatura;
    private List<Asignatura> lstAsignaturas;
    public Docente getDocentes() {
        return docentes;
    }

    public void setDocentes(Docente docentes) {
        this.docentes = docentes;
    }

    public Integer getId() {
        return id;
    }

    public Asignatura getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
    }

    public List<Asignatura> getLstAsignaturas() {
        return lstAsignaturas;
    }

    public void setLstAsignaturas(List<Asignatura> lstAsignaturas) {
        this.lstAsignaturas = lstAsignaturas;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo_contrato() {
        return tipo_contrato;
    }

    public void setTipo_contrato(String tipo_contrato) {
        this.tipo_contrato = tipo_contrato;
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

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void registrarDocente(Integer id, String nombre, String apellido, Integer edad,
            String email, String tipo_contrato) {
        Docente doce = new Docente();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        doce.setId(id);
        doce.setNombre(nombre);
        doce.setApellido(apellido);
        doce.setEdad(edad);
        doce.setEmail(email);
        doce.setTipoContrato(tipo_contrato);
        em.getTransaction().begin();
        em.persist(doce);
        em.getTransaction().commit();
        
    }
    public void registrarDocenteV() {
        Docente doce = new Docente();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        doce.setId(id);
        doce.setNombre(nombre);
        doce.setApellido(apellido);
        doce.setEdad(edad);
        doce.setEmail(email);
        doce.setTipoContrato(tipo_contrato);
        em.getTransaction().begin();
        em.persist(doce);
        em.getTransaction().commit();
        
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "ÈXITO", "Registro realizado satisfactoriamente");
            // For PrimeFaces < 6.2
            RequestContext.getCurrentInstance().showMessageInDialog(message);  
    }
    public List<Docente> obtenerDocentes() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<Docente> q = em.createNamedQuery("Docente.findAll", Docente.class);
        return q.getResultList();
    }
    public String irDocente(){
        return "gestionDocente.xhtml";
    }
    
    public void handleChange(ValueChangeEvent event) throws SQLException, ClassNotFoundException{  
        List<Asignatura> lstAsignaturas = new ArrayList<Asignatura>();
        int IdDocente = Integer.parseInt(event.getNewValue().toString());
        System.out.println("New value: " + event.getNewValue());
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

		//List<Asignatura> asignaturas = new ArrayList<Asignatura>();
		/*PreparedStatement pstmt = connect
				.prepareStatement("SELECT CONCAT( CONCAT(Dia, \" \", hora_inicio), \" a \", hora_fin) AS jornada"+
                                        ", (SELECT 'MATERIA' FROM horario WHERE Dia = 'Lunes') AS Lunes FROM horario");*/
                try
                {
                    String query = "SELECT a.descripcion,a.id,a.semestre,a.rowid_pensum,a.rowid_programa,a.activo FROM asignatura_docente as adocs " +
"INNER JOIN asignatura a on a.id = adocs.rowid_asignatura " +
"WHERE adocs.rowid_docente = "+IdDocente;
                    System.out.println("**********Consulta: " + query);
                  PreparedStatement pstmt = connect
				.prepareStatement(query);
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			Asignatura objAsignatura = new Asignatura();
			//objRelacion.setId(rs.getInt("id"));
			objAsignatura.setId(Integer.parseInt(rs.getString("id")));
			objAsignatura.setDescripcion(rs.getString("descripcion"));
                        System.out.println("**********Descripcion: " + rs.getString("descripcion"));
                        objAsignatura.setSemestre(rs.getInt("semestre"));
                        
			lstAsignaturas.add(objAsignatura);

		} 
                // close resources
		rs.close();
		pstmt.close();
		connect.close();
                
                }catch(Exception ex)
                {
                    System.out.println("******************* Error cargado Asignaturas *******************************");
			System.out.println(ex.getMessage());
                }
                

		
}
}
