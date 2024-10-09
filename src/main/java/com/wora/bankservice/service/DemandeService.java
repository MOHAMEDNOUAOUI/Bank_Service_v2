package com.wora.bankservice.service;

import com.wora.bankservice.entity.Demande;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DemandeService {

    Demande createDemande(Demande demande);
    List<Demande> findAllDemandes();
    Optional<Demande> findDemandeById(Long id);
    boolean deleteDemandeById(Demande demande);
    Optional<Demande> updateDemande(Demande demande);
    List<Demande> findAllDemandesByDate(String date);
}
