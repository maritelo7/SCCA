/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author marianacro
 */
@Entity
@Table(name = "Reservacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reservacion.findAll", query = "SELECT r FROM Reservacion r")
    , @NamedQuery(name = "Reservacion.findByIdReservacion", query = "SELECT r FROM Reservacion r WHERE r.idReservacion = :idReservacion")
    , @NamedQuery(name = "Reservacion.findByFechaReservacion", query = "SELECT r FROM Reservacion r WHERE r.fechaReservacion = :fechaReservacion")})
public class Reservacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idReservacion")
    private Integer idReservacion;
    @Basic(optional = false)
    @Column(name = "fechaReservacion")
    @Temporal(TemporalType.DATE)
    private Date fechaReservacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idReservacion")
    private List<Conversacion> conversacionList;
    @JoinColumn(name = "idSeguimiento", referencedColumnName = "idSeguimiento")
    @ManyToOne(optional = false)
    private Seguimiento idSeguimiento;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idReservacion")
    private List<Asesoria> asesoriaList;

    public Reservacion() {
    }

    public Reservacion(Integer idReservacion) {
        this.idReservacion = idReservacion;
    }

    public Reservacion(Integer idReservacion, Date fechaReservacion) {
        this.idReservacion = idReservacion;
        this.fechaReservacion = fechaReservacion;
    }

    public Integer getIdReservacion() {
        return idReservacion;
    }

    public void setIdReservacion(Integer idReservacion) {
        this.idReservacion = idReservacion;
    }

    public Date getFechaReservacion() {
        return fechaReservacion;
    }

    public void setFechaReservacion(Date fechaReservacion) {
        this.fechaReservacion = fechaReservacion;
    }

    @XmlTransient
    public List<Conversacion> getConversacionList() {
        return conversacionList;
    }

    public void setConversacionList(List<Conversacion> conversacionList) {
        this.conversacionList = conversacionList;
    }

    public Seguimiento getIdSeguimiento() {
        return idSeguimiento;
    }

    public void setIdSeguimiento(Seguimiento idSeguimiento) {
        this.idSeguimiento = idSeguimiento;
    }

    @XmlTransient
    public List<Asesoria> getAsesoriaList() {
        return asesoriaList;
    }

    public void setAsesoriaList(List<Asesoria> asesoriaList) {
        this.asesoriaList = asesoriaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idReservacion != null ? idReservacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reservacion)) {
            return false;
        }
        Reservacion other = (Reservacion) object;
        if ((this.idReservacion == null && other.idReservacion != null) || (this.idReservacion != null && !this.idReservacion.equals(other.idReservacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Persistencia.Reservacion[ idReservacion=" + idReservacion + " ]";
    }
    
}
