package br.unitins.tp1.dto;

import java.util.List;

import br.unitins.tp1.model.Categoria;
import jakarta.validation.constraints.NotNull;

public record ProdutoDTO(
    @NotNull(message = "")
    Long idTextura,
    Long idMarca,
    List<Categoria> categoria,
    EspecificacaoTecnicaDTO especificacaoTecnica

) {

}
