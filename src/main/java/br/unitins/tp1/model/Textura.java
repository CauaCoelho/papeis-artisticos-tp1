package br.unitins.tp1.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(shape = Shape.OBJECT)
public enum Textura { 
 //Trançado, casca de ovo, kraft, liso, etc.
    TRANCADO(1l, "Trançado"),
    CASCA_DE_OVO(2l, "Casca de ovo"),
    KRAFT(3l, "Kraft"),
    LISO(4l, "Liso");

    @JsonProperty("id")
    public final Long ID;

    @JsonProperty("nome")
    public final String NOME;

    Textura(Long id, String nome) {
        this.ID = id;
        this.NOME = nome;
    }

    public static Textura valueOf(Long id){
        if (id == null) return null;
        for (Textura textura : Textura.values()) {
            if (textura.ID != null && textura.ID.equals(id))
                return textura;
        }
        return null;
    }

}


