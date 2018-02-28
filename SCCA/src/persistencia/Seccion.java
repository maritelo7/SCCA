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
@Table(name = "Seccion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Seccion.findAll", query = "SELECT s FROM Seccion s")
    , @NamedQuery(name = "Seccion.findByNumSeccion", query = "SELECT s FROM Seccion s WHERE s.numSeccion = :numSeccion")
    , @NamedQuery(name = "Seccion.findByNombreSeccion", query = "SELECT s FROM Seccion s WHERE s.nombreSeccion = :nombreSeccion")
    , @NamedQuery(name = "Seccion.findByNumModulo", query = "SELECT s FROM Seccion s WHERE s.numModulo = :numModulo")})
public class Seccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "numSeccion")
    private Integer numSeccion;
    @Basic(optional = false)
    @Column(name = "nombreSeccion")
    private String nombreSeccion;
    @Basic(optional = false)
    @Column(name = "numModulo")
    private int numModulo;
    @JoinColumn(name = "codigo", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private CatalogoEE codigo;

    public Seccion() {
    }

    public Seccion(Integer numSeccion) {
        this.numSeccion = numSeccion;
    }

    public Seccion(Integer numSeccion, String nombreSeccion, int numModulo) {
        this.numSeccion = numSeccion;
        this.nombreSeccion = nombreSeccion;
        this.numModulo = numModulo;
    }

    public Integer getNumSeccion() {
        return numSeccion;
    }

    public void setNumSeccion(Integer numSeccion) {
        this.numSeccion = numSeccion;
    }

    public String getNombreSeccion() {
        return nombreSeccion;
    }

    public void setNombreSeccion(String nombreSeccion) {
        this.nombreSeccion = nombreSeccion;
    }

    public int getNumModulo() {
        return numModulo;
    }

    public void setNumModulo(int numModulo) {
        this.numModulo = numModulo;
    }

    public CatalogoEE getCodigo() {
        return codigo;
    }

    public void setCodigo(CatalogoEE codigo) {
        this.codigo = codigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numSeccion != null ? numSeccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Seccion)) {
            return false;
        }
        Seccion other = (Seccion) object;
        if ((this.numSeccion == null && other.numSeccion != null) || (this.numSeccion != null && !this.numSeccion.equals(other.numSeccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistencia.Seccion[ numSeccion=" + numSeccion + " ]";
    }
    
}
