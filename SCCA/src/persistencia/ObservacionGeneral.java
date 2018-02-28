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
@Table(name = "ObservacionGeneral")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ObservacionGeneral.findAll", query = "SELECT o FROM ObservacionGeneral o")
    , @NamedQuery(name = "ObservacionGeneral.findByIdObservacionGral", query = "SELECT o FROM ObservacionGeneral o WHERE o.idObservacionGral = :idObservacionGral")
    , @NamedQuery(name = "ObservacionGeneral.findByComentario", query = "SELECT o FROM ObservacionGeneral o WHERE o.comentario = :comentario")})
public class ObservacionGeneral implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idObservacionGral")
    private Integer idObservacionGral;
    @Basic(optional = false)
    @Column(name = "comentario")
    private String comentario;
    @JoinColumn(name = "idSeguimiento", referencedColumnName = "idSeguimiento")
    @ManyToOne(optional = false)
    private Seguimiento idSeguimiento;

    public ObservacionGeneral() {
    }

    public ObservacionGeneral(Integer idObservacionGral) {
        this.idObservacionGral = idObservacionGral;
    }

    public ObservacionGeneral(Integer idObservacionGral, String comentario) {
        this.idObservacionGral = idObservacionGral;
        this.comentario = comentario;
    }

    public Integer getIdObservacionGral() {
        return idObservacionGral;
    }

    public void setIdObservacionGral(Integer idObservacionGral) {
        this.idObservacionGral = idObservacionGral;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
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
        hash += (idObservacionGral != null ? idObservacionGral.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ObservacionGeneral)) {
            return false;
        }
        ObservacionGeneral other = (ObservacionGeneral) object;
        if ((this.idObservacionGral == null && other.idObservacionGral != null) || (this.idObservacionGral != null && !this.idObservacionGral.equals(other.idObservacionGral))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistencia.ObservacionGeneral[ idObservacionGral=" + idObservacionGral + " ]";
    }
    
}
