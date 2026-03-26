package br.unitins.tp1.model;

import jakarta.persistence.Entity;

@Entity
public class Bloco extends Produto{
private Integer quantidadeFolhas;
private Categoria categoria;

public Integer getQuantidadeFolhas() {
    return quantidadeFolhas;
}

public void setQuantidadeFolhas(Integer quantidadeFolhas) {
    this.quantidadeFolhas = quantidadeFolhas;
}

}
