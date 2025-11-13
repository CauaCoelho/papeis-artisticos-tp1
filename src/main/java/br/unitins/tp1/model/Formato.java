package br.unitins.tp1.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Formato {
//Formato diz respeito aos tamanhos das folhas: A1-A4, B1-B4 e assim por diante
    A6(1, "A6 105 mm x 148 mm"),
    A5(2, "A5 148 mm x 210 mm"),
    A4(3, "A4 210 mm x 297 mm"),
    A3(4, "A3 297 mm x 420 mm"),
    A2(5, "A2 420 mm x 594 mm"),
    B6(6, "B6 125 mm x 176 mm"),
    B5(7, "B5 176 mm x 250 mm"),
    B4(8, "B4 250 mm x 353 mm"),
    B3(9, "B3 353 mm x 500 mm"),
    B2(10, "B2 500 mm x 707 mm"),

    // Rolos — altura variável, comprimento fixo de 10 metros (10.000 mm)
    ROLO_S(11, "Rolo Small 210 mm x 10.000 mm"),
    ROLO_M(12, "Rolo Medium 297 mm x 10.000 mm"),
    ROLO_L(13, "Rolo Large 420 mm x 10.000 mm");


@JsonProperty("id")
public final Long ID;

@JsonProperty("nome")
public final String NOME;

Formato(long id, String nome) {
        this.ID = id;
        this.NOME = nome;
    }

    public static Formato valueOf(Long id){
        for (Formato formato : Formato.values()) {
            if(id == formato.ID)
                return formato;
        }
        return null;
    }
}
