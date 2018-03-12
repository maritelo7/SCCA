package Persistencia.consultas;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import Persistencia.Bitacora;
import Persistencia.controladores.BitacoraJpaController;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author marianacro
 */
public class BitacoraCONS {
    EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("PUCRIS");
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
    System.out.println(bitacoras.get(0).toString());
    return bitacoras;
  }
  
  public boolean registrarBitacora(Bitacora bitacora){
    BitacoraJpaController controller = new BitacoraJpaController(emfactory);
    try{
      controller.create(bitacora);
    }catch(Exception ex){
       Logger.getLogger(BitacoraCONS.class.getName()).log(Level.SEVERE, null, ex);
    }
    return true;
  }
    
}
