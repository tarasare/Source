/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sunday.june.aplikasi.penjualan.controller;

import com.sunday.june.aplikasi.penjualan.orm.Barang;
import com.sunday.june.aplikasi.penjualan.orm.Faktur;
import com.sunday.june.aplikasi.penjualan.orm.Fakturdetail;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Collection;
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
public class FakturController implements Serializable{
    
    private EntityManagerFactory emf = null;
    public FakturController(EntityManagerFactory emf){
        this.emf = emf;
    }
    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
    
    
    /*================================================
    
    Method Menyimpan faktur
    
    *///==============================================
    public void simpan(Faktur faktur) throws Exception{
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(faktur);
            Collection<Fakturdetail> listFaktur = faktur.getFakturdetailCollection();
            for(Fakturdetail fd : listFaktur){
                em.persist(fd);
                Barang barang = fd.getKodebarang();
                if(barang!= null){
                    barang.setStok(barang.getStok()- fd.getQty());
                    em.merge(barang);
                }
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if(cari(faktur.getNomorfaktur())!= null){
                throw new Exception("Faktur "+ faktur +" sudah tersedia");
            }
            throw e;
        }finally{
            if(em!=null){
                em.close();
            }
        }
    }
    /*=================================
    END MTHOD MENYIMPAN FAKTUR
    *///===============================
    
    
    /*=================================
    
    Method Edit / Update Faktur
    
    *///===============================    
    public void edit(Faktur faktur)throws Exception{
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Faktur fk = cari(faktur.getNomorfaktur());
            if(fk!=null){
                for(Fakturdetail fd : fk.getFakturdetailCollection()){
                    Barang barang = fd.getKodebarang();
                    if(barang != null){
                        barang.setStok(barang.getStok()+ fd.getQty());
                        em.merge(barang);
                    }
                }
            }
            em.merge(faktur);
            Collection<Fakturdetail> listFaktur = faktur.getFakturdetailCollection();
            for(Fakturdetail fd : listFaktur){
                Barang barang = em.find(Barang.class, fd.getKodebarang().getKodebarang());
                barang.setStok(barang.getStok()-fd.getQty());
                em.merge(barang);
                if(fd.getId()!=null){
                    em.merge(fd);
                }
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            String msg = e.getLocalizedMessage();
            if(msg==null || msg.length()==0){
                String id = faktur.getNomorfaktur();
                if(cari(id)==null){
                    throw new Exception("Faktur dengan ID "+ id +" Tidak Tersedia dalam database");
                }
            }
            throw e;
        }finally{
            if(em!=null){
                em.close();
            }
        }
    }
    /*========================================
    
    END METHOD EDIT / UPDATE FAKTUR
    
    *///======================================

    /*=======================================
    
    METHOD HAPUS FAKTUR
    
    *///=======================================
    
    public void hapus(String id)throws Exception{
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Faktur faktur;
            try {
                faktur = em.find(Faktur.class, id);
                faktur.getNomorfaktur();
            } catch (EntityNotFoundException ex) {
                throw new Exception("Faktur Dengan ID "+ id +" Belum ada dalam database", ex);
            }
            em.remove(faktur);
            Collection<Fakturdetail> listFaktur = faktur.getFakturdetailCollection();
            for(Fakturdetail fd : listFaktur){
                Barang barang = fd.getKodebarang();
                barang.setStok(barang.getStok()+ fd.getQty());
                em.remove(fd);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
        }finally{
            if(em!=null){
                em.close();
            }
        }
    }
    /*=========================================
    END METHOD HAPUS FAKTUR
    *///=======================================
    
    private List<Faktur> tampilkanFaktur(boolean semua, int maxResult, int fisrtResult){
        EntityManager em = null;
        try {
            em= getEntityManager();
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Faktur.class));
            Query q = em.createQuery(cq);
            if(!semua){
                q.setMaxResults(maxResult);
                q.setFirstResult(fisrtResult);
            }
            return q.getResultList();
        }finally{
            if(em!=null){
                em.close();
            }
        }
    }
    public List<Faktur> tampilkanFaktur(){
        return tampilkanFaktur(true, -1,-1);
    }
    public List<Faktur> tampilkanFaktur(int maxResult, int firstResult){
        return tampilkanFaktur(false, maxResult, firstResult);
    }
    
    
    
    /*=======================================
    Fungsi Mencari Faktur
    *///=====================================    
    
    public Faktur cari(String id){
        EntityManager em = null;
        try {
            em = getEntityManager();
            return em.find(Faktur.class, id);
        }finally{
            if(em!=null){
                em.close();
            }
        }
    }
    /*=======================================
    END FUNGSI PENCARIAN FAKTUR
    *///=====================================
    
    /*=======================================
    
    FUNGSI MENGHITUNG JUMLAH FAKTUR
    
    *///=====================================
    public int hitungJumlahFaktur(){
        EntityManager em = null;
        try {
            em = getEntityManager();
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Faktur> rt = cq.from(Faktur.class);
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        }finally{
            if(em!=null){
                em.close();
            }
        }
    }
    /*====================================
    END FUNGSI MENGHITUNG JUMLAH FAKTUR
    *///==================================
    
    
    /*====================================
    
    FUNGSI MENGCREATE NOMOR FAKTUR
    
    *///==================================
    public String createNomorFAktur(){
        String nomorFaktur = "F0001";
        EntityManager em = null;
        try {
            em = getEntityManager();
            Query q = em.createQuery("SELECT f FROM Faktur f Order By f.nomorfaktur Desc");
            List<Faktur> list = q.getResultList();
            if(list.size()>0){
                Faktur f = (Faktur) list.get(0);
                DecimalFormat nfF000 = new DecimalFormat("F0000");
                String nomor = f.getNomorfaktur().substring(1);
                nomorFaktur = nfF000.format(Double.parseDouble(nomor)+1);
            }else{
                nomorFaktur = "F0001";
            }
        }catch(javax.persistence.NoResultException e){
            
        }
        finally{
            if(em!=null){
                em.close();
            }
        }
        return nomorFaktur;
    }
    /*====================================
    END FUNGSI MENGCREATE NOMOR FAKTUR
    *///==================================
}
