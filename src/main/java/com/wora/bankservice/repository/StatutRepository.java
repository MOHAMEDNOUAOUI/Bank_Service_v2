package com.wora.bankservice.repository;

import com.wora.bankservice.entity.Statut;

import java.util.List;
import java.util.Optional;

public interface StatutRepository {
    List<Statut> findAll();
    Optional<Statut> findByStatut(String statut);
}
