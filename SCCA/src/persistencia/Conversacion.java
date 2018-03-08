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
@Table(name = "conversacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Conversacion.findAll", query = "SELECT c FROM Conversacion c")
    , @NamedQuery(name = "Conversacion.findByIdConversacion", query = "SELECT c FROM Conversacion c WHERE c.idConversacion = :idConversacion")
    , @NamedQuery(name = "Conversacion.findByCupoActual", query = "SELECT c FROM Conversacion c WHERE c.cupoActual = :cupoActual")
    , @NamedQuery(name = "Conversacion.findByCodigo", query = "SELECT c FROM Conversacion c WHERE c.codigo = :codigo")
    , @NamedQuery(name = "Conversacion.findByCupoMaximo", query = "SELECT c FROM Conversacion c WHERE c.cupoMaximo = :cupoMaximo")
    , @NamedQuery(name = "Conversacion.findByFecha", query = "SELECT c FROM Conversacion c WHERE c.fecha = :fecha")
    , @NamedQuery(name = "Conversacion.findByHora", query = "SELECT c FROM Conversacion c WHERE c.hora = :hora")
    , @NamedQuery(name = "Conversacion.findByLugar", query = "SELECT c FROM Conversacion c WHERE c.lugar = :lugar")})
public class Conversacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idConversacion")
    private Integer idConversacion;
    @Basic(optional = false)
    @Column(name = "cupoActual")
    private int cupoActual;
    @Basic(optional = false)
    @Column(name = "codigo")
    private int codigo;
    @Column(name = "cupoMaximo")
    private Integer cupoMaximo;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "hora")
    private String hora;
    @Column(name = "lugar")
    private String lugar;
    @JoinColumn(name = "idReservacion", referencedColumnName = "idReservacion")
    @ManyToOne(optional = false)
    private Reservacion idReservacion;

    public Conversacion() {
    }

    public Conversacion(Integer idConversacion) {
        this.idConversacion = idConversacion;
    }

    public Conversacion(Integer idConversacion, int cupoActual, int codigo) {
        this.idConversacion = idConversacion;
        this.cupoActual = cupoActual;
        this.codigo = codigo;
    }

    public Integer getIdConversacion() {
        return idConversacion;
    }

    public void setIdConversacion(Integer idConversacion) {
        this.idConversacion = idConversacion;
    }

    public int getCupoActual() {
        return cupoActual;
    }

    public void setCupoActual(int cupoActual) {
        this.cupoActual = cupoActual;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Integer getCupoMaximo() {
        return cupoMaximo;
    }

    public void setCupoMaximo(Integer cupoMaximo) {
        this.cupoMaximo = cupoMaximo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public Reservacion getIdReservacion() {
        return idReservacion;
    }

    public void setIdReservacion(Reservacion idReservacion) {
        this.idReservacion = idReservacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idConversacion != null ? idConversacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Conversacion)) {
            return false;
        }
        Conversacion other = (Conversacion) object;
        if ((this.idConversacion == null && other.idConversacion != null) || (this.idConversacion != null && !this.idConversacion.equals(other.idConversacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Persistencia.Conversacion[ idConversacion=" + idConversacion + " ]";
    }
    
}
