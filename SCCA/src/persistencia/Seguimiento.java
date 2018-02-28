/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.io.Serializable;
import java.util.Collection;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author marianacro
 */
@Entity
@Table(name = "seguimiento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Seguimiento.findAll", query = "SELECT s FROM Seguimiento s")
    , @NamedQuery(name = "Seguimiento.findByIdSeguimiento", query = "SELECT s FROM Seguimiento s WHERE s.idSeguimiento = :idSeguimiento")})
public class Seguimiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idSeguimiento")
    private Integer idSeguimiento;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSeguimiento")
    private Collection<Asesoria> asesoriaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSeguimiento")
    private Collection<ObservacionGeneral> observacionGeneralCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSeguimiento")
    private Collection<HojaSeguimiento> hojaSeguimientoCollection;
    @JoinColumn(name = "numInscripcion", referencedColumnName = "numInscripcion")
    @ManyToOne(optional = false)
    private Inscripcion numInscripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSeguimiento")
    private Collection<Reservacion> reservacionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSeguimiento")
    private Collection<Bitacora> bitacoraCollection;

    public Seguimiento() {
    }

    public Seguimiento(Integer idSeguimiento) {
        this.idSeguimiento = idSeguimiento;
    }

    public Integer getIdSeguimiento() {
        return idSeguimiento;
    }

    public void setIdSeguimiento(Integer idSeguimiento) {
        this.idSeguimiento = idSeguimiento;
    }

    @XmlTransient
    public Collection<Asesoria> getAsesoriaCollection() {
        return asesoriaCollection;
    }

    public void setAsesoriaCollection(Collection<Asesoria> asesoriaCollection) {
        this.asesoriaCollection = asesoriaCollection;
    }

    @XmlTransient
    public Collection<ObservacionGeneral> getObservacionGeneralCollection() {
        return observacionGeneralCollection;
    }

    public void setObservacionGeneralCollection(Collection<ObservacionGeneral> observacionGeneralCollection) {
        this.observacionGeneralCollection = observacionGeneralCollection;
    }

    @XmlTransient
    public Collection<HojaSeguimiento> getHojaSeguimientoCollection() {
        return hojaSeguimientoCollection;
    }

    public void setHojaSeguimientoCollection(Collection<HojaSeguimiento> hojaSeguimientoCollection) {
        this.hojaSeguimientoCollection = hojaSeguimientoCollection;
    }

    public Inscripcion getNumInscripcion() {
        return numInscripcion;
    }

    public void setNumInscripcion(Inscripcion numInscripcion) {
        this.numInscripcion = numInscripcion;
    }

    @XmlTransient
    public Collection<Reservacion> getReservacionCollection() {
        return reservacionCollection;
    }

    public void setReservacionCollection(Collection<Reservacion> reservacionCollection) {
        this.reservacionCollection = reservacionCollection;
    }

    @XmlTransient
    public Collection<Bitacora> getBitacoraCollection() {
        return bitacoraCollection;
    }

    public void setBitacoraCollection(Collection<Bitacora> bitacoraCollection) {
        this.bitacoraCollection = bitacoraCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSeguimiento != null ? idSeguimiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Seguimiento)) {
            return false;
        }
        Seguimiento other = (Seguimiento) object;
        if ((this.idSeguimiento == null && other.idSeguimiento != null) || (this.idSeguimiento != null && !this.idSeguimiento.equals(other.idSeguimiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistencia.Seguimiento[ idSeguimiento=" + idSeguimiento + " ]";
    }
    
}
