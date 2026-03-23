package br.unitins.tp1.dto;

import br.unitins.tp1.model.Textura;
import jakarta.validation.constraints.NotBlank;

public record ProdutoDTO(
    @NotBlank (message = "")
    Textura textura
) {

}
