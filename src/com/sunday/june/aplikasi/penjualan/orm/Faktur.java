/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sunday.june.aplikasi.penjualan.orm;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Null Pointer
 */
@Entity
@Table(name = "faktur")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Faktur.findAll", query = "SELECT f FROM Faktur f"),
    @NamedQuery(name = "Faktur.findByNomorfaktur", query = "SELECT f FROM Faktur f WHERE f.nomorfaktur = :nomorfaktur"),
    @NamedQuery(name = "Faktur.findByTanggalfaktur", query = "SELECT f FROM Faktur f WHERE f.tanggalfaktur = :tanggalfaktur")})
public class Faktur implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "nomorfaktur")
    private String nomorfaktur;
    @Column(name = "tanggalfaktur")
    @Temporal(TemporalType.DATE)
    private Date tanggalfaktur;
    @JoinColumn(name = "kodepelanggan", referencedColumnName = "kodepelanggan")
    @ManyToOne
    private Pelanggan kodepelanggan;
    @OneToMany(mappedBy = "nomorfaktur")
    private Collection<Kwitansi> kwitansiCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nomorfaktur")
    private Collection<Fakturdetail> fakturdetailCollection;

    public Faktur() {
    }

    public Faktur(String nomorfaktur) {
        this.nomorfaktur = nomorfaktur;
    }

    public String getNomorfaktur() {
        return nomorfaktur;
    }

    public void setNomorfaktur(String nomorfaktur) {
        this.nomorfaktur = nomorfaktur;
    }

    public Date getTanggalfaktur() {
        return tanggalfaktur;
    }

    public void setTanggalfaktur(Date tanggalfaktur) {
        this.tanggalfaktur = tanggalfaktur;
    }

    public Pelanggan getKodepelanggan() {
        return kodepelanggan;
    }

    public void setKodepelanggan(Pelanggan kodepelanggan) {
        this.kodepelanggan = kodepelanggan;
    }

    @XmlTransient
    public Collection<Kwitansi> getKwitansiCollection() {
        return kwitansiCollection;
    }

    public void setKwitansiCollection(Collection<Kwitansi> kwitansiCollection) {
        this.kwitansiCollection = kwitansiCollection;
    }

    @XmlTransient
    public Collection<Fakturdetail> getFakturdetailCollection() {
        return fakturdetailCollection;
    }

    public void setFakturdetailCollection(Collection<Fakturdetail> fakturdetailCollection) {
        this.fakturdetailCollection = fakturdetailCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nomorfaktur != null ? nomorfaktur.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Faktur)) {
            return false;
        }
        Faktur other = (Faktur) object;
        if ((this.nomorfaktur == null && other.nomorfaktur != null) || (this.nomorfaktur != null && !this.nomorfaktur.equals(other.nomorfaktur))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sunday.june.aplikasi.penjualan.orm.Faktur[ nomorfaktur=" + nomorfaktur + " ]";
    }
    
}
