/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "taller")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Taller.findAll", query = "SELECT t FROM Taller t")
    , @NamedQuery(name = "Taller.findByIdTaller", query = "SELECT t FROM Taller t WHERE t.idTaller = :idTaller")
    , @NamedQuery(name = "Taller.findByTallerDe", query = "SELECT t FROM Taller t WHERE t.tallerDe = :tallerDe")
    , @NamedQuery(name = "Taller.findByCodigo", query = "SELECT t FROM Taller t WHERE t.codigo = :codigo")
    , @NamedQuery(name = "Taller.findByFecha", query = "SELECT t FROM Taller t WHERE t.fecha = :fecha")
    , @NamedQuery(name = "Taller.findByHora", query = "SELECT t FROM Taller t WHERE t.hora = :hora")
    , @NamedQuery(name = "Taller.findByLugar", query = "SELECT t FROM Taller t WHERE t.lugar = :lugar")})
public class Taller implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTaller")
    private Integer idTaller;
    @Basic(optional = false)
    @Column(name = "tallerDe")
    private String tallerDe;
    @Basic(optional = false)
    @Column(name = "codigo")
    private int codigo;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "hora")
    private String hora;
    @Column(name = "lugar")
    private String lugar;

    public Taller() {
    }

    public Taller(Integer idTaller) {
        this.idTaller = idTaller;
    }

    public Taller(Integer idTaller, String tallerDe, int codigo) {
        this.idTaller = idTaller;
        this.tallerDe = tallerDe;
        this.codigo = codigo;
    }

    public Integer getIdTaller() {
        return idTaller;
    }

    public void setIdTaller(Integer idTaller) {
        this.idTaller = idTaller;
    }

    public String getTallerDe() {
        return tallerDe;
    }

    public void setTallerDe(String tallerDe) {
        this.tallerDe = tallerDe;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTaller != null ? idTaller.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Taller)) {
            return false;
        }
        Taller other = (Taller) object;
        if ((this.idTaller == null && other.idTaller != null) || (this.idTaller != null && !this.idTaller.equals(other.idTaller))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Persistencia.Taller[ idTaller=" + idTaller + " ]";
    }
    
}
