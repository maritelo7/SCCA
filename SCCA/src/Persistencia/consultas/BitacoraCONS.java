package Persistencia.consultas;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import Persistencia.Bitacora;
/**
 *
 * @author marianacro
 */
public class BitacoraCONS {
    EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("SCCAPU");
    EntityManager entitymanager = emfactory.createEntityManager();
    
    /**
     * Método que recupera de la base de datos las bitácoras registradas en el seguimiento
     * de un alumno
     * @return Retorna una lista de tipo Bitacora que contiene las bitácoras 
     * que se registraron por alumno en su seguimiento
     */
  public List<Bitacora> recuperarBitacoras(){
    List<Bitacora> bitacoras;
    bitacoras = entitymanager.createNamedQuery("Bitacora.findByidSeguimiento").getResultList();
    //System.out.println(bitacoras.get(0).toString());
    return bitacoras;
  }
    
}
