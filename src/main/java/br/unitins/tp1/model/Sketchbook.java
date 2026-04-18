package br.unitins.tp1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
public class Sketchbook extends Produto {
    private Integer quantidadeFolhas;
    @Enumerated(EnumType.STRING)
    @Column(name = "capa_id", nullable = false)
    private Capa capa;
    
    

    public Integer getQuantidadeFolhas() {
        return quantidadeFolhas;
    }

    public void setQuantidadeFolhas(Integer quantidadeFolhas) {
        this.quantidadeFolhas = quantidadeFolhas;
    }   

    public Capa getCapa() {
        return capa;
    }
    public void setCapa(Capa capa) {
        this.capa = capa;
    }


    
}
