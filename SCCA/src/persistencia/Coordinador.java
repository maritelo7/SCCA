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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author marianacro
 */
@Entity
@Table(name = "Coordinador")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Coordinador.findAll", query = "SELECT c FROM Coordinador c")
    , @NamedQuery(name = "Coordinador.findByNumPersonal", query = "SELECT c FROM Coordinador c WHERE c.numPersonal = :numPersonal")
    , @NamedQuery(name = "Coordinador.findByApellidoPaterno", query = "SELECT c FROM Coordinador c WHERE c.apellidoPaterno = :apellidoPaterno")
    , @NamedQuery(name = "Coordinador.findByApellidoMaterno", query = "SELECT c FROM Coordinador c WHERE c.apellidoMaterno = :apellidoMaterno")
    , @NamedQuery(name = "Coordinador.findByCorreo", query = "SELECT c FROM Coordinador c WHERE c.correo = :correo")
    , @NamedQuery(name = "Coordinador.findByNombre", query = "SELECT c FROM Coordinador c WHERE c.nombre = :nombre")
    , @NamedQuery(name = "Coordinador.findByNombreUsuario", query = "SELECT c FROM Coordinador c WHERE c.nombreUsuario = :nombreUsuario")
    , @NamedQuery(name = "Coordinador.findByClaveUsuario", query = "SELECT c FROM Coordinador c WHERE c.claveUsuario = :claveUsuario")})
public class Coordinador implements Serializable {

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
    @Column(name = "claveUsuario")
    private String claveUsuario;

    public Coordinador() {
    }

    public Coordinador(String numPersonal) {
        this.numPersonal = numPersonal;
    }

    public Coordinador(String numPersonal, String apellidoPaterno, String correo, String nombre, String nombreUsuario) {
        this.numPersonal = numPersonal;
        this.apellidoPaterno = apellidoPaterno;
        this.correo = correo;
        this.nombre = nombre;
        this.nombreUsuario = nombreUsuario;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numPersonal != null ? numPersonal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Coordinador)) {
            return false;
        }
        Coordinador other = (Coordinador) object;
        if ((this.numPersonal == null && other.numPersonal != null) || (this.numPersonal != null && !this.numPersonal.equals(other.numPersonal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistencia.Coordinador[ numPersonal=" + numPersonal + " ]";
    }
    
}
