/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import Persistencia.Alumno;
import Consultas.AlumnoCONS;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * FXML Controller class
 *
 * @author Cristhian Ubaldo Promotor
 */
public class GUIActualizarInformacionAlumnoController implements Initializable {

    @FXML
    private JFXTextField txtBusqueda;
    @FXML
    private ListView<Alumno> lvCoincidencias;
    @FXML
    private JFXButton btnBuscar;
    @FXML
    private JFXButton btnRegresar;
    @FXML
    private JFXButton btnEditar;
    @FXML
    private JFXButton btnEliminar;
    @FXML
    private JFXTextField txtMatricula;
    @FXML
    private JFXTextField txtNOmbre;
    @FXML
    private JFXTextField txtPaterno;
    @FXML
    private JFXTextField txtMaterno;
    @FXML
    private JFXTextField txtCarrera;
    @FXML
    private JFXTextField txtCorreo;
    @FXML
    private JFXTextField txtTelefono;
    @FXML
    private ComboBox<String> cbxArea;
    @FXML
    private ComboBox<String> cbxSexo;
    @FXML
    private DatePicker dtpFechaNac;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbxSexo.getItems().addAll("Femenino", "Masculino");
        cbxArea.getItems().addAll("Técnica", "Humanidades", "Económico administrativa", "Ciencias de la salud", "Biológico agropecuaria", "Artes");
    }    

    @FXML
    private void buscar(ActionEvent event) {
        if (validarCamposVacios()) {
//            Alumno listaAlumnos;
//            AlumnoCONS  alumno = new AlumnoCONS();
//            listaAlumnos = alumno.obtenerAlumnos(txtBusqueda.getText());
//            
        }else{
            String mensaje = "No ha ingresado la matricula, por favor primero ingrese una";
            mostrarMensajeCampos(mensaje);
            txtBusqueda.setText("");
        }
        
    }
    
    public boolean validarCamposVacios(){
        boolean camposLlenos = false;
        String[]tam;
        tam = txtBusqueda.getText().split(" ");
        if( txtBusqueda.getText().isEmpty() || tam.length > 0){
           camposLlenos = true;
        }
        return camposLlenos;
    }
    public void mostrarMensajeCampos(String mensaje) {
        Alert confirmacion = new Alert(Alert.AlertType.INFORMATION);
        confirmacion.setTitle("Ventana emergente error");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText(mensaje);
        ButtonType btAceptar = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirmacion.getButtonTypes().setAll(btAceptar);
        confirmacion.showAndWait();
    }
    
    
    
}
