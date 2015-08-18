/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sunday.june.aplikasi.penjualan.controller;

import com.sunday.june.aplikasi.penjualan.orm.Faktur;
import com.sunday.june.aplikasi.penjualan.orm.Kwitansi;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Null Pointer
 */
public class KwitansiController implements Serializable{
    private EntityManagerFactory emf = null;
    public KwitansiController(EntityManagerFactory emf){
        this.emf = emf;
    }
    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
    
    
    /*==========================================
    
    METHOD MENYIMPAN KWITANSI
    
    *///========================================
    
    public void simpan(Kwitansi kwitansi) throws Exception{
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(kwitansi);
            em.getTransaction().commit();
        } catch (Exception e) {
            if(cari(kwitansi.getNomorkwitansi())!= null){
                throw new Exception("Kwitansi "+ kwitansi + " Sudah ada dalam database ");
            }
            throw e;
        }finally{
            if(em!=null){
                em.close();
            }
        }
    }
    /*==========================================
    END METHOD MENYIMPAN KWITANSI
    *///========================================
    
    
    /*=========================================
    
    METHOD EDIT KWITANSI
    
    *///=======================================
    public void edit(Kwitansi kwitansi)throws Exception{
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(kwitansi);
            em.getTransaction().commit();
        } catch (Exception e) {
            String msg = e.getLocalizedMessage();
            if(msg == null || msg.length()==0){
                String id = kwitansi.getNomorkwitansi();
                if(cari(id)== null){
                    throw new Exception ("Kwitansi "+ id+" Belum ada di database ", e);
                }
            }
            throw e;
        }finally{
            if(em!=null){
                em.close();
            }
        }
        
    }
    /*=========================================
    END METHOD EDIT KWITANSI
    *///=======================================
    
    
    
    
    /*========================================
    
    METHOD HAPUS KWITANSI
    
    *///=====================================
    
    public void hapus(String id)throws Exception{
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Kwitansi kwitansi;
            try {
                kwitansi = em.getReference(Kwitansi.class, id);
                kwitansi.getNomorkwitansi();
            } catch (EntityNotFoundException ex) {
                throw new Exception("Kwitansi dengan ID "+id +" Tidak ada dalam database ", ex);
                }
            Faktur nomorFaktur = kwitansi.getNomorfaktur();
            if(nomorFaktur != null){
                nomorFaktur.getKwitansiCollection().remove(kwitansi);
                nomorFaktur = em.merge(nomorFaktur);            
            }
            em.remove(kwitansi);
            em.getTransaction().commit();
            
        } finally{
            if(em!=null){
                em.close();
            }
        }
        
    }
    /*=======================================
    END METHOD HAPUS KWITANSI
    *///=====================================
    
    
    
    
    /*======================================
    
    MENAMPILKAN LIST KWITANSI
    
    *///====================================
    private List<Kwitansi> tampilkanListKwitansi(boolean semua, int maxResults, int firstResults){
        EntityManager em = null;
        try {
            em = getEntityManager();
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Kwitansi.class));
            Query q = em.createQuery(cq);
            if(!semua){
                q.setFirstResult(firstResults);
                q.setMaxResults(maxResults);
            }
            return q.getResultList();
        } finally{
            if(em!=null){
                em.close();
            }
        }
    }
    
    
    public List<Kwitansi> tampilkanListKwitansi(int maxResults, int firstResults){
        return tampilkanListKwitansi(false, maxResults, firstResults);
    }
    public List<Kwitansi> tampilkanListKwitansi(){
        return tampilkanListKwitansi(true, -1, -1);
    }
    
    
    /*======================================
    END MENAMPILKAN LIS KWITANSI
    *///====================================
    
    
    /*======================================
    
    FUNGSI MENGHITUNG JUMLAH KWITANSI
    
    *///====================================
    
    public int hitungKwitansi(){
        EntityManager em = null;
        try {
            em = getEntityManager();
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Kwitansi> rt = cq.from(Kwitansi.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            if(em!=null){
                em.close();
            }
        }
    }
    
    
    /*======================================
    END FUNGSI MENGHITUNG JULAH KWITANSI
    *///====================================
    
    
    /*======================================
    
    FUNGSI CREATE NOMOR KWITANSI
    
    *///====================================
    public String createNomorKwitansi(){
        String nomorKwitansi = "K0001";
        EntityManager em = null;
        try {
            em = getEntityManager();
            Query q = em.createQuery("SELECT k FROM Kwitansi k Order By k.nomorkwitansi Desc");
            List<Kwitansi> listKw = q.getResultList();
            if(listKw.size()>0){
                Kwitansi k =(Kwitansi) listKw.get(0);
                DecimalFormat df = new DecimalFormat("K0000");
                String nomor = k.getNomorkwitansi().substring(1);
                nomorKwitansi = df.format(Integer.parseInt(nomor+"")+1);
            }else{
                nomorKwitansi = "K0001";
            }
        } catch (Exception e) {
        }finally{
            if(em!=null){
                em.close();
            }
        }
        return nomorKwitansi;
    }
    /*==========================================
    END FUNGSI CREATE NOMOR KWITANSI
    *///========================================
    
    
    /*==========================================
    
    FUNGSI CHECK NOMOR FAKTUR
    
    *///========================================
    
    
    public boolean checkNomorFaktur(Faktur f){
        boolean cek = false;
        EntityManager em = null;
        try {
            em = getEntityManager();
            Query q = em.createQuery("SELECT k FROM Kwitansi k where k.nomorfaktur= :nomorFaktur");
            q.setParameter("nomorFaktur", f);
            Kwitansi k = (Kwitansi) q.getSingleResult();
            if(k != null){
                cek = true;
            }
        } catch (Exception e) {
            cek = false;
        }finally{
            if(em!=null){
                em.close();
            }
        }return cek;
    }
    
    /*==========================================
    END CEK NOMOR FAKTUR
    *///========================================
    
    
    
    /*==========================================
    
    FUNGSI PENCARIAN KWITANSI
    
    *///========================================
    public Kwitansi cari(String id){
        EntityManager em = null;
        try {
            em = getEntityManager();
            return em.find(Kwitansi.class, id);
        } finally {
            if(em!=null){
                em.close();
            }
        }
    }
    /*=========================================
    END FUNGSI PENCARIAN KWITANSI
    *///=======================================
}
