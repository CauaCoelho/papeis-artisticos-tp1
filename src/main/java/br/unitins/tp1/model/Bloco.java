package br.unitins.tp1.model;

import jakarta.persistence.Entity;

@Entity
public class Bloco extends Papel{
private Integer quantidadeFolhas;

public Integer getQuantidadeFolhas() {
    return quantidadeFolhas;
}

public void setQuantidadeFolhas(Integer quantidadeFolhas) {
    this.quantidadeFolhas = quantidadeFolhas;
}

}
