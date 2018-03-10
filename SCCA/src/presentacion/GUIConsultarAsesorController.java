/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Mari
 */
public class GUIConsultarAsesorController implements Initializable {

   @FXML
   private JFXButton buttonRegresar;
   @FXML
   private TextField tFieldNombre;
   @FXML
   private TextField tFieldApellidoPaterno;
   @FXML
   private TextField tFieldApellidoMaterno;
   @FXML
   private TextField tFieldCorreo;
   @FXML
   private TextField tFieldNumPersonal;
   @FXML
   private JFXComboBox<?> comboTipoContratacion;
   @FXML
   private TextField tFieldCarrera;
   @FXML
   private JFXButton buttonRegistrar;

   /**
    * Initializes the controller class.
    */
   @Override
   public void initialize(URL url, ResourceBundle rb) {
      // TODO
   }   
   
}
