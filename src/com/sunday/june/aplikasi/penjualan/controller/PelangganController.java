/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sunday.june.aplikasi.penjualan.controller;

import com.sunday.june.aplikasi.penjualan.orm.Pelanggan;
import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;

/**
 *
 * @author Null Pointer
 */
public class PelangganController implements Serializable {

    private EntityManagerFactory emf = null;
    public PelangganController(EntityManagerFactory emf){
        this.emf = emf;
    }
    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    } 
    
    
    public void simpan(Pelanggan pelanggan)throws Exception{
        EntityManager em = null;
        try {
            em= getEntityManager();
            em.getTransaction().begin();
            em.persist(pelanggan);
            em.getTransaction().commit();
        } catch (Exception e) {
            if(cari(pelanggan.getKodepelanggan())!=null){
                throw new Exception("Pelanggan "+ pelanggan +" Sudah Tersedia ");
            }
            throw e;
        }finally{
            if(em!=null){
                em.close();
            }
        }
    }
    
    
    public void edit(Pelanggan pelanggan)throws Exception{
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(pelanggan);
            em.getTransaction().commit();
        } catch (Exception e) {
            String msg = e.getLocalizedMessage();
            if(msg==null || msg.length()==0){
                String id = pelanggan.getKodepelanggan();
                if(cari(id)== null){
                    throw new Exception("Pelanggan Dengan Kode "+ id +" Belum Tersedia ",e);
                }
            }throw e;   
        }finally{
            if(em!=null){
                em.close();
            }
        }    
    }
    
    public void hapus(String id)throws Exception{
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pelanggan pelanggan;
            try {
                pelanggan = em.find(Pelanggan.class, id);
                pelanggan.getKodepelanggan();
            } catch (EntityNotFoundException ex) {
                throw new Exception("Pelanggan dengan "+ id  +" Tidak ada dalam database", ex);
            }
            em.remove(pelanggan);
            em.getTransaction().commit();
        } catch (Exception e) {
        }finally{
            if(em!=null){
                em.close();
            }
        }
        
        
        
        
    }
    
    
    public Pelanggan cari(String id){
        EntityManager em = null;
        try {
            em = getEntityManager();
            return em.find(Pelanggan.class, id);
        }finally{
            if(em!=null){
                em.close();
            }
        }
    }
    
}
