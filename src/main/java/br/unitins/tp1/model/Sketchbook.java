package br.unitins.tp1.model;

import java.beans.Transient;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Sketchbook extends Produto {
    private Integer quantidadeFolhas;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_capa")
    @Column(name = "capa_id", nullable = false)
    private Long capaId;
    
    

    public Integer getQuantidadeFolhas() {
        return quantidadeFolhas;
    }

    public void setQuantidadeFolhas(Integer quantidadeFolhas) {
        this.quantidadeFolhas = quantidadeFolhas;
    }
    @Transient
    public Capa getCapa() {
        return Capa.valueOf(capaId);
    }
    @Transient
    public void setCapa(Capa capa) {
        this.capaId = (capa != null) ? capa.ID : null;
    }


    
}
