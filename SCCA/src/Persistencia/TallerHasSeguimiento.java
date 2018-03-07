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
@Table(name = "taller_has_seguimiento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TallerHasSeguimiento.findAll", query = "SELECT t FROM TallerHasSeguimiento t")
    , @NamedQuery(name = "TallerHasSeguimiento.findByIdTaller", query = "SELECT t FROM TallerHasSeguimiento t WHERE t.idTaller = :idTaller")
    , @NamedQuery(name = "TallerHasSeguimiento.findByIdTallerSeguimiento", query = "SELECT t FROM TallerHasSeguimiento t WHERE t.idTallerSeguimiento = :idTallerSeguimiento")})
public class TallerHasSeguimiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "idTaller")
    private int idTaller;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTaller_Seguimiento")
    private Integer idTallerSeguimiento;
    @JoinColumn(name = "idSeguimiento", referencedColumnName = "idSeguimiento")
    @ManyToOne(optional = false)
    private Seguimiento idSeguimiento;

    public TallerHasSeguimiento() {
    }

    public TallerHasSeguimiento(Integer idTallerSeguimiento) {
        this.idTallerSeguimiento = idTallerSeguimiento;
    }

    public TallerHasSeguimiento(Integer idTallerSeguimiento, int idTaller) {
        this.idTallerSeguimiento = idTallerSeguimiento;
        this.idTaller = idTaller;
    }

    public int getIdTaller() {
        return idTaller;
    }

    public void setIdTaller(int idTaller) {
        this.idTaller = idTaller;
    }

    public Integer getIdTallerSeguimiento() {
        return idTallerSeguimiento;
    }

    public void setIdTallerSeguimiento(Integer idTallerSeguimiento) {
        this.idTallerSeguimiento = idTallerSeguimiento;
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
        hash += (idTallerSeguimiento != null ? idTallerSeguimiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TallerHasSeguimiento)) {
            return false;
        }
        TallerHasSeguimiento other = (TallerHasSeguimiento) object;
        if ((this.idTallerSeguimiento == null && other.idTallerSeguimiento != null) || (this.idTallerSeguimiento != null && !this.idTallerSeguimiento.equals(other.idTallerSeguimiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Persistencia.TallerHasSeguimiento[ idTallerSeguimiento=" + idTallerSeguimiento + " ]";
    }
    
}
