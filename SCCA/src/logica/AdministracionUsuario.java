/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import persistencia.Asesor;
import persistencia.Coordinador;
import persistencia.Recepcionista;

/**
 * Clase para implementar la gestión de la entidad cuenta
 *
 * @author Maribel Tello Rodriguez
 * @author José Alí Valdivia Ruiz
 */
public class AdministracionUsuario {

   final static String UNIDAD_PERSISTENCIA = "SSCAPU2";
   EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(UNIDAD_PERSISTENCIA, null);
   EntityManager entity = entityManagerFactory.createEntityManager();

   public AsesorDAO recuperarCuentaAsesor(String nombreUsuario, String clave) throws NoSuchAlgorithmException,
       ArrayIndexOutOfBoundsException, PersistenceException {
      Asesor cuentaAsesorRecuperada;
      String claveHasheada = getHash(clave);
      cuentaAsesorRecuperada = (Asesor) entity.createNamedQuery("Asesor.iniciarSesion").setParameter("nombreUsuario", nombreUsuario).setParameter("claveUsuario", claveHasheada).getResultList().get(0);
      AsesorDAO cuentaAsesor = new AsesorDAO(cuentaAsesorRecuperada.getNombreUsuario(), cuentaAsesorRecuperada.getClaveUsuario());
      return cuentaAsesor;
   }

   public CoordinadorDAO recuperarCuentaCoordinador(String nombreUsuario, String clave) throws NoSuchAlgorithmException,
       ArrayIndexOutOfBoundsException, PersistenceException {
      Coordinador cuentaCoordinadorRecuperada;
      String claveHasheada = getHash(clave);
      cuentaCoordinadorRecuperada = (Coordinador) entity.createNamedQuery("Coordinador.iniciarSesion").setParameter("nombreUsuario", nombreUsuario).setParameter("claveUsuario", claveHasheada).getResultList().get(0);
      CoordinadorDAO cuentaCoordinador = new CoordinadorDAO(cuentaCoordinadorRecuperada.getNombreUsuario(), cuentaCoordinadorRecuperada.getClaveUsuario());
      return cuentaCoordinador;
   }

   public RecepcionistaDAO recuperarCuentaRecepcionista(String nombreUsuario, String clave) throws NoSuchAlgorithmException,
       ArrayIndexOutOfBoundsException, PersistenceException {
      Recepcionista cuentaRecepcionistaRecuperada;
      String claveHasheada = getHash(clave);
      cuentaRecepcionistaRecuperada = (Recepcionista) entity.createNamedQuery("Recepcionista.iniciarSesion").setParameter("nombreUsuario", nombreUsuario).setParameter("claveUsuario", claveHasheada).getResultList().get(0);
      RecepcionistaDAO cuentaRecepcionista = new RecepcionistaDAO(cuentaRecepcionistaRecuperada.getNombreUsuario(), cuentaRecepcionistaRecuperada.getClaveUsuario());
      return cuentaRecepcionista;
   }

   public String getHash(String string) throws NoSuchAlgorithmException {
      MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
      byte[] hash = messageDigest.digest(string.getBytes(Charset.forName("UTF-8")));
      StringBuilder stringBuilder = new StringBuilder();
      for (int i = 0; i < hash.length; i++) {
         stringBuilder.append(Integer.toString((hash[i] & 0xff) + 0x100, 16).substring(1));
      }
      return stringBuilder.toString();
   }

}
