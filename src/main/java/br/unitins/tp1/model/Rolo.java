package br.unitins.tp1.model;

import jakarta.persistence.Entity;

@Entity
public class Rolo extends Produto {
    private Double comprimento;
    private Categoria categoria;
    
    public Double getComprimento() {
        return comprimento;
    }
    public void setComprimento(Double comprimento) {
        this.comprimento = comprimento;
    }
    public Categoria getCategoria() {
        return categoria;
    }
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

}
  