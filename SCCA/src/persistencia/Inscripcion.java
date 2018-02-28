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
@Table(name = "inscripcion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Inscripcion.findAll", query = "SELECT i FROM Inscripcion i")
    , @NamedQuery(name = "Inscripcion.findByNumInscripcion", query = "SELECT i FROM Inscripcion i WHERE i.numInscripcion = :numInscripcion")
    , @NamedQuery(name = "Inscripcion.findBySemestre", query = "SELECT i FROM Inscripcion i WHERE i.semestre = :semestre")
    , @NamedQuery(name = "Inscripcion.findByNumPersonal", query = "SELECT i FROM Inscripcion i WHERE i.numPersonal = :numPersonal")})
public class Inscripcion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "numInscripcion")
    private Integer numInscripcion;
    @Basic(optional = false)
    @Column(name = "semestre")
    private String semestre;
    @Basic(optional = false)
    @Column(name = "numPersonal")
    private String numPersonal;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "numInscripcion")
    private Collection<Seguimiento> seguimientoCollection;
    @JoinColumn(name = "matricula", referencedColumnName = "matricula")
    @ManyToOne(optional = false)
    private Alumno matricula;
    @JoinColumn(name = "nrc", referencedColumnName = "nrc")
    @ManyToOne(optional = false)
    private ExperienciaEducativa nrc;

    public Inscripcion() {
    }

    public Inscripcion(Integer numInscripcion) {
        this.numInscripcion = numInscripcion;
    }

    public Inscripcion(Integer numInscripcion, String semestre, String numPersonal) {
        this.numInscripcion = numInscripcion;
        this.semestre = semestre;
        this.numPersonal = numPersonal;
    }

    public Integer getNumInscripcion() {
        return numInscripcion;
    }

    public void setNumInscripcion(Integer numInscripcion) {
        this.numInscripcion = numInscripcion;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public String getNumPersonal() {
        return numPersonal;
    }

    public void setNumPersonal(String numPersonal) {
        this.numPersonal = numPersonal;
    }

    @XmlTransient
    public Collection<Seguimiento> getSeguimientoCollection() {
        return seguimientoCollection;
    }

    public void setSeguimientoCollection(Collection<Seguimiento> seguimientoCollection) {
        this.seguimientoCollection = seguimientoCollection;
    }

    public Alumno getMatricula() {
        return matricula;
    }

    public void setMatricula(Alumno matricula) {
        this.matricula = matricula;
    }

    public ExperienciaEducativa getNrc() {
        return nrc;
    }

    public void setNrc(ExperienciaEducativa nrc) {
        this.nrc = nrc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numInscripcion != null ? numInscripcion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Inscripcion)) {
            return false;
        }
        Inscripcion other = (Inscripcion) object;
        if ((this.numInscripcion == null && other.numInscripcion != null) || (this.numInscripcion != null && !this.numInscripcion.equals(other.numInscripcion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistencia.Inscripcion[ numInscripcion=" + numInscripcion + " ]";
    }
    
}
