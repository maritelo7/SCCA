/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

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
@Table(name = "recepcionista")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Recepcionista.findAll", query = "SELECT r FROM Recepcionista r")
    , @NamedQuery(name = "Recepcionista.findByNumPersonal", query = "SELECT r FROM Recepcionista r WHERE r.numPersonal = :numPersonal")
    , @NamedQuery(name = "Recepcionista.findByApellidoPaterno", query = "SELECT r FROM Recepcionista r WHERE r.apellidoPaterno = :apellidoPaterno")
    , @NamedQuery(name = "Recepcionista.findByApellidoMaterno", query = "SELECT r FROM Recepcionista r WHERE r.apellidoMaterno = :apellidoMaterno")
    , @NamedQuery(name = "Recepcionista.findByCorreo", query = "SELECT r FROM Recepcionista r WHERE r.correo = :correo")
    , @NamedQuery(name = "Recepcionista.findByNombre", query = "SELECT r FROM Recepcionista r WHERE r.nombre = :nombre")
    , @NamedQuery(name = "Recepcionista.findByNombreUsuario", query = "SELECT r FROM Recepcionista r WHERE r.nombreUsuario = :nombreUsuario")
    , @NamedQuery(name = "Recepcionista.findByClaveUsuario", query = "SELECT r FROM Recepcionista r WHERE r.claveUsuario = :claveUsuario")
    , @NamedQuery(name = "Recepcionista.findByTurno", query = "SELECT r FROM Recepcionista r WHERE r.turno = :turno")
    , @NamedQuery(name = "Recepcionista.iniciarSesion", query = "SELECT r FROM Recepcionista r WHERE r.nombreUsuario = :nombreUsuario AND r.claveUsuario =:claveUsuario")})
public class Recepcionista implements Serializable {

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
    @Column(name = "turno")
    private String turno;

    public Recepcionista() {
    }

    public Recepcionista(String numPersonal) {
        this.numPersonal = numPersonal;
    }

    public Recepcionista(String numPersonal, String apellidoPaterno, String correo, String nombre, String nombreUsuario) {
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

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
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
        if (!(object instanceof Recepcionista)) {
            return false;
        }
        Recepcionista other = (Recepcionista) object;
        if ((this.numPersonal == null && other.numPersonal != null) || (this.numPersonal != null && !this.numPersonal.equals(other.numPersonal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Persistencia.Recepcionista[ numPersonal=" + numPersonal + " ]";
    }
    
}
