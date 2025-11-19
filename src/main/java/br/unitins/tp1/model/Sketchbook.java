package br.unitins.tp1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Sketchbook extends Papel {
    private Integer quantidadeFolhas;
    @OneToOne
    @JoinColumn(name = "id_capa")
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
