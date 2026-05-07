package br.unitins.tp1.dto;

import java.util.List;


import jakarta.validation.constraints.NotNull;

public record ProdutoDTO(
    @NotNull(message = "")
    Long idTextura,
    Long idMarca,
    EspecificacaoTecnicaDTO especificacaoTecnica

) {

}
