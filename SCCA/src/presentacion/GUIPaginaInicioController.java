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
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javax.persistence.PersistenceException;
import logica.AdministracionUsuario;
import logica.AsesorDAO;
import logica.CoordinadorDAO;
import logica.RecepcionistaDAO;

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
   @FXML
   private JFXButton botonSalir;
   @FXML
   private JFXTextField fieldClave;
   @FXML
   private JFXTextField fieldUsuario;
   @FXML
   private JFXButton botonIngresar;
   @FXML
   private Label labelMensaje;

   @Override
   public void initialize(URL url, ResourceBundle rb) {
      // TODO
       botonIngresar.setOnAction(event -> {
           Stage stage = (Stage) botonIngresar.getScene().getWindow();
           stage.close();
       });
      
      botonIngresar.setOnAction(event -> {
         String usuario = fieldUsuario.getText();
         String clave = fieldClave.getText();
         if (validarCampos(usuario, clave)) {
            String tipoUsuario = "";
            RecepcionistaDAO usuarioRE = null;
            AsesorDAO usuarioAS = null;
            CoordinadorDAO usuarioCO = null;

            try {
               if (recuperarUsuarioCoordinador(usuario, clave) == null) {
                  if (recuperarUsuarioAsesor(usuario, clave) == null) {
                     if (recuperarUsuarioRecepcionista(usuario, clave) == null) {
                     } else {
                        usuarioRE = recuperarUsuarioRecepcionista(usuario, clave);
                        tipoUsuario = "recepcionista";
                     }
                  } else {
                     usuarioAS = recuperarUsuarioAsesor(usuario, clave);
                     tipoUsuario = "asesor";
                  }
               } else {
                  usuarioCO = recuperarUsuarioCoordinador(usuario, clave);
                  tipoUsuario = "coordinador";
               }
            } catch (PersistenceException ex) {
               labelMensaje.setText("ERROR CONEXIÓN");
                limpiar();
            } catch (NoSuchAlgorithmException ex) {
               Logger.getLogger(GUIPaginaInicioController.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (!tipoUsuario.equals("")) {
               Node node = (Node) event.getSource();
               Stage stage = (Stage) node.getScene().getWindow();
               String nombreMenuPorActor = null;
               try {
                  FXMLLoader loader = new FXMLLoader(getClass().getResource(nombreMenuPorActor));
                  Scene scene = new Scene(loader.load());
                  if (tipoUsuario.equals("coordinador")) {
                     GUIMenuPrincipalCoordinadorController controller = loader.getController();
                     controller.cargarCuenta(usuarioCO);
                     System.out.println("ERES UN COORDINADOR");
                  }
                  if (tipoUsuario.equals("asesor")) {
                     GUIMenuPrincipalAsesorController controller = loader.getController();
                     controller.cargarCuenta(usuarioAS);
                     System.out.println("ERES UN ASESOR");
                  }
                  if (tipoUsuario.equals("recepcionista")) {
                     GUIMenuPrincipalRecepcionistaController controller = loader.getController();
                     controller.cargarCuenta(usuarioRE);                     
                     System.out.println("ERES UN RECEPCIONISTA");
                  }
                  stage.setScene(scene);
                  stage.setResizable(false);
                  stage.show();
               } catch (IOException ex) {
                  //Logger.getLogger(GUI_IniciarSesionController.class.getName()).log(Level.SEVERE, null, ex);
               }
            } else {
                labelMensaje.setText("DATOS INCORRECTOS");
                limpiar();
            }
         } else {
             labelMensaje.setText("LLENAR CAMPOS");            
         }
      });
   }

   public CoordinadorDAO recuperarUsuarioCoordinador(String usuario, String clave) throws PersistenceException, NoSuchAlgorithmException {
      CoordinadorDAO usuarioCoordinador = null;
      AdministracionUsuario adminUsuario = new AdministracionUsuario();
      try {
         usuarioCoordinador = adminUsuario.recuperarCuentaCoordinador(usuario, clave);
      } catch (ArrayIndexOutOfBoundsException ex) {
         //Logger.getLogger(GUIPaginaInicioController.class.getName()).log(Level.SEVERE, null, ex);
      }
      return usuarioCoordinador;
   }

   public AsesorDAO recuperarUsuarioAsesor(String usuario, String clave) throws PersistenceException, NoSuchAlgorithmException {
      AsesorDAO usuarioAsesor = null;
      AdministracionUsuario adminUsuario = new AdministracionUsuario();
      try {
         usuarioAsesor = adminUsuario.recuperarCuentaAsesor(usuario, clave);
      } catch (ArrayIndexOutOfBoundsException ex) {
         //Logger.getLogger(GUIPaginaInicioController.class.getName()).log(Level.SEVERE, null, ex);
      }
      return usuarioAsesor;
   }

   public RecepcionistaDAO recuperarUsuarioRecepcionista(String usuario, String clave) throws PersistenceException, NoSuchAlgorithmException {
      RecepcionistaDAO usuarioRecepcionista = null;
      AdministracionUsuario adminUsuario = new AdministracionUsuario();
      try {
         usuarioRecepcionista = adminUsuario.recuperarCuentaRecepcionista(usuario, clave);
      } catch (ArrayIndexOutOfBoundsException ex) {
         //Logger.getLogger(GUIPaginaInicioController.class.getName()).log(Level.SEVERE, null, ex);
      }
      return usuarioRecepcionista;
   }

   public boolean validarCampos(String usuario, String clave) {
      return ((usuario != null && !(usuario.trim().isEmpty()))
          && (clave != null && !(clave.trim().isEmpty())));
   }

   public void limpiar() {
      fieldUsuario.clear();
      fieldClave.clear();
   }

   @FXML
   private void initialize(ActionEvent event) {
   }

}
