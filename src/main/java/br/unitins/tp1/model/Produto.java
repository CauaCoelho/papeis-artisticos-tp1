package br.unitins.tp1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)

public class Produto extends DefaultEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Textura textura;

    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
  

    public Textura getTextura() {
        return textura;
    }

    public void setTextura(Textura textura) {
        this.textura = textura;
    }
    
}
