/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Cristhian Ubaldo Promotor
 */
public class GUIActualizarAlumnoController implements Initializable {

    @FXML
    private AnchorPane fondo;
    @FXML
    private JFXTextField txtTelefono;
    @FXML
    private JFXTextField txtCorreo;
    @FXML
    private JFXTextField txtMaterno;
    @FXML
    private JFXTextField txtPaterno;
    @FXML
    private JFXTextField txtNombre;
    @FXML
    private JFXComboBox<?> cbxCarrera;
    @FXML
    private JFXComboBox<?> cbxSexo;
    @FXML
    private DatePicker dtpFechaNacimiento;
    @FXML
    private JFXTextField txtMatricula;
    @FXML
    private JFXComboBox<?> cbxArea;
    @FXML
    private ListView<?> listaResultados;
    @FXML
    private JFXTextField txtBusqueda;
    @FXML
    private JFXButton btnBuscar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
