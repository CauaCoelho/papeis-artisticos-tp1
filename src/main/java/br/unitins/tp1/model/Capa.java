package br.unitins.tp1.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Capa {
    PAPEL_COUCHE(1L, "Papel Couchê"),
    BROCHURA(2L, "Brochura"),
    COURO(3L, "Couro"),
    CARTAO(4L, "Cartão");



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public final Long ID;
    private final String NOME;

    Capa(Long id, String nome) {
        this.ID = id;
        this.NOME = nome;
    }

    public Long getID() {
        return ID;
    }

    public String getNOME() {
        return NOME;
    }

    public static Capa valueOf(long id) {
        for (Capa capa : Capa.values()) {
            if (capa.ID == id)
                return capa;
        }
        return null;
    }

}
