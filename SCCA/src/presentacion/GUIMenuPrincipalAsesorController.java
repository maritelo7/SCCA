/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import logica.Asesor;

/**
 * FXML Controller class
 *
 * @author Mari
 */
public class GUIMenuPrincipalAsesorController implements Initializable {
Asesor asesor;
   /**
    * Initializes the controller class.
    */
   @Override
   public void initialize(URL url, ResourceBundle rb) {
      // TODO
   }   
   
   public void cargarCuenta(Asesor asesor) {
      this.asesor = asesor;
   }
   
}
