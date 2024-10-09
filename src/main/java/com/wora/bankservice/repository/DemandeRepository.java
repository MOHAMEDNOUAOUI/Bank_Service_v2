package com.wora.bankservice.repository;

import com.wora.bankservice.entity.Demande;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DemandeRepository {

    Demande save(Demande demande);
    Optional<Demande> findById(Long id);
    List<Demande> findAll();
    boolean delete(Demande demande);
    Demande update(Demande demande);
    List<Demande> findDemandeByDate(LocalDate date);

}
