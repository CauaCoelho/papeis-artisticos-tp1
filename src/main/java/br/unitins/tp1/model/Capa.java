package br.unitins.tp1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Capa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String material;

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }


}
