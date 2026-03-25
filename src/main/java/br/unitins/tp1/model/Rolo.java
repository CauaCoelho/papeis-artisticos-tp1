package br.unitins.tp1.model;

import jakarta.persistence.Entity;

@Entity
public class Rolo extends Produto {
    private Double comprimento;
    private Categoria categoria;

}
  