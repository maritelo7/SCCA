/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

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
@Table(name = "conversacion_has_seguimiento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConversacionHasSeguimiento.findAll", query = "SELECT c FROM ConversacionHasSeguimiento c")
    , @NamedQuery(name = "ConversacionHasSeguimiento.findByIdConversacion", query = "SELECT c FROM ConversacionHasSeguimiento c WHERE c.idConversacion = :idConversacion")
    , @NamedQuery(name = "ConversacionHasSeguimiento.findByIdConversacionSeguimiento", query = "SELECT c FROM ConversacionHasSeguimiento c WHERE c.idConversacionSeguimiento = :idConversacionSeguimiento")})
public class ConversacionHasSeguimiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "idConversacion")
    private int idConversacion;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idConversacion_Seguimiento")
    private Integer idConversacionSeguimiento;
    @JoinColumn(name = "idSeguimiento", referencedColumnName = "idSeguimiento")
    @ManyToOne(optional = false)
    private Seguimiento idSeguimiento;

    public ConversacionHasSeguimiento() {
    }

    public ConversacionHasSeguimiento(Integer idConversacionSeguimiento) {
        this.idConversacionSeguimiento = idConversacionSeguimiento;
    }

    public ConversacionHasSeguimiento(Integer idConversacionSeguimiento, int idConversacion) {
        this.idConversacionSeguimiento = idConversacionSeguimiento;
        this.idConversacion = idConversacion;
    }

    public int getIdConversacion() {
        return idConversacion;
    }

    public void setIdConversacion(int idConversacion) {
        this.idConversacion = idConversacion;
    }

    public Integer getIdConversacionSeguimiento() {
        return idConversacionSeguimiento;
    }

    public void setIdConversacionSeguimiento(Integer idConversacionSeguimiento) {
        this.idConversacionSeguimiento = idConversacionSeguimiento;
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
        hash += (idConversacionSeguimiento != null ? idConversacionSeguimiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConversacionHasSeguimiento)) {
            return false;
        }
        ConversacionHasSeguimiento other = (ConversacionHasSeguimiento) object;
        if ((this.idConversacionSeguimiento == null && other.idConversacionSeguimiento != null) || (this.idConversacionSeguimiento != null && !this.idConversacionSeguimiento.equals(other.idConversacionSeguimiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Persistencia.ConversacionHasSeguimiento[ idConversacionSeguimiento=" + idConversacionSeguimiento + " ]";
    }
    
}
