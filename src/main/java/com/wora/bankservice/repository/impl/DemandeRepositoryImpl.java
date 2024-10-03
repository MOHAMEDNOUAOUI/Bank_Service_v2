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
            throw new RuntimeException(e);
        }
        return demande;
    }

    @Override
    public Optional<Demande> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Demande> findAll() {
        return List.of();
    }

    @Override
    public Demande delete(Demande demande) {
        return null;
    }

    @Override
    public Demande update(Demande demande) {
        return null;
    }
}
