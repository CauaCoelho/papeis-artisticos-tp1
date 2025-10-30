package br.unitins.tp1.dto;

import br.unitins.tp1.model.Formato;
import br.unitins.tp1.model.Textura;

public record PapelDto(
    Textura textura,
    Formato formato
) {

}
