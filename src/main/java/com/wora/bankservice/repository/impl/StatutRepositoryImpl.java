package com.wora.bankservice.repository.impl;

import com.wora.bankservice.Config.EntityManagerFactoryProvider;
import com.wora.bankservice.entity.Statut;
import com.wora.bankservice.repository.StatutRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.Optional;

public class StatutRepositoryImpl implements StatutRepository {

    EntityManagerFactory Entity_Manager_Factory;

    public StatutRepositoryImpl() {
        Entity_Manager_Factory = EntityManagerFactoryProvider.getEntityManagerFactory();
    }

    @Override
    public List<Statut> findAll() {
        EntityManager em = Entity_Manager_Factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        List<Statut> statutList = em.createQuery("SELECT s FROM Statut s").getResultList();
        tx.commit();
        em.close();
        return statutList;
    }

    @Override
    public Optional<Statut> findByStatut(String statut) {
        EntityManager em = Entity_Manager_Factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Statut statut1 = em.createQuery("SELECT s FROM Statut s WHERE s.Statut = :statut" , Statut.class)
                .setParameter("statut" , statut)
                .getSingleResult();
        tx.commit();
        em.close();
        return Optional.of(statut1);
    }
}
