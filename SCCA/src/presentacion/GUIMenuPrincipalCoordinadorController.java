/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import logica.AsesorDAO;
import logica.CoordinadorDAO;

/**
 * FXML Controller class
 *
 * @author marianacro
 */
public class GUIMenuPrincipalCoordinadorController implements Initializable {

    @FXML
    private Button botonAdministrarAsesor;
    @FXML
    private Button botonEditarAlumno;
    @FXML
    private Button botonRegistrarAlumno;

    /**
     * Initializes the controller class.
     */
    CoordinadorDAO coordinador;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void cargarCuenta(CoordinadorDAO coordinador) {
      this.coordinador = coordinador;
   }
}
