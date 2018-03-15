/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import logica.AlumnoDAO;
import Persistencia.Alumno;
import Consultas.AlumnoCONS;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author marianacro
 */
public class GUIRegistrarAlumnoController implements Initializable {

    @FXML
    private TextField textMatriculaConsultar;
    @FXML
    private Button botonBuscar;
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
    private Button botonAceptar;
    @FXML
    private Button botonCancelar;
    
    AlumnoDAO alumno = null;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }   
    
    public Alumno obtenerInformacion(){
        Alumno entidadAlumno = null;
        String nombre = textNombre.getText();
        String apellidoPat = textApellidoPat.getText();
        String apellidoMat = textApellidoMat.getText();
        String matricula = textMatricula.getText();
        String carrera = textCarrera.getText();
        String area = textArea.getText();
        String correo = textCorreo.getText();
        String telefono = textTelefono.getText();
        String genero = textGenero.getText();
        Date fechaNac = obtenerFechaNac();
        entidadAlumno = new Alumno(matricula, apellidoPat, correo, nombre, 
                telefono, carrera, fechaNac, genero, area);
        
        return entidadAlumno;
     }
    
     public Date obtenerFechaNac(){
        String fecha = textFechaNac.getText();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaNac = null;
        try {
            fechaNac = formato.parse(fecha);
        } 
        catch (ParseException ex) 
        {
            System.out.println(ex);
        }
        return fechaNac;
    }
    
    
    @FXML
    public void registrarAlumno() {
        if(textNombre.getText().equals("") || textApellidoPat.getText().equals("") ||
        textMatricula.getText().equals("") || textCarrera.getText().equals("") || 
        textArea.getText().equals("") || textCorreo.getText().equals("") || 
        textTelefono.getText().equals("")||textGenero.getText().equals("")){
        Alert camposVacios = new Alert(Alert.AlertType.INFORMATION);
        camposVacios.setTitle("Ventana emergente error");
        camposVacios.setHeaderText(null);
        camposVacios.setContentText("Por favor ingrese todos los capos obligatorios");
        ButtonType btAceptar = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
        camposVacios.getButtonTypes().setAll(btAceptar);
        camposVacios.showAndWait();
        }else{
        AlumnoCONS alumno = new AlumnoCONS();
        alumno.registrarAlumno(obtenerInformacion());
        Alert confirmacion = new Alert(Alert.AlertType.INFORMATION);
        confirmacion.setTitle("Ventana emergente exito");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("Alumno registrado con exito");
        ButtonType btAceptar = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirmacion.getButtonTypes().setAll(btAceptar);
        confirmacion.showAndWait();
        }
        
    }
    
    
    @FXML
    public void consultarAlumnoPorMatricula(){
       
        
    }
    @FXML
    public void consultarAlumno(){
        AlumnoCONS alumnoCons = new AlumnoCONS();
        boolean resultado = alumnoCons.validarMatricula(textMatriculaConsultar.getText());
        if(resultado==true){
            try {
                Stage stage = null;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ConsultarAlumno.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(GUIRegistrarAlumnoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
        Alert confirmacion = new Alert(Alert.AlertType.ERROR);
        confirmacion.setTitle("Ventana emergente error");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("Contraseña no existente");
        ButtonType btAceptar = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirmacion.getButtonTypes().setAll(btAceptar);
        confirmacion.showAndWait();
        }
    }
    
    
    
}
