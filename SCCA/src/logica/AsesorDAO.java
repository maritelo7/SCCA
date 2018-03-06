/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.Date;

/**
 *
 * @author Mari
 */
public class AsesorDAO {
   private String numPersonal;
   private String apellidoPaterno;
   private String apellidoMaterno;
   private String correo;
   private String nombre;
   private String nombreUsuario;
   private String claveUsuario;
   private String carreraDeInteres;
   private String tipoContratación;
   private Date fechaContratacion;
   
   AsesorDAO(String nombreUsuario, String claveUsuario) {
      this.nombreUsuario = nombreUsuario;
      this.claveUsuario = nombreUsuario;
   }  

   
}
