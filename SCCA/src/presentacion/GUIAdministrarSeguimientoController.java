/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import persistencia.Bitacora;
import persistencia.Consultas.BitacoraCONS;
import persistencia.Conversacion;
import persistencia.Taller;

/**
 * FXML Controller class
 *
 * @author cristina
 */
public class GUIAdministrarSeguimientoController implements Initializable {

 @FXML
 private TableColumn<Bitacora, Integer> clmNo; 
 
@FXML
 private TableColumn<Bitacora, Integer> clmModulo; 

@FXML
 private TableColumn<Bitacora, Integer> clmSeccion;

@FXML
 private TableColumn<Bitacora, String> clmFecha;

@FXML
 private TableColumn<Bitacora, Integer> clmCalificacion; 

@FXML
 private TableColumn<Bitacora, String> clmObservaciones; 

@FXML 
private TableView<Bitacora> tableBitacoras;

@FXML
 private TableColumn<Bitacora, Integer> clmNoConversaciones; 
 
@FXML
 private TableColumn<Bitacora, String> clmFechaConversaciones; 

@FXML 
private TableView<Conversacion> tableConversaciones;
  
@FXML
 private TableColumn<Bitacora, Integer> clmNoTalleres; 
   
@FXML
 private TableColumn<Bitacora, String> clmNombreTaller; 

@FXML 
private TableView<Taller> tableTalleres;

@FXML
private TextArea areaObservaciones;

@FXML
private Button btnModificar;

@FXML Button btnCancelar;

ObservableList<Bitacora> bitacoras = null;
 
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
  }  
  
  public void llenarTablaBitacoras(){
    BitacoraCONS consulta = new BitacoraCONS();
    bitacoras = FXCollections.observableList(consulta.recuperarBitacoras());
    clmNo.setCellValueFactory(new PropertyValueFactory<>("numBitacora"));
    clmModulo.setCellValueFactory(new PropertyValueFactory<>("modulo"));
    clmSeccion.setCellValueFactory(new PropertyValueFactory<>("seccion"));
    clmFecha.setCellValueFactory(new PropertyValueFactory<>("fechaEntrega"));
    clmCalificacion.setCellValueFactory(new PropertyValueFactory<>("calificacionObservacion"));
    clmObservaciones.setCellValueFactory(new PropertyValueFactory<>("observaciones"));
  }
  
}
