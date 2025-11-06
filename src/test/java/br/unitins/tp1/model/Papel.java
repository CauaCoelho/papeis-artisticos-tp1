package br.unitins.tp1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Papel extends DefaultEntity {
    private Long id;
    private Textura textura;
    private Formato formato;

    @ManyToOne
    @JoinColumn(name = "id_sketchbook")
    private Sketchbook sketchbook;
    @ManyToOne
    @JoinColumn(name = "id_bloco")
    private Bloco bloco;
    

    public Formato getFormato() {
        return formato;
    }

    public void setFormato(Formato formato) {
        this.formato = formato;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Textura getTextura() {
        return textura;
    }

    public void setTextura(Textura textura) {
        this.textura = textura;
    }
    
}
