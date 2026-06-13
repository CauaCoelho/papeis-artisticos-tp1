package br.unitins.tp1.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record BlocoDTO(
    @NotNull(message = "Quantidade de folhas é obrigatória")
    @Min(value = 1, message = "Quantidade de folhas deve ser maior ou igual a 1")
    Integer quantidadeFolhas,

    @NotNull(message = "idTextura é obrigatório")
    Long idTextura
) {

}
