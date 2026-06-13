package br.unitins.tp1.dto;

import jakarta.validation.constraints.NotNull;

public record ProdutoDTO(
        @NotNull(message = "idTextura é obrigatório") Long idTextura,
        Long idMarca,
        EspecificacaoTecnicaDTO especificacaoTecnica

) {

}
