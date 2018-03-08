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
@Table(name = "Aviso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Aviso.findAll", query = "SELECT a FROM Aviso a")
    , @NamedQuery(name = "Aviso.findByIdAviso", query = "SELECT a FROM Aviso a WHERE a.idAviso = :idAviso")
    , @NamedQuery(name = "Aviso.findByTopico", query = "SELECT a FROM Aviso a WHERE a.topico = :topico")
    , @NamedQuery(name = "Aviso.findByDescripcion", query = "SELECT a FROM Aviso a WHERE a.descripcion = :descripcion")
    , @NamedQuery(name = "Aviso.findByFechaSubida", query = "SELECT a FROM Aviso a WHERE a.fechaSubida = :fechaSubida")})
public class Aviso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idAviso")
    private Integer idAviso;
    @Basic(optional = false)
    @Column(name = "topico")
    private String topico;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "fechaSubida")
    @Temporal(TemporalType.DATE)
    private Date fechaSubida;

    public Aviso() {
    }

    public Aviso(Integer idAviso) {
        this.idAviso = idAviso;
    }

    public Aviso(Integer idAviso, String topico, String descripcion, Date fechaSubida) {
        this.idAviso = idAviso;
        this.topico = topico;
        this.descripcion = descripcion;
        this.fechaSubida = fechaSubida;
    }

    public Integer getIdAviso() {
        return idAviso;
    }

    public void setIdAviso(Integer idAviso) {
        this.idAviso = idAviso;
    }

    public String getTopico() {
        return topico;
    }

    public void setTopico(String topico) {
        this.topico = topico;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaSubida() {
        return fechaSubida;
    }

    public void setFechaSubida(Date fechaSubida) {
        this.fechaSubida = fechaSubida;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAviso != null ? idAviso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Aviso)) {
            return false;
        }
        Aviso other = (Aviso) object;
        if ((this.idAviso == null && other.idAviso != null) || (this.idAviso != null && !this.idAviso.equals(other.idAviso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Persistencia.Aviso[ idAviso=" + idAviso + " ]";
    }
    
}
