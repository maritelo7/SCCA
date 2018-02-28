/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author marianacro
 */
@Entity
@Table(name = "Asesoria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Asesoria.findAll", query = "SELECT a FROM Asesoria a")
    , @NamedQuery(name = "Asesoria.findByIdAsesoria", query = "SELECT a FROM Asesoria a WHERE a.idAsesoria = :idAsesoria")
    , @NamedQuery(name = "Asesoria.findByAsunto", query = "SELECT a FROM Asesoria a WHERE a.asunto = :asunto")})
public class Asesoria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idAsesoria")
    private Integer idAsesoria;
    @Basic(optional = false)
    @Column(name = "asunto")
    private String asunto;
    @JoinColumn(name = "numPersonal", referencedColumnName = "numPersonal")
    @ManyToOne(optional = false)
    private Asesor numPersonal;
    @JoinColumn(name = "idReservacion", referencedColumnName = "idReservacion")
    @ManyToOne(optional = false)
    private Reservacion idReservacion;
    @JoinColumn(name = "idSeguimiento", referencedColumnName = "idSeguimiento")
    @ManyToOne(optional = false)
    private Seguimiento idSeguimiento;

    public Asesoria() {
    }

    public Asesoria(Integer idAsesoria) {
        this.idAsesoria = idAsesoria;
    }

    public Asesoria(Integer idAsesoria, String asunto) {
        this.idAsesoria = idAsesoria;
        this.asunto = asunto;
    }

    public Integer getIdAsesoria() {
        return idAsesoria;
    }

    public void setIdAsesoria(Integer idAsesoria) {
        this.idAsesoria = idAsesoria;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public Asesor getNumPersonal() {
        return numPersonal;
    }

    public void setNumPersonal(Asesor numPersonal) {
        this.numPersonal = numPersonal;
    }

    public Reservacion getIdReservacion() {
        return idReservacion;
    }

    public void setIdReservacion(Reservacion idReservacion) {
        this.idReservacion = idReservacion;
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
        hash += (idAsesoria != null ? idAsesoria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Asesoria)) {
            return false;
        }
        Asesoria other = (Asesoria) object;
        if ((this.idAsesoria == null && other.idAsesoria != null) || (this.idAsesoria != null && !this.idAsesoria.equals(other.idAsesoria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistencia.Asesoria[ idAsesoria=" + idAsesoria + " ]";
    }
    
}
