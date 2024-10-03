package com.wora.bankservice;

import com.wora.bankservice.Config.EntityManagerFactoryProvider;
import com.wora.bankservice.entity.Demande;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = EntityManagerFactoryProvider.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Demande demande = new Demande();
        demande.setNom("demande");
        demande.setMontant(5000);
        demande.setDuree(12);
        demande.setMensualite(520);
        entityManager.persist(demande);
        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
    }
}
