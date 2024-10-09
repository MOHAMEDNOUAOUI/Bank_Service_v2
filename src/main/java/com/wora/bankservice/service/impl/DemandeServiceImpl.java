package com.wora.bankservice.service.impl;

import com.wora.bankservice.entity.Demande;
import com.wora.bankservice.entity.DemandeStatut;
import com.wora.bankservice.entity.Statut;
import com.wora.bankservice.repository.impl.DemandeRepositoryImpl;
import com.wora.bankservice.repository.impl.DemandeStatutRepositoryImpl;
import com.wora.bankservice.repository.impl.StatutRepositoryImpl;
import com.wora.bankservice.service.DemandeService;
import com.wora.bankservice.validation.validator;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.wora.bankservice.validation.validator.validateDate;
import static com.wora.bankservice.validation.validator.validationCalcule;

public class DemandeServiceImpl implements DemandeService {

    private DemandeRepositoryImpl repo;
    private StatutRepositoryImpl statutRepo;
    private DemandeStatutRepositoryImpl demandRepo;
    public DemandeServiceImpl() {
        repo = new DemandeRepositoryImpl();
        statutRepo = new StatutRepositoryImpl();
        demandRepo = new DemandeStatutRepositoryImpl();
    }


    @Override
    public Demande createDemande(Demande demande) {
        Optional<Statut> statut = statutRepo.findByStatut("PENDING");
        if(demande != null) {
            if(validationCalcule(demande)){
                if (statut.isPresent()) {
                    DemandeStatut demandStatus = new DemandeStatut();
                    System.out.println(demande.getDuree());
                    System.out.println(demande.getMensualite());
                    demandStatus.setStatut(statut.get());
                    demandStatus.setDemande(demande);
                    demandStatus.setDateInsert(LocalDateTime.now());
                    repo.save(demande);
                    demandRepo.save(demandStatus);
                }
            }
        }
        return demande;
    }

    @Override
    public List<Demande> findAllDemandes() {
        return repo.findAll();
    }

    @Override
    public Optional<Demande> findDemandeById(Long id) {
        return repo.findById(id);
    }

    @Override
    public boolean deleteDemandeById(Demande demande) {
        if(repo.delete(demande)) {
            return true;
        }
        return false;
    }

    @Override
    public Optional<Demande> updateDemande(Demande demande) {
        Demande demande1 = repo.update(demande);
        if(demande1 != null) {
            return Optional.of(demande1);
        }
        return Optional.empty();
    }

    @Override
    public List<Demande> findAllDemandesByDate(String date) {
        List<Demande> demandeList = List.of();
        LocalDate newdate = validateDate(date);
        if(newdate != null){
            demandeList = repo.findDemandeByDate(newdate);
        }
        return demandeList;
    }
}
