/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "Asesor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Asesor.findAll", query = "SELECT a FROM Asesor a")
    , @NamedQuery(name = "Asesor.findByNumPersonal", query = "SELECT a FROM Asesor a WHERE a.numPersonal = :numPersonal")
    , @NamedQuery(name = "Asesor.findByApellidoPaterno", query = "SELECT a FROM Asesor a WHERE a.apellidoPaterno = :apellidoPaterno")
    , @NamedQuery(name = "Asesor.findByApellidoMaterno", query = "SELECT a FROM Asesor a WHERE a.apellidoMaterno = :apellidoMaterno")
    , @NamedQuery(name = "Asesor.findByCorreo", query = "SELECT a FROM Asesor a WHERE a.correo = :correo")
    , @NamedQuery(name = "Asesor.findByNombre", query = "SELECT a FROM Asesor a WHERE a.nombre = :nombre")
    , @NamedQuery(name = "Asesor.findByNombreUsuario", query = "SELECT a FROM Asesor a WHERE a.nombreUsuario = :nombreUsuario")
    , @NamedQuery(name = "Asesor.findByClaveUsuario", query = "SELECT a FROM Asesor a WHERE a.claveUsuario = :claveUsuario")
    , @NamedQuery(name = "Asesor.findByCarreraDeInteres", query = "SELECT a FROM Asesor a WHERE a.carreraDeInteres = :carreraDeInteres")
    , @NamedQuery(name = "Asesor.findByTipoContrataci\u00f3n", query = "SELECT a FROM Asesor a WHERE a.tipoContrataci\u00f3n = :tipoContrataci\u00f3n")
    , @NamedQuery(name = "Asesor.findByFechaContratacion", query = "SELECT a FROM Asesor a WHERE a.fechaContratacion = :fechaContratacion")})
public class Asesor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "numPersonal")
    private String numPersonal;
    @Basic(optional = false)
    @Column(name = "apellidoPaterno")
    private String apellidoPaterno;
    @Column(name = "apellidoMaterno")
    private String apellidoMaterno;
    @Basic(optional = false)
    @Column(name = "correo")
    private String correo;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "nombreUsuario")
    private String nombreUsuario;
    @Basic(optional = false)
    @Column(name = "claveUsuario")
    private String claveUsuario;
    @Column(name = "carreraDeInteres")
    private String carreraDeInteres;
    @Column(name = "tipoContrataci\u00f3n")
    private String tipoContratación;
    @Basic(optional = false)
    @Column(name = "fechaContratacion")
    @Temporal(TemporalType.DATE)
    private Date fechaContratacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "numPersonal")
    private Collection<Asesoria> asesoriaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "numPersonal")
    private Collection<ExperienciaEducativa> experienciaEducativaCollection;

    public Asesor() {
    }

    public Asesor(String numPersonal) {
        this.numPersonal = numPersonal;
    }

    public Asesor(String numPersonal, String apellidoPaterno, String correo, String nombre, String nombreUsuario, String claveUsuario, Date fechaContratacion) {
        this.numPersonal = numPersonal;
        this.apellidoPaterno = apellidoPaterno;
        this.correo = correo;
        this.nombre = nombre;
        this.nombreUsuario = nombreUsuario;
        this.claveUsuario = claveUsuario;
        this.fechaContratacion = fechaContratacion;
    }

    public String getNumPersonal() {
        return numPersonal;
    }

    public void setNumPersonal(String numPersonal) {
        this.numPersonal = numPersonal;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getClaveUsuario() {
        return claveUsuario;
    }

    public void setClaveUsuario(String claveUsuario) {
        this.claveUsuario = claveUsuario;
    }

    public String getCarreraDeInteres() {
        return carreraDeInteres;
    }

    public void setCarreraDeInteres(String carreraDeInteres) {
        this.carreraDeInteres = carreraDeInteres;
    }

    public String getTipoContratación() {
        return tipoContratación;
    }

    public void setTipoContratación(String tipoContratación) {
        this.tipoContratación = tipoContratación;
    }

    public Date getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(Date fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    @XmlTransient
    public Collection<Asesoria> getAsesoriaCollection() {
        return asesoriaCollection;
    }

    public void setAsesoriaCollection(Collection<Asesoria> asesoriaCollection) {
        this.asesoriaCollection = asesoriaCollection;
    }

    @XmlTransient
    public Collection<ExperienciaEducativa> getExperienciaEducativaCollection() {
        return experienciaEducativaCollection;
    }

    public void setExperienciaEducativaCollection(Collection<ExperienciaEducativa> experienciaEducativaCollection) {
        this.experienciaEducativaCollection = experienciaEducativaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numPersonal != null ? numPersonal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Asesor)) {
            return false;
        }
        Asesor other = (Asesor) object;
        if ((this.numPersonal == null && other.numPersonal != null) || (this.numPersonal != null && !this.numPersonal.equals(other.numPersonal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistencia.Asesor[ numPersonal=" + numPersonal + " ]";
    }
    
}
