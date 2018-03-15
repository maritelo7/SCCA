/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import Persistencia.ObservacionGeneral;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import Consultas.ObservacionesCONS;

/**
 * FXML Controller class
 *
 * @author cristina
 */
public class GUIModificarSeguimientoController implements Initializable {

  @FXML
  private TextField fieldSeguimiento;
  
  @FXML
  private TextArea areaComentario;
  
  @FXML 
  private Button btnRegistrar;
  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
  }  
  
  public ObservacionGeneral obtenerInformacion(){
    ObservacionGeneral entidadObservacion = null;
    int idSeguimiento = Integer.parseInt(fieldSeguimiento.getText()); 
    String comentario = areaComentario.getText();
    entidadObservacion = new ObservacionGeneral(idSeguimiento, comentario);
    System.out.println(entidadObservacion);
    return entidadObservacion; 
  }
  
  @FXML
  public void registrarObservacion(){
    if(fieldSeguimiento.getText().equals("") || areaComentario.getText().equals("")){
       Alert camposVacios = new Alert(Alert.AlertType.INFORMATION);
        camposVacios.setTitle("Ventana emergente error");
        camposVacios.setHeaderText(null);
        camposVacios.setContentText("Por favor ingrese todos los campos obligatorios");
        ButtonType btAceptar = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
        camposVacios.getButtonTypes().setAll(btAceptar);
        camposVacios.showAndWait();
    }else{
      ObservacionesCONS observaciones = new ObservacionesCONS();
      observaciones.registrarObservacion(obtenerInformacion());
      Alert confirmacion = new Alert(Alert.AlertType.INFORMATION);
        confirmacion.setTitle("Ventana emergente exito");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("Observacion registrada con exito");
        ButtonType btAceptar = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirmacion.getButtonTypes().setAll(btAceptar);
        confirmacion.showAndWait();
    }
  }
}
