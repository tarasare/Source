/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sunday.june.aplikasi.penjualan.controller;

import com.sunday.june.aplikasi.penjualan.orm.Barang;
import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;

/**
 *
 * @author Null Pointer
 */
public class BarangController implements Serializable{
    private EntityManagerFactory emf = null;
    public BarangController(EntityManagerFactory emf){
        this.emf = emf;
    }
    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
    public void simpan(Barang barang)throws Exception{
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(barang);
            em.getTransaction().commit();
        } catch (Exception e) {
        if(cari(barang.getKodebarang())!= null){
            throw new Exception("Barang "+ barang + " Sudah Tersedia",e);
        }
        throw e;
        }finally{
            if(em!= null){
                em.close();
            }
        }
    }
    
    public void edit(Barang barang) throws Exception{
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(barang);
            em.getTransaction().commit();
        } catch (Exception e) {
            String msg = e.getLocalizedMessage();
            if(msg== null || msg.length()== 0){
                String id = barang.getKodebarang();
                if(cari(id)== null){
                    throw new Exception("Barang Dengan Kode BArang "+ id + " Belum Tersedia "); 
                }
            }
            throw e;
        }finally{
            if(em!= null){
                em.close();
            }
        }
    }
    
    public void hapus(String id) throws Exception{
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Barang barang;
            try {
                barang = em.find(Barang.class, id);
                barang.getKodebarang();
            } catch (EntityNotFoundException ex) {
                throw new Exception("Barang dengan ID "+ id +" Belum Tersedia", ex);
            }
            em.remove(barang);
            em.getTransaction().commit();
        } catch (Exception e) {
        }finally{
            if(em!=null){
                em.close();
            }
        }
    }
    
    
    public Barang cari(String id){
        EntityManager em = null;
        try {
            em = getEntityManager();
            return (Barang) em.find(Barang.class, id);
        }finally{
            if(em!=null){
              em.close();  
            }
            
        }
    }
}
