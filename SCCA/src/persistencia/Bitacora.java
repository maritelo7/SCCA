/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author marianacro
 */
@Entity
@Table(name = "Bitacora")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bitacora.findAll", query = "SELECT b FROM Bitacora b")
    , @NamedQuery(name = "Bitacora.findByIdBitacora", query = "SELECT b FROM Bitacora b WHERE b.idBitacora = :idBitacora")
    , @NamedQuery(name = "Bitacora.findByFechaEntrega", query = "SELECT b FROM Bitacora b WHERE b.fechaEntrega = :fechaEntrega")
    , @NamedQuery(name = "Bitacora.findByCalificacionAutoevaluacion", query = "SELECT b FROM Bitacora b WHERE b.calificacionAutoevaluacion = :calificacionAutoevaluacion")
    , @NamedQuery(name = "Bitacora.findByModulo", query = "SELECT b FROM Bitacora b WHERE b.modulo = :modulo")
    , @NamedQuery(name = "Bitacora.findBySeccion", query = "SELECT b FROM Bitacora b WHERE b.seccion = :seccion")
    , @NamedQuery(name = "Bitacora.findByNumBitacora", query = "SELECT b FROM Bitacora b WHERE b.numBitacora = :numBitacora")
    , @NamedQuery(name = "Bitacora.findByObservaciones", query = "SELECT b FROM Bitacora b WHERE b.observaciones = :observaciones")})
public class Bitacora implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idBitacora")
    private Integer idBitacora;
    @Basic(optional = false)
    @Column(name = "fechaEntrega")
    @Temporal(TemporalType.DATE)
    private Date fechaEntrega;
    @Basic(optional = false)
    @Column(name = "calificacionAutoevaluacion")
    private int calificacionAutoevaluacion;
    @Basic(optional = false)
    @Column(name = "modulo")
    private int modulo;
    @Basic(optional = false)
    @Column(name = "seccion")
    private int seccion;
    @Basic(optional = false)
    @Column(name = "numBitacora")
    private int numBitacora;
    @Basic(optional = false)
    @Column(name = "observaciones")
    private String observaciones;
    @JoinColumn(name = "idSeguimiento", referencedColumnName = "idSeguimiento")
    @ManyToOne(optional = false)
    private Seguimiento idSeguimiento;

    public Bitacora() {
    }

    public Bitacora(Integer idBitacora) {
        this.idBitacora = idBitacora;
    }

    public Bitacora(Integer idBitacora, Date fechaEntrega, int calificacionAutoevaluacion, int modulo, int seccion, int numBitacora, String observaciones) {
        this.idBitacora = idBitacora;
        this.fechaEntrega = fechaEntrega;
        this.calificacionAutoevaluacion = calificacionAutoevaluacion;
        this.modulo = modulo;
        this.seccion = seccion;
        this.numBitacora = numBitacora;
        this.observaciones = observaciones;
    }

    public Integer getIdBitacora() {
        return idBitacora;
    }

    public void setIdBitacora(Integer idBitacora) {
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

    public Seguimiento getIdSeguimiento() {
        return idSeguimiento;
    }

    public void setIdSeguimiento(Seguimiento idSeguimiento) {
        this.idSeguimiento = idSeguimiento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBitacora != null ? idBitacora.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bitacora)) {
            return false;
        }
        Bitacora other = (Bitacora) object;
        if ((this.idBitacora == null && other.idBitacora != null) || (this.idBitacora != null && !this.idBitacora.equals(other.idBitacora))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Persistencia.Bitacora[ idBitacora=" + idBitacora + " ]";
    }
    
}
