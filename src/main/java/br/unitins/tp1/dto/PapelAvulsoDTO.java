package br.unitins.tp1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PapelAvulsoDTO(
    @NotBlank(message = "tipoPapel é obrigatório")
    String tipoPapel,

    @NotBlank(message = "tamanho é obrigatório")
    String tamanho,

    @NotNull(message = "idTextura é obrigatório")
    Long idTextura
) {

}
