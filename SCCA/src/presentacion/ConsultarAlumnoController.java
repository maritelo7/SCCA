/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author marianacro
 */
public class ConsultarAlumnoController implements Initializable {

    @FXML
    private TextField textNombre;
    @FXML
    private TextField textApellidoPat;
    @FXML
    private TextField textApellidoMat;
    @FXML
    private TextField textFechaNac;
    @FXML
    private TextField textMatricula;
    @FXML
    private TextField textCarrera;
    @FXML
    private TextField textArea;
    @FXML
    private TextField textCorreo;
    @FXML
    private TextField textTelefono;
    @FXML
    private TextField textGenero;
    @FXML
    private Button botonRegresar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void registrarAlumno(ActionEvent event) {
    }
    
}
