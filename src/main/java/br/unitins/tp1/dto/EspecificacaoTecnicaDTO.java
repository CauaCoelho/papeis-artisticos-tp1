package br.unitins.tp1.dto;

import jakarta.validation.constraints.NotNull;

public record EspecificacaoTecnicaDTO(
    @NotNull(message = "")
    Double ph,
    @NotNull(message = "")
    Double opacidade,
    @NotNull(message = "")
    Double absorcao
) {

}
