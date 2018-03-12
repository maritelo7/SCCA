/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

/**
 *
 * @author cristina
 */
public class ObservacionesDAO {
  int idObservacionGral;
  String comentario;
  int idSeguimiento;

  public ObservacionesDAO() {
  }

  
  public ObservacionesDAO(int idObservacionGral, String comentario, int idSeguimiento) {
    this.idObservacionGral = idObservacionGral;
    this.comentario = comentario;
    this.idSeguimiento = idSeguimiento;
  }

  public int getIdObservacionGral() {
    return idObservacionGral;
  }

  public void setIdObservacionGral(int idObservacionGral) {
    this.idObservacionGral = idObservacionGral;
  }

  public String getComentario() {
    return comentario;
  }

  public void setComentario(String comentario) {
    this.comentario = comentario;
  }

  public int getIdSeguimiento() {
    return idSeguimiento;
  }

  public void setIdSeguimiento(int idSeguimiento) {
    this.idSeguimiento = idSeguimiento;
  }
  
  
}
