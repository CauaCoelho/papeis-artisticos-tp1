package br.unitins.tp1.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

public class Categoria {

    @Id
    @GeneratedValue
    public Long id;

    public String nome;
}
