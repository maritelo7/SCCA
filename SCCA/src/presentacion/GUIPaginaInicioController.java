/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import javafx.fxml.FXML;

/**
 * FXML Controller class
 *
 * @author marianacro
 */
public class GUIPaginaInicioController implements Initializable {

    @FXML
    private JFXButton botonConsultarActividades;

    @FXML
    private JFXButton botonConsultarAvisos;

    @FXML
    private JFXButton botonIniciarSesion;

    @FXML
    private JFXDrawer drawerPaginaInicio;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
