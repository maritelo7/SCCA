/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;

/**
 * FXML Controller class
 *
 * @author Cristhian Ubaldo Promotor
 */
public class GUIActualizarInformacionAlumnoController implements Initializable {

    @FXML
    private JFXTextField txtBusqueda;
    @FXML
    private ListView<?> lvCoincidencias;
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
    private ComboBox<?> cbxArea;
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
    }    
    
}
