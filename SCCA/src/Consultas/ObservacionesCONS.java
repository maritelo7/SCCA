/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Consultas;

import Persistencia.ObservacionGeneral;
import Persistencia.Controlladores.ObservacionGeneralJpaController;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author cristina
 */
public class ObservacionesCONS {

  EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("SCCAPU");
  EntityManager entitymanager = emfactory.createEntityManager();

  public ObservacionesCONS() {
  }
  
  public boolean registrarObservacion(ObservacionGeneral observacion){
   ObservacionGeneralJpaController controller = new ObservacionGeneralJpaController(emfactory);
    try{
      controller.create(observacion);
    }catch(Exception ex){
      Logger.getLogger(ObservacionesCONS.class.getName()).log(Level.SEVERE, null, ex);
    }
    return true;
  }
}
