/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.Date;

/**
 *
 * @author cristina
 */
public class BitacoraDAO {
  int idBitacora;
  Date fechaEntrega;
  int calificacionAutoevaluacion;
  int modulo;
  int seccion;
  int numBitacora;
  String observaciones;
  int idSeguimiento;


  public BitacoraDAO(int idBitacora, Date fechaEntrega, int calificacionAutoevaluacion, int modulo, int seccion, int numBitacora, String observaciones, int idSeguimiento) {
    this.idBitacora = idBitacora;
    this.fechaEntrega = fechaEntrega;
    this.calificacionAutoevaluacion = calificacionAutoevaluacion;
    this.modulo = modulo;
    this.seccion = seccion;
    this.numBitacora = numBitacora;
    this.observaciones = observaciones;
    this.idSeguimiento = idSeguimiento;
  }

  public BitacoraDAO() {
  }

  public int getIdBitacora() {
    return idBitacora;
  }

  public void setIdBitacora(int idBitacora) {
    this.idBitacora = idBitacora;
  }

  public Date getFechaEntrega() {
    return fechaEntrega;
  }

  public void setFechaEntrega(Date fechaEntrega) {
    this.fechaEntrega = fechaEntrega;
  }

  public int getCalificacionAutoevaluacion() {
    return calificacionAutoevaluacion;
  }

  public void setCalificacionAutoevaluacion(int calificacionAutoevaluacion) {
    this.calificacionAutoevaluacion = calificacionAutoevaluacion;
  }

  public int getModulo() {
    return modulo;
  }

  public void setModulo(int modulo) {
    this.modulo = modulo;
  }

  public int getSeccion() {
    return seccion;
  }

  public void setSeccion(int seccion) {
    this.seccion = seccion;
  }

  public int getNumBitacora() {
    return numBitacora;
  }

  public void setNumBitacora(int numBitacora) {
    this.numBitacora = numBitacora;
  }

  public String getObservaciones() {
    return observaciones;
  }

  public void setObservaciones(String observaciones) {
    this.observaciones = observaciones;
  }

  public int getIdSeguimiento() {
    return idSeguimiento;
  }

  public void setIdSeguimiento(int idSeguimiento) {
    this.idSeguimiento = idSeguimiento;
  }
  
  
}
