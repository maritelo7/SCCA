/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import logica.CoordinadorDAO;

/**
 * FXML Controller class
 *
 * @author Mari
 */
public class GUIMenuPrincipalCoordinadorController implements Initializable {
   CoordinadorDAO coordinador;
   /**
    * Initializes the controller class.
    */
   @Override
   public void initialize(URL url, ResourceBundle rb) {
      // TODO
   }   
   
   public void cargarCuenta(CoordinadorDAO coordinador) {
      this.coordinador = coordinador;
   }
   
}
