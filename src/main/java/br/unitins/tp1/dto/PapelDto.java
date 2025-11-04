package br.unitins.tp1.dto;

import br.unitins.tp1.model.Formato;
import br.unitins.tp1.model.Textura;
import jakarta.validation.constraints.NotBlank;

public record PapelDTO(
    @NotBlank (message = "")
    Textura textura,
    Formato formato
) {

}
