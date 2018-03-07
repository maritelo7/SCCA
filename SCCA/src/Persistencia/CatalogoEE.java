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
import javax.persistence.Id;
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
@Table(name = "CatalogoEE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CatalogoEE.findAll", query = "SELECT c FROM CatalogoEE c")
    , @NamedQuery(name = "CatalogoEE.findByCodigo", query = "SELECT c FROM CatalogoEE c WHERE c.codigo = :codigo")
    , @NamedQuery(name = "CatalogoEE.findByHorasPractica", query = "SELECT c FROM CatalogoEE c WHERE c.horasPractica = :horasPractica")
    , @NamedQuery(name = "CatalogoEE.findByHorasTeoria", query = "SELECT c FROM CatalogoEE c WHERE c.horasTeoria = :horasTeoria")
    , @NamedQuery(name = "CatalogoEE.findByNombreEE", query = "SELECT c FROM CatalogoEE c WHERE c.nombreEE = :nombreEE")})
public class CatalogoEE implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Basic(optional = false)
    @Column(name = "horasPractica")
    private int horasPractica;
    @Basic(optional = false)
    @Column(name = "horasTeoria")
    private int horasTeoria;
    @Basic(optional = false)
    @Column(name = "nombreEE")
    private String nombreEE;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigo")
    private List<ExperienciaEducativa> experienciaEducativaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigo")
    private List<Seccion> seccionList;

    public CatalogoEE() {
    }

    public CatalogoEE(Integer codigo) {
        this.codigo = codigo;
    }

    public CatalogoEE(Integer codigo, int horasPractica, int horasTeoria, String nombreEE) {
        this.codigo = codigo;
        this.horasPractica = horasPractica;
        this.horasTeoria = horasTeoria;
        this.nombreEE = nombreEE;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public int getHorasPractica() {
        return horasPractica;
    }

    public void setHorasPractica(int horasPractica) {
        this.horasPractica = horasPractica;
    }

    public int getHorasTeoria() {
        return horasTeoria;
    }

    public void setHorasTeoria(int horasTeoria) {
        this.horasTeoria = horasTeoria;
    }

    public String getNombreEE() {
        return nombreEE;
    }

    public void setNombreEE(String nombreEE) {
        this.nombreEE = nombreEE;
    }

    @XmlTransient
    public List<ExperienciaEducativa> getExperienciaEducativaList() {
        return experienciaEducativaList;
    }

    public void setExperienciaEducativaList(List<ExperienciaEducativa> experienciaEducativaList) {
        this.experienciaEducativaList = experienciaEducativaList;
    }

    @XmlTransient
    public List<Seccion> getSeccionList() {
        return seccionList;
    }

    public void setSeccionList(List<Seccion> seccionList) {
        this.seccionList = seccionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CatalogoEE)) {
            return false;
        }
        CatalogoEE other = (CatalogoEE) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Persistencia.CatalogoEE[ codigo=" + codigo + " ]";
    }
    
}
