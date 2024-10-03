package com.wora.bankservice.repository;

import com.wora.bankservice.entity.Demande;

import java.util.List;
import java.util.Optional;

public interface DemandeRepository {

    public Demande save(Demande demande);
    public Optional<Demande> findById(Long id);
    public List<Demande> findAll();
    public Demande delete(Demande demande);
    public Demande update(Demande demande);

}
