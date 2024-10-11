package com.wora.bankservice.service.impl;

import com.wora.bankservice.entity.Demande;
import com.wora.bankservice.entity.DemandeStatut;
import com.wora.bankservice.entity.Statut;
import com.wora.bankservice.repository.DemandeRepository;
import com.wora.bankservice.repository.DemandeStatutRepository;
import com.wora.bankservice.repository.StatutRepository;
import com.wora.bankservice.repository.impl.DemandeRepositoryImpl;
import com.wora.bankservice.repository.impl.DemandeStatutRepositoryImpl;
import com.wora.bankservice.repository.impl.StatutRepositoryImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.swing.text.html.Option;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DemandeServiceImplTest {


    @Mock
    private DemandeRepositoryImpl demandeRepository;

    @Mock
    private StatutRepositoryImpl statutRepository;

    @Mock
    private DemandeStatutRepositoryImpl demandeStatutRepository;

    @InjectMocks
    private DemandeServiceImpl demandeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateDemande_valid() {
        Demande demande = new Demande();
        demande.setDuree(12);
        demande.setMensualite(500);

        Statut statut = new Statut();
        statut.setStatut("PENDING");

        when(statutRepository.findByStatut("PENDING")).thenReturn(Optional.of(statut));
        when(demandeRepository.save(demande)).thenReturn(demande);
        Demande result = demandeService.createDemande(demande);
        assertNotNull(result);
        verify(demandeStatutRepository).save(any(DemandeStatut.class));
    }
}