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
public class SeguimientoDAO {
  int idSeguimiento;
  float califPrimerParcial;
  float califSegundoParcial;
  float califProyecto;
  float califFinal;

  public SeguimientoDAO() {
  }

  public SeguimientoDAO(int idSeguimiento, float califPrimerParcial, float califSegundoParcial, float califProyecto, float califFinal) {
    this.idSeguimiento = idSeguimiento;
    this.califPrimerParcial = califPrimerParcial;
    this.califSegundoParcial = califSegundoParcial;
    this.califProyecto = califProyecto;
    this.califFinal = califFinal;
  }

  public int getIdSeguimiento() {
    return idSeguimiento;
  }

  public void setIdSeguimiento(int idSeguimiento) {
    this.idSeguimiento = idSeguimiento;
  }

  public float getCalifPrimerParcial() {
    return califPrimerParcial;
  }

  public void setCalifPrimerParcial(float califPrimerParcial) {
    this.califPrimerParcial = califPrimerParcial;
  }

  public float getCalifSegundoParcial() {
    return califSegundoParcial;
  }

  public void setCalifSegundoParcial(float califSegundoParcial) {
    this.califSegundoParcial = califSegundoParcial;
  }

  public float getCalifProyecto() {
    return califProyecto;
  }

  public void setCalifProyecto(float califProyecto) {
    this.califProyecto = califProyecto;
  }

  public float getCalifFinal() {
    return califFinal;
  }

  public void setCalifFinal(float califFinal) {
    this.califFinal = califFinal;
  }
  
}
