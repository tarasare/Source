/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sunday.june.aplikasi.penjualan.orm;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
 * @author Null Pointer
 */
@Entity
@Table(name = "kwitansi")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kwitansi.findAll", query = "SELECT k FROM Kwitansi k"),
    @NamedQuery(name = "Kwitansi.findByNomorkwitansi", query = "SELECT k FROM Kwitansi k WHERE k.nomorkwitansi = :nomorkwitansi"),
    @NamedQuery(name = "Kwitansi.findByTanggalkwitansi", query = "SELECT k FROM Kwitansi k WHERE k.tanggalkwitansi = :tanggalkwitansi")})
public class Kwitansi implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "nomorkwitansi")
    private String nomorkwitansi;
    @Column(name = "tanggalkwitansi")
    @Temporal(TemporalType.DATE)
    private Date tanggalkwitansi;
    @JoinColumn(name = "nomorfaktur", referencedColumnName = "nomorfaktur")
    @ManyToOne
    private Faktur nomorfaktur;

    public Kwitansi() {
    }

    public Kwitansi(String nomorkwitansi) {
        this.nomorkwitansi = nomorkwitansi;
    }

    public String getNomorkwitansi() {
        return nomorkwitansi;
    }

    public void setNomorkwitansi(String nomorkwitansi) {
        this.nomorkwitansi = nomorkwitansi;
    }

    public Date getTanggalkwitansi() {
        return tanggalkwitansi;
    }

    public void setTanggalkwitansi(Date tanggalkwitansi) {
        this.tanggalkwitansi = tanggalkwitansi;
    }

    public Faktur getNomorfaktur() {
        return nomorfaktur;
    }

    public void setNomorfaktur(Faktur nomorfaktur) {
        this.nomorfaktur = nomorfaktur;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nomorkwitansi != null ? nomorkwitansi.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kwitansi)) {
            return false;
        }
        Kwitansi other = (Kwitansi) object;
        if ((this.nomorkwitansi == null && other.nomorkwitansi != null) || (this.nomorkwitansi != null && !this.nomorkwitansi.equals(other.nomorkwitansi))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sunday.june.aplikasi.penjualan.orm.Kwitansi[ nomorkwitansi=" + nomorkwitansi + " ]";
    }
    
}
