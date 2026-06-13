package br.unitins.tp1.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SketchbookDTO(
        
        @NotNull(message = "nome é obrigatório")
        @NotBlank(message = "nome não pode ser vazio")
        String nome,

        @NotNull(message = "idCapa é obrigatório")
        Long idCapa,

        @NotNull(message = "Quantidade de folhas é obrigatória")
        @Min(value = 1, message = "Quantidade de folhas deve ser maior ou igual a 1")
        Integer quantidadeFolhas,

        @NotNull(message = "idTextura é obrigatório")
        Long idTextura,

        Long idMarca) {

}
