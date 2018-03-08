/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import java.io.Serializable;
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
    , @NamedQuery(name = "Seguimiento.findByIdSeguimiento", query = "SELECT s FROM Seguimiento s WHERE s.idSeguimiento = :idSeguimiento")
    , @NamedQuery(name = "Seguimiento.findByCalifPrimerParcial", query = "SELECT s FROM Seguimiento s WHERE s.califPrimerParcial = :califPrimerParcial")
    , @NamedQuery(name = "Seguimiento.findByCalifSegundoParcial", query = "SELECT s FROM Seguimiento s WHERE s.califSegundoParcial = :califSegundoParcial")
    , @NamedQuery(name = "Seguimiento.findByCalifProyecto", query = "SELECT s FROM Seguimiento s WHERE s.califProyecto = :califProyecto")
    , @NamedQuery(name = "Seguimiento.findByCalifFinal", query = "SELECT s FROM Seguimiento s WHERE s.califFinal = :califFinal")})
public class Seguimiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idSeguimiento")
    private Integer idSeguimiento;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "califPrimerParcial")
    private Float califPrimerParcial;
    @Column(name = "califSegundoParcial")
    private Float califSegundoParcial;
    @Column(name = "califProyecto")
    private Float califProyecto;
    @Column(name = "califFinal")
    private Float califFinal;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSeguimiento")
    private List<ObservacionGeneral> observacionGeneralList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSeguimiento")
    private List<HojaSeguimiento> hojaSeguimientoList;
    @JoinColumn(name = "numInscripcion", referencedColumnName = "numInscripcion")
    @ManyToOne(optional = false)
    private Inscripcion numInscripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSeguimiento")
    private List<Reservacion> reservacionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSeguimiento")
    private List<ConversacionHasSeguimiento> conversacionHasSeguimientoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSeguimiento")
    private List<TallerHasSeguimiento> tallerHasSeguimientoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSeguimiento")
    private List<Asesoria> asesoriaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSeguimiento")
    private List<Bitacora> bitacoraList;

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

    public Float getCalifPrimerParcial() {
        return califPrimerParcial;
    }

    public void setCalifPrimerParcial(Float califPrimerParcial) {
        this.califPrimerParcial = califPrimerParcial;
    }

    public Float getCalifSegundoParcial() {
        return califSegundoParcial;
    }

    public void setCalifSegundoParcial(Float califSegundoParcial) {
        this.califSegundoParcial = califSegundoParcial;
    }

    public Float getCalifProyecto() {
        return califProyecto;
    }

    public void setCalifProyecto(Float califProyecto) {
        this.califProyecto = califProyecto;
    }

    public Float getCalifFinal() {
        return califFinal;
    }

    public void setCalifFinal(Float califFinal) {
        this.califFinal = califFinal;
    }

    @XmlTransient
    public List<ObservacionGeneral> getObservacionGeneralList() {
        return observacionGeneralList;
    }

    public void setObservacionGeneralList(List<ObservacionGeneral> observacionGeneralList) {
        this.observacionGeneralList = observacionGeneralList;
    }

    @XmlTransient
    public List<HojaSeguimiento> getHojaSeguimientoList() {
        return hojaSeguimientoList;
    }

    public void setHojaSeguimientoList(List<HojaSeguimiento> hojaSeguimientoList) {
        this.hojaSeguimientoList = hojaSeguimientoList;
    }

    public Inscripcion getNumInscripcion() {
        return numInscripcion;
    }

    public void setNumInscripcion(Inscripcion numInscripcion) {
        this.numInscripcion = numInscripcion;
    }

    @XmlTransient
    public List<Reservacion> getReservacionList() {
        return reservacionList;
    }

    public void setReservacionList(List<Reservacion> reservacionList) {
        this.reservacionList = reservacionList;
    }

    @XmlTransient
    public List<ConversacionHasSeguimiento> getConversacionHasSeguimientoList() {
        return conversacionHasSeguimientoList;
    }

    public void setConversacionHasSeguimientoList(List<ConversacionHasSeguimiento> conversacionHasSeguimientoList) {
        this.conversacionHasSeguimientoList = conversacionHasSeguimientoList;
    }

    @XmlTransient
    public List<TallerHasSeguimiento> getTallerHasSeguimientoList() {
        return tallerHasSeguimientoList;
    }

    public void setTallerHasSeguimientoList(List<TallerHasSeguimiento> tallerHasSeguimientoList) {
        this.tallerHasSeguimientoList = tallerHasSeguimientoList;
    }

    @XmlTransient
    public List<Asesoria> getAsesoriaList() {
        return asesoriaList;
    }

    public void setAsesoriaList(List<Asesoria> asesoriaList) {
        this.asesoriaList = asesoriaList;
    }

    @XmlTransient
    public List<Bitacora> getBitacoraList() {
        return bitacoraList;
    }

    public void setBitacoraList(List<Bitacora> bitacoraList) {
        this.bitacoraList = bitacoraList;
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
        return "Persistencia.Seguimiento[ idSeguimiento=" + idSeguimiento + " ]";
    }
    
}
