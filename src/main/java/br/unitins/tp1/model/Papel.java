package br.unitins.tp1.model;

import jakarta.persistence.Entity;

@Entity
public class Papel extends DefaultEntity {
    private String modelo;

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
}
