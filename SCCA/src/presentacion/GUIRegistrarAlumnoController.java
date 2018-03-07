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
import Persistencia.consultas.AlumnoCONS;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
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
        boolean resultado = alumnoCons.validarMatricula(textMatricula.getText());
        if(resultado==true){
            System.out.println("Matricula existente, chido morro");
        }else{
            System.out.println("No existe :(");
        }
    }
    
    
    
}
