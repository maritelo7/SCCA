/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Consultas;

import Persistencia.Alumno;
import Persistencia.Bitacora;
import Persistencia.Controlladores.AlumnoJpaController;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import logica.AlumnoDAO;

/**
 *
 * @author marianacro
 */
public class AlumnoCONS {
    
    EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("SCCAPU");
    EntityManager entitymanager = emfactory.createEntityManager();

    public AlumnoCONS() {
       
    }
    
    
    
    public boolean registrarAlumno(Alumno alumno) {
        AlumnoJpaController controller = new AlumnoJpaController(emfactory);
        try {
            controller.create(alumno);
        } catch (Exception ex) {
            Logger.getLogger(AlumnoCONS.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return true;
    }
    
    public boolean validarMatricula(String alumnoMat){
        AlumnoJpaController controller = new AlumnoJpaController(emfactory);
        Alumno alumno;
        try {
            alumno = controller.findAlumno(alumnoMat);
            if (alumno.getMatricula().equals(alumnoMat)) {
                return true;
            }
        } catch (Exception e) {

        }
        return false;
        
    }
    
    public Alumno obtenerAlumnos(String matricula){
        AlumnoJpaController controller = new AlumnoJpaController(emfactory);
        Alumno alumno = null;
       // String parecido = "%" + matricula +"%";
        
        try{
            alumno = controller.findAlumno(matricula);
        }catch (Exception e) {

        }
        return alumno;
    }
    
    public List<Alumno> recuperarAlumno(String matricula) {
        List<Alumno> alumno;
        alumno = entitymanager.createNamedQuery("Alumno.findByMatricula").setParameter("matricula", matricula).getResultList();
        return alumno;
    }

    public List<AlumnoDAO> obtenerInfoAlumno(String matricula) {
        List<AlumnoDAO> alumnoDAO = new ArrayList<>();
        List<Alumno> alumno = recuperarAlumno(matricula);
        for (int i = 0; i < alumno.size(); i++) {
            AlumnoDAO alumnoObjeto = new AlumnoDAO();
            alumnoObjeto.setMatricula(alumno.get(i).getMatricula());
            alumnoObjeto.setApellidoMat(alumno.get(i).getApellidoMaterno());
            alumnoObjeto.setApellidoPat(alumno.get(i).getApellidoPaterno());
            alumnoObjeto.setNombre(alumno.get(i).getNombre());
            alumnoObjeto.setCorreo(alumno.get(i).getCorreo());
            alumnoObjeto.setTelefono(alumno.get(i).getTelefono());
            alumnoObjeto.setCarrera(alumno.get(i).getCarrera());
            alumnoObjeto.setFechaNac(alumno.get(i).getFechaNacimiento());
            alumnoObjeto.setGenero(alumno.get(i).getGenero());
            alumnoObjeto.setArea(alumno.get(i).getArea());
            
            alumnoDAO.add(alumnoObjeto);
        }
            return alumnoDAO;
    }
    
    
    
}
