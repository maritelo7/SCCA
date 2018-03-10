/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Mari
 */
public class GUIConsultarAsesoresFXMLController implements Initializable {

   @FXML
   private TableView<?> tableAsesores;
   @FXML
   private TableColumn<?, ?> columnNumPersonal;
   @FXML
   private TableColumn<?, ?> columnCorreo;
   @FXML
   private TableColumn<?, ?> columnNombreCompleto;
   @FXML
   private TextField tFieldBuscarNumPersonal;
   @FXML
   private JFXButton buttonBuscar;
   @FXML
   private JFXButton buttonRegistrar;
   @FXML
   private JFXButton buttonRegresar;

   /**
    * Initializes the controller class.
    */
   @Override
   public void initialize(URL url, ResourceBundle rb) {
      // TODO
   }   
   
}
