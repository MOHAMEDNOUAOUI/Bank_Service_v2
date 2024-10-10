package com.wora.bankservice.service.impl;

import com.wora.bankservice.entity.DemandeStatut;
import com.wora.bankservice.repository.DemandeStatutRepository;
import com.wora.bankservice.repository.impl.DemandeStatutRepositoryImpl;
import com.wora.bankservice.service.DemandeStatutService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class DemandeStatutServiceImplt implements DemandeStatutService {
    private DemandeStatutRepository demandeStatutRepository;
    @Inject
    public DemandeStatutServiceImplt() {
        demandeStatutRepository = new DemandeStatutRepositoryImpl();
    }
    @Override
    public DemandeStatut save(DemandeStatut demandeStatut) {
        demandeStatutRepository.save(demandeStatut);
        return null;
    }
}
