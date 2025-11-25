package br.unitins.tp1.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

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
        for (Textura textura : Textura.values()) {
            if(id == textura.ID)
                return textura;
        }
        return null;
    }

}


