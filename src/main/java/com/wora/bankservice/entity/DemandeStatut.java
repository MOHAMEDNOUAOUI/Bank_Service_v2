package com.wora.bankservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "demande_statut")
public class DemandeStatut {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "demande_id")
    private Demande demande;

    @ManyToOne
    @JoinColumn(name = "statut_id")
    private Statut statut;

    @Column(name = "date_insert")
    private LocalDateTime dateInsert;
}
