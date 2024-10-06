package com.wora.bankservice.repository.impl;

import com.wora.bankservice.Config.EntityManagerFactoryProvider;
import com.wora.bankservice.entity.Demande;
import com.wora.bankservice.repository.DemandeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.Optional;

public class DemandeRepositoryImpl implements DemandeRepository {

    private EntityManagerFactory ENTITY_MANAGER_FACTORY;

    public DemandeRepositoryImpl() {
        ENTITY_MANAGER_FACTORY = EntityManagerFactoryProvider.getEntityManagerFactory();
    }

    @Override
    public Demande save(Demande demande) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try{
            tx.begin();
            em.persist(demande);
            tx.commit();
        } catch (Exception e) {
            if(tx.isActive()){
                tx.rollback();
            }
            e.printStackTrace();
        }
        return demande;
    }

    @Override
    public Optional<Demande> findById(Long id) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Optional<Demande> demande = Optional.ofNullable(em.find(Demande.class, id));
        tx.commit();
        em.close();
        return demande;
    }

    @Override
    public List<Demande> findAll() {
        EntityManager em= ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction EntityTransaction = em.getTransaction();
        EntityTransaction.begin();
        List DemandeList = em.createQuery("SELECT d FROM Demande d JOIN FETCH d.demandeStatuts ds JOIN FETCH ds.statut").getResultList();
        EntityTransaction.commit();
        em.close();
        return DemandeList;
    }

    @Override
    public boolean delete(Demande demande) {
        EntityManager em= ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction EntityTransaction = em.getTransaction();
        EntityTransaction.begin();
        em.remove(demande);
        EntityTransaction.commit();
        em.close();
        return true;
    }

    @Override
    public Demande update(Demande demande) {
        EntityManager em= ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction EntityTransaction = em.getTransaction();
        EntityTransaction.begin();
        Demande demandemerge = em.merge(demande);
        EntityTransaction.commit();
        em.close();
        return demandemerge;
    }
}
