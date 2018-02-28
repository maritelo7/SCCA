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
@Table(name = "ExperienciaEducativa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ExperienciaEducativa.findAll", query = "SELECT e FROM ExperienciaEducativa e")
    , @NamedQuery(name = "ExperienciaEducativa.findByNrc", query = "SELECT e FROM ExperienciaEducativa e WHERE e.nrc = :nrc")
    , @NamedQuery(name = "ExperienciaEducativa.findByPeriodo", query = "SELECT e FROM ExperienciaEducativa e WHERE e.periodo = :periodo")
    , @NamedQuery(name = "ExperienciaEducativa.findByCupo", query = "SELECT e FROM ExperienciaEducativa e WHERE e.cupo = :cupo")})
public class ExperienciaEducativa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "nrc")
    private Integer nrc;
    @Basic(optional = false)
    @Column(name = "periodo")
    private String periodo;
    @Column(name = "cupo")
    private Integer cupo;
    @JoinColumn(name = "numPersonal", referencedColumnName = "numPersonal")
    @ManyToOne(optional = false)
    private Asesor numPersonal;
    @JoinColumn(name = "codigo", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private CatalogoEE codigo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nrc")
    private Collection<Inscripcion> inscripcionCollection;

    public ExperienciaEducativa() {
    }

    public ExperienciaEducativa(Integer nrc) {
        this.nrc = nrc;
    }

    public ExperienciaEducativa(Integer nrc, String periodo) {
        this.nrc = nrc;
        this.periodo = periodo;
    }

    public Integer getNrc() {
        return nrc;
    }

    public void setNrc(Integer nrc) {
        this.nrc = nrc;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public Integer getCupo() {
        return cupo;
    }

    public void setCupo(Integer cupo) {
        this.cupo = cupo;
    }

    public Asesor getNumPersonal() {
        return numPersonal;
    }

    public void setNumPersonal(Asesor numPersonal) {
        this.numPersonal = numPersonal;
    }

    public CatalogoEE getCodigo() {
        return codigo;
    }

    public void setCodigo(CatalogoEE codigo) {
        this.codigo = codigo;
    }

    @XmlTransient
    public Collection<Inscripcion> getInscripcionCollection() {
        return inscripcionCollection;
    }

    public void setInscripcionCollection(Collection<Inscripcion> inscripcionCollection) {
        this.inscripcionCollection = inscripcionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nrc != null ? nrc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ExperienciaEducativa)) {
            return false;
        }
        ExperienciaEducativa other = (ExperienciaEducativa) object;
        if ((this.nrc == null && other.nrc != null) || (this.nrc != null && !this.nrc.equals(other.nrc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistencia.ExperienciaEducativa[ nrc=" + nrc + " ]";
    }
    
}
