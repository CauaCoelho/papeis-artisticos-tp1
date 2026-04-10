package br.unitins.tp1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Marca {
    @Id
    @GeneratedValue
    public Long id;
    private String nome;
}
