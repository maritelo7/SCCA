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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logica.Asesor;
import logica.Coordinador;
import logica.Recepcionista;

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

   @Override
   public void initialize(URL url, ResourceBundle rb) {
      // TODO
       botonSalir.setOnAction(event -> {          
          String usuario = fieldUsuario.getText();
          String clave = fieldClave.getText();
          if (validarCampos(usuario, clave)) {
            //Se checaría primero si el nombre y usuario es de un CO, sino si es un AS y finalmente si es un RE
           String tipoUsuario = "";
           Recepcionista usuarioRE = null;
           Asesor usuarioAS = null;
           Coordinador usuarioCO = null;
            if (recuperarUsuarioCoordinador(usuario, clave) == null){
              if (recuperarUsuarioAsesor(usuario, clave == null)){
                 if (recuperarUsuarioRecepcionista(usuario, clave)== null){
                 
                 } else{
                    usuarioRE = recuperarUsuarioRecepcionista(usuario, clave);
                    tipoUsuario = "recepcionista";
                 }
              }else{
                 usuarioAS = recuperarUsuarioAsesor(usuario, clave);
                 tipoUsuario = "asesor";
              }
           } else {
              usuarioCO = recuperarUsuarioCoordinador(usuario, clave);
              tipoUsuario = "coordinador";
           }
            que onda que pex 


            try {               
               Node node = (Node) event.getSource();
               Stage stage = (Stage) node.getScene().getWindow();
               String nombreMenuPorActor = null;
               try {
                  FXMLLoader loader = new FXMLLoader(getClass().getResource(nombreMenuPorActor));
                  Scene scene = new Scene(loader.load());
                  if (tipoUsuario.equals("coordinador")) {
                     GUIMenuPrincipalCoordinadorController controller = loader.getController();
                     controller.cargarCuenta(usuarioCO);
                  }                  
                  if (tipoUsuario.equals("asesor")){
                     GUIMenuPrincipalAsesorController controller = loader.getController();
                     controller.cargarCuenta(usuarioAS);
                  }
                  if(tipoUsuario.equals("recepcionista")){
                     GUIMenuPrincipalRecepcionistaController controller = loader.getController();
                     controller.cargarCuenta(usuarioRE);
                  }   
                  stage.setScene(scene);
                  stage.setResizable(false);
                  stage.show();
               } catch (IOException ex) {
                  //Logger.getLogger(GUI_IniciarSesionController.class.getName()).log(Level.SEVERE, null, ex);
               }
            } catch (ArrayIndexOutOfBoundsException ex) {
               //mensaje datos incorrectos o nó válidos
           //    limpiar();
            //} catch (PersistenceException ex) {
               //mensaje error conexion
             //  limpiar();            
            //} catch (NoSuchAlgorithmException ex) {
              //Logger.getLogger(GUI_IniciarSesionController.class.getName()).log(Level.SEVERE, null, ex);
           } 
         } else {
           //mensaje campos llenos
         }
      });
   }
       
//   public Coordinador recuperarUsuarioCoordinador(usuario, clave){     
//      Coordinador usuarioCO = null;
//      AdministracionCuenta adminCuenta = new AdministracionCuenta();
//      cuentaRecuperada = adminCuenta.consultarCuenta(nickname, clave);
//      return usuarioCO; 
//   }
//   
//      public Asesor recuperarUsuarioAsesor(usuario, clave){     
//      Asesor usuarioAS = null;
//      AdministracionCuenta adminCuenta = new AdministracionCuenta();
//      String nickname = tFieldNick.getText();
//      String clave = pFieldClave.getText();
//      cuentaRecuperada = adminCuenta.consultarCuenta(nickname, clave);
//      return usuarioAS; 
//   }
//      
//   public Recepcionista recuperarUsuarioRecepcionista(usuario, clave){     
//      Recepcionista usuarioRE = null;
//      AdministracionCuenta adminCuenta = new AdministracionCuenta();
//      String nickname = tFieldNick.getText();
//      String clave = pFieldClave.getText();
//      cuentaRecuperada = adminCuenta.consultarCuenta(nickname, clave);
//      return usuarioRE; 
//   }
   
      public boolean validarCampos(String usuario, String clave) {
      return ((usuario!= null && !(usuario.trim().isEmpty()))
          && (clave != null && !(clave.trim().isEmpty())));
   }
      
      public void limpiar() {
      fieldUsuario.clear();
      fieldClave.clear();
   }
      
   
   
}
