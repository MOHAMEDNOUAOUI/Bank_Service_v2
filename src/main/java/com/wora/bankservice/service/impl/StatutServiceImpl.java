package com.wora.bankservice.service.impl;

import com.wora.bankservice.entity.Statut;
import com.wora.bankservice.repository.StatutRepository;
import com.wora.bankservice.repository.impl.StatutRepositoryImpl;
import com.wora.bankservice.service.StatutService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Optional;

@ApplicationScoped
public class StatutServiceImpl implements StatutService {
    private StatutRepository repository;

    @Inject
    public StatutServiceImpl() {
        this.repository = new StatutRepositoryImpl();
    }
    @Override
    public Optional<Statut> findByStatut(String statut) {
        Optional<Statut> statut1 = repository.findByStatut(statut);
        return statut1;
    }
}
