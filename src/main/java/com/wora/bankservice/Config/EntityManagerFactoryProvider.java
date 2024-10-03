package com.wora.bankservice.Config;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerFactoryProvider {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY;

    static {
        try {
            ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("BankService");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        if(ENTITY_MANAGER_FACTORY == null) {
            throw new RuntimeException("No EntityManagerFactory provided");
        }
        return ENTITY_MANAGER_FACTORY;
    }


}
