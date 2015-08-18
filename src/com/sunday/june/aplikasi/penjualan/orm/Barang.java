/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sunday.june.aplikasi.penjualan.orm;

import java.io.Serializable;
import java.util.Collection;
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
 * @author Null Pointer
 */
@Entity
@Table(name = "barang")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Barang.findAll", query = "SELECT b FROM Barang b"),
    @NamedQuery(name = "Barang.findByKodebarang", query = "SELECT b FROM Barang b WHERE b.kodebarang = :kodebarang"),
    @NamedQuery(name = "Barang.findByNamabarang", query = "SELECT b FROM Barang b WHERE b.namabarang = :namabarang"),
    @NamedQuery(name = "Barang.findByStok", query = "SELECT b FROM Barang b WHERE b.stok = :stok"),
    @NamedQuery(name = "Barang.findByHargaStandar", query = "SELECT b FROM Barang b WHERE b.hargaStandar = :hargaStandar")})
public class Barang implements Serializable , Comparable<Barang>{
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "kodebarang")
    private String kodebarang;
    @Column(name = "namabarang")
    private String namabarang;
    @Column(name = "Stok")
    private Integer stok;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "HargaStandar")
    private Double hargaStandar;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "kodebarang")
    private Collection<Fakturdetail> fakturdetailCollection;

    public Barang() {
    }

    public Barang(String kodebarang) {
        this.kodebarang = kodebarang;
    }

    public String getKodebarang() {
        return kodebarang;
    }

    public void setKodebarang(String kodebarang) {
        this.kodebarang = kodebarang;
    }

    public String getNamabarang() {
        return namabarang;
    }

    public void setNamabarang(String namabarang) {
        this.namabarang = namabarang;
    }

    public Integer getStok() {
        return stok;
    }

    public void setStok(Integer stok) {
        this.stok = stok;
    }

    public Double getHargaStandar() {
        return hargaStandar;
    }

    public void setHargaStandar(Double hargaStandar) {
        this.hargaStandar = hargaStandar;
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
        int hash = 7;
        hash = 50*hash+(kodebarang != null ? kodebarang.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Barang)) {
            return false;
        }
        Barang other = (Barang) object;
        if ((this.kodebarang == null && other.kodebarang != null) || (this.kodebarang != null && !this.kodebarang.equals(other.kodebarang))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sunday.june.aplikasi.penjualan.orm.Barang[ kodebarang=" + kodebarang + " ]";
    }

    @Override
    public int compareTo(Barang t) {
    return this.kodebarang.compareTo(t.getKodebarang());
    }
    
}
