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
@Table(name = "Conversacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Conversacion.findAll", query = "SELECT c FROM Conversacion c")
    , @NamedQuery(name = "Conversacion.findByIdConversacion", query = "SELECT c FROM Conversacion c WHERE c.idConversacion = :idConversacion")
    , @NamedQuery(name = "Conversacion.findByCupo", query = "SELECT c FROM Conversacion c WHERE c.cupo = :cupo")
    , @NamedQuery(name = "Conversacion.findByCodigo", query = "SELECT c FROM Conversacion c WHERE c.codigo = :codigo")})
public class Conversacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idConversacion")
    private Integer idConversacion;
    @Basic(optional = false)
    @Column(name = "cupo")
    private int cupo;
    @Basic(optional = false)
    @Column(name = "codigo")
    private int codigo;
    @JoinColumn(name = "idReservacion", referencedColumnName = "idReservacion")
    @ManyToOne(optional = false)
    private Reservacion idReservacion;

    public Conversacion() {
    }

    public Conversacion(Integer idConversacion) {
        this.idConversacion = idConversacion;
    }

    public Conversacion(Integer idConversacion, int cupo, int codigo) {
        this.idConversacion = idConversacion;
        this.cupo = cupo;
        this.codigo = codigo;
    }

    public Integer getIdConversacion() {
        return idConversacion;
    }

    public void setIdConversacion(Integer idConversacion) {
        this.idConversacion = idConversacion;
    }

    public int getCupo() {
        return cupo;
    }

    public void setCupo(int cupo) {
        this.cupo = cupo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
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
        return "persistencia.Conversacion[ idConversacion=" + idConversacion + " ]";
    }
    
}
