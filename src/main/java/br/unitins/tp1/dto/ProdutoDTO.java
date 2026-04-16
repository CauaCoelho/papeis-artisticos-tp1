package br.unitins.tp1.dto;

import java.util.List;

import br.unitins.tp1.model.Categoria;
import br.unitins.tp1.model.Marca;
import br.unitins.tp1.model.Textura;
import jakarta.validation.constraints.NotNull;

public record ProdutoDTO(
    @NotNull(message = "")
    Textura textura,
    Marca marca,
    List<Categoria> categoria,
    EspecificacaoTecnicaDTO especificacaoTecnica

) {

}
