package br.unitins.tp1.dto;

import br.unitins.tp1.model.Bloco;
import br.unitins.tp1.model.Textura;

public record BlocoDTOResponse(
    Long id,
    Integer quantidadeFolhas,
    Textura textura
) {
    public static BlocoDTOResponse valueOf(Bloco bloco){
        return new BlocoDTOResponse(
            bloco.getId(),
            bloco.getQuantidadeFolhas(),
            bloco.getTextura()
        );
    }

}
