package br.unitins.tp1.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class DefaultEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Gerar automaticamente o valor do Id e seu "formato"
    private Long id;
}
