package br.unitins.tp1.dto;

import br.unitins.tp1.model.Textura;

public record PapelAvulsoDTO(
    String tipoPapel,
    String tamanho,
    Textura textura
) {

}
