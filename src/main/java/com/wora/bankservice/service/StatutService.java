package com.wora.bankservice.service;

import com.wora.bankservice.entity.Statut;

import java.util.Optional;

public interface StatutService {

    Optional<Statut> findByStatut(String statut);

}
