package com.wora.bankservice.service.impl;

import com.wora.bankservice.entity.Demande;
import com.wora.bankservice.repository.impl.DemandeRepositoryImpl;
import com.wora.bankservice.service.DemandeService;
import jakarta.transaction.Transactional;

public class DemandeServiceImpl implements DemandeService {

    private DemandeRepositoryImpl repo;
    public DemandeServiceImpl() {
        repo = new DemandeRepositoryImpl();
    }


    @Override
    public void createDemande(Demande demande) {
        repo.save(demande);
    }
}
