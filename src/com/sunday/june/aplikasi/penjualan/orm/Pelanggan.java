/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sunday.june.aplikasi.penjualan.orm;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
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
@Table(name = "pelanggan")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pelanggan.findAll", query = "SELECT p FROM Pelanggan p"),
    @NamedQuery(name = "Pelanggan.findByKodepelanggan", query = "SELECT p FROM Pelanggan p WHERE p.kodepelanggan = :kodepelanggan"),
    @NamedQuery(name = "Pelanggan.findByNamapelanggan", query = "SELECT p FROM Pelanggan p WHERE p.namapelanggan = :namapelanggan"),
    @NamedQuery(name = "Pelanggan.findByAlamat", query = "SELECT p FROM Pelanggan p WHERE p.alamat = :alamat")})
public class Pelanggan implements Serializable,Comparable<Pelanggan> {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "kodepelanggan")
    private String kodepelanggan;
    @Column(name = "namapelanggan")
    private String namapelanggan;
    @Column(name = "alamat")
    private String alamat;
    @OneToMany(mappedBy = "kodepelanggan")
    private Collection<Faktur> fakturCollection;

    public Pelanggan() {
    }

    public Pelanggan(String kodepelanggan) {
        this.kodepelanggan = kodepelanggan;
    }

    public String getKodepelanggan() {
        return kodepelanggan;
    }

    public void setKodepelanggan(String kodepelanggan) {
        this.kodepelanggan = kodepelanggan;
    }

    public String getNamapelanggan() {
        return namapelanggan;
    }

    public void setNamapelanggan(String namapelanggan) {
        this.namapelanggan = namapelanggan;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    @XmlTransient
    public Collection<Faktur> getFakturCollection() {
        return fakturCollection;
    }

    public void setFakturCollection(Collection<Faktur> fakturCollection) {
        this.fakturCollection = fakturCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kodepelanggan != null ? kodepelanggan.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pelanggan)) {
            return false;
        }
        Pelanggan other = (Pelanggan) object;
        if ((this.kodepelanggan == null && other.kodepelanggan != null) || (this.kodepelanggan != null && !this.kodepelanggan.equals(other.kodepelanggan))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sunday.june.aplikasi.penjualan.orm.Pelanggan[ kodepelanggan=" + kodepelanggan + " ]";
    }

    @Override
    public int compareTo(Pelanggan t) {
    return this.kodepelanggan.compareTo(t.getKodepelanggan());
    }
    
}
