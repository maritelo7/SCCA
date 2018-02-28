/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author marianacro
 */
@Entity
@Table(name = "HojaSeguimiento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HojaSeguimiento.findAll", query = "SELECT h FROM HojaSeguimiento h")
    , @NamedQuery(name = "HojaSeguimiento.findByIdHojaSeguimiento", query = "SELECT h FROM HojaSeguimiento h WHERE h.idHojaSeguimiento = :idHojaSeguimiento")
    , @NamedQuery(name = "HojaSeguimiento.findByFechaInicioSeccion", query = "SELECT h FROM HojaSeguimiento h WHERE h.fechaInicioSeccion = :fechaInicioSeccion")
    , @NamedQuery(name = "HojaSeguimiento.findByFechaFinSeccion", query = "SELECT h FROM HojaSeguimiento h WHERE h.fechaFinSeccion = :fechaFinSeccion")
    , @NamedQuery(name = "HojaSeguimiento.findByModulo", query = "SELECT h FROM HojaSeguimiento h WHERE h.modulo = :modulo")
    , @NamedQuery(name = "HojaSeguimiento.findBySeccion", query = "SELECT h FROM HojaSeguimiento h WHERE h.seccion = :seccion")})
public class HojaSeguimiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idHojaSeguimiento")
    private Integer idHojaSeguimiento;
    @Basic(optional = false)
    @Column(name = "fechaInicioSeccion")
    @Temporal(TemporalType.DATE)
    private Date fechaInicioSeccion;
    @Basic(optional = false)
    @Column(name = "fechaFinSeccion")
    @Temporal(TemporalType.DATE)
    private Date fechaFinSeccion;
    @Basic(optional = false)
    @Column(name = "modulo")
    private int modulo;
    @Basic(optional = false)
    @Column(name = "seccion")
    private int seccion;
    @JoinColumn(name = "idSeguimiento", referencedColumnName = "idSeguimiento")
    @ManyToOne(optional = false)
    private Seguimiento idSeguimiento;

    public HojaSeguimiento() {
    }

    public HojaSeguimiento(Integer idHojaSeguimiento) {
        this.idHojaSeguimiento = idHojaSeguimiento;
    }

    public HojaSeguimiento(Integer idHojaSeguimiento, Date fechaInicioSeccion, Date fechaFinSeccion, int modulo, int seccion) {
        this.idHojaSeguimiento = idHojaSeguimiento;
        this.fechaInicioSeccion = fechaInicioSeccion;
        this.fechaFinSeccion = fechaFinSeccion;
        this.modulo = modulo;
        this.seccion = seccion;
    }

    public Integer getIdHojaSeguimiento() {
        return idHojaSeguimiento;
    }

    public void setIdHojaSeguimiento(Integer idHojaSeguimiento) {
        this.idHojaSeguimiento = idHojaSeguimiento;
    }

    public Date getFechaInicioSeccion() {
        return fechaInicioSeccion;
    }

    public void setFechaInicioSeccion(Date fechaInicioSeccion) {
        this.fechaInicioSeccion = fechaInicioSeccion;
    }

    public Date getFechaFinSeccion() {
        return fechaFinSeccion;
    }

    public void setFechaFinSeccion(Date fechaFinSeccion) {
        this.fechaFinSeccion = fechaFinSeccion;
    }

    public int getModulo() {
        return modulo;
    }

    public void setModulo(int modulo) {
        this.modulo = modulo;
    }

    public int getSeccion() {
        return seccion;
    }

    public void setSeccion(int seccion) {
        this.seccion = seccion;
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
        hash += (idHojaSeguimiento != null ? idHojaSeguimiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HojaSeguimiento)) {
            return false;
        }
        HojaSeguimiento other = (HojaSeguimiento) object;
        if ((this.idHojaSeguimiento == null && other.idHojaSeguimiento != null) || (this.idHojaSeguimiento != null && !this.idHojaSeguimiento.equals(other.idHojaSeguimiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistencia.HojaSeguimiento[ idHojaSeguimiento=" + idHojaSeguimiento + " ]";
    }
    
}
