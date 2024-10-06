package com.wora.bankservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="statut")
public class Statut  implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @Column(name = "statut")
    private String Statut;

    @OneToMany(mappedBy = "statut" , cascade = CascadeType.ALL , orphanRemoval = true)
    private Set<DemandeStatut> demandeStatuts = new HashSet<>();

}
