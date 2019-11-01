/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Asignatura;
import entity.Docente;
import entity.Horario;
import entity.RelacionDocenteHorarioMateria;
import entity.Sede;
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
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
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
@ViewScoped //INDISPENSABLE PONER ESTA ANOTACIÓN EN VEZ DEL REQUESTSCOPED
public class docenteBean {
    private Integer id;
    private Docente docentes;
    private Docente docenteSearch;
   //INDISPENSABLE ESTA VARIABLE CON EL ALCANCE ESTÁTICO
    private static List<Docente> docenteList;
    //INDISPENSABLE EL MÉTODO GET. SÓLO EL GET
    public List<Docente> getDocenteList() {
        return docenteList;
    }
    
    public Docente getDocentes() {
        return docentes;
    }

    public void setDocentes(Docente docentes) {
        this.docentes = docentes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Docente getDocenteSearch() {
        return docenteSearch;
    }

    public void setDocenteSearch(Docente docenteSearch) {
        this.docenteSearch = docenteSearch;
    }
    
    public docenteBean(){
        docentes = new Docente();
        docenteSearch = new Docente();
        obtenerDocentes();
    }
    //EL MÉTODO DEBE QUEDAR ASÍ MISMO
    private void obtenerDocentes() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<Docente> q = em.createNamedQuery("Docente.findAll", Docente.class);
        docenteList = q.getResultList();
    }
    public void buscarDocentePorId(Integer id) {
        docenteSearch = buscarById(id);
    }
    public Docente buscarById(Integer id) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<Docente> docente = em.createNamedQuery("Docente.findByIdentificacion", Docente.class);
        docente.setParameter("identificacion", id);
        return (docente.getResultList().isEmpty())?  null : docente.getResultList().get(0);
    }
    public Docente buscarPorId(Integer id) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("SisgehoPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<Docente> docen = em.createNamedQuery("Docente.findById", Docente.class);
        docen.setParameter("id", id);
        return docen.getResultList().get(0);
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
    //EL MÉTODO DEBE QUEDAR ASÍ MISMO
    public void registrarDocenteV() {
        try {
            EntityManagerFactory emf
                    = Persistence.createEntityManagerFactory("SisgehoPU");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(docentes);
            em.getTransaction().commit();
            em.close();
            docentes = new Docente();
            obtenerDocentes(); //Actualiza lista
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "REALIZADO CON ÉXITO", "Se guardó correctamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "NO SE PUDO REALIZAR", "No se pudo guardar los datos. Inténtelo más tarde");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
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
    //Agregar este método para campos booleanos, como "activo"
    public String transformActivo(Boolean activo) {
        return (activo) ? "ACTIVA" : "INACTIVA";
    }

    //INDISPENSABLE tener este método
    public void enableEditarOption(Docente docentes, boolean estado) {
        docentes.setEditable(estado);
    }
    //EL MÉTODO DEBE QUEDAR ASÍ MISMO
    public void editarDocente(Docente d) {
        try {
            EntityManagerFactory emf
                    = Persistence.createEntityManagerFactory("SisgehoPU");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.merge(docentes);
            em.getTransaction().commit();
            em.close();
            obtenerDocentes(); //Actualiza lista
            d.setEditable(false);
            docentes = new Docente();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "REALIZADO CON ÉXITO", "Se actualizó correctamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "NO SE PUDO REALIZAR", "No se pudo editar los datos. Inténtelo más tarde");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    //EL MÉTODO DEBE QUEDAR ASÍ MISMO
    public void eliminarDocente() {
        try {
            EntityManagerFactory emf
                    = Persistence.createEntityManagerFactory("SisgehoPU");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            if (!em.contains(docentes)) {
                docentes = em.merge(docentes);
            }
            em.remove(docentes);
            em.getTransaction().commit();
            obtenerDocentes(); //Actualiza lista
            docentes = new Docente();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "REALIZADO CON ÉXITO", "Se eliminó correctamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "NO SE PUDO REALIZAR", "No se pudo eliminar los datos. Inténtelo más tarde");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    @FacesConverter(forClass = Docente.class)
    public static class docenteBeanConverter implements Converter {

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
            return ((docenteBean) context.getApplication().evaluateExpressionGet(context, "#{" + "docenteBean" + "}", docenteBean.class)).buscarPorId(getKey(value));
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object value) {
            if (value == null) {
                return null;
            } else if (value instanceof Horario) {
                return getStringKey(((Horario) value).getId());
            } else {
                throw new IllegalArgumentException("object " + value + " is of type " + value.getClass().getName() + "; expected type: " + Horario.class.getName());
            }
        }

    }
}
