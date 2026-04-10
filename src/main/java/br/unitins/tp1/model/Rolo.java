package br.unitins.tp1.model;

import jakarta.persistence.Entity;

@Entity
public class Rolo extends Produto {
    private Double comprimento;
    
    public Double getComprimento() {
        return comprimento;
    }
    public void setComprimento(Double comprimento) {
        this.comprimento = comprimento;
    }

}
  