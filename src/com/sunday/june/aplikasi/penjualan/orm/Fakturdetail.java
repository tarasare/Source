/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sunday.june.aplikasi.penjualan.orm;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Null Pointer
 */
@Entity
@Table(name = "fakturdetail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fakturdetail.findAll", query = "SELECT f FROM Fakturdetail f"),
    @NamedQuery(name = "Fakturdetail.findById", query = "SELECT f FROM Fakturdetail f WHERE f.id = :id"),
    @NamedQuery(name = "Fakturdetail.findByQty", query = "SELECT f FROM Fakturdetail f WHERE f.qty = :qty"),
    @NamedQuery(name = "Fakturdetail.findByHarga", query = "SELECT f FROM Fakturdetail f WHERE f.harga = :harga")})
public class Fakturdetail implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "qty")
    private Integer qty;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "harga")
    private Double harga;
    @JoinColumn(name = "nomorfaktur", referencedColumnName = "nomorfaktur")
    @ManyToOne(optional = false)
    private Faktur nomorfaktur;
    @JoinColumn(name = "kodebarang", referencedColumnName = "kodebarang")
    @ManyToOne(optional = false)
    private Barang kodebarang;

    public Fakturdetail() {
    }

    public Fakturdetail(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Double getHarga() {
        return harga;
    }

    public void setHarga(Double harga) {
        this.harga = harga;
    }

    public Faktur getNomorfaktur() {
        return nomorfaktur;
    }

    public void setNomorfaktur(Faktur nomorfaktur) {
        this.nomorfaktur = nomorfaktur;
    }

    public Barang getKodebarang() {
        return kodebarang;
    }

    public void setKodebarang(Barang kodebarang) {
        this.kodebarang = kodebarang;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fakturdetail)) {
            return false;
        }
        Fakturdetail other = (Fakturdetail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sunday.june.aplikasi.penjualan.orm.Fakturdetail[ id=" + id + " ]";
    }
    
}
