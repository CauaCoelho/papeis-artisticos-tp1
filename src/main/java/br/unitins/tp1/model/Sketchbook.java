package br.unitins.tp1.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Sketchbook extends Produto {
    private Integer quantidadeFolhas;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_capa")
    @Enumerated(EnumType.STRING)
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
