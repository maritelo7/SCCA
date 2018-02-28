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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "Taller")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Taller.findAll", query = "SELECT t FROM Taller t")
    , @NamedQuery(name = "Taller.findByIdTaller", query = "SELECT t FROM Taller t WHERE t.idTaller = :idTaller")
    , @NamedQuery(name = "Taller.findByTallerDe", query = "SELECT t FROM Taller t WHERE t.tallerDe = :tallerDe")
    , @NamedQuery(name = "Taller.findByCodigo", query = "SELECT t FROM Taller t WHERE t.codigo = :codigo")})
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
        return "persistencia.Taller[ idTaller=" + idTaller + " ]";
    }
    
}
