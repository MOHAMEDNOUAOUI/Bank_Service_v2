package com.wora.bankservice.repository.impl;

import com.wora.bankservice.Config.EntityManagerFactoryProvider;
import com.wora.bankservice.entity.DemandeStatut;
import com.wora.bankservice.repository.DemandeStatutRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class DemandeStatutRepositoryImpl implements DemandeStatutRepository {

    private EntityManagerFactory entityManagerFactory;

    public DemandeStatutRepositoryImpl() {
        entityManagerFactory = EntityManagerFactoryProvider.getEntityManagerFactory();
    }

    @Override
    public DemandeStatut save(DemandeStatut demandeStatut) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(demandeStatut);
        entityManager.getTransaction().commit();
        entityManager.close();
        return demandeStatut;
    }
}
